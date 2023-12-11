package lv.sanelite.initium.item.advanced;

import lv.sanelite.initium.screen.ActorAddToolMenu;
import lv.sanelite.initium.screen.ActorAddToolScreen;
import lv.sanelite.initium.screen.SaneliteScreenMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AddActorToolItem extends Item {
    public AddActorToolItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player usePlayer, InteractionHand hand) {

        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            NetworkHooks.openScreen((ServerPlayer) usePlayer, new SimpleMenuProvider(
                    (containerId, playerInventory, player) -> new ActorAddToolMenu(containerId,playerInventory),
                    Component.translatable("menu.initium.actortool")
            ));
            Minecraft.getInstance().player.getCooldowns().addCooldown(this,5);
        }
        return super.use(level, usePlayer, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(Screen.hasShiftDown()){
            components.add(Component.literal("Right click to Spawn new actor!").withStyle(ChatFormatting.GOLD));
        }else{
            components.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.BLUE));
        }

        super.appendHoverText(stack, level, components, flag);
    }
}
