package io.rulebased.group.javahelp.converter.facade;

import io.rulebased.group.javahelp.converter.antora.exception.JavaHelpToAntoraConverterException;
import io.rulebased.group.javahelp.converter.utils.Utils;
import org.apache.commons.io.IOUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class JarFile implements InputFacade {

    final ZipFile source;
    final Charset encoding;

    final Map<String, String> mapFileNameToModule = new TreeMap<>();

    public JarFile(File inputFile, Charset encoding) throws JavaHelpToAntoraConverterException {
        try {
            this.source = new ZipFile(inputFile, encoding);
            this.encoding = encoding;
        } catch (IOException e) {
            throw new JavaHelpToAntoraConverterException("Failed to open zip file.", e);
        }
    }

    @Override
    public Document getTableOfContentFile(String fileName) throws JavaHelpToAntoraConverterException {
        ZipEntry entry = this.source.getEntry(fileName);
        try {
            String content = IOUtils.toString(this.source.getInputStream(entry), this.encoding);
            return new SAXBuilder().build(new StringReader(content));
        } catch (IOException | JDOMException e) {
            throw new JavaHelpToAntoraConverterException("Failed to read entry from zip file", e);
        }
    }

    @Override
    public String getContentOfFile(Path path2File) throws InputFacadeRuntimeException {
        return getContentOfFile(path2File.toString());
    }

    @Override
    public String getContentOfFile(String path2File) throws InputFacadeRuntimeException {
        try {
            return IOUtils.toString(this.source.getInputStream(this.source.getEntry(normalizeZipPath(path2File))), this.encoding);
        } catch (IOException e) {
            throw new JavaHelpToAntoraConverterException("Failed to read content from zip file", e);
        }
    }

    @Override
    public InputStream getInputstream(String path2File) throws InputFacadeRuntimeException {
        try {
            return this.source.getInputStream(this.source.getEntry(normalizeZipPath(path2File)));
        } catch (IOException e) {
            throw new JavaHelpToAntoraConverterException("Failed to read content from zip file", e);
        }
    }

    @Override
    public boolean isExistFile(Path filePath) {
        String fileName = normalizeZipPath(filePath.toString());
        boolean result = this.source.getEntry(fileName) != null;
        if (!result) {
            System.out.println("file not found in JAR: " + fileName);
        }
        return result;
    }

    private String normalizeZipPath(String path) {
        return path.replace('\\', '/');
    }

    @Override
    public void addFileMappings(String baseDir, Element e) {

        String target = e.getAttributeValue("target");

        mapFileNameToModule.put(target, baseDir);
        mapFileNameToModule.put(Utils.getAdocfileName(target), baseDir);

        for (Element child : e.getChildren("tocitem")) {
            addFileMappings(baseDir, child);
        }
    }

    @Override
    public String getAntoraModuleName(String antoraFileName) {
        final String result = mapFileNameToModule.get(antoraFileName);
        return Utils.isNotEmpty(result) ? result : "";
    }
}
