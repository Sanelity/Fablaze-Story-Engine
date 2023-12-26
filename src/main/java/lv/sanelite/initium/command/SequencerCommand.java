package lv.sanelite.initium.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lv.sanelite.initium.external.sequencer.Executor;
import lv.sanelite.initium.external.sequencer.ScriptReader;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.LevelResource;

import java.io.File;
import java.nio.file.Path;

public class SequencerCommand {
    public SequencerCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("sequence")
                .then(Commands.literal("run").executes(command -> {
                    try {
                        return initSequence(command);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }))
                .then(Commands.literal("path").executes(this::testCommand))
                );
    }

    private int initSequence(CommandContext<CommandSourceStack> command) throws CommandSyntaxException, IllegalAccessException {
        Executor.runScript(command.getSource().getPlayer(), "scene");
        return 1;
    }
    private int testCommand(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        Minecraft.getInstance().player.sendSystemMessage(Component.literal(ScriptReader.readScript("act1") + " - file"));

        return 1;
    }

}
