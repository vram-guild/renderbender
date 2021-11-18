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

import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;

import io.vram.frex.api.model.BlockItemModel;
import io.vram.frex.api.renderer.Renderer;

public abstract class AbstractModel implements BakedModel, BlockItemModel {
	protected static final Renderer RENDERER = Renderer.get();

	protected final TextureAtlasSprite modelSprite;
	protected final ItemTransforms transformation;
	protected final DynamicRenderer dynamicRender;

	protected AbstractModel(
			TextureAtlasSprite sprite,
			ItemTransforms transformation,
			DynamicRenderer dynamicRender) {
		modelSprite = sprite;
		this.transformation = transformation;
		this.dynamicRender = dynamicRender;
	}

	@Override
	public boolean useAmbientOcclusion() {
		return true;
	}

	@Override
	public boolean usesBlockLight() {
		return true;
	}

	@Override
	public boolean isGui3d() {
		return true;
	}

	@Override
	public boolean isCustomRenderer() {
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleIcon() {
		return modelSprite;
	}

	@Override
	public ItemTransforms getTransforms() {
		return transformation;
	}
}
