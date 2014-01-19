package dmillerw.industrialization.client.render.block;

import dmillerw.industrialization.client.render.util.UtilRender;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

/**
 * Created by Dylan Miller on 1/18/14
 */
public class RenderBlockTable extends SimpleBlockRenderer {

    @Override
    public void renderInventoryBlock(RenderBlocks renderer, Block block, int meta) {
        // TABLE
        renderDimensionsInInventory(renderer, block, 0, 0.75, 0, 1, 1, 1, Block.stone.getIcon(0, 0));

        // LEGS
        renderDimensionsInInventory(renderer, block, 0,    0, 0,    0.25, 0.75, 0.25, Block.stoneBrick.getIcon(0, 0));
        renderDimensionsInInventory(renderer, block, 0.75, 0, 0.75, 1,    0.75, 1,    Block.stoneBrick.getIcon(0, 0));
        renderDimensionsInInventory(renderer, block, 0,    0, 0.75, 0.25, 0.75, 1,    Block.stoneBrick.getIcon(0, 0));
        renderDimensionsInInventory(renderer, block, 0.75, 0, 0,    1,    0.75, 0.25, Block.stoneBrick.getIcon(0, 0));
    }

    @Override
    public void renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, RenderBlocks renderer) {
        // TABLE
        renderDimensionsInWorld(renderer, block, x, y, z, 0, 0.75, 0, 1, 1, 1, Block.stone.getIcon(0, 0));

        // LEGS
        UtilRender.setLighting(0);
        renderDimensionsInWorld(renderer, block, x, y, z, 0, 0, 0, 0.25, 0.75, 0.25, Block.stoneBrick.getIcon(0, 0));
        renderDimensionsInWorld(renderer, block, x, y, z, 0.75, 0, 0.75, 1,    0.75, 1,    Block.stoneBrick.getIcon(0, 0));
        renderDimensionsInWorld(renderer, block, x, y, z, 0,    0, 0.75, 0.25, 0.75, 1,    Block.stoneBrick.getIcon(0, 0));
        renderDimensionsInWorld(renderer, block, x, y, z, 0.75, 0, 0,    1,    0.75, 0.25, Block.stoneBrick.getIcon(0, 0));
        UtilRender.resetLighting();
    }

}
