package sexybeast.tutorial_island.utils;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;

public class Locations {

    private Locations() {
    }

    public static class Tiles {
        public static final Tile RAT_CAGE_INSIDE = new Tile(3108, 9518, 0);
        public static final Tile RAT_CAGE_OUTSIDE = new Tile(3112, 9518, 0);
        public static final Tile OUTSIDE_ACCOUNT_MANAGEMENT = new Tile(3130, 3124, 0);
        public static final Tile INSIDE_BANK = new Tile(3122, 3121, 0);
        public static final Tile PAST_SURVIVAL_FENCE = new Tile(3088, 3091, 0);
        public static final Tile CHICKENS = new Tile(3140, 3091, 0);
        public static final Tile QUEST_BUILDING_INSIDE = new Tile(3086, 3126, 0);
        public static final Tile PAST_MINING_GATE = new Tile(3096, 9503, 0);
        public static final Tile PAST_COOK_DOOR = new Tile(3071, 3090, 0);
        public static final Tile PAST_PRIEST_DOOR = new Tile(3122, 3101, 0);

        private Tiles() {
        }
    }

    public static class Areas {
        public static final Area CHICKEN_HOUSE = new Area(3139, 3085, 3143, 3091);
        public static final Area COOK_BUILDING = new Area(3073, 3083, 3078, 3086);
        public static final Area QUEST_BUILDING = new Area(3083, 3119, 3089, 3125);
        public static final Area RANGING_AREA = new Area(3108, 9508, 3112, 9517);
        public static final Area BANK_AREA = new Area(3119, 3119, 3124, 3123);
        public static final Area CHURCH_AREA = new Area(3120, 3103, 3128, 3110);
        public static final Area MINING_AREA = new Area(3084, 9497, 3074, 9508);
        public static final Area RAT_CAGE = new Area(
                new Tile(3110, 9520, 0),
                new Tile(3110, 9521, 0),
                new Tile(3110, 9522, 0),
                new Tile(3109, 9523, 0),
                new Tile(3107, 9523, 0),
                new Tile(3105, 9525, 0),
                new Tile(3102, 9526, 0),
                new Tile(3094, 9518, 0),
                new Tile(3097, 9515, 0),
                new Tile(3100, 9512, 0),
                new Tile(3102, 9510, 0),
                new Tile(3105, 9510, 0),
                new Tile(3106, 9511, 0),
                new Tile(3107, 9513, 0),
                new Tile(3108, 9514, 0),
                new Tile(3109, 9514, 0),
                new Tile(3110, 9515, 0),
                new Tile(3110, 9517, 0));

        public static final Area CHICKEN_AREA = new Area(
                new Tile(3144, 3089, 0),
                new Tile(3139, 3089, 0),
                new Tile(3137, 3092, 0),
                new Tile(3142, 3092, 0));

        private Areas() {
        }
    }

}
