/*
 * This file is part of RenderBender and is licensed to the project under
 * terms that are compatible with the GNU Lesser General Public License.
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership and licensing.
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
 */

package io.vram.renderbender.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.FoliageColor;

import io.vram.frex.api.buffer.QuadEmitter;
import io.vram.frex.api.material.MaterialFinder;
import io.vram.frex.api.material.RenderMaterial;
import io.vram.frex.api.mesh.MeshBuilder;
import io.vram.frex.api.renderer.Renderer;
import io.vram.frex.api.world.BlockColorRegistry;
import io.vram.frex.api.world.ItemColorRegistry;
import io.vram.frex.base.client.model.CachedMeshModel;
import io.vram.renderbender.common.AoTestCommon;

public class AoTest {
	public static void initialize() {
		CachedMeshModel.createAndRegisterProvider(
			b -> b.defaultParticleSprite("minecraft:block/white_concrete")
			.keyCount(16)
			.blockKeyFunction(input -> 1 + ((input.pos() == null ? 8 : input.pos().hashCode()) & 15))
			.itemKeyFunction(input -> 8)
			.vanillaKeyFunction((blockState, random) -> 8)
			.meshFactory(key -> {
				final RenderMaterial mat = MaterialFinder.threadLocal().disableDiffuse(true).find();
				final float height = (1 + (key & 15)) / 16f;
				final TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation("minecraft:block/white_concrete"));
				final MeshBuilder builder = Renderer.get().meshBuilder();
				final QuadEmitter emitter = builder.getEmitter();

				for (int d = 0; d < 6; d++) {
					final Direction face = Direction.from3DDataValue(d);

					if (face == Direction.UP) {
						final float depth = face == Direction.UP ? 1 - height : 0;

						for (int i = 0; i < 4; i++) {
							for (int j = 0; j < 4; j++) {
								final float u = i * .25f;
								final float v = j * .25f;
								emitter.square(face, u, v, u + .25f, v + .25f, depth)
								.material(mat).vertexColor(-1, -1, -1, -1)
								.colorIndex(0)
								.spriteBake(sprite, QuadEmitter.BAKE_LOCK_UV).emit();
							}
						}
					} else if (face == Direction.DOWN) {
						emitter.square(face, 0, 0, 1, 1, 0)
						.material(mat).vertexColor(-1, -1, -1, -1)
						.colorIndex(0)
						.spriteBake(sprite, QuadEmitter.BAKE_LOCK_UV).emit();
					} else {
						emitter.square(face, 0, 0, 1, height, 0)
						.material(mat).vertexColor(-1, -1, -1, -1)
						.colorIndex(0)
						.spriteBake(sprite, QuadEmitter.BAKE_LOCK_UV).emit();
					}
				}

				return builder.build();
			}),
			"renderbender:ao_test");

		BlockColorRegistry.register((blockState, extendedBlockView, pos, colorIndex) -> {
			return extendedBlockView != null && pos != null ? BiomeColors.getAverageFoliageColor(extendedBlockView, pos) : FoliageColor.getDefaultColor();
		}, AoTestCommon.AO_TEST);

		ItemColorRegistry.register((stack, colorIndex) -> {
			return FoliageColor.getDefaultColor();
		}, AoTestCommon.AO_TEST);
	}
}
