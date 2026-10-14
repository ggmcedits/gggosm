package com.meflea.landofmonsters.block;

import com.meflea.landofmonsters.LandOfMonsters;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, LandOfMonsters.MOD_ID);

    public static final RegistryObject<Block> TITAN_ALTAR = BLOCKS.register("titan_altar",
            () -> new TitanAltarBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(50.0F, 1200.0F)
                    .sound(SoundType.STONE)
                    .noOcclusion()));
}