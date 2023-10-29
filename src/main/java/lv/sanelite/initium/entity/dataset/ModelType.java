package lv.sanelite.initium.entity.dataset;

public enum ModelType {
    STEVE("steve.geo.json", false), MAGIC_STEVE("steve.geo.json", true),
    ALEX("slim.geo.json", false), MAGIC_ALEX("slim.geo.json", true),
    ANGELOID("angeloid.geo.json", true),
    SENTRY("sentry.geo.json", true);

    private final String filename;
    private final boolean glow;

    ModelType(String location, boolean glowing){
        this.filename = location;
        this.glow = glowing;
    }

    public String getFilename() {
        return filename;
    }

    public boolean isGlowing() {
        return glow;
    }
}
