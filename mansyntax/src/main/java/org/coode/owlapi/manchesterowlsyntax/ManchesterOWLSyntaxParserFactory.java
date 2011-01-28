package org.coode.owlapi.manchesterowlsyntax;

import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserFactory;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Aug-2007<br><br>
 */
public class ManchesterOWLSyntaxParserFactory implements OWLParserFactory {

	  @SuppressWarnings("unused")
    public OWLParser createParser(OWLOntologyManager owlOntologyManager) {
        return new ManchesterOWLSyntaxOntologyParser();
    }
}
