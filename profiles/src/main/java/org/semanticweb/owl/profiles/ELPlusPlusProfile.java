package org.semanticweb.owl.profiles;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.io.ToStringRenderer;
import org.semanticweb.owl.model.*;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;
import org.semanticweb.owl.vocab.XSDVocabulary;
import uk.ac.manchester.cs.owl.dlsyntax.DLSyntaxObjectRenderer;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
/*
 * Copyright (C) 2007, University of Manchester
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
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16-Apr-2008<br><br>
 */
public class ELPlusPlusProfile implements OWLProfile {

    private Set<URI> allowedDataTypes;

    private OWLOntology ont;


    public ELPlusPlusProfile() {
        allowedDataTypes = new HashSet<URI>();
        allowedDataTypes.add(OWLRDFVocabulary.RDFS_LITERAL.getURI());
        allowedDataTypes.add(XSDVocabulary.DECIMAL.getURI());
        allowedDataTypes.add(XSDVocabulary.INTEGER.getURI());
        allowedDataTypes.add(XSDVocabulary.NON_NEGATIVE_INTEGER.getURI());
        allowedDataTypes.add(XSDVocabulary.POSITIVE_INTEGER.getURI());
        allowedDataTypes.add(XSDVocabulary.DATE_TIME.getURI());
        allowedDataTypes.add(XSDVocabulary.DATE.getURI());
        allowedDataTypes.add(XSDVocabulary.STRING.getURI());
        allowedDataTypes.add(XSDVocabulary.NORMALIZED_STRING.getURI());
        allowedDataTypes.add(XSDVocabulary.ANY_URI.getURI());
        allowedDataTypes.add(XSDVocabulary.TOKEN.getURI());
        allowedDataTypes.add(XSDVocabulary.NAME.getURI());
        allowedDataTypes.add(XSDVocabulary.NCNAME.getURI());
        allowedDataTypes.add(XSDVocabulary.HEX_BINARY.getURI());
        allowedDataTypes.add(XSDVocabulary.BASE_64_BINARY.getURI());
    }


    public String getName() {
        return "EL++";
    }


    public OWLProfileReport checkOntology(OWLOntology ontology, OWLOntologyManager manager) {
        this.ont = ontology;
        ELPlusPlusChecker checker = new ELPlusPlusChecker();
        Set<ConstructNotAllowed> disallowedConstructs = new HashSet<ConstructNotAllowed>();
        for (OWLAxiom ax : ontology.getLogicalAxioms()) {
            ConstructNotAllowed cause = ax.accept(checker);
            if (cause != null) {
                disallowedConstructs.add(cause);
            }
        }
        return new OWLProfileReport(this, ontology.getURI(), disallowedConstructs);
    }

//    private void dump(ConstructNotAllowed cause, int level) {
//        for (int i = 0; i < level; i++) {
//            System.out.print("\t");
//        }
//        System.out.println(cause);
//        if(cause.getCause() != null) {
//            dump(cause.getCause(), level + 1);
//        }
//    }


    private void performGlobalCheck() {
        Map<OWLObjectProperty, Set<OWLObjectProperty>> map = new HashMap<OWLObjectProperty, Set<OWLObjectProperty>>();
        Set<OWLObjectProperty> processed = new HashSet<OWLObjectProperty>();
        for (OWLObjectProperty prop : ont.getReferencedObjectProperties()) {
            processProp(prop, map, processed);
        }
    }


    private Set<OWLObjectProperty> processProp(OWLObjectProperty prop,
                                               Map<OWLObjectProperty, Set<OWLObjectProperty>> map,
                                               Set<OWLObjectProperty> processed) {
        Set<OWLObjectProperty> props = map.get(prop);
        if (props == null) {
            props = new HashSet<OWLObjectProperty>();
            map.put(prop, props);
        }

        if (processed.contains(prop)) {
            return props;
        }
        processed.add(prop);
        for (OWLObjectSubPropertyAxiom ax : ont.getObjectSubPropertyAxiomsForLHS(prop)) {
            if (!ax.getSuperProperty().isAnonymous()) {
                props.add(ax.getSuperProperty().asOWLObjectProperty());
                props.addAll(processProp(ax.getSuperProperty().asOWLObjectProperty(), map, processed));
            }
        }
        return props;
    }


    private class ELPlusPlusChecker implements OWLDescriptionVisitorEx<ConstructNotAllowed>, OWLPropertyExpressionVisitorEx<ConstructNotAllowed>, OWLDataVisitorEx<ConstructNotAllowed>, OWLAxiomVisitorEx<ConstructNotAllowed> {


