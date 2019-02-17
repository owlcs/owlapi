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
package org.semanticweb.owlapi6.impl;

import static org.semanticweb.owlapi6.model.parameters.ChangeApplied.NO_OPERATION;
import static org.semanticweb.owlapi6.model.parameters.ChangeApplied.SUCCESSFULLY;

import java.io.Serializable;

import javax.inject.Inject;

import org.semanticweb.owlapi6.model.AddAxiom;
import org.semanticweb.owlapi6.model.AddImport;
import org.semanticweb.owlapi6.model.AddOntologyAnnotation;
import org.semanticweb.owlapi6.model.OWLAnnotationAxiom;
import org.semanticweb.owlapi6.model.OWLMutableOntology;
import org.semanticweb.owlapi6.model.OWLOntologyChange;
import org.semanticweb.owlapi6.model.OWLOntologyChangeVisitorEx;
import org.semanticweb.owlapi6.model.OWLOntologyID;
import org.semanticweb.owlapi6.model.OWLOntologyManager;
import org.semanticweb.owlapi6.model.OntologyConfigurator;
import org.semanticweb.owlapi6.model.RemoveAxiom;
import org.semanticweb.owlapi6.model.RemoveImport;
import org.semanticweb.owlapi6.model.RemoveOntologyAnnotation;
import org.semanticweb.owlapi6.model.SetOntologyID;
import org.semanticweb.owlapi6.model.parameters.ChangeApplied;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class OWLOntologyImpl extends OWLImmutableOntologyImpl
    implements OWLMutableOntology, Serializable {

    /**
     * @param manager ontology manager
     * @param ontologyID ontology id
     * @param config ontology configurator
     */
    @Inject
    public OWLOntologyImpl(OWLOntologyManager manager, OWLOntologyID ontologyID,
        OntologyConfigurator config) {
        super(manager, ontologyID, config);
    }

    /**
     * Determines if a change is applicable. A change may not be applicable for a number of reasons.
     * 
     * @param change The change to be tested.
     * @return {@code true} if the change is applicable, otherwise, {@code false}.
     */
    private boolean isChangeApplicable(OWLOntologyChange change) {
        return config.shouldLoadAnnotations() || !isAnnotationAxiom(change);
    }

    protected boolean isAnnotationAxiom(OWLOntologyChange change) {
        return change.isAddAxiom() && change.getAxiom() instanceof OWLAnnotationAxiom;
    }

    @Override
    public ChangeApplied enactChange(OWLOntologyChange change) {
        if (!isChangeApplicable(change)) {
            return ChangeApplied.UNSUCCESSFULLY;
        }
        OWLOntologyChangeFilter changeFilter = new OWLOntologyChangeFilter();
        getOWLOntologyManager().handleOntologyIDChange(change);
        ChangeApplied appliedChange = change.accept(changeFilter);
        getOWLOntologyManager().handleImportsChange(change);
        return appliedChange;
    }

    protected class OWLOntologyChangeFilter
        implements OWLOntologyChangeVisitorEx<ChangeApplied>, Serializable {

        @Override
        public ChangeApplied visit(RemoveAxiom change) {
            if (ints.removeAxiom(change.getAxiom())) {
                invalidateOntologyCaches(OWLOntologyImpl.this);
                return SUCCESSFULLY;
            }
            return NO_OPERATION;
        }

        @Override
        public ChangeApplied visit(SetOntologyID change) {
            OWLOntologyID id = change.getNewOntologyID();
            if (!id.equals(ontologyID)) {
                // force hashcode recomputation
                hashCode = 0;
                ontologyID = id;
                invalidateOntologyCaches(OWLOntologyImpl.this);
                return SUCCESSFULLY;
            }
            return NO_OPERATION;
        }

        @Override
        public ChangeApplied visit(AddAxiom change) {
            if (ints.addAxiom(change.getAxiom())) {
                invalidateOntologyCaches(OWLOntologyImpl.this);
                return SUCCESSFULLY;
            }
            return NO_OPERATION;
        }

        @Override
        public ChangeApplied visit(AddImport change) {
            if (ints.addImportsDeclaration(change.getImportDeclaration())) {
                invalidateOntologyCaches(OWLOntologyImpl.this);
                return SUCCESSFULLY;
            }
            return NO_OPERATION;
        }

        @Override
        public ChangeApplied visit(RemoveImport change) {
            if (ints.removeImportsDeclaration(change.getImportDeclaration())) {
                invalidateOntologyCaches(OWLOntologyImpl.this);
                return SUCCESSFULLY;
            }
            return NO_OPERATION;
        }

        @Override
        public ChangeApplied visit(AddOntologyAnnotation change) {
            if (ints.addOntologyAnnotation(change.getAnnotation())) {
                invalidateOntologyCaches(OWLOntologyImpl.this);
                return SUCCESSFULLY;
            }
            return NO_OPERATION;
        }

        @Override
        public ChangeApplied visit(RemoveOntologyAnnotation change) {
            if (ints.removeOntologyAnnotation(change.getAnnotation())) {
                invalidateOntologyCaches(OWLOntologyImpl.this);
                return SUCCESSFULLY;
            }
            return NO_OPERATION;
        }
    }
}
