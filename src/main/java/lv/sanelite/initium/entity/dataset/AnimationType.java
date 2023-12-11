package lv.sanelite.initium.entity.dataset;

import java.util.Map;

public class AnimationType {
    public static Map<String, AnimationType> animationTypeMap = Map.of(
            "sanelite", new AnimationType("sanelite.animation.json"),
            "npc", new AnimationType("npc.animation.json")
    );

    private final String filename;

    private AnimationType(String location){
        this.filename = location;
    }
    public static void newAnimationType(String namespace, String location){
        AnimationType animationType = new AnimationType(location);
        animationTypeMap.put(namespace, animationType);
    }
    public static AnimationType getAnimationType(String namespace){
        if(animationTypeMap.containsKey(namespace)){
            return animationTypeMap.get(namespace);
        }else return animationTypeMap.get("npc");
    }

    public String getFilename() {
        return filename;
    }
}
