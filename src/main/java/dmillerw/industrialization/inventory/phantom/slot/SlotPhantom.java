package dmillerw.industrialization.inventory.phantom.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotPhantom extends Slot {

    public SlotPhantom(IInventory contents, int id, int x, int y) {
        super(contents, id, x, y);
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public boolean isItemValid(ItemStack itemstack) {
        return true;
    }

    @Override
    public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
        return true;
    }

    public boolean canAdjust() {
        return true;
    }

}