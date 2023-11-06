package lv.sanelite.initium;

import com.mojang.logging.LogUtils;
import lv.sanelite.initium.block.ModBlocks;
import lv.sanelite.initium.entity.ModEntityTypes;
import lv.sanelite.initium.entity.ModVillager;
//import lv.sanelite.initium.entity.client.ActorRenderer;
import lv.sanelite.initium.entity.client.AzureRenderer;
import lv.sanelite.initium.event.ModForgeEvent;
import lv.sanelite.initium.item.ModItems;
import lv.sanelite.initium.networking.ModMessages;
import lv.sanelite.initium.painting.ModPaintings;
import lv.sanelite.initium.world.feature.ModConfiguredFeatures;
import lv.sanelite.initium.world.feature.ModPlacedFeatures;
import mod.azure.azurelib.AzureLib;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
//import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Initium.MOD_ID)
//Comment
public class Initium {
    public static final String MOD_ID = "initium";
    private static final Logger LOGGER = LogUtils.getLogger();
    public Initium() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgebus = MinecraftForge.EVENT_BUS;

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModVillager.register(modEventBus);
        ModPaintings.register(modEventBus);

        ModConfiguredFeatures.register(modEventBus);
        ModPlacedFeatures.register(modEventBus);

        ModEntityTypes.register(modEventBus);

        AzureLib.initialize();

        modEventBus.addListener(this::commonSetup);
        forgebus.addListener(ModForgeEvent::onUnload);
        forgebus.register(this);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(()->{
            ModVillager.registerPOIs();
        });
        ModMessages.register();
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

//            EntityRenderers.register(ModEntityTypes.ACTOR.get(), ActorRenderer::new);
            EntityRenderers.register(ModEntityTypes.AZURE.get(), AzureRenderer::new);
        }
    }
}
