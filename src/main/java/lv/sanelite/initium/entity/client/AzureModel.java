package lv.sanelite.initium.entity.client;

import lv.sanelite.initium.Initium;
import lv.sanelite.initium.entity.custom.AzureNPC;
import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.azurelib.model.data.EntityModelData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AzureModel extends GeoModel<AzureNPC> {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setCustomAnimations(AzureNPC animatable, long instanceId, AnimationState<AzureNPC> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        final CoreGeoBone head = getAnimationProcessor().getBone("head");
        final EntityModelData extraData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        if (head != null) {
            head.setRotX(extraData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(extraData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

    @Override
    public ResourceLocation getModelResource(AzureNPC geoAnimatable) {
        return new ResourceLocation(Initium.MOD_ID, "geo/sentry.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AzureNPC geoAnimatable) {
        return new ResourceLocation(Initium.MOD_ID, "textures/entity/sentrytexture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AzureNPC geoAnimatable) {
        return new ResourceLocation(Initium.MOD_ID, "animations/sentry.animation.json");
    }

}
