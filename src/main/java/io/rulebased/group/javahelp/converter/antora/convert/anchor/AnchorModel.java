package io.rulebased.group.javahelp.converter.antora.convert.anchor;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;

import java.util.List;

@RequiredArgsConstructor
class AnchorModel {

    final Element anchorElement;

    List<String> asciidoc;

}
