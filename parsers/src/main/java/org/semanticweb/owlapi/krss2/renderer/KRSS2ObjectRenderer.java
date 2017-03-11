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

import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.COMPOSE;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.DEFINE_CONCEPT;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.DEFINE_INDIVIDUAL;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.DEFINE_PRIMITIVE_CONCEPT;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.DEFINE_PRIMITIVE_ROLE;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.DEFINE_ROLE;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.DISJOINT_ROLES;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.DOMAIN_ATTR;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.EQUIVALENT;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.IMPLIES;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.IMPLIES_ROLE;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.INV;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.INVERSE;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.INVERSE_ATTR;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.LEFTIDENTITY_ATTR;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.ONE_OF;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.PARENTS_ATTR;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.PARENT_ATTR;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.RANGE_ATTR;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.REFLEXIVE_ATTR;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.RIGHTIDENTITY_ATTR;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.ROLES_EQUIVALENT;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.ROLE_INCLUSTION;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.SYMMETRIC_ATTR;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.TRANSITIVE_ATTR;
import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.TRUE;
import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.search.EntitySearcher.isDefined;
import static org.semanticweb.owlapi.search.EntitySearcher.isReflexive;
import static org.semanticweb.owlapi.search.EntitySearcher.isSymmetric;
import static org.semanticweb.owlapi.search.EntitySearcher.isTransitive;
import static org.semanticweb.owlapi.search.Searcher.domain;
import static org.semanticweb.owlapi.search.Searcher.equivalent;
import static org.semanticweb.owlapi.search.Searcher.inverse;
import static org.semanticweb.owlapi.search.Searcher.range;
import static org.semanticweb.owlapi.search.Searcher.sup;
import static org.semanticweb.owlapi.util.CollectionFactory.sortOptionally;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.pairs;

import java.io.Writer;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.search.Filters;

