package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 05-Dec-2006<br><br>
 */
public abstract class OWLLogicalAxiomImpl extends OWLAxiomImpl implements OWLLogicalAxiom {

    protected OWLLogicalAxiomImpl(OWLDataFactory dataFactory, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
    }


    public boolean isLogicalAxiom() {
        return true;
    }

    public boolean isAnnotationAxiom() {
        return false;
    }
}
