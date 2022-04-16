// *** WARNING: DO NOT MODIFY *** This is a generated Java source code! 
// 
// Generated by LF-ET 2.2.0 (220224a), https://www.lohrfink.de/lfet
// From decision table
// "/opt/data/github/rulebased-group/javahelp-to-antora-converter/src/main/resources/JHTAC_HtmlToAsciiDocConverter.lfet"
// 16.04.2022 21:24
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
     * <b>B04: current element is ...</b><br>
     * <br>
     * // $$Enum.Plain<br>
     * <br>
     * The enum generation has been triggered in project.ini by:<br>
     * - <b>ide.Java.InterfaceEnum.001</b>: Symbol; Title; IncludeTagsText = $$Enum.Plain; MethodName = EnumName;
     */
    boolean isCurrentElementIs(JHTAC_HtmlToAsciiDocConverterDT.CurrentElementIs arg0, T model);
    
    /** 
     * <b>B05: text is  '&nbsp;'</b>
     */
    boolean isTextIsNbsp(T model);
    
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
     * <b>A04: extract text</b><br>
     * <br>
     * // $$Enum.Plain<br>
     * <br>
     * The enum generation has been triggered in project.ini by:<br>
     * - <b>ide.Java.InterfaceEnum.001</b>: Symbol; Title; IncludeTagsText = $$Enum.Plain; MethodName = EnumName;
     */
    void doExtractText(JHTAC_HtmlToAsciiDocConverterDT.ExtractText arg0, T model);
    
    /** 
     * <b>A05: increment current header level</b>
     */
    void doIncrementCurrentHeaderLevel(T model);
    
    /** 
     * <b>A06: linebreak</b>
     */
    void doLinebreak(T model);
    
    void doTrace(java.lang.String dtName, java.lang.String version, int rules, int rule, T model);
    
    /** 
     * <b>B04: current element is ...</b><br>
     * <br>
     * // $$Enum.Plain<br>
     * <br>
     * The enum generation has been triggered in project.ini by:<br>
     * - <b>ide.Java.InterfaceEnum.001</b>: Symbol; Title; IncludeTagsText = $$Enum.Plain; MethodName = EnumName;
     */
    enum CurrentElementIs
    {
        /** 
         * <b>B04: current element is ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B04/01: h - header with level x</b>
         */
        $001("h", "header with level x"),
        
        /** 
         * <b>B04: current element is ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B04/02: img - Image</b>
         */
        $002("img", "Image"),
        
        /** 
         * <b>B04: current element is ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B04/03: b - Bold text</b>
         */
        $003("b", "Bold text"),
        
        /** 
         * <b>B04: current element is ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B04/04: i - italic text</b>
         */
        $004("i", "italic text"),
        
        /** 
         * <b>B04: current element is ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B04/05: a - anchor</b>
         */
        $005("a", "anchor"),
        
        /** 
         * <b>B04: current element is ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B04/06: font - font</b>
         */
        $006("font", "font"),
        
        /** 
         * <b>B04: current element is ...</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>B04/07: br - line break</b>
         */
        $007("br", "line break");
        
        private final String symbol;
        
        private final String title;
        
        CurrentElementIs(String symbol, String title)
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
     * <b>A04: extract text</b><br>
     * <br>
     * // $$Enum.Plain<br>
     * <br>
     * The enum generation has been triggered in project.ini by:<br>
     * - <b>ide.Java.InterfaceEnum.001</b>: Symbol; Title; IncludeTagsText = $$Enum.Plain; MethodName = EnumName;
     */
    enum ExtractText
    {
        /** 
         * <b>A04: extract text</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>A04/01: * - surroung with *</b>
         */
        $001("*", "surroung with *"),
        
        /** 
         * <b>A04: extract text</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>A04/02: = - start with '=' multiply with current header level</b>
         */
        $002("=", "start with '=' multiply with current header level"),
        
        /** 
         * <b>A04: extract text</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>A04/03: _ - surround with _</b>
         */
        $003("_", "surround with _"),
        
        /** 
         * <b>A04: extract text</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>A04/04: NONE - extratct only text</b>
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

    // Epilog Standard ---->
    // profile LFET.Java.Epilog.Standard.Interface.ini not found
    // used LF-ET 2.2.0 (220224a) build in default

}
 
// Epilog Standard <----

// End of generated Java source code
// Generated by LF-ET 2.2.0 (220224a), https://www.lohrfink.de/lfet

