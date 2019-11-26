package grondag.renderbender.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;

import com.mojang.datafixers.util.Pair;

import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

public class SimpleUnbakedModel implements UnbakedModel {
	Function<ModelBuilder, BakedModel> baker;

	public SimpleUnbakedModel(Function<ModelBuilder, BakedModel> baker) {
		this.baker = baker;
	}

	@Override
	public Collection<Identifier> getModelDependencies() {
		return Collections.emptyList();
	}

	@Override
	public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> var1, Set<Pair<String, String>> set) {
		return Collections.emptyList();
	}

	@Override
	public BakedModel bake(ModelLoader var1, Function<SpriteIdentifier, Sprite> spriteFunc, ModelBakeSettings var3, Identifier var4) {
		return baker.apply(ModelBuilder.prepare(spriteFunc));
	}
}