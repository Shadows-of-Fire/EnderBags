package gigabit101.EnderBags.container;

import gigabit101.EnderBags.config.EnderBagConfig;
import gigabit101.EnderBags.init.ModRegistry;
import gigabit101.EnderBags.items.ItemEnderBag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by Gigabit101 on 03/05/2016.
 */

public class ContainerEnderBag extends ContainerBase {

	private ItemStackHandler inv;
	private Hand bagHand;
	private ItemStack bag;

	public ContainerEnderBag(int id, PlayerInventory pInv) {
		super(ModRegistry.ENDERBAG, id);
		bag = pInv.player.getHeldItemMainhand();
		bagHand = Hand.MAIN_HAND;
		if (bag.isEmpty() || !(bag.getItem() instanceof ItemEnderBag)) {
			bag = pInv.player.getHeldItemOffhand();
			bagHand = Hand.OFF_HAND;
		}

		inv = ItemEnderBag.getHandlerForContainer(bag);

		int j;
		int k;

		for (j = 0; j < 8; ++j) {
			for (k = 0; k < 13; ++k) {
				this.addSlot(new SlotItemHandler(inv, k + j * 13, 12 + k * 18, 5 + j * 18) {
					@Override
					public boolean isItemValid(ItemStack stack) {
						return !EnderBagConfig.INSTANCE.realBlacklist.contains(stack.getItem());
					}
				});
			}
		}

		for (j = 0; j < 3; ++j) {
			for (k = 0; k < 9; ++k) {
				this.addSlot(new Slot(pInv, k + j * 9 + 9, 48 + k * 18, 152 + j * 18));
			}
		}

		for (j = 0; j < 9; ++j) {
			if (pInv.currentItem == j) {
				this.addSlot(new SlotLocked(pInv, j, 48 + j * 18, 210));
			} else {
				addSlot(new Slot(pInv, j, 48 + j * 18, 210));
			}
		}
	}

	@Override
	public boolean canInteractWith(PlayerEntity player) {
		return !bag.isEmpty() && player.getHeldItem(bagHand).getItem() instanceof ItemEnderBag;
	}

	@Override
	public void onContainerClosed(PlayerEntity player) {
		if (!bag.hasTag()) bag.setTag(new CompoundNBT());
		bag.getTag().put("inv", inv.serializeNBT());
		super.onContainerClosed(player);
	}
}
