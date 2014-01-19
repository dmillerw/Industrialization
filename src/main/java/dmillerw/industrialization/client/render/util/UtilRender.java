package dmillerw.industrialization.client.render.util;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import org.lwjgl.opengl.GL11;

/**
 * Created by Dylan Miller on 1/2/14
 */
public class UtilRender {

    public static boolean lastGraphics;

    public static int lastLighting;

    public static void setGraphics(boolean value) {
        Minecraft mc = Minecraft.getMinecraft();
        lastGraphics = mc.gameSettings.fancyGraphics;
        mc.gameSettings.fancyGraphics = value;
    }

    public static void resetGraphics() {
        Minecraft mc = Minecraft.getMinecraft();
        mc.gameSettings.fancyGraphics = lastGraphics;
    }

    public static void setLighting(int value) {
        Minecraft mc = Minecraft.getMinecraft();
        lastLighting = mc.gameSettings.ambientOcclusion;
        mc.gameSettings.ambientOcclusion = value;
    }

    public static void resetLighting() {
        Minecraft mc = Minecraft.getMinecraft();
        mc.gameSettings.ambientOcclusion = lastLighting;
    }

    public static void renderItem(ItemStack stack, boolean force3D) {
        boolean mcSetting = Minecraft.getMinecraft().gameSettings.fancyGraphics;

        if (force3D) {
            Minecraft.getMinecraft().gameSettings.fancyGraphics = true;
        }

        EntityItem item = new EntityItem(FMLClientHandler.instance().getClient().theWorld);
        item.hoverStart = 0F;

        ItemStack renderStack = stack.copy();
        renderStack.stackSize = 1;

        item.setEntityItemStack(renderStack);

        RenderManager.instance.renderEntityWithPosYaw(item, 0, 0, 0, 0, 0);

        if (force3D) {
            Minecraft.getMinecraft().gameSettings.fancyGraphics = mcSetting;
        }
    }

    public static void renderAllSides(RenderBlocks renderer, Block block) {
        renderAllSides(renderer, block, renderer.overrideBlockTexture);
    }

    public static void renderAllSides(RenderBlocks renderblocks, Block block, Icon icon) {
        Tessellator tessellator = Tessellator.instance;
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        if (icon != null) {
            renderblocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, icon);
        }
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        if (icon != null) {
            renderblocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, icon);
        }
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        if (icon != null) {
            renderblocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, icon);
        }
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        if (icon != null) {
            renderblocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, icon);
        }
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        if (icon != null) {
            renderblocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, icon);
        }
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        if (icon != null) {
            renderblocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, icon);
        }
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

}