package grondag.renderbender;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

import grondag.renderbender.init.BasicBlocks;

public class FrexEventTest {
	private static boolean active = true;
	private static boolean firstBlockOutline = true;

	static void init() {
		RenderBender.LOG.info("Registering FREX event test callbacks.");
		RenderBender.LOG.info("Should see the following messages 1X in this order when world is loaded.");
		RenderBender.LOG.info("    WorldRenderStart");
		RenderBender.LOG.info("    WorldRenderAfterSetup");
		RenderBender.LOG.info("    WorldRenderBeforeEntities");
		RenderBender.LOG.info("    WorldRenderAfterEntities");
		RenderBender.LOG.info("    WorldRenderBeforeDebugRender");
		RenderBender.LOG.info("    WorldRenderAfterTranslucent");
		RenderBender.LOG.info("    WorldRenderLast");
		RenderBender.LOG.info("    WorldRenderEnd");

		WorldRenderEvents.START.register(c -> {
			if (active) {
				RenderBender.LOG.info("WorldRenderStart");
			}
		});

		WorldRenderEvents.AFTER_SETUP.register(c -> {
			if (active) {
				RenderBender.LOG.info("WorldRenderAfterSetup");
			}
		});

		WorldRenderEvents.BEFORE_ENTITIES.register(c -> {
			if (active) {
				RenderBender.LOG.info("WorldRenderBeforeEntities");
			}
		});

		WorldRenderEvents.AFTER_ENTITIES.register(c -> {
			if (active) {
				RenderBender.LOG.info("WorldRenderAfterEntities");
			}
		});

		WorldRenderEvents.BEFORE_BLOCK_OUTLINE.register((c, h) -> {
			if (firstBlockOutline) {
				RenderBender.LOG.info("WorldRenderBeforeBlockOutline");
				firstBlockOutline = false;
			}

			return true;
		});

		WorldRenderEvents.BLOCK_OUTLINE.register((c, b) -> {
			return b.blockState().getBlock() != BasicBlocks.GLOW_BLOCK_DYNAMIC;
		});

		WorldRenderEvents.BEFORE_DEBUG_RENDER.register(c -> {
			if (active) {
				RenderBender.LOG.info("WorldRenderBeforeDebugRender");
			}
		});

		WorldRenderEvents.AFTER_TRANSLUCENT.register(c -> {
			if (active) {
				RenderBender.LOG.info("WorldRenderAfterTranslucent");
			}
		});

		WorldRenderEvents.LAST.register(c -> {
			if (active) {
				RenderBender.LOG.info("WorldRenderLast");
			}
		});

		WorldRenderEvents.END.register(c -> {
			if (active) {
				RenderBender.LOG.info("WorldRenderEnd");
				active = false;
			}
		});
	}
}
