package de.client.base.event;

import de.client.base.eventapi.Event;
import net.minecraft.client.util.math.MatrixStack;

public class RenderIngameHudEvent implements Event {

    private final MatrixStack stack;
    private final float       tickDelta;

    public RenderIngameHudEvent(MatrixStack stack, float tickDelta) {
        this.stack = stack;
        this.tickDelta = tickDelta;
    }

    public MatrixStack getStack() {
        return stack;
    }

    public float getTickDelta() {
        return tickDelta;
    }
}
