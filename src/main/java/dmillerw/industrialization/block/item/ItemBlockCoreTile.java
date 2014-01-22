package dmillerw.industrialization.block.item;

import dmillerw.industrialization.block.tile.TileCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by Dylan Miller on 1/21/14
 */
public class ItemBlockCoreTile extends ItemBlockCore {

    public ItemBlockCoreTile(int id) {
        super(id);
    }

    @Override
    public boolean postBlockPlace(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz, int meta) {
        TileCore tile = (TileCore) world.getBlockTileEntity(x, y, z);

        if (tile != null) {
            tile.onBlockAdded(player);
        } else {
            return false;
        }

        return true;
    }

}
