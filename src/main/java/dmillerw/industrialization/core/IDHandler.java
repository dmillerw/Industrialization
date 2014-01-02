package dmillerw.industrialization.core;

import net.minecraftforge.common.Configuration;

/**
 * Created by Dylan Miller on 1/1/14
 */
public class IDHandler {

    private final Configuration config;

    private int currentID;

    public IDHandler(Configuration config, int startID) {
        this.config = config;
        this.currentID = startID;
    }

    public int getBlock(String name) {
        int id = config.getBlock("blockID_" + name, currentID).getInt(currentID);
        currentID++;
        return id;
    }

    public int getItem(String name) {
        int id = config.getItem("itemID_" + name, currentID).getInt(currentID);
        currentID++;
        return id;
    }

}
