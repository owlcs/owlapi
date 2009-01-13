package org.semanticweb.owl.util;

import org.semanticweb.owl.inference.OWLClassReasoner;
import org.semanticweb.owl.inference.OWLReasonerException;
import org.semanticweb.owl.model.*;

import java.util.*;
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
 * Date: 22-Nov-2006<br><br>
 * <p/>
 * A very simple syntactic (and therefore incomplete) reasoner, which
 * provides information about the told named class hierarchy for an ontology
 * and its imports closure. The current implementation does some very simple
 * syntactic reasoning.  For example  SubClassOf( A  And(B C)) would lead to
 * B and C being superclasses of A, and A being a subclass of B and A being
 * a subclass of C.
 */
public class ToldClassHierarchyReasoner implements OWLClassReasoner {

    private OWLOntologyManager manager;

    private Set<OWLOntology> ontologies;

    private OWLClass root;

    private ParentClassExtractor parentClassExtractor;

    private ChildClassExtractor childClassExtractor;

    private OWLOntologyChangeListener listener;

    private Set<OWLClass> implicitRoots;


    public ToldClassHierarchyReasoner(OWLOntologyManager owlOntologyManager) {
        this.manager = owlOntologyManager;
        ontologies = new HashSet<OWLOntology>();
        implicitRoots = new HashSet<OWLClass>();

        parentClassExtractor = new ParentClassExtractor();
        childClassExtractor = new ChildClassExtractor();
        listener = new OWLOntologyChangeListener() {
            public void ontologiesChanged(List<? extends OWLOntologyChange> changes) {
                handleChanges(changes);
            }
        };
        getManager().addOntologyChangeListener(listener);
    }


    private OWLOntologyManager getManager() {
        return manager;
    }


    public void dispose() {
        getManager().removeOntologyChangeListener(listener);
    }


    public boolean isClassified() {
        return true;
    }


    public void classify() {
    }


    public boolean isRealised() throws OWLReasonerException {
        return true;
    }


    public void realise() throws OWLReasonerException {
    }


    public boolean isDefined(OWLClass cls) throws OWLReasonerException {
        return true;
    }


    public boolean isDefined(OWLObjectProperty prop) throws OWLReasonerException {
        return true;
    }


    public boolean isDefined(OWLDataProperty prop) throws OWLReasonerException {
        return true;
    }


    public boolean isDefined(OWLIndividual ind) throws OWLReasonerException {
        return true;
    }


    /**
     * Sets the ontologies that this hierarchy provider should use
     * in order to determine the hierarchy.
     */
    private void setOntologies() {
        if (root == null) {
            root = manager.getOWLDataFactory().getOWLThing();
        }
        rebuildImplicitRoots();
    }


    public void loadOntologies(Set<OWLOntology> ontologies) {
        this.ontologies.addAll(ontologies);
        setOntologies();
    }


    public Set<OWLOntology> getLoadedOntologies() {
        return Collections.unmodifiableSet(ontologies);
    }


    public void unloadOntologies(Set<OWLOntology> ontologies) {
        this.ontologies.removeAll(ontologies);
        setOntologies();
    }


    public void clearOntologies() {
        ontologies.clear();
        setOntologies();
    }


    /**
     * Rebuilds a set of classes that are considered to be root classes - i.e.
     * direct subclasses of owl:Thing.  This set contains directly
     * asserted sub classes and classes which don't have any syntactic super
     * classes.
     */
    private void rebuildImplicitRoots() {
        checker = new SimpleRootClassChecker(ontologies);
        implicitRoots.clear();
        Set<OWLClass> checkedClasses = new HashSet<OWLClass>();
        for (OWLOntology ont : ontologies) {
            Set<OWLClass> ref = ont.getReferencedClasses();
            for (OWLClass cls : ref) {
                if (checkedClasses.contains(cls)) {
                    continue;
                }
                if (isImplicitSubClassOfThing(cls)) {
                    implicitRoots.add(cls);
                }
                checkedClasses.add(cls);
            }
        }
    }


    private SimpleRootClassChecker checker;


