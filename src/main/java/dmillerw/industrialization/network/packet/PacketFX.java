package dmillerw.industrialization.network.packet;

import dmillerw.industrialization.lib.client.EnumParticle;
import net.minecraft.item.ItemStack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketFX extends PacketCore {

    public static final int MAX_PARTICLE_RANGE = 64;

    public static final int FX_PARTICLE = 0;
    public static final int FX_BLOCK_BREAK = 1;

    public double x;
    public double y;
    public double z;
    
    public int fxType;
    
    public int[] extraData;
    
    public PacketFX() {
        
    }
    
    public PacketFX(double x, double y, double z, int fxType, int ... extraData) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.fxType = fxType;
        this.extraData = extraData;
    }
    
    public PacketFX(double x, double y, double z, EnumParticle particle) {
        this(x, y, z, FX_PARTICLE, new int[] {particle.ordinal()});
    }
    
    public PacketFX(double x, double y, double z, ItemStack stack) {
        this(x, y, z, FX_BLOCK_BREAK, new int[] {stack.itemID, stack.getItemDamage()});
    }
    
    @Override
    public void readData(DataInputStream dis) throws IOException {
        this.x = dis.readDouble();
        this.y = dis.readDouble();
        this.z = dis.readDouble();
        this.fxType = dis.readInt();
        
        int arrayLength = dis.readInt();
        this.extraData = new int[arrayLength];
        for (int i=0; i<arrayLength; i++) {
            this.extraData[i] = dis.readInt();
        }
    }

    @Override
    public void writeData(DataOutputStream dos) throws IOException {
        dos.writeDouble(this.x);
        dos.writeDouble(this.y);
        dos.writeDouble(this.z);
        dos.writeInt(this.fxType);
        
        dos.writeInt(this.extraData != null ? this.extraData.length : 0);
        for (int i=0; i<this.extraData.length; i++) {
            dos.writeInt(this.extraData[i]);
        }
    }

}
