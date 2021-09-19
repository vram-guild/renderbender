package grondag.renderbender.init;

import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;

public class Materials {
	public static final Material TEST_FLUID = new FabricMaterialBuilder(MaterialColor.COLOR_LIGHT_BLUE).lightPassesThrough().noCollider().nonSolid().replaceable().liquid().build();
}