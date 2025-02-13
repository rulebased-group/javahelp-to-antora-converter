package io.rulebased.group.javahelp.converter.antora.convert.anchor;

import io.rulebased.group.javahelp.converter.antora.logging.ILfetLogging;
import io.rulebased.group.javahelp.converter.facade.InputFacade;
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
        final boolean result;

        switch (arg0) {
            case $001: {
                result = model.currentNode instanceof TextNode;
                break;
            }
            case $002: {
                result = model.currentNode instanceof Element;
                break;
            }
            default: {
                result = false;
            }
        }
        return result;
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
    public void doCreateXrefLink(AnchorModel model) {

        String anchorTarget = model.anchorTarget;
        String module = model.inputFacade != null ? model.inputFacade.getAntoraModuleName(anchorTarget) + ":" : "";

        if (anchorTarget.matches(".*\\.htm$")) {
            // .htm is the generated file extension by DocToHelp which is the authoring system used for the german LF-ET user manual
            // so it should be pretty save to add ".adoc"
            // TODO maybe we should use and maintain a "generated document" list including paths etc.
            anchorTarget = anchorTarget + ".adoc";
        }

        String result = "xref:" + module + anchorTarget + "[" + model.anchorText + "]";
        model.asciidoc.add(result);
    }

    @Override
    public void doTrace(String dtName, String version, int rules, int rule, AnchorModel model) {
        lfetLogging.trace(dtName, version, rule, rules, model);
    }

    @Override
    public String convert(Element element, InputFacade inputFacade) {
        AnchorModel model = new AnchorModel(element, inputFacade);
        rulesEngine.execute(this, model);
        return String.join("", model.asciidoc);
    }
}
