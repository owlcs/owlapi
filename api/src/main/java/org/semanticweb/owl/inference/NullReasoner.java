package org.semanticweb.owl.inference;

import org.semanticweb.owl.model.*;

import java.util.Collections;
import java.util.Map;
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
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 02-Jun-2008<br><br>
 *
 * A reasoner that does nothing.  For use in the "null object"
 * pattern (http://en.wikipedia.org/wiki/Null_Object_pattern)
 */
public class NullReasoner implements OWLReasoner {

    public NullReasoner() {
    }


    /**
     * @return Always returns <code>true</code> in this implementation.
     * @throws OWLReasonerException
     */
    public boolean isConsistent(OWLOntology ontology) throws OWLReasonerException {
        return true;
    }


    public void loadOntologies(Set<OWLOntology> ontologies) throws OWLReasonerException {

    }


    /**
     * @return Always <code>true</code>
     * @throws OWLReasonerException
     */
    public boolean isClassified() throws OWLReasonerException {
        return true;
    }


    /**
     * Does nothing in this implementation
     * @throws OWLReasonerException
     */
    public void classify() throws OWLReasonerException {
    }


    /**
     * @return Always returns <code>true</code>
     * @throws OWLReasonerException
     */
    public boolean isRealised() throws OWLReasonerException {
        return true;
    }


    /**
     * Does nothing in this implementation.
     * @throws OWLReasonerException
     */
    public void realise() throws OWLReasonerException {
    }

    /**
     * @return Always returns <code>true</code> in this implementation
     * @throws OWLReasonerException
     */
    public boolean isDefined(OWLClass cls) throws OWLReasonerException {
        return true;
    }

    /**
     * @return Always returns <code>true</code>
     * @throws OWLReasonerException
     */
    public boolean isDefined(OWLObjectProperty prop) throws OWLReasonerException {
        return true;
    }

    /**
     * @return Always returns <code>true</code> in this implementation
     * @throws OWLReasonerException
     */
    public boolean isDefined(OWLDataProperty prop) throws OWLReasonerException {
        return true;
    }

    /**
     * @return Always returns <code>true</code> in this implementation
     * @throws OWLReasonerException
     */
    public boolean isDefined(OWLIndividual ind) throws OWLReasonerException {
        return true;
    }


    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<OWLOntology> getLoadedOntologies() {
        return Collections.emptySet();
    }


    /**
     * Does nothing in this implementation.
     * @throws OWLReasonerException
     */
    public void unloadOntologies(Set<OWLOntology> ontologies) throws OWLReasonerException {
    }


    /**
     * Does nothing in this implementation.
     * @throws OWLReasonerException
     */
    public void clearOntologies() throws OWLReasonerException {
    }


    public void dispose() throws OWLReasonerException {
    }

    /**
     * @return Always returns <code>false</code> in this implementation
     * @throws OWLReasonerException
     */
    public boolean isSubClassOf(OWLDescription clsC, OWLDescription clsD) throws OWLReasonerException {
        return false;
    }

    /**
     * @return Always returns <code>false</code> in this implementation
     * @throws OWLReasonerException
     */
    public boolean isEquivalentClass(OWLDescription clsC, OWLDescription clsD) throws OWLReasonerException {
        return false;
    }


    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<Set<OWLClass>> getSuperClasses(OWLDescription clsC) throws OWLReasonerException {
        return Collections.emptySet();
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<Set<OWLClass>> getAncestorClasses(OWLDescription clsC) throws OWLReasonerException {
        return Collections.emptySet();
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<Set<OWLClass>> getSubClasses(OWLDescription clsC) throws OWLReasonerException {
        return Collections.emptySet();
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<Set<OWLClass>> getDescendantClasses(OWLDescription clsC) throws OWLReasonerException {
        return Collections.emptySet();
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<OWLClass> getEquivalentClasses(OWLDescription clsC) throws OWLReasonerException {
        return Collections.emptySet();
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<OWLClass> getInconsistentClasses() throws OWLReasonerException {
        return Collections.emptySet();
    }


    /**
     * @return Always returns <code>true</code> in this implementation
     * @throws OWLReasonerException
     */
    public boolean isSatisfiable(OWLDescription description) throws OWLReasonerException {
        return true;
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<Set<OWLClass>> getTypes(OWLIndividual individual, boolean direct) throws OWLReasonerException {
        return Collections.emptySet();
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<OWLIndividual> getIndividuals(OWLDescription clsC, boolean direct) throws OWLReasonerException {
        return Collections.emptySet();
    }

    /**
     * @return Always returns an empty map in this implementation.
     * @throws OWLReasonerException
     */
    public Map<OWLObjectProperty, Set<OWLIndividual>> getObjectPropertyRelationships(OWLIndividual individual) throws
                                                                                                               OWLReasonerException {
        return Collections.emptyMap();
    }

    /**
     * @return Always returns an empty map in this implementation.
     * @throws OWLReasonerException
     */
    public Map<OWLDataProperty, Set<OWLConstant>> getDataPropertyRelationships(OWLIndividual individual) throws
                                                                                                         OWLReasonerException {
        return Collections.emptyMap();
    }


    /**
     * @return Always returns <code>false</code> in this implementation.
     * @throws OWLReasonerException
     */
    public boolean hasType(OWLIndividual individual, OWLDescription type, boolean direct) throws OWLReasonerException {
        return false;
    }

    /**
     * @return Always returns <code>false</code> in this implementation.
     * @throws OWLReasonerException
     */
    public boolean hasObjectPropertyRelationship(OWLIndividual subject, OWLObjectPropertyExpression property,
                                                 OWLIndividual object) throws OWLReasonerException {
        return false;
    }

    /**
     * @return Always returns <code>false</code> in this implementation.
     * @throws OWLReasonerException
     */
    public boolean hasDataPropertyRelationship(OWLIndividual subject, OWLDataPropertyExpression property, OWLConstant object) throws
                                                                                                                              OWLReasonerException {
        return false;
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<OWLIndividual> getRelatedIndividuals(OWLIndividual subject, OWLObjectPropertyExpression property) throws
                                                                                                                 OWLReasonerException {
        return Collections.emptySet();
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<OWLConstant> getRelatedValues(OWLIndividual subject, OWLDataPropertyExpression property) throws
                                                                                                        OWLReasonerException {
        return Collections.emptySet();
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<Set<OWLObjectProperty>> getSuperProperties(OWLObjectProperty property) throws OWLReasonerException {
        return Collections.emptySet();
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<Set<OWLObjectProperty>> getSubProperties(OWLObjectProperty property) throws OWLReasonerException {
        return Collections.emptySet();
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<Set<OWLObjectProperty>> getAncestorProperties(OWLObjectProperty property) throws OWLReasonerException {
        return Collections.emptySet();
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<Set<OWLObjectProperty>> getDescendantProperties(OWLObjectProperty property) throws OWLReasonerException {
        return Collections.emptySet();
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<Set<OWLObjectProperty>> getInverseProperties(OWLObjectProperty property) throws OWLReasonerException {
        return Collections.emptySet();
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<OWLObjectProperty> getEquivalentProperties(OWLObjectProperty property) throws OWLReasonerException {
        return Collections.emptySet();
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<Set<OWLDescription>> getDomains(OWLObjectProperty property) throws OWLReasonerException {
        return Collections.emptySet();
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<OWLDescription> getRanges(OWLObjectProperty property) throws OWLReasonerException {
        return Collections.emptySet();
    }

    /**
     * @return Always returns <code>false</code> in this implementation.
     * @throws OWLReasonerException
     */
    public boolean isFunctional(OWLObjectProperty property) throws OWLReasonerException {
        return false;
    }

    /**
     * @return Always returns <code>false</code> in this implementation.
     * @throws OWLReasonerException
     */
    public boolean isInverseFunctional(OWLObjectProperty property) throws OWLReasonerException {
        return false;
    }

    /**
     * @return Always returns <code>false</code> in this implementation.
     * @throws OWLReasonerException
     */
    public boolean isSymmetric(OWLObjectProperty property) throws OWLReasonerException {
        return false;
    }

    /**
     * @return Always returns <code>false</code> in this implementation.
     * @throws OWLReasonerException
     */
    public boolean isTransitive(OWLObjectProperty property) throws OWLReasonerException {
        return false;
    }

    /**
     * @return Always returns <code>false</code> in this implementation.
     * @throws OWLReasonerException
     */
    public boolean isReflexive(OWLObjectProperty property) throws OWLReasonerException {
        return false;
    }

    /**
     * @return Always returns <code>false</code> in this implementation.
     * @throws OWLReasonerException
     */
    public boolean isIrreflexive(OWLObjectProperty property) throws OWLReasonerException {
        return false;
    }

    /**
     * @return Always returns <code>false</code> in this implementation.
     * @throws OWLReasonerException
     */
    public boolean isAntiSymmetric(OWLObjectProperty property) throws OWLReasonerException {
        return false;
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<Set<OWLDataProperty>> getSuperProperties(OWLDataProperty property) throws OWLReasonerException {
        return Collections.emptySet();
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<Set<OWLDataProperty>> getSubProperties(OWLDataProperty property) throws OWLReasonerException {
        return Collections.emptySet();
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<Set<OWLDataProperty>> getAncestorProperties(OWLDataProperty property) throws OWLReasonerException {
        return Collections.emptySet();
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<Set<OWLDataProperty>> getDescendantProperties(OWLDataProperty property) throws OWLReasonerException {
        return Collections.emptySet();
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<OWLDataProperty> getEquivalentProperties(OWLDataProperty property) throws OWLReasonerException {
        return Collections.emptySet();
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<Set<OWLDescription>> getDomains(OWLDataProperty property) throws OWLReasonerException {
        return Collections.emptySet();
    }

    /**
     * @return Always returns an empty set in this implementation.
     * @throws OWLReasonerException
     */
    public Set<OWLDataRange> getRanges(OWLDataProperty property) throws OWLReasonerException {
        return Collections.emptySet();
    }


    /**
     * @return Always returns <code>false</code> in this implementation.
     * @throws OWLReasonerException
     */
    public boolean isFunctional(OWLDataProperty property) throws OWLReasonerException {
        return false;
    }
}
