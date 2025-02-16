package io.rulebased.group.javahelp.converter.antora.convert.html;

import io.rulebased.group.javahelp.converter.antora.convert.anchor.IAnchorConverter;
import io.rulebased.group.javahelp.converter.antora.logging.ILfetLogging;
import io.rulebased.group.javahelp.converter.config.ConverterConfig;
import io.rulebased.group.javahelp.converter.config.OutputConfig;
import io.rulebased.group.javahelp.converter.facade.InputFacade;
import io.rulebased.group.javahelp.converter.facade.InputFacadeRuntimeException;
import io.rulebased.group.javahelp.converter.utils.LogUtil;
import io.rulebased.group.javahelp.converter.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.jdom2.Document;
import org.jdom2.Element;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class HtmlToAsciiDocConverterTest implements ILfetLogging {

    private static final Logger LOGGER = LogManager.getLogger(HtmlToAsciiDocConverterTest.class);
    private static final boolean logD = LOGGER.isDebugEnabled() && LogUtil.isLogLevelDebug();

    @ParameterizedTest
    @ValueSource(strings = {"" //
//        , "online_hilfe_in_lf_et.htm" //
//        , "projectini_vordefinierte_standard_schluessel.htm" //
//        , "backuphistoryversion.htm" //
//        , "mglichkeit_1_ber_das_men.htm" //
//        , "version_2_4_0.htm" //
//        , "test.htm" //
//        , "ti_check_num_bsp.htm" //
//        , "tastenkombinationen.htm" //
//        , "funktionstasten_f1_f12.htm" //
//        , "navigationstasten1.htm" //
//        , "dateneingabe-02.htm" //
//        , "sonstigetasten1.htm" //
//        , "tastenkombinationen_fr_texte.htm" //
//        , "prog_gen_navigationindensourcecodes.htm" //
//        , "natural_generierungsparameter.htm" //
//        , "v211.htm" //
        , "definition1.htm" //
    })
    void htmPages(String htmFileName) {
        if (Utils.isNotEmpty(htmFileName)) {
            ConverterConfig config = new ConverterConfig();
            config.setOutput(new OutputConfig());
            config.getOutput().setSaveOriginalHtmlFile(true);
            config.getOutput().setEncoding(StandardCharsets.UTF_8);

            TestFileFacade testFileFacade = new TestFileFacade();

            List<String> asciidocContent = new HtmlToAsciiDocConverter(this, IAnchorConverter.create(this)).execute(config, testFileFacade, htmFileName);
            System.out.println(asciidocContent);

            testFileFacade.writeAdocFile(htmFileName, asciidocContent);

            Assertions.assertThat(asciidocContent).isNotEmpty();
        }
    }


    @Override
    public <T> void trace(String lfet, String version, int currentRule, int maxRules, T model) {
        switch (lfet) {
            case "HtmlToAsciiDocConverter": {
                // System.out.println(lfet + " - " + currentRule + " / " + maxRules + " - " + ((HtmlToAsciiDocConverter.Model) model).currentChildElement);
                // if (logD) LogUtil.mStmtf(LOGGER, "%s rule %s of %s \n%s", lfet, currentRule, maxRules,((HtmlToAsciiDocConverter.Model) model).currentChildElement);
                break;
            }
            default: {
                // System.out.println(lfet + " - " + currentRule + " / " + maxRules);
                // if (logD) LogUtil.mStmtf(LOGGER, "%s rule %s of %s", lfet, currentRule, maxRules);
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

        public void writeAdocFile(String path2File, List<String> contentLines) throws InputFacadeRuntimeException {
            try {
                StringBuilder sb = new StringBuilder();
                for (String line : contentLines) {
                    if (sb.length() > 0) {
                        sb.append("\n");
                    }
                    sb.append(line);
                }

                File dir = new File("target/generated-resources/convert/html");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                Files.writeString(Path.of("target/generated-resources/convert/html",
                    path2File.replaceAll("(?i)\\.html?$", ".adoc")), sb.toString(), StandardCharsets.UTF_8);
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

        final Map<String, String> mapFileNameToModule = new TreeMap<>();

        @Override
        public void addFileMappings(String baseDir, Element e) {
            if (logD) LogUtil.mEntry(LOGGER, "addFileMappings(String baseDir, Element e)");

            String target = e.getAttributeValue("target");
            if (logD) LogUtil.mStmtf(LOGGER, "add: %s -> %s", target, baseDir);

            mapFileNameToModule.put(target, baseDir);
            for (Element child : e.getChildren("tocitem")) {
                addFileMappings(baseDir, child);
            }

            if (logD) LogUtil.mExit(LOGGER, "addFileMappings(String baseDir, Element e)");
        }

        @Override
        public String getAntoraModuleName(String antoraFileName) {
            return mapFileNameToModule.get(antoraFileName);
        }
    }
}
