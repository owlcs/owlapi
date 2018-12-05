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
package org.semanticweb.owlapi.profiles;

import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.HasOperands;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.profiles.violations.CycleInDatatypeDefinition;
import org.semanticweb.owlapi.profiles.violations.DatatypeIRIAlsoUsedAsClassIRI;
import org.semanticweb.owlapi.profiles.violations.EmptyOneOfAxiom;
import org.semanticweb.owlapi.profiles.violations.IllegalPunning;
import org.semanticweb.owlapi.profiles.violations.InsufficientIndividuals;
import org.semanticweb.owlapi.profiles.violations.InsufficientOperands;
import org.semanticweb.owlapi.profiles.violations.InsufficientPropertyExpressions;
import org.semanticweb.owlapi.profiles.violations.LastPropertyInChainNotInImposedRange;
import org.semanticweb.owlapi.profiles.violations.LexicalNotInLexicalSpace;
import org.semanticweb.owlapi.profiles.violations.OntologyIRINotAbsolute;
import org.semanticweb.owlapi.profiles.violations.OntologyVersionIRINotAbsolute;
import org.semanticweb.owlapi.profiles.violations.UseOfAnonymousIndividual;
import org.semanticweb.owlapi.profiles.violations.UseOfBuiltInDatatypeInDatatypeDefinition;
import org.semanticweb.owlapi.profiles.violations.UseOfDataOneOfWithMultipleLiterals;
import org.semanticweb.owlapi.profiles.violations.UseOfDefinedDatatypeInDatatypeRestriction;
import org.semanticweb.owlapi.profiles.violations.UseOfDefinedDatatypeInLiteral;
import org.semanticweb.owlapi.profiles.violations.UseOfIllegalAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfIllegalClassExpression;
import org.semanticweb.owlapi.profiles.violations.UseOfIllegalDataRange;
import org.semanticweb.owlapi.profiles.violations.UseOfIllegalFacetRestriction;
import org.semanticweb.owlapi.profiles.violations.UseOfNonAbsoluteIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfNonAtomicClassExpression;
import org.semanticweb.owlapi.profiles.violations.UseOfNonEquivalentClassExpression;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInCardinalityRestriction;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInDisjointPropertiesAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInFunctionalPropertyAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInIrreflexivePropertyAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInObjectHasSelf;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSubClassExpression;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSuperClassExpression;
import org.semanticweb.owlapi.profiles.violations.UseOfObjectOneOfWithMultipleIndividuals;
import org.semanticweb.owlapi.profiles.violations.UseOfObjectPropertyInverse;
import org.semanticweb.owlapi.profiles.violations.UseOfPropertyInChainCausesCycle;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForAnnotationPropertyIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForClassIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForDataPropertyIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForIndividualIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForObjectPropertyIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForOntologyIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForVersionIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfUndeclaredAnnotationProperty;
import org.semanticweb.owlapi.profiles.violations.UseOfUndeclaredClass;
import org.semanticweb.owlapi.profiles.violations.UseOfUndeclaredDataProperty;
import org.semanticweb.owlapi.profiles.violations.UseOfUndeclaredDatatype;
import org.semanticweb.owlapi.profiles.violations.UseOfUndeclaredObjectProperty;
import org.semanticweb.owlapi.profiles.violations.UseOfUnknownDatatype;
import org.semanticweb.owlapi.util.OWLObjectPropertyManager;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * Base class for profile violation visitors.
 * 
 * Separates the visiting code from the utility methods used in the visit.
 * 
 * @author ignazio
 *
 */
public class ProfileVisitorBase extends OWLOntologyWalkerVisitor {
    private static final OWLClassExpressionVisitorEx<Boolean> superClassExpressionChecker =
        new OWLClassExpressionVisitorEx<Boolean>() {
            @Override
            public <T> Boolean doDefault(T object) {
                return Boolean.FALSE;
            }

            @Override
            public Boolean visit(OWLClass ce) {
                return Boolean.TRUE;
            }

            @Override
            public Boolean visit(OWLDataSomeValuesFrom ce) {
                return Boolean.TRUE;
            }

            @Override
            public Boolean visit(OWLObjectComplementOf ce) {
                return Boolean.valueOf(isOWL2QLSubClassExpression(ce.getOperand()));
            }

            @Override
            public Boolean visit(OWLObjectIntersectionOf ce) {
                return Boolean
                    .valueOf(ce.operands().noneMatch(e -> e.accept(this) == Boolean.FALSE));
            }

            @Override
            public Boolean visit(OWLObjectSomeValuesFrom ce) {
                return Boolean.valueOf(!ce.getFiller().isAnonymous());
            }
        };

