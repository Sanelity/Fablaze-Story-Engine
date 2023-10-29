package lv.sanelite.initium.entity.dataset;

import lv.sanelite.initium.entity.actor.AzureNPC;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import java.util.*;

public class NPCMapper {
    public static Map<String, AzureNPC> actorMap = new HashMap<>();

    public static void addListed(String key, AzureNPC actor){
        int id = actor.getId();
        if(actorMap.containsKey(String.valueOf(id))){
            actorMap.remove(String.valueOf(id));
        }
        actorMap.put(key, actor);

    }
    public static void delListed(String key){
        if(actorMap.containsKey(key)){
            actorMap.remove(key);
        }

    }
    public static boolean rename(int id, String key){
        if(actorMap.containsKey(String.valueOf(id))){
            AzureNPC actor = getActor(String.valueOf(id));
            delListed(String.valueOf(id));
            addListed(key, actor);
            return true;
        }
        return false;
    }


    public static AzureNPC getActor(String key){
        return actorMap.get(key);
    }
    public static boolean contains(AzureNPC actor){
        return actorMap.containsValue(actor);
    }


    public static int availableActors(){
        Set<String> ActorList;
        ActorList = actorMap.keySet();
        Minecraft.getInstance().player.sendSystemMessage(Component.literal(ActorList.toString()).withStyle(ChatFormatting.BLUE));

        return 1;
    }



}



