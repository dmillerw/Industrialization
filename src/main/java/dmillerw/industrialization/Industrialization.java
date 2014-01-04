package dmillerw.industrialization;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dmillerw.industrialization.block.BlockHandler;
import dmillerw.industrialization.core.IDAllocator;
import dmillerw.industrialization.core.ore.OreHandler;
import dmillerw.industrialization.core.ore.OreWrapper;
import dmillerw.industrialization.core.proxy.ServerProxy;
import dmillerw.industrialization.item.ItemHandler;
import dmillerw.industrialization.lib.ModInfo;
import dmillerw.industrialization.recipe.CrushingManager;
import dmillerw.industrialization.util.UtilString;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

@Mod(modid=ModInfo.ID, name=ModInfo.NAME, version= ModInfo.VERSION)
public class Industrialization {

    @Mod.Instance(ModInfo.ID)
    public static Industrialization instance;

    @SidedProxy(serverSide = ModInfo.SERVER_PROXY, clientSide = ModInfo.CLIENT_PROXY)
    public static ServerProxy proxy;

    public Configuration config;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.config = new Configuration(new File(event.getModConfigurationDirectory(), ModInfo.NAME));

        this.config.load();

        BlockHandler.initializeIDs(new IDAllocator(this.config, 3000));
        BlockHandler.initialize();

        ItemHandler.initializeIDs(new IDAllocator(this.config, 15000));
        ItemHandler.initialize();

        if (this.config.hasChanged()) {
            this.config.save();
        }

        proxy.registerRenders();

        OreHandler.INSTANCE.addVanillaBlocks();
        MinecraftForge.EVENT_BUS.register(OreHandler.INSTANCE);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        CrushingManager.INSTANCE.initializeRecipes();

        // It's assumed that all ore dictionary registrations have been completed by the time we hit post-init
        OreHandler.INSTANCE.clean();

        // Don't know what the rules are on localization, but I don't think handling some here is a bad thing
        // Correct me if I'm wrong though
        for (OreWrapper ore : OreHandler.INSTANCE.getRegisteredOres()) {
            LanguageRegistry.addName(ore.getGrinding(), UtilString.insertSpacing(ore.oreTag) + " Grinding");
        }
    }

}