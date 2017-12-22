package gigabit101.EnderBags.init;

import java.util.ArrayList;

import gigabit101.EnderBags.EnderBags;
import gigabit101.EnderBags.items.ItemEnderBag;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Created by Gigabit101 on 06/05/2016.
 */

public class RecipeColour extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	public RecipeColour() {
		setRegistryName(EnderBags.MODID, "bag_coloring");
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		ItemStack itemStack = ItemStack.EMPTY;
		ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();

		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack currentStack = inv.getStackInSlot(i);

			if (!currentStack.isEmpty()) {
				if (currentStack.getItem() instanceof ItemEnderBag) {
					if (!itemStack.isEmpty()) { return false; }

					itemStack = currentStack;
				} else {
					if (currentStack.getItem() != Items.DYE) { return false; }

					arrayList.add(currentStack);
				}
			}
		}
		return !itemStack.isEmpty() && !arrayList.isEmpty();
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack itemStack = ItemStack.EMPTY;
		boolean isBag = false;
		int newcolour = 0;
		int i = 0;

		for (i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack currentStack = inv.getStackInSlot(i);
			if (!currentStack.isEmpty()) {
				if (currentStack.getItem() instanceof ItemEnderBag) {
					isBag = true;
					itemStack = currentStack.copy();
					itemStack.setCount(1);
				} else {
					if (currentStack.getItem() != Items.DYE) {
						return ItemStack.EMPTY;
					} else {
						newcolour = EnumDyeColor.byMetadata(currentStack.getItemDamage()).getDyeDamage();
					}
				}
			}
		}

		if (!isBag) {
			return ItemStack.EMPTY;
		} else {
			itemStack.setItemDamage(newcolour);
			ItemStack outputstack = itemStack;
			return outputstack;
		}
	}

	@Override
	public boolean canFit(int width, int height) {
		return width * height >= 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		int i = 0;
		for (i = 0; i < inv.getSizeInventory(); ++i) {
			inv.decrStackSize(i, 1);
		}
		return NonNullList.create();
	}
}