    private boolean isImplicitSubClassOfThing(OWLClass cls) {
        boolean isRoot = checker.isRootClass(cls);
        if (!isRoot) {
            Set<OWLClass> ancestors = getAncestors(cls);
            if (ancestors.contains(cls)) {
                for (OWLClass anc : ancestors) {
                    if (getAncestors(anc).contains(cls)) {
                        implicitRoots.add(anc);
                    }
                }
                return true;
            }
        }
        return isRoot;
    }


    private void handleChanges(List<? extends OWLOntologyChange> changes) {
        for (OWLOntologyChange change : changes) {
            if (change.isAxiomChange()) {
                for (OWLEntity entity : ((OWLAxiomChange) change).getEntities()) {
                    if (entity instanceof OWLClass) {
                        updateImplicitRoots((OWLClass) entity);
                    }
                }
            }
        }
    }


    private void updateImplicitRoots(OWLClass cls) {
        if (!containsReference(cls)) {
            implicitRoots.remove(cls);
            return;
        }
        if (isImplicitSubClassOfThing(cls)) {
            implicitRoots.add(cls);
        }
        else {
            implicitRoots.remove(cls);
        }
    }


    private Set<OWLClass> getChildren(OWLClass object) {
            Set<OWLClass> result;
            if (object.equals(root)) {
                result = new HashSet<OWLClass>();
                result.addAll(implicitRoots);
                result.addAll(extractChildren(object));
                result.remove(object);
            }
            else {
                result = extractChildren(object);
                for (Iterator<OWLClass> it = result.iterator(); it.hasNext();) {
                    OWLClass curChild = it.next();
                    if (getAncestors(curChild).contains(curChild)) {
                        it.remove();
                    }
                }
            }

            return result;
    }


    private Set<OWLClass> extractChildren(OWLClass parent) {
        childClassExtractor.setCurrentParentClass(parent);
        Set<OWLClass> result = new HashSet<OWLClass>(childClassExtractor.getResult());
        for (OWLOntology ont : ontologies) {
            for (OWLAxiom ax : ont.getReferencingAxioms(parent)) {
                childClassExtractor.reset();
                if (ax.isLogicalAxiom()) {
                    ax.accept(childClassExtractor);
                    result.addAll(childClassExtractor.getResult());
                }
            }
        }
        return result;
    }


    private boolean containsReference(OWLClass object) {
        for (OWLOntology ont : ontologies) {
            if (ont.containsClassReference(object.getURI())) {
                return true;
            }
        }
        return false;
    }


    private Set<OWLClass> getParents(OWLClass object) {
            // If the object is thing then there are no
            // parents
            if (object.equals(root)) {
                return Collections.emptySet();
            }
            Set<OWLClass> result = new HashSet<OWLClass>();
            // Thing if the object is a root class
            if (implicitRoots.contains(object)) {
                result.add(root);
            }
            // Not a root, so must have another parent
            parentClassExtractor.reset();
            parentClassExtractor.setCurrentClass(object);
            for (OWLOntology ont : ontologies) {
                for (OWLAxiom ax : ont.getAxioms(object)) {
                    ax.accept(parentClassExtractor);
                }
            }
            result.addAll(parentClassExtractor.getResult());
            return result;
    }


    private Set<OWLClass> getEquivalents(OWLClass object) {
            Set<OWLClass> result = new HashSet<OWLClass>();
            for (OWLOntology ont : ontologies) {
                for (OWLDescription equiv : object.getEquivalentClasses(ont)) {
                    if (!equiv.isAnonymous()) {
                        result.add((OWLClass) equiv);
                    }
                }
            }
            Set<OWLClass> ancestors = getAncestors(object);
            if (ancestors.contains(object)) {
                for (OWLClass cls : ancestors) {
                    if (getAncestors(cls).contains(object)) {
                        result.add(cls);
                    }
                }
                result.remove(object);
                result.remove(root);
            }
            return result;
    }


    private Set<OWLClass> getAncestors(OWLClass object) {
        Set<OWLClass> results = new HashSet<OWLClass>();
        getAncestors(results, object);
        return results;
    }


