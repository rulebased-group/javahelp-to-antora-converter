package io.rulebased.group.javahelp.converter.antora.convert.anchor;

import io.rulebased.group.javahelp.converter.antora.logging.ILfetLogging;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AnchorConverterTest implements ILfetLogging {

    @Test
    void convert() {
        Document anchorDocument = Jsoup.parse("<a href=\"link\">text</a>");
        assertThat(new AnchorConverter(this).convert(anchorDocument.select("a").first())).isEqualTo("xref:link[text]");
        assertThat(new AnchorConverter(this).convert(Jsoup.parse("<a href=\"link\"><img src=\"button.gif\"></a>").select("a").first())).isEmpty();
    }

    @Override
    public <T> void trace(String lfet, String version, int currentRule, int maxRules, T model) {
        System.out.println(lfet + " " + currentRule + " / " + maxRules);
    }
}