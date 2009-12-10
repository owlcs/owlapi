package de.uulm.ecs.ai.owlapi.krssrenderer;

import static de.uulm.ecs.ai.owlapi.krssrenderer.KRSS2Vocabulary.*;
import org.semanticweb.owlapi.model.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
/*
 * Copyright (C) 2008, Ulm University
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
 * <code>KRSS2ObjectRenderer</code> is an extension of {@link KRSSObjectRenderer
 * KRSSObjectRenderer} which uses the extended vocabulary.
 * <p/>
 * <b>Abbreviations</b>
 * <table bordercolor="#000200" border="1">
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
 * <p/>
 * <b>KRSS concept language</b>
 * <table bordercolor="#000200" border="1">
 * <tr>
 * <td>KRSS</td>
 * <td>OWLClassExpression</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(at-least n R C)</td>
 * <td>(OWLObjectMinCardinality R n C)</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(at-most n R C)</td>
 * <td>(OWLObjectMaxCardinality R n C)</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(exactly n R C)</td>
 * <td>(OWLObjectExactCardinality R n C)</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(some R C)</td>
 * <td>(OWLObjectSomeValuesFrom R C)</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(all R C)</td>
 * <td>(OWLObjectAllValuesFrom R C)</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(not C)</td>
 * <td>(OWLObjectComplementOf C)</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(and C D E)</td>
 * <td>(OWLObjectIntersectionOf C D E)</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(or C D E)</td>
 * <td>(OWLObjectUnionOf C D E)</td>
 * </tr>
 * <p/>
 * <p/>
 * </table>
 * <p/>
 * <b>KRSS role language</b>
 * <table bordercolor="#000200" border="1">
 * <tr>
 * <td>KRSS</td>
 * <td>OWLObjectPropertyExpression</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(inv R)</td>
 * <td>(OWLInverseObjectPropertiesAxiom R)</td>
 * </tr>
 * <p/>
 * </table>
 * Each referenced class, object property as well as individual is
 * defined using <i>define-concept</i> resp. <i>define-primitive-concept</i>,
 * <i>define-role</i> and <i>define-individual</i>. In addition, axioms are
 * translated as follows.
 * <p/>
 * <table bordercolor="#000200" border="1">
 * <th>OWLAxiom</th>
 * <th>KRSS syntax</th>
 * <th>Remarks</th>
 * <p/>
 * <tr>
 * <td>OWLDisjointClassesAxiom</td>
 * <td>(disjoint C D)</td>
 * <td><i>OWLDisjointClasses C D1 D2 ... Dn</i> will be translated to: <br>
 * { (disjoint i(j) i(j+k)) | 1 &lt;= j &lt;=n, j&lt;k&lt;=n, j=|=k}  <br>
 * </td>
 * </tr>
 * <tr>
 * <td>OWLEquivalentClasses</td>
 * <td>(define-concept C D)</td>
 * <td><i>OWLEquivalentClasses C D1 D2...Dn</i> will be translated to:<br>
 * (define-concept C (and D1 D2...Dn))
 * <p/>
 * Only applicable if there is no OWLSubClassOf axiom. <p>
 * </td>
 * </tr>
 * <tr>
 * <td>OWLSubClassOfAxiom</td>
 * <td>(define-primitive-concept C D)
 * </td>
 * <td><i>OWLSubClassOfAxiom C D1...Dn</i> (n>1) will be translated to:<br>
 * (define-primitive-concept C (and D1...Dn))
 * <p/>
 * Only applicable if there is no OWLEquivalentClasses axiom. In that
 * case the class will be introduced via (define-concept...) and
 * all subclass axioms are handled via implies
 * <br>
 * </td>
 * </tr>
 * <tr>
 * <td>OWLSubClassOfAxiom</td>
 * <td>(implies D C)</td>
 * <td>Only in case of GCIs with concept expression (not named class) D,
 * or in case that D is a non-primitive concept. Otherwise superclasses
 * are introduced via (define-primitive-concept D ...)
 * </td>
 * </tr>
 * <p/>
 * <tr>
 * <td>OWLEquivalentObjectPropertiesAxiom</td>
 * <td>(roles-equivalent r s)</td>
 * <td>All roles are explicitly introduced via
 * define-primitive-role.</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>OWLSubPropertyChainOfAxiom</td>
 * <td>(role-inclusion (compose r s) t)</td>
 * <td>Role inclusions of the kind (role-inclusion (compose r s) r)
 * resp. (role-inclusion (compose s r) r) are handled within the
 * (define-primitive-role) statement as right- resp. left-identities iff
 * it is the only role-inclusion wrt. the super property. </td></tr>
 * <p/>
 * <tr>
 * <td>OWLSubObjectPropertyAxiom</td>
 * <td>(define-primitive-role R :parent S)<p>
 * (define-primitive-role R :parents S T U)<p>
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
 * <td>This will be only used if there is no OWLEquivalentClasses
 * axiom containing R (see define-role).
 * The additional attributes are added if there is an OWLTransitiveObjectProperyAxiom,
 * OWLSymmetricObjectPropertyAxiom, OWLReflexiveObjectPropertyAxiom,
 * OWLObjectPropertyDomainAxiom, OWLObjectPropertyRangeAxiom resp.
 * OWLInverseObjectPropertiesAxiom. If there are multiple OWLInverseObjectPropertiesAxioms
 * only one inverse is handled here, all others are handled via
 * (inverse) statements. Domains/ranges of multiple
 * domain/range axioms are handled as (and C C1...Cn).
 * </td>
 * </tr>
 * <p/>
 * <tr>
 * <td>OWLSubObjectPropertyAxiom</td>
 * <td>(implies-role r s)</td>
 * <td>Only applicable if r is an inverse property, otherwise superproperties
 * are handled within the define-primitive-role
 * statement.
 * </td>
 * </tr>
 * <p/>
 * <p/>
 * <tr>
 * <td>OWLInverseObjectPropertiesAxiom</td>
 * <td>(inverse r s)</td>
 * <td>Only inverse properties which are not introduced via
 * define-primitive-role.
 * </td>
 * </tr>
 * <tr>
 * <td>OWLObjectPropertyRangeAxiom</td>
 * <td></td>
 * <td>see define-primitive-role</td>
 * </tr>
 * <p/>
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
 * <p/>
 * <tr>
 * <td>OWLTransitiveObjectPropertyAxiom</td>
 * <td></td>
 * <td>see define-primitive-role</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>OWLReflexiveObjectPropertyAxiom</td>
 * <td></td>
 * <td>see define-primitive-role</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>OWLClassAssertionAxiom</td>
 * <td>(instance i D)</td>
 * </td></td>
 * </tr>
 * <tr>
 * <td>OWLDifferentIndividualsAxiom</td>
 * <td>(distinct i1 i2)</td>
 * <td><i>OWLDifferentIndividualsAxiom i1 i2 ... in</i> will be splitted into:<br>
 * { (distinct i(j) i(j+k)) | 1 &lt;= j &lt;=n, j&lt;k&lt;=n, j=|=k}  <br>
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
 * { (equal i(j) i(j+k)) | 1 &lt;= j &lt;=n, j&lt;k&lt;=n, j=|=k}  <br>
 * (equal i1 i2)<br>
 * (equal i1 i3)<br>
 * ...<br>
 * (equal i(n-1) in)
 * </td>
 * </tr>
 * <p/>
 * </table>
 * <p/>
 * Author: Olaf Noppens<br>
 * Ulm University<br>
 * Institute of Artificial Intelligence<br>
 */
