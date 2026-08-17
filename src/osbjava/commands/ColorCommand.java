package osbjava.commands;

import osbjava.objects.OsbColor;
import osbjava.types.*;
@SuppressWarnings("unused")


public class ColorCommand extends OsbCommand {


    // ============================================================

    // [CONSTRUCTORS]


    public ColorCommand(OsbEasing easing, double startTime, double endTime,
                       double startR, double startG, double startB,
                       double endR, double endG, double endB) {
        super(OsbCommandType.COLOR, easing, startTime, endTime);
        this.setStartR(startR);
        this.setStartG(startG);
        this.setStartB(startB);
        this.setEndR(endR);
        this.setEndG(endG);
        this.setEndB(endB);
    }


    public ColorCommand(OsbEasing easing, double startTime, double endTime,
                        OsbColor startColor, OsbColor endColor) {
        super(OsbCommandType.COLOR, easing, startTime, endTime);
        this.setStartColor(startColor);
        this.setEndColor(endColor);
    }


    // ============================================================

    // [GETTERS]


    public double getStartR() {
        return this.getParam(0);
    }


    public double getStartG() {
        return this.getParam(1);
    }


    public double getStartB() {
        return this.getParam(2);
    }


    public double getEndR() {
        return this.getParam(3);
    }


    public double getEndG() {
        return this.getParam(4);
    }


    public double getEndB() {
        return this.getParam(5);
    }


    public OsbColor getStartColor() {
        return new OsbColor(this.getStartR(), this.getStartG(), this.getStartB());
    }


    public OsbColor getEndColor() {
        return new OsbColor(this.getEndR(), this.getEndG(), this.getEndB());
    }


    // ============================================================

    // [SETTERS]


    public void setStartR(double value) {
        OsbUtils.assertParameterRange(value, OsbParameter.COLOR);
        this.setParam(0, value);
    }


    public void setStartG(double value) {
        OsbUtils.assertParameterRange(value, OsbParameter.COLOR);
        this.setParam(1, value);
    }


    public void setStartB(double value) {
        OsbUtils.assertParameterRange(value, OsbParameter.COLOR);
        this.setParam(2, value);
    }


    public void setEndR(double value) {
        OsbUtils.assertParameterRange(value, OsbParameter.COLOR);
        this.setParam(3, value);
    }


    public void setEndG(double value) {
        OsbUtils.assertParameterRange(value, OsbParameter.COLOR);
        this.setParam(4, value);
    }


    public void setEndB(double value) {
        OsbUtils.assertParameterRange(value, OsbParameter.COLOR);
        this.setParam(5, value);
    }


    public void setStartColor(OsbColor color) {
        this.setStartR(color.getR());
        this.setStartG(color.getG());
        this.setStartB(color.getB());
    }


    public void setEndColor(OsbColor color) {
        this.setEndR(color.getR());
        this.setEndG(color.getG());
        this.setEndB(color.getB());
    }


}
