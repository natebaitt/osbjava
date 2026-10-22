package projects.tetration;

import osbjava.objects.*;
import osbjava.types.OsbEasing;
import osbjava.types.OsbOrigin;
import java.util.*;
import static osbjava.types.OsbEasing.*;
import static osbjava.types.OsbOrigin.*;


@SuppressWarnings("unused")
public class CurrentSongText {


    // ============================================================
    // [GLOBAL VARIABLES]


    //static final String OSB_FOLDER             = "tetration (a BFDITPOT medley)";
    static final String OSB_DIRECTORY          = ".\\src\\projects\\tetration\\beatmap\\";
    static final String OSB_NAME               = "nate bait - tetration (a BFDITPOT medley) (Nate Bait).osb";
    static final String BIN_DIRECTORY          = ".\\src\\projects\\tetration\\bin\\";
    static final String BIN_NAME               = "current_song_text.bin";

    //static final double START_TIME            = 54987;
    //static final double END_TIME              = 104987;
    //static final double TIME_RESOLUTION       = 10;

    static final double    IMG_SCALE           = 0.4445;
    static final double    IMG_HEIGHT          = 83;
    static final OsbOrigin IMG_ORIGIN          = TOP_RIGHT;
    static final double    START_X             = 730;
    static final double    START_Y             = 18;

    static final String    TEXT_DIRECTORY      = "storyboard\\text\\";
    static final OsbEasing TRANSITION_EASING   = QUAD_IN_OUT;
    static final double    TRANSITION_LENGTH   = 800;
    static final double    MOTION_OFFSET       = 16;
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
        textArray.appendText(17398, 0, "m1_dream_island.png");
        file.addObject(textArray.removeText(40442, 0));
        textArray.appendText(47714, 0, "m1_new_friendly.png");
        textArray.setBaseColor(54532, 1, "324ccd");
        file.addObject(textArray.replaceText(104987, 0, "m1_places.png"));
        file.addObject(textArray.replaceText(134532, 0, "m1_even_after.png"));
        file.addObject(textArray.replaceText(149987, 0, "m1_evolekaf.png"));
        textArray.appendText(151351, 0, "m1_pixel_peeker_polka.png");
        file.addObject(textArray.replaceText(163263, 0, "m1_even_after.png"));
        file.addObjects(textArray.removeAllText(178396));
        textArray.appendText(178396, 0, "m1_famous_600k.png");
        textArray.appendText(181123, 1, "m1_evolekaf.png");
        textArray.appendText(183851, 1, "m1_even_after.png");
        file.addObjects(textArray.removeAllText(211123));


        /* Movement II */
        textArray.setBaseColor(214759, 0, "005901");
        textArray.appendText(214760, 0, "m2_readme_txt.png");
        textArray.appendText(243851, 0, "m2_movement_proposition.png");
        file.addObjects(textArray.removeAllText(258623));
        textArray.appendText(265669, 0, "m2_ballers.png");
        file.addObject(textArray.replaceText(294760, 0, "m2_votely.png"));
        file.addObject(textArray.replaceText(324078, 0, "m2_hey_two.png"));
        file.addObject(textArray.replaceText(338851, 0, "m2_votely.png"));
        file.addObject(textArray.replaceText(353055, 0, "m2_who_likes_to_party.png"));
        file.addObjects(textArray.removeAllText(385442));


        /* Movement III */
        textArray.setBaseColor(389941, 0, "af0200");
        textArray.appendText(389942, 0, "m3_bnob.png");
        textArray.appendText(428492, 0, "m3_night_mote.png");
        file.addObjects(textArray.removeAllText(466742));
        textArray.appendText(466742, 0, "m3_hallway.png");
        file.addObject(textArray.replaceText(486092, 0, "m3_branchless.png"));
        file.addObjects(textArray.removeAllText(524492));
        textArray.appendText(524492, 0, "m3_gamla.png");
        textArray.appendText(524492, 1, "m3_hush.png");
        file.addObjects(textArray.removeAllText(548042));
        textArray.appendText(548042, 0, "m3_hit_it.png");
        file.addObjects(textArray.removeAllText(567542));


        /* Movement IV */
        textArray.setBaseColor(570841, 0, "13589d");
        textArray.appendText(570842, 0, "m4_infetterence.png");
        textArray.appendText(576156, 1, "m4_postehaste.png");
        file.addObject(textArray.removeText(595527, 1));
        textArray.appendText(619870, 1, "m4_postehaste.png");
        file.addObject(textArray.removeText(630842, 1));
        file.addObject(textArray.replaceText(683299, 0, "m4_breakup_simulator_lite.png"));
        file.addObjects(textArray.removeAllText(707642));
        textArray.appendText(707642, 0, "m4_shirtsong.png");
        textArray.appendText(707642, 1, "m4_burst.png");
        file.addObjects(textArray.removeAllText(729927));
        textArray.setBaseColor(757355, 0, "008000");
        textArray.appendText(757356, 0, "m4_gibberish.png");
        file.addObject(textArray.replaceText(809127, 0, "m4_music_of_fours_3d_hand.png"));
        file.addObject(textArray.replaceText(814784, 0, "m4_this_is_bfb_season_recap.png"));
        file.addObject(textArray.replaceText(820270, 0, "m4_music_of_fours_3d_hand.png"));
        file.addObject(textArray.replaceText(825756, 0, "m4_gibberish.png"));
        file.addObject(textArray.replaceText(831927, 0, "m4_idfb_theme.png"));
        file.addObject(textArray.replaceText(846499, 0, "m4_gibberish.png"));
        file.addObjects(textArray.removeAllAbrupt(853657));


        // --------
        // [EXPORT]
        // --------

        file.exportBinary(BIN_DIRECTORY + BIN_NAME);
        file.export(OSB_DIRECTORY + OSB_NAME);



    } // [END MAIN]


}
