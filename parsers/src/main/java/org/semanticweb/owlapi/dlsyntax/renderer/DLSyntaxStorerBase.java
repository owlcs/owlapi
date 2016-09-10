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
package org.semanticweb.owlapi.dlsyntax.renderer;

import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.util.CollectionFactory.sortOptionally;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.AbstractOWLStorer;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public abstract class DLSyntaxStorerBase extends AbstractOWLStorer {

    private static final long serialVersionUID = 40000L;
    private DLSyntaxObjectRenderer ren = new DLSyntaxObjectRenderer();

    @Override
    protected void storeOntology(@Nonnull OWLOntology ontology, Writer writer, OWLDocumentFormat format) {
        checkNotNull(ontology, "ontology cannot be null");
        PrintWriter printWriter = new PrintWriter(checkNotNull(writer, "writer cannot be null"));
        beginWritingOntology(ontology, printWriter);
        Set<OWLAxiom> printed = new HashSet<>();
        for (OWLObjectProperty prop : sortOptionally(ontology.getObjectPropertiesInSignature())) {
            assert prop != null;
            write(ontology, prop, ontology.getAxioms(prop, EXCLUDED), printWriter, printed);
        }
        for (OWLDataProperty prop : sortOptionally(ontology.getDataPropertiesInSignature())) {
            assert prop != null;
            write(ontology, prop, ontology.getAxioms(prop, EXCLUDED), printWriter, printed);
        }
        for (OWLClass cls : sortOptionally(ontology.getClassesInSignature())) {
            assert cls != null;
            write(ontology, cls, ontology.getAxioms(cls, EXCLUDED), printWriter, printed);
        }
        for (OWLNamedIndividual ind : sortOptionally(ontology.getIndividualsInSignature())) {
            assert ind != null;
            write(ontology, ind, ontology.getAxioms(ind, EXCLUDED), printWriter, printed);
        }
        beginWritingGeneralAxioms(ontology.getGeneralClassAxioms(), printWriter);
        for (OWLAxiom ax : ontology.getGeneralClassAxioms()) {
            assert ax != null;
            if (printed.add(ax)) {
                beginWritingAxiom(ax, printWriter);
                writeAxiom(null, ax, printWriter);
                endWritingAxiom(ax, printWriter);
            }
        }
        endWritingGeneralAxioms(ontology.getGeneralClassAxioms(), printWriter);
        endWritingOntology(ontology, printWriter);
        printWriter.flush();
    }

    private void write(@Nonnull OWLOntology ont, @Nonnull OWLEntity entity, @Nonnull Set<? extends OWLAxiom> axioms,
        @Nonnull PrintWriter writer, Set<OWLAxiom> printed) {
        beginWritingAxioms(entity, axioms, writer);
        for (OWLAxiom ax : sortOptionally(axioms)) {
            assert ax != null;
            if (printed.add(ax)) {
                beginWritingAxiom(ax, writer);
                writeAxiom(entity, ax, writer);
                endWritingAxiom(ax, writer);
            }
        }
        Set<OWLAxiom> usages = new TreeSet<>(ont.getReferencingAxioms(entity, EXCLUDED));
        usages.removeAll(axioms);
        beginWritingUsage(entity, usages, writer);
        for (OWLAxiom usage : usages) {
            assert usage != null;
            if (!axioms.contains(usage) && printed.add(usage)) {
                beginWritingAxiom(usage, writer);
                writeAxiom(entity, usage, writer);
                endWritingAxiom(usage, writer);
            }
        }
        endWritingUsage(entity, usages, writer);
        endWritingAxioms(entity, axioms, writer);
    }

    protected void writeAxiom(@Nullable OWLEntity subject, @Nonnull OWLAxiom axiom, @Nonnull PrintWriter writer) {
        writer.write(getRendering(subject, axiom));
    }

    @SuppressWarnings("unused")
    @Nonnull
    protected String getRendering(@Nullable OWLEntity subject, @Nonnull OWLAxiom axiom) {
        return ren.render(axiom);
    }

    @SuppressWarnings("unused")
    protected void beginWritingOntology(@Nonnull OWLOntology ontology, @Nonnull PrintWriter writer) {}

    @SuppressWarnings("unused")
    protected void endWritingOntology(@Nonnull OWLOntology ontology, @Nonnull PrintWriter writer) {}

    @SuppressWarnings("unused")
    protected void beginWritingAxiom(@Nonnull OWLAxiom axiom, @Nonnull PrintWriter writer) {}

    @SuppressWarnings("unused")
    protected void endWritingAxiom(@Nonnull OWLAxiom axiom, @Nonnull PrintWriter writer) {}

    @SuppressWarnings("unused")
    protected void beginWritingAxioms(@Nonnull OWLEntity subject, @Nonnull Set<? extends OWLAxiom> axioms,
        @Nonnull PrintWriter writer) {}

    @SuppressWarnings("unused")
    protected void endWritingAxioms(@Nonnull OWLEntity subject, @Nonnull Set<? extends OWLAxiom> axioms,
        @Nonnull PrintWriter writer) {}

    @SuppressWarnings("unused")
    protected void beginWritingUsage(@Nonnull OWLEntity subject, @Nonnull Set<? extends OWLAxiom> axioms,
        @Nonnull PrintWriter writer) {}

    @SuppressWarnings("unused")
    protected void endWritingUsage(@Nonnull OWLEntity subject, @Nonnull Set<? extends OWLAxiom> axioms,
        @Nonnull PrintWriter writer) {}

    @SuppressWarnings("unused")
    protected void beginWritingGeneralAxioms(@Nonnull Set<? extends OWLAxiom> axioms, @Nonnull PrintWriter writer) {}

    @SuppressWarnings("unused")
    protected void endWritingGeneralAxioms(@Nonnull Set<? extends OWLAxiom> axioms, @Nonnull PrintWriter writer) {}
}
