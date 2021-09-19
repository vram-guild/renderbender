package grondag.renderbender.init;

import java.util.HashMap;

import io.vram.frex.api.material.MaterialCondition;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.material.BlendMode;
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;

import grondag.frex.api.Renderer;
import grondag.renderbender.model.SimpleModel;
import grondag.renderbender.model.SimpleUnbakedModel;

public class ExtendedModels {

	public static void initialize(HashMap<String, SimpleUnbakedModel> models) {

		models.put("layers", new SimpleUnbakedModel(mb -> {
			final QuadEmitter qe = mb.builder.getEmitter();
			qe.material(mb.finder().spriteDepth(3)
				.blendMode(0, BlendMode.SOLID).emissive(0, true)
				.disableAo(0, true).disableDiffuse(0, true)
				.blendMode(1, BlendMode.TRANSLUCENT)
				.blendMode(2, BlendMode.TRANSLUCENT)
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
				.blendMode(0, BlendMode.SOLID).emissive(0, true)
				.disableAo(0, true).disableDiffuse(0, true)
				.blendMode(1, BlendMode.TRANSLUCENT).emissive(1, true)
				.disableAo(1, true).disableDiffuse(1, true)
				.blendMode(2, BlendMode.TRANSLUCENT)
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
				.blendMode(0, BlendMode.SOLID)
				.blendMode(1, BlendMode.TRANSLUCENT)
				.blendMode(2, BlendMode.TRANSLUCENT).emissive(2, true)
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
				.blendMode(0, BlendMode.SOLID)
				.blendMode(1, BlendMode.TRANSLUCENT).emissive(1, true)
				.disableAo(1, true).disableDiffuse(0, true)
				.blendMode(2, BlendMode.TRANSLUCENT)
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
				.blendMode(0, BlendMode.SOLID).emissive(0, true)
				.disableAo(0, true).disableDiffuse(0, true)
				.blendMode(1, BlendMode.TRANSLUCENT)
				.blendMode(2, BlendMode.TRANSLUCENT)
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
				.blendMode(0, BlendMode.SOLID)
				.blendMode(1, BlendMode.TRANSLUCENT).emissive(1, true)
				.disableAo(1, true).disableDiffuse(1, true)
				.blendMode(2, BlendMode.TRANSLUCENT).emissive(2, true)
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
			final Renderer er = (Renderer) RendererAccess.INSTANCE.getRenderer();
			final RenderMaterial mat = er.materialFinder()
			.shader(new ResourceLocation("renderbender", "shader/test.vert"), new ResourceLocation("renderbender", "shader/test.frag"))
			.find();
			final TextureAtlasSprite sprite = mb.getSprite("minecraft:block/gray_concrete");
			mb.box(mat,
				-1, sprite,
				0, 0, 0, 1, 1, 1);
			return new SimpleModel(mb.builder.build(), null, sprite, ModelHelper.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("conditional", new SimpleUnbakedModel(mb -> {
			final Renderer er = (Renderer) RendererAccess.INSTANCE.getRenderer();
			final MaterialCondition condition = er.createCondition(() -> {
				@SuppressWarnings("resource")
				final Entity entity = Minecraft.getInstance().cameraEntity;
				if(entity == null || entity.level == null) {
					return false;
				} else if(entity.level.isRaining()) {
					return true;
				} else if(entity instanceof final LivingEntity living) {
					return living.getMainHandItem().getItem() == ExtendedBlocks.CONDITION_ITEM
					|| living.getOffhandItem().getItem() == ExtendedBlocks.CONDITION_ITEM;
				} else
					return false;
			}, true, true);
			final RenderMaterial mat = er.materialFinder()
			.shader(new ResourceLocation("renderbender", "shader/test.vert"), new ResourceLocation("renderbender", "shader/test.frag"))
			.blendMode(BlendMode.TRANSLUCENT)
			.condition(condition)
			.emissive(true)
			.disableDiffuse(true)
			.disableAo(true)
			.find();
			final TextureAtlasSprite sprite = mb.getSprite("minecraft:block/snow");
			mb.box(mat,
				0x80DCEFFF, sprite,
				0, 0, 0, 1, 1, 1);
			return new SimpleModel(mb.builder.build(), null, sprite, ModelHelper.MODEL_TRANSFORM_BLOCK, null);
		}));
	}
}
