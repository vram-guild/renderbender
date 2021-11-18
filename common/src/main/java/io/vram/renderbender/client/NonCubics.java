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

import java.util.function.Function;

import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import io.vram.frex.api.buffer.QuadEmitter;
import io.vram.frex.api.material.MaterialFinder;
import io.vram.frex.api.material.RenderMaterial;
import io.vram.frex.api.model.provider.ModelProviderRegistry;
import io.vram.frex.base.client.model.MeshFactory;
import io.vram.frex.base.client.model.StaticMeshModel;

public class NonCubics {
	public static void initialize() {
		// should have foil only in inventory
		registerRoundModel("renderbender:round_enchanted", false, f -> f.find());

		// should have foil always
		registerRoundModel("renderbender:round_foil", false, f -> f.foilOverlay(true).find());

		registerRoundModel("renderbender:round_hard", false, f -> f.find());
		registerRoundModel("renderbender:round_hard_no_diffuse", false, f -> f.disableDiffuse(true).find());
		registerRoundModel("renderbender:round_hard_no_ao", false, f -> f.disableAo(true).find());
		registerRoundModel("renderbender:round_hard_no_shading", false, f -> f.disableAo(true).disableDiffuse(true).find());

		registerRoundModel("renderbender:round_soft", true, f -> f.find());
		registerRoundModel("renderbender:round_soft_no_diffuse", true, f -> f.disableDiffuse(true).find());
		registerRoundModel("renderbender:round_soft_no_ao", true, f -> f.disableAo(true).find());
		registerRoundModel("renderbender:round_soft_no_shading", true, f -> f.disableAo(true).disableDiffuse(true).find());
	}

	private static void registerRoundModel(String blockPath, boolean smoothNormals, Function<MaterialFinder, RenderMaterial> materialFunc) {
		final MeshFactory meshFactory = (meshBuilder, materialFinder, spriteFunc) -> {
			final TextureAtlasSprite sprite = spriteFunc.getSprite("minecraft:block/white_concrete");
			makeIcosahedron(new Vector3f(0.5f, 0.5f, 0.5f), 0.5f, meshBuilder.getEmitter(), materialFunc.apply(materialFinder), sprite, smoothNormals);
			return meshBuilder.build();
		};

		ModelProviderRegistry.registerBlockItemProvider(StaticMeshModel.createProvider(b -> b.defaultParticleSprite("minecraft:block/white_concrete"), meshFactory), blockPath);
	}

