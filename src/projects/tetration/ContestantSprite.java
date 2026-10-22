package projects.tetration;

import osbjava.objects.DynamicSprite;
import osbjava.objects.OsbColor;
import osbjava.types.*;
import static osbjava.types.OsbOrigin.*;


@SuppressWarnings("unused")
public class ContestantSprite extends DynamicSprite {


    // ============================================================
    // [VARIABLES]


    private final Contestant contestant;
    private OsbColor         baseColor;
    private OsbColor         contestantColor;
    private double           spriteWidth;
    private double           spriteHeight;
    private double[]         spriteXRange;

    static final double BASE_OPACITY           = 0.2;
    static final double BASE_TRANSITION_LENGTH = 1200;
    static final double FLASH_LENGTH           = 550;
    static final double FLASH_HOLD_LENGTH      = 150;
    private double      flashHoldTimer         = 0;

    public enum State {
        OFF,
        ON,
        FLASH,
        FLASH_HOLD,
        TRANSITION
    }
    private State state = State.OFF;


    // ============================================================
    // [CONSTRUCTORS]


    public ContestantSprite(Contestant contestant, String filepath, OsbLayer layer, OsbOrigin origin,
                            double startTime, double x, double y) {
        super(filepath + contestant.fileName(), layer, origin, startTime, x, y);
        this.contestant = contestant;
    }


    public ContestantSprite(Contestant contestant, String filepath, OsbLayer layer, OsbOrigin origin, double startTime) {
        super(filepath + contestant.fileName(), layer, origin, startTime);
        this.contestant = contestant;
    }


    public ContestantSprite(Contestant contestant, String filepath, OsbLayer layer, double startTime) {
        super(filepath + contestant.fileName(), layer, startTime);
        this.contestant = contestant;
    }


    public ContestantSprite(Contestant contestant, String filepath, OsbOrigin origin, double startTime) {
        super(filepath + contestant.fileName(), origin, startTime);
        this.contestant = contestant;
    }


    public ContestantSprite(Contestant contestant, String filepath, double startTime) {
        super(filepath + contestant.fileName(), startTime);
        this.contestant = contestant;
    }


    /* Copy constructor */
    public ContestantSprite(ContestantSprite other, double startTime, double x, double y) {
        super(other.getFilepath(),
              other.getLayer(),
              other.getOrigin(),
              startTime,
              other.getX(),
              other.getY());

        this.contestant = other.contestant;
        this.state = other.state;
        this.initializeColors(other.baseColor);
        this.initializeDimensions(other.getCurrScale()[0], other.getCurrAngle());
        this.initializeMovement(other.getMoveVelocities()[1], other.getRotateRate());
        this.initializePosition(x, y);
    }


    // ============================================================
    // [METHODS]


    public void initializeColors(OsbColor baseColor) {
        this.baseColor = baseColor;
        this.contestantColor = contestant.color();
        setColor(baseColor);
        setOpacity(BASE_OPACITY);
    }


    public void initializeDimensions(double scale, double angle) {

        setScale(scale);
        setAngle(angle);

        double w1 = this.contestant.fileWidth() * scale;
        double h1 = this.contestant.fileHeight() * scale;
        double w2 = w1 * Math.abs(Math.cos(angle * (Math.PI/180))) + h1 * Math.abs(Math.sin(angle * (Math.PI/180)));
        double h2 = h1 * Math.abs(Math.cos(angle * (Math.PI/180))) + w1 * Math.abs(Math.sin(angle * (Math.PI/180)));
        spriteWidth  = w2;
        spriteHeight = h2;
    }


    public void initializePosition(double x, double y) {

        setPosition(x, y);

        OsbOrigin origin = this.getOrigin();
        if (origin == TOP_LEFT || origin == CENTER_LEFT || origin == BOTTOM_LEFT)         // Pivoted on the left edge
            spriteXRange = new double[] {getCurrX(), getCurrX() + spriteWidth};
        else if (origin == TOP_RIGHT || origin == CENTER_RIGHT || origin == BOTTOM_RIGHT) // Pivoted on the right edge
            spriteXRange = new double[] {getCurrX() - spriteWidth, getCurrX()};
        else                                                                              // Pivoted on the center
            spriteXRange = new double[] {getCurrX() - spriteWidth/2, getCurrX() + spriteWidth/2};
    }


    public void initializeMovement(double velocity, double rotateRate) {
        setMoveVelocity(0, velocity);
        setRotateRate(rotateRate);
    }


