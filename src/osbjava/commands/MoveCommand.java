package osbjava.commands;

import osbjava.types.*;
@SuppressWarnings("unused")


public class MoveCommand extends OsbCommand {


    // ============================================================

    // [CONSTRUCTORS]


    public MoveCommand(OsbEasing easing, double startTime, double endTime,
                       double startX, double startY, double endX, double endY) {
        super(OsbCommandType.MOVE, easing, startTime, endTime);
        this.setStartX(startX);
        this.setStartY(startY);
        this.setEndX(endX);
        this.setEndY(endY);
    }


    // ============================================================

    // [GETTERS]


    public double getStartX() {
        return this.getParam(0);
    }


    public double getStartY()  {
        return this.getParam(1);
    }


    public double getEndX() {
        return this.getParam(2);
    }


    public double getEndY() {
        return this.getParam(3);
    }


    // ============================================================

    // [SETTERS]


    public void setStartX(double value) {
        this.setParam(0, value);
    }


    public void setStartY(double value) {
        this.setParam(1, value);
    }


    public void setEndX(double value) {
        this.setParam(2, value);
    }


    public void setEndY(double value) {
        this.setParam(3, value);
    }


}
