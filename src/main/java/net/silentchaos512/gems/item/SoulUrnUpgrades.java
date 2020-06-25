/*
 * Silent's Gems -- SoulUrnUpgrades
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

package net.silentchaos512.gems.item;

import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.init.GemsItemGroups;
import net.silentchaos512.gems.init.Registration;
import net.silentchaos512.gems.lib.urn.IUrnUpgradeItem;
import net.silentchaos512.gems.lib.urn.UpgradePlanter;
import net.silentchaos512.gems.lib.urn.UpgradeVacuum;
import net.silentchaos512.gems.lib.urn.UrnUpgrade;
import net.silentchaos512.lib.registry.ItemRegistryObject;

import java.util.Locale;
import java.util.function.Supplier;

public enum SoulUrnUpgrades implements IItemProvider {
    EXTRA_STORAGE(basicSerializer("extra_storage", UrnUpgrade::new)),
    VACUUM(basicSerializer("vacuum", UpgradeVacuum::new)),
    PLANTER(basicSerializer("planter", UpgradePlanter::new));
    ;

    @SuppressWarnings("NonFinalFieldInEnum")
    private ItemRegistryObject<UpgradeItem> item;
    private final UrnUpgrade.Serializer<? extends UrnUpgrade> serializer;

    SoulUrnUpgrades(UrnUpgrade.Serializer<? extends UrnUpgrade> serializer) {
        this.serializer = serializer;
    }

    public static void registerItems() {
        for (SoulUrnUpgrades upgrade : values()) {
            upgrade.item = new ItemRegistryObject<>(Registration.ITEMS.register(upgrade.getName(), () -> new UpgradeItem(upgrade)));
        }
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT) + "_urn_upgrade";
    }

    @Override
    public UpgradeItem asItem() {
        return this.item.get();
    }

    public UrnUpgrade.Serializer<? extends UrnUpgrade> getSerializer() {
        return serializer;
    }

    private static UrnUpgrade.Serializer<UrnUpgrade> basicSerializer(String name, Supplier<UrnUpgrade> constructor) {
        return new UrnUpgrade.Serializer<>(new ResourceLocation(SilentGems.MOD_ID, name), constructor);
    }

    public static class UpgradeItem extends Item implements IUrnUpgradeItem {
        private final SoulUrnUpgrades type;

        UpgradeItem(SoulUrnUpgrades type) {
            super(new Properties().group(GemsItemGroups.UTILITY));
            this.type = type;
        }

        @Override
        public UrnUpgrade.Serializer<? extends UrnUpgrade> getSerializer() {
            return type.serializer;
        }

//        @Override
//        public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
//            // TODO
//        }
    }
}
