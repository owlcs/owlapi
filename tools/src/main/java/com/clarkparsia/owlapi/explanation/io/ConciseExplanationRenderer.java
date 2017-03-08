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
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.util.SimpleRenderer;

/**
 * Explanation renderer in concise form.
 */
public class ConciseExplanationRenderer implements ExplanationRenderer {

    /**
     * The Constant INDENT.
     */
    private static final String INDENT = "   ";
    /**
     * The renderer.
     */
    private final SimpleRenderer renderer = new SimpleRenderer();
    /**
     * The writer.
     */
    @Nullable
    private Writer printWriter;

    @Override
    public void startRendering(Writer writer) {
        printWriter = checkNotNull(writer, "w cannot be null");
    }

    @Override
    public void render(OWLAxiom axiom, @Nonnull Set<Set<OWLAxiom>> explanations) {
        checkNotNull(axiom, "axiom cannot be null");
        try {
            getWriter().write("Axiom: " + renderer.render(axiom) + "\n");
            int expSize = explanations.size();
            if (expSize == 0) {
                getWriter().write("Explanation: AXIOM IS NOT ENTAILED!\n");
                return;
            }
            if (expSize == 1) {
                getWriter().write("Explanation: ");
                Set<OWLAxiom> explanation = explanations.iterator().next();
                renderSingleExplanation(INDENT, explanation);
            } else {
                getWriter().write("Explanations (" + expSize + "): \n");
                renderMultipleExplanations(explanations);
            }
            getWriter().write("\n");
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected Writer getWriter() {
        return verifyNotNull(printWriter, "printWriter not set yet");
    }

    private void renderMultipleExplanations(Set<Set<OWLAxiom>> explanations) {
        int count = 1;
        for (Set<OWLAxiom> exp : explanations) {
            String header = count++ + ") ";
            renderSingleExplanation(header, exp);
        }
    }

    private void renderSingleExplanation(String inputHeader, Set<OWLAxiom> axioms) {
        String header = inputHeader;
        boolean first = true;
        for (OWLAxiom axiom : axioms) {
            if (first) {
                first = false;
            } else {
                header = INDENT;
            }
            try {
                getWriter().write(header + renderer.render(axiom) + "\n");
            } catch (IOException e) {
                throw new OWLRuntimeException(e);
            }
        }
    }

    @Override
    public void endRendering() {
        try {
            getWriter().flush();
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }
}
