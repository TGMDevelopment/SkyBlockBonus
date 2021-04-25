package ga.matthewtgm.skyblockmod.features.impl.trackers;

public class TrackerItem<T> {

    private T value = (T) new Object();
    private String name;

    public TrackerItem(String name) {
        this.name = name;
    }

    public static boolean compare(TrackerItem<?> item, TrackerItem<?> secondItem) {
        return item.getName().equals(secondItem.getName()) && item.getValue().getClass().isAssignableFrom(secondItem.getValue().getClass());
    }

    public T getValue() {
        return value;
    }

    public TrackerItem<T> setValue(T value) {
        this.value = value;
        return this;
    }

    public String getName() {
        return name;
    }

    public TrackerItem<T> setName(String name) {
        this.name = name;
        return this;
    }

}