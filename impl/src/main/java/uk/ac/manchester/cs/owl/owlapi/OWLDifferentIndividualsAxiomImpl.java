package uk.ac.manchester.cs.owl.owlapi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLDifferentIndividualsAxiomImpl extends OWLNaryIndividualAxiomImpl implements OWLDifferentIndividualsAxiom {

    public OWLDifferentIndividualsAxiomImpl(OWLDataFactory dataFactory, Set<? extends OWLIndividual> individuals, Set<? extends OWLAnnotation> annotations) {
        super(dataFactory, individuals, annotations);
    }

    public OWLDifferentIndividualsAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLDifferentIndividualsAxiom(getIndividuals());
    }

    public OWLDifferentIndividualsAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLDifferentIndividualsAxiom(getIndividuals(), mergeAnnos(annotations));
    }

    public Set<OWLDifferentIndividualsAxiom> asPairwiseAxioms() {
        List<OWLIndividual> individuals = getIndividualsAsList();
        Set<OWLDifferentIndividualsAxiom> result = new HashSet<OWLDifferentIndividualsAxiom>();
        for (int i = 0; i < individuals.size() - 1; i++) {
            for (int j = i + 1; j < individuals.size(); j++) {
                OWLIndividual indI = individuals.get(i);
                OWLIndividual indJ = individuals.get(j);
                result.add(getOWLDataFactory().getOWLDifferentIndividualsAxiom(indI, indJ));
            }
        }
        return result;
    }

    /**
     * Determines whether this axiom contains anonymous individuals.  Anonymous individuals are not allowed in
     * different individuals axioms.
     * @return <code>true</code> if this axioms contains anonymous individual axioms
     */
    public boolean containsAnonymousIndividuals() {
        for (OWLIndividual ind : getIndividuals()) {
            if (ind.isAnonymous()) {
                return true;
            }
        }
        return false;
    }

    @Override
	public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof OWLDifferentIndividualsAxiom;
    }

    public Set<OWLSubClassOfAxiom> asOWLSubClassOfAxioms() {
        List<OWLClassExpression> nominalsList = new ArrayList<OWLClassExpression>();
        for (OWLIndividual individual : getIndividuals()) {
            nominalsList.add(getOWLDataFactory().getOWLObjectOneOf(individual));
        }
        Set<OWLSubClassOfAxiom> result = new HashSet<OWLSubClassOfAxiom>();
        for (int i = 0; i < nominalsList.size() - 1; i++) {
            for (int j = i + 1; j < nominalsList.size(); j++) {
                OWLClassExpression ceI = nominalsList.get(i);
                OWLClassExpression ceJ = nominalsList.get(j).getObjectComplementOf();
                result.add(getOWLDataFactory().getOWLSubClassOfAxiom(ceI, ceJ));
            }
        }
        return result;
    }

    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }


    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public AxiomType<?> getAxiomType() {
        return AxiomType.DIFFERENT_INDIVIDUALS;
    }
}
