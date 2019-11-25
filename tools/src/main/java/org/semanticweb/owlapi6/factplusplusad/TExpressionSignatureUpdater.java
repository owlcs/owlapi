package org.semanticweb.owlapi6.factplusplusad;

import java.util.Collection;

import org.semanticweb.owlapi6.atomicdecomposition.Signature;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLObjectVisitor;

/**
 * update the signature by adding all signature elements from the expression
 */
class TExpressionSignatureUpdater implements OWLObjectVisitor {

    /**
     * Signature to be filled
     */
    Signature sig;

    /**
     * init c'tor
     *
     * @param s signature
     */
    TExpressionSignatureUpdater(Signature s) {
        sig = s;
    }

    @Override
    public void doDefault(OWLObject object) {
        if (object instanceof OWLAnnotation || object instanceof OWLAnnotationProperty
            || object instanceof OWLAnnotationAssertionAxiom
            || object instanceof OWLAnnotationPropertyDomainAxiom
            || object instanceof OWLAnnotationPropertyRangeAxiom) {
            return;
        }
        if (object instanceof OWLEntity) {
            vE((OWLEntity) object);
        }
        object.componentStream().forEach(this::accept);
    }

    @SuppressWarnings("unchecked")
    private void accept(Object o) {
        if (o instanceof Collection) {
            ((Collection<OWLObject>) o).forEach(this::accept);
        } else if (o instanceof OWLObject) {
            ((OWLObject) o).accept(this);
        }
    }

    private void vE(OWLEntity e) {
        if (e.isTopEntity() || e.isBottomEntity()) {
            return;
        }
        sig.add(e);
    }
}
