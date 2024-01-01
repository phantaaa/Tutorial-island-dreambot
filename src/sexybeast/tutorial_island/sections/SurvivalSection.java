package sexybeast.tutorial_island.sections;

import org.dreambot.api.input.Mouse;
import org.dreambot.api.input.mouse.destination.impl.TileDestination;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.map.Map;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import static sexybeast.tutorial_island.utils.Locations.Tiles.PAST_SURVIVAL_FENCE;

public class SurvivalSection extends AbstractSection {

    public SurvivalSection() {
        super("Survival Expert");
    }

    @Override
    public void execute() {
        if (handleContinue()) {
            return;
        }
        switch (getSectionProgress()) {
            case 20:
                talkToInstructor();
                break;
            case 30:
                Tabs.openWithMouse(Tab.INVENTORY);
                break;
            case 40:
                fish();
                break;
            case 50:
                Tabs.openWithMouse(Tab.SKILLS);
                break;
            case 60:
                talkToInstructor();
                break;
            case 70:
                chopTree();
                break;
            case 80:
                lightFire();
                break;
            case 90:
                if (GameObjects.closest("Fire") == null) {
                    if (!Inventory.contains("Logs")) {
                        chopTree();
                    } else {
                        lightFire();
                    }
                } else {
                    cook();
                }
                break;
            case 120:
                handleNextObstacle(PAST_SURVIVAL_FENCE);
                break;
        }
    }

    private void fish() {
        NPC fishingSpot = NPCs.closest("Fishing spot");
        if (fishingSpot != null && fishingSpot.interact("Net")) {
            int emptySlots = Inventory.getEmptySlots();
            sleepUntil(() -> Inventory.getEmptySlots() != emptySlots, 12000);
        }
    }

    private void chopTree() {
        GameObject tree = GameObjects.closest("Tree");
        if (tree != null && tree.interact("Chop down")) {
            int emptySlots = Inventory.getEmptySlots();
            sleepUntil(() -> Inventory.getEmptySlots() != emptySlots, 12000);
        }
    }

    private void lightFire() {
        if (standingOnFire() && Mouse.click(new TileDestination(freeTile()))) {
            sleepUntil(() -> !standingOnFire() && !getLocalPlayer().isMoving() && !getLocalPlayer().isAnimating(), 4000);
        } else if (Inventory.isItemSelected() && !Inventory.getSelectedItemName().equals("Tinderbox")) {
            Inventory.deselect();
        } else if (Inventory.get("Tinderbox").useOn("Logs")) {
            Tile myPosition = getLocalPlayer().getTile();
            sleepUntil(() -> !myPosition.equals(getLocalPlayer().getTile()), 15000);
        }
    }

    private boolean standingOnFire() {
        return GameObjects.all(gameObject -> gameObject != null && gameObject.getTile().equals(getLocalPlayer().getTile()) && gameObject.getName().equals("Fire")).stream().findAny().isPresent();
    }

    private void cook() {
        if (Inventory.get("Raw shrimps").interact("Use") && GameObjects.closest("Fire").interact("Use")) {
            sleepUntil(() -> Inventory.contains("Shrimps"), 8000);
        }
    }

    private Tile freeTile() {
        for (Tile tile : Arrays.stream(getLocalPlayer().getSurroundingArea(5).getTiles())
                .filter(tile -> !tile.equals(getLocalPlayer().getTile()) && Map.canReach(tile))
                .sorted(Comparator.<Tile>comparingDouble(a -> getLocalPlayer().distance(a)).thenComparingDouble(b -> getLocalPlayer().distance(b)))
                .collect(Collectors.toList())) {

            for (GameObject gameObject : GameObjects.all()) {

                if (!gameObject.getName().equals("Fire")) {
                    continue;
                }

                if (!gameObject.getTile().equals(tile)) {
                    return tile;
                }

            }
        }
        return null;
    }
}
