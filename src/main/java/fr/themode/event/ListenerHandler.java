package fr.themode.event;

import fr.themode.event.listener.ListenerData;

import java.util.*;

public class ListenerHandler {

    private Map<EventListener, Set<FunctionData>> listenerFunctions;
    private Map<EventFunction, Class> eventClass;

    private Map<Class<? extends Event>, Set<FunctionData>> eventFunctions;

    protected ListenerHandler() {
        this.listenerFunctions = new HashMap<>();
        this.eventClass = new HashMap<>();
        this.eventFunctions = new HashMap<>();
    }


    public void addListener(EventListener listener) {
        listener.register(this);
    }

    public void removeListener(EventListener listener) {
        Set<FunctionData> listenerFunctions = this.listenerFunctions.getOrDefault(listener, new HashSet<>());
        this.listenerFunctions.remove(listener);
        if (listenerFunctions.isEmpty())
            return;

        for (FunctionData functionData : listenerFunctions) {
            EventFunction function = functionData.function;
            Class clazz = functionData.clazz;
            this.eventClass.remove(function);
            Set<FunctionData> eventFunctions = this.eventFunctions.getOrDefault(clazz, getSortedSet());
            eventFunctions.remove(functionData);

            if (!eventFunctions.isEmpty()) {
                this.eventFunctions.put(clazz, eventFunctions);
            } else {
                this.eventFunctions.remove(clazz);
            }
        }

    }

    public <T extends Event> void addEventListener(ListenerData data, Class<T> clazz, EventFunction<T> function) {
        EventListener eventListener = data.getEventListener();
        FunctionData functionData = new FunctionData(function, data, clazz);

        Set<FunctionData> listenerFunctions = this.listenerFunctions.getOrDefault(eventListener, getSortedSet());
        listenerFunctions.add(functionData);

        this.listenerFunctions.put(eventListener, listenerFunctions);

        Set<FunctionData> eventFunctions = this.eventFunctions.getOrDefault(clazz, getSortedSet());
        eventFunctions.add(functionData);

        this.eventFunctions.put(clazz, eventFunctions);

        this.eventClass.put(function, clazz);
    }

    public void callEvent(Event event) {
        Set<FunctionData> functionsData = eventFunctions.getOrDefault(event.getClass(), getSortedSet());

        for (FunctionData functionData : functionsData) {
            functionData.function.trigger(event);
        }

    }

    private TreeSet<FunctionData> getSortedSet() {
        return new TreeSet<>(Comparator.comparing(FunctionData::getPriority));
    }

    private class FunctionData {

        private EventFunction function;
        private ListenerData data;
        private Class<? extends Event> clazz;

        private int priority;

        private FunctionData(EventFunction function, ListenerData data, Class<? extends Event> clazz) {
            this.function = function;
            this.data = data;
            this.clazz = clazz;
            this.priority = data.getPriority();
        }

        public int getPriority() {
            return priority;
        }
    }

}
