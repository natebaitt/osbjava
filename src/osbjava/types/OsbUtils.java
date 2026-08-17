package osbjava.types;

import osbjava.exceptions.*;


public abstract class OsbUtils {


    // ============================================================

    // [VARIABLES]


    public static final String VERSION       = "1.1";
    public static final String CONSOLE_TAG   = "[osbjava " + VERSION + "] ";


    // ============================================================

    // [METHODS]


    public static double saturateParameter(double value, OsbParameter type) {

        switch (type) {

            case OPACITY: {
                if (value > 1) return 1;
                if (value < 0) return 0;
                return value;
            }

            case SCALE: {
                if (value < 0) return 0;
                return value;
            }

            case COLOR: {
                if (value > 255) return 255;
                if (value < 0) return 0;
                return value;
            }

            default:
                return value;
        }
    }


    public static void assertParameterRange(double value, OsbParameter type) {

        switch (type) {

            case OPACITY: {
                if (value < 0 || value > 1) {
                    throw new ParameterRangeException();
                }
                break;
            }

            case SCALE: {
                if (value < 0) {
                    throw new ParameterRangeException();
                }
                break;
            }

            case COLOR: {
                if (value < 0 || value > 255) {
                    throw new ParameterRangeException();
                }
                break;
            }

        }
    }


}
