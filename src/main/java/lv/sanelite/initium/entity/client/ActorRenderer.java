package lv.sanelite.initium.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lv.sanelite.initium.Initium;
import lv.sanelite.initium.entity.custom.ActorNPC;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;


public class ActorRenderer extends GeoEntityRenderer<ActorNPC> {
    public ActorRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ActorModel());
        this.shadowRadius = 0.5f;
    }


    @Override
    public ResourceLocation getTextureLocation(ActorNPC instance) {
        return new ResourceLocation(Initium.MOD_ID, "textures/entity/sentrytexture.png");
    }



    @Override
    public RenderType getRenderType(ActorNPC animatable, float partialTick, PoseStack poseStack,
                                    @Nullable MultiBufferSource bufferSource,
                                    @Nullable VertexConsumer buffer, int packedLight,
                                    ResourceLocation texture) {
        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}
