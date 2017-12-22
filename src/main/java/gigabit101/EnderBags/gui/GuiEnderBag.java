package gigabit101.EnderBags.gui;

import gigabit101.EnderBags.container.ContainerEnderBag;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Gigabit101 on 06/05/2016.
 */
public class GuiEnderBag extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation("enderbags", "textures/gui/bag.png");
	public EntityPlayer player;

	public GuiEnderBag(EntityPlayer player) {
		super(new ContainerEnderBag(player));
		this.player = player;
		this.xSize = 256;
		this.ySize = 231;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.mc.getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
		public void updateScreen() {
			if(!inventorySlots.canInteractWith(player)) player.closeScreen();
			super.updateScreen();
		}
}
