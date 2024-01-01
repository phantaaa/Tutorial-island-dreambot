package sexybeast.tutorial_island.utils;

import org.dreambot.api.methods.widget.Widget;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TextWidget implements WidgetFilter {
    private final String text;

    public TextWidget(String text) {
        this.text = text;
    }


    @Override
    public Optional<WidgetChild> get(Widgets widgets) {
        if (text != null) {
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

                if (widgetChild.getText() != null) {
                    if (widgetChild.getText().contains(text)) {
                        return Optional.ofNullable(widgetChild);
                    }

                }

                if (widgetChild.getChildren() != null) {

                    for (WidgetChild widgetChildChild : widgetChild.getChildren()) {

                        if (widgetChildChild == null || widgetChildChild.getText() == null) {
                            continue;
                        }

                        if (widgetChildChild.getText().contains(text)) {
                            return Optional.ofNullable(widgetChildChild);
                        }

                    }
                }
            }

        }
        return Optional.empty();
    }
}
