package sexybeast.tutorial_island.utils;

import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import java.util.Optional;

public interface WidgetFilter {

    Optional<WidgetChild> get(Widgets widgets);

    Optional<WidgetChild> findWidget(Widgets widgets);

}
