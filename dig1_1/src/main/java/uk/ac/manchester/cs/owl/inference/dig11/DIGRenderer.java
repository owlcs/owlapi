package uk.ac.manchester.cs.owl.inference.dig11;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.net.URI;
import java.util.Stack;
import java.util.logging.Logger;
/*
 * Copyright (C) 2006, University of Manchester
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
 * Date: 21-Nov-2006<br><br>
 */
public class DIGRenderer implements OWLObjectVisitor {

    private static final Logger logger = Logger.getLogger(DIGRenderer.class.getName());

    private OWLOntologyManager manager;

    private Document document;

    private Stack<Element> nodeStack;

    public DIGRenderer(OWLOntologyManager manager, Document document, Element node) {
        this.manager = manager;
        this.document = document;
        nodeStack = new Stack();
        nodeStack.push(node);
    }

    private void createAndPushNode(String name) {
        Element n = document.createElement(name);
        getCurrentNode().appendChild(n);
        nodeStack.push(n);
    }

    private Element getCurrentNode() {
        return nodeStack.peek();
    }

    private void popCurrentNode() {
        nodeStack.pop();
    }




    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Axioms
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLSubClassAxiom axiom) {
        createAndPushNode(Vocab.IMPLIESC);
        axiom.getSubClass().accept(this);
        axiom.getSuperClass().accept(this);
        popCurrentNode();
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        for(OWLDescription descA : axiom.getDescriptions()) {
            for(OWLDescription descB : axiom.getDescriptions()) {
                if (!descA.equals(descB)) {
                    createAndPushNode(Vocab.EQUALC);
                    descA.accept(this);
                    descB.accept(this);
                    popCurrentNode();
                }
            }
        }

    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        // Needs to be expanded
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        createAndPushNode(Vocab.DISJOINT);
        for (OWLDescription desc : axiom.getDescriptions()) {
            desc.accept(this);
        }
        popCurrentNode();
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        // Disjoint isets
        createAndPushNode(Vocab.DISJOINT);
        for(OWLIndividual ind : axiom.getIndividuals()) {
            createAndPushNode(Vocab.ISET);
            ind.accept(this);
            popCurrentNode();
        }
        popCurrentNode();
    }

    public void visit(OWLSameIndividualsAxiom axiom) {
        // Render as pairwise equivalent isets
        for(OWLIndividual indA : axiom.getIndividuals()) {
            for(OWLIndividual indB : axiom.getIndividuals()) {
                if (!indA.equals(indB)) {
                    createAndPushNode(Vocab.EQUALC);
                    createAndPushNode(Vocab.ISET);
                    indA.accept(this);
                    popCurrentNode();
                    createAndPushNode(Vocab.ISET);
                    indB.accept(this);
                    popCurrentNode();
                    popCurrentNode();
                }
            }
        }
    }



    public void visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {
        throw new RuntimeException("Anti-symmetric properties are not supported in DIG 1.1");
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        throw new RuntimeException("Reflexive properties are not supported in DIG 1.1");
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
    }


    public void visit(OWLImportsDeclaration axiom) {
        // We ignore this
    }


    public void visit(OWLAxiomAnnotationAxiom axiom) {
        // Ignore - no semantic import
    }


    public void visit(OWLEntityAnnotationAxiom axiom) {
        // Ignore - no semantic import
    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        createAndPushNode(Vocab.DOMAIN);
        axiom.getProperty().accept(this);
        axiom.getDomain().accept(this);
        popCurrentNode();
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        for (OWLObjectPropertyExpression p1 : axiom.getProperties()) {
            for (OWLObjectPropertyExpression p2 : axiom.getProperties()) {
                if (!p1.equals(p2)) {
                    createAndPushNode(Vocab.EQUALR);
                    p1.accept(this);
                    p2.accept(this);
                    popCurrentNode();
                }
            }
        }
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        createAndPushNode(Vocab.EQUALR);
        axiom.getFirstProperty().accept(this);
        // Equal to the inverse
        OWLObjectPropertyExpression invProp = axiom.getSecondProperty();
        manager.getOWLDataFactory().getOWLObjectPropertyInverse(invProp).accept(this);
        popCurrentNode();
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        // Cannot represent in DIG 1.1
        throw new RuntimeException("Negative data property relationships cannot be represented in DIG 1.1");
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        throw new RuntimeException("Disjoint data properties cannot be represented in DIG 1.1");
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        throw new RuntimeException("Disjoint object properties cannot be represented in DIG 1.1");
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        createAndPushNode(Vocab.RELATED);
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
        popCurrentNode();
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        createAndPushNode(Vocab.FUNCTIONAL);
        axiom.getProperty().accept(this);
        popCurrentNode();
    }


    public void visit(OWLObjectSubPropertyAxiom axiom) {
        createAndPushNode(Vocab.IMPLIESR);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
        popCurrentNode();
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        // Expand
    }


    public void visit(OWLDeclarationAxiom axiom) {
        // Ignore
    }


    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        createAndPushNode(Vocab.EQUALR);
        axiom.getProperty().accept(this);
        createAndPushNode(Vocab.INVERSE);
        axiom.getProperty().accept(this);
        popCurrentNode();
        popCurrentNode();
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        // All data properties are functional in DIG 1.1
        logger.info("Ignoring functional data property axiom, because all data properties are functional in DIG 1.1");
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        throw new RuntimeException("Equivalent data properties are not supported in DIG 1.1");
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        createAndPushNode(Vocab.INSTANCEOF);
        axiom.getIndividual().accept(this);
        axiom.getDescription().accept(this);
        popCurrentNode();
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
//        throw new OWLException("Individual data relationships are not supported in DIG 1.1");
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        createAndPushNode(Vocab.TRANSITIVE);
        axiom.getProperty().accept(this);
        popCurrentNode();
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        throw new RuntimeException("Irreflexive properties are not supported in DIG 1.1");
    }


    public void visit(OWLDataSubPropertyAxiom axiom) {
        // I think this is allowed in DIG 1.1
        createAndPushNode(Vocab.IMPLIESR);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
        popCurrentNode();
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        createAndPushNode(Vocab.FUNCTIONAL);
        createAndPushNode(Vocab.INVERSE);
        axiom.getProperty().accept(this);
        popCurrentNode();
        popCurrentNode();
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        createAndPushNode(Vocab.RANGE);
        axiom.getProperty().accept(this);
        axiom.getRange().accept(this);
        popCurrentNode();
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
//        throw new OWLException("OWL data property ranges are not supported in DIG 1.1");
    }


    public void visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLObjectPropertyInverse property) {
        createAndPushNode(Vocab.INVERSE);
        property.getInverse().accept(this);
        popCurrentNode();
    }


    public void visit(OWLDataProperty property) {

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void visit(OWLObjectIntersectionOf node) {
        createAndPushNode(Vocab.AND);
        for (OWLDescription desc : node.getOperands()) {
            desc.accept(this);
        }
        popCurrentNode();
    }

    public void visit(OWLIndividual node) {
        createAndPushNode(Vocab.INDIVIDUAL);
        getCurrentNode().setAttribute(Vocab.NAME, node.getURI().toString());
        popCurrentNode();
    }

    public void visit(OWLObjectAllRestriction node) {
        createAndPushNode(Vocab.ALL);
        node.getProperty().accept(this);
        node.getFiller().accept(this);
        popCurrentNode();
    }


    public void visit(OWLObjectMinCardinalityRestriction desc) {
        createAndPushNode(Vocab.ATLEST);
                getCurrentNode().setAttribute("num", Integer.toString(desc.getCardinality()));
        desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popCurrentNode();
    }


    public void visit(OWLObjectExactCardinalityRestriction desc) {
        createAndPushNode(Vocab.ATLEST);
            getCurrentNode().setAttribute("num", Integer.toString(desc.getCardinality()));
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popCurrentNode();
            createAndPushNode(Vocab.ATMOST);
            getCurrentNode().setAttribute("num", Integer.toString(desc.getCardinality()));
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popCurrentNode();
    }


    public void visit(OWLObjectMaxCardinalityRestriction desc) {
        createAndPushNode(Vocab.ATMOST);
                getCurrentNode().setAttribute("num", Integer.toString(desc.getCardinality()));
        desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popCurrentNode();
    }

    public void visit(OWLObjectProperty node) {
        createAndPushNode(Vocab.RATOM);
        getCurrentNode().setAttribute(Vocab.NAME, node.getURI().toString());
        popCurrentNode();
    }

    public void visit(OWLObjectSomeRestriction node) {
        createAndPushNode(Vocab.SOME);
        node.getProperty().accept(this);
        node.getFiller().accept(this);
        popCurrentNode();
    }

    public void visit(OWLObjectValueRestriction node) {
        createAndPushNode(Vocab.SOME);
        node.getProperty().accept(this);
        createAndPushNode(Vocab.ISET);
        node.getValue().accept(this);
        popCurrentNode();
        popCurrentNode();
    }

    public void visit(OWLObjectComplementOf node) {
        createAndPushNode(Vocab.NOT);
        node.getOperand().accept(this);
        popCurrentNode();
    }

    public void visit(OWLOntology ontology) {
        for(OWLClass cls : ontology.getReferencedClasses()) {
            createAndPushNode(Vocab.DEFCONCEPT);
            getCurrentNode().setAttribute(Vocab.NAME, cls.getURI().toString());
            popCurrentNode();
        }
        for(OWLObjectProperty prop : ontology.getReferencedObjectProperties()) {
            createAndPushNode(Vocab.DEFROLE);
            getCurrentNode().setAttribute(Vocab.NAME, prop.getURI().toString());
            popCurrentNode();
        }
        for(OWLIndividual ind : ontology.getReferencedIndividuals()) {
            createAndPushNode(Vocab.DEFINDIVIDUAL);
            getCurrentNode().setAttribute(Vocab.NAME, ind.getURI().toString());
            popCurrentNode();
        }
        for(OWLAxiom ax : ontology.getAxioms()) {
            ax.accept(this);
        }
    }

    public void visit(OWLObjectUnionOf node) {
        createAndPushNode(Vocab.OR);
        for (OWLDescription desc : node.getOperands()) {
            desc.accept(this);
        }
        popCurrentNode();
    }

    public static final URI THING_URI = OWLRDFVocabulary.OWL_THING.getURI();

    public static final URI NOTHING_URI = OWLRDFVocabulary.OWL_NOTHING.getURI();

    public void visit(OWLClass node) {
        if (node.getURI().equals(NOTHING_URI)) {
            createAndPushNode(Vocab.BOTTOM);
            popCurrentNode();
        } else if (node.getURI().equals(THING_URI)) {
            createAndPushNode(Vocab.TOP);
            popCurrentNode();
        } else {
            createAndPushNode(Vocab.CATOM);
            getCurrentNode().setAttribute(Vocab.NAME, node.getURI().toString());
            popCurrentNode();
        }
    }

    public void visit(OWLObjectOneOf node) {
        createAndPushNode(Vocab.ISET);
        for (OWLIndividual ind : node.getIndividuals()) {
            ind.accept(this);
        }
        popCurrentNode();
    }


    public void visit(OWLObjectSelfRestriction desc) {
    }


    public void visit(OWLDataSomeRestriction desc) {
    }


    public void visit(OWLDataAllRestriction desc) {
    }


    public void visit(OWLDataValueRestriction desc) {
    }


    public void visit(OWLDataMinCardinalityRestriction desc) {
    }


    public void visit(OWLDataExactCardinalityRestriction desc) {
    }


    public void visit(OWLDataMaxCardinalityRestriction desc) {
    }


    public void visit(OWLOntologyAnnotationAxiom axiom) {
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLDataType node) {
    }


    public void visit(OWLDataComplementOf node) {
    }


    public void visit(OWLDataOneOf node) {
    }


    public void visit(OWLDataRangeRestriction node) {
    }


    public void visit(OWLDataRangeFacetRestriction node) {
    }


    public void visit(OWLTypedConstant node) {
    }


    public void visit(OWLUntypedConstant node) {
    }

    public void visit(OWLConstantAnnotation annotation) {
    }


    public void visit(OWLObjectAnnotation annotation) {
    }


    public void visit(SWRLRule rule) {
    }


    public void visit(SWRLClassAtom node) {
    }


    public void visit(SWRLDataRangeAtom node) {
    }


    public void visit(SWRLObjectPropertyAtom node) {
    }


    public void visit(SWRLDataValuedPropertyAtom node) {
    }


    public void visit(SWRLBuiltInAtom node) {
    }


    public void visit(SWRLAtomDVariable node) {
    }


    public void visit(SWRLAtomIVariable node) {
    }


    public void visit(SWRLAtomIndividualObject node) {
    }


    public void visit(SWRLAtomConstantObject node) {
    }


    public void visit(SWRLDifferentFromAtom node) {
    }


    public void visit(SWRLSameAsAtom node) {
    }
}
