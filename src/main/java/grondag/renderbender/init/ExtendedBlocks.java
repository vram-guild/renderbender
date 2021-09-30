package grondag.renderbender.init;

import static grondag.renderbender.init.BasicBlocks.ITEM_FUNCTION_STANDARD;
import static grondag.renderbender.init.BasicBlocks.register;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

public class ExtendedBlocks {
	private static final Block LAYERS_BLOCK = new Block(FabricBlockSettings
			.of(Material.STONE).strength(1, 1)
			.color(MaterialColor.COLOR_GREEN)
			.isValidSpawn((s,v,p,o) -> false)
			.isRedstoneConductor((s,v,p) -> false)
			.isSuffocating((s,v,p) -> false));

	private static final Block SHADER_BLOCK = new Block(FabricBlockSettings
			.of(Material.STONE).strength(1, 1)
			.color(MaterialColor.COLOR_CYAN)
			.isValidSpawn((s,v,p,o) -> false)
			.isRedstoneConductor((s,v,p) -> false)
			.isSuffocating((s,v,p) -> false));

	private static final Block CONDITIONAL_BLOCK = new Block(FabricBlockSettings
			.of(Material.GLASS).strength(1, 1)
			.color(MaterialColor.COLOR_CYAN)
			.noCollission()
			.isValidSpawn((s,v,p,o) -> false)
			.isRedstoneConductor((s,v,p) -> false)
			.isSuffocating((s,v,p) -> false)) {

		@Environment(EnvType.CLIENT)
		@Override
		public boolean skipRendering(BlockState blockState_1, BlockState blockState_2, Direction direction_1) {
			return blockState_2.getBlock() == this ? true : super.skipRendering(blockState_1, blockState_2, direction_1);
		}

		@Override
		public boolean propagatesSkylightDown(BlockState blockState_1, BlockGetter blockView_1, BlockPos blockPos_1) {
			return true;
		}

		@Override
		public int getLightBlock(BlockState blockState_1, BlockGetter blockView_1, BlockPos blockPos_1) {
			return 0;
		}
	};

	public static Item CONDITION_ITEM;

	public static void initialize() {
		register(LAYERS_BLOCK, "layers", ITEM_FUNCTION_STANDARD);

		// WIP: remove fabric dep
		ColorProviderRegistry.BLOCK.register((blockState, extendedBlockView, pos, colorIndex) -> {
			return extendedBlockView != null && pos != null ? BiomeColors.getAverageFoliageColor(extendedBlockView, pos) : FoliageColor.getDefaultColor();
		}, LAYERS_BLOCK);

		ColorProviderRegistry.ITEM.register((stack, colorIndex) -> {
			return FoliageColor.getDefaultColor();
		}, LAYERS_BLOCK);

		register(SHADER_BLOCK, "shader", ITEM_FUNCTION_STANDARD);

		CONDITION_ITEM = register(CONDITIONAL_BLOCK, "conditional", ITEM_FUNCTION_STANDARD);
	}
}
