package fr.themode.event;

public interface EventFunction<T extends Event> {

    void trigger(T event);

}
