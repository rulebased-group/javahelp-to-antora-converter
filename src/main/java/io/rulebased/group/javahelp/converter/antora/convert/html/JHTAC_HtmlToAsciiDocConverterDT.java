// *** WARNING: DO NOT MODIFY *** This is a generated Java source code! 
// 
// Generated by LF-ET 2.2.0 (220224a), https://www.lohrfink.de/lfet
// From decision table
// "/opt/data/github/rulebased-group/javahelp-to-antora-converter/src/main/resources/JHTAC_HtmlToAsciiDocConverter.lfet"
// 23.04.2022 00:31
// 
// Prolog Standard ---->
// profile LFET.Java.Prolog.Standard.Interface.ini not found
// used LF-ET 2.2.0 (220224a) build in default

package io.rulebased.group.javahelp.converter.antora.convert.html;

import javax.annotation.Generated;

@Generated("LF-ET")
@SuppressWarnings("UnnecessaryFullyQualifiedName")
interface JHTAC_HtmlToAsciiDocConverterDT<T>
{
 
    // Prolog Standard <----

    // Prolog Decision Table ---->
    // $$Package=io.rulebased.group.javahelp.converter.antora.convert.html
    // Prolog Decision Table <----
    
    /** 
     * <b>B02: contains current element child elements</b>
     */
    boolean isContainsCurrentElementChildElements(T model);
    
    /** 
     * <b>B03: next element exists</b>
     */
    boolean isNextElementExists(T model);
    
    /** 
     * <b>B04: skip element</b>
     */
    boolean isSkipElement(T model);
    
    /** 
     * <b>B05: current element type is ...</b><br>
     * <br>
     * // $$Enum.Plain<br>
     * <br>
     * The enum generation has been triggered in project.ini by:<br>
     * - <b>ide.Java.InterfaceEnum.001</b>: Symbol; Title; IncludeTagsText = $$Enum.Plain; MethodName = EnumName;
     */
    boolean isCurrentElementTypeIs(JHTAC_HtmlToAsciiDocConverterDT.CurrentElementTypeIs arg0, T model);
    
    /** 
     * <b>B06: current element tagname is ...</b><br>
     * <br>
     * // $$Enum.Plain<br>
     * <br>
     * The enum generation has been triggered in project.ini by:<br>
     * - <b>ide.Java.InterfaceEnum.001</b>: Symbol; Title; IncludeTagsText = $$Enum.Plain; MethodName = EnumName;
     */
    boolean isCurrentElementTagnameIs(JHTAC_HtmlToAsciiDocConverterDT.CurrentElementTagnameIs arg0, T model);
    
    /** 
     * <b>B07: trimmed text is equal to ...</b><br>
     * <br>
     * // $$Enum.Plain<br>
     * <br>
     * The enum generation has been triggered in project.ini by:<br>
     * - <b>ide.Java.InterfaceEnum.001</b>: Symbol; Title; IncludeTagsText = $$Enum.Plain; MethodName = EnumName;
     */
    boolean isTrimmedTextIsEqualTo(JHTAC_HtmlToAsciiDocConverterDT.TrimmedTextIsEqualTo arg0, T model);
    
    /** 
     * <b>B08: start element is defined</b>
     */
    boolean isStartElementIsDefined(T model);
    
    /** 
     * <b>A01: extract image src</b>
     */
    void doExtractImageSrc(T model);
    
    /** 
     * <b>A02: select textbody element</b>
     */
    void doSelectTextbodyElement(T model);
    
    /** 
     * <b>A03: extract anchor</b>
     */
    void doExtractAnchor(T model);
    
    /** 
     * <b>A04: process element</b>
     */
    void doProcessElement(T model);
    
    /** 
     * <b>A05: extract text</b><br>
     * <br>
     * // $$Enum.Plain<br>
     * <br>
     * The enum generation has been triggered in project.ini by:<br>
     * - <b>ide.Java.InterfaceEnum.001</b>: Symbol; Title; IncludeTagsText = $$Enum.Plain; MethodName = EnumName;
     */
    void doExtractText(JHTAC_HtmlToAsciiDocConverterDT.ExtractText arg0, T model);
    
