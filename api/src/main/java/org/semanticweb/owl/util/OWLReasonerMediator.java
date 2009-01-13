package org.semanticweb.owl.util;

import org.semanticweb.owl.inference.OWLReasoner;
import org.semanticweb.owl.inference.OWLReasonerException;
import org.semanticweb.owl.inference.UndefinedEntityException;
import org.semanticweb.owl.model.*;

import java.util.Collections;
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
 * Date: 27-Apr-2007<br><br>
 * <p/>
 * Acts as mediator with an <code>OWLReasoner</code>.  The mediator
 * provides several useful layers of client implementation functionality and
 * insulation on top of basic reasoner functionality.  For example, autoloading
 * and unloading of imports,  silent undefined entity handling etc.
 */
public class OWLReasonerMediator implements OWLReasoner {

    public static final boolean SILENT_UNDEFINED_ENTITY_HANDLING_DEFAULT_VALUE = false;

    /**
     * The real reasoner which does the hard work.
     */
    private OWLReasoner kernel;

    /**
     * Determines how undefined entities are handled.
     * If set to <code>true</code>, then undefined entities will
     * not cause an <code>UndefinedEntityException</code> to be thrown.
     */
    private boolean silentUndefinedEntityHandling;

    /**
     * Determines the answer when asking if an undefined class is satisfiable.
     */
    private boolean undefinedClassesSatisfiable;


    private boolean entitiesDefined;


    public OWLReasonerMediator(OWLReasoner kernel) {
        this.kernel = kernel;
        silentUndefinedEntityHandling = SILENT_UNDEFINED_ENTITY_HANDLING_DEFAULT_VALUE;
        undefinedClassesSatisfiable = true;
    }


    /**
     * Gets the real reasoner that this mediator wraps
     */
    public OWLReasoner getKernel() {
        return kernel;
    }


    /**
     * Determines if undefined entities are handled silently.  An undefined
     * entity is a class, property or individual which the reasoner does not
     * know about.  If asks are performed on undefined entities then there are
     * two possibilities: 1) An <code>UndefinedEntityException</code> is thrown,
     * or 2) The undefined entity is handled silently and a "null" response is
     * returned.
     * <p/>
     * Generally speaking, if silent undefined entity handling is enabled then all asks
     * operations which return <code>Set</code>s and <code>Map</code>s will return
     * empty <code>Set</code>s and empty <code>Map</code>s if the asks request
     * references an undefined entity.  For example, if <code>getSubClasses</code>
     * asks for the subclasses of an undefined entity then an empty <code>Set</code>
     * will be returned.  Most methods which have boolean return values will return
     * <code>false</code>, for example <code>isType</code> will return false if the
     * individual is undefined, or the class description which is the type references
     * an undefined entity.  The exception to this is the <code>isSatisfiable</code>
     * asks request which will return the value set by <code>setUndefinedClassesSatisfiable</code>.
     * @return <code>true</code> if silent undefined entity handling is enabled, otherwise
     *         <code>false</code>.  If undefined entity handling is not enabled then asks
     *         requests that reference undefined entities will most likely throw
     *         <code>UndefinedEntityException</code>s.
     */
    public boolean isSilentUndefinedEntityHandlingEnabled() {
        return silentUndefinedEntityHandling;
    }


    /**
     * Sets the undefined entity handling behaviour.
     * @param silentUndefinedEntityHandling <code>true</code> if undefined entities
     *                                      should be handled silently, otherwise <code>false</code>.
     */
    public void setSilentUndefinedEntityHandling(boolean silentUndefinedEntityHandling) {
        this.silentUndefinedEntityHandling = silentUndefinedEntityHandling;
    }


    /**
     * If an <code>isSatisfiable</code> ask is performed on an undefined class
     * then the return value of this method will be equal to the return value
     * of the asks request.
     * @return <code>true</code> if undefined classes should be regarded as being
     *         satisfiable, <code>false</code> if undefined classes should be regarded as
     *         being unsatisfiable.
     */
    public boolean isUndefinedClassesSatisfiable() {
        return undefinedClassesSatisfiable;
    }


    public void setUndefinedClassesSatisfiable(boolean b) {
        this.undefinedClassesSatisfiable = b;
    }

    private OWLEntityCollector collector = new OWLEntityCollector();

