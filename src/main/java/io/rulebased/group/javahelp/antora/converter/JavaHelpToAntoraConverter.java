package io.rulebased.group.javahelp.antora.converter;

import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.zip.ZipFile;

public class JavaHelpToAntoraConverter implements JavaHelpToAntoraConverterDT<JavaHelpToAntoraConverterModel> {

    private static final JavaHelpToAntoraConverterRulesEngine rulesEngine = new JavaHelpToAntoraConverterRulesEngine();


    void execute(File inputJarFile, File outputDirectory) {
        JavaHelpToAntoraConverterModel javaHelpToAntoraConverterModel = new JavaHelpToAntoraConverterModel(inputJarFile, outputDirectory);
        rulesEngine.execute(this, javaHelpToAntoraConverterModel);
    }


    @Override
    public boolean isExistInputJarFile(JavaHelpToAntoraConverterModel model) {
        return model.inputJarFile.isFile();
    }

    @Override
    public boolean isToCFileExists(JavaHelpToAntoraConverterModel model) {
        try {
            model.processingModel.zipFile = new ZipFile(model.inputJarFile);
        } catch (IOException e) {
            throw new JavaHelpToAntoraConverterException("Unknown error", e);
        }
        return model.processingModel.getTOCFile() != null;
    }

    @Override
    public void doError(java.lang.Error arg0, JavaHelpToAntoraConverterModel model) {
        throw new JavaHelpToAntoraConverterException(arg0.getTitle());
    }

    @Override
    public void doTrace(String dtName, String version, int rules, int rule, JavaHelpToAntoraConverterModel model) {
        System.out.printf("%s[%s] - %s/%s%n", dtName, version, rule, rules);
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
        new TableOfContentConverter().execute(model.getModulDirectoryName(), model.processingModel.currentTOCElement, model.processingModel.zipFile, 1);
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
        SAXBuilder sax = new SAXBuilder();
        try {
            model.processingModel.tableOfContentDocument = sax.build(model.processingModel.zipFile.getInputStream(model.processingModel.getTOCFile()));
        } catch (JDOMException | IOException cause) {
            throw new JavaHelpToAntoraConverterException("Error on reading LF-ET-toc.xml from archive", cause);
        }
    }

    @Override
    public void doDeleteModulesDirectory(JavaHelpToAntoraConverterModel model) {
        try {
            //noinspection ResultOfMethodCallIgnored
            Files.walk(model.outputDirectory.toPath())
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            throw new JavaHelpToAntoraConverterException("Unknown error on deleting output directory", e);
        }
    }

    @Override
    public void doSaveAntoraYmlToDocsDirectory(JavaHelpToAntoraConverterModel model) {
        model.processingModel.antoraYml = new JavaHelpToAntoraConverterModel.AntoraYml("lf-customer", "LF-ET User Manual", "master", "", new ArrayList<>(), new HashMap<>());
        model.processingModel.antoraYml.asciidoc.put("experimental", true);
//        ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
//        try {
//            mapper.writeValue(new File(model.outputDirectory, "antora.yml"), model.processingModel.antoraYml);
//        } catch (IOException e) {
//            throw new JavaHelpToAntoraConverterException("Unknown error occured while writing antora.yml file",e);
//        }
    }

    public static void main(String[] args) {
        new JavaHelpToAntoraConverter().execute(new File("lfet-help-de-2.2.0.jar"), new File("src/main/resources/docs"));
    }
}