/*******************************************************************************
 * Copyright 2019 grondag
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package grondag.renderbender.model;

import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;

import io.vram.frex.api.model.BlockModel;
import io.vram.frex.api.model.ItemModel;
import io.vram.frex.api.renderer.Renderer;

public abstract class AbstractModel implements BakedModel, BlockModel, ItemModel {
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
