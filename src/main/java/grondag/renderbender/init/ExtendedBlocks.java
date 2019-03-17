package grondag.renderbender.init;

import static grondag.renderbender.init.BasicBlocks.ITEM_FUNCTION_STANDARD;
import static grondag.renderbender.init.BasicBlocks.register;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.client.render.block.BiomeColors;
import net.minecraft.client.render.block.FoliageColorHandler;

public class ExtendedBlocks {
    private static final Block LAYERS_BLOCK = new Block(FabricBlockSettings
            .of(Material.STONE).strength(1, 1)
            .materialColor(MaterialColor.FOLIAGE).build());

    private static final Block SHADER_BLOCK = new Block(FabricBlockSettings
            .of(Material.STONE).strength(1, 1)
            .materialColor(MaterialColor.CYAN).build());
    
    public static void initialize() {
        register(LAYERS_BLOCK, "layers", ITEM_FUNCTION_STANDARD);
        
        ColorProviderRegistry.BLOCK.register((blockState, extendedBlockView, pos, colorIndex) -> {
          return extendedBlockView != null && pos != null ? BiomeColors.foliageColorAt(extendedBlockView, pos) : FoliageColorHandler.getDefaultColor();
        }, LAYERS_BLOCK);
        
        ColorProviderRegistry.ITEM.register((stack, colorIndex) -> {
          return FoliageColorHandler.getDefaultColor();
        }, LAYERS_BLOCK);
        
        register(SHADER_BLOCK, "shader", ITEM_FUNCTION_STANDARD);
    }
}
