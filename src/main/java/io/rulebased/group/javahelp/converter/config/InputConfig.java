package io.rulebased.group.javahelp.converter.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.rulebased.group.javahelp.converter.config.input.Options;
import lombok.Data;

import java.io.File;
import java.nio.charset.Charset;

@Data
public class InputConfig {

    @JsonProperty(required = true)
    private File jarFile;
    private Charset encoding;
    @JsonProperty(required = true)
    private String tableOfContentFileName;
    private Options options = new Options();
}
