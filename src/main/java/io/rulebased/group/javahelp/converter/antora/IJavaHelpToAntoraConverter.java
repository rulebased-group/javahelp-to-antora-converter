package io.rulebased.group.javahelp.converter.antora;

import io.rulebased.group.javahelp.converter.antora.logging.ILfetLogging;
import io.rulebased.group.javahelp.converter.config.ConverterConfig;
import io.rulebased.group.javahelp.converter.facade.InputFacade;

public interface IJavaHelpToAntoraConverter {

    static IJavaHelpToAntoraConverter create(ILfetLogging logging) {
        return new JavaHelpToAntoraConverter(logging);
    }

    void execute(ConverterConfig config, InputFacade inputFacade);
}
