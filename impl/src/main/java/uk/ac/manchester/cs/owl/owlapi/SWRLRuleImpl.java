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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkIterableNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.equalStreams;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nullable;

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
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class SWRLRuleImpl extends OWLLogicalAxiomImpl implements SWRLRule {

    private final LinkedHashSet<SWRLAtom> head;
    private final LinkedHashSet<SWRLAtom> body;
    private final boolean containsAnonymousClassExpressions;
    protected static final AtomSimplifier ATOM_SIMPLIFIER = new AtomSimplifier();

    /**
     * @param body rule body
     * @param head rule head
     * @param annotations annotations on the axiom
     */
    public SWRLRuleImpl(Collection<? extends SWRLAtom> body, Collection<? extends SWRLAtom> head,
        Collection<OWLAnnotation> annotations) {
        super(annotations);
        checkIterableNotNull(body, "body cannot be null or contain nulls", true);
        checkIterableNotNull(head, "head cannot be null or contain nulls", true);
        this.head = new LinkedHashSet<>(head);
        this.body = new LinkedHashSet<>(body);
        containsAnonymousClassExpressions = hasAnon();
    }

    /**
     * @param body rule body
     * @param head rule head
     */
    public SWRLRuleImpl(Collection<? extends SWRLAtom> body, Collection<? extends SWRLAtom> head) {
        this(body, head, NO_ANNOTATIONS);
    }

    @Override
    @SuppressWarnings("unchecked")
    public SWRLRule getAxiomWithoutAnnotations() {
        return !isAnnotated() ? this : new SWRLRuleImpl(body, head, NO_ANNOTATIONS);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends OWLAxiom> T getAnnotatedAxiom(Stream<OWLAnnotation> anns) {
        return (T) new SWRLRuleImpl(body, head, mergeAnnos(anns));
    }

    @Override
    public Stream<SWRLVariable> variables() {
        return accept(new SWRLVariableExtractor()).stream();
    }

    private boolean hasAnon() {
        return classAtomPredicates().anyMatch(OWLClassExpression::isAnonymous);
    }

    @Override
    public boolean containsAnonymousClassExpressions() {
        return containsAnonymousClassExpressions;
    }

    @Override
    public Stream<OWLClassExpression> classAtomPredicates() {
        return Stream.concat(head.stream(), body.stream()).filter(c -> c instanceof SWRLClassAtom)
            .map(c -> ((SWRLClassAtom) c).getPredicate()).distinct().sorted();
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
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof SWRLRule)) {
            return false;
        }
        if (obj instanceof SWRLRuleImpl) {
            return body.equals(((SWRLRuleImpl) obj).body) && head.equals(((SWRLRuleImpl) obj).head)
                && equalStreams(annotations(), ((SWRLRuleImpl) obj).annotations());
        }
        SWRLRule other = (SWRLRule) obj;
        return body.equals(asSet(other.body())) && head.equals(asSet(other.head()))
            && equalStreams(annotations(), other.annotations());
    }

    protected static class AtomSimplifier implements SWRLObjectVisitorEx<SWRLObject> {

        @Override
        public SWRLObject doDefault(OWLObject o) {
            return (SWRLObject) o;
        }

        @Override
        public SWRLRule visit(SWRLRule node) {
            List<SWRLAtom> nodebody = asList(node.body().map(a -> (SWRLAtom) a.accept(this)));
            List<SWRLAtom> nodehead = asList(node.head().map(a -> (SWRLAtom) a.accept(this)));
            return new SWRLRuleImpl(nodebody, nodehead, NO_ANNOTATIONS);
        }

        @Override
        public SWRLObjectPropertyAtom visit(SWRLObjectPropertyAtom node) {
            return node.getSimplified();
        }
    }
}
