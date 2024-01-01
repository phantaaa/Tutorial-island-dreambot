package sexybeast.tutorial_island.sections;

import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.walking.impl.Walking;

import static sexybeast.tutorial_island.utils.Locations.Areas.CHURCH_AREA;
import static sexybeast.tutorial_island.utils.Locations.Tiles.PAST_PRIEST_DOOR;

public class PriestSection extends AbstractSection {

    public PriestSection() {
        super("Brother Brace");
    }

    @Override
    public void execute() {
        if (handleContinue()) {
            return;
        }
        switch (getSectionProgress()) {
            case 550:
                if (!CHURCH_AREA.contains(getLocalPlayer()) && Walking.shouldWalk()) {
                    Walking.walk(CHURCH_AREA.getCenter());
                } else {
                    talkToInstructor();
                }
                break;
            case 560:
                Tabs.openWithMouse(Tab.PRAYER);
                break;
            case 570:
                talkToInstructor();
                break;
            case 580:
                Tabs.openWithMouse(Tab.FRIENDS);
                break;
            case 600:
                talkToInstructor();
                break;
            case 610:
                handleNextObstacle(PAST_PRIEST_DOOR);
                break;
        }
    }
}
