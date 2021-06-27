/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.api.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Collection;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.dlsyntax.parser.DLSyntaxOWLParserFactory;
import org.semanticweb.owlapi.dlsyntax.renderer.DLSyntaxStorerFactory;
import org.semanticweb.owlapi.formats.DLSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.KRSS2DocumentFormat;
import org.semanticweb.owlapi.formats.KRSSDocumentFormat;
import org.semanticweb.owlapi.formats.LatexDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.functional.parser.OWLFunctionalSyntaxOWLParserFactory;
import org.semanticweb.owlapi.functional.renderer.FunctionalSyntaxStorerFactory;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.OWLParserFactory;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.krss1.parser.KRSSOWLParserFactory;
import org.semanticweb.owlapi.krss2.parser.KRSS2OWLParserFactory;
import org.semanticweb.owlapi.krss2.renderer.KRSS2OWLSyntaxStorerFactory;
import org.semanticweb.owlapi.krss2.renderer.KRSSSyntaxStorerFactory;
import org.semanticweb.owlapi.latex.renderer.LatexStorerFactory;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxOntologyParserFactory;
import org.semanticweb.owlapi.manchestersyntax.renderer.ManchesterSyntaxStorerFactory;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLStorerFactory;
import org.semanticweb.owlapi.model.parameters.AxiomAnnotations;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.owlxml.parser.OWLXMLParserFactory;
import org.semanticweb.owlapi.owlxml.renderer.OWLXMLStorerFactory;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParserFactory;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.RDFXMLStorerFactory;
import org.semanticweb.owlapi.rdf.turtle.parser.TurtleOntologyParserFactory;
import org.semanticweb.owlapi.rdf.turtle.renderer.TurtleStorerFactory;

class ParsersStorersTestCase extends TestBase {

    static Collection<OWLAxiom> getData() {
        return new Builder().all();
    }

    OWLOntology ont(OWLAxiom object) {
        OWLOntology o = getAnonymousOWLOntology();
        o.add(object);
        return o;
    }

    void test(OWLAxiom object, OWLStorerFactory s, OWLParserFactory p,
        OWLDocumentFormat ontologyFormat, boolean expectParse, boolean expectRoundtrip,
        boolean logfailures, boolean annotationsSupported) throws Exception {
        OWLOntology ont = ont(object);
        if (!annotationsSupported) {
            if (!object.getAxiomType().isLogical()) {
                return;
            }
            object = object.getAxiomWithoutAnnotations();
            ont = ont(object);
        }
        StringDocumentTarget target = new StringDocumentTarget();
        s.createStorer().storeOntology(ont, target, ontologyFormat);
        OWLOntology o = getAnonymousOWLOntology();
        try {
            p.createParser().parse(new StringDocumentSource(target), o,
                new OWLOntologyLoaderConfiguration());
        } catch (OWLParserException e) {
            if (logfailures) {
                System.out.println("parse fail: " + ontologyFormat.getKey() + " " + object);
                System.out.println(target);
            }
            if (expectParse) {
                throw e;
            }
            return;
        }
        boolean condition = o.containsAxiom(object)
            || o.containsAxiom(object, Imports.EXCLUDED, AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS)
            || object instanceof OWLObjectPropertyAssertionAxiom
                && o.containsAxiom(((OWLObjectPropertyAssertionAxiom) object).getSimplified());
        if (!condition) {
            if (expectRoundtrip) {
                // check bnodes
                String axiom = object.toString().replace("_:id", "");
                for (OWLAxiom ax : asUnorderedSet(o.axioms())) {
                    if (!condition) {
                        String a = ax.toString().replaceAll("_:genid[0-9]+", "");
                        condition = axiom.equals(a);
                    }
                }
                if (!condition) {
                    System.out.println(target.toString());
                    System.out.println(ontologyFormat + " " + axiom);
                    for (OWLAxiom ax : asUnorderedSet(o.axioms())) {
                        String a = ax.toString().replaceAll("_:genid[0-9]+", "");
                        System.out.println(ontologyFormat + " parsed " + a);
                    }
                }
                assertTrue(condition, object.toString() + "\t" + o);
            } else {
                if (logfailures) {
                    System.out.println("roundtrip fail: " + ontologyFormat.getKey() + " " + object);
                    System.out.println(target.toString());
                    o.axioms().forEach(System.out::println);
                    System.out.println("end of roundtrip failure");
                }
            }
        }
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testManchesterSyntax(OWLAxiom object) throws Exception {
        boolean logicalAxiom = object.isLogicalAxiom();
        test(object, new ManchesterSyntaxStorerFactory(),
            new ManchesterOWLSyntaxOntologyParserFactory(), new ManchesterSyntaxDocumentFormat(),
            logicalAxiom, logicalAxiom, true, true);
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testKRSS2(OWLAxiom object) throws Exception {
        // XXX at some point roundtripping should be supported
        test(object, new KRSS2OWLSyntaxStorerFactory(), new KRSS2OWLParserFactory(),
            new KRSS2DocumentFormat(), false, false, false, false);
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testKRSS(OWLAxiom object) throws Exception {
        // XXX at some point roundtripping should be supported
        test(object, new KRSSSyntaxStorerFactory(), new KRSSOWLParserFactory(),
            new KRSSDocumentFormat(), false, false, false, false);
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testTurtle(OWLAxiom object) throws Exception {
        test(object, new TurtleStorerFactory(), new TurtleOntologyParserFactory(),
            new TurtleDocumentFormat(), true, true, true, true);
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testFSS(OWLAxiom object) throws Exception {
        test(object, new FunctionalSyntaxStorerFactory(), new OWLFunctionalSyntaxOWLParserFactory(),
            new FunctionalSyntaxDocumentFormat(), true, true, true, true);
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testOWLXML(OWLAxiom object) throws Exception {
        test(object, new OWLXMLStorerFactory(), new OWLXMLParserFactory(),
            new OWLXMLDocumentFormat(), true, true, true, true);
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testRDFXML(OWLAxiom object) throws Exception {
        test(object, new RDFXMLStorerFactory(), new RDFXMLParserFactory(),
            new RDFXMLDocumentFormat(), true, true, true, true);
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testDLSyntax(OWLAxiom object) throws Exception {
        // XXX at some point roundtripping should be supported
        test(object, new DLSyntaxStorerFactory(), new DLSyntaxOWLParserFactory(),
            new DLSyntaxDocumentFormat(), false, false, true, false);
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testLatex(OWLAxiom object) throws Exception {
        LatexDocumentFormat ontologyFormat = new LatexDocumentFormat();
        LatexStorerFactory storer = new LatexStorerFactory();
        StringDocumentTarget target = new StringDocumentTarget();
        storer.createStorer().storeOntology(ont(object), target, ontologyFormat);
    }
}
