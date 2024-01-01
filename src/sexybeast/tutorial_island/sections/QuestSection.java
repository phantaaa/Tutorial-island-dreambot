package sexybeast.tutorial_island.sections;

import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.walking.impl.Walking;

import static sexybeast.tutorial_island.utils.Locations.Areas.QUEST_BUILDING;
import static sexybeast.tutorial_island.utils.Locations.Tiles.QUEST_BUILDING_INSIDE;

public class QuestSection extends AbstractSection {

    public QuestSection() {
        super("Quest Guide");
    }

    @Override
    public void execute() {
        if (handleContinue()) {
            return;
        }
        switch (getSectionProgress()) {
            case 200:
            case 210:
                if (getLocalPlayer().getTile().distance(QUEST_BUILDING_INSIDE) > 4) {
                    if (Walking.shouldWalk()) {
                        Walking.walk(QUEST_BUILDING_INSIDE);
                    }
                } else if (handleNextObstacle(QUEST_BUILDING.getRandomTile())) {
                    sleepUntil(() -> getSectionProgress() != 210, 4000);
                }
                break;
            case 220:
                talkToInstructor();
                break;
            case 230:
                Tabs.openWithMouse(Tab.QUEST);
                break;
            case 240:
                talkToInstructor();
                break;
            case 250:
                if (GameObjects.closest("Ladder").interact("Climb-down")) {
                    sleepUntil(() -> getSectionProgress() != 250, 4000);
                }
                break;
        }
    }
}
