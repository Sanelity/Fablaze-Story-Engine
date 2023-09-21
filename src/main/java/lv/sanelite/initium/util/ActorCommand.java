package lv.sanelite.initium.util;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lv.sanelite.initium.entity.goal.MoveToGoal;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.network.chat.Component;

public class ActorCommand {
    public ActorCommand(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("actor")
            .then(Commands.argument("Coordinates", Vec3Argument.vec3())
            .then(Commands.argument("Name", StringArgumentType.word())
            .executes(this::resendTarged))));
    }

    public int resendTarged(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        MoveToGoal.changeDirection(
                Vec3Argument.getVec3(command,"Coordinates"),
                StringArgumentType.getString(command,"Name"));

        if(Minecraft.getInstance().player != null){
            Minecraft.getInstance().player.sendSystemMessage(
                    Component.literal("[Debug] Target: "
                                    + Math.round(Vec3Argument.getVec3(command,"Coordinates").x)
                                    + ", "
                                    + Math.round(Vec3Argument.getVec3(command,"Coordinates").y)
                                    + ", "
                                    + Math.round(Vec3Argument.getVec3(command,"Coordinates").z)
                                    + " | "
                                    + StringArgumentType.getString(command,"Name"))
                            .withStyle(ChatFormatting.RED));
        }

        return 1;
    }

}
