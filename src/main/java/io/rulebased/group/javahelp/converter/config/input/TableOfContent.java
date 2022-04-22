package io.rulebased.group.javahelp.converter.config.input;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TableOfContent {

    List<String> skip = new ArrayList<>();
}
