package lv.sanelite.initium.entity.dataset;

import lv.sanelite.initium.util.RGB;

import java.util.Arrays;
import java.util.Comparator;

//TODO: Model Scale, SoundSet, ParticleSet


public enum Character {
    SENTRY(0,"sentry", RGB.color(255,0,162), ModelType.SENTRY, AnimationType.SANELITE),
    MAXIE(1, "maxie", RGB.color(0,255,255), ModelType.ANGELOID, AnimationType.SANELITE),
    MUSKUL(2, "muskul", RGB.color(221, 89, 89), ModelType.ALEX, AnimationType.NPC),
    BLONDE(3, "blonde", RGB.color(255,255,0), ModelType.ALEX, AnimationType.NPC);

    public static final Character[] BY_ID = Arrays.stream(values()).sorted(Comparator
                            .comparingInt(Character::getId)).toArray(Character[]::new);
    private final int id;
    private final String name;
    private int color;
    private ModelType model;
    private AnimationType animation;

    Character(int identificator, String name_key, int textColor, ModelType modelType, AnimationType animationSet){
        this.id = identificator;
        this.name = name_key;
        this.color = textColor;
        this.model = modelType;
        this.animation = animationSet;
    }

    public int getId(){return this.id;}
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

    public static Character getCharacter(String name){
        for(int i = 0; i < BY_ID.length; i++){
            if(name.equals(byId(i).getName())){
                return byId(i);
            }
        }
        return MAXIE;
    }

    public static Character byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

}
