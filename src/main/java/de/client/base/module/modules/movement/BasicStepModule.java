package de.client.base.module.modules.movement;

import de.client.base.config.MultiValue;
import de.client.base.event.MoveEvent;
import de.client.base.eventapi.EventManager;
import de.client.base.eventapi.EventTarget;
import de.client.base.module.Category;
import de.client.base.module.Module;
import net.minecraft.util.math.Box;

public class BasicStepModule extends Module {

    final MultiValue mode = (MultiValue) this.config.create("Mode", "Static", "Vanilla", "Static", "3D", "Jetpack").description("Which type of fly to do");

    public BasicStepModule() {
        super("Step", "Space Bars are overrated", Category.MOVEMENT);
    }

    @Override
    protected void onEnable() {
        EventManager.register(this);
    }

    @Override
    protected void onDisable() {
        EventManager.unregister(this);
    }

    @EventTarget
    public void onMove(MoveEvent event) {
        if(!isTouchingWall(mc.player.getBoundingBox())) return;

        mc.player.jump();
    }

    private boolean isTouchingWall(Box box) {
        // Check in 2 calls instead of just box.expand(0.01, 0, 0.01) to prevent it getting stuck in corners
        return !mc.world.isSpaceEmpty(box.expand(0.02, 0, 0)) || !mc.world.isSpaceEmpty(box.expand(0, 0, 0.01));
    }


}
