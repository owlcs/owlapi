package org.semanticweb.owlapi.model;

import java.net.URI;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 * <p/>
 * Represents an import statement in an ontology.
 */
public interface OWLImportsDeclaration extends Comparable<OWLImportsDeclaration> {

    /**
     * Gets the import IRI
     * @return The import IRI that points to the ontology to be imported.  The imported
     *         ontology should have this IRI as its IRI (although this isn't enforced).
     */
    IRI getIRI();

    /**
     * A convenience method to obtain the import IRI as a URI
     * @return The import IRI as a URI
     */
    URI getURI();
}
