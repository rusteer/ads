package com.rot.setting;
import java.util.Random;

public class Setting {
    private static String hostName1 = "localhost1";
    private static String hostName2 = "localhost2";
    public static String getHostName() {
        return new Random().nextBoolean() ? hostName1 : hostName2;
    }
}
