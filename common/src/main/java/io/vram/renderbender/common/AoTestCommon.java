/*
 * This file is part of RenderBender and is licensed to the project under
 * terms that are compatible with the GNU Lesser General Public License.
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership and licensing.
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
 */

package io.vram.renderbender.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import io.vram.renderbender.RenderBender;

public class AoTestCommon {
	public static void initialize() {
		RenderBender.registerBlock(AO_TEST, "ao_test", RenderBender.ITEM_FACTORY_STANDARD);
	}

	public static final Block AO_TEST = new Block(Properties.of(Material.STONE).dynamicShape().strength(1, 1)) {
		@Override
		public VoxelShape getShape(BlockState blockState, BlockGetter blockView, BlockPos pos, CollisionContext entityContext) {
			final float height = (1 + (pos.hashCode() & 15)) / 16f;
			return Shapes.box(0, 0, 0, 1, height, 1);
		}
	};
}
