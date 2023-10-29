package lv.sanelite.initium.entity.client;

import lv.sanelite.initium.Initium;
import lv.sanelite.initium.entity.actor.AzureNPC;
import lv.sanelite.initium.entity.dataset.Character;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.renderer.layer.AutoGlowingGeoLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;


public class AzureRenderer extends GeoEntityRenderer<AzureNPC> {
    public AzureRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AzureModel());
        withScale(0.9f);

            addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(AzureNPC animatable) {

        return new ResourceLocation(Initium.MOD_ID, "textures/entity/" + Character.getCharacter(animatable.getThisCharacter()).getName() + ".png");

        //return new ResourceLocation(Initium.MOD_ID, "textures/entity/sentry.png");
    }

}
