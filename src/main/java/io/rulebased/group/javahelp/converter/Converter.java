package io.rulebased.group.javahelp.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.rulebased.group.javahelp.converter.antora.IJavaHelpToAntoraConverter;
import io.rulebased.group.javahelp.converter.antora.exception.JavaHelpToAntoraConverterException;
import io.rulebased.group.javahelp.converter.antora.logging.ILfetLogging;
import io.rulebased.group.javahelp.converter.config.ConverterConfig;
import io.rulebased.group.javahelp.converter.facade.InputFacade;
import io.rulebased.group.javahelp.converter.facade.JarFile;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.File;
import java.io.IOException;

public class Converter implements ConverterDT<Converter.ConverterModel>, ILfetLogging {

    static final ConverterRulesEngine rulesEngine = new ConverterRulesEngine();

    public static void main(String[] args) {
        new Converter().execute(args);
    }

    void execute(String[] args) {
        ConverterModel model = new ConverterModel(args);
        rulesEngine.execute(this, model);
    }

    @Override
    public boolean isArgsSizeEqual_Equal0(ConverterModel model) {
        return model.args.length == 0;
    }

    @Override
    public boolean isArgsSizeEqual_Equal1(ConverterModel model) {
        return model.args.length == 1;
    }

    @Override
    public boolean isExistsConfigFile(ConverterModel model) {
        return model.configFile.exists();
    }

    @Override
    public boolean isInputFileTypeEqual_F(ConverterModel model) {
        return model.config.getInput().getJarFile().isFile();
    }

    @Override
    public void doSetConfigFileName_Default(ConverterModel model) {
        model.configFile = new File("./converter-config.yaml");
    }

    @Override
    public void doSetConfigFileName_Args(ConverterModel model) {
        model.configFile = new File(model.args[0]);
    }

    @Override
    public void doError(Error arg0, ConverterModel model) {
        switch (arg0) {
            case $001:
                throw new JavaHelpToAntoraConverterException(arg0.getTitle());
            case $002:
                throw new JavaHelpToAntoraConverterException(String.format(arg0.getTitle(), model.configFile.getPath()));

        }
    }


    @Override
    public void doReadConfigFileAsYaml(ConverterModel model) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            model.config = mapper.readValue(model.configFile, ConverterConfig.class);
        } catch (IOException e) {
            throw new JavaHelpToAntoraConverterException("Unknown error occured while reading converter config.yaml file", e);
        }

    }

    @Override
    public void doSetInputFacade(SetInputFacade arg0, ConverterModel model) {
        model.inputFacade = new JarFile(model.config.getInput().getJarFile(), model.config.getInput().getEncoding());
    }

    @Override
    public void doStartMigration(ConverterModel model) {
        IJavaHelpToAntoraConverter.create(this).execute(model.config, model.inputFacade);
    }

    @Override
    public void doTrace(String dtName, String version, int rules, int rule, ConverterModel model) {
        trace(dtName, version, rules, rule, model);
    }

    @Override
    public <T> void trace(String lfet, String version, int currentRule, int maxRules, T model) {
        System.out.println("Trace: " + lfet + " " + version + " " + currentRule + " " + maxRules + " " + model);
    }


    @RequiredArgsConstructor
    @ToString
    static class ConverterModel {
        final String[] args;
        File configFile;
        ConverterConfig config;
        InputFacade inputFacade;
    }
}
