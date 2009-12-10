package org.coode.owlapi.latex;

import org.semanticweb.owlapi.io.AbstractOWLRenderer;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.AxiomTypeProvider;
import org.semanticweb.owlapi.util.OWLEntityComparator;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

import java.io.IOException;
import java.io.Writer;
import java.util.*;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

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

            writer.write(w.toString());
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


    private class OWLAxiomComparator implements Comparator<OWLAxiom> {

        public int compare(OWLAxiom o1, OWLAxiom o2) {
            AxiomTypeProvider provider = new AxiomTypeProvider();
            int index1 = provider.getAxiomType(o1).getIndex();
            int index2 = provider.getAxiomType(o2).getIndex();
            return index1 - index2;
        }
    }
}
