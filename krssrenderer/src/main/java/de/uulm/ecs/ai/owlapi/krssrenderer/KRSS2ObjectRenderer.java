package de.uulm.ecs.ai.owlapi.krssrenderer;

import static de.uulm.ecs.ai.owlapi.krssrenderer.KRSS2Vocabulary.*;

import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;

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
@SuppressWarnings("unused")
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

    @Override
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

    @Override
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

    @Override
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

    @Override
	public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
    }

    @Override
	public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
    }

    @Override
	public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
    }

    @Override
	public void visit(OWLDataPropertyDomainAxiom axiom) {
    }

    @Override
	public void visit(OWLImportsDeclaration axiom) {
    }

    @Override
	public void visit(OWLObjectPropertyDomainAxiom axiom) {
    }

    @Override
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

    @Override
	public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
    }

    @Override
	public void visit(OWLDifferentIndividualsAxiom axiom) {
    }

    @Override
	public void visit(OWLDisjointDataPropertiesAxiom axiom) {
    }

    @Override
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


    @Override
	public void visit(OWLObjectPropertyRangeAxiom axiom) {
    }

    @Override
	public final void visit(OWLObjectPropertyAssertionAxiom axiom) {
        write(RELATED);
        write(axiom.getSubject());
        write(axiom.getProperty());
        write(axiom.getObject());
        writeln();
    }

    @Override
	public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
    }

    @Override
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

    @Override
	public void visit(OWLDisjointUnionAxiom axiom) {
    }

    @Override
	public void visit(OWLDeclarationAxiom axiom) {
    }


    @Override
	public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
    }

    @Override
	public void visit(OWLDataPropertyRangeAxiom axiom) {
    }

    @Override
	public void visit(OWLFunctionalDataPropertyAxiom axiom) {
    }

    @Override
	public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
    }


    @Override
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

    @Override
	public void visit(OWLDataPropertyAssertionAxiom axiom) {
    }

    @Override
	public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
    }

    @Override
	public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
    }

    @Override
	public void visit(OWLSubDataPropertyOfAxiom axiom) {
    }

    @Override
	public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
    }


    @Override
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
	public final void visit(OWLClass desc) {
        write(desc.getIRI());
    }

    @Override
	public void visit(OWLObjectOneOf desc) {
        writeOpenBracket();
        write(ONE_OF);
        for (OWLIndividual individual : desc.getIndividuals())
            write(individual);
        writeCloseBracket();
    }

    @Override
	public final void visit(OWLObjectProperty property) {
        write(property.getIRI());
    }

    @Override
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
