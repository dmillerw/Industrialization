package dmillerw.industrialization.core.handler;

import cpw.mods.fml.common.network.IGuiHandler;
import dmillerw.industrialization.block.tile.TileBlockDetector;
import dmillerw.industrialization.block.tile.TileItemDetector;
import dmillerw.industrialization.client.gui.GuiBlockDetector;
import dmillerw.industrialization.client.gui.GuiItemDetector;
import dmillerw.industrialization.inventory.ContainerBlockDetector;
import dmillerw.industrialization.inventory.ContainerItemDetector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by Dylan Miller on 1/18/14
 */
public class GuiHandler implements IGuiHandler {

    public static final int GUI_BLOCK_DETECTOR = 0;
    public static final int GUI_ITEM_DETECTOR = 1;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        switch(id) {
            case GUI_BLOCK_DETECTOR: return new ContainerBlockDetector(entityPlayer, (TileBlockDetector) world.getBlockTileEntity(x, y, z));
            case GUI_ITEM_DETECTOR: return new ContainerItemDetector(entityPlayer, (TileItemDetector) world.getBlockTileEntity(x, y, z));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        switch(id) {
            case GUI_BLOCK_DETECTOR: return new GuiBlockDetector(entityPlayer, (TileBlockDetector) world.getBlockTileEntity(x, y, z));
            case GUI_ITEM_DETECTOR: return new GuiItemDetector(entityPlayer, (TileItemDetector) world.getBlockTileEntity(x, y, z));
        }

        return null;
    }

}
