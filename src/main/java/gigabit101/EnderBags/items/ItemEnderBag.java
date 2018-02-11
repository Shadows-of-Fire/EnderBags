package gigabit101.EnderBags.items;

import java.util.List;

import gigabit101.EnderBags.EnderBags;
import gigabit101.EnderBags.GuiHandler;
import gigabit101.EnderBags.init.ModRegistry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;
import shadows.placebo.item.ItemBase;
import shadows.placebo.util.PlaceboUtil;

/**
 * Created by Gigabit101 on 06/05/2016.
 */

public class ItemEnderBag extends ItemBase implements IColorable {

	public static final String[] COLOURS = new String[] { "white", "orange", "magenta", "lightBlue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black" };

	public ItemEnderBag() {
		super("enderbag", EnderBags.INFO);
		setMaxStackSize(1);
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		int meta = itemStack.getItemDamage();
		if (meta < 0 || meta >= COLOURS.length) {
			meta = 0;
		}
		return super.getUnlocalizedName() + "." + COLOURS[meta];
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (isInCreativeTab(tab)) for (int i = 0; i < COLOURS.length; i++) {
			items.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		player.openGui(EnderBags.INSTANCE, GuiHandler.bagID, world, 0, 0, 0);
		return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		if (par1ItemStack.getItemDamage() >= EnumDyeColor.values().length) { return 0xFFFFFF; }
		return EnumDyeColor.byMetadata(par1ItemStack.getItemDamage()).getColorValue();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(TextFormatting.DARK_PURPLE + "Can be colored in a crafting table with dye");
	}

	@Override
	public void initModels(ModelRegistryEvent e) {
		for (int i = 0; i < ItemEnderBag.COLOURS.length; i++)
			PlaceboUtil.sMRL(this, i, "inventory");
	}

	public static ItemStackHandler getHandlerForContainer(ItemStack stack) {
		if (stack.isEmpty() || stack.getItem() != ModRegistry.ENDERBAG) return null;
		ItemStackHandler handler = new ItemStackHandler(104);
		if (stack.hasTagCompound()) handler.deserializeNBT(stack.getTagCompound().getCompoundTag("inv"));
		return handler;
	}

}
