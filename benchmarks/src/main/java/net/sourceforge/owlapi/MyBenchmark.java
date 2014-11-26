/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package net.sourceforge.owlapi;

import org.openjdk.jmh.annotations.Benchmark;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.io.RDFParserMetaData;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyImpl;

import java.util.Set;

public class MyBenchmark {

    @Benchmark
    public void testLoadTaxonFSS() throws OWLOntologyCreationException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntologyDocumentSource ds = new StreamDocumentSource(getClass().getResourceAsStream("/ncbitaxon.rdf.ofn") );
        OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration().setStrict(false);

        OWLOntologyImpl ontology = (OWLOntologyImpl) manager.loadOntologyFromOntologyDocument(ds, config);
        OWLDocumentFormat ontologyFormat = manager.getOntologyFormat(ontology);
        OWLOntologyLoaderMetaData ontologyLoaderMetaData = ontologyFormat.getOntologyLoaderMetaData();
        if (ontologyLoaderMetaData instanceof RDFParserMetaData) {
            RDFParserMetaData rdfParserMetaData = (RDFParserMetaData) ontologyLoaderMetaData;
            Set<RDFTriple> unparsedTriples = rdfParserMetaData.getUnparsedTriples();
            if (unparsedTriples != null) {
                System.err.printf("There were %,d unparsed triples", unparsedTriples.size());
            }
        }
        ontology.trimToSize();
        manager.removeOntology(ontology);
    }

}
