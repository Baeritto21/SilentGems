package net.silentchaos512.gems.data;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.silentchaos512.gems.GemsBase;
import net.silentchaos512.gems.setup.GemsItems;
import net.silentchaos512.gems.setup.GemsTags;
import net.silentchaos512.gems.util.Gems;

import javax.annotation.Nullable;

public class GemsItemTagsProvider extends ItemTagsProvider {
    public GemsItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, GemsBase.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        for (Gems gem : Gems.values()) {
            // Block copies
            copy(gem.getModOresTag(), gem.getModOresItemTag());
            copy(gem.getOreTag(), gem.getOreItemTag());
            copy(gem.getBlockTag(), gem.getBlockItemTag());
            copy(gem.getGlowroseTag(), gem.getGlowroseItemTag());

            // Items
            getOrCreateBuilder(gem.getItemTag()).add(gem.getItem());
//            getOrCreateBuilder(gem.getShardTag()).add(gem.getShard());

            // Groups
            getOrCreateBuilder(GemsTags.Items.GEM_ORES).addTag(gem.getModOresItemTag());
            getOrCreateBuilder(GemsTags.Items.GEMS).addTag(gem.getItemTag());
            getOrCreateBuilder(GemsTags.Items.GLOWROSES).addTag(gem.getGlowroseItemTag());
            getOrCreateBuilder(Tags.Items.GEMS).add(gem.getItem());
//            getOrCreateBuilder(Tags.Items.NUGGETS).add(gem.getShard());

            // Others
            getOrCreateBuilder(ItemTags.BEACON_PAYMENT_ITEMS).add(gem.getItem());
        }

        copy(Tags.Blocks.ORES, Tags.Items.ORES);
        copy(GemsTags.Blocks.ORES_SILVER, GemsTags.Items.ORES_SILVER);

        getOrCreateBuilder(GemsTags.Items.INGOTS_SILVER).add(GemsItems.SILVER_INGOT.get());
        getOrCreateBuilder(GemsTags.Items.NUGGETS_SILVER).add(GemsItems.SILVER_NUGGET.get());

        getOrCreateBuilder(GemsTags.Items.STEW_FISH).add(Items.COD, Items.SALMON);
        getOrCreateBuilder(GemsTags.Items.STEW_MEAT).add(Items.BEEF, Items.CHICKEN, Items.MUTTON, Items.PORKCHOP, Items.RABBIT);
    }
}
