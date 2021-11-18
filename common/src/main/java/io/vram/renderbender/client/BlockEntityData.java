/*
 * Copyright © Contributing Authors
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

package io.vram.renderbender.client;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;

import io.vram.frex.api.buffer.QuadEmitter;
import io.vram.frex.api.buffer.QuadTransform;
import io.vram.frex.api.material.MaterialConstants;
import io.vram.frex.api.material.MaterialFinder;
import io.vram.frex.api.material.RenderMaterial;
import io.vram.frex.api.model.BlockModel.BlockInputContext;
import io.vram.frex.api.model.InputContext.Type;
import io.vram.frex.api.world.BlockEntityRenderData;
import io.vram.frex.base.client.model.MeshFactory;
import io.vram.frex.base.client.model.TransformingMeshModel;
import io.vram.renderbender.common.BlockEntityDataCommon;
import io.vram.renderbender.common.BlockEntityDataCommon.BeTestBlockEntity;

public class BlockEntityData {
	public static void initialize() {
		BlockEntityRenderData.registerProvider(BlockEntityDataCommon.BE_TEST_TYPE, be -> ((BeTestBlockEntity) be).getRenderData());

		final var finder = MaterialFinder.threadLocal();
		final var matSolid = finder.clear().preset(MaterialConstants.PRESET_SOLID).find();
		final var matSolidGlow = finder.clear().preset(MaterialConstants.PRESET_SOLID).disableDiffuse(true).emissive(true).disableAo(true).find();
		final var matTrans = finder.clear().preset(MaterialConstants.PRESET_TRANSLUCENT).find();
		final var matTransGlow = finder.clear().preset(MaterialConstants.PRESET_TRANSLUCENT).disableDiffuse(true).emissive(true).disableAo(true).find();

		final QuadTransform transform = (ctx, in, out) -> {
			final RenderMaterial mat;
			final RenderMaterial matGlow;
			final boolean translucent;
			int[] data = null;

			if (ctx.type() == Type.BLOCK) {
				final var bctx = (BlockInputContext) ctx;
				data = (int[]) bctx.blockEntityRenderData(bctx.pos());
			}

			if (data == null) {
				mat = matSolid;
				matGlow = matSolidGlow;
				translucent = false;
				data = BeTestBlockEntity.ITEM_COLORS;
			} else {
				if (ctx.random().nextInt(4) == 0) {
					mat = matTrans;
					matGlow = matTransGlow;
					translucent = true;
				} else {
					mat = matSolid;
					matGlow = matSolidGlow;
					translucent = false;
				}
			}

			in.copyTo(out);
			final int s = data == null ? -1 : data[out.tag()];
			final int c = translucent ? 0x80000000 | (0xFFFFFF & s) : s;
			out.material((s & 0x3) == 0 ? matGlow : mat).vertexColor(c, c, c, c);
			out.emit();
		};

		final MeshFactory meshFactory = (meshBuilder, materialFinder, spriteFunc) -> {
			final TextureAtlasSprite sprite = spriteFunc.getSprite("minecraft:block/white_concrete");
			final RenderMaterial mat = materialFinder.find();
			final float PIXEL = 1f/16f;
			final QuadEmitter qe = meshBuilder.getEmitter();
			int t = 0;

			for (int d = 0; d < 6; d++) {
				final Direction face = Direction.from3DDataValue(d);

				for (int i = 0; i < 14; i++) {
					final float u = PIXEL + PIXEL * i;

					for (int j = 0; j < 14; j++) {
						final float v = PIXEL + PIXEL * j;
						qe.tag(t++);
						qe.material(mat).square(face, u, v, u + PIXEL, v + PIXEL, PIXEL)
						.vertexColor(-1, -1, -1, -1)
						.spriteBake(sprite, QuadEmitter.BAKE_LOCK_UV).emit();
					}
				}
			}

			return meshBuilder.build();
		};

		TransformingMeshModel.createAndRegisterProvider(b -> b.defaultParticleSprite("minecraft:block/white_concrete_powder"), meshFactory, transform, "renderbender:be_data");
	}
}
