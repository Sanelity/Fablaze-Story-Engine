package lv.sanelite.initium.entity.dataset;

import lv.sanelite.initium.entity.actor.AzureNPC;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import java.util.*;

public class NPCMapper {
    public static Map<String, AzureNPC> actorMap = new HashMap<>();

    public static void addActorToList(String key, AzureNPC actor){
        int id = actor.getId();
        if(actorMap.containsKey(String.valueOf(id))){
            actorMap.remove(String.valueOf(id));
        }
        actorMap.put(key, actor);

    }
    public static void deleteActorFromList(String key){
        actorMap.remove(key);

    }
    public static void renameActorInList(int id, String key){
        if(actorMap.containsKey(String.valueOf(id))){
            AzureNPC actor = getActorByName(String.valueOf(id));
            deleteActorFromList(String.valueOf(id));
            addActorToList(key, actor);
        }
    }


    public static AzureNPC getActorByName(String key){
        return actorMap.get(key);
    }
    public static boolean contains(AzureNPC actor){
        return actorMap.containsValue(actor);
    }


    public static String getActors(){
        Set<String> ActorList;
        ActorList = actorMap.keySet();
        return ActorList.toString();
    }



}