    /** 
     * <b>A06: increment current header level</b>
     */
    void doIncrementCurrentHeaderLevel(T model);
    
    /** 
     * <b>A07: linebreak</b>
     */
    void doLinebreak(T model);
    
    /** 
     * <b>A08: error</b><br>
     * <br>
     * // $$Enum.Plain<br>
     * <br>
     * The enum generation has been triggered in project.ini by:<br>
     * - <b>ide.Java.InterfaceEnum.001</b>: Symbol; Title; IncludeTagsText = $$Enum.Plain; MethodName = EnumName;
     */
    void doError(JHTAC_HtmlToAsciiDocConverterDT.Error arg0, T model);
    
    void doTrace(java.lang.String dtName, java.lang.String version, int rules, int rule, T model);
    
    /** 
     * <b>B05: current element type is ...</b><br>
     * <br>
     * // $$Enum.Plain<br>
     * <br>
     * The enum generation has been triggered in project.ini by:<br>
     * - <b>ide.Java.InterfaceEnum.001</b>: Symbol; Title; IncludeTagsText = $$Enum.Plain; MethodName = EnumName;
     */
    enum CurrentElementTypeIs
    {
        /** 
         * <b>B05: current element type is ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B05/01: T - TextNode</b>
         */
        $001("T", "TextNode"),
        
        /** 
         * <b>B05: current element type is ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B05/02: E - Element</b>
         */
        $002("E", "Element");
        
        private final String symbol;
        
        private final String title;
        
        CurrentElementTypeIs(String symbol, String title)
        {
            this.symbol = symbol;
            this.title = title;
        }
        
        public String getSymbol()
        {
            return symbol;
        }
        
        public String getTitle()
        {
            return title;
        }
    }
    
    /** 
     * <b>B06: current element tagname is ...</b><br>
     * <br>
     * // $$Enum.Plain<br>
     * <br>
     * The enum generation has been triggered in project.ini by:<br>
     * - <b>ide.Java.InterfaceEnum.001</b>: Symbol; Title; IncludeTagsText = $$Enum.Plain; MethodName = EnumName;
     */
    enum CurrentElementTagnameIs
    {
        /** 
         * <b>B06: current element tagname is ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B06/01: h1 - header with level 1</b>
         */
        $001("h1", "header with level 1"),
        
        /** 
         * <b>B06: current element tagname is ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B06/02: h2 - header with level 2</b>
         */
        $002("h2", "header with level 2"),
        
        /** 
         * <b>B06: current element tagname is ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B06/03: h3 - header with level 3</b>
         */
        $003("h3", "header with level 3"),
        
        /** 
         * <b>B06: current element tagname is ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B06/04: h4 - header with level 4</b>
         */
        $004("h4", "header with level 4"),
        
        /** 
         * <b>B06: current element tagname is ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B06/05: img - Image</b>
         */
        $005("img", "Image"),
        
        /** 
         * <b>B06: current element tagname is ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B06/06: b - Bold text</b>
         */
        $006("b", "Bold text"),
        
        /** 
         * <b>B06: current element tagname is ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B06/07: i - italic text</b>
         */
        $007("i", "italic text"),
        
        /** 
         * <b>B06: current element tagname is ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B06/08: a - anchor</b>
         */
        $008("a", "anchor"),
        
        /** 
         * <b>B06: current element tagname is ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B06/09: font - font</b>
         */
        $009("font", "font"),
        
        /** 
         * <b>B06: current element tagname is ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B06/10: br - line break</b>
         */
        $010("br", "line break"),
        
        /** 
         * <b>B06: current element tagname is ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B06/11: p - paragraph</b>
         */
        $011("p", "paragraph");
        
        private final String symbol;
        
        private final String title;
        