public class KRSS2ObjectRenderer extends KRSSObjectRenderer {

    private final Set<OWLSubPropertyChainOfAxiom> leftRightIdentityUsed;
    /**
     * If declarations are ignored, entities which are only referenced in a declaration
     * are not rendered.
     */
    protected boolean ignoreDeclarations = false;

    public KRSS2ObjectRenderer(OWLOntologyManager manager, OWLOntology ontology, Writer writer) {
        super(manager, ontology, writer);
        this.leftRightIdentityUsed = new HashSet<OWLSubPropertyChainOfAxiom>();
    }

    public void setIgnoreDeclarations(boolean ignoreDeclarations) {
        this.ignoreDeclarations = ignoreDeclarations;
    }

    protected void write(KRSS2Vocabulary v) {
        write(v.toString());
    }

    public final void visit(OWLOntology ontology) {
        reset();
        for (final OWLClass eachClass : ontology.getClassesInSignature()) {
            if (ignoreDeclarations) {
                if (ontology.getAxioms(eachClass).size() == 1 && ontology.getDeclarationAxioms(eachClass).size() == 1) {
                    continue;
                }
            }
            final boolean primitive = !eachClass.isDefined(ontology);//!eachClass.getSuperClasses(ontology).isEmpty();
            writeOpenBracket();


            if (primitive) { //there is no equivalentclasses axiom!
                write(DEFINE_PRIMITIVE_CONCEPT);
                write(eachClass);
                writeSpace();
                flatten(eachClass.getSuperClasses(ontology), KRSSVocabulary.AND);
                writeCloseBracket();
                writeln();
                for (OWLClassExpression description : eachClass.getEquivalentClasses(ontology)) {
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
                flatten(eachClass.getEquivalentClasses(ontology), KRSSVocabulary.AND);
                writeCloseBracket();
                writeln();
                for (OWLClassExpression description : eachClass.getSuperClasses(ontology)) {
                    writeOpenBracket();
                    write(eachClass);
                    write(IMPLIES);
                    write(description);
                    writeCloseBracket();
                    writeln();
                }
            }
        }
        /*  for (final OWLClassAxiom axiom : ontology.getGeneralClassAxioms()) {
                    axiom.accept(this);
                }
        */
        for (final OWLObjectProperty property : sort(ontology.getObjectPropertiesInSignature())) {
            if (ignoreDeclarations) {
                if (ontology.getAxioms(property).size() == 1 && ontology.getDeclarationAxioms(property).size() == 1) {
                    continue;
                }
            }
            writeOpenBracket();
            final Set<OWLObjectPropertyExpression> properties = property.getEquivalentProperties(ontology);
            final boolean isPrimitive = properties.isEmpty();

            if (isPrimitive) {
                write(DEFINE_PRIMITIVE_ROLE);
                write(property);
                Set<OWLObjectPropertyExpression> superProperties = property.getSuperProperties(ontology);
                if (superProperties.size() == 1) {
                    writeSpace();
                    write(PARENT_ATTR);
                    writeSpace();
                    write(superProperties.iterator().next());
                } else if (superProperties.size() > 1) {
                    writeSpace();
                    write(PARENTS_ATTR);
                    writeSpace();
                    flattenProperties(superProperties, null);
                } else {
                    //right/left identity?
                    //we only allow for either right or left identity axiom, otherwise it is
                    //expressed via role-inclusion axioms

                    Set<OWLSubPropertyChainOfAxiom> chainAxioms = getPropertyChainSubPropertyAxiomsFor(property);
                    if (chainAxioms.size() == 1) {
                        OWLSubPropertyChainOfAxiom axiom = chainAxioms.iterator().next();
                        if (isLeftIdentityAxiom(axiom, property)) {
                            this.leftRightIdentityUsed.add(axiom);
                            writeSpace();
                            write(LEFTIDENTITY_ATTR);
                            write(axiom.getPropertyChain().get(0));

                        } else if (isRightIdentityAxiom(axiom, property)) {
                            this.leftRightIdentityUsed.add(axiom);
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

            if (property.isTransitive(ontology)) {
                writeSpace();
                write(TRANSITIVE_ATTR);
                writeSpace();
                write(TRUE);
            }
            if (property.isSymmetric(ontology)) {
                writeSpace();
                write(SYMMETRIC_ATTR);
                writeSpace();
                write(TRUE);
            }
            if (property.isReflexive(ontology)) {
                writeSpace();
                write(REFLEXIVE_ATTR);
                writeSpace();
                write(TRUE);
            }
            final Iterator<OWLObjectPropertyExpression> inverses = property.getInverses(ontology).iterator();
            if (inverses.hasNext()) {
                writeSpace();
                write(INVERSE_ATTR);
                write(inverses.next());
            }
            Set<OWLClassExpression> desc = property.getDomains(ontology);
            if (!desc.isEmpty()) {
                writeSpace();
                write(DOMAIN_ATTR);
                flatten(desc, KRSSVocabulary.AND);
            }
            desc = property.getRanges(ontology);
            if (!desc.isEmpty()) {
                writeSpace();
                write(RANGE_ATTR);
                flatten(desc, KRSSVocabulary.AND);
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
        for (final OWLNamedIndividual individual : sort(ontology.getIndividualsInSignature())) {
            if (ignoreDeclarations) {
                if (ontology.getAxioms(individual).size() == 1 && ontology.getDeclarationAxioms(individual).size() == 1)
                {
                    continue;
                }
            }
            writeOpenBracket();
            write(DEFINE_INDIVIDUAL);
            write(individual);
            writeCloseBracket();
            writeln();
        }
        for (final OWLAxiom axiom : ontology.getAxioms())
            axiom.accept(this);
        try {
            writer.flush();
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    public final void visit(OWLSubClassOfAxiom axiom) {
        //we only handle GCIs
        if (!(axiom.getSubClass() instanceof OWLClass)) {
            writeOpenBracket();
            write(IMPLIES);
            write(axiom.getSubClass());
            write(axiom.getSuperClass());
            writeCloseBracket();
            writeln();
        }
    }

    public void visit(OWLDisjointClassesAxiom axiom) {
        List<OWLClassExpression> descs = sort(axiom.getClassExpressions());
        int size = descs.size();
        if (size <= 1) return;
        for (int i = 0; i < size; i++)
            for (int j = i + 1; j < size; j++) {
                writeOpenBracket();
                write(DISJOINT);
                write(descs.get(i));
                write(descs.get(j));
                writeCloseBracket();
                writeln();
            }
    }

    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
    }

    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
    }

    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
    }

    public void visit(OWLDataPropertyDomainAxiom axiom) {
    }

    public void visit(OWLImportsDeclaration axiom) {
    }

    public void visit(OWLObjectPropertyDomainAxiom axiom) {
    }

    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        List<OWLObjectPropertyExpression> properties = sort(axiom.getProperties());
        int size = properties.size();
        for (int i = 0; i < size; i++)
            for (int j = i + 1; j < size; j++) {
                writeOpenBracket();
                write(ROLES_EQUIVALENT);
                write(properties.get(i));
                write(properties.get(j));
                writeCloseBracket();
                writeln();
            }
    }

    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
    }

    public void visit(OWLDifferentIndividualsAxiom axiom) {
    }

    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
    }

    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        List<OWLObjectPropertyExpression> properties = sort(axiom.getProperties());
        int size = properties.size();
        if (size <= 1) return;
        for (int i = 0; i < size; i++)
            for (int j = i + 1; j < size; j++) {
                writeOpenBracket();
                write(DISJOINT_ROLES);
                write(properties.get(i));
                write(properties.get(j));
                writeCloseBracket();
                writeln();
            }
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
    }

    public final void visit(OWLObjectPropertyAssertionAxiom axiom) {
        write(RELATED);
        write(axiom.getSubject());
        write(axiom.getProperty());
        write(axiom.getObject());
        writeln();
    }

    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
    }

    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        if (!(axiom.getSubProperty() instanceof OWLObjectProperty)) {
            writeOpenBracket();
            write(IMPLIES_ROLE);
            write(axiom.getSubProperty());
            write(axiom.getSuperProperty());
            writeCloseBracket();
            writeln();
        }
    }

