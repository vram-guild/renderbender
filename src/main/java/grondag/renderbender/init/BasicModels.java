package grondag.renderbender.init;

import static net.minecraft.block.BlockRenderLayer.SOLID;
import static net.minecraft.block.BlockRenderLayer.TRANSLUCENT;

import java.util.HashMap;
import java.util.Random;
import java.util.function.Supplier;

import grondag.renderbender.ModelMeshBuilder;
import grondag.renderbender.model.DynamicRenderer;
import grondag.renderbender.model.MeshTransformer;
import grondag.renderbender.model.ModelBuilder;
import grondag.renderbender.model.SimpleModel;
import grondag.renderbender.model.SimpleUnbakedModel;
import net.fabricmc.fabric.api.client.model.fabric.ModelHelper;
import net.fabricmc.fabric.api.client.model.fabric.MutableQuadView;
import net.fabricmc.fabric.api.client.model.fabric.RenderContext;
import net.fabricmc.fabric.api.client.model.fabric.RenderMaterial;
import net.fabricmc.fabric.api.client.model.fabric.Renderer;
import net.fabricmc.fabric.api.client.model.fabric.RendererAccess;
import net.fabricmc.fabric.api.client.model.fabric.TerrainBlockView;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

public class BasicModels {

    public static void initialize(HashMap<String, SimpleUnbakedModel> models) {
        models.put("glow", new SimpleUnbakedModel(mb -> {
            Sprite sprite = mb.getSprite("minecraft:block/quartz_block_side");
            mb.box(mb.finder().emissive(0, true).disableAo(0, true).disableDiffuse(0, true).find(),
                    -1, ModelBuilder.FULL_BRIGHTNESS, sprite, 
                    0, 0, 0, 1, 1, 1);
            return new SimpleModel(mb.builder.build(), null, sprite, ModelHelper.MODEL_TRANSFORM_BLOCK, null);
        }));
        
        models.put("glow_diffuse", new SimpleUnbakedModel(mb -> {
            Sprite sprite = mb.getSprite("minecraft:block/quartz_block_side");
            mb.box(mb.finder().emissive(0, true).disableAo(0, true).find(),
                    -1, ModelBuilder.FULL_BRIGHTNESS, sprite, 
                    0, 0, 0, 1, 1, 1);
            return new SimpleModel(mb.builder.build(), null, sprite, ModelHelper.MODEL_TRANSFORM_BLOCK, null);
        }));
        
        models.put("glow_ao", new SimpleUnbakedModel(mb -> {
            Sprite sprite = mb.getSprite("minecraft:block/quartz_block_side");
            mb.box(mb.finder().emissive(0, true).disableDiffuse(0, true).find(),
                    -1, ModelBuilder.FULL_BRIGHTNESS, sprite, 
                    0, 0, 0, 1, 1, 1);
            return new SimpleModel(mb.builder.build(), null, sprite, ModelHelper.MODEL_TRANSFORM_BLOCK, null);
        }));
        
        models.put("glow_shaded", new SimpleUnbakedModel(mb -> {
            Sprite sprite = mb.getSprite("minecraft:block/quartz_block_side");
            mb.box(mb.finder().emissive(0, true).find(),
                    -1, ModelBuilder.FULL_BRIGHTNESS, sprite, 
                    0, 0, 0, 1, 1, 1);
            return new SimpleModel(mb.builder.build(), null, sprite, ModelHelper.MODEL_TRANSFORM_BLOCK, null);
        }));
        
        models.put("glow_dynamic", new SimpleUnbakedModel(mb -> {
            Sprite sprite = mb.getSprite("minecraft:block/quartz_block_side");
            mb.box(mb.finder().emissive(0, true).find(),
                    -1, 0, sprite, 
                    0, 0, 0, 1, 1, 1);
            return new SimpleModel(mb.builder.build(), glowTransform::get, sprite, ModelHelper.MODEL_TRANSFORM_BLOCK, null);
        }));
        
        models.put("round_hard", new SimpleUnbakedModel(mb -> {
            Sprite sprite = mb.getSprite("minecraft:block/quartz_block_side");
            ModelBuilder.makeIcosahedron(new Vector3f(0.5f, 0.5f, 0.5f), 0.5f, mb.builder.getEmitter(), mb.finder().find(), sprite, false);
            return new SimpleModel(mb.builder.build(), null, sprite, ModelHelper.MODEL_TRANSFORM_BLOCK, null);
        }));
        
        models.put("round_soft", new SimpleUnbakedModel(mb -> {
            Sprite sprite = mb.getSprite("minecraft:block/quartz_block_side");
            ModelBuilder.makeIcosahedron(new Vector3f(0.5f, 0.5f, 0.5f), 0.5f, mb.builder.getEmitter(), mb.finder().find(), sprite, true);
            return new SimpleModel(mb.builder.build(), null, sprite, ModelHelper.MODEL_TRANSFORM_BLOCK, null);
        }));
        
        // this is NOT the way to handle this...
        DynamicRenderer aoBuilder = new DynamicRenderer() {
            RenderMaterial mat = RendererAccess.INSTANCE.getRenderer().materialFinder().disableDiffuse(0, true).find();
            @Override
            public void render(TerrainBlockView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
                int hash = pos == null ? 8 : pos.hashCode();
                float height = (1 + (hash & 15)) / 16f;
                Sprite sprite = MinecraftClient.getInstance().getSpriteAtlas().getSprite("minecraft:block/quartz_block_side");
                ModelMeshBuilder builder = new ModelMeshBuilder();
                
                builder.tag(1);
                for(int d = 0; d < 6; d++) {
                    Direction face = Direction.byId(d);
                    if(face == Direction.UP) {
                        float depth = face == Direction.UP ? 1 - height : 0;
                        for(int i = 0; i < 4; i++) {
                            for(int j = 0; j < 4; j++) {
                                float u = i * .25f;
                                float v = j * .25f;
//                            int color = randomLightColor(r);
                                builder.getEmitter().material(mat).square(face, u, v, u + .25f, v + .25f, depth).tex(sprite, MutableQuadView.BAKE_LOCK_UV)
//                            .forEachVertex(vt -> vt.color(color))
                                .emit();
                            }
                        }
                    } else if(face == Direction.DOWN) {
                        builder.getEmitter().material(mat).square(face, 0, 0, 1, 1, 0).tex(sprite, MutableQuadView.BAKE_LOCK_UV).emit();
                    } else {
                        builder.getEmitter().material(mat).square(face, 0, 0, 1, height, 0).tex(sprite, MutableQuadView.BAKE_LOCK_UV).emit();
                    }
                }
                context.meshConsumer().accept(builder.build());
            }
            
        };
        
        models.put("ao_test", new SimpleUnbakedModel(mb -> {
            return new SimpleModel(null, null, MinecraftClient.getInstance().getSpriteAtlas().getSprite("minecraft:block/quartz_block_side"), ModelHelper.MODEL_TRANSFORM_BLOCK, aoBuilder);
        }));
        
        models.put("shade_test", new SimpleUnbakedModel(mb -> {
            SpriteAtlasTexture atlas = MinecraftClient.getInstance().getSpriteAtlas();
            Renderer renderer = RendererAccess.INSTANCE.getRenderer();
            ModelMeshBuilder builder = new ModelMeshBuilder();
            builder.tag(2);
            builder.box(renderer.materialFinder().find(),
                    -1, 0, atlas.getSprite("minecraft:block/quartz_block_side"),
                    1f/16f, 1f/16f, 1f/16f, 15f/16f, 15f/16f, 15f/16f);
            
            return new SimpleModel(builder.build(), null, atlas.getSprite("minecraft:block/quartz_block_side"), ModelHelper.MODEL_TRANSFORM_BLOCK, null);
        }));
        
        models.put("be_test", new SimpleUnbakedModel(mb -> {
            RenderMaterial mat = RendererAccess.INSTANCE.getRenderer().materialFinder().find();
            Sprite sprite = MinecraftClient.getInstance().getSpriteAtlas().getSprite("minecraft:block/quartz_block_side");
            ModelMeshBuilder builder = new ModelMeshBuilder();
            final float PIXEL = 1f/16f;
            int t = 0;
            for(int d = 0; d < 6; d++) {
                Direction face = Direction.byId(d);
                for(int i = 0; i < 14; i++) {
                    float u = PIXEL + PIXEL * i;
                    for(int j = 0; j < 14; j++) {
                        float v = PIXEL + PIXEL * j;
                        builder.tag(t++);
                        builder.getEmitter().material(mat).square(face, u, v, u + PIXEL, v + PIXEL, PIXEL)
                        .lightmapAll(ModelBuilder.FULL_BRIGHTNESS).tex(sprite, MutableQuadView.BAKE_LOCK_UV).emit();
                    }
                }
            }
            return new SimpleModel(builder.build(), beTestTransform::get, sprite, ModelHelper.MODEL_TRANSFORM_BLOCK, null);
        }));        
    }

