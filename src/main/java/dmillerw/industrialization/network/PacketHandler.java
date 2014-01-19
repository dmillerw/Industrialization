package dmillerw.industrialization.network;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import dmillerw.industrialization.Industrialization;
import dmillerw.industrialization.network.packet.PacketCore;
import dmillerw.industrialization.network.packet.PacketFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class PacketHandler implements IPacketHandler {

	public static BiMap<PacketType, Class<? extends PacketCore>> packets;
	
	static {
		ImmutableBiMap.Builder<PacketType, Class<? extends PacketCore>> builder = ImmutableBiMap.<PacketType, Class<? extends PacketCore>>builder();
	
		for (PacketType type : PacketType.values()) {
			builder.put(type, type.packetClass);
		}
		
		packets = builder.build();
	}
	
	public static PacketType getTypeForClass(Class<? extends PacketCore> clazz) {
		return packets.inverse().get(clazz);
	}
	
	public static Class<? extends PacketCore> getClassForType(PacketType type) {
		return packets.get(type);
	}
	
	public static PacketCore generatePacket(byte[] data) {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		int packetType = bis.read();
		DataInputStream dis = new DataInputStream(bis);
		
		PacketCore packet = null;
		
		try {
			packet = PacketHandler.getClassForType(PacketType.values()[packetType]).newInstance();
			packet.readData(dis);
		} catch(Exception ex) {
			ex.printStackTrace(System.err);
		}
		
		return packet;
	}
	
	public static enum PacketType {
		
		PACKET_FX(PacketFX.class);
		
		public final Class packetClass;
		
		private PacketType(Class packetClass) {
			this.packetClass = packetClass;
		}
		
	}
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		PacketCore packetCore = generatePacket(packet.data);
		Industrialization.packetProxy.handlePacket(packetCore.getType(), packetCore, (EntityPlayer)player);
	}

}
