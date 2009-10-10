package uk.ac.manchester.cs.owlapi.dlsyntax;

import org.coode.string.EscapeUtils;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

import java.io.PrintWriter;
import java.util.Set;
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
 * Bio-Health Informatics Group<br>
 * Date: 10-Feb-2008<br><br>
 */
public class DLSyntaxHTMLOntologyStorer extends DLSyntaxOntologyStorerBase {

    private ShortFormProvider sfp;


    public DLSyntaxHTMLOntologyStorer() {
        sfp = new SimpleShortFormProvider();
    }


    public boolean canStoreOntology(OWLOntologyFormat ontologyFormat) {
        return ontologyFormat.equals(new DLSyntaxHTMLOntologyFormat());
    }


    protected String getRendering(final OWLEntity subject, OWLAxiom axiom) {
        DLSyntaxObjectRenderer ren = new DLSyntaxObjectRenderer() {

            protected String renderEntity(OWLEntity entity) {
                if (!entity.equals(subject)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("<a href=\"#");
                    sb.append(sfp.getShortForm(entity));
                    sb.append("\">");
                    sb.append(sfp.getShortForm(entity));
                    sb.append("</a>");
                    return sb.toString();
                }
                else {
                    return sfp.getShortForm(entity);
                }
            }


            protected void write(DLSyntax keyword) {
                super.write(EscapeUtils.escapeXML(keyword.toString()));
            }
        };
        ren.setFocusedObject(subject);
        ren.setShortFormProvider(sfp);
        String rendering = ren.render(axiom);
        return rendering;
    }


    protected void beginWritingOntology(OWLOntology ontology, PrintWriter writer) {
        writer.println("<html>");
        
        writer.println("<body>");
        writer.print("<h1>");
        writer.print("Ontology: ");
        writer.print(ontology.getOntologyID().toString());
        writer.println("</h1>");
    }

    protected void writeEntity(OWLEntity entity, PrintWriter writer) {

    }

    protected void endWritingOntology(OWLOntology ontology, PrintWriter writer) {
        writer.println("</body>");
        writer.println("</html>");
    }


    protected void beginWritingAxiom(OWLAxiom axiom, PrintWriter writer) {
        writer.println("<div class=\"axiombox\"> ");
    }


    protected void endWritingAxiom(OWLAxiom axiom, PrintWriter writer) {
        writer.println(" </div>");
    }


    protected void beginWritingAxioms(OWLEntity subject, Set<? extends OWLAxiom> axioms, PrintWriter writer) {
        writer.print("<h2><a name=\"");
        writer.print(sfp.getShortForm(subject));
        writer.print("\">");
        writer.print(subject.toString());
        writer.println("</a></h2>");
        writer.println("<div class=\"entitybox\">");
    }


    protected void endWritingAxioms(OWLEntity subject, Set<? extends OWLAxiom> axioms, PrintWriter writer) {
        writer.println("</div>");
    }


    protected void beginWritingGeneralAxioms(Set<? extends OWLAxiom> axioms, PrintWriter writer) {
        writer.println("<div>");
    }


    protected void endWritingGeneralAxioms(Set<? extends OWLAxiom> axioms, PrintWriter writer) {
        writer.println("</div>");
    }


    protected void beginWritingUsage(OWLEntity subject, Set<? extends OWLAxiom> axioms, PrintWriter writer) {
        writer.println("<div class=\"usage\" style=\"margin-left: 60px; size: tiny\">");
        writer.print("<h3>Usages (" + axioms.size() + ")</h3>");

    }


    protected void endWritingUsage(OWLEntity subject, Set<? extends OWLAxiom> axioms, PrintWriter writer) {
        writer.println("</div>");
    }
}
