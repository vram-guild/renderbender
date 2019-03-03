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

import net.fabricmc.fabric.api.client.model.fabric.Mesh;
import net.fabricmc.fabric.api.client.model.fabric.MeshBuilder;
import net.fabricmc.fabric.api.client.model.fabric.MutableQuadView;
import net.fabricmc.fabric.api.client.model.fabric.QuadEmitter;
import net.fabricmc.fabric.api.client.model.fabric.RenderMaterial;
import net.fabricmc.fabric.api.client.model.fabric.RendererAccess;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Direction;

public class ModelMeshBuilder {
    public static int faceIndex(Direction face) {
        return face == null ? 6 : face.getId();
    }
    
    protected final MeshBuilder renderBuilder = RendererAccess.INSTANCE.getRenderer().meshBuilder();
    protected final ModelQuad modelQuad = new ModelQuad();
    protected int tag = 0;

    public void tag(int tag) {
        this.tag = tag;
    }
    
    public ModelQuad getEmitter() {
        modelQuad.wrapped = renderBuilder.getEmitter();
        modelQuad.tag(tag);
        return modelQuad;
    }
    
    public void clear() {
        tag = 0;
    }
    
    public Mesh build() {
        return renderBuilder.build();
    }
    
    public ModelMeshBuilder box(RenderMaterial material, 
            int color, int lightmap, Sprite sprite,
            float minX, float minY, float minZ, 
            float maxX, float maxY, float maxZ) {
       this.getEmitter().material(material).tex(sprite, MutableQuadView.BAKE_NORMALIZED)
            .square(Direction.UP, minX, minZ, maxX, maxZ, 1-maxY)
            .colorAll(0, color).lightmapAll(lightmap).emit()
        
            .material(material).tex(sprite, MutableQuadView.BAKE_NORMALIZED)
            .square(Direction.DOWN, minX, minZ, maxX, maxZ, minY)
            .colorAll(0, color).lightmapAll(lightmap).emit()
        
            .material(material).tex(sprite, MutableQuadView.BAKE_NORMALIZED)
            .square(Direction.EAST, minZ, minY, maxZ, maxY, 1-maxX)
            .colorAll(0, color).lightmapAll(lightmap).emit()
        
            .material(material).tex(sprite, MutableQuadView.BAKE_NORMALIZED)
            .square(Direction.WEST, minZ, minY, maxZ, maxY, minX)
            .colorAll(0, color).lightmapAll(lightmap).emit()
        
            .material(material).tex(sprite, MutableQuadView.BAKE_NORMALIZED)
            .square(Direction.SOUTH, minX, minY, maxX, maxY, 1-maxZ)
            .colorAll(0, color).lightmapAll(lightmap).emit()
        
            .material(material).tex(sprite, MutableQuadView.BAKE_NORMALIZED)
            .square(Direction.NORTH, minX, minY, maxX, maxY, minZ)
            .colorAll(0, color).lightmapAll(lightmap).emit();
        
        return this;
    }
    
    public class ModelQuad {
        protected QuadEmitter wrapped;
        protected final int[] bakeFlags = new int[3];
        protected final Sprite[] sprites = new Sprite[3];
        
        /** See {@link MutableQuadView#tag(int)} */
        public ModelQuad tag(int tag) {
            wrapped.tag(tag);
            return this;
        }
        
        public ModelQuad material(RenderMaterial material) {
            wrapped.material(material);
            return this;
        }
        
