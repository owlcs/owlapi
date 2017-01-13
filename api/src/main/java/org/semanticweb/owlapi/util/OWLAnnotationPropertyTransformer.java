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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.Nullable;

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
    private final OWLDataFactory df;
    @Nullable private Object obj;
    private final Map<OWLEntity, OWLEntity> replacementMap;

    /**
     * Creates an object duplicator that duplicates objects using the specified
     * data factory.
     * 
     * @param dataFactory
     *        The data factory to be used for the duplication.
     */
    public OWLAnnotationPropertyTransformer(OWLDataFactory dataFactory) {
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
    public OWLAnnotationPropertyTransformer(OWLDataFactory dataFactory, Map<OWLEntity, OWLEntity> iriReplacementMap) {
        df = checkNotNull(dataFactory, "dataFactory cannot be null");
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
    public OWLAnnotationPropertyTransformer(Map<OWLEntity, OWLEntity> entityIRIReplacementMap,
        OWLDataFactory dataFactory) {
        df = checkNotNull(dataFactory, "dataFactory cannot be null");
        replacementMap = new HashMap<>(checkNotNull(entityIRIReplacementMap, "entityIRIReplacementMap cannot be null"));
    }

    /**
     * @param object
     *        the object to duplicate
     * @return the duplicate
     * @param <O>
     *        return type
     */
    public <O extends OWLObject> O transformObject(O object) {
        checkNotNull(object, "object cannot be null");
        try {
            return dup(object);
        } catch (ClassCastException e) {
            LOGGER.error(
                "Attempt to transform an axiom to correct misuse of properties failed. Property replacement: {}, axiom: {}, error: {}",
                replacementMap, object, e.getMessage());
            obj = object;
            return object;
        }
    }

    protected void setLastObject(Object obj) {
        this.obj = obj;
    }

    @SuppressWarnings({ "unchecked", })
    protected <O extends OWLObject> O dup(O o) {
        o.accept(this);
        return (O) verifyNotNull(obj);
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
    private OWLEntity getIRI(OWLEntity entity) {
        OWLEntity replacement = replacementMap.get(entity);
        if (replacement != null) {
            return replacement;
        }
        return entity;
    }

    private Collection<OWLAnnotation> anns(OWLAxiom ax) {
        return set(ax.annotations());
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom ax) {
        obj = df.getOWLAsymmetricObjectPropertyAxiom(dup(ax.getProperty()), anns(ax));
    }

    @Override
    public void visit(OWLClassAssertionAxiom ax) {
        obj = df.getOWLClassAssertionAxiom(dup(ax.getClassExpression()), dup(ax.getIndividual()), anns(ax));
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom ax) {
        obj = df.getOWLDataPropertyAssertionAxiom(dup(ax.getProperty()), dup(ax.getSubject()), dup(ax.getObject()),
            anns(ax));
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom ax) {
        obj = df.getOWLDataPropertyDomainAxiom(dup(ax.getProperty()), dup(ax.getDomain()), anns(ax));
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom ax) {
        obj = df.getOWLDataPropertyRangeAxiom(dup(ax.getProperty()), dup(ax.getRange()), anns(ax));
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom ax) {
        obj = df.getOWLSubDataPropertyOfAxiom(dup(ax.getSubProperty()), dup(ax.getSuperProperty()), anns(ax));
    }

    @Override
    public void visit(OWLDeclarationAxiom ax) {
        obj = df.getOWLDeclarationAxiom(dup(ax.getEntity()), anns(ax));
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom ax) {
        obj = df.getOWLDifferentIndividualsAxiom(set(ax.individuals()), anns(ax));
    }

    @Override
    public void visit(OWLDisjointClassesAxiom ax) {
        obj = df.getOWLDisjointClassesAxiom(set(ax.classExpressions()), anns(ax));
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom ax) {
        obj = df.getOWLDisjointDataPropertiesAxiom(set(ax.properties()), anns(ax));
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom ax) {
        obj = df.getOWLDisjointObjectPropertiesAxiom(set(ax.properties()), anns(ax));
    }

    @Override
    public void visit(OWLDisjointUnionAxiom ax) {
        obj = df.getOWLDisjointUnionAxiom(dup(ax.getOWLClass()), set(ax.classExpressions()), anns(ax));
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom ax) {
        OWLAnnotationSubject subject = dup(ax.getSubject());
        OWLProperty prop = dup(ax.getProperty());
        OWLAnnotationValue value = dup(ax.getValue());
        if (prop.isObjectPropertyExpression()) {
            // turn to object property assertion
            OWLIndividual individual;
            OWLIndividual relatedIndividual;
            if (subject instanceof OWLAnonymousIndividual) {
                individual = (OWLIndividual) subject;
            } else {
                individual = df.getOWLNamedIndividual((IRI) subject);
            }
            if (value instanceof OWLIndividual) {
                relatedIndividual = (OWLIndividual) value;
            } else {
                relatedIndividual = df.getOWLNamedIndividual((IRI) value);
            }
            obj = df.getOWLObjectPropertyAssertionAxiom(prop.asOWLObjectProperty(), individual, relatedIndividual,
                asList(ax.annotations()));
            return;
        } else if (prop.isDataPropertyExpression()) {
            // turn to data property assertion
            OWLIndividual individual;
            if (subject instanceof OWLAnonymousIndividual) {
                individual = (OWLIndividual) subject;
            } else {
                individual = df.getOWLNamedIndividual((IRI) subject);
            }
            obj = df.getOWLDataPropertyAssertionAxiom(prop.asOWLDataProperty(), individual, (OWLLiteral) value, asList(
                ax.annotations()));
            return;
        }
        obj = df.getOWLAnnotationAssertionAxiom(prop.asOWLAnnotationProperty(), subject, value, anns(ax));
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom ax) {
        obj = df.getOWLEquivalentClassesAxiom(set(ax.classExpressions()), anns(ax));
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom ax) {
        obj = df.getOWLEquivalentDataPropertiesAxiom(set(ax.properties()), anns(ax));
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom ax) {
        obj = df.getOWLEquivalentObjectPropertiesAxiom(set(ax.properties()), anns(ax));
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom ax) {
        obj = df.getOWLFunctionalDataPropertyAxiom(dup(ax.getProperty()), anns(ax));
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom ax) {
        obj = df.getOWLFunctionalObjectPropertyAxiom(dup(ax.getProperty()), anns(ax));
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom ax) {
        obj = df.getOWLInverseFunctionalObjectPropertyAxiom(dup(ax.getProperty()), anns(ax));
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom ax) {
        obj = df.getOWLInverseObjectPropertiesAxiom(dup(ax.getFirstProperty()), dup(ax.getSecondProperty()), anns(ax));
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom ax) {
        obj = df.getOWLIrreflexiveObjectPropertyAxiom(dup(ax.getProperty()), anns(ax));
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom ax) {
        obj = df.getOWLNegativeDataPropertyAssertionAxiom(dup(ax.getProperty()), dup(ax.getSubject()), dup(ax
            .getObject()), anns(ax));
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom ax) {
        obj = df.getOWLNegativeObjectPropertyAssertionAxiom(dup(ax.getProperty()), dup(ax.getSubject()), dup(ax
            .getObject()), anns(ax));
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom ax) {
        obj = df.getOWLObjectPropertyAssertionAxiom(dup(ax.getProperty()), dup(ax.getSubject()), dup(ax.getObject()),
            anns(ax));
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom ax) {
        obj = df.getOWLSubPropertyChainOfAxiom(asList(ax.getPropertyChain().stream().map(this::dup)), dup(ax
            .getSuperProperty()), anns(ax));
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom ax) {
        obj = df.getOWLObjectPropertyDomainAxiom(dup(ax.getProperty()), dup(ax.getDomain()), anns(ax));
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom ax) {
        obj = df.getOWLObjectPropertyRangeAxiom(dup(ax.getProperty()), dup(ax.getRange()), anns(ax));
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom ax) {
        obj = df.getOWLSubObjectPropertyOfAxiom(dup(ax.getSubProperty()), dup(ax.getSuperProperty()), anns(ax));
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom ax) {
        obj = df.getOWLReflexiveObjectPropertyAxiom(dup(ax.getProperty()), anns(ax));
    }

    @Override
    public void visit(OWLSameIndividualAxiom ax) {
        obj = df.getOWLSameIndividualAxiom(set(ax.individuals()), anns(ax));
    }

    @Override
    public void visit(OWLSubClassOfAxiom ax) {
        obj = df.getOWLSubClassOfAxiom(dup(ax.getSubClass()), dup(ax.getSuperClass()), anns(ax));
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom ax) {
        obj = df.getOWLSymmetricObjectPropertyAxiom(dup(ax.getProperty()), anns(ax));
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom ax) {
        obj = df.getOWLTransitiveObjectPropertyAxiom(dup(ax.getProperty()), anns(ax));
    }

    @Override
    public void visit(OWLClass ce) {
        obj = getIRI(ce);
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        obj = df.getOWLDataAllValuesFrom(dup(ce.getProperty()), dup(ce.getFiller()));
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        obj = df.getOWLDataExactCardinality(ce.getCardinality(), dup(ce.getProperty()), dup(ce.getFiller()));
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        obj = df.getOWLDataMaxCardinality(ce.getCardinality(), dup(ce.getProperty()), dup(ce.getFiller()));
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        obj = df.getOWLDataMinCardinality(ce.getCardinality(), dup(ce.getProperty()), dup(ce.getFiller()));
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        obj = df.getOWLDataSomeValuesFrom(dup(ce.getProperty()), dup(ce.getFiller()));
    }

    @Override
    public void visit(OWLDataHasValue ce) {
        obj = df.getOWLDataHasValue(dup(ce.getProperty()), dup(ce.getFiller()));
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        obj = df.getOWLObjectAllValuesFrom(dup(ce.getProperty()), dup(ce.getFiller()));
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        obj = df.getOWLObjectComplementOf(dup(ce.getOperand()));
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        obj = df.getOWLObjectExactCardinality(ce.getCardinality(), dup(ce.getProperty()), dup(ce.getFiller()));
    }

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        obj = df.getOWLObjectIntersectionOf(set(ce.operands()));
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        obj = df.getOWLObjectMaxCardinality(ce.getCardinality(), dup(ce.getProperty()), dup(ce.getFiller()));
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        obj = df.getOWLObjectMinCardinality(ce.getCardinality(), dup(ce.getProperty()), dup(ce.getFiller()));
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        obj = df.getOWLObjectOneOf(set(ce.individuals()));
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        obj = df.getOWLObjectHasSelf(dup(ce.getProperty()));
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        obj = df.getOWLObjectSomeValuesFrom(dup(ce.getProperty()), dup(ce.getFiller()));
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        obj = df.getOWLObjectUnionOf(set(ce.operands()));
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        obj = df.getOWLObjectHasValue(dup(ce.getProperty()), dup(ce.getFiller()));
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        obj = df.getOWLDataComplementOf(dup(node.getDataRange()));
    }

    @Override
    public void visit(OWLDataOneOf node) {
        obj = df.getOWLDataOneOf(set(node.values()));
    }

    @Override
    public void visit(OWLDatatype node) {
        obj = getIRI(node);
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        obj = df.getOWLDatatypeRestriction(dup(node.getDatatype()), asList(node.facetRestrictions().map(this::dup)));
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        obj = df.getOWLFacetRestriction(node.getFacet(), dup(node.getFacetValue()));
    }

    @Override
    public void visit(OWLLiteral node) {
        if (node.hasLang()) {
            obj = df.getOWLLiteral(node.getLiteral(), node.getLang());
        } else {
            obj = df.getOWLLiteral(node.getLiteral(), dup(node.getDatatype()));
        }
    }

    @Override
    public void visit(OWLDataProperty property) {
        obj = getIRI(property);
    }

    @Override
    public void visit(OWLObjectProperty property) {
        obj = getIRI(property);
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        obj = df.getOWLObjectInverseOf(dup(property.getInverse()).asOWLObjectProperty());
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        obj = getIRI(individual);
    }

    @Override
    public void visit(OWLOntology ontology) {
        // Should we duplicate ontologies here? Probably not.
        obj = ontology;
    }

    @Override
    public void visit(SWRLRule rule) {
        obj = df.getSWRLRule(asList(rule.body().map(this::dup)), asList(rule.head().map(this::dup)));
    }

    @Override
    public void visit(SWRLClassAtom node) {
        obj = df.getSWRLClassAtom(dup(node.getPredicate()), dup(node.getArgument()));
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        obj = df.getSWRLDataRangeAtom(dup(node.getPredicate()), dup(node.getArgument()));
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        obj = df.getSWRLObjectPropertyAtom(dup(node.getPredicate()), dup(node.getFirstArgument()), dup(node
            .getSecondArgument()));
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        obj = df.getSWRLDataPropertyAtom(dup(node.getPredicate()), dup(node.getFirstArgument()), dup(node
            .getSecondArgument()));
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        obj = df.getSWRLBuiltInAtom(node.getPredicate(), asList(node.arguments().map(this::dup)));
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        obj = df.getSWRLDifferentIndividualsAtom(dup(node.getFirstArgument()), dup(node.getSecondArgument()));
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        obj = df.getSWRLSameIndividualAtom(dup(node.getFirstArgument()), dup(node.getSecondArgument()));
    }

    @Override
    public void visit(SWRLVariable node) {
        obj = df.getSWRLVariable(dup(node.getIRI()));
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        obj = df.getSWRLIndividualArgument(dup(node.getIndividual()));
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        obj = df.getSWRLLiteralArgument(dup(node.getLiteral()));
    }

    @Override
    public void visit(OWLHasKeyAxiom ax) {
        obj = df.getOWLHasKeyAxiom(dup(ax.getClassExpression()), set(ax.propertyExpressions()), anns(ax));
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        obj = df.getOWLDataIntersectionOf(set(node.operands()));
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        obj = df.getOWLDataUnionOf(set(node.operands()));
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        obj = getIRI(property);
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom ax) {
        OWLProperty prop = dup(ax.getProperty());
        IRI domain = dup(ax.getDomain());
        if (prop.isObjectPropertyExpression()) {
            // turn to object property domain
            OWLClassExpression d = df.getOWLClass(domain);
            LOGGER.warn(
                "Annotation property domain axiom turned to object property domain after parsing. This could introduce errors if the original domain was an anonymous expression: {} is the new domain.",
                domain);
            obj = df.getOWLObjectPropertyDomainAxiom(prop.asOWLObjectProperty(), d, asList(ax.annotations()));
            return;
        } else if (prop.isDataPropertyExpression()) {
            // turn to data property domain
            OWLClassExpression d = df.getOWLClass(domain);
            LOGGER.warn(
                "Annotation property domain axiom turned to data property domain after parsing. This could introduce errors if the original domain was an anonymous expression: {} is the new domain.",
                domain);
            obj = df.getOWLDataPropertyDomainAxiom(prop.asOWLDataProperty(), d, asList(ax.annotations()));
            return;
        }
        obj = df.getOWLAnnotationPropertyDomainAxiom(prop.asOWLAnnotationProperty(), domain, anns(ax));
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom ax) {
        OWLProperty prop = dup(ax.getProperty());
        IRI range = dup(ax.getRange());
        if (prop.isObjectPropertyExpression()) {
            // turn to object property domain
            OWLClassExpression d = df.getOWLClass(range);
            LOGGER.warn(
                "Annotation property range axiom turned to object property range after parsing. This could introduce errors if the original range was an anonymous expression: {} is the new domain.",
                range);
            obj = df.getOWLObjectPropertyRangeAxiom(prop.asOWLObjectProperty(), d, asList(ax.annotations()));
            return;
        } else if (prop.isDataPropertyExpression()) {
            // turn to data property domain
            OWLDataRange d = df.getOWLDatatype(range);
            LOGGER.warn(
                "Annotation property range axiom turned to data property range after parsing. This could introduce errors if the original range was an anonymous expression: {} is the new domain.",
                range);
            obj = df.getOWLDataPropertyRangeAxiom(prop.asOWLDataProperty(), d, asList(ax.annotations()));
            return;
        }
        obj = df.getOWLAnnotationPropertyRangeAxiom(prop.asOWLAnnotationProperty(), range, anns(ax));
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom ax) {
        OWLProperty sub = dup(ax.getSubProperty());
        OWLProperty sup = dup(ax.getSuperProperty());
        if (sub.isObjectPropertyExpression() || sup.isObjectPropertyExpression()) {
            // check: it is possible that the properties represent an actual
            // illegal punning, where this fix cannot be applied
            if (sub.isOWLObjectProperty() && sup.isOWLObjectProperty()) {
                obj = df.getOWLSubObjectPropertyOfAxiom(sub.asOWLObjectProperty(), sup.asOWLObjectProperty(), asList(ax
                    .annotations()));
            } else {
                // cannot repair: leave unchanged
                obj = ax;
            }
            return;
        } else if (sub.isDataPropertyExpression() || sup.isDataPropertyExpression()) {
            if (sub.isOWLDataProperty() && sup.isOWLDataProperty()) {
                obj = df.getOWLSubDataPropertyOfAxiom(sub.asOWLDataProperty(), sup.asOWLDataProperty(), asList(ax
                    .annotations()));
            } else {
                // cannot repair: leave unchanged
                obj = ax;
            }
            return;
        }
        if (sub.isOWLAnnotationProperty() && sup.isOWLAnnotationProperty()) {
            obj = df.getOWLSubAnnotationPropertyOfAxiom(sub.asOWLAnnotationProperty(), sup.asOWLAnnotationProperty(),
                anns(ax));
        } else {
            // cannot repair: leave unchanged
            obj = ax;
        }
    }

    @Override
    public void visit(OWLAnnotation node) {
        obj = df.getOWLAnnotation(dup(node.getProperty()), dup(node.getValue()));
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        obj = individual;
    }

    @Override
    public void visit(IRI iri) {
        obj = iri;
        for (EntityType<?> entityType : EntityType.values()) {
            assert entityType != null;
            OWLEntity entity = df.getOWLEntity(entityType, iri);
            OWLEntity replacementIRI = replacementMap.get(entity);
            if (replacementIRI != null) {
                obj = replacementIRI.getIRI();
                break;
            }
        }
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom ax) {
        obj = df.getOWLDatatypeDefinitionAxiom(dup(ax.getDatatype()), dup(ax.getDataRange()), anns(ax));
    }

    /**
     * A utility function that duplicates a set of objects.
     * 
     * @param objects
     *        The set of object to be duplicated
     * @return The set of duplicated objects
     */
    private <O extends OWLObject> Collection<O> set(Stream<O> objects) {
        return asList(objects.map(this::dup));
    }
}
