package osbjava.objects;

import osbjava.commands.*;
import osbjava.exceptions.*;
import osbjava.types.*;
import static osbjava.types.OsbParameter.*;
import java.util.Arrays;


@SuppressWarnings("unused")
public class DynamicSprite extends OsbObject {


    // ============================================================

    // [VARIABLES]


    private final double objectStartTime;
    private double       objectLength;

    private double currOpacity;
    private double fadeRate;
    private double fadeStartTime;

    private double currX;
    private double currY;
    private double moveVelocityX;
    private double moveVelocityY;
    private double moveStartTime;

    private double currScaleX;
    private double currScaleY;
    private double scaleRateX;
    private double scaleRateY;
    private double scaleStartTime;

    private double currAngle;
    private double rotateRate;
    private double rotateStartTime;

    private OsbColor currColor;
    private double colorRateR;
    private double colorRateG;
    private double colorRateB;
    private double colorStartTime;


    // ============================================================

    // [CONSTRUCTORS]


    /* TODO: Make a constructor with initialized command parameters */
    /* TODO: Make a copy constructor lol */

    public DynamicSprite(String filepath, OsbLayer layer, OsbOrigin origin, double startTime,
                         double x, double y) {
        super(OsbObjectType.DYNAMIC_SPRITE, layer, origin, filepath, x, y);
        this.objectStartTime = startTime;
        init(startTime, x, y);
    }


    public DynamicSprite(String filepath, OsbLayer layer, OsbOrigin origin, double startTime) {
        super(OsbObjectType.DYNAMIC_SPRITE, layer, origin, filepath, OsbConstants.CENTER_X, OsbConstants.CENTER_Y);
        this.objectStartTime = startTime;
        init(startTime, OsbConstants.CENTER_X, OsbConstants.CENTER_Y);
    }


    public DynamicSprite(String filepath, OsbLayer layer, double startTime) {
        super(OsbObjectType.DYNAMIC_SPRITE, layer, OsbOrigin.CENTER, filepath,
                OsbConstants.CENTER_X, OsbConstants.CENTER_Y);
        this.objectStartTime = startTime;
        init(startTime, OsbConstants.CENTER_X, OsbConstants.CENTER_Y);
    }


    public DynamicSprite(String filepath, OsbOrigin origin, double startTime) {
        super(OsbObjectType.DYNAMIC_SPRITE, OsbLayer.BACKGROUND, origin, filepath,
                OsbConstants.CENTER_X, OsbConstants.CENTER_Y);
        this.objectStartTime = startTime;
        init(startTime, OsbConstants.CENTER_X, OsbConstants.CENTER_Y);
    }


    public DynamicSprite(String filepath, double startTime) {
        super(OsbObjectType.DYNAMIC_SPRITE, OsbLayer.BACKGROUND, OsbOrigin.CENTER, filepath,
                OsbConstants.CENTER_X, OsbConstants.CENTER_Y);
        this.objectStartTime = startTime;
        init(startTime, OsbConstants.CENTER_X, OsbConstants.CENTER_Y);
    }


    DynamicSprite(OsbObjectType objectType, OsbLayer layer, OsbOrigin origin, String filepath,
           double x, double y, double startTime) {
        super(objectType, layer, origin, filepath, x, y);
        this.objectStartTime = startTime;
        init(startTime, x, y);
    }


    private void init(double startTime, double x, double y) {

        /* Initialize object lifetime */
        this.objectLength = 0;

        /* Initialize fade variables */
        this.currOpacity = 1;
        this.fadeRate = Double.NaN;
        this.fadeStartTime = startTime;

        /* Initialize move variables */
        this.currX = x;
        this.currY = y;
        this.moveVelocityX = 0;
        this.moveVelocityY = 0;
        this.moveStartTime = startTime;

        /* Initialize scale variables */
        this.currScaleX = 1;
        this.currScaleY = 1;
        this.scaleRateX = Double.NaN;
        this.scaleRateY = Double.NaN;
        this.scaleStartTime = startTime;

        /* Initialize rotate variables */
        this.currAngle = 0;
        this.rotateRate = Double.NaN;
        this.rotateStartTime = startTime;

        /* Initialize color variables */
        this.currColor = new OsbColor("FFFFFF");
        this.colorRateR = Double.NaN;
        this.colorRateG = Double.NaN;
        this.colorRateB = Double.NaN;
        this.colorStartTime = startTime;
    }


    // ============================================================

