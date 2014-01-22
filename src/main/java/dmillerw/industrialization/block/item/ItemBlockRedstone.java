package dmillerw.industrialization.block.item;

import dmillerw.industrialization.block.BlockUtility;

/**
 * Created by Dylan Miller on 1/19/14
 */
public class ItemBlockRedstone extends ItemBlockCoreTile {

    public ItemBlockRedstone(int id) {
        super(id);
    }

    @Override
    public String[] getNameArray() {
        return BlockUtility.NAMES;
    }

}
