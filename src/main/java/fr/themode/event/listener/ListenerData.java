package fr.themode.event.listener;

import fr.themode.event.EventListener;

public class ListenerData {

    private EventListener eventListener;
    private int priority;

    private ListenerData(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public static ListenerData create(EventListener eventListener) {
        return new ListenerData(eventListener);
    }

    public ListenerData withPriority(int priority) {
        setPriority(priority);
        return this;
    }

    public EventListener getEventListener() {
        return eventListener;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
