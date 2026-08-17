package osbjava.commands;

import osbjava.types.*;
@SuppressWarnings("unused")


public class MoveXCommand extends OsbCommand {


    // ============================================================

    // [CONSTRUCTORS]


    public MoveXCommand(OsbEasing easing, double startTime, double endTime,
                       double startX, double endX) {
        super(OsbCommandType.MOVE_X, easing, startTime, endTime);
        this.setStartX(startX);
        this.setEndX(endX);
    }


    // ============================================================

    // [GETTERS]


    public double getStartX() {
        return this.getParam(0);
    }


    public double getEndX() {
        return this.getParam(1);
    }


    // ============================================================

    // [SETTERS]


    public void setStartX(double value) {
        this.setParam(0, value);
    }


    public void setEndX(double value) {
        this.setParam(1, value);
    }


}
