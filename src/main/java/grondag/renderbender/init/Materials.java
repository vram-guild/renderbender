package grondag.renderbender.init;

import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;

public class Materials {
	public static final Material TEST_FLUID = new FabricMaterialBuilder(MaterialColor.LIGHT_BLUE).allowsMovement().lightPassesThrough().notSolid().replaceable().liquid().build();
}