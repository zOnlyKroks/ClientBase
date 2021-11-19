package de.client.base.module.modules.movement;

import de.client.base.event.MoveEvent;
import de.client.base.eventapi.EventManager;
import de.client.base.eventapi.EventTarget;
import de.client.base.module.Category;
import de.client.base.module.Module;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Box;

public class NoFallModule extends Module {

    public NoFallModule() {
        super("NoFall", "Falldamage is for losers", Category.MOVEMENT);
    }

    @Override
    protected void onEnable() {
        EventManager.register(this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        super.onDisable();
    }

    @EventTarget
    public void onMove(MoveEvent event) {
        mc.player.setOnGround(true);
    }

}
