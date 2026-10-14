package com.meflea.landofmonsters.entity;

import com.meflea.landofmonsters.LandOfMonsters;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, LandOfMonsters.MOD_ID);

    public static final RegistryObject<EntityType<DoratsEntity>> DORATS =
            ENTITY_TYPES.register("dorats", () -> EntityType.Builder.of(DoratsEntity::new, MobCategory.CREATURE)
                    .sized(1.0f, 1.0f) // placeholder size, tune once in-game
                    .build("dorats"));

    public static final RegistryObject<EntityType<RodanEntity>> RODAN =
            ENTITY_TYPES.register("rodan", () -> EntityType.Builder.of(RodanEntity::new, MobCategory.MONSTER)
                    .sized(2.0f, 2.5f) // placeholder size, we'll tune this once he's in-game
                    .build("rodan"));
}