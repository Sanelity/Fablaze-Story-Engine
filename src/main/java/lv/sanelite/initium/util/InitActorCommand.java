package lv.sanelite.initium.util;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import lv.sanelite.initium.entity.ModEntityTypes;
import lv.sanelite.initium.entity.custom.ActorNPC;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;

public class InitActorCommand {
    private static final SimpleCommandExceptionType ERROR_DUPLICATE_UUID = new SimpleCommandExceptionType(Component.translatable("commands.summon.failed.uuid"));
    private static final SimpleCommandExceptionType INVALID_POSITION = new SimpleCommandExceptionType(Component.translatable("commands.summon.invalidPosition"));

    // TODO - Clean up summon code
    // TODO HIGH PRIORITY TASK *** Change SentryNPC class initialization to fit in Keying!!!

    public InitActorCommand(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("actor")
            .then(Commands.literal("new")
            .then(Commands.argument("Coordinates", Vec3Argument.vec3())
            .then(Commands.argument("Key",StringArgumentType.word())
            .executes((command) -> summonKeyedActor(command.getSource(), command))))));
    }

    private int summonKeyedActor(CommandSourceStack stack, CommandContext command) throws CommandSyntaxException {
        BlockPos blockpos = new BlockPos(Vec3Argument.getVec3(command, "Coordinates"));
        if (!Level.isInSpawnableBounds(blockpos)) {
            throw INVALID_POSITION.create();
        } else {
            ServerLevel serverlevel = stack.getLevel();

            ActorNPC entity = new ActorNPC(ModEntityTypes.ACTOR.get(), serverlevel);
            entity.setKey(StringArgumentType.getString(command,"Key"));
            entity.moveTo(
                        Vec3Argument.getVec3(command, "Coordinates").x,
                        Vec3Argument.getVec3(command, "Coordinates").y,
                        Vec3Argument.getVec3(command, "Coordinates").z,
                        entity.getYRot(), entity.getXRot());

            if (!net.minecraftforge.event.ForgeEventFactory.doSpecialSpawn(entity, stack.getLevel(), (float) entity.getX(), (float) entity.getY(), (float) entity.getZ(), null, MobSpawnType.COMMAND))
                entity.finalizeSpawn(stack.getLevel(), stack.getLevel().getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.COMMAND,null, null);

            if (!serverlevel.tryAddFreshEntityWithPassengers(entity)) {
                throw ERROR_DUPLICATE_UUID.create();
            } else {
                stack.sendSuccess(Component.translatable("commands.summon.success", entity.getDisplayName()), true);
                return 1;
            }
        }
    }
}