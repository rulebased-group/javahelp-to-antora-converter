package io.rulebased.group.javahelp.converter.antora.logging;

public interface ILfetLogging {

    <T> void trace(String lfet, String version, int currentRule, int maxRules, T model);

}