    // [TIME GETTERS]


    public double getObjectStartTime() {
        return this.objectStartTime;
    }


    public double getObjectLength() {
        return this.objectLength;
    }


    public double getObjectCurrTime() {
        return this.objectStartTime + this.objectLength;
    }


    // ============================================================

    // [PARAMETER GETTERS]


    public double getCurrOpacity() {
        double length = this.getObjectCurrTime() - fadeStartTime;
        double opacity = OsbUtils.saturateParameter(currOpacity + fadeRate * (length / 1000), OPACITY);
        return opacity;
    }


    public double[] getCurrPosition() {
        double length = this.getObjectCurrTime() - moveStartTime;
        double x = currX + moveVelocityX * (length / 1000);
        double y = currY + moveVelocityY * (length / 1000);
        return new double[] {x, y};
    }


    public double getCurrX() {
        return this.getCurrPosition()[0];
    }


    public double getCurrY() {
        return this.getCurrPosition()[1];
    }


    public double[] getCurrScale() {
        double length = this.getObjectCurrTime() - scaleStartTime;
        double x = OsbUtils.saturateParameter(currScaleX + scaleRateX * (length / 1000), SCALE);
        double y = OsbUtils.saturateParameter(currScaleY + scaleRateY * (length / 1000), SCALE);
        return new double[] {x, y};
    }


    public double getCurrAngle() {
        double length = this.getObjectCurrTime() - rotateStartTime;
        double angle = currAngle + rotateRate * (length / 1000);
        return angle;
    }


    public double getCurrAbsoluteAngle() {
        double absAngle = this.getCurrAngle() % 360;
        return (absAngle < 0) ? absAngle + 360 : absAngle;
    }


    public OsbColor getCurrColor() {
        double length = this.getObjectCurrTime() - colorStartTime;
        double R = OsbUtils.saturateParameter(currColor.getR() + colorRateR * (length / 1000), COLOR);
        double G = OsbUtils.saturateParameter(currColor.getG() + colorRateG * (length / 1000), COLOR);
        double B = OsbUtils.saturateParameter(currColor.getB() + colorRateB * (length / 1000), COLOR);
        return new OsbColor(R, G, B);
    }


    // ============================================================

    // [RATE GETTERS]


    public double getFadeRate() {
        return fadeRate;
    }


    public double[] getMoveVelocities() {
        return new double[] {moveVelocityX, moveVelocityY};
    }


    public double[] getScaleRates() {
        return new double[] {scaleRateX, scaleRateY};
    }


    public double getRotateRate() {
        return rotateRate;
    }


    public double[] getColorRates() {
        return new double[] {colorRateR, colorRateG, colorRateB};
    }


    // ============================================================

    // [COMMAND CONTROL]


    public void addLength(double time) {
        if (time < 0) {
            throw new DynamicTimeRangeException();
        }
        this.objectLength += time;
    }


    public void writeAll() {
        writeFade();
        writeMove();
        writeScale();
        writeRotate();
        writeColor();
    }


    public void writeFade() {

        /* Check if command-specific variables are activated and command has non-zero length */
        if (!Double.isNaN(fadeRate) && fadeStartTime != this.getObjectCurrTime()) {

            /* Get start and end times */
            double startTime = fadeStartTime;
            double endTime = this.getObjectCurrTime();
            double length = endTime - startTime;

            /* Get start and end opacities */
            double startOpacity = currOpacity;
            double endOpacity = currOpacity + fadeRate * (length / 1000);

            /* NOTE: Ensure end opacity is in range and adjust end time */
            if (endOpacity < 0 || endOpacity > 1) {
                endOpacity = (endOpacity < 0) ? 0 : 1;
                length = ((endOpacity - currOpacity) / fadeRate) * 1000;
                endTime = startTime + length;
            }

            /* Finalize dynamic command */
            this.addCommand(new FadeCommand(OsbEasing.LINEAR, startTime, endTime, startOpacity, endOpacity));

            /* Update command-specific variables */
            this.fadeStartTime = this.getObjectCurrTime();
            this.currOpacity = endOpacity;
        }
    }


