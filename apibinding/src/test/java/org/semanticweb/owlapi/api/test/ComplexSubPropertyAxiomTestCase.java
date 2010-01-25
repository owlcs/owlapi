package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;/*
 * Copyright (C) 2008, University of Manchester
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
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 02-Feb-2009
 */
public class ComplexSubPropertyAxiomTestCase extends AbstractFileRoundTrippingTestCase {

    public void testContains() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLObjectProperty propP = getOWLObjectProperty("p");
        OWLObjectProperty propQ = getOWLObjectProperty("q");
        OWLObjectProperty propR = getOWLObjectProperty("r");
        List<OWLObjectProperty> chain = new ArrayList<OWLObjectProperty>();
        chain.add(propP);
        chain.add(propQ);
        axioms.add(getFactory().getOWLSubPropertyChainOfAxiom(chain, propR));
        assertEquals(getOnt().getAxioms(), axioms);
    }

    protected void handleSaved(StringDocumentTarget target, OWLOntologyFormat format) {
        System.out.println(target);
    }

    protected String getFileName() {
        return "ComplexSubProperty.rdf";
    }
}
