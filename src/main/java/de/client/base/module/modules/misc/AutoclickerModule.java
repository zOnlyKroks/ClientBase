package de.client.base.module.modules.misc;

import de.client.base.event.UpdateEvent;
import de.client.base.eventapi.EventManager;
import de.client.base.eventapi.EventTarget;
import de.client.base.module.Category;
import de.client.base.module.Module;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

public class AutoclickerModule extends Module {

    private double ticks = 0;

    public AutoclickerModule() {
        super("Autoclicker", "Be a cool guy", Category.MISC);
    }

    @Override protected void onEnable() {
        EventManager.register(this);
    }

    @Override protected void onDisable() {
        EventManager.unregister(this);
    }

    @EventTarget public void onUpdate(UpdateEvent event) {
        //1 tick = 50ms
        //2 tick = 100ms
        //4 tick = 200ms
        ticks++;
        System.out.println(ticks);

        if (ticks >= 1) {
            if (mc.crosshairTarget.getType() != HitResult.Type.BLOCK) {
                return;
            }
            BlockHitResult result = (BlockHitResult) mc.crosshairTarget;
//            mc.player.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, result));
            mc.interactionManager.interactBlock(mc.player,mc.world,Hand.MAIN_HAND,result);
            ticks = 0;
        }
    }

}
