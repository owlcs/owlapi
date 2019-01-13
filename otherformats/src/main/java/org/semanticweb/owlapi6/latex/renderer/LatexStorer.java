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
package org.semanticweb.owlapi6.latex.renderer;

import static org.semanticweb.owlapi6.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi6.utilities.OWLAPIPreconditions.verifyNotNull;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi6.formats.LatexDocumentFormat;
import org.semanticweb.owlapi6.io.OWLStorer;
import org.semanticweb.owlapi6.io.OWLStorerParameters;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;
import org.semanticweb.owlapi6.model.OWLRuntimeException;
import org.semanticweb.owlapi6.utilities.ShortFormProvider;
import org.semanticweb.owlapi6.utility.OWLEntityComparator;
import org.semanticweb.owlapi6.utility.SimpleShortFormProvider;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public class LatexStorer implements OWLStorer {

    private final ShortFormProvider shortFormProvider = new SimpleShortFormProvider();
    private final OWLEntityComparator entityComparator = new OWLEntityComparator(shortFormProvider);

    private static String escapeName(String name) {
        return name.replace("_", "\\_");
    }

    private static Stream<? extends OWLAxiom> sortAxioms(Stream<? extends OWLAxiom> axioms) {
        return axioms.sorted(LatexStorer::compare);
    }

    @Override
    public void storeOntology(OWLOntology ontology, PrintWriter writer, OWLDocumentFormat format,
        OWLStorerParameters storerParameters) throws OWLOntologyStorageException {
        try {
            render(ontology, writer);
            writer.flush();
        } catch (OWLRuntimeException e) {
            throw new OWLOntologyStorageException(e);
        }
    }

    @Override
    public boolean canStoreOntology(OWLDocumentFormat ontologyFormat) {
        return ontologyFormat instanceof LatexDocumentFormat;
    }

    private void writeEntitySection(OWLEntity entity, LatexWriter w) {
        w.write("\\subsubsection*{");
        w.write(escapeName(shortFormProvider.getShortForm(entity)));
        w.write("}\n\n");
    }

    private void render(OWLOntology o, PrintWriter w) throws OWLOntologyStorageException {
        try {
            LatexWriter wr = new LatexWriter(w);
            wr.write("\\documentclass{article}\n");
            wr.write("\\parskip 0pt\n");
            wr.write("\\parindent 0pt\n");
            wr.write("\\oddsidemargin 0cm\n");
            wr.write("\\textwidth 19cm\n");
            wr.write("\\begin{document}\n\n");
            LatexObjectVisitor renderer = new LatexObjectVisitor(wr);
            Iterator<OWLClass> clses = o.classesInSignature().sorted(entityComparator).iterator();
            if (clses.hasNext()) {
                wr.write("\\subsection*{Classes}\n\n");
                while (clses.hasNext()) {
                    OWLClass cls = clses.next();
                    writeEntity(wr, renderer, cls, sortAxioms(o.axioms(cls)));
                }
            }
            wr.write("\\section*{Object properties}");
            o.objectPropertiesInSignature().sorted(entityComparator)
                .forEach(p -> writeEntity(wr, renderer, p, sortAxioms(o.axioms(p))));
            wr.write("\\section*{Data properties}");
            o.dataPropertiesInSignature().sorted(entityComparator)
                .forEach(prop -> writeEntity(wr, renderer, prop, sortAxioms(o.axioms(prop))));
            wr.write("\\section*{Individuals}");
            o.individualsInSignature().sorted(entityComparator)
                .forEach(i -> writeEntity(wr, renderer, i, sortAxioms(o.axioms(i))));
            wr.write("\\section*{Datatypes}");
            o.datatypesInSignature().sorted(entityComparator)
                .forEach(type -> writeEntity(wr, renderer, type, sortAxioms(o.axioms(type, EXCLUDED))));
            wr.write("\\end{document}\n");
            wr.flush();
        } catch (OWLRuntimeException e) {
            throw new OWLOntologyStorageException(e);
        }
    }

    protected void writeEntity(LatexWriter w, LatexObjectVisitor renderer, OWLEntity cls,
        Stream<? extends OWLAxiom> axioms) {
        writeEntitySection(cls, w);
        axioms.forEach(ax -> {
            renderer.setSubject(cls);
            ax.accept(renderer);
            w.write("\n\n");
        });
    }

    private static int compare(@Nullable OWLAxiom o1, @Nullable OWLAxiom o2) {
        int index1 = verifyNotNull(o1).getAxiomType().getIndex();
        int index2 = verifyNotNull(o2).getAxiomType().getIndex();
        return index1 - index2;
    }
}
