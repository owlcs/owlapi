package org.semanticweb.owl.profiles;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.util.OWLObjectPropertyManager;

import java.net.URI;
import java.util.*;
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
 * <p/>
 * Checks that an ontology can be saved as an OWL 1 document.  This isn't a strict
 * check - it pays no attention to the original concrete representation of the ontology.
 * This means, for example, that aspects of a valid RDF graph such as structure sharing
 * are not validated.
 * <p/>
 * Specifically, this profile ensures that:
 * <p/>
 * <ul>
 * <li>Punning is not used</li>
 * <li>Only simple properties are used in number restrictions (including functional properties)</li>
 * </ul>
 * <p/>
 * Additionally, no OWL 2 constructs can be used:
 * <ul>
 * <li>No use of QCRs</li>
 * <li>No use sub property axioms whose sub property is a chain</li>
 * <li>No use of OWL 2 data ranges</li>
 * <li>No use of disjoint classes axioms where more than two classes are declared to be disjoint</li>
 * <li>No use disjoint properties axioms</li>
 * <li>No use reflexive, antisymmetric, irreflexive property axioms</li>
 * </ul>
 */
public class OWLDLProfile implements OWLProfile {

    private OWLObjectPropertyManager propertyManager;


    public String getName() {
        return "OWL DL";
    }


    public OWLProfileReport checkOntology(OWLOntology ontology, OWLOntologyManager manager) {
        propertyManager = new OWLObjectPropertyManager(manager, ontology);

        Set<ConstructNotAllowed> notAllowed = new HashSet<ConstructNotAllowed>();
        AxiomChecker checker = new AxiomChecker();
        for (OWLOntology ont : manager.getImportsClosure(ontology)) {
            for (OWLAxiom ax : ont.getAxioms()) {
                ConstructNotAllowed na = ax.accept(checker);
                if (na != null) {
                    notAllowed.add(na);
                }
            }
        }

        Map<URI, Set<OWLEntity>> uri2EntityMap = new HashMap<URI, Set<OWLEntity>>();
        Set<URI> annotationURIs = new HashSet<URI>();
        for (OWLOntology ont : manager.getImportsClosure(ontology)) {
            for (OWLClass cls : ont.getReferencedClasses()) {
                mapEntity(cls, uri2EntityMap);
            }
            for (OWLObjectProperty prop : ont.getReferencedObjectProperties()) {
                mapEntity(prop, uri2EntityMap);
            }
            for (OWLDataProperty prop : ont.getReferencedDataProperties()) {
                mapEntity(prop, uri2EntityMap);
            }
            for (OWLIndividual ind : ont.getReferencedIndividuals()) {
                mapEntity(ind, uri2EntityMap);
            }
            annotationURIs.addAll(ont.getAnnotationURIs());
        }
        for(URI uri : uri2EntityMap.keySet()) {
            Set<OWLEntity> entities = uri2EntityMap.get(uri);
            if(entities.size() > 2) {
                // We have punning!
                notAllowed.add(new PunningNotAllowed(new HashSet<OWLEntity>(entities)));
            }
            if(annotationURIs.contains(uri)) {
                notAllowed.add(new PunningWithAnnotationURI(entities));
            }
        }


        return new OWLProfileReport(this, ontology.getURI(), notAllowed);
    }


    private static void mapEntity(OWLEntity entity, Map<URI, Set<OWLEntity>> map) {
        Set<OWLEntity> entities = map.get(entity.getURI());
        if (entities == null) {
            entities = new HashSet<OWLEntity>(2);
            map.put(entity.getURI(), entities);
        }
        entities.add(entity);
    }


    private class AxiomChecker implements OWLObjectVisitorEx<ConstructNotAllowed> {


        public ConstructNotAllowed visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {
            return new OWL2AxiomNotAllowed(axiom, false);
        }


        public ConstructNotAllowed visit(OWLAxiomAnnotationAxiom axiom) {
            return new OWL2AxiomNotAllowed(axiom, false);
        }


        public ConstructNotAllowed visit(OWLClassAssertionAxiom axiom) {
            ConstructNotAllowed descNA = axiom.getDescription().accept(this);
            if (descNA != null) {
                return new AxiomNotAllowed(descNA, axiom);
            }
            else {
                return null;
            }
        }


        public ConstructNotAllowed visit(OWLDataPropertyAssertionAxiom axiom) {
            return null;
        }


