package com.meflea.landofmonsters.entity.client;

import com.meflea.landofmonsters.LandOfMonsters;
import com.meflea.landofmonsters.entity.RodanEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RodanModel extends GeoModel<RodanEntity> {

    @Override
    public ResourceLocation getModelResource(RodanEntity animatable) {
        return new ResourceLocation(LandOfMonsters.MOD_ID, "geo/rodan.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RodanEntity animatable) {
        return new ResourceLocation(LandOfMonsters.MOD_ID, "textures/entity/rodan.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RodanEntity animatable) {
        return new ResourceLocation(LandOfMonsters.MOD_ID, "animations/rodan.animation.json");
    }
}