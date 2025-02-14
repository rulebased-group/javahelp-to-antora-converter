package io.rulebased.group.javahelp.converter.antora.convert.html;

import io.rulebased.group.javahelp.converter.antora.convert.anchor.IAnchorConverter;
import io.rulebased.group.javahelp.converter.antora.logging.ILfetLogging;
import io.rulebased.group.javahelp.converter.config.ConverterConfig;
import io.rulebased.group.javahelp.converter.facade.InputFacade;
import io.rulebased.group.javahelp.converter.utils.LogUtil;
import io.rulebased.group.javahelp.converter.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.*;

@RequiredArgsConstructor
class HtmlToAsciiDocConverter implements HtmlToAsciiDocConverterIFace<HtmlToAsciiDocConverter.Model>, IHtmlToAsciiDocConverter {

    private static final Logger LOGGER = LogManager.getLogger(HtmlToAsciiDocConverter.class);
    private static final boolean logD = LOGGER.isDebugEnabled() && LogUtil.isLogLevelDebug();

    static final HtmlToAsciiDocConverterRulesEngine rulesEngine = new HtmlToAsciiDocConverterRulesEngine();
    final ILfetLogging lfetLogging;
    final IAnchorConverter anchorConverter;


    @Override
    public List<String> execute(ConverterConfig config, InputFacade inputFacade, String targetFileName) {
        if (logD) LogUtil.mEntry(LOGGER, "execute(...)");
        if (logD) LogUtil.mStmt(LOGGER, "inputFacade=" + inputFacade);

        String contentOfFile = inputFacade.getContentOfFile(targetFileName);
        Document document = Jsoup.parse(contentOfFile, "");
        Model model = new Model(document);
        model.inputFacade = inputFacade;

        if (logD) LogUtil.mStmt(LOGGER, "model=" + model);

        rulesEngine.execute(this, model);

        if (logD) LogUtil.mExit(LOGGER, "execute(...)");
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
        return (model.currentChildElement = model.childElementsIt.hasNext() ? model.childElementsIt.next() : null) != null;
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
        model.addToAsciiDocContent("image:" + imageSrc.substring(imageSrc.indexOf("/") + 1) + "[]");
    }

    @Override
    public void doSelectTextbodyElement(Model model) {
        model.element = model.document.select("div#nstext").first();
    }

    @Override
    public void doExtractAnchor(Model model) {
        if (logD) LogUtil.mEntry(LOGGER, "doExtractAnchor(...)");
        if (logD) LogUtil.mStmt(LOGGER, "model=" + model);
        if (logD) LogUtil.mStmt(LOGGER, "model.inputFacade=" + model.inputFacade);

        String anchor = anchorConverter.convert((Element) model.currentChildElement, model.inputFacade);
        if (anchor != null && !anchor.isEmpty()) {
            if (model.isLastElementAddedListItemTag()) {
                model.removeLastAsciiDocContentLine();
                model.addToAsciiDocContent("* " + anchor);
            } else {
                model.addToAsciiDocContent(anchor);
            }
        }

        if (logD) LogUtil.mExit(LOGGER, "doExtractAnchor(...)");
    }

    @Override
    public void doAddToAdocContent(AddToAdocContent arg0, Model model) {
        switch (arg0) {
            case $EQUALEQUALEQUAL: {
                // Here: begin of new table
                model.tableBorder = Integer.parseInt(model.currentChildElement.attr("border"));
                model.tableEntries1stColumn.clear();
                model.addToAsciiDocContent("", "[cols=???]", "|===", "");
                break;
            }
            case $TR: {
                model.addToAsciiDocContent("");
                break;
            }
            case $TD: {
                String colValue = Utils.convertTextForAdoc(((Element) model.currentChildElement).text().trim());
                if (model.tableColumnCount == 1 && !colValue.isEmpty()) {
                    model.tableEntries1stColumn.add(colValue);
                }
                model.addToAsciiDocContent("|");
                break;
            }
            default: {
            }
        }
    }

    @Override
    public void doProcessChildElements(Model model) {
        // if (logD) LogUtil.mEntry(LOGGER, "doProcessChildElements(" + model.currentChildElement.nodeName() + ")");

        Model processElementModel = new Model((Element) model.currentChildElement, model.currentHeaderLevel);
        processElementModel.inputFacade = model.inputFacade;
        processElementModel.tableHeader = model.tableHeader;
        processElementModel.tableColumnCount = model.tableColumnCount;
        processElementModel.tableEntries1stColumn.addAll(model.tableEntries1stColumn);

        rulesEngine.execute(this, processElementModel);

        model.addToAsciiDocContent("");
        model.asciidocContent.addAll(processElementModel.asciidocContent);
        model.addToAsciiDocContent("");

        model.tableHeader = processElementModel.tableHeader;
        model.tableColumnCount = processElementModel.tableColumnCount;
        model.tableEntries1stColumn.addAll(processElementModel.tableEntries1stColumn);

        // if (logD) LogUtil.mExit(LOGGER, "doProcessChildElements(" + model.currentChildElement.nodeName() + ")");
    }

