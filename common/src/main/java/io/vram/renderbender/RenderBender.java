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

package io.vram.renderbender;

import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class RenderBender {
	public static final Logger LOG = LogManager.getLogger("RenderBender");

	public static boolean customFluid = true;
	public static boolean regionBakeListener = true;
	public static boolean renderLoopListener = true;
	public static boolean simpleMaterials = true;
	public static boolean nonCubics = true;

	public static Item registerBlock(Block block, String name, Function<Block, Item> itemFactory) {
		final ResourceLocation id = new ResourceLocation("renderbender", name);
		Registry.register(Registry.BLOCK, id, block);
		final Item result = itemFactory.apply(block);
		Registry.register(Registry.ITEM, id, result);
		return result;
	}

	public static final Function<Block, Item> ITEM_FACTORY_STANDARD = block -> {
		return new BlockItem(block, new Item.Properties()
				.stacksTo(64)
				.tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
	};

	public static final Function<Block, Item> ITEM_FACTORY_ENCHANTED = block -> {
		return new BlockItem(block, new Item.Properties()
				.stacksTo(64)
				.tab(CreativeModeTab.TAB_BUILDING_BLOCKS)) {
			@Override
			public boolean isFoil(ItemStack itemStack_1) {
				return true;
			};
		};
	};
}
