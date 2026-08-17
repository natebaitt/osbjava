package osbjava.commands;

import osbjava.exceptions.*;
import osbjava.types.*;
import java.io.Serializable;


/**
 * Base class for creating storyboard commands.
 * <p>
 * Contains the <u>easing</u>, <u>start and end times</u>, and an array of <u>start and
 * end parameters</u> depending on the command type.
 * <p>
 * If possible, the {@link OsbCommand#toString() toString()} method automatically uses
 * <u>shorthand notation</u> as based on the osu! wiki to simplify the command string.
 */
@SuppressWarnings("unused")
public abstract class OsbCommand implements Serializable {


    // ============================================================

    // [VARIABLES]


    private final OsbCommandType commandType;
    private OsbEasing            easing;
    private double               startTime;
    private double               endTime;
    private double[]             params;


    // ============================================================

    // [CONSTRUCTORS]


    public OsbCommand(OsbCommandType commandType, OsbEasing easing, double startTime, double endTime) {

        /* Check if start and end times are valid */
        if (startTime > endTime) {
            throw new TimeRangeException();
        }

        /* Initialize general parameters */
        this.commandType = commandType;
        this.easing = easing;
        this.startTime = startTime;
        this.endTime = endTime;

        /* Initialize command-specific parameters */
        switch (commandType) {
            case FADE:
            case MOVE_X:
            case MOVE_Y:
            case SCALE:
            case ROTATE:
                params = new double[2];
                break;
            case MOVE:
            case VECTOR_SCALE:
                params = new double[4];
                break;
            case COLOR:
                params = new double[6];
                break;
        }
    }


    // ============================================================

    // [GETTERS]


    public OsbCommandType getCommandType() {
        return this.commandType;
    }


    public OsbEasing getEasing() {
        return this.easing;
    }


    public double getStartTime() {
        return this.startTime;
    }


    public double getEndTime() {
        return this.endTime;
    }


    public double getParam(int index) {
        if (index < 0 || index >= params.length) {
            throw new OutOfBoundsException();
        }
        return this.params[index];
    }


    // ============================================================

    // [SETTERS]


    public void setEasing(OsbEasing easing) {
        this.easing = easing;
    }


    public void setStartTime(double startTime) {
        if (startTime > this.endTime) {
            throw new TimeRangeException();
        }
        this.startTime = startTime;
    }


    public void setEndTime(double endTime) {
        if (endTime < this.startTime) {
            throw new TimeRangeException();
        }
        this.endTime = endTime;
    }


    public void setParam(int index, double value) {
        if (index < 0 || index >= params.length) {
            throw new OutOfBoundsException();
        }
        this.params[index] = value;
    }


    // ============================================================

    // [METHODS]


    /**
     * Returns the storyboard syntax of an {@link OsbCommand} as a string.
     *
     * @return String representation of the command
     */
    public String toString() {

        String commandStr = " ";

        /* Check for shorthand notation */
        boolean simplifyTimeNotation = startTime == endTime;
        boolean simplifyParamNotation = true;
        for (int i = 0; i < params.length / 2; i++) {
            if (getParam(i) != getParam(i + params.length / 2)) {
                simplifyParamNotation = false;
            }
        }

        /* Concatenate general parameters */
        commandStr += this.commandType.toString() + ","
                + this.easing.toString() + ","
                + Math.round(this.startTime) + ",";

        /* Concatenate time parameters */
        if (simplifyTimeNotation) {
            commandStr += ",";
        } else {
            commandStr += Math.round(this.endTime) + ",";
        }

        /* Concatenate command-specific parameters */
        /* NOTE: Decimals are allowed for everything except for RGB */
        /* NOTE: Prefer integers over decimals if possible */
        if (simplifyTimeNotation || simplifyParamNotation) {

            for (int i = 0; i < params.length / 2; i++) {
                commandStr += (this.getCommandType() == OsbCommandType.COLOR || params[i] % 1 == 0) ?
                              Math.round(params[i]) : String.format("%.4f", params[i]);
                if (i < params.length / 2 - 1) {
                    commandStr += ",";
                }
            }

        } else {

            for (int i = 0; i < params.length; i++) {
                commandStr += (this.getCommandType() == OsbCommandType.COLOR || params[i] % 1 == 0) ?
                              Math.round(params[i]) : String.format("%.4f", params[i]);
                if (i < params.length - 1) {
                    commandStr += ",";
                }
            }

        }

        return commandStr;
    }


}
