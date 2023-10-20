package lv.sanelite.initium.entity.client;

import lv.sanelite.initium.entity.custom.AzureNPC;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.renderer.layer.AutoGlowingGeoLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;


public class AzureRenderer extends GeoEntityRenderer<AzureNPC> {
    public AzureRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AzureModel());
        withScale(0.9f);

        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
