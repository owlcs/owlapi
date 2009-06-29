package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.io.*;
import org.semanticweb.owlapi.model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Logger;
/*
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


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Nov-2006<br><br>
 * <p/>
 * An ontology factory that creates ontologies by parsing documents containing
 * concrete representations of ontologies.  This ontology factory will claim that
 * it is suitable for creating an ontology if the physical URI can be opened for
 * reading.  This factory will not create empty ontologies.  Parsers are instantiated
 * by using a list of <code>OWLParserFactory</code> objects that are obtained from
 * the <code>OWLParserFactoryRegistry</code>.
 */
public class ParsableOWLOntologyFactory extends AbstractInMemOWLOntologyFactory {

    private static final Logger logger = Logger.getLogger(ParsableOWLOntologyFactory.class.getName());

    private Set<String> parsableSchemes;

    /**
     * Creates an ontology factory.
     */
    public ParsableOWLOntologyFactory() {
        parsableSchemes = new HashSet<String>();
        parsableSchemes.add("http");
        parsableSchemes.add("https");
        parsableSchemes.add("file");
        parsableSchemes.add("ftp");
    }


    public void setOWLOntologyManager(OWLOntologyManager owlOntologyManager) {
        super.setOWLOntologyManager(owlOntologyManager);
    }


    /**
     * Gets a list of parsers that this factory uses when it tries to
     * create an ontology from a concrete representation.
     */
    public List<OWLParser> getParsers() {
        List<OWLParser> parsers = new ArrayList<OWLParser>();
        List<OWLParserFactory> factories = OWLParserFactoryRegistry.getInstance().getParserFactories();
        for (OWLParserFactory factory : factories) {
            OWLParser parser = factory.createParser(getOWLOntologyManager());
            parser.setOWLOntologyManager(getOWLOntologyManager());
            parsers.add(parser);
        }
        return new ArrayList<OWLParser>(parsers);
    }


    /**
     * Overriden - We don't create new empty ontologies - this isn't our responsibility
     */
    public boolean canCreateFromPhysicalURI(URI physicalURI) {
        return false;
    }


    /**
     * Overriden - This method will throw an OWLException which wraps an UnsupportedOperationException.
     */
    public OWLOntology createOWLOntology(URI ontologyURI, URI physicalURI) {
        throw new OWLRuntimeException(new UnsupportedOperationException("Cannot create new empty ontologes!"));
    }


    public boolean canLoad(OWLOntologyInputSource inputSource) {
        if (inputSource.isReaderAvailable()) {
            return true;
        }
        if (inputSource.isInputStreamAvailable()) {
            return true;
        }
        if (parsableSchemes.contains(inputSource.getPhysicalURI().getScheme())) {
            return true;
        }
        // If we can open an input stream then we can attempt to parse the ontology
        // TODO: Take into consideration the request type!
        try {
            InputStream is = inputSource.getPhysicalURI().toURL().openStream();
            is.close();
            return true;
        }
        catch (UnknownHostException e) {
            logger.info("Unknown host: " + e.getMessage());
        }
        catch (MalformedURLException e) {
            logger.info("Malformed URL: " + e.getMessage());
        }
        catch (FileNotFoundException e) {
            logger.info("File not found: " + e.getMessage());
        }
        catch (IOException e) {
            logger.info("IO Exception: " + e.getMessage());
        }
        return false;
    }


    public OWLOntology loadOWLOntology(OWLOntologyInputSource inputSource,
                                       final OWLOntologyCreationHandler mediator) throws OWLOntologyCreationException {
        // Attempt to parse the ontology by looping through the parsers.  If the
        // ontology is parsed successfully then we break out and return the ontology.
        // I think that this is more reliable than selecting a parser based on a file extension
        // for example (perhaps the parser list could be ordered based on most likely parser, which
        // could be determined by file extension).
        Map<OWLParser, Throwable> exceptions = new LinkedHashMap<OWLParser, Throwable>();
        // Call the super method to create the ontology - this is needed, because
        // we throw an exception if someone tries to create an ontology directly
        OWLOntology existingOntology = getOWLOntologyManager().getOntology(IRI.create(inputSource.getPhysicalURI()));
        OWLOntologyID ontologyID = new OWLOntologyID(getOWLOntologyManager().getOWLDataFactory().getIRI(inputSource.getPhysicalURI()));
        OWLOntology ont = super.createOWLOntology(ontologyID, inputSource.getPhysicalURI(), mediator);
        // Now parse the input into the empty ontology that we created
        for (final OWLParser parser : getParsers()) {
            try {
                if (existingOntology == null && !ont.isEmpty()) {
                    // Junk from a previous parse.  We should clear the ont
                    getOWLOntologyManager().removeOntology(ont);
                    ont = super.createOWLOntology(ontologyID, inputSource.getPhysicalURI(), mediator);
                }
                OWLOntologyFormat format = parser.parse(inputSource, ont);
                mediator.setOntologyFormat(ont, format);
                return ont;
            }
            catch (UnparsableOntologyException e) {
                throw e;
            }
            catch (Throwable e) {
                Throwable t = e;
                while (t != null) {
                    // For certain IO errors, no matter which parser we try, it
                    // will fail because it can't read anything.
                    if (t instanceof IOException) {
                        // Need to remove ontology that we tried to parse into
                        getOWLOntologyManager().removeOntology(ont);
                        throw new OWLOntologyCreationIOException((IOException) t);
                    }
                    Throwable tt = e.getCause();
                    if (tt == t) {
                        break;
                    }
                    t = tt;
                }
                exceptions.put(parser, e);
            }
        }
        if (existingOntology == null) {
            getOWLOntologyManager().removeOntology(ont);
        }
        // We haven't found a parser that could parse the ontology properly.  Throw an
        // exception whose message contains the stack traces from all of the parsers
        // that we have tried.
        throw new UnparsableOntologyException(inputSource.getPhysicalURI(), exceptions);
    }
}
