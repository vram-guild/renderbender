/*******************************************************************************
 * Copyright 2019 grondag
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package grondag.renderbender.model;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import io.vram.frex.api.mesh.Mesh;
import io.vram.frex.api.model.ModelHelper;
import io.vram.frex.api.model.ModelRenderContext;

/**
 * Simple baked model supporting the Fabric Render API features.<p>
 */
public class SimpleModel extends AbstractModel {
	protected final Mesh mesh;
	protected final Supplier<MeshTransformer> transformerFactory;
	protected WeakReference<List<BakedQuad>[]> quadLists = null;
	protected final ItemProxy itemProxy = new ItemProxy();

	public SimpleModel(
	Mesh mesh,
	Supplier<MeshTransformer> transformerFactory,
	TextureAtlasSprite sprite,
	ItemTransforms transformation,
	DynamicRenderer dynamicRender) {
		super(sprite, transformation, dynamicRender);
		this.mesh = mesh;
		this.transformerFactory = transformerFactory;
	}

	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction face, Random rand) {
		List<BakedQuad>[] lists = quadLists == null ? null : quadLists.get();
		if(lists == null) {
			lists = ModelHelper.toQuadLists(mesh);
			quadLists = new WeakReference<>(lists);
		}
		final List<BakedQuad> result = lists[face == null ? 6 : face.get3DDataValue()];
		return result == null ? ImmutableList.of() : result;
	}

	@Override
	public void renderAsBlock(BlockInputContext input, ModelRenderContext context) {
		final MeshTransformer transform = transformerFactory == null ? null : transformerFactory.get().prepare(input, context);

		if(transform != null) {
			context.pushTransform(transform);
		}
		if(mesh != null) {
			context.accept(mesh);
		}
		if(dynamicRender != null) {
			dynamicRender.render(input.blockView(), input.blockState(), input.pos(), input::random, context);
		}
		if(transform != null) {
			context.popTransform();
		}
	}

	@Override
	public ItemOverrides getOverrides() {
		return itemProxy;
	}

	protected class ItemProxy extends ItemOverrides {
		public ItemProxy() {
			super(null, null, null, Collections.emptyList());
		}

		@Override
		public BakedModel resolve(BakedModel bakedModel, ItemStack itemStack, @Nullable ClientLevel clientWorld, @Nullable LivingEntity livingEntity, int seed) {
			return SimpleModel.this;
		}
	}

	@Override
	public void renderAsItem(ItemInputContext input, ModelRenderContext context) {
		final MeshTransformer transform = transformerFactory == null ? null : transformerFactory.get().prepare(input, context);
		if(transform != null) {
			context.pushTransform(transform);
		}
		if(mesh != null) {
			context.accept(mesh);
		}
		if(dynamicRender != null) {
			dynamicRender.render(null, null, null, input::random, context);
		}
		if(transform != null) {
			context.popTransform();
		}
	}
}
