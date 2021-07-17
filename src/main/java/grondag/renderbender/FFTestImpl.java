package grondag.renderbender;

import java.util.function.Consumer;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

public class FFTestImpl {
	private int frameCounter;
	private boolean active = false;

	FFTestImpl(Consumer<Boolean> controller, int interval) {
		WorldRenderEvents.START.register(c -> {
			if((++frameCounter % interval) == 0) {
				active = !active;
				controller.accept(active);
			}
		});
	}
}
