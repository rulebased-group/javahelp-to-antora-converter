package io.rulebased.group.javahelp.converter.config.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Options {

    @JsonProperty("toc")
    private TableOfContent toc = new TableOfContent();
}
