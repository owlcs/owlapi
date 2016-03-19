/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.*;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLAnnotationPropertyTransformer implements OWLObjectVisitor, SWRLObjectVisitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(OWLAnnotationPropertyTransformer.class);
    @Nonnull private final OWLDataFactory dataFactory;
    private Object obj;
    @Nonnull private final Map<OWLEntity, OWLEntity> replacementMap;
    protected RemappingIndividualProvider anonProvider;

    /**
     * Creates an object duplicator that duplicates objects using the specified
     * data factory.
     * 
     * @param dataFactory
     *        The data factory to be used for the duplication.
     */
    public OWLAnnotationPropertyTransformer(@Nonnull OWLDataFactory dataFactory) {
        this(new HashMap<OWLEntity, OWLEntity>(), dataFactory);
    }

    /**
     * Creates an object duplicator that duplicates objects using the specified
     * data factory and uri replacement map.
     * 
     * @param dataFactory
     *        The data factory to be used for the duplication.
     * @param iriReplacementMap
     *        The map to use for the replacement of URIs. Any uris the appear in
     *        the map will be replaced as objects are duplicated. This can be
     *        used to "rename" entities.
     */
    public OWLAnnotationPropertyTransformer(@Nonnull OWLDataFactory dataFactory,
        @Nonnull Map<OWLEntity, OWLEntity> iriReplacementMap) {
        this.dataFactory = checkNotNull(dataFactory, "dataFactory cannot be null");
        checkNotNull(iriReplacementMap, "iriReplacementMap cannot be null");
        replacementMap = new HashMap<>(iriReplacementMap);
    }

    /**
     * Creates an object duplicator that duplicates objects using the specified
     * data factory and uri replacement map.
     * 
     * @param dataFactory
     *        The data factory to be used for the duplication.
     * @param entityIRIReplacementMap
     *        The map to use for the replacement of URIs. Any uris the appear in
     *        the map will be replaced as objects are duplicated. This can be
     *        used to "rename" entities.
     */
    public OWLAnnotationPropertyTransformer(@Nonnull Map<OWLEntity, OWLEntity> entityIRIReplacementMap,
        @Nonnull OWLDataFactory dataFactory) {
        this.dataFactory = checkNotNull(dataFactory, "dataFactory cannot be null");
        anonProvider = new RemappingIndividualProvider(this.dataFactory);
        replacementMap = new HashMap<>(checkNotNull(entityIRIReplacementMap, "entityIRIReplacementMap cannot be null"));
    }

    /**
     * @param object
     *        the object to duplicate
     * @return the duplicate
     * @param <O>
     *        return type
     */
    @Nonnull
    public <O extends OWLObject> O transformObject(@Nonnull O object) {
        checkNotNull(object, "object cannot be null");
        try {
            object.accept(this);
        } catch (ClassCastException e) {
            LOGGER.error(
                "Attempt to transform an axiom to correct misuse of properties failed. Property replacement: {}, axiom: {}, error: {}",
                replacementMap, object, e.getMessage());
            obj = object;
        }
        return getLastObject();
    }

    protected void setLastObject(@Nonnull Object obj) {
        this.obj = obj;
    }

    @SuppressWarnings({ "unchecked", })
    @Nonnull
    protected <O> O getLastObject() {
        return (O) obj;
    }

    /**
     * Given an IRI belonging to an entity, returns a IRI. This may be the same
     * IRI that the entity has, or an alternative IRI if a replacement has been
     * specified.
     * 
     * @param entity
     *        The entity
     * @return The IRI
     */
    @Nonnull
    private OWLEntity getIRI(@Nonnull OWLEntity entity) {
        checkNotNull(entity, "entity cannot be null");
        OWLEntity replacement = replacementMap.get(entity);
        if (replacement != null) {
            return replacement;
        } else {
            return entity;
        }
    }

    @Nonnull
    private Set<OWLAnnotation> duplicateAxiomAnnotations(@Nonnull OWLAxiom axiom) {
        checkNotNull(axiom, "axiom cannot be null");
        Set<OWLAnnotation> duplicatedAnnos = new HashSet<>();
        for (OWLAnnotation anno : axiom.getAnnotations()) {
            anno.accept(this);
            duplicatedAnnos.add((OWLAnnotation) getLastObject());
        }
        return duplicatedAnnos;
    }

    @Override
    public void visit(@Nonnull OWLAsymmetricObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        obj = dataFactory.getOWLAsymmetricObjectPropertyAxiom((OWLObjectPropertyExpression) getLastObject(),
            duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLClassAssertionAxiom axiom) {
        axiom.getIndividual().accept(this);
        OWLIndividual ind = getLastObject();
        axiom.getClassExpression().accept(this);
        OWLClassExpression type = getLastObject();
        obj = dataFactory.getOWLClassAssertionAxiom(type, ind, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        OWLIndividual subj = getLastObject();
        axiom.getProperty().accept(this);
        OWLDataPropertyExpression prop = getLastObject();
        axiom.getObject().accept(this);
        OWLLiteral con = getLastObject();
        obj = dataFactory.getOWLDataPropertyAssertionAxiom(prop, subj, con, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyDomainAxiom axiom) {
        axiom.getProperty().accept(this);
        OWLDataPropertyExpression prop = getLastObject();
        axiom.getDomain().accept(this);
        OWLClassExpression domain = getLastObject();
        obj = dataFactory.getOWLDataPropertyDomainAxiom(prop, domain, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyRangeAxiom axiom) {
        axiom.getProperty().accept(this);
        OWLDataPropertyExpression prop = getLastObject();
        axiom.getRange().accept(this);
        OWLDataRange range = getLastObject();
        obj = dataFactory.getOWLDataPropertyRangeAxiom(prop, range, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLSubDataPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        OWLDataPropertyExpression subProp = getLastObject();
        axiom.getSuperProperty().accept(this);
        OWLDataPropertyExpression supProp = getLastObject();
        obj = dataFactory.getOWLSubDataPropertyOfAxiom(subProp, supProp, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLDeclarationAxiom axiom) {
        axiom.getEntity().accept(this);
        OWLEntity ent = getLastObject();
        obj = dataFactory.getOWLDeclarationAxiom(ent, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLDifferentIndividualsAxiom axiom) {
        Set<OWLIndividual> inds = duplicateSet(axiom.getIndividuals());
        obj = dataFactory.getOWLDifferentIndividualsAxiom(inds, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLDisjointClassesAxiom axiom) {
        Set<OWLClassExpression> descs = duplicateSet(axiom.getClassExpressions());
        obj = dataFactory.getOWLDisjointClassesAxiom(descs, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLDisjointDataPropertiesAxiom axiom) {
        Set<OWLDataPropertyExpression> props = duplicateSet(axiom.getProperties());
        obj = dataFactory.getOWLDisjointDataPropertiesAxiom(props, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLDisjointObjectPropertiesAxiom axiom) {
        Set<OWLObjectPropertyExpression> props = duplicateSet(axiom.getProperties());
        obj = dataFactory.getOWLDisjointObjectPropertiesAxiom(props, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLDisjointUnionAxiom axiom) {
        axiom.getOWLClass().accept(this);
        OWLClass cls = getLastObject();
        Set<OWLClassExpression> ops = duplicateSet(axiom.getClassExpressions());
        obj = dataFactory.getOWLDisjointUnionAxiom(cls, ops, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLAnnotationAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        OWLAnnotationSubject subject = getLastObject();
        axiom.getProperty().accept(this);
        OWLProperty prop = getLastObject();
        axiom.getValue().accept(this);
        OWLAnnotationValue value = getLastObject();
        if (prop.isObjectPropertyExpression()) {
            // turn to object property assertion
            OWLIndividual individual;
            OWLIndividual relatedIndividual;
            if (subject instanceof OWLAnonymousIndividual) {
                individual = (OWLIndividual) subject;
            } else {
                individual = dataFactory.getOWLNamedIndividual((IRI) subject);
            }
            if (value instanceof OWLIndividual) {
                relatedIndividual = (OWLIndividual) value;
            } else {
                relatedIndividual = dataFactory.getOWLNamedIndividual((IRI) value);
            }
            obj = dataFactory.getOWLObjectPropertyAssertionAxiom(prop.asOWLObjectProperty(), individual,
                relatedIndividual, axiom.getAnnotations());
            return;
        } else if (prop.isDataPropertyExpression()) {
            // turn to data property assertion
            OWLIndividual individual;
            if (subject instanceof OWLAnonymousIndividual) {
                individual = (OWLIndividual) subject;
            } else {
                individual = dataFactory.getOWLNamedIndividual((IRI) subject);
            }
            obj = dataFactory.getOWLDataPropertyAssertionAxiom(prop.asOWLDataProperty(), individual, (OWLLiteral) value,
                axiom.getAnnotations());
            return;
        }
        obj = dataFactory.getOWLAnnotationAssertionAxiom(prop.asOWLAnnotationProperty(), subject, value,
            duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLEquivalentClassesAxiom axiom) {
        Set<OWLClassExpression> descs = duplicateSet(axiom.getClassExpressions());
        obj = dataFactory.getOWLEquivalentClassesAxiom(descs, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLEquivalentDataPropertiesAxiom axiom) {
        Set<OWLDataPropertyExpression> props = duplicateSet(axiom.getProperties());
        obj = dataFactory.getOWLEquivalentDataPropertiesAxiom(props, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLEquivalentObjectPropertiesAxiom axiom) {
        Set<OWLObjectPropertyExpression> props = duplicateSet(axiom.getProperties());
        obj = dataFactory.getOWLEquivalentObjectPropertiesAxiom(props, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLFunctionalDataPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        OWLDataPropertyExpression obj2 = getLastObject();
        obj = dataFactory.getOWLFunctionalDataPropertyAxiom(obj2, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLFunctionalObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        OWLObjectPropertyExpression obj2 = getLastObject();
        obj = dataFactory.getOWLFunctionalObjectPropertyAxiom(obj2, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLInverseFunctionalObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        OWLObjectPropertyExpression obj2 = getLastObject();
        obj = dataFactory.getOWLInverseFunctionalObjectPropertyAxiom(obj2, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLInverseObjectPropertiesAxiom axiom) {
        axiom.getFirstProperty().accept(this);
        OWLObjectPropertyExpression propA = getLastObject();
        axiom.getSecondProperty().accept(this);
        OWLObjectPropertyExpression propB = getLastObject();
        obj = dataFactory.getOWLInverseObjectPropertiesAxiom(propA, propB, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLIrreflexiveObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        OWLObjectPropertyExpression obj2 = getLastObject();
        obj = dataFactory.getOWLIrreflexiveObjectPropertyAxiom(obj2, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLNegativeDataPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        OWLIndividual ind = getLastObject();
        axiom.getProperty().accept(this);
        OWLDataPropertyExpression prop = getLastObject();
        axiom.getObject().accept(this);
        OWLLiteral con = getLastObject();
        obj = dataFactory.getOWLNegativeDataPropertyAssertionAxiom(prop, ind, con, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLNegativeObjectPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        OWLIndividual ind = getLastObject();
        axiom.getProperty().accept(this);
        OWLObjectPropertyExpression prop = getLastObject();
        axiom.getObject().accept(this);
        OWLIndividual ind2 = getLastObject();
        obj = dataFactory.getOWLNegativeObjectPropertyAssertionAxiom(prop, ind, ind2, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        OWLIndividual ind = getLastObject();
        axiom.getProperty().accept(this);
        OWLObjectPropertyExpression prop = getLastObject();
        axiom.getObject().accept(this);
        OWLIndividual ind2 = getLastObject();
        obj = dataFactory.getOWLObjectPropertyAssertionAxiom(prop, ind, ind2, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLSubPropertyChainOfAxiom axiom) {
        axiom.getSuperProperty().accept(this);
        OWLObjectPropertyExpression prop = getLastObject();
        List<OWLObjectPropertyExpression> chain = new ArrayList<>();
        for (OWLObjectPropertyExpression p : axiom.getPropertyChain()) {
            p.accept(this);
            chain.add((OWLObjectPropertyExpression) getLastObject());
        }
        obj = dataFactory.getOWLSubPropertyChainOfAxiom(chain, prop, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyDomainAxiom axiom) {
        axiom.getProperty().accept(this);
        OWLObjectPropertyExpression prop = getLastObject();
        axiom.getDomain().accept(this);
        OWLClassExpression domain = getLastObject();
        obj = dataFactory.getOWLObjectPropertyDomainAxiom(prop, domain, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyRangeAxiom axiom) {
        axiom.getProperty().accept(this);
        OWLObjectPropertyExpression prop = getLastObject();
        axiom.getRange().accept(this);
        OWLClassExpression range = getLastObject();
        obj = dataFactory.getOWLObjectPropertyRangeAxiom(prop, range, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLSubObjectPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        OWLObjectPropertyExpression subProp = getLastObject();
        axiom.getSuperProperty().accept(this);
        OWLObjectPropertyExpression supProp = getLastObject();
        obj = dataFactory.getOWLSubObjectPropertyOfAxiom(subProp, supProp, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLReflexiveObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        OWLObjectPropertyExpression prop = getLastObject();
        obj = dataFactory.getOWLReflexiveObjectPropertyAxiom(prop, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLSameIndividualAxiom axiom) {
        Set<OWLIndividual> individuals = duplicateSet(axiom.getIndividuals());
        obj = dataFactory.getOWLSameIndividualAxiom(individuals, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLSubClassOfAxiom axiom) {
        axiom.getSubClass().accept(this);
        OWLClassExpression subClass = getLastObject();
        axiom.getSuperClass().accept(this);
        OWLClassExpression supClass = getLastObject();
        obj = dataFactory.getOWLSubClassOfAxiom(subClass, supClass, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLSymmetricObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        OWLObjectPropertyExpression prop = getLastObject();
        obj = dataFactory.getOWLSymmetricObjectPropertyAxiom(prop, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLTransitiveObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        OWLObjectPropertyExpression prop = getLastObject();
        obj = dataFactory.getOWLTransitiveObjectPropertyAxiom(prop, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLClass ce) {
        obj = getIRI(ce);
    }

    @Override
    public void visit(@Nonnull OWLDataAllValuesFrom ce) {
        ce.getProperty().accept(this);
        OWLDataPropertyExpression prop = getLastObject();
        ce.getFiller().accept(this);
        OWLDataRange filler = getLastObject();
        obj = dataFactory.getOWLDataAllValuesFrom(prop, filler);
    }

    @Override
    public void visit(@Nonnull OWLDataExactCardinality ce) {
        ce.getProperty().accept(this);
        OWLDataPropertyExpression prop = getLastObject();
        ce.getFiller().accept(this);
        OWLDataRange filler = getLastObject();
        obj = dataFactory.getOWLDataExactCardinality(ce.getCardinality(), prop, filler);
    }

    @Override
    public void visit(@Nonnull OWLDataMaxCardinality ce) {
        ce.getProperty().accept(this);
        OWLDataPropertyExpression prop = getLastObject();
        ce.getFiller().accept(this);
        OWLDataRange filler = getLastObject();
        obj = dataFactory.getOWLDataMaxCardinality(ce.getCardinality(), prop, filler);
    }

    @Override
    public void visit(@Nonnull OWLDataMinCardinality ce) {
        ce.getProperty().accept(this);
        OWLDataPropertyExpression prop = getLastObject();
        ce.getFiller().accept(this);
        OWLDataRange filler = getLastObject();
        obj = dataFactory.getOWLDataMinCardinality(ce.getCardinality(), prop, filler);
    }

    @Override
    public void visit(@Nonnull OWLDataSomeValuesFrom ce) {
        ce.getProperty().accept(this);
        OWLDataPropertyExpression prop = getLastObject();
        ce.getFiller().accept(this);
        OWLDataRange filler = getLastObject();
        obj = dataFactory.getOWLDataSomeValuesFrom(prop, filler);
    }

    @Override
    public void visit(@Nonnull OWLDataHasValue ce) {
        ce.getProperty().accept(this);
        OWLDataPropertyExpression prop = getLastObject();
        ce.getFiller().accept(this);
        OWLLiteral val = getLastObject();
        obj = dataFactory.getOWLDataHasValue(prop, val);
    }

    @Override
    public void visit(@Nonnull OWLObjectAllValuesFrom ce) {
        ce.getProperty().accept(this);
        OWLObjectPropertyExpression prop = getLastObject();
        ce.getFiller().accept(this);
        OWLClassExpression filler = getLastObject();
        obj = dataFactory.getOWLObjectAllValuesFrom(prop, filler);
    }

    @Override
    public void visit(@Nonnull OWLObjectComplementOf ce) {
        ce.getOperand().accept(this);
        OWLClassExpression op = getLastObject();
        obj = dataFactory.getOWLObjectComplementOf(op);
    }

    @Override
    public void visit(@Nonnull OWLObjectExactCardinality ce) {
        ce.getProperty().accept(this);
        OWLObjectPropertyExpression prop = getLastObject();
        ce.getFiller().accept(this);
        OWLClassExpression filler = getLastObject();
        obj = dataFactory.getOWLObjectExactCardinality(ce.getCardinality(), prop, filler);
    }

    @Override
    public void visit(@Nonnull OWLObjectIntersectionOf ce) {
        Set<OWLClassExpression> ops = duplicateSet(ce.getOperands());
        obj = dataFactory.getOWLObjectIntersectionOf(ops);
    }

    @Override
    public void visit(@Nonnull OWLObjectMaxCardinality ce) {
        ce.getProperty().accept(this);
        OWLObjectPropertyExpression prop = getLastObject();
        ce.getFiller().accept(this);
        OWLClassExpression filler = getLastObject();
        obj = dataFactory.getOWLObjectMaxCardinality(ce.getCardinality(), prop, filler);
    }

    @Override
    public void visit(@Nonnull OWLObjectMinCardinality ce) {
        ce.getProperty().accept(this);
        OWLObjectPropertyExpression prop = getLastObject();
        ce.getFiller().accept(this);
        OWLClassExpression filler = getLastObject();
        obj = dataFactory.getOWLObjectMinCardinality(ce.getCardinality(), prop, filler);
    }

    @Override
    public void visit(@Nonnull OWLObjectOneOf ce) {
        Set<OWLIndividual> inds = duplicateSet(ce.getIndividuals());
        obj = dataFactory.getOWLObjectOneOf(inds);
    }

    @Override
    public void visit(@Nonnull OWLObjectHasSelf ce) {
        ce.getProperty().accept(this);
        OWLObjectPropertyExpression prop = getLastObject();
        obj = dataFactory.getOWLObjectHasSelf(prop);
    }

    @Override
    public void visit(@Nonnull OWLObjectSomeValuesFrom ce) {
        ce.getProperty().accept(this);
        OWLObjectPropertyExpression prop = getLastObject();
        ce.getFiller().accept(this);
        OWLClassExpression filler = getLastObject();
        obj = dataFactory.getOWLObjectSomeValuesFrom(prop, filler);
    }

    @Override
    public void visit(@Nonnull OWLObjectUnionOf ce) {
        Set<OWLClassExpression> ops = duplicateSet(ce.getOperands());
        obj = dataFactory.getOWLObjectUnionOf(ops);
    }

    @Override
    public void visit(@Nonnull OWLObjectHasValue ce) {
        ce.getProperty().accept(this);
        OWLObjectPropertyExpression prop = getLastObject();
        ce.getFiller().accept(this);
        OWLIndividual value = getLastObject();
        obj = dataFactory.getOWLObjectHasValue(prop, value);
    }

    @Override
    public void visit(@Nonnull OWLDataComplementOf node) {
        node.getDataRange().accept(this);
        OWLDataRange dr = getLastObject();
        obj = dataFactory.getOWLDataComplementOf(dr);
    }

    @Override
    public void visit(@Nonnull OWLDataOneOf node) {
        Set<OWLLiteral> vals = duplicateSet(node.getValues());
        obj = dataFactory.getOWLDataOneOf(vals);
    }

    @Override
    public void visit(@Nonnull OWLDatatype node) {
        obj = getIRI(node);
    }

    @Override
    public void visit(@Nonnull OWLDatatypeRestriction node) {
        node.getDatatype().accept(this);
        OWLDatatype dr = getLastObject();
        Set<OWLFacetRestriction> restrictions = new HashSet<>();
        for (OWLFacetRestriction restriction : node.getFacetRestrictions()) {
            restriction.accept(this);
            restrictions.add((OWLFacetRestriction) getLastObject());
        }
        obj = dataFactory.getOWLDatatypeRestriction(dr, restrictions);
    }

    @Override
    public void visit(@Nonnull OWLFacetRestriction node) {
        node.getFacetValue().accept(this);
        OWLLiteral val = getLastObject();
        obj = dataFactory.getOWLFacetRestriction(node.getFacet(), val);
    }

    @Override
    public void visit(@Nonnull OWLLiteral node) {
        node.getDatatype().accept(this);
        OWLDatatype dt = getLastObject();
        if (node.hasLang()) {
            obj = dataFactory.getOWLLiteral(node.getLiteral(), node.getLang());
        } else {
            obj = dataFactory.getOWLLiteral(node.getLiteral(), dt);
        }
    }

    @Override
    public void visit(@Nonnull OWLDataProperty property) {
        obj = getIRI(property);
    }

    @Override
    public void visit(@Nonnull OWLObjectProperty property) {
        obj = getIRI(property);
    }

    @Override
    public void visit(@Nonnull OWLObjectInverseOf property) {
        property.getInverse().accept(this);
        OWLObjectPropertyExpression prop = getLastObject();
        obj = dataFactory.getOWLObjectInverseOf(prop);
    }

    @Override
    public void visit(@Nonnull OWLNamedIndividual individual) {
        obj = getIRI(individual);
    }

    @Override
    public void visit(OWLOntology ontology) {
        // Should we duplicate ontologies here? Probably not.
        obj = ontology;
    }

    @Override
    public void visit(@Nonnull SWRLRule rule) {
        Set<SWRLAtom> antecedents = new HashSet<>();
        Set<SWRLAtom> consequents = new HashSet<>();
        for (SWRLAtom atom : rule.getBody()) {
            atom.accept(this);
            antecedents.add((SWRLAtom) getLastObject());
        }
        for (SWRLAtom atom : rule.getHead()) {
            atom.accept(this);
            consequents.add((SWRLAtom) getLastObject());
        }
        obj = dataFactory.getSWRLRule(antecedents, consequents);
    }

    @Override
    public void visit(@Nonnull SWRLClassAtom node) {
        node.getPredicate().accept(this);
        OWLClassExpression desc = getLastObject();
        node.getArgument().accept(this);
        SWRLIArgument atom = getLastObject();
        obj = dataFactory.getSWRLClassAtom(desc, atom);
    }

    @Override
    public void visit(@Nonnull SWRLDataRangeAtom node) {
        node.getPredicate().accept(this);
        OWLDataRange rng = getLastObject();
        node.getArgument().accept(this);
        SWRLDArgument atom = getLastObject();
        obj = dataFactory.getSWRLDataRangeAtom(rng, atom);
    }

    @Override
    public void visit(@Nonnull SWRLObjectPropertyAtom node) {
        node.getPredicate().accept(this);
        OWLObjectPropertyExpression exp = getLastObject();
        node.getFirstArgument().accept(this);
        SWRLIArgument arg0 = getLastObject();
        node.getSecondArgument().accept(this);
        SWRLIArgument arg1 = getLastObject();
        obj = dataFactory.getSWRLObjectPropertyAtom(exp, arg0, arg1);
    }

    @Override
    public void visit(@Nonnull SWRLDataPropertyAtom node) {
        node.getPredicate().accept(this);
        OWLDataPropertyExpression exp = getLastObject();
        node.getFirstArgument().accept(this);
        SWRLIArgument arg0 = getLastObject();
        node.getSecondArgument().accept(this);
        SWRLDArgument arg1 = getLastObject();
        obj = dataFactory.getSWRLDataPropertyAtom(exp, arg0, arg1);
    }

    @Override
    public void visit(@Nonnull SWRLBuiltInAtom node) {
        List<SWRLDArgument> atomObjects = new ArrayList<>();
        for (SWRLDArgument atomObject : node.getArguments()) {
            atomObject.accept(this);
            atomObjects.add((SWRLDArgument) getLastObject());
        }
        obj = dataFactory.getSWRLBuiltInAtom(node.getPredicate(), atomObjects);
    }

    @Override
    public void visit(@Nonnull SWRLDifferentIndividualsAtom node) {
        node.getFirstArgument().accept(this);
        SWRLIArgument arg0 = getLastObject();
        node.getSecondArgument().accept(this);
        SWRLIArgument arg1 = getLastObject();
        obj = dataFactory.getSWRLDifferentIndividualsAtom(arg0, arg1);
    }

    @Override
    public void visit(@Nonnull SWRLSameIndividualAtom node) {
        node.getFirstArgument().accept(this);
        SWRLIArgument arg0 = getLastObject();
        node.getSecondArgument().accept(this);
        SWRLIArgument arg1 = getLastObject();
        obj = dataFactory.getSWRLSameIndividualAtom(arg0, arg1);
    }

    @Override
    public void visit(@Nonnull SWRLVariable node) {
        node.getIRI().accept(this);
        IRI iri = getLastObject();
        obj = dataFactory.getSWRLVariable(iri);
    }

    @Override
    public void visit(@Nonnull SWRLIndividualArgument node) {
        node.getIndividual().accept(this);
        OWLIndividual ind = getLastObject();
        obj = dataFactory.getSWRLIndividualArgument(ind);
    }

    @Override
    public void visit(@Nonnull SWRLLiteralArgument node) {
        node.getLiteral().accept(this);
        OWLLiteral con = getLastObject();
        obj = dataFactory.getSWRLLiteralArgument(con);
    }

    @Override
    public void visit(@Nonnull OWLHasKeyAxiom axiom) {
        axiom.getClassExpression().accept(this);
        OWLClassExpression ce = getLastObject();
        Set<OWLPropertyExpression> props = duplicateSet(axiom.getPropertyExpressions());
        obj = dataFactory.getOWLHasKeyAxiom(ce, props, duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLDataIntersectionOf node) {
        Set<OWLDataRange> ranges = duplicateSet(node.getOperands());
        obj = dataFactory.getOWLDataIntersectionOf(ranges);
    }

    @Override
    public void visit(@Nonnull OWLDataUnionOf node) {
        Set<OWLDataRange> ranges = duplicateSet(node.getOperands());
        obj = dataFactory.getOWLDataUnionOf(ranges);
    }

    @Override
    public void visit(@Nonnull OWLAnnotationProperty property) {
        obj = getIRI(property);
    }

    @Override
    public void visit(@Nonnull OWLAnnotationPropertyDomainAxiom axiom) {
        axiom.getProperty().accept(this);
        OWLProperty prop = getLastObject();
        axiom.getDomain().accept(this);
        IRI domain = getLastObject();
        if (prop.isObjectPropertyExpression()) {
            // turn to object property domain
            OWLClassExpression d = dataFactory.getOWLClass(domain);
            LOGGER.warn(
                "Annotation property domain axiom turned to object property domain after parsing. This could introduce errors if the original domain was an anonymous expression: {} is the new domain.",
                domain);
            obj = dataFactory.getOWLObjectPropertyDomainAxiom(prop.asOWLObjectProperty(), d, axiom.getAnnotations());
            return;
        } else if (prop.isDataPropertyExpression()) {
            // turn to data property domain
            OWLClassExpression d = dataFactory.getOWLClass(domain);
            LOGGER.warn(
                "Annotation property domain axiom turned to data property domain after parsing. This could introduce errors if the original domain was an anonymous expression: {} is the new domain.",
                domain);
            obj = dataFactory.getOWLDataPropertyDomainAxiom(prop.asOWLDataProperty(), d, axiom.getAnnotations());
            return;
        }
        obj = dataFactory.getOWLAnnotationPropertyDomainAxiom(prop.asOWLAnnotationProperty(), domain,
            duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLAnnotationPropertyRangeAxiom axiom) {
        axiom.getProperty().accept(this);
        OWLProperty prop = getLastObject();
        axiom.getRange().accept(this);
        IRI range = getLastObject();
        if (prop.isObjectPropertyExpression()) {
            // turn to object property domain
            OWLClassExpression d = dataFactory.getOWLClass(range);
            LOGGER.warn(
                "Annotation property range axiom turned to object property range after parsing. This could introduce errors if the original range was an anonymous expression: {} is the new domain.",
                range);
            obj = dataFactory.getOWLObjectPropertyRangeAxiom(prop.asOWLObjectProperty(), d, axiom.getAnnotations());
            return;
        } else if (prop.isDataPropertyExpression()) {
            // turn to data property domain
            OWLDataRange d = dataFactory.getOWLDatatype(range);
            LOGGER.warn(
                "Annotation property range axiom turned to data property range after parsing. This could introduce errors if the original range was an anonymous expression: {} is the new domain.",
                range);
            obj = dataFactory.getOWLDataPropertyRangeAxiom(prop.asOWLDataProperty(), d, axiom.getAnnotations());
            return;
        }
        obj = dataFactory.getOWLAnnotationPropertyRangeAxiom(prop.asOWLAnnotationProperty(), range,
            duplicateAxiomAnnotations(axiom));
    }

    @Override
    public void visit(@Nonnull OWLSubAnnotationPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        OWLProperty sub = getLastObject();
        axiom.getSuperProperty().accept(this);
        OWLProperty sup = getLastObject();
        if (sub.isObjectPropertyExpression() || sup.isObjectPropertyExpression()) {
            // check: it is possible that the properties represent an actual
            // illegal punning, where this fix cannot be applied
            if (sub.isOWLObjectProperty() && sup.isOWLObjectProperty()) {
                obj = dataFactory.getOWLSubObjectPropertyOfAxiom(sub.asOWLObjectProperty(), sup.asOWLObjectProperty(),
                    axiom.getAnnotations());
            } else {
                // cannot repair: leave unchanged
                obj = axiom;
            }
            return;
        } else if (sub.isDataPropertyExpression() || sup.isDataPropertyExpression()) {
            if (sub.isOWLDataProperty() && sup.isOWLDataProperty()) {
                obj = dataFactory.getOWLSubDataPropertyOfAxiom(sub.asOWLDataProperty(), sup.asOWLDataProperty(), axiom
                    .getAnnotations());
            } else {
                // cannot repair: leave unchanged
                obj = axiom;
            }
            return;
        }
        if (sub.isOWLAnnotationProperty() && sup.isOWLAnnotationProperty()) {
            obj = dataFactory.getOWLSubAnnotationPropertyOfAxiom(sub.asOWLAnnotationProperty(), sup
                .asOWLAnnotationProperty(), duplicateAxiomAnnotations(axiom));
        } else {
            // cannot repair: leave unchanged
            obj = axiom;
        }
    }

    @Override
    public void visit(@Nonnull OWLAnnotation node) {
        node.getProperty().accept(this);
        OWLAnnotationProperty prop = getLastObject();
        node.getValue().accept(this);
        OWLAnnotationValue val = getLastObject();
        obj = dataFactory.getOWLAnnotation(prop, val);
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        obj = anonProvider.getOWLAnonymousIndividual(individual.getID().getID());
    }

    @Override
    public void visit(IRI iri) {
        obj = iri;
        for (EntityType<?> entityType : EntityType.values()) {
            assert entityType != null;
            OWLEntity entity = dataFactory.getOWLEntity(entityType, iri);
            OWLEntity replacementIRI = replacementMap.get(entity);
            if (replacementIRI != null) {
                obj = replacementIRI.getIRI();
                break;
            }
        }
    }

    @Override
    public void visit(@Nonnull OWLDatatypeDefinitionAxiom axiom) {
        axiom.getDatatype().accept(this);
        OWLDatatype dt = getLastObject();
        axiom.getDataRange().accept(this);
        OWLDataRange rng = getLastObject();
        obj = dataFactory.getOWLDatatypeDefinitionAxiom(dt, rng, duplicateAxiomAnnotations(axiom));
    }

    /**
     * A utility function that duplicates a set of objects.
     * 
     * @param objects
     *        The set of object to be duplicated
     * @return The set of duplicated objects
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    private <O extends OWLObject> Set<O> duplicateSet(@Nonnull Set<O> objects) {
        Set<O> dup = new HashSet<>();
        for (O o : objects) {
            o.accept(this);
            dup.add((O) getLastObject());
        }
        return dup;
    }
}