    private static final OWLClassExpressionVisitorEx<Boolean> subClassExpressionChecker =
        new OWLClassExpressionVisitorEx<Boolean>() {

            @Override
            public <T> Boolean doDefault(T o) {
                return Boolean.FALSE;
            }

            @Override
            public Boolean visit(OWLClass ce) {
                return Boolean.TRUE;
            }

            @Override
            public Boolean visit(OWLDataSomeValuesFrom ce) {
                return Boolean.TRUE;
            }

            @Override
            public Boolean visit(OWLObjectSomeValuesFrom ce) {
                return Boolean.valueOf(ce.getFiller().isOWLThing());
            }
        };
    static final OWLClassExpressionVisitorEx<Boolean> subClassRLExpressionChecker =
        new OWLClassExpressionVisitorEx<Boolean>() {

            @Override
            public <T> Boolean doDefault(T o) {
                return Boolean.FALSE;
            }

            @Override
            public Boolean visit(OWLClass ce) {
                return Boolean.valueOf(!ce.isOWLThing());
            }

            @Override
            public Boolean visit(OWLDataHasValue ce) {
                return Boolean.TRUE;
            }

            @Override
            public Boolean visit(OWLDataSomeValuesFrom ce) {
                return Boolean.TRUE;
            }

            @Override
            public Boolean visit(OWLObjectHasValue ce) {
                return Boolean.TRUE;
            }

            @Override
            public Boolean visit(OWLObjectIntersectionOf ce) {
                return Boolean
                    .valueOf(ce.operands().allMatch(op -> isOWL2RLSubClassExpression(op)));
            }

            @Override
            public Boolean visit(OWLObjectOneOf ce) {
                return Boolean.TRUE;
            }

            @Override
            public Boolean visit(OWLObjectSomeValuesFrom ce) {
                return Boolean.valueOf(
                    ce.getFiller().isOWLThing() || isOWL2RLSubClassExpression(ce.getFiller()));
            }

            @Override
            public Boolean visit(OWLObjectUnionOf ce) {
                return Boolean
                    .valueOf(ce.operands().allMatch(op -> isOWL2RLSubClassExpression(op)));
            }
        };

    static final OWLClassExpressionVisitorEx<Boolean> superClassRLExpressionChecker =
        new OWLClassExpressionVisitorEx<Boolean>() {

            @Override
            public <T> Boolean doDefault(T o) {
                return Boolean.FALSE;
            }

            @Override
            public Boolean visit(OWLClass ce) {
                return Boolean.valueOf(!ce.isOWLThing());
            }

            @Override
            public Boolean visit(OWLDataAllValuesFrom ce) {
                return Boolean.TRUE;
            }

            @Override
            public Boolean visit(OWLDataHasValue ce) {
                return Boolean.TRUE;
            }

            @Override
            public Boolean visit(OWLDataMaxCardinality ce) {
                return Boolean.valueOf(ce.getCardinality() == 0 || ce.getCardinality() == 1);
            }

            @Override
            public Boolean visit(OWLObjectAllValuesFrom ce) {
                return ce.getFiller().accept(this);
            }

            // XXX difference in subclass and superclass - correct?
            @Override
            public Boolean visit(OWLObjectComplementOf ce) {
                return Boolean.valueOf(isOWL2RLSubClassExpression(ce.getOperand()));
            }

            @Override
            public Boolean visit(OWLObjectHasValue ce) {
                return Boolean.TRUE;
            }

            @Override
            public Boolean visit(OWLObjectIntersectionOf ce) {
                return Boolean.valueOf(ce.operands().allMatch(e -> e.accept(this).booleanValue()));
            }

            @Override
            public Boolean visit(OWLObjectMaxCardinality ce) {
                return Boolean.valueOf((ce.getCardinality() == 0 || ce.getCardinality() == 1)
                    && (ce.getFiller().isOWLThing() || isOWL2RLSubClassExpression(ce.getFiller())));
            }
        };

