package gigabit101.EnderBags.init;

import gigabit101.EnderBags.EnderBags;
import gigabit101.EnderBags.items.ItemEnderBag;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.RecipeSerializers;
import net.minecraft.item.crafting.RecipeSerializers.SimpleSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Created by Gigabit101 on 06/05/2016.
 */

public class RecipeColour implements IRecipe {

	public RecipeColour(ResourceLocation id) {

	}

	@Override
	public boolean matches(IInventory inv, World world) {
		ItemStack bag = ItemStack.EMPTY, dye = ItemStack.EMPTY;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack s = inv.getStackInSlot(i);
			if (s.getItem() instanceof ItemEnderBag) {
				if (!bag.isEmpty()) return false;
				bag = s;
			} else if (s.getItem() instanceof ItemDye) {
				if (!dye.isEmpty()) return false;
				dye = s;
			} else if (!s.isEmpty()) return false;
		}
		return !bag.isEmpty() && !dye.isEmpty();
	}

	@Override
	public ItemStack getCraftingResult(IInventory inv) {
		ItemStack bag = ItemStack.EMPTY, dye = ItemStack.EMPTY;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack s = inv.getStackInSlot(i);
			if (s.getItem() instanceof ItemEnderBag) bag = s;
			else if (s.getItem() instanceof ItemDye) dye = s;
		}
		if (bag.isEmpty() || dye.isEmpty()) return ItemStack.EMPTY;
		ItemStack out = new ItemStack(ModRegistry.BAGS.get(((ItemDye) dye.getItem()).getDyeColor()));
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

	static SimpleSerializer<RecipeColour> serializer = RecipeSerializers.register(new RecipeSerializers.SimpleSerializer<>(id.toString(), RecipeColour::new));

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return serializer;
	}

	@Override
	public boolean isDynamic() {
		return true;
	}
}