package io.rulebased.group.javahelp.antora.converter;

import org.jdom2.Element;

import java.io.File;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.zip.ZipFile;

class TableOfContentConverterModel {

    File moduleDirectory;
    ZipFile zipFile;
    Element tocElement;
    Iterator<Element> nestedToCEntries;
    Element currentNestedToCEntry;
    int currentToCLevel;

    TableOfContentConverterModel(File moduleDirectory, ZipFile zipFile, Element tocElement, int currentToCLevel){
        this.moduleDirectory = moduleDirectory;
        this.zipFile = zipFile;
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

    File getNavFile() {
        return new File(moduleDirectory, "nav.adoc");
    }

    String getAdocNavEntry() {
        return String.format("%s xref:%s[]%s", new String(new char[currentToCLevel]).replace("\0", "*"), getPageName(), System.lineSeparator());
    }

}
