package dmillerw.industrialization.block.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * Created by Dylan Miller on 1/24/14
 */
public class ItemBlockAnchor extends ItemBlockCore {

    public ItemBlockAnchor(int id) {
        super(id);
    }

    public boolean preBlockPlace(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz, int meta) {
        ForgeDirection sideForge = ForgeDirection.getOrientation(side);

        int mx = x + sideForge.getOpposite().offsetX;
        int my = y + sideForge.getOpposite().offsetY;
        int mz = z + sideForge.getOpposite().offsetZ;

        if (world.isAirBlock(mx, my,mz)) {
            return false;
        }

        if (sideForge == ForgeDirection.UP) {
            return false;
        }

        if (!world.isBlockSolidOnSide(mx, my, mz, sideForge)) {
            return false;
        }

        return true;
    }

    public boolean postBlockPlace(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz, int meta) {
        world.setBlockMetadataWithNotify(x, y, z, ForgeDirection.getOrientation(side).getOpposite().ordinal(), 3);
        return true;
    }

}
