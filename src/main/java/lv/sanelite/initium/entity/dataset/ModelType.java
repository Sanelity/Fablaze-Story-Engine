package lv.sanelite.initium.entity.dataset;

import java.util.Map;

public class ModelType {
    private static Map<String, ModelType> modelTypeMap = Map.of(
            "steve", new ModelType("steve.geo.json", false),
            "magic_steve", new ModelType("steve.geo.json", true),
            "alex", new ModelType("slim.geo.json", false),
            "magic_alex", new ModelType("slim.geo.json", true),
            "angeloid", new ModelType("angeloid.geo.json", true),
            "sentry", new ModelType("sentry.geo.json", true)
    );

    private final String filename;
    private final boolean glow;

    private ModelType(String location, boolean glowing){
        this.filename = location;
        this.glow = glowing;
    }
    public void newModelType(String name, String filename, boolean glowing){
        ModelType modelType = new ModelType(filename, glowing);
        modelTypeMap.put(name, modelType);
    }

    public static ModelType getModelType(String namespace){
        if(modelTypeMap.containsKey(namespace)){
            return modelTypeMap.get(namespace);
        }else return modelTypeMap.get("steve");
    }

    public String getFilename() {
        return filename;
    }

    public boolean isGlowing() {
        return glow;
    }
}
