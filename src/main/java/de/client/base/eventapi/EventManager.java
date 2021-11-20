package de.client.base.eventapi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The event manager
 */
public class EventManager {

    /**
     * A list of all event handlers
     */
    private static final Map<Class<? extends Event>, List<MethodData>> REGISTRY_MAP = new HashMap<>();

    /**
     * Registers a new event handler
     *
     * @param object The event handler class
     */
    public static void register(Object object) {
        for (final Method method : object.getClass().getDeclaredMethods()) {
            if (isMethodBad(method)) {
                continue;
            }
            register(method, object);
        }
    }

    /**
     * Registers a new event handler
     *
     * @param object     The event handler class
     * @param eventClass The event to subscribe to
     */
    public static void register(Object object, Class<? extends Event> eventClass) {
        for (final Method method : object.getClass().getDeclaredMethods()) {
            if (isMethodBad(method, eventClass)) {
                continue;
            }

            register(method, object);
        }
    }

    /**
     * Inner register, parses info about the event handler in question and registers it
     *
     * @param method The event handler
     * @param object The instance of the class containing the event handler
     */
    @SuppressWarnings("unchecked") private static void register(Method method, Object object) {
        Class<? extends Event> indexClass = (Class<? extends Event>) method.getParameterTypes()[0];
        //New MethodData from the Method we are registering.
        final MethodData data = new MethodData(object, method, Priority.HIGHEST);

        //Set's the method to accessible so that we can also invoke it if it's protected or private.
        data.getTarget().setAccessible(true);

        if (REGISTRY_MAP.containsKey(indexClass)) {
            if (!REGISTRY_MAP.get(indexClass).contains(data)) {
                REGISTRY_MAP.get(indexClass).add(data);
                sortListValue(indexClass);
            }
        } else {
            REGISTRY_MAP.put(indexClass, new CopyOnWriteArrayList<MethodData>() {
                //Eclipse was bitching about a serialVersionUID.
                //                private static final long serialVersionUID = 666L;

                {
                    add(data);
                }
            });
        }
    }

    /**
     * Removes a registered class
     *
     * @param object The instance of the class to unsubscribe
     */
    public static void unregister(Object object) {
        for (final List<MethodData> dataList : REGISTRY_MAP.values()) {
            dataList.removeIf(data -> data.getSource().equals(object));
        }

        cleanMap(true);
    }

    /**
     * Removes a registered class
     *
     * @param object     The instance of the class to unsubscribe
     * @param eventClass The event to unregister
     */
    public static void unregister(Object object, Class<? extends Event> eventClass) {
        if (REGISTRY_MAP.containsKey(eventClass)) {
            REGISTRY_MAP.get(eventClass).removeIf(data -> data.getSource().equals(object));

            cleanMap(true);
        }
    }

    /**
     * Cleans the registry map
     *
     * @param onlyEmptyEntries Clear only empty entries
     */
    public static void cleanMap(boolean onlyEmptyEntries) {
        Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> mapIterator = REGISTRY_MAP.entrySet().iterator();

        while (mapIterator.hasNext()) {
            if (!onlyEmptyEntries || mapIterator.next().getValue().isEmpty()) {
                mapIterator.remove();
            }
        }
    }

    private static void sortListValue(Class<? extends Event> indexClass) {
        List<MethodData> sortedList = new CopyOnWriteArrayList<>();

        for (final byte priority : Priority.VALUE_ARRAY) {
            for (final MethodData data : REGISTRY_MAP.get(indexClass)) {
                if (data.getPriority() == priority) {
                    sortedList.add(data);
                }
            }
        }

        //Overwriting the existing entry.
        REGISTRY_MAP.put(indexClass, sortedList);
    }

    /**
     * Checks if a method isn't supposed to be treated as an event handler
     *
     * @param method The method to check
     * @return true if it is an event handler, false if otherwise
     */
    private static boolean isMethodBad(Method method) {
        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(EventTarget.class);
    }

    /**
     * Checks if a method isn't supposed to be treated as an event handler for a specific event
     *
     * @param method     The method to check
     * @param eventClass The event class that is supposed to be listened for
     * @return true if it is an event handler, false if otherwise
     */
    private static boolean isMethodBad(Method method, Class<? extends Event> eventClass) {
        return isMethodBad(method) || !method.getParameterTypes()[0].equals(eventClass);
    }

    /**
     * Fires an event chain
     *
     * @param event The event to send
     * @return The modified event post call chain
     */
    public static Event call(final Event event) {
        List<MethodData> dataList = REGISTRY_MAP.get(event.getClass());

        try {
            for (final MethodData data : dataList) {
                invoke(data, event);
            }
        } catch (NullPointerException ignored) {
        }

        return event;
    }

    /**
     * Invokes a method with the given event as argument
     *
     * @param data     The method to invoke
     * @param argument The event to send
     */
    private static void invoke(MethodData data, Event argument) {
        try {
            data.getTarget().invoke(data.getSource(), argument);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ignored) {
        }
    }


    private record MethodData(Object source, Method target, byte priority) {

        /**
         * Sets the values of the data.
         *
         * @param source   The source Object of the data. Used by the VM to determine to which object it should send the call to.
         * @param target   The targeted Method to which the Event should be send to.
         * @param priority The priority of this Method. Used by the registry to sort the data on.
         */
        private MethodData {
        }

        /**
         * Gets the source Object of the data.
         *
         * @return Source Object of the targeted Method.
         */
        public Object getSource() {
            return source;
        }

        /**
         * Gets the targeted Method.
         *
         * @return The Method that is listening to certain Event calls.
         */
        public Method getTarget() {
            return target;
        }

        /**
         * Gets the priority value of the targeted Method.
         *
         * @return The priority value of the targeted Method.
         */
        public byte getPriority() {
            return priority;
        }

    }
}
