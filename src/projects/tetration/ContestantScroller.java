package projects.tetration;

import osbjava.objects.*;
import static osbjava.types.OsbLayer.BACKGROUND;
import static osbjava.types.OsbOrigin.CENTER;
import static projects.tetration.ContestantSprite.State.*;
import java.util.*;


@SuppressWarnings("unused")
public class ContestantScroller {


    // ============================================================
    // [GLOBAL VARIABLES]


    //static final String OSB_FOLDER           = "tetration (a BFDITPOT medley)";
    static final String OSB_DIRECTORY        = ".\\src\\projects\\tetration\\beatmap\\";
    static final String OSB_NAME             = "nate bait - tetration (a BFDITPOT medley) (Nate Bait).osb";
    static final String BIN_DIRECTORY        = ".\\src\\projects\\tetration\\bin\\";
    static final String BIN_NAME             = "contestant_scroller.bin";

    static final double START_TIME           = 54532;
    static final double END_TIME             = 853657;
    static final double TIME_RESOLUTION      = 10;

    static final double IMG_SCALE            = 0.4445;
    static final double MIN_X                = -240;
    static final double MAX_X                = 760;
    static final double MIN_Y                = -500;
    static final double MAX_Y                = 700;

    static final String CONTESTANT_DIRECTORY = "storyboard\\bodies\\";
    static final double CONTESTANT_SCALE     = 0.2;
    static final double CONTESTANT_VELOCITY  = 72;
    static final double CONTESTANT_PADDING   = 8;
    static final double ANGLE_MAGNITUDE      = 10;
    static final double PACKING_LENIENCY     = 2;
    static final int    UNIQUE_GEN_COUNT     = Contestant.length() - 10;
    //static final String INITIAL_BASE_COLOR   = "506cfa";
    static final Random RANDOM               = new Random(System.currentTimeMillis());

    static final String[] RAINBOW_COLORS = new String[]
            {"e6261f", "eb8732", "f7d038", "a3e048", "45f8a9", "31c9f9", "d23be7"};
    static int rainbowColorIndex = 0;


    // ============================================================
    // [HELPERS]


    /* Subclass for handling timed events */
    static class Event implements Comparable<Event> {
        double time;
        String event;
        String value1, value2;

        public Event(double time, String event) {
            this.time = time;
            this.event = event;
            this.value1 = null;
            this.value2 = null;
        }

        public Event(double time, String event, String value) {
            this.time = time;
            this.event = event;
            this.value1 = value;
            this.value2 = null;
        }

        public Event(double time, String event, String value1, String value2) {
            this.time = time;
            this.event = event;
            this.value1 = value1;
            this.value2 = value2;
        }

        @Override
        public int compareTo(Event other) {
            return Double.compare(this.time, other.time);
        }
    }


    /* Generate a new and different contestant */
    public static Contestant generateContestant(Contestant currContestant) {
        int nextContestantIndex = RANDOM.nextInt(Contestant.length());
        if (currContestant != null)
            if (currContestant.ordinal() == nextContestantIndex)
                nextContestantIndex = (nextContestantIndex + RANDOM.nextInt(Contestant.length())) % Contestant.length();
        return Contestant.toEnum(nextContestantIndex);
    }


    /* Generate a new initialized contestant sprite */
    public static ContestantSprite generateContestantSprite(Contestant contestant, double time, OsbColor baseColor) {

        /* Generate new sprite */
        ContestantSprite sprite = new ContestantSprite(
                contestant,
                CONTESTANT_DIRECTORY,
                BACKGROUND,
                CENTER,
                time
        );

        /* Initialize sprite parameters */
        double[] angleRatePair = generateAngleRatePair();
        sprite.initializeColors(baseColor);
        sprite.initializeDimensions(CONTESTANT_SCALE, angleRatePair[0]);
        sprite.initializeMovement(CONTESTANT_VELOCITY, angleRatePair[1]);

        return sprite;
    }


    /* Generate a random pair of initial angle and suitable rotate rate */
    public static double[] generateAngleRatePair() {

        double startAngle  = -ANGLE_MAGNITUDE + 2*ANGLE_MAGNITUDE*RANDOM.nextDouble();
        double endAngleMin = -(startAngle/2 - 2*ANGLE_MAGNITUDE/4);
        double endAngleMax = -(startAngle/2 + 2*ANGLE_MAGNITUDE/4);
        double endAngle    = endAngleMin + (endAngleMax-endAngleMin)*RANDOM.nextDouble();

        double rotateRateFactor = 1 + ((ANGLE_MAGNITUDE - Math.abs(startAngle)) / ANGLE_MAGNITUDE) * 1;
        double rotateRate  = (endAngle-startAngle) / ((MAX_Y - MIN_Y) / CONTESTANT_VELOCITY);
        rotateRate *= rotateRateFactor;

        return new double[] {startAngle, rotateRate};
    }


    /* Generate a randomly ordered array of a range of integers and custom size */
    public static ArrayList<Integer> generateRandomArray(int range, int size) {
        ArrayList<Integer> array = new ArrayList<>();
        for (int i = 0; i < range; i++)
            array.add(i);
        Collections.shuffle(array);
        return new ArrayList<>(array.subList(0, size-1));
    }


    /* Generate next rainbow color */
    public static String generateNextRainbowColor() {
        return RAINBOW_COLORS[rainbowColorIndex++ % RAINBOW_COLORS.length];
    }


    /* Calculate beat length of a tempo */
    public static double calculateBeatLength(double tempo) {
        return (60.0 / tempo) * 1000;
    }


    // ============================================================
    // [MAIN METHOD]


