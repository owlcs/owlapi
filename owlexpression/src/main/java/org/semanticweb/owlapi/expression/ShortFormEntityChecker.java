package org.semanticweb.owlapi.expression;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;

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
 * Date: 28-Nov-2007<br><br>
 * <p/>
 * An entity checker that maps from string to entities using a bidirectional
 * short form provider.
 */
public class ShortFormEntityChecker implements OWLEntityChecker {

    private BidirectionalShortFormProvider shortFormProvider;


    /**
     * Creates a short form entity checker, which uses the specified bidirectional
     * short form provider to map entity name strings to entities.
     *
     * @param shortFormProvider The BidirectionalShortFormProvider that should be
     *                          used to perform the required mapping.
     */
    public ShortFormEntityChecker(BidirectionalShortFormProvider shortFormProvider) {
        this.shortFormProvider = shortFormProvider;
    }

    public OWLClass getOWLClass(String name) {
        for (OWLEntity ent : shortFormProvider.getEntities(name)) {
            if (ent.isOWLClass()) {
                return ent.asOWLClass();
            }
        }
        return null;
    }


    public OWLDataProperty getOWLDataProperty(String name) {
        for (OWLEntity ent : shortFormProvider.getEntities(name)) {
            if (ent.isOWLDataProperty()) {
                return ent.asOWLDataProperty();
            }
        }
        return null;
    }


    public OWLDatatype getOWLDatatype(String name) {
        for (OWLEntity ent : shortFormProvider.getEntities(name)) {
            if (ent.isOWLDatatype()) {
                return ent.asOWLDatatype();
            }
        }
        return null;
    }


    public OWLNamedIndividual getOWLIndividual(String name) {
        for (OWLEntity ent : shortFormProvider.getEntities(name)) {
            if (ent.isOWLNamedIndividual()) {
                return ent.asOWLNamedIndividual();
            }
        }
        return null;
    }


    public OWLObjectProperty getOWLObjectProperty(String name) {
        for (OWLEntity ent : shortFormProvider.getEntities(name)) {
            if (ent.isOWLObjectProperty()) {
                return ent.asOWLObjectProperty();
            }
        }
        return null;
    }

    public OWLAnnotationProperty getOWLAnnotationProperty(String name) {
        for(OWLEntity ent : shortFormProvider.getEntities(name)) {
            if(ent.isOWLAnnotationProperty()) {
                return ent.asOWLAnnotationProperty();
            }
        }
        return null;
    }
}