    static final OWLClassExpressionVisitorEx<Boolean> equivalentClassExpressionChecker =
        new OWLClassExpressionVisitorEx<Boolean>() {

            @Override
            public <T> Boolean doDefault(T o) {
                return Boolean.FALSE;
            }

            @Override
            public Boolean visit(OWLClass ce) {
                return Boolean.valueOf(!ce.isOWLThing());
            }

            @Override
            public Boolean visit(OWLDataHasValue ce) {
                return Boolean.TRUE;
            }

            @Override
            public Boolean visit(OWLObjectHasValue ce) {
                return Boolean.TRUE;
            }

            @Override
            public Boolean visit(OWLObjectIntersectionOf ce) {
                return Boolean.valueOf(ce.operands().allMatch(e -> e.accept(this).booleanValue()));
            }
        };

    protected static boolean isOWL2QLSubClassExpression(OWLClassExpression ce) {
        return ce.accept(subClassExpressionChecker).booleanValue();
    }

    protected static boolean isOWL2QLSuperClassExpression(OWLClassExpression ce) {
        return ce.accept(superClassExpressionChecker).booleanValue();
    }

    protected static boolean isOWL2RLSubClassExpression(OWLClassExpression ce) {
        return ce.accept(subClassRLExpressionChecker).booleanValue();
    }

    protected static boolean isOWL2RLSuperClassExpression(OWLClassExpression ce) {
        return ce.accept(superClassRLExpressionChecker).booleanValue();
    }

    protected static final Set<IRI> ALLOWED_RL_DATATYPES =
        asUnorderedSet(OWL2Datatype.RL_DATATYPES.stream().map(HasIRI::getIRI));
    protected static final Set<IRI> ALLOWED_EL_DATATYPES =
        asUnorderedSet(OWL2Datatype.EL_DATATYPES.stream().map(HasIRI::getIRI));

    protected static final Set<IRI> ALLOWED_QL_DATATYPES =
        asUnorderedSet(OWL2Datatype.EL_DATATYPES.stream().map(HasIRI::getIRI));

    protected final Set<Profiles> validating;
    protected Collection<OWLProfileViolation> violations;
    @Nullable
    protected OWLObjectPropertyManager propertyManager = null;

    /**
     * @param walker onotlogy walker to use
     * @param violations collection of violations; the collection is modified during the visit
     * @param profiles the profiles to check. An empty collection means OWL 2 FULL will be the
     *        pofile used.
     */
    protected ProfileVisitorBase(OWLOntologyWalker walker,
        Collection<OWLProfileViolation> violations, Collection<Profiles> profiles) {
        super(walker);
        this.violations = violations;
        validating = new HashSet<>(profiles);
    }

    protected void dl(Runnable... runnables) {
        if (validating.contains(Profiles.OWL2_DL)) {
            for (Runnable r : runnables) {
                r.run();
            }
        }
    }

    protected void ql(Runnable... runnables) {
        if (validating.contains(Profiles.OWL2_QL)) {
            for (Runnable r : runnables) {
                r.run();
            }
        }
    }

    protected void el(Runnable... runnables) {
        if (validating.contains(Profiles.OWL2_EL)) {
            for (Runnable r : runnables) {
                r.run();
            }
        }
    }

    protected void rl(Runnable... runnables) {
        if (validating.contains(Profiles.OWL2_RL)) {
            for (Runnable r : runnables) {
                r.run();
            }
        }
    }

    protected OWLObjectPropertyManager getPropertyManager() {
        if (propertyManager == null) {
            propertyManager = new OWLObjectPropertyManager(getCurrentOntology());
        }
        return verifyNotNull(propertyManager);
    }

    protected void illegalRLDatatype(OWLDatatype node) {
        if (!ALLOWED_RL_DATATYPES.contains(node.getIRI())) {
            illegalDataRange(node);
        }
    }

