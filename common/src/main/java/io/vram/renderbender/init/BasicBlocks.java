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

package io.vram.renderbender.init;

import java.util.Random;

import io.netty.util.internal.ThreadLocalRandom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import io.vram.renderbender.RenderBender;
import io.vram.renderbender.client.Dynamic;

public class BasicBlocks {
	public static void initialize() {
		RenderBender.registerBlock(ITEM_TRANSFORM, "item_transform", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(AO_TEST, "ao_test", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(SHADE_TEST, "shade_test", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(BE_TEST_BLOCK, "be_test", RenderBender.ITEM_FACTORY_STANDARD);

		Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation("renderbender", "be_test"), BE_TEST_TYPE);
	}

	public static final Block ITEM_TRANSFORM = new Block(Properties.of(Material.STONE).strength(1, 1));

	public static final Block AO_TEST = new Block(Properties.of(Material.STONE).dynamicShape().strength(1, 1)) {
		@Override
		public VoxelShape getShape(BlockState blockState, BlockGetter blockView, BlockPos pos, CollisionContext entityContext) {
			final float height = (1 + (pos.hashCode() & 15)) / 16f;
			return Shapes.box(0, 0, 0, 1, height, 1);
		}
	};

	public static final Block SHADE_TEST = new Block(Properties.of(Material.STONE).strength(1, 1)) {
		@Override
		public VoxelShape getShape(BlockState blockState, BlockGetter blockView, BlockPos pos, CollisionContext entityContext) {
			return Shapes.box(1f/16f, 1f/16f, 1f/16f, 15f/16f, 15f/16f, 15f/16f);
		}

		@Override
		public int getLightBlock(BlockState blockState_1, BlockGetter blockView_1, BlockPos blockPos_1) {
			return 1;
		}

		@Override
		public float getShadeBrightness(BlockState blockState_1, BlockGetter blockView_1, BlockPos blockPos_1) {
			return .4f;
		}
	};

	public static Block BE_TEST_BLOCK = new BeTestBlock();
	public static final BlockEntityType<BasicBlocks.BeTestBlockEntity> BE_TEST_TYPE = BlockEntityType.Builder.of(BasicBlocks.BeTestBlockEntity::new, BE_TEST_BLOCK).build(null);

	public static class BeTestBlock extends Block implements EntityBlock {
		public BeTestBlock() {
			super(Properties.of(Material.STONE).dynamicShape().strength(1, 1));
		}

		@Override
		public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
			return new BeTestBlockEntity(pos, state);
		}

		@Override
		public VoxelShape getShape(BlockState blockState, BlockGetter blockView, BlockPos pos, CollisionContext entityContext) {
			return Shapes.box(1f/16f, 1f/16f, 1f/16f, 15f/16f, 15f/16f, 15f/16f);
		}

		@Override
		public int getLightBlock(BlockState blockState_1, BlockGetter blockView_1, BlockPos blockPos_1) {
			return 1;
		}

		@Override
		public float getShadeBrightness(BlockState blockState_1, BlockGetter blockView_1, BlockPos blockPos_1) {
			return .4f;
		}
	}

	public static class BeTestBlockEntity extends BlockEntity {
		static final int QUAD_COUNT = 6 * 14 * 14;
		private final int[] colors = new int[QUAD_COUNT];

		static final int[] ITEM_COLORS;

		static {
			ITEM_COLORS = new int[QUAD_COUNT];
			genColors(ITEM_COLORS);
		}

		public BeTestBlockEntity(BlockPos pos, BlockState state) {
			super(BE_TEST_TYPE, pos, state);
			genColors(colors);
		}

		static void genColors(int[] data) {
			final Random r = ThreadLocalRandom.current();

			for (int i = 0; i < QUAD_COUNT; i++) {
				data[i] = Dynamic.randomPastelColor(r);
			}
		}

		public Object getRenderData() {
			return colors;
		}
	}
}