        public ConstructNotAllowed visit(OWLSubClassAxiom axiom) {
            ConstructNotAllowed cause = axiom.getSubClass().accept(this);
            if (cause != null) {
                return new AxiomNotAllowed(cause, axiom);
            }
            cause = axiom.getSuperClass().accept(this);
            if (cause != null) {
                return new AxiomNotAllowed(cause, axiom);
            }
            return null;
        }


        public ConstructNotAllowed visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            return new AxiomNotAllowed(axiom);
        }


        public ConstructNotAllowed visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {
            return new AxiomNotAllowed(axiom);
        }


        public ConstructNotAllowed visit(OWLReflexiveObjectPropertyAxiom axiom) {
            return axiom.getProperty().accept(this);
        }


        public ConstructNotAllowed visit(OWLDisjointClassesAxiom axiom) {
            for (OWLClassExpression desc : axiom.getDescriptions()) {
                ConstructNotAllowed cause = desc.accept(this);
                if (cause != null) {
                    return new AxiomNotAllowed(cause, axiom);
                }
            }
            return null;
        }


        public ConstructNotAllowed visit(OWLDataPropertyDomainAxiom axiom) {
            return axiom.getDomain().accept(this);
        }


        public ConstructNotAllowed visit(OWLImportsDeclaration axiom) {
            return null;
        }


        public ConstructNotAllowed visit(OWLAxiomAnnotationAxiom axiom) {
            return null;
        }


        public ConstructNotAllowed visit(OWLObjectPropertyDomainAxiom axiom) {
            ConstructNotAllowed cause = axiom.getProperty().accept(this);
            if (cause != null) {
                return new AxiomNotAllowed(cause, axiom);
            }
            cause = axiom.getDomain().accept(this);
            if (cause != null) {
                return new AxiomNotAllowed(cause, axiom);
            }
            return null;
        }