    protected void illegalQLDatatype(OWLDatatype node) {
        if (!ALLOWED_QL_DATATYPES.contains(node.getIRI())) {
            illegalDataRange(node);
        }
    }

    protected void nonSuperClassRL(OWLClassExpression ce) {
        if (!isOWL2RLSuperClassExpression(ce)) {
            nonSuper(ce);
        }
    }

    protected void nonSubClassRL(OWLClassExpression ce) {
        if (!isOWL2RLSubClassExpression(ce)) {
            nonSubclass(ce);
        }
    }

    protected void nonSuperClassQL(OWLClassExpression ce) {
        if (!isOWL2QLSuperClassExpression(ce)) {
            nonSuper(ce);
        }
    }

    protected void nonSubClassQL(OWLSubClassOfAxiom axiom) {
        if (!isOWL2QLSubClassExpression(axiom.getSubClass())) {
            nonSubclass(axiom.getSubClass());
        }
    }

    protected void elDataRange(OWLDatatype node) {
        if (!ALLOWED_EL_DATATYPES.contains(node.getIRI())) {
            illegalDataRange(node);
        }
    }

    protected void illegalClass(OWLClassExpression ce) {
        violations
            .add(new UseOfIllegalClassExpression(getCurrentOntology(), getCurrentAxiom(), ce));
    }

    protected void illegalPunning(HasIRI p) {
        violations.add(new IllegalPunning(getCurrentOntology(), getCurrentAxiom(), p.getIRI()));
    }

    protected void insufficientOperands(HasOperands<?> node) {
        if (node.getOperandsAsList().size() < 2) {
            violations.add(new InsufficientOperands(getCurrentOntology(), getCurrentAxiom(),
                (OWLObject) node));
        }
    }

    protected void insufficientProperties(HasOperands<?> node) {
        if (node.getOperandsAsList().size() < 2) {
            violations
                .add(new InsufficientPropertyExpressions(getCurrentOntology(), getCurrentAxiom()));
        }
    }

    protected void emptyProperties(HasOperands<?> node) {
        if (node.getOperandsAsList().isEmpty()) {
            violations
                .add(new InsufficientPropertyExpressions(getCurrentOntology(), getCurrentAxiom()));
        }
    }

    protected void illegalAxiom() {
        violations
            .add(new UseOfIllegalAxiom(getCurrentOntology(), verifyNotNull(getCurrentAxiom())));
    }

    protected void illegalDataRange(OWLDataRange node) {
        violations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
    }

    protected void nonSimple(OWLObjectCardinalityRestriction ce) {
        if (getPropertyManager().isNonSimple(ce.getProperty())) {
            violations.add(new UseOfNonSimplePropertyInCardinalityRestriction(getCurrentOntology(),
                getCurrentAxiom(), ce));
        }
    }

    protected boolean nonSubclass(OWLClassExpression ce) {
        return violations.add(new UseOfNonSubClassExpression(getCurrentOntology(),
            verifyNotNull(getCurrentAxiom()), ce));
    }

    protected void nonSuper(OWLClassExpression ce) {
        violations.add(new UseOfNonSuperClassExpression(getCurrentOntology(),
            verifyNotNull(getCurrentAxiom()), ce));
    }

    protected void chainCycle(OWLObjectPropertyExpression last) {
        violations.add(new UseOfPropertyInChainCausesCycle(getCurrentOntology(),
            verifyNotNull(getCurrentAxiom()), last));
    }

    protected void undeclaredDatatype(OWLDatatype dt) {
        if (!dt.isTopDatatype() && !dt.isBuiltIn()
            && !getCurrentOntology().isDeclared(dt, Imports.INCLUDED)) {
            violations
                .add(new UseOfUndeclaredDatatype(getCurrentOntology(), getCurrentAxiom(), dt));
        }
    }

    protected boolean rangePresent(OWLClassExpression imposedRange,
        OWLObjectPropertyExpression lastProperty) {
        return getCurrentOntology().importsClosure()
            .flatMap(o -> o.objectPropertyRangeAxioms(lastProperty))
            .anyMatch(l -> l.getRange().equals(imposedRange));
    }

