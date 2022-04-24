package io.rulebased.group.javahelp.converter.antora.convert.anchor;

import io.rulebased.group.javahelp.converter.antora.logging.ILfetLogging;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

@RequiredArgsConstructor
class AnchorConverter implements ConvertAnchorDT<AnchorModel>, IAnchorConverter {

    static final ConvertAnchorRulesEngine rulesEngine = new ConvertAnchorRulesEngine();
    final ILfetLogging lfetLogging;

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
        switch (arg0) {
            case $001: {
                return model.currentNode instanceof TextNode;
            }
            case $002: {
                return model.currentNode instanceof Element;
            }
            default: {
                return false;
            }
        }
    }

    @Override
    public boolean isAnchorTextIsEmpty(AnchorModel model) {
        return model.anchorText.isEmpty();
    }

    @Override
    public void doExtractAnchorTarget(AnchorModel model) {
        model.anchorTarget = model.element.attr("href");
    }

    @Override
    public void doExtractAnchorText(AnchorModel model) {
        model.anchorText = ((TextNode)model.currentNode).text();
    }

    @Override
    public void doCreateXrefLink(AnchorModel model) {
        model.asciidoc.add("xref:" + model.anchorTarget + "[" + model.anchorText + "]");
    }

    @Override
    public void doTrace(String dtName, String version, int rules, int rule, AnchorModel model) {
        lfetLogging.trace(dtName, version, rule, rules, model);
    }

    @Override
    public String convert(Element element) {
        AnchorModel model = new AnchorModel(element);
        rulesEngine.execute(this, model);
        return String.join("", model.asciidoc);
    }
}
