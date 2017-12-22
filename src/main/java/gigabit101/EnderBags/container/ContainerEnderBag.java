package gigabit101.EnderBags.container;

import gigabit101.EnderBags.config.ConfigEnderBag;
import gigabit101.EnderBags.init.ModRegistry;
import gigabit101.EnderBags.items.ItemEnderBag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by Gigabit101 on 03/05/2016.
 */

public class ContainerEnderBag extends ContainerBase {

	private ItemStackHandler inv;
	private EnumHand bagHand;
	private ItemStack bag;

	public ContainerEnderBag(EntityPlayer player) {

		bag = player.getHeldItemMainhand();
		bagHand = EnumHand.MAIN_HAND;
		if (bag.isEmpty() || bag.getItem() != ModRegistry.ENDERBAG) {
			bag = player.getHeldItemOffhand();
			bagHand = EnumHand.OFF_HAND;
		}

		inv = ItemEnderBag.getHandlerForContainer(bag);

		int j;
		int k;

		for (j = 0; j < 8; ++j) {
			for (k = 0; k < 13; ++k) {
				this.addSlotToContainer(new SlotItemHandler(inv, k + j * 13, 12 + k * 18, 5 + j * 18) {
					@Override
					public boolean isItemValid(ItemStack stack) {
						return !ConfigEnderBag.isBlacklistedFromBag(stack);
					}
				});
			}
		}

		for (j = 0; j < 3; ++j) {
			for (k = 0; k < 9; ++k) {
				this.addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 48 + k * 18, 152 + j * 18));
			}
		}

		for (j = 0; j < 9; ++j) {
			if (player.inventory.currentItem == j) {
				this.addSlotToContainer(new SlotLocked(player.inventory, j, 48 + j * 18, 210));
			} else {
				addSlotToContainer(new Slot(player.inventory, j, 48 + j * 18, 210));
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return !bag.isEmpty() && player.getHeldItem(bagHand).getItem() == ModRegistry.ENDERBAG;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		if (!bag.hasTagCompound()) bag.setTagCompound(new NBTTagCompound());
		bag.getTagCompound().setTag("inv", inv.serializeNBT());
		super.onContainerClosed(player);
	}
}
