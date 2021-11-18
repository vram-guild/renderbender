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

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;

import io.vram.renderbender.RenderBender;

public class SimpleMaterialsCommon {
	public static void initialize() {
		RenderBender.registerBlock(new Block(Properties.of(Material.STONE).strength(1, 1)), "mat_default", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.STONE).strength(1, 1)), "mat_no_ao", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.STONE).strength(1, 1)), "mat_no_diffuse", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.STONE).strength(1, 1)), "mat_no_shading", RenderBender.ITEM_FACTORY_STANDARD);

		RenderBender.registerBlock(new Block(Properties.of(Material.STONE).strength(1, 1)), "mat_emissive", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.STONE).strength(1, 1)), "mat_emissive_no_ao", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.STONE).strength(1, 1)), "mat_emissive_no_diffuse", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.STONE).strength(1, 1)), "mat_emissive_no_shading", RenderBender.ITEM_FACTORY_STANDARD);

		RenderBender.registerBlock(new Block(Properties.of(Material.BUILDABLE_GLASS).strength(1, 1)), "mat_trans", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.BUILDABLE_GLASS).strength(1, 1)), "mat_no_ao_trans", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.BUILDABLE_GLASS).strength(1, 1)), "mat_no_diffuse_trans", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.BUILDABLE_GLASS).strength(1, 1)), "mat_no_shading_trans", RenderBender.ITEM_FACTORY_STANDARD);

		RenderBender.registerBlock(new Block(Properties.of(Material.BUILDABLE_GLASS).strength(1, 1)), "mat_emissive_trans", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.BUILDABLE_GLASS).strength(1, 1)), "mat_emissive_no_ao_trans", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.BUILDABLE_GLASS).strength(1, 1)), "mat_emissive_no_diffuse_trans", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.BUILDABLE_GLASS).strength(1, 1)), "mat_emissive_no_shading_trans", RenderBender.ITEM_FACTORY_STANDARD);

		RenderBender.registerBlock(new Block(Properties.of(Material.LEAVES).strength(1, 1)), "mat_cutout", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.LEAVES).strength(1, 1)), "mat_no_ao_cutout", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.LEAVES).strength(1, 1)), "mat_no_diffuse_cutout", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.LEAVES).strength(1, 1)), "mat_no_shading_cutout", RenderBender.ITEM_FACTORY_STANDARD);

		RenderBender.registerBlock(new Block(Properties.of(Material.LEAVES).strength(1, 1)), "mat_emissive_cutout", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.LEAVES).strength(1, 1)), "mat_emissive_no_ao_cutout", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.LEAVES).strength(1, 1)), "mat_emissive_no_diffuse_cutout", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.LEAVES).strength(1, 1)), "mat_emissive_no_shading_cutout", RenderBender.ITEM_FACTORY_STANDARD);

		RenderBender.registerBlock(new Block(Properties.of(Material.LEAVES).strength(1, 1)), "mat_cutout_mipped", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.LEAVES).strength(1, 1)), "mat_no_ao_cutout_mipped", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.LEAVES).strength(1, 1)), "mat_no_diffuse_cutout_mipped", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.LEAVES).strength(1, 1)), "mat_no_shading_cutout_mipped", RenderBender.ITEM_FACTORY_STANDARD);

		RenderBender.registerBlock(new Block(Properties.of(Material.LEAVES).strength(1, 1)), "mat_emissive_cutout_mipped", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.LEAVES).strength(1, 1)), "mat_emissive_no_ao_cutout_mipped", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.LEAVES).strength(1, 1)), "mat_emissive_no_diffuse_cutout_mipped", RenderBender.ITEM_FACTORY_STANDARD);
		RenderBender.registerBlock(new Block(Properties.of(Material.LEAVES).strength(1, 1)), "mat_emissive_no_shading_cutout_mipped", RenderBender.ITEM_FACTORY_STANDARD);
	}
}
