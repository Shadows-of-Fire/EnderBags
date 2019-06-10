package gigabit101.EnderBags.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Gigabit101 on 06/05/2016.
 */

public abstract class ContainerBase extends Container {

	protected ContainerBase(ContainerType<?> type, int id) {
		super(type, id);
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int slotIndex) {
		ItemStack originalStack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(slotIndex);
		int numSlots = inventorySlots.size();
		if (slot != null && slot.getHasStack()) {
			ItemStack stackInSlot = slot.getStack();
			originalStack = stackInSlot.copy();
			if (slotIndex >= numSlots - 9 * 4 && tryShiftItem(stackInSlot, numSlots)) {
				// NOOP
			} else if (slotIndex >= numSlots - 9 * 4 && slotIndex < numSlots - 9) {
				if (!shiftItemStack(stackInSlot, numSlots - 9, numSlots)) { return ItemStack.EMPTY; }
			} else if (slotIndex >= numSlots - 9 && slotIndex < numSlots) {
				if (!shiftItemStack(stackInSlot, numSlots - 9 * 4, numSlots - 9)) { return ItemStack.EMPTY; }
			} else if (!shiftItemStack(stackInSlot, numSlots - 9 * 4, numSlots)) { return ItemStack.EMPTY; }
			slot.onSlotChange(stackInSlot, originalStack);
			if (stackInSlot.getCount() <= 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			if (stackInSlot.getCount() == originalStack.getCount()) { return ItemStack.EMPTY; }
			slot.onTake(player, stackInSlot);
		}
		return originalStack;
	}

	protected boolean shiftItemStack(ItemStack stackToShift, int start, int end) {
		boolean changed = false;
		if (stackToShift.isStackable()) {
			for (int slotIndex = start; stackToShift.getCount() > 0 && slotIndex < end; slotIndex++) {
				Slot slot = inventorySlots.get(slotIndex);
				ItemStack stackInSlot = slot.getStack();
				if (!stackInSlot.isEmpty() && canStacksMerge(stackInSlot, stackToShift)) {
					int resultingStackSize = stackInSlot.getCount() + stackToShift.getCount();
					int max = Math.min(stackToShift.getMaxStackSize(), slot.getSlotStackLimit());
					if (resultingStackSize <= max) {
						stackToShift.setCount(0);
						stackInSlot.setCount(resultingStackSize);
						slot.onSlotChanged();
						changed = true;
					} else if (stackInSlot.getCount() < max) {
						stackToShift.shrink(max - stackInSlot.getCount());
						stackInSlot.setCount(max);
						slot.onSlotChanged();
						changed = true;
					}
				}
			}
		}
		if (stackToShift.getCount() > 0) {
			for (int slotIndex = start; stackToShift.getCount() > 0 && slotIndex < end; slotIndex++) {
				Slot slot = inventorySlots.get(slotIndex);
				ItemStack stackInSlot = slot.getStack();
				if (stackInSlot.isEmpty()) {
					int max = Math.min(stackToShift.getMaxStackSize(), slot.getSlotStackLimit());
					stackInSlot = stackToShift.copy();
					stackInSlot.setCount(Math.min(stackToShift.getCount(), max));
					stackToShift.shrink(stackInSlot.getCount());
					slot.putStack(stackInSlot);
					slot.onSlotChanged();
					changed = true;
				}
			}
		}
		return changed;
	}

	private boolean tryShiftItem(ItemStack stackToShift, int numSlots) {
		for (int machineIndex = 0; machineIndex < numSlots - 9 * 4; machineIndex++) {
			Slot slot = inventorySlots.get(machineIndex);
			if (!slot.isItemValid(stackToShift)) continue;
			if (shiftItemStack(stackToShift, machineIndex, machineIndex + 1)) return true;
		}
		return false;
	}

	public static boolean canStacksMerge(ItemStack stack1, ItemStack stack2) {
		if (stack1.isEmpty() || stack2.isEmpty()) { return false; }
		if (!stack1.isItemEqual(stack2)) { return false; }
		if (!ItemStack.areItemStackTagsEqual(stack1, stack2)) { return false; }
		return true;
	}
}
