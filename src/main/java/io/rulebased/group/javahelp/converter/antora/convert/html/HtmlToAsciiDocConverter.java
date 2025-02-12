package io.rulebased.group.javahelp.converter.antora.convert.html;

import io.rulebased.group.javahelp.converter.antora.convert.anchor.IAnchorConverter;
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
    final IAnchorConverter anchorConverter;


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
    public void doTableHeaderEqual(TableHeaderEqual arg0, Model model) {
        switch (arg0) {
            case $001: {
                model.tableHeader = false;
                break;
            }
            case $002: {
                model.tableHeader = true;
                break;
            }
        }
    }

    @Override
    public void doTableCountColumnsEqual(TableCountColumnsEqual arg0, Model model) {
        switch (arg0) {
            case $001: {
                model.tableColumnCount = 0;
                break;
            }
            case $002: {
                model.tableColumnCount++;
                break;
            }
        }
    }

    @Override
    public void doExtractImageSrc(Model model) {
        String imageSrc = model.currentChildElement.attr("src");
        model.addToAsciiDocContent("image::" + imageSrc.substring(imageSrc.indexOf("/") + 1) + "[]");
    }

    @Override
    public void doSelectTextbodyElement(Model model) {
        model.element = model.document.select("div#nstext").first();
    }

    @Override
    public void doExtractAnchor(Model model) {
        String anchor = anchorConverter.convert((Element) model.currentChildElement);
        if (anchor != null && !anchor.isEmpty()) {
            model.addToAsciiDocContent(anchor);
        }
    }

    @Override
    public void doAddToAdocContent(AddToAdocContent arg0, Model model) {
        switch (arg0) {
            case $001: {
                model.addToAsciiDocContent("", "[cols=???]", "|===", "");
                break;
            }
            case $002: {
                model.addToAsciiDocContent("");
                break;
            }
            default: {
            }
        }
    }

    @Override
    public void doProcessElement(Model model) {

        Model processElementModel = new Model((Element) model.currentChildElement, model.currentHeaderLevel);
        processElementModel.tableHeader = model.tableHeader;

        rulesEngine.execute(this, processElementModel);

        model.addToAsciiDocContent("");
        model.asciidocContent.addAll(processElementModel.asciidocContent);
        model.addToAsciiDocContent("");

        model.tableHeader = processElementModel.tableHeader;
        model.tableColumnCount = processElementModel.tableColumnCount;
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
                model.addToAsciiDocContent("=".repeat(model.currentHeaderLevel) + " " + value);
                break;
            }
            case $004: {
                if (model.currentChildElement instanceof TextNode) {
                    model.addToAsciiDocContent(((TextNode) model.currentChildElement).text());
                } else {
                    model.addToAsciiDocContent(((Element) model.currentChildElement).text());
                }
                break;
            }
            case $005: {
                for (int i = model.asciidocContent.size() - 1; i > 0; i--) {
                    if (model.asciidocContent.get(i).startsWith("[cols=???]")) {
                        model.asciidocContent.remove(i);
                        model.asciidocContent.add(i, String.format("[cols=%s%s]" //
                                , model.tableColumnCount //
                                , model.tableHeader ? ",options=\"header\"" : "" //
                            )
                        );
                    }
                }
                model.addToAsciiDocContent("", "|===", "");
                break;
            }
            case $006: {
                model.addToAsciiDocContent("| " + ((Element) model.currentChildElement).text());
                break;
            }
            default: {
                model.addToAsciiDocContent(arg0.getSymbol() + ((Element) model.currentChildElement).text() + arg0.getSymbol());
            }
        }
    }

    @Override
    public void doIncrementCurrentHeaderLevel(Model model) {
        model.currentHeaderLevel++;
    }

    @Override
    public void doLinebreak(Model model) {
        model.addToAsciiDocContent(System.lineSeparator());
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
        private final List<String> asciidocContent = new ArrayList<>(100);
        boolean tableHeader;
        int tableColumnCount;

        Model(Document document) {
            this.document = document;
        }

        Model(Element element, int currentHeaderLevel) {
            this.element = element;
            this.currentHeaderLevel = currentHeaderLevel;
        }

        public void addToAsciiDocContent(String... lines) {
            if (lines != null) {
                for (String line : lines) {
                    if (line != null && !line.isEmpty()) {
                        asciidocContent.add(
                            line
                                .replaceAll("[$][{]", "{dollarbracket}")
                        );
                    } else if (!asciidocContent.isEmpty() && !asciidocContent.get(asciidocContent.size() - 1).isEmpty()) {
                        // avoid multiple empty lines
                        asciidocContent.add("");
                    }
                }
            }
        }

    }

}
