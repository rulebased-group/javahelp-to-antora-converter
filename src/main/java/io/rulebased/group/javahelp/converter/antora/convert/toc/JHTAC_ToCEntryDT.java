// *** WARNING: DO NOT MODIFY *** This is a generated Java source code! 
// 
// Generated by LF-ET 2.2.0 (220224a), https://www.lohrfink.de/lfet
// From decision table
// "/opt/data/github/rulebased-group/javahelp-to-antora-converter/src/main/resources/JHTAC_ToCEntry.lfet"
// 21.04.2022 11:32
// 
// Prolog Standard ---->
// profile LFET.Java.Prolog.Standard.Interface.ini not found
// used LF-ET 2.2.0 (220224a) build in default

package io.rulebased.group.javahelp.converter.antora.convert.toc;

import javax.annotation.Generated;

@Generated("LF-ET")
@SuppressWarnings("UnnecessaryFullyQualifiedName")
interface JHTAC_ToCEntryDT<T>
{
 
    // Prolog Standard <----

    // Prolog Decision Table ---->
    // $$Package=io.rulebased.group.javahelp.converter.antora.convert.toc
    // Prolog Decision Table <----
    
    /** 
     * <b>B02: next nested toc entry exists</b>
     */
    boolean isNextNestedTocEntryExists(T model);
    
    /** 
     * <b>B03: Exists target file in archive</b>
     */
    boolean isExistsTargetFileInArchive(T model);
    
    /** 
     * <b>B04: skip toc enry</b>
     */
    boolean isSkipTocEnry(T model);
    
    /** 
     * <b>B05: save original file</b>
     */
    boolean isSaveOriginalFile(T model);
    
    /** 
     * <b>A01: Error</b><br>
     * <br>
     * // $$Enum.Plain<br>
     * <br>
     * The enum generation has been triggered in project.ini by:<br>
     * - <b>ide.Java.InterfaceEnum.001</b>: Symbol; Title; IncludeTagsText = $$Enum.Plain; MethodName = EnumName;
     */
    void doError(JHTAC_ToCEntryDT.Error arg0, T model);
    
    /** 
     * <b>A02: process table of content entry</b>
     */
    void doProcessTableOfContentEntry(T model);
    
    /** 
     * <b>A03: convert html to adoc</b>
     */
    void doConvertHtmlToAdoc(T model);
    
    /** 
     * <b>A04: create pages directory</b>
     */
    void doCreatePagesDirectory(T model);
    
    /** 
     * <b>A05: save original file</b><br>
     * <br>
     * // $$Enum.Plain<br>
     * <br>
     * The enum generation has been triggered in project.ini by:<br>
     * - <b>ide.Java.InterfaceEnum.001</b>: Symbol; Title; IncludeTagsText = $$Enum.Plain; MethodName = EnumName;
     */
    void doSaveOriginalFile(JHTAC_ToCEntryDT.SaveOriginalFile arg0, T model);
    
    /** 
     * <b>A06: add entry to nav</b>
     */
    void doAddEntryToNav(T model);
    
    /** 
     * <b>A07: extract images</b>
     */
    void doExtractImages(T model);
    
    void doTrace(java.lang.String dtName, java.lang.String version, int rules, int rule, T model);
    
    /** 
     * <b>A01: Error</b><br>
     * <br>
     * // $$Enum.Plain<br>
     * <br>
     * The enum generation has been triggered in project.ini by:<br>
     * - <b>ide.Java.InterfaceEnum.001</b>: Symbol; Title; IncludeTagsText = $$Enum.Plain; MethodName = EnumName;
     */
    enum Error
    {
        /** 
         * <b>A01: Error</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>A01/01: TFM - Target file in archive missing</b>
         */
        $001("TFM", "Target file in archive missing");
        
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
    
    /** 
     * <b>A05: save original file</b><br>
     * <br>
     * // $$Enum.Plain<br>
     * <br>
     * The enum generation has been triggered in project.ini by:<br>
     * - <b>ide.Java.InterfaceEnum.001</b>: Symbol; Title; IncludeTagsText = $$Enum.Plain; MethodName = EnumName;
     */
    enum SaveOriginalFile
    {
        /** 
         * <b>A05: save original file</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>A05/01: HTML - save original file</b>
         */
        $001("HTML", "save original file"),
        
        /** 
         * <b>A05: save original file</b><br>
         * <br>
         * // $$Enum.Plain<br>
         * <br>
         * <b>A05/02: ADOC - save converted file</b>
         */
        $002("ADOC", "save converted file");
        
        private final String symbol;
        
        private final String title;
        
        SaveOriginalFile(String symbol, String title)
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

