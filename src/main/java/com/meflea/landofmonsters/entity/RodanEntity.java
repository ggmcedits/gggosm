package com.meflea.landofmonsters.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;

//basic
public class RodanEntity extends TamableKaijuEntity implements PlayerRideableJumping {

    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.rodan.idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.rodan.walk");
    private static final RawAnimation FLY = RawAnimation.begin().thenLoop("animation.rodan.fly");

    private boolean isFlying = false;

    public RodanEntity(EntityType<? extends TamableKaijuEntity> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new GroundPathNavigation(this, level);
    }

    @Override
    protected boolean isTameItem(ItemStack stack) {
        return stack.is(Items.MAGMA_BLOCK);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 32.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    // riding

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        Entity passenger = this.getFirstPassenger();
        if (passenger instanceof Player player && this.isOwnedBy(player)) {
            return player;
        }
        return null;
    }

    @Override
    public boolean isControlledByLocalInstance() {
        return this.getControllingPassenger() instanceof Player;
    }

    @Override
    protected void positionRider(Entity passenger, Entity.MoveFunction callback) {
        super.positionRider(passenger, callback);
        double yOffset = this.getBbHeight() * 1.1D;
        Vec3 backOffset = new Vec3(0.0D, 0.0D, -0.3D).yRot((float) -Math.toRadians(this.getYRot()));
        Vec3 seatPos = this.position().add(0.0D, yOffset, 0.0D).add(backOffset);
        callback.accept(passenger, seatPos.x, seatPos.y, seatPos.z);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected float getRiddenSpeed(Player player) {
        float base = (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED);
        return this.isFlying ? base * 4.0F : base;
    }

    // start flighg

    @Override
    public boolean canJump() {
        return true;
    }

    @Override
    public void onPlayerJump(int jumpPower) {
        if (!this.isFlying && this.onGround() && jumpPower > 0) {
            this.isFlying = true;
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.5D, 0.0D));
        }
    }

    @Override
    public void handleStartJump(int jumpPower) {}

    @Override
    public void handleStopJump() {}

    @Override
    public boolean isNoGravity() {
        return this.isFlying || super.isNoGravity();
    }

    // moving

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isVehicle() && this.getControllingPassenger() instanceof Player player) {
            this.setYRot(player.getYRot());
            this.yRotO = this.getYRot();
            this.yBodyRot = this.getYRot();
            this.yHeadRot = this.getYRot();

            float forward = player.zza;

            if (this.isFlying) {
                this.setXRot(player.getXRot());

                float speed = this.getRiddenSpeed(player);
                Vec3 look = this.getLookAngle();
                Vec3 motion = look.scale(forward * speed * 0.4D);

                this.setDeltaMovement(motion);
                this.move(MoverType.SELF, this.getDeltaMovement());

                if (this.onGround()) {
                    this.isFlying = false;
                }
            } else {
                this.setXRot(0.0F);
                this.setSpeed(this.getRiddenSpeed(player));
                super.travel(new Vec3(player.xxa, travelVector.y, forward));
            }
            return;
        }
        super.travel(travelVector);
    }

    // animations

    @Override
    protected <E extends TamableKaijuEntity> PlayState predicate(AnimationState<E> state) {
        if (this.isFlying) {
            state.getController().setAnimation(FLY);
        } else if (state.isMoving()) {
            state.getController().setAnimation(WALK);
        } else {
            state.getController().setAnimation(IDLE);
        }
        return PlayState.CONTINUE;
    }
}