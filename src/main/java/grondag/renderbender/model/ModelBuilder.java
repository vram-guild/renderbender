package grondag.renderbender.model;

import java.util.Random;

import grondag.renderbender.ModelMeshBuilder;
import grondag.renderbender.ModelMeshBuilder.ModelQuad;
import net.fabricmc.fabric.api.client.model.fabric.MaterialFinder;
import net.fabricmc.fabric.api.client.model.fabric.MeshBuilder;
import net.fabricmc.fabric.api.client.model.fabric.MutableQuadView;
import net.fabricmc.fabric.api.client.model.fabric.RenderMaterial;
import net.fabricmc.fabric.api.client.model.fabric.Renderer;
import net.fabricmc.fabric.api.client.model.fabric.RendererAccess;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.Vector3f;
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
