package sexybeast.tutorial_island.events;

import org.dreambot.api.ClientSettings;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.widget.Widgets;
import sexybeast.tutorial_island.utils.ActionWidget;
import sexybeast.tutorial_island.utils.TextWidget;
import sexybeast.tutorial_island.utils.WidgetFilter;

public class ToggleRoofsHiddenEvent implements Event {
    private final WidgetFilter DISPLAY_WIDGET = new ActionWidget("Display");
    private final WidgetFilter ADVANCED_OPTIONS_WIDGET = new TextWidget("Advanced options");
    private final WidgetFilter TOGGLE_ROOFS_HIDDEN_WIDGET = new ActionWidget("Roof-removal");
    private final WidgetFilter CLOSE_WIDGET = new ActionWidget("Close");

    @Override
    public boolean perform() {
        if (!ClientSettings.roofsEnabled()) {
            return true;
        } else if (Tabs.getOpen() != Tab.OPTIONS) {
            Tabs.openWithMouse(Tab.OPTIONS);

        } else if (TOGGLE_ROOFS_HIDDEN_WIDGET.get(Widgets.getWidgetsInstance()).isPresent() && TOGGLE_ROOFS_HIDDEN_WIDGET.get(Widgets.getWidgetsInstance()).get().interact() && MethodContext.sleepUntil(() -> !ClientSettings.roofsEnabled(), 2000)) {
            CLOSE_WIDGET.get(Widgets.getWidgetsInstance()).get().interact();
        } else if (ADVANCED_OPTIONS_WIDGET.get(Widgets.getWidgetsInstance()).isPresent()) {
            ADVANCED_OPTIONS_WIDGET.get(Widgets.getWidgetsInstance()).get().interact();
        } else if (DISPLAY_WIDGET.get(Widgets.getWidgetsInstance()).isPresent()) {
            DISPLAY_WIDGET.get(Widgets.getWidgetsInstance()).get().interact();
        }
        return false;
    }
}
