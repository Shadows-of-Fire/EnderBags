package gigabit101.EnderBags.proxy;

import gigabit101.EnderBags.init.ModRegistry;
import gigabit101.EnderBags.items.IColorable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Gigabit101 on 06/05/2016.
 */
public class ClientProxy extends CommonProxy {

	@Override
	public void preInit() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void models(ModelRegistryEvent e) {
		ModRegistry.ENDERBAG.initModels(e);
	}

	@Override
	public void registerColors() {
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {
				Item item = stack.getItem();
				if (item instanceof IColorable) {
					return ((IColorable) item).getColorFromItemStack(stack, tintIndex);
				} else {
					return 0xFFFFFF;
				}
			}
		}, ModRegistry.ENDERBAG);
	}
}