/**
 * {@code KRSS2ObjectRenderer} is an extension of {@link KRSSObjectRenderer KRSSObjectRenderer}
 * which uses the extended vocabulary. <br>
 * <b>Abbreviations</b>
 * <table summary="Abbreviations">
 * <tr>
 * <td>CN</td>
 * <td>concept name</td>
 * </tr>
 * <tr>
 * <td>C,D,E</td>
 * <td>concept expression</td>
 * </tr>
 * <tr>
 * <td>RN</td>
 * <td>role name</td>
 * </tr>
 * <tr>
 * <td>R, R1, R2,...</td>
 * <td>role expressions, i.e. role name or inverse role</td>
 * </tr>
 * </table>
 * <br>
 * <b>KRSS concept language</b>
 * <table summary="KRSS concept language">
 * <tr>
 * <td>KRSS</td>
 * <td>OWLClassExpression</td>
 * </tr>
 * <tr>
 * <td>(at-least n R C)</td>
 * <td>(OWLObjectMinCardinality R n C)</td>
 * </tr>
 * <tr>
 * <td>(at-most n R C)</td>
 * <td>(OWLObjectMaxCardinality R n C)</td>
 * </tr>
 * <tr>
 * <td>(exactly n R C)</td>
 * <td>(OWLObjectExactCardinality R n C)</td>
 * </tr>
 * <tr>
 * <td>(some R C)</td>
 * <td>(OWLObjectSomeValuesFrom R C)</td>
 * </tr>
 * <tr>
 * <td>(all R C)</td>
 * <td>(OWLObjectAllValuesFrom R C)</td>
 * </tr>
 * <tr>
 * <td>(not C)</td>
 * <td>(OWLObjectComplementOf C)</td>
 * </tr>
 * <tr>
 * <td>(and C D E)</td>
 * <td>(OWLObjectIntersectionOf C D E)</td>
 * </tr>
 * <tr>
 * <td>(or C D E)</td>
 * <td>(OWLObjectUnionOf C D E)</td>
 * </tr>
 * </table>
 * <br>
 * <b>KRSS role language</b>
 * <table summary="KRSS role language">
 * <tr>
 * <td>KRSS</td>
 * <td>OWLObjectPropertyExpression</td>
 * </tr>
 * <tr>
 * <td>(inv R)</td>
 * <td>(OWLInverseObjectPropertiesAxiom R)</td>
 * </tr>
 * </table>
 * <br>
 * Each referenced class, object property as well as individual is defined using
 * <i>define-concept</i> resp. <i>define-primitive-concept</i>, <i>define-role</i> and
 * <i>define-individual</i>. In addition, axioms are translated as follows. <br>
 * <table summary="remarks">
 * <tr>
 * <td>KRSS2</td>
 * <td>OWLAxiom</td>
 * <td>KRSS syntax</td>
 * <td>Remarks</td>
 * </tr>
 * <tr>
 * <td>OWLEquivalentClasses</td>
 * <td>(define-concept C D)</td>
 * <td><i>OWLEquivalentClasses C D1 D2...Dn</i> will be translated to:<br>
 * (define-concept C (and D1 D2...Dn)) <br>
 * Only applicable if there is no OWLSubClassOf axiom. <br>
 * </td>
 * </tr>
 * <tr>
 * <td>OWLDisjointClassesAxiom</td>
 * <td>(disjoint C D)</td>
 * <td><i>OWLDisjointClasses C D1 D2 ... Dn</i> { (disjoint i(j) i(j+k)) | 1 &lt;= j &lt;=n,
 * j&lt;k&lt;=n, j=|=k} <br>
 * </td>
 * </tr>
 * <tr>
 * <td>OWLSubClassOfAxiom</td>
 * <td>(define-primitive-concept C D)</td>
 * <td><i>OWLSubClassOfAxiom C D1...Dn</i> (n &gt; 1) will be translated to:<br>
 * (define-primitive-concept C (and D1...Dn)) <br>
 * Only applicable if there is no OWLEquivalentClasses axiom. In that case the class will be
 * introduced via (define-concept...) and all subclass axioms are handled via implies</td>
 * </tr>
 * <tr>
 * <td>OWLSubClassOfAxiom</td>
 * <td>(implies D C)</td>
 * <td>Only in case of GCIs with concept expression (not named class) D, or in case that D is a
 * non-primitive concept. Otherwise superclasses are introduced via (define-primitive-concept D
 * ...)</td>
 * </tr>
 * <tr>
 * <td>OWLEquivalentObjectPropertiesAxiom</td>
 * <td>(roles-equivalent r s)</td>
 * <td>All roles are explicitly introduced via define-primitive-role.</td>
 * </tr>
 * <tr>
 * <td>OWLSubPropertyChainOfAxiom</td>
 * <td>(role-inclusion (compose r s) t)</td>
 * <td>Role inclusions of the kind (role-inclusion (compose r s) r) resp. (role-inclusion (compose s
 * r) r) are handled within the (define-primitive-role) statement as right- resp. left-identities
 * iff it is the only role-inclusion wrt. the super property.</td>
 * </tr>
 * <tr>
 * <td>OWLSubObjectPropertyAxiom</td>
 * <td>(define-primitive-role R :parent S)<br>
 * (define-primitive-role R :parents S T U)<br>
 * Additional attributes:
 * <ul>
 * <li>:transitive t
 * <li>:symmetric t
 * <li>:reflexive t
 * <li>:inverse I
 * <li>:domain C resp. :domain (and C C1...Cn)
 * <li>:range D resp. :range (and D D1..Dn)
 * </ul>
 * </td>
 * <td>This will be only used if there is no OWLEquivalentClasses axiom containing R (see
 * define-role). The additional attributes are added if there is an OWLTransitiveObjectProperyAxiom,
 * OWLSymmetricObjectPropertyAxiom, OWLReflexiveObjectPropertyAxiom, OWLObjectPropertyDomainAxiom,
 * OWLObjectPropertyRangeAxiom resp. OWLInverseObjectPropertiesAxiom. If there are multiple
 * OWLInverseObjectPropertiesAxioms only one inverse is handled here, all others are handled via
 * (inverse) statements. Domains/ranges of multiple domain/range axioms are handled as (and C
 * C1...Cn).</td>
 * </tr>
 * <tr>
 * <td>OWLSubObjectPropertyAxiom</td>
 * <td>(implies-role r s)</td>
 * <td>Only applicable if r is an inverse property, otherwise superproperties are handled within the
 * define-primitive-role statement.</td>
 * </tr>
 * <tr>
 * <td>OWLInverseObjectPropertiesAxiom</td>
 * <td>(inverse r s)</td>
 * <td>Only inverse properties which are not introduced via define-primitive-role.</td>
 * </tr>
 * <tr>
 * <td>OWLObjectPropertyRangeAxiom</td>
 * <td></td>
 * <td>see define-primitive-role</td>
 * </tr>
 * <tr>
 * <td>OWLObjectPropertyDomainAxiom</td>
 * <td></td>
 * <td>see define-primitive-role</td>
 * </tr>
 * <tr>
 * <td>OWLSymmetricObjectPropertyAxiom</td>
 * <td></td>
 * <td>see define-primitive-role</td>
 * </tr>
 * <tr>
 * <td>OWLTransitiveObjectPropertyAxiom</td>
 * <td></td>
 * <td>see define-primitive-role</td>
 * </tr>
 * <tr>
 * <td>OWLReflexiveObjectPropertyAxiom</td>
 * <td></td>
 * <td>see define-primitive-role</td>
 * </tr>
 * <tr>
 * <td>OWLClassAssertionAxiom</td>
 * <td>(instance i D)</td>
 * </tr>
 * <tr>
 * <td>OWLDifferentIndividualsAxiom</td>
 * <td>(distinct i1 i2)</td>
 * <td><i>OWLDifferentIndividualsAxiom i1 i2 ... in</i> will be splitted into: <br>
 * { (distinct i(j) i(j+k)) | 1 &lt;= j &lt;=n, j&lt;k&lt;=n, j=|=k} <br>
 * </td>
 * </tr>
 * <tr>
 * <td>OWLObjectPropertyAssertionAxiom</td>
 * <td>(related i1 P i2)</td>
 * <td>i1: subject, i2: object</td>
 * </tr>
 * <tr>
 * <td>OWLSameIndividualsAxiom</td>
 * <td>(equal i1 i2)</td>
 * <td><i>OWLSameIndividual i1 i2 ...i(n-1) in</i> in will be splitted into:<br>
 * { (equal i(j) i(j+k)) | 1 &lt;= j &lt;=n, j&lt;k&lt;=n, j=|=k} <br>
 * (equal i1 i2)<br>
 * (equal i1 i3)<br>
 * ...<br>
 * (equal i(n-1) in)</td>
 * </tr>
 * </table>
 *
 * @author Olaf Noppens, Ulm University, Institute of Artificial Intelligence
 */
