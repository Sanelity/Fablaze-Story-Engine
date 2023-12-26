package lv.sanelite.initium.gui;


import com.mojang.blaze3d.systems.RenderSystem;
import lv.sanelite.initium.Initium;
import lv.sanelite.initium.event.ClientEvents;
import lv.sanelite.initium.util.RGB;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

@OnlyIn(Dist.CLIENT)
public class BlackScreenHUD{
    private static final ResourceLocation BLACK_SCREEN = new ResourceLocation(Initium.MOD_ID, "textures/gui/blackscreen.png");
    public static boolean ACTIVE = true;
    public static boolean DEBUG = false;

    private static RGB screenColor = new RGB(0,0,0);

    private static float alpha = 0f;
    private static int markTime = 0;
    private static int fadeTime = 0;
    private static int offset = 0;
    private static boolean fadeToBlack = false;


    public static IGuiOverlay BLACKSCREEN_HUD = (gui, poseStack, partialTick, width, height) -> {


        if((ClientEvents.ClientForgeEvents.tick - markTime + offset) <= (fadeTime + offset) && fadeToBlack){
            alpha = (float) (ClientEvents.ClientForgeEvents.tick - markTime + offset) / (fadeTime + offset);


        }else if ((ClientEvents.ClientForgeEvents.tick - markTime + offset) <= (fadeTime + offset) && !fadeToBlack) {
            alpha = (float) (markTime + offset + fadeTime - ClientEvents.ClientForgeEvents.tick) / (fadeTime + offset);

        }

        if(ACTIVE){
            RenderSystem.enableBlend(); RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(
                    screenColor.getFloatRed(),
                    screenColor.getFloatGreen(),
                    screenColor.getFloatBlue(),
                    alpha);
            RenderSystem.setShaderTexture(0,BLACK_SCREEN);
            GuiComponent.blit(poseStack,0,0,width,height,width,height,0,0);
            RenderSystem.setShaderColor(1F,1F,1F,1F);
        }

        if(DEBUG){
            GuiComponent.drawString(poseStack, Minecraft.getInstance().font, String.valueOf(ClientEvents.ClientForgeEvents.tick),
                10,10,RGB.color(255,0,0));

            GuiComponent.drawString(poseStack, Minecraft.getInstance().font, String.valueOf(alpha),
                    10,0,RGB.color(0,0,255));
        }

    };

    public static void use(int transitionTime, int offset, boolean polarity, RGB color){
        markTime = ClientEvents.ClientForgeEvents.tick;
        fadeTime = transitionTime;
        fadeToBlack = polarity;
        screenColor = color;

        int calc = markTime + fadeTime;
        Player player = Minecraft.getInstance().player;
        player.sendSystemMessage(Component.literal("[Polarity: " + polarity + "] - Fade time ticks - " + transitionTime).withStyle(ChatFormatting.RED));
        player.sendSystemMessage(Component.literal("Marked at " + markTime + " - fade to " + calc).withStyle(ChatFormatting.RED));

    }

}
