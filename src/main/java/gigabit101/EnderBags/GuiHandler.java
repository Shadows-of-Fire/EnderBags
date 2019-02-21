package gigabit101.EnderBags;

import gigabit101.EnderBags.gui.GuiEnderBag;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class GuiHandler {

	public static GuiEnderBag getClientGuiElement(FMLPlayMessages.OpenContainer msg) {
		return new GuiEnderBag();
	}

}
