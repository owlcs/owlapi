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
package org.semanticweb.owlapi.latex.renderer;

import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.io.AbstractOWLRenderer;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.util.OWLEntityComparator;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

/**
 * @author Matthew Horridge, The University Of Manchester, Medical Informatics
 *         Group
 * @since 2.0.0
 */
public class LatexRenderer extends AbstractOWLRenderer {

    private final ShortFormProvider shortFormProvider = new SimpleShortFormProvider();
    private final OWLEntityComparator entityComparator = new OWLEntityComparator(shortFormProvider);

    private void writeEntitySection(OWLEntity entity, LatexWriter w) {
        w.write("\\subsubsection*{");
        w.write(escapeName(shortFormProvider.getShortForm(entity)));
        w.write("}\n\n");
    }

    private static String escapeName(String name) {
        return name.replace("_", "\\_");
    }

    @Override
    public void render(OWLOntology o, PrintWriter _w) throws OWLRendererException {
        try {
            LatexWriter w = new LatexWriter(_w);
            w.write("\\documentclass{article}\n");
            w.write("\\parskip 0pt\n");
            w.write("\\parindent 0pt\n");
            w.write("\\oddsidemargin 0cm\n");
            w.write("\\textwidth 19cm\n");
            w.write("\\begin{document}\n\n");
            LatexObjectVisitor renderer = new LatexObjectVisitor(w, o.getOWLOntologyManager().getOWLDataFactory());
            Collection<OWLClass> clses = sortEntities(o.classesInSignature());
            if (!clses.isEmpty()) {
                w.write("\\subsection*{Classes}\n\n");
            }
            for (OWLClass cls : clses) {
                writeEntity(w, renderer, cls, sortAxioms(o.axioms(cls)));
            }
            w.write("\\section*{Object properties}");
            sortEntities(o.objectPropertiesInSignature())
                    .forEach(p -> writeEntity(w, renderer, p, sortAxioms(o.axioms(p))));
            w.write("\\section*{Data properties}");
            o.dataPropertiesInSignature().sorted(entityComparator)
                    .forEach(prop -> writeEntity(w, renderer, prop, sortAxioms(o.axioms(prop))));
            w.write("\\section*{Individuals}");
            o.individualsInSignature().sorted(entityComparator)
                    .forEach(i -> writeEntity(w, renderer, i, sortAxioms(o.axioms(i))));
            w.write("\\section*{Datatypes}");
            o.datatypesInSignature().sorted(entityComparator)
                    .forEach(type -> writeEntity(w, renderer, type, sortAxioms(o.axioms(type, EXCLUDED))));
            w.write("\\end{document}\n");
            w.flush();
        } catch (OWLRuntimeException e) {
            throw new LatexRendererIOException(e);
        }
    }

    protected void writeEntity(LatexWriter w, LatexObjectVisitor renderer, OWLEntity cls,
            Collection<? extends OWLAxiom> axioms) {
        writeEntitySection(cls, w);
        for (OWLAxiom ax : axioms) {
            renderer.setSubject(cls);
            ax.accept(renderer);
            w.write("\n\n");
        }
    }

    private <T extends OWLEntity> Collection<T> sortEntities(Stream<T> entities) {
        return asList(entities.sorted(entityComparator));
    }

    private static Collection<? extends OWLAxiom> sortAxioms(Stream<? extends OWLAxiom> axioms) {
        return asList(axioms.sorted(new OWLAxiomComparator()));
    }

    private static class OWLAxiomComparator implements Comparator<OWLAxiom>, Serializable {

        OWLAxiomComparator() {}

        @Override
        public int compare(@Nullable OWLAxiom o1, @Nullable OWLAxiom o2) {
            int index1 = verifyNotNull(o1).getAxiomType().getIndex();
            int index2 = verifyNotNull(o2).getAxiomType().getIndex();
            return index1 - index2;
        }
    }
}
