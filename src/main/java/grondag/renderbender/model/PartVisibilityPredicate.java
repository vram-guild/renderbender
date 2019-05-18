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

package grondag.renderbender.model;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ExtendedBlockView;

/**
 * Used to control visibility of multi-part model parts.
 */
@FunctionalInterface
public interface PartVisibilityPredicate {
    public static final PartVisibilityPredicate ALWAYS = (v,s,p) -> true;
    
    /**
     * Note the block view and position parameters will be null
     * if the model is being converted to vanilla baked quads.
     * Recommendation is to use reasonable defaults for a damage
     * model in that case. Future API versions will probably provide
     * better support for dynamic damage models.
     */
    boolean apply(ExtendedBlockView blockView, BlockState state, BlockPos pos);
}
