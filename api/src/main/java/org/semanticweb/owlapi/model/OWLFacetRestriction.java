package org.semanticweb.owlapi.model;

import org.semanticweb.owlapi.vocab.OWLFacet;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Jan-2007<br><br>
 * <p/>
 * A facet restriction is used to restrict a particular datatype.  For
 * example the set of integers greater than 18 can be obtained by restricting
 * the integer datatype using a minExclusive facet with a value of 18
 */
public interface OWLFacetRestriction extends OWLObject {

    /**
     * Gets the retricted facet
     *
     * @return The restricted facet
     */
    OWLFacet getFacet();


    /**
     * Gets the value that restricts the facet
     *
     * @return the restricting value
     */
    OWLLiteral getFacetValue();

    void accept(OWLDataVisitor visitor);

    <O> O accept(OWLDataVisitorEx<O> visitor);
}
