package projects.tetration;

import osbjava.commands.FadeCommand;
import osbjava.commands.OsbCommand;
import osbjava.objects.*;
import static osbjava.types.OsbConstants.*;
import static osbjava.types.OsbCommandType.*;
import static osbjava.types.OsbEasing.*;
import java.util.*;


@SuppressWarnings("unused")
public class TwoThree {


    // ============================================================
    // [GLOBAL VARIABLES]


    //static final String   OSB_FOLDER        = "tetration (a BFDITPOT medley)";
    static final String   OSB_DIRECTORY     = ".\\src\\projects\\tetration\\beatmap\\";
    static final String   OSB_NAME          = "nate bait - tetration (a BFDITPOT medley) (Nate Bait).osb";
    static final String   BIN_DIRECTORY     = ".\\src\\projects\\tetration\\bin\\";
    static final String   BIN_NAME          = "two_three.bin";

    static final double   START_TIME        = 17398;
    static final double   END_TIME          = 853657;
    static final double   GLITCH_START_TIME = 564842;
    static final double   GLITCH_END_TIME   = 567842;
    static final double   GLITCH_FADE_OUT   = 800;
    static final double   TIME_RESOLUTION   = 10;

    static final double   IMG_SCALE         = 0.4445;
    static final double[] TWO_DIMENSIONS    = {1000, 800};
    static final double[] THREE_DIMENSIONS  = {400, 400};

    static final double   PULSE_LENGTH      = 600;
    static final double   PULSE_SCALE       = 1.04;
    static final double   FLASH_LENGTH      = 1000;
    static final double   FLASH_OPACITY     = 0.3;
    static final double   GLITCH_INTENSITY  = 10;
    static final Random   RANDOM            = new Random(System.currentTimeMillis());


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


    /* Calculate beat length of a tempo */
    public static double calculateBeatLength(double tempo) {
        return (60.0 / tempo) * 1000;
    }


    // ============================================================
    // [MAIN METHOD]