    protected void anonIndividual(OWLAnonymousIndividual individual) {
        violations
            .add(new UseOfAnonymousIndividual(getCurrentOntology(), getCurrentAxiom(), individual));
    }

    protected void nonAtomic(OWLClassAssertionAxiom axiom) {
        if (axiom.getClassExpression().isAnonymous()) {
            violations.add(new UseOfNonAtomicClassExpression(getCurrentOntology(), axiom,
                axiom.getClassExpression()));
        }
    }

    protected boolean nonEquivalentClass(OWLClassExpression ce) {
        return violations.add(new UseOfNonEquivalentClassExpression(getCurrentOntology(),
            verifyNotNull(getCurrentAxiom()), ce));
    }

    protected void ontologyIRINotAbsolute(OWLOntologyID id) {
        id.getOntologyIRI().filter(o -> !o.isAbsolute())
            .ifPresent(x -> violations.add(new OntologyIRINotAbsolute(getCurrentOntology(), id)));
    }

    protected void versionNotAbsolute(OWLOntologyID id) {
        id.getVersionIRI().filter(v -> !v.isAbsolute()).ifPresent(
            x -> violations.add(new OntologyVersionIRINotAbsolute(getCurrentOntology(), id)));
    }

    protected boolean definedDatatype(OWLDatatypeRestriction node) {
        return violations.add(new UseOfDefinedDatatypeInDatatypeRestriction(getCurrentOntology(),
            getCurrentAxiom(), node));
    }

    protected void illegalFacet(OWLDatatypeRestriction node, OWLDatatype datatype,
        OWLFacetRestriction r) {
        OWL2Datatype dt = datatype.getBuiltInDatatype();
        if (!dt.getFacets().contains(r.getFacet())) {
            violations.add(new UseOfIllegalFacetRestriction(getCurrentOntology(), getCurrentAxiom(),
                node, r.getFacet()));
        }
    }

    protected boolean isBuiltin(OWLLiteral node) {
        return node.getDatatype().isBuiltIn()
            && !node.getDatatype().getBuiltInDatatype().isInLexicalSpace(node.getLiteral());
    }

    protected void chainRange(OWLObjectPropertyRangeAxiom rngAx) {
        OWLSubPropertyChainOfAxiom axiom =
            (OWLSubPropertyChainOfAxiom) verifyNotNull(getCurrentAxiom());
        if (getPropertyManager().isSubPropertyOf(axiom.getSuperProperty(), rngAx.getProperty())) {
            // Imposed range restriction!
            OWLClassExpression imposedRange = rngAx.getRange();
            // There must be an axiom that imposes a
            // range on the last
            // prop in the chain
            List<OWLObjectPropertyExpression> chain = axiom.getPropertyChain();
            if (!chain.isEmpty()) {
                OWLObjectPropertyExpression lastProperty = chain.get(chain.size() - 1);
                boolean rngPresent = rangePresent(imposedRange, lastProperty);
                if (!rngPresent) {
                    violations.add(new LastPropertyInChainNotInImposedRange(getCurrentOntology(),
                        axiom, rngAx));
                }
            }
        }
    }

    protected void relativeIRI(IRI iri) {
        if (!iri.isAbsolute()) {
            violations.add(new UseOfNonAbsoluteIRI(getCurrentOntology(), getCurrentAxiom(), iri));
        }
    }

    protected void notInLexicalSpace(OWLLiteral node) {
        if (!node.getDatatype().isBuiltIn()) {
            violations.add(
                new UseOfDefinedDatatypeInLiteral(getCurrentOntology(), getCurrentAxiom(), node));
        }
        // Check that the lexical value of the literal is in the lexical
        // space of the literal datatype
        if (isBuiltin(node)) {
            violations
                .add(new LexicalNotInLexicalSpace(getCurrentOntology(), getCurrentAxiom(), node));
        }
    }

    protected void multipleOneOf(OWLObjectOneOf ce) {
        if (ce.getOperandsAsList().size() != 1) {
            violations.add(new UseOfObjectOneOfWithMultipleIndividuals(getCurrentOntology(),
                getCurrentAxiom(), ce));
        }
    }

