package osbjava.commands;

import osbjava.types.*;
@SuppressWarnings("unused")


public class VectorScaleCommand extends OsbCommand {


    // ============================================================

    // [CONSTRUCTORS]


    public VectorScaleCommand(OsbEasing easing, double startTime, double endTime,
                              double startScaleX, double startScaleY,
                              double endScaleX, double endScaleY) {
        super(OsbCommandType.VECTOR_SCALE, easing, startTime, endTime);
        this.setStartScaleX(startScaleX);
        this.setStartScaleY(startScaleY);
        this.setEndScaleX(endScaleX);
        this.setEndScaleY(endScaleY);
    }


    // ============================================================

    // [GETTERS]


    public double getStartScaleX() {
        return this.getParam(0);
    }


    public double getStartScaleY() {
        return this.getParam(1);
    }


    public double getEndScaleX() {
        return this.getParam(2);
    }


    public double getEndScaleY() {
        return this.getParam(3);
    }


    // ============================================================

    // [SETTERS]


    public void setStartScaleX(double value) {
        OsbUtils.assertParameterRange(value, OsbParameter.SCALE);
        this.setParam(0, value);
    }


    public void setStartScaleY(double value) {
        OsbUtils.assertParameterRange(value, OsbParameter.SCALE);
        this.setParam(1, value);
    }


    public void setEndScaleX(double value) {
        OsbUtils.assertParameterRange(value, OsbParameter.SCALE);
        this.setParam(2, value);
    }


    public void setEndScaleY(double value) {
        OsbUtils.assertParameterRange(value, OsbParameter.SCALE);
        this.setParam(3, value);
    }


}
