package io.rulebased.group.javahelp.converter.antora.convert.images;

import io.rulebased.group.javahelp.converter.antora.logging.ILfetLogging;
import io.rulebased.group.javahelp.converter.config.ConverterConfig;
import io.rulebased.group.javahelp.converter.facade.InputFacade;

import java.io.File;

public interface IImageConverter {

    static IImageConverter create(ILfetLogging lfetLogging) {
        return new HtmlImageExtractor(lfetLogging);
    }

    void execute(ConverterConfig config, File moduleDirectory, InputFacade inputFacade, String currentFileName);

}
