package projects.tetration;

import osbjava.objects.*;
import java.util.*;


@SuppressWarnings("unused")
public class BlurGenerator {


    // ============================================================
    // [GLOBAL VARIABLES]

    /* Taken from "Empty Town" */


    //static String project_name  = "tetration (a BFDITPOT medley)";
    static String osb_directory = ".\\src\\projects\\tetration\\beatmap\\";
    static String osb_name      = "nate bait - tetration (a BFDITPOT medley) (Nate Bait).osb";
    static String bin_directory = ".\\src\\projects\\tetration\\bin\\";
    static String bin_name      = "blur_generator.bin";

    static double start_time = 17398;
    static double end_time   = 54532;
    static double time_res   = 10;
    static double curr_time  = start_time;
    static double ref_time;

    static Random rnd         = new Random(System.currentTimeMillis());
    static double tempo       = 160;
    static double beat_length = (60 / tempo) * 1000;

    static double obj_gen_rate  = 650;
    static double obj_gen_var   = 250;
    static double obj_angle     = 25;
    static double[] obj_speed   = {25, 75};
    static double[] obj_opacity = {0.04, 0.10};
    static double[] obj_scale   = {0.4 * 2, 2 * 2};
    static double[] obj_rotate  = {-10, 10};
    static double obj_fade_rate = 0.05;
    static int init_count       = 24;

    static double x_lower_limit = -160;
    static double x_upper_limit = 760;
    static double y_upper_limit = -110;
    static double y_lower_limit = 700;


    // ============================================================
    // [INNER CLASSES]


    /* Subclass for handling blurs */
    static class Blur {
        DynamicSprite object;
        double opacity;

        public Blur(DynamicSprite object, double opacity) {
            this.object = object;
            this.opacity = opacity;
        }
    }


    // ============================================================
    // [HELPER METHODS]


    private static void printCurrTime() {
        System.out.print("Current time is " + curr_time + " ms.\r");
    }


    // ============================================================
    // [MAIN METHOD]


    public static void main(String[] args) {

        OsbFile file = new OsbFile();

        ArrayList<Blur> blurs = new ArrayList<>();


        // --------------
        // INITIALIZATION
        // --------------

        for (int i = 0; i < init_count; i++) {

            DynamicSprite object = new DynamicSprite("storyboard\\blur.png", curr_time);
            double x_pos = x_lower_limit + rnd.nextDouble() * (x_upper_limit - x_lower_limit);
            double y_pos = y_lower_limit + rnd.nextDouble() * (y_upper_limit - y_lower_limit);
            double scale = obj_scale[0] + rnd.nextDouble() * (obj_scale[1] - obj_scale[0]);
            double speed = obj_speed[0] + rnd.nextDouble() * (obj_speed[1] - obj_speed[0]);
            double opacity = obj_opacity[0] + rnd.nextDouble() * (obj_opacity[1] - obj_opacity[0]);
            double rotate = obj_rotate[0] + rnd.nextDouble() * (obj_rotate[1] - obj_rotate[0]);
            double angle = 90 - obj_angle/2 + rnd.nextDouble() * obj_angle;

            object.setPosition(x_pos, y_pos);
            object.setScale(scale);
            object.setOpacity(opacity);
            object.setRotateRate(rotate);
            // object.setFadeRate(obj_fade_rate);
            object.setPolarMoveVelocity(speed, angle);

            Blur blur = new Blur(object, 1);
            blurs.add(blur);

        }


        // ---------
        // RENDERING
        // ---------

        ref_time = curr_time;
        while (curr_time < end_time) {

            printCurrTime();

            /* Update existing objects */
            Iterator<Blur> iterator = blurs.iterator();
            while (iterator.hasNext()) {
                Blur blur = iterator.next();
                if (blur.object.getCurrY() > y_lower_limit) {
                    blur.object.writeAll();
                    file.addObject(blur.object);
                    iterator.remove();
                }
                if (blur.object.getCurrOpacity() >= blur.opacity) {
                    blur.object.setFadeRate(0);
                }
            }

            /* Generate new object */
            if (curr_time - ref_time >= obj_gen_rate) {

                DynamicSprite object = new DynamicSprite("storyboard\\blur.png", curr_time);
                double x_pos = x_lower_limit + rnd.nextDouble() * (x_upper_limit - x_lower_limit);
                double scale = obj_scale[0] + rnd.nextDouble() * (obj_scale[1] - obj_scale[0]);
                double speed = obj_speed[0] + rnd.nextDouble() * (obj_speed[1] - obj_speed[0]);
                double opacity = obj_opacity[0] + rnd.nextDouble() * (obj_opacity[1] - obj_opacity[0]);
                double rotate = obj_rotate[0] + rnd.nextDouble() * (obj_rotate[1] - obj_rotate[0]);
                double angle = 90 - obj_angle/2 + rnd.nextDouble() * obj_angle;

                object.setPosition(x_pos, y_upper_limit);
                object.setScale(scale);
                object.setOpacity(0);
                object.setRotateRate(rotate);
                object.setFadeRate(obj_fade_rate);
                object.setPolarMoveVelocity(speed, angle);

                Blur blur = new Blur(object, opacity);
                blurs.add(blur);

                ref_time = curr_time;
            }

            /* Increment object times */
            for (Blur blur : blurs) {
                blur.object.addLength(time_res);
            }
            curr_time += time_res;

        }

        for (Blur blur : blurs) {
            blur.object.writeAll();
            file.addObject(blur.object);
        }


        // ---------
        // EXPORTING
        // ---------

        file.exportBinary(bin_directory + bin_name);
        file.export(osb_directory + osb_name);


    } // [END MAIN]


}
