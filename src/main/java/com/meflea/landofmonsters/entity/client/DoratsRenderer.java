package com.meflea.landofmonsters.entity.client;

import com.meflea.landofmonsters.entity.DoratsEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DoratsRenderer extends GeoEntityRenderer<DoratsEntity> {

    public DoratsRenderer(EntityRendererProvider.Context context) {
        super(context, new DoratsModel());
    }
}