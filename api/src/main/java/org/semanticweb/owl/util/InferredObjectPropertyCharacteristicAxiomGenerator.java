package org.semanticweb.owl.util;

import org.semanticweb.owl.inference.OWLReasoner;
import org.semanticweb.owl.inference.OWLReasonerException;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLObjectPropertyCharacteristicAxiom;

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


    protected void addAxioms(OWLObjectProperty entity, OWLReasoner reasoner, OWLDataFactory dataFactory, Set<OWLObjectPropertyCharacteristicAxiom> result) throws
                                                                                                                               OWLReasonerException {
        if(reasoner.isFunctional(entity)) {
            result.add(dataFactory.getOWLFunctionalObjectPropertyAxiom(entity));
        }
        if(reasoner.isInverseFunctional(entity)) {
            result.add(dataFactory.getOWLInverseFunctionalObjectPropertyAxiom(entity));
        }
        if(reasoner.isSymmetric(entity)) {
            result.add(dataFactory.getOWLSymmetricObjectPropertyAxiom(entity));
        }
        if(reasoner.isTransitive(entity)) {
            result.add(dataFactory.getOWLTransitiveObjectPropertyAxiom(entity));
        }
        if(reasoner.isReflexive(entity)) {
            result.add(dataFactory.getOWLReflexiveObjectPropertyAxiom(entity));
        }
        if(reasoner.isIrreflexive(entity)) {
            result.add(dataFactory.getOWLIrreflexiveObjectPropertyAxiom(entity));
        }
        if(reasoner.isAntiSymmetric(entity)) {
            result.add(dataFactory.getOWLAntiSymmetricObjectPropertyAxiom(entity));
        }
    }


    public String getLabel() {
        return "Object property characteristics";
    }
}
