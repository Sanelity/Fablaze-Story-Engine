package lv.sanelite.initium.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lv.sanelite.initium.entity.actor.AzureNPC;
import lv.sanelite.initium.entity.dataset.NPCMapper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;

public class ActorUtilCommand {
    public ActorUtilCommand(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("actor")                                   // - /actor ...

            .then(Commands.literal("move")                                              // - /actor move
            .then(Commands.argument("Coordinates", Vec3Argument.vec3())                 // - /actor move "Coordinates" ...
            .then(Commands.argument("Name", StringArgumentType.word())                  // - /actor move "Coordinates" "Name" ...
            .then(Commands.argument("Speed", DoubleArgumentType.doubleArg())            // - /actor move "Coordinates" "Name" "Speed" ...
            .then(Commands.argument("Bypass", BoolArgumentType.bool())                  // - /actor move "Coordinates" "Name" "Speed" "Bypass"...
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

            .then(Commands.literal("eliminate")                                         // - /actor eliminate ...
            .then(Commands.argument("Name", StringArgumentType.word())                  // - /actor eliminate "Name" ...
            .executes(this::eliminate))));                                                       //Execute
    }
    public int sendMessage(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        AzureNPC entity = getEntity(StringArgumentType.getString(command, "Name"));
        entity.talk(StringArgumentType.getString(command, "Message"));
        return 1;
    }

    public int changeRes(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        AzureNPC entity = getEntity(StringArgumentType.getString(command, "Name"));
        entity.setCharacter(StringArgumentType.getString(command, "Resource"));
        return 1;
    }

    public int listing(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        return NPCMapper.availableActors();
    }
    public int eliminate(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        Entity killable = getEntity(StringArgumentType.getString(command, "Name"));
        killable.kill();
        NPCMapper.delListed(StringArgumentType.getString(command, "Name"));
        return 1;
    }
    public int resendTarged(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        AzureNPC entity = getEntity(StringArgumentType.getString(command,"Name"));
        entity.newTarget(
                Vec3Argument.getVec3(command,"Coordinates"),
                DoubleArgumentType.getDouble(command,"Speed"),
                BoolArgumentType.getBool(command, "Bypass")
        );

        if(Minecraft.getInstance().player != null){
            Minecraft.getInstance().player.sendSystemMessage(
                    Component.literal("[Debug] Target: "
                                    + Math.round(Vec3Argument.getVec3(command,"Coordinates").x)
                                    + ", "
                                    + Math.round(Vec3Argument.getVec3(command,"Coordinates").y)
                                    + ", "
                                    + Math.round(Vec3Argument.getVec3(command,"Coordinates").z)
                                    + " | Speed: "
                                    + Math.round(DoubleArgumentType.getDouble(command,"Speed"))
                                    + " | "
                                    + StringArgumentType.getString(command,"Name"))
                            .withStyle(ChatFormatting.RED));
        }

        return 1;
    }

    public int setVisibility(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        AzureNPC entity = getEntity(StringArgumentType.getString(command,"Name"));
        entity.setCustomNameVisible(BoolArgumentType.getBool(command, "Boolean"));
        return 1;
    }
    private AzureNPC getEntity(String key){
        return NPCMapper.getActor(key);
    }
}