    public boolean checkDefined(OWLDescription description) throws OWLReasonerException {
        if (description.isAnonymous()) {
            collector.reset();
            description.accept(collector);
            for(OWLEntity entity : collector.getObjects()) {
                if(entity instanceof OWLClass) {
                    if(!isDefined((OWLClass) entity)) {
                        processUndefinedEntity(entity);
                        return false;
                    }
                }
                else if(entity instanceof OWLObjectProperty) {
                    if(!isDefined((OWLObjectProperty) entity)) {
                        processUndefinedEntity(entity);
                        return false;
                    }

                }
                else if(entity instanceof OWLDataProperty) {
                    if(!isDefined((OWLDataProperty) entity)) {
                        processUndefinedEntity(entity);
                        return false;
                    }

                }
                else if(entity instanceof OWLIndividual) {
                    if(!isDefined((OWLIndividual) entity)) {
                        processUndefinedEntity(entity);
                        return false;
                    }

                }
            }
            return true;
        }
        else {
            if (isDefined(description.asOWLClass())) {
                return true;
            }
            else {
                processUndefinedEntity(description.asOWLClass());
                return false;
            }
        }
    }


    public boolean checkDefined(OWLObjectPropertyExpression prop) throws OWLReasonerException {
        if (prop.isAnonymous()) {
            return true;
        }
        else {
            if (isDefined(prop.asOWLObjectProperty())) {
                return true;
            }
            else {
                processUndefinedEntity(prop.asOWLObjectProperty());
                return false;
            }
        }
    }


    public boolean checkDefined(OWLDataPropertyExpression prop) throws OWLReasonerException {
        if (isDefined((OWLDataProperty) prop)) {
            return true;
        }
        else {
            processUndefinedEntity((OWLDataProperty) prop);
            return false;
        }
    }


    public boolean checkDefined(OWLIndividual individual) throws OWLReasonerException {
        if (isDefined(individual)) {
            return true;
        }
        else {
            processUndefinedEntity(individual);
            return false;
        }
    }


