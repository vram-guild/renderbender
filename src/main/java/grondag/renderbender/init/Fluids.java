package grondag.renderbender.init;

import io.vram.frex.api.model.FluidAppearance;
import io.vram.frex.api.model.FluidColorProvider;
import io.vram.frex.api.model.FluidSpriteProvider;

import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class Fluids {
	public static final FlowableFluid TEST_FLUID = Registry.register(Registry.FLUID, new Identifier("renderbender:test_fluid"), new TestFluid());

	public static void initialize() {
	}

	@Environment(EnvType.CLIENT)
	public static void initClient() {
		FluidAppearance.register(TEST_FLUID, FluidColorProvider.of(0x88FFAABB), FluidSpriteProvider.WATER_SPRITES);
	}
}