package dmillerw.industrialization.client.render.block;

import dmillerw.industrialization.block.BlockHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

/**
 * Created by Dylan Miller on 1/26/14
 */
public class RenderBlockRope extends SimpleBlockRenderer {

    private static final float ANCHOR_TOP = 0.725F;

    @Override
    public void renderInventoryBlock(RenderBlocks renderer, Block block, int meta) {}

    @Override
    public void renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, RenderBlocks renderer) {
        renderer.setRenderBoundsFromBlock(block);
        renderer.renderStandardBlock(block, x, y, z);

        if (world.getBlockId(x, y + 1, z) == BlockHandler.blockAnchorID) {
            renderer.setRenderBounds(0.375, 1F, 0.375, 0.625, 1F + ANCHOR_TOP, 0.625);
            renderer.renderStandardBlock(block, x, y, z);
        }
    }

}