    public void visit(OWLDisjointUnionAxiom axiom) {
    }

    public void visit(OWLDeclarationAxiom axiom) {
    }


    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
    }

    public void visit(OWLDataPropertyRangeAxiom axiom) {
    }

    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
    }

    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        List<OWLClassExpression> descriptions = sort(axiom.getClassExpressions());
        int size = descriptions.size();
        if (size <= 1) return;
        for (int i = 0; i < size; i++)
            for (int j = i + 1; j < size; j++) {
                writeOpenBracket();
                write(EQUIVALENT);
                write(descriptions.get(i));
                write(descriptions.get(j));
                writeCloseBracket();
                writeln();
            }
    }

    public void visit(OWLDataPropertyAssertionAxiom axiom) {
    }

    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
    }

    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
    }

    public void visit(OWLSubDataPropertyOfAxiom axiom) {
    }

    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
    }


    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        if (this.leftRightIdentityUsed.contains(axiom)) return;
        writeOpenBracket();
        write(ROLE_INCLUSTION);
        writeSpace();
        writeChain(axiom.getPropertyChain(), 0);
        writeSpace();
        write(axiom.getSuperProperty());
        writeCloseBracket();
        writeln();
    }

    protected void writeChain(final List<OWLObjectPropertyExpression> expressions, int i) {
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


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        writeOpenBracket();
        write(INVERSE);
        axiom.getFirstProperty().accept(this);
        axiom.getSecondProperty().accept(this);
        writeCloseBracket();
        writeln();
    }

    public final void visit(OWLClass desc) {
        write(desc.getIRI());
    }

    public void visit(OWLObjectOneOf desc) {
        writeOpenBracket();
        write(ONE_OF);
        for (OWLIndividual individual : desc.getIndividuals())
            write(individual);
        writeCloseBracket();
    }

    public final void visit(OWLObjectProperty property) {
        write(property.getIRI());
    }

    public final void visit(OWLObjectInverseOf property) {
        writeOpenBracket();
        write(INV);
        writeSpace();
        property.getInverseProperty();
        property.getInverse().accept(this);
        writeCloseBracket();
    }


    protected boolean isLeftIdentityAxiom(OWLSubPropertyChainOfAxiom axiom, OWLObjectProperty property) {
        if (axiom.getSuperProperty().equals(property)) {
            Iterator<OWLObjectPropertyExpression> chain = axiom.getPropertyChain().iterator();
            if (chain.hasNext()) {
                if (chain.next() instanceof OWLObjectProperty) {
                    if (chain.hasNext() && chain.next().equals(property)) {
                        return !chain.hasNext();
                    }
                }
            }
        }
        return false;
    }

    protected boolean isRightIdentityAxiom(OWLSubPropertyChainOfAxiom axiom, OWLObjectProperty property) {
        if (axiom.getSuperProperty().equals(property)) {
            Iterator<OWLObjectPropertyExpression> chain = axiom.getPropertyChain().iterator();
            if (chain.hasNext()) {
                if (chain.next().equals(property)) {
                    if (chain.hasNext()) {
                        chain.next();
                        return !chain.hasNext();
                    }
                }
            }
        }
        return false;
    }

    protected Set<OWLSubPropertyChainOfAxiom> getPropertyChainSubPropertyAxiomsFor(OWLPropertyExpression property) {
        Set<OWLSubPropertyChainOfAxiom> axioms = new HashSet<OWLSubPropertyChainOfAxiom>();
        for (OWLSubPropertyChainOfAxiom axiom : ontology.getAxioms(AxiomType.SUB_PROPERTY_CHAIN_OF))
            if (axiom.getSuperProperty().equals(property))
                axioms.add(axiom);
        return axioms;
    }

    public void reset() {
        this.leftRightIdentityUsed.clear();
    }
}
