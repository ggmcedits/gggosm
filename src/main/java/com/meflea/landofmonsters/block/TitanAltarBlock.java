package com.meflea.landofmonsters.block;

import com.meflea.landofmonsters.entity.ModEntityTypes;
import com.meflea.landofmonsters.entity.RodanEntity;
import com.meflea.landofmonsters.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class TitanAltarBlock extends Block {

    public TitanAltarBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        return tryPerformRitual(level, pos, player, stack);
    }

    private InteractionResult tryPerformRitual(Level level, BlockPos pos, Player player, ItemStack stack) {
        if (!stack.is(ModItems.RODAN_AMULET.get())) {
            return InteractionResult.PASS;
        }

        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        RodanEntity rodan = new RodanEntity(ModEntityTypes.RODAN.get(), level);
        rodan.moveTo(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, player.getYRot(), 0.0F);
        level.addFreshEntity(rodan);

        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        return InteractionResult.CONSUME;
    }
}