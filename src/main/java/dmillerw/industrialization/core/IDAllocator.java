package dmillerw.industrialization.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;

/**
 * Created by Dylan Miller on 1/1/14
 */
public class IDAllocator {

    private final Configuration config;

    private int currentID;

    public IDAllocator(Configuration config, int startID) {
        this.config = config;
        this.currentID = startID;
    }

    public int getBlock(String name) {
        int nextID = getNextBlockID();
        int id = config.getBlock("blockID_" + name, nextID).getInt(nextID);
        return id;
    }

    public int getItem(String name) {
        int nextID = getNextItemID();
        int id = config.getItem("itemID_" + name, nextID).getInt(nextID);
        return id;
    }

    private int getNextBlockID() {
        int id = currentID;

        while (id > 255 && id < (Block.blocksList.length - 1)) {
            Block block = Block.blocksList[id];
            if (block == null) {
                break;
            }
            id++;
        }
        currentID = id + 1;
        return id;
    }

    private int getNextItemID() {
        int id = currentID;

        while (id > 255 && id < (Item.itemsList.length - 1)) {
            Item item = Item.itemsList[id];
            if (item == null) {
                break;
            }
            id++;
        }
        currentID = id + 1;
        return id;
    }

}
