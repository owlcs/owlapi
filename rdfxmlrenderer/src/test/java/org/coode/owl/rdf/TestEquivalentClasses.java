package org.coode.owl.rdf;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.io.OWLParserFactoryRegistry;
import org.semanticweb.owl.util.CollectionFactory;
import org.coode.owl.rdfxml.parser.RDFXMLParserFactory;
import org.coode.owl.rdf.rdfxml.RDFXMLOntologyStorer;
import uk.ac.manchester.cs.owl.OWLOntologyManagerImpl;
import uk.ac.manchester.cs.owl.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.EmptyInMemOWLOntologyFactory;
import uk.ac.manchester.cs.owl.ParsableOWLOntologyFactory;

import java.util.*;
import java.io.File;

import junit.framework.TestCase;
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
 * Date: 09-May-2007<br><br>
 */
public class TestEquivalentClasses extends AbstractRendererAndParserTestCase {

    protected String getDescription() {
        return "Equivalent classes axioms test case";
    }


    protected Set<OWLAxiom> getAxioms() {
        OWLClass clsA = getManager().getOWLDataFactory().getOWLClass(TestUtils.createURI());
        OWLObjectProperty prop = getManager().getOWLDataFactory().getOWLObjectProperty(TestUtils.createURI());
        OWLDescription descA = getManager().getOWLDataFactory().getOWLObjectSomeRestriction(prop,
                                                                                            getManager().getOWLDataFactory().getOWLThing());
        Set<OWLDescription> descriptions = new HashSet<OWLDescription>();
        descriptions.add(clsA);
        descriptions.add(descA);
        OWLAxiom ax = getManager().getOWLDataFactory().getOWLEquivalentClassesAxiom(descriptions);
        return Collections.singleton(ax);
    }
}
