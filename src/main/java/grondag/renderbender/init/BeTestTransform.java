package grondag.renderbender.init;


import io.vram.frex.api.material.MaterialConstants;
import io.vram.frex.api.material.RenderMaterial;
import io.vram.frex.api.mesh.QuadEmitter;
import io.vram.frex.api.model.BlockModel.BlockInputContext;
import io.vram.frex.api.model.ItemModel.ItemInputContext;
import io.vram.frex.api.model.ModelOuputContext;
import io.vram.frex.api.renderer.Renderer;

import grondag.renderbender.init.BasicBlocks.BeTestBlockEntity;
import grondag.renderbender.model.MeshTransformer;

class BeTestTransform implements MeshTransformer {
    static RenderMaterial matSolid = Renderer.get().materialFinder()
            .preset(MaterialConstants.PRESET_SOLID).find();

    static RenderMaterial matSolidGlow = Renderer.get().materialFinder()
            .preset(MaterialConstants.PRESET_SOLID).disableDiffuse(true).emissive(true).disableAo(true).find();

    static RenderMaterial matTrans = Renderer.get().materialFinder()
            .preset(MaterialConstants.PRESET_TRANSLUCENT).find();

    static RenderMaterial matTransGlow = Renderer.get().materialFinder()
            .preset(MaterialConstants.PRESET_TRANSLUCENT).disableDiffuse(true).emissive(true).disableAo(true).find();

    private RenderMaterial mat = null;
    private RenderMaterial matGlow = null;
    private int stupid[];
    private boolean translucent;

    @Override
    public boolean transform(QuadEmitter q) {
        final int s = stupid == null ? -1 : stupid[q.tag()];
        final int c = translucent ? 0x80000000 | (0xFFFFFF & s) : s;
        q.material((s & 0x3) == 0 ? matGlow : mat).vertexColor(c, c, c, c);
        return true;
    }

    @Override
    public MeshTransformer prepare(BlockInputContext input, ModelOuputContext context) {
        if(input.random().nextInt(4) == 0) {
            mat = matTrans;
            matGlow = matTransGlow;
            translucent = true;
        } else {
            mat = matSolid;
            matGlow = matSolidGlow;
            translucent = false;
        }
        stupid = (int[]) input.blockEntityRenderData(input.pos());
        return this;
    }

    @Override
    public MeshTransformer prepare(ItemInputContext input, ModelOuputContext context) {
        mat = matSolid;
        matGlow = matSolidGlow;
        translucent = false;
        stupid = BeTestBlockEntity.ITEM_COLORS;
        return this;
    }
}