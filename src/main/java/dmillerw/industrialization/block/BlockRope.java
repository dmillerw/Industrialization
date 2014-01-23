package dmillerw.industrialization.block;

import dmillerw.industrialization.core.TabIndustrialization;
import dmillerw.industrialization.lib.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * Created by Dylan Miller on 1/21/14
 */
public class BlockRope extends Block {

    private Icon icon;

    public BlockRope(int id) {
        super(id, Material.cloth);

        setCreativeTab(TabIndustrialization.TAB);
        setHardness(0F);
        setResistance(0F);

        setBlockBounds(0.4375F, 0F, 0.4375F, 0.5625F, 1F, 0.5625F);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
        if (!world.isRemote) {
            if (!isAttached(world, x, y, z)) {
                this.dropBlockAsItem_do(world, x, y, z, new ItemStack(this));
                world.setBlockToAir(x, y, z);
            }
        }
    }

    @Override
    public Icon getIcon(int side, int meta) {
        return icon;
    }

    @Override
    public void registerIcons(IconRegister register) {
        icon = register.registerIcon(ModInfo.RESOURCE_PREFIX + "rope");
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isLadder(World world, int x, int y, int z, EntityLivingBase entity) {
        return true;
    }

    public static boolean isAttached(World world, int x, int y, int z) {
        return (!world.isAirBlock(x, y + 1, z) && (world.isBlockSolidOnSide(x, y + 1, z, ForgeDirection.DOWN) || world.getBlockId(x, y + 1, z) == BlockHandler.blockRopeID));
    }

}

