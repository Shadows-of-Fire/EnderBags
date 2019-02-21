package gigabit101.EnderBags.init;

import java.util.EnumMap;

import gigabit101.EnderBags.items.ItemEnderBag;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModRegistry {

	public static final EnumMap<EnumDyeColor, ItemEnderBag> BAGS = new EnumMap<>(EnumDyeColor.class);

	@SubscribeEvent
	public static void items(Register<Item> e) {
		for (EnumDyeColor color : EnumDyeColor.values()) {
			ItemEnderBag s = new ItemEnderBag(color);
			BAGS.put(color, s);
			e.getRegistry().register(s);
		}
	}
}
