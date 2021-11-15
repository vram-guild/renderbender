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
