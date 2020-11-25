package grondag.renderbender;

import grondag.frex.api.event.WorldRenderEvents;
import grondag.renderbender.init.BasicBlocks;

public class FrexEventTest {
	private static boolean active = true;

	static void init() {
		RenderBender.LOG.info("Registering FREX event test callbacks.");
		RenderBender.LOG.info("Should see the following messages 1X in this order when world is loaded.");
		RenderBender.LOG.info("    WorldRenderStartCallback");
		RenderBender.LOG.info("    WorldRenderPostSetupCallback");
		RenderBender.LOG.info("    WorldRenderPreEntityCallback");
		RenderBender.LOG.info("    WorldRenderPostEntityCallback");
		RenderBender.LOG.info("    WorldRenderDebugRenderCallback");
		RenderBender.LOG.info("    WorldRenderPostTranslucentCallback");
		RenderBender.LOG.info("    WorldRenderLastCallback");
		RenderBender.LOG.info("    WorldRenderEndCallback");

		WorldRenderEvents.START.register(c -> {
			if (active) {
				RenderBender.LOG.info("WorldRenderStartCallback");
			}
		});

		WorldRenderEvents.AFTER_SETUP.register(c -> {
			if (active) {
				RenderBender.LOG.info("WorldRenderPostSetupCallback");
			}
		});

		WorldRenderEvents.BEFORE_ENTITIES.register(c -> {
			if (active) {
				RenderBender.LOG.info("WorldRenderPreEntityCallback");
			}
		});

		WorldRenderEvents.AFTER_ENTITIES.register(c -> {
			if (active) {
				RenderBender.LOG.info("WorldRenderPostEntityCallback");
			}
		});

		WorldRenderEvents.BLOCK_OUTLINE.register(c -> {
			if (c.blockState().getBlock() == BasicBlocks.GLOW_BLOCK_DYNAMIC) {
            	c.cancelDefaultBlockOutline();
			}
		});

		WorldRenderEvents.BEFORE_DEBUG_RENDER.register(c -> {
			if (active) {
				RenderBender.LOG.info("WorldRenderDebugRenderCallback");
			}
		});

		WorldRenderEvents.AFTER_TRANSLUCENT.register(c -> {
			if (active) {
				RenderBender.LOG.info("WorldRenderPostTranslucentCallback");
			}
		});

		WorldRenderEvents.LAST.register(c -> {
			if (active) {
				RenderBender.LOG.info("WorldRenderLastCallback");
			}
		});

		WorldRenderEvents.END.register(c -> {
			if (active) {
				RenderBender.LOG.info("WorldRenderEndCallback");
				active = false;
			}
		});
	}
}
