package de.client.base.event;

import de.client.base.eventapi.Event;
import net.minecraft.client.util.math.MatrixStack;

/**
 * Emitted when the HUD renders
 */
public record RenderIngameHudEvent(MatrixStack stack, float tickDelta) implements Event {
}
