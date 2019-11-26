package grondag.renderbender.init;

import static grondag.renderbender.model.ModelBuilder.FULL_BRIGHTNESS;

import java.util.HashMap;
import java.util.Random;
import java.util.function.Supplier;

import grondag.renderbender.model.DynamicRenderer;
import grondag.renderbender.model.MeshTransformer;
import grondag.renderbender.model.ModelBuilder;
import grondag.renderbender.model.SimpleModel;
import grondag.renderbender.model.SimpleUnbakedModel;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockRenderView;

public class BasicModels {

	static boolean hackformer(MutableQuadView victim) {
		final Sprite sprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier("minecraft:block/quartz_block_side"));
		victim.spriteColor(0, 0xFFFF0000, 0xFFFF0000, 0xFFFF0000, 0xFFFF0000);
		victim.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV);
		return true;
	}

	public static void initialize(HashMap<String, SimpleUnbakedModel> models) {
		models.put("item_transform", new SimpleUnbakedModel(mb -> {
			return new SimpleModel(mb.builder.build(), () -> BasicModels::hackformer, mb.getSprite("minecraft:block/cobble"), ModelHelper.MODEL_TRANSFORM_BLOCK, new DynamicRenderer() {
				@Override
				public void render(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
					final BakedModel baseModel = MinecraftClient.getInstance().getBakedModelManager().getModel(new ModelIdentifier(new Identifier("minecraft", "cobble"), "inventory"));
					context.fallbackConsumer().accept(baseModel);
				}
			});
		}));

		models.put("glow", new SimpleUnbakedModel(mb -> {
			final Sprite sprite = mb.getSprite("minecraft:block/quartz_block_side");
			mb.box(mb.finder().emissive(0, true).disableAo(0, true).disableDiffuse(0, true).find(),
					-1, sprite,
					0, 0, 0, 1, 1, 1);
			return new SimpleModel(mb.builder.build(), null, sprite, ModelHelper.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("glow_diffuse", new SimpleUnbakedModel(mb -> {
			final Sprite sprite = mb.getSprite("minecraft:block/quartz_block_side");
			mb.box(mb.finder().emissive(0, true).disableAo(0, true).find(),
					-1, sprite,
					0, 0, 0, 1, 1, 1);
			return new SimpleModel(mb.builder.build(), null, sprite, ModelHelper.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("glow_ao", new SimpleUnbakedModel(mb -> {
			final Sprite sprite = mb.getSprite("minecraft:block/quartz_block_side");
			mb.box(mb.finder().emissive(0, true).disableDiffuse(0, true).find(),
					-1, sprite,
					0, 0, 0, 1, 1, 1);
			return new SimpleModel(mb.builder.build(), null, sprite, ModelHelper.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("glow_shaded", new SimpleUnbakedModel(mb -> {
			final Sprite sprite = mb.getSprite("minecraft:block/quartz_block_side");
			mb.box(mb.finder().emissive(0, true).find(),
					-1, sprite,
					0, 0, 0, 1, 1, 1);
			return new SimpleModel(mb.builder.build(), null, sprite, ModelHelper.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("glow_dynamic", new SimpleUnbakedModel(mb -> {
			final Sprite sprite = mb.getSprite("minecraft:block/quartz_block_side");
			mb.box(mb.finder().find(),
					-1, sprite,
					0, 0, 0, 1, 1, 1);
			return new SimpleModel(mb.builder.build(), glowTransform::get, sprite, ModelHelper.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("round_hard", new SimpleUnbakedModel(mb -> {
			final Sprite sprite = mb.getSprite("minecraft:block/quartz_block_side");
			ModelBuilder.makeIcosahedron(new Vector3f(0.5f, 0.5f, 0.5f), 0.5f, mb.builder.getEmitter(), mb.finder().find(), sprite, false);
			return new SimpleModel(mb.builder.build(), null, sprite, ModelHelper.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("round_soft", new SimpleUnbakedModel(mb -> {
			final Sprite sprite = mb.getSprite("minecraft:block/quartz_block_side");
			ModelBuilder.makeIcosahedron(new Vector3f(0.5f, 0.5f, 0.5f), 0.5f, mb.builder.getEmitter(), mb.finder().find(), sprite, true);
			return new SimpleModel(mb.builder.build(), null, sprite, ModelHelper.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("ao_test", new SimpleUnbakedModel(mb -> {
			return new SimpleModel(null, null, mb.getSprite("minecraft:block/quartz_block_side"), ModelHelper.MODEL_TRANSFORM_BLOCK, aoBuilder());
		}));

		models.put("shade_test", new SimpleUnbakedModel(mb -> {
			final Sprite sprite = mb.getSprite("minecraft:block/quartz_block_side");
			mb.box(mb.finder().find(),
					-1, sprite,
					1f/16f, 1f/16f, 1f/16f, 15f/16f, 15f/16f, 15f/16f);
			return new SimpleModel(mb.builder.build(), null, sprite, ModelHelper.MODEL_TRANSFORM_BLOCK, null);
		}));

		models.put("be_test", new SimpleUnbakedModel(mb -> {
			final Sprite sprite = mb.getSprite("minecraft:block/quartz_block_side");
			final RenderMaterial mat = mb.finder().find();
			final float PIXEL = 1f/16f;
			final QuadEmitter qe = mb.builder.getEmitter();
			int t = 0;
			for(int d = 0; d < 6; d++) {
				final Direction face = Direction.byId(d);
				for(int i = 0; i < 14; i++) {
					final float u = PIXEL + PIXEL * i;
					for(int j = 0; j < 14; j++) {
						final float v = PIXEL + PIXEL * j;
						qe.tag(t++);
						qe.material(mat).square(face, u, v, u + PIXEL, v + PIXEL, PIXEL)
						.spriteColor(0, -1, -1, -1, -1)
						.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV).emit();
					}
				}
			}
			return new SimpleModel(mb.builder.build(), beTestTransform::get, sprite, ModelHelper.MODEL_TRANSFORM_BLOCK, null);
		}));
	}

	// this is NOT the way to handle this...
	static DynamicRenderer aoBuilder() {
		return new DynamicRenderer() {
			Renderer renderer = RendererAccess.INSTANCE.getRenderer();
			RenderMaterial mat = renderer.materialFinder().disableDiffuse(0, true).find();
			@Override
			public void render(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
				final int hash = pos == null ? 8 : pos.hashCode();
				final float height = (1 + (hash & 15)) / 16f;
				final Sprite sprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier("minecraft:block/quartz_block_side"));
				final MeshBuilder builder = renderer.meshBuilder();
				final QuadEmitter emitter = builder.getEmitter();

				for(int d = 0; d < 6; d++) {
					final Direction face = Direction.byId(d);
					if(face == Direction.UP) {
						final float depth = face == Direction.UP ? 1 - height : 0;
						for(int i = 0; i < 4; i++) {
							for(int j = 0; j < 4; j++) {
								final float u = i * .25f;
								final float v = j * .25f;
								emitter.square(face, u, v, u + .25f, v + .25f, depth)
								.material(mat).spriteColor(0, -1, -1, -1, -1)
								.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV).emit();
							}
						}
					} else if(face == Direction.DOWN) {
						emitter.square(face, 0, 0, 1, 1, 0)
						.material(mat).spriteColor(0, -1, -1, -1, -1)
						.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV).emit();
					} else {
						emitter.square(face, 0, 0, 1, height, 0)
						.material(mat).spriteColor(0, -1, -1, -1, -1)
						.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV).emit();
					}
				}
				context.meshConsumer().accept(builder.build());
			}
		};
	}

	static class GlowTransform implements MeshTransformer {
		int topColor;
		int bottomColor;
		int topLight;
		int bottomLight;

		@Override
		public boolean transform(MutableQuadView q) {
			for(int i = 0; i < 4; i++) {
				if(MathHelper.approximatelyEquals(q.y(i), 0)) {
					q.spriteColor(i, 0, bottomColor).lightmap(i, bottomLight);
				} else {
					q.spriteColor(i, 0, topColor).lightmap(i, topLight);
				}
			}
			return true;
		}

		@Override
		public GlowTransform prepare(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier) {
			return prep(randomSupplier);
		}

		@Override
		public GlowTransform prepare(ItemStack stack, Supplier<Random> randomSupplier) {
			return prep(randomSupplier);
		}

		private GlowTransform prep(Supplier<Random> randomSupplier) {
			final Random random = randomSupplier.get();
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
