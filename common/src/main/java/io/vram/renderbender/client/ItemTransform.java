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
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import io.vram.frex.api.buffer.QuadEmitter;
import io.vram.frex.api.buffer.QuadTransform;
import io.vram.frex.api.model.BlockItemModel;
import io.vram.frex.api.model.ItemModel;
import io.vram.frex.base.client.model.TransformingModel;

public class ItemTransform {
	public static void initialize() {
		final QuadTransform hackformer = (ctx, in, out) -> {
			final TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation("minecraft:block/white_concrete"));
			in.copyTo(out);
			out.vertexColor(0xFFFF0000, 0xFFFF0000, 0xFFFF0000, 0xFFFF0000);
			out.spriteBake(sprite, QuadEmitter.BAKE_LOCK_UV);
			out.emit();
		};

		TransformingModel.createAndRegisterProvider(
			b -> b.defaultParticleSprite("minecraft:block/white_concrete"),
			() -> (BlockItemModel) ItemModel.get(Items.IRON_PICKAXE),
			hackformer,
			"renderbender:item_transform");
	}
}
