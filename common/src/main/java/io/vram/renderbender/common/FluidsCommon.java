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

package io.vram.renderbender.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;

public class FluidsCommon {
	public static final Material TEST_FLUID_MATERIAL = new Material(MaterialColor.COLOR_LIGHT_BLUE, true, false, false, false, false, true, PushReaction.DESTROY);
	public static final FlowingFluid TEST_FLUID = Registry.register(Registry.FLUID, new ResourceLocation("renderbender:test_fluid"), new TestFluid());
	public static final LiquidBlock TEST_FLUID_BLOCK = new LiquidBlock(FluidsCommon.TEST_FLUID, BlockBehaviour.Properties.of(TEST_FLUID_MATERIAL).noCollission().strength(100.0F).noLootTable()) { };

	static void initialize() {
	}

	public static class TestFluid extends FlowingFluid {
		@Override
		public Fluid getFlowing() {
			return FluidsCommon.TEST_FLUID;
		}

		@Override
		public Fluid getSource() {
			return FluidsCommon.TEST_FLUID;
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
			return FluidsCommon.TEST_FLUID_BLOCK.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
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
}
