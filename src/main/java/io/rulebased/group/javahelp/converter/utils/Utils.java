package io.rulebased.group.javahelp.converter.utils;

public final class Utils {

    public static String convertTextForAdoc(String input) {
        String result = input
            .replaceAll("[$][{]","{dollarbracket}")
            .replaceAll("[|]","{vbar}")
            ;

        return result;
    }

}
