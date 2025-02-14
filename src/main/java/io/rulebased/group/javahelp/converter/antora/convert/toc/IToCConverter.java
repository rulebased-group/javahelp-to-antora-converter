package io.rulebased.group.javahelp.converter.antora.convert.toc;

import io.rulebased.group.javahelp.converter.antora.convert.html.IHtmlToAsciiDocConverter;
import io.rulebased.group.javahelp.converter.antora.convert.images.IHtmlImageExtractor;
import io.rulebased.group.javahelp.converter.antora.logging.ILfetLogging;
import io.rulebased.group.javahelp.converter.config.ConverterConfig;
import io.rulebased.group.javahelp.converter.facade.InputFacade;
import org.jdom2.Element;

import java.io.File;

public interface IToCConverter {

    static IToCConverter create(ILfetLogging lfetLogging, IHtmlImageExtractor imageConverter, IHtmlToAsciiDocConverter htmlConverter) {
        return new ToCConverter(lfetLogging, imageConverter, htmlConverter);
    }

    void execute(ConverterConfig config, File outputModuleDirectory, Element tocElement, InputFacade inputFacade, int currentToCLevel);
}
