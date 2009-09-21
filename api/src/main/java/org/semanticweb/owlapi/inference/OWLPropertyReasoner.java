package org.semanticweb.owlapi.inference;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLObjectProperty;

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
public interface OWLPropertyReasoner extends OWLReasonerBase {

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Object properties
    //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    public Set<Set<OWLObjectProperty>> getSuperProperties(OWLObjectProperty property) throws OWLReasonerException;


    public Set<Set<OWLObjectProperty>> getSubProperties(OWLObjectProperty property) throws OWLReasonerException;


    public Set<Set<OWLObjectProperty>> getAncestorProperties(OWLObjectProperty property) throws OWLReasonerException;


    public Set<Set<OWLObjectProperty>> getDescendantProperties(OWLObjectProperty property) throws OWLReasonerException;


    public Set<Set<OWLObjectProperty>> getInverseProperties(OWLObjectProperty property) throws OWLReasonerException;


    public Set<OWLObjectProperty> getEquivalentProperties(OWLObjectProperty property) throws OWLReasonerException;


    /**
     * Gets the domains of a particular property.  A domain class, A, of a property, P,
     * is a named class such that A is an ancestor of \exists p Top ("p some Thing"). 
     * @param property The property whose domains are to be retrieved.
     * @return The domains of the property.  A set of sets of (named) equivalence classes.
     * @throws OWLReasonerException If there is a problem with the reasoner.
     */
    public Set<Set<OWLClassExpression>> getDomains(OWLObjectProperty property) throws OWLReasonerException;


    public Set<OWLClassExpression> getRanges(OWLObjectProperty property) throws OWLReasonerException;


    public boolean isFunctional(OWLObjectProperty property) throws OWLReasonerException;


    public boolean isInverseFunctional(OWLObjectProperty property) throws OWLReasonerException;


    public boolean isSymmetric(OWLObjectProperty property) throws OWLReasonerException;


    public boolean isTransitive(OWLObjectProperty property) throws OWLReasonerException;


    public boolean isReflexive(OWLObjectProperty property) throws OWLReasonerException;


    public boolean isIrreflexive(OWLObjectProperty property) throws OWLReasonerException;


    public boolean isAsymmetric(OWLObjectProperty property) throws OWLReasonerException;


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Data properties
    //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    public Set<Set<OWLDataProperty>> getSuperProperties(OWLDataProperty property) throws OWLReasonerException;


    public Set<Set<OWLDataProperty>> getSubProperties(OWLDataProperty property) throws OWLReasonerException;


    public Set<Set<OWLDataProperty>> getAncestorProperties(OWLDataProperty property) throws OWLReasonerException;


    public Set<Set<OWLDataProperty>> getDescendantProperties(OWLDataProperty property) throws OWLReasonerException;


    public Set<OWLDataProperty> getEquivalentProperties(OWLDataProperty property) throws OWLReasonerException;


    public Set<Set<OWLClassExpression>> getDomains(OWLDataProperty property) throws OWLReasonerException;


    public Set<OWLDataRange> getRanges(OWLDataProperty property) throws OWLReasonerException;


    public boolean isFunctional(OWLDataProperty property) throws OWLReasonerException;
}
