package dmillerw.industrialization.core.handler;

import cpw.mods.fml.common.network.IGuiHandler;
import dmillerw.industrialization.block.tile.TileFilter;
import dmillerw.industrialization.client.gui.GuiFilter;
import dmillerw.industrialization.inventory.ContainerFilter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by Dylan Miller on 1/18/14
 */
public class GuiHandler implements IGuiHandler {

    public static final int GUI_FILTER = 0;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        switch(id) {
            case GUI_FILTER: return new ContainerFilter(entityPlayer, (TileFilter) world.getBlockTileEntity(x, y, z));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        switch(id) {
            case GUI_FILTER: return new GuiFilter(entityPlayer, (TileFilter) world.getBlockTileEntity(x, y, z));
        }

        return null;
    }

}
