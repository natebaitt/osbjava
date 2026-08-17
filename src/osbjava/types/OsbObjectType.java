package osbjava.types;

/**
 * Enumeration for storyboard object types.
 */
public enum OsbObjectType {

    SPRITE,
    DYNAMIC_SPRITE,
    ANIMATION,
    DYNAMIC_ANIMATION;

    public String toString() {
        return switch (this) {
            case SPRITE, DYNAMIC_SPRITE ->       "Sprite";
            case ANIMATION, DYNAMIC_ANIMATION -> "Animation";
        };
    }

}


