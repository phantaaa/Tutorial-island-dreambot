package sexybeast.tutorial_island.sections;

import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.widget.Widgets;
import sexybeast.tutorial_island.utils.ActionWidget;
import sexybeast.tutorial_island.utils.TextWidget;
import sexybeast.tutorial_island.utils.WidgetFilter;

import static sexybeast.tutorial_island.utils.Locations.Areas.BANK_AREA;
import static sexybeast.tutorial_island.utils.Locations.Tiles.INSIDE_BANK;
import static sexybeast.tutorial_island.utils.Locations.Tiles.OUTSIDE_ACCOUNT_MANAGEMENT;

public class BankingSection extends AbstractSection {
    private final WidgetFilter CLICK_HERE_TO_CONTINUE_WIDGET = new TextWidget("Click here to continue");
    private final WidgetFilter PLEASE_WAIT_WIDGET = new TextWidget("Please wait...");
    private final WidgetFilter CLOSE_WIDGET = new ActionWidget("Close");

    public BankingSection() {
        super("Account Guide");
    }

    @Override
    public void execute() {

        if (handleContinue()) {
            return;
        }

        switch (getSectionProgress()) {
            case 510:
                if (!BANK_AREA.contains(getLocalPlayer())) {
                    if (isBankClosed()) {
                        handleNextObstacle(INSIDE_BANK);
                    } else if (Walking.shouldWalk()) {
                        Walking.clickTileOnMinimap(INSIDE_BANK);
                    }
                } else if (Dialogues.canContinue()) {
                    Dialogues.chooseOption("Yes.");
                } else if (GameObjects.closest("Bank booth").interact("Use")) {
                    sleepUntil(Bank::isOpen, 8000);
                }
                break;
            case 520:
                if (Bank.isOpen()) {
                    Bank.close();
                } else if (PLEASE_WAIT_WIDGET.get(Widgets.getWidgetsInstance()).isPresent()) {
                    sleep(1000);
                } else if (CLICK_HERE_TO_CONTINUE_WIDGET.get(Widgets.getWidgetsInstance()).isPresent() || Dialogues.canContinue()) {
                    Dialogues.spaceToContinue();
                } else if (GameObjects.closest("Poll booth").interact("Use")) {
                    sleepUntil(Dialogues::canContinue, 8000);
                }
                break;
            case 525:
                if (CLOSE_WIDGET.get(Widgets.getWidgetsInstance()).isPresent()) {
                    CLOSE_WIDGET.get(Widgets.getWidgetsInstance()).get().interact();
                } else {
                    handleNextObstacle(new Tile(3125, 3124, 0));
                }
                break;
            case 530:
                talkToInstructor();
                break;
            case 531:
                Tabs.openWithMouse(Tab.ACCOUNT_MANAGEMENT);
                break;
            case 532:
                talkToInstructor();
                break;
            case 540:
                handleNextObstacle(OUTSIDE_ACCOUNT_MANAGEMENT);
                break;
        }
    }

    private boolean isBankClosed() {
        return GameObjects.closest(gameObject -> gameObject.getName().equals("Large door") && gameObject.hasAction("Open")) != null;
    }
}
