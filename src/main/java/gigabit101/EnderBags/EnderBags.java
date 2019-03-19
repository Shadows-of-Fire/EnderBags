package gigabit101.EnderBags;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gigabit101.EnderBags.config.EnderBagConfig;
import gigabit101.EnderBags.init.ModRegistry;
import gigabit101.EnderBags.init.RecipeColour;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * Created by Gigabit101 on 06/05/2016.
 */

@Mod(EnderBags.MODID)
public class EnderBags {

	public static final String MODID = "ender_bags";
	public static final ItemGroup TAB = new ItemGroup(MODID) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModRegistry.BAGS.get(EnumDyeColor.WHITE));
		}
	};
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static final RecipeHelper RECIPES = new RecipeHelper(MODID, "Ender Bags");

	public EnderBags() {
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, ModRegistry::items);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(EnderBagConfig::onLoad);
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> GuiHandler::getClientGuiElement);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EnderBagConfig.SPEC);
	}

	@SubscribeEvent
	public void setup(FMLCommonSetupEvent e) {
		RECIPES.addRecipe(new RecipeColour(null));
		Block ww = Blocks.WHITE_WOOL;
		RECIPES.addShaped(ModRegistry.BAGS.get(EnumDyeColor.WHITE), 3, 3, ww, Items.STRING, ww, ww, Blocks.ENDER_CHEST, ww, ww, ww, ww);
	}
}
