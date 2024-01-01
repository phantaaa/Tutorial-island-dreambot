package sexybeast.tutorial_island.sections;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.map.Map;

import static sexybeast.tutorial_island.utils.Locations.Areas.COOK_BUILDING;
import static sexybeast.tutorial_island.utils.Locations.Tiles.PAST_COOK_DOOR;
import static sexybeast.tutorial_island.utils.Locations.Tiles.PAST_SURVIVAL_FENCE;

public class CookingSection extends AbstractSection {

    public CookingSection() {
        super("Master Chef");
    }

    @Override
    public void execute() {
        if (handleContinue()) {
            return;
        }
        switch (getSectionProgress()) {
            case 130:
                if (!Map.canReach(PAST_SURVIVAL_FENCE)) {
                    handleNextObstacle(PAST_SURVIVAL_FENCE);
                } else if (!COOK_BUILDING.contains(getLocalPlayer())) {
                    handleNextObstacle(COOK_BUILDING.getCenter());
                }
                break;
            case 140:
                talkToInstructor();
                break;
            case 150:
                makeDough();
                break;
            case 160:
                bakeDough();
                break;
            case 170:
                if (handleNextObstacle(PAST_COOK_DOOR)) {
                    sleepUntil(() -> getSectionProgress() != 180, 8000);
                }
                break;
        }
    }

    private void makeDough() {
        if (Inventory.get("Bucket of water").interact() && Inventory.get("Pot of flour").interact()) {
            sleepUntil(() -> Inventory.contains("Bread dough"), 4000);
        }
    }

    private void bakeDough() {
        if (Inventory.get("Bread dough").useOn(GameObjects.closest("Range"))) {
            sleepUntil(() -> Inventory.contains("Bread"), 8000);
        }
    }
}