public class KRSS2ObjectRenderer extends KRSSObjectRenderer {

    private final Set<OWLSubPropertyChainOfAxiom> leftRightIdentityUsed;
    /**
     * If declarations are ignored, entities which are only referenced in a declaration are not
     * rendered.
     */
    protected boolean ignoreDeclarations = false;

    /**
     * @param ontology ontology to render
     * @param writer writer to render to
     */
    public KRSS2ObjectRenderer(OWLOntology ontology, Writer writer) {
        super(ontology, writer);
        leftRightIdentityUsed = new HashSet<>();
    }

    protected static boolean isLeftIdentityAxiom(OWLSubPropertyChainOfAxiom axiom,
        OWLObjectProperty property) {
        if (axiom.getSuperProperty().equals(property)) {
            List<OWLObjectPropertyExpression> propertyChain = axiom.getPropertyChain();
            if (propertyChain.size() < 3) {
                return false;
            }
            if (propertyChain.get(0).isOWLObjectProperty()
                && propertyChain.get(1).equals(property)) {
                return propertyChain.size() > 2;
            }
        }
        return false;
    }

    protected static boolean isRightIdentityAxiom(OWLSubPropertyChainOfAxiom axiom,
        OWLObjectProperty property) {
        if (axiom.getSuperProperty().equals(property)) {
            List<OWLObjectPropertyExpression> propertyChain = axiom.getPropertyChain();
            if (propertyChain.size() < 2) {
                return false;
            }
            if (propertyChain.get(0).equals(property)) {
                return propertyChain.size() > 2;
            }
        }
        return false;
    }

    /**
     * @param ignoreDeclarations true if declarations should be ignored
     */
    public void setIgnoreDeclarations(boolean ignoreDeclarations) {
        this.ignoreDeclarations = ignoreDeclarations;
    }

