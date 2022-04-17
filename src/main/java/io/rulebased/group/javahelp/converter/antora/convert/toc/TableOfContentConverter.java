package io.rulebased.group.javahelp.converter.antora.convert.toc;

import io.rulebased.group.javahelp.converter.antora.convert.html.IHtmlConverter;
import io.rulebased.group.javahelp.converter.antora.convert.images.IImageConverter;
import io.rulebased.group.javahelp.converter.antora.exception.JavaHelpToAntoraConverterException;
import io.rulebased.group.javahelp.converter.antora.logging.ILfetLogging;
import io.rulebased.group.javahelp.converter.config.ConverterConfig;
import io.rulebased.group.javahelp.converter.facade.InputFacade;
import lombok.RequiredArgsConstructor;
import org.jdom2.Element;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

@RequiredArgsConstructor
class TableOfContentConverter implements JHTAC_ToCEntryDT<TableOfContentConverterModel>, IToCConverter {

    final static JHTAC_ToCEntryRulesEngine rulesEngine = new JHTAC_ToCEntryRulesEngine();
    final ILfetLogging lfetLogging;
    final IImageConverter imageConverter;
    final IHtmlConverter htmlConverter;


    @Override
    public void execute(ConverterConfig config, File outputModuleDirectory, Element tocElement, InputFacade inputFacade, int currentToCLevel) {
        rulesEngine.execute(this, new TableOfContentConverterModel(config, outputModuleDirectory, inputFacade, tocElement, currentToCLevel));
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
        return model.inputFacade.isExistFile(Path.of(model.tocElement.getAttributeValue("target")));
    }

    @Override
    public boolean isSaveOriginalFile(TableOfContentConverterModel model) {
        return model.config.getOutput().isSaveOriginalHtmlFile();
    }

    @Override
    public void doError(Error arg0, TableOfContentConverterModel model) {
        throw new JavaHelpToAntoraConverterException(arg0.getTitle());
    }

    @Override
    public void doProcessTableOfContentEntry(TableOfContentConverterModel model) {
        rulesEngine.execute(this, new TableOfContentConverterModel(model.config, model.moduleDirectory, model.inputFacade, model.currentNestedToCEntry, model.currentToCLevel + 1));
    }

    @Override
    public void doConvertHtmlToAdoc(TableOfContentConverterModel model) {
        model.asciidocContent = htmlConverter.execute(model.config, model.inputFacade, model.tocElement.getAttributeValue("target"));
    }


    @Override
    public void doCreatePagesDirectory(TableOfContentConverterModel model) {
        //noinspection ResultOfMethodCallIgnored
        model.getPagesDirectory().mkdirs();
    }

    @Override
    public void doSaveOriginalFile(SaveOriginalFile arg0, TableOfContentConverterModel model) {
        switch (arg0) {
            case $001: {
                String originalFileName = model.tocElement.getAttributeValue("target");
                String originalFileContent = model.inputFacade.getContentOfFile(originalFileName);
                try {
                    Files.writeString(Path.of(model.getPagesDirectory().getPath(), originalFileName), originalFileContent, model.config.getOutput().getEncoding(), StandardOpenOption.CREATE);
                } catch (IOException e) {
                    throw new JavaHelpToAntoraConverterException("Failed to save file " + originalFileName + " in " + model.getPagesDirectory().getPath(), e);
                }
                break;
            }
            case $002: {
                List<String> fileContent = model.asciidocContent;
                String fileName = model.getPageName();
                try {
                    Files.write(Path.of(model.getPagesDirectory().getPath(), fileName), fileContent, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
                } catch (IOException e) {
                    throw new JavaHelpToAntoraConverterException("Failed to save file " + fileName + " in " + model.getPagesDirectory().getPath(), e);
                }
                break;
            }
        }
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
        imageConverter.execute(model.config, model.moduleDirectory, model.inputFacade, model.tocElement.getAttributeValue("target"));
    }

    @Override
    public void doTrace(String dtName, String version, int rules, int rule, TableOfContentConverterModel model) {
        lfetLogging.trace(dtName, version, rules, rule, model);
    }
}
