package sexybeast.tutorial_island.sections;

import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.map.Map;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.widget.Widget;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.wrappers.interactive.GameObject;
import sexybeast.tutorial_island.mappings.Configs;
import sexybeast.tutorial_island.utils.TextWidget;
import sexybeast.tutorial_island.utils.WidgetFilter;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractSection extends MethodContext {
    private final WidgetFilter CLICK_TO_CONTINUE = new TextWidget("Click to continue");
    private final String INSTRUCTOR_NAME;

    public AbstractSection(String INSTRUCTOR_NAME) {
        this.INSTRUCTOR_NAME = INSTRUCTOR_NAME;
    }

    protected boolean talkToInstructor() {
        if (NPCs.closest(INSTRUCTOR_NAME) != null && NPCs.closest(INSTRUCTOR_NAME).interact("Talk-to")) {
            sleepUntil(() -> Dialogues.canContinue() || inDialogue(), 8000);
            return true;
        }
        return false;
    }

    public abstract void execute();

    protected int getSectionProgress() {
        return PlayerSettings.getConfig(Configs.OLD_TUTORIAL_ISLAND_PROGRESS_CONFIG);
    }

    protected String getInstructorName() {
        return INSTRUCTOR_NAME;
    }

    protected boolean handleNextObstacle(Tile tile) {
        final GameObject obstacle = GameObjects.all().stream()
                .filter(gameObject -> gameObject != null && gameObject.getTile().distance(tile) < 5 && (gameObject.getName().equals("Door") || gameObject.getName().equals("Gate") || gameObject.getName().equals("Large door")))
                .min(Comparator.<GameObject>comparingDouble(a -> a.distance(tile)).thenComparingDouble(b -> b.distance(tile)))
                .orElse(null);

        log("[Handling obstacle] - " + obstacle);

        if (obstacle != null) {
            if (!Map.isTileOnScreen(tile)) {
                List<Tile> path = new LinkedList<>(Walking.getAStarPathFinder().calculate(getLocalPlayer().getTile(), obstacle.getTile()));
                for (Tile walkable : path) {

                    if (walkable.distance(getLocalPlayer().getTile()) < 10) {
                        continue;
                    }

                    if (Walking.shouldWalk() && Walking.walk(walkable)) {
                        sleepUntil(() -> getLocalPlayer().distance(walkable) < 2, 8000);
                    }

                }
            } else if (!obstacle.isOnScreen()) {
                Camera.rotateToEntity(obstacle);
            }

            if (obstacle.interact("Open")) {
                sleep(1000); // safe sleep for high latency
                sleepUntil(() -> Map.canReach(tile) && !getLocalPlayer().isMoving() && !getLocalPlayer().isAnimating() && Walking.shouldWalk(), 4000, 500);
                return true;
            }

        }

        return false;
    }

    protected WidgetFilter getClickToContinueWidget() {
        return CLICK_TO_CONTINUE;
    }

    private boolean inDialogue() {

        if (Dialogues.canContinue()) {
            return true;
        }

        for (Widget widget : Widgets.getAllWidgets()) {
            if (widget.getID() == 231 || widget.getID() == 217 || widget.getID() == 192 || widget.getID() == 193) {
                return true;
            }
        }

        return false;
    }

    protected boolean handleContinue() {
        if (CLICK_TO_CONTINUE.get(Widgets.getWidgetsInstance()).isPresent()) {
            return CLICK_TO_CONTINUE.get(Widgets.getWidgetsInstance()).get().interact();
        } else if (inDialogue()) {
            final String lastText = Dialogues.getNPCDialogue();
            Dialogues.spaceToContinue();
            sleepUntil(() -> !Dialogues.getNPCDialogue().equals(lastText) || !inDialogue(), 3000);
            return true;
        }

        return false;

    }

}
