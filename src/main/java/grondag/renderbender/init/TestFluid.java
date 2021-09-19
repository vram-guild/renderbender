package grondag.renderbender.init;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public class TestFluid extends FlowingFluid {
	@Override
	public Fluid getFlowing() {
		return Fluids.TEST_FLUID;
	}

	@Override
	public Fluid getSource() {
		return Fluids.TEST_FLUID;
	}

	@Override
	protected boolean canConvertToSource() {
		return true;
	}

	@Override
	protected void beforeDestroyingBlock(LevelAccessor world, BlockPos pos, BlockState state) {
		final BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
		Block.dropResources(state, world, pos, blockEntity);
	}

	@Override
	protected int getSlopeFindDistance(LevelReader world) {
		return 10;
	}

	@Override
	protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
		super.createFluidStateDefinition(builder);
		builder.add(LEVEL);
	}

	@Override
	public int getDropOff(LevelReader world) {
		return 1;
	}

	@Override
	public Item getBucket() {
		return Items.AIR;
	}

	@Override
	protected boolean canBeReplacedWith(FluidState state, BlockGetter world, BlockPos pos, Fluid fluid, Direction direction) {
		return true;
	}

	@Override
	public int getTickDelay(LevelReader world) {
		return 5;
	}

	@Override
	protected float getExplosionResistance() {
		return 100F;
	}

	@Override
	protected BlockState createLegacyBlock(FluidState state) {
		return BasicBlocks.TEST_FLUID.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
	}

	@Override
	public boolean isSource(FluidState state) {
		return true;
	}

	@Override
	public int getAmount(FluidState state) {
		return 8;
	}
}
