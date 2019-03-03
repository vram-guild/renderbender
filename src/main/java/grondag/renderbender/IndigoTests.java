/*******************************************************************************
 * Copyright 2019 grondag
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package grondag.renderbender;

import static net.minecraft.block.BlockRenderLayer.SOLID;
import static net.minecraft.block.BlockRenderLayer.TRANSLUCENT;

import java.util.HashMap;
import java.util.Random;
import java.util.function.Supplier;

import grondag.renderbender.ModelMeshBuilder.ModelQuad;
import grondag.renderbender.init.StandardBlocks;
import grondag.renderbender.model.DynamicRenderer;
import grondag.renderbender.model.MeshTransformer;
import grondag.renderbender.model.ModelBuilder;
import grondag.renderbender.model.SimpleModel;
import grondag.renderbender.model.SimpleUnbakedModel;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
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
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

/** A COLLECTION OF EGREGIOUSLY EGREGIOUS HACKS USED FOR TESTING. */
public class IndigoTests {

    // feature disabled
//    private static final Block LAYERS_BLOCK = new Block(FabricBlockSettings
//            .of(Material.STONE).strength(1, 1)
//            .materialColor(MaterialColor.FOLIAGE).build());
    
    static void initialize() {
        RenderBender.LOG.info("Initialising Render Tests");
        
        ModelLoadingRegistry.INSTANCE.registerVariantProvider(manager -> ((modelId, context) -> {
            if (modelId.getNamespace().equals("renderbender")) {
                initModels();
                return models.get(modelId.getPath());
            } else {
                return null;
            }
        }));

        // feature disabled
//        register(LAYERS_BLOCK, "layers");
        
        // Feature disabled
//        ColorProviderRegistry.BLOCK.register((blockState, extendedBlockView, pos, colorIndex) -> {
//            return extendedBlockView != null && pos != null ? BiomeColors.foliageColorAt(extendedBlockView, pos) : FoliageColorHandler.getDefaultColor();
//         }, LAYERS_BLOCK);
//        
//        ColorProviderRegistry.ITEM.register((stack, colorIndex) -> {
//            return FoliageColorHandler.getDefaultColor();
//         }, LAYERS_BLOCK);
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
    
    static HashMap<String, SimpleUnbakedModel> models = new HashMap<>();
    
    // so bad...
    static void initModels() {
        if(!models.isEmpty()) {
            return;
        }
        
        // feature disabled
//        models.put("layers", new ModelHolder(() -> {
//            SpriteAtlasTexture atlas = MinecraftClient.getInstance().getSpriteAtlas();
//            Renderer renderer = RendererAccess.INSTANCE.getRenderer();
//            ModelMeshBuilder builder = new ModelMeshBuilder();
//            builder.quad(renderer.materialFinder().spriteDepth(3)
//                    .blendMode(0, SOLID).emissive(0, true)
//                    .disableAo(0, true).disableDiffuse(0, true)
//                    .blendMode(1, TRANSLUCENT)
//                    .blendMode(2, TRANSLUCENT)
//                    .disableColorIndex(0, true)
//                    .disableColorIndex(2, true).find())
//                    .colorIndex(0)
//                    .square(Direction.UP,  0, 0, 1, 1, 0)
//                    .tex(atlas.getSprite("minecraft:block/quartz_block_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//                    .tex2(atlas.getSprite("minecraft:item/ghast_tear"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//                    .tex3(atlas.getSprite("minecraft:block/scaffolding_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//                    .colorAll(0, -1).colorAll(1, -1).colorAll(2, -1).lightmapAll(15 << 20 | 15 << 4)
//                    .emit();
//            
//            builder.quad(renderer.materialFinder().spriteDepth(3)
//                    .blendMode(0, SOLID).emissive(0, true)
//                    .disableAo(0, true).disableDiffuse(0, true)
//                    .blendMode(1, TRANSLUCENT).emissive(1, true)
//                    .disableAo(1, true).disableDiffuse(1, true)
//                    .blendMode(2, TRANSLUCENT)
//                    .disableColorIndex(0, true)
//                    .disableColorIndex(2, true).find())
//                    .colorIndex(0)
//                    .square(Direction.DOWN,  0, 0, 1, 1, 0)
//                    .tex(atlas.getSprite("minecraft:block/quartz_block_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//                    .tex2(atlas.getSprite("minecraft:item/ghast_tear"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//                    .tex3(atlas.getSprite("minecraft:block/scaffolding_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//                    .colorAll(0, 0xFFFFA0FF).colorAll(1, 0x6FFFFFFF).colorAll(2, -1).lightmapAll(15 << 20 | 15 << 4)
//                    .emit();
//            
//            builder.quad(renderer.materialFinder().spriteDepth(3)
//                    .blendMode(0, SOLID).emissive(0, true)
//                    .disableAo(0, true).disableDiffuse(0, true)
//                    .blendMode(1, TRANSLUCENT)
//                    .blendMode(2, TRANSLUCENT).emissive(2, true)
//                    .disableAo(2, true).disableDiffuse(2, true)
//                    .disableColorIndex(0, true)
//                    .disableColorIndex(2, true).find())
//                    .colorIndex(0)
//                    .square(Direction.EAST,  0, 0, 1, 1, 0)
//                    .tex(atlas.getSprite("minecraft:block/quartz_block_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//                    .tex2(atlas.getSprite("minecraft:item/ghast_tear"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//                    .tex3(atlas.getSprite("minecraft:block/scaffolding_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//                    .colorAll(0, 0xFFFFFF60).colorAll(1, -1).colorAll(2, 0x20FF00FF).lightmapAll(15 << 20 | 15 << 4)
//                    .emit();
//            
//            builder.quad(renderer.materialFinder().spriteDepth(3)
//                    .blendMode(0, SOLID).emissive(0, true)
//                    .disableAo(0, true).disableDiffuse(0, true)
//                    .blendMode(1, TRANSLUCENT).emissive(1, true)
//                    .disableAo(1, true).disableDiffuse(0, true)
//                    .blendMode(2, TRANSLUCENT)
//                    .disableColorIndex(0, true)
//                    .disableColorIndex(2, true).find())
//                    .colorIndex(-1)
//                    .square(Direction.WEST,  0, 0, 1, 1, 0)
//                    .tex(atlas.getSprite("minecraft:block/quartz_block_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//                    .tex2(atlas.getSprite("minecraft:item/ghast_tear"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//                    .tex3(atlas.getSprite("minecraft:block/scaffolding_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//                    .colorAll(0, 0xFFD0D0D0).colorAll(1, 0x5080FFFF).colorAll(2, 0x802020FF).lightmapAll(15 << 20 | 15 << 4)
//                    .emit();
//            
//            builder.quad(renderer.materialFinder().spriteDepth(3)
//                    .blendMode(0, SOLID).emissive(0, true)
//                    .disableAo(0, true).disableDiffuse(0, true)
//                    .blendMode(1, TRANSLUCENT)
//                    .blendMode(2, TRANSLUCENT)
//                    .disableColorIndex(0, true)
//                    .disableColorIndex(1, true)
//                    .disableColorIndex(2, true).find())
//                    .colorIndex(0)
//                    .square(Direction.NORTH,  0, 0, 1, 1, 0)
//                    .tex(atlas.getSprite("minecraft:block/quartz_block_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//                    .tex2(atlas.getSprite("minecraft:item/ghast_tear"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//                    .tex3(atlas.getSprite("minecraft:block/scaffolding_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//                    .colorAll(0, 0xFFA0FFFF).colorAll(1, -1).colorAll(2, -1).lightmapAll(15 << 20 | 15 << 4)
//                    .emit();
//            
//            builder.quad(renderer.materialFinder().spriteDepth(3)
//                    .blendMode(0, SOLID)
//                    .blendMode(1, TRANSLUCENT).emissive(1, true)
//                    .disableAo(1, true).disableDiffuse(1, true)
//                    .blendMode(2, TRANSLUCENT).emissive(2, true)
//                    .disableAo(2, true).disableDiffuse(2, true)
//                    .disableColorIndex(0, true)
//                    .disableColorIndex(2, true).find())
//                    .colorIndex(0)
//                    .square(Direction.SOUTH,  0, 0, 1, 1, 0)
//                    .tex(atlas.getSprite("minecraft:block/quartz_block_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//                    .tex2(atlas.getSprite("minecraft:item/ghast_tear"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//                    .tex3(atlas.getSprite("minecraft:block/scaffolding_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//                    .colorAll(0, 0xFF202020).colorAll(1, -1).colorAll(2, 0xFFFF2020).lightmapAll(15 << 20 | 15 << 4)
//                    .emit();
//            
//            return new SimpleModel(builder.build(), null, atlas.getSprite("minecraft:block/quartz_block_side"), ModelHelper.BLOCK, null);
//        }));
        
        models.put("glow", new SimpleUnbakedModel(() -> {
            ModelBuilder mb = ModelBuilder.instance();
            Sprite sprite = mb.getSprite("minecraft:block/quartz_block_side");
            mb.box(mb.finder.emissive(0, true).disableAo(0, true).disableDiffuse(0, true).find(),
                    -1, ModelBuilder.FULL_BRIGHTNESS, sprite, 
                    0, 0, 0, 1, 1, 1);
            return new SimpleModel(mb.builder.build(), null, sprite, ModelHelper.MODEL_TRANSFORM_BLOCK, null);
        }));
        
        models.put("glow_diffuse", new SimpleUnbakedModel(() -> {
            SpriteAtlasTexture atlas = MinecraftClient.getInstance().getSpriteAtlas();
            Renderer renderer = RendererAccess.INSTANCE.getRenderer();
            ModelMeshBuilder builder = new ModelMeshBuilder();
            builder.box(renderer.materialFinder().emissive(0, true).disableAo(0, true).find(),
                    -1, ModelBuilder.FULL_BRIGHTNESS, atlas.getSprite("minecraft:block/quartz_block_side"),
                    0, 0, 0, 1, 1, 1);
            
            return new SimpleModel(builder.build(), null, atlas.getSprite("minecraft:block/quartz_block_side"), ModelHelper.MODEL_TRANSFORM_BLOCK, null);
        }));
        
        models.put("glow_ao", new SimpleUnbakedModel(() -> {
            SpriteAtlasTexture atlas = MinecraftClient.getInstance().getSpriteAtlas();
            Renderer renderer = RendererAccess.INSTANCE.getRenderer();
            ModelMeshBuilder builder = new ModelMeshBuilder();
            builder.box(renderer.materialFinder().emissive(0, true).disableDiffuse(0, true).find(),
                    -1, ModelBuilder.FULL_BRIGHTNESS, atlas.getSprite("minecraft:block/quartz_block_side"),
                    0, 0, 0, 1, 1, 1);
            
            return new SimpleModel(builder.build(), null, atlas.getSprite("minecraft:block/quartz_block_side"), ModelHelper.MODEL_TRANSFORM_BLOCK, null);
        }));
        
        models.put("glow_shaded", new SimpleUnbakedModel(() -> {
            SpriteAtlasTexture atlas = MinecraftClient.getInstance().getSpriteAtlas();
            Renderer renderer = RendererAccess.INSTANCE.getRenderer();
            ModelMeshBuilder builder = new ModelMeshBuilder();
            builder.box(renderer.materialFinder().emissive(0, true).find(),
                    -1, ModelBuilder.FULL_BRIGHTNESS, atlas.getSprite("minecraft:block/quartz_block_side"),
                    0, 0, 0, 1, 1, 1);
            
            return new SimpleModel(builder.build(), null, atlas.getSprite("minecraft:block/quartz_block_side"), ModelHelper.MODEL_TRANSFORM_BLOCK, null);
        }));
        
        models.put("glow_dynamic", new SimpleUnbakedModel(() -> {
            SpriteAtlasTexture atlas = MinecraftClient.getInstance().getSpriteAtlas();
            Renderer renderer = RendererAccess.INSTANCE.getRenderer();
            ModelMeshBuilder builder = new ModelMeshBuilder();
            builder.box(renderer.materialFinder().emissive(0, true).find(),
                    -1, 0, atlas.getSprite("minecraft:block/quartz_block_side"),
                    0, 0, 0, 1, 1, 1);
            return new SimpleModel(builder.build(), glowTransform::get, atlas.getSprite("minecraft:block/quartz_block_side"), ModelHelper.MODEL_TRANSFORM_BLOCK, null);
        }));
        
        models.put("round_hard", new SimpleUnbakedModel(() -> {
            SpriteAtlasTexture atlas = MinecraftClient.getInstance().getSpriteAtlas();
            Renderer renderer = RendererAccess.INSTANCE.getRenderer();
            ModelMeshBuilder builder = new ModelMeshBuilder();
            makeIcosahedron(new Vector3f(0.5f, 0.5f, 0.5f), 0.5f, builder,
                    renderer.materialFinder().find(), atlas.getSprite("minecraft:block/quartz_block_side"), false);
            return new SimpleModel(builder.build(), null, atlas.getSprite("minecraft:block/quartz_block_side"), ModelHelper.MODEL_TRANSFORM_BLOCK, null);
        }));
        
        models.put("round_soft", new SimpleUnbakedModel(() -> {
            SpriteAtlasTexture atlas = MinecraftClient.getInstance().getSpriteAtlas();
            Renderer renderer = RendererAccess.INSTANCE.getRenderer();
            ModelMeshBuilder builder = new ModelMeshBuilder();
            makeIcosahedron(new Vector3f(0.5f, 0.5f, 0.5f), 0.5f, builder,
                    renderer.materialFinder().find(), atlas.getSprite("minecraft:block/quartz_block_side"), true);
            return new SimpleModel(builder.build(), null, atlas.getSprite("minecraft:block/quartz_block_side"), ModelHelper.MODEL_TRANSFORM_BLOCK, null);
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
        
        models.put("ao_test", new SimpleUnbakedModel(() -> {
            return new SimpleModel(null, null, MinecraftClient.getInstance().getSpriteAtlas().getSprite("minecraft:block/quartz_block_side"), ModelHelper.MODEL_TRANSFORM_BLOCK, aoBuilder);
        }));
        
        models.put("shade_test", new SimpleUnbakedModel(() -> {
            SpriteAtlasTexture atlas = MinecraftClient.getInstance().getSpriteAtlas();
            Renderer renderer = RendererAccess.INSTANCE.getRenderer();
            ModelMeshBuilder builder = new ModelMeshBuilder();
            builder.tag(2);
            builder.box(renderer.materialFinder().find(),
                    -1, 0, atlas.getSprite("minecraft:block/quartz_block_side"),
                    1f/16f, 1f/16f, 1f/16f, 15f/16f, 15f/16f, 15f/16f);
            
            return new SimpleModel(builder.build(), null, atlas.getSprite("minecraft:block/quartz_block_side"), ModelHelper.MODEL_TRANSFORM_BLOCK, null);
        }));
        
        models.put("be_test", new SimpleUnbakedModel(() -> {
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
    
    
    /**
     * Makes a regular icosahedron, which is a very close approximation to a sphere for most purposes.
     * Loosely based on http://blog.andreaskahler.com/2009/06/creating-icosphere-mesh-in-code.html
     */
    public static void makeIcosahedron(Vector3f center, float radius, ModelMeshBuilder builder, RenderMaterial material, Sprite sprite, boolean smoothNormals) {
        /** vertex scale */
        final float s = (float) (radius  / (2 * Math.sin(2 * Math.PI / 5)));
        
        Vector3f[] vertexes = new Vector3f[12];
        Vector3f[] normals = new Vector3f[12];
        // create 12 vertices of a icosahedron
        final float t = (float) (s * (1.0 + Math.sqrt(5.0)) / 2.0);
        int vi = 0;
        
        normals[vi++] = new Vector3f(-s,  t,  0);
        normals[vi++] = new Vector3f( s,  t,  0);
        normals[vi++] = new Vector3f(-s, -t,  0);
        normals[vi++] = new Vector3f( s, -t,  0);
        
        normals[vi++] = new Vector3f( 0, -s,  t);
        normals[vi++] = new Vector3f( 0,  s,  t);
        normals[vi++] = new Vector3f( 0, -s, -t);
        normals[vi++] = new Vector3f( 0,  s, -t);
        
        normals[vi++] = new Vector3f( t,  0, -s);
        normals[vi++] = new Vector3f( t,  0,  s);
        normals[vi++] = new Vector3f(-t,  0, -s);
        normals[vi++] = new Vector3f(-t,  0,  s);

        for(int i = 0; i < 12; i++) {
            Vector3f n = normals[i];
            vertexes[i] = new Vector3f(center.x() + n.x(), center.y() + n.y(), center.z() + n.z());
            
            if(smoothNormals) {
                float x = n.x();
                float y = n.y();
                float z = n.z();
                
                float len = (float) Math.sqrt(x * x + y * y + z * z);
                
                x /= len;
                y /= len;
                z /= len;
                n.set(x, y, z);
            }
        }
        
        if(!smoothNormals) {
            normals = null;
        }
        
        // create 20 triangles of the icosahedron
        makeIcosahedronFace(true, 0, 11, 5, vertexes, normals, builder, material, sprite);
        makeIcosahedronFace(false, 4, 5, 11, vertexes, normals, builder, material, sprite);
        makeIcosahedronFace(true, 0, 5, 1, vertexes, normals, builder, material, sprite);
        makeIcosahedronFace(false, 9, 1, 5, vertexes, normals, builder, material, sprite);
        makeIcosahedronFace(true,  0, 1, 7, vertexes, normals, builder, material, sprite);
        makeIcosahedronFace(false, 8, 7, 1, vertexes, normals, builder, material, sprite);
        makeIcosahedronFace(true, 0, 7, 10, vertexes, normals, builder, material, sprite);
        makeIcosahedronFace(false, 6, 10, 7, vertexes, normals, builder, material, sprite);
        makeIcosahedronFace(true, 0, 10, 11, vertexes, normals, builder, material, sprite);
        makeIcosahedronFace(false, 2, 11, 10, vertexes, normals, builder, material, sprite);
        makeIcosahedronFace(true, 5, 4, 9, vertexes, normals, builder, material, sprite);
        makeIcosahedronFace(false, 3, 9, 4, vertexes, normals, builder, material, sprite);
        makeIcosahedronFace(true, 11, 2, 4, vertexes, normals, builder, material, sprite);
        makeIcosahedronFace(false, 3, 4, 2, vertexes, normals, builder, material, sprite);
        makeIcosahedronFace(true, 10, 6, 2, vertexes, normals, builder, material, sprite);
        makeIcosahedronFace(false, 3, 2, 6, vertexes, normals, builder, material, sprite);
        makeIcosahedronFace(true, 7, 8, 6, vertexes, normals, builder, material, sprite);
        makeIcosahedronFace(false, 3, 6, 8, vertexes, normals, builder, material, sprite);
        makeIcosahedronFace(true, 1, 9, 8, vertexes, normals, builder, material, sprite);
        makeIcosahedronFace(false, 3, 8, 9, vertexes, normals, builder, material, sprite);
    }
    
    private static void makeIcosahedronFace(boolean topHalf, int p1, int p2, int p3, Vector3f[] points, Vector3f[] normals, ModelMeshBuilder builder, RenderMaterial material, Sprite sprite) {
        ModelQuad q = builder.getEmitter().material(material);
        q.tex(sprite, MutableQuadView.BAKE_NORMALIZED);
        
        if(topHalf) {
            q.pos(0, points[p1]).uv(0, 0, 1, 1).colorAll(0, -1);
            q.pos(1, points[p2]).uv(1, 0, 0, 1).colorAll(0, -1);
            q.pos(2, points[p3]).uv(2, 0, 1, 0).colorAll(0, -1);
            q.pos(3, points[p3]).uv(3, 0, 1, 0).colorAll(0, -1);
        } else {
            q.pos(0, points[p1]).uv(0, 0, 0, 0).colorAll(0, -1);
            q.pos(1, points[p2]).uv(1, 0, 1, 0).colorAll(0, -1);
            q.pos(2, points[p3]).uv(2, 0, 0, 1).colorAll(0, -1);
            q.pos(3, points[p3]).uv(3, 0, 0, 1).colorAll(0, -1);
        }
        if(normals != null) {
            q.normal(0, normals[p1]);
            q.normal(1, normals[p2]);
            q.normal(2, normals[p3]);
            q.normal(3, normals[p3]);
        }
        q.emit();
    }
}
