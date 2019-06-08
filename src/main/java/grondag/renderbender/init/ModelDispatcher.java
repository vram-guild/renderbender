package grondag.renderbender.init;

import java.util.HashMap;

import grondag.frex.Frex;
import grondag.renderbender.model.SimpleUnbakedModel;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;

public class ModelDispatcher {
    private static HashMap<String, SimpleUnbakedModel> models;
    
    private static void initModels() {
        if(models == null) {
            models = new HashMap<>();
            BasicModels.initialize(models);
            if(Frex.isAvailable()) {
                ExtendedModels.initialize(models);
            }
        }
    }
    
    public static void initialize() {
        ModelLoadingRegistry.INSTANCE.registerVariantProvider(manager -> ((modelId, context) -> {
            if (modelId.getNamespace().equals("renderbender")) {
                initModels();
                return models.get(modelId.getPath());
            } else {
                return null;
            }
        }));
    }

}
