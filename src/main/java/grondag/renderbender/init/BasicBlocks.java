package grondag.renderbender.init;

import java.util.Random;
import java.util.function.Function;

import io.netty.util.internal.ThreadLocalRandom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachmentBlockEntity;

import grondag.renderbender.model.ModelBuilder;

public class BasicBlocks {
	public static Item register(Block block, String name, Function<Block, Item> itemFunc) {
		final Identifier id = new Identifier("renderbender", name);
		Registry.register(Registry.BLOCK, id, block);
		final Item result = itemFunc.apply(block);
		Registry.register(Registry.ITEM, id, result);
		return result;
	}

	public static final Function<Block, Item> ITEM_FUNCTION_STANDARD = block -> {
		return new BlockItem(block, new Item.Settings()
				.maxCount(64)
				.group(ItemGroup.BUILDING_BLOCKS));
	};

	public static final Function<Block, Item> ITEM_FUNCTION_ENCHANTED = block -> {
		return new BlockItem(block, new Item.Settings()
				.maxCount(64)
				.group(ItemGroup.BUILDING_BLOCKS)) {
			@Override
			public boolean hasGlint(ItemStack itemStack_1) {
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

		Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("renderbender", "be_test"), BE_TEST_TYPE);
	}

	public static final Block ITEM_TRANSFORM = new Block(FabricBlockSettings.of(Material.STONE).strength(1, 1));
	public static final Block GLOW_BLOCK = new Block(FabricBlockSettings.of(Material.STONE).strength(1, 1));
	public static final Block GLOW_BLOCK_SHADED = new Block(FabricBlockSettings.of(Material.STONE).strength(1, 1));
	public static final Block GLOW_BLOCK_DIFFUSE = new Block(FabricBlockSettings.of(Material.STONE).strength(1, 1));
	public static final Block GLOW_BLOCK_AO = new Block(FabricBlockSettings.of(Material.STONE).strength(1, 1));
	public static final Block GLOW_BLOCK_DYNAMIC = new Block(FabricBlockSettings.of(Material.STONE).strength(1, 1));

	public static final Block AO_TEST = new Block(FabricBlockSettings.of(Material.STONE).dynamicBounds().strength(1, 1)) {
		@Override
		public VoxelShape getOutlineShape(BlockState blockState, BlockView blockView, BlockPos pos, ShapeContext entityContext) {
			final float height = (1 + (pos.hashCode() & 15)) / 16f;
			return VoxelShapes.cuboid(0, 0, 0, 1, height, 1);
		}
	};

	public static final Block SHADE_TEST = new Block(FabricBlockSettings.of(Material.STONE).strength(1, 1)) {
		@Override
		public VoxelShape getOutlineShape(BlockState blockState, BlockView blockView, BlockPos pos, ShapeContext entityContext) {
			return VoxelShapes.cuboid(1f/16f, 1f/16f, 1f/16f, 15f/16f, 15f/16f, 15f/16f);
		}
		@Override
		public int getOpacity(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
			return 1;
		}
		@Override
		public float getAmbientOcclusionLightLevel(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
			return .4f;
		}
	};

	public static final VoxelShape ROUND_SHAPE = Block.createCuboidShape(1.0D, 1.0D, 1.0D, 14.0D, 14.0D, 15.0D);

	private static class RoundBlock extends Block {
		public RoundBlock(Settings settings) {
			super(settings);
		}

		@Override
		public VoxelShape getOutlineShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, ShapeContext entityContext) {
			return ROUND_SHAPE;
		}
		@Override
		public int getOpacity(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
			return 1;
		}
		@Override
		public float getAmbientOcclusionLightLevel(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
			return .5f;
		}
	}

	public static final Block ROUND_BLOCK_HARD = new RoundBlock(FabricBlockSettings.of(Material.STONE).strength(1, 1));
	public static final Block ROUND_BLOCK_HARD_AO = new RoundBlock(FabricBlockSettings.of(Material.STONE).strength(1, 1));
	public static final Block ROUND_BLOCK_HARD_DIFFUSE = new RoundBlock(FabricBlockSettings.of(Material.STONE).strength(1, 1));
	public static final Block ROUND_BLOCK_HARD_DIFFUSE_GLOW = new RoundBlock(FabricBlockSettings.of(Material.STONE).strength(1, 1));

	public static final Block ROUND_BLOCK_SOFT = new RoundBlock(FabricBlockSettings.of(Material.STONE).strength(1, 1));
	public static final Block ROUND_BLOCK_SOFT_AO = new RoundBlock(FabricBlockSettings.of(Material.STONE).strength(1, 1));
	public static final Block ROUND_BLOCK_SOFT_DIFFUSE = new RoundBlock(FabricBlockSettings.of(Material.STONE).strength(1, 1));
	public static final Block ROUND_BLOCK_SOFT_DIFFUSE_GLOW = new RoundBlock(FabricBlockSettings.of(Material.STONE).strength(1, 1));

	public static Block BE_TEST_BLOCK = new BeTestBlock();
	public static final BlockEntityType<BasicBlocks.BeTestBlockEntity> BE_TEST_TYPE = BlockEntityType.Builder.create(BasicBlocks.BeTestBlockEntity::new, BE_TEST_BLOCK).build(null);

	public static class BeTestBlock extends Block implements BlockEntityProvider {
		public BeTestBlock() {
			super(FabricBlockSettings.of(Material.STONE).dynamicBounds().strength(1, 1));
		}

		@Override
		public BlockEntity createBlockEntity(BlockView blockView) {
			return new BeTestBlockEntity();
		}

		@Override
		public VoxelShape getOutlineShape(BlockState blockState, BlockView blockView, BlockPos pos, ShapeContext entityContext) {
			return VoxelShapes.cuboid(1f/16f, 1f/16f, 1f/16f, 15f/16f, 15f/16f, 15f/16f);
		}
		@Override
		public int getOpacity(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
			return 1;
		}
		@Override
		public float getAmbientOcclusionLightLevel(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
			return .4f;
		}
	}

	public static class BeTestBlockEntity extends BlockEntity implements RenderAttachmentBlockEntity {


		static final int QUAD_COUNT = 6 * 14 * 14;
		private final int[] colors = new int[QUAD_COUNT];

		static final int[] ITEM_COLORS;

		static {
			ITEM_COLORS = new int[QUAD_COUNT];
			genColors(ITEM_COLORS);
		}

		public BeTestBlockEntity() {
			super(BE_TEST_TYPE);
			genColors(colors);
		}

		static void genColors(int[] data) {
			final Random r = ThreadLocalRandom.current();
			for(int i = 0; i < QUAD_COUNT; i++) {
				data[i] = ModelBuilder.randomPastelColor(r);
			}
		}

		@Override
		public Object getRenderAttachmentData() {
			return colors;
		}
	}
}
