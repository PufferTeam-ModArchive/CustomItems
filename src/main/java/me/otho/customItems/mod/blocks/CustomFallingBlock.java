package me.otho.customItems.mod.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import me.otho.customItems.CustomItems;

public class CustomFallingBlock extends BlockFalling implements IMMBlock {

    public CustomFallingBlock() {
        this(Material.sand);
    }

    public CustomFallingBlock(Material material) {
        super(material);
    }

    private IIcon[] icons = new IIcon[6];
    private boolean canSilkHarvest;

    private int maxItemDrop;

    private int minItemDrop;
    private int eachExtraItemDropChance;

    private String dropItem;

    private String[] textureNames;
    private boolean breaks;

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
        Block i1 = par1IBlockAccess.getBlock(par2, par3, par4);
        return i1 == this ? false : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        if (!this.opaque) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getRenderType() {
        return 0;
    }

    private int getItemDropQuantity(World world, int fortune) {
        int ret = 0;
        int i;
        ret = this.minItemDrop;
        for (i = this.minItemDrop; i < this.maxItemDrop + fortune; i++) {
            boolean willDrop = world.rand.nextInt(100) < this.eachExtraItemDropChance;
            if (willDrop) {
                ret++;
            }
        }

        return ret;
    }

    @Override
    public void setMaxItemDrop(int maxItemDrop) {
        this.maxItemDrop = maxItemDrop;
    }

    @Override
    public void setMinItemDrop(int minItemDrop) {
        this.minItemDrop = minItemDrop;
    }

    @Override
    public void setEachExtraItemDropChance(int eachExtraItemDropChance) {
        this.eachExtraItemDropChance = eachExtraItemDropChance;
    }

    @Override
    public void setDropItem(String dropItem) {
        this.dropItem = dropItem;
    }

    @Override
    public void setOpaque(boolean isOpaque) {
        this.opaque = isOpaque;
        this.lightOpacity = this.isOpaqueCube() ? 255 : 0;
    }

    @Override
    public void setCanSilkHarvest(boolean canSilkHarvest) {
        this.canSilkHarvest = canSilkHarvest;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (this.textureName != null) {
            return blockIcon;
        } else {
            return icons[side];
        }
    }

    @Override
    public boolean renderAsNormalBlock() {
        return true;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList();

        if (dropItem != null) {
            String[] parser = dropItem.split(":");
            Item item = GameRegistry.findItem(parser[0], parser[1]);

            if (item != null) {
                int itemQuantity = getItemDropQuantity(world, fortune);
                int damage;

                if (parser.length > 2) {
                    damage = Integer.parseInt(parser[2]);
                } else {
                    damage = 0;
                }
                drops.add(new ItemStack(item, itemQuantity, damage));
            }
        } else {
            if (!breaks) {
                drops.add(new ItemStack(Item.getItemFromBlock(this)));
            }
        }

        return drops;
    }

    @Override
    public String getUnlocalizedName() {
        return super.getUnlocalizedName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        if (textureNames == null) {
            blockIcon = iconRegister.registerIcon(CustomItems.MOD_ID.toLowerCase() + ":" + this.textureName);
        } else {
            for (int i = 0; i < icons.length; i++) {
                icons[i] = iconRegister.registerIcon(CustomItems.MOD_ID.toLowerCase() + ":" + textureNames[i]);
            }
        }
    }

    @Override
    public void registerBlockTextures(String[] textureNames) {
        this.textureNames = textureNames;
    }

    @Override
    public boolean isOpaqueCube() {
        return this.opaque;
    }

    @Override
    public boolean canSilkHarvest() {
        return this.canSilkHarvest;
    }

    @Override
    public void setBreaks(boolean breaks) {
        this.breaks = breaks;
    }

    @Override
    public void setCollides(boolean collides) {
        // TODO Auto-generated method stub

    }
}