    public void writeMove() {

        /* Check if command-specific variables are activated and command has non-zero length */
        if (!Double.isNaN(moveVelocityX) && !Double.isNaN(moveVelocityY)
                && moveStartTime != this.getObjectCurrTime()) {

            /* Get start and end times */
            double startTime = moveStartTime;
            double endTime = this.getObjectCurrTime();
            double length = endTime - startTime;

            /* Get start and end positions */
            double startX = currX;
            double startY = currY;
            double endX = currX + moveVelocityX * (length / 1000);
            double endY = currY + moveVelocityY * (length / 1000);

            /* Finalize dynamic command */
            this.addCommand(new MoveCommand(OsbEasing.LINEAR, startTime, endTime, startX, startY, endX, endY));

            /* Update command-specific variables */
            this.moveStartTime = this.getObjectCurrTime();
            this.currX = endX;
            this.currY = endY;
        }
    }


    public void writeScale() {

        /* Check if command-specific variables are activated and command has non-zero length */
        if (!Double.isNaN(scaleRateX) && !Double.isNaN(scaleRateY)
                && scaleStartTime != this.getObjectCurrTime()) {

            /* NOTE: Because scale rates can vary and scaling can undersaturate at different
               times, must calculate multiple sets of commands instead */

            /* Get start and end times */
            double startTime = scaleStartTime;
            double endTime = this.getObjectCurrTime();
            double length = endTime - startTime;

            /* Get start scales */
            double startScaleX = currScaleX;
            double startScaleY = currScaleY;

            /* Find the ending lengths of each individual scale */
            double[] individualLengths = new double[2];
            individualLengths[0] = Math.min(getScaleSaturationLength(startScaleX, scaleRateX), length);
            individualLengths[1] = Math.min(getScaleSaturationLength(startScaleY, scaleRateY), length);

            /* Account for NaN values (if saturation lengths are infinite) */
            for (int i = 0; i < 2; i++) {
                if (Double.isNaN(individualLengths[i]))
                    individualLengths[i] = length;
            }

            Arrays.sort(individualLengths);

            /* Iterate through the lengths to increment colors */
            for (int i = 0; i < 2; i++) {

                double currLength = individualLengths[i];

                /* Only perform if current length is more than 0 */
                if (currLength > 0) {

                    /* Get end colors */
                    double endScaleX =
                            OsbUtils.saturateParameter(startScaleX + scaleRateX * (currLength / 1000), SCALE);
                    double endScaleY =
                            OsbUtils.saturateParameter(startScaleY + scaleRateY * (currLength / 1000), SCALE);

                    /* Add dynamic command */
                    this.addCommand(new VectorScaleCommand(OsbEasing.LINEAR,
                            startTime, startTime + currLength,
                            startScaleX, startScaleY, endScaleX, endScaleY));

                    /* Update new starting parameters */
                    startTime = startTime + currLength;
                    startScaleX = endScaleX;
                    startScaleY = endScaleY;

                    /* Decrement individual lengths */
                    for (int j = 0; j < 2; j++) {
                        individualLengths[j] -= currLength;
                    }

                }
            }

            /* Update command-specific variables */
            this.scaleStartTime = this.getObjectCurrTime();
            this.currScaleX = startScaleX;
            this.currScaleY = startScaleY;
        }
    }


    private double getScaleSaturationLength(double startScale, double rate) {

        /* Length it takes to undersaturate to 0 */
        double underSaturationLength = ((0.0 - startScale) / rate) * 1000;

        /* If rate is zero, return NaN (indicates saturation length is infinite) */
        if (rate == 0) return Double.NaN;

        /* Return the non-negative value (negative means it never saturates) */
        return (underSaturationLength > 0) ? underSaturationLength : Double.NaN;
    }


    public void writeRotate() {

        /* Check if command-specific variables are activated and command has non-zero length */
        if (!Double.isNaN(rotateRate) && rotateStartTime != this.getObjectCurrTime()) {

            /* Get start and end times */
            double startTime = rotateStartTime;
            double endTime = this.getObjectCurrTime();
            double length = endTime - startTime;

            /* Get start and end angles */
            double startAngle = currAngle;
            double endAngle = currAngle + rotateRate * (length / 1000);

            /* Finalize dynamic command */
            this.addCommand(new RotateCommand(OsbEasing.LINEAR, startTime, endTime, startAngle, endAngle));

            /* Update command-specific variables */
            this.rotateStartTime = this.getObjectCurrTime();
            this.currAngle = endAngle;
            this.currAngle = this.getCurrAbsoluteAngle();
        }
    }


