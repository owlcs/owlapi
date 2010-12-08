package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.SWRLVariable;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 08-Jul-2009
 */
public interface SWRLVariableShortFormProvider {

    String getShortForm(SWRLVariable variable);
    
}
