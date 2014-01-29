package dmillerw.industrialization.block.tile;

import dmillerw.industrialization.block.BlockHandler;
import dmillerw.industrialization.util.UtilEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by Dylan Miller on 1/19/14
 */
public class TileBlockDetector extends TileDetector {

    @Override
    public boolean targetDetected() {
        for (int i=1; i<=range; i++) {
            if (targetAtLocation(xCoord + (orientation.offsetX * i),  yCoord + (orientation.offsetY * i), zCoord + (orientation.offsetZ * i))) {
                return true;
            }
        }
        
        return false;
    }

    private boolean targetAtLocation(int x, int y, int z) {
        ItemStack targetStack = target.getStackInSlot(0);

        int id = worldObj.getBlockId(x, y, z);
        int meta = worldObj.getBlockMetadata(x, y, z);

        Block block = Block.blocksList[id];

        String targetOreID = OreDictionary.getOreName(OreDictionary.getOreID(targetStack));
        String oreID = OreDictionary.getOreName(OreDictionary.getOreID(new ItemStack(id, 0, meta)));
        
        if (block != null && targetStack == null) {
            return true;
        }

        if (block == null || targetStack == null) {
            return false;
        }

        return (targetStack.itemID == id && (ignoreMeta || targetStack.getItemDamage() == meta) && !useOreDict) || (useOreDict && (targetOreID.equals(oreID)));
    }
    
}
