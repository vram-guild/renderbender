package grondag.renderbender.init;

import net.minecraft.block.MapColor;
import net.minecraft.block.Material;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;

public class Materials {
	public static final Material TEST_FLUID = new FabricMaterialBuilder(MapColor.LIGHT_BLUE).allowsMovement().lightPassesThrough().notSolid().replaceable().liquid().build();
}