package grondag.renderbender.init;


import io.vram.frex.api.buffer.QuadTransform;
import io.vram.frex.api.material.MaterialConstants;
import io.vram.frex.api.material.RenderMaterial;
import io.vram.frex.api.model.BlockModel.BlockInputContext;
import io.vram.frex.api.model.InputContext.Type;
import io.vram.frex.api.renderer.Renderer;

import grondag.renderbender.init.BasicBlocks.BeTestBlockEntity;

class BeTestTransform  {
	static RenderMaterial matSolid = Renderer.get().materialFinder()
			.preset(MaterialConstants.PRESET_SOLID).find();

	static RenderMaterial matSolidGlow = Renderer.get().materialFinder()
			.preset(MaterialConstants.PRESET_SOLID).disableDiffuse(true).emissive(true).disableAo(true).find();

	static RenderMaterial matTrans = Renderer.get().materialFinder()
			.preset(MaterialConstants.PRESET_TRANSLUCENT).find();

	static RenderMaterial matTransGlow = Renderer.get().materialFinder()
			.preset(MaterialConstants.PRESET_TRANSLUCENT).disableDiffuse(true).emissive(true).disableAo(true).find();


	public static final QuadTransform INSTANCE = (ctx, in, out) -> {
		RenderMaterial mat = null;
		RenderMaterial matGlow = null;
		int stupid[];
		boolean translucent;

		if (ctx.type() == Type.BLOCK) {
			if(ctx.random().nextInt(4) == 0) {
				mat = matTrans;
				matGlow = matTransGlow;
				translucent = true;
			} else {
				mat = matSolid;
				matGlow = matSolidGlow;
				translucent = false;
			}

			final var bctx = (BlockInputContext) ctx;
			stupid = (int[]) bctx.blockEntityRenderData(bctx.pos());
		} else {
			mat = matSolid;
			matGlow = matSolidGlow;
			translucent = false;
			stupid = BeTestBlockEntity.ITEM_COLORS;
		}

		in.copyTo(out);
		final int s = stupid == null ? -1 : stupid[out.tag()];
		final int c = translucent ? 0x80000000 | (0xFFFFFF & s) : s;
		out.material((s & 0x3) == 0 ? matGlow : mat).vertexColor(c, c, c, c);
		out.emit();
	};
}