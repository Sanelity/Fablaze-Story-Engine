package lv.sanelite.initium.entity.client;

import lv.sanelite.initium.Initium;
import lv.sanelite.initium.entity.custom.SentryNPC;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class SentryModel extends AnimatedGeoModel<SentryNPC> {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(SentryNPC entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            ((IBone) head).setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }

    @Override
    public ResourceLocation getModelResource(SentryNPC object) {
        return new ResourceLocation(Initium.MOD_ID, "geo/sentry.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SentryNPC object) {
        return new ResourceLocation(Initium.MOD_ID, "textures/entity/sentrytexture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SentryNPC animatable) {
        return new ResourceLocation(Initium.MOD_ID, "animations/sentry.animation.json");
    }
}
