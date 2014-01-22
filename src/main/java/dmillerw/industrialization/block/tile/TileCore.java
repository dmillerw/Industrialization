package dmillerw.industrialization.block.tile;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

/**
 * Created by Dylan Miller on 1/1/14
 */
public abstract class TileCore extends TileEntity {

    public boolean firstLoad = false;

    public int getSignalStrength(ForgeDirection side) { return 0; }

    public boolean onBlockActivated(EntityPlayer player) { return false; }

    public void onBlockAdded(EntityPlayer player) {}

    public void onNeighborBlockUpdate() {}

    public void onBlockBreak() {}

    public void onFirstLoad() {}

    public abstract void writeCustomNBT(NBTTagCompound nbt);

    public abstract void readCustomNBT(NBTTagCompound nbt);

    @Override
    public void updateEntity() {
        if (firstLoad) {
            onFirstLoad();
            firstLoad = false;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        writeCustomNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        readCustomNBT(nbt);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, tag);
    }

    public void sendClientUpdate(NBTTagCompound tag) {
        PacketDispatcher.sendPacketToAllInDimension((new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag)), this.worldObj.provider.dimensionId);
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
        switch(pkt.actionType) {
            case 0: readFromNBT(pkt.data); break;
            default: break;
        }
        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
    }

}
