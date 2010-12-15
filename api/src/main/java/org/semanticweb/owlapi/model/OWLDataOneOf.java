package org.semanticweb.owlapi.model;

import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 24-Oct-2006
 * </p>
 * Represents <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Enumeration_of_Literals">DataOneOf</a>
 * in the OWL 2 Specification.
 *
 */
public interface OWLDataOneOf extends OWLDataRange {

    /**
     * Gets the values ({@link OWLLiteral}s) that this data range consists of.  These values may be a mixture
     * of {@link org.semanticweb.owlapi.model.OWLTypedLiteral}s and {@link org.semanticweb.owlapi.model.OWLStringLiteral}s.
     * @return The values that this data range consists of.
     */
    public Set<OWLLiteral> getValues();

}
