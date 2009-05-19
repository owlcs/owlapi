package org.semanticweb.owl.util;

import org.semanticweb.owl.model.*;

import java.net.URI;
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
 * Date: 11-Dec-2006<br><br>
 */
public class OWLObjectDuplicator implements OWLObjectVisitor, SWRLObjectVisitor {

    private OWLDataFactory dataFactory;

    private Object obj;

    private Map<OWLEntity, URI> replacementMap;


    /**
     * Creates an object duplicator that duplicates objects using the specified
     * data factory.
     *
     * @param dataFactory The data factory to be used for the duplication.
     */
    public OWLObjectDuplicator(OWLDataFactory dataFactory) {
        this(new HashMap<OWLEntity, URI>(), dataFactory);
    }


    /**
     * Creates an object duplicator that duplicates objects using the specified
     * data factory and uri replacement map.
     *
     * @param dataFactory       The data factory to be used for the duplication.
     * @param uriReplacementMap The map to use for the replacement of URIs.  Any uris
     *                          the appear in the map will be replaced as objects are duplicated.  This can
     *                          be used to "rename" entities.
     */
    public OWLObjectDuplicator(OWLDataFactory dataFactory, Map<URI, URI> uriReplacementMap) {
        this.dataFactory = dataFactory;
        this.replacementMap = new HashMap<OWLEntity, URI>();
        for (URI uri : uriReplacementMap.keySet()) {
            URI repURI = uriReplacementMap.get(uri);
            replacementMap.put(dataFactory.getOWLClass(uri), repURI);
            replacementMap.put(dataFactory.getOWLObjectProperty(uri), repURI);
            replacementMap.put(dataFactory.getOWLDataProperty(uri), repURI);
            replacementMap.put(dataFactory.getOWLNamedIndividual(uri), repURI);
        }
    }

    /**
     * Creates an object duplicator that duplicates objects using the specified
     * data factory and uri replacement map.
     *
     * @param dataFactory             The data factory to be used for the duplication.
     * @param entityURIReplacementMap The map to use for the replacement of URIs.  Any uris
     *                                the appear in the map will be replaced as objects are duplicated.  This can
     *                                be used to "rename" entities.
     */
    public OWLObjectDuplicator(Map<OWLEntity, URI> entityURIReplacementMap, OWLDataFactory dataFactory) {
        this.dataFactory = dataFactory;
        this.replacementMap = new HashMap<OWLEntity, URI>(entityURIReplacementMap);
    }

    public <O extends OWLObject> O duplicateObject(OWLObject object) {
        object.accept(this);
        return (O) obj;
    }

    protected Object getLastObject() {
        return obj;
    }

    protected void setLastObject(Object obj) {
        this.obj = obj;
    }


