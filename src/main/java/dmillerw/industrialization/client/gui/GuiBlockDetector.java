package dmillerw.industrialization.client.gui;

import dmillerw.industrialization.block.tile.TileBlockDetector;
import dmillerw.industrialization.client.gui.button.GuiButtonCheckbox;
import dmillerw.industrialization.client.gui.button.GuiButtonSmall;
import dmillerw.industrialization.inventory.ContainerBlockDetector;
import dmillerw.industrialization.lib.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
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

    private GuiButtonSmall rangeDec;
    private GuiButtonSmall rangeInc;
    
    private GuiButtonCheckbox ignoreMeta;
    
    public GuiBlockDetector(EntityPlayer player, TileBlockDetector tile) {
        super(new ContainerBlockDetector(player, tile));

        this.tile = tile;
        
        rangeDec = new GuiButtonSmall(0, 54, 28, '-');
        rangeInc = new GuiButtonSmall(1, 101, 28, '+');
        ignoreMeta = new GuiButtonCheckbox(2, 30, 28).setTooltip(new String[] {"Ignore Metadata"});
    }

    @Override
    public void updateScreen() {
        ignoreMeta.setState(tile.ignoreMeta);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String s = I18n.getString("gui.block_detector");
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    
        String range = Integer.toString(tile.range);
        this.fontRenderer.drawString(range, this.xSize / 2 - this.fontRenderer.getStringWidth(range) / 2, 34, 4210752);
        
        // Buttons
        drawButton(rangeDec, par1, par2);
        drawButton(rangeInc, par1, par2);
        drawButton(ignoreMeta, par1, par2);
    }

    private void drawButton(GuiButton button, int mouseX, int mouseY) {
        button.drawButton(mc, mouseX - (this.width - this.xSize) / 2, mouseY - (this.height - this.ySize) / 2);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation(ModInfo.RESOURCE_PREFIX + "textures/gui/block_detector.png"));
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        
        if (mouseButton == 0) {
            if (rangeDec.mousePressed(mc, mouseX - (this.width - this.xSize) / 2, mouseY - (this.height - this.ySize) / 2)) {
                this.mc.sndManager.playSoundFX("random.click", 1, 1);
                actionPerformed(rangeDec);
            } else if (rangeInc.mousePressed(mc, mouseX - (this.width - this.xSize) / 2, mouseY - (this.height - this.ySize) / 2)) {
                this.mc.sndManager.playSoundFX("random.click", 1, 1);
                actionPerformed(rangeInc);
            } else if (ignoreMeta.mousePressed(mc, mouseX - (this.width - this.xSize) / 2, mouseY - (this.height - this.ySize) / 2)) {
                this.mc.sndManager.playSoundFX("random.click", 1, 1);
                actionPerformed(ignoreMeta);
            }
        }
    }
    
    @Override
    public void actionPerformed(GuiButton button) {
        this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, button.id);
    }
    
}
