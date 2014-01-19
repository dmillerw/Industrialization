package dmillerw.industrialization.network.packet;

import dmillerw.industrialization.lib.ModInfo;
import dmillerw.industrialization.network.PacketHandler;
import net.minecraft.network.packet.Packet250CustomPayload;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class PacketCore {

    public PacketHandler.PacketType getType() {
        return PacketHandler.getTypeForClass(this.getClass());
    }
    
	private byte[] getContents() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		try {
			dos.writeByte(PacketHandler.getTypeForClass(this.getClass()).ordinal());
			this.writeData(dos);
		} catch(IOException e) {
			e.printStackTrace(System.err);
		}

		return bos.toByteArray();
	}
	
	public Packet250CustomPayload toVanilla() {
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = ModInfo.CHANNEL;
		packet.data = getContents();
		packet.length = packet.data.length;
		packet.isChunkDataPacket = false;
		return packet;
	}
	
	public abstract void readData(DataInputStream dis) throws IOException;	

	public abstract void writeData(DataOutputStream dos) throws IOException;
	
}
