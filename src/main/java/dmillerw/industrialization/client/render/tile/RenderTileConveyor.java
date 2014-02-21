package dmillerw.industrialization.client.render.tile;

import dmillerw.industrialization.block.tile.TileConveyor;
import dmillerw.industrialization.client.render.util.StaticBlockRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

/**
 * Created by Dylan Miller on 1/20/14
 */
public class RenderTileConveyor extends TileEntitySpecialRenderer {

    private RenderBlocks renderer;

    public void renderConveyorAt(TileConveyor tile, double x, double y, double z) {
        if (tile.storedID > 0) {
            Tessellator tessellator = Tessellator.instance;
            this.bindTexture(TextureMap.locationBlocksTexture);
            RenderHelper.disableStandardItemLighting();
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_CULL_FACE);

            if (Minecraft.isAmbientOcclusionEnabled())
            {
                GL11.glShadeModel(GL11.GL_SMOOTH);
            }
            else
            {
                GL11.glShadeModel(GL11.GL_FLAT);
            }

            tessellator.startDrawingQuads();
            tessellator.setTranslation(x - tile.getRenderOffsetX(), y - tile.getRenderOffsetY(), z - tile.getRenderOffsetZ());
            tessellator.setColorOpaque(1, 1, 1);

            Block stored = Block.blocksList[tile.storedID];

            renderer.renderAllFaces = true;
            stored.setBlockBoundsBasedOnState(tile.worldObj, tile.xCoord, tile.yCoord + 1, tile.zCoord);
            renderer.setRenderBoundsFromBlock(stored);

            StaticBlockRenderer.renderBlock(stored, tile.storedMeta, tile.xCoord, tile.yCoord + 1, tile.zCoord, renderer, tile.worldObj);

            tessellator.setTranslation(0, 0, 0);
            tessellator.draw();
        }
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partial) {
        renderConveyorAt((TileConveyor) tile, x, y, z);
    }

    public void onWorldChange(World world) {
        renderer = new RenderBlocks(world);
    }

}
