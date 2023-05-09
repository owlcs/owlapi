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
package org.semanticweb.owlapi.utility;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.HasAnnotations;
import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.utilities.PrefixManagerImpl;
import org.semanticweb.owlapi.utilities.ShortFormProvider;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * A simple renderer that can be used for debugging purposes and provide an implementation of the
 * toString method for different implementations.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class SimpleRendererDebug implements OWLObjectVisitor, OWLObjectRenderer {

    private StringBuilder builder = new StringBuilder();
    private PrefixManager shortFormProvider = new PrefixManagerImpl();

    /**
     * reset the renderer.
     */
    public void reset() {
        builder = new StringBuilder();
    }

    /**
     * Resets the short form provider and adds prefix name to prefix mappings based on the specified
     * ontology's format (if it is a prefix format) and possibly the ontologies in the imports
     * closure.
     *
     * @param ontology The ontology whose format will be used to obtain prefix mappings
     * @param processImportedOntologies Specifies whether or not the prefix mapping should be
     *        obtained from imported ontologies.
     */
    public void setPrefixesFromOntologyFormat(OWLOntology ontology,
        boolean processImportedOntologies) {
        shortFormProvider = new PrefixManagerImpl();
        Imports.fromBoolean(processImportedOntologies).stream(ontology)
            .forEach(o -> shortFormProvider.copyPrefixesFrom(o.getPrefixManager()));
    }

    /**
     * Sets a prefix name for a given prefix. Note that prefix names MUST end with a colon.
     *
     * @param prefixName The prefix name (ending with a colon)
     * @param prefix The prefix that the prefix name maps to
     */
    public void setPrefix(String prefixName, String prefix) {
        shortFormProvider.withPrefix(prefixName, prefix);
    }

    @Override
    public void setPrefixManager(PrefixManager shortFormProvider) {
        this.shortFormProvider = shortFormProvider;
    }

    @Override
    public void setShortFormProvider(ShortFormProvider shortFormProvider) {
        this.shortFormProvider.setShortFormProvider(shortFormProvider);
    }

    @Override
    public String render(OWLObject object) {
        reset();
        accept(object);
        return builder.toString();
    }

    protected SimpleRendererDebug accept(OWLObject object) {
        object.accept(this);
        return this;
    }

    protected SimpleRendererDebug render(Collection<? extends OWLObject> objects) {
        Iterator<? extends OWLObject> it = objects.iterator();
        while (it.hasNext()) {
            accept(it.next());
            if (it.hasNext()) {
                insertSpace();
            }
        }
        return this;
    }

    private SimpleRendererDebug insertSpace() {
        builder.append(' ');
        return this;
    }

    private SimpleRendererDebug writeAnnotations(HasAnnotations axiom) {
        render(axiom.annotationsAsList());
        if (!axiom.annotationsAsList().isEmpty()) {
            insertSpace();
        }
        return this;
    }

    @Override
    public String toString() {
        return builder.toString();
    }

    @Override
    public void doDefault(OWLObject object) {
        name(object).left();
        if (object instanceof HasAnnotations a) {
            writeAnnotations(a);
        }
        iterate(object.componentStream());
        right();
    }

    protected SimpleRendererDebug name(OWLObject object) {
        builder.append(System.identityHashCode(object)).append(object.type().getName());
        return this;
    }

    @SuppressWarnings("unchecked")
    private SimpleRendererDebug iterate(Stream<?> componentStream) {
        Iterator<?> it = componentStream.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof Collection) {
                render((Collection<? extends OWLObject>) o);
            } else if (o instanceof OWLObject obj) {
                accept(obj);
            } else {
                builder.append(System.identityHashCode(o)).append(o);
            }
            if (it.hasNext()) {
                insertSpace();
            }
        }
        return this;
    }

    @Override
    public void visit(OWLOntology ontology) {
        builder.append(ontology.type().getName()).append('(').append(ontology.getOntologyID())
            .append(" [Axioms: ").append(ontology.getAxiomCount()).append("] [Logical axioms: ")
            .append(ontology.getLogicalAxiomCount()).append("])");
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        name(axiom).left().writeAnnotations(axiom).name(axiom.getEntity()).left()
            .accept(axiom.getEntity()).right().right();
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        name(axiom).left().writeAnnotations(axiom);
        builder.append("ObjectPropertyChain");
        left().render(axiom.getPropertyChain()).right().insertSpace()
            .accept(axiom.getSuperProperty()).right();
    }

    @Override
    public void visit(OWLClass ce) {
        shortIRI(ce);
    }

    @Override
    public void visit(OWLDatatype node) {
        shortIRI(node);
    }

    protected SimpleRendererDebug right() {
        builder.append(')');
        return this;
    }

    protected SimpleRendererDebug left() {
        builder.append('(');
        return this;
    }

    @Override
    public void visit(OWLLiteral node) {
        String literal = EscapeUtils.escapeString(node.getLiteral());
        if (node.isRDFPlainLiteral()
            || node.getDatatype().getIRI().equals(OWL2Datatype.RDF_LANG_STRING.getIRI())) {
            // We can use a syntactic shortcut
            builder.append('"').append(literal).append('"');
            if (node.hasLang()) {
                builder.append('@').append(node.getLang());
            }
        } else {
            builder.append('"').append(literal).append("\"^^");
            node.getDatatype().accept(this);
        }
    }

    @Override
    public void visit(OWLObjectProperty property) {
        shortIRI(property);
    }

    @Override
    public void visit(OWLDataProperty property) {
        shortIRI(property);
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        shortIRI(individual);
    }

    protected SimpleRendererDebug shortIRI(HasIRI i) {
        builder.append(System.identityHashCode(i))
            .append(shortFormProvider.getShortForm(i.getIRI()));
        return this;
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        name(axiom).left().writeAnnotations(axiom).accept(axiom.getClassExpression()).insertSpace()
            .left().render(axiom.objectPropertyExpressionsAsList()).right().insertSpace().left()
            .render(axiom.dataPropertyExpressionsAsList()).right().right();
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        shortIRI(property);
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        builder.append(System.identityHashCode(individual)).append(individual.getID());
    }

    @Override
    public void visit(IRI iri) {
        builder.append(System.identityHashCode(iri)).append('<').append(iri).append('>');
    }

    @Override
    public void visit(SWRLRule rule) {
        name(rule).left().writeAnnotations(rule);
        builder.append("Body");
        left().render(rule.bodyList()).right().insertSpace();
        builder.append("Head");
        left().render(rule.headList()).right().right();
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        name(node).left().accept(node.getFirstArgument()).insertSpace()
            .accept(node.getSecondArgument()).right();
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        name(node).left().accept(node.getFirstArgument()).insertSpace()
            .accept(node.getSecondArgument()).right();
    }

    @Override
    public void visit(SWRLVariable node) {
        name(node).left().shortIRI(node).right();
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        accept(node.getIndividual());
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        node.getLiteral().accept(this);
    }
}
