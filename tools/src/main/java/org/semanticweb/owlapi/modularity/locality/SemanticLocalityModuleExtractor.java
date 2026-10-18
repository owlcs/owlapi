/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2020, Marc Robin Nolte
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.modularity.locality;


import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import java.util.stream.Stream;

/**
 * Class to extract modules based on semantic locality.
 * Assumes that given ontologies conform to the OWL 2 DL profile (beyond this, the behavior is undefined).
 *
 * @author Marc Robin Nolte
 *
 */
public final class SemanticLocalityModuleExtractor
        extends LocalityModuleExtractor {

    private final SemanticLocalityEvaluator topEvaluator;
    private final SemanticLocalityEvaluator botEvaluator;

    /**
     * Creates a module extractor based on semantic locality.
     *
     * <p>Depending on the locality class, one or two semantic
     * locality evaluators are created. These evaluators maintain
     * their own reasoners and temporary ontologies.</p>
     *
     * <p>The caller must invoke {@link #dispose()} when the
     * extractor is no longer needed. The supplied ontology
     * manager and reasoner factory remain owned by the caller.</p>
     *
     * @param localityClass the locality class (BOTTOM, TOP or STAR)
     * @param axiomBase the axioms from which modules are extracted
     * @param ontologyManager the ontology manager used by the evaluators
     * @param reasonerFactory the factory used to create reasoners
     */
    public SemanticLocalityModuleExtractor(
            LocalityClass localityClass,
            Stream<OWLAxiom> axiomBase,
            OWLOntologyManager ontologyManager,
            OWLReasonerFactory reasonerFactory) {

        super(localityClass, axiomBase);

        SemanticLocalityEvaluator bottom = null;
        SemanticLocalityEvaluator top = null;

        try {
            if (localityClass == LocalityClass.BOTTOM
                    || localityClass == LocalityClass.STAR) {
                bottom = new SemanticLocalityEvaluator(
                        LocalityClass.BOTTOM,
                        ontologyManager,
                        reasonerFactory);
            }

            if (localityClass == LocalityClass.TOP
                    || localityClass == LocalityClass.STAR) {
                top = new SemanticLocalityEvaluator(
                        LocalityClass.TOP,
                        ontologyManager,
                        reasonerFactory);
            }
        } catch (RuntimeException | Error e) {
            if (bottom != null) {
                try {
                    bottom.dispose();
                } catch (RuntimeException cleanupFailure) {
                    e.addSuppressed(cleanupFailure);
                }
            }
            throw e;
        }

        this.botEvaluator = bottom;
        this.topEvaluator = top;
    }

    /**
     * Releases all resources owned by the locality evaluators.
     *
     * <p>The supplied ontology manager is not disposed.</p>
     *
     * <p>The extractor must not be used after disposal.</p>
     */
    public void dispose() {
        try {
            if (botEvaluator != null) {
                botEvaluator.dispose();
            }
        } finally {
            if (topEvaluator != null) {
                topEvaluator.dispose();
            }
        }
    }

    @Override
    protected LocalityEvaluator bottomEvaluator() {
        return botEvaluator;
    }

    @Override
    protected LocalityEvaluator topEvaluator() {
        return topEvaluator;
    }
}