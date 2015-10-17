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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.*;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.AbstractOWLStorer;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public abstract class DLSyntaxStorerBase extends AbstractOWLStorer {

    @Override
    protected void storeOntology(OWLOntology ontology, PrintWriter printWriter, OWLDocumentFormat format) {
        checkNotNull(ontology, "ontology cannot be null");
        checkNotNull(printWriter, "writer cannot be null");
        beginWritingOntology(ontology, printWriter);
        ontology.objectPropertiesInSignature().sorted()
                .forEach(p -> write(ontology, p, ontology.axioms(p), printWriter));
        ontology.dataPropertiesInSignature().sorted().forEach(p -> write(ontology, p, ontology.axioms(p), printWriter));
        ontology.classesInSignature().sorted().forEach(c -> write(ontology, c, ontology.axioms(c), printWriter));
        ontology.individualsInSignature().sorted().forEach(i -> write(ontology, i, ontology.axioms(i), printWriter));
        beginWritingGeneralAxioms(printWriter);
        ontology.generalClassAxioms().sorted().forEach(ax -> {
            beginWritingAxiom(printWriter);
            writeAxiom(null, ax, printWriter);
            endWritingAxiom(printWriter);
        } );
        endWritingGeneralAxioms(printWriter);
        endWritingOntology(ontology, printWriter);
        printWriter.flush();
    }

    private void write(OWLOntology ont, OWLEntity entity, Collection<? extends OWLAxiom> axioms, PrintWriter writer) {
        beginWritingAxioms(entity, writer);
        for (OWLAxiom ax : axioms) {
            beginWritingAxiom(writer);
            writeAxiom(entity, ax, writer);
            endWritingAxiom(writer);
        }
        List<OWLAxiom> usages = asList(ont.referencingAxioms(entity).sorted());
        usages.removeAll(axioms);
        beginWritingUsage(usages.size(), writer);
        for (OWLAxiom usage : usages) {
            if (!axioms.contains(usage)) {
                beginWritingAxiom(writer);
                writeAxiom(entity, usage, writer);
                endWritingAxiom(writer);
            }
        }
        endWritingUsage(writer);
        endWritingAxioms(writer);
    }

    private void write(OWLOntology ont, OWLEntity entity, Stream<? extends OWLAxiom> axioms, PrintWriter writer) {
        write(ont, entity, asSet(axioms), writer);
    }

    protected void writeAxiom(@Nullable OWLEntity subject, OWLAxiom axiom, PrintWriter writer) {
        writer.write(getRendering(subject, axiom));
    }

    @SuppressWarnings("unused")
    protected String getRendering(@Nullable OWLEntity subject, OWLAxiom axiom) {
        DLSyntaxObjectRenderer ren = new DLSyntaxObjectRenderer();
        return ren.render(axiom);
    }

    @SuppressWarnings("unused")
    protected void beginWritingOntology(OWLOntology ontology, PrintWriter writer) {}

    @SuppressWarnings("unused")
    protected void endWritingOntology(OWLOntology ontology, PrintWriter writer) {}

    @SuppressWarnings("unused")
    protected void beginWritingAxiom(PrintWriter writer) {}

    @SuppressWarnings("unused")
    protected void endWritingAxiom(PrintWriter writer) {}

    @SuppressWarnings("unused")
    protected void beginWritingAxioms(OWLEntity subject, PrintWriter writer) {}

    @SuppressWarnings("unused")
    protected void endWritingAxioms(PrintWriter writer) {}

    @SuppressWarnings("unused")
    protected void beginWritingUsage(int size, PrintWriter writer) {}

    @SuppressWarnings("unused")
    protected void endWritingUsage(PrintWriter writer) {}

    @SuppressWarnings("unused")
    protected void beginWritingGeneralAxioms(PrintWriter writer) {}

    @SuppressWarnings("unused")
    protected void endWritingGeneralAxioms(PrintWriter writer) {}
}
