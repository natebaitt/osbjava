package projects.tetration;

import osbjava.objects.*;
import osbjava.types.OsbEasing;
import osbjava.types.OsbOrigin;
import java.util.*;
import static osbjava.types.OsbEasing.*;
import static osbjava.types.OsbOrigin.*;


@SuppressWarnings("unused")
public class CurrentMovementText {


    // ============================================================
    // [GLOBAL VARIABLES]


    //static final String OSB_FOLDER             = "tetration (a BFDITPOT medley)";
    static final String OSB_DIRECTORY          = ".\\src\\projects\\tetration\\beatmap\\";
    static final String OSB_NAME               = "nate bait - tetration (a BFDITPOT medley) (Nate Bait).osb";
    static final String BIN_DIRECTORY          = ".\\src\\projects\\tetration\\bin\\";
    static final String BIN_NAME               = "current_movement_text.bin";

    //static final double START_TIME            = 54987;
    //static final double END_TIME              = 104987;
    //static final double TIME_RESOLUTION       = 10;

    static final double    IMG_SCALE           = 0.4445;
    static final double    IMG_HEIGHT          = 83;
    static final OsbOrigin IMG_ORIGIN          = TOP_LEFT;
    static final double    START_X             = -90;
    static final double    START_Y             = 18;

    static final String    TEXT_DIRECTORY      = "storyboard\\text\\";
    static final OsbEasing TRANSITION_EASING   = QUAD_IN_OUT;
    static final double    TRANSITION_LENGTH   = 800;
    static final double    MOTION_OFFSET       = -16;
    static final double    PADDING             = 10;


    // ============================================================
    // [HELPERS]


    /* Subclass for handling OSB commands when appending or removing text */
    static class TextArray {

        ArrayList<Sprite> textArray = new ArrayList<>();
        String baseColor = "ffffff";

        public int getArrayLength() {
            return textArray.size();
        }


        public void setBaseColor(double time, double length, String baseColor) {
            for (Sprite existingText : textArray)
                existingText.color(time, time+length, new OsbColor(this.baseColor), new OsbColor(baseColor));
            this.baseColor = baseColor;
        }


        public void appendText(double time, int index, String filename) {

            /* Initialize new text */
            Sprite newText = new Sprite(TEXT_DIRECTORY + filename, IMG_ORIGIN);
            newText.scale(time, IMG_SCALE);
            newText.color(time, new OsbColor(baseColor));

            /* Transition new text (slide in) */
            newText.fade(TRANSITION_EASING, time, time+TRANSITION_LENGTH, 0, 1);
            newText.move(TRANSITION_EASING, time, time+TRANSITION_LENGTH,
                                            START_X + MOTION_OFFSET, START_Y + index*(IMG_HEIGHT*IMG_SCALE + PADDING),
                                            START_X, START_Y + index*(IMG_HEIGHT*IMG_SCALE + PADDING));

            /* Transition existing text (move down) */
            for (int i = index; i < this.getArrayLength(); i++) {
                Sprite existingText = this.textArray.get(i);
                existingText.move(TRANSITION_EASING, time, time+TRANSITION_LENGTH,
                                  START_X, START_Y + i*(IMG_HEIGHT*IMG_SCALE + PADDING),
                                  START_X, START_Y + (i+1)*(IMG_HEIGHT*IMG_SCALE + PADDING));
            }

            /* Add sprite to array */
            this.textArray.add(index, newText);
        }


        public Sprite replaceText(double time, int index, String filename) {

            /* Initialize new text */
            Sprite newText = new Sprite(TEXT_DIRECTORY + filename, IMG_ORIGIN);
            newText.scale(time, IMG_SCALE);
            newText.color(time, new OsbColor(baseColor));

            /* Transition new text (slide in) */
            newText.fade(TRANSITION_EASING, time, time+TRANSITION_LENGTH, 0, 1);
            newText.move(TRANSITION_EASING, time, time+TRANSITION_LENGTH,
                         START_X + MOTION_OFFSET, START_Y + index*(IMG_HEIGHT*IMG_SCALE + PADDING),
                         START_X, START_Y + index*(IMG_HEIGHT*IMG_SCALE + PADDING));

            /* Transition old text (slide out) */
            Sprite oldText = this.textArray.get(index);
            oldText.fade(TRANSITION_EASING, time, time+TRANSITION_LENGTH, 1, 0);
            oldText.move(TRANSITION_EASING, time, time+TRANSITION_LENGTH,
                         START_X, START_Y + index*(IMG_HEIGHT*IMG_SCALE + PADDING),
                         START_X + MOTION_OFFSET, START_Y + index*(IMG_HEIGHT*IMG_SCALE + PADDING));

            /* Add sprite to array and return and remove old sprite */
            this.textArray.remove(index);
            this.textArray.add(index, newText);
            return oldText;
        }


