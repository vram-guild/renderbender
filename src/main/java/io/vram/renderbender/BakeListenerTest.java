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

import java.util.function.Predicate;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import io.vram.frex.api.world.RenderRegionBakeListener;
import io.vram.frex.api.world.RenderRegionBakeListener.RenderRegionContext;
import io.vram.frex.base.client.world.ForwardingBlockView;

// TODO: add compatibility test for corresponding Fabric API
public class BakeListenerTest {
	private static final RenderRegionBakeListener listener = new RenderRegionBakeListener() {
		@Override
		public void bake(RenderRegionContext<BlockAndTintGetter> context, BlockStateRenderer blockStateRenderer) {
			final BlockState bs = Blocks.MAGENTA_STAINED_GLASS.defaultBlockState();

			for (int x = 0; x < 16; ++x) {
				for (int z = 0; z < 16; ++z) {
					blockStateRenderer.bake(context.originOffset(x, 0, z), bs);
				}
			}
		}

		@Override
		public BlockAndTintGetter blockViewOverride(BlockAndTintGetter baseView) {
			return new ForwardingBlockView(baseView) {
				@Override
				public BlockState getBlockState(BlockPos blockPos) {
					var result = wrapped.getBlockState(blockPos);

					if (result == null || result.isAir()) {
						if (blockPos.getY() == 128 & (blockPos.getX() & 0xF0) == 0 & (blockPos.getZ() & 0xF0) == 0) {
							result = Blocks.MAGENTA_STAINED_GLASS.defaultBlockState();
						}
					}

					return result;
				}
			};
		}
	};

	private static final Predicate<? super RenderRegionContext<Level>> predicate = ctx -> {
		return ctx.origin().getY() == 128 & (ctx.origin().getZ() & 0xFF) == 0 & (ctx.origin().getX() & 0xFF) == 0;
	};

	public static void initialize() {
		RenderRegionBakeListener.register(predicate, listener);
	}
}