    public void addLengthAndUpdate(double time) {

        addLength(time);

        /* Check if flash hold timer is done --> enable rates */
        if (state == State.FLASH_HOLD) {
            flashHoldTimer -= time;
            if (flashHoldTimer <= 0) {
                double[] colorRates = calculateColorRates(getCurrColor(), baseColor, FLASH_LENGTH);
                setColorRate(colorRates[0], colorRates[1], colorRates[2]);
                setFadeRate(calculateFadeRate(1, BASE_OPACITY, FLASH_LENGTH));
                state = State.FLASH;
            }
        }

        /* Check if flash event is done --> disable rates */
        if (state == State.FLASH) {

            /* If parameters exceed base values, disable rates */
            boolean completedR = (getCurrColor().getR() <= baseColor.getR() && getColorRates()[0] <= 0) ||
                    (getCurrColor().getR() >= baseColor.getR() && getColorRates()[0] >= 0);
            boolean completedG = (getCurrColor().getG() <= baseColor.getG() && getColorRates()[1] <= 0) ||
                    (getCurrColor().getG() >= baseColor.getG() && getColorRates()[1] >= 0);
            boolean completedB = (getCurrColor().getB() <= baseColor.getB() && getColorRates()[2] <= 0) ||
                    (getCurrColor().getB() >= baseColor.getB() && getColorRates()[2] >= 0);
            boolean completedOpacity = getCurrOpacity() <= BASE_OPACITY;

            if (completedR && completedG && completedB && completedOpacity) {
                setColorRate(0, 0, 0);
                setColor(baseColor);
                setFadeRate(0);
                setOpacity(BASE_OPACITY);
                state = State.OFF;
            }
        }

        /* Check if transition event is done --> disable rates */
        if (state == State.TRANSITION) {

            /* If parameters exceed base values, disable rates */
            boolean completedR = (getCurrColor().getR() <= baseColor.getR() && getColorRates()[0] <= 0) ||
                    (getCurrColor().getR() >= baseColor.getR() && getColorRates()[0] >= 0);
            boolean completedG = (getCurrColor().getG() <= baseColor.getG() && getColorRates()[1] <= 0) ||
                    (getCurrColor().getG() >= baseColor.getG() && getColorRates()[1] >= 0);
            boolean completedB = (getCurrColor().getB() <= baseColor.getB() && getColorRates()[2] <= 0) ||
                    (getCurrColor().getB() >= baseColor.getB() && getColorRates()[2] >= 0);

            if (completedR && completedG && completedB) {
                setColorRate(0, 0, 0);
                setColor(baseColor);
                state = State.OFF;
            }
        }

    }


    private double[] calculateColorRates(OsbColor startColor, OsbColor endColor, double length) {
        double colorRateR = ((endColor.getR() - startColor.getR()) / length) * 1000;
        double colorRateG = ((endColor.getG() - startColor.getG()) / length) * 1000;
        double colorRateB = ((endColor.getB() - startColor.getB()) / length) * 1000;
        return new double[] {colorRateR, colorRateG, colorRateB};
    }


    private double calculateFadeRate(double startOpacity, double endOpacity, double length) {
        double fadeRate = ((endOpacity - startOpacity) / length) * 1000;
        return fadeRate;
    }


    public double getSpriteWidth() {
        return spriteWidth;
    }


    public double getSpriteHeight() {
        return spriteHeight;
    }


    public double[] getSpriteXRange() {
        return spriteXRange;
    }


    public double getSpriteYTop() {
        OsbOrigin origin = this.getOrigin();
        if (origin == TOP_LEFT || origin == TOP_CENTER || origin == TOP_RIGHT)               // Pivoted on the top edge
            return getCurrY();
        else if (origin == BOTTOM_LEFT || origin == BOTTOM_CENTER || origin == BOTTOM_RIGHT) // Pivoted on the bottom edge
            return getCurrY() - spriteHeight;
        else                                                                                 // Pivoted on the center
            return getCurrY() - spriteHeight/2;
    }


    public State getState() {
        return state;
    }


    // ============================================================
    // [EVENTS]


    public void changeBaseColor(OsbColor baseColor) {

        /* Apply effect */
        this.baseColor = baseColor;
        double[] colorRates = calculateColorRates(getCurrColor(), baseColor, BASE_TRANSITION_LENGTH);
        setColorRate(colorRates[0], colorRates[1], colorRates[2]);

        /* Disable rates */
        setOpacity(BASE_OPACITY);
        setFadeRate(0);

        /* Update state */
        state = State.TRANSITION;
    }


    public void flash(OsbColor color) {

        /* Apply effect */
        setColor(color);
        setOpacity(1);
        flashHoldTimer = FLASH_HOLD_LENGTH;

        /* Disable rates */
        if (state == State.FLASH) {
            setColorRate(0, 0, 0);
            setFadeRate(0);
        }
        if (state == State.TRANSITION) {
            setColorRate(0, 0, 0);
        }

        /* Update state */
        state = State.FLASH_HOLD;
    }


    public void flash() {
        this.flash(contestantColor);
    }


    public void turnOn(OsbColor color) {

        /* Apply effect */
        setColor(color);
        setOpacity(1);

        /* Disable rates */
        if (state == State.FLASH) {
            setColorRate(0, 0, 0);
            setFadeRate(0);
        }
        if (state == State.TRANSITION) {
            setColorRate(0, 0, 0);
        }

        /* Update state */
        state = State.ON;
    }


    public void turnOn() {
        this.turnOn(contestantColor);
    }


    public void turnOff() {

        /* Apply effect */
        setColor(baseColor);
        setOpacity(BASE_OPACITY);

        /* Disable rates */
        if (state == State.FLASH) {
            setColorRate(0, 0, 0);
            setFadeRate(0);
        }
        if (state == State.TRANSITION) {
            setColorRate(0, 0, 0);
        }

        /* Update state */
        state = State.OFF;
    }


}
