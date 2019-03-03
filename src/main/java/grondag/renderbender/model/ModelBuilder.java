package grondag.renderbender.model;

import java.util.Random;

import net.fabricmc.fabric.api.client.model.fabric.MaterialFinder;
import net.fabricmc.fabric.api.client.model.fabric.MeshBuilder;
import net.fabricmc.fabric.api.client.model.fabric.MutableQuadView;
import net.fabricmc.fabric.api.client.model.fabric.RenderMaterial;
import net.fabricmc.fabric.api.client.model.fabric.Renderer;
import net.fabricmc.fabric.api.client.model.fabric.RendererAccess;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.Direction;

public class ModelBuilder {
    
    private static ModelBuilder instance;
    
    public static ModelBuilder instance() {
        if(instance == null) {
            instance = new ModelBuilder();
        }
        return instance;
    }
    
    public final MeshBuilder builder;
    public final MaterialFinder finder;
    public static final int FULL_BRIGHTNESS = 15 << 20 | 15 << 4;
    
    private ModelBuilder() {
        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        builder = renderer.meshBuilder();
        finder = renderer.materialFinder();
    }
    
    public Sprite getSprite(String spriteName) {
        return MinecraftClient.getInstance().getSpriteAtlas().getSprite(spriteName);
    }
    
    public void box(
            RenderMaterial material, 
            int color, int lightmap, Sprite sprite,
            float minX, float minY, float minZ, 
            float maxX, float maxY, float maxZ) {
        
       builder.getEmitter()
            .material(material)
            .square(Direction.UP, minX, minZ, maxX, maxZ, 1-maxY)
            .spriteColor(0, color, color, color, color)
            .spriteUnitSquare(0)
            .lightmap(lightmap, lightmap, lightmap, lightmap)
            .spriteBake(0, sprite, MutableQuadView.BAKE_NORMALIZED)
            .emit()
        
            .material(material)
            .square(Direction.DOWN, minX, minZ, maxX, maxZ, minY)
            .spriteColor(0, color, color, color, color)
            .spriteUnitSquare(0)
            .lightmap(lightmap, lightmap, lightmap, lightmap)
            .spriteBake(0, sprite, MutableQuadView.BAKE_NORMALIZED)
            .emit()
        
            .material(material)
            .square(Direction.EAST, minZ, minY, maxZ, maxY, 1-maxX)
            .spriteColor(0, color, color, color, color)
            .spriteUnitSquare(0)
            .lightmap(lightmap, lightmap, lightmap, lightmap)
            .spriteBake(0, sprite, MutableQuadView.BAKE_NORMALIZED)
            .emit()
        
            .material(material)
            .square(Direction.WEST, minZ, minY, maxZ, maxY, minX)
            .spriteColor(0, color, color, color, color)
            .spriteUnitSquare(0)
            .lightmap(lightmap, lightmap, lightmap, lightmap)
            .spriteBake(0, sprite, MutableQuadView.BAKE_NORMALIZED)
            .emit()
        
            .material(material)
            .square(Direction.SOUTH, minX, minY, maxX, maxY, 1-maxZ)
            .spriteColor(0, color, color, color, color)
            .spriteUnitSquare(0)
            .lightmap(lightmap, lightmap, lightmap, lightmap)
            .spriteBake(0, sprite, MutableQuadView.BAKE_NORMALIZED)
            .emit()
        
            .material(material)
            .square(Direction.NORTH, minX, minY, maxX, maxY, minZ)
            .spriteColor(0, color, color, color, color)
            .spriteUnitSquare(0)
            .lightmap(lightmap, lightmap, lightmap, lightmap)
            .spriteBake(0, sprite, MutableQuadView.BAKE_NORMALIZED)
            .emit();
    }

    public static int randomPastelColor(Random random) {
        return 0xFF000000 | ((random.nextInt(127) + 127) << 16) | ((random.nextInt(127) + 127) << 8) | (random.nextInt(127) + 127); 
    }
}
