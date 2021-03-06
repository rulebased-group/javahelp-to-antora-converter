// *** WARNING: DO NOT MODIFY *** This is a generated Java source code! 
// 
// Generated by LF-ET 2.2.0 (220224a), https://www.lohrfink.de/lfet
// From decision table
// "/opt/data/github/rulebased-group/javahelp-to-antora-converter/src/main/resources/JHTAC_ToCEntry.lfet"
// 21.04.2022 11:32
// 

// Prolog Standard ---->
// profile LFET.Java.Prolog.Standard.Interface.Dt.ini not found
// used LF-ET 2.2.0 (220224a) build in default

package io.rulebased.group.javahelp.converter.antora.convert.toc;

import javax.annotation.Generated;

@Generated("LF-ET")
@SuppressWarnings("ConstantConditions")
class JHTAC_ToCEntryRulesEngine
{
    public <T> void execute(JHTAC_ToCEntryDT<T> iface, T model)
    {
 
    // Prolog Standard <----

        // Prolog Decision Table ---->
        // $$Package=io.rulebased.group.javahelp.converter.antora.convert.toc
        // Prolog Decision Table <----
        
        int ruleGroup = 1;
        boolean exit = false;
        
        while ( !exit )
        {
            exit = true;
            
            if ( ruleGroup == 1 )
            {
                if ( iface.isExistsTargetFileInArchive(model) )
                {
                    if ( iface.isSkipTocEnry(model) )
                    {
                        // Rule R01 ---->
                        
                        iface.doTrace("JHTAC_ToCEntry", "20220421.113215", 9, 1, model);
                        
                        exit = true;
                        
                        // Rule R01 <----
                    }
                    else
                    {
                        // Rule R02 ---->
                        
                        iface.doTrace("JHTAC_ToCEntry", "20220421.113215", 9, 2, model);
                        
                        iface.doCreatePagesDirectory(model);
                        iface.doAddEntryToNav(model);
                        
                        ruleGroup++;
                        exit = false;
                        
                        // Rule R02 <----
                    }
                }
                else
                {
                    // Rule R03 ---->
                    
                    iface.doTrace("JHTAC_ToCEntry", "20220421.113215", 9, 3, model);
                    
                    iface.doError(JHTAC_ToCEntryDT.Error.$001 /* TFM */ , model);
                    
                    exit = true;
                    
                    // Rule R03 <----
                }
            }
            else if ( ruleGroup == 2 )
            {
                if ( iface.isSaveOriginalFile(model) )
                {
                    // Rule R04 ---->
                    
                    iface.doTrace("JHTAC_ToCEntry", "20220421.113215", 9, 4, model);
                    
                    iface.doSaveOriginalFile(JHTAC_ToCEntryDT.SaveOriginalFile.$001 /* HTML */ , model);
                    
                    ruleGroup++;
                    exit = false;
                    
                    // Rule R04 <----
                }
                else
                {
                    // Rule R05 ---->
                    
                    iface.doTrace("JHTAC_ToCEntry", "20220421.113215", 9, 5, model);
                    
                    ruleGroup++;
                    exit = false;
                    
                    // Rule R05 <----
                }
            }
            else if ( ruleGroup == 3 )
            {
                // Rule R06 ---->
                
                iface.doTrace("JHTAC_ToCEntry", "20220421.113215", 9, 6, model);
                
                iface.doExtractImages(model);
                
                ruleGroup++;
                exit = false;
                
                // Rule R06 <----
            }
            else if ( ruleGroup == 4 )
            {
                // Rule R07 ---->
                
                iface.doTrace("JHTAC_ToCEntry", "20220421.113215", 9, 7, model);
                
                iface.doConvertHtmlToAdoc(model);
                iface.doSaveOriginalFile(JHTAC_ToCEntryDT.SaveOriginalFile.$002 /* ADOC */ , model);
                
                ruleGroup++;
                exit = false;
                
                // Rule R07 <----
            }
            else
            {
                if ( iface.isNextNestedTocEntryExists(model) )
                {
                    // Rule R08 ---->
                    
                    iface.doTrace("JHTAC_ToCEntry", "20220421.113215", 9, 8, model);
                    
                    iface.doProcessTableOfContentEntry(model);
                    
                    ruleGroup = 5;
                    exit = false;
                    
                    // Rule R08 <----
                }
                else
                {
                    // Rule R09 ---->
                    
                    iface.doTrace("JHTAC_ToCEntry", "20220421.113215", 9, 9, model);
                    
                    exit = true;
                    
                    // Rule R09 <----
                }
            }
        
        }

        // Epilog Standard ---->
        // profile LFET.Java.Epilog.Standard.Interface.Dt.ini not found
        // used LF-ET 2.2.0 (220224a) build in default

    }

}
 
// Epilog Standard <----

// End of generated Java source code
// Generated by LF-ET 2.2.0 (220224a), https://www.lohrfink.de/lfet

