package org.semanticweb.owlapi.apibinding;/*
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


import de.uulm.ecs.ai.owlapi.krssrenderer.KRSS2OWLSyntaxOntologyStorer;
import de.uulm.ecs.ai.owlapi.krssparser.KRSS2OWLParserFactory;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxParserFactory;
import org.coode.owlapi.obo.parser.OBOParserFactory;
import org.coode.owlapi.obo.renderer.OBOFlatFileOntologyStorer;
import org.coode.owlapi.functionalparser.OWLFunctionalSyntaxParserFactory;
import org.coode.owlapi.functionalrenderer.OWLFunctionalSyntaxOntologyStorer;
import org.coode.owlapi.latex.LatexOntologyStorer;
import org.coode.owlapi.owlxmlparser.OWLXMLParserFactory;
import org.coode.owlapi.rdf.rdfxml.RDFXMLOntologyStorer;
import org.coode.owlapi.turtle.TurtleOntologyStorer;
import org.coode.owlapi.rdfxml.parser.RDFXMLParserFactory;
import org.coode.owlapi.owlxml.renderer.OWLXMLOntologyStorer;
import org.semanticweb.owlapi.io.OWLParserFactoryRegistry;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.NonMappingOntologyIRIMapper;
import uk.ac.manchester.cs.owl.owlapi.EmptyInMemOWLOntologyFactory;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;
import uk.ac.manchester.cs.owl.owlapi.ParsableOWLOntologyFactory;
import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxOntologyStorer;
import uk.ac.manchester.cs.owl.owlapi.turtle.parser.TurtleOntologyParserFactory;

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
        return createOWLOntologyManager(getOWLDataFactory());
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

        ontologyManager.addIRIMapper(new NonMappingOntologyIRIMapper());

        ontologyManager.addOntologyFactory(new EmptyInMemOWLOntologyFactory());
        ontologyManager.addOntologyFactory(new ParsableOWLOntologyFactory());

        return ontologyManager;
    }

    /**
     * Gets a global data factory that can be used to create OWL API objects.
     * @return An OWLDataFactory  that can be used for creating OWL API objects.
     */
    public static OWLDataFactory getOWLDataFactory() {
        return OWLDataFactoryImpl.getInstance();
    }
}
