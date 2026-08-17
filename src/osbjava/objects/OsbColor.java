package osbjava.objects;

import osbjava.exceptions.*;
import osbjava.types.*;
import java.io.Serializable;


@SuppressWarnings("unused")
public class OsbColor implements Serializable {


    // ============================================================

    // [VARIABLES]


    private double R;
    private double G;
    private double B;


    // ============================================================

    // [CONSTRUCTORS]


    public OsbColor(double R, double G, double B) {
        this.setR(R);
        this.setG(G);
        this.setB(B);
    }


    public OsbColor(String hexValue) {
        this.setHex(hexValue);
    }


    // ============================================================

    // [GETTERS]


    public double getR() {
        return this.R;
    }


    public double getG() {
        return this.G;
    }


    public double getB() {
        return this.B;
    }


    public String getHex() {
        return String.format("%02x%02x%02x", (int) R, (int) G, (int) B);
    }


    // ============================================================

    // [SETTERS]


    public void setR(double value) {
        OsbUtils.assertParameterRange(value, OsbParameter.COLOR);
        this.R = value;
    }


    public void setG(double value) {
        OsbUtils.assertParameterRange(value, OsbParameter.COLOR);
        this.G = value;
    }


    public void setB(double value) {
        OsbUtils.assertParameterRange(value, OsbParameter.COLOR);
        this.B = value;
    }


    public void setHex(String hexValue) {

        /* Check if string is correct size */
        if (hexValue.length() != 6) {
            throw new ColorException();
        }

        /* Check if string is a valid hex value */
        for (int i = 0; i < 6; i++) {
            if (!('0' <= hexValue.charAt(i) && hexValue.charAt(i) <= '9')) {
                if (!('a' <= hexValue.charAt(i) && hexValue.charAt(i) <= 'f')
                        && !('A' <= hexValue.charAt(i) && hexValue.charAt(i) <= 'F')) {
                    throw new ColorException();
                }
            }
        }

        /* Parse RGB components */
        this.setR(Integer.parseInt(hexValue.substring(0, 2), 16));
        this.setG(Integer.parseInt(hexValue.substring(2, 4), 16));
        this.setB(Integer.parseInt(hexValue.substring(4, 6), 16));
    }


    // ============================================================

    // [METHODS]


    @Override
    public boolean equals(Object obj) {

        /* Assert that object is not null */
        if (obj == null)
            throw new NullPointerException();

        /* Assert that object is an OsbColor */
        if (!(obj instanceof OsbColor))
            throw new IllegalArgumentException();

        return (this.getR() == ((OsbColor) obj).getR()) &&
               (this.getG() == ((OsbColor) obj).getG()) &&
               (this.getB() == ((OsbColor) obj).getB());
    }


}
