package com.meflea.landofmonsters.entity.client;

import com.meflea.landofmonsters.entity.RodanEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RodanRenderer extends GeoEntityRenderer<RodanEntity> {

    public RodanRenderer(EntityRendererProvider.Context context) {
        super(context, new RodanModel());
    }
}