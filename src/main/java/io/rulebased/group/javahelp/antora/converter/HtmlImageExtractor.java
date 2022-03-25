package io.rulebased.group.javahelp.antora.converter;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

class HtmlImageExtractor implements JHTAC_ExtractImagesDT<HtmlImageExtractor.HtmlImageExtractorModel> {

    static final JHTAC_ExtractImagesRulesEngine rulesEngine = new JHTAC_ExtractImagesRulesEngine();

    void execute(File moduleDirectory, ZipFile zipFile, String currentFileName) {
        rulesEngine.execute(this, new HtmlImageExtractorModel(moduleDirectory, zipFile, currentFileName));
    }

    @Override
    public boolean isExistImageDirectory(HtmlImageExtractorModel model) {
        return model.getImageDirectory().exists() && model.getImageDirectory().isDirectory();
    }

    @Override
    public boolean isNextImageElementExists(HtmlImageExtractorModel model) {
        if (model.images.hasNext()) {
            model.currentImage = model.images.next();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isIsImageToIgnore(IsImageToIgnore arg0, HtmlImageExtractorModel model) {
        return model.currentImage.attr("src").equalsIgnoreCase(arg0.getTitle());
    }

    @Override
    public boolean isExistsEntryInZipFile(HtmlImageExtractorModel model) {
        return model.zipFile.getEntry(model.currentImage.attr("src")) != null;
    }

    @Override
    public void doError(Error arg0, HtmlImageExtractorModel model) {
        throw new JavaHelpToAntoraConverterException(arg0.getTitle());
    }

    @Override
    public void doCreateImageDirectory(HtmlImageExtractorModel model) {
        //noinspection ResultOfMethodCallIgnored
        model.getImageDirectory().mkdirs();
    }

    @Override
    public void doSaveImageToImageDirectory(HtmlImageExtractorModel model) {
        String originalImageSource = model.currentImage.attr("src");
        ZipEntry imageZipEntry = model.zipFile.getEntry(originalImageSource);
        String targetImageName = originalImageSource.replaceAll("Images/", "");

        try {
            IOUtils.copy(model.zipFile.getInputStream(imageZipEntry), new FileOutputStream(new File(model.getImageDirectory(), targetImageName)));
        } catch (IOException cause) {
            throw new JavaHelpToAntoraConverterException("Unknown error on extracting image from archive.", cause);
        }
    }

    @Override
    public void doSelectAllImageElements(HtmlImageExtractorModel model) {
        try {
            org.jsoup.nodes.Document document = Jsoup.parse(model.zipFile.getInputStream(model.zipFile.getEntry(model.currentFileName)), "UTF-8", "");
            model.images = document.select("img[src]").stream().iterator();
        } catch (IOException cause) {
            throw new JavaHelpToAntoraConverterException("Unknown error", cause);
        }
    }

    @Override
    public void doTrace(String dtName, String version, int rules, int rule, HtmlImageExtractorModel model) {
        System.out.printf("%s[%s] - %s/%s%n", dtName, version, rule, rules);
    }

    static class HtmlImageExtractorModel {
        Element currentImage;
        File moduleDirectory;
        ZipFile zipFile;
        private String currentFileName;
        Iterator<Element> images;

        HtmlImageExtractorModel(File moduleDirectory, ZipFile zipFile, String currentFileName) {
            this.moduleDirectory = moduleDirectory;
            this.zipFile = zipFile;
            this.currentFileName = currentFileName;
        }

        File getImageDirectory() {
            return new File(moduleDirectory, "images");
        }

    }
}
