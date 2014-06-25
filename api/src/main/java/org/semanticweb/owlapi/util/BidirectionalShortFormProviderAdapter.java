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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.RemoveAxiom;

/**
 * A bidirectional short form provider which uses a specified short form
 * provider to generate the bidirectional entity--shortform mappings.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class BidirectionalShortFormProviderAdapter extends
        CachingBidirectionalShortFormProvider {

    @Nonnull
    private final ShortFormProvider shortFormProvider;
    private Set<OWLOntology> ontologies;
    private OWLOntologyManager man;
    @Nonnull
    private final OWLOntologyChangeListener changeListener = new OWLOntologyChangeListener() {

        @Override
        public void ontologiesChanged(
                @Nonnull List<? extends OWLOntologyChange> changes) {
            handleChanges(changes);
        }
    };

    /**
     * @param shortFormProvider
     *        the short form provider to use
     */
    public BidirectionalShortFormProviderAdapter(
            @Nonnull ShortFormProvider shortFormProvider) {
        this.shortFormProvider = checkNotNull(shortFormProvider,
                "shortFormProvider cannot be null");
    }

    /**
     * Creates a BidirectionalShortFormProvider that maps between the entities
     * that are referenced in the specified ontologies and the short forms of
     * these entities.
     * 
     * @param ontologies
     *        The ontologies that contain references to the entities to be
     *        mapped.
     * @param shortFormProvider
     *        The short form provider that should be used to generate the short
     *        forms of the referenced entities.
     */
    public BidirectionalShortFormProviderAdapter(
            @Nonnull Set<OWLOntology> ontologies,
            @Nonnull ShortFormProvider shortFormProvider) {
        this.shortFormProvider = checkNotNull(shortFormProvider,
                "shortFormProvider cannot be null");
        this.ontologies = new HashSet<>(checkNotNull(ontologies,
                "ontologies cannot be null"));
        rebuild(new ReferencedEntitySetProvider(ontologies));
    }

    /**
     * Creates a BidirectionalShortFormProvider that maps between the entities
     * that are referenced in the specified ontologies and the shortforms of
     * these entities. Note that the {@code dispose} method must be called when
     * the provider has been finished with so that the provider may remove
     * itself as a listener from the manager.
     * 
     * @param ontologies
     *        The ontologies that contain references to the entities to be
     *        mapped.
     * @param shortFormProvider
     *        The short form provider that should be used to generate the short
     *        forms of the referenced entities.
     * @param man
     *        This short form provider will track changes to ontologies. The
     *        provider will listen for ontology changes and update the cache of
     *        entity--shortform mappings based on whether the specified
     *        ontologies contain references to entities or not.
     */
    public BidirectionalShortFormProviderAdapter(
            @Nonnull OWLOntologyManager man,
            @Nonnull Set<OWLOntology> ontologies,
            @Nonnull ShortFormProvider shortFormProvider) {
        this(ontologies, shortFormProvider);
        this.man = checkNotNull(man, "man cannot be null");
        this.man.addOntologyChangeListener(changeListener);
    }

    @Override
    protected String generateShortForm(@Nonnull OWLEntity entity) {
        return shortFormProvider.getShortForm(entity);
    }

    @Override
    public void dispose() {
        if (man != null) {
            man.removeOntologyChangeListener(changeListener);
        }
    }

    void handleChanges(@Nonnull List<? extends OWLOntologyChange> changes) {
        if (ontologies == null) {
            return;
        }
        Set<OWLEntity> processed = new HashSet<>();
        for (OWLOntologyChange chg : changes) {
            if (ontologies.contains(chg.getOntology())) {
                if (chg.isAddAxiom()) {
                    AddAxiom addAx = (AddAxiom) chg;
                    for (OWLEntity ent : addAx.getSignature()) {
                        assert ent != null;
                        if (!processed.contains(ent)) {
                            processed.add(ent);
                            add(ent);
                        }
                    }
                } else if (chg.isRemoveAxiom()) {
                    RemoveAxiom remAx = (RemoveAxiom) chg;
                    for (OWLEntity ent : remAx.getSignature()) {
                        assert ent != null;
                        if (!processed.contains(ent)) {
                            processed.add(ent);
                            boolean stillRef = false;
                            for (OWLOntology ont : ontologies) {
                                if (ont.containsEntityInSignature(ent)) {
                                    stillRef = true;
                                    break;
                                }
                            }
                            if (!stillRef) {
                                remove(ent);
                            }
                        }
                    }
                }
            }
        }
    }
}
