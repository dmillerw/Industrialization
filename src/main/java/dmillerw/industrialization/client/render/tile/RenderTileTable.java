package dmillerw.industrialization.client.render.tile;

import dmillerw.industrialization.block.tile.TileTable;
import dmillerw.industrialization.client.render.util.UtilRender;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

/**
 * Created by Dylan Miller on 1/2/14
 */
public class RenderTileTable extends TileEntitySpecialRenderer {

    public void renderTableAt(TileTable tile, double x, double y, double z, float partial) {
        GL11.glPushMatrix();

        GL11.glTranslated(x + 0.5F, y + 1F, z + 0.5F);

        if (tile.contents != null) {
            UtilRender.renderItem(tile.contents, true);
        }

        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
        renderTableAt((TileTable) tileentity, d0, d1, d2, f);
    }

}
