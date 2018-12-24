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
package org.semanticweb.owlapi6.model;

import java.util.stream.Stream;

import org.semanticweb.owlapi6.model.axiomproviders.DeclarationAxiomProvider;
import org.semanticweb.owlapi6.model.axiomproviders.DisjointAxiomProvider;
import org.semanticweb.owlapi6.model.axiomproviders.DisjointUnionAxiomProvider;
import org.semanticweb.owlapi6.model.axiomproviders.DomainAxiomProvider;
import org.semanticweb.owlapi6.model.axiomproviders.EquivalentAxiomProvider;
import org.semanticweb.owlapi6.model.axiomproviders.HasKeyAxiomProvider;
import org.semanticweb.owlapi6.model.axiomproviders.InverseAxiomProvider;
import org.semanticweb.owlapi6.model.axiomproviders.PropertyChainAxiomProvider;
import org.semanticweb.owlapi6.model.axiomproviders.PropertyCharacteristicAxiomProvider;
import org.semanticweb.owlapi6.model.axiomproviders.RangeAxiomProvider;
import org.semanticweb.owlapi6.model.axiomproviders.SubAxiomProvider;
import org.semanticweb.owlapi6.model.providers.AnnotationAssertionProvider;
import org.semanticweb.owlapi6.model.providers.AnnotationProvider;
import org.semanticweb.owlapi6.model.providers.AnonymousIndividualByIdProvider;
import org.semanticweb.owlapi6.model.providers.AnonymousIndividualProvider;
import org.semanticweb.owlapi6.model.providers.CardinalityRestrictionProvider;
import org.semanticweb.owlapi6.model.providers.ClassAssertionProvider;
import org.semanticweb.owlapi6.model.providers.ComplementProvider;
import org.semanticweb.owlapi6.model.providers.DataAssertionProvider;
import org.semanticweb.owlapi6.model.providers.DatatypeExpressionProvider;
import org.semanticweb.owlapi6.model.providers.EntityByTypeProvider;
import org.semanticweb.owlapi6.model.providers.EntityProvider;
import org.semanticweb.owlapi6.model.providers.ExistentialProvider;
import org.semanticweb.owlapi6.model.providers.HasSelfProvider;
import org.semanticweb.owlapi6.model.providers.HasValueProvider;
import org.semanticweb.owlapi6.model.providers.IRICreator;
import org.semanticweb.owlapi6.model.providers.IRIProvider;
import org.semanticweb.owlapi6.model.providers.IndividualAssertionProvider;
import org.semanticweb.owlapi6.model.providers.IntersectionProvider;
import org.semanticweb.owlapi6.model.providers.InverseProvider;
import org.semanticweb.owlapi6.model.providers.LiteralProvider;
import org.semanticweb.owlapi6.model.providers.NominalProvider;
import org.semanticweb.owlapi6.model.providers.OWLVocabularyProvider;
import org.semanticweb.owlapi6.model.providers.ObjectAssertionProvider;
import org.semanticweb.owlapi6.model.providers.OntologyIDProvider;
import org.semanticweb.owlapi6.model.providers.SWRLProvider;
import org.semanticweb.owlapi6.model.providers.UnionProvider;
import org.semanticweb.owlapi6.model.providers.UniversalProvider;

