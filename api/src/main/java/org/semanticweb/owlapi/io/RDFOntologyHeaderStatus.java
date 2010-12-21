package org.semanticweb.owlapi.io;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 21/12/2010
 * @since 3.2
 */
public enum RDFOntologyHeaderStatus {

        /**
         * Specifies that during parsing, the ontology document did not contain any ontology headers
         */
        PARSED_ZERO_HEADERS,

        /**
         * Specifies that during parsing, the ontology document that the ontology was created from contained one header
         */
        PARSED_ONE_HEADER,

        /**
         * Specifies that during parsing, the ontology document that the ontology was created from contained multiple headers
         */
        PARSED_MULTIPLE_HEADERS
}
