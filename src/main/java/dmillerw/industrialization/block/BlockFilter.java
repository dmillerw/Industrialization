package dmillerw.industrialization.block;

import dmillerw.industrialization.block.tile.TileCore;
import dmillerw.industrialization.block.tile.TileFilter;
import dmillerw.industrialization.client.render.block.SimpleBlockRenderer;
import dmillerw.industrialization.lib.ModInfo;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

/**
 * Created by Dylan Miller on 1/1/14
 */
public class BlockFilter extends BlockCore {

    public Icon[] icons;

    public BlockFilter(int id) {
        super(id, Material.iron);

        setHardness(2F);
        setResistance(2F);
    }

    @Override
    public Icon getIcon(int side, int meta) {
        return (side == 1 || side == 0) ? icons[0] : icons[1];
    }

    @Override
    public void registerIcons(IconRegister register) {
        icons = new Icon[2];

        icons[0] = register.registerIcon(ModInfo.RESOURCE_PREFIX + "filter");
        icons[1] = register.registerIcon(ModInfo.RESOURCE_PREFIX + "filter_side");
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public TileCore getTile(int meta) {
        return new TileFilter();
    }

}
