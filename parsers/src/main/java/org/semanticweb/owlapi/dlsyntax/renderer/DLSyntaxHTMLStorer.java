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

import java.io.PrintWriter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.formats.DLSyntaxHTMLDocumentFormat;
import org.semanticweb.owlapi.io.XMLUtils;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public class DLSyntaxHTMLStorer extends DLSyntaxStorerBase {

    protected final @Nonnull SimpleShortFormProvider sfp = new SimpleShortFormProvider();

    @Override
    public boolean canStoreOntology(OWLDocumentFormat ontologyFormat) {
        return ontologyFormat instanceof DLSyntaxHTMLDocumentFormat;
    }

    @Override
    protected String getRendering(@Nullable final OWLEntity subject, OWLAxiom axiom) {
        checkNotNull(axiom, "axiom cannot be null");
        DLSyntaxObjectRenderer ren = new DLSyntaxObjectRenderer() {

            @Override
            protected String renderEntity(OWLEntity entity) {
                String shortForm = sfp.getShortForm(checkNotNull(entity, "entity cannot be null"));
                if (entity.equals(subject)) {
                    return shortForm;
                }
                return "<a href=\"#" + shortForm + "\">" + shortForm + "</a>";
            }

            @Override
            protected void write(DLSyntax keyword) {
                write(XMLUtils.escapeXML(checkNotNull(keyword, "keyword cannot be null").toString()));
            }
        };
        ren.setFocusedObject(subject);
        ren.setShortFormProvider(sfp);
        return ren.render(axiom);
    }

    @Override
    protected void beginWritingOntology(OWLOntology ontology, PrintWriter writer) {
        checkNotNull(ontology, "ontology cannot be null");
        checkNotNull(writer, "writer cannot be null").println("<html>\n<body>\n<h1>Ontology: ");
        writer.print(ontology.getOntologyID());
        writer.println("</h1>");
    }

    @SuppressWarnings("unused")
    protected void writeEntity(OWLEntity entity, PrintWriter writer) {}

    @Override
    protected void endWritingOntology(OWLOntology ontology, PrintWriter writer) {
        checkNotNull(ontology, "ontology cannot be null");
        checkNotNull(writer, "writer cannot be null").println("</body>\n</html>");
    }

    @Override
    protected void beginWritingAxiom(PrintWriter writer) {
        checkNotNull(writer, "writer cannot be null").println("<div class=\"axiombox\"> ");
    }

    @Override
    protected void endWritingAxiom(PrintWriter writer) {
        checkNotNull(writer, "writer cannot be null").println(" </div>");
    }

    @Override
    protected void beginWritingAxioms(OWLEntity subject, PrintWriter writer) {
        checkNotNull(subject, "subject cannot be null");
        checkNotNull(writer, "writer cannot be null").print("<h2><a name=\"");
        writer.print(sfp.getShortForm(subject));
        writer.print("\">");
        writer.print(subject.getIRI());
        writer.println("</a></h2>\n<div class=\"entitybox\">");
    }

    @Override
    protected void endWritingAxioms(PrintWriter writer) {
        writer.println("</div>");
    }

    @Override
    protected void beginWritingGeneralAxioms(PrintWriter writer) {
        writer.println("<div>");
    }

    @Override
    protected void endWritingGeneralAxioms(PrintWriter writer) {
        writer.println("</div>");
    }

    @Override
    protected void beginWritingUsage(int size, PrintWriter writer) {
        writer.println("<div class=\"usage\" style=\"margin-left: 60px; size: tiny\">\n<h3>Usages (" + size + ")</h3>");
    }

    @Override
    protected void endWritingUsage(PrintWriter writer) {
        writer.println("</div>");
    }
}
