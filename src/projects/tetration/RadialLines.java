package projects.tetration;

import osbjava.objects.*;
import java.util.*;


@SuppressWarnings("unused")
public class RadialLines {


    // ============================================================
    // [GLOBAL VARIABLES]


    //static final String OSB_FOLDER      = "tetration (a BFDITPOT medley)";
    static final String OSB_DIRECTORY   = ".\\src\\projects\\tetration\\beatmap\\";
    static final String OSB_NAME        = "nate bait - tetration (a BFDITPOT medley) (Nate Bait).osb";
    static final String BIN_DIRECTORY   = ".\\src\\projects\\tetration\\bin\\";
    static final String BIN_NAME        = "radial_lines.bin";

    static final double START_TIME      = 47714;
    static final double END_TIME        = 853657;
    //static final double TIME_RESOLUTION = 10;

    static final int    LINE_COUNT      = 8;
    static final double SCALE           = 0.4445;
    static final double OPACITY         = 0.6;
    static final double ROTATE_RATE     = 10;
    static final double FADE_IN_LENGTH  = 51351 - 47714;


    // ============================================================
    // [MAIN METHOD]


    public static void main(String[] args) {

        OsbFile file = new OsbFile();

        ArrayList<DynamicSprite> lines = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            DynamicSprite line = new DynamicSprite("storyboard\\radial_line.png", START_TIME);
            line.setScale(SCALE);
            line.setOpacity(0);
            line.setFadeRate((OPACITY/FADE_IN_LENGTH)*1000);
            line.setAngle(i * (180.0 / LINE_COUNT));
            line.setRotateRate(ROTATE_RATE);
            lines.add(line);
        }

        for (DynamicSprite line : lines) {
            line.addLength(FADE_IN_LENGTH);
            line.setFadeRate(0);
            line.addLength(END_TIME - (START_TIME+FADE_IN_LENGTH));
        }


        // --------
        // [EXPORT]
        // --------

        for (DynamicSprite line : lines) {
            line.writeAll();
            file.addObject(line);
        }
        file.exportBinary(BIN_DIRECTORY + BIN_NAME);
        file.export(OSB_DIRECTORY + OSB_NAME);


    } // [END MAIN]


}