        public Sprite removeText(double time, int index) {

            /* Transition old text (slide out) */
            Sprite oldText = this.textArray.get(index);
            oldText.fade(TRANSITION_EASING, time, time+TRANSITION_LENGTH, 1, 0);
            oldText.move(TRANSITION_EASING, time, time+TRANSITION_LENGTH,
                                            START_X, START_Y + index*(IMG_HEIGHT*IMG_SCALE + PADDING),
                                            START_X + MOTION_OFFSET, START_Y + index*(IMG_HEIGHT*IMG_SCALE + PADDING));

            /* Transition existing text (move up) */
            for (int i = index+1; i < this.getArrayLength(); i++) {
                Sprite existingText = this.textArray.get(i);
                existingText.move(TRANSITION_EASING, time, time+TRANSITION_LENGTH,
                                  START_X, START_Y + i*(IMG_HEIGHT*IMG_SCALE + PADDING),
                                  START_X, START_Y + (i-1)*(IMG_HEIGHT*IMG_SCALE + PADDING));
            }

            /* Remove and return sprite from array */
            this.textArray.remove(index);
            return oldText;
        }


        public List<OsbObject> removeAllText(double time) {

            List<OsbObject> oldTexts = new ArrayList<>();

            /* Transition existing text (slide out) */
            for (int i = 0; i < this.getArrayLength(); i++) {
                Sprite existingText = textArray.get(i);
                existingText.fade(TRANSITION_EASING, time, time+TRANSITION_LENGTH, 1, 0);
                existingText.move(TRANSITION_EASING, time, time+TRANSITION_LENGTH,
                                  START_X, START_Y + i*(IMG_HEIGHT*IMG_SCALE + PADDING),
                                  START_X + MOTION_OFFSET, START_Y + i*(IMG_HEIGHT*IMG_SCALE + PADDING));
                oldTexts.add(existingText);
            }

            /* Remove and return all sprites from array */
            this.textArray.clear();
            return oldTexts;
        }


        public List<OsbObject> removeAllAbrupt(double time) {

            List<OsbObject> oldTexts = new ArrayList<>();

            /* Transition existing text (slide out) */
            for (int i = 0; i < this.getArrayLength(); i++) {
                Sprite existingText = textArray.get(i);
                existingText.fade(time, 0);
                oldTexts.add(existingText);
            }

            /* Remove and return all sprites from array */
            this.textArray.clear();
            return oldTexts;
        }


    } // [END TEXT ARRAY]


    // ============================================================
    // [MAIN METHOD]


    public static void main(String[] args) {

        OsbFile file = new OsbFile();

        /* Variable for handling text array events */
        TextArray textArray = new TextArray();


        /* Movement I */
        textArray.appendText(17398, 0, "movement_1.png");
        textArray.setBaseColor(54532, 1, "324ccd");

        /* Movement II */
        file.addObject(textArray.replaceText(214532, 0, "movement_2.png"));
        textArray.setBaseColor(214532, 1200, "005901");

        /* Movement III */
        file.addObject(textArray.replaceText(386042, 0, "movement_3.png"));
        textArray.setBaseColor(386042, 1200, "af0200");

        /* Movement IV */
        file.addObject(textArray.replaceText(570842, 0, "movement_4.png"));
        textArray.setBaseColor(570842, 1200, "13589d");
        textArray.setBaseColor(740556, 1200, "008000");
        file.addObjects(textArray.removeAllAbrupt(853657));


        // --------
        // [EXPORT]
        // --------

        file.exportBinary(BIN_DIRECTORY + BIN_NAME);
        file.export(OSB_DIRECTORY + OSB_NAME);



    } // [END MAIN]


}
