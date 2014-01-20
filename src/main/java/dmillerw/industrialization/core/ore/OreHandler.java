package dmillerw.industrialization.core.ore;

import cpw.mods.fml.common.Loader;
import dmillerw.industrialization.core.handler.ModHandler;
import dmillerw.industrialization.core.helper.CoreLogger;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dylan Miller on 1/2/14
 */
public class OreHandler {

    public static String preferredMod;

    public static final OreHandler INSTANCE = new OreHandler();

    private Map<String, OreWrapper> registeredOres = new HashMap<String, OreWrapper>();

    public List<OreWrapper> getRegisteredOres() {
        List<OreWrapper> ores = new ArrayList<OreWrapper>();
        for (Map.Entry<String, OreWrapper> entry : registeredOres.entrySet()) {
            ores.add(entry.getValue());
        }
        return ores;
    }

    public OreWrapper getOre(String oreTag) {
        if (!registeredOres.containsKey(oreTag)) {
            OreWrapper ore = new OreWrapper(oreTag);
            registeredOres.put(oreTag, ore);
        }
        return registeredOres.get(oreTag);
    }

    private void replace(OreWrapper ore) {
        registeredOres.put(ore.oreTag, ore);
    }

    public void addVanillaBlocks() {
        for (String ore : OreDictionary.getOreNames()) {
            for (ItemStack oreStack : OreDictionary.getOres(ore)) {
                handleOre(ore, oreStack);
            }
        }
    }

    /** Clears out any registered ores that don't have both a block and a dust defined */
    public void clean() {
        for (OreWrapper ore : getRegisteredOres()) {
            if (ore.getOreBlocks().length == 0 || ore.getDust() == null) {
                registeredOres.remove(ore.oreTag);
            }
        }
    }

    public void fillGrindings() {
        for (OreWrapper ore : getRegisteredOres()) {
            ore.generateGrinding();
        }
    }

    @ForgeSubscribe
    public void oreDictionaryEvent(OreDictionary.OreRegisterEvent event) {
        handleOre(event.Name, event.Ore);
    }

    private void handleOre(String oreTag, ItemStack oreStack) {
        if (oreTag.startsWith("ore")) {
            String baseTag = oreTag.substring("ore".length());
            if (!blacklisted(baseTag)) {
                OreWrapper ore = getOre(baseTag);
                ore.addBlock(oreStack);
                if (ore.getGrinding() == null) {
                    ore.generateGrinding();
                }
                replace(ore);
            }
        } else if (oreTag.startsWith("dust") && !(oreTag.contains("Tiny"))) {
            String baseTag = oreTag.substring("dust".length());
            if (!blacklisted(oreTag)) {
                OreWrapper ore = getOre(baseTag);

                String owner = ModHandler.INSTANCE.getOwner(oreStack);

                if (preferredMod != null && !preferredMod.isEmpty() && Loader.isModLoaded(preferredMod)) {
                    if (owner != null && owner.equalsIgnoreCase(preferredMod)) {
                        if (ore.getDust() == null) {
                            CoreLogger.fine("Preferred mod found. Setting dust form of " + baseTag + " to " + preferredMod + "'s " + oreStack.getDisplayName());
                            ore.setDust(oreStack);
                            replace(ore);
                        }
                    }
                } else {
                    if (ore.getDust() == null) {
                        CoreLogger.fine("Preferred mod either not set or not found. Setting dust form of " + baseTag + " to " + (owner != null ? owner + "'s " : "") + oreStack.getDisplayName());
                        ore.setDust(oreStack);
                        replace(ore);
                    }
                }
            }
        }
    }

    private boolean blacklisted(String oreTag) {
        return (oreTag.equalsIgnoreCase("coal") || oreTag.equalsIgnoreCase("certusquartz") || oreTag.equalsIgnoreCase("nickel"));
    }

}