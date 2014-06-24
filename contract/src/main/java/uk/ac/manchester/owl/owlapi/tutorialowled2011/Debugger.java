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
package uk.ac.manchester.owl.owlapi.tutorialowled2011;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.debugging.BlackBoxOWLDebugger;
import org.semanticweb.owlapi.debugging.OWLDebugger;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

/**
 * This class demonstrates some aspects of the OWL API. It expects three
 * arguments:
 * <ol>
 * <li>The URI of an ontology</li>
 * <li>The URI of a reasoner</li>
 * <li>A location to place the results.</li>
 * </ol>
 * When executed, the class will find all inconsistent classes in the ontology.
 * For each inconsistent class, the debugger will be used to determine the set
 * of support for the inconsistency. A report will then be written to the outpur
 * file.
 * 
 * @author Sean Bechhofer, The University Of Manchester, Information Management
 *         Group
 * @since 2.0.0
 */
@SuppressWarnings("javadoc")
public class Debugger {

    @Nonnull
    private final OWLOntology ontology;
    @Nonnull
    private final OWLDebugger debugger;
    private final OWLReasoner checker;
    @Nonnull
    private final OWLClass bottom;

    public Debugger(@Nonnull OWLOntologyManager manager,
            @Nonnull OWLOntology ontology,
            @Nonnull OWLReasonerFactory reasonerFactory) {
        this.ontology = ontology;
        checker = reasonerFactory.createNonBufferingReasoner(ontology);
        /* Create a new debugger */
        debugger = new BlackBoxOWLDebugger(manager, ontology, reasonerFactory);
        /* Get bottom */
        bottom = manager.getOWLDataFactory().getOWLNothing();
    }

    public void report(@Nonnull PrintWriter writer) throws OWLException {
        OWLTutorialSyntaxObjectRenderer renderer = new OWLTutorialSyntaxObjectRenderer(
                writer);
        /* Write a header */
        renderer.header();
        Set<OWLClass> unsatisfiables = new HashSet<>();
        for (OWLClass clazz : ontology.getClassesInSignature()) {
            assert clazz != null;
            /* Collect the unsatisfiable classes that aren't bottom. */
            if (!checker.isSatisfiable(clazz) && !clazz.equals(bottom)) {
                unsatisfiables.add(clazz);
            }
        }
        writer.println("<h1>Ontology Debugging Report</h1>");
        writer.println("<br>Ontology: " + ontology.getOntologyID() + "<br>");
        if (unsatisfiables.isEmpty()) {
            writer.println("<br>No Unsatisfiable Classes found<br>");
        } else {
            for (OWLClass unsatisfiable : unsatisfiables) {
                writer.println("<div class='box'>\n");
                writer.println("<h2 class='cl'>");
                unsatisfiable.accept(renderer);
                writer.println("</h2>");
                writer.println("<br>Axioms causing inconsistency:<br>");
                writer.println("<ul>");
                /*
                 * Find the set of support for the inconsistency. This will
                 * return us a collection of axioms
                 */
                Set<OWLAxiom> sos = debugger
                        .getSOSForInconsistentClass(unsatisfiable);
                /* Print the axioms. */
                for (OWLAxiom axiom : sos) {
                    writer.println("<li>");
                    axiom.accept(renderer);
                    writer.println("</li>");
                }
                writer.println("</ul>");
                writer.println("</div>\n");
            }
        }
        renderer.footer();
    }
}
