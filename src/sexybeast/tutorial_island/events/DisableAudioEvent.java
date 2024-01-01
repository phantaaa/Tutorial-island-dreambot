package sexybeast.tutorial_island.events;

import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.widget.Widgets;
import sexybeast.tutorial_island.utils.ActionWidget;
import sexybeast.tutorial_island.utils.WidgetFilter;

public class DisableAudioEvent implements Event {
    private static final int musicVolumeConfig = 168;
    private static final int soundEffectVolumeConfig = 169;
    private static final int areaSoundEffectVolumeConfig = 872;
    private final WidgetFilter ADJUST_MUSIC_WIDGET = new ActionWidget("Adjust Music Volume");
    private final WidgetFilter ADJUST_SOUND_EFFECT_WIDGET = new ActionWidget("Adjust Sound Effect Volume");
    private final WidgetFilter ADJUST_AREA_SOUND_EFFECT_WIDGET = new ActionWidget("Adjust Area Sound Effect Volume");
    private final WidgetFilter AUDIO_WIDGET = new ActionWidget("Audio");

    @Override
    public boolean perform() {
        if (Tabs.getOpen() != Tab.OPTIONS) {
            Tabs.openWithMouse(Tab.OPTIONS);
        } else if (!ADJUST_MUSIC_WIDGET.get(Widgets.getWidgetsInstance()).isPresent()) {
            AUDIO_WIDGET.get(Widgets.getWidgetsInstance()).get().interact();
        } else if (!isVolumeDisabled(musicVolumeConfig)) {
            ADJUST_MUSIC_WIDGET.get(Widgets.getWidgetsInstance()).get().interact();
        } else if (!isVolumeDisabled(soundEffectVolumeConfig)) {
            ADJUST_SOUND_EFFECT_WIDGET.get(Widgets.getWidgetsInstance()).get().interact();
        } else if (!isVolumeDisabled(areaSoundEffectVolumeConfig)) {
            ADJUST_AREA_SOUND_EFFECT_WIDGET.get(Widgets.getWidgetsInstance()).get().interact();
        } else {
            return true;
        }
        return false;
    }

    private boolean isVolumeDisabled(final int config) {
        return PlayerSettings.getConfig(config) == 4;
    }
}
