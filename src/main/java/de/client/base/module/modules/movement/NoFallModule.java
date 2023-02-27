package de.client.base.module.modules.movement;

import de.client.base.event.MoveEvent;
import de.client.base.eventapi.EventManager;
import de.client.base.eventapi.EventTarget;
import de.client.base.module.Category;
import de.client.base.module.Module;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoFallModule extends Module {

    public NoFallModule() {
        super("NoFall", "Falldamage is for losers", Category.MOVEMENT);
    }

    @Override protected void onEnable() {
        EventManager.register(this);
    }

    @Override public void onDisable() {
        EventManager.unregister(this);
    }

    @EventTarget public void onMove(MoveEvent event) {
        if (mc.player.fallDistance < 2.5f) {
            return;
        }
        if (mc.player.isFallFlying()) {
            return;
        }
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() - 420.69, mc.player.getZ(), true));
        mc.player.fallDistance = 0;
    }

}
