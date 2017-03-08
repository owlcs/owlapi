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
package org.semanticweb.owlapi.model;

import java.util.stream.Stream;

import org.semanticweb.owlapi.model.axiomproviders.DeclarationAxiomProvider;
import org.semanticweb.owlapi.model.axiomproviders.DisjointAxiomProvider;
import org.semanticweb.owlapi.model.axiomproviders.DisjointUnionAxiomProvider;
import org.semanticweb.owlapi.model.axiomproviders.DomainAxiomProvider;
import org.semanticweb.owlapi.model.axiomproviders.EquivalentAxiomProvider;
import org.semanticweb.owlapi.model.axiomproviders.HasKeyAxiomProvider;
import org.semanticweb.owlapi.model.axiomproviders.InverseAxiomProvider;
import org.semanticweb.owlapi.model.axiomproviders.PropertyChainAxiomProvider;
import org.semanticweb.owlapi.model.axiomproviders.PropertyCharacteristicAxiomProvider;
import org.semanticweb.owlapi.model.axiomproviders.RangeAxiomProvider;
import org.semanticweb.owlapi.model.axiomproviders.SubAxiomProvider;
import org.semanticweb.owlapi.model.providers.AnnotationAssertionProvider;
import org.semanticweb.owlapi.model.providers.AnnotationProvider;
import org.semanticweb.owlapi.model.providers.AnonymousIndividualByIdProvider;
import org.semanticweb.owlapi.model.providers.AnonymousIndividualProvider;
import org.semanticweb.owlapi.model.providers.CardinalityRestrictionProvider;
import org.semanticweb.owlapi.model.providers.ClassAssertionProvider;
import org.semanticweb.owlapi.model.providers.ComplementProvider;
import org.semanticweb.owlapi.model.providers.DataAssertionProvider;
import org.semanticweb.owlapi.model.providers.DatatypeExpressionProvider;
import org.semanticweb.owlapi.model.providers.EntityByTypeProvider;
import org.semanticweb.owlapi.model.providers.EntityProvider;
import org.semanticweb.owlapi.model.providers.ExistentialProvider;
import org.semanticweb.owlapi.model.providers.HasSelfProvider;
import org.semanticweb.owlapi.model.providers.HasValueProvider;
import org.semanticweb.owlapi.model.providers.IndividualAssertionProvider;
import org.semanticweb.owlapi.model.providers.IntersectionProvider;
import org.semanticweb.owlapi.model.providers.InverseProvider;
import org.semanticweb.owlapi.model.providers.LiteralProvider;
import org.semanticweb.owlapi.model.providers.NominalProvider;
import org.semanticweb.owlapi.model.providers.OWLVocabularyProvider;
import org.semanticweb.owlapi.model.providers.ObjectAssertionProvider;
import org.semanticweb.owlapi.model.providers.SWRLProvider;
import org.semanticweb.owlapi.model.providers.UnionProvider;
import org.semanticweb.owlapi.model.providers.UniversalProvider;

/**
 * An interface for creating entities, class expressions and axioms.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public interface OWLDataFactory extends SWRLProvider, EntityProvider, EntityByTypeProvider,
    AnonymousIndividualProvider, AnonymousIndividualByIdProvider, OWLVocabularyProvider,
    LiteralProvider, AnnotationProvider, AnnotationAssertionProvider,
    ClassAssertionProvider, DataAssertionProvider, ObjectAssertionProvider,
    IndividualAssertionProvider, CardinalityRestrictionProvider, DisjointAxiomProvider,
    EquivalentAxiomProvider, PropertyCharacteristicAxiomProvider,
    DatatypeExpressionProvider, DomainAxiomProvider, RangeAxiomProvider,
    IntersectionProvider, UnionProvider, SubAxiomProvider, DeclarationAxiomProvider,
    ComplementProvider, NominalProvider, UniversalProvider, ExistentialProvider,
    HasKeyAxiomProvider, InverseAxiomProvider, HasValueProvider, InverseProvider,
    HasSelfProvider, DisjointUnionAxiomProvider, PropertyChainAxiomProvider {

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
