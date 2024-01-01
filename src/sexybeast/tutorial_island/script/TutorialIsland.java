package sexybeast.tutorial_island.script;

import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import sexybeast.tutorial_island.mappings.Configs;
import sexybeast.tutorial_island.sections.*;

import java.awt.*;


@ScriptManifest(category = Category.MISC, name = "NASA Tutorial Island", author = "SexyBeast", version = 1.3)
public class TutorialIsland extends AbstractScript {

    private static final Color color1 = new Color(0, 0, 0, 150);
    private static final Color color2 = new Color(0, 0, 0);
    private static final Color color3 = new Color(153, 0, 153, 207);
    private static final BasicStroke stroke1 = new BasicStroke(1);
    private static final Font font1 = new Font("Serif", Font.BOLD | Font.ITALIC, 14);
    private static final Font font2 = new Font("Serif", Font.ITALIC, 14);

    private final AbstractSection gielinorSection = new GielinorSection(),
            survivalSection = new SurvivalSection(),
            cookingSection = new CookingSection(),
            questSection = new QuestSection(),
            miningSection = new MiningSection(),
            fightingSection = new FightingSection(),
            bankingSection = new BankingSection(),
            priestSection = new PriestSection(),
            wizardSection = new WizardSection();

    private String currentSection = null;
    private long startTime;

    @Override
    public void onStart() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public int onLoop() {
        if (isTutorialIslandCompleted()) {
            stop();
        } else {
            switch (PlayerSettings.getConfig(Configs.OLD_TUTORIAL_ISLAND_CONFIG)) {
                case 0:
                case 1:
                    currentSection = "Gielinor Section";
                    gielinorSection.execute();
                    break;
                case 2:
                case 3:
                    currentSection = "Survival Section";
                    survivalSection.execute();
                    break;
                case 4:
                case 5:
                    currentSection = "Cooking Section";
                    cookingSection.execute();
                    break;
                case 6:
                case 7:
                    currentSection = "Quest Section";
                    questSection.execute();
                    break;
                case 8:
                case 9:
                    currentSection = "Mining Section";
                    miningSection.execute();
                    break;
                case 10:
                case 11:
                case 12:
                    currentSection = "Fighting Section";
                    fightingSection.execute();
                    break;
                case 14:
                case 15:
                    currentSection = "Banking Section";
                    bankingSection.execute();
                    break;
                case 16:
                case 17:
                    currentSection = "Priest Section";
                    priestSection.execute();
                    break;
                case 18:
                case 19:
                case 20:
                    currentSection = "Wizard Section";
                    wizardSection.execute();
                    break;
            }
        }
        return 300;
    }

    @Override
    public void onPaint(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        g.setColor(color1);
        g.fillRoundRect(7, 347, 204, 79, 16, 16);
        g.setColor(color2);
        g.setStroke(stroke1);
        g.drawRoundRect(7, 347, 204, 79, 16, 16);
        g.setFont(font1);
        g.setColor(color3);
        g.drawString("NASA TUTORIAL ISLAND v" + getVersion(), 15, 366);
        g.setFont(font2);
        g.drawString("Time elapsed: " + formatTime(getRuntime()), 17, 414);
        g.drawString("Current Section: " + currentSection, 15, 391);
    }

    private boolean isTutorialIslandCompleted() {
        return PlayerSettings.getConfig(Configs.OLD_TUTORIAL_ISLAND_PROGRESS_CONFIG) >= 1000;
    }

    private String formatTime(long ms) {
        long s = ms / 1000, m = s / 60;
        s %= 60;
        m %= 60;
        return String.format("%01d:%02d", m, s);
    }

    private long getRuntime() {
        return System.currentTimeMillis() - startTime;
    }

}