    protected void multipleOneOf(OWLDataOneOf node) {
        if (node.getOperandsAsList().size() != 1) {
            violations.add(new UseOfDataOneOfWithMultipleLiterals(getCurrentOntology(),
                getCurrentAxiom(), node));
        }
    }

    protected void insufficientProperties(OWLSubPropertyChainOfAxiom axiom) {
        if (axiom.getPropertyChain().size() < 2) {
            violations.add(new InsufficientPropertyExpressions(getCurrentOntology(), axiom));
        }
    }

    protected void topAsSubProperty(OWLSubDataPropertyOfAxiom axiom) {
        if (axiom.getSubProperty().isOWLTopDataProperty()) {
            violations.add(new UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom(
                getCurrentOntology(), axiom));
        }
    }

    protected void inverse(OWLObjectInverseOf property) {
        violations
            .add(new UseOfObjectPropertyInverse(getCurrentOntology(), getCurrentAxiom(), property));
    }

    protected void insufficientIndividuals(HasOperands<? extends OWLIndividual> axiom) {
        if (axiom.getOperandsAsList().size() < 2) {
            violations.add(new InsufficientIndividuals(getCurrentOntology(), getCurrentAxiom()));
        }
    }

    protected void punningAnnotation(OWLAnnotationProperty property) {
        if (getCurrentOntology().containsObjectPropertyInSignature(property.getIRI(), INCLUDED)) {
            illegalPunning(property);
        }
        if (getCurrentOntology().containsDataPropertyInSignature(property.getIRI(), INCLUDED)) {
            illegalPunning(property);
        }
    }

    protected void undeclaredAnnotation(OWLAnnotationProperty property) {
        if (!property.isBuiltIn() && !property.getIRI().isReservedVocabulary()
            && !getCurrentOntology().isDeclared(property, INCLUDED)) {
            violations.add(new UseOfUndeclaredAnnotationProperty(getCurrentOntology(),
                getCurrentAxiom(), getCurrentAnnotation(), property));
        }
    }

    protected void reservedForAnnotation(OWLAnnotationProperty property) {
        if (!property.isBuiltIn() && property.getIRI().isReservedVocabulary()) {
            violations.add(new UseOfReservedVocabularyForAnnotationPropertyIRI(getCurrentOntology(),
                getCurrentAxiom(), property));
        }
    }

    protected void punningDatatypeAndClass(HasIRI ce) {
        if (getCurrentOntology().containsClassInSignature(ce.getIRI(), Imports.INCLUDED)
            && getCurrentOntology().containsDatatypeInSignature(ce.getIRI(), Imports.INCLUDED)) {
            violations.add(new DatatypeIRIAlsoUsedAsClassIRI(getCurrentOntology(),
                getCurrentAxiom(), ce.getIRI()));
        }
    }

    protected void undeclaredClass(OWLClass ce) {
        if (!ce.isBuiltIn() && !ce.getIRI().isReservedVocabulary()
            && !getCurrentOntology().isDeclared(ce, INCLUDED)) {
            violations.add(new UseOfUndeclaredClass(getCurrentOntology(), getCurrentAxiom(), ce));
        }
    }

    protected void reservedForClass(OWLClass ce) {
        if (!ce.isBuiltIn() && ce.getIRI().isReservedVocabulary()) {
            violations.add(new UseOfReservedVocabularyForClassIRI(getCurrentOntology(),
                getCurrentAxiom(), ce));
        }
    }

    protected void emptyOneOf(HasOperands<?> node) {
        if (node.getOperandsAsList().size() < 1) {
            violations.add(new EmptyOneOfAxiom(getCurrentOntology(), getCurrentAxiom()));
        }
    }

    protected void illegalDataPropertyPunning(OWLDataProperty property) {
        if (getCurrentOntology().containsObjectPropertyInSignature(property.getIRI(), INCLUDED)) {
            illegalPunning(property);
        }
        if (getCurrentOntology().containsAnnotationPropertyInSignature(property.getIRI(),
            INCLUDED)) {
            illegalPunning(property);
        }
    }

