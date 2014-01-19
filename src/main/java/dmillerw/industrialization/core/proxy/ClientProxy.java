package dmillerw.industrialization.core.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import dmillerw.industrialization.block.tile.TileTable;
import dmillerw.industrialization.client.render.block.RenderBlockTable;
import dmillerw.industrialization.client.render.tile.RenderTileTable;

/**
 * Created by Dylan Miller on 1/1/14
 */
public class ClientProxy extends ServerProxy {

    @Override
    public void registerRenders() {
        RenderingRegistry.registerBlockHandler(new RenderBlockTable());

        ClientRegistry.bindTileEntitySpecialRenderer(TileTable.class, new RenderTileTable());
    }

}
