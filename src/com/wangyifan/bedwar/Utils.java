package com.wangyifan.bedwar;

public class Utils {
    static public String colorBoolean(Boolean key) {
        if (key) {
            return "\033[32mtrue\033[0m";
        } else {
            return "\033[31mfalse\033[0m";
        }
    }
    static public String colorString(String string, String color) {
        switch (color) {
            case "green":
                return "\033[32m" + string + "\033[0m";
            case "red":
                return "\033[31m" + string + "\033[0m";
            case "yellow":
                return "\033[33m" + string + "\033[0m";
            case "blue":
                return "\033[34m" + string + "\033[0m";
            case "magenta":
                return "\033[35m" + string + "\033[0m";
            case "cyan":
                return "\033[36m" + string + "\033[0m";
            default:
                return "-1";

        }
    }
}
