package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 22-Jan-2007<br><br>
 */
public interface RootClassChecker {

    boolean isRootClass(OWLClass cls) throws OWLException;

}
