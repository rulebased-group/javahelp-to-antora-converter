package io.rulebased.group.javahelp.converter.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@JsonPropertyOrder(value = {"name", "title", "version", "start_page", "nav", "asciidoc"})
public class AntoraYml {

    String name;
    String title;
    String version;
    @JsonProperty(value = "start_page")
    String startPage;
    List<String> nav = new ArrayList<>();
    Map<String, Object> asciidoc = new HashMap<>();

}