package org.semanticweb.owl.apibinding;/*
 * Copyright (C) 2006, University of Manchester
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


import de.uulm.ecs.ai.owl.krssparser.KRSS2OWLParserFactory;
import de.uulm.ecs.ai.owl.krssrenderer.KRSS2OWLSyntaxOntologyStorer;
import org.coode.manchesterowlsyntax.ManchesterOWLSyntaxParserFactory;
import org.coode.obo.parser.OBOParserFactory;
import org.coode.obo.renderer.OBOFlatFileOntologyStorer;
import org.coode.owl.functionalparser.OWLFunctionalSyntaxParserFactory;
import org.coode.owl.functionalrenderer.OWLFunctionalSyntaxOntologyStorer;
import org.coode.owl.latex.LatexOntologyStorer;
import org.coode.owl.owlxmlparser.OWLXMLParserFactory;
import org.coode.owl.rdf.rdfxml.RDFXMLOntologyStorer;
import org.coode.owl.rdf.turtle.TurtleOntologyStorer;
import org.coode.owl.rdfxml.parser.RDFXMLParserFactory;
import org.coode.owlapi.owlxml.renderer.OWLXMLOntologyStorer;
import org.semanticweb.owl.io.OWLParserFactoryRegistry;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.semanticweb.owl.util.NonMappingOntologyDocumentMapper;
import uk.ac.manchester.cs.owl.EmptyInMemOWLOntologyFactory;
import uk.ac.manchester.cs.owl.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.OWLOntologyManagerImpl;
import uk.ac.manchester.cs.owl.ParsableOWLOntologyFactory;
import uk.ac.manchester.cs.owl.mansyntaxrenderer.ManchesterOWLSyntaxOntologyStorer;
import uk.ac.manchester.cs.owl.turtle.parser.TurtleOntologyParserFactory;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 06-Dec-2006<br><br>
 * <p/>
 * Provides a point of convenience for creating an <code>OWLOntologyManager</code>
 * with commonly required features (such as an RDF parser for example).
 */
public class OWLManager {

    static {
        // Register useful parsers
        OWLParserFactoryRegistry registry = OWLParserFactoryRegistry.getInstance();
        registry.registerParserFactory(new ManchesterOWLSyntaxParserFactory());
        registry.registerParserFactory(new KRSS2OWLParserFactory());
        registry.registerParserFactory(new OBOParserFactory());
        registry.registerParserFactory(new TurtleOntologyParserFactory());
        registry.registerParserFactory(new OWLFunctionalSyntaxParserFactory());
        registry.registerParserFactory(new OWLXMLParserFactory());
        registry.registerParserFactory(new RDFXMLParserFactory());

    }


    /**
     * Creates an OWL ontology manager that is configured with standard parsers,
     * storeres etc.
     *
     * @return The new manager.
     */
    public static OWLOntologyManager createOWLOntologyManager() {
        return createOWLOntologyManager(new OWLDataFactoryImpl());
    }


    /**
     * Creates an OWL ontology manager that is configured with standard parsers,
     * storeres etc.
     *
     * @param dataFactory The data factory that the manager should have a reference to.
     * @return The manager.
     */
    public static OWLOntologyManager createOWLOntologyManager(OWLDataFactory dataFactory) {
        // Create the ontology manager and add ontology factories, mappers and storers
        OWLOntologyManager ontologyManager = new OWLOntologyManagerImpl(dataFactory);
        ontologyManager.addOntologyStorer(new RDFXMLOntologyStorer());
        ontologyManager.addOntologyStorer(new OWLXMLOntologyStorer());
        ontologyManager.addOntologyStorer(new OWLFunctionalSyntaxOntologyStorer());
        ontologyManager.addOntologyStorer(new ManchesterOWLSyntaxOntologyStorer());
        ontologyManager.addOntologyStorer(new OBOFlatFileOntologyStorer());
        ontologyManager.addOntologyStorer(new KRSS2OWLSyntaxOntologyStorer());
        ontologyManager.addOntologyStorer(new TurtleOntologyStorer());
        ontologyManager.addOntologyStorer(new LatexOntologyStorer());

        ontologyManager.addURIMapper(new NonMappingOntologyDocumentMapper());

        ontologyManager.addOntologyFactory(new EmptyInMemOWLOntologyFactory());
        ontologyManager.addOntologyFactory(new ParsableOWLOntologyFactory());

        return ontologyManager;
    }
}
