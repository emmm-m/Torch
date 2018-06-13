package com.bucai.torch.util;

import android.util.Log;

/**
 * Created by zia on 2018/5/24.
 */
public class LogUtil {

    private final static boolean openLog = true;
    private final static String TAG = "LogUtil";


    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(String TAG, String msg) {
        if (openLog && checkNull("e", TAG, msg))
            Log.e(TAG, msg);
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(String TAG, String msg) {
        if (openLog && checkNull("d", TAG, msg))
            Log.d(TAG, msg);
    }

    private static boolean checkNull(String level, String TAG, String msg) {
        if (msg != null) return true;
        switch (level) {
            case "d":
                Log.d(TAG, "msg is null");
                break;
            case "e":
                Log.e(TAG, "msg is null");
                break;
        }
        return false;
    }
}
