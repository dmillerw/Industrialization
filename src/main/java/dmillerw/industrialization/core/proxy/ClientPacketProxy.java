package dmillerw.industrialization.core.proxy;

import cpw.mods.fml.client.FMLClientHandler;
import dmillerw.industrialization.lib.client.EnumParticle;
import dmillerw.industrialization.network.PacketHandler;
import dmillerw.industrialization.network.packet.PacketCore;
import dmillerw.industrialization.network.packet.PacketFX;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Dylan Miller on 1/19/14
 */
public class ClientPacketProxy extends ServerPacketProxy {

    @Override
    public void handlePacket(PacketHandler.PacketType type, PacketCore packet, EntityPlayer player) {
        super.handlePacket(type, packet, player);

        if (type == PacketHandler.PacketType.PACKET_FX) {
            handleFXPacket((PacketFX) packet);
        }
    }

    private void handleFXPacket(PacketFX packet) {
        switch(packet.fxType) {
            // Particle
            case 0: {
                EnumParticle particle = EnumParticle.values()[packet.extraData[0]];
                particle.display(FMLClientHandler.instance().getClient().theWorld, packet.x, packet.y, packet.z, 0, 0, 0);
            }

            // Block destroy FX
            case 1: {
                if (packet.extraData[0] > 0 && packet.extraData[0] < 4096) {
                    FMLClientHandler.instance().getClient().effectRenderer.addBlockDestroyEffects((int)packet.x, (int)packet.y, (int)packet.z, packet.extraData[0], packet.extraData[1]);
                }
            }
        }
    }

}
