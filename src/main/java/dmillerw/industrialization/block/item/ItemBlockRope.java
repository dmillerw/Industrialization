package dmillerw.industrialization.block.item;

import dmillerw.industrialization.block.BlockHandler;
import dmillerw.industrialization.lib.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * Created by Dylan Miller on 1/21/14
 */
public class ItemBlockRope extends ItemBlockCore {

    private Icon icon;

    public ItemBlockRope(int id) {
        super(id);
    }

    @Override
    public int getSpriteNumber() {
        return 1;
    }

    @Override
    public Icon getIconFromDamage(int damage) {
        return icon;
    }

    @Override
    public void registerIcons(IconRegister register) {
        icon = register.registerIcon(ModInfo.RESOURCE_PREFIX + "rope");
    }

    public boolean preBlockPlace(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz, int meta) {
        ForgeDirection dir = ForgeDirection.getOrientation(side).getOpposite();
        Block anchor = BlockHandler.blockAnchor;
        Block rope = BlockHandler.blockRope;
        int newX = x + dir.offsetX;
        int newY = y + dir.offsetY;
        int newZ = z + dir.offsetZ;

        int currID = world.getBlockId(newX, newY, newZ);

        if (currID == anchor.blockID|| currID == rope.blockID) {
            if (world.isAirBlock(newX, newY - 1, newZ)) {
                if (player.isSneaking()) {
                    placeRopesBelow(world, newX, newY, newZ, stack);
                } else {
                    placeRopeBelow(world, newX, newY, newZ, stack);
                }
            } else if (world.getBlockId(newX, newY - 1, newZ) == rope.blockID) {
                while(world.getBlockId(newX, newY - 1, newZ) == rope.blockID) {
                    newY--;
                }

                if (player.isSneaking()) {
                    placeRopesBelow(world, newX, newY, newZ, stack);
                } else {
                    placeRopeBelow(world, newX, newY, newZ, stack);
                }
            }
        }

        return false;
    }

    private boolean placeRopeBelow(World world, int x, int y, int z, ItemStack stack) {
        if (stack.stackSize > 0 && world.isAirBlock(x, y - 1, z)) {
            if (world.setBlock(x, y - 1, z, BlockHandler.blockRopeID, 0, 3)) {
                world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y - 1 + 0.5F), (double)((float)z + 0.5F), BlockHandler.blockRope.stepSound.getPlaceSound(), (BlockHandler.blockRope.stepSound.getVolume() + 1.0F) / 2.0F, BlockHandler.blockRope.stepSound.getPitch() * 0.8F);
                --stack.stackSize;
                return true;
            }
        }
        return false;
    }

    private void placeRopesBelow(World world, int x, int y, int z, ItemStack stack) {
        while(placeRopeBelow(world, x, y, z, stack)) {
            y--;
        }
    }

}
