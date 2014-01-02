package dmillerw.industrialization;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dmillerw.industrialization.block.BlockHandler;
import dmillerw.industrialization.core.IDHandler;
import dmillerw.industrialization.core.proxy.ServerProxy;
import dmillerw.industrialization.lib.ModInfo;
import dmillerw.industrialization.recipe.CrushingManager;
import net.minecraftforge.common.Configuration;

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

        BlockHandler.initializeIDs(new IDHandler(this.config, 2000));
        BlockHandler.initialize();

        proxy.registerRenders();

        if (this.config.hasChanged()) {
            this.config.save();
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        CrushingManager.INSTANCE.initializeRecipes();
    }

}