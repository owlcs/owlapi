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
package uk.ac.manchester.cs.owl.explanation.ordering;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.add;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.empty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;

/**
 * Provides ordering and indenting of explanations based on various ordering heuristics.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.2.0
 */
public class ExplanationOrdererImpl extends ExplanationOrdererImplNoManager {

    private static final AtomicLong RANDOMSTART = new AtomicLong(System.currentTimeMillis());

    private final OWLOntologyManager man;
    @Nullable
    private OWLOntology ont;

    /**
     * Instantiates a new explanation orderer impl.
     *
     * @param m the manager to use
     */
    public ExplanationOrdererImpl(OWLOntologyManager m) {
        man = checkNotNull(m, "m cannot be null");
    }

    @Override
    protected Set<OWLAxiom> getTargetAxioms(OWLEntity target) {
        Set<OWLAxiom> axioms = new HashSet<>();
        if (target.isOWLClass()) {
            add(axioms, getOntology().axioms(target.asOWLClass()));
        }
        if (target.isOWLObjectProperty()) {
            add(axioms, getOntology().axioms(target.asOWLObjectProperty()));
        }
        if (target.isOWLDataProperty()) {
            add(axioms, getOntology().axioms(target.asOWLDataProperty()));
        }
        if (target.isOWLNamedIndividual()) {
            add(axioms, getOntology().axioms(target.asOWLNamedIndividual()));
        }
        return axioms;
    }

    protected OWLOntology getOntology() {
        return verifyNotNull(ont, "ontology has not been set yet");
    }

    @Override
    protected Stream<? extends OWLAxiom> getAxioms(OWLEntity entity) {
        if (entity.isOWLClass()) {
            return getOntology().axioms(entity.asOWLClass());
        }
        if (entity.isOWLObjectProperty()) {
            return getOntology().axioms(entity.asOWLObjectProperty());
        }
        if (entity.isOWLDataProperty()) {
            return getOntology().axioms(entity.asOWLDataProperty());
        }
        if (entity.isOWLNamedIndividual()) {
            return getOntology().axioms(entity.asOWLNamedIndividual());
        }
        return empty();
    }

    @Override
    protected void buildIndices() {
        reset();
        AxiomMapBuilder builder = new AxiomMapBuilder();
        currentExplanation.forEach(ax -> ax.accept(builder));
        try {
            if (ont != null) {
                man.removeOntology(verifyNotNull(getOntology()));
            }
            ont = man.createOntology(IRI.create("http://www.semanticweb.org/",
                "ontology" + RANDOMSTART.incrementAndGet()));
            List<AddAxiom> changes = new ArrayList<>();
            for (OWLAxiom ax : currentExplanation) {
                changes.add(new AddAxiom(getOntology(), ax));
                ax.accept(builder);
            }
            man.applyChanges(changes);
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }
}
