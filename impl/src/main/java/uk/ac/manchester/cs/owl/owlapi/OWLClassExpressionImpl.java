package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;

import java.util.*;
/*
 * Copyright (C) 2009, University of Manchester
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
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 29-Dec-2009
 */
public abstract class OWLClassExpressionImpl extends OWLObjectImpl implements OWLClassExpression {

    protected OWLClassExpressionImpl(OWLDataFactory dataFactory) {
        super(dataFactory);
    }

    public void addSubClassOf(OWLOntology ontology, OWLClassExpression classExpression) {
        ontology.getOWLOntologyManager().addAxiom(ontology, getOWLDataFactory().getOWLSubClassOfAxiom(this, classExpression));
    }

    public void addSubClassOf(OWLOntology ontology, OWLClassExpression classExpression, OWLOntologyChangeBuilder changeBuilder) {
        addSubClassOf(ontology, classExpression, Collections.<OWLAnnotation>emptySet(), changeBuilder);
    }

    public void addSubClassOf(OWLOntology ontology, OWLClassExpression classExpression, Set<? extends OWLAnnotation> annotations, OWLOntologyChangeBuilder changeBuilder) {
        changeBuilder.add(new AddAxiom(ontology, getOWLDataFactory().getOWLSubClassOfAxiom(this, classExpression, annotations)));
    }

    public void removeSubClassOf(OWLOntology ontology, boolean fromImportsClosure, OWLClassExpression classExpression) {
        OWLOntologyChangeBuilder cb = new OWLOntologyChangeBuilder(ontology.getOWLOntologyManager());
        removeSubClassOf(ontology, fromImportsClosure, classExpression, cb);
        cb.applyChanges();
    }

    public void removeSubClassOf(OWLOntology ontology, boolean fromImportsClosure, OWLClassExpression classExpression, OWLOntologyChangeBuilder changeBuilder) {
        Collection<OWLOntology> ontologies;
        if (fromImportsClosure) {
            ontologies = ontology.getImportsClosure();
        }
        else {
            ontologies = Collections.singleton(ontology);
        }
        for (OWLOntology ont : ontologies) {
            if (isAnonymous()) {
                for (OWLClassAxiom ax : ont.getGeneralClassAxioms()) {
                    if (ax.isOfType(AxiomType.SUBCLASS_OF)) {
                        OWLSubClassOfAxiom sca = (OWLSubClassOfAxiom) ax;
                        if (sca.getSubClass().equals(this) && sca.getSuperClass().equals(this)) {
                            changeBuilder.add(new RemoveAxiom(ont, ax));
                        }
                    }
                }
            }
            else {
                for (OWLSubClassOfAxiom ax : ont.getSubClassAxiomsForSubClass(this.asOWLClass())) {
                    if (ax.getSuperClass().equals(classExpression)) {
                        changeBuilder.add(new RemoveAxiom(ont, ax));
                    }
                }
            }
        }
    }

    public void addEquivalentClasses(OWLOntology ontology, OWLClassExpression classExpression) {
        OWLOntologyChangeBuilder cb = new OWLOntologyChangeBuilder(ontology.getOWLOntologyManager());
        addEquivalentClasses(ontology, classExpression, cb);
        cb.applyChanges();
    }

    public void addEquivalentClasses(OWLOntology ontology, OWLClassExpression classExpression, OWLOntologyChangeBuilder changeBuilder) {
        changeBuilder.add(new AddAxiom(ontology, getOWLDataFactory().getOWLEquivalentClassesAxiom(this, classExpression)));
    }

    public void removeEquivalentClasses(OWLOntology ontology, boolean fromImportsClosure, OWLClassExpression classExpression, OWLOntologyChangeBuilder changeBuilder) {
        Collection<OWLOntology> ontologies;
        if (fromImportsClosure) {
            ontologies = ontology.getImportsClosure();
        }
        else {
            ontologies = Collections.singleton(ontology);
        }
        for (OWLOntology ont : ontologies) {
            if (isAnonymous()) {
                for (OWLClassAxiom ax : ont.getGeneralClassAxioms()) {
                    if (ax instanceof OWLEquivalentClassesAxiom) {
                        OWLEquivalentClassesAxiom eca = (OWLEquivalentClassesAxiom) ax;
                        Set<OWLClassExpression> clses = eca.getClassExpressions();
                        if (clses.contains(this) && clses.contains(classExpression)) {
                            removeClassExpressionFromEquivalentClassesAxiom(ont, classExpression, eca, changeBuilder);
                        }
                    }
                }
            }
            else {
                for(OWLEquivalentClassesAxiom eca : ont.getEquivalentClassesAxioms(this.asOWLClass())) {
                    removeClassExpressionFromEquivalentClassesAxiom(ont, classExpression, eca, changeBuilder);
                }
            }
        }
    }

    private void removeClassExpressionFromEquivalentClassesAxiom(OWLOntology ont, OWLClassExpression classExpression, OWLEquivalentClassesAxiom eca, OWLOntologyChangeBuilder changeBuilder) {
        Set<OWLClassExpression> oldClses = eca.getClassExpressions();
        if(oldClses.size() <= 2) {
            changeBuilder.add(new RemoveAxiom(ont, eca));
        }
        else {
            changeBuilder.add(new RemoveAxiom(ont, eca));
            Set<OWLClassExpression> newClses = new HashSet<OWLClassExpression>(oldClses);
            newClses.remove(classExpression);
            OWLAxiom newAx = getOWLDataFactory().getOWLEquivalentClassesAxiom(newClses, eca.getAnnotations());
            changeBuilder.add(new AddAxiom(ont, newAx));
        }
    }

    public void addDisjointClasses(OWLOntology ontology, OWLClassExpression classExpression, OWLOntologyChangeBuilder changeBuilder) {
        changeBuilder.add(new AddAxiom(ontology, getOWLDataFactory().getOWLDisjointClassesAxiom(this, classExpression)));
    }

    public void addClassAssertion(OWLOntology ontology, OWLIndividual individual, OWLOntologyChangeBuilder changeBuilder) {
        changeBuilder.add(new AddAxiom(ontology, getOWLDataFactory().getOWLClassAssertionAxiom(this, individual)));
    }

    public void removeClassAssertion(OWLOntology ontology, boolean fromImportsClosure, OWLIndividual individual, OWLOntologyChangeBuilder changeBuilder) {
        Collection<OWLOntology> ontologies;
        if(fromImportsClosure) {
            ontologies = ontology.getImportsClosure();
        }
        else {
            ontologies = Collections.singleton(ontology);
        }
        for(OWLOntology ont : ontologies) {
            for(OWLClassAssertionAxiom ax : ont.getClassAssertionAxioms(individual)) {
                if(ax.getClassExpression().equals(this)) {
                    changeBuilder.add(new RemoveAxiom(ont, ax));
                }
            }
        }
    }
}
