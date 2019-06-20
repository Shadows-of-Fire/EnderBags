package gigabit101.EnderBags;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gigabit101.EnderBags.config.EnderBagConfig;
import gigabit101.EnderBags.init.ModRegistry;
import gigabit101.EnderBags.init.RecipeColor;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
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
			return new ItemStack(ModRegistry.BAGS.get(DyeColor.WHITE));
		}
	};
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static final RecipeHelper RECIPES = new RecipeHelper(MODID, "Ender Bags");

	public EnderBags() {
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, ModRegistry::items);
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ContainerType.class, ModRegistry::containers);
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(IRecipeSerializer.class, ModRegistry::serializers);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(EnderBagConfig::onLoad);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EnderBagConfig.SPEC);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void setup(FMLCommonSetupEvent e) {
		RECIPES.addRecipe(new RecipeColor(new ResourceLocation(MODID, "bag_color")));
		Block ww = Blocks.WHITE_WOOL;
		RECIPES.addShaped(ModRegistry.BAGS.get(DyeColor.WHITE), 3, 3, ww, Items.STRING, ww, ww, Blocks.ENDER_CHEST, ww, ww, ww, ww);
	}

	@SubscribeEvent
	public void onPickup(EntityItemPickupEvent e) {
		if (e.getItem().getItem().getItem() == Blocks.ENDER_CHEST.asItem()) {
			PlayerEntity player = e.getEntityPlayer();
			player.unlockRecipes(RECIPES.getRecipes());
		}
	}

	@SubscribeEvent
	public void craft(ItemCraftedEvent e) {
		if (e.getCrafting().getItem() == Blocks.ENDER_CHEST.asItem()) {
			PlayerEntity player = e.getPlayer();
			player.unlockRecipes(RECIPES.getRecipes());
		}
	}
}
