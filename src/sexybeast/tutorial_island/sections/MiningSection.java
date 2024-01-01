package sexybeast.tutorial_island.sections;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.WidgetChild;
import sexybeast.tutorial_island.mappings.WidgetId;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static sexybeast.tutorial_island.utils.Locations.Areas.MINING_AREA;
import static sexybeast.tutorial_island.utils.Locations.Tiles.PAST_MINING_GATE;

public class MiningSection extends AbstractSection {
    private static final short[] TIN = {53};
    private static final short[] COPPER = {4510};

    public MiningSection() {
        super("Mining Instructor");
    }

    @Override
    public void execute() {
        if (handleContinue()) {
            return;
        }
        switch (getSectionProgress()) {
            case 260:
                if (!MINING_AREA.contains(getLocalPlayer()) && Walking.shouldWalk()) {
                    Walking.walk(MINING_AREA.getCenter());
                } else if (talkToInstructor()) {
                    sleepUntil(Dialogues::canContinue, 8000);
                }
                break;
            case 270:
                talkToInstructor();
                break;
            case 300:
                interactWithRock(TIN);
                break;
            case 310:
                interactWithRock(COPPER);
                break;
            case 320:
                smelt();
                break;
            case 330:
                talkToInstructor();
                break;
            case 340:
                smith();
                break;
            case 350:
                if (bronzeDagger() != null && bronzeDagger().isVisible() && bronzeDagger().interact()) {
                    sleepUntil(() -> Inventory.contains("Bronze dagger"), 6000);
                } else {
                    smith();
                }
                break;
            case 360:
                handleNextObstacle(PAST_MINING_GATE);
                break;
        }
    }

    private void smelt() {
        if (Tabs.getOpen() != Tab.INVENTORY) {
            Tabs.open(Tab.INVENTORY);
        }

        Item randomOre = new Random().nextInt(2) == 1 ? Inventory.get("Tin ore") : Inventory.get("Copper ore");

        if (randomOre.interact() && GameObjects.closest("Furnace").interact("Use")) {
            sleepUntil(() -> Inventory.contains("Bronze bar"), 8000);
        }
    }

    private void smith() {
        if (GameObjects.closest("Anvil").interact("Smith")) {
            sleepUntil(() -> getSectionProgress() == 350, 8000);
        }
    }

    private WidgetChild bronzeDagger() {
        return Widgets.getWidget(WidgetId.BRONZE_DAGGER.getRoot())
                .getChild(WidgetId.BRONZE_DAGGER.getChild())
                .getChild(WidgetId.BRONZE_DAGGER.getGrandChild());
    }

    private void interactWithRock(short[] modelColours) {
        List<GameObject> gameObjects = GameObjects.all().stream()
                .filter(gameObject -> gameObject != null && gameObject.getName().equalsIgnoreCase("Rocks"))
                .sorted(Comparator.<GameObject>comparingDouble(a -> a.distance(getLocalPlayer())).thenComparingDouble(b -> b.distance(getLocalPlayer())))
                .collect(Collectors.toList());


        for (GameObject gameObject : gameObjects) {

            if (gameObject.getModelColors() == null) {
                continue;
            }

            for (short s : gameObject.getModelColors()) {
                for (short s2 : modelColours) {
                    if (s == s2 && gameObject.interact("Mine")) {
                        int emptySlots = Inventory.getEmptySlots();
                        sleepUntil(() -> Inventory.getEmptySlots() != emptySlots, 8000);
                        return;
                    }
                }
            }
        }

    }
}
