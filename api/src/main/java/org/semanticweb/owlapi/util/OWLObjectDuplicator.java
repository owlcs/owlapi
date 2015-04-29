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
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLObjectDuplicator implements OWLObjectVisitorEx<Object> {

    @Nonnull
    private final OWLDataFactory df;
    @Nonnull
    private final Map<OWLEntity, IRI> replacementMap;

    /**
     * Creates an object duplicator that duplicates objects using the specified
     * data factory.
     * 
     * @param dataFactory
     *        The data factory to be used for the duplication.
     */
    public OWLObjectDuplicator(@Nonnull OWLDataFactory dataFactory) {
        this(new HashMap<OWLEntity, IRI>(), dataFactory);
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
    public OWLObjectDuplicator(@Nonnull OWLDataFactory dataFactory,
            @Nonnull Map<IRI, IRI> iriReplacementMap) {
        df = checkNotNull(dataFactory, "dataFactory cannot be null");
        checkNotNull(iriReplacementMap, "iriReplacementMap cannot be null");
        replacementMap = new HashMap<>();
        iriReplacementMap.forEach((k, v) -> {
            replacementMap.put(dataFactory.getOWLClass(k), v);
            replacementMap.put(dataFactory.getOWLObjectProperty(k), v);
            replacementMap.put(dataFactory.getOWLDataProperty(k), v);
            replacementMap.put(dataFactory.getOWLNamedIndividual(k), v);
            replacementMap.put(dataFactory.getOWLDatatype(k), v);
            replacementMap.put(dataFactory.getOWLAnnotationProperty(k), v);
        });
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
    public OWLObjectDuplicator(
            @Nonnull Map<OWLEntity, IRI> entityIRIReplacementMap,
            @Nonnull OWLDataFactory dataFactory) {
        df = checkNotNull(dataFactory, "dataFactory cannot be null");
        replacementMap = new HashMap<>(checkNotNull(entityIRIReplacementMap,
                "entityIRIReplacementMap cannot be null"));
    }

    /**
     * @param object
     *        the object to duplicate
     * @return the duplicate
     * @param <O>
     *        return type
     */
    @Nonnull
    public <O extends OWLObject> O duplicateObject(@Nonnull O object) {
        checkNotNull(object, "object cannot be null");
        return get(object);
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
    private IRI getIRI(@Nonnull OWLEntity entity) {
        checkNotNull(entity, "entity cannot be null");
        IRI replacement = replacementMap.get(entity);
        if (replacement == null) {
            return entity.getIRI();
        }
        return replacement;
    }

    @Nonnull
    private Set<OWLAnnotation> anns(@Nonnull OWLAxiom axiom) {
        checkNotNull(axiom, "axiom cannot be null");
        return asSet(axiom.annotations().map(a -> (OWLAnnotation) get(a)));
    }

    @Override
    public OWLAsymmetricObjectPropertyAxiom visit(
            @Nonnull OWLAsymmetricObjectPropertyAxiom axiom) {
        return df.getOWLAsymmetricObjectPropertyAxiom(get(axiom.getProperty()),
                anns(axiom));
    }

    @Override
    public OWLClassAssertionAxiom visit(@Nonnull OWLClassAssertionAxiom axiom) {
        return df.getOWLClassAssertionAxiom(get(axiom.getClassExpression()),
                get(axiom.getIndividual()), anns(axiom));
    }

    @Override
    public OWLDataPropertyAssertionAxiom visit(
            @Nonnull OWLDataPropertyAssertionAxiom axiom) {
        return df.getOWLDataPropertyAssertionAxiom(get(axiom.getProperty()),
                get(axiom.getSubject()), get(axiom.getObject()), anns(axiom));
    }

    @Override
    public OWLDataPropertyDomainAxiom visit(
            @Nonnull OWLDataPropertyDomainAxiom axiom) {
        return df.getOWLDataPropertyDomainAxiom(get(axiom.getProperty()),
                get(axiom.getDomain()), anns(axiom));
    }

    @Override
    public OWLDataPropertyRangeAxiom visit(
            @Nonnull OWLDataPropertyRangeAxiom axiom) {
        return df.getOWLDataPropertyRangeAxiom(get(axiom.getProperty()),
                get(axiom.getRange()), anns(axiom));
    }

    @Override
    public OWLSubDataPropertyOfAxiom visit(
            @Nonnull OWLSubDataPropertyOfAxiom axiom) {
        return df.getOWLSubDataPropertyOfAxiom(get(axiom.getSubProperty()),
                get(axiom.getSuperProperty()), anns(axiom));
    }

    @Override
    public OWLDeclarationAxiom visit(@Nonnull OWLDeclarationAxiom axiom) {
        return df.getOWLDeclarationAxiom(get(axiom.getEntity()), anns(axiom));
    }

    @Override
    public OWLDifferentIndividualsAxiom visit(
            @Nonnull OWLDifferentIndividualsAxiom axiom) {
        return df.getOWLDifferentIndividualsAxiom(set(axiom.individuals()),
                anns(axiom));
    }

    @Override
    public OWLDisjointClassesAxiom
            visit(@Nonnull OWLDisjointClassesAxiom axiom) {
        return df.getOWLDisjointClassesAxiom(set(axiom.classExpressions()),
                anns(axiom));
    }

    @Override
    public OWLDisjointDataPropertiesAxiom visit(
            @Nonnull OWLDisjointDataPropertiesAxiom axiom) {
        return df.getOWLDisjointDataPropertiesAxiom(set(axiom.properties()),
                anns(axiom));
    }

    @Override
    public OWLDisjointObjectPropertiesAxiom visit(
            @Nonnull OWLDisjointObjectPropertiesAxiom axiom) {
        return df.getOWLDisjointObjectPropertiesAxiom(set(axiom.properties()),
                anns(axiom));
    }

    @Override
    public OWLDisjointUnionAxiom visit(@Nonnull OWLDisjointUnionAxiom axiom) {
        return df.getOWLDisjointUnionAxiom(get(axiom.getOWLClass()),
                set(axiom.classExpressions()), anns(axiom));
    }

    @Override
    public OWLAnnotationAssertionAxiom visit(
            @Nonnull OWLAnnotationAssertionAxiom axiom) {
        return df.getOWLAnnotationAssertionAxiom(get(axiom.getProperty()),
                get(axiom.getSubject()), get(axiom.getValue()), anns(axiom));
    }

    @Override
    public OWLEquivalentClassesAxiom visit(
            @Nonnull OWLEquivalentClassesAxiom axiom) {
        return df.getOWLEquivalentClassesAxiom(set(axiom.classExpressions()),
                anns(axiom));
    }

    @Override
    public OWLEquivalentDataPropertiesAxiom visit(
            @Nonnull OWLEquivalentDataPropertiesAxiom axiom) {
        return df.getOWLEquivalentDataPropertiesAxiom(set(axiom.properties()),
                anns(axiom));
    }

    @Override
    public OWLEquivalentObjectPropertiesAxiom visit(
            @Nonnull OWLEquivalentObjectPropertiesAxiom axiom) {
        return df.getOWLEquivalentObjectPropertiesAxiom(
                set(axiom.properties()), anns(axiom));
    }

    @Override
    public OWLFunctionalDataPropertyAxiom visit(
            @Nonnull OWLFunctionalDataPropertyAxiom axiom) {
        return df.getOWLFunctionalDataPropertyAxiom(get(axiom.getProperty()),
                anns(axiom));
    }

    @Override
    public OWLFunctionalObjectPropertyAxiom visit(
            @Nonnull OWLFunctionalObjectPropertyAxiom axiom) {
        return df.getOWLFunctionalObjectPropertyAxiom(get(axiom.getProperty()),
                anns(axiom));
    }

    @Override
    public OWLInverseFunctionalObjectPropertyAxiom visit(
            @Nonnull OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return df.getOWLInverseFunctionalObjectPropertyAxiom(
                get(axiom.getProperty()), anns(axiom));
    }

    @Override
    public OWLInverseObjectPropertiesAxiom visit(
            @Nonnull OWLInverseObjectPropertiesAxiom axiom) {
        return df.getOWLInverseObjectPropertiesAxiom(
                get(axiom.getFirstProperty()), get(axiom.getSecondProperty()),
                anns(axiom));
    }

    @Override
    public OWLIrreflexiveObjectPropertyAxiom visit(
            @Nonnull OWLIrreflexiveObjectPropertyAxiom axiom) {
        return df.getOWLIrreflexiveObjectPropertyAxiom(
                get(axiom.getProperty()), anns(axiom));
    }

    @Override
    public OWLNegativeDataPropertyAssertionAxiom visit(
            @Nonnull OWLNegativeDataPropertyAssertionAxiom axiom) {
        return df.getOWLNegativeDataPropertyAssertionAxiom(
                get(axiom.getProperty()), get(axiom.getSubject()),
                get(axiom.getObject()), anns(axiom));
    }

    @Override
    public OWLNegativeObjectPropertyAssertionAxiom visit(
            @Nonnull OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return df.getOWLNegativeObjectPropertyAssertionAxiom(
                get(axiom.getProperty()), get(axiom.getSubject()),
                get(axiom.getObject()), anns(axiom));
    }

    @Override
    public OWLObjectPropertyAssertionAxiom visit(
            @Nonnull OWLObjectPropertyAssertionAxiom axiom) {
        return df.getOWLObjectPropertyAssertionAxiom(get(axiom.getProperty()),
                get(axiom.getSubject()), get(axiom.getObject()), anns(axiom));
    }

    @Override
    public OWLSubPropertyChainOfAxiom visit(
            @Nonnull OWLSubPropertyChainOfAxiom axiom) {
        List<OWLObjectPropertyExpression> chain = new ArrayList<>();
        axiom.getPropertyChain().forEach(p -> chain.add(get(p)));
        return df.getOWLSubPropertyChainOfAxiom(chain,
                get(axiom.getSuperProperty()), anns(axiom));
    }

    @Override
    public OWLObjectPropertyDomainAxiom visit(
            @Nonnull OWLObjectPropertyDomainAxiom axiom) {
        return df.getOWLObjectPropertyDomainAxiom(get(axiom.getProperty()),
                get(axiom.getDomain()), anns(axiom));
    }

    @Override
    public OWLObjectPropertyRangeAxiom visit(
            @Nonnull OWLObjectPropertyRangeAxiom axiom) {
        return df.getOWLObjectPropertyRangeAxiom(get(axiom.getProperty()),
                get(axiom.getRange()), anns(axiom));
    }

    @Override
    public OWLSubObjectPropertyOfAxiom visit(
            @Nonnull OWLSubObjectPropertyOfAxiom axiom) {
        return df.getOWLSubObjectPropertyOfAxiom(get(axiom.getSubProperty()),
                get(axiom.getSuperProperty()), anns(axiom));
    }

    @Override
    public OWLReflexiveObjectPropertyAxiom visit(
            @Nonnull OWLReflexiveObjectPropertyAxiom axiom) {
        return df.getOWLReflexiveObjectPropertyAxiom(get(axiom.getProperty()),
                anns(axiom));
    }

    @Override
    public OWLSameIndividualAxiom visit(@Nonnull OWLSameIndividualAxiom axiom) {
        return df.getOWLSameIndividualAxiom(set(axiom.individuals()),
                anns(axiom));
    }

    @Override
    public OWLSubClassOfAxiom visit(@Nonnull OWLSubClassOfAxiom axiom) {
        return df.getOWLSubClassOfAxiom(get(axiom.getSubClass()),
                get(axiom.getSuperClass()), anns(axiom));
    }

    @Override
    public OWLSymmetricObjectPropertyAxiom visit(
            @Nonnull OWLSymmetricObjectPropertyAxiom axiom) {
        return df.getOWLSymmetricObjectPropertyAxiom(get(axiom.getProperty()),
                anns(axiom));
    }

    @Override
    public OWLTransitiveObjectPropertyAxiom visit(
            @Nonnull OWLTransitiveObjectPropertyAxiom axiom) {
        return df.getOWLTransitiveObjectPropertyAxiom(get(axiom.getProperty()),
                anns(axiom));
    }

    @Override
    public OWLClass visit(@Nonnull OWLClass ce) {
        return df.getOWLClass(getIRI(ce));
    }

    @Override
    public OWLDataAllValuesFrom visit(@Nonnull OWLDataAllValuesFrom ce) {
        return df.getOWLDataAllValuesFrom(get(ce.getProperty()),
                get(ce.getFiller()));
    }

    @Override
    public OWLDataExactCardinality visit(@Nonnull OWLDataExactCardinality ce) {
        return df.getOWLDataExactCardinality(ce.getCardinality(),
                get(ce.getProperty()), get(ce.getFiller()));
    }

    @Override
    public OWLDataMaxCardinality visit(@Nonnull OWLDataMaxCardinality ce) {
        return df.getOWLDataMaxCardinality(ce.getCardinality(),
                get(ce.getProperty()), get(ce.getFiller()));
    }

    @Override
    public OWLDataMinCardinality visit(@Nonnull OWLDataMinCardinality ce) {
        return df.getOWLDataMinCardinality(ce.getCardinality(),
                get(ce.getProperty()), get(ce.getFiller()));
    }

    @Override
    public OWLDataSomeValuesFrom visit(@Nonnull OWLDataSomeValuesFrom ce) {
        return df.getOWLDataSomeValuesFrom(get(ce.getProperty()),
                get(ce.getFiller()));
    }

    @Override
    public OWLDataHasValue visit(@Nonnull OWLDataHasValue ce) {
        return df
                .getOWLDataHasValue(get(ce.getProperty()), get(ce.getFiller()));
    }

    @Override
    public OWLObjectAllValuesFrom visit(@Nonnull OWLObjectAllValuesFrom ce) {
        return df.getOWLObjectAllValuesFrom(get(ce.getProperty()),
                get(ce.getFiller()));
    }

    @Override
    public OWLObjectComplementOf visit(@Nonnull OWLObjectComplementOf ce) {
        return df.getOWLObjectComplementOf(get(ce.getOperand()));
    }

    @Override
    public OWLObjectExactCardinality
            visit(@Nonnull OWLObjectExactCardinality ce) {
        return df.getOWLObjectExactCardinality(ce.getCardinality(),
                get(ce.getProperty()), get(ce.getFiller()));
    }

    @Override
    public OWLObjectIntersectionOf visit(@Nonnull OWLObjectIntersectionOf ce) {
        return df.getOWLObjectIntersectionOf(set(ce.operands()));
    }

    @Override
    public OWLObjectMaxCardinality visit(@Nonnull OWLObjectMaxCardinality ce) {
        return df.getOWLObjectMaxCardinality(ce.getCardinality(),
                get(ce.getProperty()), get(ce.getFiller()));
    }

    @Override
    public OWLObjectMinCardinality visit(@Nonnull OWLObjectMinCardinality ce) {
        OWLObjectPropertyExpression prop = get(ce.getProperty());
        return df.getOWLObjectMinCardinality(ce.getCardinality(), prop,
                get(ce.getFiller()));
    }

    @Override
    public OWLObjectOneOf visit(@Nonnull OWLObjectOneOf ce) {
        return df.getOWLObjectOneOf(set(ce.individuals()));
    }

    @Override
    public OWLObjectHasSelf visit(@Nonnull OWLObjectHasSelf ce) {
        return df.getOWLObjectHasSelf(get(ce.getProperty()));
    }

    @Override
    public OWLObjectSomeValuesFrom visit(@Nonnull OWLObjectSomeValuesFrom ce) {
        return df.getOWLObjectSomeValuesFrom(get(ce.getProperty()),
                get(ce.getFiller()));
    }

    @Override
    public OWLObjectUnionOf visit(@Nonnull OWLObjectUnionOf ce) {
        return df.getOWLObjectUnionOf(set(ce.operands()));
    }

    @Override
    public OWLObjectHasValue visit(@Nonnull OWLObjectHasValue ce) {
        return df.getOWLObjectHasValue(get(ce.getProperty()),
                get(ce.getFiller()));
    }

    @Override
    public OWLDataComplementOf visit(@Nonnull OWLDataComplementOf node) {
        return df.getOWLDataComplementOf(get(node.getDataRange()));
    }

    @Override
    public OWLDataOneOf visit(@Nonnull OWLDataOneOf node) {
        return df.getOWLDataOneOf(set(node.values()));
    }

    @Override
    public OWLDatatype visit(@Nonnull OWLDatatype node) {
        return df.getOWLDatatype(getIRI(node));
    }

    @Override
    public OWLDatatypeRestriction visit(@Nonnull OWLDatatypeRestriction node) {
        return df.getOWLDatatypeRestriction(get(node.getDatatype()),
                set(node.facetRestrictions()));
    }

    @Override
    public OWLFacetRestriction visit(@Nonnull OWLFacetRestriction node) {
        return df.getOWLFacetRestriction(node.getFacet(),
                get(node.getFacetValue()));
    }

    @Override
    public OWLLiteral visit(@Nonnull OWLLiteral node) {
        if (node.hasLang()) {
            return df.getOWLLiteral(node.getLiteral(), node.getLang());
        }
        return df.getOWLLiteral(node.getLiteral(),
                (OWLDatatype) get(node.getDatatype()));
    }

    @Override
    public OWLDataProperty visit(@Nonnull OWLDataProperty property) {
        return df.getOWLDataProperty(getIRI(property));
    }

    @Override
    public OWLObjectProperty visit(@Nonnull OWLObjectProperty property) {
        return df.getOWLObjectProperty(getIRI(property));
    }

    @Override
    public OWLObjectInverseOf visit(@Nonnull OWLObjectInverseOf property) {
        return df.getOWLObjectInverseOf(get(property.getInverse()));
    }

    @Override
    public OWLNamedIndividual visit(@Nonnull OWLNamedIndividual individual) {
        return df.getOWLNamedIndividual(getIRI(individual));
    }

    @Override
    public OWLOntology visit(OWLOntology ontology) {
        // Should we duplicate ontologies here? Probably not.
        return ontology;
    }

    @Override
    public SWRLRule visit(@Nonnull SWRLRule rule) {
        return df.getSWRLRule(set(rule.body()), set(rule.head()));
    }

    @Override
    public SWRLClassAtom visit(@Nonnull SWRLClassAtom node) {
        return df.getSWRLClassAtom(get(node.getPredicate()),
                get(node.getArgument()));
    }

    @Override
    public SWRLDataRangeAtom visit(@Nonnull SWRLDataRangeAtom node) {
        return df.getSWRLDataRangeAtom(get(node.getPredicate()),
                get(node.getArgument()));
    }

    @Override
    public SWRLObjectPropertyAtom visit(@Nonnull SWRLObjectPropertyAtom node) {
        return df.getSWRLObjectPropertyAtom(get(node.getPredicate()),
                get(node.getFirstArgument()), get(node.getSecondArgument()));
    }

    @Override
    public SWRLDataPropertyAtom visit(@Nonnull SWRLDataPropertyAtom node) {
        return df.getSWRLDataPropertyAtom(get(node.getPredicate()),
                get(node.getFirstArgument()), get(node.getSecondArgument()));
    }

    @Override
    public SWRLBuiltInAtom visit(@Nonnull SWRLBuiltInAtom node) {
        List<SWRLDArgument> atomObjects = new ArrayList<>();
        node.getArguments().forEach(a -> atomObjects.add(get(a)));
        return df.getSWRLBuiltInAtom(node.getPredicate(), atomObjects);
    }

    @Override
    public SWRLDifferentIndividualsAtom visit(
            @Nonnull SWRLDifferentIndividualsAtom node) {
        return df.getSWRLDifferentIndividualsAtom(get(node.getFirstArgument()),
                get(node.getSecondArgument()));
    }

    @Override
    public SWRLSameIndividualAtom visit(@Nonnull SWRLSameIndividualAtom node) {
        return df.getSWRLSameIndividualAtom(get(node.getFirstArgument()),
                get(node.getSecondArgument()));
    }

    @Override
    public SWRLVariable visit(@Nonnull SWRLVariable node) {
        return df.getSWRLVariable((IRI) get(node.getIRI()));
    }

    @Override
    public SWRLIndividualArgument visit(@Nonnull SWRLIndividualArgument node) {
        return df.getSWRLIndividualArgument(get(node.getIndividual()));
    }

    @Override
    public SWRLLiteralArgument visit(@Nonnull SWRLLiteralArgument node) {
        return df.getSWRLLiteralArgument(get(node.getLiteral()));
    }

    @Override
    public OWLHasKeyAxiom visit(@Nonnull OWLHasKeyAxiom axiom) {
        return df.getOWLHasKeyAxiom(get(axiom.getClassExpression()),
                set(axiom.propertyExpressions()), anns(axiom));
    }

    @Override
    public OWLDataIntersectionOf visit(@Nonnull OWLDataIntersectionOf node) {
        return df.getOWLDataIntersectionOf(set(node.operands()));
    }

    @Override
    public OWLDataUnionOf visit(@Nonnull OWLDataUnionOf node) {
        return df.getOWLDataUnionOf(set(node.operands()));
    }

    @Override
    public OWLAnnotationProperty visit(@Nonnull OWLAnnotationProperty property) {
        return df.getOWLAnnotationProperty(getIRI(property));
    }

    @Override
    public OWLAnnotationPropertyDomainAxiom visit(
            @Nonnull OWLAnnotationPropertyDomainAxiom axiom) {
        return df.getOWLAnnotationPropertyDomainAxiom(get(axiom.getProperty()),
                get(axiom.getDomain()), anns(axiom));
    }

    @Override
    public OWLAnnotationPropertyRangeAxiom visit(
            @Nonnull OWLAnnotationPropertyRangeAxiom axiom) {
        return df.getOWLAnnotationPropertyRangeAxiom(get(axiom.getProperty()),
                get(axiom.getRange()), anns(axiom));
    }

    @Override
    public OWLSubAnnotationPropertyOfAxiom visit(
            @Nonnull OWLSubAnnotationPropertyOfAxiom axiom) {
        return df.getOWLSubAnnotationPropertyOfAxiom(
                get(axiom.getSubProperty()), get(axiom.getSuperProperty()),
                anns(axiom));
    }

    @Override
    public OWLAnnotation visit(@Nonnull OWLAnnotation node) {
        return df.getOWLAnnotation(get(node.getProperty()),
                get(node.getValue()));
    }

    @Override
    public OWLAnonymousIndividual visit(OWLAnonymousIndividual individual) {
        return individual;
    }

    @Override
    public IRI visit(IRI iri) {
        for (EntityType<?> entityType : EntityType.values()) {
            OWLEntity entity = df.getOWLEntity(entityType, iri);
            IRI replacementIRI = replacementMap.get(entity);
            if (replacementIRI != null) {
                return replacementIRI;
            }
        }
        return iri;
    }

    @Override
    public OWLDatatypeDefinitionAxiom visit(
            @Nonnull OWLDatatypeDefinitionAxiom axiom) {
        return df.getOWLDatatypeDefinitionAxiom(get(axiom.getDatatype()),
                get(axiom.getDataRange()), anns(axiom));
    }

    /**
     * A utility function that duplicates a set of objects.
     * 
     * @param objects
     *        The set of object to be duplicated
     * @return The set of duplicated objects
     */
    @SuppressWarnings("unchecked")
    @Nonnull
    private <O extends OWLObject> Set<O> set(@Nonnull Stream<O> objects) {
        return asSet(objects.map(o -> (O) get(o)));
    }

    @SuppressWarnings("unchecked")
    @Nonnull
    protected <O> O get(OWLObject o) {
        return (O) o.accept(this);
    }
}
