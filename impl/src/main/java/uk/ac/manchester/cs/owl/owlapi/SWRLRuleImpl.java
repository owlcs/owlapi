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
package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.*;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLObject;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.util.SWRLVariableExtractor;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class SWRLRuleImpl extends OWLLogicalAxiomImpl implements SWRLRule {

    private static final long serialVersionUID = 40000L;
    private final LinkedHashSet<SWRLAtom> head;
    private final LinkedHashSet<SWRLAtom> body;
    private final boolean containsAnonymousClassExpressions;

    /**
     * @param body
     *        rule body
     * @param head
     *        rule head
     * @param annotations
     *        annotations on the axiom
     */
    public SWRLRuleImpl(@Nonnull Set<? extends SWRLAtom> body,
            @Nonnull Set<? extends SWRLAtom> head,
            @Nonnull Collection<OWLAnnotation> annotations) {
        super(annotations);
        this.head = new LinkedHashSet<>(checkNotNull(head,
                "head cannot be null"));
        this.body = new LinkedHashSet<>(checkNotNull(body,
                "body cannot be null"));
        containsAnonymousClassExpressions = hasAnon();
    }

    @Override
    public SWRLRule getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return new SWRLRuleImpl(body, head, NO_ANNOTATIONS);
    }

    @Override
    public OWLAxiom getAnnotatedAxiom(@Nonnull Stream<OWLAnnotation> anns) {
        return new SWRLRuleImpl(body, head, mergeAnnos(anns));
    }

    /**
     * @param body
     *        rule body
     * @param head
     *        rule head
     */
    public SWRLRuleImpl(@Nonnull Set<? extends SWRLAtom> body,
            @Nonnull Set<? extends SWRLAtom> head) {
        this(body, head, NO_ANNOTATIONS);
    }

    @Nonnull
    @Override
    public Stream<SWRLVariable> variables() {
        return accept(new SWRLVariableExtractor()).stream();
    }

    private boolean hasAnon() {
        return classAtomPredicates().anyMatch(p -> p.isAnonymous());
    }

    @Override
    public boolean containsAnonymousClassExpressions() {
        return containsAnonymousClassExpressions;
    }

    @Override
    public Stream<OWLClassExpression> classAtomPredicates() {
        return Stream.concat(head.stream(), body.stream())
                .filter(c -> c instanceof SWRLClassAtom)
                .map(c -> ((SWRLClassAtom) c).getPredicate());
    }

    @Override
    public Stream<SWRLAtom> body() {
        return body.stream();
    }

    @Override
    public Stream<SWRLAtom> head() {
        return head.stream();
    }

    @Override
    public SWRLRule getSimplified() {
        return (SWRLRule) accept(ATOM_SIMPLIFIER);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof SWRLRule)) {
            return false;
        }
        SWRLRule other = (SWRLRule) obj;
        return equalStreams(body(), other.body())
                && equalStreams(head(), other.head());
    }

    @Override
    public AxiomType<?> getAxiomType() {
        return AxiomType.SWRL_RULE;
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        SWRLRule other = (SWRLRule) object;
        int diff = compareStreams(body(), other.body());
        if (diff == 0) {
            diff = compareStreams(head(), other.head());
        }
        return diff;
    }

    @Nonnull
    protected static final AtomSimplifier ATOM_SIMPLIFIER = new AtomSimplifier();

    protected static class AtomSimplifier implements
            SWRLObjectVisitorEx<SWRLObject> {

        @Override
        public SWRLObject doDefault(Object o) {
            return (SWRLObject) o;
        }

        @Override
        public SWRLRule visit(SWRLRule node) {
            Set<SWRLAtom> nodebody = asSet(node.body().map(
                    a -> (SWRLAtom) a.accept(this)));
            Set<SWRLAtom> nodehead = asSet(node.head().map(
                    a -> (SWRLAtom) a.accept(this)));
            return new SWRLRuleImpl(nodebody, nodehead, NO_ANNOTATIONS);
        }

        @Override
        public SWRLObjectPropertyAtom visit(SWRLObjectPropertyAtom node) {
            return node.getSimplified();
        }
    }
}
