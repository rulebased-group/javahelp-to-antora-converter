package io.rulebased.group.javahelp.converter.antora.convert.anchor;

import io.rulebased.group.javahelp.converter.antora.logging.ILfetLogging;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;

@RequiredArgsConstructor
class AnchorConverter implements ConvertAnchorDT<AnchorModel>, IAnchorConverter {

    static final ConvertAnchorRulesEngine rulesEngine = new ConvertAnchorRulesEngine();
    final ILfetLogging lfetLogging;

    @Override
    public boolean isCurrentElementIs(CurrentElementIs arg0, AnchorModel model) {
        return model.anchorElement.nodeName().equalsIgnoreCase(arg0.getSymbol());
    }

    @Override
    public void doExtractAnchor(AnchorModel model) {

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
