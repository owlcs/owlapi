package uk.ac.manchester.cs.owlapi.dlsyntax;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.AbstractOWLOntologyStorer;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Set;
import java.util.TreeSet;
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
public abstract class DLSyntaxOntologyStorerBase extends AbstractOWLOntologyStorer {

    private OWLOntology ont;

    protected void storeOntology(OWLOntologyManager manager, OWLOntology ontology, Writer w, OWLOntologyFormat format) throws
            OWLOntologyStorageException {

        ont = ontology;
        PrintWriter writer = new PrintWriter(w);
        beginWritingOntology(ontology, writer);
        for (OWLObjectProperty prop : new TreeSet<OWLObjectProperty>(ontology.getObjectPropertiesInSignature())) {
            write(prop, ontology.getAxioms(prop), writer);
        }
        for (OWLDataProperty prop : new TreeSet<OWLDataProperty>(ontology.getDataPropertiesInSignature())) {
            write(prop, ontology.getAxioms(prop), writer);
        }
        for (OWLClass cls : new TreeSet<OWLClass>(ontology.getClassesInSignature())) {
            write(cls, ontology.getAxioms(cls), writer);
        }
        for (OWLNamedIndividual ind : new TreeSet<OWLNamedIndividual>(ontology.getIndividualsInSignature())) {
            write(ind, ontology.getAxioms(ind), writer);
        }
        beginWritingGeneralAxioms(ontology.getGeneralClassAxioms(), writer);
        for (OWLAxiom ax : ontology.getGeneralClassAxioms()) {
            beginWritingAxiom(ax, writer);
            writeAxiom(null, ax, writer);
            endWritingAxiom(ax, writer);
        }
        endWritingGeneralAxioms(ontology.getGeneralClassAxioms(), writer);
        endWritingOntology(ontology, writer);
        writer.flush();
    }

    private void write(OWLEntity entity, Set<? extends OWLAxiom> axioms, PrintWriter writer) {
        beginWritingAxioms(entity, axioms, writer);
        for (OWLAxiom ax : new TreeSet<OWLAxiom>(axioms)) {
            beginWritingAxiom(ax, writer);
            writeAxiom(entity, ax, writer);
            endWritingAxiom(ax, writer);
        }
        Set<OWLAxiom> usages = new TreeSet<OWLAxiom>(ont.getReferencingAxioms(entity));
        usages.removeAll(axioms);
        beginWritingUsage(entity, usages, writer);
        for (OWLAxiom usage : usages) {
            if (!axioms.contains(usage)) {
                beginWritingAxiom(usage, writer);
                writeAxiom(entity, usage, writer);
                endWritingAxiom(usage, writer);
            }
        }
        endWritingUsage(entity, usages, writer);
        endWritingAxioms(entity, axioms, writer);
    }

    protected void writeAxiom(OWLEntity subject, OWLAxiom axiom, PrintWriter writer) {
        writer.write(getRendering(subject, axiom));
    }

    protected String getRendering(OWLEntity subject, OWLAxiom axiom) {
        DLSyntaxObjectRenderer ren = new DLSyntaxObjectRenderer();
        return ren.render(axiom);
    }

    protected void beginWritingOntology(OWLOntology ontology, PrintWriter writer) {

    }

    protected void endWritingOntology(OWLOntology ontology, PrintWriter writer) {

    }

    protected void beginWritingAxiom(OWLAxiom axiom, PrintWriter writer) {

    }

    protected void endWritingAxiom(OWLAxiom axiom, PrintWriter writer) {

    }

    protected void beginWritingAxioms(OWLEntity subject, Set<? extends OWLAxiom> axioms, PrintWriter writer) {

    }

    protected void endWritingAxioms(OWLEntity subject, Set<? extends OWLAxiom> axioms, PrintWriter writer) {

    }

    protected void beginWritingUsage(OWLEntity subject, Set<? extends OWLAxiom> axioms, PrintWriter writer) {

    }


    protected void endWritingUsage(OWLEntity subject, Set<? extends OWLAxiom> axioms, PrintWriter writer) {

    }

    protected void beginWritingGeneralAxioms(Set<? extends OWLAxiom> axioms, PrintWriter writer) {

    }

    protected void endWritingGeneralAxioms(Set<? extends OWLAxiom> axioms, PrintWriter writer) {

    }
}
