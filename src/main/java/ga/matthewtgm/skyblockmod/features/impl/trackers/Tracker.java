package ga.matthewtgm.skyblockmod.features.impl.trackers;

import ga.matthewtgm.json.objects.JsonArray;
import ga.matthewtgm.json.objects.JsonObject;
import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeatureCategory;
import ga.matthewtgm.skyblockmod.features.FeaturePosition;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

public abstract class Tracker extends Feature {

    public Tracker(String name) {
        super(name, FeatureCategory.TRACKING, true);
    }

    public Tracker(String name, FeatureCategory category) {
        super(name, category, true);
    }

    public abstract List<TrackerItem<?>> trackerItems();

    @Override
    public void onSave() {
        super.onSave();
        if (trackerItems() != null && !trackerItems().isEmpty()) {
            JsonArray itemArray = new JsonArray();
            for (TrackerItem<?> item : trackerItems()) {
                itemArray.plus(new JsonObject().add(item.getName(), item.getValue()));
            }
            getConfig().put("items", itemArray);
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        try {
            if (getConfig().getConfigObject().get("items") != null && !getConfig().getConfigObject().getValueAsJsonArray("items").isEmpty()) {
                for (TrackerItem<?> item : trackerItems()) {
                    for (Object object : getConfig().getConfigObject().getValueAsJsonArray("items")) {
                        JsonObject theObject = (JsonObject) object;
                        if (TrackerItem.compare(item, new TrackerItem<>(theObject.getValueAsString(item.getName())).setValue(theObject.get(item.getName())))) {
                            trackerItems().remove(item);
                            trackerItems().add(new TrackerItem<>(theObject.getValueAsString(item.getName())).setValue(theObject.get(item.getName())));
                        }
                    }
                }
            }
        } catch (ConcurrentModificationException ignored) {}
    }

    @Override
    public void onRendered(FeaturePosition position) {
        if (this.width <= 0)
            this.width = 100;
        this.fontRenderer.drawString(this.getName(), position.getX(), position.getY(), -1);
        Iterator<TrackerItem<?>> itemIterator = this.trackerItems().iterator();
        int offset = 0;
        while (itemIterator.hasNext()) {
            TrackerItem<?> item = itemIterator.next();
            String itemName = item.getName();
            String itemVal = item.getValue().toString();
            this.height = Math.round(this.trackerItems().size() * this.fontRenderer.FONT_HEIGHT) + this.fontRenderer.FONT_HEIGHT;
            if (itemName != null && itemVal != null) {
                ++offset;
                int valX = position.getX() + this.width + 1;
                int valY = position.getY() + offset * this.fontRenderer.FONT_HEIGHT;
                this.fontRenderer.drawString(itemName, position.getX(), valY, this.colour.getRGBA());
                this.fontRenderer.drawString(itemVal, valX - this.fontRenderer.getStringWidth(itemVal), valY, this.colour.getRGBA());
            }
        }
    }

}
