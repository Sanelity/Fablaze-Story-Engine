package lv.sanelite.initium.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import lv.sanelite.initium.core.ActorFunction;
import lv.sanelite.initium.entity.ModEntityTypes;
import lv.sanelite.initium.entity.actor.AzureNPC;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ActorNewCommand {
    // TODO - Clean up summon code
    // TODO HIGH PRIORITY TASK *** Change SentryNPC class initialization to fit in Keying!!!

    public ActorNewCommand(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("actor")
            .then(Commands.literal("new")
            .then(Commands.argument("Coordinates", Vec3Argument.vec3())
            .then(Commands.argument("Key",StringArgumentType.word())
            .then(Commands.argument("Character",StringArgumentType.word())
            .executes((command) -> summonKeyedActor(command.getSource(), command)))))));
    }

    private int summonKeyedActor(CommandSourceStack stack, CommandContext command) throws CommandSyntaxException {
        Player player = stack.getPlayer();
        ActorFunction.createActor(
                StringArgumentType.getString(command, "Character"),
                StringArgumentType.getString(command, "Key"),
                stack.getLevel(),
                Vec3Argument.getVec3(command, "Coordinates"));

        if(player != null){
            player.sendSystemMessage(Component.translatable("commands.summon.success", "Azure Actor<" + StringArgumentType.getString(command, "Key") + ">"));
        }
        return 1;
    }
}