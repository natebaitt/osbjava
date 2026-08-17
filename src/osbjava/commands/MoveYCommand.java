package osbjava.commands;

import osbjava.types.*;
@SuppressWarnings("unused")


public class MoveYCommand extends OsbCommand {


    // ============================================================

    // [CONSTRUCTORS]


    public MoveYCommand(OsbEasing easing, double startTime, double endTime,
                        double startY, double endY) {
        super(OsbCommandType.MOVE_Y, easing, startTime, endTime);
        this.setStartY(startY);
        this.setEndY(endY);
    }


    // ============================================================

    // [GETTERS]


    public double getStartY() {
        return this.getParam(0);
    }


    public double getEndY() {
        return this.getParam(1);
    }


    // ============================================================

    // [SETTERS]


    public void setStartY(double value) {
        this.setParam(0, value);
    }


    public void setEndY(double value) {
        this.setParam(1, value);
    }


}
