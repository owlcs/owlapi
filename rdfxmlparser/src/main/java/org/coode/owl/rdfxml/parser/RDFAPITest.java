package org.coode.owl.rdfxml.parser;

import edu.unika.aifb.rdf.api.syntax.RDFConsumer;
import edu.unika.aifb.rdf.api.syntax.RDFParser;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
 * Date: 07-Dec-2006<br><br>
 */
public class RDFAPITest implements RDFConsumer {

    private int count = 0;

//    private Map<String, RDFResourceNode> nodeMap;

    private Set<AbstractTriple> triples;

    private Map<URI, Set<AbstractTriple>> triplesBySubject;

    private Map<URI, Set<AbstractTriple>> triplesByPredicate;

    private Map<String, URI> uriCache;

    public static void main(String[] args) {
        try {
            RDFAPITest con = new RDFAPITest();
            RDFParser parser = new RDFParser();


            URI uri = URI.create("http://www.co-ode.org/ontologies/pizza/2006/07/18/pizza.owl");
            File file = new File("/Users/matthewhorridge/Desktop/Thesaurus.owl");
            uri = file.toURI();
            BufferedInputStream inputStream = new BufferedInputStream(uri.toURL().openStream());


            long t0 = System.currentTimeMillis();
            InputSource is = new InputSource(inputStream);
            is.setSystemId(uri.toString());
            parser.parse(is, con);
            long t1 = System.currentTimeMillis();
            System.gc();
                        System.gc();
                        System.gc();
                        System.gc();
                        System.out.println("Mem consumed: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024)));

            System.out.println("Time to load: " + (t1 - t0));
            System.out.println("Number of triples: " + con.count);
        }
        catch (SAXException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public RDFAPITest() {
//        nodeMap = new HashMap<String, RDFResourceNode>();
        triples = new HashSet<AbstractTriple>();
        uriCache = new HashMap<String, URI>();
        triplesBySubject = new HashMap<URI, Set<AbstractTriple>>();
        triplesByPredicate = new HashMap<URI, Set<AbstractTriple>>();
    }


    public void addModelAttribte(String string, String string1) throws SAXException {
    }


    public void endModel() throws SAXException {
        uriCache.clear();
        Set<AbstractTriple> trips = getTriplesByPredicate(OWLRDFVocabulary.RDF_TYPE.getURI());

    }


    public void includeModel(String string, String string1) throws SAXException {
    }


    public void logicalURI(String string) throws SAXException {
    }


    public void startModel(String string) throws SAXException {
    }


    public void statementWithLiteralValue(String subject, String predicate, String val, String datatype,
                                          String lang) throws SAXException {
        count++;
        LiteralTriple t = new LiteralTriple(getURI(subject), getURI(predicate), null, lang, null);
        triples.add(t);
        indexTriple(t);
    }

    private URI getURI(String s) {
        URI uri = uriCache.get(s);
        if(uri == null) {
            uri = URI.create(s);
            uriCache.put(s, uri);
        }
        return uri;
    }

    private void index(AbstractTriple triple, Map<URI, Set<AbstractTriple>> map, URI key) {
        Set<AbstractTriple> triples = map.get(key);
        if(triples == null) {
            triples = new HashSet<AbstractTriple>();
            map.put(key, triples);
        }
        triples.add(triple);

    }

    private Set<AbstractTriple> getTriplesByPredicate(URI predicate) {
        long t0 = System.currentTimeMillis();
        Set<AbstractTriple> ts0 = triplesByPredicate.get(predicate);
        long t1 = System.currentTimeMillis();
        System.out.println("Time to get indexed: " + (t1 - t0));
        long t2 = System.currentTimeMillis();
        Set<AbstractTriple> ts1 = new HashSet<AbstractTriple>();
        for(AbstractTriple trip : triples) {
            if(trip.predicate.equals(predicate)) {
                ts1.add(trip);
            }
        }
        long t3 = System.currentTimeMillis();
        System.out.println("Time to get via search: " + (t3 - t2));
        return ts0;
    }


    private void indexTriple(AbstractTriple t) {
        index(t, triplesBySubject, t.subject);
        index(t, triplesByPredicate, t.predicate);
    }

    public void statementWithResourceValue(String subject, String predicate, String object) throws SAXException {
        count++;

        Triple t = new Triple(getURI(subject), getURI(predicate), getURI(object));
        triples.add(t);
        indexTriple(t);
    }

    private class AbstractTriple {

        URI subject;

        URI predicate;

    }

    private class LiteralTriple extends AbstractTriple {

        String literal;

        URI datatype;

        String lang;


        public LiteralTriple(URI subject, URI predicate, String literal, String lang, URI datatype) {
            this.subject = subject;
            this.predicate = predicate;
            this.literal = literal;
            this.lang = lang;
            this.datatype = datatype;
        }
    }


    private class Triple extends AbstractTriple {

        URI object;


        public Triple(URI subject, URI predicate, URI object) {
            this.subject = subject;
            this.predicate = predicate;
            this.object = object;
        }
    }

}
