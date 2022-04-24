package io.rulebased.group.javahelp.converter.antora;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import io.rulebased.group.javahelp.converter.antora.convert.anchor.IAnchorConverter;
import io.rulebased.group.javahelp.converter.antora.convert.html.IHtmlConverter;
import io.rulebased.group.javahelp.converter.antora.convert.images.IImageConverter;
import io.rulebased.group.javahelp.converter.antora.convert.toc.IToCConverter;
import io.rulebased.group.javahelp.converter.antora.exception.JavaHelpToAntoraConverterException;
import io.rulebased.group.javahelp.converter.antora.logging.ILfetLogging;
import io.rulebased.group.javahelp.converter.config.ConverterConfig;
import io.rulebased.group.javahelp.converter.facade.InputFacade;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

class JavaHelpToAntoraConverter implements JavaHelpToAntoraConverterDT<JavaHelpToAntoraConverterModel>, IJavaHelpToAntoraConverter {

    static final JavaHelpToAntoraConverterRulesEngine rulesEngine = new JavaHelpToAntoraConverterRulesEngine();
    final ILfetLogging lfetLogging;
    final IToCConverter toCConverter;

    JavaHelpToAntoraConverter(ILfetLogging lfetLogging) {
        this.lfetLogging = lfetLogging;
        toCConverter = IToCConverter.create(lfetLogging, IImageConverter.create(lfetLogging), IHtmlConverter.create(lfetLogging, IAnchorConverter.create(lfetLogging)));
    }


    @Override
    public void execute(ConverterConfig config, InputFacade inputFacade) {
        JavaHelpToAntoraConverterModel javaHelpToAntoraConverterModel = new JavaHelpToAntoraConverterModel(config, inputFacade);
        rulesEngine.execute(this, javaHelpToAntoraConverterModel);
    }


    @Override
    public boolean isExistsTableOfContentFile(JavaHelpToAntoraConverterModel model) {
        return model.inputFacade.isExistFile(Path.of(model.config.getInput().getTableOfContentFileName()));
    }

    @Override
    public void doError(Error arg0, JavaHelpToAntoraConverterModel model) {
        throw new JavaHelpToAntoraConverterException(String.format(arg0.getTitle(), model.config.getInput().getJarFile().getPath()));
    }

    @Override
    public void doTrace(String dtName, String version, int rules, int rule, JavaHelpToAntoraConverterModel model) {
        lfetLogging.trace(dtName, version, rules, rule, model);
    }

    @Override
    public void doCreateModulWithNameOfTableOfContentEntry(JavaHelpToAntoraConverterModel model) {
        //noinspection ResultOfMethodCallIgnored
        model.getModulDirectoryName().mkdirs();
        //noinspection ResultOfMethodCallIgnored
        new File(model.getModulDirectoryName(), "pages").mkdirs();
    }

    @Override
    public void doProcessTableOfContentEntry(JavaHelpToAntoraConverterModel model) {
        toCConverter.execute(model.config, model.getModulDirectoryName(), model.processingModel.currentTOCElement, model.inputFacade, 1);
    }

    @Override
    public boolean isNextTableOfContentEntryOnLevel1Exists(JavaHelpToAntoraConverterModel model) {
        if (model.processingModel.tocEntriesIt == null) {
            model.processingModel.tocEntriesIt = model.processingModel.tableOfContentDocument.getRootElement().getChildren("tocitem").iterator();
        }
        if (model.processingModel.tocEntriesIt.hasNext()) {
            model.processingModel.currentTOCElement = model.processingModel.tocEntriesIt.next();
        } else {
            model.processingModel.currentTOCElement = null;
        }
        return model.processingModel.currentTOCElement != null;
    }

    @Override
    public void doReadToCFile(JavaHelpToAntoraConverterModel model) {
        model.processingModel.tableOfContentDocument = model.inputFacade.getTableOfContentFile(model.config.getInput().getTableOfContentFileName());
    }

    @Override
    public void doDeleteModulesDirectory(JavaHelpToAntoraConverterModel model) {
        try {
            //noinspection ResultOfMethodCallIgnored
            Files.walk(model.config.getOutput().getDirectory().toPath())
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
        } catch (IOException e) {
            throw new JavaHelpToAntoraConverterException("Unknown error on deleting output directory", e);
        }
    }

    @Override
    public void doAddModuleEntryToNav(JavaHelpToAntoraConverterModel model) {
        String moduleName = model.processingModel.currentTOCElement.getAttributeValue("text").replaceAll(" ", "_").replaceAll(":", "");
        model.config.getOutput().getAntoraYml().getNav().add("modules/" + moduleName + "/nav.adoc");
    }

    @Override
    public void doSaveAntoraYmlToDocsDirectory(JavaHelpToAntoraConverterModel model) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        try {
            mapper.writeValue(new File(model.config.getOutput().getDirectory(), "antora.yml"), model.config.getOutput().getAntoraYml());
        } catch (IOException e) {
            throw new JavaHelpToAntoraConverterException("Unknown error occured while writing antora.yml file", e);
        }
    }
}