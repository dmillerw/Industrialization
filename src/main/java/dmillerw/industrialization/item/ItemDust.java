package dmillerw.industrialization.item;

import dmillerw.industrialization.core.TabIndustrialization;
import dmillerw.industrialization.lib.ModInfo;
import dmillerw.industrialization.util.UtilString;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import java.util.List;

/**
 * Created by Dylan Miller on 1/30/14
 */
public class ItemDust extends Item {

    public static final String[] NAMES = new String[] {"gold", "iron"};

    private Icon icon;

    public ItemDust(int id) {
        super(id);

        setHasSubtypes(true);
        setCreativeTab(TabIndustrialization.TAB);
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int pass) {
        return ItemGrinding.getColorFromOre(UtilString.capitalize(NAMES[stack.getItemDamage()]));
    }

    @Override
    public Icon getIconFromDamage(int damage) {
        return icon;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "." + NAMES[stack.getItemDamage()];
    }

    @Override
    public void getSubItems(int id, CreativeTabs tab, List list) {
        list.add(new ItemStack(this, 0, 0));
        list.add(new ItemStack(this, 0, 1));
    }

    @Override
    public void registerIcons(IconRegister register) {
        this.icon = register.registerIcon(ModInfo.RESOURCE_PREFIX + "dust");
    }

}
