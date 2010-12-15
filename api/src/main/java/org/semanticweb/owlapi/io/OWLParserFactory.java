package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Nov-2006<br><br>
 *
 * An object that can create an <code>OWLParser</code>.
 */
public interface OWLParserFactory {

    OWLParser createParser(OWLOntologyManager owlOntologyManager);
}
