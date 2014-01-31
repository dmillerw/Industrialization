package dmillerw.industrialization.core.ore;

import cpw.mods.fml.common.Loader;
import dmillerw.industrialization.Industrialization;
import dmillerw.industrialization.item.ItemHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dylan Miller on 1/2/14
 */
public class OreWrapper {

    public final String oreTag;

    private List<ItemStack> oreBlocks = new ArrayList<ItemStack>();

    private ItemStack grinding;
    private ItemStack dust;

    public OreWrapper(String oreTag) {
        this.oreTag = oreTag;
    }

    public void addBlock(ItemStack block) {
        oreBlocks.add(block);
    }

    public void generateGrinding() {
        if (grinding == null) {
            grinding = new ItemStack(ItemHandler.itemGrinding, 1, Industrialization.instance.grindingMapper.getID("grinding_" + this.oreTag, true));
            OreDictionary.registerOre("grinding" + oreTag, grinding);
            if (OreHandler.ic2Support && Loader.isModLoaded("IC2")) {
                OreDictionary.registerOre("crushed" + oreTag, grinding); // IC2 support
            }
        }
    }

    public void generateDust() {
        if (dust == null) {
            if (oreTag.equalsIgnoreCase("gold")) {
                dust = new ItemStack(ItemHandler.itemDust, 1, 0);
            } else if (oreTag.equalsIgnoreCase("iron")) {
                dust = new ItemStack(ItemHandler.itemDust, 1, 1);
            }
        }
    }

    public void setDust(ItemStack dust) {
        this.dust = dust;
    }

    public ItemStack[] getOreBlocks() {
        return oreBlocks.toArray(new ItemStack[oreBlocks.size()]);
    }

    public ItemStack getGrinding() {
        return grinding;
    }

    public ItemStack getDust() {
        return dust;
    }

}
