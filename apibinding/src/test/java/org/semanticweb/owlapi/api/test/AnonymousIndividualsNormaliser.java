package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.util.OWLObjectDuplicator;

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
 * Date: 02-Jul-2009
 */
public class AnonymousIndividualsNormaliser extends OWLObjectDuplicator {

    private Map<OWLAnonymousIndividual, OWLAnonymousIndividual> renamingMap = new HashMap<OWLAnonymousIndividual, OWLAnonymousIndividual>();

    private OWLDataFactory dataFactory;

    private int counter = 0;

    /**
     * Creates an object duplicator that duplicates objects using the specified
     * data factory.
     * @param dataFactory The data factory to be used for the duplication.
     */
    public AnonymousIndividualsNormaliser(OWLDataFactory dataFactory) {
        super(dataFactory);
        this.dataFactory = dataFactory;
    }

    public Set<OWLAxiom> getNormalisedAxioms(Set<OWLAxiom> axioms) {
        List<OWLAxiom> axiomsList = new ArrayList<OWLAxiom>(axioms);
        Collections.sort(axiomsList);
        Set<OWLAxiom> normalised = new HashSet<OWLAxiom>();
        for(OWLAxiom ax : axiomsList) {
            OWLAxiom dup = duplicateObject(ax);
            normalised.add(dup);
        }
        return normalised;
    }

    public void visit(OWLAnonymousIndividual individual) {
        OWLAnonymousIndividual ind = renamingMap.get(individual);
        if(ind == null) {
            counter++;
            ind = dataFactory.getOWLAnonymousIndividual("anon-ind-" + counter);
            renamingMap.put(individual, ind);
        }
        setLastObject(ind);
    }
}
