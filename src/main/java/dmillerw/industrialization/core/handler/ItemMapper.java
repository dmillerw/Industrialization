package dmillerw.industrialization.core.handler;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import dmillerw.industrialization.core.helper.CoreLogger;
import net.minecraft.item.ItemStack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Dylan Miller on 1/19/14
 */
public class ItemMapper {

    private BiMap<String, Integer> idToMetaMapping = HashBiMap.<String, Integer>create();

    private final File saveLocation;

    public ItemMapper(File saveLocation) {
        this.saveLocation = saveLocation;

        load();
    }

    public void load() {
        if (saveLocation.exists()) {
            try {
                Properties properties = new Properties();
                FileInputStream fis = new FileInputStream(saveLocation);

                properties.load(fis);

                for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                    idToMetaMapping.put((String)entry.getKey(), Integer.valueOf((String)entry.getValue()));
                }
            } catch(IOException ex) {
                CoreLogger.warn("Failed to read item mappings from file! This is bad!");
                ex.printStackTrace();
            }
        }
    }

    public void save() {
        if (!saveLocation.exists()) {
            try {
                saveLocation.createNewFile();
            } catch(IOException ex) {
                CoreLogger.warn("Failed to create new ItemMapping.properties file. Bad things may happen!");
            }
        }

        try {
            Properties properties = new Properties();
            for (Map.Entry<String, Integer> entry : idToMetaMapping.entrySet()) {
                properties.setProperty(entry.getKey(), String.valueOf(entry.getValue()));
            }
            FileOutputStream fos = new FileOutputStream(saveLocation);
            properties.store(fos, "DO NOT CHANGE UNLESS YOU UNDERSTAND WHAT YOU'RE DOING!");
            fos.close();
        } catch(IOException ex) {
            CoreLogger.warn("Failed to save item mappings to file! This is bad!");
            ex.printStackTrace();
        }
    }

    public String getIDForStack(ItemStack stack) {
        return getIDForAllocation(stack.getItemDamage());
    }

    public String getIDForAllocation(int idInt) {
        return idToMetaMapping.inverse().get(idInt);
    }

    public int getAllocationFromID(String id) {
        return idToMetaMapping.get(id);
    }

    public int getID(String id, boolean allocate) {
        if (idToMetaMapping.containsKey(id)) {
            return idToMetaMapping.get(id);
        } else {
            if (!allocate) {
                return -1;
            } else {
                return allocateID(id, idToMetaMapping.size());
            }
        }
    }

    private int allocateID(String id, int meta) {
        if (!idToMetaMapping.inverse().containsKey(meta)) {
            idToMetaMapping.put(id, meta);
            return meta;
        } else {
            return allocateID(id, meta + 1);
        }
    }

}
