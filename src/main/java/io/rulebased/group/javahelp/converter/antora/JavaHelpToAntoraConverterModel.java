package io.rulebased.group.javahelp.converter.antora;

import io.rulebased.group.javahelp.converter.config.ConverterConfig;
import io.rulebased.group.javahelp.converter.facade.InputFacade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jdom2.Document;
import org.jdom2.Element;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

class JavaHelpToAntoraConverterModel {

    ProcessingModel processingModel;

    ConverterConfig config;

    InputFacade inputFacade;


    JavaHelpToAntoraConverterModel(ConverterConfig config, InputFacade inputFacade) {
        this.config = config;
        this.inputFacade = inputFacade;
        this.processingModel = new ProcessingModel();
        this.processingModel.antoraYml = new AntoraYml("lf-customer", "LF-ET User Manual", "master", "", new ArrayList<>(), new HashMap<>());
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
        return Path.of(config.getOutput().getDirectory().getPath(), "modules", normalizedDirectoryName).toFile();
    }

    @AllArgsConstructor
    @Getter
    static class AntoraYml {

        String name;
        String title;
        String version;
        String startPage;
        List<String> nav;
        Map<String, Object> asciidoc;

    }


}
