package org.semanticweb.owl.profiles;

import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;
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
 * Information Management Group<br>
 * Date: 21-Jun-2008<br><br>
 */
public class OWL2Profile implements OWLProfile {
    public OWLProfileReport checkOntology(OWLOntology ontology, OWLOntologyManager manager) {
        return null;
    }

    public String getName() {
        return null;
    }

    //
//
//    private OWLObjectPropertyManager propertyManager;
//
//
//    public String getName() {
//        return "OWL 2";
//    }
//
//
//    public OWLProfileReport checkOntology(OWLOntology ontology, OWLOntologyManager manager) {
//        propertyManager = new OWLObjectPropertyManager(manager, ontology);
//
//        Set<OWLObjectPropertyExpression> simpleRoles = new HashSet<OWLObjectPropertyExpression>();
//
//
//        Set<OWLObjectPropertyExpression> nonSimpleRoles = new HashSet<OWLObjectPropertyExpression>();
//
//        for (OWLOntology ont : manager.getImportsClosure(ontology)) {
//            for (OWLObjectProperty prop : ont.getReferencedObjectProperties()) {
//                if (!propertyManager.getNonSimpleProperties().contains(prop)) {
//                    simpleRoles.add(prop);
//                } else {
//                    nonSimpleRoles.add(prop);
//                }
//            }
//        }
//
//        OWLOntologyWalker walker = new OWLOntologyWalker(manager.getImports(ontology));
//
//
//
//        Set<ConstructNotAllowed> notAllowed = new HashSet<ConstructNotAllowed>();
//        AxiomChecker checker = new AxiomChecker();
//        for (OWLOntology ont : manager.getImportsClosure(ontology)) {
//            for (OWLAxiom ax : ont.getAxioms()) {
//                Set<ConstructNotAllowed> na = ax.accept(checker);
//                notAllowed.addAll(na);
//            }
//        }
//
//// A strict partial order (i.e., an irreflexive and transitive relation) < on AllOPE(Ax) exists that fulfills the
//// following conditions:
////OP1 < OP2 if and only if INV(OP1) < OP2 for all object properties OP1 and OP2 occurring in AllOPE(Ax).
////If OPE1 < OPE2 holds, then OPE2 ?* OPE1 does not hold;
////Each axiom in Ax of the form SubPropertyOf( PropertyChain( OPE1 ... OPEn ) OPE ) with n � 2 fulfills the following conditions:
////OPE is equal to owl:topObjectProperty, or
////n = 2 and OPE1 = OPE2 = OPE, or
////OPEi < OPE for each 1 � i � n, or
////OPE1 = OPE and OPEi < OPE for each 2 � i � n, or
////OPEn = OPE and OPEi < OPE for each 1 � i � n-1.
//
//
//        Set<OWLObjectProperty> props = new HashSet<OWLObjectProperty>();
//        for(OWLOntology ont : manager.getImportsClosure(ontology)) {
//            props.addAll(ont.getReferencedObjectProperties());
//        }
//        // For any pair of properties, and the ordering of them in the rt, the same must hold for
//        // the inverses and vice versa
//
//
//        Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> rtc = propertyManager.getHierarchyReflexiveTransitiveClosure();
//
//        for(OWLObjectProperty propA : props) {
//            for(OWLObjectProperty propB : props) {
//                if(propertyManager.isSubPropertyOf(propA, propB)) {
//                    if(!propertyManager.isSubPropertyOf(propA.getInverseProperty(), propB.getInverseProperty())) {
//                        // NOT ALLOWED
//                    }
//                }
//                else if(propertyManager.isSubPropertyOf(propB, propA)) {
//                    if(!propertyManager.isSubPropertyOf(propB.getInverseProperty(), propA.getInverseProperty())) {
//                        // NOT ALLOWED
//                    }
//                }
//            }
//        }
//
//        // Property chain restrictions
//        for (OWLObjectPropertyChainSubPropertyAxiom ax : ontology.getAxioms(AxiomType.PROPERTY_CHAIN_SUB_PROPERTY)) {
//            if(ax.getSuperProperty().equals(manager.getOWLDataFactory().getOWLObjectProperty(OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY.getURI()))) {
//                // 1st condition is
//                continue;
//            }
//            if (ax.getPropertyChain().size() == 2) {
//                // 2nd condition
//                // OPE1 = OPE2 = OPE
//                boolean equal = true;
//                for(OWLObjectPropertyExpression prop : ax.getPropertyChain()) {
//                    if(!prop.equals(ax.getSuperProperty())) {
//                        equal = false;
//                        break;
//                    }
//                }
//                if(equal) {
//                    continue;
//                }
//            }
//            for(OWLObjectPropertyExpression prop : ax.getPropertyChain()) {
//                // OPE_n < OPE
//                if(propertyManager.isLessThan(ax.getSuperProperty().getSimplified(), prop.getSimplified())) {
//                    // NOT ALLOWED
////                        System.out.println("CYCLE DETECTED! " + ax.getSuperProperty() + " -> " + prop + " -> " + ax.getSuperProperty());
//                    notAllowed.add(new CycleInPropertyChain(Arrays.asList(ax.getSuperProperty(), prop)));
//                }
//            }
//            if(ax.getPropertyChain().get(0).equals(ax.getSuperProperty())) {
//                for(int i = 1; i < ax.getPropertyChain().size(); i++) {
//                    OWLObjectPropertyExpression chainProp = ax.getPropertyChain().get(i).getSimplified();
//                    // OPE_n < OPE
//                    if(propertyManager.isLessThan(ax.getSuperProperty().getSimplified(), chainProp)) {
//                        // NOT ALLOWED
////                        System.out.println("CYCLE DETECTED! " + ax.getSuperProperty() + " -> " + chainProp + " -> " + ax.getSuperProperty());
//                        notAllowed.add(new CycleInPropertyChain(Arrays.asList(ax.getSuperProperty(), chainProp)));
//                    }
//                }
//                continue;
//            }
//            if(ax.getPropertyChain().get(ax.getPropertyChain().size() -1 ).equals(ax.getSuperProperty())) {
//                for(int i = 0; i < ax.getPropertyChain().size() - 1; i++) {
//                    OWLObjectPropertyExpression chainProp = ax.getPropertyChain().get(i).getSimplified();
//                    // OPE_n < OPE
//                    if(propertyManager.isLessThan(ax.getSuperProperty().getSimplified(), chainProp)) {
//                        // NOT ALLOWED
////                        System.out.println("CYCLE DETECTED! " + ax.getSuperProperty() + " -> " + chainProp + " -> " + ax.getSuperProperty());
//                        notAllowed.add(new CycleInPropertyChain(Arrays.asList(ax.getSuperProperty(), chainProp)));
//                    }
//                }
//            }
//        }
//
//        // No punning between data and object properties
//        Set<URI> objectPropertyURIs = new HashSet<URI>();
//        Set<URI> dataPropertyURIs = new HashSet<URI>();
//        for (OWLOntology ont : manager.getImportsClosure(ontology)) {
//            for (OWLObjectProperty prop : ont.getReferencedObjectProperties()) {
//                objectPropertyURIs.add(prop.getURI());
//            }
//            for (OWLDataProperty prop : ont.getReferencedDataProperties()) {
//                dataPropertyURIs.add(prop.getURI());
//            }
//        }
//        for (URI uri : objectPropertyURIs) {
//            if (dataPropertyURIs.contains(uri)) {
//                notAllowed.add(new DataPropertyURIUsedAsObjectPropertyURI(uri));
//            }
//        }
//
//
//        return new OWL2ProfileReport(this, ontology.getURI(), notAllowed, nonSimpleRoles, simpleRoles);
//    }
//
//    private static Set<ConstructNotAllowed> getSet(ConstructNotAllowed na) {
//        return Collections.singleton(na);
//    }
//
//    private static Set<ConstructNotAllowed> getSet() {
//        return new HashSet<ConstructNotAllowed>(2);
//    }
//
//    private class ObjectChecker extends OWLOntologyWalkerVisitor {
//
//        private Set<ConstructNotAllowed<? extends OWLObject>> disallowedConstructs;
//
//        private ObjectChecker(OWLOntologyWalker walker) {
//            super(walker);
//        }
//
//        private boolean isSimple(OWLObjectPropertyExpression prop) {
//            return !propertyManager.isNonSimple(prop);
//        }
//
//        private void add(ConstructNotAllowed<? extends OWLObject> cna) {
//            disallowedConstructs.add(cna);
//        }
//
//        public Object visit(OWLConstantAnnotation annotation) {
//            return super.visit(annotation);
//        }
//
//        public Object visit(OWLObjectAnnotation annotation) {
//            return super.visit(annotation);
//        }
//
//        public Object visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLAxiomAnnotationAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLClassAssertionAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLDataPropertyAssertionAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLDataPropertyDomainAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLDataPropertyRangeAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLDataSubPropertyAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLDeclarationAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLDifferentIndividualsAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLDisjointClassesAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLDisjointDataPropertiesAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLDisjointObjectPropertiesAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLDisjointUnionAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLEntityAnnotationAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLEquivalentClassesAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLEquivalentDataPropertiesAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLEquivalentObjectPropertiesAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLFunctionalDataPropertyAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLFunctionalObjectPropertyAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLImportsDeclaration axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLInverseObjectPropertiesAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLObjectPropertyAssertionAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLObjectPropertyDomainAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLObjectPropertyRangeAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLObjectSubPropertyAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLOntologyAnnotationAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLReflexiveObjectPropertyAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLSameIndividualsAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLSubClassAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLSymmetricObjectPropertyAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLTransitiveObjectPropertyAxiom axiom) {
//            return super.visit(axiom);
//        }
//
//        public Object visit(OWLClass desc) {
//            return super.visit(desc);
//        }
//
//        public Object visit(OWLDataAllRestriction desc) {
//            return super.visit(desc);
//        }
//
//        public Object visit(OWLDataExactCardinalityRestriction desc) {
//            return super.visit(desc);
//        }
//
//        public Object visit(OWLDataMaxCardinalityRestriction desc) {
//            return super.visit(desc);
//        }
//
//        public Object visit(OWLDataMinCardinalityRestriction desc) {
//            return super.visit(desc);
//        }
//
//        public Object visit(OWLDataSomeRestriction desc) {
//            return super.visit(desc);
//        }
//
//        public Object visit(OWLDataValueRestriction desc) {
//            return super.visit(desc);
//        }
//
//        public Object visit(OWLObjectAllRestriction desc) {
//            return super.visit(desc);
//        }
//
//        public Object visit(OWLObjectComplementOf desc) {
//            return super.visit(desc);
//        }
//
//        public Object visit(OWLObjectExactCardinalityRestriction desc) {
//            return super.visit(desc);
//        }
//
//        public Object visit(OWLObjectIntersectionOf desc) {
//            return super.visit(desc);
//        }
//
//        public Object visit(OWLObjectMaxCardinalityRestriction desc) {
//            return super.visit(desc);
//        }
//
//        public Object visit(OWLObjectMinCardinalityRestriction desc) {
//            if(!isSimple(desc.getProperty())) {
//                add(new NonSimplePropertyInCardinalityRestriction(desc));
//            }
//            return null;
//        }
//
//        public Object visit(OWLObjectOneOf desc) {
//            return super.visit(desc);
//        }
//
//        public Object visit(OWLObjectSelfRestriction desc) {
//            return super.visit(desc);
//        }
//
//        public Object visit(OWLObjectSomeRestriction desc) {
//            return super.visit(desc);
//        }
//
//        public Object visit(OWLObjectUnionOf desc) {
//            return super.visit(desc);
//        }
//
//        public Object visit(OWLObjectValueRestriction desc) {
//            return super.visit(desc);
//        }
//
//        public Object visit(OWLIndividual individual) {
//            return super.visit(individual);
//        }
//
//        public Object visit(OWLDataComplementOf node) {
//            return super.visit(node);
//        }
//
//        public Object visit(OWLDataOneOf node) {
//            return super.visit(node);
//        }
//
//        public Object visit(OWLDataRangeFacetRestriction node) {
//            return super.visit(node);
//        }
//
//        public Object visit(OWLDataRangeRestriction node) {
//            return super.visit(node);
//        }
//
//        public Object visit(OWLDatatype node) {
//            return super.visit(node);
//        }
//
//        public Object visit(OWLTypedLiteral node) {
//            return super.visit(node);
//        }
//
//        public Object visit(OWLRDFTextLiteral node) {
//            return super.visit(node);
//        }
//
//        public Object visit(SWRLAtomConstantObject node) {
//            return super.visit(node);
//        }
//
//        public Object visit(SWRLAtomDVariable node) {
//            return super.visit(node);
//        }
//
//        public Object visit(SWRLAtomIndividualObject node) {
//            return super.visit(node);
//        }
//
//        public Object visit(SWRLAtomIVariable node) {
//            return super.visit(node);
//        }
//
//        public Object visit(SWRLBuiltInAtom node) {
//            return super.visit(node);
//        }
//
//        public Object visit(SWRLClassAtom node) {
//            return super.visit(node);
//        }
//
//        public Object visit(SWRLDataRangeAtom node) {
//            return super.visit(node);
//        }
//
//        public Object visit(SWRLDataValuedPropertyAtom node) {
//            return super.visit(node);
//        }
//
//        public Object visit(SWRLDifferentFromAtom node) {
//            return super.visit(node);
//        }
//
//        public Object visit(SWRLObjectPropertyAtom node) {
//            return super.visit(node);
//        }
//
//        public Object visit(SWRLSameAsAtom node) {
//            return super.visit(node);
//        }
//
//        public Object visit(OWLOntology ontology) {
//            return super.visit(ontology);
//        }
//
//        public Object visit(OWLDataProperty property) {
//            return super.visit(property);
//        }
//
//        public Object visit(OWLObjectProperty property) {
//            return super.visit(property);
//        }
//
//        public Object visit(OWLObjectPropertyInverse property) {
//            return super.visit(property);
//        }
//
//        public Object visit(SWRLRule rule) {
//            return super.visit(rule);
//        }
//
//        protected Object clone() throws CloneNotSupportedException {
//            return super.clone();
//        }
//
//        public boolean equals(Object obj) {
//            return super.equals(obj);
//        }
//
//        protected void finalize() throws Throwable {
//            super.finalize();
//        }
//
//        public int hashCode() {
//            return super.hashCode();
//        }
//
//        public String toString() {
//            return super.toString();
//        }
//    }
//
//    private class AxiomChecker implements OWLObjectVisitorEx<Set<ConstructNotAllowed>> {
//
//
//        public Set<ConstructNotAllowed> visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {
//            if (propertyManager.isNonSimple(axiom.getProperty())) {
//                return getSet(new NonSimplePropertyInAntiSymmetricPropertyAxiom(axiom));
//            }
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLAxiomAnnotationAxiom axiom) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLClassAssertionAxiom axiom) {
//            Set<ConstructNotAllowed> na = axiom.getDescription().accept(this);
//            if (na.isEmpty()) {
//                return Collections.emptySet();
//            } else {
//                return getSet(new AxiomNotAllowed(na, axiom));
//            }
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLDataPropertyAssertionAxiom axiom) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLDataPropertyDomainAxiom axiom) {
//            Set<ConstructNotAllowed> na = axiom.getDomain().accept(this);
//            if (!na.isEmpty()) {
//                return getSet(new AxiomNotAllowed(na, axiom));
//            }
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLDataPropertyRangeAxiom axiom) {
//            Set<ConstructNotAllowed> na = axiom.getRange().accept(this);
//            if (!na.isEmpty()) {
//                return getSet(new AxiomNotAllowed(na, axiom));
//            } else {
//                return Collections.emptySet();
//            }
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLDataSubPropertyAxiom axiom) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLDeclarationAxiom axiom) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLDifferentIndividualsAxiom axiom) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLDisjointClassesAxiom axiom) {
//            Set<ConstructNotAllowed> na = getSet();
//            for (OWLDescription desc : axiom.getDescriptions()) {
//                Set<ConstructNotAllowed> descNa = desc.accept(this);
//                na.addAll(descNa);
//            }
//            if (!na.isEmpty()) {
//                return getSet(new AxiomNotAllowed(na, axiom));
//            }
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLDisjointDataPropertiesAxiom axiom) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLDisjointObjectPropertiesAxiom axiom) {
//            Set<OWLObjectPropertyExpression> nonSimpleProperties = new HashSet<OWLObjectPropertyExpression>();
//            for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
//                if (propertyManager.isNonSimple(prop)) {
//                    nonSimpleProperties.add(prop);
//                }
//            }
//            if (!nonSimpleProperties.isEmpty()) {
//                return getSet(new NonSimplePropertyInDisjointPropertiesAxiom(axiom, nonSimpleProperties));
//            }
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLDisjointUnionAxiom axiom) {
//            Set<ConstructNotAllowed> na = getSet();
//            for (OWLDescription desc : axiom.getDescriptions()) {
//                na.addAll(desc.accept(this));
//            }
//            if (!na.isEmpty()) {
//                return getSet(new AxiomNotAllowed(na, axiom));
//            }
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLEntityAnnotationAxiom axiom) {
//            Set<ConstructNotAllowed> na = axiom.getAnnotation().accept(this);
//            if (!na.isEmpty()) {
//                return getSet(new AxiomNotAllowed(na, axiom));
//            } else {
//                return Collections.emptySet();
//            }
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLEquivalentClassesAxiom axiom) {
//            Set<ConstructNotAllowed> na = getSet();
//            for (OWLDescription desc : axiom.getDescriptions()) {
//                na.addAll(desc.accept(this));
//            }
//            if (!na.isEmpty()) {
//                return getSet(new AxiomNotAllowed(na, axiom));
//            }
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLEquivalentDataPropertiesAxiom axiom) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLEquivalentObjectPropertiesAxiom axiom) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLFunctionalDataPropertyAxiom axiom) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLFunctionalObjectPropertyAxiom axiom) {
//            if (propertyManager.isNonSimple(axiom.getProperty())) {
//                return getSet(new NonSimplePropertyInFunctionalPropertyAxiom(axiom));
//            } else {
//                return Collections.emptySet();
//            }
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLImportsDeclaration axiom) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
//            if (propertyManager.isNonSimple(axiom.getProperty())) {
//                return getSet(new NonSimplePropertyInInverseFunctionalPropertyAxiom(axiom));
//            } else {
//                return Collections.emptySet();
//            }
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLInverseObjectPropertiesAxiom axiom) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
//            if (propertyManager.isNonSimple(axiom.getProperty())) {
//                return getSet(new NonSimplePropertyInIrreflexivePropertyAxiom(axiom));
//            } else {
//                return Collections.emptySet();
//            }
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLObjectPropertyAssertionAxiom axiom) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
//            // Handled separately
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLObjectPropertyDomainAxiom axiom) {
//            Set<ConstructNotAllowed> na = axiom.getDomain().accept(this);
//            if (!na.isEmpty()) {
//                return getSet(new AxiomNotAllowed(na, axiom));
//            } else {
//                return Collections.emptySet();
//            }
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLObjectPropertyRangeAxiom axiom) {
//            Set<ConstructNotAllowed> na = axiom.getRange().accept(this);
//            if (!na.isEmpty()) {
//                return getSet(new AxiomNotAllowed(na, axiom));
//            } else {
//                return Collections.emptySet();
//            }
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLObjectSubPropertyAxiom axiom) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLOntologyAnnotationAxiom axiom) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLReflexiveObjectPropertyAxiom axiom) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLSameIndividualsAxiom axiom) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLSubClassAxiom axiom) {
//            Set<ConstructNotAllowed> na = new HashSet<ConstructNotAllowed>();
//            na.addAll(axiom.getSubClass().accept(this));
//            na.addAll(axiom.getSuperClass().accept(this));
//            if(!na.isEmpty()) {
//                return getSet(new AxiomNotAllowed(na, axiom));
//            }
//            else {
//                return Collections.emptySet();
//            }
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLSymmetricObjectPropertyAxiom axiom) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLTransitiveObjectPropertyAxiom axiom) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(SWRLRule rule) {
//            for (SWRLAtom atom : rule.getBody()) {
//
//            }
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLClass desc) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLDataAllRestriction desc) {
//            Set<ConstructNotAllowed> na = desc.getFiller().accept(this);
//            if (!na.isEmpty()) {
//                return getSet(new DescriptionNotAllowed(na, desc));
//            } else {
//                return Collections.emptySet();
//            }
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLDataExactCardinalityRestriction desc) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLDataMaxCardinalityRestriction desc) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLDataMinCardinalityRestriction desc) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLDataSomeRestriction desc) {
//            Set<ConstructNotAllowed> na = desc.getFiller().accept(this);
//            if (!na.isEmpty()) {
//                return getSet(new DescriptionNotAllowed(na, desc));
//            } else {
//                return Collections.emptySet();
//            }
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLDataValueRestriction desc) {
//            Set<ConstructNotAllowed> na = desc.getValue().accept(this);
//            if (!na.isEmpty()) {
//                return getSet(new DescriptionNotAllowed(na, desc));
//            } else {
//                return Collections.emptySet();
//            }
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLObjectAllRestriction desc) {
//            Set<ConstructNotAllowed> na = desc.getFiller().accept(this);
//            if (!na.isEmpty()) {
//                return getSet(new DescriptionNotAllowed(na, desc));
//            } else {
//                return Collections.emptySet();
//            }
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLObjectComplementOf desc) {
//            Set<ConstructNotAllowed> na = desc.getOperand().accept(this);
//            if (!na.isEmpty()) {
//                return getSet(new DescriptionNotAllowed(na, desc));
//            } else {
//                return Collections.emptySet();
//            }
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLObjectExactCardinalityRestriction desc) {
//            if (propertyManager.isNonSimple(desc.getProperty())) {
//                return getSet(new NonSimplePropertyInCardinalityRestriction(desc));
//            } else {
//                return Collections.emptySet();
//            }
//
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLObjectIntersectionOf desc) {
//            for (OWLDescription op : desc.getOperands()) {
//                Set<ConstructNotAllowed> na = op.accept(this);
//                if (!na.isEmpty()) {
//                    return getSet(new DescriptionNotAllowed(na, desc));
//                }
//            }
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLObjectMaxCardinalityRestriction desc) {
//            if (propertyManager.isNonSimple(desc.getProperty())) {
//                return getSet(new NonSimplePropertyInCardinalityRestriction(desc));
//            } else {
//                return Collections.emptySet();
//            }
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLObjectMinCardinalityRestriction desc) {
//            if (propertyManager.isNonSimple(desc.getProperty())) {
//                return getSet(new NonSimplePropertyInCardinalityRestriction(desc));
//            } else {
//                return Collections.emptySet();
//            }
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLObjectOneOf desc) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLObjectSelfRestriction desc) {
//            if (propertyManager.isNonSimple(desc.getProperty())) {
//                return getSet(new NonSimplePropertyInExistsSelfRestriction(desc));
//            }
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLObjectSomeRestriction desc) {
//            Set<ConstructNotAllowed> na = desc.getFiller().accept(this);
//            if (!na.isEmpty()) {
//                return getSet(new DescriptionNotAllowed(na, desc));
//            } else {
//                return Collections.emptySet();
//            }
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLObjectUnionOf desc) {
//            for (OWLDescription op : desc.getOperands()) {
//                Set<ConstructNotAllowed> na = op.accept(this);
//                if (!na.isEmpty()) {
//                    return getSet(new DescriptionNotAllowed(na, desc));
//                }
//            }
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLObjectValueRestriction desc) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLDataComplementOf node) {
//            Set<ConstructNotAllowed> na = node.getDataRange().accept(this);
//            if (!na.isEmpty()) {
//                return getSet(new DataRangeNotAllowed(na, node));
//            } else {
//                return Collections.emptySet();
//            }
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLDataOneOf node) {
//            for (OWLLiteral op : node.getValues()) {
//                Set<ConstructNotAllowed> na = op.accept(this);
//                if (!na.isEmpty()) {
//                    return getSet(new DataRangeNotAllowed(na, node));
//                }
//            }
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLDataRangeFacetRestriction node) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLDataRangeRestriction node) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLDatatype node) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLTypedLiteral node) {
//            Set<ConstructNotAllowed> na = node.getDataType().accept(this);
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLRDFTextLiteral node) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLDataProperty property) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLObjectProperty property) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLObjectPropertyInverse property) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLIndividual individual) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLConstantAnnotation annotation) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLObjectAnnotation annotation) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(SWRLAtomConstantObject node) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(SWRLAtomDVariable node) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(SWRLAtomIndividualObject node) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(SWRLAtomIVariable node) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(SWRLBuiltInAtom node) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(SWRLClassAtom node) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(SWRLDataRangeAtom node) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(SWRLDataValuedPropertyAtom node) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(SWRLDifferentFromAtom node) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(SWRLObjectPropertyAtom node) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(SWRLSameAsAtom node) {
//            return Collections.emptySet();
//        }
//
//
//        public Set<ConstructNotAllowed> visit(OWLOntology ontology) {
//            return Collections.emptySet();
//        }
//    }
//
//    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
//    //
//    //  Not allowed constructs
//    //
//    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//
//    public class AnnotationValueNotAllowed extends ConstructNotAllowed<OWLAnnotation> {
//
//
//        public AnnotationValueNotAllowed(OWLAnnotation annotation) {
//            super(annotation);
//        }
//
//
//        public AnnotationValueNotAllowed(ConstructNotAllowed cause, OWLAnnotation construct) {
//            super(cause, construct);
//        }
//    }
//
//
//    public class OWL2DataRangeNotAllowed extends DataRangeNotAllowed {
//
//        public OWL2DataRangeNotAllowed(ConstructNotAllowed cause, OWLDataRange construct) {
//            super(cause, construct);
//        }
//
//
//        public OWL2DataRangeNotAllowed(OWLDataRange construct) {
//            super(construct);
//        }
//    }
//
//
//    public class NonSimplePropertyInFunctionalPropertyAxiom extends AxiomNotAllowed<OWLFunctionalObjectPropertyAxiom> {
//
//        public NonSimplePropertyInFunctionalPropertyAxiom(OWLFunctionalObjectPropertyAxiom construct) {
//            super(construct);
//        }
//    }
//
//    public class CycleInPropertyChain extends ConstructNotAllowed<List<OWLObjectPropertyExpression>> {
//        public CycleInPropertyChain(List<OWLObjectPropertyExpression> construct) {
//            super(construct);
//        }
//    }
//
//    public class NonSimplePropertyInCardinalityRestriction extends DescriptionNotAllowed {
//
//        public NonSimplePropertyInCardinalityRestriction(OWLObjectCardinalityRestriction construct) {
//            super(construct);
//        }
//
//    }
//
//    public class NonSimplePropertyInInverseFunctionalPropertyAxiom extends AxiomNotAllowed {
//
//        public NonSimplePropertyInInverseFunctionalPropertyAxiom(OWLAxiom construct) {
//            super(construct);
//        }
//    }
//
//    public class NonSimplePropertyInAntiSymmetricPropertyAxiom extends AxiomNotAllowed {
//
//        public NonSimplePropertyInAntiSymmetricPropertyAxiom(OWLAxiom construct) {
//            super(construct);
//        }
//    }
//
//    public class NonSimplePropertyInIrreflexivePropertyAxiom extends AxiomNotAllowed {
//
//        public NonSimplePropertyInIrreflexivePropertyAxiom(OWLAxiom construct) {
//            super(construct);
//        }
//    }
//
//
//    public class NonSimplePropertyInDisjointPropertiesAxiom extends AxiomNotAllowed {
//
//        private Set<OWLObjectPropertyExpression> props;
//
//        public NonSimplePropertyInDisjointPropertiesAxiom(OWLAxiom construct, Set<OWLObjectPropertyExpression> properties) {
//            super(construct);
//            props = properties;
//        }
//
//
//        public Set<OWLObjectPropertyExpression> getProperties() {
//            return props;
//        }
//    }
//
//
//    public class NonSimplePropertyInExistsSelfRestriction extends DescriptionNotAllowed {
//
//        public NonSimplePropertyInExistsSelfRestriction(ConstructNotAllowed cause, OWLDescription construct) {
//            super(cause, construct);
//        }
//
//
//        public NonSimplePropertyInExistsSelfRestriction(Set<ConstructNotAllowed> cause, OWLDescription construct) {
//            super(cause, construct);
//        }
//
//
//        public NonSimplePropertyInExistsSelfRestriction(OWLDescription construct) {
//            super(construct);
//        }
//    }
//
//    public class DataPropertyURIUsedAsObjectPropertyURI extends ConstructNotAllowed<URI> {
//
//        public DataPropertyURIUsedAsObjectPropertyURI(URI uri) {
//            super(uri);
//        }
//
//
//        public String toString() {
//            StringBuilder sb = new StringBuilder();
//            sb.append("<");
//            sb.append(getConstruct());
//            sb.append(">  is used as both a data and object property URI");
//            return sb.toString();
//        }
//    }
//
//
//    public static void main(String[] args) {
//        try {
//            OWLOntologyManager man = OWLManager.createOWLOntologyManager();
//            OWLOntology ont = man.loadOntologyFromPhysicalURI(URI.create("http://xmlns.com/foaf/0.1"));
//            OWL2Profile p = new OWL2Profile();
//            OWLProfileReport rp = p.checkOntology(ont, man);
//            System.out.println(rp);
//
//        }
//        catch (OWLOntologyCreationException e) {
//            e.printStackTrace();
//        }
//    }
}
