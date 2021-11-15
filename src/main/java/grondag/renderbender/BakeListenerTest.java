package grondag.renderbender;

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
