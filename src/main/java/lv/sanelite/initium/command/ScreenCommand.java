package lv.sanelite.initium.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lv.sanelite.initium.gui.BlackScreenHUD;
import lv.sanelite.initium.screen.SaneliteScreenMenu;
import lv.sanelite.initium.util.RGB;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class ScreenCommand {
    public ScreenCommand(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("screen")
                .then(Commands.literal("open").executes(this::openGui))

                .then(Commands.literal("black")
                .then(Commands.argument("Transition", IntegerArgumentType.integer())
                .then(Commands.argument("Polarity", BoolArgumentType.bool())
                        .then(Commands.argument("Red", IntegerArgumentType.integer(0,255))
                        .then(Commands.argument("Green", IntegerArgumentType.integer(0,255))
                        .then(Commands.argument("Blue", IntegerArgumentType.integer(0,255))
                .executes(this::black))))))));
    }

    public int openGui(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        ServerPlayer serverPlayer = command.getSource().getPlayer();

        if(serverPlayer != null){
            NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider(
                    (containerId, playerInventory, player) -> new SaneliteScreenMenu(containerId,playerInventory),
                    Component.translatable("menu.initium.screen")
            ));
        }
        return 1;
    }
    public int black(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        BlackScreenHUD.use(
                IntegerArgumentType.getInteger(command,"Transition"),
                0,
                BoolArgumentType.getBool(command, "Polarity"),
                new RGB(
                        IntegerArgumentType.getInteger(command,"Red"),
                        IntegerArgumentType.getInteger(command,"Green"),
                        IntegerArgumentType.getInteger(command,"Blue")
                ));
        return 1;
    }

}