    public void writeColor() {

        /* Check if command-specific variables are activated and command has non-zero length */
        if (!Double.isNaN(colorRateR) && !Double.isNaN(colorRateG) && !Double.isNaN(colorRateB) &&
                colorStartTime != this.getObjectCurrTime()) {

            /* NOTE: Because color rates can vary and color values can over/undersaturate at different
               times, must calculate multiple sets of commands instead */

            /* Get start and end times */
            double startTime = colorStartTime;
            double endTime = this.getObjectCurrTime();
            double length = endTime - startTime;

            /* Get start colors */
            double startColorR = currColor.getR();
            double startColorG = currColor.getG();
            double startColorB = currColor.getB();

            /* Find the ending lengths of each individual color */
            double[] individualLengths = new double[3];
            individualLengths[0] = Math.min(getColorSaturationLength(startColorR, colorRateR), length);
            individualLengths[1] = Math.min(getColorSaturationLength(startColorG, colorRateG), length);
            individualLengths[2] = Math.min(getColorSaturationLength(startColorB, colorRateB), length);

            /* Account for NaN values (if saturation lengths are infinite) */
            for (int i = 0; i < 3; i++) {
                if (Double.isNaN(individualLengths[i]))
                    individualLengths[i] = length;
            }

            Arrays.sort(individualLengths);

            /* Iterate through the lengths to increment colors */
            for (int i = 0; i < 3; i++) {

                double currLength = individualLengths[i];

                /* Only perform if current length is more than 0 */
                if (currLength > 0) {

                    /* Get end colors */
                    double endColorR =
                            OsbUtils.saturateParameter(startColorR + colorRateR * (currLength / 1000), COLOR);
                    double endColorG =
                            OsbUtils.saturateParameter(startColorG + colorRateG * (currLength / 1000), COLOR);
                    double endColorB =
                            OsbUtils.saturateParameter(startColorB + colorRateB * (currLength / 1000), COLOR);

                    /* Add dynamic command */
                    this.addCommand(new ColorCommand(OsbEasing.LINEAR,
                            startTime, startTime + currLength,
                            startColorR, startColorG, startColorB,
                            endColorR, endColorG, endColorB));

                    /* Update new starting parameters */
                    startTime = startTime + currLength;
                    startColorR = endColorR;
                    startColorG = endColorG;
                    startColorB = endColorB;

                    /* Decrement individual lengths */
                    for (int j = 0; j < 3; j++) {
                        individualLengths[j] -= currLength;
                    }

                }
            }

            /* Update command-specific variables */
            this.colorStartTime = this.getObjectCurrTime();
            this.currColor.setR(startColorR);
            this.currColor.setG(startColorG);
            this.currColor.setB(startColorB);
        }
    }


    private double getColorSaturationLength(double startColor, double rate) {

        /* Length it takes to undersaturate to 0 */
        double underSaturationLength = ((0.0 - startColor) / rate) * 1000;

        /* Length it takes to oversaturate to 255 */
        double overSaturationLength = ((255.0 - startColor) / rate) * 1000;

        /* If rate is zero, return NaN (indicates saturation length is infinite) */
        if (rate == 0) return Double.NaN;

        /* Return the non-negative value */
        return (underSaturationLength > 0) ? underSaturationLength : overSaturationLength;
    }


    // ============================================================

    // [INSTANTANEOUS PARAMETER CONTROL]


    public void setOpacity(double opacity) {

        /* Automatically write previous dynamic command */
        this.writeFade();

        /* Get instantaneous time */
        double time = this.getObjectCurrTime();

        /* Finalize instantaneous command */
        this.addCommand(new FadeCommand(OsbEasing.LINEAR, time, time, opacity, opacity));

        /* Update command-specific variables */
        /* NOTE: Rates are automatically set to zero if uninitialized */
        this.fadeStartTime = this.getObjectCurrTime();
        this.currOpacity = opacity;
        this.fadeRate = (Double.isNaN(this.fadeRate)) ? 0 : this.fadeRate;
    }


    public void setPosition(double x, double y) {

        /* Automatically write previous dynamic command */
        this.writeMove();

        /* Get instantaneous time */
        double time = this.getObjectCurrTime();

        /* Finalize instantaneous command */
        this.addCommand(new MoveCommand(OsbEasing.LINEAR, time, time, x, y, x, y));

        /* Update command-specific variables */
        /* NOTE: Rates are automatically set to zero if uninitialized */
        this.moveStartTime = this.getObjectCurrTime();
        this.currX = x;
        this.currY = y;
        this.moveVelocityX = (Double.isNaN(this.moveVelocityX) || Double.isNaN(this.moveVelocityY))
                ? 0 : this.moveVelocityX;
        this.moveVelocityY = (Double.isNaN(this.moveVelocityX) || Double.isNaN(this.moveVelocityY))
                ? 0 : this.moveVelocityY;
    }


