package osbjava.types;

/**
 * Enumeration for storyboard layers.
 */
public enum OsbLayer {

    BACKGROUND,
    FAIL,
    PASS,
    FOREGROUND;

    public String toString() {
        return switch (this) {
            case BACKGROUND -> "Background";
            case FAIL ->       "Fail";
            case PASS ->       "Pass";
            case FOREGROUND -> "Foreground";
        };
    }

}