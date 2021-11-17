package de.client.base.event;

import de.client.base.eventapi.Event;

public class KeyboardEvent implements Event {

    private int key;

    public KeyboardEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
