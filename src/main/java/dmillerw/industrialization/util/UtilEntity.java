package dmillerw.industrialization.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * Created by Dylan Miller on 1/19/14
 */
public class UtilEntity {

    public static ForgeDirection determine2DOrientation_Forge(World world, int x, int y, int z, EntityLivingBase entity) {
        return ForgeDirection.getOrientation(determine2DOrientation_I(world, x, y, z, entity));
    }

    public static int determine2DOrientation_I(World world, int x, int y, int z, EntityLivingBase entity) {
        int rotation = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        switch(rotation) {
            case 0: return 2;
            case 1: return 5;
            case 2: return 3;
            case 3: return 4;
            default: return -1;
        }
    }

    public static ForgeDirection determine3DOrientation_Forge(World world, int x, int y, int z, EntityLivingBase entity) {
        return ForgeDirection.getOrientation(determine3DOrientation_I(world, x, y, z, entity));
    }

    public static int determine3DOrientation_I(World par0World, int par1, int par2, int par3, EntityLivingBase par4EntityLivingBase) {
        if (MathHelper.abs((float) par4EntityLivingBase.posX - (float) par1) < 2.0F && MathHelper.abs((float) par4EntityLivingBase.posZ - (float) par3) < 2.0F) {
            double d0 = par4EntityLivingBase.posY + 1.82D - (double) par4EntityLivingBase.yOffset;

            if (d0 - (double) par2 > 2.0D) {
                return 1;
            }

            if ((double) par2 - d0 > 0.0D) {
                return 0;
            }
        }

        int l = MathHelper.floor_double((double) (par4EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
    }

}
