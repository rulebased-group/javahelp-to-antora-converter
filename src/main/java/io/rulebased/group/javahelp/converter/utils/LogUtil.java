package io.rulebased.group.javahelp.converter.utils;

import org.apache.logging.log4j.Logger;

import java.util.Hashtable;

public final class LogUtil {

    public static String mStmtlastString = null; // just to avoid more than 1 'empty' line
    private static final Hashtable<Thread, Integer> threadLevels = new Hashtable<>();
    private static final String _LEV = "| ";

    public static boolean isLogLevelDebug() {
        return true;
    }

    /**
     * Method entry
     *
     * @param logger
     * @param s
     */
    public static void mEntry(Logger logger, String s) {
        mStmtlastString = s;
        int level = getThreadLogLevel();
        final String result = getSLevel(level) + "+--- Entry --> " + s;
        setThreadLogLevel(++level);

        logger.debug(result);
    }

    /**
     * Method exit
     *
     * @param logger
     * @param s
     */
    public static void mExit(Logger logger, String s) {
        mStmtlastString = s;
        int level = getThreadLogLevel();
        setThreadLogLevel(--level);
        final String result = getSLevel(level) + "+--- Exit <--  " + s;

        logger.debug(result);
    }

    public static synchronized void mStmtf(Logger logger, String format, Object... objects) {
        mStmt(logger, String.format(format, objects));
    }

    public static synchronized void mStmt(Logger logger, String... s) {
        for (String s2 : s) {
            mStmtf(logger, s2);
        }
    }

    public static synchronized void mStmt(Logger logger, final String s) {
        if (!isEmpty(s) || mStmtlastString == null || !isEmpty(mStmtlastString)) { // avoid multiple empty lines

            mStmtlastString = s;

            final int level = getThreadLogLevel();
            final String sLevel = getSLevel(level);

            for (final String sLine : s.split("(\\n\\r|\\r\\n|\\n|\\r)")) {
                //noinspection StringConcatenationArgumentToLogCall
                logger.debug(String.format("%s%s", sLevel, sLine));
            }
        }
    }

    public static int getThreadLogLevel() {
        Integer result = threadLevels.get(Thread.currentThread());

        if (result == null) {
            result = 0;
        }

        return result;
    }

    public static void setThreadLogLevel(final int newValue) {
        threadLevels.put(Thread.currentThread(), newValue);
    }

    private static String getSLevel(final int level) {
        return _LEV.repeat(Math.max(0, level));
    }

    public static boolean isEmpty(final String string) {
        return string == null || string.trim().isEmpty();
    }
}
