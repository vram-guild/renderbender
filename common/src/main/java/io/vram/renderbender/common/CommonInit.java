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

import io.vram.renderbender.RenderBender;

public class CommonInit {
	public static void initialize() {
		if (RenderBender.renderLoopListener) RenderLoopCommon.initialize();
		if (RenderBender.customFluid) FluidsCommon.initialize();
		if (RenderBender.simpleMaterials) SimpleMaterialsCommon.initialize();
		if (RenderBender.nonCubics) NonCubicsCommon.initialize();
		if (RenderBender.dynamicGlow) DynamicGlowCommon.initialize();
		if (RenderBender.blockEntityData) BlockEntityDataCommon.initialize();
		if (RenderBender.subcubicShading) SubcubicCommon.initialize();
		if (RenderBender.itemTransform) ItemTransformCommon.initialize();
		if (RenderBender.aoTest) AoTestCommon.initialize();
		if (RenderBender.conditional) ConditionalCommon.initialize();
		if (RenderBender.shader) ShaderCommon.initialize();
	}
}
