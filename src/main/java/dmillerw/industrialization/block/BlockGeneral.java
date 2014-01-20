package dmillerw.industrialization.block;

import cpw.mods.fml.common.network.PacketDispatcher;
import dmillerw.industrialization.core.TabIndustrialization;
import dmillerw.industrialization.lib.ModInfo;
import dmillerw.industrialization.network.packet.PacketFX;
import dmillerw.industrialization.recipe.CrushingManager;
import dmillerw.industrialization.recipe.CrushingRecipe;
import dmillerw.industrialization.util.UtilStack;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * Created by Dylan Miller on 1/19/14
 */
public class BlockGeneral extends Block {

    public static final String[] NAMES = new String[] {"reinforced_iron"};

    public Icon[] icons;

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
                for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
                    // Coordinates of piston head
                    int nx = x + side.offsetX;
                    int ny = y + side.offsetY;
                    int nz = z + side.offsetZ;

                    if (world.getBlockId(nx, ny, nz) == Block.pistonMoving.blockID) {
                        TileEntityPiston piston = (TileEntityPiston) world.getBlockTileEntity(nx, ny, nz);

                        // If there is an actively extending piston on that side, and it's pushing towards the block
                        if (ForgeDirection.getOrientation(piston.getPistonOrientation()) == side.getOpposite()) {
                            // Coordinates of block being smashed
                            int opX = x + side.getOpposite().offsetX;
                            int opY = y + side.getOpposite().offsetY;
                            int opZ = z + side.getOpposite().offsetZ;

                            // Data of block being smashed
                            int opID = world.getBlockId(opX, opY, opZ);
                            int opMeta = world.getBlockMetadata(opX, opY, opZ);

                            // Coordinates of block being pressed against
                            int aX = opX + side.getOpposite().offsetX;
                            int aY = opY + side.getOpposite().offsetY;
                            int aZ = opZ + side.getOpposite().offsetZ;

                            // Data of block being pressed against
                            int aID = world.getBlockId(aX, aY, aZ);
                            int aMeta = world.getBlockMetadata(aX, aY, aZ);

                            if (!world.isAirBlock(aX, aY, aZ)) {
                                CrushingRecipe result = CrushingManager.INSTANCE.getRecipeFor(new ItemStack(opID, 1, opMeta));

                                if (result != null) {
                                    UtilStack.dropStack(world, opX, opY, opZ, result.getOutput());
                                    PacketFX packet = new PacketFX(opX, opY, opZ, new ItemStack(opID, 1, opMeta));
                                    PacketDispatcher.sendPacketToAllAround(opX, opY, opZ, PacketFX.MAX_PARTICLE_RANGE, world.provider.dimensionId, packet.toVanilla());
                                    world.setBlockToAir(opX, opY, opZ);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Icon getIcon(int side, int meta) {
        return icons[meta];
    }

    @Override
    public void registerIcons(IconRegister register) {
        this.icons = new Icon[1];

        this.icons[0] = register.registerIcon(ModInfo.RESOURCE_PREFIX + "reinforced_iron");
    }

}
