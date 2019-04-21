package grondag.renderbender.init;

import static net.minecraft.block.BlockRenderLayer.SOLID;
import static net.minecraft.block.BlockRenderLayer.TRANSLUCENT;

import java.util.Random;
import java.util.function.Supplier;

import grondag.frex.api.RendererAccess;
import grondag.frex.api.material.RenderMaterial;
import grondag.frex.api.mesh.MutableQuadView;
import grondag.frex.api.render.TerrainBlockView;
import grondag.renderbender.init.BasicBlocks.BeTestBlockEntity;
import grondag.renderbender.model.MeshTransformer;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

class BeTestTransform implements MeshTransformer {
    static RenderMaterial matSolid = RendererAccess.INSTANCE.getRenderer().materialFinder()
            .blendMode(0, SOLID).find();
    
    static RenderMaterial matSolidGlow = RendererAccess.INSTANCE.getRenderer().materialFinder()
            .blendMode(0, SOLID).disableDiffuse(0, true).emissive(0, true).disableAo(0, true).find();
    
    static RenderMaterial matTrans = RendererAccess.INSTANCE.getRenderer().materialFinder()
            .blendMode(0, TRANSLUCENT).find();
    
    static RenderMaterial matTransGlow = RendererAccess.INSTANCE.getRenderer().materialFinder()
            .blendMode(0, TRANSLUCENT).disableDiffuse(0, true).emissive(0, true).disableAo(0, true).find();
    
    private RenderMaterial mat = null;
    private RenderMaterial matGlow = null;
    private int stupid[];
    private boolean translucent;

    @Override
    public boolean transform(MutableQuadView q) {
        final int s = stupid == null ? -1 : stupid[q.tag()];
        final int c = translucent ? 0x80000000 | (0xFFFFFF & s) : s;
        q.material((s & 0x3) == 0 ? matGlow : mat).spriteColor(0, c, c, c, c);
        return true;
    }
    
    @Override
    public MeshTransformer prepare(TerrainBlockView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier) {
        if(randomSupplier.get().nextInt(4) == 0) {
            mat = matTrans;
            matGlow = matTransGlow;
            translucent = true;
        } else {
            mat = matSolid;
            matGlow = matSolidGlow;
            translucent = false;
        }
        stupid = (int[])blockView.getCachedRenderData(pos);
        return this;
    }
    
    @Override
    public MeshTransformer prepare(ItemStack stack, Supplier<Random> randomSupplier) {
        mat = matSolid;
        matGlow = matSolidGlow;
        translucent = false;
        stupid = BeTestBlockEntity.ITEM_COLORS;
        return this;
    }
}