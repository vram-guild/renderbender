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

package io.vram.renderbender.init;

import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;

import io.vram.frex.api.buffer.QuadEmitter;
import io.vram.frex.api.buffer.QuadTransform;
import io.vram.frex.api.material.MaterialFinder;
import io.vram.frex.api.material.RenderMaterial;
import io.vram.frex.api.mesh.MeshBuilder;
import io.vram.frex.api.model.BakedInputContext;
import io.vram.frex.api.model.util.BakedModelUtil;
import io.vram.frex.api.renderer.Renderer;
import io.vram.frex.base.renderer.util.BakedModelTranscoder;
import io.vram.renderbender.model.DynamicRenderer;
import io.vram.renderbender.model.SimpleModel;
import io.vram.renderbender.model.SimpleUnbakedModel;

public class BasicModels {
	static final QuadTransform HACKFORMER = (ctx, in, out) -> {
		final TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation("minecraft:block/white_concrete"));
		in.copyTo(out);
		out.vertexColor(0xFFFF0000, 0xFFFF0000, 0xFFFF0000, 0xFFFF0000);
		out.spriteBake(sprite, QuadEmitter.BAKE_LOCK_UV);
		out.emit();
	};

	public static void initialize(HashMap<String, SimpleUnbakedModel> models) {
		models.put("item_transform", new SimpleUnbakedModel(mb -> {
			return new SimpleModel(mb.builder.build(), HACKFORMER, mb.getSprite("minecraft:block/cobble"), BakedModelUtil.MODEL_TRANSFORM_BLOCK, new DynamicRenderer() {
				@Override
				public void render(BlockAndTintGetter blockView, BakedInputContext input, QuadEmitter output) {
					final BakedModel baseModel = Minecraft.getInstance().getModelManager().getModel(new ModelResourceLocation(new ResourceLocation("minecraft", "cobble"), "inventory"));
					BakedModelTranscoder.accept(baseModel, input, output);
				}
			});
		}));

		models.put("ao_test", new SimpleUnbakedModel(mb -> {
			return new SimpleModel(null, null, mb.getSprite("minecraft:block/white_concrete"), BakedModelUtil.MODEL_TRANSFORM_BLOCK, aoBuilder());
		}));

		models.put("shade_test", new SimpleUnbakedModel(mb -> {
			final TextureAtlasSprite sprite = mb.getSprite("minecraft:block/white_concrete");
			mb.box(mb.finder().find(),
				-1, sprite,
				1f/16f, 1f/16f, 1f/16f, 15f/16f, 15f/16f, 15f/16f);
			return new SimpleModel(mb.builder.build(), null, sprite, BakedModelUtil.MODEL_TRANSFORM_BLOCK, null);
		}));
	}

	// this is NOT the way to handle this...
	static DynamicRenderer aoBuilder() {
		return new DynamicRenderer() {
			Renderer renderer = Renderer.get();
			RenderMaterial mat = MaterialFinder.threadLocal().disableDiffuse(true).find();
			@Override
			public void render(BlockAndTintGetter blockView, BakedInputContext input, QuadEmitter output) {
				final int hash = input.pos() == null ? 8 : input.pos().hashCode();
				final float height = (1 + (hash & 15)) / 16f;
				final TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation("minecraft:block/white_concrete"));
				final MeshBuilder builder = renderer.meshBuilder();
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

				builder.build().outputTo(output);
			}
		};
	}
}
