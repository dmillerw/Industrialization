package dmillerw.industrialization.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dmillerw.industrialization.block.tile.TileBlockDetector;
import dmillerw.industrialization.inventory.phantom.ContainerPhantom;
import dmillerw.industrialization.inventory.phantom.slot.SlotPhantom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

/**
 * Created by Dylan Miller on 1/19/14
 */
public class ContainerBlockDetector extends ContainerPhantom {

    private final EntityPlayer player;

    private final TileBlockDetector tile;

    private int lastRange = -1;
    
    private byte lastMetaFlag = -1;
    
    public ContainerBlockDetector(EntityPlayer player, TileBlockDetector tile) {
        this.player = player;
        this.tile = tile;

        this.addSlotToContainer(new SlotPhantom(tile.target, 0, 80, 53));

        /* PLAYER INVENTORY */
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting player) {
        super.addCraftingToCrafters(player);
        
        player.sendProgressBarUpdate(this, 0, tile.range);
        player.sendProgressBarUpdate(this, 1, tile.ignoreMeta ? 1 : 0);
    }
    
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        if (lastRange != tile.range || (tile.ignoreMeta ? 1 : 0) != lastMetaFlag) {
            for (int i = 0; i < this.crafters.size(); ++i) {
                ICrafting icrafting = (ICrafting) this.crafters.get(i);
                icrafting.sendProgressBarUpdate(this, 0, tile.range);
                icrafting.sendProgressBarUpdate(this, 1, tile.ignoreMeta ? 1 : 0);
            }
            lastRange = tile.range;
            lastMetaFlag = (byte) (tile.ignoreMeta ? 1 : 0);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int value) {
        if (id == 0) {
            tile.range = value;
        } else if (id == 1) {
            tile.ignoreMeta = value == 0 ? false : true;
        }
    }
    
    @Override
    public boolean enchantItem(EntityPlayer player, int id) {
        if (id == 0 || id == 1) {
            int range = tile.range;
            
            if (id == 0) {
                range--;
                if (range < 1) {
                    range = 1;
                }
            } else if (id == 1) {
                range++;
            }
            
            tile.setRange(range);
        } else if (id == 2) {
            tile.setIgnoreMetaFlag(!tile.ignoreMeta);
        }
        
        return true;
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return true;
    }

}
