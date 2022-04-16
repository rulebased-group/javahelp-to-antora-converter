package io.rulebased.group.javahelp.converter.facade;

import io.rulebased.group.javahelp.converter.antora.exception.JavaHelpToAntoraConverterException;
import org.apache.commons.io.IOUtils;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class JarFile implements InputFacade {

    final ZipFile source;
    final Charset encoding;

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
            return IOUtils.toString(this.source.getInputStream(this.source.getEntry(path2File)), this.encoding);
        } catch (IOException e) {
            throw new JavaHelpToAntoraConverterException("Failed to read content from zip file", e);
        }
    }

    @Override
    public InputStream getInputstream(String path2File) throws InputFacadeRuntimeException {
        try {
            return this.source.getInputStream(this.source.getEntry(path2File));
        } catch (IOException e) {
            throw new JavaHelpToAntoraConverterException("Failed to read content from zip file", e);
        }
    }

    @Override
    public boolean isExistFile(Path fileName) {
        return this.source.getEntry(fileName.toString()) != null;
    }
}
