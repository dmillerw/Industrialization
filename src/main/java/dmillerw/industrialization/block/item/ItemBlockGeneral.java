package dmillerw.industrialization.block.item;

import dmillerw.industrialization.block.BlockGeneral;

/**
 * Created by Dylan Miller on 1/19/14
 */
public class ItemBlockGeneral extends ItemBlockCore {

    public ItemBlockGeneral(int id) {
        super(id);
    }

    @Override
    public String[] getNameArray() {
        return BlockGeneral.NAMES;
    }

}