    static class GlowTransform implements MeshTransformer {
        int topColor;
        int bottomColor;
        int topLight;
        int bottomLight;
        
        @Override
        public boolean transform(MutableQuadView q) {
            for(int i = 0; i < 4; i++) {
                if(MathHelper.equalsApproximate(q.y(i), 0)) {
                    q.spriteColor(i, 0, bottomColor).lightmap(i, bottomLight);
                } else {
                    q.spriteColor(i, 0, topColor).lightmap(i, topLight);
                }
            }
            return true;
        }
        
        @Override
        public GlowTransform prepare(TerrainBlockView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier) {
            Random random = randomSupplier.get();
            topColor = ModelBuilder.randomPastelColor(random);
            bottomColor = ModelBuilder.randomPastelColor(random);
            final boolean topGlow = random.nextBoolean();
            topLight = topGlow ? ModelBuilder.FULL_BRIGHTNESS : 0;
            bottomLight = topGlow ? 0 : ModelBuilder.FULL_BRIGHTNESS;
            return this;
        }
    }
    
    static ThreadLocal<MeshTransformer> glowTransform = ThreadLocal.withInitial(GlowTransform::new);
    
    
    static class BeTestTransform implements MeshTransformer {
        static RenderMaterial matSolid = RendererAccess.INSTANCE.getRenderer().materialFinder()
                .blendMode(0, SOLID).find();
        
        static RenderMaterial matSolidGlow = RendererAccess.INSTANCE.getRenderer().materialFinder()
                .blendMode(0, SOLID).disableDiffuse(0, true).disableAo(0, true).emissive(0, true).find();
        
        static RenderMaterial matTrans = RendererAccess.INSTANCE.getRenderer().materialFinder()
                .blendMode(0, TRANSLUCENT).find();
        
        static RenderMaterial matTransGlow = RendererAccess.INSTANCE.getRenderer().materialFinder()
                .blendMode(0, TRANSLUCENT).disableDiffuse(0, true).disableAo(0, true).emissive(0, true).find();
        
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
    }
    
    static ThreadLocal<MeshTransformer> beTestTransform = ThreadLocal.withInitial(BeTestTransform::new);
}
