package lv.sanelite.initium.entity.client;

import lv.sanelite.initium.Initium;
import lv.sanelite.initium.entity.actor.AzureNPC;
import lv.sanelite.initium.entity.dataset.Character;
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
    public ResourceLocation getModelResource(AzureNPC geoAnimatable) {
//        return locate("geo", Character.getCharacter(geoAnimatable.getThisCharacter()).getModel());
        return new ResourceLocation(Initium.MOD_ID, "geo/" + Character.getCharacter(geoAnimatable.getThisCharacter()).getModel());
//        return new ResourceLocation(Initium.MOD_ID, "geo/sentry.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AzureNPC geoAnimatable) {

//        return locate("textures", Character.getCharacter(geoAnimatable.getThisCharacter()).getName());
        return new ResourceLocation(Initium.MOD_ID, "textures/entity/" + Character.getCharacter(geoAnimatable.getThisCharacter()).getName() + ".png");
//        return new ResourceLocation(Initium.MOD_ID, "textures/entity/sentry.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AzureNPC geoAnimatable) {
//        return locate("animations", Character.getCharacter(geoAnimatable.getThisCharacter()).getAnimation());
        return  new ResourceLocation(Initium.MOD_ID, "animations/" + Character.getCharacter(geoAnimatable.getThisCharacter()).getAnimation());
//        return new ResourceLocation(Initium.MOD_ID, "animations/sanelite.animation.json");
    }

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
}
