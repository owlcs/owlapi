package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/*
 * Copyright (C) 2006, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


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
        if(!isAnnotated()) {
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
        for(int i = 0; i < individuals.size() - 1; i++) {
            for(int j = i + 1; j < individuals.size(); j++) {
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
        for(OWLIndividual ind : getIndividuals()) {
            if(ind.isAnonymous()) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof OWLDifferentIndividualsAxiom;
    }

    public Set<OWLSubClassOfAxiom> asOWLSubClassOfAxioms() {
        List<OWLClassExpression> nominalsList = new ArrayList<OWLClassExpression>();
        for(OWLIndividual individual : getIndividuals()) {
            nominalsList.add(getOWLDataFactory().getOWLObjectOneOf(individual));
        }
        Set<OWLSubClassOfAxiom> result = new HashSet<OWLSubClassOfAxiom>();
        for(int i = 0; i < nominalsList.size() - 1; i++) {
            for(int j = i + 1; j < nominalsList.size(); j++) {
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


    public AxiomType getAxiomType() {
        return AxiomType.DIFFERENT_INDIVIDUALS;
    }
}
