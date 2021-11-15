/*
 * Copyright © Contributing Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Additional copyright and licensing notices may apply for content that was
 * included from other projects. For more information, see ATTRIBUTION.md.
 */

package grondag.renderbender.model;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Used to control visibility of multi-part model parts.
 */
@FunctionalInterface
public interface PartVisibilityPredicate {
	PartVisibilityPredicate ALWAYS = (v, s, p) -> true;

	/**
	 * Note the block view and position parameters will be null
	 * if the model is being converted to vanilla baked quads.
	 * Recommendation is to use reasonable defaults for a damage
	 * model in that case. Future API versions will probably provide
	 * better support for dynamic damage models.
	 */
	boolean apply(BlockAndTintGetter blockView, BlockState state, BlockPos pos);
}
