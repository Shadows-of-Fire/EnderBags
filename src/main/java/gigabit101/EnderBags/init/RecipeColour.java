package gigabit101.EnderBags.init;

import gigabit101.EnderBags.EnderBags;
import gigabit101.EnderBags.items.ItemEnderBag;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Created by Gigabit101 on 06/05/2016.
 */

public class RecipeColour implements ICraftingRecipe {

	public RecipeColour(ResourceLocation id) {

	}

	@Override
	public boolean matches(CraftingInventory inv, World world) {
		ItemStack bag = ItemStack.EMPTY, dye = ItemStack.EMPTY;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack s = inv.getStackInSlot(i);
			if (s.getItem() instanceof ItemEnderBag) {
				if (!bag.isEmpty()) return false;
				bag = s;
			} else if (s.getItem() instanceof DyeItem) {
				if (!dye.isEmpty()) return false;
				dye = s;
			} else if (!s.isEmpty()) return false;
		}
		return !bag.isEmpty() && !dye.isEmpty();
	}

	@Override
	public ItemStack getCraftingResult(CraftingInventory inv) {
		ItemStack bag = ItemStack.EMPTY, dye = ItemStack.EMPTY;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack s = inv.getStackInSlot(i);
			if (s.getItem() instanceof ItemEnderBag) bag = s;
			else if (s.getItem() instanceof DyeItem) dye = s;
		}
		if (bag.isEmpty() || dye.isEmpty()) return ItemStack.EMPTY;
		ItemStack out = new ItemStack(ModRegistry.BAGS.get(((DyeItem) dye.getItem()).getDyeColor()));
		out.setTag(bag.getTag());
		return out;
	}

	@Override
	public boolean canFit(int width, int height) {
		return width * height >= 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}

	static ResourceLocation id = new ResourceLocation(EnderBags.MODID, "bag_color");

	@Override
	public ResourceLocation getId() {
		return id;
	}

	static SpecialRecipeSerializer<RecipeColour> serializer = new SpecialRecipeSerializer<>(RecipeColour::new);

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return serializer;
	}

	@Override
	public boolean isDynamic() {
		return true;
	}
}