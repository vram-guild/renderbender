package grondag.renderbender.init;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.util.Lazy;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;

public class TestFluid extends FlowableFluid {
	@Override
	public Fluid getFlowing() {
		return Fluids.TEST_FLUID;
	}

	@Override
	public Fluid getStill() {
		return Fluids.TEST_FLUID;
	}

	@Override
	protected boolean isInfinite() {
		return true;
	}

	@Override
	protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
		final BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
		Block.dropStacks(state, world, pos, blockEntity);
	}

	@Override
	protected int getFlowSpeed(WorldView world) {
		return 10;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
		super.appendProperties(builder);
		builder.add(LEVEL);
	}

	@Override
	public int getLevelDecreasePerBlock(WorldView world) {
		return 1;
	}

	@Override
	public Item getBucketItem() {
		return Items.AIR;
	}

	@Override
	protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
		return true;
	}

	@Override
	public int getTickRate(WorldView world) {
		return 5;
	}

	@Override
	protected float getBlastResistance() {
		return 100F;
	}

	@Override
	protected BlockState toBlockState(FluidState state) {
		return BasicBlocks.TEST_FLUID.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
	}

	@Override
	public boolean isStill(FluidState state) {
		return true;
	}

	@Override
	public int getLevel(FluidState state) {
		return 8;
	}

	public static class Renderer implements FluidRenderHandler {
		private static final Lazy<Sprite[]> SPRITES = new Lazy<>(() ->
			new Sprite[] {
				ModelLoader.WATER_OVERLAY.getSprite(),
				ModelLoader.WATER_OVERLAY.getSprite()
			}
		);

		@Override
		public int getFluidColor(@Nullable BlockRenderView view, @Nullable BlockPos pos, FluidState state) {
			return 0x887FAAFF;
		}

		@Override
		public Sprite[] getFluidSprites(@Nullable BlockRenderView blockRenderView, @Nullable BlockPos blockPos, FluidState fluidState) {
			return SPRITES.get();
		}
	}
}
