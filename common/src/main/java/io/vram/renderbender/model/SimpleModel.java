/*
 * Copyright © Original Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Additional copyright and licensing notices may apply for content that was
 * included from other projects. For more information, see ATTRIBUTION.md.
 */

package io.vram.renderbender.model;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

import io.vram.frex.api.buffer.QuadEmitter;
import io.vram.frex.api.buffer.QuadSink;
import io.vram.frex.api.buffer.QuadTransform;
import io.vram.frex.api.mesh.Mesh;
import io.vram.frex.api.model.util.BakedModelUtil;

/**
 * Simple baked model supporting the Fabric Render API features.
 */
public class SimpleModel extends AbstractModel {
	protected final Mesh mesh;
	protected final QuadTransform transform;
	protected WeakReference<List<BakedQuad>[]> quadLists = null;
	protected final ItemProxy itemProxy = new ItemProxy();

	public SimpleModel(
			Mesh mesh,
			QuadTransform transform,
			TextureAtlasSprite sprite,
			ItemTransforms transformation,
			DynamicRenderer dynamicRender) {
		super(sprite, transformation, dynamicRender);
		this.mesh = mesh;
		this.transform = transform;
	}

	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction face, Random rand) {
		List<BakedQuad>[] lists = quadLists == null ? null : quadLists.get();

		if (lists == null) {
			lists = BakedModelUtil.toQuadLists(mesh);
			quadLists = new WeakReference<>(lists);
		}

		final List<BakedQuad> result = lists[face == null ? 6 : face.get3DDataValue()];
		return result == null ? ImmutableList.of() : result;
	}

	@Override
	public void renderAsBlock(BlockInputContext input, QuadSink output) {
		QuadEmitter emitter = output.asQuadEmitter();

		if (transform != null) {
			emitter = emitter.withTransformQuad(input, transform);
		}

		if (mesh != null) {
			mesh.outputTo(emitter);
		}

		if (dynamicRender != null) {
			dynamicRender.render(input.blockView(), input, emitter);
		}

		if (transform != null) {
			emitter.close();
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
	public void renderAsItem(ItemInputContext input, QuadSink output) {
		QuadEmitter emitter = output.asQuadEmitter();

		if (transform != null) {
			emitter = emitter.withTransformQuad(input, transform);
		}

		if (mesh != null) {
			mesh.outputTo(emitter);
		}

		if (dynamicRender != null) {
			dynamicRender.render(null, input, emitter);
		}

		if (transform != null) {
			emitter.close();
		}
	}
}
