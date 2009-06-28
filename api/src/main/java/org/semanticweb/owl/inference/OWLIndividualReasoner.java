package org.semanticweb.owl.inference;

import org.semanticweb.owl.model.*;

import java.util.Map;
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
 * Date: 25-Oct-2006<br><br>
 */
public interface OWLIndividualReasoner extends OWLReasonerBase {


    Set<Set<OWLClass>> getTypes(OWLNamedIndividual individual, boolean direct) throws OWLReasonerException;


    Set<OWLNamedIndividual> getIndividuals(OWLClassExpression clsC, boolean direct) throws OWLReasonerException;


    Map<OWLObjectProperty, Set<OWLNamedIndividual>> getObjectPropertyRelationships(OWLNamedIndividual individual) throws OWLReasonerException;


    Map<OWLDataProperty, Set<OWLLiteral>> getDataPropertyRelationships(OWLNamedIndividual individual) throws OWLReasonerException;

    
    boolean hasType(OWLNamedIndividual individual, OWLClassExpression type, boolean direct) throws OWLReasonerException;


    boolean hasObjectPropertyRelationship(OWLNamedIndividual subject, OWLObjectPropertyExpression property, OWLNamedIndividual object) throws OWLReasonerException;


    boolean hasDataPropertyRelationship(OWLNamedIndividual subject, OWLDataPropertyExpression property, OWLLiteral object) throws OWLReasonerException;


    Set<OWLNamedIndividual> getRelatedIndividuals(OWLNamedIndividual subject, OWLObjectPropertyExpression property) throws OWLReasonerException;


    Set<OWLLiteral> getRelatedValues(OWLNamedIndividual subject, OWLDataPropertyExpression property) throws OWLReasonerException;


}
