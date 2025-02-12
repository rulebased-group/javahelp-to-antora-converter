package io.rulebased.group.javahelp.converter;

import org.junit.jupiter.api.Test;

class ConverterTest  {

    @Test
    void convert_javahelp_lfet_240() {
        Converter.main(new String[]{"src/test/resources/javahelp/lfet-help-de-2.4.0.converter-config.yaml"});
    }

}
