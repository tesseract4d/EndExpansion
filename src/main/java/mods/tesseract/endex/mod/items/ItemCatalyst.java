package mods.tesseract.endex.mod.items;

import mods.tesseract.endex.init.ItemInit;
import mods.tesseract.endex.EndEx;
import mods.tesseract.endex.utils.IHasModel;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemCatalyst extends Item implements IHasModel
{
    public ItemCatalyst(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(EndEx.endertab);

        ItemInit.ITEMS.add(this);
    }
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(I18n.format("tile.catalyst.tooltip"));
    }
    @Override
    public void registerModels()
    {
        EndEx.proxy.registerItemRenderer(this, 0, "inventory");
    }
    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.RARE;
    }
}