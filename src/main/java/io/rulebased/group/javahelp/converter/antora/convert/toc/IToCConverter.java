package io.rulebased.group.javahelp.converter.antora.convert.toc;

import io.rulebased.group.javahelp.converter.antora.convert.html.IHtmlConverter;
import io.rulebased.group.javahelp.converter.antora.convert.images.IImageConverter;
import io.rulebased.group.javahelp.converter.antora.logging.ILfetLogging;
import io.rulebased.group.javahelp.converter.config.ConverterConfig;
import io.rulebased.group.javahelp.converter.facade.InputFacade;
import org.jdom2.Element;

import java.io.File;

public interface IToCConverter {

    static IToCConverter create(ILfetLogging lfetLogging, IImageConverter imageConverter, IHtmlConverter htmlConverter) {
        return new TableOfContentConverter(lfetLogging, imageConverter, htmlConverter);
    }

    void execute(ConverterConfig config, File outputModuleDirectory, Element tocElement, InputFacade inputFacade, int currentToCLevel);
}
