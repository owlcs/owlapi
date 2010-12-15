package org.semanticweb.owlapi.model;

import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 24-Oct-2006
 * <p/>
 * Represents a <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Datatype_Restrictions">DatatypeRestriction</a> data range in the OWL 2 Specification.
 */
public interface OWLDatatypeRestriction extends OWLDataRange {

    /**
     * Gets the data range that this data range restricts.
     *
     * @return The datatype that is restricted
     */
    OWLDatatype getDatatype();


    /**
     * Gets the facet restrictions on this data range
     *
     * @return A <code>Set</code> of facet restrictions that apply to
     *         this data range
     */
    Set<OWLFacetRestriction> getFacetRestrictions();
}
