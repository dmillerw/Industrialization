package dmillerw.industrialization.block;

import dmillerw.industrialization.Industrialization;
import dmillerw.industrialization.block.tile.TileBlockDetector;
import dmillerw.industrialization.block.tile.TileCore;
import dmillerw.industrialization.core.handler.GuiHandler;
import dmillerw.industrialization.lib.ModInfo;
import dmillerw.industrialization.util.UtilEntity;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * Created by Dylan Miller on 1/19/14
 */
public class BlockUtilityRedstone extends BlockCore {

    public static final String[] NAMES = new String[] {"block_detector"};

    public Icon[] icons;

    public BlockUtilityRedstone(int id) {
        super(id, Material.rock);

        setHardness(2F);
        setResistance(2F);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        if (!world.isRemote) {
            TileCore tile = (TileCore) world.getBlockTileEntity(x, y, z);

            if (tile != null) {
                if (tile instanceof TileBlockDetector) {
                    ((TileBlockDetector)tile).orientation = UtilEntity.determine3DOrientation_Forge(world, x, y, z, entity);
                    world.markBlockForUpdate(x, y, z);
                }
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fx, float fy, float fz) {
        if (!world.isRemote) {
            if (!player.isSneaking()) {
                int meta = world.getBlockMetadata(x, y, z);

                switch(meta) {
                    case 0: {
                        player.openGui(Industrialization.instance, GuiHandler.GUI_BLOCK_DETECTOR, world, x, y, z);
                        break;
                    }
                }

                return true;
            } else {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        if (!world.isRemote) {
            TileCore tile = (TileCore) world.getBlockTileEntity(x, y, z);

            if (tile != null) {
                return tile.getSignalStrength(ForgeDirection.getOrientation(side));
            }
        }

        return 0;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
        TileCore tile = (TileCore) world.getBlockTileEntity(x, y, z);

        if (tile != null) {
            return tile.getSignalStrength(ForgeDirection.getOrientation(side));
        }

        return 0;
    }

    @Override
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
        TileCore tile = (TileCore) world.getBlockTileEntity(x, y, z);

        if (tile != null) {
            if (tile instanceof TileBlockDetector) {
                return (ForgeDirection.getOrientation(side) == ((TileBlockDetector)tile).orientation) ? icons[0] : icons[1];
            }
        }

        return getIcon(side, world.getBlockMetadata(x, y, z));
    }

    @Override
    public Icon getIcon(int side, int meta) {
        return icons[meta];
    }

    @Override
    public void registerIcons(IconRegister register) {
        this.icons = new Icon[2];

        this.icons[0] = register.registerIcon(ModInfo.RESOURCE_PREFIX + "utility/block_detector_front");
        this.icons[1] = register.registerIcon(ModInfo.RESOURCE_PREFIX + "utility/block_detector_side");
    }

    @Override
    public TileCore getTile(int meta) {
        switch(meta) {
            case 0: return new TileBlockDetector();
        }
        return null;
    }

}
