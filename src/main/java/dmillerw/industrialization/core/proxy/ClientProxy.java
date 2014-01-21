package dmillerw.industrialization.core.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import dmillerw.industrialization.block.tile.TileConveyor;
import dmillerw.industrialization.client.render.block.RenderBlockConveyor;
import dmillerw.industrialization.client.render.tile.RenderTileConveyor;

/**
 * Created by Dylan Miller on 1/1/14
 */
public class ClientProxy extends ServerProxy {

    @Override
    public void registerRenders() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileConveyor.class, new RenderTileConveyor());

        RenderingRegistry.registerBlockHandler(new RenderBlockConveyor());
    }

}
