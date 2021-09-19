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

import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;

public abstract class AbstractModel implements BakedModel, FabricBakedModel {
	protected static final Renderer RENDERER = RendererAccess.INSTANCE.getRenderer();

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
