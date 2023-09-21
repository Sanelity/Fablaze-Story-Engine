package lv.sanelite.initium.util;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntitySummonArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;

public class InitActorCommand {
    private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType(Component.translatable("commands.summon.failed"));
    private static final SimpleCommandExceptionType ERROR_DUPLICATE_UUID = new SimpleCommandExceptionType(Component.translatable("commands.summon.failed.uuid"));
    private static final SimpleCommandExceptionType INVALID_POSITION = new SimpleCommandExceptionType(Component.translatable("commands.summon.invalidPosition"));

    // TODO - Clean up summon code
    // TODO HIGH PRIORITY TASK *** Change SentryNPC class initialization to fit in Keying!!!

    public InitActorCommand(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("init")
            .then(Commands.argument("Coordinates", Vec3Argument.vec3())
            .then(Commands.argument("Key",StringArgumentType.word())
            .then(Commands.argument("Entity", EntitySummonArgument.id())
                    .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
            .executes((command) -> summonKeyedActor(command.getSource(), EntitySummonArgument.getSummonableEntity(command, "Entity"),command))))));
    }

    private int summonKeyedActor(CommandSourceStack stack, ResourceLocation resource, CommandContext command) throws CommandSyntaxException {
        BlockPos blockpos = new BlockPos(Vec3Argument.getVec3(command, "Coordinates"));
        if (!Level.isInSpawnableBounds(blockpos)) {
            throw INVALID_POSITION.create();
        } else {
            CompoundTag compoundtag = new CompoundTag();
            compoundtag.putString("id", resource.toString());
            ServerLevel serverlevel = stack.getLevel();
            Entity entity = EntityType.loadEntityRecursive(compoundtag, serverlevel, (mob) -> {
                mob.moveTo(
                        Vec3Argument.getVec3(command, "Coordinates").x,
                        Vec3Argument.getVec3(command, "Coordinates").y,
                        Vec3Argument.getVec3(command, "Coordinates").z,
                        mob.getYRot(), mob.getXRot());
                return mob;
            });
            if (entity == null) {
                throw ERROR_FAILED.create();
            } else {
                if (entity instanceof Mob) {
                    if (!net.minecraftforge.event.ForgeEventFactory.doSpecialSpawn((Mob)entity, stack.getLevel(), (float)entity.getX(), (float)entity.getY(), (float)entity.getZ(), null, MobSpawnType.COMMAND))
                        ((Mob)entity).finalizeSpawn(stack.getLevel(), stack.getLevel().getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.COMMAND, (SpawnGroupData)null, (CompoundTag)null);
                }

                if (!serverlevel.tryAddFreshEntityWithPassengers(entity)) {
                    throw ERROR_DUPLICATE_UUID.create();
                } else {
                    stack.sendSuccess(Component.translatable("commands.summon.success", entity.getDisplayName()), true);
                    return 1;
                }
            }
        }
    }




}