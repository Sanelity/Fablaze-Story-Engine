package lv.sanelite.initium.event;

import lv.sanelite.initium.Initium;
import lv.sanelite.initium.networking.ModMessages;
import lv.sanelite.initium.networking.packet.ExampleC2SPacket;
import lv.sanelite.initium.util.KeyBinding;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.TitleCommand;
import net.minecraft.world.level.block.CommandBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Initium.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event){
            if(KeyBinding.CHEAT_KEY.consumeClick()){
                LocalPlayer user = Minecraft.getInstance().player;
                user.sendSystemMessage(Component.literal("You are Cheater!")
                    .withStyle(ChatFormatting.RED));
                user.displayClientMessage(Component.literal("Fuck"),true);
                ModMessages.sendToServer(new ExampleC2SPacket());
            }
        }
    }
    @Mod.EventBusSubscriber(modid = Initium.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents{
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event){
            event.register(KeyBinding.CHEAT_KEY);
        }
    }
}
