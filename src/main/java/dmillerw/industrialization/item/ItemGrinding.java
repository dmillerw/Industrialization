package dmillerw.industrialization.item;

import dmillerw.industrialization.core.TabIndustrialization;
import dmillerw.industrialization.core.ore.OreHandler;
import dmillerw.industrialization.core.ore.OreWrapper;
import dmillerw.industrialization.lib.ModInfo;
import dmillerw.industrialization.util.Color;
import dmillerw.industrialization.util.UtilIcon;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dylan Miller on 1/2/14
 */
public class ItemGrinding extends Item {

    public static Map<String, Color> oreColorCache = new HashMap<String, Color>();

    public static ItemStack getGrinding(String oreTag) {
        ItemStack grinding = new ItemStack(ItemHandler.itemGrinding);
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("oreTag", oreTag);
        grinding.setTagCompound(nbt);
        return grinding;
    }

    public static String getTag(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound nbt = stack.getTagCompound();

        if (nbt.hasKey("oreTag")) {
            return nbt.getString("oreTag");
        } else {
            return "NULL";
        }
    }

    private Icon[] icons;

    public ItemGrinding(int id) {
        super(id);

        setHasSubtypes(true);
        setCreativeTab(TabIndustrialization.TAB);
    }

    private int getColorFromOre(String oreTag) {
        if (oreTag.equals("NULL")) {
            return 0xFFFFFF;
        }

        if (!oreColorCache.containsKey(oreTag)) {
            List<Color> colors = new ArrayList<Color>();
            Block ore = Block.blocksList[OreHandler.INSTANCE.getOre(oreTag).getOreBlocks()[0].itemID];

            try {
                BufferedImage oreImage = ImageIO.read(UtilIcon.getResource(UtilIcon.getLocation(ore)).getInputStream());
                for (int i=0; i<16; i++) {
                    for (int j=0; j<16; j++) {
                        Color pixel = new Color(oreImage.getRGB(i, j));

                        if (!pixel.grayscale()) {
                            colors.add(pixel);
                        }
                    }
                }

                oreColorCache.put(oreTag, Color.average(colors.toArray(new Color[colors.size()])));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Color myColor = oreColorCache.get(oreTag);
        return myColor.toInt();
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int pass) {
        if (pass > 0) {
            return getColorFromOre(getTag(stack)); // Grinding color
        } else {
            return 0xFFFFFF; // WHITE
        }
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public Icon getIconFromDamageForRenderPass(int damage, int pass) {
        return icons[pass];
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String tag = getTag(stack);
        return super.getUnlocalizedName(stack) + "." + tag.toLowerCase();
    }

    @Override
    public void getSubItems(int id, CreativeTabs tab, List list) {
        for (OreWrapper ore : OreHandler.INSTANCE.getRegisteredOres()) {
            if (ore.getGrinding() != null) {
                list.add(ore.getGrinding());
            }
        }
    }

    @Override
    public void registerIcons(IconRegister register) {
        this.icons = new Icon[2];

        this.icons[0] = register.registerIcon(ModInfo.RESOURCE_PREFIX + "grinding/grinding_base");
        this.icons[1] = register.registerIcon(ModInfo.RESOURCE_PREFIX + "grinding/grinding_overlay");
    }

}
