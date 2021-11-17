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

package io.vram.renderbender.client;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.FoliageColor;

import io.vram.frex.api.config.ShaderConfig;
import io.vram.frex.api.world.BlockColorRegistry;
import io.vram.frex.api.world.BlockEntityRenderData;
import io.vram.frex.api.world.ItemColorRegistry;
import io.vram.renderbender.RenderBender;
import io.vram.renderbender.init.BasicBlocks;
import io.vram.renderbender.init.BasicBlocks.BeTestBlockEntity;
import io.vram.renderbender.init.ModelDispatcher;

public class ClientInit {
	public static void initialize() {
		if (RenderBender.customFluid) Fluids.initialize();
		if (RenderBender.regionBakeListener) RegionBakeListener.initialize();
		if (RenderBender.renderLoopListener) RenderLoop.initialize();
		if (RenderBender.simpleMaterials) SimpleMaterials.initialize();
		if (RenderBender.nonCubics) NonCubics.initialize();
		if (RenderBender.dynamicModels) Dynamic.initialize();

		ModelDispatcher.initialize();

		ShaderConfig.registerShaderConfigSupplier(new ResourceLocation("renderbender:configtest"), () -> "#define SHADER_CONFIG_WORKS");
		BlockEntityRenderData.registerProvider(BasicBlocks.BE_TEST_TYPE, be -> ((BeTestBlockEntity) be).getRenderData());

		BlockColorRegistry.register((blockState, extendedBlockView, pos, colorIndex) -> {
			return extendedBlockView != null && pos != null ? BiomeColors.getAverageFoliageColor(extendedBlockView, pos) : FoliageColor.getDefaultColor();
		}, BasicBlocks.AO_TEST);

		ItemColorRegistry.register((stack, colorIndex) -> {
			return FoliageColor.getDefaultColor();
		}, BasicBlocks.AO_TEST);
	}
}
