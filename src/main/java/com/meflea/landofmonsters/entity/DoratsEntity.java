package com.meflea.landofmonsters.entity;

import net.minecraft.world.entity.EntityType;
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
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

/**
 * Dorats - tameable kaiju. Ground-only, no riding/flight (scope: just get him in-game).
 */
public class DoratsEntity extends TamableKaijuEntity {

    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.dorats.idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.dorats.walk");

    public DoratsEntity(EntityType<? extends TamableKaijuEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new GroundPathNavigation(this, level);
    }

    @Override
    protected boolean isTameItem(ItemStack stack) {
        return stack.is(Items.GOLD_INGOT); // placeholder tame item, tell me if you want something else
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 32.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    protected <E extends TamableKaijuEntity> PlayState predicate(AnimationState<E> state) {
        if (state.isMoving()) {
            state.getController().setAnimation(WALK);
        } else {
            state.getController().setAnimation(IDLE);
        }
        return PlayState.CONTINUE;
    }
}