    protected void undeclaredDataProperty(OWLDataProperty property) {
        if (!property.isBuiltIn() && !property.getIRI().isReservedVocabulary()
            && !getCurrentOntology().isDeclared(property, INCLUDED)) {
            violations.add(
                new UseOfUndeclaredDataProperty(getCurrentOntology(), getCurrentAxiom(), property));
        }
    }

    protected void reservedForDataProperty(OWLDataProperty property) {
        if (!property.isOWLTopDataProperty() && !property.isOWLBottomDataProperty()
            && property.getIRI().isReservedVocabulary()) {
            violations.add(new UseOfReservedVocabularyForDataPropertyIRI(getCurrentOntology(),
                getCurrentAxiom(), property));
        }
    }

    protected void unknownDatatype(OWLDatatype node) {
        if (!Namespaces.XSD.inNamespace(node.getIRI()) && !node.isBuiltIn() && !node.isTopDatatype()
            && node.getIRI().isReservedVocabulary()) {
            violations.add(new UseOfUnknownDatatype(getCurrentOntology(), getCurrentAxiom(), node));
        }
    }

    protected void reservedForDatatype(OWLDatatypeDefinitionAxiom axiom) {
        if (axiom.getDatatype().getIRI().isReservedVocabulary()) {
            violations
                .add(new UseOfBuiltInDatatypeInDatatypeDefinition(getCurrentOntology(), axiom));
        }
    }

    protected void cycleInDefinition(OWLDatatypeDefinitionAxiom axiom) {
        // Check for cycles
        Set<OWLDatatype> datatypes = new HashSet<>();
        Set<OWLAxiom> axioms = new LinkedHashSet<>();
        axioms.add(axiom);
        getDatatypesInSignature(datatypes, axiom.getDataRange(), axioms);
        if (datatypes.contains(axiom.getDatatype())) {
            violations.add(new CycleInDatatypeDefinition(getCurrentOntology(), axiom));
        }
    }

    private void getDatatypesInSignature(Set<OWLDatatype> datatypes, OWLObject obj,
        Set<OWLAxiom> axioms) {
        Consumer<? super OWLDatatypeDefinitionAxiom> addAndRecurse = ax -> {
            axioms.add(ax);
            getDatatypesInSignature(datatypes, ax.getDataRange(), axioms);
        };
        obj.datatypesInSignature().filter(datatypes::add)
            .forEach(dt -> datatypeDefinitions(dt).forEach(addAndRecurse));
    }

    protected Stream<OWLDatatypeDefinitionAxiom> datatypeDefinitions(OWLDatatype dt) {
        return Imports.INCLUDED.stream(getCurrentOntology())
            .flatMap(o -> o.datatypeDefinitions(dt));
    }

    protected boolean disjointNonSimple(OWLObjectPropertyExpression p) {
        return violations.add(new UseOfNonSimplePropertyInDisjointPropertiesAxiom(
            getCurrentOntology(), verifyNotNull(getCurrentAxiom()), p));
    }

    protected void functionalNonSimple(OWLFunctionalObjectPropertyAxiom axiom) {
        if (getPropertyManager().isNonSimple(axiom.getProperty())) {
            violations.add(
                new UseOfNonSimplePropertyInFunctionalPropertyAxiom(getCurrentOntology(), axiom));
        }
    }

    protected void inverseFunctionalNonSimple(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        if (getPropertyManager().isNonSimple(axiom.getProperty())) {
            violations.add(new UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom(
                getCurrentOntology(), axiom));
        }
    }

    protected void irreflexiveNonSimple(OWLIrreflexiveObjectPropertyAxiom axiom) {
        if (getPropertyManager().isNonSimple(axiom.getProperty())) {
            violations.add(
                new UseOfNonSimplePropertyInIrreflexivePropertyAxiom(getCurrentOntology(), axiom));
        }
    }

    protected void reservedForIndividual(OWLNamedIndividual individual) {
        if (individual.isNamed() && individual.getIRI().isReservedVocabulary()) {
            violations.add(new UseOfReservedVocabularyForIndividualIRI(getCurrentOntology(),
                getCurrentAxiom(), individual));
        }
    }

