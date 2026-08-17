package osbjava.commands;

import osbjava.types.*;
@SuppressWarnings("unused")


public class FadeCommand extends OsbCommand {


    // ============================================================

    // [CONSTRUCTORS]


    public FadeCommand(OsbEasing easing, double startTime, double endTime,
                       double startOpacity, double endOpacity) {
        super(OsbCommandType.FADE, easing, startTime, endTime);
        this.setStartOpacity(startOpacity);
        this.setEndOpacity(endOpacity);
    }


    // ============================================================

    // [GETTERS]


    public double getStartOpacity() {
        return this.getParam(0);
    }


    public double getEndOpacity() {
        return this.getParam(1);
    }


    // ============================================================

    // [SETTERS]


    public void setStartOpacity(double value) {
        OsbUtils.assertParameterRange(value, OsbParameter.OPACITY);
        this.setParam(0, value);
    }


    public void setEndOpacity(double value) {
        OsbUtils.assertParameterRange(value, OsbParameter.OPACITY);
        this.setParam(1, value);
    }


}
