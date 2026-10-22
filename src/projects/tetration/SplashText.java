package projects.tetration;

import osbjava.objects.*;


@SuppressWarnings("unused")
public class SplashText {


    // ============================================================
    // [GLOBAL VARIABLES]


    //static final String OSB_FOLDER     = "tetration (a BFDITPOT medley)";
    static final String OSB_DIRECTORY  = ".\\src\\projects\\tetration\\beatmap\\";
    static final String OSB_NAME       = "nate bait - tetration (a BFDITPOT medley) (Nate Bait).osb";
    static final String BIN_DIRECTORY  = ".\\src\\projects\\tetration\\bin\\";
    static final String BIN_NAME       = "splash_text.bin";

    static final double IMG_SCALE      = 0.4445;
    static final String TEXT_DIRECTORY = "storyboard\\text\\";
    static final double OPACITY        = 0.6;
    //static final double BASE_TRANSITION_LENGTH = 1200;


    // ============================================================

    // [MAIN METHOD]

    public static void main(String[] args) {

        OsbFile file = new OsbFile();

        /* HOW DARE YOU! */
        Sprite how, dare, you, slap, gasp;
        how = new Sprite(TEXT_DIRECTORY + "how.png");
        how.scale(177260, IMG_SCALE);
        how.fade(177260, OPACITY);
        how.color(177260, 177714, 50, 76, 205);
        dare = new Sprite(TEXT_DIRECTORY + "dare.png");
        dare.scale(177714, IMG_SCALE);
        dare.fade(177714, OPACITY);
        dare.color(177714, 178169, 50, 76, 205);
        you = new Sprite(TEXT_DIRECTORY + "you.png");
        you.scale(178169, IMG_SCALE);
        you.fade(178169, OPACITY);
        you.color(178169, 178623, 50, 76, 205);
        slap = new Sprite(TEXT_DIRECTORY + "splash_2.png");
        slap.scale(193396, IMG_SCALE);
        slap.fade(193396, 193737, OPACITY, 0);
        slap.color(193396, 50, 76, 205);
        gasp = new Sprite(TEXT_DIRECTORY + "splash_3.png");
        gasp.scale(193851, IMG_SCALE);
        gasp.fade(193851, 194192, OPACITY, 0);
        gasp.color(193851, 50, 76, 205);
        file.addObjects(how, dare, you, slap, gasp);


        /* Switch */
        // switch is a keyword lol
        Sprite switchh;
        switchh = new Sprite(TEXT_DIRECTORY + "splash_4.png");
        switchh.scale(664442, IMG_SCALE);
        switchh.color(664442, 19, 88, 157);
        for (int i = 0; i < 16; i++)
            switchh.fade(664442 + i*(60.0*1000/175)*2, 665042 + i*(60.0*1000/175)*2, OPACITY*0.6666, 0);
        file.addObject(switchh);


        // --------
        // [EXPORT]
        // --------

        file.exportBinary(BIN_DIRECTORY + BIN_NAME);
        file.export(OSB_DIRECTORY + OSB_NAME);


    }

}
