package lv.sanelite.initium.entity.dataset;

public enum AnimationType {
    SANELITE("sanelite.animation.json"),
    NPC("npc.animation.json");

    private final String filename;

    AnimationType(String location){
        this.filename = location;
    }

    public String getFilename() {
        return filename;
    }
}
