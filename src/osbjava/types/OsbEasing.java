package osbjava.types;

/**
 * Enumeration for easing movements.
 */
public enum OsbEasing {

    LINEAR,
    EASE_OUT,
    EASE_IN,
    QUAD_IN,
    QUAD_OUT,
    QUAD_IN_OUT,
    CUBIC_IN,
    CUBIC_OUT,
    CUBIC_IN_OUT,
    QUART_IN,
    QUART_OUT,
    QUART_IN_OUT,
    QUINT_IN,
    QUINT_OUT,
    QUINT_IN_OUT,
    SINE_IN,
    SINE_OUT,
    SINE_IN_OUT,
    EXPO_IN,
    EXPO_OUT,
    EXPO_IN_OUT,
    CIRC_IN,
    CIRC_OUT,
    CIRC_IN_OUT,
    ELASTIC_IN,
    ELASTIC_OUT,
    ELASTIC_HALF_OUT,
    ELASTIC_QUARTER_OUT,
    ELASTIC_IN_OUT,
    BACK_IN,
    BACK_OUT,
    BACK_IN_OUT,
    BOUNCE_IN,
    BOUNCE_OUT,
    BOUNCE_IN_OUT;

    public String toString() {
        return switch (this) {
            case LINEAR ->              "0";
            case EASE_OUT ->            "1";
            case EASE_IN ->             "2";
            case QUAD_IN ->             "3";
            case QUAD_OUT ->            "4";
            case QUAD_IN_OUT ->         "5";
            case CUBIC_IN ->            "6";
            case CUBIC_OUT ->           "7";
            case CUBIC_IN_OUT ->        "8";
            case QUART_IN ->            "9";
            case QUART_OUT ->           "10";
            case QUART_IN_OUT ->        "11";
            case QUINT_IN ->            "12";
            case QUINT_OUT ->           "13";
            case QUINT_IN_OUT ->        "14";
            case SINE_IN ->             "15";
            case SINE_OUT ->            "16";
            case SINE_IN_OUT ->         "17";
            case EXPO_IN ->             "18";
            case EXPO_OUT ->            "19";
            case EXPO_IN_OUT ->         "20";
            case CIRC_IN ->             "21";
            case CIRC_OUT ->            "22";
            case CIRC_IN_OUT ->         "23";
            case ELASTIC_IN ->          "24";
            case ELASTIC_OUT ->         "25";
            case ELASTIC_HALF_OUT ->    "26";
            case ELASTIC_QUARTER_OUT -> "27";
            case ELASTIC_IN_OUT ->      "28";
            case BACK_IN ->             "29";
            case BACK_OUT ->            "30";
            case BACK_IN_OUT ->         "31";
            case BOUNCE_IN ->           "32";
            case BOUNCE_OUT ->          "33";
            case BOUNCE_IN_OUT ->       "34";
        };
    }

}