        public ConstructNotAllowed visit(OWLDataPropertyDomainAxiom axiom) {
            ConstructNotAllowed na = axiom.getDomain().accept(this);
            if (na != null) {
                return new AxiomNotAllowed(na, axiom);
            }
            return null;
        }


        public ConstructNotAllowed visit(OWLDataPropertyRangeAxiom axiom) {
            ConstructNotAllowed na = axiom.getRange().accept(this);
            if (na != null) {
                return new AxiomNotAllowed(na, axiom);
            }
            else {
                return null;
            }
        }


        public ConstructNotAllowed visit(OWLDataSubPropertyAxiom axiom) {
            return null;
        }


        public ConstructNotAllowed visit(OWLDeclarationAxiom axiom) {
            return null;
        }


        public ConstructNotAllowed visit(OWLDifferentIndividualsAxiom axiom) {
            return null;
        }


        public ConstructNotAllowed visit(OWLDisjointClassesAxiom axiom) {
            if (axiom.getDescriptions().size() > 2) {
                return new DisjointClassAxiomNotAllowed(axiom);
            }
            for(OWLDescription desc : axiom.getDescriptions()) {
                ConstructNotAllowed na = desc.accept(this);
                if(na != null) {
                    return new AxiomNotAllowed(na, axiom);
                }
            }
            return null;
        }


        public ConstructNotAllowed visit(OWLDisjointDataPropertiesAxiom axiom) {
            return new OWL2AxiomNotAllowed(axiom, false);
        }


        public ConstructNotAllowed visit(OWLDisjointObjectPropertiesAxiom axiom) {
            return new OWL2AxiomNotAllowed(axiom, false);
        }


        public ConstructNotAllowed visit(OWLDisjointUnionAxiom axiom) {
            return new OWL2AxiomNotAllowed(axiom, true);
        }


        public ConstructNotAllowed visit(OWLEntityAnnotationAxiom axiom) {
            ConstructNotAllowed na = axiom.getAnnotation().accept(this);
            if (na != null) {
                return new AxiomNotAllowed(na, axiom);
            }
            else {
                return null;
            }
        }


        public ConstructNotAllowed visit(OWLEquivalentClassesAxiom axiom) {
            for(OWLDescription desc : axiom.getDescriptions()) {
                ConstructNotAllowed na = desc.accept(this);
                if(na != null) {
                    return new AxiomNotAllowed(na, axiom);
                }
            }
            return null;
        }


        public ConstructNotAllowed visit(OWLEquivalentDataPropertiesAxiom axiom) {
            return null;
        }


        public ConstructNotAllowed visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            return null;
        }


        public ConstructNotAllowed visit(OWLFunctionalDataPropertyAxiom axiom) {
            return null;
        }


        public ConstructNotAllowed visit(OWLFunctionalObjectPropertyAxiom axiom) {
            if (propertyManager.isNonSimple(axiom.getProperty())) {
                return new NonSimplePropertiesNotAllowedInFunctionalPropertyAxioms(axiom);
            }
            else {
                return null;
            }
        }


        public ConstructNotAllowed visit(OWLImportsDeclaration axiom) {
            return null;
        }


