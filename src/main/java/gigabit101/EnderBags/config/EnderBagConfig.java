package gigabit101.EnderBags.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import gigabit101.EnderBags.EnderBags;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = EnderBags.MODID)
public class EnderBagConfig {

	public static final ForgeConfigSpec SPEC;
	public static final EnderBagConfig INSTANCE;
	static {
		Pair<EnderBagConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(EnderBagConfig::new);
		SPEC = specPair.getRight();
		INSTANCE = specPair.getLeft();
	}

	public final ConfigValue<List<? extends String>> blacklist;
	public final List<Item> realBlacklist = new ArrayList<>();

	public EnderBagConfig(ForgeConfigSpec.Builder build) {
		build.comment("Server configuration");
		build.push("server");
		List<String> def = new ArrayList<>();
		for (DyeColor e : DyeColor.values()) {
			def.add(EnderBags.MODID + ":" + e.getTranslationKey() + "_bag");
		}
		blacklist = build.comment("A list of item registry names that are not allowed in ender bags.  Format is modid:name.").defineList("blacklist", def, (x) -> true);
		build.pop();
	}

	public void parseBlacklist() {
		realBlacklist.clear();
		for (String s : blacklist.get()) {
			Item i = ForgeRegistries.ITEMS.getValue(new ResourceLocation(s));
			if (i == null || i == Items.AIR) {
				EnderBags.LOGGER.error("Invalid config entry {} will be ignored from blacklist.", s);
				continue;
			}
			realBlacklist.add(i);
		}
	}

	@SubscribeEvent
	public static void onLoad(ModConfig.Loading e) {
		if (e.getConfig().getModId().equals(EnderBags.MODID)) INSTANCE.parseBlacklist();
	}

}
