package projects.tetration;

import osbjava.objects.*;
import static osbjava.types.OsbOrigin.*;
import static osbjava.types.OsbEasing.*;


@SuppressWarnings("unused")
public class Transitions {

    // ============================================================
    // [GLOBAL VARIABLES]


    //static final String OSB_FOLDER     = "tetration (a BFDITPOT medley)";
    static final String OSB_DIRECTORY  = ".\\src\\projects\\tetration\\beatmap\\";
    static final String OSB_NAME       = "nate bait - tetration (a BFDITPOT medley) (Nate Bait).osb";
    static final String BIN_DIRECTORY  = ".\\src\\projects\\tetration\\bin\\";
    static final String BIN_NAME       = "transitions.bin";

    static final double IMG_SCALE      = 0.4445;
    static final String TEXT_DIRECTORY = "storyboard\\text\\";


    // ============================================================

    // [MAIN METHOD]

    public static void main(String[] args) {

        OsbFile file = new OsbFile();

        /* Black BG */
        Sprite black;
        black = new Sprite("storyboard\\bg_black.png");
        black.scale(0, IMG_SCALE * 2);
        black.fade(17398, 20785, 1, 0);
        black.fade(853657, 891894, 1);
        file.addObject(black);

        /* White BG */
        Sprite white;
        white = new Sprite("storyboard\\bg_white.png");
        white.scale(847870, 853657, IMG_SCALE * 2);
        white.fade(QUAD_IN, 847870, 853457, 0, 1);
        file.addObject(white);

        /* Transitions */
        Sprite transition1, yeah;
        transition1 = new Sprite("storyboard\\transition.png", TOP_CENTER);
        transition1.scale(50442, IMG_SCALE * 2);
        transition1.fade(50442, 54305, 0, 1);
        transition1.move(QUAD_IN, 54760, 54760 + 300, 320, 0, 320, -1000);
        file.addObject(transition1);
        yeah = new Sprite(TEXT_DIRECTORY + "splash_1.png", TOP_CENTER);
        yeah.scale(54532, IMG_SCALE);
        yeah.color(54532, 50, 76, 205);
        yeah.move(QUAD_IN, 54760, 54760 + 300, 320, 0, 320, -1000);
        file.addObject(yeah);

        Sprite transition2;
        transition2 = new Sprite("storyboard\\transition.png", TOP_CENTER);
        transition2.scale(750499, IMG_SCALE * 2);
        transition2.color(750499, 255, 255, 255);
        transition2.fade(750499, 754613, 0, 1);
        transition2.color(754613, 0, 0, 0);
        transition2.move(QUAD_IN, 759842, 759842 + 300, 320, 0, 320, -1000);
        file.addObject(transition2);

        /* Intro */
        Sprite intro1, intro2, intro3, intro4;
        intro1 = new Sprite(TEXT_DIRECTORY + "intro_1.png");
        intro1.scale(1914, 5785, IMG_SCALE * 0.4);
        intro2 = new Sprite(TEXT_DIRECTORY + "intro_2.png");
        intro2.scale(5785, 9656, IMG_SCALE);
        intro3 = new Sprite(TEXT_DIRECTORY + "intro_3.png");
        intro3.scale(9656, 13527, IMG_SCALE);
        intro4 = new Sprite(TEXT_DIRECTORY + "intro_4.png");
        intro4.scale(13527, 17398, IMG_SCALE);
        intro4.fade(17398, 18366, 1, 0);
        file.addObjects(intro1, intro2, intro3, intro4);

        /* Outro */
        Sprite outro1, outro2;
        outro1 = new Sprite(TEXT_DIRECTORY + "outro_1.png");
        outro1.scale(858257, IMG_SCALE);
        outro1.fade(858257, 861457, 0, 1);
        outro1.fade(866257, 869457, 1, 0);
        outro2 = new Sprite(TEXT_DIRECTORY + "outro_2.png");
        outro2.scale(869457, IMG_SCALE);
        outro2.fade(869457, 872657, 0, 1);
        outro2.fade(875857, 879057, 1, 0);
        file.addObjects(outro1, outro2);

        // --------
        // [EXPORT]
        // --------

        file.exportBinary(BIN_DIRECTORY + BIN_NAME);
        file.export(OSB_DIRECTORY + OSB_NAME);


    }

}
