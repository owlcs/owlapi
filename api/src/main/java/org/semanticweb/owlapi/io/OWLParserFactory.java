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

    /**
     * Creates a parser
     * @param owlOntologyManager This parameter is here for legacy reasons.  Parser factories should not use it.
     * @return The parser created by this parser factory.
     */
    OWLParser createParser(OWLOntologyManager owlOntologyManager);

}
