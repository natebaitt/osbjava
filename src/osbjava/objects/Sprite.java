package osbjava.objects;

import osbjava.commands.*;
import osbjava.types.*;


/**
 * Creates a traditional sprite object.
 * <p>
 *
 */
@SuppressWarnings("unused")
public class Sprite extends OsbObject {


    // ============================================================

    // [CONSTRUCTORS]


    public Sprite(String filepath, OsbLayer layer, OsbOrigin origin, double x, double y) {
        super(OsbObjectType.SPRITE, layer, origin, filepath, x, y);
    }


    public Sprite(String filepath, OsbLayer layer, OsbOrigin origin) {
        super(OsbObjectType.SPRITE, layer, origin, filepath, OsbConstants.CENTER_X, OsbConstants.CENTER_Y);
    }


    public Sprite(String filepath, OsbLayer layer) {
        super(OsbObjectType.SPRITE, layer, OsbOrigin.CENTER, filepath,
                OsbConstants.CENTER_X, OsbConstants.CENTER_Y);
    }


    public Sprite(String filepath, OsbOrigin origin) {
        super(OsbObjectType.SPRITE, OsbLayer.BACKGROUND, origin, filepath,
                OsbConstants.CENTER_X, OsbConstants.CENTER_Y);
    }


    public Sprite(String filepath) {
        super(OsbObjectType.SPRITE, OsbLayer.BACKGROUND, OsbOrigin.CENTER, filepath,
                OsbConstants.CENTER_X, OsbConstants.CENTER_Y);
    }


    Sprite(OsbObjectType objectType, OsbLayer layer, OsbOrigin origin, String filepath,
           double x, double y) {
        super(objectType, layer, origin, filepath, x, y);
    }


    // ============================================================

    // [FADE COMMANDS]


    public void fade(OsbEasing easing, double startTime, double endTime,
                     double startOpacity, double endOpacity) {
        this.addCommand(new FadeCommand(easing, startTime, endTime, startOpacity, endOpacity));
    }


    public void fade(double startTime, double endTime, double startOpacity, double endOpacity) {
        this.addCommand(new FadeCommand(OsbEasing.LINEAR, startTime, endTime, startOpacity, endOpacity));
    }


    public void fade(double startTime, double endTime, double opacity) {
        this.addCommand(new FadeCommand(OsbEasing.LINEAR, startTime, endTime, opacity, opacity));
    }


    public void fade(double time, double opacity) {
        this.addCommand(new FadeCommand(OsbEasing.LINEAR, time, time, opacity, opacity));
    }


    // ============================================================

    // [MOVE COMMANDS]


    public void move(OsbEasing easing, double startTime, double endTime,
                     double startX, double startY, double endX, double endY) {
        this.addCommand(new MoveCommand(easing, startTime, endTime, startX, startY, endX, endY));
    }


    public void move(double startTime, double endTime,
                     double startX, double startY, double endX, double endY) {
        this.addCommand(new MoveCommand(OsbEasing.LINEAR, startTime, endTime, startX, startY, endX, endY));
    }


    public void move(double startTime, double endTime,
                     double x, double y) {
        this.addCommand(new MoveCommand(OsbEasing.LINEAR, startTime, endTime, x, y, x, y));
    }


    public void move(double time, double x, double y) {
        this.addCommand(new MoveCommand(OsbEasing.LINEAR, time, time, x, y, x, y));
    }


    // ============================================================

    // [MOVE X COMMANDS]


    public void movex(OsbEasing easing, double startTime, double endTime,
                      double startX, double endX) {
        this.addCommand(new MoveXCommand(easing, startTime, endTime, startX, endX));
    }


    public void movex(double startTime, double endTime, double startX, double endX) {
        this.addCommand(new MoveXCommand(OsbEasing.LINEAR, startTime, endTime, startX, endX));
    }


    public void movex(double startTime, double endTime, double x) {
        this.addCommand(new MoveXCommand(OsbEasing.LINEAR, startTime, endTime, x, x));
    }


    public void movex(double time, double x) {
        this.addCommand(new MoveXCommand(OsbEasing.LINEAR, time, time, x, x));
    }


    // ============================================================

    // [MOVE Y COMMANDS]


    public void movey(OsbEasing easing, double startTime, double endTime,
                      double startY, double endY) {
        this.addCommand(new MoveYCommand(easing, startTime, endTime, startY, endY));
    }


    public void movey(double startTime, double endTime, double startY, double endY) {
        this.addCommand(new MoveYCommand(OsbEasing.LINEAR, startTime, endTime, startY, endY));
    }


    public void movey(double startTime, double endTime, double y) {
        this.addCommand(new MoveYCommand(OsbEasing.LINEAR, startTime, endTime, y, y));
    }


