package io.rulebased.group.javahelp.converter.antora.convert.html;

import io.rulebased.group.javahelp.converter.antora.convert.anchor.IAnchorConverter;
import io.rulebased.group.javahelp.converter.antora.logging.ILfetLogging;
import io.rulebased.group.javahelp.converter.config.ConverterConfig;
import io.rulebased.group.javahelp.converter.facade.InputFacade;

import java.util.List;

public interface IHtmlConverter {

    static IHtmlConverter create(ILfetLogging lfetLogging, IAnchorConverter anchorConverter) {
        return new HtmlToAsciiDocConverter(lfetLogging, anchorConverter);
    }

    List<String> execute(ConverterConfig config, InputFacade inputFacade, String fileName);
}
