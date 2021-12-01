package de.client.base.event;

import de.client.base.eventapi.Event;
import net.minecraft.network.Packet;

public class PacketReadEvent extends Event {

    private Packet<?> packet;

    public PacketReadEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }
}
