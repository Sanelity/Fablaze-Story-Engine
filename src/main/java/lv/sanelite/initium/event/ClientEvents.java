package lv.sanelite.initium.event;

import lv.sanelite.initium.Initium;
import lv.sanelite.initium.gui.BlackScreenHUD;
import lv.sanelite.initium.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Initium.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        public static int tick = 0;
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event){
            if(KeyBinding.DEBUG_KEY.consumeClick()){
                BlackScreenHUD.DEBUG = !BlackScreenHUD.DEBUG;
            }
        }
        @SubscribeEvent
        public static void clientTick(TickEvent.ClientTickEvent e){
            if(!Minecraft.getInstance().isPaused()){
                tick++;
            }
        }
    }
    @Mod.EventBusSubscriber(modid = Initium.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents{

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event){
            event.register(KeyBinding.DEBUG_KEY);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event){
            event.registerAboveAll("blackscreen", BlackScreenHUD.BLACKSCREEN_HUD);
        }

    }
}
