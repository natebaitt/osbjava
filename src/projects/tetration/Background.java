package projects.tetration;

import osbjava.objects.*;
import static osbjava.types.OsbEasing.*;


@SuppressWarnings("unused")
public class Background {


    // ============================================================
    // [GLOBAL VARIABLES]


    //static final String OSB_FOLDER             = "tetration (a BFDITPOT medley)";
    static final String OSB_DIRECTORY          = ".\\src\\projects\\tetration\\beatmap\\";
    static final String OSB_NAME               = "nate bait - tetration (a BFDITPOT medley) (Nate Bait).osb";
    static final String BIN_DIRECTORY          = ".\\src\\projects\\tetration\\bin\\";
    static final String BIN_NAME               = "background.bin";

    static final double IMG_SCALE              = 0.4445;
    static final double BASE_TRANSITION_LENGTH = 1200;


    // ============================================================

    // [MAIN METHOD]

    public static void main(String[] args) {

        OsbFile file = new OsbFile();

        Sprite m1_dark;
        Sprite m1_bg, m2_bg, m3_bg, m4_bg, m4_ending;

        /* Movement I */
        m1_dark = new Sprite( "storyboard\\bg_dark.png");
        m1_dark.scale(0, IMG_SCALE);
        m1_dark.fade(QUAD_IN_OUT, 38596, 43169, 1, 0);
        m1_bg = new Sprite( "storyboard\\bg_1.png");
        m1_bg.scale(38596, 214532 + BASE_TRANSITION_LENGTH, IMG_SCALE);
        m1_bg.fade(214532, 214532 + BASE_TRANSITION_LENGTH, 1, 0);
        m1_bg.color(47714, 54532, 200, 200, 200, 255, 255, 255);

        /* Movement II */
        m2_bg = new Sprite( "storyboard\\bg_2.png");
        m2_bg.scale(214532, 386042 + BASE_TRANSITION_LENGTH, IMG_SCALE);
        m2_bg.fade(386042, 386042 + BASE_TRANSITION_LENGTH, 1, 0);

        /* Movement III */
        m3_bg = new Sprite( "storyboard\\bg_3.png");
        m3_bg.scale(386042, 570842 + BASE_TRANSITION_LENGTH, IMG_SCALE);
        m3_bg.fade(570842, 570842 + BASE_TRANSITION_LENGTH, 1, 0);

        /* Movement IV */
        m4_bg = new Sprite( "storyboard\\bg_4.png");
        m4_bg.scale(570842, 740556 + BASE_TRANSITION_LENGTH, IMG_SCALE);
        m4_bg.fade(740556, 740556 + BASE_TRANSITION_LENGTH, 1, 0);
        m4_ending = new Sprite( "storyboard\\bg_2.png");
        m4_ending.scale(740556, 853657, IMG_SCALE);

        /* Adding in order */
        file.addObjects(m4_ending, m4_bg, m3_bg, m2_bg, m1_bg, m1_dark);


        // --------
        // [EXPORT]
        // --------

        file.exportBinary(BIN_DIRECTORY + BIN_NAME);
        file.export(OSB_DIRECTORY + OSB_NAME);


    }

}