    private void processUndefinedEntity(OWLEntity entity) throws OWLReasonerException {
        if (silentUndefinedEntityHandling) {
            return;
        }
        throw new UndefinedEntityException(entity);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public boolean isConsistent(OWLOntology ontology) throws OWLReasonerException {
        return kernel.isConsistent(ontology);
    }


    public void loadOntologies(Set<OWLOntology> ontologies) throws OWLReasonerException {
        kernel.loadOntologies(ontologies);
    }


    public boolean isClassified() throws OWLReasonerException {
        return kernel.isClassified();
    }


    public void classify() throws OWLReasonerException {
        kernel.classify();
    }


    public boolean isRealised() throws OWLReasonerException {
        return kernel.isRealised();
    }


    public void realise() throws OWLReasonerException {
        kernel.realise();
    }


    public boolean isDefined(OWLClass cls) throws OWLReasonerException {
        return kernel.isDefined(cls);
    }


    public boolean isDefined(OWLObjectProperty prop) throws OWLReasonerException {
        return kernel.isDefined(prop);
    }


    public boolean isDefined(OWLDataProperty prop) throws OWLReasonerException {
        return kernel.isDefined(prop);
    }


    public boolean isDefined(OWLIndividual ind) throws OWLReasonerException {
        return kernel.isDefined(ind);
    }


    public Set<OWLOntology> getLoadedOntologies() {
        return kernel.getLoadedOntologies();
    }


    public void unloadOntologies(Set<OWLOntology> ontologies) throws OWLReasonerException {
        kernel.unloadOntologies(ontologies);
    }


    public void clearOntologies() throws OWLReasonerException {
        kernel.clearOntologies();
    }


    public void dispose() throws OWLReasonerException {
        kernel.dispose();
    }


    public boolean isSubClassOf(OWLDescription clsC, OWLDescription clsD) throws OWLReasonerException {
        if (!checkDefined(clsC)) {
            return false;
        }
        if (!checkDefined(clsD)) {
            return false;
        }
        return kernel.isSubClassOf(clsC, clsD);
    }


    public boolean isEquivalentClass(OWLDescription clsC, OWLDescription clsD) throws OWLReasonerException {
        if (!checkDefined(clsC)) {
            return false;
        }
        if (!checkDefined(clsD)) {
            return false;
        }
        return kernel.isEquivalentClass(clsC, clsD);
    }


    public Set<Set<OWLClass>> getSuperClasses(OWLDescription clsC) throws OWLReasonerException {
        if (!checkDefined(clsC)) {
            return Collections.emptySet();
        }
        return kernel.getSuperClasses(clsC);
    }


    public Set<Set<OWLClass>> getAncestorClasses(OWLDescription clsC) throws OWLReasonerException {
        if (!checkDefined(clsC)) {
            return Collections.emptySet();
        }
        return kernel.getAncestorClasses(clsC);
    }


    public Set<Set<OWLClass>> getSubClasses(OWLDescription clsC) throws OWLReasonerException {
        if (!checkDefined(clsC)) {
            return Collections.emptySet();
        }
        return kernel.getSubClasses(clsC);
    }


    public Set<Set<OWLClass>> getDescendantClasses(OWLDescription clsC) throws OWLReasonerException {
        if (!checkDefined(clsC)) {
            return Collections.emptySet();
        }
        return kernel.getDescendantClasses(clsC);
    }


    public Set<OWLClass> getEquivalentClasses(OWLDescription clsC) throws OWLReasonerException {
        if (!checkDefined(clsC)) {
            return Collections.emptySet();
        }
        return kernel.getEquivalentClasses(clsC);
    }


    public Set<OWLClass> getInconsistentClasses() throws OWLReasonerException {
        return kernel.getInconsistentClasses();
    }


    public boolean isSatisfiable(OWLDescription description) throws OWLReasonerException {
        if (!checkDefined(description)) {
            return undefinedClassesSatisfiable;
        }
        return kernel.isSatisfiable(description);
    }


    public Set<Set<OWLClass>> getTypes(OWLIndividual individual, boolean direct) throws OWLReasonerException {
        if (!checkDefined(individual)) {
            return Collections.emptySet();
        }
        return kernel.getTypes(individual, direct);
    }


    public Set<OWLIndividual> getIndividuals(OWLDescription clsC, boolean direct) throws OWLReasonerException {
        if (!checkDefined(clsC)) {
            return Collections.emptySet();
        }
        return kernel.getIndividuals(clsC, direct);
    }


    public Map<OWLObjectProperty, Set<OWLIndividual>> getObjectPropertyRelationships(OWLIndividual individual) throws
                                                                                                               OWLReasonerException {
        if (!checkDefined(individual)) {
            return Collections.emptyMap();
        }
        return kernel.getObjectPropertyRelationships(individual);
    }


    public Map<OWLDataProperty, Set<OWLConstant>> getDataPropertyRelationships(OWLIndividual individual) throws
                                                                                                              OWLReasonerException {
        if (!checkDefined(individual)) {
            return Collections.emptyMap();
        }
        return kernel.getDataPropertyRelationships(individual);
    }


    public boolean hasType(OWLIndividual individual, OWLDescription type, boolean direct) throws OWLReasonerException {
        if (!checkDefined(individual)) {
            return false;
        }
        if (!checkDefined(type)) {
            return false;
        }
        return kernel.hasType(individual, type, direct);
    }


    public boolean hasObjectPropertyRelationship(OWLIndividual subject, OWLObjectPropertyExpression property,
                                                 OWLIndividual object) throws OWLReasonerException {
        if (!checkDefined(subject)) {
            return false;
        }
        if (!checkDefined(property)) {
            return false;
        }
        if (!checkDefined(object)) {
            return false;
        }
        return kernel.hasObjectPropertyRelationship(subject, property, object);
    }


    public boolean hasDataPropertyRelationship(OWLIndividual subject, OWLDataPropertyExpression property,
                                               OWLConstant object) throws OWLReasonerException {
        if (!checkDefined(subject)) {
            return false;
        }
        if (!checkDefined(property)) {
            return false;
        }
        return kernel.hasDataPropertyRelationship(subject, property, object);
    }


    public Set<OWLIndividual> getRelatedIndividuals(OWLIndividual subject, OWLObjectPropertyExpression property) throws
                                                                                                                 OWLReasonerException {
        if (!checkDefined(subject)) {
            return Collections.emptySet();
        }
        if (!checkDefined(property)) {
            return Collections.emptySet();
        }
        return kernel.getRelatedIndividuals(subject, property);
    }


    public Set<OWLConstant> getRelatedValues(OWLIndividual subject, OWLDataPropertyExpression property) throws
                                                                                                        OWLReasonerException {
        if (!checkDefined(subject)) {
            return Collections.emptySet();
        }
        if (!checkDefined(property)) {
            return Collections.emptySet();
        }
        return kernel.getRelatedValues(subject, property);
    }


    public Set<Set<OWLObjectProperty>> getSuperProperties(OWLObjectProperty property) throws OWLReasonerException {
        if (!checkDefined(property)) {
            return Collections.emptySet();
        }
        return kernel.getSuperProperties(property);
    }


    public Set<Set<OWLObjectProperty>> getSubProperties(OWLObjectProperty property) throws OWLReasonerException {
        if (!checkDefined(property)) {
            return Collections.emptySet();
        }
        return kernel.getSubProperties(property);
    }


    public Set<Set<OWLObjectProperty>> getAncestorProperties(OWLObjectProperty property) throws OWLReasonerException {
        if (!checkDefined(property)) {
            return Collections.emptySet();
        }
        return kernel.getAncestorProperties(property);
    }


    public Set<Set<OWLObjectProperty>> getDescendantProperties(OWLObjectProperty property) throws OWLReasonerException {
        if (!checkDefined(property)) {
            return Collections.emptySet();
        }
        return kernel.getDescendantProperties(property);
    }


    public Set<Set<OWLObjectProperty>> getInverseProperties(OWLObjectProperty property) throws OWLReasonerException {
        if (!checkDefined(property)) {
            return Collections.emptySet();
        }
        return kernel.getInverseProperties(property);
    }


    public Set<OWLObjectProperty> getEquivalentProperties(OWLObjectProperty property) throws OWLReasonerException {
        if (!checkDefined(property)) {
            return Collections.emptySet();
        }
        return kernel.getEquivalentProperties(property);
    }


    public Set<Set<OWLDescription>> getDomains(OWLObjectProperty property) throws OWLReasonerException {
        if (!checkDefined(property)) {
            return Collections.emptySet();
        }
        return kernel.getDomains(property);
    }


    public Set<OWLDescription> getRanges(OWLObjectProperty property) throws OWLReasonerException {
        if (!checkDefined(property)) {
            return Collections.emptySet();
        }
        return kernel.getRanges(property);
    }


    public boolean isFunctional(OWLObjectProperty property) throws OWLReasonerException {
        if (!checkDefined(property)) {
            return false;
        }
        return kernel.isFunctional(property);
    }


    public boolean isInverseFunctional(OWLObjectProperty property) throws OWLReasonerException {
        if (!checkDefined(property)) {
            return false;
        }
        return kernel.isInverseFunctional(property);
    }


    public boolean isSymmetric(OWLObjectProperty property) throws OWLReasonerException {
        if (!checkDefined(property)) {
            return false;
        }

        return kernel.isSymmetric(property);
    }


    public boolean isTransitive(OWLObjectProperty property) throws OWLReasonerException {
        if (!checkDefined(property)) {
            return false;
        }
        return kernel.isTransitive(property);
    }


    public boolean isReflexive(OWLObjectProperty property) throws OWLReasonerException {
        if (!checkDefined(property)) {
            return false;
        }
        return kernel.isReflexive(property);
    }


    public boolean isIrreflexive(OWLObjectProperty property) throws OWLReasonerException {
        if (!checkDefined(property)) {
            return false;
        }
        return kernel.isIrreflexive(property);
    }


    public boolean isAntiSymmetric(OWLObjectProperty property) throws OWLReasonerException {
        if (!checkDefined(property)) {
            return false;
        }
        return kernel.isAntiSymmetric(property);
    }


    public Set<Set<OWLDataProperty>> getSuperProperties(OWLDataProperty property) throws OWLReasonerException {
        if (!checkDefined(property)) {
            return Collections.emptySet();
        }

        return kernel.getSuperProperties(property);
    }


    public Set<Set<OWLDataProperty>> getSubProperties(OWLDataProperty property) throws OWLReasonerException {
        if (!checkDefined(property)) {
            return Collections.emptySet();
        }
        return kernel.getSubProperties(property);
    }


    public Set<Set<OWLDataProperty>> getAncestorProperties(OWLDataProperty property) throws OWLReasonerException {
        if (!checkDefined(property)) {
            return Collections.emptySet();
        }
        return kernel.getAncestorProperties(property);
    }


    public Set<Set<OWLDataProperty>> getDescendantProperties(OWLDataProperty property) throws OWLReasonerException {
        if (!checkDefined(property)) {
            return Collections.emptySet();
        }
        return kernel.getDescendantProperties(property);
    }


    public Set<OWLDataProperty> getEquivalentProperties(OWLDataProperty property) throws OWLReasonerException {
        if (!checkDefined(property)) {
            return Collections.emptySet();
        }
        return kernel.getEquivalentProperties(property);
    }


    public Set<Set<OWLDescription>> getDomains(OWLDataProperty property) throws OWLReasonerException {
        if (!checkDefined(property)) {
            return Collections.emptySet();
        }
        return kernel.getDomains(property);
    }


    public Set<OWLDataRange> getRanges(OWLDataProperty property) throws OWLReasonerException {
        if (!checkDefined(property)) {
            return Collections.emptySet();
        }
        return kernel.getRanges(property);
    }


    public boolean isFunctional(OWLDataProperty property) throws OWLReasonerException {
        if (!checkDefined(property)) {
            return false;
        }
        return kernel.isFunctional(property);
    }


    public String toString() {
        return kernel.toString();
    }
}
