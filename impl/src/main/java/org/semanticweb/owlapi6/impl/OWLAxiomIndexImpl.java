package org.semanticweb.owlapi6.impl;

import java.util.stream.Stream;

import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLAxiomIndex;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi6.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi6.search.Filters;

/**
 * @author ignazio
 * @since 4.0.0
 */
public abstract class OWLAxiomIndexImpl extends OWLObjectImpl
    implements OWLAxiomIndex, HasTrimToSize {

    protected final Internals ints = new Internals();

    @Override
    public void trimToSize() {
        ints.trimToSize();
    }

    @Override
    public Stream<OWLDatatypeDefinitionAxiom> datatypeDefinitions(OWLDatatype datatype) {
        // XXX stream better?
        return ints.filterAxioms(Filters.datatypeDefFilter, datatype).stream()
            .map(ax -> (OWLDatatypeDefinitionAxiom) ax);
    }

    @Override
    public Stream<OWLSubAnnotationPropertyOfAxiom> subAnnotationPropertyOfAxioms(
        OWLAnnotationProperty subProperty) {
        return ints.filterAxioms(Filters.subAnnotationWithSub, subProperty).stream()
            .map(ax -> (OWLSubAnnotationPropertyOfAxiom) ax);
    }

    @Override
    public Stream<OWLAnnotationPropertyDomainAxiom> annotationPropertyDomainAxioms(
        OWLAnnotationProperty property) {
        return ints.filterAxioms(Filters.apDomainFilter, property).stream()
            .map(ax -> (OWLAnnotationPropertyDomainAxiom) ax);
    }

    @Override
    public Stream<OWLAnnotationPropertyRangeAxiom> annotationPropertyRangeAxioms(
        OWLAnnotationProperty property) {
        return ints.filterAxioms(Filters.apRangeFilter, property).stream()
            .map(ax -> (OWLAnnotationPropertyRangeAxiom) ax);
    }
}
