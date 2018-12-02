package fr.themode.event.demo;

import fr.themode.event.Event;
import fr.themode.event.EventListener;
import fr.themode.event.EventManager;
import fr.themode.event.ListenerHandler;
import fr.themode.event.impl.TestEvent;
import fr.themode.event.listener.ListenerData;

public class Main {

    public static void main(String args[]) {
        new Main();
    }


    public Main() {
        EventManager manager = new EventManager();

        TestListener testListener = new TestListener();

        manager.addListener(testListener);

        long time = System.currentTimeMillis();

        Event event = new Event();
        TestEvent testEvent = new TestEvent();
        testEvent.value = 5;

        for (int i = 0; i < 100; i++) {
            manager.callEvent(testEvent);
        }

        System.out.println("Time: " + (System.currentTimeMillis() - time));

    }

    private class TestListener implements EventListener {

        @Override
        public void register(ListenerHandler handler) {
            ListenerData data = ListenerData.create(this)
                    .withPriority(50);

            handler.addEventListener(data, TestEvent.class, (event -> {
                System.out.println("Event called with value: " + event.value++);
            }));
        }

    }

}
