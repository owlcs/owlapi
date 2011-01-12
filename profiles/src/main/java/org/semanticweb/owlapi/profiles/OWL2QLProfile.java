package org.semanticweb.owlapi.profiles;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 18-Jul-2009
 */
public class OWL2QLProfile implements OWLProfile {

    private Set<IRI> allowedDatatypes = new HashSet<IRI>();

    public OWL2QLProfile() {
        allowedDatatypes.add(OWLRDFVocabulary.RDF_PLAIN_LITERAL.getIRI());
        allowedDatatypes.add(OWLRDFVocabulary.RDF_XML_LITERAL.getIRI());
        allowedDatatypes.add(OWLRDFVocabulary.RDFS_LITERAL.getIRI());
        allowedDatatypes.add(OWL2Datatype.OWL_REAL.getIRI());
        allowedDatatypes.add(OWL2Datatype.OWL_RATIONAL.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_DECIMAL.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_INTEGER.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_NON_NEGATIVE_INTEGER.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_STRING.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_NORMALIZED_STRING.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_TOKEN.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_NAME.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_NCNAME.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_NMTOKEN.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_HEX_BINARY.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_BASE_64_BINARY.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_ANY_URI.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_DATE_TIME.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_DATE_TIME_STAMP.getIRI());
    }

    /**
     * Gets the name of the profile.
     * @return A string that represents the name of the profile
     */
    public String getName() {
        return "OWL 2 QL";
    }

    /**
     * Checks an ontology and its import closure to see if it is within
     * this profile.
     * @param ontology The ontology to be checked.
     * @return An <code>OWLProfileReport</code> that describes whether or not the
     *         ontology is within this profile.
     */
    public OWLProfileReport checkOntology(OWLOntology ontology) {
        OWL2DLProfile profile = new OWL2DLProfile();
        OWLProfileReport report = profile.checkOntology(ontology);
        Set<OWLProfileViolation> violations = new HashSet<OWLProfileViolation>();
        violations.addAll(report.getViolations());

        OWLOntologyWalker walker = new OWLOntologyWalker(ontology.getImportsClosure());
        OWL2QLObjectVisitor visitor = new OWL2QLObjectVisitor(walker);
        walker.walkStructure(visitor);
        violations.addAll(visitor.getProfileViolations());
        return new OWLProfileReport(this, violations);
    }
    
    private class OWL2QLObjectVisitor extends OWLOntologyWalkerVisitor<Object> {

        private Set<OWLProfileViolation> profileViolations = new HashSet<OWLProfileViolation>();

        private OWL2QLObjectVisitor(OWLOntologyWalker walker) {
            super(walker);
        }

        public Set<OWLProfileViolation> getProfileViolations() {
            return new HashSet<OWLProfileViolation>(profileViolations);
        }

        @Override
		public Object visit(OWLDatatype node) {
            if(!allowedDatatypes.contains(node.getIRI())) {
                profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
            }
            return null;
        }

        @Override
		public Object visit(OWLAnonymousIndividual individual) {
            profileViolations.add(new UseOfAnonymousIndividual(getCurrentOntology(), getCurrentAxiom(), individual));
            return null;
        }

