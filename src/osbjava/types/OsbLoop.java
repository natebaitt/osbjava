package osbjava.types;

/**
 * Enumeration for animation loop styles.
 */
public enum OsbLoop {

    LOOP_FOREVER,
    LOOP_ONCE;

    public String toString() {
        return switch (this) {
            case LOOP_FOREVER -> "LoopForever";
            case LOOP_ONCE ->    "LoopOnce";
        };
    }

}
