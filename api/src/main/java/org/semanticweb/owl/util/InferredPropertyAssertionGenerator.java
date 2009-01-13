package org.semanticweb.owl.util;

import org.semanticweb.owl.inference.OWLReasoner;
import org.semanticweb.owl.inference.OWLReasonerException;
import org.semanticweb.owl.model.*;

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
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public class InferredPropertyAssertionGenerator extends InferredIndividualAxiomGenerator<OWLPropertyAssertionAxiom> {


    protected void addAxioms(OWLIndividual entity, OWLReasoner reasoner, OWLDataFactory dataFactory, Set<OWLPropertyAssertionAxiom> result) throws
                                                                                                                           OWLReasonerException {
        Map<OWLObjectProperty, Set<OWLIndividual>> objectPropertyRels = reasoner.getObjectPropertyRelationships(entity);
        for(OWLObjectProperty prop : objectPropertyRels.keySet()) {
            for(OWLIndividual obj : objectPropertyRels.get(prop)) {
                result.add(dataFactory.getOWLObjectPropertyAssertionAxiom(entity, prop, obj));
            }
        }

        Map<OWLDataProperty, Set<OWLConstant>> dataPropertyRels = reasoner.getDataPropertyRelationships(entity);
        for(OWLDataProperty prop : dataPropertyRels.keySet()) {
            for(OWLConstant con : dataPropertyRels.get(prop)) {
                result.add(dataFactory.getOWLDataPropertyAssertionAxiom(entity, prop, con));
            }
        }
    }


    public String getLabel() {
        return "Property assertions (property values)";
    }
}
