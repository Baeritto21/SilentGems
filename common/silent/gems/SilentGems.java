package silent.gems;

import java.util.Random;

import net.minecraftforge.common.MinecraftForge;
import silent.gems.block.ModBlocks;
import silent.gems.configuration.Config;
import silent.gems.core.handler.GemsEventHandler;
import silent.gems.core.proxy.CommonProxy;
import silent.gems.core.registry.SRegistry;
import silent.gems.enchantment.ModEnchantments;
import silent.gems.item.ModItems;
import silent.gems.lib.Reference;
import silent.gems.lib.buff.ChaosBuff;
import silent.gems.world.GemsWorldGenerator;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER)
//@NetworkMod(clientSideRequired = true, serverSideRequired = true, channels = { Reference.CHANNEL_NAME }, packetHandler = PacketHandler.class)
public class SilentGems {

	public Random random = new Random();
	
	@Instance(Reference.MOD_ID)
	public static SilentGems instance;
	
	@SidedProxy(clientSide = "silent.gems.core.proxy.ClientProxy", serverSide = "silent.gems.core.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		//LogHelper.init();
		
		Config.init(event.getSuggestedConfigurationFile());
		
		ModBlocks.init();
		ModItems.init();
		ModEnchantments.init();
		ChaosBuff.init();
		
		ModItems.initItemRecipes();
		
		SRegistry.addRecipesAndOreDictEntries();
		ModItems.addRandomChestGenLoot();
		
		Config.save();
		
		MinecraftForge.EVENT_BUS.register(new GemsEventHandler());
		FMLCommonHandler.instance().bus().register(new GemsEventHandler());
	}
	
	@EventHandler
    public void load(FMLInitializationEvent event) {
	
        proxy.registerTileEntities();
        proxy.registerRenderers();
        proxy.registerKeyHandlers();
        
        GameRegistry.registerWorldGenerator(new GemsWorldGenerator(), 0);
	}
	
	@EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        // TODO
    }
	
//	@EventHandler
//	public void serverLoad(FMLServerStartingEvent event) {
//	    
//	    NetworkRegistry.INSTANCE.newChannel(Reference.CHANNEL_NAME, new PacketHandler());
//	}
}
