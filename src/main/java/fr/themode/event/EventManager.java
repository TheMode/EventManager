package fr.themode.event;

public class EventManager {

    private ListenerHandler handler;

    public EventManager() {
        this.handler = new ListenerHandler();
    }

    public void callEvent(Event event) {
        handler.callEvent(event);
    }

    public void addListener(EventListener listener) {
        handler.addListener(listener);
    }

    public void removeListener(EventListener listener) {
        handler.removeListener(listener);
    }

    public void unregister(EventListener listener) {

    }

}
