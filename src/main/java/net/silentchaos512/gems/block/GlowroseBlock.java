/*
 * Silent's Gems -- GlowroseBlock
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

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.silentchaos512.gems.config.GemsConfig;
import net.silentchaos512.gems.lib.Gems;

import javax.annotation.Nullable;
import java.util.List;

public class GlowroseBlock extends FlowerBlock implements IGemBlock {
    private final Gems gem;

    public GlowroseBlock(Gems gem) {
        super(Effects.GLOWING, 8, Properties.create(Material.PLANTS)
                .sound(SoundType.PLANT)
                .setLightLevel(state -> GemsConfig.COMMON.glowroseNormalLight.get())
                .hardnessAndResistance(0)
                .doesNotBlockMovement()
        );
        this.gem = gem;
    }

    @Override
    public Gems getGem() {
        return gem;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        if (gem.getSet() == Gems.Set.DARK) {
            Block block = state.getBlock();
            if (block == Blocks.NETHERRACK || block == Blocks.NETHER_QUARTZ_ORE) {
                return true;
            }
        } else if (gem.getSet() == Gems.Set.LIGHT) {
            Block block = state.getBlock();
            if (block == Blocks.END_STONE) {
                return true;
            }
        }

        return super.isValidGround(state, worldIn, pos);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(gem.getSet().getDisplayName());
    }

    @Override
    public IFormattableTextComponent getTranslatedName() {
        return getGemBlockName();
    }

    @Override
    public IFormattableTextComponent getGemBlockName() {
        return new TranslationTextComponent("block.silentgems.glowrose", this.gem.getDisplayName());
    }
}
