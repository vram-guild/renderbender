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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import grondag.frex.Frex;
import grondag.renderbender.init.BasicBlocks;
import grondag.renderbender.init.ExtendedBlocks;
import grondag.renderbender.init.ModelDispatcher;
import net.fabricmc.api.ModInitializer;

public class RenderBender implements ModInitializer {
    public static final Logger LOG = LogManager.getLogger();

    @Override
    public void onInitialize() {
        BasicBlocks.initialize();
        if(Frex.isAvailable()) {
            ExtendedBlocks.initialize();
        }
        ModelDispatcher.initialize();
    }
}