    private void getAncestors(Set<OWLClass> results, OWLClass object) {
        for (OWLClass parent : getParents(object)) {
            if (!results.contains(parent)) {
                results.add(parent);
                getAncestors(results, parent);
            }
        }
    }


    private Set<OWLClass> getDescendants(OWLClass object) {
        Set<OWLClass> results = new HashSet<OWLClass>();
        getDescendants(results, object);
        return results;
    }


    private void getDescendants(Set<OWLClass> results, OWLClass object) {
        for (OWLClass child : getChildren(object)) {
            if (!results.contains(child)) {
                results.add(child);
                getDescendants(results, child);
            }
        }
    }


    /**
     * Checks whether the first class is a subclass of the second class
     * @return <code>true</code> if the first class is a subclass of the second class
     */
    public boolean isSubClassOf(OWLDescription clsC, OWLDescription clsD) {
        if (clsC.isAnonymous() || clsD.isAnonymous()) {
            return false;
        }
        return getChildren(((OWLClass) clsD)).contains(clsC);
    }


    /**
     * Checks whether the first class is equivalent to the second class
     * @return <code>true</code> if the first class is equivalent to the second class, or
     *         <code>false</code> if the first class is not equivalent to the second class
     */
    public boolean isEquivalentClass(OWLDescription clsC, OWLDescription clsD) {
        if (clsC.isAnonymous() || clsD.isAnonymous()) {
            return false;
        }
        return getEquivalents((OWLClass) clsC).contains(clsD);
    }


    /**
     * Checks whether the specified class is consistent
     * @param clsC the class to check
     * @return <code>true</code> if the class is consistent, or <code>false</code>
     *         if the class is not consistent
     */
    public boolean isSatisfiable(OWLDescription clsC) {
        return true;
    }


    /**
     * A convenience methods for obtaining all classes which are inconsistent.
     * @return A set of classes which are inconsistent.
     */
    public Set<OWLClass> getInconsistentClasses() {
        return Collections.emptySet();
    }


    /**
     * Returns the collection of (named) most specific superclasses
     * of the given description. The result of this will be a set of
     * sets, where each set in the collection represents an
     * equivalence class.
     */

    public Set<Set<OWLClass>> getSuperClasses(OWLDescription clsC) {
        if (clsC.isAnonymous()) {
            return Collections.emptySet();
        }
        return toSetOfSets(getParents((OWLClass) clsC));
    }


    /**
     * Returns the collection of all superclasses of the given
     * description. The result of this will be a set of sets, where
     * each set in the collection represents an equivalence class.
     */

    public Set<Set<OWLClass>> getAncestorClasses(OWLDescription clsC) {
        if (clsC.isAnonymous()) {
            return Collections.emptySet();
        }
        return toSetOfSets(getAncestors((OWLClass) clsC));
    }


    /**
     * Returns the collection of (named) most general subclasses
     * of the given description. The result of this will be a set of
     * sets, where each set in the collection represents an
     * equivalence class.
     */

    public Set<Set<OWLClass>> getSubClasses(OWLDescription clsC) {
        if (clsC.isAnonymous()) {
            return Collections.emptySet();
        }
        return toSetOfSets(getChildren((OWLClass) clsC));
    }


    /**
     * Returns the collection of all subclasses of the given
     * description. The result of this will be a set of sets, where
     * each set in the collection represents an equivalence class.
     */

    public Set<Set<OWLClass>> getDescendantClasses(OWLDescription clsC) {
        if (clsC.isAnonymous()) {
            return Collections.emptySet();
        }
        return toSetOfSets(getDescendants((OWLClass) clsC));
    }


    /**
     * Returns the collection of (named) classes which are equivalent
     * to the given description.
     */
    public Set<OWLClass> getEquivalentClasses(OWLDescription clsC) {
        if (clsC.isAnonymous()) {
            return Collections.emptySet();
        }
        return getEquivalents((OWLClass) clsC);
    }


