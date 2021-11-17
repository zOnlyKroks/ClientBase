package de.client.base.module.modules.movement;

import de.client.base.event.MoveEvent;
import de.client.base.eventapi.EventManager;
import de.client.base.eventapi.EventTarget;
import de.client.base.module.Category;
import de.client.base.module.Module;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class BasicStepModule extends Module {

    public BasicStepModule() {
        super("Step", "Space Bars are overrated", Category.MOVEMENT, GLFW.GLFW_KEY_X);
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
        if(mc.player.getMovementDirection() == Direction.NORTH) {
            mc.player.addVelocity(0.5,0,0);
        }else if(mc.player.getMovementDirection() == Direction.SOUTH) {
            mc.player.addVelocity(0,0,0.5);
        }
    }

    private boolean isTouchingWall(Box box) {
        // Check in 2 calls instead of just box.expand(0.01, 0, 0.01) to prevent it getting stuck in corners
        return !mc.world.isSpaceEmpty(box.expand(0.02, 0, 0)) || !mc.world.isSpaceEmpty(box.expand(0, 0, 0.01));
    }


}
