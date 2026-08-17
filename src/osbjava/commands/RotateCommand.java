package osbjava.commands;

import osbjava.types.*;
@SuppressWarnings("unused")


public class RotateCommand extends OsbCommand {


    // ============================================================

    // [CONSTRUCTORS]


    public RotateCommand(OsbEasing easing, double startTime, double endTime,
                       double startAngle, double endAngle) {
        super(OsbCommandType.ROTATE, easing, startTime, endTime);
        this.setStartAngle(startAngle);
        this.setEndAngle(endAngle);
    }


    // ============================================================

    // [GETTERS]


    public double getStartAngle() {
        return this.getParam(0);
    }


    public double getEndAngle() {
        return this.getParam(1);
    }


    // ============================================================

    // [SETTERS]


    public void setStartAngle(double value) {
        this.setParam(0, value * (Math.PI / 180));
    }


    public void setEndAngle(double value) {
        this.setParam(1, value * (Math.PI / 180));
    }


}
