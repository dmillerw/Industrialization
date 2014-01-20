package dmillerw.industrialization.block;

import dmillerw.industrialization.block.tile.TileBlockDetector;
import dmillerw.industrialization.block.tile.TileCore;
import dmillerw.industrialization.lib.ModInfo;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * Created by Dylan Miller on 1/19/14
 */
public class BlockUtilityRedstone extends BlockCore {

    public static final String[] NAMES = new String[] {"Block Detector"};

    public Icon[] icons;

    public BlockUtilityRedstone(int id) {
        super(id, Material.circuits);

        setHardness(2F);
        setResistance(2F);
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
