package dmillerw.industrialization.inventory;

import dmillerw.industrialization.block.tile.TileFilter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

/**
 * Created by Dylan Miller on 1/18/14
 */
public class ContainerFilter extends Container {

    private final EntityPlayer player;

    private final TileFilter tile;

    public ContainerFilter(EntityPlayer player, TileFilter tile) {
        this.player = player;
        this.tile = tile;

        // Player Inventory
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
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

}
