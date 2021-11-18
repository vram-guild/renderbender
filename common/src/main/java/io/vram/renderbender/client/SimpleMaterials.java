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

package io.vram.renderbender.client;

import io.vram.frex.api.material.MaterialConstants;
import io.vram.frex.base.client.model.StaticMeshModel;

public class SimpleMaterials {
	public static void initialize() {
		StaticMeshModel.createAndRegisterCube("renderbender:mat_default", "minecraft:block/white_concrete", -1, f -> f.find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_no_ao", "minecraft:block/white_concrete", -1, f -> f.disableAo(true).find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_no_diffuse", "minecraft:block/white_concrete", -1, f -> f.disableDiffuse(true).find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_no_shading", "minecraft:block/white_concrete", -1, f -> f.disableAo(true).disableDiffuse(true).find());

		StaticMeshModel.createAndRegisterCube("renderbender:mat_emissive", "minecraft:block/white_concrete", -1, f -> f.emissive(true).find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_emissive_no_ao", "minecraft:block/white_concrete", -1, f -> f.emissive(true).disableAo(true).find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_emissive_no_diffuse", "minecraft:block/white_concrete", -1, f -> f.emissive(true).disableDiffuse(true).find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_emissive_no_shading", "minecraft:block/white_concrete", -1, f -> f.emissive(true).disableAo(true).disableDiffuse(true).find());

		StaticMeshModel.createAndRegisterCube("renderbender:mat_trans", "minecraft:block/white_concrete", 0x80FFFFFF, f -> f.preset(MaterialConstants.PRESET_TRANSLUCENT).find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_no_ao_trans", "minecraft:block/white_concrete", 0x80FFFFFF, f -> f.disableAo(true).preset(MaterialConstants.PRESET_TRANSLUCENT).find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_no_diffuse_trans", "minecraft:block/white_concrete", 0x80FFFFFF, f -> f.disableDiffuse(true).preset(MaterialConstants.PRESET_TRANSLUCENT).find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_no_shading_trans", "minecraft:block/white_concrete", 0x80FFFFFF, f -> f.disableAo(true).disableDiffuse(true).preset(MaterialConstants.PRESET_TRANSLUCENT).find());

		StaticMeshModel.createAndRegisterCube("renderbender:mat_emissive_trans", "minecraft:block/white_concrete", 0x80FFFFFF, f -> f.emissive(true).preset(MaterialConstants.PRESET_TRANSLUCENT).find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_emissive_no_ao_trans", "minecraft:block/white_concrete", 0x80FFFFFF, f -> f.emissive(true).disableAo(true).preset(MaterialConstants.PRESET_TRANSLUCENT).find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_emissive_no_diffuse_trans", "minecraft:block/white_concrete", 0x80FFFFFF, f -> f.emissive(true).disableDiffuse(true).preset(MaterialConstants.PRESET_TRANSLUCENT).find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_emissive_no_shading_trans", "minecraft:block/white_concrete", 0x80FFFFFF, f -> f.emissive(true).disableAo(true).disableDiffuse(true).preset(MaterialConstants.PRESET_TRANSLUCENT).find());

		StaticMeshModel.createAndRegisterCube("renderbender:mat_cutout", "minecraft:block/oak_leaves", -1, f -> f.preset(MaterialConstants.PRESET_CUTOUT).find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_no_ao_cutout", "minecraft:block/oak_leaves", -1, f -> f.disableAo(true).preset(MaterialConstants.PRESET_CUTOUT).find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_no_diffuse_cutout", "minecraft:block/oak_leaves", -1, f -> f.disableDiffuse(true).preset(MaterialConstants.PRESET_CUTOUT).find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_no_shading_cutout", "minecraft:block/oak_leaves", -1, f -> f.disableAo(true).disableDiffuse(true).preset(MaterialConstants.PRESET_CUTOUT).find());

		StaticMeshModel.createAndRegisterCube("renderbender:mat_emissive_cutout", "minecraft:block/oak_leaves", -1, f -> f.emissive(true).preset(MaterialConstants.PRESET_CUTOUT).find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_emissive_no_ao_cutout", "minecraft:block/oak_leaves", -1, f -> f.emissive(true).disableAo(true).preset(MaterialConstants.PRESET_CUTOUT).find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_emissive_no_diffuse_cutout", "minecraft:block/oak_leaves", -1, f -> f.emissive(true).disableDiffuse(true).preset(MaterialConstants.PRESET_CUTOUT).find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_emissive_no_shading_cutout", "minecraft:block/oak_leaves", -1, f -> f.emissive(true).disableAo(true).disableDiffuse(true).preset(MaterialConstants.PRESET_CUTOUT).find());

		StaticMeshModel.createAndRegisterCube("renderbender:mat_cutout_mipped", "minecraft:block/oak_leaves", -1, f -> f.preset(MaterialConstants.PRESET_CUTOUT_MIPPED).find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_no_ao_cutout_mipped", "minecraft:block/oak_leaves", -1, f -> f.disableAo(true).preset(MaterialConstants.PRESET_CUTOUT_MIPPED).find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_no_diffuse_cutout_mipped", "minecraft:block/oak_leaves", -1, f -> f.disableDiffuse(true).preset(MaterialConstants.PRESET_CUTOUT_MIPPED).find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_no_shading_cutout_mipped", "minecraft:block/oak_leaves", -1, f -> f.disableAo(true).disableDiffuse(true).preset(MaterialConstants.PRESET_CUTOUT_MIPPED).find());

		StaticMeshModel.createAndRegisterCube("renderbender:mat_emissive_cutout_mipped", "minecraft:block/oak_leaves", -1, f -> f.emissive(true).preset(MaterialConstants.PRESET_CUTOUT_MIPPED).find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_emissive_no_ao_cutout_mipped", "minecraft:block/oak_leaves", -1, f -> f.emissive(true).disableAo(true).preset(MaterialConstants.PRESET_CUTOUT_MIPPED).find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_emissive_no_diffuse_cutout_mipped", "minecraft:block/oak_leaves", -1, f -> f.emissive(true).disableDiffuse(true).preset(MaterialConstants.PRESET_CUTOUT_MIPPED).find());
		StaticMeshModel.createAndRegisterCube("renderbender:mat_emissive_no_shading_cutout_mipped", "minecraft:block/oak_leaves", -1, f -> f.emissive(true).disableAo(true).disableDiffuse(true).preset(MaterialConstants.PRESET_CUTOUT_MIPPED).find());
	}
}
