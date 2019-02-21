package gigabit101.EnderBags;

import gigabit101.EnderBags.init.ModRegistry;
import gigabit101.EnderBags.items.IColorable;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = EnderBags.MODID, value = Dist.CLIENT)
public class ClientHandler {

	@SubscribeEvent
	public static void registerColors(ColorHandlerEvent.Item e) {
		e.getItemColors().register(new IItemColor() {
			@Override
			public int getColor(ItemStack stack, int tintIndex) {
				return ((IColorable) stack.getItem()).getColor(stack, tintIndex);
			}
		}, ModRegistry.BAGS.values().toArray(new Item[0]));
	}
}
