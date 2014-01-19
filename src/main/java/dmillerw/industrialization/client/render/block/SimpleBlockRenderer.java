package dmillerw.industrialization.client.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import dmillerw.industrialization.client.render.util.UtilRender;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dylan Miller on 1/18/14
 */
public abstract class SimpleBlockRenderer implements ISimpleBlockRenderingHandler {

    public static Map<Class<? extends SimpleBlockRenderer>, Integer> allocatedRenderIDs = new HashMap<Class<? extends SimpleBlockRenderer>, Integer>();

    public static int getRenderID(Class<? extends SimpleBlockRenderer> clazz) {
        if (allocatedRenderIDs.containsKey(clazz)) {
            return allocatedRenderIDs.get(clazz);
        } else {
            throw new RuntimeException("Tried to get render id for renderer that doesn't extend SimpleBlockRenderer");
        }
    }

    public SimpleBlockRenderer() {
        allocatedRenderIDs.put(this.getClass(), RenderingRegistry.getNextAvailableRenderId());
    }

    protected void renderDimensionsInInventory(RenderBlocks renderer, Block block, double x1, double y1, double z1, double x2, double y2, double z2, Icon textureOverride) {
        renderer.setRenderBounds(x1, y1, z1, x2, y2, z2);
        UtilRender.renderAllSides(renderer, block, textureOverride);
    }

    protected void renderDimensionsInWorld(RenderBlocks renderer, Block block, int x, int y, int z, double x1, double y1, double z1, double x2, double y2, double z2, Icon textureOverride) {
        if (textureOverride != null) {
            renderer.setOverrideBlockTexture(textureOverride);
        }
        renderer.setRenderBounds(x1, y1, z1, x2, y2, z2);
        renderer.renderStandardBlock(block, x, y, z);
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        renderInventoryBlock(renderer, block, metadata);
        renderer.clearOverrideBlockTexture();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        renderWorldBlock(world, x, y, z, block, renderer);
        renderer.clearOverrideBlockTexture(); // Just in case
        return true;
    }

    public abstract void renderInventoryBlock(RenderBlocks renderer, Block block, int meta);

    public abstract void renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, RenderBlocks renderer);

    @Override
    public boolean shouldRender3DInInventory() {
        return true;
    }

    @Override
    public int getRenderId() {
        return getRenderID(this.getClass());
    }

}
