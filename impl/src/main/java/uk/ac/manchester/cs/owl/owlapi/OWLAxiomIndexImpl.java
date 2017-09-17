package uk.ac.manchester.cs.owl.owlapi;

import java.util.stream.Stream;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAxiomIndex;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.search.Filters;

/**
 * @author ignazio
 * @since 4.0.0
 */
public abstract class OWLAxiomIndexImpl extends OWLObjectImpl implements OWLAxiomIndex,
    HasTrimToSize {

    protected final Internals ints = new Internals();

    @Override
    public void trimToSize() {
        ints.trimToSize();
    }

    /**
     *
     * @param datatype The datatype for which the returned axioms provide a definition.
     * @return Sorted stream of axioms
     */
    @Override
    public Stream<OWLDatatypeDefinitionAxiom> datatypeDefinitions(OWLDatatype datatype) {
        // XXX stream better?
        return ints.filterAxioms(Filters.datatypeDefFilter, datatype).stream().map(
            ax -> (OWLDatatypeDefinitionAxiom) ax);
    }

    /**
     *
     * @param subProperty The sub-property of the axioms to be retrieved.
     * @return Sorted stream of axioms
     */
    @Override
    public Stream<OWLSubAnnotationPropertyOfAxiom> subAnnotationPropertyOfAxioms(
        OWLAnnotationProperty subProperty) {
        return ints.filterAxioms(Filters.subAnnotationWithSub, subProperty).stream().map(
            ax -> (OWLSubAnnotationPropertyOfAxiom) ax);
    }

    /**
     *
     * @param property The property that the axiom specifies a domain for.
     * @return Sorted stream of axioms
     */
    @Override
    public Stream<OWLAnnotationPropertyDomainAxiom> annotationPropertyDomainAxioms(
        OWLAnnotationProperty property) {
        return ints.filterAxioms(Filters.apDomainFilter, property).stream().map(
            ax -> (OWLAnnotationPropertyDomainAxiom) ax);
    }

    /**
     *
     * @param property The property that the axiom specifies a range for.
     * @return Sorted stream of axioms
     */
    @Override
    public Stream<OWLAnnotationPropertyRangeAxiom> annotationPropertyRangeAxioms(
        OWLAnnotationProperty property) {
        return ints.filterAxioms(Filters.apRangeFilter, property).stream().map(
            ax -> (OWLAnnotationPropertyRangeAxiom) ax);
    }
}
