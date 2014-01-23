package dmillerw.industrialization.block.item;

import dmillerw.industrialization.block.BlockHandler;
import dmillerw.industrialization.block.BlockRope;
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
        if (BlockRope.isAttached(world, x, y, z)) {
            return true;
        } else { // If rope isnt' directly considered attached, check to see if they simply right-clicked on a rope block
            ForgeDirection dir = ForgeDirection.getOrientation(side).getOpposite();
            Block rope = BlockHandler.blockRope;
            int newX = x + dir.offsetX;
            int newY = y + dir.offsetY;
            int newZ = z + dir.offsetZ;

            if (world.getBlockId(newX, newY, newZ) == rope.blockID) {
                while (world.getBlockId(newX, newY, newZ) == rope.blockID) {
                    newY--;

                    if (world.isAirBlock(newX, newY, newZ)) {
                        if (world.setBlock(newX, newY, newZ, rope.blockID)) {
                            world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), rope.stepSound.getPlaceSound(), (rope.stepSound.getVolume() + 1.0F) / 2.0F, rope.stepSound.getPitch() * 0.8F);
                            --stack.stackSize;

                            if (!player.isSneaking() || stack.stackSize <= 0) {
                                break;
                            }
                        }
                    }
                }

                return false; // To stop normal execution of block placement
            }
        }

        return false;
    }

}
