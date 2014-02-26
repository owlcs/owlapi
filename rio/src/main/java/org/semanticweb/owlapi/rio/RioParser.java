/**
 * 
 */
package org.semanticweb.owlapi.rio;

import org.semanticweb.owlapi.formats.RioRDFOntologyFormat;
import org.semanticweb.owlapi.io.OWLParser;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
public interface RioParser extends OWLParser {

    RioRDFOntologyFormat getParserFormat();
}
