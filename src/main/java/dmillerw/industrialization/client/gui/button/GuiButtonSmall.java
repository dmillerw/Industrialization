package dmillerw.industrialization.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonSmall extends GuiButton {

    public GuiButtonSmall(int id, int x, int y, char display) {
        super(id, x, y, 20, 20, Character.toString(display));
    }
    
}
