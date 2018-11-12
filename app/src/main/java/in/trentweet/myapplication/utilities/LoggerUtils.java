package in.trentweet.myapplication.utilities;

import android.util.Log;

public class LoggerUtils {

    private static boolean isLogEnabled = true;

    public static void error(String tag) {
        if (isLogEnabled)
            Log.e(tag, "");
    }

    public static void info(String tag, String msg) {
        if (isLogEnabled)
            Log.i(tag, msg);
    }

    public static void verbose(String tag, String msg) {
        if (isLogEnabled)
            Log.v(tag, msg);
    }

    public static void debug(String tag, String msg) {
        if (isLogEnabled)
            Log.d(tag, msg);
    }

    public static void printMessage(String msg) {
        if (isLogEnabled)
            System.out.println(msg);
    }


}
