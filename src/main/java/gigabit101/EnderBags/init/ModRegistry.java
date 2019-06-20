package gigabit101.EnderBags.init;

import java.util.EnumMap;

import gigabit101.EnderBags.EnderBags;
import gigabit101.EnderBags.container.ContainerEnderBag;
import gigabit101.EnderBags.items.ItemEnderBag;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;

public class ModRegistry {

	public static final EnumMap<DyeColor, ItemEnderBag> BAGS = new EnumMap<>(DyeColor.class);

	@ObjectHolder("ender_bags:ender_bag")
	public static final ContainerType<ContainerEnderBag> ENDERBAG = null;

	@SubscribeEvent
	public static void items(Register<Item> e) {
		for (DyeColor color : DyeColor.values()) {
			ItemEnderBag s = new ItemEnderBag(color);
			BAGS.put(color, s);
			e.getRegistry().register(s);
		}
	}

	@SubscribeEvent
	public static void containers(Register<ContainerType<?>> e) {
		e.getRegistry().register(new ContainerType<>(ContainerEnderBag::new).setRegistryName("ender_bag"));
	}

	@SubscribeEvent
	public static void serializers(Register<IRecipeSerializer<?>> e) {
		e.getRegistry().register(RecipeColor.SERIALIZER.setRegistryName(new ResourceLocation(EnderBags.MODID, "bag_color")));
	}
}
