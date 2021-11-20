package de.client.base.event;

import de.client.base.eventapi.Event;

/**
 * Emitted when the user interacted via the keyboard while in game (screen is null)
 */
public record KeyboardEvent(int key) implements Event {

}
