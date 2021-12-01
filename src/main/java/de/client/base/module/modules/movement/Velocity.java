package de.client.base.module.modules.movement;

import de.client.base.event.PacketReadEvent;
import de.client.base.event.UpdateEvent;
import de.client.base.eventapi.EventManager;
import de.client.base.eventapi.EventTarget;
import de.client.base.module.Category;
import de.client.base.module.Module;
import de.client.base.util.FabricReflect;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;

public class Velocity extends Module {

    public Velocity() {
        super("Velocity", "Knockback for losers", Category.MISC);
    }

    @Override protected void onEnable() {
        EventManager.register(this);
    }

    @Override protected void onDisable() {
        EventManager.unregister(this);
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if(mc.player == null || mc.player.hurtTime <= 0) return;
        System.out.println(mc.player.hurtTime);
        mc.player.setOnGround(true);
    }

    @EventTarget
    public void onPacketRead(PacketReadEvent event) {

        if(!(event.getPacket() instanceof EntityVelocityUpdateS2CPacket)) return;

        EntityVelocityUpdateS2CPacket packet = (EntityVelocityUpdateS2CPacket) event.getPacket();

        if(mc.player == null || mc.player.hurtTime <= 0) return;
        if (packet.getId() == mc.player.getEntityId()) {
            FabricReflect.writeField(packet, (int) (packet.getVelocityX() * (0.5 / 100)), "field_12563", "velocityX");
            FabricReflect.writeField(packet, (int) (packet.getVelocityY() * (0.5 / 100)), "field_12562", "velocityY");
            FabricReflect.writeField(packet, (int) (packet.getVelocityZ() * (0.5 / 100)), "field_12561", "velocityZ");
        }
    }
}
