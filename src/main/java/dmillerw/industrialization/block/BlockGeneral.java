package dmillerw.industrialization.block;

import dmillerw.industrialization.core.TabIndustrialization;
import dmillerw.industrialization.recipe.CrushingManager;
import dmillerw.industrialization.recipe.CrushingRecipe;
import dmillerw.industrialization.util.UtilStack;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

/**
 * Created by Dylan Miller on 1/19/14
 */
public class BlockGeneral extends Block {

    public static final String[] NAMES = new String[] {"Reinforced Iron"};

    public BlockGeneral(int id) {
        super(id, Material.iron);

        setCreativeTab(TabIndustrialization.TAB);
        setHardness(5F);
        setResistance(10F);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        if (!world.isRemote) {
            int meta = world.getBlockMetadata(x, y, z);

            if (meta == 0) {
                if (world.getBlockId(x, y + 1, z) == Block.pistonMoving.blockID) {
                    Block below = Block.blocksList[world.getBlockId(x, y - 1, z)];
                    int belowMeta = world.getBlockMetadata(x, y - 1, z);

                    if (below != null) {
                        CrushingRecipe result = CrushingManager.INSTANCE.getRecipeFor(new ItemStack(below, 1, belowMeta));

                        if (result != null) {
                            UtilStack.dropStack(world, x, y - 1, z, result.getOutput());
                            // TODO Particles
                            world.setBlockToAir(x, y - 1, z);
                        }
                    }
                }
            }
        }
    }

    @Override
    public Icon getIcon(int side, int meta) {
        return Block.blockIron.getIcon(0, 0); // Temp
    }

}
