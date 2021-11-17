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

package io.vram.renderbender.model;

import java.util.function.Function;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import io.vram.frex.api.buffer.QuadEmitter;
import io.vram.frex.api.material.MaterialFinder;
import io.vram.frex.api.material.RenderMaterial;
import io.vram.frex.api.mesh.MeshBuilder;
import io.vram.frex.api.renderer.Renderer;

public class ModelBuilder {
	private static ModelBuilder instance;

	private static Function<Material, TextureAtlasSprite> spriteFunc;

	public static ModelBuilder prepare(Function<Material, TextureAtlasSprite> spriteFuncIn) {
		if (instance == null) {
			instance = new ModelBuilder();
		}

		spriteFunc = spriteFuncIn;
		return instance;
	}

	public final MeshBuilder builder;
	private final MaterialFinder finder;
	private ModelBuilder() {
		final Renderer renderer = Renderer.get();
		builder = renderer.meshBuilder();
		finder = MaterialFinder.newInstance();
	}

	public MaterialFinder finder() {
		return finder.clear();
	}

	public TextureAtlasSprite getSprite(String spriteName) {
		return spriteFunc.apply(new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(spriteName)));
	}

	public void box(
		RenderMaterial material,
		int color, TextureAtlasSprite sprite,
		float minX, float minY, float minZ,
		float maxX, float maxY, float maxZ
	) {
		builder.getEmitter()
		.material(material)
		.square(Direction.UP, minX, minZ, maxX, maxZ, 1-maxY)
		.vertexColor(color, color, color, color)
		.uvUnitSquare()
		.spriteBake(sprite, QuadEmitter.BAKE_NORMALIZED)
		.emit()

		.material(material)
		.square(Direction.DOWN, minX, minZ, maxX, maxZ, minY)
		.vertexColor(color, color, color, color)
		.uvUnitSquare()
		.spriteBake(sprite, QuadEmitter.BAKE_NORMALIZED)
		.emit()

		.material(material)
		.square(Direction.EAST, minZ, minY, maxZ, maxY, 1-maxX)
		.vertexColor(color, color, color, color)
		.uvUnitSquare()
		.spriteBake(sprite, QuadEmitter.BAKE_NORMALIZED)
		.emit()

		.material(material)
		.square(Direction.WEST, minZ, minY, maxZ, maxY, minX)
		.vertexColor(color, color, color, color)
		.uvUnitSquare()
		.spriteBake(sprite, QuadEmitter.BAKE_NORMALIZED)
		.emit()

		.material(material)
		.square(Direction.SOUTH, minX, minY, maxX, maxY, 1-maxZ)
		.vertexColor(color, color, color, color)
		.uvUnitSquare()
		.spriteBake(sprite, QuadEmitter.BAKE_NORMALIZED)
		.emit()

		.material(material)
		.square(Direction.NORTH, minX, minY, maxX, maxY, minZ)
		.vertexColor(color, color, color, color)
		.uvUnitSquare()
		.spriteBake(sprite, QuadEmitter.BAKE_NORMALIZED)
			.emit();
	}
}
