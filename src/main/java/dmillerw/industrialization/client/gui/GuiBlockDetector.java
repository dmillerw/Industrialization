package dmillerw.industrialization.client.gui;

import dmillerw.industrialization.block.tile.TileBlockDetector;
import dmillerw.industrialization.inventory.ContainerBlockDetector;
import dmillerw.industrialization.lib.ModInfo;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by Dylan Miller on 1/19/14
 */
public class GuiBlockDetector extends GuiContainer {

    private final TileBlockDetector tile;

    public GuiBlockDetector(EntityPlayer player, TileBlockDetector tile) {
        super(new ContainerBlockDetector(player, tile));

        this.tile = tile;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String s = I18n.getString("gui.block_detector");
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);

        String warn = "This block is still a WIP!";
        this.fontRenderer.drawString(warn, this.xSize / 2 - this.fontRenderer.getStringWidth(warn) / 2, 55, 0xFF0000);

        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation(ModInfo.RESOURCE_PREFIX + "textures/gui/block_detector.png"));
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }

}