/**
 * An interface for creating entities, class expressions and axioms.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public interface OWLDataFactory
    extends SWRLProvider, IRIProvider, OntologyIDProvider, IRICreator, EntityProvider,
    EntityByTypeProvider, AnonymousIndividualProvider, AnonymousIndividualByIdProvider,
    OWLVocabularyProvider, LiteralProvider, AnnotationProvider, AnnotationAssertionProvider,
    ClassAssertionProvider, DataAssertionProvider, ObjectAssertionProvider,
    IndividualAssertionProvider, CardinalityRestrictionProvider, DisjointAxiomProvider,
    EquivalentAxiomProvider, PropertyCharacteristicAxiomProvider, DatatypeExpressionProvider,
    DomainAxiomProvider, RangeAxiomProvider, IntersectionProvider, UnionProvider, SubAxiomProvider,
    DeclarationAxiomProvider, ComplementProvider, NominalProvider, UniversalProvider,
    ExistentialProvider, HasKeyAxiomProvider, InverseAxiomProvider, HasValueProvider,
    InverseProvider, HasSelfProvider, DisjointUnionAxiomProvider, PropertyChainAxiomProvider {

    /**
     * @param importedOntologyIRI imported ontology
     * @return an imports declaration
     */
    OWLImportsDeclaration getOWLImportsDeclaration(IRI importedOntologyIRI);

    /**
     * Empty all caches
     */
    void purge();

    /**
     * Shorthand for {@code getOWLAnnotation(getRDFSLabel(), getOWLLiteral(value))}
     *
     * @param value The annotation value.
     * @return an rdfs:label annotation with provided value
     */
    default OWLAnnotation getRDFSLabel(String value) {
        return getOWLAnnotation(getRDFSLabel(), getOWLLiteral(value));
    }

    /**
     * Shorthand for {@code getOWLAnnotation(getRDFSLabel(), getOWLLiteral(value), annotations)}
     *
     * @param value The annotation value.
     * @param annotations annotations
     * @return an rdfs:label annotation with provided value
     */
    default OWLAnnotation getRDFSLabel(String value, Stream<OWLAnnotation> annotations) {
        return getOWLAnnotation(getRDFSLabel(), getOWLLiteral(value), annotations);
    }

    /**
     * Shorthand for {@code getOWLAnnotation(getRDFSLabel(), getOWLLiteral(value))}
     *
     * @param value The annotation value.
     * @return an rdfs:label annotation with provided value
     */
    default OWLAnnotation getRDFSLabel(OWLAnnotationValue value) {
        return getOWLAnnotation(getRDFSLabel(), value);
    }

    /**
     * Shorthand for {@code getOWLAnnotation(getRDFSLabel(), getOWLLiteral(value), annotations)}
     *
     * @param value The annotation value.
     * @param annotations annotations
     * @return an rdfs:label annotation with provided value
     */
    default OWLAnnotation getRDFSLabel(OWLAnnotationValue value,
        Stream<OWLAnnotation> annotations) {
        return getOWLAnnotation(getRDFSLabel(), value, annotations);
    }

    /**
     * Shorthand for {@code getOWLAnnotation(getRDFSComment(), getOWLLiteral(value))}
     *
     * @param value The annotation value.
     * @return an rdfs:comment annotation with provided value
     */
    default OWLAnnotation getRDFSComment(String value) {
        return getOWLAnnotation(getRDFSComment(), getOWLLiteral(value));
    }

    /**
     * Shorthand for {@code getOWLAnnotation(getRDFSComment(), getOWLLiteral(value), annotations)}
     *
     * @param value The annotation value.
     * @param annotations annotations
     * @return an rdfs:comment annotation with provided value
     */
    default OWLAnnotation getRDFSComment(String value, Stream<OWLAnnotation> annotations) {
        return getOWLAnnotation(getRDFSComment(), getOWLLiteral(value), annotations);
    }

    /**
     * Shorthand for {@code getOWLAnnotation(getRDFSComment(), getOWLLiteral(value))}
     *
     * @param value The annotation value.
     * @return an rdfs:comment annotation with provided value
     */
    default OWLAnnotation getRDFSComment(OWLAnnotationValue value) {
        return getOWLAnnotation(getRDFSComment(), value);
    }

    /**
     * Shorthand for {@code getOWLAnnotation(getRDFSComment(), getOWLLiteral(value), annotations)}
     *
     * @param value The annotation value.
     * @param annotations annotations
     * @return an rdfs:comment annotation with provided value
     */
    default OWLAnnotation getRDFSComment(OWLAnnotationValue value,
        Stream<OWLAnnotation> annotations) {
        return getOWLAnnotation(getRDFSComment(), value, annotations);
    }
}
