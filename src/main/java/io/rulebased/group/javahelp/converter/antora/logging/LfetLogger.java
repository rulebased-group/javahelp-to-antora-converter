package io.rulebased.group.javahelp.converter.antora.logging;

public class LfetLogger implements ILfetLogging{
    @Override
    public <T> void trace(String lfet, String version, int currentRule, int maxRules, T model) {
        System.out.println("Trace: " + lfet + " " + version + " " + currentRule + " " + maxRules + " " + model);
    }
}
