package lv.sanelite.initium.entity.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

import java.util.*;

public class NPCMapper {
    public static Map<Integer, ActorNPC> entityKeyed = new HashMap<>(Map.of(0, null));

    public static boolean addListed (int key, ActorNPC actor){
        entityKeyed.put(key, actor);
        return entityKeyed.get(key) != null;
    }
    public static void delListed (int key){
        entityKeyed.remove(key);
    }

    public static boolean availableActors(){
        if(entityKeyed.size() == 0) return false;
        else{
            Set<Integer> collect;
            LocalPlayer players = Minecraft.getInstance().player;
            collect = entityKeyed.keySet();
            players.sendSystemMessage(Component.literal(collect.toString()).withStyle(ChatFormatting.GREEN));
            return true;
        }
    }


}