    protected void hasSelfNonSimple(OWLObjectHasSelf ce) {
        if (getPropertyManager().isNonSimple(ce.getProperty())) {
            violations.add(new UseOfNonSimplePropertyInObjectHasSelf(getCurrentOntology(),
                getCurrentAxiom(), ce));
        }
    }

    protected void illegalObjectPropertyPunning(OWLObjectProperty property) {
        if (getCurrentOntology().containsDataPropertyInSignature(property.getIRI(), INCLUDED)) {
            illegalPunning(property);
        }
        if (getCurrentOntology().containsAnnotationPropertyInSignature(property.getIRI(),
            INCLUDED)) {
            illegalPunning(property);
        }
    }

    protected void undeclaredObjectProperty(OWLObjectProperty property) {
        if (!property.isBuiltIn() && !property.getIRI().isReservedVocabulary()
            && !getCurrentOntology().isDeclared(property, INCLUDED)) {
            violations.add(new UseOfUndeclaredObjectProperty(getCurrentOntology(),
                getCurrentAxiom(), property));
        }
    }

    protected void reservedForVersionIRI(OWLOntologyID id) {
        if (id.isAnonymous()) {
            return;
        }
        Optional<IRI> vIRI = id.getVersionIRI();
        if (vIRI.isPresent() && vIRI.get().isReservedVocabulary()) {
            violations.add(new UseOfReservedVocabularyForVersionIRI(getCurrentOntology()));
        }
    }

    protected void reservedForOntologyIRI(OWLOntologyID id) {
        if (id.isAnonymous()) {
            return;
        }
        Optional<IRI> oIRI = id.getOntologyIRI();
        if (oIRI.isPresent() && oIRI.get().isReservedVocabulary()) {
            violations.add(new UseOfReservedVocabularyForOntologyIRI(getCurrentOntology()));
        }
    }

    protected void reservedForObjectProperty(OWLObjectProperty property) {
        if (!property.isOWLTopObjectProperty() && !property.isOWLBottomObjectProperty()
            && property.getIRI().isReservedVocabulary()) {
            violations.add(new UseOfReservedVocabularyForObjectPropertyIRI(getCurrentOntology(),
                getCurrentAxiom(), property));
        }
    }

    protected void chainCycle(OWLSubPropertyChainOfAxiom axiom) {
        OWLObjectPropertyExpression superProp = axiom.getSuperProperty();
        if (superProp.isOWLTopObjectProperty() || axiom.isEncodingOfTransitiveProperty()) {
            // TOP or TRANSITIVE_PROP: no violation can occur
            return;
        }
        List<OWLObjectPropertyExpression> chain = axiom.getPropertyChain();
        OWLObjectPropertyExpression first = chain.get(0);
        OWLObjectPropertyExpression last = chain.get(chain.size() - 1);
        checkCenter(superProp, chain);
        checkExtremes(superProp, first, last);
        checkExtremes(superProp, last, first);
    }

    protected void asymmetricNonSimple(OWLAsymmetricObjectPropertyAxiom axiom) {
        if (getPropertyManager().isNonSimple(axiom.getProperty())) {
            violations.add(new UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom(
                getCurrentOntology(), axiom));
        }
    }

    protected void checkCenter(OWLObjectPropertyExpression superProp,
        List<OWLObjectPropertyExpression> chain) {
        // center part of the chain must be smaller in any case
        for (int i = 1; i < chain.size() - 1; i++) {
            if (getPropertyManager().isLessThan(superProp, chain.get(i))) {
                chainCycle(chain.get(i));
            }
        }
    }

    protected void checkExtremes(OWLObjectPropertyExpression superProp,
        OWLObjectPropertyExpression first, OWLObjectPropertyExpression last) {
        if (first.equals(superProp)) {
            // first equals, last must be smaller
            if (getPropertyManager().isLessThan(superProp, last)) {
                chainCycle(last);
            }
        } else {
            // first not equal, it must be smaller
            if (getPropertyManager().isLessThan(superProp, first)) {
                chainCycle(first);
            }
        }
    }
}
