/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2011, Ulm University
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.krss2.renderer;

import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.DEFINE_CONCEPT;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.DEFINE_PRIMITIVE_CONCEPT;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.DEFINE_PRIMITIVE_ROLE;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.DOMAIN;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.EQUIVALENT;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.IMPLIES;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.PARENTS_ATTR;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.RANGE_ATTR;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.SYMMETRIC_ATTR;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.TRANSITIVE_ATTR;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.TRUE;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.search.EntitySearcher.isDefined;
import static org.semanticweb.owlapi.search.EntitySearcher.isSymmetric;
import static org.semanticweb.owlapi.search.EntitySearcher.isTransitive;
import static org.semanticweb.owlapi.search.Searcher.domain;
import static org.semanticweb.owlapi.search.Searcher.equivalent;
import static org.semanticweb.owlapi.search.Searcher.range;
import static org.semanticweb.owlapi.search.Searcher.sup;
import static org.semanticweb.owlapi.util.CollectionFactory.sortOptionally;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.search.Filters;

/**
 * @author Olaf Noppens
 */
public class KRSS2OWLObjectRenderer extends KRSSObjectRenderer {

    /**
     * @param ontology ontology
     * @param writer writer
     */
    public KRSS2OWLObjectRenderer(OWLOntology ontology, Writer writer) {
        super(ontology, writer);
    }

    private void writeAttribute(KRSS2Vocabulary v) {
        writeSpace();
        writer.write(":");
        writer.write(v.toString());
    }

    @Override
    public void visit(OWLOntology ontology) {
        List<OWLClass> classes1 = asList(ontology.classesInSignature());
        classes1.remove(ontology.getOWLOntologyManager().getOWLDataFactory().getOWLThing());
        classes1.remove(ontology.getOWLOntologyManager().getOWLDataFactory().getOWLNothing());
        sortOptionally(classes1);
        for (OWLClass eachClass : classes1) {
            boolean primitive = !isDefined(eachClass, ontology);
            if (primitive) {
                writeOpenBracket();
                write(DEFINE_PRIMITIVE_CONCEPT);
                write(eachClass);
                writeSpace();
                flatten(asList(sup(ontology.subClassAxiomsForSubClass(eachClass),
                    OWLClassExpression.class)));
                writeCloseBracket();
                writeln();
                Collection<OWLClassExpression> classes =
                    asList(equivalent(ontology.equivalentClassesAxioms(eachClass),
                        OWLClassExpression.class));
                for (OWLClassExpression classExpression : classes) {
                    writeOpenBracket();
                    write(eachClass);
                    write(EQUIVALENT);
                    writeSpace();
                    classExpression.accept(this);
                    writeCloseBracket();
                    writeln();
                }
            } else {
                writeOpenBracket();
                write(DEFINE_CONCEPT);
                write(eachClass);
                Collection<OWLClassExpression> classes =
                    asList(equivalent(ontology.equivalentClassesAxioms(eachClass),
                        OWLClassExpression.class));
                if (classes.isEmpty()) {
                    // ?
                    writeCloseBracket();
                    writeln();
                } else if (classes.size() == 1) {
                    write(classes.iterator().next());
                    writeCloseBracket();
                    writeln();
                } else {
                    Iterator<OWLClassExpression> iter = classes.iterator();
                    write(iter.next());
                    writeCloseBracket();
                    writeln();
                    while (iter.hasNext()) {
                        writeOpenBracket();
                        write(EQUIVALENT);
                        write(eachClass);
                        writeSpace();
                        iter.next().accept(this);
                        writeCloseBracket();
                        writeln();
                    }
                }
            }
        }
        ontology.generalClassAxioms().forEach(a -> a.accept(this));
        for (OWLObjectProperty property : asList(ontology.objectPropertiesInSignature())) {
            writeOpenBracket();
            write(DEFINE_PRIMITIVE_ROLE);
            write(property);
            if (isTransitive(property, ontology)) {
                writeAttribute(TRANSITIVE_ATTR);
                writeSpace();
                write(TRUE);
            }
            if (isSymmetric(property, ontology)) {
                writeAttribute(SYMMETRIC_ATTR);
                writeSpace();
                write(TRUE);
            }
            List<OWLClassExpression> domains =
                asList(domain(ontology.objectPropertyDomainAxioms(property)));
            if (!domains.isEmpty()) {
                writeAttribute(DOMAIN);
                flatten(domains);
            }
            List<OWLClassExpression> ranges =
                asList(range(ontology.objectPropertyRangeAxioms(property)));
            if (!ranges.isEmpty()) {
                writeAttribute(RANGE_ATTR);
                flatten(ranges);
            }
            Stream<OWLObjectPropertyExpression> superProperties = sup(
                ontology.axioms(Filters.subObjectPropertyWithSub, property, INCLUDED),
                OWLObjectPropertyExpression.class);
            Iterator<OWLObjectPropertyExpression> it = superProperties.iterator();
            if (it.hasNext()) {
                writeAttribute(PARENTS_ATTR);
                writeOpenBracket();
                while (it.hasNext()) {
                    write(it.next());
                }
                writeCloseBracket();
            }
            writeCloseBracket();
        }
        writer.flush();
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        writeOpenBracket();
        write(IMPLIES);
        write(axiom.getSubClass());
        write(axiom.getSuperClass());
        writeCloseBracket();
    }
}
