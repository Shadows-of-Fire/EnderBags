package gigabit101.EnderBags.init;

import gigabit101.EnderBags.EnderBags;
import gigabit101.EnderBags.config.ConfigEnderBag;
import gigabit101.EnderBags.items.ItemEnderBag;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

public class ModRegistry {

	public static final ItemEnderBag ENDERBAG = new ItemEnderBag();

	@SubscribeEvent
	public void items(Register<Item> e) {
		e.getRegistry().register(ENDERBAG);
	}

	@SubscribeEvent
	public void recipes(Register<IRecipe> e) {
		if (!ConfigEnderBag.disableEnderBagDyes) e.getRegistry().register(new RecipeColour());

		if (!ConfigEnderBag.disableEnderBagRecipe) EnderBags.HELPER.addForgeShaped(ENDERBAG, "WSW", "WCW", "WWW", 'S', Items.STRING, 'C', new ItemStack(Blocks.ENDER_CHEST), 'W', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE));

		e.getRegistry().registerAll(EnderBags.INFO.getRecipeList().toArray(new IRecipe[1]));
	}
}
