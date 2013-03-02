/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afstudeerproject;

import java.util.ArrayList;

public class Log {

    static ArrayList<String> log = new ArrayList<String>();
    static long last = 0;

    public static void log(String message) {

        if (last == 0) {
            message += " at time " + System.currentTimeMillis();
        } else {
            message += ". Elapsed since last log: " + dtime(last, System.currentTimeMillis());
        }
        last = System.currentTimeMillis();
        log.add(message);
    }

    public static void showtime() {
        System.out.println("-------------TIMINGS-------------");
        for (String string : log) {
            System.out.println(string);
        }
        System.out.println("---------------------------------");
    }

    public static double dtime(long t1, long t2) {
        long dt = t2 - t1;
        return dt / 1000.0;
    }
}
