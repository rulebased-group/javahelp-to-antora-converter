package io.rulebased.group.javahelp.converter.antora.convert.html;

import io.rulebased.group.javahelp.converter.antora.logging.ILfetLogging;
import io.rulebased.group.javahelp.converter.config.ConverterConfig;
import io.rulebased.group.javahelp.converter.facade.InputFacade;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
class HtmlToAsciiDocConverter implements JHTAC_HtmlToAsciiDocConverterDT<HtmlToAsciiDocConverter.Model>, IHtmlConverter {

    static final JHTAC_HtmlToAsciiDocConverterRulesEngine rulesEngine = new JHTAC_HtmlToAsciiDocConverterRulesEngine();
    final ILfetLogging lfetLogging;


    @Override
    public List<String> execute(ConverterConfig config, InputFacade inputFacade, String targetFileName) {
        String contentOfFile = inputFacade.getContentOfFile(targetFileName);
        Document document = Jsoup.parse(contentOfFile, "");
        Model model = new Model(document);
        rulesEngine.execute(this, model);
        return model.asciidocContent;
    }


    @Override
    public boolean isContainsCurrentElementChildElements(Model model) {
        List<Node> nodes = model.element.childNodes();
        model.childElementsIt = nodes.iterator();
        return !nodes.isEmpty();
    }

    @Override
    public boolean isNextElementExists(Model model) {
        if (model.childElementsIt.hasNext()) {
            model.currentChildElement = model.childElementsIt.next();
        } else {
            model.currentChildElement = null;
        }
        return model.currentChildElement != null;
    }

    @Override
    public boolean isSkipElement(Model model) {
        return model.currentChildElement.attr("class").equals("lfhtml-title-01") || model.currentChildElement.attr("class").equals("lfhtml-title-02");
    }

    @Override
    public boolean isCurrentElementTypeIs(CurrentElementTypeIs arg0, Model model) {
        switch (arg0) {
            case $001: {
                return model.currentChildElement instanceof TextNode;
            }
            case $002: {
                return model.currentChildElement instanceof Element;
            }
            default: {
                return false;
            }
        }
    }


    @Override
    public boolean isCurrentElementTagnameIs(CurrentElementTagnameIs arg0, Model model) {
        return model.currentChildElement.nodeName().equals(arg0.getSymbol());
    }

    @Override
    public boolean isTrimmedTextIsEqualTo(TrimmedTextIsEqualTo arg0, Model model) {
        String text = "";
        if (model.currentChildElement instanceof TextNode) {
            text = ((TextNode) model.currentChildElement).text();
        } else {
            text = ((Element) model.currentChildElement).text();
        }
        switch (arg0) {
            case $001: {
                return text.equalsIgnoreCase("%nbsp;");
            }
            case $002: {
                return text.isEmpty();
            }
            case $003: {
                return text.isBlank();
            }
            case $004: {
                return text.equalsIgnoreCase("\n");
            }
            default: {
                return false;
            }
        }
    }

    @Override
    public boolean isStartElementIsDefined(Model model) {
        return model.element != null;
    }

    @Override
    public void doExtractImageSrc(Model model) {
        String imageSrc = model.currentChildElement.attr("src");
        model.asciidocContent.add("image::" + imageSrc.substring(imageSrc.indexOf("/") + 1) + "[]");
    }

    @Override
    public void doSelectTextbodyElement(Model model) {
        model.element = model.document.select("div#nstext").first();
    }

    @Override
    public void doExtractAnchor(Model model) {
        String anchor = model.currentChildElement.attr("href");
        // model.asciidocContent.add("xref::" + anchor + "]]");
    }

    @Override
    public void doProcessElement(Model model) {
        Model processElementModel = new Model((Element) model.currentChildElement, model.currentHeaderLevel);
        rulesEngine.execute(this, processElementModel);
        model.asciidocContent.addAll(processElementModel.asciidocContent);
    }

    @Override
    public void doExtractText(ExtractText arg0, Model model) {
        switch (arg0) {
            case $002: {
                String value;
                if (model.currentChildElement instanceof TextNode) {
                    value = ((TextNode) model.currentChildElement).text();
                } else {
                    value = ((Element) model.currentChildElement).text();
                }
                model.asciidocContent.add("=".repeat(model.currentHeaderLevel) + " " + value);
                break;
            }
            case $004: {
                if (model.currentChildElement instanceof TextNode) {
                    model.asciidocContent.add(((TextNode) model.currentChildElement).text());
                } else {
                    model.asciidocContent.add(((Element) model.currentChildElement).text());
                }
                break;
            }
            default: {
                model.asciidocContent.add(arg0.getSymbol() + ((Element) model.currentChildElement).text() + arg0.getSymbol());
            }
        }
    }

    @Override
    public void doIncrementCurrentHeaderLevel(Model model) {
        model.currentHeaderLevel++;
    }

    @Override
    public void doLinebreak(Model model) {
        model.asciidocContent.add(System.lineSeparator());
    }

    @Override
    public void doError(Error arg0, Model model) {

    }

    @Override
    public void doTrace(String dtName, String version, int rules, int rule, Model model) {
        lfetLogging.trace(dtName, version, rule, rules, model);
    }

    @ToString
    static class Model {

        Document document;
        int currentHeaderLevel = 1;
        Element element;
        Node currentChildElement;
        Iterator<Node> childElementsIt;
        List<String> asciidocContent = new ArrayList<>(100);

        Model(Document document) {
            this.document = document;
        }

        Model(Element element, int currentHeaderLevel) {
            this.element = element;
            this.currentHeaderLevel = currentHeaderLevel;
        }

    }

}
