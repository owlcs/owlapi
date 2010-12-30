package org.semanticweb.owlapi.profiles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.util.OWLObjectPropertyManager;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16-Apr-2008<br><br>
 */
public class OWL2ELProfile implements OWLProfile {

    protected Set<IRI> allowedDatatypes;

    //private OWLOntology ont;


    public OWL2ELProfile() {
        allowedDatatypes = new HashSet<IRI>();
        allowedDatatypes.add(OWLRDFVocabulary.RDF_XML_LITERAL.getIRI());
        allowedDatatypes.add(OWLRDFVocabulary.RDFS_LITERAL.getIRI());
        allowedDatatypes.add(OWL2Datatype.OWL_RATIONAL.getIRI());
        allowedDatatypes.add(OWL2Datatype.OWL_REAL.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_DECIMAL.getIRI());
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


    public String getName() {
        return "OWL 2 EL";
    }


    public OWLProfileReport checkOntology(OWLOntology ontology) {
        //this.ont = ontology;
        OWL2DLProfile profile = new OWL2DLProfile();
        OWLProfileReport report = profile.checkOntology(ontology);
        Set<OWLProfileViolation> violations = new HashSet<OWLProfileViolation>();
        violations.addAll(report.getViolations());
        OWLOntologyWalker ontologyWalker = new OWLOntologyWalker(ontology.getImportsClosure());
        OWL2ELProfileObjectVisitor visitor = new OWL2ELProfileObjectVisitor(ontologyWalker, ontology.getOWLOntologyManager());
        ontologyWalker.walkStructure(visitor);
        violations.addAll(visitor.getProfileViolations());
        return new OWLProfileReport(this, violations);
    }

    protected class OWL2ELProfileObjectVisitor extends OWLOntologyWalkerVisitor<Object> {

        private OWLOntologyManager man;

        private OWLObjectPropertyManager propertyManager;

        private Set<OWLProfileViolation> profileViolations = new HashSet<OWLProfileViolation>();

        public OWL2ELProfileObjectVisitor(OWLOntologyWalker walker, OWLOntologyManager man) {
            super(walker);
            this.man = man;
        }

        public Set<OWLProfileViolation> getProfileViolations() {
            return new HashSet<OWLProfileViolation>(profileViolations);
        }

        private OWLObjectPropertyManager getPropertyManager() {
            if (propertyManager == null) {
                propertyManager = new OWLObjectPropertyManager(man, getCurrentOntology());
            }
            return propertyManager;
        }

        @Override
		public Object visit(OWLDatatype node) {
            if (!allowedDatatypes.contains(node.getIRI())) {
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
		public Object visit(OWLObjectInverseOf property) {
            profileViolations.add(new UseOfObjectPropertyInverse(getCurrentOntology(), getCurrentAxiom(), property));
            return null;
        }

        @Override
		public Object visit(OWLDataAllValuesFrom desc) {
            profileViolations.add(new UseOfIllegalClassExpression(getCurrentOntology(), getCurrentAxiom(), desc));
            return null;
        }

        @Override
		public Object visit(OWLDataExactCardinality desc) {
            profileViolations.add(new UseOfIllegalClassExpression(getCurrentOntology(), getCurrentAxiom(), desc));
            return null;
        }

        @Override
		public Object visit(OWLDataMaxCardinality desc) {
            profileViolations.add(new UseOfIllegalClassExpression(getCurrentOntology(), getCurrentAxiom(), desc));
            return null;
        }

        @Override
		public Object visit(OWLDataMinCardinality desc) {
            profileViolations.add(new UseOfIllegalClassExpression(getCurrentOntology(), getCurrentAxiom(), desc));
            return null;
        }

        @Override
		public Object visit(OWLObjectAllValuesFrom desc) {
            profileViolations.add(new UseOfIllegalClassExpression(getCurrentOntology(), getCurrentAxiom(), desc));
            return null;
        }

        @Override
		public Object visit(OWLObjectComplementOf desc) {
            profileViolations.add(new UseOfIllegalClassExpression(getCurrentOntology(), getCurrentAxiom(), desc));
            return null;
        }

        @Override
		public Object visit(OWLObjectExactCardinality desc) {
            profileViolations.add(new UseOfIllegalClassExpression(getCurrentOntology(), getCurrentAxiom(), desc));
            return null;
        }

        @Override
		public Object visit(OWLObjectMaxCardinality desc) {
            profileViolations.add(new UseOfIllegalClassExpression(getCurrentOntology(), getCurrentAxiom(), desc));
            return null;
        }

        @Override
		public Object visit(OWLObjectMinCardinality desc) {
            profileViolations.add(new UseOfIllegalClassExpression(getCurrentOntology(), getCurrentAxiom(), desc));
            return null;
        }

        @Override
		public Object visit(OWLObjectOneOf desc) {
            if (desc.getIndividuals().size() != 1) {
                profileViolations.add(new UseOfObjectOneOfWithMultipleIndividuals(getCurrentOntology(), getCurrentAxiom(), desc));
            }
            return null;
        }

        @Override
		public Object visit(OWLObjectUnionOf desc) {
            profileViolations.add(new UseOfIllegalClassExpression(getCurrentOntology(), getCurrentAxiom(), desc));
            return null;
        }

        @Override
		public Object visit(OWLDataComplementOf node) {
            profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
            return null;
        }

        @Override
		public Object visit(OWLDataOneOf node) {
            if (node.getValues().size() != 1) {
                profileViolations.add(new UseOfDataOneOfWithMultipleLiterals(getCurrentOntology(), getCurrentAxiom(), node));
            }
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

        @Override
		public Object visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(OWLDisjointDataPropertiesAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(OWLDisjointObjectPropertiesAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(OWLDisjointUnionAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(OWLFunctionalObjectPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(OWLHasKeyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(OWLInverseObjectPropertiesAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(OWLSymmetricObjectPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(SWRLRule rule) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), rule));
            return super.visit(rule);
        }

        @Override
		public Object visit(OWLSubPropertyChainOfAxiom axiom) {
            Set<OWLObjectPropertyRangeAxiom> rangeAxioms = getCurrentOntology().getAxioms(AxiomType.OBJECT_PROPERTY_RANGE, true);
            if (rangeAxioms.isEmpty()) {
                return false;
            }

            // Do we have a range restriction imposed on our super property?
            for (OWLObjectPropertyRangeAxiom rngAx : rangeAxioms) {
                if (getPropertyManager().isSubPropertyOf(axiom.getSuperProperty(), rngAx.getProperty())) {
                    // Imposed range restriction!
                    OWLClassExpression imposedRange = rngAx.getRange();
                    // There must be an axiom that imposes a range on the last prop in the chain
                    List<OWLObjectPropertyExpression> chain = axiom.getPropertyChain();
                    if (!chain.isEmpty()) {
                        OWLObjectPropertyExpression lastProperty = chain.get(chain.size() - 1);
                        boolean rngPresent = false;
                        for (OWLOntology ont : getCurrentOntology().getImportsClosure()) {
                            for (OWLObjectPropertyRangeAxiom lastPropRngAx : ont.getObjectPropertyRangeAxioms(lastProperty)) {
                                if (lastPropRngAx.getRange().equals(imposedRange)) {
                                    // We're o.k.
                                    rngPresent = true;
                                    break;
                                }
                            }
                        }
                        if (!rngPresent) {
                            profileViolations.add(new LastPropertyInChainNotInImposedRange(getCurrentOntology(), axiom, rngAx));
                        }
                    }
                }
            }


            return null;
        }

        @Override
        @SuppressWarnings("unused")
		public Object visit(OWLOntology ontology) {
            propertyManager = null;
            return null;
        }
    }


   
}
