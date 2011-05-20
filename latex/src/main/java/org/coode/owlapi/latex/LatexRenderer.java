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

package org.coode.owlapi.latex;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.io.AbstractOWLRenderer;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.AxiomTypeProvider;
import org.semanticweb.owlapi.util.OWLEntityComparator;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: 15-Jun-2007<br><br>
 */
public class LatexRenderer extends AbstractOWLRenderer {

    private ShortFormProvider shortFormProvider;

    public LatexRenderer(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
        shortFormProvider = new SimpleShortFormProvider();
    }

    private void writeEntitySection(OWLEntity entity, LatexWriter w) {
        w.write("\\subsubsection*{");
        w.write(shortFormProvider.getShortForm(entity));
        w.write("}\n\n");
    }

    @Override
	public void render(OWLOntology ontology, Writer writer) throws OWLRendererException {
        try {


            LatexWriter w = new LatexWriter(writer);
            w.write("\\documentclass{article}\n");
            w.write("\\parskip 0pt\n");
            w.write("\\parindent 0pt\n");
            w.write("\\oddsidemargin 0cm\n");
            w.write("\\textwidth 19cm\n");

            w.write("\\begin{document}\n\n");

            LatexObjectVisitor renderer = new LatexObjectVisitor(w, getOWLOntologyManager().getOWLDataFactory());

            Collection<OWLClass> clses = sortEntities(ontology.getClassesInSignature());
            if (!clses.isEmpty()) {
                w.write("\\subsection*{Classes}\n\n");
            }
            for (OWLClass cls : clses) {
                writeEntitySection(cls, w);
                for (OWLAxiom ax : sortAxioms(ontology.getAxioms(cls))) {
                    renderer.setSubject(cls);
                    ax.accept(renderer);
                    w.write("\n\n");
                }
            }

            w.write("\\section*{Object properties}");

            for (OWLObjectProperty prop : sortEntities(ontology.getObjectPropertiesInSignature())) {
                writeEntitySection(prop, w);
                for (OWLAxiom ax : sortAxioms(ontology.getAxioms(prop))) {
                    ax.accept(renderer);
                    w.write("\n\n");
                }
            }

            w.write("\\section*{Data properties}");

            for (OWLDataProperty prop : sortEntities(ontology.getDataPropertiesInSignature())) {
                writeEntitySection(prop, w);
                for (OWLAxiom ax : sortAxioms(ontology.getAxioms(prop))) {
                    ax.accept(renderer);
                    w.write("\n\n");
                }
            }

            w.write("\\section*{Individuals}");

            for (OWLNamedIndividual ind : sortEntities(ontology.getIndividualsInSignature())) {
                writeEntitySection(ind, w);
                for (OWLAxiom ax : sortAxioms(ontology.getAxioms(ind))) {
                    ax.accept(renderer);
                    w.write("\n\n");
                }
            }

            w.write("\\section*{Datatypes}");
            for(OWLDatatype type:sortEntities(ontology.getDatatypesInSignature())) {
            	writeEntitySection(type, w);
            	for(OWLAxiom ax:sortAxioms(ontology.getAxioms(type))) {
            		ax.accept(renderer);
            		w.write("\n\n");
            	}
            }
//            writer.write(w.toString());
            writer.write("\\end{document}\n");
            writer.flush();
        }
        catch (IOException e) {
            throw new LatexRendererIOException(e);
        }
    }

    private <T extends OWLEntity> Collection<T> sortEntities(Set<T> entites) {
        List<T> list = new ArrayList<T>(entites);
        OWLEntityComparator entityComparator = new OWLEntityComparator(shortFormProvider);
        Collections.sort(list, entityComparator);
        return list;
    }

    private Collection<OWLAxiom> sortAxioms(Set<? extends OWLAxiom> axioms) {
        List<OWLAxiom> list = new ArrayList<OWLAxiom>(axioms);
        Collections.sort(list, new OWLAxiomComparator());
        return list;
    }


    private static class OWLAxiomComparator implements Comparator<OWLAxiom> {

        public int compare(OWLAxiom o1, OWLAxiom o2) {
            AxiomTypeProvider provider = new AxiomTypeProvider();
            int index1 = provider.getAxiomType(o1).getIndex();
            int index2 = provider.getAxiomType(o2).getIndex();
            return index1 - index2;
        }
    }
}
