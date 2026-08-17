package osbjava.objects;

import osbjava.exceptions.*;
import osbjava.types.*;


@SuppressWarnings("unused")
public class DynamicAnimation extends DynamicSprite {


    // ============================================================

    // [VARIABLES]


    private int     frameCount;
    private double  frameDelay;
    private OsbLoop loopType;


    // ============================================================

    // [CONSTRUCTORS]


    public DynamicAnimation(String filepath, OsbLayer layer, OsbOrigin origin, double startTime,
                         double x, double y,
                         int frameCount, double frameDelay, OsbLoop loopType) {
        super(OsbObjectType.DYNAMIC_ANIMATION, layer, origin, filepath, x, y, startTime);
        init(frameCount, frameDelay, loopType);
    }


    public DynamicAnimation(String filepath, OsbLayer layer, OsbOrigin origin, double startTime,
                            int frameCount, double frameDelay, OsbLoop loopType) {
        super(OsbObjectType.DYNAMIC_ANIMATION, layer, origin, filepath,
                OsbConstants.CENTER_X, OsbConstants.CENTER_Y, startTime);
        init(frameCount, frameDelay, loopType);
    }


    public DynamicAnimation(String filepath, OsbLayer layer, double startTime,
                            int frameCount, double frameDelay, OsbLoop loopType) {
        super(OsbObjectType.DYNAMIC_ANIMATION, layer, OsbOrigin.CENTER, filepath,
                OsbConstants.CENTER_X, OsbConstants.CENTER_Y, startTime);
        init(frameCount, frameDelay, loopType);
    }


    public DynamicAnimation(String filepath, OsbOrigin origin, double startTime,
                            int frameCount, double frameDelay, OsbLoop loopType) {
        super(OsbObjectType.DYNAMIC_ANIMATION, OsbLayer.BACKGROUND, origin, filepath,
                OsbConstants.CENTER_X, OsbConstants.CENTER_Y, startTime);
        init(frameCount, frameDelay, loopType);
    }


    public DynamicAnimation(String filepath, double startTime,
                            int frameCount, double frameDelay, OsbLoop loopType) {
        super(OsbObjectType.DYNAMIC_ANIMATION, OsbLayer.BACKGROUND, OsbOrigin.CENTER, filepath,
                OsbConstants.CENTER_X, OsbConstants.CENTER_Y, startTime);
        init(frameCount, frameDelay, loopType);
    }


    private void init(int frameCount, double frameDelay, OsbLoop loopType) {

        /* Check if object parameters are in range */
        if (frameCount < 1 || frameDelay < 0) {
            throw new AnimationException();
        }

        /* Initialize animation parameters */
        this.frameCount = frameCount;
        this.frameDelay = frameDelay;
        this.loopType = loopType;
    }


    // ============================================================

    // [GETTERS]


    public int getFrameCount() {
        return this.frameCount;
    }


    public double getFrameDelay() {
        return this.frameDelay;
    }


    public OsbLoop getLoopType() {
        return this.loopType;
    }


    // ============================================================

    // [SETTERS]


    public void setFrameCount(int frameCount) {
        if (frameCount < 1) {
            throw new AnimationException();
        }
        this.frameCount = frameCount;
    }


    public void setFrameDelay(double frameDelay) {
        if (frameDelay < 0) {
            throw new AnimationException();
        }
        this.frameDelay = frameDelay;
    }


    public void setLoopType(OsbLoop loopType) {
        this.loopType = loopType;
    }


}
