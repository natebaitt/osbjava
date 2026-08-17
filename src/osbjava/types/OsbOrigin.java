package osbjava.types;

/**
 * Enumeration for storyboard object origins.
 */
public enum OsbOrigin {

    TOP_LEFT,
    CENTER,
    CENTER_LEFT,
    TOP_RIGHT,
    BOTTOM_CENTER,
    TOP_CENTER,
    CENTER_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT;

    public String toString() {
        return switch (this) {
            case TOP_LEFT ->      "TopLeft";
            case CENTER ->        "Centre";
            case CENTER_LEFT ->   "CentreLeft";
            case TOP_RIGHT ->     "TopRight";
            case BOTTOM_CENTER -> "BottomCentre";
            case TOP_CENTER ->    "TopCentre";
            case CENTER_RIGHT ->  "CentreRight";
            case BOTTOM_LEFT ->   "BottomLeft";
            case BOTTOM_RIGHT ->  "BottomRight";
        };
    }

}
