package lv.sanelite.initium.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import lv.sanelite.initium.core.ActorFunction;
import lv.sanelite.initium.entity.actor.AzureNPC;
import lv.sanelite.initium.entity.dataset.NPCMapper;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.EntitySummonArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

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

            .then(Commands.literal("look-actor")
            .then(Commands.argument("Name", StringArgumentType.word())
            .then(Commands.argument("Target", StringArgumentType.word())
            .executes(this::setLook))))

            .then(Commands.literal("look-position")
            .then(Commands.argument("Name", StringArgumentType.word())
            .then(Commands.argument("Coordinates", Vec3Argument.vec3())
            .executes(this::setLookPos))))

            .then(Commands.literal("look-entity")
            .then(Commands.argument("Name", StringArgumentType.word())
            .then(Commands.argument("Target", EntityArgument.entity())
            .executes(this::setLookEntity))))

            .then(Commands.literal("look-type")
            .then(Commands.argument("Name", StringArgumentType.word())
            .then(Commands.argument("Target", EntitySummonArgument.id()).suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
            .executes(this::setLookType))))

            .then(Commands.literal("animation")
            .then(Commands.argument("Name", StringArgumentType.word())
            .then(Commands.argument("Action", StringArgumentType.string())
            .then(Commands.argument("Emote", StringArgumentType.string())
            .then(Commands.argument("Look", StringArgumentType.string())
            .then(Commands.argument("Additional", StringArgumentType.string())
            .executes(this::setAnimation)))))))

            .then(Commands.literal("eliminate")                                         // - /actor eliminate ...
            .then(Commands.argument("Name", StringArgumentType.word())                  // - /actor eliminate "Name" ...
            .executes(this::eliminate))));                                                       //Execute
    }
    public int setAnimation(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        AzureNPC entity = ActorFunction.getActor(StringArgumentType.getString(command, "Name"));
        String act,emo,look,add;
        if(!StringArgumentType.getString(command, "Action").equals("0")){
            act = StringArgumentType.getString(command, "Action");
        }else act = null;
        if(!StringArgumentType.getString(command, "Emote").equals("0")){
            emo = StringArgumentType.getString(command, "Emote");
        }else emo = null;
        if(!StringArgumentType.getString(command, "Look").equals("0")){
            look = StringArgumentType.getString(command, "Look");
        }else look = null;
        if(!StringArgumentType.getString(command, "Additional").equals("0")){
            add = StringArgumentType.getString(command, "Additional");
        }else add = null;


        ActorFunction.setAnimation(entity, act, emo, look, add);
        return 1;
    }

    public int sendMessage(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        AzureNPC entity = ActorFunction.getActor(StringArgumentType.getString(command, "Name"));
        entity.talk(StringArgumentType.getString(command, "Message"));
        return 1;
    }

    public int setLookEntity(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        AzureNPC entity = ActorFunction.getActor(StringArgumentType.getString(command, "Name"));
        Entity target = EntityArgument.getEntity(command, "Target");
        ActorFunction.setLook(entity, target);


        return 1;
    }
    public int setLookPos(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        AzureNPC entity = ActorFunction.getActor(StringArgumentType.getString(command, "Name"));
        Vec3 vector = Vec3Argument.getVec3(command, "Coordinates");
        ActorFunction.setLookPos(entity, vector);


        return 1;
    }

    public int setLook(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        AzureNPC entity = ActorFunction.getActor(StringArgumentType.getString(command, "Name"));
        AzureNPC target = ActorFunction.getActor(StringArgumentType.getString(command, "Target"));

        ActorFunction.setLook(entity, target);
        return 1;
    }

    public int setLookType(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        AzureNPC entity = ActorFunction.getActor(StringArgumentType.getString(command, "Name"));
        ActorFunction.setLookType(entity, EntitySummonArgument.getSummonableEntity(command, "Target"));

//        CompoundTag tag = new CompoundTag();
//        tag.putString("id",EntitySummonArgument.getSummonableEntity(command, "Target").toString());
//        Entity target = EntityType.loadEntityRecursive(tag,command.getSource().getLevel(), summoned -> {
//            summoned.discard();
//            return summoned;
//        });
//
//        if(target.getType().getCategory() != MobCategory.MISC){
//            ActorFunction.setLookType(entity, target);
//        }else throw ERROR_NOT_LIVING.create();

        return 1;
    }

    public int changeRes(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        AzureNPC entity = ActorFunction.getActor(StringArgumentType.getString(command, "Name"));
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
        Entity killable = ActorFunction.getActor(StringArgumentType.getString(command, "Name"));
        killable.discard();
        NPCMapper.deleteActorFromList(StringArgumentType.getString(command, "Name"));
        return 1;
    }
    public int resendTarged(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        AzureNPC entity = ActorFunction.getActor(StringArgumentType.getString(command,"Name"));

        ActorFunction.setMoveTarget(
                Vec3Argument.getVec3(command,"Coordinates"),
                DoubleArgumentType.getDouble(command, "Speed"),
                BoolArgumentType.getBool(command, "Bypass"),
                entity);
        return 1;
    }

    public int setVisibility(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        AzureNPC entity = ActorFunction.getActor(StringArgumentType.getString(command,"Name"));
        entity.setCustomNameVisible(BoolArgumentType.getBool(command, "Boolean"));
        return 1;
    }
}



