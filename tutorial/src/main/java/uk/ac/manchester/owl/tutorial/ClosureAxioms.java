package uk.ac.manchester.owl.tutorial;

import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLClassAxiom;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLException;
import org.semanticweb.owl.model.OWLObjectPropertyExpression;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.semanticweb.owl.model.OWLSubClassAxiom;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * Copyright (C) 2007, University of Manchester
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
 * <p>This class demonstrates some aspects of the OWL API. Given a class in an
 * ontology, it will determine the subclass axioms that define the class. For
 * each of these, if the superclass is a conjunction of existential
 * restrictions, then an additional subclass axiom will be added to the
 * ontology, "closing" the restrictions.</p>
 * 
 * Author: Sean Bechhofer<br>
 * The University Of Manchester<br>
 * Information Management Group<br>
 * Date: 24-April-2007<br>
 * <br>
 */
public class ClosureAxioms {

    private OWLOntologyManager manager;

    private OWLOntology ontology;

    private OWLDataFactory factory;

    public ClosureAxioms(OWLOntologyManager manager, OWLOntology ontology)
            throws OWLException {
        this.manager = manager;
        this.ontology = ontology;
        this.factory = manager.getOWLDataFactory();

    }

    public void addClosureAxioms(OWLClass clazz) throws OWLException {
        /* Get the class axioms */
        Set<OWLClassAxiom> axioms = ontology.getClassAxioms();

        /* Collect those that assert superclasses of the class */
        SubClassCollector collector = new SubClassCollector(clazz);

        for (OWLClassAxiom axiom : axioms) {
            axiom.accept(collector);
        }

        Map<OWLObjectPropertyExpression, Set<OWLDescription>> restrictions = new HashMap<OWLObjectPropertyExpression, Set<OWLDescription>>();

        /* For each axiom.... */
        for (OWLSubClassAxiom axiom : collector.getAxioms()) {
            /* Get the superclass */
            OWLDescription superClass = axiom.getSuperClass();

            /* Collect any existentials */
            ExistentialCollector ec = new ExistentialCollector(restrictions);
            superClass.accept(ec);
        }

        /* For any existentials.... */
        for (OWLObjectPropertyExpression prop : restrictions.keySet()) {
            System.out.println("prop: " + prop);
            Set<OWLDescription> fillers = restrictions.get(prop);
            for (OWLDescription filler : fillers) {
                System.out.println("------> " + filler);
            }

            /* Create a union of the fillers */
            OWLDescription union = factory.getOWLObjectUnionOf(fillers);

            /* Create a universal restriction */
            OWLDescription universal = factory.getOWLObjectAllRestriction(prop,
                    union);

            /* Create a new axiom */
            OWLAxiom newAxiom = factory.getOWLSubClassAxiom(clazz, universal);

            /* Now add the axiom to the ontology */
            AddAxiom addAxiom = new AddAxiom(ontology, newAxiom);
            /* Use the manager to apply the change */
            manager.applyChange(addAxiom);

        }

    }

}