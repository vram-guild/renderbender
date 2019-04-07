package grondag.renderbender.init;

import static net.minecraft.block.BlockRenderLayer.SOLID;
import static net.minecraft.block.BlockRenderLayer.TRANSLUCENT;

import java.util.HashMap;

import grondag.frex.api.core.ModelHelper;
import grondag.frex.api.core.MutableQuadView;
import grondag.frex.api.core.QuadEmitter;
import grondag.frex.api.core.RenderMaterial;
import grondag.frex.api.core.RendererAccess;
import grondag.frex.api.extended.ExtendedRenderer;
import grondag.frex.api.extended.Pipeline;
import grondag.frex.api.extended.RenderCondition;
import grondag.renderbender.model.SimpleModel;
import grondag.renderbender.model.SimpleUnbakedModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.Sprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class ExtendedModels {

    public static void initialize(HashMap<String, SimpleUnbakedModel> models) {
        
          models.put("layers", new SimpleUnbakedModel(mb -> {
              final QuadEmitter qe = mb.builder.getEmitter();
          qe.material(mb.finder().spriteDepth(3)
                  .blendMode(0, SOLID).emissive(0, true)
                  .disableAo(0, true).disableDiffuse(0, true)
                  .blendMode(1, TRANSLUCENT)
                  .blendMode(2, TRANSLUCENT)
                  .disableColorIndex(0, true)
                  .disableColorIndex(2, true).find())
                  .colorIndex(0)
                  .square(Direction.UP,  0, 0, 1, 1, 0)
                  .spriteBake(0, mb.getSprite("minecraft:block/quartz_block_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
                  .spriteBake(1, mb.getSprite("minecraft:item/ghast_tear"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
                  .spriteBake(2, mb.getSprite("minecraft:block/scaffolding_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
                  .spriteColor(0, -1, -1, -1, -1)
                  .spriteColor(1, -1, -1, -1, -1)
                  .spriteColor(2, -1, -1, -1, -1)
                  .emit();
          
          qe.material(mb.finder().spriteDepth(3)
                  .blendMode(0, SOLID).emissive(0, true)
                  .disableAo(0, true).disableDiffuse(0, true)
                  .blendMode(1, TRANSLUCENT).emissive(1, true)
                  .disableAo(1, true).disableDiffuse(1, true)
                  .blendMode(2, TRANSLUCENT)
                  .disableColorIndex(0, true)
                  .disableColorIndex(2, true).find())
                  .colorIndex(0)
                  .square(Direction.DOWN,  0, 0, 1, 1, 0)
                  .spriteBake(0, mb.getSprite("minecraft:block/quartz_block_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
                  .spriteBake(1, mb.getSprite("minecraft:item/ghast_tear"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
                  .spriteBake(2, mb.getSprite("minecraft:block/scaffolding_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
                  .spriteColor(0, 0xFFFFA0FF, 0xFFFFA0FF, 0xFFFFA0FF, 0xFFFFA0FF)
                  .spriteColor(1, 0x6FFFFFFF, 0x6FFFFFFF, 0x6FFFFFFF, 0x6FFFFFFF)
                  .spriteColor(2, -1, -1, -1, -1)
                  .emit();
          
          qe.material(mb.finder().spriteDepth(3)
                  .blendMode(0, SOLID).emissive(0, true)
                  .disableAo(0, true).disableDiffuse(0, true)
                  .blendMode(1, TRANSLUCENT)
                  .blendMode(2, TRANSLUCENT).emissive(2, true)
                  .disableAo(2, true).disableDiffuse(2, true)
                  .disableColorIndex(0, true)
                  .disableColorIndex(2, true).find())
                  .colorIndex(0)
                  .square(Direction.EAST,  0, 0, 1, 1, 0)
                  .spriteBake(0, mb.getSprite("minecraft:block/quartz_block_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
                  .spriteBake(1, mb.getSprite("minecraft:item/ghast_tear"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
                  .spriteBake(2, mb.getSprite("minecraft:block/scaffolding_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
                  .spriteColor(0, 0xFFFFFF60, 0xFFFFFF60, 0xFFFFFF60, 0xFFFFFF60)
                  .spriteColor(1, -1, -1, -1, -1)
                  .spriteColor(2, 0x20FF00FF, 0x20FF00FF, 0x20FF00FF, 0x20FF00FF)
                  .emit();
          
          qe.material(mb.finder().spriteDepth(3)
                  .blendMode(0, SOLID).emissive(0, true)
                  .disableAo(0, true).disableDiffuse(0, true)
                  .blendMode(1, TRANSLUCENT).emissive(1, true)
                  .disableAo(1, true).disableDiffuse(0, true)
                  .blendMode(2, TRANSLUCENT)
                  .disableColorIndex(0, true)
                  .disableColorIndex(2, true).find())
                  .colorIndex(-1)
                  .square(Direction.WEST,  0, 0, 1, 1, 0)
                  .spriteBake(0, mb.getSprite("minecraft:block/quartz_block_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
                  .spriteBake(1, mb.getSprite("minecraft:item/ghast_tear"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
                  .spriteBake(2, mb.getSprite("minecraft:block/scaffolding_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
                  .spriteColor(0, 0xFFD0D0D0, 0xFFD0D0D0, 0xFFD0D0D0, 0xFFD0D0D0)
                  .spriteColor(1, 0x5080FFFF, 0x5080FFFF, 0x5080FFFF, 0x5080FFFF)
                  .spriteColor(2, 0x802020FF, 0x802020FF, 0x802020FF, 0x802020FF)
                  .emit();
          
          qe.material(mb.finder().spriteDepth(3)
                  .blendMode(0, SOLID).emissive(0, true)
                  .disableAo(0, true).disableDiffuse(0, true)
                  .blendMode(1, TRANSLUCENT)
                  .blendMode(2, TRANSLUCENT)
                  .disableColorIndex(0, true)
                  .disableColorIndex(1, true)
                  .disableColorIndex(2, true).find())
                  .colorIndex(0)
                  .square(Direction.NORTH,  0, 0, 1, 1, 0)
                  .spriteBake(0, mb.getSprite("minecraft:block/quartz_block_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
                  .spriteBake(1, mb.getSprite("minecraft:item/ghast_tear"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
                  .spriteBake(2, mb.getSprite("minecraft:block/scaffolding_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
                  .spriteColor(0, 0xFFA0FFFF, 0xFFA0FFFF, 0xFFA0FFFF, 0xFFA0FFFF)
                  .spriteColor(1, -1, -1, -1, -1)
                  .spriteColor(2, -1, -1, -1, -1)
                  .emit();
          
          qe.material(mb.finder().spriteDepth(3)
                  .blendMode(0, SOLID)
                  .blendMode(1, TRANSLUCENT).emissive(1, true)
                  .disableAo(1, true).disableDiffuse(1, true)
                  .blendMode(2, TRANSLUCENT).emissive(2, true)
                  .disableAo(2, true).disableDiffuse(2, true)
                  .disableColorIndex(0, true)
                  .disableColorIndex(2, true).find())
                  .colorIndex(0)
                  .square(Direction.SOUTH,  0, 0, 1, 1, 0)
                  .spriteBake(0, mb.getSprite("minecraft:block/quartz_block_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
                  .spriteBake(1, mb.getSprite("minecraft:item/ghast_tear"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
                  .spriteBake(2, mb.getSprite("minecraft:block/scaffolding_side"), MutableQuadView.BAKE_LOCK_UV | MutableQuadView.BAKE_NORMALIZED)
                  .spriteColor(0, 0xFF202020, 0xFF202020, 0xFF202020, 0xFF202020)
                  .spriteColor(1, -1, -1, -1, -1)
                  .spriteColor(2, 0xFFFF2020, 0xFFFF2020, 0xFFFF2020, 0xFFFF2020)
                  .emit();
          
          return new SimpleModel(mb.builder.build(), null, mb.getSprite("minecraft:block/quartz_block_side"), ModelHelper.MODEL_TRANSFORM_BLOCK, null);
      }));  
          
      models.put("shader", new SimpleUnbakedModel(mb -> {
          ExtendedRenderer er = (ExtendedRenderer) RendererAccess.INSTANCE.getRenderer();
          Pipeline p = getTestPipeline(er);
          RenderMaterial mat = er.materialFinder().pipeline(p).find();
          Sprite sprite = mb.getSprite("minecraft:block/gray_concrete");
          mb.box(mat,
                  -1, sprite, 
                  0, 0, 0, 1, 1, 1);
          return new SimpleModel(mb.builder.build(), null, sprite, ModelHelper.MODEL_TRANSFORM_BLOCK, null);
      }));
      
      models.put("conditional", new SimpleUnbakedModel(mb -> {
          ExtendedRenderer er = (ExtendedRenderer) RendererAccess.INSTANCE.getRenderer();
          RenderCondition condition = er.createCondition(() -> {
              Entity entity = MinecraftClient.getInstance().cameraEntity;
              if(entity == null || entity.world == null) {
                  return false;
              } else if(entity.world.isRaining()) {
                  return true;
              } else if(entity instanceof LivingEntity) {
                  LivingEntity living = (LivingEntity)entity;
                  return living.getMainHandStack().getItem() == ExtendedBlocks.CONDITION_ITEM
                          || living.getOffHandStack().getItem() == ExtendedBlocks.CONDITION_ITEM;
              } else
                  return false;
          }, true, true); 
          Pipeline p = getTestPipeline(er);
          RenderMaterial mat = er.materialFinder().pipeline(p)
                  .blendMode(0, TRANSLUCENT)
                  .condition(condition)
                  .emissive(0, true)
                  .disableDiffuse(0, true)
                  .disableAo(0, true)
                  .find();
          Sprite sprite = mb.getSprite("minecraft:block/snow");
          mb.box(mat,
                  0x80DCEFFF, sprite, 
                  0, 0, 0, 1, 1, 1);
          return new SimpleModel(mb.builder.build(), null, sprite, ModelHelper.MODEL_TRANSFORM_BLOCK, null);
      }));
    }
    
    private static final Identifier TEST_PIPELINE = new Identifier("renderbender", "test");
    
    private static Pipeline getTestPipeline(ExtendedRenderer er) {
        Pipeline p = er.pipelineById(TEST_PIPELINE);
        if(p == null) {
            p = er.pipelineBuilder()
                .vertexSource(new Identifier("renderbender", "shader/test.vert"))
                .fragmentSource(new Identifier("renderbender", "shader/test.frag"))
                .build();
            er.registerPipeline(TEST_PIPELINE, p);
        }
        return p;
    }
}
