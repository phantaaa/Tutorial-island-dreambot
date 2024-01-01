package sexybeast.tutorial_island.sections;

import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.magic.Magic;
import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.wrappers.interactive.NPC;

import static sexybeast.tutorial_island.utils.Locations.Areas.CHICKEN_AREA;
import static sexybeast.tutorial_island.utils.Locations.Areas.CHICKEN_HOUSE;
import static sexybeast.tutorial_island.utils.Locations.Tiles.CHICKENS;

public class WizardSection extends AbstractSection {
    private static final String[] DIALOGUE_OPTIONS = {"Yes.", "No, I'm not planning to do that."};

    public WizardSection() {
        super("Magic Instructor");
    }

    @Override
    public void execute() {
        if (handleContinue()) {
            return;
        }
        switch (getSectionProgress()) {
            case 620:
                if (!CHICKEN_HOUSE.contains(getLocalPlayer()) && Walking.shouldWalk()) {
                    Walking.walk(CHICKEN_HOUSE.getCenter());
                } else {
                    talkToInstructor();
                }
                break;
            case 630:
                Tabs.openWithMouse(Tab.MAGIC);
                break;
            case 640:
                talkToInstructor();
                break;
            case 650:
                if (!CHICKEN_AREA.contains(getLocalPlayer()) && Walking.shouldWalk()) { // dont walk??
                    Walking.clickTileOnMinimap(CHICKENS);
                } else {
                    attackChicken();
                }
                break;
            case 670:
                if (Dialogues.getOptions() != null) {
                    handleDialogues();
                } else {
                    talkToInstructor();
                }
                break;
        }
    }

    private void handleDialogues() {
        for (String s : Dialogues.getOptions()) {
            for (String s2 : DIALOGUE_OPTIONS) {
                if (s2.equals(s) && Dialogues.chooseOption(s2))
                    sleep(1200);
            }
        }
    }

    private void attackChicken() {
        NPC chicken = NPCs.closest(npc -> npc.getName().equals("Chicken") && npc.getInteractingCharacter() == null);
        if (chicken != null && Magic.castSpellOn(Normal.WIND_STRIKE, chicken)) {
            sleepUntil(() -> getSectionProgress() != 650, 4000);
        }
    }
}
