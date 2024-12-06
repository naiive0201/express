package com.hyeonsoo.express.util;

public class EmptyCheckerUtil {

    public static boolean exists(Object obc) {
        return (obc != null);
    }

    public static boolean notExists(Object obc) {
        return (obc == null);
    }
}
