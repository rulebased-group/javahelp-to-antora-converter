package io.rulebased.group.javahelp.converter.facade;

import org.jdom2.Document;

import java.io.InputStream;
import java.nio.file.Path;

public interface InputFacade {

    Document getTableOfContentFile(String fileName) throws InputFacadeRuntimeException;

    String getContentOfFile(Path path2File) throws InputFacadeRuntimeException;

    String getContentOfFile(String path2File) throws InputFacadeRuntimeException;

    InputStream getInputstream(String path2File) throws InputFacadeRuntimeException;

    boolean isExistFile(Path fileName);

}
