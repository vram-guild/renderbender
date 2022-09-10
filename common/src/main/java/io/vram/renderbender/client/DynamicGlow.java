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

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;

import io.vram.frex.api.buffer.QuadTransform;
import io.vram.frex.api.model.BlockItemModel;
import io.vram.frex.api.model.BlockModel;
import io.vram.frex.api.model.util.ColorUtil;
import io.vram.frex.base.client.model.TransformingModel;

public class DynamicGlow {
	public static void initialize() {
		final QuadTransform transform = (ctx, in, out) -> {
			final var random = ctx.random();
			final int topColor = DynamicGlow.randomPastelColor(random);
			final int bottomColor = DynamicGlow.randomPastelColor(random);
			final boolean topGlow = random.nextBoolean();
			final int topLight = topGlow ? ColorUtil.FULL_BRIGHTNESS : 0;
			final int bottomLight = topGlow ? 0 : ColorUtil.FULL_BRIGHTNESS;
			in.copyTo(out);

			for (int i = 0; i < 4; i++) {
				if (Mth.equal(out.y(i), 0)) {
					out.vertexColor(i, bottomColor).lightmap(i, bottomLight);
				} else {
					out.vertexColor(i, topColor).lightmap(i, topLight);
				}
			}

			out.emit();
		};

		TransformingModel.createAndRegisterProvider(
			b -> b.defaultParticleSprite("minecraft:block/white_concrete_powder"),
			() -> (BlockItemModel) BlockModel.get(Blocks.WHITE_CONCRETE_POWDER.defaultBlockState()),
			transform,
			"renderbender:dynamic_glow");
	}

	public static int randomPastelColor(RandomSource random) {
		return 0xFF000000 | ((random.nextInt(127) + 127) << 16) | ((random.nextInt(127) + 127) << 8) | (random.nextInt(127) + 127);
	}
}
