package com.meflea.landofmonsters.entity;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public abstract class TamableKaijuEntity extends TamableAnimal implements GeoEntity {

    private final AnimatableInstanceCache cache =
            GeckoLibUtil.createInstanceCache(this);

    protected static final RawAnimation IDLE_ANIM =
            RawAnimation.begin().thenLoop("idle");

    protected static final RawAnimation WALK_ANIM =
            RawAnimation.begin().thenLoop("walk");

    protected TamableKaijuEntity(
            EntityType<? extends TamableAnimal> type,
            Level level) {

        super(type, level);
    }

    protected abstract boolean isTameItem(ItemStack stack);

    public static AttributeSupplier.Builder createAttributes() {

        return TamableAnimal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(
            ServerLevel level,
            AgeableMob otherParent) {

        return null;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Override
    public InteractionResult mobInteract(
            Player player,
            InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);

        if (!this.level().isClientSide) {

            if (!this.isTame()) {

                if (isTameItem(stack)) {

                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }

                    if (this.random.nextInt(3) == 0) {

                        this.tame(player);

                        this.navigation.stop();

                        this.setTarget(null);

                        this.setOrderedToSit(true);

                        this.level().broadcastEntityEvent(
                                this,
                                (byte) 7
                        );

                    } else {

                        this.level().broadcastEntityEvent(
                                this,
                                (byte) 6
                        );
                    }

                    return InteractionResult.SUCCESS;
                }

                return InteractionResult.PASS;
            }

            if (this.isOwnedBy(player) && stack.isEmpty()) {

                this.setOrderedToSit(false);

                if (!player.isPassenger()) {
                    player.startRiding(this);
                }

                return InteractionResult.SUCCESS;
            }
        }

        return super.mobInteract(player, hand);
    }

    protected <E extends TamableKaijuEntity> PlayState predicate(
            AnimationState<E> state) {

        if (state.isMoving()) {
            state.getController().setAnimation(WALK_ANIM);
        }
        else {
            state.getController().setAnimation(IDLE_ANIM);
        }

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(
            AnimatableManager.ControllerRegistrar controllers) {

        controllers.add(
                new AnimationController<>(
                        this,
                        "base_controller",
                        5,
                        this::predicate
                )
        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}