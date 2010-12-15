package com.clarkparsia.owlapi.explanation.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.util.SimpleRenderer;

/*
* Copyright (C) 2007, Clark & Parsia
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
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Clark & Parsia, LLC. <http://www.clarkparsia.com></p>
 * @author Evren Sirin
 */
public class ConciseExplanationRenderer implements ExplanationRenderer {

    private static final String INDENT = "   ";

    private SimpleRenderer renderer = new SimpleRenderer();

    private PrintWriter writer;


    public void startRendering(Writer writer) {
        this.writer = writer instanceof PrintWriter ? (PrintWriter) writer : new PrintWriter(writer);
    }


    public void render(OWLAxiom axiom, Set<Set<OWLAxiom>> explanations) throws OWLException, IOException {
        writer.println("Axiom: " + renderer.render(axiom));

        int expSize = explanations.size();

        if (expSize == 0) {
            writer.println("Explanation: AXIOM IS NOT ENTAILED!");
            return;
        }

        if (expSize == 1) {
            writer.println("Explanation: ");
            Set<OWLAxiom> explanation = explanations.iterator().next();
            renderSingleExplanation(INDENT, explanation);
        }
        else {
            writer.println("Explanations (" + expSize + "): ");
            renderMultipleExplanations(explanations);
        }

        writer.println();
    }


    private void renderMultipleExplanations(Set<Set<OWLAxiom>> explanations) throws OWLException, IOException {
        int count = 1;
        for (Set<OWLAxiom> exp : explanations) {
            String header = (count++) + ") ";
            renderSingleExplanation(header, exp);
        }
    }


    private void renderSingleExplanation(String header, Set<OWLAxiom> axioms) throws OWLException, IOException {
        boolean first = true;
        for (OWLAxiom axiom : axioms) {
            if (first)
                first = false;
            else
                header = INDENT;
            writer.println(header + renderer.render(axiom));
        }
    }


    public void endRendering() {
        writer.flush();
        // writer.close();
    }
}
