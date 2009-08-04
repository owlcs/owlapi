package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.*;

import java.util.HashSet;
import java.util.Set;
/*
 * Copyright (C) 2008, University of Manchester
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
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 18-Jul-2008<br><br>
 */
public class OWLOntologyAccessorsTestCase extends AbstractOWLAPITestCase {


    public void testAccessors() throws Exception {
        OWLOntology ont = getOWLOntology("ont");
        OWLDataFactory df = getFactory();
        Set<OWLAxiom> logicalAxioms = new HashSet<OWLAxiom>();

        // Class axioms
        logicalAxioms.add(df.getOWLSubClassOfAxiom(getOWLClass("A"), getOWLClass("B")));
        logicalAxioms.add(df.getOWLEquivalentClassesAxiom(getOWLClass("C"), getOWLClass("D")));
        logicalAxioms.add(df.getOWLDisjointClassesAxiom(getOWLClass("D"), getOWLClass("E")));
        // Object property
        OWLObjectProperty propP = getOWLObjectProperty("p");
        logicalAxioms.add(df.getOWLSubObjectPropertyOfAxiom(propP, getOWLObjectProperty("q")));
        logicalAxioms.add(df.getOWLEquivalentObjectPropertiesAxiom(propP, getOWLObjectProperty("q")));
        logicalAxioms.add(df.getOWLDisjointObjectPropertiesAxiom(propP, getOWLObjectProperty("q")));
        logicalAxioms.add(df.getOWLObjectPropertyDomainAxiom(propP, getOWLClass("A")));
        logicalAxioms.add(df.getOWLObjectPropertyRangeAxiom(propP, getOWLClass("A")));
        logicalAxioms.add(df.getOWLObjectPropertyAssertionAxiom(propP, getOWLIndividual("a"), getOWLIndividual("b")));
        logicalAxioms.add(df.getOWLFunctionalObjectPropertyAxiom(propP));
        logicalAxioms.add(df.getOWLInverseFunctionalObjectPropertyAxiom(propP));
        logicalAxioms.add(df.getOWLSymmetricObjectPropertyAxiom(propP));
        logicalAxioms.add(df.getOWLTransitiveObjectPropertyAxiom(propP));
        logicalAxioms.add(df.getOWLReflexiveObjectPropertyAxiom(propP));
        logicalAxioms.add(df.getOWLIrreflexiveObjectPropertyAxiom(propP));
        logicalAxioms.add(df.getOWLAsymmetricObjectPropertyAxiom(propP));

        // Data property
        OWLDataProperty propD = getOWLDataProperty("d");
        logicalAxioms.add(df.getOWLSubDataPropertyOfAxiom(propD, getOWLDataProperty("q")));
        logicalAxioms.add(df.getOWLEquivalentDataPropertiesAxiom(propD, getOWLDataProperty("q")));
        logicalAxioms.add(df.getOWLDisjointDataPropertiesAxiom(propD, getOWLDataProperty("q")));
        logicalAxioms.add(df.getOWLDataPropertyDomainAxiom(propD, getOWLClass("A")));
        logicalAxioms.add(df.getOWLDataPropertyRangeAxiom(propD, df.getIntegerOWLDatatype()));
        logicalAxioms.add(df.getOWLDataPropertyAssertionAxiom(propD, getOWLIndividual("a"), df.getOWLTypedLiteral(3)));
        logicalAxioms.add(df.getOWLFunctionalDataPropertyAxiom(propD));


        getManager().addAxioms(ont, logicalAxioms);

        // Test that none of the accessors throw null pointer exceptions
        assertNotNull(ont.getAxioms());
        assertNotNull(ont.getLogicalAxioms());

        for (AxiomType type : AxiomType.AXIOM_TYPES) {
            assertNotNull(ont.getAxioms(type));
        }


    }


}
