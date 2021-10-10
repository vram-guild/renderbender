package grondag.renderbender.init;

import static grondag.renderbender.model.ModelBuilder.FULL_BRIGHTNESS;

import java.util.HashMap;
import java.util.Random;

import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockAndTintGetter;

import io.vram.frex.api.material.RenderMaterial;
import io.vram.frex.api.mesh.MeshBuilder;
import io.vram.frex.api.mesh.QuadEmitter;
import io.vram.frex.api.model.BakedInputContext;
import io.vram.frex.api.model.BlockModel.BlockInputContext;
import io.vram.frex.api.model.ItemModel.ItemInputContext;
import io.vram.frex.api.model.ModelOuputContext;
import io.vram.frex.api.model.util.BakedModelUtil;
import io.vram.frex.api.renderer.Renderer;
import io.vram.frex.base.renderer.context.BaseFallbackConsumer;

import grondag.renderbender.model.DynamicRenderer;
import grondag.renderbender.model.MeshTransformer;
import grondag.renderbender.model.ModelBuilder;
import grondag.renderbender.model.SimpleModel;
import grondag.renderbender.model.SimpleUnbakedModel;

public class BasicModels {

	static boolean hackformer(QuadEmitter victim) {
		final TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation("minecraft:block/white_concrete"));
		victim.vertexColor(0xFFFF0000, 0xFFFF0000, 0xFFFF0000, 0xFFFF0000);
		victim.spriteBake(sprite, QuadEmitter.BAKE_LOCK_UV);
		return true;
	}

	public static void initialize(HashMap<String, SimpleUnbakedModel> models) {
		models.put("item_transform", new SimpleUnbakedModel(mb -> {
			return new SimpleModel(mb.builder.build(), () -> BasicModels::hackformer, mb.getSprite("minecraft:block/cobble"), BakedModelUtil.MODEL_TRANSFORM_BLOCK, new DynamicRenderer() {
				@Override
				public void render(BlockAndTintGetter blockView, BakedInputContext input, ModelOuputContext context) {
					final BakedModel baseModel = Minecraft.getInstance().getModelManager().getModel(new ModelResourceLocation(new ResourceLocation("minecraft", "cobble"), "inventory"));
					BaseFallbackConsumer.accept(baseModel, input, context);
				}
			});
		}));

		models.put("glow", new SimpleUnbakedModel(mb -> {
			final TextureAtlasSprite sprite = mb.getSprite("minecraft:block/white_concrete");
			mb.box(mb.finder().emissive(true).disableAo(true).disableDiffuse(true).find(),
				-1, sprite,
				0, 0, 0, 1, 1, 1);
			return new SimpleModel(mb.builder.build(), null, sprite, BakedModelUtil.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("glow_diffuse", new SimpleUnbakedModel(mb -> {
			final TextureAtlasSprite sprite = mb.getSprite("minecraft:block/white_concrete");
			mb.box(mb.finder().emissive(true).disableAo(true).find(),
				-1, sprite,
				0, 0, 0, 1, 1, 1);
			return new SimpleModel(mb.builder.build(), null, sprite, BakedModelUtil.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("glow_ao", new SimpleUnbakedModel(mb -> {
			final TextureAtlasSprite sprite = mb.getSprite("minecraft:block/white_concrete");
			mb.box(mb.finder().emissive(true).disableDiffuse(true).find(),
				-1, sprite,
				0, 0, 0, 1, 1, 1);
			return new SimpleModel(mb.builder.build(), null, sprite, BakedModelUtil.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("glow_shaded", new SimpleUnbakedModel(mb -> {
			final TextureAtlasSprite sprite = mb.getSprite("minecraft:block/white_concrete");
			mb.box(mb.finder().emissive(true).find(),
				-1, sprite,
				0, 0, 0, 1, 1, 1);
			return new SimpleModel(mb.builder.build(), null, sprite, BakedModelUtil.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("glow_dynamic", new SimpleUnbakedModel(mb -> {
			final TextureAtlasSprite sprite = mb.getSprite("minecraft:block/white_concrete");
			mb.box(mb.finder().find(),
				-1, sprite,
				0, 0, 0, 1, 1, 1);
			return new SimpleModel(mb.builder.build(), glowTransform::get, sprite, BakedModelUtil.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("round_hard", new SimpleUnbakedModel(mb -> {
			final TextureAtlasSprite sprite = mb.getSprite("minecraft:block/white_concrete");
			ModelBuilder.makeIcosahedron(new Vector3f(0.5f, 0.5f, 0.5f), 0.5f, mb.builder.getEmitter(), mb.finder().find(), sprite, false);
			return new SimpleModel(mb.builder.build(), null, sprite, BakedModelUtil.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("round_hard_diffuse", new SimpleUnbakedModel(mb -> {
			final TextureAtlasSprite sprite = mb.getSprite("minecraft:block/white_concrete");
			ModelBuilder.makeIcosahedron(new Vector3f(0.5f, 0.5f, 0.5f), 0.5f, mb.builder.getEmitter(), mb.finder().disableAo(true).find(), sprite, false);
			return new SimpleModel(mb.builder.build(), null, sprite, BakedModelUtil.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("round_hard_diffuse_glow", new SimpleUnbakedModel(mb -> {
			final TextureAtlasSprite sprite = mb.getSprite("minecraft:block/white_concrete");
			ModelBuilder.makeIcosahedron(new Vector3f(0.5f, 0.5f, 0.5f), 0.5f, mb.builder.getEmitter(), mb.finder().emissive(true).disableAo(true).find(), sprite, false);
			return new SimpleModel(mb.builder.build(), null, sprite, BakedModelUtil.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("round_hard_ao", new SimpleUnbakedModel(mb -> {
			final TextureAtlasSprite sprite = mb.getSprite("minecraft:block/white_concrete");
			ModelBuilder.makeIcosahedron(new Vector3f(0.5f, 0.5f, 0.5f), 0.5f, mb.builder.getEmitter(), mb.finder().disableDiffuse(true).find(), sprite, false);
			return new SimpleModel(mb.builder.build(), null, sprite, BakedModelUtil.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("round_soft", new SimpleUnbakedModel(mb -> {
			final TextureAtlasSprite sprite = mb.getSprite("minecraft:block/white_concrete");
			ModelBuilder.makeIcosahedron(new Vector3f(0.5f, 0.5f, 0.5f), 0.5f, mb.builder.getEmitter(), mb.finder().find(), sprite, true);
			return new SimpleModel(mb.builder.build(), null, sprite, BakedModelUtil.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("round_soft_diffuse", new SimpleUnbakedModel(mb -> {
			final TextureAtlasSprite sprite = mb.getSprite("minecraft:block/white_concrete");
			ModelBuilder.makeIcosahedron(new Vector3f(0.5f, 0.5f, 0.5f), 0.5f, mb.builder.getEmitter(), mb.finder().disableAo(true).find(), sprite, true);
			return new SimpleModel(mb.builder.build(), null, sprite, BakedModelUtil.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("round_soft_diffuse_glow", new SimpleUnbakedModel(mb -> {
			final TextureAtlasSprite sprite = mb.getSprite("minecraft:block/white_concrete");
			ModelBuilder.makeIcosahedron(new Vector3f(0.5f, 0.5f, 0.5f), 0.5f, mb.builder.getEmitter(), mb.finder().emissive(true).disableAo(true).find(), sprite, true);
			return new SimpleModel(mb.builder.build(), null, sprite, BakedModelUtil.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("round_soft_ao", new SimpleUnbakedModel(mb -> {
			final TextureAtlasSprite sprite = mb.getSprite("minecraft:block/white_concrete");
			ModelBuilder.makeIcosahedron(new Vector3f(0.5f, 0.5f, 0.5f), 0.5f, mb.builder.getEmitter(), mb.finder().disableDiffuse(true).find(), sprite, true);
			return new SimpleModel(mb.builder.build(), null, sprite, BakedModelUtil.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("ao_test", new SimpleUnbakedModel(mb -> {
			return new SimpleModel(null, null, mb.getSprite("minecraft:block/white_concrete"), BakedModelUtil.MODEL_TRANSFORM_BLOCK, aoBuilder());
		}));

		models.put("shade_test", new SimpleUnbakedModel(mb -> {
			final TextureAtlasSprite sprite = mb.getSprite("minecraft:block/white_concrete");
			mb.box(mb.finder().find(),
				-1, sprite,
				1f/16f, 1f/16f, 1f/16f, 15f/16f, 15f/16f, 15f/16f);
			return new SimpleModel(mb.builder.build(), null, sprite, BakedModelUtil.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("be_test", new SimpleUnbakedModel(mb -> {
			final TextureAtlasSprite sprite = mb.getSprite("minecraft:block/white_concrete");
			final RenderMaterial mat = mb.finder().find();
			final float PIXEL = 1f/16f;
			final QuadEmitter qe = mb.builder.getEmitter();
			int t = 0;
			for(int d = 0; d < 6; d++) {
				final Direction face = Direction.from3DDataValue(d);
				for(int i = 0; i < 14; i++) {
					final float u = PIXEL + PIXEL * i;
					for(int j = 0; j < 14; j++) {
						final float v = PIXEL + PIXEL * j;
						qe.tag(t++);
						qe.material(mat).square(face, u, v, u + PIXEL, v + PIXEL, PIXEL)
						.vertexColor(-1, -1, -1, -1)
						.spriteBake(sprite, QuadEmitter.BAKE_LOCK_UV).emit();
					}
				}
			}
			return new SimpleModel(mb.builder.build(), beTestTransform::get, sprite, BakedModelUtil.MODEL_TRANSFORM_BLOCK, null);
		}));
	}

	// this is NOT the way to handle this...
	static DynamicRenderer aoBuilder() {
		return new DynamicRenderer() {
			Renderer renderer = Renderer.get();
			RenderMaterial mat = renderer.materialFinder().disableDiffuse(true).find();
			@Override
			public void render(BlockAndTintGetter blockView, BakedInputContext input ,ModelOuputContext context) {
				final int hash = input.pos() == null ? 8 : input.pos().hashCode();
				final float height = (1 + (hash & 15)) / 16f;
				final TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation("minecraft:block/white_concrete"));
				final MeshBuilder builder = renderer.meshBuilder();
				final QuadEmitter emitter = builder.getEmitter();

				for(int d = 0; d < 6; d++) {
					final Direction face = Direction.from3DDataValue(d);
					if(face == Direction.UP) {
						final float depth = face == Direction.UP ? 1 - height : 0;
						for(int i = 0; i < 4; i++) {
							for(int j = 0; j < 4; j++) {
								final float u = i * .25f;
								final float v = j * .25f;
								emitter.square(face, u, v, u + .25f, v + .25f, depth)
								.material(mat).vertexColor(-1, -1, -1, -1)
								.colorIndex(0)
								.spriteBake(sprite, QuadEmitter.BAKE_LOCK_UV).emit();
							}
						}
					} else if(face == Direction.DOWN) {
						emitter.square(face, 0, 0, 1, 1, 0)
						.material(mat).vertexColor(-1, -1, -1, -1)
						.colorIndex(0)
						.spriteBake(sprite, QuadEmitter.BAKE_LOCK_UV).emit();
					} else {
						emitter.square(face, 0, 0, 1, height, 0)
						.material(mat).vertexColor(-1, -1, -1, -1)
						.colorIndex(0)
						.spriteBake(sprite, QuadEmitter.BAKE_LOCK_UV).emit();
					}
				}

				builder.build().outputTo(context.quadEmitter());
			}
		};
	}

	static class GlowTransform implements MeshTransformer {
		int topColor;
		int bottomColor;
		int topLight;
		int bottomLight;

		@Override
		public boolean transform(QuadEmitter q) {
			for(int i = 0; i < 4; i++) {
				if(Mth.equal(q.y(i), 0)) {
					q.vertexColor(i, bottomColor).lightmap(i, bottomLight);
				} else {
					q.vertexColor(i, topColor).lightmap(i, topLight);
				}
			}
			return true;
		}

		@Override
		public GlowTransform prepare(BlockInputContext input, ModelOuputContext context) {
			return prep(input.random());
		}

		@Override
		public GlowTransform prepare(ItemInputContext input, ModelOuputContext context) {
			return prep(input.random());
		}

		private GlowTransform prep(Random random) {
			topColor = ModelBuilder.randomPastelColor(random);
			bottomColor = ModelBuilder.randomPastelColor(random);
			final boolean topGlow = random.nextBoolean();
			topLight = topGlow ? FULL_BRIGHTNESS : 0;
			bottomLight = topGlow ? 0 : FULL_BRIGHTNESS;
			return this;
		}
	}

	static ThreadLocal<MeshTransformer> glowTransform = ThreadLocal.withInitial(GlowTransform::new);

	static ThreadLocal<MeshTransformer> beTestTransform = ThreadLocal.withInitial(BeTestTransform::new);

}