    @Override
    public void doExtractText(ExtractText arg0, Model model) {
        switch (arg0) {
            case $EQUAL: {
                String value;
                if (model.currentChildElement instanceof TextNode) {
                    value = ((TextNode) model.currentChildElement).text();
                } else {
                    value = ((Element) model.currentChildElement).text();
                }
                model.addToAsciiDocContent("=".repeat(model.currentHeaderLevel) + " " + Utils.convertTextForAdoc(value));
                break;
            }
            case $NONE: {
                if (model.currentChildElement instanceof TextNode) {
                    model.addToAsciiDocContent(Utils.convertTextForAdoc(((TextNode) model.currentChildElement).text()));
                } else {
                    model.addToAsciiDocContent(Utils.convertTextForAdoc(((Element) model.currentChildElement).text()));
                }
                break;
            }
            case $EQUALEQUALEQUAL: {
                // Here: End of table
                if (model.isTableAList()) {
                    // transform table to list
                    String entry = (String) model.tableEntries1stColumn.toArray()[0];
                    for (int i = model.asciidocContent.size() - 1; i > 1; i--) {
                        String line = model.asciidocContent.get(i).trim();
                        if (line.startsWith("[cols=???]")) {
                            model.asciidocContent.remove(i);
                            model.removeEmptyLinesFromToAsciiDocContentUpFrom(i);
                            break;
                        } else if (line.equals("|===")) {
                            model.asciidocContent.remove(i);
                            model.removeEmptyLinesFromToAsciiDocContentUpFrom(i);
                        } else if (line.equals("|")) {
                            model.asciidocContent.remove(i);
                            model.removeEmptyLinesFromToAsciiDocContentUpFrom(i);
                            line = model.asciidocContent.get(i).trim();
                            if (entry.equals(line)) {
                                model.asciidocContent.remove(i);
                                model.removeEmptyLinesFromToAsciiDocContentUpFrom(i);
                            } else {
                                model.asciidocContent.remove(i);
                                model.asciidocContent.add(i, "* " + line);
                            }
                        }
                    }
                } else {
                    // post process adoc table lines
                    for (int i = model.asciidocContent.size() - 1; i > 0; i--) {
                        String line = model.asciidocContent.get(i).trim();
                        if (line.startsWith("[cols=???]")) {
                            model.asciidocContent.remove(i);
                            model.asciidocContent.add(i, String.format("[cols=%s%s%s]" //
                                    , model.tableColumnCount > 1 //
                                        ? "\"~" + ",~".repeat(model.tableColumnCount - 1) + "\"" //
                                        : model.tableColumnCount //
                                    , model.tableHeader ? ",options=\"header\"" : "" //
                                    , model.tableBorder < 1 ? ",frame=none,grid=none" : "" //
                                )
                            );
                            break;
                        } else if (line.equals("|")) {
                            if (i < model.asciidocContent.size() - 2) {
                                model.asciidocContent.remove(i);
                                model.removeEmptyLinesFromToAsciiDocContentUpFrom(i);
                                line = model.asciidocContent.get(i).trim();
                                model.asciidocContent.remove(i);
                                model.asciidocContent.add(i, "| " + line);
                            }
                        }
                    }
                    model.addToAsciiDocContent("", "|===", "");
                }
                break;
            }
            default: {
                String text = Utils.convertTextForAdoc(((Element) model.currentChildElement).text().trim());
                if (!text.isEmpty()) {
                    model.addToAsciiDocContent(arg0.getSymbol() + text + arg0.getSymbol());
                }
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

    // @ToString
    static class Model {

        Document document;
        int currentHeaderLevel = 1;
        Element element;
        Node currentChildElement;
        Iterator<Node> childElementsIt;
        private final List<String> asciidocContent = new ArrayList<>(100);
        InputFacade inputFacade;

        private boolean tableHeader;
        private int tableColumnCount;
        private int tableBorder;
        private final Set<String> tableEntries1stColumn = new HashSet<>();

        Model(Document document) {
            this.document = document;
        }

        Model(Element element, int currentHeaderLevel) {
            this.element = element;
            this.currentHeaderLevel = currentHeaderLevel;
        }

        /**
         * The german user manual often used tables as lists with no borders, no grid and a single
         * list bullet point character (e.g. '-') in the first column
         */
        boolean isTableAList() {
            boolean result = tableEntries1stColumn.size() == 1;
            if (result) {
                String entry = (String) tableEntries1stColumn.toArray()[0];
                result = entry.length() == 1
                /* && "-".indexOf(entry.charAt(0)) != -1 */ // we rate any 1 char entry as list bullet point
                ;
            }
            return result;
        }

        public void addToAsciiDocContent(String... lines) {
            if (lines != null) {
                for (String line : lines) {
                    if (line != null && !line.isEmpty()) {
                        asciidocContent.add(line);
                    } else if (!asciidocContent.isEmpty() && !asciidocContent.get(asciidocContent.size() - 1).isEmpty()) {
                        // just avoid multiple empty lines
                        asciidocContent.add("");
                    }
                }
            }
        }

        private String getLastAsciiDocContentLine() {
            return !asciidocContent.isEmpty() ? asciidocContent.get(asciidocContent.size() - 1) : null;
        }

        private String removeLastAsciiDocContentLine() {
            final String result;
            if (!asciidocContent.isEmpty()) {
                result = asciidocContent.remove(asciidocContent.size() - 1);
            } else {
                result = null;
            }
            return result;
        }

        private boolean isLastElementAddedListItemTag() {
            return Arrays.asList("* ", "- ").contains(getLastAsciiDocContentLine());
        }


        public void removeEmptyLinesFromToAsciiDocContentUpFrom(int index) {
            while (index < asciidocContent.size() && asciidocContent.get(index).trim().isEmpty()) {
                asciidocContent.remove(index);
            }
        }

    }

}
