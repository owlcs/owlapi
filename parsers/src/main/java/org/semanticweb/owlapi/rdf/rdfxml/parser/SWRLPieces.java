package org.semanticweb.owlapi.rdf.rdfxml.parser;

import static org.semanticweb.owlapi.util.CollectionFactory.createSet;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;

class SWRLPieces {

    /**
     * The swrl rules.
     */
    final Set<IRI> swrlRules = createSet();
    /**
     * The swrl individual property atoms.
     */
    private final Set<IRI> swrlIndividualPropertyAtoms = createSet();
    /**
     * The swrl data valued property atoms.
     */
    private final Set<IRI> swrlDataValuedPropertyAtoms = createSet();
    /**
     * The swrl class atoms.
     */
    private final Set<IRI> swrlClassAtoms = createSet();
    /**
     * The swrl data range atoms.
     */
    private final Set<IRI> swrlDataRangeAtoms = createSet();
    /**
     * The swrl built in atoms.
     */
    private final Set<IRI> swrlBuiltInAtoms = createSet();
    /**
     * The swrl variables.
     */
    private final Set<IRI> swrlVariables = createSet();
    /**
     * The swrl same as atoms.
     */
    private final Set<IRI> swrlSameAsAtoms = createSet();
    /**
     * The swrl different from atoms.
     */
    private final Set<IRI> swrlDifferentFromAtoms = createSet();

    protected IRI addSWRLRule(IRI iri) {
        swrlRules.add(iri);
        return iri;
    }

    protected boolean isSWRLRule(IRI iri) {
        return swrlRules.contains(iri);
    }

    protected void addSWRLIndividualPropertyAtom(IRI iri) {
        swrlIndividualPropertyAtoms.add(iri);
    }

    protected boolean isSWRLIndividualPropertyAtom(IRI iri) {
        return swrlIndividualPropertyAtoms.contains(iri);
    }

    protected void addSWRLDataPropertyAtom(IRI iri) {
        swrlDataValuedPropertyAtoms.add(iri);
    }

    protected boolean isSWRLDataValuedPropertyAtom(IRI iri) {
        return swrlDataValuedPropertyAtoms.contains(iri);
    }

    protected void addSWRLClassAtom(IRI iri) {
        swrlClassAtoms.add(iri);
    }

    protected boolean isSWRLClassAtom(IRI iri) {
        return swrlClassAtoms.contains(iri);
    }

    protected void addSWRLSameAsAtom(IRI iri) {
        swrlSameAsAtoms.add(iri);
    }

    protected boolean isSWRLSameAsAtom(IRI iri) {
        return swrlSameAsAtoms.contains(iri);
    }

    protected void addSWRLDifferentFromAtom(IRI iri) {
        swrlDifferentFromAtoms.add(iri);
    }

    protected boolean isSWRLDifferentFromAtom(IRI iri) {
        return swrlDifferentFromAtoms.contains(iri);
    }

    protected void addSWRLDataRangeAtom(IRI iri) {
        swrlDataRangeAtoms.add(iri);
    }

    protected boolean isSWRLDataRangeAtom(IRI iri) {
        return swrlDataRangeAtoms.contains(iri);
    }

    protected void addSWRLBuiltInAtom(IRI iri) {
        swrlBuiltInAtoms.add(iri);
    }

    protected boolean isSWRLBuiltInAtom(IRI iri) {
        return swrlBuiltInAtoms.contains(iri);
    }

    protected void addSWRLVariable(IRI iri) {
        swrlVariables.add(iri);
    }

    protected boolean isSWRLVariable(IRI iri) {
        return swrlVariables.contains(iri);
    }
}
