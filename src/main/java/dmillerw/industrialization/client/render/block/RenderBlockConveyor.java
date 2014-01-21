package dmillerw.industrialization.client.render.block;

import dmillerw.industrialization.block.tile.TileConveyor;
import dmillerw.industrialization.block.tile.TileCore;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

/**
 * Created by Dylan Miller on 1/20/14
 */
public class RenderBlockConveyor extends SimpleBlockRenderer {

    @Override
    public void renderInventoryBlock(RenderBlocks renderer, Block block, int meta) {
        renderDimensionsInInventory(renderer, block, 0, 0, 0, 1, 1, 1, null);
    }

    @Override
    public void renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, RenderBlocks renderer) {
        TileCore tile = (TileCore) world.getBlockTileEntity(x, y, z);

        if (tile != null && tile instanceof TileConveyor) {
            switch(((TileConveyor)tile).orientation) {
                case EAST: renderer.uvRotateTop = 2; break;
                case WEST: renderer.uvRotateTop = 1; break;
                case SOUTH: renderer.uvRotateTop = 3; break;
            }
        }

        renderer.renderStandardBlock(block, x, y, z);

        renderer.uvRotateTop = 0;
    }

}
