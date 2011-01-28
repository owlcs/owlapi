package de.uulm.ecs.ai.owlapi.krssrenderer;

import static de.uulm.ecs.ai.owlapi.krssrenderer.KRSS2Vocabulary.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;

/**
 * @author Olaf Noppens
 */
@SuppressWarnings("unused")
public class KRSS2OWLObjectRenderer implements OWLObjectVisitor {
    private final OWLOntology ontology;
    private final Writer writer;

    public KRSS2OWLObjectRenderer(OWLOntology ontology, Writer writer) {
        this.ontology = ontology;
        this.writer = writer;
    }

    private void writeOpenBracket() {
        try {
            writer.write("(");
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private void writeCloseBracket() {
        try {
            writer.write(")");
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private void write(int i) {
        try {
            writer.write(" " + i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(IRI iri) {
        try {
            writer.write(iri.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeAttribute(KRSS2Vocabulary v) {
        try {
            writeSpace();
            writer.write(":");
            writer.write(v.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(KRSS2Vocabulary v) {
        try {
            writer.write(v.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeSpace() {
        try {
            writer.write(" ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeln() {
        try {
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public final void write(OWLClassExpression obj) {
        writeSpace();
        obj.accept(this);
    }

    public final void write(OWLIndividual ind) {
        writeSpace();
        ind.accept(this);
    }

    public final void write(OWLPropertyExpression obj) {
        writeSpace();
        obj.accept(this);
    }

    public final void write(OWLDataRange obj) {
        writeSpace();
        obj.accept(this);
    }


    private final void flatten(Set<OWLClassExpression> classExpressions) {
        if (classExpressions.isEmpty()) return;
        final OWLClassExpression desc = classExpressions.iterator().next();
        if (classExpressions.size() == 1) {
            write(desc);
            return;
        }
        classExpressions.remove(desc);
        writeOpenBracket();
        write(AND);
        write(desc);
        flatten(classExpressions);
        writeCloseBracket();
    }

    public final void visit(OWLOntology ontology) {
        for (final OWLClass eachClass : ontology.getClassesInSignature()) {
            final boolean primitive = !eachClass.isDefined(ontology);//!eachClass.getSuperClasses(ontology).isEmpty();
            if (primitive) {
                writeOpenBracket();
                write(DEFINE_PRIMITIVE_CONCEPT);
                write(eachClass);
                writeSpace();
                Set<OWLClassExpression> superClasses = eachClass.getSuperClasses(ontology);
                if (superClasses.size() == 1) {
                    write(superClasses.iterator().next());
                } else {
                    flatten(superClasses);
                }
                writeCloseBracket(); //==> end definition of primitive-concept
                writeln();
                for (OWLClassExpression classExpression : eachClass.getEquivalentClasses(ontology)) {
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
                Set<OWLClassExpression> equivalentClasses = eachClass.getEquivalentClasses(ontology);
                if (equivalentClasses.isEmpty()) {
                    //?
                    writeCloseBracket();
                    writeln();
                } else if (equivalentClasses.size() == 1) {
                    write(equivalentClasses.iterator().next());
                    writeCloseBracket();
                    writeln();
                } else {
                    Iterator<OWLClassExpression> iter = equivalentClasses.iterator();
                    write(iter.next());
                    writeCloseBracket();
                    writeln();
                    for (; iter.hasNext();) {
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
        for (final OWLClassAxiom axiom : ontology.getGeneralClassAxioms()) {
            axiom.accept(this);
        }

        for (final OWLObjectProperty property : ontology.getObjectPropertiesInSignature()) {
            writeOpenBracket();
            write(DEFINE_PRIMITIVE_ROLE);
            write(property);

            if (property.isTransitive(ontology)) {
                writeAttribute(TRANSITIVE_ATTR);
                writeSpace();
                write(TRUE);
            }
            if (property.isSymmetric(ontology)) {
                writeAttribute(SYMMETRIC_ATTR);
                writeSpace();
                write(TRUE);
            }
            final Set<OWLClassExpression> domains = property.getDomains(ontology);
            if (!domains.isEmpty()) {
                writeAttribute(DOMAIN);
                flatten(domains);
            }
            final Set<OWLClassExpression> ranges = property.getDomains(ontology);
            if (!ranges.isEmpty()) {
                writeAttribute(RANGE_ATTR);
                flatten(ranges);
            }
            final Set<OWLObjectPropertyExpression> superProperties = property.getSuperProperties(ontology);
            if (!superProperties.isEmpty()) {
                writeAttribute(PARENTS_ATTR);
                writeOpenBracket();
                for (OWLObjectPropertyExpression express : superProperties) {
                    write(express);
                }
                writeCloseBracket();
            }

            writeCloseBracket();
        }
        try {
            writer.flush();
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    public final void visit(OWLSubClassOfAxiom axiom) {
        writeOpenBracket();
        write(IMPLIES);
        write(axiom.getSubClass());
        write(axiom.getSuperClass());
        writeCloseBracket();
    }

    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
    }

    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
    }

    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
    }

    public final void visit(OWLDisjointClassesAxiom axiom) {
        writeOpenBracket();
        for (final OWLClassExpression desc : axiom.getClassExpressions()) {
            write(desc);
        }
        writeCloseBracket();
    }

    public void visit(OWLDataPropertyDomainAxiom axiom) {
    }

    public void visit(OWLImportsDeclaration axiom) {
    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {
    }

    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
    }

    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
    }

    public void visit(OWLDifferentIndividualsAxiom axiom) {
    }

    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
    }

    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
    }

    public void visit(OWLObjectPropertyRangeAxiom axiom) {
    }

    public final void visit(OWLObjectPropertyAssertionAxiom axiom) {
        write(RELATED);
        write(axiom.getSubject());
        write(axiom.getObject());
        write(axiom.getProperty());
        writeln();
    }

    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
    }

    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
    }

    public void visit(OWLDisjointUnionAxiom axiom) {
    }

    public void visit(OWLDeclarationAxiom axiom) {
    }

    public void visit(OWLAnnotationAssertionAxiom axiom) {
    }

    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
    }

    public void visit(OWLDataPropertyRangeAxiom axiom) {
    }

    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
    }

    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
    }

    public final void visit(OWLClassAssertionAxiom axiom) {
        write(INSTANCE);
        write(axiom.getIndividual());
        write(axiom.getClassExpression());
        writeln();
    }

    public void visit(OWLLiteral node) {
    }

    public void visit(OWLEquivalentClassesAxiom axiom) {
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

    public void visit(OWLSameIndividualAxiom axiom) {
    }

    public void visit(OWLSubPropertyChainOfAxiom axiom) {
    }

    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
    }

    public void visit(SWRLRule rule) {
    }

    public void visit(SWRLClassAtom node) {
    }

    public void visit(SWRLDataRangeAtom node) {
    }

    public void visit(SWRLObjectPropertyAtom node) {
    }

    public void visit(SWRLDataPropertyAtom node) {
    }

    public void visit(SWRLBuiltInAtom node) {
    }

    public void visit(SWRLVariable node) {
    }

    public void visit(SWRLIndividualArgument node) {
    }

    public void visit(SWRLLiteralArgument node) {
    }

    public void visit(SWRLSameIndividualAtom node) {
    }

    public void visit(SWRLDifferentIndividualsAtom node) {
    }

    public final void visit(OWLClass desc) {
        write(desc.getIRI());
    }

    public final void visit(OWLObjectIntersectionOf desc) {
        writeOpenBracket();
        write(AND);
        for (final OWLClassExpression des : desc.getOperands()) {
            write(des);
        }
        writeCloseBracket();
    }

    public final void visit(OWLObjectUnionOf desc) {
        writeOpenBracket();
        write(OR);
        for (OWLClassExpression des : desc.getOperands()) {
            write(des);
        }
        writeCloseBracket();
    }

    public final void visit(OWLObjectComplementOf desc) {
        writeOpenBracket();
        write(NOT);
        write(desc.getOperand());
        writeCloseBracket();
    }

    public final void visit(OWLObjectSomeValuesFrom desc) {
        writeOpenBracket();
        write(SOME);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    public final void visit(OWLObjectAllValuesFrom desc) {
        writeOpenBracket();
        write(ALL);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    public void visit(OWLObjectHasValue desc) {

    }

    public final void visit(OWLObjectMinCardinality desc) {
        writeOpenBracket();
        write(AT_LEAST);
        write(desc.getCardinality());
        write(desc.getProperty());
        if (desc.isQualified()) {
            write(desc.getFiller());
        }
        writeCloseBracket();
    }

    public final void visit(OWLObjectExactCardinality desc) {
        writeOpenBracket();
        write(EXACTLY);
        write(desc.getCardinality());
        write(desc.getProperty());
        if (desc.isQualified()) {
            write(desc.getFiller());
        }
        writeCloseBracket();
    }

    public final void visit(OWLObjectMaxCardinality desc) {
        writeOpenBracket();
        write(AT_MOST);
        write(desc.getCardinality());
        write(desc.getProperty());
        if (desc.isQualified()) {
            write(desc.getFiller());
        }
        writeCloseBracket();
    }

    public void visit(OWLObjectHasSelf desc) {
    }

    public void visit(OWLObjectOneOf desc) {
    }

    public final void visit(OWLDataSomeValuesFrom desc) {
        writeOpenBracket();
        write(SOME);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    public final void visit(OWLDataAllValuesFrom desc) {
        writeOpenBracket();
        write(ALL);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    public void visit(OWLDataHasValue desc) {
    }

    public final void visit(OWLDataMinCardinality desc) {
        writeOpenBracket();
        write(AT_LEAST);
        write(desc.getCardinality());
        write(desc.getProperty());
        if (desc.isQualified()) {
            write(desc.getFiller());
        }
        writeCloseBracket();
    }

    public final void visit(OWLDataExactCardinality desc) {
        writeOpenBracket();
        write(EXACTLY);
        write(desc.getCardinality());
        write(desc.getProperty());
        if (desc.isQualified()) {
            write(desc.getFiller());
        }
        writeCloseBracket();
    }

    public final void visit(OWLDataMaxCardinality desc) {
        writeOpenBracket();
        write(AT_MOST);
        write(desc.getCardinality());
        write(desc.getProperty());
        if (desc.isQualified()) {
            write(desc.getFiller());
        }
        writeCloseBracket();
    }

    public void visit(OWLDatatype node) {

    }

    public void visit(OWLDataComplementOf node) {

    }

    public void visit(OWLDataOneOf node) {

    }

    public void visit(OWLDatatypeRestriction node) {

    }

    public void visit(OWLFacetRestriction node) {

    }

    public final void visit(OWLObjectProperty property) {
        write(property.getIRI());
    }

    public final void visit(OWLObjectInverseOf property) {
        writeOpenBracket();
        write(INVERSE);
        writeSpace();
        property.getInverse().accept(this);
        writeCloseBracket();
    }

    public final void visit(OWLDataProperty property) {
        write(property.getIRI());
    }

    public final void visit(OWLNamedIndividual individual) {
        write(individual.getIRI());
    }

    public void visit(OWLHasKeyAxiom axiom) {
    }

    public void visit(OWLDataIntersectionOf node) {
    }


    public void visit(OWLDatatypeDefinitionAxiom axiom) {
    }


    public void visit(OWLDataUnionOf node) {
    }

    public void visit(OWLAnnotationProperty property) {
    }

    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
    }

    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
    }

    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
    }

    public void visit(OWLAnonymousIndividual individual) {
    }

    public void visit(IRI iri) {
    }

    public void visit(OWLAnnotation node) {
    }
}
