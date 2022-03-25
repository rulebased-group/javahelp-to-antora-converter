package io.rulebased.group.javahelp.antora.converter;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

class HtmlToAsciiDocConverter implements JHTAC_HtmlToAsciiDocConverterDT<HtmlToAsciiDocConverter.Model> {

    static final JHTAC_HtmlToAsciiDocConverterRulesEngine rulesEngine = new JHTAC_HtmlToAsciiDocConverterRulesEngine();

    void execute(File modulsDirectory, ZipFile zipFile, ZipEntry zipEntry) {
        rulesEngine.execute(this, new Model(modulsDirectory, zipFile, zipEntry));
    }

    @Override
    public boolean isExistHeaderTitle(ExistHeaderTitle arg0, Model model) {
        return !model.document.select(arg0.getTitle()).isEmpty();
    }

    @Override
    public boolean isSaveOriginalFile(Model model) {
        return true;
    }

    @Override
    public void doError(java.lang.Error arg0, Model model) {
        throw new JavaHelpToAntoraConverterException(String.format(arg0.getTitle(), model.zipEntry.getName()));
    }

    @Override
    public void doCreatePagesDirectory(Model model) {
        //noinspection ResultOfMethodCallIgnored
        model.getPagesDirectory().mkdirs();
    }

    @Override
    public void doSaveFileInPagesDirectory(SaveFileInPagesDirectory arg0, Model model) {
        switch (arg0) {
            case $001: {
                try {
                    Files.write(model.getAsciiDocPageFile(), model.asciidocContent, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                } catch (IOException e) {
                    throw new RuntimeException("Unknown error", e);
                }
                break;
            }
            case $002: {
                try (InputStream inputStream = model.zipFile.getInputStream(model.zipEntry)) {
                    FileWriter fileWriter = new FileWriter(new File(model.getPagesDirectory(), model.zipEntry.getName()));
                    IOUtils.copy(inputStream, fileWriter, StandardCharsets.UTF_8);
                    fileWriter.flush();
                } catch (IOException cause) {
                    throw new RuntimeException("Unknown error", cause);
                }
                break;
            }
        }

    }

    @Override
    public void doExtractHeader(ExtractHeader arg0, Model model) {
        model.asciidocContent.add("= " + model.document.select(arg0.getTitle()).first().text());
        model.asciidocContent.add(System.lineSeparator());
    }

    @Override
    public void doTrace(String dtName, String version, int rules, int rule, Model model) {
        System.out.printf("%s[%s] - %s/%s%n", dtName, version, rule, rules);
    }

    static class Model {
        final Document document;
        final File modulDirectory;
        final ZipFile zipFile;
        final ZipEntry zipEntry;
        final List<String> asciidocContent;

        Model(File modulDirectory, ZipFile zipFile, ZipEntry zipEntry) {
            this.modulDirectory = modulDirectory;
            this.zipFile = zipFile;
            this.zipEntry = zipEntry;
            this.asciidocContent = new ArrayList<>();
            try {
                this.document = Jsoup.parse(zipFile.getInputStream(zipEntry), "UTF-8", "");
            } catch (IOException e) {
                throw new RuntimeException("Unknown error occured while reading html file", e);
            }
        }


        File getPagesDirectory() {
            return new File(modulDirectory, "pages");
        }

        Path getAsciiDocPageFile() {
            return Path.of(getPagesDirectory().getPath(), zipEntry.getName() + ".adoc");
        }
    }

}
