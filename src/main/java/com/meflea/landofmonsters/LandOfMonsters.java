package com.meflea.landofmonsters;

import com.meflea.landofmonsters.block.ModBlocks;
import com.meflea.landofmonsters.entity.ModEntityTypes;
import com.meflea.landofmonsters.entity.TamableKaijuEntity;
import com.meflea.landofmonsters.entity.client.RodanRenderer;
import com.meflea.landofmonsters.item.ModItems;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import com.meflea.landofmonsters.entity.client.DoratsRenderer;

@Mod(LandOfMonsters.MOD_ID)
public class LandOfMonsters {

    public static final String MOD_ID = "lam";

    public LandOfMonsters() {

        IEventBus modEventBus =
                FMLJavaModLoadingContext.get().getModEventBus();

        ModEntityTypes.ENTITY_TYPES.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);

        modEventBus.addListener(this::registerAttributes);

        modEventBus.addListener(this::registerRenderers);
    }

    private void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.RODAN.get(), TamableKaijuEntity.createAttributes().build());
        event.put(ModEntityTypes.DORATS.get(), TamableKaijuEntity.createAttributes().build());
    }

    private void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.RODAN.get(), RodanRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.DORATS.get(), DoratsRenderer::new);
    }
}