package de.client.base.event;

import de.client.base.eventapi.Event;

/**
 * Emitted when the user interacted via the keyboard while in game (screen is null)
 */
public class KeyboardEvent extends Event {

    private int key;

    public KeyboardEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