    public static void main(String[] args) {

        OsbFile file = new OsbFile();

        /* Two and Three sprites */
        DynamicSprite two         = new DynamicSprite("storyboard\\numbers_two.png", START_TIME);
        DynamicSprite two_flash   = new DynamicSprite("storyboard\\numbers_two_white.png", START_TIME);
        DynamicSprite three       = new DynamicSprite("storyboard\\numbers_three.png", START_TIME);
        DynamicSprite three_flash = new DynamicSprite("storyboard\\numbers_three_white.png", START_TIME);
        ArrayList<Event> events   = new ArrayList<>();

        /* Initialize scale and opacity */
        two.setScale(IMG_SCALE);
        two_flash.setScale(IMG_SCALE);
        two_flash.setOpacity(0);
        three.setScale(IMG_SCALE);
        three_flash.setScale(IMG_SCALE);
        three_flash.setOpacity(0);

        /* Initialize positions */
        double threeX = CENTER_X - TWO_DIMENSIONS[0]*IMG_SCALE/2 + THREE_DIMENSIONS[0]*IMG_SCALE/2;
        double threeY = CENTER_Y - TWO_DIMENSIONS[1]*IMG_SCALE/2 + THREE_DIMENSIONS[1]*IMG_SCALE/2;
        three.setPosition(threeX, threeY);
        three_flash.setPosition(threeX, threeY);

        /* Variables for keeping track of time */
        double currTime = START_TIME;
        //double refTime  = START_TIME;


        // --------
        // [EVENTS]
        // --------


        // ==================== [MOVEMENT I] ====================


        for (int i = 0; i < 31*4; i++)
            events.add(new Event(47714 + i*calculateBeatLength(132), "two_pulse"));
        for (int i = 0; i < 7*4; i++)
            events.add(new Event(105896 + i*calculateBeatLength(132), "two_pulse"));
        events.add(new Event(118623, "two_pulse"));
        events.add(new Event(119305, "two_pulse"));
        events.add(new Event(119760, "two_pulse"));
        events.add(new Event(119987, "two_pulse"));
        for (int i = 0; i < 8*4; i++)
            events.add(new Event(120442 + i*calculateBeatLength(132), "two_pulse"));
        for (int i = 0; i < 4; i++) {
            events.add(new Event(142260 + i*calculateBeatLength(132)*16, "two_pulse"));
            events.add(new Event(142942 + i*calculateBeatLength(132)*16, "two_pulse"));
            events.add(new Event(144078 + i*calculateBeatLength(132)*16, "two_pulse"));
            events.add(new Event(144760 + i*calculateBeatLength(132)*16, "two_pulse"));
            events.add(new Event(145896 + i*calculateBeatLength(132)*16, "two_pulse"));
            events.add(new Event(146578 + i*calculateBeatLength(132)*16, "two_pulse"));
            events.add(new Event(147714 + i*calculateBeatLength(132)*16, "two_pulse"));
            events.add(new Event(148396 + i*calculateBeatLength(132)*16, "two_pulse"));
            events.add(new Event(148851 + i*calculateBeatLength(132)*16, "two_pulse"));
        }
        events.add(new Event(178623, "two_pulse"));
        events.add(new Event(179873, "two_pulse"));
        events.add(new Event(180214, "two_pulse"));
        events.add(new Event(180555, "two_pulse"));
        events.add(new Event(180896, "two_pulse"));
        events.add(new Event(182260, "two_pulse"));
        events.add(new Event(183510, "two_pulse"));
        events.add(new Event(183851, "two_pulse"));
        events.add(new Event(184192, "two_pulse"));
        events.add(new Event(184532, "two_pulse"));
        events.add(new Event(185442, "two_pulse"));
        events.add(new Event(185896, "two_pulse"));
        events.add(new Event(187146, "two_pulse"));
        events.add(new Event(187487, "two_pulse"));
        events.add(new Event(187828, "two_pulse"));
        events.add(new Event(188169, "two_pulse"));
        events.add(new Event(189532, "two_pulse"));
        events.add(new Event(190782, "two_pulse"));
        events.add(new Event(191123, "two_pulse"));
        events.add(new Event(191351, "two_pulse"));
        events.add(new Event(191805, "two_pulse"));
        events.add(new Event(192260, "two_pulse"));
        events.add(new Event(192714, "two_pulse"));
        events.add(new Event(193169, "two_pulse"));
        events.add(new Event(193623, "two_pulse"));
        events.add(new Event(194078, "two_pulse"));
        events.add(new Event(194419, "two_pulse"));
        events.add(new Event(194760, "two_pulse"));
        events.add(new Event(195101, "two_pulse"));
        events.add(new Event(195442, "two_pulse"));
        events.add(new Event(196805, "two_pulse"));
        events.add(new Event(198055, "two_pulse"));
        events.add(new Event(198396, "two_pulse"));
        events.add(new Event(198737, "two_pulse"));
        events.add(new Event(199078, "two_pulse"));
        events.add(new Event(199987, "two_pulse"));
        events.add(new Event(200442, "two_pulse"));
        events.add(new Event(201692, "two_pulse"));
        events.add(new Event(202032, "two_pulse"));
        events.add(new Event(202373, "two_pulse"));
        events.add(new Event(202714, "two_pulse"));
        events.add(new Event(204078, "two_pulse"));
        events.add(new Event(205328, "two_pulse"));
        events.add(new Event(205669, "two_pulse"));
        events.add(new Event(205896, "two_pulse"));
        events.add(new Event(206351, "two_pulse"));
        events.add(new Event(206805, "two_pulse"));
        events.add(new Event(207260, "two_pulse"));
        events.add(new Event(207942, "two_pulse"));
        events.add(new Event(208282, "two_pulse"));
        events.add(new Event(208623, "two_pulse"));
        events.add(new Event(208964, "two_pulse"));
        events.add(new Event(209305, "two_pulse"));

        for (int i = 0; i < 27*2; i++)
            events.add(new Event(55442 + i*calculateBeatLength(132)*2, "three_pulse"));
        for (int i = 0; i < 16*2; i++)
            events.add(new Event(106351 + i*calculateBeatLength(132)*2, "three_pulse"));
        for (int i = 0; i < 16*2; i++)
            events.add(new Event(142714 + i*calculateBeatLength(132)*2, "three_pulse"));
        for (int i = 0; i < 16; i++)
            events.add(new Event(179532 + i*calculateBeatLength(132)*4, "three_pulse"));
        events.add(new Event(207260, "three_pulse"));
        events.add(new Event(207942, "three_pulse"));
        events.add(new Event(208282, "three_pulse"));
        events.add(new Event(208623, "three_pulse"));
        events.add(new Event(208964, "three_pulse"));
        events.add(new Event(209305, "three_pulse"));

        events.add(new Event(105896, "two_flash"));
        events.add(new Event(178623, "two_flash"));
        events.add(new Event(193169, "two_flash"));
        events.add(new Event(193623, "two_flash"));
        events.add(new Event(194078, "two_flash"));
        events.add(new Event(206805, "two_flash"));
        events.add(new Event(207260, "two_flash"));
        events.add(new Event(207942, "two_flash"));
        events.add(new Event(208623, "two_flash"));
        events.add(new Event(209305, "two_flash"));
        events.add(new Event(105896, "three_flash"));
        events.add(new Event(178623, "three_flash"));
        events.add(new Event(193169, "three_flash"));
        events.add(new Event(193623, "three_flash"));
        events.add(new Event(194078, "three_flash"));
        events.add(new Event(206805, "three_flash"));
        events.add(new Event(207260, "three_flash"));
        events.add(new Event(208282, "three_flash"));
        events.add(new Event(208964, "three_flash"));
        events.add(new Event(209305, "three_flash"));


        // ==================== [MOVEMENT II] ====================


        for (int i = 0; i < 16; i++) {
            events.add(new Event(214987 + i*calculateBeatLength(132)*4, "two_pulse"));
            events.add(new Event(215366 + i*calculateBeatLength(132)*4, "two_pulse"));
            events.add(new Event(215669 + i*calculateBeatLength(132)*4, "two_pulse"));
            events.add(new Event(216048 + i*calculateBeatLength(132)*4, "two_pulse"));
            events.add(new Event(216351 + i*calculateBeatLength(132)*4, "three_pulse"));
        }
        for (int i = 0; i < 2; i++) {
            events.add(new Event(244078 + i*calculateBeatLength(132)*16, "two_pulse"));
            events.add(new Event(244760 + i*calculateBeatLength(132)*16, "two_pulse"));
            events.add(new Event(247714 + i*calculateBeatLength(132)*16, "two_pulse"));
            events.add(new Event(248396 + i*calculateBeatLength(132)*16, "two_pulse"));
            events.add(new Event(248851 + i*calculateBeatLength(132)*16, "two_pulse"));
            events.add(new Event(244078 + i*calculateBeatLength(132)*16, "three_pulse"));
            events.add(new Event(244760 + i*calculateBeatLength(132)*16, "three_pulse"));
            events.add(new Event(247714 + i*calculateBeatLength(132)*16, "three_pulse"));
            events.add(new Event(248396 + i*calculateBeatLength(132)*16, "three_pulse"));
            events.add(new Event(248851 + i*calculateBeatLength(132)*16, "three_pulse"));
        }

        for (int i = 0; i < 12*2; i++)
            events.add(new Event(265896 + i*calculateBeatLength(132)*2, "two_pulse"));
        for (int i = 0; i < 4*2; i++)
            events.add(new Event(266351 + i*calculateBeatLength(132)*2, "three_pulse"));
        events.add(new Event(273623, "three_pulse"));
        events.add(new Event(274532, "three_pulse"));
        events.add(new Event(275442, "three_pulse"));
        events.add(new Event(276351, "three_pulse"));
        events.add(new Event(277260, "three_pulse"));
        events.add(new Event(278169, "three_pulse"));
        events.add(new Event(278987, "three_pulse"));
        events.add(new Event(279987, "three_pulse"));
        events.add(new Event(280745, "three_pulse"));
        events.add(new Event(281805, "three_pulse"));
        events.add(new Event(282623, "three_pulse"));
        events.add(new Event(283623, "three_pulse"));
        events.add(new Event(284532, "three_pulse"));
        events.add(new Event(285247, "three_pulse"));
        events.add(new Event(285636, "three_pulse"));
        events.add(new Event(286351, "three_pulse"));
        events.add(new Event(287108, "three_pulse"));

        for (int i = 0; i < 8*4; i++)
            if (i != 27 && i != 28)
                events.add(new Event(294987 + i*calculateBeatLength(132), "two_pulse"));
        for (int i = 0; i < 8*4; i++)
            if (i != 27 && i != 28)
                events.add(new Event(309532 + i*calculateBeatLength(132), "two_pulse"));
        events.add(new Event(307487, "two_pulse"));
        events.add(new Event(307828, "two_pulse"));
        events.add(new Event(322032, "two_pulse"));
        events.add(new Event(322373, "two_pulse"));
        for (int i = 0; i < 16*2; i++)
            events.add(new Event(295442 + i*calculateBeatLength(132)*2, "three_pulse"));

        for (int i = 0; i < 7*4; i++)
            if (i != 23 && i != 24)
                events.add(new Event(325896 + i*calculateBeatLength(132), "two_pulse"));
        for (int i = 0; i < 6*4; i++)
            if (i != 0)
                events.add(new Event(338623 + i*calculateBeatLength(132), "two_pulse"));
        events.add(new Event(336578, "two_pulse"));
        events.add(new Event(336919, "two_pulse"));
        for (int i = 0; i < 13*2; i++)
            events.add(new Event(326351 + i*calculateBeatLength(132)*2, "three_pulse"));

        for (int i = 0; i < 14*4; i++)
            if (i != 21 && i != 22 && i != 53 && i != 54)
                events.add(new Event(353169 + i*calculateBeatLength(132), "two_pulse"));
        for (int i = 0; i < 14*2; i++)
            events.add(new Event(353623 + i*calculateBeatLength(132)*2, "three_pulse"));
        events.add(new Event(362942, "two_pulse"));
        events.add(new Event(363282, "two_pulse"));
        events.add(new Event(377487, "two_pulse"));
        events.add(new Event(377828, "two_pulse"));

        for (int i = 0; i < 5; i++)
            events.add(new Event(382260 + i*calculateBeatLength(132), "two_pulse"));
        events.add(new Event(382714, "three_pulse"));
        events.add(new Event(383623, "three_pulse"));
        events.add(new Event(384078, "three_pulse"));

        events.add(new Event(244078, "two_flash"));
        events.add(new Event(244760, "two_flash"));
        events.add(new Event(247714, "two_flash"));
        events.add(new Event(248396, "two_flash"));
        events.add(new Event(248851, "two_flash"));
        events.add(new Event(251351, "two_flash"));
        events.add(new Event(252032, "two_flash"));
        events.add(new Event(254987, "two_flash"));
        events.add(new Event(255669, "two_flash"));
        events.add(new Event(256123, "two_flash"));
        events.add(new Event(294987, "two_flash"));
        events.add(new Event(309532, "two_flash"));
        events.add(new Event(325896, "two_flash"));
        events.add(new Event(339078, "two_flash"));
        events.add(new Event(368169, "two_flash"));
        events.add(new Event(382260, "two_flash"));
        events.add(new Event(244078, "three_flash"));
        events.add(new Event(244760, "three_flash"));
        events.add(new Event(247714, "three_flash"));
        events.add(new Event(248396, "three_flash"));
        events.add(new Event(248851, "three_flash"));
        events.add(new Event(251351, "three_flash"));
        events.add(new Event(252032, "three_flash"));
        events.add(new Event(254987, "three_flash"));
        events.add(new Event(255669, "three_flash"));
        events.add(new Event(256123, "three_flash"));
        events.add(new Event(294987, "three_flash"));
        events.add(new Event(309532, "three_flash"));
        events.add(new Event(325896, "three_flash"));
        events.add(new Event(339078, "three_flash"));
        events.add(new Event(368169, "three_flash"));
        events.add(new Event(382260, "three_flash"));


        // ==================== [MOVEMENT III] ====================


        for (int i = 0; i < 4; i++) {
            events.add(new Event(428642 + i*calculateBeatLength(100)*16, "two_pulse"));
            events.add(new Event(430292 + i*calculateBeatLength(100)*16, "two_pulse"));
            events.add(new Event(431042 + i*calculateBeatLength(100)*16, "two_pulse"));
            events.add(new Event(432692 + i*calculateBeatLength(100)*16, "two_pulse"));
            events.add(new Event(433442 + i*calculateBeatLength(100)*16, "two_pulse"));
            events.add(new Event(435092 + i*calculateBeatLength(100)*16, "two_pulse"));
            events.add(new Event(435842 + i*calculateBeatLength(100)*16, "two_pulse"));
            events.add(new Event(437342 + i*calculateBeatLength(100)*16, "two_pulse"));
            events.add(new Event(429092 + i*calculateBeatLength(100)*16, "three_pulse"));
            events.add(new Event(429542 + i*calculateBeatLength(100)*16, "three_pulse"));
            events.add(new Event(430742 + i*calculateBeatLength(100)*16, "three_pulse"));
            events.add(new Event(431492 + i*calculateBeatLength(100)*16, "three_pulse"));
            events.add(new Event(431942 + i*calculateBeatLength(100)*16, "three_pulse"));
            events.add(new Event(432992 + i*calculateBeatLength(100)*16, "three_pulse"));
            events.add(new Event(433142 + i*calculateBeatLength(100)*16, "three_pulse"));
            events.add(new Event(433892 + i*calculateBeatLength(100)*16, "three_pulse"));
            events.add(new Event(434342 + i*calculateBeatLength(100)*16, "three_pulse"));
            events.add(new Event(435542 + i*calculateBeatLength(100)*16, "three_pulse"));
            events.add(new Event(436292 + i*calculateBeatLength(100)*16, "three_pulse"));
            events.add(new Event(436742 + i*calculateBeatLength(100)*16, "three_pulse"));
            events.add(new Event(437492 + i*calculateBeatLength(100)*16, "three_pulse"));
            if (i != 3)
                events.add(new Event(437942 + i*calculateBeatLength(100)*16, "three_pulse"));
        }

        events.add(new Event(466442, "two_pulse"));
        events.add(new Event(467042, "two_pulse"));
        events.add(new Event(471842, "two_pulse"));
        events.add(new Event(476642, "two_pulse"));
        events.add(new Event(481442, "two_pulse"));
        events.add(new Event(481742, "two_pulse"));
        events.add(new Event(482042, "two_pulse"));
        events.add(new Event(482342, "two_pulse"));
        events.add(new Event(482642, "two_pulse"));
        events.add(new Event(482942, "two_pulse"));
        events.add(new Event(483242, "two_pulse"));
        events.add(new Event(483542, "two_pulse"));
        events.add(new Event(467042, "three_pulse"));
        events.add(new Event(471842, "three_pulse"));
        events.add(new Event(476642, "three_pulse"));
        events.add(new Event(481442, "three_pulse"));
        events.add(new Event(481742, "three_pulse"));
        events.add(new Event(482042, "three_pulse"));
        events.add(new Event(482342, "three_pulse"));
        events.add(new Event(482642, "three_pulse"));
        events.add(new Event(482942, "three_pulse"));
        events.add(new Event(483242, "three_pulse"));
        events.add(new Event(483542, "three_pulse"));

        events.add(new Event(486242, "two_pulse"));
        events.add(new Event(487742, "two_pulse"));
        events.add(new Event(488642, "two_pulse"));
        events.add(new Event(490442, "two_pulse"));
        events.add(new Event(491042, "two_pulse"));
        events.add(new Event(492542, "two_pulse"));
        events.add(new Event(493442, "two_pulse"));
        events.add(new Event(493742, "two_pulse"));
        events.add(new Event(494042, "two_pulse"));
        events.add(new Event(494342, "two_pulse"));
        events.add(new Event(494642, "two_pulse"));
        events.add(new Event(495242, "two_pulse"));
        events.add(new Event(495842, "two_pulse"));
        events.add(new Event(497342, "two_pulse"));
        events.add(new Event(498242, "two_pulse"));
        events.add(new Event(500042, "two_pulse"));
        events.add(new Event(500642, "two_pulse"));
        events.add(new Event(502142, "two_pulse"));
        events.add(new Event(503042, "two_pulse"));
        events.add(new Event(503642, "two_pulse"));
        events.add(new Event(504242, "two_pulse"));
        events.add(new Event(504842, "two_pulse"));
        events.add(new Event(505442, "two_pulse"));
        events.add(new Event(506942, "two_pulse"));
        events.add(new Event(507842, "two_pulse"));
        events.add(new Event(509642, "two_pulse"));
        events.add(new Event(510242, "two_pulse"));
        events.add(new Event(511742, "two_pulse"));
        events.add(new Event(512642, "two_pulse"));
        events.add(new Event(512942, "two_pulse"));
        events.add(new Event(513242, "two_pulse"));
        events.add(new Event(513542, "two_pulse"));
        events.add(new Event(513842, "two_pulse"));
        events.add(new Event(514442, "two_pulse"));
        events.add(new Event(515042, "two_pulse"));
        events.add(new Event(516542, "two_pulse"));
        events.add(new Event(517442, "two_pulse"));
        events.add(new Event(519242, "two_pulse"));
        events.add(new Event(519842, "two_pulse"));
        events.add(new Event(521042, "two_pulse"));
        events.add(new Event(522242, "two_pulse"));
        events.add(new Event(522842, "two_pulse"));
        events.add(new Event(523442, "two_pulse"));
        events.add(new Event(524042, "two_pulse"));
        events.add(new Event(486242, "three_pulse"));
        events.add(new Event(487742, "three_pulse"));
        events.add(new Event(488642, "three_pulse"));
        events.add(new Event(490442, "three_pulse"));
        events.add(new Event(491042, "three_pulse"));
        events.add(new Event(492542, "three_pulse"));
        events.add(new Event(493442, "three_pulse"));
        events.add(new Event(493742, "three_pulse"));
        events.add(new Event(494042, "three_pulse"));
        events.add(new Event(494342, "three_pulse"));
        events.add(new Event(494642, "three_pulse"));
        events.add(new Event(495242, "three_pulse"));
        events.add(new Event(495842, "three_pulse"));
        events.add(new Event(497342, "three_pulse"));
        events.add(new Event(498242, "three_pulse"));
        events.add(new Event(500042, "three_pulse"));
        events.add(new Event(500642, "three_pulse"));
        events.add(new Event(502142, "three_pulse"));
        events.add(new Event(503042, "three_pulse"));
        events.add(new Event(503642, "three_pulse"));
        events.add(new Event(504242, "three_pulse"));
        events.add(new Event(504842, "three_pulse"));
        events.add(new Event(505442, "three_pulse"));
        events.add(new Event(506942, "three_pulse"));
        events.add(new Event(507842, "three_pulse"));
        events.add(new Event(509642, "three_pulse"));
        events.add(new Event(510242, "three_pulse"));
        events.add(new Event(511742, "three_pulse"));
        events.add(new Event(512642, "three_pulse"));
        events.add(new Event(512942, "three_pulse"));
        events.add(new Event(513242, "three_pulse"));
        events.add(new Event(513542, "three_pulse"));
        events.add(new Event(513842, "three_pulse"));
        events.add(new Event(514442, "three_pulse"));
        events.add(new Event(515042, "three_pulse"));
        events.add(new Event(516542, "three_pulse"));
        events.add(new Event(517442, "three_pulse"));
        events.add(new Event(519242, "three_pulse"));
        events.add(new Event(519842, "three_pulse"));
        events.add(new Event(521042, "three_pulse"));
        events.add(new Event(522242, "three_pulse"));
        events.add(new Event(522842, "three_pulse"));
        events.add(new Event(523442, "three_pulse"));
        events.add(new Event(524042, "three_pulse"));

        events.add(new Event(524642, "two_pulse"));
        events.add(new Event(526142, "two_pulse"));
        events.add(new Event(527042, "two_pulse"));
        events.add(new Event(528542, "two_pulse"));
        events.add(new Event(529442, "two_pulse"));
        events.add(new Event(530942, "two_pulse"));
        events.add(new Event(531842, "two_pulse"));
        events.add(new Event(533042, "two_pulse"));
        events.add(new Event(533642, "two_pulse"));
        events.add(new Event(534242, "two_pulse"));
        events.add(new Event(535742, "two_pulse"));
        events.add(new Event(536642, "two_pulse"));
        events.add(new Event(538142, "two_pulse"));
        events.add(new Event(539042, "two_pulse"));
        events.add(new Event(540542, "two_pulse"));
        events.add(new Event(541442, "two_pulse"));
        events.add(new Event(542642, "two_pulse"));
        events.add(new Event(543242, "two_pulse"));
        events.add(new Event(543842, "two_pulse"));
        events.add(new Event(546842, "two_pulse"));

        events.add(new Event(548642, "two_pulse"));
        events.add(new Event(549542, "two_pulse"));
        events.add(new Event(553442, "two_pulse"));
        events.add(new Event(554342, "two_pulse"));
        events.add(new Event(558242, "two_pulse"));
        events.add(new Event(559142, "two_pulse"));
        events.add(new Event(563042, "two_pulse"));
        events.add(new Event(563942, "two_pulse"));
        events.add(new Event(548642, "three_pulse"));
        events.add(new Event(549542, "three_pulse"));
        events.add(new Event(553442, "three_pulse"));
        events.add(new Event(554342, "three_pulse"));
        events.add(new Event(558242, "three_pulse"));
        events.add(new Event(559142, "three_pulse"));
        events.add(new Event(563042, "three_pulse"));
        events.add(new Event(563942, "three_pulse"));

        events.add(new Event(550442, "three_pulse"));
        events.add(new Event(555242, "three_pulse"));
        events.add(new Event(560042, "three_pulse"));
        events.add(new Event(564842, "three_pulse"));

        events.add(new Event(555842, "two_pulse"));
        events.add(new Event(556142, "three_pulse"));
        events.add(new Event(556442, "two_pulse"));
        events.add(new Event(556742, "three_pulse"));
        events.add(new Event(557042, "two_pulse"));
        events.add(new Event(557342, "two_pulse"));
        events.add(new Event(557492, "three_pulse"));
        events.add(new Event(557642, "two_pulse"));
        events.add(new Event(557792, "three_pulse"));
        events.add(new Event(557942, "two_pulse"));
        events.add(new Event(558092, "three_pulse"));

        events.add(new Event(428642, "two_flash"));
        events.add(new Event(467042, "two_flash"));
        events.add(new Event(471842, "two_flash"));
        events.add(new Event(476642, "two_flash"));
        events.add(new Event(481442, "two_flash"));
        events.add(new Event(481742, "two_flash"));
        events.add(new Event(482042, "two_flash"));
        events.add(new Event(482342, "two_flash"));
        events.add(new Event(482642, "two_flash"));
        events.add(new Event(482942, "two_flash"));
        events.add(new Event(483242, "two_flash"));
        events.add(new Event(483542, "two_flash"));
        //events.add(new Event(550442, "two_flash"));
        //events.add(new Event(555242, "two_flash"));
        //events.add(new Event(560042, "two_flash"));
        //events.add(new Event(564842, "two_flash"));
        events.add(new Event(428642, "three_flash"));
        events.add(new Event(467042, "three_flash"));
        events.add(new Event(471842, "three_flash"));
        events.add(new Event(476642, "three_flash"));
        events.add(new Event(481442, "three_flash"));
        events.add(new Event(481742, "three_flash"));
        events.add(new Event(482042, "three_flash"));
        events.add(new Event(482342, "three_flash"));
        events.add(new Event(482642, "three_flash"));
        events.add(new Event(482942, "three_flash"));
        events.add(new Event(483242, "three_flash"));
        events.add(new Event(483542, "three_flash"));
        events.add(new Event(550442, "three_flash"));
        events.add(new Event(555242, "three_flash"));
        events.add(new Event(560042, "three_flash"));
        //events.add(new Event(564842, "three_flash"));

        events.add(new Event(483842, "two_flash"));
        events.add(new Event(483992, "three_flash"));
        events.add(new Event(484142, "two_flash"));
        events.add(new Event(484292, "three_flash"));
        events.add(new Event(484442, "two_flash"));
        events.add(new Event(484592, "three_flash"));
        events.add(new Event(484742, "two_flash"));
        events.add(new Event(484892, "three_flash"));
        events.add(new Event(485042, "two_flash"));
        events.add(new Event(485192, "three_flash"));
        events.add(new Event(485342, "two_flash"));
        events.add(new Event(485492, "three_flash"));
        events.add(new Event(485642, "two_flash"));
        events.add(new Event(485792, "three_flash"));
        events.add(new Event(485942, "two_flash"));
        events.add(new Event(486092, "three_flash"));
        events.add(new Event(557342, "two_flash"));
        events.add(new Event(557492, "three_flash"));
        events.add(new Event(557642, "two_flash"));
        events.add(new Event(557792, "three_flash"));
        events.add(new Event(557942, "two_flash"));
        events.add(new Event(558092, "three_flash"));


        // ==================== [MOVEMENT IV] ====================

        for (int i = 0; i < 7; i++) {
            events.add(new Event(576327 + i*calculateBeatLength(175)*8, "two_pulse"));
            events.add(new Event(576842 + i*calculateBeatLength(175)*8, "two_pulse"));
            events.add(new Event(577699 + i*calculateBeatLength(175)*8, "two_pulse"));
            events.add(new Event(578213 + i*calculateBeatLength(175)*8, "two_pulse"));
            events.add(new Event(576670 + i*calculateBeatLength(175)*8, "three_pulse"));
            events.add(new Event(577184 + i*calculateBeatLength(175)*8, "three_pulse"));
            events.add(new Event(578042 + i*calculateBeatLength(175)*8, "three_pulse"));
        }

        events.add(new Event(620213, "two_pulse"));
        events.add(new Event(625699, "two_pulse"));
        events.add(new Event(631184, "two_pulse"));
        events.add(new Event(631527, "two_pulse"));
        events.add(new Event(631870, "two_pulse"));
        events.add(new Event(632213, "two_pulse"));
        events.add(new Event(632556, "two_pulse"));
        events.add(new Event(632899, "two_pulse"));
        events.add(new Event(633242, "two_pulse"));
        events.add(new Event(633584, "two_pulse"));
        events.add(new Event(633927, "two_pulse"));
        events.add(new Event(634270, "two_pulse"));
        events.add(new Event(634613, "two_pulse"));
        events.add(new Event(634956, "two_pulse"));
        events.add(new Event(635299, "two_pulse"));
        events.add(new Event(635642, "two_pulse"));
        events.add(new Event(635984, "two_pulse"));
        events.add(new Event(636670, "two_pulse"));
        events.add(new Event(636927, "two_pulse"));
        events.add(new Event(637184, "two_pulse"));
        events.add(new Event(637356, "two_pulse"));
        events.add(new Event(637613, "two_pulse"));
        events.add(new Event(637870, "two_pulse"));
        events.add(new Event(638042, "two_pulse"));
        events.add(new Event(638299, "two_pulse"));
        events.add(new Event(638556, "two_pulse"));
        events.add(new Event(638727, "two_pulse"));
        events.add(new Event(638984, "two_pulse"));
        events.add(new Event(639242, "two_pulse"));
        events.add(new Event(639413, "two_pulse"));
        events.add(new Event(639670, "two_pulse"));
        events.add(new Event(639927, "two_pulse"));
        events.add(new Event(640184, "two_pulse"));
        events.add(new Event(640442, "two_pulse"));
        events.add(new Event(637013, "three_pulse"));
        events.add(new Event(637699, "three_pulse"));
        events.add(new Event(638384, "three_pulse"));
        events.add(new Event(639070, "three_pulse"));
        events.add(new Event(639413, "three_pulse"));
        events.add(new Event(639670, "three_pulse"));
        events.add(new Event(639927, "three_pulse"));
        events.add(new Event(640184, "three_pulse"));
        events.add(new Event(640442, "three_pulse"));

        for (int i = 0; i < 2; i++) {
            events.add(new Event(642156 + i * calculateBeatLength(175) * 32, "two_pulse"));
            events.add(new Event(642756 + i * calculateBeatLength(175) * 32, "two_pulse"));
            events.add(new Event(643013 + i * calculateBeatLength(175) * 32, "two_pulse"));
            events.add(new Event(643442 + i * calculateBeatLength(175) * 32, "two_pulse"));
            events.add(new Event(643699 + i * calculateBeatLength(175) * 32, "two_pulse"));
            events.add(new Event(644127 + i * calculateBeatLength(175) * 32, "two_pulse"));
            events.add(new Event(644384 + i * calculateBeatLength(175) * 32, "two_pulse"));
            events.add(new Event(644899 + i * calculateBeatLength(175) * 32, "two_pulse"));
            events.add(new Event(645413 + i * calculateBeatLength(175) * 32, "two_pulse"));
            events.add(new Event(645756 + i * calculateBeatLength(175) * 32, "two_pulse"));
            events.add(new Event(646184 + i * calculateBeatLength(175) * 32, "two_pulse"));
            events.add(new Event(646442 + i * calculateBeatLength(175) * 32, "two_pulse"));
            events.add(new Event(646870 + i * calculateBeatLength(175) * 32, "two_pulse"));
            events.add(new Event(647127 + i * calculateBeatLength(175) * 32, "two_pulse"));
            events.add(new Event(647642 + i * calculateBeatLength(175) * 32, "two_pulse"));
            events.add(new Event(648242 + i * calculateBeatLength(175) * 32, "two_pulse"));
            events.add(new Event(648499 + i * calculateBeatLength(175) * 32, "two_pulse"));
            events.add(new Event(648927 + i * calculateBeatLength(175) * 32, "two_pulse"));
            events.add(new Event(649184 + i * calculateBeatLength(175) * 32, "two_pulse"));
            events.add(new Event(649613 + i * calculateBeatLength(175) * 32, "two_pulse"));
            events.add(new Event(649870 + i * calculateBeatLength(175) * 32, "two_pulse"));
            events.add(new Event(650384 + i * calculateBeatLength(175) * 32, "two_pulse"));
            events.add(new Event(651756 + i * calculateBeatLength(175) * 32, "two_pulse"));
            events.add(new Event(642499 + i * calculateBeatLength(175) * 32, "three_pulse"));
            events.add(new Event(643184 + i * calculateBeatLength(175) * 32, "three_pulse"));
            events.add(new Event(643870 + i * calculateBeatLength(175) * 32, "three_pulse"));
            events.add(new Event(644556 + i * calculateBeatLength(175) * 32, "three_pulse"));
            events.add(new Event(645242 + i * calculateBeatLength(175) * 32, "three_pulse"));
            events.add(new Event(645927 + i * calculateBeatLength(175) * 32, "three_pulse"));
            events.add(new Event(646613 + i * calculateBeatLength(175) * 32, "three_pulse"));
            events.add(new Event(647299 + i * calculateBeatLength(175) * 32, "three_pulse"));
            events.add(new Event(647984 + i * calculateBeatLength(175) * 32, "three_pulse"));
            events.add(new Event(648670 + i * calculateBeatLength(175) * 32, "three_pulse"));
            events.add(new Event(649356 + i * calculateBeatLength(175) * 32, "three_pulse"));
            events.add(new Event(650042 + i * calculateBeatLength(175) * 32, "three_pulse"));
            events.add(new Event(650384 + i * calculateBeatLength(175) * 32, "three_pulse"));
            events.add(new Event(651756 + i * calculateBeatLength(175) * 32, "three_pulse"));
        }

        events.add(new Event(664099, "two_pulse"));
        events.add(new Event(664613, "two_pulse"));
        events.add(new Event(665127, "two_pulse"));
        events.add(new Event(666842, "two_pulse"));
        events.add(new Event(667356, "two_pulse"));
        events.add(new Event(667870, "two_pulse"));
        events.add(new Event(668556, "two_pulse"));
        events.add(new Event(669584, "two_pulse"));
        events.add(new Event(670099, "two_pulse"));
        events.add(new Event(670613, "two_pulse"));
        events.add(new Event(672327, "two_pulse"));
        events.add(new Event(672842, "two_pulse"));
        events.add(new Event(673356, "two_pulse"));
        events.add(new Event(674042, "two_pulse"));
        events.add(new Event(664099, "three_pulse"));
        events.add(new Event(664613, "three_pulse"));
        events.add(new Event(665127, "three_pulse"));
        events.add(new Event(666842, "three_pulse"));
        events.add(new Event(667356, "three_pulse"));
        events.add(new Event(667870, "three_pulse"));
        events.add(new Event(668556, "three_pulse"));
        events.add(new Event(669584, "three_pulse"));
        events.add(new Event(670099, "three_pulse"));
        events.add(new Event(670613, "three_pulse"));
        events.add(new Event(672327, "three_pulse"));
        events.add(new Event(672842, "three_pulse"));
        events.add(new Event(673356, "three_pulse"));
        events.add(new Event(674042, "three_pulse"));

        for (int i = 0; i < 31; i++)
            events.add(new Event(675070 + i*calculateBeatLength(175), "two_pulse"));
        for (int i = 0; i < 14; i++)
            events.add(new Event(680727 + i*calculateBeatLength(175), "three_pulse"));

        events.add(new Event(686042, "two_pulse"));
        events.add(new Event(687070, "two_pulse"));
        events.add(new Event(687756, "two_pulse"));
        events.add(new Event(688784, "two_pulse"));
        events.add(new Event(689813, "two_pulse"));
        events.add(new Event(690499, "two_pulse"));
        events.add(new Event(691527, "two_pulse"));
        events.add(new Event(692556, "two_pulse"));
        events.add(new Event(693242, "two_pulse"));
        events.add(new Event(694270, "two_pulse"));
        events.add(new Event(695299, "two_pulse"));
        events.add(new Event(695642, "two_pulse"));
        events.add(new Event(695984, "two_pulse"));
        events.add(new Event(697013, "two_pulse"));
        events.add(new Event(698042, "two_pulse"));
        events.add(new Event(698727, "two_pulse"));
        events.add(new Event(699756, "two_pulse"));
        events.add(new Event(700784, "two_pulse"));
        events.add(new Event(701470, "two_pulse"));
        events.add(new Event(702499, "two_pulse"));
        events.add(new Event(703527, "two_pulse"));
        events.add(new Event(704213, "two_pulse"));
        events.add(new Event(705242, "two_pulse"));
        events.add(new Event(706270, "two_pulse"));
        events.add(new Event(706613, "two_pulse"));
        events.add(new Event(706956, "two_pulse"));
        events.add(new Event(707299, "two_pulse"));
        events.add(new Event(707642, "two_pulse"));
        events.add(new Event(686042, "three_pulse"));
        events.add(new Event(687070, "three_pulse"));
        events.add(new Event(687756, "three_pulse"));
        events.add(new Event(688784, "three_pulse"));
        events.add(new Event(689813, "three_pulse"));
        events.add(new Event(690499, "three_pulse"));
        events.add(new Event(691527, "three_pulse"));
        events.add(new Event(692556, "three_pulse"));
        events.add(new Event(693242, "three_pulse"));
        events.add(new Event(694270, "three_pulse"));
        events.add(new Event(695299, "three_pulse"));
        events.add(new Event(695642, "three_pulse"));
        events.add(new Event(695984, "three_pulse"));
        events.add(new Event(697013, "three_pulse"));
        events.add(new Event(698042, "three_pulse"));
        events.add(new Event(698727, "three_pulse"));
        events.add(new Event(699756, "three_pulse"));
        events.add(new Event(700784, "three_pulse"));
        events.add(new Event(701470, "three_pulse"));
        events.add(new Event(702499, "three_pulse"));
        events.add(new Event(703527, "three_pulse"));
        events.add(new Event(704213, "three_pulse"));
        events.add(new Event(705242, "three_pulse"));
        events.add(new Event(706270, "three_pulse"));
        events.add(new Event(706613, "three_pulse"));
        events.add(new Event(706956, "three_pulse"));
        events.add(new Event(707299, "three_pulse"));
        events.add(new Event(707642, "three_pulse"));

        events.add(new Event(729927, "two_pulse"));
        events.add(new Event(735413, "two_pulse"));
        events.add(new Event(740899, "two_pulse"));
        events.add(new Event(746384, "two_pulse"));
        events.add(new Event(747070, "two_pulse"));
        events.add(new Event(747756, "two_pulse"));
        events.add(new Event(748442, "two_pulse"));
        events.add(new Event(749127, "two_pulse"));
        events.add(new Event(749642, "two_pulse"));
        events.add(new Event(750156, "two_pulse"));
        events.add(new Event(750499, "two_pulse"));
        events.add(new Event(751013, "two_pulse"));
        events.add(new Event(751527, "two_pulse"));
        events.add(new Event(751870, "two_pulse"));
        events.add(new Event(752213, "two_pulse"));
        events.add(new Event(752556, "two_pulse"));
        events.add(new Event(752899, "two_pulse"));
        events.add(new Event(753242, "two_pulse"));
        events.add(new Event(753584, "two_pulse"));
        events.add(new Event(753927, "two_pulse"));
        events.add(new Event(754270, "two_pulse"));
        events.add(new Event(746727, "three_pulse"));
        events.add(new Event(747413, "three_pulse"));
        events.add(new Event(748099, "three_pulse"));
        events.add(new Event(748784, "three_pulse"));
        events.add(new Event(749127, "three_pulse"));
        events.add(new Event(749642, "three_pulse"));
        events.add(new Event(750156, "three_pulse"));
        events.add(new Event(750499, "three_pulse"));
        events.add(new Event(751013, "three_pulse"));
        events.add(new Event(751527, "three_pulse"));
        events.add(new Event(751870, "three_pulse"));
        events.add(new Event(752213, "three_pulse"));
        events.add(new Event(752556, "three_pulse"));
        events.add(new Event(752899, "three_pulse"));
        events.add(new Event(753242, "three_pulse"));
        events.add(new Event(753584, "three_pulse"));
        events.add(new Event(753927, "three_pulse"));
        events.add(new Event(754270, "three_pulse"));

        // i need to do homework
        // i deeply apologize to whoever may read this in the future

        events.add(new Event(760099, "two_pulse"));
        events.add(new Event(760956, "two_pulse"));
        events.add(new Event(761470, "two_pulse"));
        events.add(new Event(762327, "two_pulse"));
        events.add(new Event(762842, "two_pulse"));
        events.add(new Event(763699, "two_pulse"));
        events.add(new Event(764213, "two_pulse"));
        events.add(new Event(764899, "two_pulse"));
        events.add(new Event(765584, "two_pulse"));
        events.add(new Event(766442, "two_pulse"));
        events.add(new Event(766956, "two_pulse"));
        events.add(new Event(767813, "two_pulse"));
        events.add(new Event(768327, "two_pulse"));
        events.add(new Event(769184, "two_pulse"));
        events.add(new Event(769699, "two_pulse"));
        events.add(new Event(770556, "two_pulse"));
        events.add(new Event(771070, "two_pulse"));
        events.add(new Event(771756, "two_pulse"));
        events.add(new Event(772442, "two_pulse"));
        events.add(new Event(773127, "two_pulse"));
        events.add(new Event(773813, "two_pulse"));
        events.add(new Event(774670, "two_pulse"));
        events.add(new Event(775184, "two_pulse"));
        events.add(new Event(776042, "two_pulse"));
        events.add(new Event(776556, "two_pulse"));
        events.add(new Event(777413, "two_pulse"));
        events.add(new Event(777927, "two_pulse"));
        events.add(new Event(778784, "two_pulse"));
        events.add(new Event(779299, "two_pulse"));
        events.add(new Event(779984, "two_pulse"));
        events.add(new Event(780670, "two_pulse"));
        events.add(new Event(781356, "two_pulse"));
        events.add(new Event(782042, "two_pulse"));
        events.add(new Event(783070, "two_pulse"));
        events.add(new Event(783756, "two_pulse"));
        events.add(new Event(784784, "two_pulse"));
        events.add(new Event(785299, "two_pulse"));
        events.add(new Event(785813, "two_pulse"));
        events.add(new Event(787527, "two_pulse"));
        events.add(new Event(788556, "two_pulse"));
        events.add(new Event(790270, "two_pulse"));
        events.add(new Event(791299, "two_pulse"));
        events.add(new Event(791984, "two_pulse"));
        events.add(new Event(793013, "two_pulse"));
        events.add(new Event(794042, "two_pulse"));
        events.add(new Event(794727, "two_pulse"));
        events.add(new Event(795756, "two_pulse"));
        events.add(new Event(796784, "two_pulse"));
        events.add(new Event(797470, "two_pulse"));
        events.add(new Event(798499, "two_pulse"));
        events.add(new Event(799527, "two_pulse"));
        events.add(new Event(801242, "two_pulse"));
        events.add(new Event(802270, "two_pulse"));
        events.add(new Event(802956, "two_pulse"));
        events.add(new Event(803984, "two_pulse"));
        events.add(new Event(805356, "two_pulse"));
        events.add(new Event(806727, "two_pulse"));
        events.add(new Event(809470, "two_pulse"));
        events.add(new Event(810327, "two_pulse"));
        events.add(new Event(810842, "two_pulse"));
        events.add(new Event(811699, "two_pulse"));
        events.add(new Event(812213, "two_pulse"));
        events.add(new Event(813070, "two_pulse"));
        events.add(new Event(813584, "two_pulse"));
        events.add(new Event(814442, "two_pulse"));
        events.add(new Event(814956, "two_pulse"));
        events.add(new Event(815813, "two_pulse"));
        events.add(new Event(816327, "two_pulse"));
        events.add(new Event(817184, "two_pulse"));
        events.add(new Event(817699, "two_pulse"));
        events.add(new Event(818556, "two_pulse"));
        events.add(new Event(819070, "two_pulse"));
        events.add(new Event(819584, "two_pulse"));
        events.add(new Event(819927, "two_pulse"));
        events.add(new Event(820442, "two_pulse"));
        events.add(new Event(821299, "two_pulse"));
        events.add(new Event(821813, "two_pulse"));
        events.add(new Event(822670, "two_pulse"));
        events.add(new Event(823184, "two_pulse"));
        events.add(new Event(824042, "two_pulse"));
        events.add(new Event(824556, "two_pulse"));
        events.add(new Event(825413, "two_pulse"));
        events.add(new Event(825927, "two_pulse"));
        events.add(new Event(826784, "two_pulse"));
        events.add(new Event(827299, "two_pulse"));
        events.add(new Event(828156, "two_pulse"));
        events.add(new Event(828670, "two_pulse"));
        events.add(new Event(829527, "two_pulse"));
        events.add(new Event(830042, "two_pulse"));
        events.add(new Event(830556, "two_pulse"));
        events.add(new Event(830899, "two_pulse"));
        events.add(new Event(831413, "two_pulse"));
        events.add(new Event(832784, "two_pulse"));
        events.add(new Event(833642, "two_pulse"));
        events.add(new Event(834156, "two_pulse"));
        events.add(new Event(835013, "two_pulse"));
        events.add(new Event(835527, "two_pulse"));
        events.add(new Event(836384, "two_pulse"));
        events.add(new Event(836899, "two_pulse"));
        events.add(new Event(837756, "two_pulse"));
        events.add(new Event(838270, "two_pulse"));
        events.add(new Event(839127, "two_pulse"));
        events.add(new Event(839642, "two_pulse"));
        events.add(new Event(840499, "two_pulse"));
        events.add(new Event(841013, "two_pulse"));
        events.add(new Event(841527, "two_pulse"));
        events.add(new Event(841870, "two_pulse"));
        events.add(new Event(842384, "two_pulse"));
        events.add(new Event(842899, "two_pulse"));
        events.add(new Event(843413, "two_pulse"));
        events.add(new Event(843756, "two_pulse"));
        events.add(new Event(844270, "two_pulse"));
        events.add(new Event(844784, "two_pulse"));
        events.add(new Event(845127, "two_pulse"));
        events.add(new Event(845642, "two_pulse"));
        events.add(new Event(846156, "two_pulse"));
        events.add(new Event(846499, "two_pulse"));
        events.add(new Event(847013, "two_pulse"));
        events.add(new Event(847527, "two_pulse"));
        events.add(new Event(847870, "two_pulse"));
        events.add(new Event(848213, "two_pulse"));
        events.add(new Event(848556, "two_pulse"));
        events.add(new Event(848899, "two_pulse"));
        events.add(new Event(849242, "two_pulse"));
        events.add(new Event(849584, "two_pulse"));
        events.add(new Event(849927, "two_pulse"));
        events.add(new Event(850270, "two_pulse"));
        events.add(new Event(850613, "two_pulse"));
        events.add(new Event(850956, "two_pulse"));
        events.add(new Event(851299, "two_pulse"));
        events.add(new Event(851642, "two_pulse"));
        events.add(new Event(851984, "two_pulse"));
        events.add(new Event(852336, "two_pulse"));
        events.add(new Event(852697, "two_pulse"));
        events.add(new Event(853065, "two_pulse"));

        events.add(new Event(760442, "three_pulse"));
        events.add(new Event(761127, "three_pulse"));
        events.add(new Event(761813, "three_pulse"));
        events.add(new Event(762499, "three_pulse"));
        events.add(new Event(763184, "three_pulse"));
        events.add(new Event(763870, "three_pulse"));
        events.add(new Event(764556, "three_pulse"));
        events.add(new Event(765242, "three_pulse"));
        events.add(new Event(765927, "three_pulse"));
        events.add(new Event(766613, "three_pulse"));
        events.add(new Event(767299, "three_pulse"));
        events.add(new Event(767984, "three_pulse"));
        events.add(new Event(768670, "three_pulse"));
        events.add(new Event(769356, "three_pulse"));
        events.add(new Event(770042, "three_pulse"));
        events.add(new Event(770727, "three_pulse"));
        events.add(new Event(771413, "three_pulse"));
        events.add(new Event(772099, "three_pulse"));
        events.add(new Event(772784, "three_pulse"));
        events.add(new Event(774156, "three_pulse"));
        events.add(new Event(774842, "three_pulse"));
        events.add(new Event(775527, "three_pulse"));
        events.add(new Event(776213, "three_pulse"));
        events.add(new Event(776899, "three_pulse"));
        events.add(new Event(777584, "three_pulse"));
        events.add(new Event(778270, "three_pulse"));
        events.add(new Event(778956, "three_pulse"));
        events.add(new Event(779642, "three_pulse"));
        events.add(new Event(780327, "three_pulse"));
        events.add(new Event(781013, "three_pulse"));
        events.add(new Event(782727, "three_pulse"));
        events.add(new Event(784099, "three_pulse"));
        events.add(new Event(785470, "three_pulse"));
        events.add(new Event(786842, "three_pulse"));
        events.add(new Event(788213, "three_pulse"));
        events.add(new Event(789584, "three_pulse"));
        events.add(new Event(790956, "three_pulse"));
        events.add(new Event(792327, "three_pulse"));
        events.add(new Event(793699, "three_pulse"));
        events.add(new Event(795070, "three_pulse"));
        events.add(new Event(796442, "three_pulse"));
        events.add(new Event(797813, "three_pulse"));
        events.add(new Event(799184, "three_pulse"));
        events.add(new Event(800556, "three_pulse"));
        events.add(new Event(801927, "three_pulse"));
        events.add(new Event(803299, "three_pulse"));
        events.add(new Event(804670, "three_pulse"));
        events.add(new Event(806042, "three_pulse"));
        events.add(new Event(809813, "three_pulse"));
        events.add(new Event(810499, "three_pulse"));
        events.add(new Event(811184, "three_pulse"));
        events.add(new Event(811870, "three_pulse"));
        events.add(new Event(812556, "three_pulse"));
        events.add(new Event(813242, "three_pulse"));
        events.add(new Event(813927, "three_pulse"));
        events.add(new Event(814613, "three_pulse"));
        events.add(new Event(815299, "three_pulse"));
        events.add(new Event(815984, "three_pulse"));
        events.add(new Event(816670, "three_pulse"));
        events.add(new Event(817356, "three_pulse"));
        events.add(new Event(818042, "three_pulse"));
        events.add(new Event(818727, "three_pulse"));
        events.add(new Event(819413, "three_pulse"));
        events.add(new Event(820099, "three_pulse"));
        events.add(new Event(820784, "three_pulse"));
        events.add(new Event(821470, "three_pulse"));
        events.add(new Event(822156, "three_pulse"));
        events.add(new Event(822842, "three_pulse"));
        events.add(new Event(823527, "three_pulse"));
        events.add(new Event(824213, "three_pulse"));
        events.add(new Event(824899, "three_pulse"));
        events.add(new Event(825584, "three_pulse"));
        events.add(new Event(826270, "three_pulse"));
        events.add(new Event(826956, "three_pulse"));
        events.add(new Event(827642, "three_pulse"));
        events.add(new Event(828327, "three_pulse"));
        events.add(new Event(829013, "three_pulse"));
        events.add(new Event(829699, "three_pulse"));
        events.add(new Event(830384, "three_pulse"));
        events.add(new Event(830727, "three_pulse"));
        events.add(new Event(831070, "three_pulse"));
        events.add(new Event(833127, "three_pulse"));
        events.add(new Event(833813, "three_pulse"));
        events.add(new Event(834499, "three_pulse"));
        events.add(new Event(835184, "three_pulse"));
        events.add(new Event(835870, "three_pulse"));
        events.add(new Event(836556, "three_pulse"));
        events.add(new Event(837242, "three_pulse"));
        events.add(new Event(837927, "three_pulse"));
        events.add(new Event(838613, "three_pulse"));
        events.add(new Event(839299, "three_pulse"));
        events.add(new Event(839984, "three_pulse"));
        events.add(new Event(840670, "three_pulse"));
        events.add(new Event(841356, "three_pulse"));
        events.add(new Event(842042, "three_pulse"));
        events.add(new Event(842384, "three_pulse"));
        events.add(new Event(842899, "three_pulse"));
        events.add(new Event(843413, "three_pulse"));
        events.add(new Event(843756, "three_pulse"));
        events.add(new Event(844270, "three_pulse"));
        events.add(new Event(844784, "three_pulse"));
        events.add(new Event(845127, "three_pulse"));
        events.add(new Event(845642, "three_pulse"));
        events.add(new Event(846156, "three_pulse"));
        events.add(new Event(846499, "three_pulse"));
        events.add(new Event(847013, "three_pulse"));
        events.add(new Event(847527, "three_pulse"));
        events.add(new Event(847870, "three_pulse"));
        events.add(new Event(848213, "three_pulse"));
        events.add(new Event(848556, "three_pulse"));
        events.add(new Event(848899, "three_pulse"));
        events.add(new Event(849242, "three_pulse"));
        events.add(new Event(849584, "three_pulse"));
        events.add(new Event(849927, "three_pulse"));
        events.add(new Event(850270, "three_pulse"));
        events.add(new Event(850613, "three_pulse"));
        events.add(new Event(850956, "three_pulse"));
        events.add(new Event(851299, "three_pulse"));
        events.add(new Event(851642, "three_pulse"));
        events.add(new Event(851984, "three_pulse"));
        events.add(new Event(852336, "three_pulse"));
        events.add(new Event(852697, "three_pulse"));
        events.add(new Event(853065, "three_pulse"));

        events.add(new Event(637013, "two_flash"));
        events.add(new Event(637699, "two_flash"));
        events.add(new Event(638384, "two_flash"));
        events.add(new Event(639070, "two_flash"));
        events.add(new Event(639413, "two_flash"));
        events.add(new Event(639670, "two_flash"));
        events.add(new Event(639927, "two_flash"));
        events.add(new Event(640184, "two_flash"));
        events.add(new Event(640442, "two_flash"));
        events.add(new Event(642156, "two_flash"));
        events.add(new Event(644384, "two_flash"));
        events.add(new Event(645242, "two_flash"));
        events.add(new Event(646184, "two_flash"));
        events.add(new Event(647642, "two_flash"));
        events.add(new Event(648927, "two_flash"));
        events.add(new Event(649613, "two_flash"));
        events.add(new Event(650384, "two_flash"));
        events.add(new Event(651756, "two_flash"));
        events.add(new Event(653127, "two_flash"));
        events.add(new Event(655356, "two_flash"));
        events.add(new Event(656213, "two_flash"));
        events.add(new Event(657156, "two_flash"));
        events.add(new Event(658613, "two_flash"));
        events.add(new Event(659899, "two_flash"));
        events.add(new Event(660584, "two_flash"));
        events.add(new Event(661356, "two_flash"));
        events.add(new Event(662727, "two_flash"));
        events.add(new Event(664099, "two_flash"));
        events.add(new Event(637013, "three_flash"));
        events.add(new Event(637699, "three_flash"));
        events.add(new Event(638384, "three_flash"));
        events.add(new Event(639070, "three_flash"));
        events.add(new Event(639413, "three_flash"));
        events.add(new Event(639670, "three_flash"));
        events.add(new Event(639927, "three_flash"));
        events.add(new Event(640184, "three_flash"));
        events.add(new Event(640442, "three_flash"));
        events.add(new Event(642156, "three_flash"));
        events.add(new Event(644384, "three_flash"));
        events.add(new Event(645242, "three_flash"));
        events.add(new Event(646184, "three_flash"));
        events.add(new Event(647642, "three_flash"));
        events.add(new Event(648927, "three_flash"));
        events.add(new Event(649613, "three_flash"));
        events.add(new Event(650384, "three_flash"));
        events.add(new Event(651756, "three_flash"));
        events.add(new Event(653127, "three_flash"));
        events.add(new Event(655356, "three_flash"));
        events.add(new Event(656213, "three_flash"));
        events.add(new Event(657156, "three_flash"));
        events.add(new Event(658613, "three_flash"));
        events.add(new Event(659899, "three_flash"));
        events.add(new Event(660584, "three_flash"));
        events.add(new Event(661356, "three_flash"));
        events.add(new Event(662727, "three_flash"));
        events.add(new Event(664099, "three_flash"));

        events.add(new Event(683299, "two_flash"));
        events.add(new Event(683470, "three_flash"));
        events.add(new Event(683642, "two_flash"));
        events.add(new Event(683813, "three_flash"));
        events.add(new Event(683984, "two_flash"));
        events.add(new Event(684156, "three_flash"));
        events.add(new Event(684327, "two_flash"));
        events.add(new Event(684499, "three_flash"));
        events.add(new Event(684670, "two_flash"));
        events.add(new Event(684842, "three_flash"));
        events.add(new Event(685013, "two_flash"));
        events.add(new Event(685184, "three_flash"));
        events.add(new Event(685356, "two_flash"));

        events.add(new Event(686042, "two_flash"));
        events.add(new Event(698042, "two_flash"));
        events.add(new Event(746384, "two_flash"));
        events.add(new Event(749127, "two_flash"));
        events.add(new Event(750499, "two_flash"));
        events.add(new Event(686042, "three_flash"));
        events.add(new Event(698042, "three_flash"));
        events.add(new Event(746384, "three_flash"));
        events.add(new Event(749127, "three_flash"));
        events.add(new Event(750499, "three_flash"));

        events.add(new Event(751870, "two_flash"));
        events.add(new Event(752556, "two_flash"));
        events.add(new Event(753242, "two_flash"));
        events.add(new Event(760099, "two_flash"));
        events.add(new Event(760784, "two_flash"));
        events.add(new Event(761470, "two_flash"));
        events.add(new Event(762156, "two_flash"));
        events.add(new Event(762842, "two_flash"));
        events.add(new Event(763527, "two_flash"));
        events.add(new Event(764213, "two_flash"));
        events.add(new Event(764899, "two_flash"));
        events.add(new Event(765584, "two_flash"));
        events.add(new Event(766270, "two_flash"));
        events.add(new Event(766956, "two_flash"));
        events.add(new Event(767642, "two_flash"));
        events.add(new Event(768327, "two_flash"));
        events.add(new Event(769013, "two_flash"));
        events.add(new Event(769699, "two_flash"));
        events.add(new Event(770384, "two_flash"));
        events.add(new Event(771070, "two_flash"));
        events.add(new Event(771756, "two_flash"));
        events.add(new Event(772442, "two_flash"));
        events.add(new Event(773127, "two_flash"));
        events.add(new Event(773813, "two_flash"));
        events.add(new Event(774499, "two_flash"));
        events.add(new Event(775184, "two_flash"));
        events.add(new Event(775870, "two_flash"));
        events.add(new Event(776556, "two_flash"));
        events.add(new Event(777242, "two_flash"));
        events.add(new Event(777927, "two_flash"));
        events.add(new Event(778613, "two_flash"));
        events.add(new Event(779299, "two_flash"));
        events.add(new Event(779984, "two_flash"));
        events.add(new Event(780670, "two_flash"));
        events.add(new Event(781356, "two_flash"));
        events.add(new Event(784784, "two_flash"));
        events.add(new Event(786156, "two_flash"));
        events.add(new Event(787527, "two_flash"));
        events.add(new Event(788899, "two_flash"));
        events.add(new Event(790270, "two_flash"));
        events.add(new Event(791642, "two_flash"));
        events.add(new Event(793013, "two_flash"));
        events.add(new Event(795756, "two_flash"));
        events.add(new Event(797127, "two_flash"));
        events.add(new Event(798499, "two_flash"));
        events.add(new Event(799870, "two_flash"));
        events.add(new Event(801242, "two_flash"));
        events.add(new Event(802613, "two_flash"));
        events.add(new Event(803984, "two_flash"));
        events.add(new Event(805356, "two_flash"));
        events.add(new Event(809470, "two_flash"));
        events.add(new Event(810156, "two_flash"));
        events.add(new Event(810842, "two_flash"));
        events.add(new Event(811527, "two_flash"));
        events.add(new Event(812213, "two_flash"));
        events.add(new Event(812899, "two_flash"));
        events.add(new Event(813584, "two_flash"));
        events.add(new Event(814270, "two_flash"));
        events.add(new Event(814956, "two_flash"));
        events.add(new Event(815642, "two_flash"));
        events.add(new Event(816327, "two_flash"));
        events.add(new Event(817013, "two_flash"));
        events.add(new Event(817699, "two_flash"));
        events.add(new Event(818384, "two_flash"));
        events.add(new Event(819070, "two_flash"));
        events.add(new Event(819756, "two_flash"));
        events.add(new Event(820442, "two_flash"));
        events.add(new Event(821127, "two_flash"));
        events.add(new Event(821813, "two_flash"));
        events.add(new Event(822499, "two_flash"));
        events.add(new Event(823184, "two_flash"));
        events.add(new Event(823870, "two_flash"));
        events.add(new Event(824556, "two_flash"));
        events.add(new Event(825242, "two_flash"));
        events.add(new Event(825927, "two_flash"));
        events.add(new Event(826613, "two_flash"));
        events.add(new Event(827299, "two_flash"));
        events.add(new Event(827984, "two_flash"));
        events.add(new Event(828670, "two_flash"));
        events.add(new Event(829356, "two_flash"));
        events.add(new Event(830042, "two_flash"));
        events.add(new Event(830727, "two_flash"));
        events.add(new Event(832784, "two_flash"));
        events.add(new Event(833470, "two_flash"));
        events.add(new Event(834156, "two_flash"));
        events.add(new Event(834842, "two_flash"));
        events.add(new Event(835527, "two_flash"));
        events.add(new Event(836213, "two_flash"));
        events.add(new Event(836899, "two_flash"));
        events.add(new Event(837584, "two_flash"));
        events.add(new Event(838270, "two_flash"));
        events.add(new Event(838956, "two_flash"));
        events.add(new Event(839642, "two_flash"));
        events.add(new Event(840327, "two_flash"));
        events.add(new Event(841013, "two_flash"));
        events.add(new Event(841699, "two_flash"));
        events.add(new Event(842384, "two_flash"));
        events.add(new Event(843070, "two_flash"));
        events.add(new Event(843756, "two_flash"));
        events.add(new Event(844442, "two_flash"));
        events.add(new Event(845127, "two_flash"));
        events.add(new Event(845813, "two_flash"));
        events.add(new Event(846499, "two_flash"));
        events.add(new Event(847184, "two_flash"));
        events.add(new Event(847870, "two_flash"));
        events.add(new Event(848556, "two_flash"));
        events.add(new Event(849242, "two_flash"));
        events.add(new Event(849927, "two_flash"));
        events.add(new Event(850613, "two_flash"));
        events.add(new Event(851299, "two_flash"));
        events.add(new Event(851984, "two_flash"));
        events.add(new Event(852697, "two_flash"));

        events.add(new Event(752213, "three_flash"));
        events.add(new Event(752899, "three_flash"));
        events.add(new Event(760442, "three_flash"));
        events.add(new Event(761127, "three_flash"));
        events.add(new Event(761813, "three_flash"));
        events.add(new Event(762499, "three_flash"));
        events.add(new Event(763184, "three_flash"));
        events.add(new Event(763870, "three_flash"));
        events.add(new Event(764556, "three_flash"));
        events.add(new Event(765242, "three_flash"));
        events.add(new Event(765927, "three_flash"));
        events.add(new Event(766613, "three_flash"));
        events.add(new Event(767299, "three_flash"));
        events.add(new Event(767984, "three_flash"));
        events.add(new Event(768670, "three_flash"));
        events.add(new Event(769356, "three_flash"));
        events.add(new Event(770042, "three_flash"));
        events.add(new Event(770727, "three_flash"));
        events.add(new Event(771413, "three_flash"));
        events.add(new Event(772099, "three_flash"));
        events.add(new Event(772784, "three_flash"));
        events.add(new Event(774156, "three_flash"));
        events.add(new Event(774842, "three_flash"));
        events.add(new Event(775527, "three_flash"));
        events.add(new Event(776213, "three_flash"));
        events.add(new Event(776899, "three_flash"));
        events.add(new Event(777584, "three_flash"));
        events.add(new Event(778270, "three_flash"));
        events.add(new Event(778956, "three_flash"));
        events.add(new Event(779642, "three_flash"));
        events.add(new Event(780327, "three_flash"));
        events.add(new Event(781013, "three_flash"));
        events.add(new Event(784099, "three_flash"));
        events.add(new Event(785470, "three_flash"));
        events.add(new Event(786842, "three_flash"));
        events.add(new Event(788213, "three_flash"));
        events.add(new Event(789584, "three_flash"));
        events.add(new Event(790956, "three_flash"));
        events.add(new Event(792327, "three_flash"));
        events.add(new Event(795070, "three_flash"));
        events.add(new Event(796442, "three_flash"));
        events.add(new Event(797813, "three_flash"));
        events.add(new Event(799184, "three_flash"));
        events.add(new Event(800556, "three_flash"));
        events.add(new Event(801927, "three_flash"));
        events.add(new Event(803299, "three_flash"));
        events.add(new Event(804670, "three_flash"));
        events.add(new Event(806042, "three_flash"));
        events.add(new Event(809813, "three_flash"));
        events.add(new Event(810499, "three_flash"));
        events.add(new Event(811184, "three_flash"));
        events.add(new Event(811870, "three_flash"));
        events.add(new Event(812556, "three_flash"));
        events.add(new Event(813242, "three_flash"));
        events.add(new Event(813927, "three_flash"));
        events.add(new Event(814613, "three_flash"));
        events.add(new Event(815299, "three_flash"));
        events.add(new Event(815984, "three_flash"));
        events.add(new Event(816670, "three_flash"));
        events.add(new Event(817356, "three_flash"));
        events.add(new Event(818042, "three_flash"));
        events.add(new Event(818727, "three_flash"));
        events.add(new Event(819413, "three_flash"));
        events.add(new Event(820099, "three_flash"));
        events.add(new Event(820784, "three_flash"));
        events.add(new Event(821470, "three_flash"));
        events.add(new Event(822156, "three_flash"));
        events.add(new Event(822842, "three_flash"));
        events.add(new Event(823527, "three_flash"));
        events.add(new Event(824213, "three_flash"));
        events.add(new Event(824899, "three_flash"));
        events.add(new Event(825584, "three_flash"));
        events.add(new Event(826270, "three_flash"));
        events.add(new Event(826956, "three_flash"));
        events.add(new Event(827642, "three_flash"));
        events.add(new Event(828327, "three_flash"));
        events.add(new Event(829013, "three_flash"));
        events.add(new Event(829699, "three_flash"));
        events.add(new Event(830384, "three_flash"));
        events.add(new Event(831070, "three_flash"));
        events.add(new Event(833127, "three_flash"));
        events.add(new Event(833813, "three_flash"));
        events.add(new Event(834499, "three_flash"));
        events.add(new Event(835184, "three_flash"));
        events.add(new Event(835870, "three_flash"));
        events.add(new Event(836556, "three_flash"));
        events.add(new Event(837242, "three_flash"));
        events.add(new Event(837927, "three_flash"));
        events.add(new Event(838613, "three_flash"));
        events.add(new Event(839299, "three_flash"));
        events.add(new Event(839984, "three_flash"));
        events.add(new Event(840670, "three_flash"));
        events.add(new Event(841356, "three_flash"));
        events.add(new Event(842042, "three_flash"));
        events.add(new Event(842727, "three_flash"));
        events.add(new Event(843413, "three_flash"));
        events.add(new Event(844099, "three_flash"));
        events.add(new Event(844784, "three_flash"));
        events.add(new Event(845470, "three_flash"));
        events.add(new Event(846156, "three_flash"));
        events.add(new Event(846842, "three_flash"));
        events.add(new Event(847527, "three_flash"));
        events.add(new Event(848213, "three_flash"));
        events.add(new Event(848899, "three_flash"));
        events.add(new Event(849584, "three_flash"));
        events.add(new Event(850270, "three_flash"));
        events.add(new Event(850956, "three_flash"));
        events.add(new Event(851642, "three_flash"));
        events.add(new Event(852336, "three_flash"));
        events.add(new Event(853065, "three_flash"));

        events.add(new Event(783070, "two_flash"));
        events.add(new Event(794042, "two_flash"));
        events.add(new Event(783070, "three_flash"));
        events.add(new Event(794042, "three_flash"));
        // oh my god its over


        Collections.sort(events);


        // ----------------
        // [EVENT HANDLING]
        // ----------------

        while (currTime <= END_TIME) {

            System.out.print("Handling " + events.size() + " remaining events. [" + currTime + " ms]\r");

            // -------------------
            // [TRIGGERING EVENTS]
            // -------------------

            /* Check if there are remaining events to trigger */
            while (!events.isEmpty() && currTime >= events.getFirst().time) {

                Event nextEvent = events.getFirst();

                if (currTime >= nextEvent.time) {

                    /* Pulse two */
                    if (nextEvent.event.equals("two_pulse")) {
                        two.setScaleRate((0.2/PULSE_LENGTH)*1000);
                        two_flash.setScaleRate((0.2/PULSE_LENGTH)*1000);
                    }

                    /* Pulse three */
                    else if (nextEvent.event.equals("three_pulse")) {
                        three.setScaleRate((0.2/PULSE_LENGTH)*1000);
                        three_flash.setScaleRate((0.2/PULSE_LENGTH)*1000);
                    }

                    /* Flash two */
                    else if (nextEvent.event.equals("two_flash")) {
                        two_flash.setOpacity(FLASH_OPACITY);
                        two_flash.setFadeRate((-FLASH_OPACITY / FLASH_LENGTH) * 1000);
                    }

                    /* Flash three */
                    else if (nextEvent.event.equals("three_flash")) {
                        three_flash.setOpacity(FLASH_OPACITY);
                        three_flash.setFadeRate((-FLASH_OPACITY / FLASH_LENGTH) * 1000);
                    }

                    events.removeFirst();

                } // [END IF]

            } // [END IF]


            // -------------------------
            // [HANDLING CURRENT EVENTS]
            // -------------------------

            /* Decreasing pulse */
            if (two.getCurrScale()[0] < IMG_SCALE) {
                two.setScaleRate(0);
                two.setScale(IMG_SCALE);
            }
            if (two_flash.getCurrScale()[0] < IMG_SCALE) {
                two_flash.setScaleRate(0);
                two_flash.setScale(IMG_SCALE);
            }
            if (three.getCurrScale()[0] < IMG_SCALE) {
                three.setScaleRate(0);
                three.setScale(IMG_SCALE);
            }
            if (three_flash.getCurrScale()[0] < IMG_SCALE) {
                three_flash.setScaleRate(0);
                three_flash.setScale(IMG_SCALE);
            }

            /* Increasing pulse */
            if (two.getCurrScale()[0] > IMG_SCALE*PULSE_SCALE) {
                two.setScaleRate(((IMG_SCALE - IMG_SCALE*PULSE_SCALE) / PULSE_LENGTH) * 1000);
                two.setScale(IMG_SCALE*PULSE_SCALE);
            }
            if (two_flash.getCurrScale()[0] > IMG_SCALE*PULSE_SCALE) {
                two_flash.setScaleRate(((IMG_SCALE - IMG_SCALE*PULSE_SCALE) / PULSE_LENGTH) * 1000);
                two_flash.setScale(IMG_SCALE*PULSE_SCALE);
            }
            if (three.getCurrScale()[0] > IMG_SCALE*PULSE_SCALE) {
                three.setScaleRate(((IMG_SCALE - IMG_SCALE*PULSE_SCALE) / PULSE_LENGTH) * 1000);
                three.setScale(IMG_SCALE*PULSE_SCALE);
            }
            if (three_flash.getCurrScale()[0] > IMG_SCALE*PULSE_SCALE) {
                three_flash.setScaleRate(((IMG_SCALE - IMG_SCALE*PULSE_SCALE) / PULSE_LENGTH) * 1000);
                three_flash.setScale(IMG_SCALE*PULSE_SCALE);
            }

            // Flash is automatically handled by the setOpacity() and writeAll() functions


            // -------------------
            // [INCREMENTING TIME]
            // -------------------

            /* Increment time */
            two.addLength(TIME_RESOLUTION);
            two_flash.addLength(TIME_RESOLUTION);
            three.addLength(TIME_RESOLUTION);
            three_flash.addLength(TIME_RESOLUTION);
            currTime += TIME_RESOLUTION;


        } // [END WHILE]
        System.out.println();


        // -----------------
        // [THREE GLITCHING]
        // -----------------

        /* Initialize glitch sprites */
        DynamicSprite three_blue = new DynamicSprite( "storyboard\\numbers_three_white.png", GLITCH_START_TIME);
        DynamicSprite three_red = new DynamicSprite( "storyboard\\numbers_three_white.png", GLITCH_START_TIME);
        three_blue.setScale(IMG_SCALE);
        three_red.setScale(IMG_SCALE);
        three_blue.setColor(0, 255, 255);
        three_red.setColor(255, 0, 0);

        /* Apply glitch effect via random movement */
        double glitchTime = GLITCH_START_TIME;
        double glitchTimeResolution = calculateBeatLength(100)/2;
        while (glitchTime < GLITCH_END_TIME) {

            double blueOffsetX = -GLITCH_INTENSITY*RANDOM.nextDouble();
            double blueOffsetY = -GLITCH_INTENSITY + 2*GLITCH_INTENSITY*RANDOM.nextDouble();
            double redOffsetX = GLITCH_INTENSITY*RANDOM.nextDouble();
            double redOffsetY = -GLITCH_INTENSITY + 2*GLITCH_INTENSITY*RANDOM.nextDouble();
            three_blue.setPosition(threeX + blueOffsetX, threeY + blueOffsetY);
            three_red.setPosition(threeX + redOffsetX, threeY + redOffsetY);

            three_blue.addLength(glitchTimeResolution);
            three_red.addLength(glitchTimeResolution);
            glitchTime += glitchTimeResolution;
        }
        three_blue.writeAll();
        three_red.writeAll();

        /* Manually apply fade out */
        three_flash.addCommand(new FadeCommand(LINEAR, GLITCH_START_TIME, GLITCH_START_TIME, 1, 1));
        three_flash.addCommand(new FadeCommand(LINEAR, GLITCH_END_TIME, GLITCH_END_TIME+GLITCH_FADE_OUT, 1, 0));
        three_blue.addCommand(new FadeCommand(LINEAR, GLITCH_END_TIME, GLITCH_END_TIME+GLITCH_FADE_OUT, 1, 0));
        three_red.addCommand(new FadeCommand(LINEAR, GLITCH_END_TIME, GLITCH_END_TIME+GLITCH_FADE_OUT, 1, 0));

        /* Apply radial lines */
        Sprite three_lines = new Sprite( "storyboard\\three_lines.png");
        three_lines.scale(SINE_OUT, GLITCH_START_TIME, 566342, IMG_SCALE*0.6, IMG_SCALE*1.1);
        three_lines.scale(SINE_IN, 566342, 567542 + GLITCH_FADE_OUT, IMG_SCALE*1.1, IMG_SCALE*0.6);
        three_lines.move(GLITCH_START_TIME, threeX, threeY);
        three_lines.fade(SINE_IN_OUT, 564842, 564842 + 1200, 0, 0.8);
        three_lines.fade(SINE_IN, 567542, 567542 + 1200, 0.8, 0);
        three_lines.rotate(GLITCH_START_TIME, 567542 + GLITCH_FADE_OUT, 15, 40);



        // --------
        // [EXPORT]
        // --------

        two.writeAll();
        two_flash.writeAll();
        three.writeAll();
        three_flash.writeAll();

        /* Replace easing on scale commands */
        /* (Using exponential to accommodate for any "preempted" events) */
        for (int i = 0; i < two.getCommandCount(); i++) {
            if (two.getCommand(i).getCommandType() == VECTOR_SCALE)
                two.getCommand(i).setEasing(SINE_OUT);
        }
        for (int i = 0; i < three.getCommandCount(); i++) {
            if (three.getCommand(i).getCommandType() == VECTOR_SCALE)
                three.getCommand(i).setEasing(SINE_OUT);
        }
        for (int i = 0; i < two_flash.getCommandCount(); i++) {
            if (two_flash.getCommand(i).getCommandType() == VECTOR_SCALE)
                two_flash.getCommand(i).setEasing(SINE_OUT);
        }
        for (int i = 0; i < three.getCommandCount(); i++) {
            if (three_flash.getCommand(i).getCommandType() == VECTOR_SCALE)
                three_flash.getCommand(i).setEasing(SINE_OUT);
        }

        file.addObjects(three_blue, three_red, two, three_lines, three, two_flash, three_flash);
        file.exportBinary(BIN_DIRECTORY + BIN_NAME);
        file.export(OSB_DIRECTORY + OSB_NAME);


    } // [END MAIN]


}