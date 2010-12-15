package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.ShortFormProvider;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Oct-2007<br><br>
 *
 * A general purpose interface, implementations of which can be used
 * to renderer OWL objects (e.g. class expressions, axioms etc.) for
 * presentation in user interfaces, colsole writing etc. etc.  Many
 * ontology renderers may also choose to implement this interface.
 */
public interface OWLObjectRenderer {

    /**
     * Sets the short form provider, which determines the short form
     * that should be used for entities.
     * @param shortFormProvider The short form provider to be used.
     */
    void setShortFormProvider(ShortFormProvider shortFormProvider);

    /**
     * Renders the specified object.
     * @param object The object to be rendered.
     * @return A string that represents the rendering
     * of the object.
     */
    String render(OWLObject object);
}
