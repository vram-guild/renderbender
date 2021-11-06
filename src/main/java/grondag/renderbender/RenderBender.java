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

package grondag.renderbender;

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

import grondag.renderbender.init.BasicBlocks;
import grondag.renderbender.init.BasicBlocks.BeTestBlockEntity;
import grondag.renderbender.init.ExtendedBlocks;
import grondag.renderbender.init.Fluids;
import grondag.renderbender.init.ModelDispatcher;

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
