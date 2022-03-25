package io.rulebased.group.javahelp.antora.converter;

import org.jdom2.Element;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

class TableOfContentConverter implements JHTAC_ToCEntryDT<TableOfContentConverterModel> {

    final static JHTAC_ToCEntryRulesEngine rulesEngine = new JHTAC_ToCEntryRulesEngine();
    HtmlImageExtractor htmlImageExtractor = new HtmlImageExtractor();
    HtmlToAsciiDocConverter htmlToAsciiDocConverter = new HtmlToAsciiDocConverter();



    void execute(File outputModuleDirectory, Element tocElement, ZipFile zipFile, int currentToCLevel) {
        rulesEngine.execute(this, new TableOfContentConverterModel(outputModuleDirectory, zipFile, tocElement, currentToCLevel));
    }

    @Override
    public boolean isNextNestedTocEntryExists(TableOfContentConverterModel model) {
        if (model.nestedToCEntries.hasNext()) {
            model.currentNestedToCEntry = model.nestedToCEntries.next();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isExistsTargetFileInArchive(TableOfContentConverterModel model) {
        ZipEntry target = model.zipFile.getEntry(model.tocElement.getAttributeValue("target"));
        return target != null;
    }

    @Override
    public void doError(java.lang.Error arg0, TableOfContentConverterModel model) {
        throw new JavaHelpToAntoraConverterException(arg0.getTitle());
    }

    @Override
    public void doProcessTableOfContentEntry(TableOfContentConverterModel model) {
        rulesEngine.execute(this, new TableOfContentConverterModel(model.moduleDirectory, model.zipFile, model.currentNestedToCEntry, model.currentToCLevel + 1));
    }

    @Override
    public void doConvertAndSaveHtmlAsAdoc(TableOfContentConverterModel model) {
        htmlToAsciiDocConverter.execute(model.moduleDirectory, model.zipFile, model.zipFile.getEntry(model.tocElement.getAttributeValue("target")));
    }

    @Override
    public void doAddEntryToNav(TableOfContentConverterModel model) {
        try {
            Files.writeString(model.getNavFile().toPath(), model.getAdocNavEntry(), StandardCharsets.UTF_8, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException("Unknown Error", e);
        }
    }

    @Override
    public void doExtractImages(TableOfContentConverterModel model) {
        htmlImageExtractor.execute(model.moduleDirectory, model.zipFile, model.tocElement.getAttributeValue("target"));
    }

    @Override
    public void doTrace(String dtName, String version, int rules, int rule, TableOfContentConverterModel model) {
        System.out.printf("%s[%s] - %s/%s%n", dtName, version, rule, rules);
    }
}
