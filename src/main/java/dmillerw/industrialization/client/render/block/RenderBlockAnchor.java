package dmillerw.industrialization.client.render.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

/**
 * Created by Dylan Miller on 1/24/14
 */
public class RenderBlockAnchor extends SimpleBlockRenderer {

    @Override
    public void renderInventoryBlock(RenderBlocks renderer, Block block, int meta) {}

    @Override
    public void renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, RenderBlocks renderer) {
        renderer.renderAllFaces = true;

        switch(world.getBlockMetadata(x, y, z)) {
            case 1: {
                renderer.setRenderBounds(0.4375, 0.625, 0.4375, 0.5625, 1, 0.5625);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(0.4375, 0.375, 0.4375, 0.5625, 0.5, 0.5625);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(0.46875, 0.5, 0.46875, 0.53125, 0.625, 0.53125);
                renderer.renderStandardBlock(block, x, y, z);
                break;
            }

            case 3: {
                renderer.setRenderBounds(0.4375, 0.4375, 0.625, 0.5625, 0.5625, 1);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(0.4375, 0.4375, 0.375, 0.5625, 0.5625, 0.5);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(0.46875, 0.46875, 0.5, 0.53125, 0.53125, 0.625);
                renderer.renderStandardBlock(block, x, y, z);
                break;
            }

            case 2: {
                renderer.setRenderBounds(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.375);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(0.4375, 0.4375, 0.4875, 0.5625, 0.5625, 0.6125);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(0.46875, 0.46875, 0.375, 0.53125, 0.53125, 0.4875);
                renderer.renderStandardBlock(block, x, y, z);
                break;
            }

            case 4: {
                renderer.setRenderBounds(0, 0.4375, 0.4375, 0.375, 0.5625, 0.5625);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(0.4875, 0.4375, 0.4375, 0.6125, 0.5625, 0.5625);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(0.375, 0.46875, 0.46875, 0.4875, 0.53125, 0.53125);
                renderer.renderStandardBlock(block, x, y, z);
                break;
            }

            case 5: {
                renderer.setRenderBounds(0.625, 0.4375, 0.4375, 1, 0.5625, 0.5625);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(0.375, 0.4375, 0.4375, 0.5, 0.5625, 0.5625);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(0.5, 0.46875, 0.46875, 0.625, 0.53125, 0.53125);
                renderer.renderStandardBlock(block, x, y, z);
                break;
            }
        }

        renderer.renderAllFaces = false;
    }

}
