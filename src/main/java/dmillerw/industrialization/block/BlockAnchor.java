package dmillerw.industrialization.block;

import dmillerw.industrialization.client.render.block.RenderBlockAnchor;
import dmillerw.industrialization.client.render.block.SimpleBlockRenderer;
import dmillerw.industrialization.core.TabIndustrialization;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * Created by Dylan Miller on 1/24/14
 */
public class BlockAnchor extends Block {

    public BlockAnchor(int id) {
        super(id, Material.iron);

        setHardness(1F);
        setResistance(1F);
        setCreativeTab(TabIndustrialization.TAB);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int causeID) {
        if (!world.isRemote) {
            ForgeDirection orientation = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
            Block block = Block.blocksList[world.getBlockId(x + orientation.offsetX, y + orientation.offsetY, z + orientation.offsetZ)];

            if (block == null || !(block.isBlockSolidOnSide(world, x + orientation.offsetX, y + orientation.offsetY, z + orientation.offsetZ, orientation.getOpposite()))) {
                this.dropBlockAsItem_do(world, x, y, z, new ItemStack(this));
                world.setBlockToAir(x, y, z);
            }
        }
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);

        switch(meta) {
            case 1: {
                this.setBlockBounds(0.4375F, 0.375F, 0.4375F, 0.5625F, 1F, 0.5625F);
                break;
            }

            case 2: {
                this.setBlockBounds(0.4375F, 0.4375F, 0, 0.5625F, 0.5625F, 0.6125F);
                break;
            }

            case 3: {
                this.setBlockBounds(0.4375F, 0.4375F, 0.375F, 0.5625F, 0.5625F, 1);
                break;
            }

            case 4: {
                this.setBlockBounds(0, 0.4375F, 0.4375F, 0.6125F, 0.5625F, 0.5625F);
                break;
            }

            case 5: {
                this.setBlockBounds(0.375F, 0.4375F, 0.4375F, 1, 0.5625F, 0.5625F);
                break;
            }
        }
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
    public Icon getIcon(int side, int meta) {
        return Block.blockIron.getIcon(0, 0);
    }

    @Override
    public int getRenderType() {
        return SimpleBlockRenderer.getRenderID(RenderBlockAnchor.class);
    }

}