        CurrentElementTagnameIs(String symbol, String title)
        {
            this.symbol = symbol;
            this.title = title;
        }
        
        public String getSymbol()
        {
            return symbol;
        }
        
        public String getTitle()
        {
            return title;
        }
    }
    
    /** 
     * <b>B07: trimmed text is equal to ...</b><br>
     * <br>
     * // $$Enum.Plain<br>
     * <br>
     * The enum generation has been triggered in project.ini by:<br>
     * - <b>ide.Java.InterfaceEnum.001</b>: Symbol; Title; IncludeTagsText = $$Enum.Plain; MethodName = EnumName;
     */
    enum TrimmedTextIsEqualTo
    {
        /** 
         * <b>B07: trimmed text is equal to ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B07/01: &NBSP; - None breaking space</b>
         */
        $001("&NBSP;", "None breaking space"),
        
        /** 
         * <b>B07: trimmed text is equal to ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B07/02: EMPTY - Empty string</b>
         */
        $002("EMPTY", "Empty string"),
        
        /** 
         * <b>B07: trimmed text is equal to ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B07/03: BLANK - Blank string</b>
         */
        $003("BLANK", "Blank string"),
        
        /** 
         * <b>B07: trimmed text is equal to ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B07/04: \n - Linebreak</b>
         */
        $004("\\n", "Linebreak");
        
        private final String symbol;
        
        private final String title;
        
        TrimmedTextIsEqualTo(String symbol, String title)
        {
            this.symbol = symbol;
            this.title = title;
        }
        
        public String getSymbol()
        {
            return symbol;
        }
        
        public String getTitle()
        {
            return title;
        }
    }
    
    /** 
     * <b>A05: extract text</b><br>
     * <br>
     * // $$Enum.Plain<br>
     * <br>
     * The enum generation has been triggered in project.ini by:<br>
     * - <b>ide.Java.InterfaceEnum.001</b>: Symbol; Title; IncludeTagsText = $$Enum.Plain; MethodName = EnumName;
     */
    enum ExtractText
    {
        /** 
         * <b>A05: extract text</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>A05/01: * - surroung with *</b>
         */
        $001("*", "surroung with *"),
        
        /** 
         * <b>A05: extract text</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>A05/02: = - start with '=' multiply with current header level</b>
         */
        $002("=", "start with '=' multiply with current header level"),
        
        /** 
         * <b>A05: extract text</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>A05/03: _ - surround with _</b>
         */
        $003("_", "surround with _"),
        
        /** 
         * <b>A05: extract text</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>A05/04: NONE - extratct only text</b>
         */
        $004("NONE", "extratct only text");
        
        private final String symbol;
        
        private final String title;
        
        ExtractText(String symbol, String title)
        {
            this.symbol = symbol;
            this.title = title;
        }
        
        public String getSymbol()
        {
            return symbol;
        }
        
        public String getTitle()
        {
            return title;
        }
    }
    
    /** 
     * <b>A08: error</b><br>
     * <br>
     * // $$Enum.Plain<br>
     * <br>
     * The enum generation has been triggered in project.ini by:<br>
     * - <b>ide.Java.InterfaceEnum.001</b>: Symbol; Title; IncludeTagsText = $$Enum.Plain; MethodName = EnumName;
     */
    enum Error
    {
        /** 
         * <b>A08: error</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>A08/01: UT - Unknown element type</b>
         */
        $001("UT", "Unknown element type");
        
        private final String symbol;
        
        private final String title;
        
        Error(String symbol, String title)
        {
            this.symbol = symbol;
            this.title = title;
        }
        
        public String getSymbol()
        {
            return symbol;
        }
        
        public String getTitle()
        {
            return title;
        }
    }

    // Epilog Standard ---->
    // profile LFET.Java.Epilog.Standard.Interface.ini not found
    // used LF-ET 2.2.0 (220224a) build in default

}
 
// Epilog Standard <----

// End of generated Java source code
// Generated by LF-ET 2.2.0 (220224a), https://www.lohrfink.de/lfet

