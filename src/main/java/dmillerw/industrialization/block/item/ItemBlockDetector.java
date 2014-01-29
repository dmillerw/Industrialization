package dmillerw.industrialization.block.item;

import dmillerw.industrialization.block.BlockDetector;

/**
 * Created by Dylan Miller on 1/19/14
 */
public class ItemBlockDetector extends ItemBlockCoreTile {

    public ItemBlockDetector(int id) {
        super(id);
    }

    @Override
    public String[] getNameArray() {
        return BlockDetector.NAMES;
    }

}
