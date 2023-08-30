package lv.sanelite.initium.item.advanced;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class D6DiceItem extends Item {
    public D6DiceItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            //Some output
            D6RollMSG(player);
            //Cooldown
            player.getCooldowns().addCooldown(this,5);
        }
        return super.use(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(Screen.hasShiftDown()){
            components.add(Component.literal("Right click to Roll the Dice and get Number!").withStyle(ChatFormatting.GOLD));
        }else{
            components.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.BLUE));
        }

        super.appendHoverText(stack, level, components, flag);
    }

    private void D6RollMSG(Player player){
        player.sendSystemMessage(Component.literal("Your D6 rolled "+ getD6Roll()));
    }
    private int getD6Roll(){
        return RandomSource.createNewThreadLocalInstance().nextInt(6)+1;
    }
}
