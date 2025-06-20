package me.otho.customItems.mod.items.tools;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemAxe;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import me.otho.customItems.CustomItems;

public class CustomAxe extends ItemAxe {

    public CustomAxe(ToolMaterial mat) {
        super(mat);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(CustomItems.MOD_ID.toLowerCase() + ":" + this.iconString);
    }
}
