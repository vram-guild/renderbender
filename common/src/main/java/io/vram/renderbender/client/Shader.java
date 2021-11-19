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

package io.vram.renderbender.client;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;

import io.vram.frex.api.material.RenderMaterial;
import io.vram.frex.base.client.model.MeshFactory;
import io.vram.frex.base.client.model.StaticMeshModel;

public class Shader {
	public static void initialize() {
		final MeshFactory meshFactory = (meshBuilder, materialFinder, spriteFunc) -> {
			final RenderMaterial mat = materialFinder
					.shader(new ResourceLocation("renderbender", "shader/test.vert"), new ResourceLocation("renderbender", "shader/test.frag"))
					.castShadows(false)
					.find();

			final TextureAtlasSprite sprite = spriteFunc.getSprite("minecraft:block/gray_concrete");

			return meshBuilder.box(mat, -1, sprite, 0, 0, 0, 1, 1, 1).build();
		};

		StaticMeshModel.createAndRegisterProvider(b -> b.defaultParticleSprite("minecraft:block/gray_concrete"), meshFactory, "renderbender:shader");
	}
}
