package gigabit101.EnderBags;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gigabit101.EnderBags.config.ConfigEnderBag;
import gigabit101.EnderBags.init.ModRegistry;
import gigabit101.EnderBags.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import shadows.placebo.registry.RegistryInformation;
import shadows.placebo.util.RecipeHelper;

/**
 * Created by Gigabit101 on 06/05/2016.
 */

@Mod(modid = EnderBags.MODID, name = EnderBags.MODNAME, version = EnderBags.VERSION, dependencies = "required-after:placebo@[1.1.3,)")
public class EnderBags {

	public static final String MODID = "enderbags";
	public static final String MODNAME = "EnderBags";
	public static final String VERSION = "2.0.0";

	@Instance
	public static EnderBags INSTANCE;

	@SidedProxy(clientSide = "gigabit101.EnderBags.proxy.ClientProxy", serverSide = "gigabit101.EnderBags.proxy.CommonProxy")
	public static CommonProxy PROXY;

	public static final CreativeTabs TAB = new CreativeTabs(MODID) {

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModRegistry.ENDERBAG);
		}

	};

	public static final RegistryInformation INFO = new RegistryInformation(MODID, TAB);
	public static final RecipeHelper HELPER = new RecipeHelper(MODID, MODNAME, INFO.getRecipeList());

	public static Configuration config;

	public static Logger logger = LogManager.getLogger(MODID);

	@EventHandler
	public static void preInit(FMLPreInitializationEvent e) {
		config = new Configuration(e.getSuggestedConfigurationFile());
		ConfigEnderBag.load(config);
		MinecraftForge.EVENT_BUS.register(new ModRegistry());
		PROXY.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		ConfigEnderBag.parseBlacklist();
		NetworkRegistry.INSTANCE.registerGuiHandler(MODID, new GuiHandler());
		PROXY.registerColors();
	}
}
