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
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.*;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class OWLObjectDuplicator implements OWLObjectVisitorEx<OWLObject> {

    protected final RemappingIndividualProvider anonProvider;
    private final OWLDataFactory df;
    private final Map<OWLEntity, IRI> replacementMap;
    private final Map<OWLLiteral, OWLLiteral> replacementLiterals;

    /**
     * Creates an object duplicator that duplicates objects using the specified data factory and uri
     * replacement map.
     *
     * @param m The manager providing data factory and config to be used for the duplication.
     * @param entityIRIReplacementMap The map to use for the replacement of URIs. Any uris the
     *        appear in the map will be replaced as objects are duplicated. This can be used to
     *        "rename" entities.
     */
    public OWLObjectDuplicator(Map<OWLEntity, IRI> entityIRIReplacementMap, OWLOntologyManager m) {
        this(entityIRIReplacementMap, Collections.<OWLLiteral, OWLLiteral>emptyMap(), m);
    }

    /**
     * Creates an object duplicator that duplicates objects using the specified data factory and uri
     * replacement map.
     *
     * @param m The manager providing data factory and config to be used for the duplication.
     * @param entityIRIReplacementMap The map to use for the replacement of URIs. Any uris the
     *        appear in the map will be replaced as objects are duplicated. This can be used to
     *        "rename" entities.
     * @param literals replacement literals
     */
    public OWLObjectDuplicator(Map<OWLEntity, IRI> entityIRIReplacementMap,
                    Map<OWLLiteral, OWLLiteral> literals, OWLOntologyManager m) {
        df = checkNotNull(m, "ontology manager cannot be null").getOWLDataFactory();
        anonProvider = new RemappingIndividualProvider(m.getOntologyConfigurator(), df);
        replacementMap = new HashMap<>(checkNotNull(entityIRIReplacementMap,
                        "entityIRIReplacementMap cannot be null"));
        checkNotNull(literals, "literals cannot be null");
        replacementLiterals = literals;
    }

    /**
     * Creates an object duplicator that duplicates objects using the specified data factory.
     *
     * @param m The manager providing data factory and config to be used for the duplication.
     */
    public OWLObjectDuplicator(OWLOntologyManager m) {
        this(Collections.<OWLEntity, IRI>emptyMap(), m);
    }

    /**
     * Creates an object duplicator that duplicates objects using the specified data factory and uri
     * replacement map.
     *
     * @param m The manager providing data factory and config to be used for the duplication.
     * @param iriReplacementMap The map to use for the replacement of URIs. Any uris the appear in
     *        the map will be replaced as objects are duplicated. This can be used to "rename"
     *        entities.
     */
    public OWLObjectDuplicator(OWLOntologyManager m, Map<IRI, IRI> iriReplacementMap) {
        this(remap(iriReplacementMap, m.getOWLDataFactory()), m);
    }

    private static Map<OWLEntity, IRI> remap(Map<IRI, IRI> iriReplacementMap, OWLDataFactory df) {
        Map<OWLEntity, IRI> map = new HashMap<>();
        iriReplacementMap.forEach((k, v) -> {
            map.put(df.getOWLClass(k), v);
            map.put(df.getOWLObjectProperty(k), v);
            map.put(df.getOWLDataProperty(k), v);
            map.put(df.getOWLNamedIndividual(k), v);
            map.put(df.getOWLDatatype(k), v);
            map.put(df.getOWLAnnotationProperty(k), v);
        });
        return map;
    }

    /**
     * @param object the object to duplicate
     * @param <O> return type
     * @return the duplicate
     */
    public <O extends OWLObject> O duplicateObject(O object) {
        checkNotNull(object, "object cannot be null");
        return t(object);
    }

    /**
     * Given an IRI belonging to an entity, returns a IRI. This may be the same IRI that the entity
     * has, or an alternative IRI if a replacement has been specified.
     *
     * @param entity The entity
     * @return The IRI
     */
    private IRI getIRI(OWLEntity entity) {
        checkNotNull(entity, "entity cannot be null");
        IRI replacement = replacementMap.get(entity);
        if (replacement == null) {
            return entity.getIRI();
        }
        return replacement;
    }

    private List<OWLAnnotation> a(HasAnnotations axiom) {
        checkNotNull(axiom, "axiom cannot be null");
        return asList(axiom.annotations().map(this::t));
    }

    @Override
    public OWLAsymmetricObjectPropertyAxiom visit(OWLAsymmetricObjectPropertyAxiom ax) {
        return df.getOWLAsymmetricObjectPropertyAxiom(t(ax.getProperty()), a(ax));
    }

    @Override
    public OWLClassAssertionAxiom visit(OWLClassAssertionAxiom ax) {
        return df.getOWLClassAssertionAxiom(t(ax.getClassExpression()), t(ax.getIndividual()),
                        a(ax));
    }

    @Override
    public OWLDataPropertyAssertionAxiom visit(OWLDataPropertyAssertionAxiom ax) {
        return df.getOWLDataPropertyAssertionAxiom(t(ax.getProperty()), t(ax.getSubject()),
                        t(ax.getObject()), a(ax));
    }

    @Override
    public OWLDataPropertyDomainAxiom visit(OWLDataPropertyDomainAxiom ax) {
        return df.getOWLDataPropertyDomainAxiom(t(ax.getProperty()), t(ax.getDomain()), a(ax));
    }

    @Override
    public OWLDataPropertyRangeAxiom visit(OWLDataPropertyRangeAxiom ax) {
        return df.getOWLDataPropertyRangeAxiom(t(ax.getProperty()), t(ax.getRange()), a(ax));
    }

    @Override
    public OWLSubDataPropertyOfAxiom visit(OWLSubDataPropertyOfAxiom ax) {
        return df.getOWLSubDataPropertyOfAxiom(t(ax.getSubProperty()), t(ax.getSuperProperty()),
                        a(ax));
    }

    @Override
    public OWLDeclarationAxiom visit(OWLDeclarationAxiom ax) {
        return df.getOWLDeclarationAxiom(t(ax.getEntity()), a(ax));
    }

    @Override
    public OWLDifferentIndividualsAxiom visit(OWLDifferentIndividualsAxiom ax) {
        return df.getOWLDifferentIndividualsAxiom(list(ax.individuals()), a(ax));
    }

    @Override
    public OWLDisjointClassesAxiom visit(OWLDisjointClassesAxiom ax) {
        return df.getOWLDisjointClassesAxiom(list(ax.classExpressions()), a(ax));
    }

    @Override
    public OWLDisjointDataPropertiesAxiom visit(OWLDisjointDataPropertiesAxiom ax) {
        return df.getOWLDisjointDataPropertiesAxiom(list(ax.properties()), a(ax));
    }

    @Override
    public OWLDisjointObjectPropertiesAxiom visit(OWLDisjointObjectPropertiesAxiom ax) {
        return df.getOWLDisjointObjectPropertiesAxiom(list(ax.properties()), a(ax));
    }

    @Override
    public OWLDisjointUnionAxiom visit(OWLDisjointUnionAxiom ax) {
        return df.getOWLDisjointUnionAxiom(t(ax.getOWLClass()), list(ax.classExpressions()), a(ax));
    }

    @Override
    public OWLAnnotationAssertionAxiom visit(OWLAnnotationAssertionAxiom ax) {
        return df.getOWLAnnotationAssertionAxiom(t(ax.getProperty()), t(ax.getSubject()),
                        t(ax.getValue()), a(ax));
    }

    @Override
    public OWLEquivalentClassesAxiom visit(OWLEquivalentClassesAxiom ax) {
        return df.getOWLEquivalentClassesAxiom(list(ax.classExpressions()), a(ax));
    }

    @Override
    public OWLEquivalentDataPropertiesAxiom visit(OWLEquivalentDataPropertiesAxiom ax) {
        return df.getOWLEquivalentDataPropertiesAxiom(list(ax.properties()), a(ax));
    }

    @Override
    public OWLEquivalentObjectPropertiesAxiom visit(OWLEquivalentObjectPropertiesAxiom ax) {
        return df.getOWLEquivalentObjectPropertiesAxiom(list(ax.properties()), a(ax));
    }

    @Override
    public OWLFunctionalDataPropertyAxiom visit(OWLFunctionalDataPropertyAxiom ax) {
        return df.getOWLFunctionalDataPropertyAxiom(t(ax.getProperty()), a(ax));
    }

    @Override
    public OWLFunctionalObjectPropertyAxiom visit(OWLFunctionalObjectPropertyAxiom ax) {
        return df.getOWLFunctionalObjectPropertyAxiom(t(ax.getProperty()), a(ax));
    }

    @Override
    public OWLInverseFunctionalObjectPropertyAxiom visit(
                    OWLInverseFunctionalObjectPropertyAxiom ax) {
        return df.getOWLInverseFunctionalObjectPropertyAxiom(t(ax.getProperty()), a(ax));
    }

    @Override
    public OWLInverseObjectPropertiesAxiom visit(OWLInverseObjectPropertiesAxiom ax) {
        return df.getOWLInverseObjectPropertiesAxiom(t(ax.getFirstProperty()),
                        t(ax.getSecondProperty()), a(ax));
    }

    @Override
    public OWLIrreflexiveObjectPropertyAxiom visit(OWLIrreflexiveObjectPropertyAxiom ax) {
        return df.getOWLIrreflexiveObjectPropertyAxiom(t(ax.getProperty()), a(ax));
    }

    @Override
    public OWLNegativeDataPropertyAssertionAxiom visit(OWLNegativeDataPropertyAssertionAxiom ax) {
        return df.getOWLNegativeDataPropertyAssertionAxiom(t(ax.getProperty()), t(ax.getSubject()),
                        t(ax.getObject()), a(ax));
    }

    @Override
    public OWLNegativeObjectPropertyAssertionAxiom visit(
                    OWLNegativeObjectPropertyAssertionAxiom ax) {
        return df.getOWLNegativeObjectPropertyAssertionAxiom(t(ax.getProperty()),
                        t(ax.getSubject()), t(ax.getObject()), a(ax));
    }

    @Override
    public OWLObjectPropertyAssertionAxiom visit(OWLObjectPropertyAssertionAxiom ax) {
        return df.getOWLObjectPropertyAssertionAxiom(t(ax.getProperty()), t(ax.getSubject()),
                        t(ax.getObject()), a(ax));
    }

    @Override
    public OWLSubPropertyChainOfAxiom visit(OWLSubPropertyChainOfAxiom ax) {
        List<OWLObjectPropertyExpression> chain =
                        asList(ax.getPropertyChain().stream().map(this::t));
        return df.getOWLSubPropertyChainOfAxiom(chain, t(ax.getSuperProperty()), a(ax));
    }

    @Override
    public OWLObjectPropertyDomainAxiom visit(OWLObjectPropertyDomainAxiom ax) {
        return df.getOWLObjectPropertyDomainAxiom(t(ax.getProperty()), t(ax.getDomain()), a(ax));
    }

    @Override
    public OWLObjectPropertyRangeAxiom visit(OWLObjectPropertyRangeAxiom ax) {
        return df.getOWLObjectPropertyRangeAxiom(t(ax.getProperty()), t(ax.getRange()), a(ax));
    }

    @Override
    public OWLSubObjectPropertyOfAxiom visit(OWLSubObjectPropertyOfAxiom ax) {
        return df.getOWLSubObjectPropertyOfAxiom(t(ax.getSubProperty()), t(ax.getSuperProperty()),
                        a(ax));
    }

    @Override
    public OWLReflexiveObjectPropertyAxiom visit(OWLReflexiveObjectPropertyAxiom ax) {
        return df.getOWLReflexiveObjectPropertyAxiom(t(ax.getProperty()), a(ax));
    }

    @Override
    public OWLSameIndividualAxiom visit(OWLSameIndividualAxiom ax) {
        return df.getOWLSameIndividualAxiom(list(ax.individuals()), a(ax));
    }

    @Override
    public OWLSubClassOfAxiom visit(OWLSubClassOfAxiom ax) {
        return df.getOWLSubClassOfAxiom(t(ax.getSubClass()), t(ax.getSuperClass()), a(ax));
    }

    @Override
    public OWLSymmetricObjectPropertyAxiom visit(OWLSymmetricObjectPropertyAxiom ax) {
        return df.getOWLSymmetricObjectPropertyAxiom(t(ax.getProperty()), a(ax));
    }

    @Override
    public OWLTransitiveObjectPropertyAxiom visit(OWLTransitiveObjectPropertyAxiom ax) {
        return df.getOWLTransitiveObjectPropertyAxiom(t(ax.getProperty()), a(ax));
    }

    @Override
    public OWLClass visit(OWLClass ce) {
        return df.getOWLClass(getIRI(ce));
    }

    @Override
    public OWLDataAllValuesFrom visit(OWLDataAllValuesFrom ce) {
        return df.getOWLDataAllValuesFrom(t(ce.getProperty()), t(ce.getFiller()));
    }

    @Override
    public OWLDataExactCardinality visit(OWLDataExactCardinality ce) {
        return df.getOWLDataExactCardinality(ce.getCardinality(), t(ce.getProperty()),
                        t(ce.getFiller()));
    }

    @Override
    public OWLDataMaxCardinality visit(OWLDataMaxCardinality ce) {
        return df.getOWLDataMaxCardinality(ce.getCardinality(), t(ce.getProperty()),
                        t(ce.getFiller()));
    }

    @Override
    public OWLDataMinCardinality visit(OWLDataMinCardinality ce) {
        return df.getOWLDataMinCardinality(ce.getCardinality(), t(ce.getProperty()),
                        t(ce.getFiller()));
    }

    @Override
    public OWLDataSomeValuesFrom visit(OWLDataSomeValuesFrom ce) {
        return df.getOWLDataSomeValuesFrom(t(ce.getProperty()), t(ce.getFiller()));
    }

    @Override
    public OWLDataHasValue visit(OWLDataHasValue ce) {
        return df.getOWLDataHasValue(t(ce.getProperty()), t(ce.getFiller()));
    }

    @Override
    public OWLObjectAllValuesFrom visit(OWLObjectAllValuesFrom ce) {
        return df.getOWLObjectAllValuesFrom(t(ce.getProperty()), t(ce.getFiller()));
    }

    @Override
    public OWLObjectComplementOf visit(OWLObjectComplementOf ce) {
        return df.getOWLObjectComplementOf(t(ce.getOperand()));
    }

    @Override
    public OWLObjectExactCardinality visit(OWLObjectExactCardinality ce) {
        return df.getOWLObjectExactCardinality(ce.getCardinality(), t(ce.getProperty()),
                        t(ce.getFiller()));
    }

    @Override
    public OWLObjectIntersectionOf visit(OWLObjectIntersectionOf ce) {
        return df.getOWLObjectIntersectionOf(list(ce.operands()));
    }

    @Override
    public OWLObjectMaxCardinality visit(OWLObjectMaxCardinality ce) {
        return df.getOWLObjectMaxCardinality(ce.getCardinality(), t(ce.getProperty()),
                        t(ce.getFiller()));
    }

    @Override
    public OWLObjectMinCardinality visit(OWLObjectMinCardinality ce) {
        OWLObjectPropertyExpression prop = t(ce.getProperty());
        return df.getOWLObjectMinCardinality(ce.getCardinality(), prop, t(ce.getFiller()));
    }

    @Override
    public OWLObjectOneOf visit(OWLObjectOneOf ce) {
        return df.getOWLObjectOneOf(list(ce.individuals()));
    }

    @Override
    public OWLObjectHasSelf visit(OWLObjectHasSelf ce) {
        return df.getOWLObjectHasSelf(t(ce.getProperty()));
    }

    @Override
    public OWLObjectSomeValuesFrom visit(OWLObjectSomeValuesFrom ce) {
        return df.getOWLObjectSomeValuesFrom(t(ce.getProperty()), t(ce.getFiller()));
    }

    @Override
    public OWLObjectUnionOf visit(OWLObjectUnionOf ce) {
        return df.getOWLObjectUnionOf(list(ce.operands()));
    }

    @Override
    public OWLObjectHasValue visit(OWLObjectHasValue ce) {
        return df.getOWLObjectHasValue(t(ce.getProperty()), t(ce.getFiller()));
    }

    @Override
    public OWLDataComplementOf visit(OWLDataComplementOf node) {
        return df.getOWLDataComplementOf(t(node.getDataRange()));
    }

    @Override
    public OWLDataOneOf visit(OWLDataOneOf node) {
        return df.getOWLDataOneOf(list(node.values()));
    }

    @Override
    public OWLDatatype visit(OWLDatatype node) {
        return df.getOWLDatatype(getIRI(node));
    }

    @Override
    public OWLDatatypeRestriction visit(OWLDatatypeRestriction node) {
        return df.getOWLDatatypeRestriction(t(node.getDatatype()), list(node.facetRestrictions()));
    }

    @Override
    public OWLFacetRestriction visit(OWLFacetRestriction node) {
        return df.getOWLFacetRestriction(node.getFacet(), t(node.getFacetValue()));
    }

    @Override
    public OWLLiteral visit(OWLLiteral node) {
        OWLLiteral l = replacementLiterals.get(node);
        if (l != null) {
            return l;
        }
        if (node.hasLang()) {
            return df.getOWLLiteral(node.getLiteral(), node.getLang());
        }
        return df.getOWLLiteral(node.getLiteral(), t(node.getDatatype()));
    }

    @Override
    public OWLDataProperty visit(OWLDataProperty property) {
        return df.getOWLDataProperty(getIRI(property));
    }

    @Override
    public OWLObjectProperty visit(OWLObjectProperty property) {
        return df.getOWLObjectProperty(getIRI(property));
    }

    @Override
    public OWLObjectInverseOf visit(OWLObjectInverseOf property) {
        OWLObjectPropertyExpression inverse = property.getInverse();
        if (inverse.isAnonymous()) {
            return df.getOWLObjectInverseOf(t(property.getNamedProperty()));
        }
        return df.getOWLObjectInverseOf(t(inverse.asOWLObjectProperty()));
    }

    @Override
    public OWLNamedIndividual visit(OWLNamedIndividual individual) {
        return df.getOWLNamedIndividual(getIRI(individual));
    }

    @Override
    public OWLOntology visit(OWLOntology ontology) {
        // Should we duplicate ontologies here? Probably not.
        return ontology;
    }

    @Override
    public SWRLRule visit(SWRLRule rule) {
        return df.getSWRLRule(list(rule.body()), list(rule.head()), a(rule));
    }

    @Override
    public SWRLClassAtom visit(SWRLClassAtom node) {
        return df.getSWRLClassAtom(t(node.getPredicate()), t(node.getArgument()));
    }

    @Override
    public SWRLDataRangeAtom visit(SWRLDataRangeAtom node) {
        return df.getSWRLDataRangeAtom(t(node.getPredicate()), t(node.getArgument()));
    }

    @Override
    public SWRLObjectPropertyAtom visit(SWRLObjectPropertyAtom node) {
        return df.getSWRLObjectPropertyAtom(t(node.getPredicate()), t(node.getFirstArgument()),
                        t(node.getSecondArgument()));
    }

    @Override
    public SWRLDataPropertyAtom visit(SWRLDataPropertyAtom node) {
        return df.getSWRLDataPropertyAtom(t(node.getPredicate()), t(node.getFirstArgument()),
                        t(node.getSecondArgument()));
    }

    @Override
    public SWRLBuiltInAtom visit(SWRLBuiltInAtom node) {
        return df.getSWRLBuiltInAtom(node.getPredicate(), list(node.arguments()));
    }

    @Override
    public SWRLDifferentIndividualsAtom visit(SWRLDifferentIndividualsAtom node) {
        return df.getSWRLDifferentIndividualsAtom(t(node.getFirstArgument()),
                        t(node.getSecondArgument()));
    }

    @Override
    public SWRLSameIndividualAtom visit(SWRLSameIndividualAtom node) {
        return df.getSWRLSameIndividualAtom(t(node.getFirstArgument()),
                        t(node.getSecondArgument()));
    }

    @Override
    public SWRLVariable visit(SWRLVariable node) {
        return df.getSWRLVariable(t(node.getIRI()));
    }

    @Override
    public SWRLIndividualArgument visit(SWRLIndividualArgument node) {
        return df.getSWRLIndividualArgument(t(node.getIndividual()));
    }

    @Override
    public SWRLLiteralArgument visit(SWRLLiteralArgument node) {
        return df.getSWRLLiteralArgument(t(node.getLiteral()));
    }

    @Override
    public OWLHasKeyAxiom visit(OWLHasKeyAxiom ax) {
        return df.getOWLHasKeyAxiom(t(ax.getClassExpression()), list(ax.propertyExpressions()),
                        a(ax));
    }

    @Override
    public OWLDataIntersectionOf visit(OWLDataIntersectionOf node) {
        return df.getOWLDataIntersectionOf(list(node.operands()));
    }

    @Override
    public OWLDataUnionOf visit(OWLDataUnionOf node) {
        return df.getOWLDataUnionOf(list(node.operands()));
    }

    @Override
    public OWLAnnotationProperty visit(OWLAnnotationProperty property) {
        return df.getOWLAnnotationProperty(getIRI(property));
    }

    @Override
    public OWLAnnotationPropertyDomainAxiom visit(OWLAnnotationPropertyDomainAxiom ax) {
        return df.getOWLAnnotationPropertyDomainAxiom(t(ax.getProperty()), t(ax.getDomain()),
                        a(ax));
    }

    @Override
    public OWLAnnotationPropertyRangeAxiom visit(OWLAnnotationPropertyRangeAxiom ax) {
        return df.getOWLAnnotationPropertyRangeAxiom(t(ax.getProperty()), t(ax.getRange()), a(ax));
    }

    @Override
    public OWLSubAnnotationPropertyOfAxiom visit(OWLSubAnnotationPropertyOfAxiom ax) {
        return df.getOWLSubAnnotationPropertyOfAxiom(t(ax.getSubProperty()),
                        t(ax.getSuperProperty()), a(ax));
    }

    @Override
    public OWLAnnotation visit(OWLAnnotation node) {
        return df.getOWLAnnotation(t(node.getProperty()), t(node.getValue()),
                        node.annotations().map(this::t));
    }

    @Override
    public OWLAnonymousIndividual visit(OWLAnonymousIndividual individual) {
        return anonProvider.getOWLAnonymousIndividual(individual.getID().getID());
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
    public OWLDatatypeDefinitionAxiom visit(OWLDatatypeDefinitionAxiom ax) {
        return df.getOWLDatatypeDefinitionAxiom(t(ax.getDatatype()), t(ax.getDataRange()), a(ax));
    }

    /**
     * A utility function that duplicates a set of objects.
     *
     * @param objects The set of object to be duplicated
     * @return The set of duplicated objects
     */
    protected <O extends OWLObject> List<O> list(Stream<O> objects) {
        return asList(objects.map(this::t));
    }

    @SuppressWarnings("unchecked")
    protected <O extends OWLObject> O t(O o) {
        return (O) o.accept(this);
    }
}