        public ModelQuad square(Direction nominalFace, 
                final float left, final float bottom, final float right, final float top, float depth)
        {
            cullFace(depth == 0 ? nominalFace : null);
            nominalFace(nominalFace);
            switch(nominalFace)
            {
            case UP:
                wrapped.pos(0, left, 1 - depth, 1 - top).spriteColor(0, -1, -1, -1, -1).sprite(0, 0, 0, 0);
                wrapped.pos(1, left, 1 - depth, 1 - bottom).spriteColor(0, -1, -1, -1, -1).sprite(1, 0, 0, 1);
                wrapped.pos(2, right, 1 - depth, 1 - bottom).spriteColor(0, -1, -1, -1, -1).sprite(2, 0, 1, 1);
                wrapped.pos(3, right, 1 - depth, 1 - top).spriteColor(0, -1, -1, -1, -1).sprite(3, 0, 1, 0);
                break;

            case DOWN:   
                wrapped.pos(0, left, depth, top).spriteColor(0, -1, -1, -1, -1).sprite(0, 0, 0, 0);
                wrapped.pos(1, left, depth, bottom).spriteColor(0, -1, -1, -1, -1).sprite(1, 0, 0, 1);
                wrapped.pos(2, right, depth, bottom).spriteColor(0, -1, -1, -1, -1).sprite(2, 0, 1, 1);
                wrapped.pos(3, right, depth, top).spriteColor(0, -1, -1, -1, -1).sprite(3, 0, 1, 0);
                break;

            case EAST:
                wrapped.pos(0, 1 - depth, top, 1 - left).spriteColor(0, -1, -1, -1, -1).sprite(0, 0, 0, 0);
                wrapped.pos(1, 1 - depth, bottom, 1 - left).spriteColor(0, -1, -1, -1, -1).sprite(1, 0, 0, 1);
                wrapped.pos(2, 1 - depth, bottom, 1 - right).spriteColor(0, -1, -1, -1, -1).sprite(2, 0, 1, 1);
                wrapped.pos(3, 1 - depth, top, 1 - right).spriteColor(0, -1, -1, -1, -1).sprite(3, 0, 1, 0);
                break;

            case WEST:
                wrapped.pos(0, depth, top, left).spriteColor(0, -1, -1, -1, -1).sprite(0, 0, 0, 0);
                wrapped.pos(1, depth, bottom, left).spriteColor(0, -1, -1, -1, -1).sprite(1, 0, 0, 1);
                wrapped.pos(2, depth, bottom, right).spriteColor(0, -1, -1, -1, -1).sprite(2, 0, 1, 1);
                wrapped.pos(3, depth, top, right).spriteColor(0, -1, -1, -1, -1).sprite(3, 0, 1, 0);
                break;

            case NORTH:
                wrapped.pos(0, right, top, depth).spriteColor(0, -1, -1, -1, -1).sprite(0, 0, 0, 0);
                wrapped.pos(1, right, bottom, depth).spriteColor(0, -1, -1, -1, -1).sprite(1, 0, 0, 1);
                wrapped.pos(2, left, bottom, depth).spriteColor(0, -1, -1, -1, -1).sprite(2, 0, 1, 1);
                wrapped.pos(3, left, top, depth).spriteColor(0, -1, -1, -1, -1).sprite(3, 0, 1, 0);
                break;

            case SOUTH:
                wrapped.pos(0, 1 - right, top, 1 - depth).spriteColor(0, -1, -1, -1, -1).sprite(0, 0, 0, 0);
                wrapped.pos(1, 1 - right, bottom, 1 - depth).spriteColor(0, -1, -1, -1, -1).sprite(1, 0, 0, 1);
                wrapped.pos(2, 1 - left, bottom, 1 - depth).spriteColor(0, -1, -1, -1, -1).sprite(2, 0, 1, 1);
                wrapped.pos(3, 1 - left, top, 1 - depth).spriteColor(0, -1, -1, -1, -1).sprite(3, 0, 1, 0);
                break;
            }
            return this;
        }
        
        public ModelQuad colorAll(int textureIndex, int color) {
            wrapped.spriteColor(textureIndex, color, color, color, color);
            return this;
        }
        
        public ModelQuad lightmapAll(int lightmap) {
            wrapped.lightmap(lightmap, lightmap, lightmap, lightmap);
            return this;
        }
        
        public ModelQuad tex(Sprite sprite, int bakeFlags) {
            sprites[0] = sprite;
            this.bakeFlags[0] = bakeFlags;
            return this;
        }
        
        public ModelQuad tex2(Sprite sprite, int bakeFlags) {
            sprites[1] = sprite;
            this.bakeFlags[1] = bakeFlags;
            return this;
        }
        
        public ModelQuad tex3(Sprite sprite, int bakeFlags) {
            sprites[2] = sprite;
            this.bakeFlags[2] = bakeFlags;
            return this;
        }
        
        public ModelQuad emit() {
            wrapped.spriteBake(0, sprites[0], bakeFlags[0]);
            if(wrapped.material().spriteDepth() > 1) {
                wrapped.spriteBake(1, sprites[1], bakeFlags[1]);
                if(wrapped.material().spriteDepth() == 3) {
                    wrapped.spriteBake(2, sprites[2], bakeFlags[2]);
                }
            }
            wrapped.emit();
            wrapped.tag(tag);
            return this;
        }
        
        public void toVanilla(int textureIndex, int[] target, int targetIndex, boolean isItem) {
            wrapped.toVanilla(textureIndex, target, targetIndex, isItem);
        }

        public void toPackager(MutableQuadView target) {
            wrapped.copyTo(target);
        }

        public RenderMaterial material() {
            return wrapped.material();
        }

        public int colorIndex() {
            return wrapped.colorIndex();
        }

        public Direction lightFace() {
            return wrapped.lightFace();
        }

        public Direction cullFace() {
            return wrapped.cullFace();
        }

        public Direction nominalFace() {
            return wrapped.nominalFace();
        }

        public Vector3f faceNormal() {
            return wrapped.faceNormal();
        }

        public ModelQuad cullFace(Direction face) {
            wrapped.cullFace(face);
            return this;
        }

        public ModelQuad nominalFace(Direction face) {
            wrapped.nominalFace(face);
            return this;
        }

        public ModelQuad colorIndex(int colorIndex) {
            wrapped.colorIndex(colorIndex);
            return this;
        }

        public ModelQuad fromVanilla(int[] quadData, int startIndex, boolean isItem) {
            wrapped.fromVanilla(quadData, startIndex, isItem);
            return this;
        }

        public ModelQuad pos(int vertexIndex, Vector3f vector3f) {
            wrapped.pos(vertexIndex, vector3f);
            return this;
        }
        
        public ModelQuad uv(int vertexIndex, int textureIndex, float u, float v) {
            wrapped.sprite(vertexIndex, textureIndex, u, v);
            return this;
        }

        public ModelQuad normal(int vertexIndex, Vector3f vector3f) {
            wrapped.normal(vertexIndex, vector3f);
            return this;
        }
    }
}
