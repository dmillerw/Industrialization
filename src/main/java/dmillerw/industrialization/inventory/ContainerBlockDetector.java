package dmillerw.industrialization.inventory;

import dmillerw.industrialization.block.tile.TileBlockDetector;
import dmillerw.industrialization.inventory.phantom.ContainerPhantom;
import dmillerw.industrialization.inventory.phantom.slot.SlotPhantom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

/**
 * Created by Dylan Miller on 1/19/14
 */
public class ContainerBlockDetector extends ContainerPhantom {

    private final EntityPlayer player;

    private final TileBlockDetector tile;

    public ContainerBlockDetector(EntityPlayer player, TileBlockDetector tile) {
        this.player = player;
        this.tile = tile;

        this.addSlotToContainer(new SlotPhantom(tile.target, 0, 80, 35));

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
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return true;
    }

}
