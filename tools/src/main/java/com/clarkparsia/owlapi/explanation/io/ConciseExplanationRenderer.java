/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2011, Clark & Parsia, LLC
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package com.clarkparsia.owlapi.explanation.io;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.util.SimpleRenderer;

/** Explanation renderer in concise form. */
public class ConciseExplanationRenderer implements ExplanationRenderer {

    /** The Constant INDENT. */
    private static final String INDENT = "   ";
    /** The renderer. */
    private final SimpleRenderer renderer = new SimpleRenderer();
    /** The writer. */
    private PrintWriter writer;

    @Override
    public void startRendering(Writer w) {
        checkNotNull(w, "w cannot be null");
        writer = w instanceof PrintWriter ? (PrintWriter) w
                : new PrintWriter(w);
    }

    @Override
    public void
            render(OWLAxiom axiom, @Nonnull Set<Set<OWLAxiom>> explanations) {
        writer.println("Axiom: "
                + renderer.render(checkNotNull(axiom, "axiom cannot be null")));
        int expSize = checkNotNull(explanations.size());
        if (expSize == 0) {
            writer.println("Explanation: AXIOM IS NOT ENTAILED!");
            return;
        }
        if (expSize == 1) {
            writer.println("Explanation: ");
            Set<OWLAxiom> explanation = explanations.iterator().next();
            renderSingleExplanation(INDENT, explanation);
        } else {
            writer.println("Explanations (" + expSize + "): ");
            renderMultipleExplanations(explanations);
        }
        writer.println();
    }

    private void renderMultipleExplanations(Set<Set<OWLAxiom>> explanations) {
        int count = 1;
        for (Set<OWLAxiom> exp : explanations) {
            String header = count++ + ") ";
            renderSingleExplanation(header, exp);
        }
    }

    private void renderSingleExplanation(String _header, Set<OWLAxiom> axioms) {
        String header = _header;
        boolean first = true;
        for (OWLAxiom axiom : axioms) {
            if (first) {
                first = false;
            } else {
                header = INDENT;
            }
            writer.println(header + renderer.render(axiom));
        }
    }

    @Override
    public void endRendering() {
        writer.flush();
    }
}
