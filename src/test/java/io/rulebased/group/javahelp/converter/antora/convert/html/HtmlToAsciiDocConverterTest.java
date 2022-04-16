package io.rulebased.group.javahelp.converter.antora.convert.html;

import io.rulebased.group.javahelp.converter.antora.logging.LfetLogger;
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

class HtmlToAsciiDocConverterTest {


    @Test
    void execute() {

        ConverterConfig config = new ConverterConfig();
        config.setOutput(new OutputConfig());
        config.getOutput().setSaveOriginalHtmlFile(true);
        config.getOutput().setEncoding(StandardCharsets.UTF_8);


        List<String> asciidocContent = new HtmlToAsciiDocConverter(new LfetLogger()).execute(config, new TestFileFacade(), "aktionsanzeigeteil1.htm");
        Assertions.assertThat(asciidocContent).isNotEmpty();
        Assertions.assertThat(asciidocContent.get(0)).isEqualTo("= Aktionsanzeigeteil");

    }


    static class TestFileFacade implements InputFacade {

        @Override
        public Document getTableOfContentFile(String fileName) throws InputFacadeRuntimeException {
            return null;
        }

        @Override
        public String getContentOfFile(Path path2File) throws InputFacadeRuntimeException {
            try {
                return Files.readString(Path.of("src/test/resources/convert/html", path2File.toString()), StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new InputFacadeRuntimeException("Error while reading file", e);
            }
        }

        @Override
        public String getContentOfFile(String path2File) throws InputFacadeRuntimeException {
            try {
                return Files.readString(Path.of("src/test/resources/convert/html", path2File), StandardCharsets.UTF_8);
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