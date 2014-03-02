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
package org.semanticweb.owlapi.modularity;

import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/**
 * An interface for any class implementing ontology segmentation or
 * modularisation.
 * 
 * @author Thomas Schneider
 * @author School of Computer Science
 * @author University of Manchester
 */
public interface OntologySegmenter {

    /**
     * Returns a set of axioms that is a segment of the ontology associated with
     * this segmenter. This segment is determined by the specified seed
     * signature (set of entities).
     * 
     * @param signature
     *        the seed signature
     * @return the segment as a set of axioms
     */
    @Nonnull
    Set<OWLAxiom> extract(@Nonnull Set<OWLEntity> signature);

    /**
     * Returns a set of axioms that is a segment of the ontology associated with
     * this segmenter. This segment is determined by a seed signature (set of
     * entities), which is the specified signature plus possibly all
     * superclasses and/or subclasses of the classes therein. Sub-/superclasses
     * are determined using the specified reasoner.
     * 
     * @param signature
     *        the seed signature
     * @param superClassLevel
     *        determines whether superclasses are added to the signature before
     *        segment extraction, see below for admissible values
     * @param subClassLevel
     *        determines whether subclasses are added to the signature before
     *        segment extraction, see below for admissible values
     * @param reasoner
     *        the reasoner to determine super-/subclasses
     * @return the segment as a set of axioms Meaning of the value of
     *         superClassLevel, subClassLevel:<br>
     *         Let this value be k. If k gerater than 0, then all classes are
     *         included that are (direct or indirect) super-/subclasses of some
     *         class in signature, with a distance of at most k to this class in
     *         the class hierarchy computed by reasoner. If k = 0, then no
     *         super-/subclasses are added. If k lesser than 0, then all direct
     *         and indirect super-/subclasses of any class in the signature are
     *         added.
     */
    @Nonnull
    Set<OWLAxiom> extract(@Nonnull Set<OWLEntity> signature,
            int superClassLevel, int subClassLevel,
            @Nonnull OWLReasoner reasoner);

    /**
     * Returns an ontology that is a segment of the ontology associated with
     * this segmenter.
     * 
     * @param signature
     *        the seed signature (set of entities) for the module
     * @param iri
     *        the URI for the module
     * @return the module, having the specified URI
     * @throws OWLOntologyCreationException
     *         if the module cannot be created
     */
    @Nonnull
    OWLOntology extractAsOntology(@Nonnull Set<OWLEntity> signature,
            @Nonnull IRI iri) throws OWLOntologyCreationException;

    /**
     * Returns an ontology that is a segment of the ontology associated with
     * this segmenter. This segment is determined by a seed signature (set of
     * entities), which is the specified signature plus possibly all
     * superclasses and/or subclasses of the classes therein. Sub-/superclasses
     * are determined using the specified reasoner.
     * 
     * @param signature
     *        the seed signature
     * @param iri
     *        the URI for the module
     * @param superClassLevel
     *        determines whether superclasses are added to the signature before
     *        segment extraction, see below for admissible values
     * @param subClassLevel
     *        determines whether subclasses are added to the signature before
     *        segment extraction, see below for admissible values
     * @param reasoner
     *        the reasoner to determine super-/subclasses
     * @return the segment as a set of axioms
     * @throws OWLOntologyCreationException
     *         if the module cannot be created Meaning of the value of
     *         superClassLevel, subClassLevel:<br>
     *         Let this value be k. If k gerater than 0, then all classes are
     *         included that are (direct or indirect) super-/subclasses of some
     *         class in signature, with a distance of at most k to this class in
     *         the class hierarchy computed by reasoner. If k = 0, then no
     *         super-/subclasses are added. If k lesser than 0, then all direct
     *         and indirect super-/subclasses of any class in the signature are
     *         added.
     */
    @Nonnull
    OWLOntology extractAsOntology(@Nonnull Set<OWLEntity> signature,
            @Nonnull IRI iri, int superClassLevel, int subClassLevel,
            @Nonnull OWLReasoner reasoner) throws OWLOntologyCreationException;
}
