/*
 * Copyright © Original Authors
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

package io.vram.renderbender.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import io.vram.renderbender.RenderBender;

public class NonCubicsCommon {
	public static void initialize() {
		RenderBender.registerBlock(new RoundBlock(Properties.of(Material.STONE).strength(1, 1)), "round_enchanted", RenderBender.ITEM_FACTORY_ENCHANTED);
		RenderBender.registerBlock(new RoundBlock(Properties.of(Material.STONE).strength(1, 1)), "round_foil", RenderBender.ITEM_FACTORY_STANDARD);

		RenderBender.registerBlock(new RoundBlock(Properties.of(Material.STONE).strength(1, 1)), "round_hard", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new RoundBlock(Properties.of(Material.STONE).strength(1, 1)), "round_hard_no_diffuse", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new RoundBlock(Properties.of(Material.STONE).strength(1, 1)), "round_hard_no_ao", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new RoundBlock(Properties.of(Material.STONE).strength(1, 1)), "round_hard_no_shading", RenderBender.ITEM_FACTORY_STANDARD);

		RenderBender.registerBlock(new RoundBlock(Properties.of(Material.STONE).strength(1, 1)), "round_soft", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new RoundBlock(Properties.of(Material.STONE).strength(1, 1)), "round_soft_no_diffuse", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new RoundBlock(Properties.of(Material.STONE).strength(1, 1)), "round_soft_no_ao", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new RoundBlock(Properties.of(Material.STONE).strength(1, 1)), "round_soft_no_shading", RenderBender.ITEM_FACTORY_STANDARD);
	}

	private static final VoxelShape ROUND_SHAPE = Block.box(1.0D, 1.0D, 1.0D, 14.0D, 14.0D, 15.0D);

	private static class RoundBlock extends Block {
		RoundBlock(Properties settings) {
			super(settings);
		}

		@Override
		public VoxelShape getShape(BlockState blockState_1, BlockGetter blockView_1, BlockPos blockPos_1, CollisionContext entityContext) {
			return ROUND_SHAPE;
		}

		@Override
		public int getLightBlock(BlockState blockState_1, BlockGetter blockView_1, BlockPos blockPos_1) {
			return 1;
		}

		@Override
		public float getShadeBrightness(BlockState blockState_1, BlockGetter blockView_1, BlockPos blockPos_1) {
			return .5f;
		}
	}
}
