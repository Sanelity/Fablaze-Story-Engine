package lv.sanelite.initium.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lv.sanelite.initium.Initium;
import lv.sanelite.initium.entity.custom.SentryNPC;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;


public class SentryRenderer extends GeoEntityRenderer<SentryNPC> {
    public SentryRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SentryModel());
        this.shadowRadius = 0.5f;
    }


    @Override
    public ResourceLocation getTextureLocation(SentryNPC instance) {
        return new ResourceLocation(Initium.MOD_ID, "textures/entity/sentrytexture.png");
    }



    @Override
    public RenderType getRenderType(SentryNPC animatable, float partialTick, PoseStack poseStack,
                                    @Nullable MultiBufferSource bufferSource,
                                    @Nullable VertexConsumer buffer, int packedLight,
                                    ResourceLocation texture) {
        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}
