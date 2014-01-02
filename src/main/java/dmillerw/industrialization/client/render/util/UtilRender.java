package dmillerw.industrialization.client.render.util;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

/**
 * Created by Dylan Miller on 1/2/14
 */
public class UtilRender {

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

}
