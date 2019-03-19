package gigabit101.EnderBags.gui;

import gigabit101.EnderBags.EnderBags;
import gigabit101.EnderBags.container.ContainerEnderBag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Gigabit101 on 06/05/2016.
 */
public class GuiEnderBag extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation(EnderBags.MODID, "textures/gui/bag.png");
	private final EntityPlayer player = Minecraft.getInstance().player;

	public GuiEnderBag() {
		super(new ContainerEnderBag(Minecraft.getInstance().player));
		this.xSize = 256;
		this.ySize = 231;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.mc.getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		GlStateManager.color3f(1, 1, 1);
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}

	@Override
	public void tick() {
		if (!inventorySlots.canInteractWith(player)) player.closeScreen();
		super.tick();
	}
}
