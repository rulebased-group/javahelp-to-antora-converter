package io.rulebased.group.javahelp.converter.antora;

import io.rulebased.group.javahelp.converter.config.ConverterConfig;
import io.rulebased.group.javahelp.converter.facade.InputFacade;
import org.jdom2.Document;
import org.jdom2.Element;

import java.io.File;
import java.nio.file.Path;
import java.util.Iterator;
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
    }

    static class ProcessingModel {

        ZipFile zipFile;
        Document tableOfContentDocument;
        Iterator<Element> tocEntriesIt;
        Element currentTOCElement;

        ZipEntry getTOCFile() {
            return zipFile.getEntry("LF-ET-toc.xml");
        }

    }

    File getModulDirectoryName() {
        String normalizedDirectoryName = processingModel.currentTOCElement.getAttributeValue("text").replaceAll(" ", "_").replaceAll(":","");
        return Path.of(config.getOutput().getDirectory().getPath(), "modules", normalizedDirectoryName).toFile();
    }




}
