package io.rulebased.group.javahelp.converter.antora.convert.toc;

import io.rulebased.group.javahelp.converter.config.ConverterConfig;
import io.rulebased.group.javahelp.converter.facade.InputFacade;
import io.rulebased.group.javahelp.converter.utils.Utils;
import lombok.ToString;
import org.jdom2.Element;

import java.io.File;
import java.util.Iterator;
import java.util.List;

@ToString
class ToCConverterModel {

    ConverterConfig config;
    InputFacade inputFacade;
    File moduleDirectory;
    Element tocElement;
    Iterator<Element> nestedToCEntries;
    Element currentNestedToCEntry;
    int currentToCLevel;
    List<String> asciidocContent;

    ToCConverterModel(ConverterConfig config, File moduleDirectory, InputFacade inputFacade, Element tocElement, int currentToCLevel) {
        this.config = config;
        this.inputFacade = inputFacade;
        this.moduleDirectory = moduleDirectory;
        this.tocElement = tocElement;
        this.nestedToCEntries = tocElement.getChildren("tocitem").iterator();
        this.currentToCLevel = currentToCLevel;
    }

    String getAdocfileName() {
        return Utils.getAdocfileName(tocElement.getAttributeValue("target"));
    }

    File getPagesDirectory() {
        return new File(moduleDirectory, "pages");
    }

    File getNavFile() {
        return new File(moduleDirectory, "nav.adoc");
    }

    String getAdocNavEntry() {
        final String result = String.format("%s xref:%s[]%s",
            new String(new char[currentToCLevel]).replace("\0", "*"),
            getAdocfileName(),
            System.lineSeparator());
        return result;
    }

}
