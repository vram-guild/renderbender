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

package grondag.renderbender.init;

import java.util.Random;
import java.util.function.Function;

import io.netty.util.internal.ThreadLocalRandom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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

import grondag.renderbender.model.ModelBuilder;

public class BasicBlocks {
	public static Item register(Block block, String name, Function<Block, Item> itemFunc) {
		final ResourceLocation id = new ResourceLocation("renderbender", name);
		Registry.register(Registry.BLOCK, id, block);
		final Item result = itemFunc.apply(block);
		Registry.register(Registry.ITEM, id, result);
		return result;
	}

	public static final Function<Block, Item> ITEM_FUNCTION_STANDARD = block -> {
		return new BlockItem(block, new Item.Properties()
				.stacksTo(64)
				.tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
	};

	public static final Function<Block, Item> ITEM_FUNCTION_ENCHANTED = block -> {
		return new BlockItem(block, new Item.Properties()
				.stacksTo(64)
				.tab(CreativeModeTab.TAB_BUILDING_BLOCKS)) {
			@Override
			public boolean isFoil(ItemStack itemStack_1) {
				return true;
			};
		};
	};

	public static void initialize() {
		register(ITEM_TRANSFORM, "item_transform", ITEM_FUNCTION_STANDARD);
		register(GLOW_BLOCK, "glow", ITEM_FUNCTION_STANDARD);
		register(GLOW_BLOCK_DIFFUSE, "glow_diffuse", ITEM_FUNCTION_STANDARD);
		register(GLOW_BLOCK_AO, "glow_ao", ITEM_FUNCTION_STANDARD);
		register(GLOW_BLOCK_SHADED, "glow_shaded", ITEM_FUNCTION_STANDARD);
		register(GLOW_BLOCK_DYNAMIC, "glow_dynamic", ITEM_FUNCTION_STANDARD);
		register(AO_TEST, "ao_test", ITEM_FUNCTION_STANDARD);
		register(SHADE_TEST, "shade_test", ITEM_FUNCTION_STANDARD);
		register(ROUND_BLOCK_HARD, "round_hard", ITEM_FUNCTION_ENCHANTED);
		register(ROUND_BLOCK_HARD_AO, "round_hard_ao", ITEM_FUNCTION_ENCHANTED);
		register(ROUND_BLOCK_HARD_DIFFUSE, "round_hard_diffuse", ITEM_FUNCTION_ENCHANTED);
		register(ROUND_BLOCK_HARD_DIFFUSE_GLOW, "round_hard_diffuse_glow", ITEM_FUNCTION_ENCHANTED);
		register(ROUND_BLOCK_SOFT, "round_soft", ITEM_FUNCTION_ENCHANTED);
		register(ROUND_BLOCK_SOFT_AO, "round_soft_ao", ITEM_FUNCTION_ENCHANTED);
		register(ROUND_BLOCK_SOFT_DIFFUSE, "round_soft_diffuse", ITEM_FUNCTION_ENCHANTED);
		register(ROUND_BLOCK_SOFT_DIFFUSE_GLOW, "round_soft_diffuse_glow", ITEM_FUNCTION_ENCHANTED);
		register(BE_TEST_BLOCK, "be_test", ITEM_FUNCTION_STANDARD);

		Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation("renderbender", "be_test"), BE_TEST_TYPE);
	}

	public static final Block ITEM_TRANSFORM = new Block(Properties.of(Material.STONE).strength(1, 1));
	public static final Block GLOW_BLOCK = new Block(Properties.of(Material.STONE).strength(1, 1));
	public static final Block GLOW_BLOCK_SHADED = new Block(Properties.of(Material.STONE).strength(1, 1));
	public static final Block GLOW_BLOCK_DIFFUSE = new Block(Properties.of(Material.STONE).strength(1, 1));
	public static final Block GLOW_BLOCK_AO = new Block(Properties.of(Material.STONE).strength(1, 1));
	public static final Block GLOW_BLOCK_DYNAMIC = new Block(Properties.of(Material.STONE).strength(1, 1));

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

	public static final VoxelShape ROUND_SHAPE = Block.box(1.0D, 1.0D, 1.0D, 14.0D, 14.0D, 15.0D);

	private static class RoundBlock extends Block {
		RoundBlock(Properties settings) {
			super(settings);
		}

		@Override
		public VoxelShape getShape(BlockState blockState_1, BlockGetter blockView_1, BlockPos blockPos_1, CollisionContext entityContext) {
			return ROUND_SHAPE;
		}

		@Override
		public int getLightBlock(BlockState blockState_1, BlockGetter blockView_1, BlockPos blockPos_1) {
			return 1;
		}

		@Override
		public float getShadeBrightness(BlockState blockState_1, BlockGetter blockView_1, BlockPos blockPos_1) {
			return .5f;
		}
	}

	public static final Block ROUND_BLOCK_HARD = new RoundBlock(Properties.of(Material.STONE).strength(1, 1));
	public static final Block ROUND_BLOCK_HARD_AO = new RoundBlock(Properties.of(Material.STONE).strength(1, 1));
	public static final Block ROUND_BLOCK_HARD_DIFFUSE = new RoundBlock(Properties.of(Material.STONE).strength(1, 1));
	public static final Block ROUND_BLOCK_HARD_DIFFUSE_GLOW = new RoundBlock(Properties.of(Material.STONE).strength(1, 1));

	public static final Block ROUND_BLOCK_SOFT = new RoundBlock(Properties.of(Material.STONE).strength(1, 1));
	public static final Block ROUND_BLOCK_SOFT_AO = new RoundBlock(Properties.of(Material.STONE).strength(1, 1));
	public static final Block ROUND_BLOCK_SOFT_DIFFUSE = new RoundBlock(Properties.of(Material.STONE).strength(1, 1));
	public static final Block ROUND_BLOCK_SOFT_DIFFUSE_GLOW = new RoundBlock(Properties.of(Material.STONE).strength(1, 1));

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
				data[i] = ModelBuilder.randomPastelColor(r);
			}
		}

		public Object getRenderData() {
			return colors;
		}
	}
}
