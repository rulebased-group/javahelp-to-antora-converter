package io.rulebased.group.javahelp.converter.utils;

import org.jdom2.Element;

public final class Utils {

    public static String getAdocfileName(String htmlFileName) {
        return isNotEmpty(htmlFileName) //
            && !(htmlFileName.matches("^(https?|mailto):.*")) //

            ? htmlFileName
            .replaceAll("(?i)[.]html?$", ".adoc") //
            .replaceAll("(?i)[-_.]lf[-_]et", "_lfet") //

            : htmlFileName;
    }

    public static boolean isNodeName(Element element, String name) {
        return element != null && element.getName().equals(name);
    }

    public static boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    public static boolean isNotEmpty(String input) {
        return !isEmpty(input);
    }


}
