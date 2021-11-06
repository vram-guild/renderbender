package grondag.renderbender.init;


import io.vram.frex.api.buffer.QuadTransform;
import io.vram.frex.api.material.MaterialConstants;
import io.vram.frex.api.material.MaterialFinder;
import io.vram.frex.api.material.RenderMaterial;
import io.vram.frex.api.model.BlockModel.BlockInputContext;
import io.vram.frex.api.model.InputContext.Type;

import grondag.renderbender.init.BasicBlocks.BeTestBlockEntity;

class BeTestTransform  {
	static RenderMaterial matSolid = MaterialFinder.threadLocal()
			.preset(MaterialConstants.PRESET_SOLID).find();

	static RenderMaterial matSolidGlow = MaterialFinder.threadLocal()
			.preset(MaterialConstants.PRESET_SOLID).disableDiffuse(true).emissive(true).disableAo(true).find();

	static RenderMaterial matTrans = MaterialFinder.threadLocal()
			.preset(MaterialConstants.PRESET_TRANSLUCENT).find();

	static RenderMaterial matTransGlow = MaterialFinder.threadLocal()
			.preset(MaterialConstants.PRESET_TRANSLUCENT).disableDiffuse(true).emissive(true).disableAo(true).find();


	public static final QuadTransform INSTANCE = (ctx, in, out) -> {
		RenderMaterial mat = null;
		RenderMaterial matGlow = null;
		int stupid[] = null;
		boolean translucent;

		if (ctx.type() == Type.BLOCK) {
			final var bctx = (BlockInputContext) ctx;
			stupid = (int[]) bctx.blockEntityRenderData(bctx.pos());
		}

		if (stupid == null) {
			mat = matSolid;
			matGlow = matSolidGlow;
			translucent = false;
			stupid = BeTestBlockEntity.ITEM_COLORS;
		} else {
			if(ctx.random().nextInt(4) == 0) {
				mat = matTrans;
				matGlow = matTransGlow;
				translucent = true;
			} else {
				mat = matSolid;
				matGlow = matSolidGlow;
				translucent = false;
			}
		}

		in.copyTo(out);
		final int s = stupid == null ? -1 : stupid[out.tag()];
		final int c = translucent ? 0x80000000 | (0xFFFFFF & s) : s;
		out.material((s & 0x3) == 0 ? matGlow : mat).vertexColor(c, c, c, c);
		out.emit();
	};
}