package org.semanticweb.owlapi.api.test;

import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
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
 * Date: 10-May-2008<br><br>
 */
public abstract class AbstractRoundTrippingTest extends AbstractOWLAPITestCase {

    private OWLOntology ont;

    protected abstract OWLOntology createOntology() throws Exception;

    protected OWLOntology getOnt() {
        return ont;
    }

    protected void setUp() throws Exception {
        ont = createOntology();    
    }

    public void testRDFXML() throws Exception {
        roundTripOntology(ont);
    }

    public void testOWLXML() throws Exception {
        roundTripOntology(ont, new OWLXMLOntologyFormat());
    }

    public void testFunctionalSyntax() throws Exception {
        roundTripOntology(ont, new OWLFunctionalSyntaxOntologyFormat());
    }

    public void testTurtle() throws Exception {
        roundTripOntology(ont, new TurtleOntologyFormat());
    }

    public void testManchesterOWLSyntax() throws Exception {
//        roundTripOntology(ont, new ManchesterOWLSyntaxOntologyFormat());
    }

    protected void handleSaved(StringDocumentTarget target, OWLOntologyFormat format) {
        super.handleSaved(target, format);
    }

}
