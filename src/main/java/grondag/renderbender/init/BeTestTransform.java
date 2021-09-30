package grondag.renderbender.init;


import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import io.vram.frex.api.material.MaterialConstants;
import io.vram.frex.api.material.RenderMaterial;
import io.vram.frex.api.mesh.QuadEditor;
import io.vram.frex.api.model.ModelRenderContext;
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
    public boolean transform(QuadEditor q) {
        final int s = stupid == null ? -1 : stupid[q.tag()];
        final int c = translucent ? 0x80000000 | (0xFFFFFF & s) : s;
        q.material((s & 0x3) == 0 ? matGlow : mat).vertexColor(c, c, c, c);
        return true;
    }

    @Override
    public MeshTransformer prepare(BlockAndTintGetter blockView, BlockState state, BlockPos pos, ModelRenderContext context) {
        if(context.random().nextInt(4) == 0) {
            mat = matTrans;
            matGlow = matTransGlow;
            translucent = true;
        } else {
            mat = matSolid;
            matGlow = matSolidGlow;
            translucent = false;
        }
        stupid = (int[]) context.blockEntityRenderData(pos);
        return this;
    }

    @Override
    public MeshTransformer prepare(ItemStack stack, ModelRenderContext context) {
        mat = matSolid;
        matGlow = matSolidGlow;
        translucent = false;
        stupid = BeTestBlockEntity.ITEM_COLORS;
        return this;
    }
}