package sexybeast.tutorial_island.events;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.input.Keyboard;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.methods.widget.Widgets;
import sexybeast.tutorial_island.utils.ActionWidget;
import sexybeast.tutorial_island.utils.TextWidget;
import sexybeast.tutorial_island.utils.WidgetFilter;

import java.awt.event.KeyEvent;
import java.util.Random;


public class InsertNameEvent implements Event {
    private final WidgetFilter nameAcceptedWidget = new TextWidget("Great!");

    private final WidgetFilter nameLookupWidget = new ActionWidget("Look up name");
    private final WidgetFilter nameInputWidget = new TextWidget("Please pick a unique display name");
    private final WidgetFilter nameSetWidget = new TextWidget("Set name");
    private final WidgetFilter nameScreenDetectionWidget = new TextWidget("Choose display name");

    @Override
    public boolean perform() {
        if (nameAcceptedWidget.get(Widgets.getWidgetsInstance()).isPresent()) {
            nameSetWidget.get(Widgets.getWidgetsInstance()).ifPresent(rs2Widget -> {
                if (rs2Widget.interact()) {
                    MethodContext.sleepUntil(() -> !nameScreenDetectionWidget.get(Widgets.getWidgetsInstance()).isPresent(), 8000);
                }
            });
            return true;
        } else if (nameInputWidget.get(Widgets.getWidgetsInstance()).isPresent()
                && nameInputWidget.get(Widgets.getWidgetsInstance()).get().isVisible()) {

            Keyboard.type(generateRandomChars() + KeyEvent.VK_ENTER);

            final int configValue = PlayerSettings.getConfig(1042);

            // sending request sleep
            MethodContext.sleepUntil(() -> PlayerSettings.getConfig(1042) != configValue, 8000);

            //getting request result sleep
            MethodContext.sleepUntil(() -> PlayerSettings.getConfig(1042) == configValue || nameAcceptedWidget.get(Widgets.getWidgetsInstance()).isPresent(), 8000);
        } else if (nameLookupWidget.get(Widgets.getWidgetsInstance()).isPresent()
                && nameLookupWidget.get(Widgets.getWidgetsInstance()).get().interact()) {
            MethodContext.sleepUntil(() -> nameInputWidget.get(Widgets.getWidgetsInstance()).isPresent() && nameInputWidget.get(Widgets.getWidgetsInstance()).get().isVisible(), 8000);
        }

        return false;
    }

    private String generateRandomChars() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < Calculations.random(6, 12); i++) {
            sb.append(random.nextInt(2) == 1 ? alphabet.toLowerCase().charAt(random.nextInt(alphabet.length())) : alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return sb.toString();
    }

}
