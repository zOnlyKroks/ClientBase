package de.client.base.eventapi;

/**
 * The base class of each event. Does nothing on its own
 */
public class Event {

    private boolean cancelled = false;

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
