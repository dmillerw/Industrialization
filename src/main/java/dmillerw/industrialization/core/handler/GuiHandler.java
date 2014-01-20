package dmillerw.industrialization.core.handler;

import cpw.mods.fml.common.network.IGuiHandler;
import dmillerw.industrialization.block.tile.TileBlockDetector;
import dmillerw.industrialization.client.gui.GuiBlockDetector;
import dmillerw.industrialization.inventory.ContainerBlockDetector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by Dylan Miller on 1/18/14
 */
public class GuiHandler implements IGuiHandler {

    public static final int GUI_BLOCK_DETECTOR = 0;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        switch(id) {
            case GUI_BLOCK_DETECTOR: return new ContainerBlockDetector(entityPlayer, (TileBlockDetector) world.getBlockTileEntity(x, y, z));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        switch(id) {
            case GUI_BLOCK_DETECTOR: return new GuiBlockDetector(entityPlayer, (TileBlockDetector) world.getBlockTileEntity(x, y, z));
        }

        return null;
    }

}
