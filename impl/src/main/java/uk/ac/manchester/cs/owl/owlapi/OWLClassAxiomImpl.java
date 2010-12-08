package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 05-Dec-2006<br><br>
 */
public abstract class OWLClassAxiomImpl extends OWLLogicalAxiomImpl implements OWLClassAxiom {

    protected OWLClassAxiomImpl(OWLDataFactory dataFactory, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
    }
}