    /**
     * Given a URI, returns a URI.  This may be the same URI, or
     * an alternative URI if a replacement has been specified.
     */
    private URI getURI(OWLEntity entity) {
        URI replacement = replacementMap.get(entity);
        if (replacement != null) {
            return replacement;
        } else {
            return entity.getURI();
        }
    }

    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        obj = dataFactory.getOWLAsymmetricObjectPropertyAxiom((OWLObjectPropertyExpression) obj);
    }

    public void visit(OWLClassAssertionAxiom axiom) {
        axiom.getIndividual().accept(this);
        OWLIndividual ind = (OWLIndividual) obj;
        axiom.getClassExpression().accept(this);
        OWLClassExpression type = (OWLClassExpression) obj;
        obj = dataFactory.getOWLClassAssertionAxiom(ind, type);
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        OWLIndividual subj = (OWLIndividual) obj;
        axiom.getProperty().accept(this);
        OWLDataPropertyExpression prop = (OWLDataPropertyExpression) obj;
        axiom.getObject().accept(this);
        OWLLiteral con = (OWLLiteral) obj;
        obj = dataFactory.getOWLDataPropertyAssertionAxiom(subj, prop, con);
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        axiom.getProperty().accept(this);
        OWLDataPropertyExpression prop = (OWLDataPropertyExpression) obj;
        axiom.getDomain().accept(this);
        OWLClassExpression domain = (OWLClassExpression) obj;
        obj = dataFactory.getOWLDataPropertyDomainAxiom(prop, domain);
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
        axiom.getProperty().accept(this);
        OWLDataPropertyExpression prop = (OWLDataPropertyExpression) obj;
        axiom.getRange().accept(this);
        OWLDataRange range = (OWLDataRange) obj;
        obj = dataFactory.getOWLDataPropertyRangeAxiom(prop, range);
    }


    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        OWLDataPropertyExpression subProp = (OWLDataPropertyExpression) obj;
        axiom.getSuperProperty().accept(this);
        OWLDataPropertyExpression supProp = (OWLDataPropertyExpression) obj;
        obj = dataFactory.getOWLSubDataPropertyOfAxiom(subProp, supProp);
    }


    public void visit(OWLDeclarationAxiom axiom) {
        axiom.getEntity().accept(this);
        OWLEntity ent = (OWLEntity) obj;
        obj = dataFactory.getOWLDeclarationAxiom(ent);
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        Set<OWLIndividual> inds = duplicateSet(axiom.getIndividuals());
        obj = dataFactory.getOWLDifferentIndividualsAxiom(inds);
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        Set<OWLClassExpression> descs = duplicateSet(axiom.getClassExpressions());
        obj = dataFactory.getOWLDisjointClassesAxiom(descs);
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        Set<OWLDataPropertyExpression> props = duplicateSet(axiom.getProperties());
        obj = dataFactory.getOWLDisjointDataPropertiesAxiom(props);
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        Set<OWLObjectPropertyExpression> props = duplicateSet(axiom.getProperties());
        obj = dataFactory.getOWLDisjointObjectPropertiesAxiom(props);
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        axiom.getOWLClass().accept(this);
        OWLClass cls = (OWLClass) obj;
        Set<OWLClassExpression> ops = duplicateSet(axiom.getClassExpressions());
        obj = dataFactory.getOWLDisjointUnionAxiom(cls, ops);
    }


    public void visit(OWLAnnotationAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        OWLEntity entity = (OWLEntity) obj;
        axiom.getAnnotation().accept(this);
        OWLAnnotation anno = (OWLAnnotation) obj;
        obj = dataFactory.getOWLAnnotationAssertionAxiom(entity.getIRI(), anno);
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        Set<OWLClassExpression> descs = duplicateSet(axiom.getClassExpressions());
        obj = dataFactory.getOWLEquivalentClassesAxiom(descs);
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        Set<OWLDataPropertyExpression> props = duplicateSet(axiom.getProperties());
        obj = dataFactory.getOWLEquivalentDataPropertiesAxiom(props);
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        Set<OWLObjectPropertyExpression> props = duplicateSet(axiom.getProperties());
        obj = dataFactory.getOWLEquivalentObjectPropertiesAxiom(props);
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        obj = dataFactory.getOWLFunctionalDataPropertyAxiom((OWLDataPropertyExpression) obj);
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        obj = dataFactory.getOWLFunctionalObjectPropertyAxiom((OWLObjectPropertyExpression) obj);
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        obj = dataFactory.getOWLInverseFunctionalObjectPropertyAxiom((OWLObjectPropertyExpression) obj);
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        axiom.getFirstProperty().accept(this);
        OWLObjectPropertyExpression propA = (OWLObjectPropertyExpression) obj;
        axiom.getSecondProperty().accept(this);
        OWLObjectPropertyExpression propB = (OWLObjectPropertyExpression) obj;
        obj = dataFactory.getOWLInverseObjectPropertiesAxiom(propA, propB);
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        obj = dataFactory.getOWLIrreflexiveObjectPropertyAxiom((OWLObjectPropertyExpression) obj);
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        OWLIndividual ind = (OWLIndividual) obj;
        axiom.getProperty().accept(this);
        OWLDataPropertyExpression prop = (OWLDataPropertyExpression) obj;
        axiom.getObject().accept(this);
        OWLLiteral con = (OWLLiteral) obj;
        obj = dataFactory.getOWLNegativeDataPropertyAssertionAxiom(ind, prop, con);
    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        OWLIndividual ind = (OWLIndividual) obj;
        axiom.getProperty().accept(this);
        OWLObjectPropertyExpression prop = (OWLObjectPropertyExpression) obj;
        axiom.getObject().accept(this);
        OWLIndividual ind2 = (OWLIndividual) obj;
        obj = dataFactory.getOWLNegativeObjectPropertyAssertionAxiom(ind, prop, ind2);
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        OWLIndividual ind = (OWLIndividual) obj;
        axiom.getProperty().accept(this);
        OWLObjectPropertyExpression prop = (OWLObjectPropertyExpression) obj;
        axiom.getObject().accept(this);
        OWLIndividual ind2 = (OWLIndividual) obj;
        obj = dataFactory.getOWLObjectPropertyAssertionAxiom(ind, prop, ind2);
    }


    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        axiom.getSuperProperty().accept(this);
        OWLObjectPropertyExpression prop = (OWLObjectPropertyExpression) obj;
        List<OWLObjectPropertyExpression> chain = new ArrayList<OWLObjectPropertyExpression>();
        for (OWLObjectPropertyExpression p : axiom.getPropertyChain()) {
            p.accept(this);
            chain.add((OWLObjectPropertyExpression) obj);
        }
        obj = dataFactory.getOWLSubPropertyChainOfAxiom(chain, prop);
    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        axiom.getProperty().accept(this);
        OWLObjectPropertyExpression prop = (OWLObjectPropertyExpression) obj;
        axiom.getDomain().accept(this);
        OWLClassExpression domain = (OWLClassExpression) obj;
        obj = dataFactory.getOWLObjectPropertyDomainAxiom(prop, domain);
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        axiom.getProperty().accept(this);
        OWLObjectPropertyExpression prop = (OWLObjectPropertyExpression) obj;
        axiom.getRange().accept(this);
        OWLClassExpression range = (OWLClassExpression) obj;
        obj = dataFactory.getOWLObjectPropertyRangeAxiom(prop, range);
    }


    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        OWLObjectPropertyExpression subProp = (OWLObjectPropertyExpression) obj;
        axiom.getSuperProperty().accept(this);
        OWLObjectPropertyExpression supProp = (OWLObjectPropertyExpression) obj;
        obj = dataFactory.getOWLSubObjectPropertyOfAxiom(subProp, supProp);
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        obj = dataFactory.getOWLReflexiveObjectPropertyAxiom((OWLObjectPropertyExpression) obj);
    }


    public void visit(OWLSameIndividualAxiom axiom) {
        Set<OWLIndividual> individuals = duplicateSet(axiom.getIndividuals());
        obj = dataFactory.getOWLSameIndividualAxiom(individuals);
    }


    public void visit(OWLSubClassOfAxiom axiom) {
        axiom.getSubClass().accept(this);
        OWLClassExpression subClass = (OWLClassExpression) obj;
        axiom.getSuperClass().accept(this);
        OWLClassExpression supClass = (OWLClassExpression) obj;
        obj = dataFactory.getOWLSubClassOfAxiom(subClass, supClass);
    }


    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        obj = dataFactory.getOWLSymmetricObjectPropertyAxiom((OWLObjectPropertyExpression) obj);
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        obj = dataFactory.getOWLTransitiveObjectPropertyAxiom((OWLObjectPropertyExpression) obj);
    }


    public void visit(OWLClass desc) {
        URI uri = getURI(desc);
        obj = dataFactory.getOWLClass(uri);
    }


    public void visit(OWLDataAllValuesFrom desc) {
        desc.getProperty().accept(this);
        OWLDataPropertyExpression prop = (OWLDataPropertyExpression) obj;
        desc.getFiller().accept(this);
        OWLDataRange filler = (OWLDataRange) obj;
        obj = dataFactory.getOWLDataAllValuesFrom(prop, filler);
    }


    public void visit(OWLDataExactCardinality desc) {
        desc.getProperty().accept(this);
        OWLDataPropertyExpression prop = (OWLDataPropertyExpression) obj;
        desc.getFiller().accept(this);
        OWLDataRange filler = (OWLDataRange) obj;
        obj = dataFactory.getOWLDataExactCardinality(prop, desc.getCardinality(), filler);
    }


    public void visit(OWLDataMaxCardinality desc) {
        desc.getProperty().accept(this);
        OWLDataPropertyExpression prop = (OWLDataPropertyExpression) obj;
        desc.getFiller().accept(this);
        OWLDataRange filler = (OWLDataRange) obj;
        obj = dataFactory.getOWLDataMaxCardinality(prop, desc.getCardinality(), filler);
    }


    public void visit(OWLDataMinCardinality desc) {
        desc.getProperty().accept(this);
        OWLDataPropertyExpression prop = (OWLDataPropertyExpression) obj;
        desc.getFiller().accept(this);
        OWLDataRange filler = (OWLDataRange) obj;
        obj = dataFactory.getOWLDataMinCardinality(prop, desc.getCardinality(), filler);
    }


    public void visit(OWLDataSomeValuesFrom desc) {
        desc.getProperty().accept(this);
        OWLDataPropertyExpression prop = (OWLDataPropertyExpression) obj;
        desc.getFiller().accept(this);
        OWLDataRange filler = (OWLDataRange) obj;
        obj = dataFactory.getOWLDataSomeValuesFrom(prop, filler);
    }


    public void visit(OWLDataHasValue desc) {
        desc.getProperty().accept(this);
        OWLDataPropertyExpression prop = (OWLDataPropertyExpression) obj;
        desc.getValue().accept(this);
        OWLLiteral val = (OWLLiteral) obj;
        obj = dataFactory.getOWLDataHasValue(prop, val);
    }


    public void visit(OWLObjectAllValuesFrom desc) {
        desc.getProperty().accept(this);
        OWLObjectPropertyExpression prop = (OWLObjectPropertyExpression) obj;
        desc.getFiller().accept(this);
        OWLClassExpression filler = (OWLClassExpression) obj;
        obj = dataFactory.getOWLObjectAllValuesFrom(prop, filler);
    }


    public void visit(OWLObjectComplementOf desc) {
        desc.getOperand().accept(this);
        OWLClassExpression op = (OWLClassExpression) obj;
        obj = dataFactory.getOWLObjectComplementOf(op);
    }


    public void visit(OWLObjectExactCardinality desc) {
        desc.getProperty().accept(this);
        OWLObjectPropertyExpression prop = (OWLObjectPropertyExpression) obj;
        desc.getFiller().accept(this);
        OWLClassExpression filler = (OWLClassExpression) obj;
        obj = dataFactory.getOWLObjectExactCardinality(prop, desc.getCardinality(), filler);
    }


    public void visit(OWLObjectIntersectionOf desc) {
        Set<OWLClassExpression> ops = duplicateSet(desc.getOperands());
        obj = dataFactory.getOWLObjectIntersectionOf(ops);
    }


    public void visit(OWLObjectMaxCardinality desc) {
        desc.getProperty().accept(this);
        OWLObjectPropertyExpression prop = (OWLObjectPropertyExpression) obj;
        desc.getFiller().accept(this);
        OWLClassExpression filler = (OWLClassExpression) obj;
        obj = dataFactory.getOWLObjectMaxCardinality(prop, desc.getCardinality(), filler);
    }


    public void visit(OWLObjectMinCardinality desc) {
        desc.getProperty().accept(this);
        OWLObjectPropertyExpression prop = (OWLObjectPropertyExpression) obj;
        desc.getFiller().accept(this);
        OWLClassExpression filler = (OWLClassExpression) obj;
        obj = dataFactory.getOWLObjectMinCardinality(prop, desc.getCardinality(), filler);
    }


    public void visit(OWLObjectOneOf desc) {
        Set<OWLIndividual> inds = duplicateSet(desc.getIndividuals());
        obj = dataFactory.getOWLObjectOneOf(inds);
    }


    public void visit(OWLObjectHasSelf desc) {
        desc.getProperty().accept(this);
        OWLObjectPropertyExpression prop = (OWLObjectPropertyExpression) obj;
        obj = dataFactory.getOWLObjectHasSelf(prop);
    }


    public void visit(OWLObjectSomeValuesFrom desc) {
        desc.getProperty().accept(this);
        OWLObjectPropertyExpression prop = (OWLObjectPropertyExpression) obj;
        desc.getFiller().accept(this);
        OWLClassExpression filler = (OWLClassExpression) obj;
        obj = dataFactory.getOWLObjectSomeValuesFrom(prop, filler);
    }


    public void visit(OWLObjectUnionOf desc) {
        Set<OWLClassExpression> ops = duplicateSet(desc.getOperands());
        obj = dataFactory.getOWLObjectUnionOf(ops);
    }


    public void visit(OWLObjectHasValue desc) {
        desc.getProperty().accept(this);
        OWLObjectPropertyExpression prop = (OWLObjectPropertyExpression) obj;
        desc.getValue().accept(this);
        OWLIndividual value = (OWLIndividual) obj;
        obj = dataFactory.getOWLObjectHasValue(prop, value);
    }


    public void visit(OWLDataComplementOf node) {
        node.getDataRange().accept(this);
        OWLDataRange dr = (OWLDataRange) obj;
        obj = dataFactory.getOWLDataComplementOf(dr);
    }


    public void visit(OWLDataOneOf node) {
        Set<OWLLiteral> vals = duplicateSet(node.getValues());
        obj = dataFactory.getOWLDataOneOf(vals);
    }


    public void visit(OWLDatatype node) {
        URI uri = getURI(node);
        obj = dataFactory.getOWLDatatype(uri);
    }


    public void visit(OWLDatatypeRestriction node) {
        node.getDatatype().accept(this);
        OWLDatatype dr = (OWLDatatype) obj;
        Set<OWLFacetRestriction> restrictions = new HashSet<OWLFacetRestriction>();
        for (OWLFacetRestriction restriction : node.getFacetRestrictions()) {
            restriction.accept(this);
            restrictions.add((OWLFacetRestriction) obj);
        }
        obj = dataFactory.getOWLDatatypeRestriction(dr, restrictions);
    }


    public void visit(OWLFacetRestriction node) {
        node.getFacetValue().accept(this);
        OWLTypedLiteral val = (OWLTypedLiteral) obj;
        obj = dataFactory.getOWLFacetRestriction(node.getFacet(), val);
    }


    public void visit(OWLTypedLiteral node) {
        node.getDatatype().accept(this);
        OWLDatatype dt = (OWLDatatype) obj;
        obj = dataFactory.getOWLTypedLiteral(node.getLiteral(), dt);
    }


    public void visit(OWLStringLiteral node) {
        obj = dataFactory.getOWLStringLiteral(node.getLiteral(), node.getLang());
    }


    public void visit(OWLDataProperty property) {
        URI uri = getURI(property);
        obj = dataFactory.getOWLDataProperty(uri);
    }


    public void visit(OWLObjectProperty property) {
        URI uri = getURI(property);
        obj = dataFactory.getOWLObjectProperty(uri);
    }


    public void visit(OWLObjectPropertyInverse property) {
        property.getInverse().accept(this);
        OWLObjectPropertyExpression prop = (OWLObjectPropertyExpression) obj;
        obj = dataFactory.getOWLObjectPropertyInverse(prop);
    }

    public void visit(OWLNamedIndividual individual) {
        URI uri = getURI(individual);
        obj = dataFactory.getOWLNamedIndividual(uri);
    }


    public void visit(OWLOntology ontology) {
        // Should we duplicate ontologies here? Probably not.
        obj = ontology;
    }


    public void visit(SWRLRule rule) {
        Set<SWRLAtom> antecedents = new HashSet<SWRLAtom>();
        Set<SWRLAtom> consequents = new HashSet<SWRLAtom>();
        for (SWRLAtom atom : rule.getBody()) {
            atom.accept(this);
            antecedents.add((SWRLAtom) obj);
        }
        for (SWRLAtom atom : rule.getHead()) {
            atom.accept(this);
            consequents.add((SWRLAtom) obj);
        }
        obj = dataFactory.getSWRLRule(rule.getURI(), antecedents, consequents);
    }


    public void visit(SWRLClassAtom node) {
        node.getPredicate().accept(this);
        OWLClassExpression desc = (OWLClassExpression) obj;
        node.getArgument().accept(this);
        SWRLAtomIObject atom = (SWRLAtomIObject) obj;
        obj = dataFactory.getSWRLClassAtom(desc, atom);
    }


    public void visit(SWRLDataRangeAtom node) {
        node.getPredicate().accept(this);
        OWLDataRange rng = (OWLDataRange) obj;
        node.getArgument().accept(this);
        SWRLAtomDObject atom = (SWRLAtomDObject) obj;
        obj = dataFactory.getSWRLDataRangeAtom(rng, atom);
    }


    public void visit(SWRLObjectPropertyAtom node) {
        node.getPredicate().accept(this);
        OWLObjectPropertyExpression exp = (OWLObjectPropertyExpression) obj;
        node.getFirstArgument().accept(this);
        SWRLAtomIObject arg0 = (SWRLAtomIObject) obj;
        node.getSecondArgument().accept(this);
        SWRLAtomIObject arg1 = (SWRLAtomIObject) obj;
        obj = dataFactory.getSWRLObjectPropertyAtom(exp, arg0, arg1);
    }


    public void visit(SWRLDataValuedPropertyAtom node) {
        node.getPredicate().accept(this);
        OWLDataPropertyExpression exp = (OWLDataPropertyExpression) obj;
        node.getFirstArgument().accept(this);
        SWRLAtomIObject arg0 = (SWRLAtomIObject) obj;
        node.getSecondArgument().accept(this);
        SWRLAtomDObject arg1 = (SWRLAtomDObject) obj;
        obj = dataFactory.getSWRLDataValuedPropertyAtom(exp, arg0, arg1);
    }


    public void visit(SWRLBuiltInAtom node) {
        List<SWRLAtomDObject> atomObjects = new ArrayList<SWRLAtomDObject>();
        for (SWRLAtomDObject atomObject : node.getArguments()) {
            atomObject.accept(this);
            atomObjects.add((SWRLAtomDObject) obj);
        }
        obj = dataFactory.getSWRLBuiltInAtom(node.getPredicate(), atomObjects);
    }


    public void visit(SWRLDifferentFromAtom node) {
        node.getFirstArgument().accept(this);
        SWRLAtomIObject arg0 = (SWRLAtomIObject) obj;
        node.getSecondArgument().accept(this);
        SWRLAtomIObject arg1 = (SWRLAtomIObject) obj;
        obj = dataFactory.getSWRLDifferentFromAtom(arg0, arg1);
    }


    public void visit(SWRLSameAsAtom node) {
        node.getFirstArgument().accept(this);
        SWRLAtomIObject arg0 = (SWRLAtomIObject) obj;
        node.getSecondArgument().accept(this);
        SWRLAtomIObject arg1 = (SWRLAtomIObject) obj;
        obj = dataFactory.getSWRLSameAsAtom(arg0, arg1);
    }


    public void visit(SWRLAtomDVariable node) {
        obj = dataFactory.getSWRLAtomDVariable(node.getURI());
    }


    public void visit(SWRLAtomIVariable node) {
        obj = dataFactory.getSWRLAtomIVariable(node.getURI());
    }


    public void visit(SWRLAtomIndividualObject node) {
        node.getIndividual().accept(this);
        OWLIndividual ind = (OWLIndividual) obj;
        obj = dataFactory.getSWRLAtomIndividualObject(ind);
    }


    public void visit(SWRLAtomConstantObject node) {
        node.getConstant().accept(this);
        OWLLiteral con = (OWLLiteral) obj;
        obj = dataFactory.getSWRLAtomConstantObject(con);
    }

    public void visit(OWLHasKeyAxiom axiom) {
        axiom.getClassExpression().accept(this);
        OWLClassExpression ce = (OWLClassExpression) obj;
        Set<OWLPropertyExpression> props = duplicateSet(axiom.getPropertyExpressions());
        obj = dataFactory.getOWLHasKeyAxiom(ce, props);
    }

    public void visit(OWLDataIntersectionOf node) {
        Set<OWLDataRange> ranges = duplicateSet(node.getOperands());
        obj = dataFactory.getOWLDataIntersectionOf(ranges);
    }

    public void visit(OWLDataUnionOf node) {
        Set<OWLDataRange> ranges = duplicateSet(node.getOperands());
        obj = dataFactory.getOWLDataUnionOf(ranges);
    }


    public void visit(OWLAnnotationProperty property) {
        obj = dataFactory.getOWLAnnotationProperty(getURI(property));
    }

    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        axiom.getProperty().accept(this);
        OWLAnnotationProperty prop = (OWLAnnotationProperty) obj;
        axiom.getDomain().accept(this);
        IRI domain = (IRI) obj;
        obj = dataFactory.getOWLAnnotationPropertyDomainAxiom(prop, domain);
    }

    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        axiom.getProperty().accept(this);
        OWLAnnotationProperty prop = (OWLAnnotationProperty) obj;
        axiom.getRange().accept(this);
        IRI range = (IRI) obj;
        obj = dataFactory.getOWLAnnotationPropertyRangeAxiom(prop, range);
    }

    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        OWLAnnotationProperty sub = (OWLAnnotationProperty) obj;
        axiom.getSuperProperty().accept(this);
        OWLAnnotationProperty sup = (OWLAnnotationProperty) obj;
        obj = dataFactory.getOWLSubAnnotationPropertyOfAxiom(sub, sup);
    }


    public void visit(OWLAnnotation node) {
        node.getProperty().accept(this);
        OWLAnnotationProperty prop = (OWLAnnotationProperty) obj;
        node.getValue().accept(this);
        OWLAnnotationValue val = (OWLAnnotationValue) obj;
        obj = dataFactory.getOWLAnnotation(prop, val);
    }


    public void visit(OWLAnonymousIndividual individual) {
        obj = individual;
    }

    public void visit(IRI iri) {
        URI replacement = replacementMap.get(iri.toURI());
        if (replacement != null) {
            obj = dataFactory.getIRI(replacement);
        } else {
            obj = iri;
        }
    }


    public void visit(OWLDatatypeDefinition axiom) {
        axiom.getDatatype().accept(this);
        OWLDatatype dt = (OWLDatatype) obj;
        axiom.getDataRange().accept(this);
        OWLDataRange rng = (OWLDataRange) obj;
        obj = dataFactory.getOWLDatatypeDefinition(dt, rng);
    }


    /**
     * A utility function that duplicates a set of objects.
     *
     * @param objs The set of object to be duplicated
     */
    private <O extends OWLObject> Set<O> duplicateSet(Set<O> objs) {
        Set<O> dup = new HashSet<O>();
        for (O o : objs) {
            o.accept(this);
            dup.add((O) obj);
        }
        return dup;
    }


    /**
     * A utility function that duplicates a set of objects.
     *
     * @param objs The set of object to be duplicated
     */
    private <O extends SWRLObject> Set<O> duplicateSWRLObjectSet(Set<O> objs) {
        Set<O> dup = new HashSet<O>();
        for (O o : objs) {
            o.accept(this);
            dup.add((O) obj);
        }
        return dup;
    }
}
