/*
 * Silent's Gems -- GemOreBlock
 * Copyright (C) 2018 SilentChaos512
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 3
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.silentchaos512.gems.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import net.silentchaos512.gems.lib.Gems;

import javax.annotation.Nullable;
import java.util.List;

public class GemOreBlock extends OreBlockSG implements IGemBlock {
    private final Gems gem;

    public GemOreBlock(Gems gem) {
        super(gem::getItem, 2, Properties.create(Material.ROCK)
                .hardnessAndResistance(3, 4)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
                .sound(gem.getSet().getOreSoundType())
        );
        this.gem = gem;
    }

    @Override
    public Gems getGem() {
        return gem;
    }

    @Override
    public IFormattableTextComponent getGemBlockName() {
        return new TranslationTextComponent("block.silentgems.gem_ore", this.gem.getDisplayName());
    }

    @Override
    public int getExpRandom() {
        return MathHelper.nextInt(RANDOM, 1, 5);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(gem.getSet().getDisplayName());
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public IFormattableTextComponent getTranslatedName() {
        return getGemBlockName();
    }
}
