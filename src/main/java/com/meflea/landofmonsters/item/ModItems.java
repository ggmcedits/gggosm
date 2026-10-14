package com.meflea.landofmonsters.item;

import com.meflea.landofmonsters.LandOfMonsters;
import com.meflea.landofmonsters.block.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, LandOfMonsters.MOD_ID);

    public static final RegistryObject<Item> TITAN_ALTAR_ITEM = ITEMS.register("titan_altar",
            () -> new BlockItem(ModBlocks.TITAN_ALTAR.get(), new Item.Properties()));

    public static final RegistryObject<Item> RODAN_AMULET = ITEMS.register("rodan_amulet",
            () -> new RodanAmuletItem(new Item.Properties().stacksTo(1)));
}