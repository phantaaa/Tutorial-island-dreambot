package sexybeast.tutorial_island.events;


import org.dreambot.api.ClientSettings;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.widget.Widgets;
import sexybeast.tutorial_island.utils.ActionWidget;
import sexybeast.tutorial_island.utils.WidgetFilter;

public class ToggleShiftDropEvent implements Event {
    private final WidgetFilter TOGGLE_SHIFT_CLICK_TO_DROP_WIDGET = new ActionWidget("Toggle shift click to drop");
    private final WidgetFilter CONTROLS_WIDGET = new ActionWidget("Controls");

    @Override
    public boolean perform() {
        if (ClientSettings.isShiftInteractionEnabled()) {
            return true;
        } else if (!Tabs.isOpen(Tab.OPTIONS)) {
            Tabs.openWithMouse(Tab.OPTIONS);
        } else if (!TOGGLE_SHIFT_CLICK_TO_DROP_WIDGET.get(Widgets.getWidgetsInstance()).isPresent()) {
            CONTROLS_WIDGET.get(Widgets.getWidgetsInstance()).get().interact();
        } else {
            TOGGLE_SHIFT_CLICK_TO_DROP_WIDGET.get(Widgets.getWidgetsInstance()).get().interact();
        }
        return false;
    }
}
