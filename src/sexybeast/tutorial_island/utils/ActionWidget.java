
package sexybeast.tutorial_island.utils;

import org.dreambot.api.methods.widget.Widget;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ActionWidget implements WidgetFilter {
    private final String widgetAction;

    public ActionWidget(String widgetAction) {
        this.widgetAction = widgetAction;
    }

    @Override
    public Optional<WidgetChild> get(Widgets widgets) {
        if (widgetAction != null) {
            return findWidget(widgets);
        }
        return null;
    }

    @Override
    public Optional<WidgetChild> findWidget(Widgets widgets) {
        List<Widget> widgetsList = Widgets.getAllWidgets().stream().filter(Widget::isVisible).collect(Collectors.toList());
        WidgetChild match = null;

        for (Widget widget : widgetsList) {

            if (widget.getChildren() == null) {
                continue;
            }

            for (WidgetChild widgetChild : widget.getChildren().stream().filter(WidgetChild::isVisible).collect(Collectors.toList())) {

                if (isNotEmpty(widgetChild.getActions())) {
                    for (String action : Arrays.stream(widgetChild.getActions()).filter(s -> s != null && !s.isEmpty()).collect(Collectors.toList())) {

                        if (action.contains(widgetAction)) {
                            return Optional.ofNullable(widgetChild);
                        }

                    }
                }

                if (isNotEmpty(widgetChild.getChildren())) {

                    for (WidgetChild widgetChildChild : widgetChild.getChildren()) {

                        if (!isNotEmpty(widgetChildChild.getActions())) {
                            continue;
                        }

                        for (String action : Arrays.stream(widgetChildChild.getActions()).filter(s -> s != null && !s.isEmpty()).collect(Collectors.toList())) {

                            if (action.contains(widgetAction)) {
                                return Optional.ofNullable(widgetChildChild);
                            }

                        }
                    }
                }


            }
        }
        return Optional.empty();
    }


    public static boolean isNotEmpty(Object[] array) {
        return (array != null && array.length != 0);
    }

}
