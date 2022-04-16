package io.rulebased.group.javahelp.converter.antora.convert.anchor;

import io.rulebased.group.javahelp.converter.antora.logging.ILfetLogging;
import org.jsoup.nodes.Element;

public interface IAnchorConverter {

    static IAnchorConverter create(ILfetLogging logger) {
        return new AnchorConverter(logger);
    }

    String convert(Element element);

}
