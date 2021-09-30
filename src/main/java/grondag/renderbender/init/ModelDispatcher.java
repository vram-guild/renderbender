package grondag.renderbender.init;

import java.util.HashMap;

import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;

import io.vram.frex.api.config.FrexFeature;

import grondag.renderbender.model.SimpleUnbakedModel;

public class ModelDispatcher {
    private static HashMap<String, SimpleUnbakedModel> models;

    private static void initModels() {
        if(models == null) {
            models = new HashMap<>();
            BasicModels.initialize(models);
            if(FrexFeature.isAvailable(FrexFeature.MATERIAL_SHADERS)) {
                ExtendedModels.initialize(models);
            }
        }
    }

    public static void initialize() {
    	// WIP: remove Fabric dep
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
