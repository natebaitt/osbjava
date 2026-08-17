package osbjava.commands;

import osbjava.types.*;


public class ScaleCommand extends OsbCommand {


    // ============================================================

    // [CONSTRUCTORS]


    public ScaleCommand(OsbEasing easing, double startTime, double endTime,
                        double startScale, double endScale) {
        super(OsbCommandType.SCALE, easing, startTime, endTime);
        this.setStartScale(startScale);
        this.setEndScale(endScale);
    }


    // ============================================================

    // [GETTERS]


    public double getStartScale() {
        return this.getParam(0);
    }


    public double getEndScale() {
        return this.getParam(1);
    }


    // ============================================================

    // [SETTERS]


    public void setStartScale(double value) {
        OsbUtils.assertParameterRange(value, OsbParameter.SCALE);
        this.setParam(0, value);
    }


    public void setEndScale(double value) {
        OsbUtils.assertParameterRange(value, OsbParameter.SCALE);
        this.setParam(1, value);
    }


}
