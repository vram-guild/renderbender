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

import net.minecraft.resources.ResourceLocation;

import io.vram.frex.api.config.ShaderConfig;
import io.vram.renderbender.RenderBender;
import io.vram.renderbender.init.ModelDispatcher;

public class ClientInit {
	public static void initialize() {
		if (RenderBender.customFluid) Fluids.initialize();
		if (RenderBender.regionBakeListener) RegionBakeListener.initialize();
		if (RenderBender.renderLoopListener) RenderLoop.initialize();
		if (RenderBender.simpleMaterials) SimpleMaterials.initialize();
		if (RenderBender.nonCubics) NonCubics.initialize();
		if (RenderBender.dynamicGlow) DynamicGlow.initialize();
		if (RenderBender.blockEntityData) BlockEntityData.initialize();
		if (RenderBender.itemTransform) ItemTransform.initialize();
		if (RenderBender.subcubicShading) Subcubic.initialize();
		if (RenderBender.itemTransform) ItemTransform.initialize();
		if (RenderBender.aoTest) AoTest.initialize();

		ModelDispatcher.initialize();

		ShaderConfig.registerShaderConfigSupplier(new ResourceLocation("renderbender:configtest"), () -> "#define SHADER_CONFIG_WORKS");
	}
}
