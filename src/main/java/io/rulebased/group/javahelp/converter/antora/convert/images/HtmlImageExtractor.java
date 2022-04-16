package io.rulebased.group.javahelp.converter.antora.convert.images;

import io.rulebased.group.javahelp.converter.antora.exception.JavaHelpToAntoraConverterException;
import io.rulebased.group.javahelp.converter.antora.logging.ILfetLogging;
import io.rulebased.group.javahelp.converter.config.ConverterConfig;
import io.rulebased.group.javahelp.converter.facade.InputFacade;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;

@RequiredArgsConstructor
class HtmlImageExtractor implements JHTAC_ExtractImagesDT<HtmlImageExtractor.HtmlImageExtractorModel>, IImageConverter {

    static final JHTAC_ExtractImagesRulesEngine rulesEngine = new JHTAC_ExtractImagesRulesEngine();
    final ILfetLogging lfetLogging;


    @Override
    public void execute(ConverterConfig config, File moduleDirectory, InputFacade inputFacade, String currentFileName) {
        rulesEngine.execute(this, new HtmlImageExtractorModel(config, moduleDirectory, inputFacade, currentFileName));
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
        return model.inputFacade.isExistFile(Path.of(model.currentImage.attr("src")));
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
        String targetImageName = originalImageSource.replaceAll("Images/", "");

        try {
            IOUtils.copy(model.inputFacade.getInputstream(originalImageSource), new FileOutputStream(new File(model.getImageDirectory(), targetImageName)));
        } catch (IOException cause) {
            throw new JavaHelpToAntoraConverterException("Unknown error on extracting image from archive.", cause);
        }
    }

    @Override
    public void doSelectAllImageElements(HtmlImageExtractorModel model) {
        org.jsoup.nodes.Document document = Jsoup.parse(model.inputFacade.getContentOfFile(model.currentFileName), "");
        model.images = document.select("img[src]").stream().iterator();
    }

    @Override
    public void doTrace(String dtName, String version, int rules, int rule, HtmlImageExtractorModel model) {
        lfetLogging.trace(dtName, version, rules, rule, model);
    }

    @ToString
    static class HtmlImageExtractorModel {
        Element currentImage;
        ConverterConfig config;
        File moduleDirectory;
        InputFacade inputFacade;
        String currentFileName;
        Iterator<Element> images;

        HtmlImageExtractorModel(ConverterConfig config, File moduleDirectory, InputFacade inputFacade, String currentFileName) {
            this.config = config;
            this.moduleDirectory = moduleDirectory;
            this.inputFacade = inputFacade;
            this.currentFileName = currentFileName;
        }

        File getImageDirectory() {
            return new File(moduleDirectory, "images");
        }

    }
}
