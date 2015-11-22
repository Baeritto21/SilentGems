package net.silentchaos512.gems.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.gems.configuration.Config;
import net.silentchaos512.gems.core.util.RecipeHelper;
import net.silentchaos512.gems.item.CraftingMaterial;
import net.silentchaos512.gems.lib.Names;

public class ChaosOre extends BlockSG {

  public ChaosOre() {

    super(1, Material.rock);

    setHardness(4.0f);
    setResistance(15.0f);
    setStepSound(Block.soundTypeStone);
    setHarvestLevel("pickaxe", 3);

    setUnlocalizedName(Names.CHAOS_ORE);
  }

  @Override
  public void addOreDict() {

    OreDictionary.registerOre("oreChaos", this);
  }

  @Override
  public void addRecipes() {

    ItemStack chaosEssence = CraftingMaterial.getStack(Names.CHAOS_ESSENCE,
        Config.CHAOS_ESSENCE_PER_ORE);
    ItemStack chaosOre = new ItemStack(this);
    GameRegistry.addSmelting(this, chaosEssence, 0.5f);

    // Redstone furnace
//    ThermalExpansionHelper.addFurnaceRecipe(1600, chaosOre, chaosEssence);

    // Pulverizer
//    ItemStack chaosEssenceBonus = CraftingMaterial.getStack(Names.CHAOS_ESSENCE,
//        2 * Config.CHAOS_ESSENCE_PER_ORE);
//    ThermalExpansionHelper.addPulverizerRecipe(4000, chaosOre, chaosEssenceBonus);

    // Sag Mill
    RecipeHelper.addSagMillRecipe("Chaos", 4000, "");
  }

  @Override
  public void addThaumcraftStuff() {

//    ThaumcraftApi.addSmeltingBonus(new ItemStack(this),
//        CraftingMaterial.getStack(Names.CHAOS_ESSENCE_SHARD, 0));
  }
}