    private Set<Set<OWLClass>> toSetOfSets(Set<OWLClass> clses) {
        Set<Set<OWLClass>> result = new HashSet<Set<OWLClass>>();
        for (OWLClass cls : clses) {
            Set<OWLClass> equivSet = new HashSet<OWLClass>();
            equivSet.add(cls);
            equivSet.addAll(getEquivalents(cls));
            result.add(equivSet);
        }
        return result;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private class ParentClassExtractor extends OWLAxiomVisitorAdapter {

        private NamedClassExtractor extractor = new NamedClassExtractor();

        private OWLClass current;


        public void setCurrentClass(OWLClass current) {
            this.current = current;
        }


        public void reset() {
            extractor.reset();
        }


        public Set<OWLClass> getResult() {
            return extractor.getResult();
        }


        public void visit(OWLSubClassAxiom axiom) {
            axiom.getSuperClass().accept(extractor);
        }


        public void visit(OWLEquivalentClassesAxiom axiom) {
            for (OWLDescription desc : axiom.getDescriptions()) {
                if (desc.equals(current)) {
                    continue;
                }
                desc.accept(extractor);
            }
        }
    }


    private class NamedClassExtractor extends OWLDescriptionVisitorAdapter {

        Set<OWLClass> result = new HashSet<OWLClass>();


        public void reset() {
            result.clear();
        }


        public Set<OWLClass> getResult() {
            return result;
        }


        public void visit(OWLClass desc) {
            result.add(desc);
        }


        public void visit(OWLObjectIntersectionOf desc) {
            for (OWLDescription op : desc.getOperands()) {
                op.accept(this);
            }
        }
    }


    private class NamedClassChecker extends OWLDescriptionVisitorAdapter {

        private boolean found;

        private OWLClass searchClass;


        public void setSearchClass(OWLClass searchClass) {
            this.searchClass = searchClass;
            reset();
        }


        public void reset() {
            found = false;
        }


        public boolean containsSearchClass() {
            return found;
        }


        public void visit(OWLClass desc) {
            if (desc.equals(searchClass)) {
                found = true;
            }
        }


        public void visit(OWLObjectIntersectionOf desc) {
            for (OWLDescription op : desc.getOperands()) {
                op.accept(this);
                if (found) {
                    break;
                }
            }
        }
    }


    private class ChildClassExtractor extends OWLAxiomVisitorAdapter {


        private NamedClassChecker checker = new NamedClassChecker();

        private NamedClassExtractor namedClassExtractor = new NamedClassExtractor();

        private OWLClass currentParentClass;

        private Set<OWLClass> results = new HashSet<OWLClass>();


        public void reset() {
            results.clear();
            namedClassExtractor.reset();
        }


        public void setCurrentParentClass(OWLClass currentParentClass) {
            this.currentParentClass = currentParentClass;
            checker.setSearchClass(currentParentClass);
            reset();
        }


        public Set<OWLClass> getResult() {
            return results;
        }


        public void visit(OWLSubClassAxiom axiom) {
            checker.reset();
            axiom.getSuperClass().accept(checker);
            if (checker.containsSearchClass()) {
                if (!axiom.getSubClass().isAnonymous()) {
                    results.add((OWLClass) axiom.getSubClass());
                }
            }
        }


        public void visit(OWLEquivalentClassesAxiom axiom) {
            // We want operands that don't contain the search class when
            // flattened
            Set<OWLDescription> equivalentClasses = axiom.getDescriptions();
            Set<OWLDescription> candidateDescriptions = new HashSet<OWLDescription>();
            checker.reset();
            boolean found = false;
            for (OWLDescription equivalentClass : equivalentClasses) {
                equivalentClass.accept(checker);
                if (!checker.containsSearchClass()) {
                    candidateDescriptions.add(equivalentClass);
                }
                else {
                    found = true;
                }
            }
            if (!found) {
                return;
            }
            namedClassExtractor.reset();
            for (OWLDescription desc : candidateDescriptions) {
                desc.accept(namedClassExtractor);
            }
            results.addAll(namedClassExtractor.getResult());
        }
    }



}