    public void movey(double time, double y) {
        this.addCommand(new MoveYCommand(OsbEasing.LINEAR, time, time, y, y));
    }


    // ============================================================

    // [SCALE COMMANDS]


    public void scale(OsbEasing easing, double startTime, double endTime,
                      double startScale, double endScale) {
        this.addCommand(new ScaleCommand(easing, startTime, endTime, startScale, endScale));
    }


    public void scale(double startTime, double endTime, double startScale, double endScale) {
        this.addCommand(new ScaleCommand(OsbEasing.LINEAR, startTime, endTime, startScale, endScale));
    }


    public void scale(double startTime, double endTime, double scale) {
        this.addCommand(new ScaleCommand(OsbEasing.LINEAR, startTime, endTime, scale, scale));
    }


    public void scale(double time, double scale) {
        this.addCommand(new ScaleCommand(OsbEasing.LINEAR, time, time, scale, scale));
    }


    // ============================================================

    // [VECTOR SCALE COMMANDS]


    public void vscale(OsbEasing easing, double startTime, double endTime,
                       double startScaleX, double startScaleY,
                       double endScaleX, double endScaleY) {
        this.addCommand(new VectorScaleCommand(easing, startTime, endTime,
                startScaleX, startScaleY, endScaleX, endScaleY));
    }


    public void vscale(double startTime, double endTime,
                       double startScaleX, double startScaleY,
                       double endScaleX, double endScaleY) {
        this.addCommand(new VectorScaleCommand(OsbEasing.LINEAR, startTime, endTime,
                startScaleX, startScaleY, endScaleX, endScaleY));
    }


    public void vscale(double startTime, double endTime, double scaleX, double scaleY) {
        this.addCommand(new VectorScaleCommand(OsbEasing.LINEAR, startTime, endTime,
                scaleX, scaleY, scaleX, scaleY));
    }


    public void vscale(double time, double scaleX, double scaleY) {
        this.addCommand(new VectorScaleCommand(OsbEasing.LINEAR, time, time,
                scaleX, scaleY, scaleX, scaleY));
    }


    // ============================================================

    // [ROTATE COMMANDS]


    public void rotate(OsbEasing easing, double startTime, double endTime,
                       double startAngle, double endAngle) {
        this.addCommand(new RotateCommand(easing, startTime, endTime, startAngle, endAngle));
    }


    public void rotate(double startTime, double endTime, double startAngle, double endAngle) {
        this.addCommand(new RotateCommand(OsbEasing.LINEAR, startTime, endTime, startAngle, endAngle));
    }


    public void rotate(double startTime, double endTime, double angle) {
        this.addCommand(new RotateCommand(OsbEasing.LINEAR, startTime, endTime, angle, angle));
    }


    public void rotate(double time, double angle) {
        this.addCommand(new RotateCommand(OsbEasing.LINEAR, time, time, angle, angle));
    }


    // ============================================================

    // [COLOR COMMANDS]


    public void color(OsbEasing easing, double startTime, double endTime,
                      double startR, double startG, double startB,
                      double endR, double endG, double endB) {
        this.addCommand(new ColorCommand(easing, startTime, endTime,
                startR, startG, startB, endR, endG, endB));
    }


    public void color(double startTime, double endTime,
                      double startR, double startG, double startB,
                      double endR, double endG, double endB) {
        this.addCommand(new ColorCommand(OsbEasing.LINEAR, startTime, endTime,
                startR, startG, startB, endR, endG, endB));
    }


    public void color(double startTime, double endTime, double R, double G, double B) {
        this.addCommand(new ColorCommand(OsbEasing.LINEAR, startTime, endTime, R, G, B, R, G, B));
    }


    public void color(double time, double R, double G, double B) {
        this.addCommand(new ColorCommand(OsbEasing.LINEAR, time, time, R, G, B, R, G, B));
    }


    public void color(OsbEasing easing, double startTime, double endTime,
                      OsbColor startColor, OsbColor endColor) {
        this.addCommand(new ColorCommand(easing, startTime, endTime,
                startColor, endColor));
    }


    public void color(double startTime, double endTime,
                      OsbColor startColor, OsbColor endColor) {
        this.addCommand(new ColorCommand(OsbEasing.LINEAR, startTime, endTime,
                startColor, endColor));
    }


    public void color(double startTime, double endTime, OsbColor color) {
        this.addCommand(new ColorCommand(OsbEasing.LINEAR, startTime, endTime, color, color));
    }


    public void color(double time, OsbColor color) {
        this.addCommand(new ColorCommand(OsbEasing.LINEAR, time, time, color, color));
    }


}
