package projects.tetration;

import osbjava.objects.OsbColor;
import osbjava.objects.OsbFile;

import java.util.*;

import static osbjava.types.OsbLayer.BACKGROUND;
import static osbjava.types.OsbOrigin.CENTER;


@SuppressWarnings("unused")
public class ContestantScrollerBackup {


    // ============================================================
    // [GLOBAL VARIABLES]


    //static final String   OSB_FOLDER           = "tetration (a BFDITPOT medley)";
    static final String   OSB_DIRECTORY        = ".\\src\\projects\\tetration\\beatmap\\";
    static final String   OSB_NAME             = "nate bait - tetration (a BFDITPOT medley) (Nate Bait).osb";
    static final String   BIN_DIRECTORY        = ".\\src\\projects\\tetration\\bin\\";
    static final String   BIN_NAME             = "contestant_scroller.bin";

    static final double[] TEMPOS               = {124, 132, 100, 175};
    static final double[] BEAT_LENGTHS         = Arrays.stream(TEMPOS).map(tempo -> (60/tempo)*1000).toArray();
    static final String[] BASE_COLORS          = {"008000"};

    static final double   START_TIME           = 1000;
    static final double   END_TIME             = 60000;
    static final double   TIME_RESOLUTION      = 10;

    static final double   IMG_SCALE            = 0.4445;
    static final double   MIN_X                = -180;
    static final double   MAX_X                = 760;
    static final double   MIN_Y                = -200;
    static final double   MAX_Y                = 700;

    static final String   CONTESTANT_DIRECTORY = "storyboard\\bodies\\";
    static final double   CONTESTANT_SCALE     = 0.24;
    static final double   CONTESTANT_VELOCITY  = 72;
    static final double   CONTESTANT_PADDING   = 12;
    static final double   ANGLE_MAGNITUDE      = 12;
    static final double   PACKING_LENIENCY     = 2;
    static final Random   RANDOM               = new Random(System.currentTimeMillis());


    // ============================================================
    // [HELPERS]


    /* Subclass for handling timed events */
    static class Event implements Comparable<Event> {
        double time;
        String event;
        String value;

        public Event(double time, String event) {
            this.time = time;
            this.event = event;
            this.value = null;
        }

        public Event(double time, String event, String value) {
            this.time = time;
            this.event = event;
            this.value = value;
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
    public static ContestantSprite generateContestantSprite(Contestant contestant, double time) {

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
        sprite.initializeColors(new OsbColor(BASE_COLORS[0]));
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


    /* Generate a randomly ordered array of a range of integers */
    public static ArrayList<Integer> generateRandomArray(int range) {
        ArrayList<Integer> array = new ArrayList<>();
        for (int i = 0; i < range; i++)
            array.add(i);
        Collections.shuffle(array);
        return array;
    }


    // ============================================================
    // [MAIN METHOD]


    public static void main(String[] args) {

        OsbFile file = new OsbFile();

        /* Variables for sprites and events */
        ArrayList<ContestantSprite> sprites = new ArrayList<>();
        ArrayList<Event>            events  = new ArrayList<>();

        /* Variables for keeping track of time */
        double currTime = START_TIME;
        double refTime  = START_TIME;

        /* Variables for sprite generation */
        double genCoordX = MIN_X;    // These indicate the BOTTOM-LEFT coords to generate each incoming sprite.
        double genCoordY = MAX_Y;    // However, the origin of the sprites must be CENTER for proper rotation.
        Contestant nextContestant = null;
        ArrayList<Integer> nextContestantIndices = new ArrayList<>();


        // --------------------
        // [INITIAL GENERATION]
        // --------------------

        boolean initialGeneration = true;
        int initialGenerationCount = 0;

        while (initialGeneration) {

            /* Generate new sprite */
            if (nextContestantIndices.isEmpty())
                nextContestantIndices = generateRandomArray(Contestant.length());
            nextContestant = Contestant.toEnum((nextContestantIndices.getFirst()));
            nextContestantIndices.removeFirst();
            ContestantSprite newSprite = generateContestantSprite(nextContestant, START_TIME);

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
        boolean readyToAppendNextSprite = false;
        double appendTime = START_TIME;
        int activeGenerationCount = 0;

        while (currTime <= END_TIME) {

            System.out.print("Generated " + activeGenerationCount + " active sprites. [" + currTime + " ms]\r");

            // --------------------
            // [ADDING NEW SPRITES]
            // --------------------

            // Generating a new sprite and checking if it fits in every loop iteration would
            // make things much slower, so this if-else accommodates that potential issue.

            /* If readied, check if there is enough space to append next sprite */
            if (readyToAppendNextSprite) {

                // System.out.println("READY. Append time is " + appendTime + ", curr time is " + currTime);

                if (currTime >= appendTime) {
                    ContestantSprite appendedSprite = new ContestantSprite(
                            nextSprite,
                            appendTime,
                            genCoordX + nextSprite.getSpriteWidth()/2,
                            genCoordY - nextSprite.getSpriteHeight()/2
                    );
                    sprites.add(appendedSprite);
                    activeGenerationCount++;

                    /* Increment X coord to generate future sprites */
                    genCoordX += appendedSprite.getSpriteWidth() + CONTESTANT_PADDING;
                    if (genCoordX > MAX_X)
                        genCoordX = MIN_X;

                }
                readyToAppendNextSprite = false;
            }

            /* Else, preapre a next sprite to append */
            else {

                /* Generate next sprite */
                if (nextContestantIndices.isEmpty())
                    nextContestantIndices = generateRandomArray(Contestant.length());
                nextContestant = Contestant.toEnum((nextContestantIndices.getFirst()));
                //nextContestant = generateContestant(nextContestant);
                nextContestantIndices.removeFirst();
                nextSprite = generateContestantSprite(nextContestant, START_TIME);

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

                /* If there is enough space, append next sprite */
                System.out.println(genCoordY);
                if (genCoordY > MIN_Y) {
                    // System.out.println("IMMEDIATE APPEND.");
                    ContestantSprite appendedSprite = new ContestantSprite(
                            nextSprite,
                            currTime,
                            genCoordX + nextSprite.getSpriteWidth()/2,
                            genCoordY - nextSprite.getSpriteHeight()/2
                    );
                    sprites.add(appendedSprite);
                    activeGenerationCount++;

                    /* Increment X coord to generate future sprites */
                    genCoordX += appendedSprite.getSpriteWidth() + CONTESTANT_PADDING;
                    if (genCoordX > MAX_X)
                        genCoordX = MIN_X;
                }

                /* Else, calculate next possible time to append next sprite */
                else {
                    appendTime = currTime + ((MIN_Y - genCoordY)/CONTESTANT_VELOCITY) * 1000;
                    readyToAppendNextSprite = true;
                    // System.out.println("NOT READY. Append time is " + appendTime + ", curr time is " + currTime);
                }

            } // [END ELSE]


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







            // -------------------
            // [INCREMENTING TIME]
            // -------------------

            for (ContestantSprite sprite : sprites)
                sprite.addLengthAndUpdate(TIME_RESOLUTION);
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
