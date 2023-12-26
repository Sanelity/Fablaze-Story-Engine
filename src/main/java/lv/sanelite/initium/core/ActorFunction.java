package lv.sanelite.initium.core;

import lv.sanelite.initium.entity.ModEntityTypes;
import lv.sanelite.initium.entity.actor.AzureNPC;
import lv.sanelite.initium.entity.dataset.*;
import lv.sanelite.initium.entity.dataset.Character;
import lv.sanelite.initium.util.RGB;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.Map;

import static lv.sanelite.initium.entity.dataset.PhrasePool.addPhrase;
import static lv.sanelite.initium.entity.dataset.PhrasePool.newPhrasePool;

/**<h4>Functional base for controlling Actor Entities in any case<h4/>
 * Usage keyword [actor] -
 * Main usage for this class is:
 * <ul>
 * <li>Creating new materials for new Actors<li/>
 * Creating new Actors, Changing it parts on-fly
 * <li>Directing and defining the Actor's actions<li/>
 * </ul>
 * The list of commands may be added at any time
 */
public class ActorFunction {
                                /// --- --- --- Resource section --- --- --- ///
    ///Character section
    public static void createCharacter(String namespace, int red, int green, int blue,
                                       String dialog_pool, String hurt_pool, String modelType, String animationType)
    {
        Character.newCharacter(namespace, RGB.color(red, green, blue),
        PhrasePoolSet.loadPool(dialog_pool, hurt_pool),
        ModelType.getModelType(modelType), AnimationType.getAnimationType(animationType));
    }
    ///TODO Load new Resources
    public static void loadModel(String resource_location, boolean glowing){};
    public static void loadAnimation(String resource_location){};

    ///TODO Load new Phrases
    public static void createPool(String namespace, SequenceType sequenceType){
        Map<Integer,String> pool = Map.of();
        newPhrasePool(namespace, pool,sequenceType);
    };

    public static void createPhrase(String pool, String phrase_0 ){
        addPhrase(pool, phrase_0);
    };
    public static void createPhrase(String pool, String phrase_0, String phrase_1){
        addPhrase(pool, phrase_0);addPhrase(pool, phrase_1);
    };
    public static void createPhrase(String pool, String phrase_0, String phrase_1, String phrase_2){
        addPhrase(pool, phrase_0);addPhrase(pool, phrase_1);addPhrase(pool, phrase_2);
    };
    public static void createPhrase(String pool, String phrase_0, String phrase_1, String phrase_2, String phrase_3){
        addPhrase(pool, phrase_0);addPhrase(pool, phrase_1);addPhrase(pool, phrase_2);addPhrase(pool, phrase_3);
    };


                            ///--- --- --- In-game Actor functions --- --- ---///
    ///In-game Actor creation
    public static void createActor(String char_name, String char_key, ServerLevel serverLevel, Vec3 coords){
        CompoundTag tag = new CompoundTag();

        tag.putString("Character", char_name);
        tag.putString("Key", char_key);

        EntityType<AzureNPC> entityType = ModEntityTypes.AZURE.get();
        entityType.spawn(serverLevel,tag,null,null, new BlockPos(coords.x(),coords.y(),coords.z()), MobSpawnType.COMMAND,false,false);

    }
    public static void createActor(String char_name, String char_key, ServerLevel serverLevel, Player player){
        createActor(char_name, char_key, serverLevel, new Vec3(player.getX(),player.getY(),player.getZ()));
    }
    public static void createActorByTag(Tag compound, ServerLevel serverLevel){
        ///TODO
    }

    ///In-game Actor interaction
    public static void setLookTarget(Entity target, AzureNPC actor){
        actor.setLookTarget(target);
    }
    public static void setLookPos(Vec3 pos, AzureNPC actor){
        actor.setLookAt(pos);
    }

    public static void say(String actor, String message){
        getActor(actor).talk(message);
    }

    ///In-game Actor relocation
    public static void teleportActor(double x, double y, double z, AzureNPC actor){
        actor.moveTo(x,y,z);
    }
    public static void teleportActor(Vec3 pos, AzureNPC actor){
        double x,y,z;
        x = pos.x(); y = pos.y(); z = pos.z();
        teleportActor(x,y,z,actor);
    }

    public static void setMoveTarget(Vec3 pos, double speed, boolean bypass, AzureNPC actor){
        actor.newTarget(pos,speed,bypass);
    }
    public static void setMoveTarget(double x, double y, double z, double speed, AzureNPC actor){
        setMoveTarget(new Vec3(x,y,z), speed,false, actor);
    }
    public static void setMoveTarget(double x, double y, double z, double speed, boolean bypass, AzureNPC actor){
        setMoveTarget(new Vec3(x,y,z), speed, bypass, actor);
    }




    ///NPCMAPPER Functions
    public static AzureNPC getActor(String name){
        return NPCMapper.getActorByName(name);
    }
    public static void removeActor(String name){
        NPCMapper.deleteActorFromList(name);
    }
    public static String getActorList(){
        return NPCMapper.getActors();
    }

}
