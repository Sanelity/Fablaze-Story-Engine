package lv.sanelite.initium.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lv.sanelite.initium.core.ActorFunction;
import lv.sanelite.initium.entity.actor.AzureNPC;
import lv.sanelite.initium.entity.dataset.NPCMapper;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.lang.invoke.WrongMethodTypeException;

import static lv.sanelite.initium.core.ActorFunction.getActor;

public class ActorUtilCommand {
    public ActorUtilCommand(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("actor")                                   // - /actor ...

            .then(Commands.literal("move")                                              // - /actor move
            .then(Commands.argument("Name", StringArgumentType.word())                  // - /actor move "Name" ...
            .then(Commands.argument("Coordinates", Vec3Argument.vec3())                 // - /actor move "Name" "Coordinates" ...
            .then(Commands.argument("Speed", DoubleArgumentType.doubleArg())            // - /actor move "Name" "Coordinates" "Speed" ...
            .then(Commands.argument("Bypass", BoolArgumentType.bool())                  // - /actor move "Name" "Coordinates" "Speed" "Bypass"...
            .executes(this::resendTarged))))))                                                   //Execute

            .then(Commands.literal("list").executes(this::listing))                     // - /actor list = Execute

            .then(Commands.literal("say")
            .then(Commands.argument("Name", StringArgumentType.string())
            .then(Commands.argument("Message", StringArgumentType.string())
            .executes(this::sendMessage))))

            .then(Commands.literal("change")
            .then(Commands.argument("Name", StringArgumentType.word())
            .then(Commands.argument("Resource", StringArgumentType.word())
            .executes(this::changeRes))))

            .then(Commands.literal("name")                                              // - /actor name ...
            .then(Commands.argument("Name",StringArgumentType.word())                   // - /actor name "Name" ...
            .then(Commands.argument("Boolean", BoolArgumentType.bool())                 // - /actor name "Name" "Boolean"
            .executes(this::setVisibility))))

            .then(Commands.literal("look")
            .then(Commands.argument("Name", StringArgumentType.word())
            .then(Commands.argument("Target", StringArgumentType.word())
            .executes(this::setLook))))

            .then(Commands.literal("eliminate")                                         // - /actor eliminate ...
            .then(Commands.argument("Name", StringArgumentType.word())                  // - /actor eliminate "Name" ...
            .executes(this::eliminate))));                                                       //Execute
    }
    public int sendMessage(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        AzureNPC entity = getActor(StringArgumentType.getString(command, "Name"));
        entity.talk(StringArgumentType.getString(command, "Message"));
        return 1;
    }
    public int setLook(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        AzureNPC entity = getActor(StringArgumentType.getString(command, "Name"));
        Entity target = getActor(StringArgumentType.getString(command, "Target"));

        ActorFunction.setLookTarget(target,entity);
        return 1;
    }

    public int changeRes(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        AzureNPC entity = getActor(StringArgumentType.getString(command, "Name"));
        entity.setCharacter(StringArgumentType.getString(command, "Resource"));
        return 1;
    }

    public int listing(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        Player player = command.getSource().getPlayer();
        if (player != null){
            player.sendSystemMessage(Component.literal(ActorFunction.getActorList()).withStyle(ChatFormatting.BLUE));
        }
        return 1;
    }
    public int eliminate(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        Entity killable = getActor(StringArgumentType.getString(command, "Name"));
        killable.discard();
        NPCMapper.deleteActorFromList(StringArgumentType.getString(command, "Name"));
        return 1;
    }
    public int resendTarged(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        AzureNPC entity = getActor(StringArgumentType.getString(command,"Name"));

        ActorFunction.setMoveTarget(
                Vec3Argument.getVec3(command,"Coordinates"),
                DoubleArgumentType.getDouble(command, "Speed"),
                BoolArgumentType.getBool(command, "Bypass"),
                entity);
        return 1;
    }

    public int setVisibility(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        AzureNPC entity = getActor(StringArgumentType.getString(command,"Name"));
        entity.setCustomNameVisible(BoolArgumentType.getBool(command, "Boolean"));
        return 1;
    }
}



