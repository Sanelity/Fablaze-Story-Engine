//package lv.sanelite.initium.entity.custom;
//
//import net.minecraft.ChatFormatting;
//import net.minecraft.client.Minecraft;
//import net.minecraft.network.chat.Component;
//import net.minecraftforge.event.level.LevelEvent;
//
//
//import java.util.*;
//
//public class NPCMapper {
//
//    public static Map<String, ActorNPC> actorMap = new HashMap<>();
//
//    public static void addListed(String key, ActorNPC actor){
//        int id = actor.getId();
//        if(actorMap.containsKey(String.valueOf(id))){
//            actorMap.remove(String.valueOf(id));
//        }
//        actorMap.put(key, actor);
//
//    }
//    public static void delListed(String key){
//        if(actorMap.containsKey(key)){
//            actorMap.remove(key);
//        }
//
//    }
//    public static boolean rename(int id, String key){
//        if(actorMap.containsKey(String.valueOf(id))){
//            ActorNPC actor = getActor(String.valueOf(id));
//            delListed(String.valueOf(id));
//            addListed(key, actor);
//            return true;
//        }
//        return false;
//    }
//
//
//    public static ActorNPC getActor(String key){
//        return actorMap.get(key);
//    }
//    public static boolean contains(ActorNPC actor){
//        if(actorMap.containsValue(actor)){
//            return true;
//        }else return false;
//    }
//
//
//    public static int availableActors(){
//        Set<String> ActorList;
//        ActorList = actorMap.keySet();
//        Minecraft.getInstance().player.sendSystemMessage(Component.literal(ActorList.toString()).withStyle(ChatFormatting.BLUE));
//
//        return 1;
//    }
//
//
//
//}
//
//
//