        public ConstructNotAllowed visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                ConstructNotAllowed cause = prop.accept(this);
                if (cause != null) {
                    return new AxiomNotAllowed(cause, axiom);
                }
            }
            return null;
        }


        public ConstructNotAllowed visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            return new AxiomNotAllowed(axiom);
        }


        public ConstructNotAllowed visit(OWLDifferentIndividualsAxiom axiom) {
            return new AxiomNotAllowed(axiom);
        }


        public ConstructNotAllowed visit(OWLDisjointDataPropertiesAxiom axiom) {
            return new AxiomNotAllowed(axiom);
        }


        public ConstructNotAllowed visit(OWLDisjointObjectPropertiesAxiom axiom) {
            return new AxiomNotAllowed(axiom);
        }


        public ConstructNotAllowed visit(OWLObjectPropertyRangeAxiom axiom) {
            ConstructNotAllowed cause = axiom.getProperty().accept(this);
            if (cause != null) {
                return new AxiomNotAllowed(cause, axiom);
            }
            cause = axiom.getRange().accept(this);
            if (cause != null) {
                return new AxiomNotAllowed(cause, axiom);
            }
            return null;
        }


        public ConstructNotAllowed visit(OWLObjectPropertyAssertionAxiom axiom) {
            return new AxiomNotAllowed(axiom);
        }


        public ConstructNotAllowed visit(OWLFunctionalObjectPropertyAxiom axiom) {
            return new AxiomNotAllowed(axiom);
        }


        public ConstructNotAllowed visit(OWLObjectSubPropertyAxiom axiom) {
            ConstructNotAllowed cause = axiom.getSubProperty().accept(this);
            if (cause != null) {
                return new AxiomNotAllowed(cause, axiom);
            }
            cause = axiom.getSuperProperty().accept(this);
            if (cause != null) {
                return new AxiomNotAllowed(cause, axiom);
            }
            return null;
        }


        public ConstructNotAllowed visit(OWLDisjointUnionAxiom axiom) {
            return new AxiomNotAllowed(axiom);
        }


        public ConstructNotAllowed visit(OWLDeclarationAxiom axiom) {
            return null;
        }


        public ConstructNotAllowed visit(OWLEntityAnnotationAxiom axiom) {
            return null;
        }


        public ConstructNotAllowed visit(OWLOntologyAnnotationAxiom axiom) {
            return null;
        }


        public ConstructNotAllowed visit(OWLSymmetricObjectPropertyAxiom axiom) {
            return new AxiomNotAllowed(axiom);
        }


        public ConstructNotAllowed visit(OWLDataPropertyRangeAxiom axiom) {
            return axiom.getRange().accept(this);
        }


        public ConstructNotAllowed visit(OWLFunctionalDataPropertyAxiom axiom) {
            return new AxiomNotAllowed(axiom);
        }


        public ConstructNotAllowed visit(OWLEquivalentDataPropertiesAxiom axiom) {
            return new AxiomNotAllowed(axiom);
        }


        public ConstructNotAllowed visit(OWLClassAssertionAxiom axiom) {
            return new AxiomNotAllowed(axiom);
        }


        public ConstructNotAllowed visit(OWLEquivalentClassesAxiom axiom) {
            for (OWLClassExpression desc : axiom.getDescriptions()) {
                ConstructNotAllowed cause = desc.accept(this);
                if (cause != null) {
                    return new AxiomNotAllowed(cause, axiom);
                }
            }
            return null;
        }


        public ConstructNotAllowed visit(OWLDataPropertyAssertionAxiom axiom) {
            return new AxiomNotAllowed(axiom);
        }


        public ConstructNotAllowed visit(OWLTransitiveObjectPropertyAxiom axiom) {
            return axiom.getProperty().accept(this);
        }


        public ConstructNotAllowed visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            return new AxiomNotAllowed(axiom);
        }


        public ConstructNotAllowed visit(OWLDataSubPropertyAxiom axiom) {
            return null;
        }


        public ConstructNotAllowed visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            return new AxiomNotAllowed(axiom);
        }


        public ConstructNotAllowed visit(OWLSameIndividualsAxiom axiom) {
            return new AxiomNotAllowed(axiom);
        }


        public ConstructNotAllowed visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
            // Global check required.
            return null;
        }


        public ConstructNotAllowed visit(OWLInverseObjectPropertiesAxiom axiom) {
            return new AxiomNotAllowed(axiom);
        }


        public ConstructNotAllowed visit(SWRLRule rule) {
            return new AxiomNotAllowed(rule);
        }


        public ConstructNotAllowed visit(OWLObjectProperty property) {
            return null;
        }


        public ConstructNotAllowed visit(OWLObjectPropertyInverse property) {
            return new InversePropertiesNotAllowed(property);
        }


        public ConstructNotAllowed visit(OWLDataProperty property) {
            return null;
        }


        public ConstructNotAllowed visit(OWLDatatype node) {
            if (allowedDataTypes.contains(node.getURI())) {
                return null;
            } else {
                return new DataTypeNotAllowed(node);
            }
        }


        public ConstructNotAllowed visit(OWLDataComplementOf node) {
            return new DataRangeNotAllowed(node);
        }


        public ConstructNotAllowed visit(OWLDataOneOf node) {
            if (node.getValues().size() != 1) {
                return new DataRangeNotAllowed(node);
            }
            ConstructNotAllowed cause = node.getValues().iterator().next().accept(this);
            if (cause != null) {
                return new DataRangeNotAllowed(cause, node);
            }
            return null;
        }


        public ConstructNotAllowed visit(OWLDataRangeRestriction node) {
            return new DataRangeNotAllowed(node);
        }


        public ConstructNotAllowed visit(OWLTypedLiteral node) {
            return node.getDataType().accept(this);
        }


        public ConstructNotAllowed visit(OWLRDFTextLiteral node) {
            return null;
        }


        public ConstructNotAllowed visit(OWLDataRangeFacetRestriction node) {
            return null;
        }


        public ConstructNotAllowed visit(OWLClass desc) {
            return null;
        }


        public ConstructNotAllowed visit(OWLObjectIntersectionOf desc) {
            for (OWLClassExpression op : desc.getOperands()) {
                ConstructNotAllowed obj = op.accept(this);
                if (obj != null) {
                    return new DescriptionNotAllowed(obj, desc);
                }
            }
            return null;
        }


        public ConstructNotAllowed visit(OWLObjectUnionOf desc) {
            return new DescriptionNotAllowed(desc);
        }


        public ConstructNotAllowed visit(OWLObjectComplementOf desc) {
            return new DescriptionNotAllowed(desc);
        }


        public ConstructNotAllowed visit(OWLObjectSomeValuesFrom desc) {
            ConstructNotAllowed cause = desc.getProperty().accept(this);
            if (cause != null) {
                return new DescriptionNotAllowed(cause, desc);
            }
            cause = desc.getFiller().accept(this);
            if (cause != null) {
                return new DescriptionNotAllowed(cause, desc);
            }
            return null;
        }


        public ConstructNotAllowed visit(OWLObjectAllValuesFrom desc) {
            return new DescriptionNotAllowed(desc);
        }


        public ConstructNotAllowed visit(OWLObjectValueRestriction desc) {
            ConstructNotAllowed cause = desc.getProperty().accept(this);
            if (cause != null) {
                return new DescriptionNotAllowed(cause, desc);
            }
            return null;
        }


        public ConstructNotAllowed visit(OWLObjectMinCardinalityRestriction desc) {
            return new DescriptionNotAllowed(desc);
        }


        public ConstructNotAllowed visit(OWLObjectExactCardinalityRestriction desc) {
            return new DescriptionNotAllowed(desc);
        }


        public ConstructNotAllowed visit(OWLObjectMaxCardinalityRestriction desc) {
            return new DescriptionNotAllowed(desc);
        }


        public ConstructNotAllowed visit(OWLObjectSelfRestriction desc) {
            ConstructNotAllowed cause = desc.getProperty().accept(this);
            if (cause != null) {
                return new DescriptionNotAllowed(cause, desc);
            }
            return null;
        }


        public ConstructNotAllowed visit(OWLObjectOneOf desc) {
            if (desc.getIndividuals().size() != 1) {
                return new NonSingletonNominalsNotAllowed(desc);
            }
            return null;
        }


        public ConstructNotAllowed visit(OWLDataSomeValuesFrom desc) {
            ConstructNotAllowed cause = desc.getProperty().accept(this);
            if (cause != null) {
                return new DescriptionNotAllowed(cause, desc);
            }
            cause = desc.getFiller().accept(this);
            if (cause != null) {
                return new DescriptionNotAllowed(cause, desc);
            }
            return null;
        }


        public ConstructNotAllowed visit(OWLDataAllValuesFrom desc) {
            return new DescriptionNotAllowed(desc);
        }


        public ConstructNotAllowed visit(OWLDataValueRestriction desc) {
            return null;
        }


        public ConstructNotAllowed visit(OWLDataMinCardinalityRestriction desc) {
            return new DescriptionNotAllowed(desc);
        }


        public ConstructNotAllowed visit(OWLDataExactCardinalityRestriction desc) {
            return new DescriptionNotAllowed(desc);
        }


        public ConstructNotAllowed visit(OWLDataMaxCardinalityRestriction desc) {
            return new DescriptionNotAllowed(desc);
        }
    }


    private class InversePropertiesNotAllowed extends ConstructNotAllowed {


        public InversePropertiesNotAllowed(Object construct) {
            super(construct);
        }


        public InversePropertiesNotAllowed(ConstructNotAllowed cause, Object construct) {
            super(cause, construct);
        }


        public String toString() {
            return "Inverse properties not allowed";
        }
    }

    private class DataTypeNotAllowed extends DataRangeNotAllowed {


        public DataTypeNotAllowed(OWLDataRange construct) {
            super(construct);
        }


        public DataTypeNotAllowed(ConstructNotAllowed cause, OWLDataRange construct) {
            super(cause, construct);
        }


        public String toString() {
            return "DataType not allowed: " + getCause();
        }
    }

    private class NonSingletonNominalsNotAllowed extends ConstructNotAllowed {


        public NonSingletonNominalsNotAllowed(Object construct) {
            super(construct);
        }


        public NonSingletonNominalsNotAllowed(ConstructNotAllowed cause, Object construct) {
            super(cause, construct);
        }


        public String toString() {
            return "Non-singleton enumerations not allowed: " + getCause();
        }
    }

    public static void main(String[] args) {
        try {
            ToStringRenderer.getInstance().setRenderer(new DLSyntaxObjectRenderer());
            OWLOntologyManager man = OWLManager.createOWLOntologyManager();
            man.setSilentMissingImportsHandling(true);
//            OWLOntology ont = man.loadOntology(URI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl"));
            OWLOntology ont = man.loadOntology(URI.create("http://rpc295.cs.man.ac.uk:8080/ontologyrepository/download?ontology=http://sweet.jpl.nasa.gov/ontology/earthrealm.owl&version=0&format=RDF/XML"));
            ELPlusPlusProfile profile = new ELPlusPlusProfile();
            profile.checkOntology(ont, man);
        }
        catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }
}
