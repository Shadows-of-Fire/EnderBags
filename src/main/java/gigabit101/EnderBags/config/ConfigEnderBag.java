package gigabit101.EnderBags.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import gigabit101.EnderBags.EnderBags;
import gigabit101.EnderBags.init.ModRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by Gigabit101 on 06/05/2016.
 */

public class ConfigEnderBag {

	public static boolean disableEnderBagDyes = false;
	public static boolean disableEnderBagRecipe = false;
	public static final List<Pair<Item, Integer>> BLACKLIST = new ArrayList<>();
	private static String[] blacklist;

	public static void load(Configuration config) {
		config.load();

		disableEnderBagDyes = config.getBoolean("Disable Dye Recipe", "recipes", false, "Setting to true will disable coloring of ender bags.");
		disableEnderBagRecipe = config.getBoolean("Disable Recipe", "recipes", false, "Setting to true will disable the recipe for ender bags.");
		blacklist = config.getStringList("blacklist", "blacklist", new String[] { ModRegistry.ENDERBAG.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE }, "A list of item registry names that are not allowed in ender bags.  Format is modid:name:meta, and using a meta of 32767 blocks all metas.");

		if (config.hasChanged()) config.save();

	}

	public static void parseBlacklist() {
		for (String s : blacklist) {
			String[] split = s.split(":");
			if (split.length != 3) {
				EnderBags.logger.error("Found invalid blacklist entry: " + s + ", it will be ignored.");
				continue;
			}

			Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(split[0], split[1]));
			BLACKLIST.add(Pair.of(item, Integer.parseInt(split[2])));
		}
	}

	public static boolean isBlacklistedFromBag(ItemStack stack) {
		for (Pair<Item, Integer> p : BLACKLIST) {
			if (p.getLeft() == stack.getItem() && (p.getRight() == OreDictionary.WILDCARD_VALUE || p.getRight() == stack.getMetadata())) return true;
		}
		return false;
	}
}
