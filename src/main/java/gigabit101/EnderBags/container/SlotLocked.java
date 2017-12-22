package gigabit101.EnderBags.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Travis on 06/05/2016.
 */
public class SlotLocked extends Slot {
	public SlotLocked(IInventory inv, int index, int x, int y) {
		super(inv, index, x, y);
	}

	@Override
	public boolean canTakeStack(EntityPlayer p_82869_1_) {
		return false;
	}

	@Override
	public boolean isItemValid(ItemStack p_75214_1_) {
		return false;
	}
}
