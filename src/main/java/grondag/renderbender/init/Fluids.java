package grondag.renderbender.init;

import io.vram.frex.api.model.FluidAppearance;
import io.vram.frex.api.model.FluidColorProvider;
import io.vram.frex.api.model.FluidSpriteProvider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FlowingFluid;

public class Fluids {
	public static final FlowingFluid TEST_FLUID = Registry.register(Registry.FLUID, new ResourceLocation("renderbender:test_fluid"), new TestFluid());

	public static void initialize() {
	}

	@Environment(EnvType.CLIENT)
	public static void initClient() {
		FluidAppearance.register(FluidColorProvider.of(0x88FFAABB), FluidSpriteProvider.WATER_SPRITES, TEST_FLUID);
	}
}