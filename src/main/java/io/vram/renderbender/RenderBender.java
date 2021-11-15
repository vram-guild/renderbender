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

package io.vram.renderbender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.FoliageColor;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;

import io.vram.frex.api.config.ShaderConfig;
import io.vram.frex.api.world.BlockColorRegistry;
import io.vram.frex.api.world.BlockEntityRenderData;
import io.vram.frex.api.world.ItemColorRegistry;
import io.vram.renderbender.init.BasicBlocks;
import io.vram.renderbender.init.ExtendedBlocks;
import io.vram.renderbender.init.ModelDispatcher;
import io.vram.renderbender.init.SimpleMaterials;
import io.vram.renderbender.init.BasicBlocks.BeTestBlockEntity;

public class RenderBender implements ModInitializer, ClientModInitializer {
	public static final Logger LOG = LogManager.getLogger();

	@Override
	public void onInitialize() {
		BasicBlocks.initialize();
		ExtendedBlocks.initialize();
		Fluids.initialize();
		BakeListenerTest.initialize();
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void onInitializeClient() {
		ModelDispatcher.initialize();
		RenderLoopTest.init();
		Fluids.initClient();
		SimpleMaterials.initialize();

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