	/**
	 * Makes a regular icosahedron, which is a very close approximation to a sphere for most purposes.
	 * Loosely based on http://blog.andreaskahler.com/2009/06/creating-icosphere-mesh-in-code.html
	 */
	private static void makeIcosahedron(Vector3f center, float radius, QuadEmitter qe, RenderMaterial material, TextureAtlasSprite sprite, boolean smoothNormals) {
		/** vertex scale */
		final float s = (float) (radius / (2 * Math.sin(2 * Math.PI / 5)));

		final Vector3f[] vertexes = new Vector3f[12];
		Vector3f[] normals = new Vector3f[12];
		// create 12 vertices of a icosahedron
		final float t = (float) (s * (1.0 + Math.sqrt(5.0)) / 2.0);
		int vi = 0;

		normals[vi++] = new Vector3f(-s, t, 0);
		normals[vi++] = new Vector3f(s, t, 0);
		normals[vi++] = new Vector3f(-s, -t, 0);
		normals[vi++] = new Vector3f(s, -t, 0);

		normals[vi++] = new Vector3f(0, -s, t);
		normals[vi++] = new Vector3f(0, s, t);
		normals[vi++] = new Vector3f(0, -s, -t);
		normals[vi++] = new Vector3f(0, s, -t);

		normals[vi++] = new Vector3f(t, 0, -s);
		normals[vi++] = new Vector3f(t, 0, s);
		normals[vi++] = new Vector3f(-t, 0, -s);
		normals[vi++] = new Vector3f(-t, 0, s);

		for (int i = 0; i < 12; i++) {
			final Vector3f n = normals[i];
			vertexes[i] = new Vector3f(center.x() + n.x(), center.y() + n.y(), center.z() + n.z());

			if (smoothNormals) {
				float x = n.x();
				float y = n.y();
				float z = n.z();

				final float len = (float) Math.sqrt(x * x + y * y + z * z);

				x /= len;
				y /= len;
				z /= len;
				n.set(x, y, z);
			}
		}

		if (!smoothNormals) {
			normals = null;
		}

		// create 20 triangles of the icosahedron
		makeIcosahedronFace(true, 0, 11, 5, vertexes, normals, qe, material, sprite);
		makeIcosahedronFace(false, 4, 5, 11, vertexes, normals, qe, material, sprite);
		makeIcosahedronFace(true, 0, 5, 1, vertexes, normals, qe, material, sprite);
		makeIcosahedronFace(false, 9, 1, 5, vertexes, normals, qe, material, sprite);
		makeIcosahedronFace(true, 0, 1, 7, vertexes, normals, qe, material, sprite);
		makeIcosahedronFace(false, 8, 7, 1, vertexes, normals, qe, material, sprite);
		makeIcosahedronFace(true, 0, 7, 10, vertexes, normals, qe, material, sprite);
		makeIcosahedronFace(false, 6, 10, 7, vertexes, normals, qe, material, sprite);
		makeIcosahedronFace(true, 0, 10, 11, vertexes, normals, qe, material, sprite);
		makeIcosahedronFace(false, 2, 11, 10, vertexes, normals, qe, material, sprite);
		makeIcosahedronFace(true, 5, 4, 9, vertexes, normals, qe, material, sprite);
		makeIcosahedronFace(false, 3, 9, 4, vertexes, normals, qe, material, sprite);
		makeIcosahedronFace(true, 11, 2, 4, vertexes, normals, qe, material, sprite);
		makeIcosahedronFace(false, 3, 4, 2, vertexes, normals, qe, material, sprite);
		makeIcosahedronFace(true, 10, 6, 2, vertexes, normals, qe, material, sprite);
		makeIcosahedronFace(false, 3, 2, 6, vertexes, normals, qe, material, sprite);
		makeIcosahedronFace(true, 7, 8, 6, vertexes, normals, qe, material, sprite);
		makeIcosahedronFace(false, 3, 6, 8, vertexes, normals, qe, material, sprite);
		makeIcosahedronFace(true, 1, 9, 8, vertexes, normals, qe, material, sprite);
		makeIcosahedronFace(false, 3, 8, 9, vertexes, normals, qe, material, sprite);
	}

	private static void makeIcosahedronFace(boolean topHalf, int p1, int p2, int p3, Vector3f[] points, Vector3f[] normals, QuadEmitter qe, RenderMaterial material, TextureAtlasSprite sprite) {
		if (topHalf) {
			qe.pos(0, points[p1]).uv(0, 1, 1).vertexColor(-1, -1, -1, -1);
			qe.pos(1, points[p2]).uv(1, 0, 1).vertexColor(-1, -1, -1, -1);
			qe.pos(2, points[p3]).uv(2, 1, 0).vertexColor(-1, -1, -1, -1);
			qe.pos(3, points[p3]).uv(3, 1, 0).vertexColor(-1, -1, -1, -1);
		} else {
			qe.pos(0, points[p1]).uv(0, 0, 0).vertexColor(-1, -1, -1, -1);
			qe.pos(1, points[p2]).uv(1, 1, 0).vertexColor(-1, -1, -1, -1);
			qe.pos(2, points[p3]).uv(2, 0, 1).vertexColor(-1, -1, -1, -1);
			qe.pos(3, points[p3]).uv(3, 0, 1).vertexColor(-1, -1, -1, -1);
		}

		if (normals != null) {
			qe.normal(0, normals[p1]);
			qe.normal(1, normals[p2]);
			qe.normal(2, normals[p3]);
			qe.normal(3, normals[p3]);
		}

		qe.spriteBake(sprite, QuadEmitter.BAKE_NORMALIZED);
		qe.material(material);
		qe.emit();
	}
}
