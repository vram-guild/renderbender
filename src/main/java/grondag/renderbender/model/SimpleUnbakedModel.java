package grondag.renderbender.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;

import com.mojang.datafixers.util.Pair;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;

public class SimpleUnbakedModel implements UnbakedModel {
	Function<ModelBuilder, BakedModel> baker;

	public SimpleUnbakedModel(Function<ModelBuilder, BakedModel> baker) {
		this.baker = baker;
	}

	@Override
	public Collection<ResourceLocation> getDependencies() {
		return Collections.emptyList();
	}

	@Override
	public Collection<Material> getMaterials(Function<ResourceLocation, UnbakedModel> var1, Set<Pair<String, String>> set) {
		return Collections.emptyList();
	}

	@Override
	public BakedModel bake(ModelBakery var1, Function<Material, TextureAtlasSprite> spriteFunc, ModelState var3, ResourceLocation var4) {
		return baker.apply(ModelBuilder.prepare(spriteFunc));
	}
}
