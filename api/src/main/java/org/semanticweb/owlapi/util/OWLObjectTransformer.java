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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import org.semanticweb.owlapi.change.AddAxiomData;
import org.semanticweb.owlapi.change.AxiomChangeData;
import org.semanticweb.owlapi.change.RemoveAxiomData;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.RemoveImport;
import org.semanticweb.owlapi.model.RemoveOntologyAnnotation;
import org.semanticweb.owlapi.model.SetOntologyID;

/**
 * Transform axioms by rewriting parts of them.
 *
 * @param <T> type to transform
 * @author Ignazio
 * @since 4.1.4
 */
public class OWLObjectTransformer<T> {

    private UnaryOperator<T> transformer;
    private Predicate<Object> predicate;
    private Class<T> witness;
    private OWLDataFactory df;

    /**
     * @param predicate the predicate to match the axioms to rebuild
     * @param transformer the transformer to apply
     * @param df data factory to use for changes
     * @param witness witness class for the transformer
     */
    public OWLObjectTransformer(Predicate<Object> predicate, UnaryOperator<T> transformer,
        OWLDataFactory df, Class<T> witness) {
        this.predicate = checkNotNull(predicate, "predicate cannot be null");
        this.transformer = checkNotNull(transformer, "transformer cannot be null");
        this.df = checkNotNull(df, "df cannot be null");
        this.witness = checkNotNull(witness, "witness cannot be null");
    }

    /**
     * Create the required changes for this transformation to be applied to the input. Note: these
     * are AxiomChangeData changes, not ontology specific changes. There is no requirement for the
     * input to be an ontology or included in an ontology.
     *
     * @param o object to transform. Must be an axiom or an ontology for the change to be
     * meaningful.
     * @return A list of axiom changes that should be applied.
     */
    public List<AxiomChangeData> change(OWLObject o) {
        checkNotNull(o, "o cannot be null");
        List<AxiomChangeData> changes = new ArrayList<>();
        // no ontology changes will be collected
        Visitor<T> v = new Visitor<>(new ArrayList<OWLOntologyChange>(), changes, predicate,
            transformer, df, witness);
        o.accept(v);
        return changes;
    }

    /**
     * Create the required changes for this transformation to be applied to the input. These changes
     * are specific to the input ontology.
     *
     * @param ontology ontology to transform.
     * @return A list of changes that should be applied.
     */
    public List<OWLOntologyChange> change(OWLOntology ontology) {
        checkNotNull(ontology, "ontology cannot be null");
        List<AxiomChangeData> changes = new ArrayList<>();
        List<OWLOntologyChange> ontologyChanges = new ArrayList<>();
        Visitor<T> v = new Visitor<>(ontologyChanges, changes, predicate, transformer, df, witness);
        ontology.accept(v);
        for (AxiomChangeData change : changes) {
            ontologyChanges.add(change.createOntologyChange(ontology));
        }
        return ontologyChanges;
    }

    private static class Visitor<T> extends TransformerVisitorBase<T> {

        private List<AxiomChangeData> changes;
        private List<OWLOntologyChange> ontologyChanges;

        Visitor(List<OWLOntologyChange> ontologyChanges, List<AxiomChangeData> changes,
            Predicate<Object> predicate, UnaryOperator<T> transformer,
            OWLDataFactory df, Class<T> witness) {
            super(predicate, transformer, df, witness);
            this.changes = changes;
            this.ontologyChanges = ontologyChanges;
        }

        @Override
        protected OWLAxiom update(OWLAxiom transform, OWLAxiom axiom) {
            if (!axiom.equals(transform)) {
                changes.add(new RemoveAxiomData(axiom));
                changes.add(new AddAxiomData(transform));
                return transform;
            }
            return axiom;
        }

        @Override
        protected void visitId(OWLOntology ontology) {
            OWLOntologyID transform = check(ontology.getOntologyID());
            if (transform != null) {
                ontologyChanges.add(new SetOntologyID(ontology, transform));
            }
        }

        @Override
        protected void visitImports(OWLOntology ontology, OWLImportsDeclaration id) {
            OWLImportsDeclaration transform = check(id);
            if (transform != null) {
                ontologyChanges.add(new RemoveImport(ontology, id));
                ontologyChanges.add(new AddImport(ontology, transform));
            }
        }

        @Override
        protected void visitAnnotation(OWLOntology ontology, OWLAnnotation a) {
            OWLAnnotation transform = t(a);
            if (transform != a) {
                ontologyChanges.add(new RemoveOntologyAnnotation(ontology, a));
                ontologyChanges.add(new AddOntologyAnnotation(ontology, transform));
            }
        }
    }
}
