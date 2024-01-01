package sexybeast.tutorial_island.sections;

import org.dreambot.api.ClientSettings;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.wrappers.widgets.WidgetChild;
import sexybeast.tutorial_island.events.*;
import sexybeast.tutorial_island.utils.TextWidget;
import sexybeast.tutorial_island.utils.WidgetFilter;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GielinorSection extends AbstractSection {
    private final WidgetFilter PLEASE_WAIT_WIDGET = new TextWidget("Please wait...");
    private final WidgetFilter CLICK_HERE_TO_CONTINUE_WIDGET = new TextWidget("Click here to continue");
    private final WidgetFilter CHOOSE_DISPLAY_NAME_WIDGET = new TextWidget("Choose display name");
    private final WidgetFilter EXPERIENCE_WIDGET = new TextWidget("What's your experience with Old School Runescape?");
    private boolean isAudioDisabled;

    public GielinorSection() {
        super("Gielinor Guide");
    }

    @Override
    public void execute() {
        if (inDialogue()) {
            if (Dialogues.spaceToContinue())
                sleepUntil(() -> CLICK_HERE_TO_CONTINUE_WIDGET.get(Widgets.getWidgetsInstance()).isPresent(), 2000);

            return;
        }

        switch (getSectionProgress()) {
            case 1:
            case 2:
                if (nameScreenIsVisible()) {
                    insertName();
                } else if (customizationScreenIsVisible()) {
                    createCharacter();
                } else if (experienceScreenIsVisible() && Dialogues.chooseOption(Calculations.random(1, 3))) {
                    sleepUntil(() -> !experienceScreenIsVisible(), 3000);
                } else {
                    talkToInstructor();
                }
                break;
            case 3:
                Tabs.openWithMouse(Tab.OPTIONS);
                break;
            case 7:
                talkToInstructor();
                break;
            case 10:
                if (ClientSettings.isResizableActive()) {
                    toggleFixedMode();
                } else if (!ClientSettings.isShiftInteractionEnabled()) {
                    toggleShiftDrop();
                } else if (!isAudioDisabled) {
                    isAudioDisabled = disableAudio();
                } else if (ClientSettings.roofsEnabled()) {
                    toggleRoofsHidden();
                } else if (!Walking.isRunEnabled()) {
                    Walking.toggleRun();
                } else if (GameObjects.closest("Door").interact("Open")) {
                    sleepUntil(() -> getSectionProgress() != 10, 8000);
                }
                break;
            default:
                talkToInstructor();
                break;
        }

    }


    private boolean inDialogue() {
        return CLICK_HERE_TO_CONTINUE_WIDGET.get(Widgets.getWidgetsInstance()).isPresent() || PLEASE_WAIT_WIDGET.get(Widgets.getWidgetsInstance()).isPresent();
    }

    private boolean experienceScreenIsVisible() {
        return EXPERIENCE_WIDGET.get(Widgets.getWidgetsInstance()).filter(WidgetChild::isVisible).isPresent();
    }

    private boolean nameScreenIsVisible() {
        return CHOOSE_DISPLAY_NAME_WIDGET.get(Widgets.getWidgetsInstance()).filter(WidgetChild::isVisible).isPresent();
    }

    private boolean customizationScreenIsVisible() {
        return !Widgets.getWidgetChildrenContainingText("Character Creator").isEmpty();
    }

    private int getCharacterCustomizationRootId() {
        List<WidgetChild> characterCreationWidgets = Widgets.getWidgetChildrenContainingText("Character Creator");

        if (characterCreationWidgets.isEmpty())
            return -1;

        return characterCreationWidgets.get(0).getParentID();
    }


    private void createCharacter() {
        List<WidgetChild> creationWidgets = Widgets.getWidget(getCharacterCustomizationRootId()).getChildren();
        Collections.shuffle(creationWidgets);

        if (new Random().nextInt(2) == 1) {
            Widgets.getWidgetChildrenContainingText("Female").stream().findFirst().ifPresent(WidgetChild::interact);
        }

        for (WidgetChild widgetChild : creationWidgets) {

            if (widgetChild.getActions() == null || widgetChild.getActions().length == 0) {
                continue;
            }

            if (widgetChild.getActions()[0].equals("Select")) {

                for (int i = 0; i < new Random().nextInt(5); i++) {
                    widgetChild.interact();
                    sleep(Calculations.random(100, 250));
                }

            }

        }

        if (Widgets.getWidgetChildrenContainingText("Confirm").get(0).interact()) {
            sleepUntil(() -> !customizationScreenIsVisible(), 4000);
        }

    }

    private boolean disableAudio() {
        Event disableAudio = new DisableAudioEvent();
        return disableAudio.perform();
    }

    private boolean toggleFixedMode() {
        Event toggleFixedMode = new ToggleFixedModeEvent();
        return toggleFixedMode.perform();
    }

    private boolean toggleRoofsHidden() {
        Event toggleRoofsHidden = new ToggleRoofsHiddenEvent();
        return toggleRoofsHidden.perform();
    }

    private boolean toggleShiftDrop() {
        Event toggleShiftDropEvent = new ToggleShiftDropEvent();
        return toggleShiftDropEvent.perform();
    }

    private boolean insertName() {
        Event insertNameEvent = new InsertNameEvent();
        return insertNameEvent.perform();
    }


}
