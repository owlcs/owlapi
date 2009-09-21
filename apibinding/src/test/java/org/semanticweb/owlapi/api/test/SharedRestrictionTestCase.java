package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.io.WriterOutputTarget;
import org.semanticweb.owlapi.io.StreamOutputTarget;

import java.net.URI;
import java.util.Set;
import java.io.BufferedReader;
import java.io.InputStreamReader;
/*
 * Copyright (C) 2009, University of Manchester
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
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 11-Aug-2009
 */
public class SharedRestrictionTestCase extends AbstractOWLAPITestCase {

     public void testParser() throws Exception {

        URI uri = URI.create("http://km.aifb.uni-karlsruhe.de/projects/owltests/index.php/Special:GetOntology/TestCase:WebOnt-Restriction-002?m=p");

        try {
            OWLOntology ont = getManager().loadOntologyFromPhysicalURI(uri);
            Set<OWLClass> classes = ont.getClassesInSignature();
            assertEquals("Should only be one class (anonymous restriction)", 1, classes.size());
            OWLClass c = classes.iterator().next();
            assertEquals("Restricted class should have an assertion", 1, ont.getAxioms(c).size());
        }
        catch (OWLOntologyCreationException e) {
            System.out.println("Failed to load ontology");
             e.printStackTrace();
            fail();
        }

    }
}