    public void setScale(double x, double y) {

        /* Automatically write previous dynamic command */
        this.writeScale();

        /* Get instantaneous time */
        double time = this.getObjectCurrTime();

        /* Finalize instantaneous command */
        this.addCommand(new VectorScaleCommand(OsbEasing.LINEAR, time, time, x, y, x, y));

        /* Update command-specific variables */
        /* NOTE: Rates are automatically set to zero if uninitialized */
        this.scaleStartTime = this.getObjectCurrTime();
        this.currScaleX = x;
        this.currScaleY = y;
        this.scaleRateX = (Double.isNaN(this.scaleRateX) || Double.isNaN(this.scaleRateY))
                ? 0 : this.scaleRateX;
        this.scaleRateY = (Double.isNaN(this.scaleRateX) || Double.isNaN(this.scaleRateY))
                ? 0 : this.scaleRateY;
    }


    public void setScale(double scale) {
        this.setScale(scale, scale);
    }


    public void setAngle(double angle) {

        /* Automatically write previous dynamic command */
        this.writeMove();

        /* Get instantaneous time */
        double time = this.getObjectCurrTime();

        /* Finalize instantaneous command */
        this.addCommand(new RotateCommand(OsbEasing.LINEAR, time, time, angle, angle));

        /* Update command-specific variables */
        /* NOTE: Rates are automatically set to zero if uninitialized */
        this.rotateStartTime = this.getObjectCurrTime();
        this.currAngle = angle;
        this.rotateRate = (Double.isNaN(this.rotateRate)) ? 0 : this.rotateRate;
    }


    public void setColor(OsbColor color) {

        /* Automatically write previous dynamic command */
        this.writeColor();

        /* Get instantaneous time */
        double time = this.getObjectCurrTime();

        /* Finalize instantaneous command */
        this.addCommand(new ColorCommand(OsbEasing.LINEAR, time, time, color, color));

        /* Update command-specific variables */
        /* NOTE: Rates are automatically set to zero if uninitialized */
        this.colorStartTime = this.getObjectCurrTime();
        this.currColor.setR(color.getR());
        this.currColor.setG(color.getG());
        this.currColor.setB(color.getB());
        this.colorRateR = (Double.isNaN(this.colorRateR)) ? 0 : this.colorRateR;
        this.colorRateG = (Double.isNaN(this.colorRateG)) ? 0 : this.colorRateG;
        this.colorRateB = (Double.isNaN(this.colorRateB)) ? 0 : this.colorRateB;
    }


    public void setColor(double R, double G, double B) {
        this.setColor(new OsbColor(R, G, B));
    }


    // ============================================================

    // [DYNAMIC PARAMETER CONTROL]


    public void setFadeRate(double rate) {

        /* Automatically write if parameters are changed */
        if (rate != this.fadeRate) {
            this.writeFade();
            this.fadeRate = rate;
        }
    }


    public void setMoveVelocity(double x, double y) {

        /* Automatically write if parameters are changed */
        if (x != this.moveVelocityX || y != this.moveVelocityY) {
            this.writeMove();
            this.moveVelocityX = x;
            this.moveVelocityY = y;
        }
    }


    public void setPolarMoveVelocity(double rate, double angle) {

        double x = rate * Math.cos(angle * (Math.PI / 180));
        double y = rate * Math.sin(angle * (Math.PI / 180));
        this.setMoveVelocity(x, y);
    }


    public void setScaleRate(double x, double y) {

        /* Automatically write if parameters are changed */
        if (x != scaleRateX || y != scaleRateY) {
            this.writeScale();
            this.scaleRateX = x;
            this.scaleRateY = y;
        }
    }


    public void setScaleRate(double rate) {
        this.setScaleRate(rate, rate);
    }


    public void setRotateRate(double rate) {

        /* Automatically write if parameters are changed */
        if (rate != this.rotateRate) {
            this.writeRotate();
            this.rotateRate = rate;
        }
    }


    public void setColorRate(double R, double G, double B) {

        /* Automatically write if parameters are changed */
        if (R != colorRateR || G != colorRateG || B != colorRateB) {
            this.writeColor();
            this.colorRateR = R;
            this.colorRateG = G;
            this.colorRateB = B;
        }
    }


}
