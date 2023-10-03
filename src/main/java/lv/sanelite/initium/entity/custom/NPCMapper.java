package lv.sanelite.initium.entity.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.level.LevelEvent;


import java.util.*;

public class NPCMapper {
//    public static Map<String, Integer> keyID = new HashMap<>();
//    public static Map<Integer, ActorNPC> entityKeyed = new HashMap<>();

    public static Map<String, ActorNPC> actorMap = new HashMap<>();

    public static void addListed (String key, ActorNPC actor){
        int id = actor.getId();
        if(actorMap.containsKey(String.valueOf(id))){
            actorMap.remove(String.valueOf(id));
        }
        actorMap.put(key, actor);

//        int id = actor.getId();
//        if(keyID.containsValue(id)){
//            keyID.remove(String.valueOf(id));
//        }
//        keyID.put(key, id);
//        entityKeyed.put(id, actor);
    }
    public static void delListed (String key){
        if(actorMap.containsKey(key)){
            actorMap.remove(key);
        }

//        if(keyID.containsKey(key)){
//            entityKeyed.remove(keyID.get(key));
//            keyID.remove(key);
//        }
    }

    public static int clearDead (){
    //TODO - Clearing logic - Possibly Not Actual
        return 1;
    }
    public static ActorNPC getActor(String key){
        return actorMap.get(key);
    }
    public static boolean contains(ActorNPC actor){
        if(actorMap.containsValue(actor)){
            return true;
        }else return false;
    }


    public static int availableActors(){
        Set<String> ActorList;
        ActorList = actorMap.keySet();
        Minecraft.getInstance().player.sendSystemMessage(Component.literal(ActorList.toString()).withStyle(ChatFormatting.BLUE));

//            Set<String> keyCollection;
//            Set<Integer> idCollection;
//            keyCollection = keyID.keySet();
//            idCollection = entityKeyed.keySet();
//            Minecraft.getInstance().player.sendSystemMessage(Component.literal(keyCollection.toString()).withStyle(ChatFormatting.GREEN));
//            Minecraft.getInstance().player.sendSystemMessage(Component.literal(idCollection.toString()).withStyle(ChatFormatting.BLUE));
        return 1;
    }



}



