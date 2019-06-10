package gigabit101.EnderBags;

import gigabit101.EnderBags.gui.GuiEnderBag;
import gigabit101.EnderBags.init.ModRegistry;
import gigabit101.EnderBags.items.IColorable;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = EnderBags.MODID, value = Dist.CLIENT, bus = Bus.MOD)
public class ClientHandler {

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent e) {
		ScreenManager.registerFactory(ModRegistry.ENDERBAG, GuiEnderBag::new);
		MinecraftForge.EVENT_BUS.addListener(ClientHandler::registerColors);
	}

	public static void registerColors(ColorHandlerEvent.Item e) {
		e.getItemColors().register(new IItemColor() {
			@Override
			public int getColor(ItemStack stack, int tintIndex) {
				return ((IColorable) stack.getItem()).getColor(stack, tintIndex);
			}
		}, ModRegistry.BAGS.values().toArray(new Item[0]));
	}
}
