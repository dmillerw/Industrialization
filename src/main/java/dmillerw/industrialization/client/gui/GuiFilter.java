package dmillerw.industrialization.client.gui;

import dmillerw.industrialization.block.tile.TileFilter;
import dmillerw.industrialization.inventory.ContainerFilter;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by Dylan Miller on 1/18/14
 */
public class GuiFilter extends GuiContainer {

    private TileFilter tile;

    public GuiFilter(EntityPlayer player, TileFilter tile) {
        super(new ContainerFilter(player, tile));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i2) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/container/gui/dispenser.png"));
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }

}
