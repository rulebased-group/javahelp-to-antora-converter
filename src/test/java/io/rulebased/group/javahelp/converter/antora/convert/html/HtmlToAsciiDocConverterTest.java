package io.rulebased.group.javahelp.converter.antora.convert.html;

import io.rulebased.group.javahelp.converter.antora.convert.anchor.IAnchorConverter;
import io.rulebased.group.javahelp.converter.antora.logging.ILfetLogging;
import io.rulebased.group.javahelp.converter.config.ConverterConfig;
import io.rulebased.group.javahelp.converter.config.OutputConfig;
import io.rulebased.group.javahelp.converter.facade.InputFacade;
import io.rulebased.group.javahelp.converter.facade.InputFacadeRuntimeException;
import org.assertj.core.api.Assertions;
import org.jdom2.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class HtmlToAsciiDocConverterTest implements ILfetLogging {


    @Test
    void aktionsanzeigeteil1() {

        ConverterConfig config = new ConverterConfig();
        config.setOutput(new OutputConfig());
        config.getOutput().setSaveOriginalHtmlFile(true);
        config.getOutput().setEncoding(StandardCharsets.UTF_8);


        List<String> asciidocContent = new HtmlToAsciiDocConverter(this, IAnchorConverter.create(this)).execute(config, new TestFileFacade(), "aktionsanzeigeteil1.htm");
        System.out.println(asciidocContent);
        Assertions.assertThat(asciidocContent).isNotEmpty();
        Assertions.assertThat(asciidocContent.get(0)).isEqualTo("= Aktionsanzeigeteil");


    }

    @Test
    void allgemeinegrundlagen1() {

        ConverterConfig config = new ConverterConfig();
        config.setOutput(new OutputConfig());
        config.getOutput().setSaveOriginalHtmlFile(true);
        config.getOutput().setEncoding(StandardCharsets.UTF_8);


        List<String> asciidocContent = new HtmlToAsciiDocConverter(this, IAnchorConverter.create(this)).execute(config, new TestFileFacade(), "allgemeinegrundlagen1.htm");
        System.out.println(asciidocContent);
        Assertions.assertThat(asciidocContent).isNotEmpty();
        Assertions.assertThat(asciidocContent.get(0)).isEqualTo("= Allgemeine Grundlagen");


    }

    @Override
    public <T> void trace(String lfet, String version, int currentRule, int maxRules, T model) {
        switch (lfet) {
            case "JHTAC_HtmlToAsciiDocConverter": {
                System.out.println(lfet + " - " + currentRule + " / " + maxRules + " - " + ((HtmlToAsciiDocConverter.Model) model).currentChildElement);
                break;
            }
            default: {
                System.out.println(lfet + " - " + currentRule + " / " + maxRules);
                break;
            }
        }
    }


    static class TestFileFacade implements InputFacade {

        @Override
        public Document getTableOfContentFile(String fileName) throws InputFacadeRuntimeException {
            return null;
        }

        @Override
        public String getContentOfFile(Path path2File) throws InputFacadeRuntimeException {
            try {
                return Files.readString(Path.of("src/test/resources/convert/html", path2File.toString()), StandardCharsets.ISO_8859_1);
            } catch (IOException e) {
                throw new InputFacadeRuntimeException("Error while reading file", e);
            }
        }

        @Override
        public String getContentOfFile(String path2File) throws InputFacadeRuntimeException {
            try {
                return Files.readString(Path.of("src/test/resources/convert/html", path2File), StandardCharsets.ISO_8859_1);
            } catch (IOException e) {
                throw new InputFacadeRuntimeException("Error while reading file", e);
            }
        }

        @Override
        public InputStream getInputstream(String path2File) throws InputFacadeRuntimeException {
            return null;
        }

        @Override
        public boolean isExistFile(Path fileName) {
            return false;
        }
    }
}