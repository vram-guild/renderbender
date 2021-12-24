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

import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import io.vram.frex.api.material.MaterialCondition;
import io.vram.frex.api.material.MaterialConstants;
import io.vram.frex.api.material.RenderMaterial;
import io.vram.frex.base.client.model.MeshFactory;
import io.vram.frex.base.client.model.StaticMeshModel;
import io.vram.renderbender.common.ConditionalCommon;

public class Conditional {
	public static void initialize() {
		final MeshFactory meshFactory = (meshBuilder, materialFinder, spriteFunc) -> {
			final RenderMaterial mat = materialFinder
					.shader(new ResourceLocation("renderbender", "shader/test.vert"), new ResourceLocation("renderbender", "shader/test.frag"))
					.preset(MaterialConstants.PRESET_TRANSLUCENT)
					.condition(condition.get())
					.emissive(true)
					.disableDiffuse(true)
					.disableAo(true)
					.find();

			final TextureAtlasSprite sprite = spriteFunc.getSprite("minecraft:block/snow");

			return meshBuilder.box(mat, 0x80DCEFFF, sprite, 0, 0, 0, 1, 1, 1).build();
		};

		StaticMeshModel.createAndRegisterProvider(b -> b.defaultParticleSprite("minecraft:block/snow"), meshFactory, "renderbender:conditional");
	}

	private static final Supplier<MaterialCondition> condition = Suppliers.memoize(() ->
		MaterialCondition.create(() -> {
			@SuppressWarnings("resource")
			final Entity entity = Minecraft.getInstance().cameraEntity;

			if (entity == null || entity.level == null) {
				return false;
			} else if (entity.level.isRaining()) {
				return true;
			} else if (entity instanceof final LivingEntity living) {
				return living.getMainHandItem().getItem() == ConditionalCommon.CONDITION_ITEM
						|| living.getOffhandItem().getItem() == ConditionalCommon.CONDITION_ITEM;
			} else {
				return false;
			}
		}, true, true));
}
