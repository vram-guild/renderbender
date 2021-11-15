package grondag.renderbender.init;

import io.vram.frex.api.model.provider.ModelProviderRegistry;
import io.vram.frex.base.client.model.MeshFactory;
import io.vram.frex.base.client.model.StaticModel;

public class SimpleMaterials {
	public static void initialize() {
		final MeshFactory glowMeshFactory = (meshBuilder, materialFinder, spriteFunc) -> {
			final var sprite = spriteFunc.getSprite("minecraft:block/white_concrete");
			final var material = materialFinder.emissive(true).disableAo(true).disableDiffuse(true).find();
			return meshBuilder.box(material, -1, sprite, 0, 0, 0, 1, 1, 1).build();
		};

		ModelProviderRegistry.registerBlockItemProvider(StaticModel.createProviderFunction(b -> b.defaultParticleSprite("minecraft:block/white_concrete"), glowMeshFactory), "renderbender:glow");
	}
}
