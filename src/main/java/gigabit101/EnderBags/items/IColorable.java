package gigabit101.EnderBags.items;

import net.minecraft.item.ItemStack;

public interface IColorable
{
    int getColorFromItemStack(ItemStack stack, int tintIndex);
}
