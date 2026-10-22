package projects.tetration;

import osbjava.objects.*;
import static osbjava.types.OsbEasing.*;


@SuppressWarnings("unused")
public class Foreground {


    // ============================================================
    // [GLOBAL VARIABLES]


    //static final String OSB_FOLDER             = "tetration (a BFDITPOT medley)";
    static final String OSB_DIRECTORY          = ".\\src\\projects\\tetration\\beatmap\\";
    static final String OSB_NAME               = "nate bait - tetration (a BFDITPOT medley) (Nate Bait).osb";
    static final String BIN_DIRECTORY          = ".\\src\\projects\\tetration\\bin\\";
    static final String BIN_NAME               = "foreground.bin";

    static final double IMG_SCALE              = 0.4445;
    //static final double BASE_TRANSITION_LENGTH = 1200;


    // ============================================================

    // [MAIN METHOD]

    public static void main(String[] args) {

        OsbFile file = new OsbFile();

        Sprite vignette, gradient;
        vignette = new Sprite( "storyboard\\vignette.png");
        vignette.scale(17398, 54532, IMG_SCALE * 2);
        vignette.fade(49532, 54532, 0.6, 0.2);
        gradient = new Sprite( "storyboard\\gradient.png");
        gradient.scale(54532, 853657, IMG_SCALE * 2);
        file.addObjects(vignette, gradient);


        // --------
        // [EXPORT]
        // --------

        file.exportBinary(BIN_DIRECTORY + BIN_NAME);
        file.export(OSB_DIRECTORY + OSB_NAME);


    }

}
