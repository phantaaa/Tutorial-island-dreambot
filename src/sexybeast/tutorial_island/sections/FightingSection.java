package sexybeast.tutorial_island.sections;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.wrappers.interactive.NPC;
import sexybeast.tutorial_island.utils.ActionWidget;
import sexybeast.tutorial_island.utils.WidgetFilter;

import static sexybeast.tutorial_island.utils.Locations.Areas.RANGING_AREA;
import static sexybeast.tutorial_island.utils.Locations.Areas.RAT_CAGE;
import static sexybeast.tutorial_island.utils.Locations.Tiles.RAT_CAGE_INSIDE;
import static sexybeast.tutorial_island.utils.Locations.Tiles.RAT_CAGE_OUTSIDE;

public class FightingSection extends AbstractSection {
    private final WidgetFilter VIEW_EQUIPMENT_WIDGET = new ActionWidget("View equipment stats");
    private final WidgetFilter CLOSE = new ActionWidget("Close");


    public FightingSection() {
        super("Combat Instructor");
    }

    @Override
    public void execute() {
        if (handleContinue()) {
            return;
        }
        switch (getSectionProgress()) {
            case 370:
                talkToInstructor();
                break;
            case 390:
                Tabs.openWithMouse(Tab.EQUIPMENT);
                break;
            case 400:
                VIEW_EQUIPMENT_WIDGET.get(Widgets.getWidgetsInstance()).get().interact();
                break;
            case 405:
                equip("Bronze dagger");
                break;
            case 410:
                if (CLOSE.get(Widgets.getWidgetsInstance()).isPresent()) {
                    CLOSE.get(Widgets.getWidgetsInstance()).get().interact();
                } else {
                    talkToInstructor();
                }
                break;
            case 420:
                equip("Bronze sword", "Wooden shield");
                break;
            case 430:
                Tabs.openWithMouse(Tab.COMBAT);
                break;
            case 440:
                handleNextObstacle(RAT_CAGE_INSIDE);
                break;
            case 450:
            case 460:
                attackRat();
                break;
            case 470:
                if (RAT_CAGE.contains(getLocalPlayer())) {
                    handleNextObstacle(RAT_CAGE_OUTSIDE);
                } else {
                    talkToInstructor();
                }
                break;
            case 480:
            case 490:
                if (!RANGING_AREA.contains(getLocalPlayer()) && Walking.walk(RANGING_AREA.getCenter())) {
                    sleepUntil(() -> RANGING_AREA.contains(getLocalPlayer()), 8000);
                } else if (equip("Shortbow", "Bronze arrow") && attackRat()) {
                    sleepUntil(() -> getSectionProgress() == 500, 12000);
                }
                break;
            case 500:
                if (GameObjects.closest("Ladder").interact("Climb-up")) {
                    sleepUntil(() -> NPCs.closest("Giant rat") == null, 8000);
                }
                break;
        }
    }

    private boolean equip(String... weaponNames) {

        if (!Tabs.isOpen(Tab.INVENTORY)) {
            Tabs.openWithMouse(Tab.INVENTORY);
        }

        for (String item : weaponNames) {
            if (Inventory.contains(item) && Inventory.get(item).interact()) {
                sleep(Calculations.random(150, 400));
            }
        }
        return true;
    }

    private boolean attackRat() {
        NPC rat = NPCs.closest(npc -> npc != null && npc.getName().equals("Giant rat") && npc.getInteractingCharacter() == null);
        if (rat != null && rat.interact("Attack")) {
            return sleepUntil(() -> rat.getHealthPercent() <= 0 && !getLocalPlayer().isInteracting(rat), 50_000, 600);
        }
        return false;
    }


}
