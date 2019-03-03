package grondag.renderbender.init;

import java.util.HashMap;

import grondag.renderbender.model.SimpleUnbakedModel;

public class ExtendedModels {

    public static void initialize(HashMap<String, SimpleUnbakedModel> models) {
        // TODO Auto-generated method stub
        
    }

    // feature disabled
//  models.put("layers", new ModelHolder(() -> {
//      SpriteAtlasTexture atlas = MinecraftClient.getInstance().getSpriteAtlas();
//      Renderer renderer = RendererAccess.INSTANCE.getRenderer();
//      ModelMeshBuilder builder = new ModelMeshBuilder();
//      builder.quad(renderer.materialFinder().spriteDepth(3)
//              .blendMode(0, SOLID).emissive(0, true)
//              .disableAo(0, true).disableDiffuse(0, true)
//              .blendMode(1, TRANSLUCENT)
//              .blendMode(2, TRANSLUCENT)
//              .disableColorIndex(0, true)
//              .disableColorIndex(2, true).find())
//              .colorIndex(0)
//              .square(Direction.UP,  0, 0, 1, 1, 0)
//              .tex(atlas.getSprite("minecraft:block/quartz_block_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//              .tex2(atlas.getSprite("minecraft:item/ghast_tear"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//              .tex3(atlas.getSprite("minecraft:block/scaffolding_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//              .colorAll(0, -1).colorAll(1, -1).colorAll(2, -1).lightmapAll(15 << 20 | 15 << 4)
//              .emit();
//      
//      builder.quad(renderer.materialFinder().spriteDepth(3)
//              .blendMode(0, SOLID).emissive(0, true)
//              .disableAo(0, true).disableDiffuse(0, true)
//              .blendMode(1, TRANSLUCENT).emissive(1, true)
//              .disableAo(1, true).disableDiffuse(1, true)
//              .blendMode(2, TRANSLUCENT)
//              .disableColorIndex(0, true)
//              .disableColorIndex(2, true).find())
//              .colorIndex(0)
//              .square(Direction.DOWN,  0, 0, 1, 1, 0)
//              .tex(atlas.getSprite("minecraft:block/quartz_block_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//              .tex2(atlas.getSprite("minecraft:item/ghast_tear"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//              .tex3(atlas.getSprite("minecraft:block/scaffolding_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//              .colorAll(0, 0xFFFFA0FF).colorAll(1, 0x6FFFFFFF).colorAll(2, -1).lightmapAll(15 << 20 | 15 << 4)
//              .emit();
//      
//      builder.quad(renderer.materialFinder().spriteDepth(3)
//              .blendMode(0, SOLID).emissive(0, true)
//              .disableAo(0, true).disableDiffuse(0, true)
//              .blendMode(1, TRANSLUCENT)
//              .blendMode(2, TRANSLUCENT).emissive(2, true)
//              .disableAo(2, true).disableDiffuse(2, true)
//              .disableColorIndex(0, true)
//              .disableColorIndex(2, true).find())
//              .colorIndex(0)
//              .square(Direction.EAST,  0, 0, 1, 1, 0)
//              .tex(atlas.getSprite("minecraft:block/quartz_block_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//              .tex2(atlas.getSprite("minecraft:item/ghast_tear"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//              .tex3(atlas.getSprite("minecraft:block/scaffolding_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//              .colorAll(0, 0xFFFFFF60).colorAll(1, -1).colorAll(2, 0x20FF00FF).lightmapAll(15 << 20 | 15 << 4)
//              .emit();
//      
//      builder.quad(renderer.materialFinder().spriteDepth(3)
//              .blendMode(0, SOLID).emissive(0, true)
//              .disableAo(0, true).disableDiffuse(0, true)
//              .blendMode(1, TRANSLUCENT).emissive(1, true)
//              .disableAo(1, true).disableDiffuse(0, true)
//              .blendMode(2, TRANSLUCENT)
//              .disableColorIndex(0, true)
//              .disableColorIndex(2, true).find())
//              .colorIndex(-1)
//              .square(Direction.WEST,  0, 0, 1, 1, 0)
//              .tex(atlas.getSprite("minecraft:block/quartz_block_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//              .tex2(atlas.getSprite("minecraft:item/ghast_tear"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//              .tex3(atlas.getSprite("minecraft:block/scaffolding_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//              .colorAll(0, 0xFFD0D0D0).colorAll(1, 0x5080FFFF).colorAll(2, 0x802020FF).lightmapAll(15 << 20 | 15 << 4)
//              .emit();
//      
//      builder.quad(renderer.materialFinder().spriteDepth(3)
//              .blendMode(0, SOLID).emissive(0, true)
//              .disableAo(0, true).disableDiffuse(0, true)
//              .blendMode(1, TRANSLUCENT)
//              .blendMode(2, TRANSLUCENT)
//              .disableColorIndex(0, true)
//              .disableColorIndex(1, true)
//              .disableColorIndex(2, true).find())
//              .colorIndex(0)
//              .square(Direction.NORTH,  0, 0, 1, 1, 0)
//              .tex(atlas.getSprite("minecraft:block/quartz_block_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//              .tex2(atlas.getSprite("minecraft:item/ghast_tear"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//              .tex3(atlas.getSprite("minecraft:block/scaffolding_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//              .colorAll(0, 0xFFA0FFFF).colorAll(1, -1).colorAll(2, -1).lightmapAll(15 << 20 | 15 << 4)
//              .emit();
//      
//      builder.quad(renderer.materialFinder().spriteDepth(3)
//              .blendMode(0, SOLID)
//              .blendMode(1, TRANSLUCENT).emissive(1, true)
//              .disableAo(1, true).disableDiffuse(1, true)
//              .blendMode(2, TRANSLUCENT).emissive(2, true)
//              .disableAo(2, true).disableDiffuse(2, true)
//              .disableColorIndex(0, true)
//              .disableColorIndex(2, true).find())
//              .colorIndex(0)
//              .square(Direction.SOUTH,  0, 0, 1, 1, 0)
//              .tex(atlas.getSprite("minecraft:block/quartz_block_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//              .tex2(atlas.getSprite("minecraft:item/ghast_tear"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//              .tex3(atlas.getSprite("minecraft:block/scaffolding_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
//              .colorAll(0, 0xFF202020).colorAll(1, -1).colorAll(2, 0xFFFF2020).lightmapAll(15 << 20 | 15 << 4)
//              .emit();
//      
//      return new SimpleModel(builder.build(), null, atlas.getSprite("minecraft:block/quartz_block_side"), ModelHelper.BLOCK, null);
//  }));
}
