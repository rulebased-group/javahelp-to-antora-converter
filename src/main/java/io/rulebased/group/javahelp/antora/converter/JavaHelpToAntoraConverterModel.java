package io.rulebased.group.javahelp.antora.converter;

import lombok.AllArgsConstructor;
import org.jdom2.Document;
import org.jdom2.Element;

import java.io.File;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

class JavaHelpToAntoraConverterModel {

    ProcessingModel processingModel;

    File inputJarFile;
    File outputDirectory;


    JavaHelpToAntoraConverterModel(File inputJarFile, File outputDirectory) {
        this.inputJarFile = inputJarFile;
        this.outputDirectory = outputDirectory;
        this.processingModel = new ProcessingModel();
    }

    static class ProcessingModel {

        ZipFile zipFile;
        Document tableOfContentDocument;
        Iterator<Element> tocEntriesIt;
        Element currentTOCElement;
        AntoraYml antoraYml;

        ZipEntry getTOCFile() {
            return zipFile.getEntry("LF-ET-toc.xml");
        }

    }

    File getModulDirectoryName() {
        String normalizedDirectoryName = processingModel.currentTOCElement.getAttributeValue("text").replaceAll(" ", "_").replaceAll(":","");
        return Path.of(outputDirectory.getPath(), "modules", normalizedDirectoryName).toFile();
    }

    @AllArgsConstructor
    static class AntoraYml {

        String name;
        String title;
        String version;
        String startPage;
        List<String> nav;
        Map<String, Object> asciidoc;

    }


}
