package io.rulebased.group.javahelp.converter.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.File;
import java.nio.charset.Charset;

@Data
public class OutputConfig {

    private File directory;
    @JsonProperty(defaultValue = "true")
    private boolean cleanupBefore;
    @JsonProperty(defaultValue = "UTF-8")
    private Charset encoding;
    @JsonProperty(defaultValue = "false")
    private boolean saveOriginalHtmlFile;
    @JsonProperty("antora")
    private AntoraYml antoraYml;
}
