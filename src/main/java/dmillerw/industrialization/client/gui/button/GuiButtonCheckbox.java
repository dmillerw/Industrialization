package dmillerw.industrialization.client.gui.button;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import dmillerw.industrialization.lib.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;

public class GuiButtonCheckbox extends GuiButton {

    public static final ResourceLocation WIDGETS = new ResourceLocation(ModInfo.RESOURCE_PREFIX + "textures/gui/widgets.png");
    
    private String[] tooltip;
    
    public boolean state = false;
    
    public GuiButtonCheckbox(int id, int x, int y) {
        super(id, x, y, 9, 9, "");
    }
    
    public GuiButtonCheckbox setTooltip(String[] tooltip) {
        this.tooltip = tooltip;
        return this;
    }
    
    public void setState(boolean state) {
        this.state = state;
    }
    
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        boolean mouseOver = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        
        if (drawButton) {
            mc.getTextureManager().bindTexture(WIDGETS);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            
            this.drawTexturedModalRect(this.xPosition, this.yPosition, state ? 0 : 9, 0, this.width, this.height);
            
            if (mouseOver) {
                drawHoveringText(Arrays.asList(this.tooltip), mouseX, mouseY, Minecraft.getMinecraft().fontRenderer);
            }
        }
    }
    
    @Override
    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
        if (this.enabled && this.drawButton && par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height) {
            setState(!state);
            return true;
        }
        return false;
    }
    
    protected void drawHoveringText(List list, int x, int y, FontRenderer font) {
        if (!list.isEmpty()) {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                String s = (String) iterator.next();
                int l = font.getStringWidth(s);

                if (l > k) {
                    k = l;
                }
            }

            int i1 = x + 6;
            int j1 = y - 6;
            int k1 = 8;

            if (list.size() > 1) {
                k1 += 2 + (list.size() - 1) * 10;
            }

            this.zLevel = 300.0F;
            int l1 = -267386864;
            this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
            this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);

            for (int k2 = 0; k2 < list.size(); ++k2) {
                String s1 = (String) list.get(k2);
                font.drawStringWithShadow(s1, i1, j1, -1);

                if (k2 == 0) {
                    j1 += 2;
                }

                j1 += 10;
            }

            this.zLevel = 0.0F;
        }
    }
    
}
