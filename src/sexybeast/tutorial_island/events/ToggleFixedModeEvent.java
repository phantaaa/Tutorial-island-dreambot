package sexybeast.tutorial_island.events;

import org.dreambot.api.ClientSettings;
import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.widget.Widgets;
import sexybeast.tutorial_island.utils.ActionWidget;
import sexybeast.tutorial_island.utils.WidgetFilter;

import java.awt.*;

public class ToggleFixedModeEvent implements Event {
    private final WidgetFilter FIXED_MODE_WIDGET = new ActionWidget("Fixed mode");

    @Override
    public boolean perform() {
        if (!ClientSettings.isResizableActive()) {
            return true;
        } else if (FIXED_MODE_WIDGET.get(Widgets.getWidgetsInstance()).isPresent() && FIXED_MODE_WIDGET.get(Widgets.getWidgetsInstance()).get().interact()) {
            MethodContext.sleep(4000);
        } else if (Tabs.openWithMouse(Tab.OPTIONS)) {
            MethodContext.sleep(1000);
        }
        return false;
    }
}
