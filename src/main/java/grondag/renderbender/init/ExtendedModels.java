package grondag.renderbender.init;

import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import io.vram.frex.api.material.MaterialCondition;
import io.vram.frex.api.material.MaterialConstants;
import io.vram.frex.api.material.MaterialFinder;
import io.vram.frex.api.material.RenderMaterial;
import io.vram.frex.api.model.util.BakedModelUtil;

import grondag.renderbender.model.SimpleModel;
import grondag.renderbender.model.SimpleUnbakedModel;

public class ExtendedModels {
	public static void initialize(HashMap<String, SimpleUnbakedModel> models) {
		models.put("shader", new SimpleUnbakedModel(mb -> {
			final RenderMaterial mat = MaterialFinder.threadLocal()
			.shader(new ResourceLocation("renderbender", "shader/test.vert"), new ResourceLocation("renderbender", "shader/test.frag"))
			.castShadows(false)
			.find();
			final TextureAtlasSprite sprite = mb.getSprite("minecraft:block/gray_concrete");
			mb.box(mat,
				-1, sprite,
				0, 0, 0, 1, 1, 1);
			return new SimpleModel(mb.builder.build(), null, sprite, BakedModelUtil.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("conditional", new SimpleUnbakedModel(mb -> {
			final MaterialCondition condition = MaterialCondition.create(() -> {
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
			final RenderMaterial mat = MaterialFinder.threadLocal()
			.shader(new ResourceLocation("renderbender", "shader/test.vert"), new ResourceLocation("renderbender", "shader/test.frag"))
			.preset(MaterialConstants.PRESET_TRANSLUCENT)
			.condition(condition)
			.emissive(true)
			.disableDiffuse(true)
			.disableAo(true)
			.find();
			final TextureAtlasSprite sprite = mb.getSprite("minecraft:block/snow");
			mb.box(mat,
				0x80DCEFFF, sprite,
				0, 0, 0, 1, 1, 1);
			return new SimpleModel(mb.builder.build(), null, sprite, BakedModelUtil.MODEL_TRANSFORM_BLOCK, null);
		}));
	}
}
