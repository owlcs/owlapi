package org.semanticweb.owlapi.modularity;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/**
 * An interface for any class implementing ontology segmentation or modularisation.
 * @author Thomas Schneider
 * @author School of Computer Science
 * @author University of Manchester
 */
public interface OntologySegmenter {

    /**
     * Returns a set of axioms that is a segment of the ontology associated with this segmenter.
     * This segment is determined by the specified seed signature (set of entities).
     * @param signature the seed signature
     * @return the segment as a set of axioms
     */
    public abstract Set<OWLAxiom> extract(Set<OWLEntity> signature);

    /**
     * Returns a set of axioms that is a segment of the ontology associated with this segmenter.
     * This segment is determined by a seed signature (set of entities),
     * which is the specified signature plus possibly all superclasses and/or subclasses of the classes therein.
     * Sub-/superclasses are determined using the specified reasoner.
     * @param signature the seed signature
     * @param superClassLevel determines whether superclasses are added to the signature before segment extraction, see below for admissible values
     * @param subClassLevel determines whether subclasses are added to the signature before segment extraction, see below for admissible values
     * @param reasoner the reasoner to determine super-/subclasses
     * @return the segment as a set of axioms
     *         Meaning of the value of superClassLevel, subClassLevel:<br>
     *         Let this value be k. If k > 0, then all classes are included that are (direct or indirect) super-/subclasses of some class in signature,
     *         with a distance of at most k to this class in the class hierarchy computed by reasoner. If k = 0, then no
     *         super-/subclasses are added. If k < 0, then all direct and indirect super-/subclasses of any class in the signature
     *         are added.
     */
    public abstract Set<OWLAxiom> extract(Set<OWLEntity> signature, int superClassLevel, int subClassLevel, OWLReasoner reasoner);


    /**
     * Returns an ontology that is a segment of the ontology associated with this segmenter.
     * @param signature the seed signature (set of entities) for the module
     * @param iri the URI for the module
     * @return the module, having the specified URI
     * @throws OWLOntologyChangeException   if adding axioms to the module fails
     * @throws OWLOntologyCreationException if the module cannot be created
     */

    public abstract OWLOntology extractAsOntology(Set<OWLEntity> signature, IRI iri) throws OWLOntologyCreationException;

    /**
     * Returns an ontology that is a segment of the ontology associated with this segmenter.
     * This segment is determined by a seed signature (set of entities),
     * which is the specified signature plus possibly all superclasses and/or subclasses of the classes therein.
     * Sub-/superclasses are determined using the specified reasoner.
     * @param signature the seed signature
     * @param iri the URI for the module
     * @param superClassLevel determines whether superclasses are added to the signature before segment extraction, see below for admissible values
     * @param subClassLevel determines whether subclasses are added to the signature before segment extraction, see below for admissible values
     * @param reasoner the reasoner to determine super-/subclasses
     * @return the segment as a set of axioms
     * @throws OWLOntologyChangeException   if adding axioms to the module fails
     * @throws OWLOntologyCreationException if the module cannot be created
     *                                      Meaning of the value of superClassLevel, subClassLevel:<br>
     *                                      Let this value be k. If k > 0, then all classes are included that are (direct or indirect) super-/subclasses of some class in signature,
     *                                      with a distance of at most k to this class in the class hierarchy computed by reasoner. If k = 0, then no
     *                                      super-/subclasses are added. If k < 0, then all direct and indirect super-/subclasses of any class in the signature
     *                                      are added.
     */
    public abstract OWLOntology extractAsOntology(Set<OWLEntity> signature, IRI iri, int superClassLevel, int subClassLevel, OWLReasoner reasoner) throws OWLOntologyCreationException;
}
