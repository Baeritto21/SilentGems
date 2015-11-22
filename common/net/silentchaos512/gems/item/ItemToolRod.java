package net.silentchaos512.gems.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.gems.lib.EnumGem;
import net.silentchaos512.gems.lib.Names;
import net.silentchaos512.gems.lib.Strings;

public class ItemToolRod extends ItemSG {

  public static final String[] NAMES = new String[EnumGem.values().length + 5];

  public ItemToolRod() {

    setMaxStackSize(64);
    setHasSubtypes(true);
    setHasGemSubtypes(true);
    setUnlocalizedName(Names.TOOL_ROD);
    setMaxDamage(0);
  }

  @Override
  public void addRecipes() {

    for (int i = 0; i < EnumGem.values().length; ++i) {
      GameRegistry.addShapelessRecipe(new ItemStack(this, 1, i),
          CraftingMaterial.getStack(Names.ORNATE_STICK), EnumGem.values()[i].getItem());
    }
  }

  @Override
  public void addOreDict() {

    for (int i = 0; i < EnumGem.values().length; ++i) {
      OreDictionary.registerOre(Strings.ORE_DICT_STICK_FANCY, new ItemStack(this, 1, i));
    }
  }
}
