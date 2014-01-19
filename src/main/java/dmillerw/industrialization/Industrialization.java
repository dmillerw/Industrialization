package dmillerw.industrialization;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dmillerw.industrialization.block.BlockHandler;
import dmillerw.industrialization.core.IDAllocator;
import dmillerw.industrialization.core.handler.GuiHandler;
import dmillerw.industrialization.core.handler.ItemMapper;
import dmillerw.industrialization.core.ore.OreHandler;
import dmillerw.industrialization.core.ore.OreWrapper;
import dmillerw.industrialization.core.proxy.ServerPacketProxy;
import dmillerw.industrialization.core.proxy.ServerProxy;
import dmillerw.industrialization.item.ItemHandler;
import dmillerw.industrialization.lib.ModInfo;
import dmillerw.industrialization.network.PacketHandler;
import dmillerw.industrialization.recipe.CrushingManager;
import dmillerw.industrialization.recipe.FilterManager;
import dmillerw.industrialization.util.UtilString;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

@Mod(modid=ModInfo.ID, name=ModInfo.NAME, version= ModInfo.VERSION)
@NetworkMod(channels = {ModInfo.CHANNEL}, packetHandler = PacketHandler.class)
public class Industrialization {

    @Mod.Instance(ModInfo.ID)
    public static Industrialization instance;

    @SidedProxy(serverSide = ModInfo.SERVER_PROXY, clientSide = ModInfo.CLIENT_PROXY)
    public static ServerProxy proxy;

    @SidedProxy(serverSide = ModInfo.SERVER_PACKET_PROXY, clientSide = ModInfo.CLIENT_PACKET_PROXY)
    public static ServerPacketProxy packetProxy;

    public File configDirectory;

    public Configuration config;

    public ItemMapper grindingMapper;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.configDirectory = new File(event.getModConfigurationDirectory(), "Industrialization");
        this.config = new Configuration(new File(configDirectory, "Industrialization"));

        this.config.load();

        BlockHandler.initialize(new IDAllocator(this.config, 3000));

        ItemHandler.initialize(new IDAllocator(this.config, 15000));

        if (this.config.hasChanged()) {
            this.config.save();
        }

        this.grindingMapper = new ItemMapper(new File(configDirectory, "GrindingMapping.properties"));

        OreHandler.INSTANCE.addVanillaBlocks();

        MinecraftForge.EVENT_BUS.register(OreHandler.INSTANCE);

        proxy.registerRenders();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        // It's assumed that all ore dictionary registrations have been completed by the time we hit post-init
        OreHandler.INSTANCE.clean();
        OreHandler.INSTANCE.fillGrindings();

        CrushingManager.INSTANCE.initializeRecipes();
        FilterManager.INSTANCE.initializeRecipes();

        this.grindingMapper.save();

        // Don't know what the rules are on localization, but I don't think handling some here is a bad thing
        // Correct me if I'm wrong though
        for (OreWrapper ore : OreHandler.INSTANCE.getRegisteredOres()) {
            LanguageRegistry.addName(ore.getGrinding(), UtilString.insertSpacing(ore.oreTag) + " Grinding");
        }
    }

}