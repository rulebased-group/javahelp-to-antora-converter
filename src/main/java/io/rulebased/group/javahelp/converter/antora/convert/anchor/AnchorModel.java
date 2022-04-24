package io.rulebased.group.javahelp.converter.antora.convert.anchor;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
class AnchorModel {

    final Element element;
    String anchorTarget;
    String anchorText = "";
    Iterator<Node> childsNodesIt;
    Node currentNode;

    List<String> asciidoc = new ArrayList<>();

}
