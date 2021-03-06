package dmillerw.industrialization.block;

import dmillerw.industrialization.block.tile.TileConveyor;
import dmillerw.industrialization.block.tile.TileCore;
import dmillerw.industrialization.client.render.block.RenderBlockConveyor;
import dmillerw.industrialization.client.render.block.SimpleBlockRenderer;
import dmillerw.industrialization.lib.ModInfo;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * Created by Dylan Miller on 1/20/14
 */
public class BlockConveyor extends BlockCore {

    private Icon[] icons;

    public BlockConveyor(int id) {
        super(id, Material.iron);

        setHardness(2F);
        setResistance(2F);
    }

    @Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
        //TODO Entity movement
    }    
    
    @Override
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
        TileCore tile = (TileCore) world.getBlockTileEntity(x, y, z);

        if (tile != null && tile instanceof TileConveyor) {
            ForgeDirection forgeDirection = ((TileConveyor)tile).orientation;

            if (side == 1) {
                return icons[0];
            } else if (side == 0) {
                return icons[2];
            } else if (side == forgeDirection.ordinal() || side == forgeDirection.getOpposite().ordinal()) {
                return icons[3];
            } else {
                return icons[1];
            }
        }

        return getIcon(side, world.getBlockMetadata(x, y, z));
    }

    @Override
    public int getRenderType() {
        return SimpleBlockRenderer.getRenderID(RenderBlockConveyor.class);
    }

    @Override
    public Icon getIcon(int side, int meta) {
        if (side == 1) {
            return icons[0];
        } else if (side == 0) {
            return icons[2];
        } else if (side == 4 || side == 5) {
            return icons[1];
        } else {
            return icons[3  ];
        }
    }

    @Override
    public void registerIcons(IconRegister register) {
        icons = new Icon[4];

        icons[0] = register.registerIcon(ModInfo.RESOURCE_PREFIX + "conveyor/conveyor_top");
        icons[1] = register.registerIcon(ModInfo.RESOURCE_PREFIX + "conveyor/conveyor_side");
        icons[2] = register.registerIcon(ModInfo.RESOURCE_PREFIX + "conveyor/conveyor_bottom");
        icons[3] = register.registerIcon(ModInfo.RESOURCE_PREFIX + "conveyor/conveyor_back");
    }

    @Override
    public TileCore getTile(int meta) {
        return meta == 0 ? new TileConveyor() : null;
    }

}
