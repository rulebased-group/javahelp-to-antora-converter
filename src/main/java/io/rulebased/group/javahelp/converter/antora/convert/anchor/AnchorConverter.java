package io.rulebased.group.javahelp.converter.antora.convert.anchor;

import io.rulebased.group.javahelp.converter.antora.logging.ILfetLogging;
import io.rulebased.group.javahelp.converter.facade.InputFacade;
import io.rulebased.group.javahelp.converter.utils.LogUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
class AnchorConverter implements ConvertAnchorIFace<AnchorModel>, IAnchorConverter {

    private static final Logger LOGGER = LogManager.getLogger(AnchorConverter.class);
    private static final boolean logD = LOGGER.isDebugEnabled() && LogUtil.isLogLevelDebug();

    static final ConvertAnchorRulesEngine rulesEngine = new ConvertAnchorRulesEngine();
    final ILfetLogging lfetLogging;

    private boolean isCreateListItem = false;

    @Override
    public boolean isCurrentElementIs(CurrentElementIs arg0, AnchorModel model) {
        return model.element.nodeName().equalsIgnoreCase(arg0.getSymbol());
    }

    @Override
    public boolean isContainsCurrentElementChildElements(AnchorModel model) {
        model.childsNodesIt = model.element.childNodes().iterator();
        return model.childsNodesIt.hasNext();
    }

    @Override
    public boolean isNextChildElementExists(AnchorModel model) {
        if (model.childsNodesIt.hasNext()) {
            model.currentNode = model.childsNodesIt.next();
        } else {
            model.currentNode = null;
        }
        return model.currentNode != null;
    }

    @Override
    public boolean isCurrentElementTypeIs(CurrentElementTypeIs arg0, AnchorModel model) {
        if (logD) LogUtil.mEntry(LOGGER, "isCurrentElementTypeIs(ConvertAnchorDTCurrentElementTypeIs arg0, AnchorModel model)");
        if (logD) LogUtil.mStmtf(LOGGER, "arg0=%s, currentNode=%s", arg0, model.currentNode);

        final boolean result;

        final List<String> selectedNodeNames = Arrays.asList("img");

        switch (arg0) {
            case $TN: {
                result = model.currentNode instanceof TextNode;
                break;
            }
            case $EL: {
                result = model.currentNode instanceof Element
                    && !selectedNodeNames.contains(((Element) model.currentNode).nodeName());
                break;
            }
            case $IMG: {
                result = model.currentNode instanceof Element
                    && model.currentNode.nodeName().equalsIgnoreCase("img");
                break;
            }
            default: {
                result = false;
            }
        }

        if (logD) LogUtil.mStmt(LOGGER, "result=" + result);
        if (logD) LogUtil.mExit(LOGGER, "isCurrentElementTypeIs(ConvertAnchorDTCurrentElementTypeIs arg0, AnchorModel model)");
        return result;
    }

    @Override
    public boolean isImageIs(ImageIs arg0, AnchorModel model) {
        if (logD) LogUtil.mEntry(LOGGER, "isImageIs(ConvertAnchorDTImageIs arg0, AnchorModel model)");
        if (logD) LogUtil.mStmtf(LOGGER, "arg0=%s, currentNode=%s", arg0, model.currentNode);

        final boolean result;

        String imgFileName = model.currentNode.attr("src").toLowerCase();
        final List<String> buttonFileNames = Arrays.asList("button.gif");

        switch (arg0) {
            case $BUTT: {
                result = buttonFileNames.contains(imgFileName);
                break;
            }
            default: {
                result = false;
            }
        }

        if (logD) LogUtil.mStmt(LOGGER, "result=" + result);
        if (logD) LogUtil.mExit(LOGGER, "isImageIs(ConvertAnchorDTImageIs arg0, AnchorModel model)");
        return result;
    }

    @Override
    public boolean isAnchorTextIsEmpty(AnchorModel model) {
        return model.anchorText.isEmpty();
    }

    @Override
    public boolean isIsCreateListItem(AnchorModel model) {
        return isCreateListItem;
    }

    @Override
    public void doExtractAnchorTarget(AnchorModel model) {
        model.anchorTarget = model.element.attr("href");
    }

    @Override
    public void doSetCreateListItem(AnchorModel model) {
        isCreateListItem = true;
    }

    @Override
    public void doExtractAnchorText(AnchorModel model) {
        if (model.currentNode instanceof TextNode) {
            model.anchorText = model.anchorText + ((TextNode) model.currentNode).text();
        } else if (model.currentNode instanceof Element) {
            String text = ((Element) model.currentNode).text();
            if (!text.trim().isEmpty()) {
                switch (model.currentNode.nodeName()) {
                    case "i":
                        text = "_" + text + "_";
                        break;
                    case "b":
                        text = "*" + text + "*";
                        break;
                }
                model.anchorText = model.anchorText + text;
            }
        }
    }

    @Override
    public void doCreateListItemPrefix(AnchorModel model) {
        model.asciidoc.add("* ");
    }

    @Override
    public void doCreateXrefLink(AnchorModel model) {

        String anchorTarget = model.anchorTarget;

        if (anchorTarget.matches(".*\\.htm$")) {
            // .htm is the generated file extension by DocToHelp which is the authoring system used for the german LF-ET user manual
            // so it should be pretty save to add ".adoc"
            // TODO maybe we should use and maintain a "generated document" list including paths etc.
            anchorTarget = anchorTarget + ".adoc";
        }

        String result = String.format("%sxref:%s%s[%s]" //
            , isCreateListItem ? "* " : "" //
            , model.inputFacade != null ? model.inputFacade.getAntoraModuleName(anchorTarget) + ":" : "" //
            , anchorTarget //
            , model.anchorText //
        );

        model.asciidoc.add(result);
    }

    @Override
    public void doTrace(String dtName, String version, int rules, int rule, AnchorModel model) {
        lfetLogging.trace(dtName, version, rule, rules, model);
    }

    @Override
    public String convert(Element element, InputFacade inputFacade) {
        if (logD) LogUtil.mEntry(LOGGER, "convert(Element element, InputFacade inputFacade)");
        if (logD) LogUtil.mStmt(LOGGER, "element=" + element);

        AnchorModel model = new AnchorModel(element, inputFacade);

        isCreateListItem = false;

        rulesEngine.execute(this, model);

        String result = String.join("", model.asciidoc);

        if (logD) LogUtil.mStmt(LOGGER, "result=" + result);
        if (logD) LogUtil.mExit(LOGGER, "convert(Element element, InputFacade inputFacade)");
        return result;
    }
}
