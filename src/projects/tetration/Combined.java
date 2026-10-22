package projects.tetration;

import osbjava.objects.*;


@SuppressWarnings("unused")
public class Combined {


    // ============================================================
    // [GLOBAL VARIABLES]


    //static final String OSB_FOLDER           = "tetration (a BFDITPOT medley)";
    static final String OSB_DIRECTORY        = ".\\src\\projects\\tetration\\beatmap\\";
    static final String OSB_NAME             = "nate bait - tetration (a BFDITPOT medley) (Nate Bait).osb";
    static final String BIN_DIRECTORY        = ".\\src\\projects\\tetration\\bin\\";
    static final String BIN_NAME             = "combined.bin";


    // ============================================================
    // [MAIN METHOD]


    public static void main(String[] args) {

        OsbFile background          = new OsbFile(BIN_DIRECTORY + "background.bin");
        OsbFile radialLines         = new OsbFile(BIN_DIRECTORY + "radial_lines.bin");
        OsbFile splashText          = new OsbFile(BIN_DIRECTORY + "splash_text.bin");
        OsbFile contestantScroller  = new OsbFile(BIN_DIRECTORY + "contestant_scroller.bin");
        OsbFile blurGenerator       = new OsbFile(BIN_DIRECTORY + "blur_generator.bin");
        OsbFile twoThree            = new OsbFile(BIN_DIRECTORY + "two_three.bin");
        OsbFile foreground          = new OsbFile(BIN_DIRECTORY + "foreground.bin");
        OsbFile currentMovementText = new OsbFile(BIN_DIRECTORY + "current_movement_text.bin");
        OsbFile currentSongText     = new OsbFile(BIN_DIRECTORY + "current_song_text.bin");
        OsbFile transitions         = new OsbFile(BIN_DIRECTORY + "transitions.bin");
        OsbFile main = new OsbFile();

        main.combine(background,
                     radialLines,
                     splashText,
                     contestantScroller,
                     blurGenerator,
                     twoThree,
                     foreground,
                     currentMovementText,
                     currentSongText,
                     transitions
        );
        main.exportBinary(BIN_DIRECTORY + BIN_NAME);
        main.export(OSB_DIRECTORY + OSB_NAME);

    } // [END MAIN]


}
