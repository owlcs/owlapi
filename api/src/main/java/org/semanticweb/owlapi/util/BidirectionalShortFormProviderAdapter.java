/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.RemoveAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 18-Apr-2007<br><br>
 *
 * A bidirectional short form provider which uses a specified short form provider
 * to generate the bidirectional entity--shortform mappings.
 */
public class BidirectionalShortFormProviderAdapter extends CachingBidirectionalShortFormProvider {

    private ShortFormProvider shortFormProvider;

    private Set<OWLOntology> ontologies;

    private OWLOntologyManager man;

    private OWLOntologyChangeListener changeListener = new OWLOntologyChangeListener() {

        public void ontologiesChanged(List<? extends OWLOntologyChange> changes) throws OWLException {
            handleChanges(changes);
        }
    };

    public BidirectionalShortFormProviderAdapter(ShortFormProvider shortFormProvider) {
        this.shortFormProvider = shortFormProvider;
    }


    /**
     * Creates a BidirectionalShortFormProvider that maps between the entities that are referenced in
     * the specified ontologies and the shortforms of these entities.
     * @param ontologies The ontologies that contain references to the entities to be mapped.
     * @param shortFormProvider The short form provider that should be used to generate the
     * short forms of the referenced entities.
     */
    public BidirectionalShortFormProviderAdapter(Set<OWLOntology> ontologies, ShortFormProvider shortFormProvider) {
        this.shortFormProvider = shortFormProvider;
        this.ontologies = new HashSet<OWLOntology>(ontologies);
        rebuild(new ReferencedEntitySetProvider(ontologies));
    }


    /**
     * Creates a BidirectionalShortFormProvider that maps between the entities that are referenced in
     * the specified ontologies and the shortforms of these entities.
     * Note that the <code>dispose</code> method must be called when the provider has
     * been finished with so that the provider may remove itself as a listener from the
     * manager.
     * @param ontologies The ontologies that contain references to the entities to be mapped.
     * @param shortFormProvider The short form provider that should be used to generate the
     * short forms of the referenced entities.
     * @param man This short form provider will track changes to ontologies.  The provider
     * will listen for ontology changes and update the cache of entity--shortform
     * mappings based on whether the specified ontologies contain
     * references to entities or not.
     */
    public BidirectionalShortFormProviderAdapter(OWLOntologyManager man, Set<OWLOntology> ontologies, ShortFormProvider shortFormProvider) {
    	this(ontologies, shortFormProvider);
        this.man = man;
        this.man.addOntologyChangeListener(changeListener);
    }


    @Override
	protected String generateShortForm(OWLEntity entity) {
        return shortFormProvider.getShortForm(entity);
    }


    /**
     * Disposes of this short form provider.  Note that this method MUST be called
     * if the constructor that specifies an ontology manager was used.
     */
    @Override
	public void dispose() {
        if(man != null) {
            man.removeOntologyChangeListener(changeListener);
        }
    }

    private void handleChanges(List<? extends OWLOntologyChange> changes) {
        Set<OWLEntity> processed = new HashSet<OWLEntity>();
        for(OWLOntologyChange chg : changes) {
            if (ontologies.contains(chg.getOntology())) {
                if(chg instanceof AddAxiom) {
                   AddAxiom addAx = (AddAxiom) chg;
                   for(OWLEntity ent : addAx.getEntities()) {
                       if(!processed.contains(ent)) {
                           processed.add(ent);
                           add(ent);
                       }
                   }
                }
                else if(chg instanceof RemoveAxiom) {
                   RemoveAxiom remAx = (RemoveAxiom) chg;
                   for(OWLEntity ent : remAx.getEntities()) {
                       if(!processed.contains(ent)) {
                           processed.add(ent);
                           boolean stillRef = false;
                           for(OWLOntology ont : ontologies) {
                               if(ont.containsEntityInSignature(ent)) {
                                   stillRef = true;
                                   break;
                               }
                           }
                           if(!stillRef) {
                               remove(ent);
                           }
                       }
                   }
                }
            }
        }
    }


}
