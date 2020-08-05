package gigabit101.EnderBags.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import gigabit101.EnderBags.EnderBags;
import gigabit101.EnderBags.container.ContainerEnderBag;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/**
 * Created by Gigabit101 on 06/05/2016.
 */
public class GuiEnderBag extends ContainerScreen<ContainerEnderBag> {

	private static final ResourceLocation texture = new ResourceLocation(EnderBags.MODID, "textures/gui/bag.png");
	private final PlayerEntity player;

	public GuiEnderBag(ContainerEnderBag container, PlayerInventory inv, ITextComponent title) {
		super(container, inv, title);
		this.xSize = 256;
		this.ySize = 231;
		this.player = inv.player;
	}

	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(stack);
		super.render(stack, mouseX, mouseY, partialTicks);
		this.drawMouseoverTooltip(stack, mouseX, mouseY);
	}

	@Override
	protected void drawBackground(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
		this.getMinecraft().getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexture(stack, k, l, 0, 0, this.xSize, this.ySize);
	}

	@Override
	public void tick() {
		if (!this.container.canInteractWith(player)) player.closeScreen();
		super.tick();
	}

	@Override
	protected void drawForeground(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {

	}
}