    public static void main(String[] args) {

        OsbFile file = new OsbFile();

        /* Variables for sprites and events */
        ArrayList<ContestantSprite> sprites = new ArrayList<>();
        ArrayList<Event>            events  = new ArrayList<>();
        OsbColor                    currBaseColor;

        /* Variables for keeping track of time */
        double currTime = START_TIME;
        double refTime  = START_TIME;

        /* Variables for sprite generation */
        double genCoordX = MIN_X;    // These indicate the BOTTOM-LEFT coords to generate each incoming sprite.
        double genCoordY = MAX_Y;    // However, the origin of the sprites must be CENTER for proper rotation.
        Contestant nextContestant = null;
        ArrayList<Integer> nextContestantIndices = new ArrayList<>();

        /* Variables for rainbow color (see MOVEMENT IV) */
        OsbColor currRainbowColor;


        // --------
        // [EVENTS]
        // --------

        // get ready for absolute carnage


        // ==================== [MOVEMENT I] ====================
        currBaseColor = new OsbColor("506cfa");
        // ======================================================


        /* New Friendly */
        for (int repeats = 0; repeats < 6; repeats++) {
            for (int i = 0; i < 32; i++) {
                if (i % 4 != 0)
                    if (i == 15)
                        events.add(new Event(54987 + repeats*calculateBeatLength(132)*16
                                                   + i*calculateBeatLength(132)/2, "turn_on", "50"));
                    else if (i == 17) {
                        events.add(new Event(54987 + repeats*calculateBeatLength(132)*16
                                                   + i*calculateBeatLength(132)/2, "turn_off_smooth"));
                        events.add(new Event(54987 + repeats*calculateBeatLength(132)*16
                                                   + i*calculateBeatLength(132)/2, "flash", "25"));
                    }
                    else
                        events.add(new Event(54987 + repeats*calculateBeatLength(132)*16
                                                   + i*calculateBeatLength(132)/2, "flash", "25"));
            }

        }
        for (int i = 0; i < 24; i++)
            if (i % 4 != 0)
                events.add(new Event(98623 + i*calculateBeatLength(132)/2, "flash", "25"));


        /* Places */
        for (int i = 0; i < 8; i++) {
            events.add(new Event(104078 + i*calculateBeatLength(132)/2, "turn_on", "1"+i, currBaseColor.getHex()));
        }
        HashSet<Integer> beatNumbers = new HashSet<>();
        Collections.addAll(beatNumbers, 0, 2, 3, 4, 6, 9, 10, 11, 12, 14,
                                        16, 18, 19, 20, 22, 23, 25, 26, 27, 30, 31);
        for (int repeats = 0; repeats < 3; repeats++) {
            for (int i = 0; i < 32; i++) {
                if (beatNumbers.contains(i) || (i == 29 && repeats == 2))
                    events.add(new Event(105896 + repeats*calculateBeatLength(132)*16
                                                + i*calculateBeatLength(132)/2, "turn_on", "40"));
                else if (i == 8 || (i == 29 && repeats != 2) || (i == 28 && repeats == 2))
                    events.add(new Event(105896 + repeats*calculateBeatLength(132)*16
                                               + i*calculateBeatLength(132)/2, "turn_off"));
            }
        }
        beatNumbers.clear();
        Collections.addAll(beatNumbers, 0, 2, 3, 4, 6, 8, 9, 11, 13, 14, 15,
                                        16, 18, 19, 20, 22, 23, 25, 26, 27);
        for (int i = 0; i < 32; i++) {
            if (beatNumbers.contains(i))
                events.add(new Event(127714 + i*calculateBeatLength(132)/2, "turn_on", "40"));
            else if (i == 29)
                events.add(new Event(127714 + i*calculateBeatLength(132)/2, "turn_off_smooth"));
        }


        /* Evolekaf */
        beatNumbers.clear();
        Collections.addAll(beatNumbers, 8, 9, 10, 11, 12, 14,
                                        24, 25, 26, 27, 28, 30,
                                        40, 41, 42, 43, 44, 46,
                                        48, 52, 54, 58, 60);
        for (int repeats = 0; repeats < 3; repeats++) {
            for (int i = 0; i < 64; i++) {
                if (beatNumbers.contains(i))
                    events.add(new Event(149532 + repeats*calculateBeatLength(132)*16
                                                + i*calculateBeatLength(132)/4, "flash", "12", currBaseColor.getHex()));
            }
        }


        /* Famous 600k */
        HashSet<Integer> f600k_on      = new HashSet<>();
        HashSet<Integer> f600k_flashes = new HashSet<>();
        Collections.addAll(f600k_on, 0, 4, 6, 8, 10, 12, 16, 20, 22,
                                     32, 36, 38, 40, 42, 44);
        Collections.addAll(f600k_flashes, 24, 25, 26, 27, 28, 30,
                                          48, 50, 52, 54, 56, 58, 60);
        for (int repeats = 0; repeats < 2; repeats++) {
            for (int i = 0; i < 64; i++) {
                if (i == 24 || i == 48)
                    events.add(new Event(178623 + repeats*calculateBeatLength(132)*16
                                                + i*calculateBeatLength(132)/4, "turn_off_smooth"));
                if (f600k_on.contains(i))
                    events.add(new Event(178623 + repeats*calculateBeatLength(132)*16
                                                + i*calculateBeatLength(132)/4, "turn_on", "40"));
                if (f600k_flashes.contains(i))
                    events.add(new Event(178623 + repeats*calculateBeatLength(132)*16
                                                + i*calculateBeatLength(132)/4, "flash", "12", currBaseColor.getHex()));
            }
        }
        f600k_on.clear();
        Collections.addAll(f600k_on, 0, 4, 8, 11, 14, 17, 20, 22,
                                     32, 36, 38, 40, 43, 46);
        for (int i = 0; i < 64; i++) {
            if (i == 2 || i == 6)
                events.add(new Event(193169 + i*calculateBeatLength(132)/4, "turn_off"));
            if (i == 24 || i == 48)
                events.add(new Event(193169 + i*calculateBeatLength(132)/4, "turn_off_smooth"));
            if (f600k_on.contains(i))
                events.add(new Event(193169 + i*calculateBeatLength(132)/4, "turn_on", (i == 0 || i == 4) ? "all" : "40"));
            if (f600k_flashes.contains(i))
                events.add(new Event(193169 + i*calculateBeatLength(132)/4, "flash", "12", currBaseColor.getHex()));
        }
        f600k_on.clear(); f600k_flashes.clear();
        Collections.addAll(f600k_on, 0, 4, 6, 8, 11, 14, 17, 20, 22,
                                     32, 36, 38, 40, 42, 44,
                                     48, 50, 52, 54, 56, 58, 60, 62);
        Collections.addAll(f600k_flashes, 24, 25, 26, 27, 28, 30);
        for (int i = 0; i < 64; i++) {
            if (i == 24)
                events.add(new Event(200442 + i*calculateBeatLength(132)/4, "turn_off_smooth"));
            if (f600k_on.contains(i))
                events.add(new Event(200442 + i*calculateBeatLength(132)/4, "turn_on", "40"));
            if (f600k_flashes.contains(i))
                events.add(new Event(200442 + i*calculateBeatLength(132)/4, "flash", "12", currBaseColor.getHex()));
        }
        events.add(new Event(207714, "turn_off"));
        for (int i = 0; i < 5; i++) {
            if (i == 4)
                events.add(new Event(207942 + i*calculateBeatLength(132)*0.75, "turn_on", "all"));
            else
                events.add(new Event(207942 + i*calculateBeatLength(132)*0.75, "turn_on", "40"));
        }
        events.add(new Event(211351, "turn_off_smooth"));


        // ==================== [MOVEMENT II] ====================
        currBaseColor = new OsbColor("008000");
        // =======================================================
        // what drugs am i on i can just use the hit objects on the beatmap


        /* readme.txt */
        for (int repeats = 0; repeats < 16; repeats++) {
            events.add(new Event(215214 + repeats*calculateBeatLength(132)*4, "turn_on", "30", currBaseColor.getHex()));
            events.add(new Event(215593 + repeats*calculateBeatLength(132)*4, "turn_on", "30", currBaseColor.getHex()));
            events.add(new Event(215896 + repeats*calculateBeatLength(132)*4, "turn_on", "30", currBaseColor.getHex()));
            events.add(new Event(216275 + repeats*calculateBeatLength(132)*4, "turn_on", "30", currBaseColor.getHex()));
            events.add(new Event(216351 + repeats*calculateBeatLength(132)*4, "turn_off_smooth"));
        }


        /* Movement Proposition */
        events.add(new Event(244078, "turn_on", "60"));
        events.add(new Event(244760, "turn_on", "60"));
        events.add(new Event(247714, "turn_on", "60"));
        events.add(new Event(248396, "turn_on", "60"));
        events.add(new Event(248851, "turn_on", "60"));
        events.add(new Event(251351, "turn_on", "60"));
        events.add(new Event(252032, "turn_on", "60"));
        events.add(new Event(254987, "turn_on", "60"));
        events.add(new Event(255669, "turn_on", "60"));
        events.add(new Event(256123, "turn_on", "60"));

        events.add(new Event(244078 + 180, "turn_off_smooth"));
        events.add(new Event(244760 + 180, "turn_off_smooth"));
        events.add(new Event(247714 + 180, "turn_off_smooth"));
        events.add(new Event(248396 + 180, "turn_off_smooth"));
        events.add(new Event(248851 + 180, "turn_off_smooth"));
        events.add(new Event(251351 + 180, "turn_off_smooth"));
        events.add(new Event(252032 + 180, "turn_off_smooth"));
        events.add(new Event(254987 + 180, "turn_off_smooth"));
        events.add(new Event(255669 + 180, "turn_off_smooth"));
        events.add(new Event(256123 + 180, "turn_off_smooth"));


        /* Ballers */
        for (int i = 0; i < 8; i++)
            events.add(new Event(265896 + i*calculateBeatLength(132), "flash", "24", currBaseColor.getHex()));
        for (int i = 0; i < 24; i++)
            events.add(new Event(269532 + i*calculateBeatLength(132)/3, "flash", "12", currBaseColor.getHex()));

        events.add(new Event(273169, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(273320, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(273472, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(273623, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(273775, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(273926, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(274078, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(274229, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(274381, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(274532, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(274684, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(274835, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(275896, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(276048, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(276199, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(276351, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(276502, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(276654, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(276805, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(276957, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(277108, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(277260, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(277411, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(277563, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(279532, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(279684, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(279835, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(279987, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(280138, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(280290, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(283169, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(283320, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(283472, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(283623, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(283775, "flash", "12", currBaseColor.getHex()));
        events.add(new Event(283926, "flash", "12", currBaseColor.getHex()));

        events.add(new Event(274987, "turn_on", "20"));
        events.add(new Event(275214, "turn_on", "20"));
        events.add(new Event(275442, "turn_on", "20"));
        events.add(new Event(275669, "turn_on", "20"));
        events.add(new Event(277714, "turn_on", "20"));
        events.add(new Event(277942, "turn_on", "20"));
        events.add(new Event(278169, "turn_on", "20"));
        events.add(new Event(278396, "turn_on", "20"));
        events.add(new Event(278623, "turn_on", "20"));
        events.add(new Event(278805, "turn_on", "20"));
        events.add(new Event(278987, "turn_on", "20"));
        events.add(new Event(279169, "turn_on", "20"));
        events.add(new Event(279351, "turn_on", "20"));
        events.add(new Event(280442, "turn_on", "20"));
        events.add(new Event(280745, "turn_on", "20"));
        events.add(new Event(281048, "turn_on", "20"));
        events.add(new Event(281351, "turn_on", "20"));
        events.add(new Event(281578, "turn_on", "20"));
        events.add(new Event(281805, "turn_on", "20"));
        events.add(new Event(282032, "turn_on", "20"));
        events.add(new Event(282260, "turn_on", "20"));
        events.add(new Event(282442, "turn_on", "20"));
        events.add(new Event(282624, "turn_on", "20"));
        events.add(new Event(282806, "turn_on", "20"));
        events.add(new Event(282988, "turn_on", "20"));
        events.add(new Event(284078, "turn_on", "20"));
        events.add(new Event(284532, "turn_on", "20"));
        events.add(new Event(284987, "turn_on", "20"));
        events.add(new Event(285117, "turn_on", "20"));
        events.add(new Event(285247, "turn_on", "20"));
        events.add(new Event(285377, "turn_on", "20"));
        events.add(new Event(285506, "turn_on", "20"));
        events.add(new Event(285636, "turn_on", "20"));
        events.add(new Event(285766, "turn_on", "20"));
        events.add(new Event(285896, "turn_on", "20"));
        events.add(new Event(286048, "turn_on", "20"));
        events.add(new Event(286199, "turn_on", "20"));
        events.add(new Event(286351, "turn_on", "20"));
        events.add(new Event(286503, "turn_on", "20"));
        events.add(new Event(286654, "turn_on", "20"));
        events.add(new Event(286805, "turn_on", "20"));
        events.add(new Event(287108, "turn_on", "20"));
        events.add(new Event(287411, "flash", "20"));


        /* Votely */
        for (int i = 0; i < 64; i++) {
            if (i != 27 && i != 28 && i != 59 && i != 60) {
                if (i % 2 == 0)
                    events.add(new Event(294987 + i * calculateBeatLength(132), "flash", "24", currBaseColor.getHex()));
                else
                    events.add(new Event(294987 + i * calculateBeatLength(132), "flash", "24"));
            }
        }
        events.add(new Event(307487, "turn_on", "24", currBaseColor.getHex()));
        events.add(new Event(307828, "turn_on", "24", currBaseColor.getHex()));
        events.add(new Event(322032, "turn_on", "24", currBaseColor.getHex()));
        events.add(new Event(322373, "turn_on", "24", currBaseColor.getHex()));


        /* Hey Two */
        for (int i = 0; i < 2; i++) {
            events.add(new Event(324078 + i*calculateBeatLength(132)*16, "turn_on", "24"));
            events.add(new Event(324305 + i*calculateBeatLength(132)*16, "turn_on", "24"));
            events.add(new Event(324532 + i*calculateBeatLength(132)*16, "turn_on", "24"));
            events.add(new Event(324760 + i*calculateBeatLength(132)*16, "turn_on", "24"));
            events.add(new Event(324987 + i*calculateBeatLength(132)*16, "flash", "24"));
            events.add(new Event(325669 + i*calculateBeatLength(132)*16, "turn_on", "24"));
            events.add(new Event(326123 + i*calculateBeatLength(132)*16, "turn_on", "24"));
            events.add(new Event(326351 + i*calculateBeatLength(132)*16, "turn_on", "24"));
            events.add(new Event(326578 + i*calculateBeatLength(132)*16, "turn_on", "24"));
            events.add(new Event(326805 + i*calculateBeatLength(132)*16, "turn_on", "24"));
            events.add(new Event(327032 + i*calculateBeatLength(132)*16, "turn_on", "24"));
            events.add(new Event(327260 + i*calculateBeatLength(132)*16, "turn_on", "24"));
            events.add(new Event(327487 + i*calculateBeatLength(132)*16, "turn_on", "24"));
            events.add(new Event(327714 + i*calculateBeatLength(132)*16, "turn_on", "24"));
            events.add(new Event(327942 + i*calculateBeatLength(132)*16, "turn_on", "24"));
            events.add(new Event(328169 + i*calculateBeatLength(132)*16, "turn_on", "24"));
            events.add(new Event(328396 + i*calculateBeatLength(132)*16, "turn_on", "24"));
            events.add(new Event(328623 + i*calculateBeatLength(132)*16, "flash", "24"));
        }

        events.add(new Event(338623, "turn_on", "24", currBaseColor.getHex()));
        events.add(new Event(338737, "turn_on", "24", currBaseColor.getHex()));
        events.add(new Event(338851, "turn_on", "24", currBaseColor.getHex()));
        events.add(new Event(338964, "turn_on", "24", currBaseColor.getHex()));
        events.add(new Event(339078, "turn_on", "24"));
        events.add(new Event(339760, "turn_on", "24"));
        events.add(new Event(339873, "turn_on", "24"));
        events.add(new Event(340101, "turn_on", "24"));
        events.add(new Event(340214, "turn_on", "24"));
        events.add(new Event(341464, "turn_on", "24"));
        events.add(new Event(341578, "turn_on", "24"));
        events.add(new Event(341692, "turn_on", "24"));
        events.add(new Event(341805, "turn_on", "24"));
        events.add(new Event(341919, "turn_on", "24"));
        events.add(new Event(342032, "turn_on", "24"));
        events.add(new Event(342146, "turn_on", "24"));
        events.add(new Event(342942, "turn_on", "24"));
        events.add(new Event(343623, "turn_on", "24"));
        events.add(new Event(343851, "turn_on", "24"));
        events.add(new Event(344305, "turn_on", "24"));
        events.add(new Event(344419, "turn_on", "24"));
        events.add(new Event(344532, "turn_on", "24"));
        events.add(new Event(344646, "turn_on", "24"));
        events.add(new Event(345214, "turn_on", "24"));
        events.add(new Event(345896, "turn_on", "24"));
        events.add(new Event(347032, "turn_on", "24"));
        events.add(new Event(347146, "turn_on", "24"));
        events.add(new Event(347373, "turn_on", "24"));
        events.add(new Event(347487, "turn_on", "24"));
        events.add(new Event(348737, "turn_on", "24"));
        events.add(new Event(348851, "turn_on", "24"));
        events.add(new Event(348964, "turn_on", "24"));
        events.add(new Event(349078, "turn_on", "24"));
        events.add(new Event(349192, "turn_on", "24"));
        events.add(new Event(349305, "turn_on", "24"));
        events.add(new Event(349419, "turn_on", "24"));
        events.add(new Event(350214, "turn_on", "24"));
        events.add(new Event(350896, "turn_on", "24"));
        events.add(new Event(351123, "turn_on", "24"));
        events.add(new Event(351578, "turn_on", "24"));
        events.add(new Event(351692, "turn_on", "24"));
        events.add(new Event(351805, "turn_on", "24"));
        events.add(new Event(351919, "turn_on", "24"));
        events.add(new Event(352487, "turn_on", "24"));
        events.add(new Event(352828, "turn_on", "24"));


        /* Who Likes to Party */
        for (int i = 0; i < 69; i++) {
            if (i != 32 && !(i >= 56 && i <= 63) && i != 21 && i != 22 && i != 53 && i != 54) {
                if (i % 2 == 0)
                    events.add(new Event(353169 + i*calculateBeatLength(132), "flash", "24", currBaseColor.getHex()));
                else
                    events.add(new Event(353169 + i*calculateBeatLength(132), "flash", "24"));
            }
        }
        events.add(new Event(362942, "turn_on", "24", currBaseColor.getHex()));
        events.add(new Event(363282, "turn_on", "24", currBaseColor.getHex()));
        events.add(new Event(377487, "turn_on", "24", currBaseColor.getHex()));
        events.add(new Event(377828, "turn_on", "24", currBaseColor.getHex()));


        // ==================== [MOVEMENT III] ====================
        currBaseColor = new OsbColor("c75656");
        // ========================================================


        /* Bnob */
        for (int i = 0; i < 120; i++)
            if ((i % 2 == 0 && i != 32 && i != 96) || i == 33 || i == 97)
                events.add(new Event(409442 + i*calculateBeatLength(100)/4, "flash", "16", currBaseColor.getHex()));


        /* Night Mote */
        for (int repeats = 0; repeats < 4; repeats++) {
            events.add(new Event(428642 + repeats*calculateBeatLength(100)*16, "turn_on", "24", currBaseColor.getHex()));
            events.add(new Event(429092 + repeats*calculateBeatLength(100)*16, "flash", "16"));
            events.add(new Event(429542 + repeats*calculateBeatLength(100)*16, "flash", "16"));
            events.add(new Event(430292 + repeats*calculateBeatLength(100)*16, "turn_on", "24", currBaseColor.getHex()));
            events.add(new Event(430742 + repeats*calculateBeatLength(100)*16, "flash", "16"));
            events.add(new Event(431042 + repeats*calculateBeatLength(100)*16, "turn_on", "24", currBaseColor.getHex()));
            events.add(new Event(431492 + repeats*calculateBeatLength(100)*16, "flash", "16"));
            events.add(new Event(431942 + repeats*calculateBeatLength(100)*16, "flash", "16"));
            events.add(new Event(432692 + repeats*calculateBeatLength(100)*16, "turn_on", "24", currBaseColor.getHex()));
            events.add(new Event(432992 + repeats*calculateBeatLength(100)*16, "flash", "16"));
            events.add(new Event(433142 + repeats*calculateBeatLength(100)*16, "flash", "16"));
            events.add(new Event(433442 + repeats*calculateBeatLength(100)*16, "turn_on", "24", currBaseColor.getHex()));
            events.add(new Event(433892 + repeats*calculateBeatLength(100)*16, "flash", "16"));
            events.add(new Event(434342 + repeats*calculateBeatLength(100)*16, "flash", "16"));
            events.add(new Event(435092 + repeats*calculateBeatLength(100)*16, "turn_on", "24", currBaseColor.getHex()));
            events.add(new Event(435542 + repeats*calculateBeatLength(100)*16, "flash", "16"));
            events.add(new Event(435842 + repeats*calculateBeatLength(100)*16, "turn_on", "24", currBaseColor.getHex()));
            events.add(new Event(436292 + repeats*calculateBeatLength(100)*16, "flash", "16"));
            events.add(new Event(436742 + repeats*calculateBeatLength(100)*16, "flash", "16"));
            events.add(new Event(437342 + repeats*calculateBeatLength(100)*16, "turn_on", "24", currBaseColor.getHex()));
            events.add(new Event(437492 + repeats*calculateBeatLength(100)*16, "flash", "16"));
            if (repeats != 3)
                events.add(new Event(437942 + repeats*calculateBeatLength(100)*16, "flash", "16"));
        }


        /* Hallway */
        for (int repeats = 0; repeats < 3; repeats++) {
            events.add(new Event(467042 + repeats*calculateBeatLength(100)*8, "turn_on", "all"));
            events.add(new Event(468542 + repeats*calculateBeatLength(100)*8, "flash", "32"));
            events.add(new Event(468692 + repeats*calculateBeatLength(100)*8, "flash", "32"));
            events.add(new Event(468842 + repeats*calculateBeatLength(100)*8, "flash", "32", currBaseColor.getHex()));
            events.add(new Event(468992 + repeats*calculateBeatLength(100)*8, "flash", "32", currBaseColor.getHex()));
            events.add(new Event(469142 + repeats*calculateBeatLength(100)*8, "flash", "32"));
            events.add(new Event(469292 + repeats*calculateBeatLength(100)*8, "flash", "32"));
            events.add(new Event(469442 + repeats*calculateBeatLength(100)*8, "flash", "32", currBaseColor.getHex()));
            events.add(new Event(469592 + repeats*calculateBeatLength(100)*8, "flash", "32", currBaseColor.getHex()));
            events.add(new Event(469742 + repeats*calculateBeatLength(100)*8, "flash", "32"));
            events.add(new Event(469892 + repeats*calculateBeatLength(100)*8, "flash", "32"));
            events.add(new Event(470042 + repeats*calculateBeatLength(100)*8, "flash", "32", currBaseColor.getHex()));
            events.add(new Event(470192 + repeats*calculateBeatLength(100)*8, "flash", "32", currBaseColor.getHex()));
            events.add(new Event(470342 + repeats*calculateBeatLength(100)*8, "flash", "32"));
            events.add(new Event(470492 + repeats*calculateBeatLength(100)*8, "flash", "32"));
            events.add(new Event(470642 + repeats*calculateBeatLength(100)*8, "flash", "32", currBaseColor.getHex()));
            events.add(new Event(470792 + repeats*calculateBeatLength(100)*8, "flash", "32", currBaseColor.getHex()));
            events.add(new Event(470942 + repeats*calculateBeatLength(100)*8, "flash", "32"));
            events.add(new Event(471092 + repeats*calculateBeatLength(100)*8, "flash", "32"));
            events.add(new Event(471242 + repeats*calculateBeatLength(100)*8, "turn_off"));
        }

        for (int i = 0; i < 8; i++)
            events.add(new Event(481442 + i*calculateBeatLength(100)/2, "flash", "40"));
        for (int i = 0; i < 8; i++)
            events.add(new Event(483842 + i*calculateBeatLength(100)/4, "flash", "40"));
        for (int i = 0; i < 16; i++)
            events.add(new Event(485042 + i*calculateBeatLength(100)/8, "flash", "40"));
        //events.add(new Event(486242, "turn_off"));


        /* Branchless */
        events.add(new Event(491042, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(492542, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(493442, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(493742, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(494042, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(494342, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(494642, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(495242, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(495842, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(497342, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(498242, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(500042, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(500642, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(502142, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(503042, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(503642, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(504242, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(504842, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(505442, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(506942, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(507842, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(509642, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(510242, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(511742, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(512642, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(512942, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(513242, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(513542, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(513842, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(514442, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(515042, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(516542, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(517442, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(519242, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(519842, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(521042, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(522242, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(522842, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(523442, "turn_on", "30", currBaseColor.getHex()));
        events.add(new Event(524042, "turn_on", "30", currBaseColor.getHex()));


        /* Gamla + Hush */
        for (int repeats = 0; repeats < 2; repeats++) {
            events.add(new Event(524642 + repeats*calculateBeatLength(100)*16, "turn_on", "30", currBaseColor.getHex()));
            events.add(new Event(525242 + repeats*calculateBeatLength(100)*16, "turn_off_smooth"));
            events.add(new Event(526142 + repeats*calculateBeatLength(100)*16, "turn_on", "30", currBaseColor.getHex()));
            events.add(new Event(526742 + repeats*calculateBeatLength(100)*16, "turn_off_smooth"));
            events.add(new Event(527042 + repeats*calculateBeatLength(100)*16, "turn_on", "30", currBaseColor.getHex()));
            events.add(new Event(527642 + repeats*calculateBeatLength(100)*16, "turn_off_smooth"));
            events.add(new Event(528542 + repeats*calculateBeatLength(100)*16, "turn_on", "30", currBaseColor.getHex()));
            events.add(new Event(529142 + repeats*calculateBeatLength(100)*16, "turn_off_smooth"));
            events.add(new Event(529442 + repeats*calculateBeatLength(100)*16, "turn_on", "30", currBaseColor.getHex()));
            events.add(new Event(530042 + repeats*calculateBeatLength(100)*16, "turn_off_smooth"));
            events.add(new Event(530942 + repeats*calculateBeatLength(100)*16, "turn_on", "30", currBaseColor.getHex()));
            events.add(new Event(531542 + repeats*calculateBeatLength(100)*16, "turn_off_smooth"));
            events.add(new Event(531842 + repeats*calculateBeatLength(100)*16, "turn_on", "30", currBaseColor.getHex()));
            events.add(new Event(532442 + repeats*calculateBeatLength(100)*16, "turn_off_smooth"));
            events.add(new Event(533042 + repeats*calculateBeatLength(100)*16, "turn_on", "30", currBaseColor.getHex()));
            events.add(new Event(533642 + repeats*calculateBeatLength(100)*16, "turn_on", "30", currBaseColor.getHex()));
        }
        events.add(new Event(543842, "turn_off_smooth"));


        /* Hit It */
        // oh god
        for (int repeats = 0; repeats < 4; repeats++) {
            events.add(new Event(548642 + repeats*calculateBeatLength(100)*8, "turn_on", "all", "fe524e"));
            events.add(new Event(549242 + repeats*calculateBeatLength(100)*8, "turn_off"));
            events.add(new Event(549542 + repeats*calculateBeatLength(100)*8, "turn_on", "all", "fe524e"));
        }

        events.add(new Event(555842, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(556142, "turn_on", "50"));
        events.add(new Event(556442, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(556742, "turn_on", "50"));
        events.add(new Event(557042, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(557342, "turn_on", "80", currBaseColor.getHex()));
        events.add(new Event(557492, "turn_on", "80"));
        events.add(new Event(557642, "turn_on", "80", currBaseColor.getHex()));
        events.add(new Event(557792, "turn_on", "80"));
        events.add(new Event(557942, "turn_on", "80", currBaseColor.getHex()));
        events.add(new Event(558092, "turn_on", "80"));

        for (int i = 0; i < 32; i++) {
            int number = RANDOM.nextInt();
            double time = 550442;
            if (number % 2 == 0)
                events.add(new Event(time + i*calculateBeatLength(100)/8, "flash", "30", "fe524e"));
            else
                events.add(new Event(time + i*calculateBeatLength(100)/8, "flash", "30", currBaseColor.getHex()));
        }

        for (int i = 0; i < 8; i++) {
            int number = RANDOM.nextInt();
            double time = 555242;
            if (number % 2 == 0)
                events.add(new Event(time + i*calculateBeatLength(100)/8, "flash", "30", "fe524e"));
            else
                events.add(new Event(time + i*calculateBeatLength(100)/8, "flash", "30", currBaseColor.getHex()));
        }

        for (int i = 0; i < 28; i++) {
            int number = RANDOM.nextInt();
            double time = 560042;
            if (number % 2 == 0)
                events.add(new Event(time + i*calculateBeatLength(100)/8, "flash", "30", "fe524e"));
            else
                events.add(new Event(time + i*calculateBeatLength(100)/8, "flash", "30", currBaseColor.getHex()));
        }

        for (int i = 0; i < 40; i++) {
            int number = RANDOM.nextInt();
            double time = 564842;
            if (number % 2 == 0)
                events.add(new Event(time + i*calculateBeatLength(100)/8, "flash", "30", "fe524e"));
            else
                events.add(new Event(time + i*calculateBeatLength(100)/8, "flash", "30", currBaseColor.getHex()));
        }

        events.add(new Event(562142, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(562254, "turn_off"));
        events.add(new Event(562292, "turn_on", "50", "fe524e"));
        events.add(new Event(562404, "turn_off"));
        events.add(new Event(562442, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(562592, "turn_off"));
        events.add(new Event(562742, "flash", "50"));



        // ==================== [MOVEMENT IV] ====================
        currBaseColor = new OsbColor("127ced");
        // =======================================================


        /* Infetterence + Postehaste (pre-drop) */
        for (int repeats = 0; repeats < 7; repeats++) {
            events.add(new Event(576327 + repeats*calculateBeatLength(175)*8, "turn_on", "24", currBaseColor.getHex()));
            events.add(new Event(576499 + repeats*calculateBeatLength(175)*8, "turn_on", "24", currBaseColor.getHex()));
            events.add(new Event(576842 + repeats*calculateBeatLength(175)*8, "turn_on", "24", currBaseColor.getHex()));
            events.add(new Event(577013 + repeats*calculateBeatLength(175)*8, "turn_on", "24", currBaseColor.getHex()));
            events.add(new Event(577184 + repeats*calculateBeatLength(175)*8, "turn_on", "24", currBaseColor.getHex()));
            events.add(new Event(577527 + repeats*calculateBeatLength(175)*8, "turn_on", "24", currBaseColor.getHex()));
            events.add(new Event(577699 + repeats*calculateBeatLength(175)*8, "turn_on", "24", currBaseColor.getHex()));
            events.add(new Event(577870 + repeats*calculateBeatLength(175)*8, "turn_on", "24", currBaseColor.getHex()));
            events.add(new Event(578042 + repeats*calculateBeatLength(175)*8, "turn_on", "24", currBaseColor.getHex()));
            events.add(new Event(578213 + repeats*calculateBeatLength(175)*8, "turn_on", "24", currBaseColor.getHex()));
            events.add(new Event(578556 + repeats*calculateBeatLength(175)*8, "turn_off_smooth"));
        }

        events.add(new Event(624670, "flash", "24"));
        events.add(new Event(625013, "flash", "24"));
        events.add(new Event(625356, "flash", "24"));
        events.add(new Event(630156, "flash", "24"));
        events.add(new Event(630499, "flash", "24"));
        events.add(new Event(630842, "flash", "24"));

        events.add(new Event(636670, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(636927, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(637013, "flash", "24", "f3fffc"             ));
        events.add(new Event(637184, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(637356, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(637613, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(637699, "flash", "24", "f3fffc"             ));
        events.add(new Event(637870, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(638042, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(638299, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(638384, "flash", "24", "f3fffc"             ));
        events.add(new Event(638556, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(638727, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(638984, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(639070, "flash", "24", "f3fffc"             ));
        events.add(new Event(639242, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(639413, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(639670, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(639927, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(640184, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(640442, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(640784, "turn_on", "40"));
        events.add(new Event(641042, "turn_on", "40"));
        events.add(new Event(641299, "turn_on", "40"));
        events.add(new Event(641556, "turn_on", "40"));
        events.add(new Event(641813, "turn_on", "40"));


        /* Infetterence (drop) */
        events.add(new Event(642156, "turn_on", "all", "f3fffc"));
        events.add(new Event(644384, "turn_on", "all", "f3fffc"));
        events.add(new Event(645242, "turn_on", "all", "f3fffc"));
        events.add(new Event(646184, "turn_on", "all", "f3fffc"));
        events.add(new Event(647642, "turn_on", "all", "f3fffc"));
        events.add(new Event(648927, "turn_on", "all", "f3fffc"));
        events.add(new Event(649613, "turn_on", "all", "f3fffc"));
        events.add(new Event(650384, "flash", "all", currBaseColor.getHex()));
        events.add(new Event(651756, "flash", "all", currBaseColor.getHex()));
        events.add(new Event(653127, "turn_on", "all", "f3fffc"));
        events.add(new Event(655356, "turn_on", "all", "f3fffc"));
        events.add(new Event(656213, "turn_on", "all", "f3fffc"));
        events.add(new Event(657156, "turn_on", "all", "f3fffc"));
        events.add(new Event(658613, "turn_on", "all", "f3fffc"));
        events.add(new Event(659899, "turn_on", "all", "f3fffc"));
        events.add(new Event(660584, "turn_on", "all", "f3fffc"));
        events.add(new Event(661356, "flash", "all", currBaseColor.getHex()));
        events.add(new Event(662727, "flash", "all", currBaseColor.getHex()));

        events.add(new Event(642499, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(642756, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(643013, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(643184, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(643442, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(643699, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(643870, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(644127, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(645413, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(645756, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(645927, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(646442, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(646613, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(646870, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(647127, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(647299, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(647984, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(648242, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(648499, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(648670, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(649184, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(649356, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(649870, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(650042, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(653470, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(653727, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(653984, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(654156, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(654413, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(654670, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(654842, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(655099, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(656384, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(656727, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(656899, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(657413, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(657584, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(657842, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(658099, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(658270, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(658956, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(659213, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(659470, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(659642, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(660156, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(660327, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(660842, "turn_on", "50", currBaseColor.getHex()));
        events.add(new Event(661013, "turn_on", "50", currBaseColor.getHex()));

        events.add(new Event(644556, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(644813, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(645070, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(652099, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(652270, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(652527, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(652784, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(655527, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(655784, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(656042, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(644556, "flash", "24", "f3fffc"));
        events.add(new Event(644813, "flash", "24", "f3fffc"));
        events.add(new Event(645070, "flash", "24", "f3fffc"));
        events.add(new Event(652099, "flash", "24", "f3fffc"));
        events.add(new Event(652270, "flash", "24", "f3fffc"));
        events.add(new Event(652527, "flash", "24", "f3fffc"));
        events.add(new Event(652784, "flash", "24", "f3fffc"));
        events.add(new Event(655527, "flash", "24", "f3fffc"));
        events.add(new Event(655784, "flash", "24", "f3fffc"));
        events.add(new Event(656042, "flash", "24", "f3fffc"));

        events.add(new Event(650727, "flash", "16"));
        events.add(new Event(650899, "flash", "16"));
        events.add(new Event(651070, "flash", "16"));
        events.add(new Event(651242, "flash", "16"));
        events.add(new Event(651413, "flash", "16"));
        events.add(new Event(651584, "flash", "16"));


        /* Infetterence (post-drop) */
        events.add(new Event(664099, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(664613, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(665127, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(666842, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(667356, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(667870, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(668556, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(669584, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(670099, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(670613, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(672327, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(672842, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(673356, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(674042, "flash", "24", currBaseColor.getHex()));

        for (int i = 0; i < 8; i++)
            events.add(new Event(664784 + i*calculateBeatLength(175)*4, "flash", "24", "f3fffc"));

        events.add(new Event(668556, "flash", "24"));
        events.add(new Event(668899, "flash", "24"));
        events.add(new Event(669242, "flash", "24"));
        events.add(new Event(674042, "flash", "24"));
        events.add(new Event(674384, "flash", "24"));
        events.add(new Event(674727, "flash", "24"));
        events.add(new Event(675070, "flash", "24"));
        events.add(new Event(675413, "flash", "24"));
        events.add(new Event(675756, "flash", "24"));
        events.add(new Event(676099, "flash", "24"));
        events.add(new Event(676442, "flash", "24"));
        events.add(new Event(676784, "flash", "24"));
        events.add(new Event(677127, "flash", "24"));
        events.add(new Event(677470, "flash", "24"));
        events.add(new Event(677813, "flash", "24"));
        events.add(new Event(678156, "flash", "24"));
        events.add(new Event(678499, "flash", "24"));
        events.add(new Event(678842, "flash", "24"));
        events.add(new Event(679184, "flash", "24"));
        events.add(new Event(679527, "flash", "24"));
        events.add(new Event(679870, "flash", "24"));
        events.add(new Event(680213, "flash", "24"));

        for (int i = 0; i < 16; i++) {
            if (i % 2 == 0)
                events.add(new Event(680556 + i * calculateBeatLength(175) / 2, "flash", "24", "f3fffc"));
            else
                events.add(new Event(680556 + i * calculateBeatLength(175) / 2, "flash", "24", currBaseColor.getHex()));
        }

        for (int i = 0; i < 25; i++)
            events.add(new Event(683299 + i * calculateBeatLength(175) / 4, "flash", "24", currBaseColor.getHex()));


        /* Breakup Simulator Lite */
        events.add(new Event(686042, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(687070, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(687756, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(688784, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(689813, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(690499, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(691184, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(691356, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(691527, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(692556, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(693242, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(694270, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(695299, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(695642, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(695984, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(698042, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(698727, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(699756, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(700784, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(701470, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(702156, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(702327, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(702499, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(703527, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(704213, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(705242, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(706270, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(706613, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(706956, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(707299, "turn_on", "40", currBaseColor.getHex()));
        events.add(new Event(707642, "turn_on", "40", currBaseColor.getHex()));

        for (int i = 0; i < 16; i++)
            if (i != 7 && i != 8 && i != 15)
                events.add(new Event(686727 + i*calculateBeatLength(175)*4, "flash", "40", "f3fffc"));

        events.add(new Event(696327, "flash", "24"));
        events.add(new Event(696499, "flash", "24"));
        events.add(new Event(696670, "flash", "24"));
        events.add(new Event(696842, "flash", "24"));
        events.add(new Event(697013, "flash", "all", currBaseColor.getHex()));

        // i'm starting to lose it
        events.add(new Event(697356, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(697442, "flash", "24", "f3fffc"));
        events.add(new Event(697527, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(697613, "flash", "24", "f3fffc"));
        events.add(new Event(697699, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(697784, "flash", "24", "f3fffc"));
        events.add(new Event(697870, "flash", "24", currBaseColor.getHex()));
        events.add(new Event(697956, "flash", "24", "f3fffc"));


        /* Shirtsong + Burst */
        for (int repeats = 0; repeats < 2; repeats++) {
            events.add(new Event(707984 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(708842 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(709184 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(709270 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(709527 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(709870 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(710213 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(710299 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(710384 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(710727 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(711584 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(711927 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(712270 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(712442 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(712784 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(712956 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(713127 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(713470 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(714327 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(714670 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(714756 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(715013 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(715356 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(715699 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(715784 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(715870 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(716213 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(716556 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(716899 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(717070 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(717242 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(717413 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(717756 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(717842 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(717927 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(718270 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
            events.add(new Event(718613 + repeats*calculateBeatLength(175)*32, "turn_on", "24"));
        }

        events.add(new Event(708670, "turn_off"));
        events.add(new Event(711413, "turn_off"));
        events.add(new Event(714156, "turn_off"));
        events.add(new Event(716384, "turn_off"));
        events.add(new Event(719642, "turn_off"));
        events.add(new Event(722384, "turn_off"));
        events.add(new Event(725127, "turn_off"));
        events.add(new Event(727356, "turn_off"));
        events.add(new Event(729927, "turn_off_smooth"));


        /* Gibberish (transition) */
        currBaseColor = new OsbColor("008000");

        for (int i = 0; i < 16; i++)
            events.add(new Event(740899 + i*calculateBeatLength(175), "flash", "20", currBaseColor.getHex()));

        for (int i = 0; i < 4; i++) {
            events.add(new Event(746384 + i*calculateBeatLength(175)*2, "flash", "25", currBaseColor.getHex()));
            events.add(new Event(746556 + i*calculateBeatLength(175)*2, "flash", "25", currBaseColor.getHex()));
            events.add(new Event(746727 + i*calculateBeatLength(175)*2, "flash", "25"));
        }

        events.add(new Event(749127, "flash", "40"));
        events.add(new Event(749642, "flash", "40"));
        events.add(new Event(750156, "flash", "40"));
        events.add(new Event(750499, "flash", "40"));
        events.add(new Event(751013, "flash", "40"));
        events.add(new Event(751527, "flash", "40"));
        events.add(new Event(751870, "flash", "40"));
        events.add(new Event(752042, "flash", "40"));
        events.add(new Event(752213, "flash", "40"));
        events.add(new Event(752384, "flash", "40"));
        events.add(new Event(752556, "flash", "40"));
        events.add(new Event(752727, "flash", "40"));
        events.add(new Event(752899, "flash", "40"));
        events.add(new Event(753070, "flash", "40"));
        events.add(new Event(753242, "flash", "40"));
        events.add(new Event(753413, "flash", "40"));
        events.add(new Event(753584, "flash", "40"));
        events.add(new Event(753756, "flash", "40"));
        events.add(new Event(753927, "flash", "40"));
        events.add(new Event(754099, "flash", "40"));
        events.add(new Event(754270, "flash", "40"));
        events.add(new Event(754442, "flash", "40"));


        /* Gibberish (drop) */
        // just realized generating the next sequential rainbow color forces me to do this brute force
        events.add(new Event(760099, "flash", "30", "f3fff3"));
        events.add(new Event(760442, "flash", "30", "f3fff3"));
        events.add(new Event(760955, "flash", "30", "f3fff3"));
        events.add(new Event(761127, "flash", "30", "f3fff3"));
        events.add(new Event(761470, "flash", "30", "f3fff3"));
        events.add(new Event(761813, "flash", "30", "f3fff3"));
        events.add(new Event(762327, "flash", "30", "f3fff3"));
        events.add(new Event(762499, "flash", "30", "f3fff3"));
        events.add(new Event(762842, "flash", "30", "f3fff3"));
        events.add(new Event(763184, "flash", "30", "f3fff3"));
        events.add(new Event(763699, "flash", "30", "f3fff3"));
        events.add(new Event(763870, "flash", "30", "f3fff3"));
        events.add(new Event(764213, "flash", "30", "f3fff3"));
        events.add(new Event(764556, "flash", "30", "f3fff3"));
        events.add(new Event(764899, "flash", "30", "f3fff3"));
        events.add(new Event(765242, "flash", "30", "f3fff3"));
        events.add(new Event(765584, "flash", "30", "f3fff3"));
        events.add(new Event(765927, "flash", "30", "f3fff3"));
        events.add(new Event(766442, "flash", "30", "f3fff3"));
        events.add(new Event(766613, "flash", "30", "f3fff3"));
        events.add(new Event(766956, "flash", "30", "f3fff3"));
        events.add(new Event(767299, "flash", "30", "f3fff3"));
        events.add(new Event(767813, "flash", "30", "f3fff3"));
        events.add(new Event(767984, "flash", "30", "f3fff3"));
        events.add(new Event(768327, "flash", "30", "f3fff3"));
        events.add(new Event(768670, "flash", "30", "f3fff3"));
        events.add(new Event(769184, "flash", "30", "f3fff3"));
        events.add(new Event(769356, "flash", "30", "f3fff3"));
        events.add(new Event(769699, "flash", "30", "f3fff3"));
        events.add(new Event(770042, "flash", "30", "f3fff3"));
        events.add(new Event(770556, "flash", "30", "f3fff3"));
        events.add(new Event(770727, "flash", "30", "f3fff3"));
        events.add(new Event(771070, "flash", "30", "f3fff3"));
        events.add(new Event(771413, "flash", "30", "f3fff3"));
        events.add(new Event(771756, "flash", "30", "f3fff3"));
        events.add(new Event(772099, "flash", "30", "f3fff3"));
        events.add(new Event(772442, "flash", "30", "f3fff3"));
        events.add(new Event(772784, "flash", "30", "f3fff3"));
        events.add(new Event(773127, "flash", "30", "f3fff3"));
        events.add(new Event(773813, "flash", "30", "f3fff3"));
        events.add(new Event(774156, "flash", "30", "f3fff3"));
        events.add(new Event(774670, "flash", "30", "f3fff3"));
        events.add(new Event(774842, "flash", "30", "f3fff3"));
        events.add(new Event(775184, "flash", "30", "f3fff3"));
        events.add(new Event(775527, "flash", "30", "f3fff3"));
        events.add(new Event(776042, "flash", "30", "f3fff3"));
        events.add(new Event(776213, "flash", "30", "f3fff3"));
        events.add(new Event(776556, "flash", "30", "f3fff3"));
        events.add(new Event(776899, "flash", "30", "f3fff3"));
        events.add(new Event(777413, "flash", "30", "f3fff3"));
        events.add(new Event(777584, "flash", "30", "f3fff3"));
        events.add(new Event(777927, "flash", "30", "f3fff3"));
        events.add(new Event(778270, "flash", "30", "f3fff3"));
        events.add(new Event(778784, "flash", "30", "f3fff3"));
        events.add(new Event(778956, "flash", "30", "f3fff3"));
        events.add(new Event(779299, "flash", "30", "f3fff3"));
        events.add(new Event(779642, "flash", "30", "f3fff3"));
        events.add(new Event(779984, "flash", "30", "f3fff3"));
        events.add(new Event(780327, "flash", "30", "f3fff3"));
        events.add(new Event(780670, "flash", "30", "f3fff3"));
        events.add(new Event(781013, "flash", "30", "f3fff3"));
        events.add(new Event(781356, "flash", "30", "f3fff3"));
        events.add(new Event(781527, "flash", "30", "f3fff3"));
        events.add(new Event(781699, "flash", "30", "f3fff3"));
        events.add(new Event(781870, "flash", "30", "f3fff3"));
        events.add(new Event(760099, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(760442, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(760955, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(761127, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(761470, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(761813, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(762327, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(762499, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(762842, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(763184, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(763699, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(763870, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(764213, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(764556, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(764899, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(765242, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(765584, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(765927, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(766442, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(766613, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(766956, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(767299, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(767813, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(767984, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(768327, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(768670, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(769184, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(769356, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(769699, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(770042, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(770556, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(770727, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(771070, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(771413, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(771756, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(772099, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(772442, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(772784, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(773127, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(773813, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(774156, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(774670, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(774842, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(775184, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(775527, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(776042, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(776213, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(776556, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(776899, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(777413, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(777584, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(777927, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(778270, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(778784, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(778956, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(779299, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(779642, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(779984, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(780327, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(780670, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(781013, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(781356, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(781527, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(781699, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(781870, "turn_on", "40", generateNextRainbowColor()));

        events.add(new Event(773470, "flash", "all"));
        events.add(new Event(782042, "turn_on", "all"));
        events.add(new Event(782727, "flash", "all", "f3fff3"));

        events.add(new Event(783070, "flash", "30", "f3fff3"));
        events.add(new Event(783413, "flash", "30", "f3fff3"));
        events.add(new Event(783756, "flash", "30", "f3fff3"));
        events.add(new Event(784099, "flash", "30", "f3fff3"));
        events.add(new Event(784270, "flash", "30", "f3fff3"));
        events.add(new Event(784442, "flash", "30", "f3fff3"));
        events.add(new Event(784784, "flash", "30", "f3fff3"));
        events.add(new Event(785299, "flash", "30", "f3fff3"));
        events.add(new Event(785813, "flash", "30", "f3fff3"));
        events.add(new Event(786499, "flash", "30", "f3fff3"));
        events.add(new Event(786842, "flash", "30", "f3fff3"));
        events.add(new Event(787184, "flash", "30", "f3fff3"));
        events.add(new Event(787527, "flash", "30", "f3fff3"));
        events.add(new Event(788042, "flash", "30", "f3fff3"));
        events.add(new Event(788556, "flash", "30", "f3fff3"));
        events.add(new Event(789584, "flash", "30", "f3fff3"));
        events.add(new Event(789927, "flash", "30", "f3fff3"));
        events.add(new Event(790270, "flash", "30", "f3fff3"));
        events.add(new Event(790956, "flash", "30", "f3fff3"));
        events.add(new Event(791299, "flash", "30", "f3fff3"));
        events.add(new Event(791984, "flash", "30", "f3fff3"));
        events.add(new Event(792327, "flash", "30", "f3fff3"));
        //events.add(new Event(793013, "flash", "30", "f3fff3"));
        //events.add(new Event(793184, "flash", "30", "f3fff3"));
        //events.add(new Event(793356, "flash", "30", "f3fff3"));
        //events.add(new Event(793527, "flash", "30", "f3fff3"));
        //events.add(new Event(793699, "flash", "30", "f3fff3"));
        //events.add(new Event(793870, "flash", "30", "f3fff3"));
        events.add(new Event(794042, "flash", "30", "f3fff3"));
        events.add(new Event(794727, "flash", "30", "f3fff3"));
        events.add(new Event(795070, "flash", "30", "f3fff3"));
        events.add(new Event(795413, "flash", "30", "f3fff3"));
        events.add(new Event(795756, "flash", "30", "f3fff3"));
        events.add(new Event(796442, "flash", "30", "f3fff3"));
        events.add(new Event(796784, "flash", "30", "f3fff3"));
        events.add(new Event(797470, "flash", "30", "f3fff3"));
        events.add(new Event(798156, "flash", "30", "f3fff3"));
        events.add(new Event(798499, "flash", "30", "f3fff3"));
        events.add(new Event(799184, "flash", "30", "f3fff3"));
        events.add(new Event(799527, "flash", "30", "f3fff3"));
        events.add(new Event(800556, "flash", "30", "f3fff3"));
        events.add(new Event(800899, "flash", "30", "f3fff3"));
        events.add(new Event(801070, "flash", "30", "f3fff3"));
        events.add(new Event(801242, "flash", "30", "f3fff3"));
        events.add(new Event(801927, "flash", "30", "f3fff3"));
        events.add(new Event(802270, "flash", "30", "f3fff3"));
        events.add(new Event(802613, "flash", "30", "f3fff3"));
        events.add(new Event(802956, "flash", "30", "f3fff3"));
        events.add(new Event(803642, "flash", "30", "f3fff3"));
        events.add(new Event(803984, "flash", "30", "f3fff3"));
        events.add(new Event(804670, "flash", "30", "f3fff3"));
        events.add(new Event(805356, "flash", "30", "f3fff3"));
        events.add(new Event(805699, "flash", "30", "f3fff3"));
        events.add(new Event(806042, "flash", "30", "f3fff3"));
        events.add(new Event(806384, "flash", "30", "f3fff3"));
        events.add(new Event(783070, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(783413, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(783756, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(784099, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(784270, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(784442, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(784784, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(785299, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(785813, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(786499, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(786842, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(787184, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(787527, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(788042, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(788556, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(789584, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(789927, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(790270, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(790956, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(791299, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(791984, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(792327, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(793013, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(793184, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(793356, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(793527, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(793699, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(793870, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(794042, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(794727, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(795070, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(795413, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(795756, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(796442, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(796784, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(797470, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(798156, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(798499, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(799184, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(799527, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(800556, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(800899, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(801070, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(801242, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(801927, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(802270, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(802613, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(802956, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(803642, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(803984, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(804670, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(805356, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(805699, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(806042, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(806384, "turn_on", "40", generateNextRainbowColor()));

        events.add(new Event(806727, "turn_on", "36"));
        events.add(new Event(806899, "turn_on", "36"));
        events.add(new Event(807070, "turn_on", "36"));
        events.add(new Event(807242, "turn_on", "36"));
        events.add(new Event(807413, "turn_on", "36"));
        events.add(new Event(807584, "turn_on", "36"));
        events.add(new Event(807755, "turn_on", "36"));
        events.add(new Event(807927, "turn_on", "36"));
        events.add(new Event(808098, "turn_on", "36"));
        events.add(new Event(808269, "turn_on", "36"));
        events.add(new Event(808442, "turn_on", "36"));
        events.add(new Event(808613, "turn_on", "36"));
        events.add(new Event(808784, "turn_on", "36"));
        events.add(new Event(809127, "turn_on", "36"));

        // AHHHHHHHH
        events.add(new Event(809470, "flash", "30", "f3fff3"));
        events.add(new Event(809813, "flash", "30", "f3fff3"));
        events.add(new Event(810327, "flash", "30", "f3fff3"));
        events.add(new Event(810499, "flash", "30", "f3fff3"));
        events.add(new Event(810842, "flash", "30", "f3fff3"));
        events.add(new Event(811184, "flash", "30", "f3fff3"));
        events.add(new Event(811699, "flash", "30", "f3fff3"));
        events.add(new Event(811870, "flash", "30", "f3fff3"));
        events.add(new Event(812213, "flash", "30", "f3fff3"));
        events.add(new Event(812556, "flash", "30", "f3fff3"));
        events.add(new Event(813070, "flash", "30", "f3fff3"));
        events.add(new Event(813242, "flash", "30", "f3fff3"));
        events.add(new Event(813584, "flash", "30", "f3fff3"));
        events.add(new Event(813927, "flash", "30", "f3fff3"));
        events.add(new Event(814442, "flash", "30", "f3fff3"));
        events.add(new Event(814613, "flash", "30", "f3fff3"));
        events.add(new Event(814956, "flash", "30", "f3fff3"));
        events.add(new Event(815299, "flash", "30", "f3fff3"));
        events.add(new Event(815813, "flash", "30", "f3fff3"));
        events.add(new Event(815984, "flash", "30", "f3fff3"));
        events.add(new Event(816327, "flash", "30", "f3fff3"));
        events.add(new Event(816670, "flash", "30", "f3fff3"));
        events.add(new Event(817184, "flash", "30", "f3fff3"));
        events.add(new Event(817356, "flash", "30", "f3fff3"));
        events.add(new Event(817699, "flash", "30", "f3fff3"));
        events.add(new Event(818042, "flash", "30", "f3fff3"));
        events.add(new Event(818556, "flash", "30", "f3fff3"));
        events.add(new Event(818727, "flash", "30", "f3fff3"));
        events.add(new Event(819070, "flash", "30", "f3fff3"));
        events.add(new Event(819413, "flash", "30", "f3fff3"));
        events.add(new Event(819584, "flash", "30", "f3fff3"));
        events.add(new Event(819927, "flash", "30", "f3fff3"));
        events.add(new Event(820099, "flash", "30", "f3fff3"));
        events.add(new Event(820442, "flash", "30", "f3fff3"));
        events.add(new Event(820784, "flash", "30", "f3fff3"));
        events.add(new Event(821299, "flash", "30", "f3fff3"));
        events.add(new Event(821470, "flash", "30", "f3fff3"));
        events.add(new Event(821813, "flash", "30", "f3fff3"));
        events.add(new Event(822156, "flash", "30", "f3fff3"));
        events.add(new Event(822670, "flash", "30", "f3fff3"));
        events.add(new Event(822842, "flash", "30", "f3fff3"));
        events.add(new Event(823184, "flash", "30", "f3fff3"));
        events.add(new Event(823527, "flash", "30", "f3fff3"));
        events.add(new Event(824042, "flash", "30", "f3fff3"));
        events.add(new Event(824213, "flash", "30", "f3fff3"));
        events.add(new Event(824556, "flash", "30", "f3fff3"));
        events.add(new Event(824899, "flash", "30", "f3fff3"));
        events.add(new Event(825413, "flash", "30", "f3fff3"));
        events.add(new Event(825584, "flash", "30", "f3fff3"));
        events.add(new Event(825927, "flash", "30", "f3fff3"));
        events.add(new Event(826270, "flash", "30", "f3fff3"));
        events.add(new Event(826784, "flash", "30", "f3fff3"));
        events.add(new Event(826956, "flash", "30", "f3fff3"));
        events.add(new Event(827299, "flash", "30", "f3fff3"));
        events.add(new Event(827642, "flash", "30", "f3fff3"));
        events.add(new Event(828156, "flash", "30", "f3fff3"));
        events.add(new Event(828327, "flash", "30", "f3fff3"));
        events.add(new Event(828670, "flash", "30", "f3fff3"));
        events.add(new Event(829013, "flash", "30", "f3fff3"));
        events.add(new Event(829527, "flash", "30", "f3fff3"));
        events.add(new Event(829699, "flash", "30", "f3fff3"));
        events.add(new Event(830042, "flash", "30", "f3fff3"));
        events.add(new Event(830384, "flash", "30", "f3fff3"));
        events.add(new Event(830556, "flash", "30", "f3fff3"));
        events.add(new Event(830727, "flash", "30", "f3fff3"));
        events.add(new Event(830899, "flash", "30", "f3fff3"));
        events.add(new Event(831070, "flash", "30", "f3fff3"));
        events.add(new Event(832784, "flash", "30", "f3fff3"));
        events.add(new Event(833127, "flash", "30", "f3fff3"));
        events.add(new Event(833642, "flash", "30", "f3fff3"));
        events.add(new Event(833813, "flash", "30", "f3fff3"));
        events.add(new Event(834156, "flash", "30", "f3fff3"));
        events.add(new Event(834499, "flash", "30", "f3fff3"));
        events.add(new Event(835013, "flash", "30", "f3fff3"));
        events.add(new Event(835184, "flash", "30", "f3fff3"));
        events.add(new Event(835527, "flash", "30", "f3fff3"));
        events.add(new Event(835870, "flash", "30", "f3fff3"));
        events.add(new Event(836384, "flash", "30", "f3fff3"));
        events.add(new Event(836556, "flash", "30", "f3fff3"));
        events.add(new Event(836899, "flash", "30", "f3fff3"));
        events.add(new Event(837242, "flash", "30", "f3fff3"));
        events.add(new Event(837756, "flash", "30", "f3fff3"));
        events.add(new Event(837927, "flash", "30", "f3fff3"));
        events.add(new Event(838270, "flash", "30", "f3fff3"));
        events.add(new Event(838613, "flash", "30", "f3fff3"));
        events.add(new Event(839127, "flash", "30", "f3fff3"));
        events.add(new Event(839299, "flash", "30", "f3fff3"));
        events.add(new Event(839642, "flash", "30", "f3fff3"));
        events.add(new Event(839984, "flash", "30", "f3fff3"));
        events.add(new Event(840499, "flash", "30", "f3fff3"));
        events.add(new Event(840670, "flash", "30", "f3fff3"));
        events.add(new Event(841013, "flash", "30", "f3fff3"));
        events.add(new Event(841356, "flash", "30", "f3fff3"));
        events.add(new Event(841527, "flash", "30", "f3fff3"));
        events.add(new Event(841870, "flash", "30", "f3fff3"));
        events.add(new Event(842042, "flash", "30", "f3fff3"));
        events.add(new Event(809470, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(809813, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(810327, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(810499, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(810842, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(811184, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(811699, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(811870, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(812213, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(812556, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(813070, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(813242, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(813584, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(813927, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(814442, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(814613, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(814956, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(815299, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(815813, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(815984, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(816327, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(816670, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(817184, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(817356, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(817699, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(818042, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(818556, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(818727, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(819070, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(819413, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(819584, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(819927, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(820099, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(820442, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(820784, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(821299, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(821470, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(821813, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(822156, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(822670, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(822842, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(823184, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(823527, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(824042, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(824213, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(824556, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(824899, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(825413, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(825584, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(825927, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(826270, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(826784, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(826956, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(827299, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(827642, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(828156, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(828327, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(828670, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(829013, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(829527, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(829699, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(830042, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(830384, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(830556, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(830727, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(830899, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(831070, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(832784, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(833127, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(833642, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(833813, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(834156, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(834499, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(835013, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(835184, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(835527, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(835870, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(836384, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(836556, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(836899, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(837242, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(837756, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(837927, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(838270, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(838613, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(839127, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(839299, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(839642, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(839984, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(840499, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(840670, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(841013, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(841356, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(841527, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(841870, "turn_on", "40", generateNextRainbowColor()));
        events.add(new Event(842042, "turn_on", "40", generateNextRainbowColor()));

        events.add(new Event(831413, "flash", "40"));
        events.add(new Event(831584, "flash", "40"));
        events.add(new Event(831670, "flash", "40"));
        events.add(new Event(831756, "flash", "40"));
        events.add(new Event(831927, "flash", "40"));
        events.add(new Event(832099, "flash", "40"));
        events.add(new Event(832270, "flash", "40"));
        events.add(new Event(832442, "flash", "40"));
        events.add(new Event(832613, "flash", "40"));
        events.add(new Event(842384, "flash", "40"));
        events.add(new Event(842899, "flash", "40"));
        events.add(new Event(843413, "flash", "40"));
        events.add(new Event(843756, "flash", "40"));
        events.add(new Event(844270, "flash", "40"));
        events.add(new Event(844784, "flash", "40"));
        events.add(new Event(845127, "flash", "40"));
        events.add(new Event(845642, "flash", "40"));
        events.add(new Event(846156, "flash", "40"));
        events.add(new Event(846499, "flash", "40"));
        events.add(new Event(847013, "flash", "40"));
        events.add(new Event(847527, "flash", "40"));
        events.add(new Event(847870, "flash", "40"));
        events.add(new Event(848213, "flash", "40"));
        events.add(new Event(848556, "flash", "40"));
        events.add(new Event(848899, "flash", "40"));
        events.add(new Event(849242, "flash", "40"));
        events.add(new Event(849584, "flash", "40"));
        events.add(new Event(849927, "flash", "40"));
        events.add(new Event(850270, "flash", "40"));
        events.add(new Event(850613, "flash", "40"));
        events.add(new Event(850956, "flash", "40"));
        events.add(new Event(851299, "flash", "40"));
        events.add(new Event(851642, "flash", "40"));
        events.add(new Event(851984, "flash", "40"));
        events.add(new Event(852336, "flash", "40"));
        events.add(new Event(852697, "flash", "40"));
        events.add(new Event(853065, "flash", "40"));

        events.add(new Event(842556, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(842727, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(843070, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(843242, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(843584, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(843927, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(844099, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(844442, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(844613, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(844956, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(845299, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(845470, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(845813, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(845984, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(846327, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(846670, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(846842, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(847184, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(847356, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(847699, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(848042, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(848384, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(848727, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(849070, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(849413, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(849756, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(850099, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(850442, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(850784, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(851127, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(851470, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(851813, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(852160, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(852516, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(852881, "flash", "40", generateNextRainbowColor()));
        events.add(new Event(853259, "flash", "40", generateNextRainbowColor()));

        // oh my god this is done
        // i did all these events in one day


        // ==================== [BASE COLOR CHANGES] ====================

        events.add(new Event(214532, "change_base_color", "008000"));
        events.add(new Event(386042, "change_base_color", "c75656"));
        events.add(new Event(570842, "change_base_color", "127ced"));
        events.add(new Event(740556, "change_base_color", "008000"));
        currBaseColor = new OsbColor("506cfa"); // reset for sprite generation

        Collections.sort(events);


        // --------------------
        // [INITIAL GENERATION]
        // --------------------

        boolean initialGeneration = true;
        int initialGenerationCount = 0;

        while (initialGeneration) {

            /* Generate new sprite */
            if (nextContestantIndices.isEmpty())
                nextContestantIndices = generateRandomArray(Contestant.length(), UNIQUE_GEN_COUNT);
            nextContestant = Contestant.toEnum((nextContestantIndices.getFirst()));
            nextContestantIndices.removeFirst();
            ContestantSprite newSprite = generateContestantSprite(nextContestant, START_TIME, currBaseColor);

            /* Find the tallest Y coord that the next contestant will sit on top of */
            genCoordY = MAX_Y;
            if (!sprites.isEmpty()) {

                /* Assume we place the new sprite's left edge on the X coord */
                double newSpriteLeftX = genCoordX;
                double newSpriteRightX = genCoordX + newSprite.getSpriteWidth();

                /* For each existing sprite, check if it "overlaps" with the new sprite, and get its Y coord */
                for (ContestantSprite sprite : sprites) {
                    double existingSpriteLeftX = sprite.getSpriteXRange()[0];
                    double existingSpriteRightX = sprite.getSpriteXRange()[1];
                    if (existingSpriteRightX > newSpriteLeftX + PACKING_LENIENCY
                        && existingSpriteLeftX < newSpriteRightX - PACKING_LENIENCY)
                         genCoordY = Math.min(genCoordY, sprite.getSpriteYTop() - CONTESTANT_PADDING);
                }
            }

            /* Move new sprite to generation coords (adjust for BOTTOM-LEFT) */
            newSprite.initializePosition(
                    genCoordX + newSprite.getSpriteWidth()/2,
                    genCoordY - newSprite.getSpriteHeight()/2
            );
            sprites.add(newSprite);
            System.out.print("Generated " + ++initialGenerationCount + " initial sprites.\r");

            /* Increment X coord to generate future sprites */
            genCoordX += newSprite.getSpriteWidth() + CONTESTANT_PADDING;
            if (genCoordX > MAX_X) {
                genCoordX = MIN_X;

                /* Stop if tallest Y coord exceeds canvas range */
                for (ContestantSprite sprite : sprites) {
                    if (sprite.getSpriteYTop() < MIN_Y) {
                        initialGeneration = false;
                        break;
                    }
                }
            }

        } // [END WHILE]
        System.out.println();


        // -------------------
        // [ACTIVE GENERATION]
        // -------------------

        ContestantSprite nextSprite = null;
        int activeGenerationCount = 0;

        while (currTime <= END_TIME) {

            System.out.print("Generated " + activeGenerationCount + " active sprites. " +
                    "Handling " + events.size() + " remaining events. [" + currTime + " ms]\r");

            // --------------------
            // [ADDING NEW SPRITES]
            // --------------------

            // Generating a new sprite and checking if it fits in every loop iteration would
            // make things much slower, so this if-else accommodates that potential issue.

            /* If there isn't a next sprite to append, generate next sprite */
            if (nextSprite == null) {

                /* Generate next sprite */
                if (nextContestantIndices.isEmpty())
                    nextContestantIndices = generateRandomArray(Contestant.length(), UNIQUE_GEN_COUNT);
                nextContestant = Contestant.toEnum((nextContestantIndices.getFirst()));
                //nextContestant = generateContestant(nextContestant);
                nextContestantIndices.removeFirst();
                nextSprite = generateContestantSprite(nextContestant, START_TIME, currBaseColor);

                /* Find the tallest Y coord that the next contestant will sit on top of */
                genCoordY = MAX_Y;

                /* Assume we place the next sprite's left edge on the X coord */
                double nextSpriteLeftX = genCoordX;
                double nextSpriteRightX = genCoordX + nextSprite.getSpriteWidth();

                /* For each existing sprite, check if it "overlaps" with the next sprite, and get its Y coord */
                for (ContestantSprite sprite : sprites) {
                    double existingSpriteLeftX = sprite.getSpriteXRange()[0];
                    double existingSpriteRightX = sprite.getSpriteXRange()[1];
                    if (existingSpriteRightX > nextSpriteLeftX + PACKING_LENIENCY
                            && existingSpriteLeftX < nextSpriteRightX - PACKING_LENIENCY)
                        genCoordY = Math.min(genCoordY, sprite.getSpriteYTop() - CONTESTANT_PADDING);
                }
            }

            /* Append next sprite if there is enough space */
            /* Otherwise, increment Y coord based on velocity (see INCREMENTING TIME) */
            if (genCoordY > MIN_Y) {
                ContestantSprite appendedSprite = new ContestantSprite(
                        nextSprite,
                        currTime,
                        genCoordX + nextSprite.getSpriteWidth() / 2,
                        genCoordY - nextSprite.getSpriteHeight() / 2
                );
                sprites.add(appendedSprite);
                activeGenerationCount++;
                nextSprite = null;

                /* Increment X coord to generate future sprites */
                genCoordX += appendedSprite.getSpriteWidth() + CONTESTANT_PADDING;
                if (genCoordX > MAX_X)
                    genCoordX = MIN_X;
            }


            // --------------------------------
            // [REMOVING OUT-OF-BOUNDS SPRITES]
            // --------------------------------

            Iterator<ContestantSprite> oobIterator = sprites.iterator();
            while (oobIterator.hasNext()) {
                ContestantSprite sprite = oobIterator.next();
                if (sprite.getCurrY() > MAX_Y) {
                    sprite.writeAll();
                    file.addObject(sprite);
                    oobIterator.remove();
                }
            }


            // -------------------
            // [TRIGGERING EVENTS]
            // -------------------

            /* Check if there are remaining events to trigger */
            while (!events.isEmpty() && currTime >= events.getFirst().time) {

                Event nextEvent = events.getFirst();

                /* Turn on N sprites */
                // [<time>, "turn_on", <count>, <color>]
                if (nextEvent.event.equals("turn_on")) {
                    for (ContestantSprite sprite : sprites) // turn off all currently on sprites
                        if (sprite.getState() == ON)
                            sprite.turnOff();

                    if (nextEvent.value1.equals("all")) {
                        for (ContestantSprite sprite : sprites) {
                            if (nextEvent.value2 == null)       // use contestant color
                                sprite.turnOn();
                            else                                // use specified color
                                sprite.turnOn(new OsbColor(nextEvent.value2));
                        }
                    }

                    else {
                        ArrayList<Integer> indices = generateRandomArray(sprites.size(), Math.min(Integer.parseInt(nextEvent.value1), sprites.size()));
                        for (Integer index : indices)
                            if (nextEvent.value2 == null)       // use contestant color
                                sprites.get(index).turnOn();
                            else                                // use specified color
                                sprites.get(index).turnOn(new OsbColor(nextEvent.value2));
                    }
                }

                /* Turn off all sprites */
                // [<time>, "turn_off", null, null]
                else if (nextEvent.event.equals("turn_off")) {
                    for (ContestantSprite sprite : sprites)
                        if (sprite.getState() != OFF)
                            sprite.turnOff();
                }

                /* Turn off all sprites smoothly (i.e. with flash) */
                // [<time>, "turn_off_smooth", null, null]
                else if (nextEvent.event.equals("turn_off_smooth")) {
                    for (ContestantSprite sprite : sprites)
                        if (sprite.getState() == ON)
                            sprite.flash(sprite.getCurrColor());
                }

                /* Flash N sprites */
                // [<time>, "flash", <count>, <color>]
                else if (nextEvent.event.equals("flash")) {
                    for (ContestantSprite sprite : sprites) // turn off all currently on sprites
                        if (sprite.getState() == ON)
                            sprite.turnOff();

                    if (nextEvent.value1.equals("all")) {
                        for (ContestantSprite sprite : sprites) {
                            if (nextEvent.value2 == null)       // use contestant color
                                sprite.flash();
                            else                                // use specified color
                                sprite.flash(new OsbColor(nextEvent.value2));
                        }
                    }

                    else {
                        ArrayList<Integer> indices = generateRandomArray(sprites.size(), Math.min(Integer.parseInt(nextEvent.value1), sprites.size()));
                        for (Integer index : indices)
                            if (nextEvent.value2 == null)       // use contestant color
                                sprites.get(index).flash();
                            else                                // use specified color
                                sprites.get(index).flash(new OsbColor(nextEvent.value2));
                    }
                }

                /* Change base color of all sprites */
                // [<time>, "change_base_color", <color>, null]
                else if (nextEvent.event.equals("change_base_color")) {
                    currBaseColor = new OsbColor(nextEvent.value1);
                    if (nextSprite != null)                 // edge case for pending sprites
                        nextSprite.changeBaseColor(currBaseColor);
                    for (ContestantSprite sprite : sprites)
                        sprite.changeBaseColor(currBaseColor);
                }

                events.removeFirst();

            } // [END IF]


            // -------------------
            // [INCREMENTING TIME]
            // -------------------

            for (ContestantSprite sprite : sprites)
                sprite.addLengthAndUpdate(TIME_RESOLUTION);
            genCoordY += CONTESTANT_VELOCITY * (TIME_RESOLUTION / 1000);
            currTime += TIME_RESOLUTION;


        } // [END WHILE]
        System.out.println();


        // --------
        // [EXPORT]
        // --------

        for (ContestantSprite sprite : sprites) {
            sprite.writeAll();
            file.addObject(sprite);
        }
        file.exportBinary(BIN_DIRECTORY + BIN_NAME);
        file.export(OSB_DIRECTORY + OSB_NAME);


    } // [END MAIN]


}