    @Override
    public void visit(OWLOntology ontology) {
        reset();
        for (OWLClass eachClass : asList(ontology.classesInSignature())) {
            if (ignoreDeclarations && ontology.axioms(eachClass).count() == 1
                && ontology.declarationAxioms(eachClass).count() == 1) {
                continue;
            }
            boolean primitive = !isDefined(eachClass, ontology);
            writeOpenBracket();
            if (primitive) { // there is no equivalentclasses axiom!
                write(DEFINE_PRIMITIVE_CONCEPT);
                write(eachClass);
                writeSpace();
                flatten(sortOptionally(
                    sup(ontology.axioms(Filters.subClassWithSub, eachClass, INCLUDED),
                        OWLClassExpression.class)));
                writeCloseBracket();
                writeln();
                Collection<OWLClassExpression> classes =
                    asList(equivalent(ontology.equivalentClassesAxioms(eachClass),
                        OWLClassExpression.class));
                for (OWLClassExpression description : classes) {
                    writeOpenBracket();
                    write(eachClass);
                    write(EQUIVALENT);
                    write(description);
                    writeCloseBracket();
                    writeln();
                }
            } else {
                writeOpenBracket();
                write(DEFINE_CONCEPT);
                write(eachClass);
                flatten(sortOptionally(equivalent(ontology.equivalentClassesAxioms(eachClass),
                    OWLClassExpression.class)));
                writeCloseBracket();
                writeln();
                Collection<OWLClassExpression> supclasses = asList(
                    sup(ontology.subClassAxiomsForSubClass(eachClass), OWLClassExpression.class));
                for (OWLClassExpression description : supclasses) {
                    writeOpenBracket();
                    write(eachClass);
                    write(IMPLIES);
                    write(description);
                    writeCloseBracket();
                    writeln();
                }
            }
        }
        for (OWLObjectProperty property : sortOptionally(ontology.objectPropertiesInSignature())) {
            if (ignoreDeclarations && ontology.axioms(property, EXCLUDED).count() == 1
                && ontology.declarationAxioms(property).count() == 1) {
                continue;
            }
            writeOpenBracket();
            Stream<OWLObjectPropertyExpression> streamp =
                equivalent(ontology.equivalentObjectPropertiesAxioms(property));
            Collection<OWLObjectPropertyExpression> properties = asList(streamp);
            boolean isPrimitive = properties.isEmpty();
            if (isPrimitive) {
                write(DEFINE_PRIMITIVE_ROLE);
                write(property);
                List<OWLObjectPropertyExpression> superProperties = sortOptionally(
                    sup(ontology.axioms(Filters.subObjectPropertyWithSub, property, INCLUDED),
                        OWLObjectPropertyExpression.class));
                int superSize = superProperties.size();
                if (superSize == 1) {
                    writeSpace();
                    write(PARENT_ATTR);
                    writeSpace();
                    write(superProperties.iterator().next());
                } else if (superSize > 1) {
                    writeSpace();
                    write(PARENTS_ATTR);
                    writeSpace();
                    flattenProperties(superProperties, null);
                } else {
                    // right/left identity?
                    // we only allow for either right or left identity axiom,
                    // otherwise it is
                    // expressed via role-inclusion axioms
                    Collection<OWLSubPropertyChainOfAxiom> chainAxioms =
                        getPropertyChainSubPropertyAxiomsFor(property);
                    if (chainAxioms.size() == 1) {
                        OWLSubPropertyChainOfAxiom axiom = chainAxioms.iterator().next();
                        if (isLeftIdentityAxiom(axiom, property)) {
                            leftRightIdentityUsed.add(axiom);
                            writeSpace();
                            write(LEFTIDENTITY_ATTR);
                            write(axiom.getPropertyChain().get(0));
                        } else if (isRightIdentityAxiom(axiom, property)) {
                            leftRightIdentityUsed.add(axiom);
                            writeSpace();
                            write(RIGHTIDENTITY_ATTR);
                            write(axiom.getPropertyChain().get(1));
                        }
                    }
                }
            } else {
                if (properties.isEmpty()) {
                    write(DEFINE_PRIMITIVE_ROLE);
                    write(property);
                    writeSpace();
                } else {
                    write(DEFINE_ROLE);
                    write(property);
                    OWLObjectPropertyExpression expr = properties.iterator().next();
                    write(expr);
                    properties.remove(expr);
                    writeSpace();
                }
            }
            if (isTransitive(property, ontology)) {
                writeSpace();
                write(TRANSITIVE_ATTR);
                writeSpace();
                write(TRUE);
            }
            if (isSymmetric(property, ontology)) {
                writeSpace();
                write(SYMMETRIC_ATTR);
                writeSpace();
                write(TRUE);
            }
            if (isReflexive(property, ontology)) {
                writeSpace();
                write(REFLEXIVE_ATTR);
                writeSpace();
                write(TRUE);
            }
            Iterator<OWLObjectPropertyExpression> inverses =
                inverse(ontology.inverseObjectPropertyAxioms(property), property).iterator();
            if (!inverses.hasNext()) {
                writeSpace();
                write(INVERSE_ATTR);
                write(inverses.next());
            }
            List<OWLClassExpression> stream =
                sortOptionally(domain(ontology.objectPropertyDomainAxioms(property)));
            if (!stream.isEmpty()) {
                writeSpace();
                write(DOMAIN_ATTR);
                flatten(stream);
            }
            stream = sortOptionally(range(ontology.objectPropertyRangeAxioms(property)));
            if (!stream.isEmpty()) {
                writeSpace();
                write(RANGE_ATTR);
                flatten(stream);
            }
            writeCloseBracket();
            writeln();
            while (inverses.hasNext()) {
                writeOpenBracket();
                write(INVERSE);
                write(property);
                write(inverses.next());
                writeOpenBracket();
                writeln();
            }
            for (OWLObjectPropertyExpression expr : properties) {
                writeOpenBracket();
                write(ROLES_EQUIVALENT);
                write(property);
                write(expr);
                writeCloseBracket();
                writeln();
            }
        }
        for (OWLNamedIndividual individual : sortOptionally(ontology.individualsInSignature())) {
            if (ignoreDeclarations && ontology.axioms(individual, EXCLUDED).count() == 1
                && ontology.declarationAxioms(individual).count() == 1) {
                continue;
            }
            writeOpenBracket();
            write(DEFINE_INDIVIDUAL);
            write(individual);
            writeCloseBracket();
            writeln();
        }
        ontology.axioms().forEach(a -> a.accept(this));
        writer.flush();
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        // we only handle GCIs
        if (!(axiom.getSubClass() instanceof OWLClass)) {
            writeOpenBracket();
            write(IMPLIES);
            write(axiom.getSubClass());
            write(axiom.getSuperClass());
            writeCloseBracket();
            writeln();
        }
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        pairs(axiom.properties()).forEach(v -> round(v, ROLES_EQUIVALENT));
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        pairs(axiom.properties()).forEach(v -> round(v, DISJOINT_ROLES));
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        if (!axiom.getSubProperty().isOWLObjectProperty()) {
            writeOpenBracket();
            write(IMPLIES_ROLE);
            write(axiom.getSubProperty());
            write(axiom.getSuperProperty());
            writeCloseBracket();
            writeln();
        }
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        pairs(axiom.classExpressions()).forEach(v -> round(v, EQUIVALENT));
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        if (leftRightIdentityUsed.contains(axiom)) {
            return;
        }
        writeOpenBracket();
        write(ROLE_INCLUSTION);
        writeSpace();
        writeChain(axiom.getPropertyChain(), 0);
        writeSpace();
        write(axiom.getSuperProperty());
        writeCloseBracket();
        writeln();
    }

    protected void writeChain(List<OWLObjectPropertyExpression> expressions, int i) {
        if (i == expressions.size() - 1) {
            write(expressions.get(i));
        } else {
            writeOpenBracket();
            write(COMPOSE);
            write(expressions.get(i));
            writeChain(expressions, i + 1);
            writeCloseBracket();
        }
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        writeOpenBracket();
        write(INVERSE);
        axiom.getFirstProperty().accept(this);
        axiom.getSecondProperty().accept(this);
        writeCloseBracket();
        writeln();
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        writeOpenBracket();
        write(ONE_OF);
        ce.individuals().forEach(this::write);
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        writeOpenBracket();
        write(INV);
        writeSpace();
        property.getInverseProperty();
        property.getInverse().accept(this);
        writeCloseBracket();
    }

    protected Collection<OWLSubPropertyChainOfAxiom> getPropertyChainSubPropertyAxiomsFor(
        OWLPropertyExpression property) {
        return asList(ont.axioms(AxiomType.SUB_PROPERTY_CHAIN_OF)
            .filter(a -> a.getSuperProperty().equals(property)));
    }

    protected void reset() {
        leftRightIdentityUsed.clear();
    }
}
