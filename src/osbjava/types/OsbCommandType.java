package osbjava.types;

/** Enumeration for storyboard commands. */
public enum OsbCommandType {

    FADE,
    MOVE,
    MOVE_X,
    MOVE_Y,
    SCALE,
    VECTOR_SCALE,
    ROTATE,
    COLOR;

    public String toString() {
        return switch (this) {
            case FADE ->         "F";
            case MOVE ->         "M";
            case MOVE_X ->       "MX";
            case MOVE_Y ->       "MY";
            case SCALE ->        "S";
            case VECTOR_SCALE -> "V";
            case ROTATE ->       "R";
            case COLOR ->        "C";
        };
    }

}
