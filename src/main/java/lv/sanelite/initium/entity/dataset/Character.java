package lv.sanelite.initium.entity.dataset;

import lv.sanelite.initium.util.RGB;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

//TODO: Model Scale, SoundSet, ParticleSet


public class Character {
    private static Map<Integer, Character> characterMap = Map.of(
            0, new Character( "maxie", RGB.color(0,255,255),
                    new PhrasePoolSet(
                            PhrasePool.getPoolByName("angeloid"),
                            PhrasePool.getPoolByName("hurt")),
                    ModelType.getModelType("angeloid"),
                    AnimationType.getAnimationType("sanelite")),

            1, new Character("sentry", RGB.color(255,0,162),
                    new PhrasePoolSet(
                            PhrasePool.getPoolByName("angeloid"),
                            PhrasePool.getPoolByName("hurt")),
                    ModelType.getModelType("sentry"),
                    AnimationType.getAnimationType("sanelite")),

            2, new Character( "muskul", RGB.color(221, 89, 89),
                    new PhrasePoolSet(
                            PhrasePool.getPoolByName("default"),
                            PhrasePool.getPoolByName("hurt")),
                    ModelType.getModelType("alex"),
                    AnimationType.getAnimationType("npc")),

            3, new Character( "blonde", RGB.color(255,255,0),
                    new PhrasePoolSet(
                            PhrasePool.getPoolByName("default"),
                            PhrasePool.getPoolByName("hurt")),
                    ModelType.getModelType("alex"),
                    AnimationType.getAnimationType("npc")),
            4, new Character( "lansundash", RGB.color(233,141,88),
                    new PhrasePoolSet(
                            PhrasePool.getPoolByName("default"),
                            PhrasePool.getPoolByName("hurt")),
                    ModelType.getModelType("sentry"),
                    AnimationType.getAnimationType("sanelite")),
            5, new Character( "waith", RGB.color(250,248,252),
                    new PhrasePoolSet(
                            PhrasePool.getPoolByName("default"),
                            PhrasePool.getPoolByName("hurt")),
                    ModelType.getModelType("sentry"),
                    AnimationType.getAnimationType("sanelite")),
            6, new Character( "mikka", RGB.color(112,26,200),
                    new PhrasePoolSet(
                            PhrasePool.getPoolByName("default"),
                            PhrasePool.getPoolByName("hurt")),
                    ModelType.getModelType("sentry"),
                    AnimationType.getAnimationType("sanelite"))
    );

    private final String name;
    private final int color;
    private final PhrasePoolSet phrasePoolset;
    private final ModelType model;
    private final AnimationType animation;

    private Character(String name_key, int textColor, PhrasePoolSet poolSet, ModelType modelType, AnimationType animationSet){
        this.name = name_key;
        this.color = textColor;
        this.phrasePoolset = poolSet;
        this.model = modelType;
        this.animation = animationSet;
    }
    public static void newCharacter(String name_key, int textColor, PhrasePoolSet poolSet, ModelType modelType, AnimationType animationSet){
        Character character = new Character(name_key, textColor, poolSet, modelType, animationSet);
        Character.characterMap.put(characterMap.size() + 1, character);
    }


    public int getColor() {
        return color;
    }
    public String getAnimation() {
        return animation.getFilename();
    }
    public String getModel() {
        return model.getFilename();
    }
    public String getName() {
        return name;
    }
    public boolean isGlowing(){
        return model.isGlowing();
    }
    public String getPhraseTalk(){
        return this.phrasePoolset.getTalk();
    }
    public String getPhraseHurt(){
        return this.phrasePoolset.getHurt();
    }


    public static Character getCharacter(String name){
        for(int i = 0; i < characterMap.size(); i++){
            if(name.equals(characterMap.get(i).getName())){
                return characterMap.get(i);
            }
        }
        return characterMap.get(0);
    }

}
