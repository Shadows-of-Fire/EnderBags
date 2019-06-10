package gigabit101.EnderBags.items;

import gigabit101.EnderBags.EnderBags;
import gigabit101.EnderBags.container.ContainerEnderBag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Created by Gigabit101 on 06/05/2016.
 */

public class ItemEnderBag extends Item implements IColorable, INamedContainerProvider {

	protected DyeColor color;
	protected final int colorV;

	public ItemEnderBag(DyeColor color) {
		super(new Item.Properties().maxStackSize(1).group(EnderBags.TAB));
		this.color = color;
		setRegistryName(EnderBags.MODID, color.getName() + "_bag");
		float[] vals = color.getColorComponentValues();
		int[] rgb = new int[3];
		for (int i = 0; i < 3; i++)
			rgb[i] = (int) (255 * vals[i]);
		int value = ((255 & 0xFF) << 24) | ((rgb[0] & 0xFF) << 16) | ((rgb[1] & 0xFF) << 8) | ((rgb[2] & 0xFF) << 0);
		colorV = value;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		if (!world.isRemote) NetworkHooks.openGui((ServerPlayerEntity) player, this);
		return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
	}

	@Override
	public int getColor(ItemStack stack, int tint) {
		return colorV;
	}

	public static ItemStackHandler getHandlerForContainer(ItemStack stack) {
		if (stack.isEmpty()) return null;
		ItemStackHandler handler = new ItemStackHandler(104);
		if (stack.hasTag()) handler.deserializeNBT(stack.getTag().getCompound("inv"));
		return handler;
	}

	@Override
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity player) {
		return new ContainerEnderBag(id, inv);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent(this.getTranslationKey());
	}

}