        public ConstructNotAllowed visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            return null;
        }


        public ConstructNotAllowed visit(OWLInverseObjectPropertiesAxiom axiom) {
            return null;
        }


        public ConstructNotAllowed visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            return new OWL2AxiomNotAllowed(axiom, false);
        }


        public ConstructNotAllowed visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            return new OWL2AxiomNotAllowed(axiom, true);
        }


        public ConstructNotAllowed visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            return new OWL2AxiomNotAllowed(axiom, true);
        }


        public ConstructNotAllowed visit(OWLObjectPropertyAssertionAxiom axiom) {
            return null;
        }


        public ConstructNotAllowed visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
            return new OWL2AxiomNotAllowed(axiom, false);
        }


        public ConstructNotAllowed visit(OWLObjectPropertyDomainAxiom axiom) {
            ConstructNotAllowed na = axiom.getDomain().accept(this);
            if (na != null) {
                return new AxiomNotAllowed(na, axiom);
            }
            else {
                return null;
            }
        }


        public ConstructNotAllowed visit(OWLObjectPropertyRangeAxiom axiom) {
            ConstructNotAllowed na = axiom.getRange().accept(this);
            if (na != null) {
                return new AxiomNotAllowed(na, axiom);
            }
            else {
                return null;
            }
        }


        public ConstructNotAllowed visit(OWLObjectSubPropertyAxiom axiom) {
            return null;
        }


        public ConstructNotAllowed visit(OWLOntologyAnnotationAxiom axiom) {
            return null;
        }


        public ConstructNotAllowed visit(OWLReflexiveObjectPropertyAxiom axiom) {
            return new OWL2AxiomNotAllowed(axiom, false);
        }


        public ConstructNotAllowed visit(OWLSameIndividualsAxiom axiom) {
            return null;
        }


        public ConstructNotAllowed visit(OWLSubClassAxiom axiom) {
            return null;
        }


        public ConstructNotAllowed visit(OWLSymmetricObjectPropertyAxiom axiom) {
            return null;
        }


        public ConstructNotAllowed visit(OWLTransitiveObjectPropertyAxiom axiom) {
            return null;
        }


        public ConstructNotAllowed visit(SWRLRule rule) {
            for (SWRLAtom atom : rule.getBody()) {

            }
            return null;
        }


        public ConstructNotAllowed visit(OWLClass desc) {
            return null;
        }


        public ConstructNotAllowed visit(OWLDataAllRestriction desc) {
            ConstructNotAllowed na = desc.getFiller().accept(this);
            if (na != null) {
                return new DescriptionNotAllowed(na, desc);
            }
            else {
                return null;
            }
        }


        public ConstructNotAllowed visit(OWLDataExactCardinalityRestriction desc) {
            if (desc.isQualified()) {
                return new QCRsNotAllowed(desc);
            }
            else {
                return null;
            }
        }


        public ConstructNotAllowed visit(OWLDataMaxCardinalityRestriction desc) {
            if (desc.isQualified()) {
                return new QCRsNotAllowed(desc);
            }
            else {
                return null;
            }
        }


        public ConstructNotAllowed visit(OWLDataMinCardinalityRestriction desc) {
            if (desc.isQualified()) {
                return new QCRsNotAllowed(desc);
            }
            else {
                return null;
            }
        }


        public ConstructNotAllowed visit(OWLDataSomeRestriction desc) {
            ConstructNotAllowed na = desc.getFiller().accept(this);
            if (na != null) {
                return new DescriptionNotAllowed(na, desc);
            }
            else {
                return null;
            }
        }


        public ConstructNotAllowed visit(OWLDataValueRestriction desc) {
            ConstructNotAllowed na = desc.getValue().accept(this);
            if (na != null) {
                return new DescriptionNotAllowed(na, desc);
            }
            else {
                return null;
            }
        }


        public ConstructNotAllowed visit(OWLObjectAllRestriction desc) {
            ConstructNotAllowed na = desc.getFiller().accept(this);
            if (na != null) {
                return new DescriptionNotAllowed(na, desc);
            }
            else {
                return null;
            }
        }


        public ConstructNotAllowed visit(OWLObjectComplementOf desc) {
            ConstructNotAllowed na = desc.getOperand().accept(this);
            if (na != null) {
                return new DescriptionNotAllowed(na, desc);
            }
            else {
                return null;
            }
        }


        public ConstructNotAllowed visit(OWLObjectExactCardinalityRestriction desc) {
            if (desc.isQualified()) {
                return new QCRsNotAllowed(desc);
            }
            else {
                if (propertyManager.isNonSimple(desc.getProperty())) {
                    return new NonSimplePropertiesNotAllowedInCardinalityRestrictions(desc);
                }
                else {
                    return null;
                }
            }
        }


        public ConstructNotAllowed visit(OWLObjectIntersectionOf desc) {
            for (OWLDescription op : desc.getOperands()) {
                ConstructNotAllowed na = op.accept(this);
                if (na != null) {
                    return new DescriptionNotAllowed(na, desc);
                }
            }
            return null;
        }


        public ConstructNotAllowed visit(OWLObjectMaxCardinalityRestriction desc) {
            if (desc.isQualified()) {
                return new QCRsNotAllowed(desc);
            }
            else {
                if (propertyManager.isNonSimple(desc.getProperty())) {
                    return new NonSimplePropertiesNotAllowedInCardinalityRestrictions(desc);
                }
                else {
                    return null;
                }
            }
        }


        public ConstructNotAllowed visit(OWLObjectMinCardinalityRestriction desc) {
            if (desc.isQualified()) {
                return new QCRsNotAllowed(desc);
            }
            else {
                if (propertyManager.isNonSimple(desc.getProperty())) {
                    return new NonSimplePropertiesNotAllowedInCardinalityRestrictions(desc);
                }
                else {
                    return null;
                }
            }
        }


        public ConstructNotAllowed visit(OWLObjectOneOf desc) {
            return null;
        }


        public ConstructNotAllowed visit(OWLObjectSelfRestriction desc) {
            return new SelfRestrictionsNotAllowed(desc);
        }


        public ConstructNotAllowed visit(OWLObjectSomeRestriction desc) {
            ConstructNotAllowed na = desc.getFiller().accept(this);
            if (na != null) {
                return new DescriptionNotAllowed(na, desc);
            }
            else {
                return null;
            }
        }


        public ConstructNotAllowed visit(OWLObjectUnionOf desc) {
            for (OWLDescription op : desc.getOperands()) {
                ConstructNotAllowed na = op.accept(this);
                if (na != null) {
                    return new DescriptionNotAllowed(na, desc);
                }
            }
            return null;
        }


        public ConstructNotAllowed visit(OWLObjectValueRestriction desc) {
            return null;
        }


        public ConstructNotAllowed visit(OWLDataComplementOf node) {
            ConstructNotAllowed na = node.getDataRange().accept(this);
            if (na != null) {
                return new DataRangeNotAllowed(na, node);
            }
            else {
                return null;
            }
        }


        public ConstructNotAllowed visit(OWLDataOneOf node) {
            for (OWLLiteral op : node.getValues()) {
                ConstructNotAllowed na = op.accept(this);
                if (na != null) {
                    return new DataRangeNotAllowed(na, node);
                }
            }
            return null;
        }


        public ConstructNotAllowed visit(OWLDataRangeFacetRestriction node) {
            return null;
        }


        public ConstructNotAllowed visit(OWLDataRangeRestriction node) {
            return new OWL2DataRangeNotAllowed(node);
        }


        public ConstructNotAllowed visit(OWLDataType node) {
            return null;
        }


        public ConstructNotAllowed visit(OWLTypedLiteral node) {
            ConstructNotAllowed na = node.getDataType().accept(this);
            return null;
        }


        public ConstructNotAllowed visit(OWLRDFTextLiteral node) {
            return null;
        }


        public ConstructNotAllowed visit(OWLDataProperty property) {
            // TODO: Shouldn't be the top property?
            return null;
        }


        public ConstructNotAllowed visit(OWLObjectProperty property) {
            // TODO: shouldn't be the top property?
            return null;
        }


        public ConstructNotAllowed visit(OWLObjectPropertyInverse property) {
            return new PropertyInversesNotAllowed(property);
        }


        public ConstructNotAllowed visit(OWLIndividual individual) {
            return null;
        }


        public ConstructNotAllowed visit(OWLConstantAnnotation annotation) {
            ConstructNotAllowed na = annotation.getAnnotationValue().accept(this);
            if (na == null) {
                return null;
            }
            else {
                return new AnnotationValueNotAllowed(na, annotation);
            }
        }


        public ConstructNotAllowed visit(OWLObjectAnnotation annotation) {
            ConstructNotAllowed na = annotation.getAnnotationValue().accept(this);
            if (na == null) {
                return null;
            }
            else {
                return new AnnotationValueNotAllowed(na, annotation);
            }
        }


        public ConstructNotAllowed visit(SWRLAtomConstantObject node) {
            return null;
        }


        public ConstructNotAllowed visit(SWRLAtomDVariable node) {
            return null;
        }


        public ConstructNotAllowed visit(SWRLAtomIndividualObject node) {
            return null;
        }


        public ConstructNotAllowed visit(SWRLAtomIVariable node) {
            return null;
        }


        public ConstructNotAllowed visit(SWRLBuiltInAtom node) {
            return null;
        }


        public ConstructNotAllowed visit(SWRLClassAtom node) {
            return null;
        }


        public ConstructNotAllowed visit(SWRLDataRangeAtom node) {
            return null;
        }


        public ConstructNotAllowed visit(SWRLDataValuedPropertyAtom node) {
            return null;
        }


        public ConstructNotAllowed visit(SWRLDifferentFromAtom node) {
            return null;
        }


        public ConstructNotAllowed visit(SWRLObjectPropertyAtom node) {
            return null;
        }


        public ConstructNotAllowed visit(SWRLSameAsAtom node) {
            return null;
        }


        public ConstructNotAllowed visit(OWLOntology ontology) {
            return null;
        }
    }


    private class OWL2AxiomNotAllowed extends AxiomNotAllowed {

        private boolean canBeRewritten;

        public OWL2AxiomNotAllowed(OWLAxiom construct, boolean canBeRewritten) {
            super(construct);
            this.canBeRewritten = canBeRewritten;
        }


        public boolean isCanBeRewrittenIntoOWLDL() {
            return canBeRewritten;
        }
    }


    private class DisjointClassAxiomNotAllowed extends AxiomNotAllowed {


        public DisjointClassAxiomNotAllowed(OWLDisjointClassesAxiom axiom) {
            super(axiom);
        }
    }


    private class AnnotationValueNotAllowed extends ConstructNotAllowed<OWLAnnotation> {


        public AnnotationValueNotAllowed(OWLAnnotation annotation) {
            super(annotation);
        }


        public AnnotationValueNotAllowed(ConstructNotAllowed cause, OWLAnnotation construct) {
            super(cause, construct);
        }
    }


    private class QCRsNotAllowed extends DescriptionNotAllowed {

        public QCRsNotAllowed(OWLDescription construct) {
            super(construct);
        }
    }


    private class SelfRestrictionsNotAllowed extends DescriptionNotAllowed {

        public SelfRestrictionsNotAllowed(OWLDescription construct) {
            super(construct);
        }
    }


    private class OWL2DataRangeNotAllowed extends DataRangeNotAllowed {

        public OWL2DataRangeNotAllowed(ConstructNotAllowed cause, OWLDataRange construct) {
            super(cause, construct);
        }


        public OWL2DataRangeNotAllowed(OWLDataRange construct) {
            super(construct);
        }
    }


    private class PropertyInversesNotAllowed extends ConstructNotAllowed {

        public PropertyInversesNotAllowed(ConstructNotAllowed cause, Object construct) {
            super(cause, construct);
        }


        public PropertyInversesNotAllowed(OWLObjectPropertyInverse construct) {
            super(construct);
        }
    }


    private class NonSimplePropertiesNotAllowedInFunctionalPropertyAxioms extends AxiomNotAllowed {

        public NonSimplePropertiesNotAllowedInFunctionalPropertyAxioms(OWLAxiom construct) {
            super(construct);
        }
    }


    private class NonSimplePropertiesNotAllowedInCardinalityRestrictions extends DescriptionNotAllowed {

        public NonSimplePropertiesNotAllowedInCardinalityRestrictions(OWLObjectCardinalityRestriction construct) {
            super(construct);
        }
    }


    private class PunningNotAllowed extends ConstructNotAllowed<Set<OWLEntity>> {

        public PunningNotAllowed(Set<OWLEntity> construct) {
            super(construct);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            Set<OWLEntity> entities = getConstruct();
            sb.append("Punning is not allowed: <");
            sb.append(entities.iterator().next().getURI());
            sb.append("> is used as ");
            for(Iterator<OWLEntity> it = entities.iterator(); it.hasNext(); ) {
                OWLEntity ent = it.next();
                if(ent.isOWLClass()) {
                    sb.append("a class URI");
                }
                else if(ent.isOWLObjectProperty()) {
                    sb.append("an object property URI");
                }
                else if(ent.isOWLDataProperty()) {
                    sb.append("a data property URI");
                }
                else if(ent.isOWLIndividual()) {
                    sb.append("an individual URI");
                }
                if(it.hasNext()) {
                    sb.append(", ");
                }
            }
            return sb.toString();
        }
    }

    private class PunningWithAnnotationURI extends ConstructNotAllowed<Set<OWLEntity>> {

        public PunningWithAnnotationURI(Set<OWLEntity> construct) {
            super(construct);
        }


        public String toString() {
            StringBuilder sb = new StringBuilder();
            Set<OWLEntity> entities = getConstruct();
            sb.append("Punning is not allowed: <");
            sb.append(entities.iterator().next().getURI());
            sb.append(">");
            sb.append(" is used as an annotation property URI, ");
            for(Iterator<OWLEntity> it = entities.iterator(); it.hasNext(); ) {
                OWLEntity ent = it.next();
                if(ent.isOWLClass()) {
                    sb.append("a class URI");
                }
                else if(ent.isOWLObjectProperty()) {
                    sb.append("an object property URI");
                }
                else if(ent.isOWLDataProperty()) {
                    sb.append("a data property URI");
                }
                else if(ent.isOWLIndividual()) {
                    sb.append("an individual URI");
                }
                if(it.hasNext()) {
                    sb.append(", ");
                }
            }
            return sb.toString();
        }
    }
}
