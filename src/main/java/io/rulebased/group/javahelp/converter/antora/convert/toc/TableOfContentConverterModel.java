package io.rulebased.group.javahelp.converter.antora.convert.toc;

import io.rulebased.group.javahelp.converter.config.ConverterConfig;
import io.rulebased.group.javahelp.converter.facade.InputFacade;
import lombok.ToString;
import org.jdom2.Element;

import java.io.File;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipFile;

@ToString
class TableOfContentConverterModel {

    ConverterConfig config;
    InputFacade inputFacade;
    File moduleDirectory;
    Element tocElement;
    Iterator<Element> nestedToCEntries;
    Element currentNestedToCEntry;
    int currentToCLevel;
    List<String> asciidocContent;

    TableOfContentConverterModel(ConverterConfig config, File moduleDirectory, InputFacade inputFacade, Element tocElement, int currentToCLevel){
        this.config = config;
        this.inputFacade = inputFacade;
        this.moduleDirectory = moduleDirectory;
        this.tocElement = tocElement;
        this.nestedToCEntries = tocElement.getChildren("tocitem").iterator();
        this.currentToCLevel = currentToCLevel;
    }

    String getPageName() {
        return tocElement.getAttributeValue("target") + ".adoc";
    }

    File getPageFile() {
        return new File(moduleDirectory, Path.of("pages", getPageName()).toString());
    }

    File getPagesDirectory() {
        return new File(moduleDirectory, "pages");
    }

    File getNavFile() {
        return new File(moduleDirectory, "nav.adoc");
    }

    String getAdocNavEntry() {
        return String.format("%s xref:%s[]%s", new String(new char[currentToCLevel]).replace("\0", "*"), getPageName(), System.lineSeparator());
    }

}
