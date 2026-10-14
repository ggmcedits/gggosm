package com.meflea.landofmonsters.entity.client;

import com.meflea.landofmonsters.LandOfMonsters;
import com.meflea.landofmonsters.entity.DoratsEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DoratsModel extends GeoModel<DoratsEntity> {

    @Override
    public ResourceLocation getModelResource(DoratsEntity animatable) {
        return new ResourceLocation(LandOfMonsters.MOD_ID, "geo/dorats.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DoratsEntity animatable) {
        return new ResourceLocation(LandOfMonsters.MOD_ID, "textures/entity/dorats.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DoratsEntity animatable) {
        return new ResourceLocation(LandOfMonsters.MOD_ID, "animations/dorats.animation.json");
    }
}