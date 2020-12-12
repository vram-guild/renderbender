package grondag.renderbender.init;

import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;

public class Fluids {
	public static final FlowableFluid TEST_FLUID = Registry.register(Registry.FLUID, new Identifier("renderbender:test_fluid"), new TestFluid());

	public static void initialize() {
	}

	@Environment(EnvType.CLIENT)
	public static void initClient() {
		FluidRenderHandlerRegistry.INSTANCE.register(TEST_FLUID, new TestFluid.Renderer());
	}
}