        @Override
		public Object visit(OWLHasKeyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(OWLSubClassOfAxiom axiom) {
            if(!isOWL2QLSubClassExpression(axiom.getSubClass())) {
                profileViolations.add(new UseOfNonSubClassExpression(getCurrentOntology(), axiom, axiom.getSubClass()));
            }
            if(!isOWL2QLSuperClassExpression(axiom.getSuperClass())) {
                profileViolations.add(new UseOfNonSuperClassExpression(getCurrentOntology(), axiom, axiom.getSuperClass()));
            }
            return null;
        }

        @Override
		public Object visit(OWLEquivalentClassesAxiom axiom) {
            for(OWLClassExpression ce : axiom.getClassExpressions()) {
                if(!isOWL2QLSubClassExpression(ce)) {
                profileViolations.add(new UseOfNonSubClassExpression(getCurrentOntology(), axiom, ce));
                }
            }
            return null;
        }

        @Override
		public Object visit(OWLDisjointClassesAxiom axiom) {
            for(OWLClassExpression ce : axiom.getClassExpressions()) {
                if(!isOWL2QLSubClassExpression(ce)) {
                    profileViolations.add(new UseOfNonSubClassExpression(getCurrentOntology(), axiom, ce));
                }
            }
            return null;
        }

        @Override
		public Object visit(OWLObjectPropertyDomainAxiom axiom) {
            if(!isOWL2QLSuperClassExpression(axiom.getDomain())) {
                profileViolations.add(new UseOfNonSuperClassExpression(getCurrentOntology(), axiom, axiom.getDomain()));
            }
            return null;
        }

        @Override
		public Object visit(OWLObjectPropertyRangeAxiom axiom) {
            if(!isOWL2QLSuperClassExpression(axiom.getRange())) {
                profileViolations.add(new UseOfNonSuperClassExpression(getCurrentOntology(), axiom, axiom.getRange()));
            }
            return null;
        }

        @Override
		public Object visit(OWLSubPropertyChainOfAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(OWLFunctionalObjectPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(OWLTransitiveObjectPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(OWLFunctionalDataPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(OWLDataPropertyDomainAxiom axiom) {
            if(!isOWL2QLSuperClassExpression(axiom.getDomain())) {
                profileViolations.add(new UseOfNonSuperClassExpression(getCurrentOntology(), axiom, axiom.getDomain()));
            }
            return null;
        }

        @Override
		public Object visit(OWLClassAssertionAxiom axiom) {
            if(axiom.getClassExpression().isAnonymous()) {
                profileViolations.add(new UseOfNonAtomicClassExpression(getCurrentOntology(), axiom, axiom.getClassExpression()));
            }
            return null;
        }

        @Override
		public Object visit(OWLSameIndividualAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(OWLDisjointUnionAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(SWRLRule rule) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), rule));
            return null;
        }

        @Override
		public Object visit(OWLDataComplementOf node) {
            profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
            return null;
        }

        @Override
		public Object visit(OWLDataOneOf node) {
            profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
            return null;
        }

        @Override
		public Object visit(OWLDatatypeRestriction node) {
            profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
            return null;
        }

        @Override
		public Object visit(OWLDataUnionOf node) {
            profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
            return null;
        }
    }

    @SuppressWarnings("unused")
    private static class OWL2QLSubClassExpressionChecker implements OWLClassExpressionVisitorEx<Boolean> {

        public Boolean visit(OWLClass desc) {
            return true;
        }

        public Boolean visit(OWLObjectIntersectionOf desc) {
            return false;
        }

        public Boolean visit(OWLObjectUnionOf desc) {
            return false;
        }

        public Boolean visit(OWLObjectComplementOf desc) {
            return false;
        }

        public Boolean visit(OWLObjectSomeValuesFrom desc) {
            return desc.getFiller().isOWLThing();
        }

        public Boolean visit(OWLObjectAllValuesFrom desc) {
            return false;
        }

        public Boolean visit(OWLObjectHasValue desc) {
            return false;
        }

        public Boolean visit(OWLObjectMinCardinality desc) {
            return false;
        }

        public Boolean visit(OWLObjectExactCardinality desc) {
            return false;
        }

        public Boolean visit(OWLObjectMaxCardinality desc) {
            return false;
        }

        public Boolean visit(OWLObjectHasSelf desc) {
            return false;
        }

        public Boolean visit(OWLObjectOneOf desc) {
            return false;
        }

        public Boolean visit(OWLDataSomeValuesFrom desc) {
            return true;
        }

        public Boolean visit(OWLDataAllValuesFrom desc) {
            return false;
        }

        public Boolean visit(OWLDataHasValue desc) {
            return false;
        }

        public Boolean visit(OWLDataMinCardinality desc) {
            return false;
        }

        public Boolean visit(OWLDataExactCardinality desc) {
            return false;
        }

        public Boolean visit(OWLDataMaxCardinality desc) {
            return false;
        }
    }


    private OWL2QLSubClassExpressionChecker subClassExpressionChecker = new OWL2QLSubClassExpressionChecker();

    protected boolean isOWL2QLSubClassExpression(OWLClassExpression ce) {
            return ce.accept(subClassExpressionChecker);
    }
    @SuppressWarnings("unused")
    private class OWL2QLSuperClassExpressionChecker implements OWLClassExpressionVisitorEx<Boolean> {

        public Boolean visit(OWLClass desc) {
            return true;
        }

        public Boolean visit(OWLObjectIntersectionOf desc) {
            for(OWLClassExpression ce : desc.getOperands()) {
                if(!ce.accept(this)) {
                    return false;
                }
            }
            return true;
        }

        public Boolean visit(OWLObjectUnionOf desc) {
            return false;
        }

        public Boolean visit(OWLObjectComplementOf desc) {
            return isOWL2QLSubClassExpression(desc.getOperand());
        }

        public Boolean visit(OWLObjectSomeValuesFrom desc) {
            return !desc.getFiller().isAnonymous();
        }

        public Boolean visit(OWLObjectAllValuesFrom desc) {
            return false;
        }

        public Boolean visit(OWLObjectHasValue desc) {
            return false;
        }

        public Boolean visit(OWLObjectMinCardinality desc) {
            return false;
        }

        public Boolean visit(OWLObjectExactCardinality desc) {
            return false;
        }

        public Boolean visit(OWLObjectMaxCardinality desc) {
            return false;
        }

        public Boolean visit(OWLObjectHasSelf desc) {
            return false;
        }

        public Boolean visit(OWLObjectOneOf desc) {
            return false;
        }

        public Boolean visit(OWLDataSomeValuesFrom desc) {
            return true;
        }

        public Boolean visit(OWLDataAllValuesFrom desc) {
            return false;
        }

        public Boolean visit(OWLDataHasValue desc) {
            return false;
        }

        public Boolean visit(OWLDataMinCardinality desc) {
            return false;
        }

        public Boolean visit(OWLDataExactCardinality desc) {
            return false;
        }

        public Boolean visit(OWLDataMaxCardinality desc) {
            return false;
        }
    }

    private OWL2QLSuperClassExpressionChecker superClassExpressionChecker = new OWL2QLSuperClassExpressionChecker();

    public boolean isOWL2QLSuperClassExpression(OWLClassExpression ce) {
        return ce.accept(superClassExpressionChecker);
    }

}
