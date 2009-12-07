package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyCharacteristicAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

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
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public class InferredObjectPropertyCharacteristicAxiomGenerator extends InferredObjectPropertyAxiomGenerator<OWLObjectPropertyCharacteristicAxiom> {


    protected void addAxioms(OWLObjectProperty entity, OWLReasoner reasoner, OWLDataFactory dataFactory, Set<OWLObjectPropertyCharacteristicAxiom> result) {
        addIfEntailed(dataFactory.getOWLFunctionalObjectPropertyAxiom(entity), reasoner, result);
        addIfEntailed(dataFactory.getOWLInverseFunctionalObjectPropertyAxiom(entity), reasoner, result);
        addIfEntailed(dataFactory.getOWLSymmetricObjectPropertyAxiom(entity), reasoner, result);
        addIfEntailed(dataFactory.getOWLAsymmetricObjectPropertyAxiom(entity), reasoner, result);
        addIfEntailed(dataFactory.getOWLTransitiveObjectPropertyAxiom(entity), reasoner, result);
        addIfEntailed(dataFactory.getOWLReflexiveObjectPropertyAxiom(entity), reasoner, result);
        addIfEntailed(dataFactory.getOWLIrreflexiveObjectPropertyAxiom(entity), reasoner, result);
    }

    protected void addIfEntailed(OWLObjectPropertyCharacteristicAxiom axiom, OWLReasoner reasoner, Set<OWLObjectPropertyCharacteristicAxiom> result) {
        if(reasoner.isEntailmentCheckingSupported(axiom.getAxiomType()) && reasoner.isEntailed(axiom)) {
            result.add(axiom);
        }
    }

    public String getLabel() {
        return "Object property characteristics";
    }
}
