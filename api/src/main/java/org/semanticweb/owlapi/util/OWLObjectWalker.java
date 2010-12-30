package org.semanticweb.owlapi.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
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
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
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
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
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
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
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
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 29-Jul-2008<br><br>
 */
public class OWLObjectWalker<O extends OWLObject> {

    protected OWLOntology ontology;

    private Collection<O> objects;

    private OWLObjectVisitorEx<?> visitor;

    private boolean visitDuplicates;

    protected OWLAxiom ax;

    private OWLAnnotation annotation;

    private List<OWLClassExpression> classExpressionPath = new ArrayList<OWLClassExpression>();

    private List<OWLDataRange> dataRangePath = new ArrayList<OWLDataRange>();
    

    public OWLObjectWalker(Set<O> objects) {
        this(objects, true);
    }

    public OWLObjectWalker(Set<O> objects, boolean visitDuplicates) {
        this.objects = new ArrayList<O>(objects);
        this.visitDuplicates = visitDuplicates;
    }

    public void walkStructure(OWLObjectVisitorEx<?> v) {
        this.visitor = v;
        StructureWalker walker = new StructureWalker();
        for (O o : objects) {
            o.accept(walker);
        }
    }

    /**
     * Gets the last ontology to be visited.
     * @return The last ontology to be visited
     */
    public OWLOntology getOntology() {
        return ontology;
    }

    /**
     * Gets the last axiom to be visited.
     * @return The last axiom to be visited, or <code>null</code> if an axiom has not be visited
     */
    public OWLAxiom getAxiom() {
        return ax;
    }

    /**
     * Gets the last annotation to be visited.
     * @return The last annotation to be visited (may be <code>null</code>)
     */
    public OWLAnnotation getAnnotation() {
        return annotation;
    }

    /**
     * Gets the current class expression path.  The current class expression path is a list of class expressions
     * that represents the containing expressions for the current class expressions.  The first item in the path (list)
     * is the root class expression that was visited.  For 0 < i < pathLength, the item at index i+1
     * is a direct sub-expression of the item at index i.  The last item in the path is the current class expression
     * being visited.
     * @return A list of class expressions that represents the path of class expressions, with the root of
     *         the class expression being the first element in the list.
     */
    public List<OWLClassExpression> getClassExpressionPath() {
        return new ArrayList<OWLClassExpression>(classExpressionPath);
    }

    /**
     * Determines if a particular class expression is the first (or root) class expression in the
     * current class expression path
     * @param classExpression The class expression
     * @return <code>true</code> if the specified class expression is the first class expression
     * in the current class expression path, otherwise <code>false</code> (<code>false</code> if the
     * path is empty)
     */
    public boolean isFirstClassExpressionInPath(OWLClassExpression classExpression) {
        return !classExpressionPath.isEmpty() && classExpressionPath.get(0).equals(classExpression);
    }

    /**
     * Pushes a class expression onto the class expression path
     * @param ce The class expression to be pushed onto the path
     */
    protected void pushClassExpression(OWLClassExpression ce) {
        classExpressionPath.add(ce);
    }

    /**
     * Pops a class expression from the class expression path.  If the path
     * is empty then this method has no effect.
     */
    protected void popClassExpression() {
        if (!classExpressionPath.isEmpty()) {
            classExpressionPath.remove(classExpressionPath.size() - 1);
        }
    }

    /**
     * Gets the current data range path.  The current data range path is a list of data ranges
     * that represents the containing expressions for the current data ranges.  The first item in the path (list)
     * is the root data range that was visited.  For 0 < i < pathLength, the item at index i+1
     * is a direct sub-expression of the item at index i.  The last item in the path is the current data range
     * being visited.
     * @return A list of data ranges that represents the path of data ranges, with the root of
     *         the data range being the first element in the list.
     */
    public List<OWLDataRange> getDataRangePath() {
        return new ArrayList<OWLDataRange>(dataRangePath);
    }

    /**
     * Pushes a data range on to the data range path
     * @param dr The data range to be pushed onto the path
     */
    protected void pushDataRange(OWLDataRange dr) {
        dataRangePath.add(dr);
    }

    /**
     * Pops a data range from the data range expression path.  If the path
     * is empty then this method has no effect.
     */
    protected void popDataRange() {
        if (!dataRangePath.isEmpty()) {
            dataRangePath.remove(dataRangePath.size() - 1);
        }
    }


    private class StructureWalker implements OWLObjectVisitor {

        private Set<OWLObject> visited = new HashSet<OWLObject>();

        private void process(OWLObject object) {
            if (!visitDuplicates) {
                if (!visited.contains(object)) {
                    visited.add(object);
                    object.accept(visitor);
                }
            }
            else {
                object.accept(visitor);
            }
        }

        public void visit(IRI iri) {
            process(iri);
        }

        public void visit(OWLOntology ontology) {
            OWLObjectWalker.this.ontology = ontology;
            OWLObjectWalker.this.ax = null;
            process(ontology);
            for(OWLAnnotation anno : ontology.getAnnotations()) {
                anno.accept(this);
            }
            for (OWLAxiom a : ontology.getAxioms()) {
                a.accept(this);
            }
        }


        public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getProperty().accept(this);
        }


        public void visit(OWLClassAssertionAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getIndividual().accept(this);
            axiom.getClassExpression().accept(this);
        }


        public void visit(OWLDataPropertyAssertionAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getSubject().accept(this);
            axiom.getProperty().accept(this);
            axiom.getObject().accept(this);
        }


        public void visit(OWLDataPropertyDomainAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getDomain().accept(this);
            axiom.getProperty().accept(this);
        }


        public void visit(OWLDataPropertyRangeAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getRange().accept(this);
            axiom.getProperty().accept(this);
        }


        public void visit(OWLSubDataPropertyOfAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getSubProperty().accept(this);
            axiom.getSuperProperty().accept(this);
        }


        public void visit(OWLDeclarationAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getEntity().accept(this);
        }


        public void visit(OWLDifferentIndividualsAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            for (OWLIndividual ind : axiom.getIndividuals()) {
                ind.accept(this);
            }
        }


        public void visit(OWLDisjointClassesAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            for (OWLClassExpression desc : axiom.getClassExpressions()) {
                desc.accept(this);
            }
        }


        public void visit(OWLDisjointDataPropertiesAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                prop.accept(this);
            }
        }


        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                prop.accept(this);
            }
        }


        public void visit(OWLDisjointUnionAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getOWLClass().accept(this);
            for (OWLClassExpression desc : axiom.getClassExpressions()) {
                desc.accept(this);
            }
        }


        public void visit(OWLAnnotationAssertionAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getSubject().accept(this);
            axiom.getAnnotation().accept(this);
        }

        public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getProperty().accept(this);
            axiom.getDomain().accept(this);
        }

        public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getProperty().accept(this);
            axiom.getRange().accept(this);
        }

        public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getSubProperty().accept(this);
            axiom.getSuperProperty().accept(this);
        }

        public void visit(OWLAnnotation node) {
            process(node);
            annotation = node;
            node.getProperty().accept(this);
            node.getValue().accept(this);
        }

        public void visit(OWLEquivalentClassesAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            for (OWLClassExpression desc : axiom.getClassExpressions()) {
                desc.accept(this);
            }
        }


        public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                prop.accept(this);
            }
        }


        public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                prop.accept(this);
            }
        }


        public void visit(OWLFunctionalDataPropertyAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getProperty().accept(this);
        }


        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getProperty().accept(this);
        }

        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getProperty().accept(this);
        }


        public void visit(OWLInverseObjectPropertiesAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getFirstProperty().accept(this);
            axiom.getSecondProperty().accept(this);
        }


        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getProperty().accept(this);
        }


        public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getSubject().accept(this);
            axiom.getProperty().accept(this);
            axiom.getObject().accept(this);
        }


        public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getSubject().accept(this);
            axiom.getProperty().accept(this);
            axiom.getObject().accept(this);
        }


        public void visit(OWLObjectPropertyAssertionAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getSubject().accept(this);
            axiom.getProperty().accept(this);
            axiom.getObject().accept(this);
        }


        public void visit(OWLSubPropertyChainOfAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            for (OWLObjectPropertyExpression prop : axiom.getPropertyChain()) {
                prop.accept(this);
            }
            axiom.getSuperProperty().accept(this);
        }


        public void visit(OWLObjectPropertyDomainAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getDomain().accept(this);
            axiom.getProperty().accept(this);
        }


        public void visit(OWLObjectPropertyRangeAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getProperty().accept(this);
            axiom.getRange().accept(this);
        }


        public void visit(OWLSubObjectPropertyOfAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getSubProperty().accept(this);
            axiom.getSuperProperty().accept(this);
        }


        public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getProperty().accept(this);
        }


        public void visit(OWLSameIndividualAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            for (OWLIndividual ind : axiom.getIndividuals()) {
                ind.accept(this);
            }
        }


        public void visit(OWLSubClassOfAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            // -ve polarity
            axiom.getSubClass().accept(this);
            // +ve polarity
            axiom.getSuperClass().accept(this);
        }


        public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getProperty().accept(this);
        }


        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getProperty().accept(this);
        }


        public void visit(SWRLRule rule) {
            process(rule);
            OWLObjectWalker.this.ax = rule;
            for (SWRLAtom at : rule.getBody()) {
                at.accept(this);
            }
            for (SWRLAtom at : rule.getHead()) {
                at.accept(this);
            }
        }

        public void visit(OWLHasKeyAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getClassExpression().accept(this);
            for (OWLObjectPropertyExpression prop : axiom.getObjectPropertyExpressions()) {
                prop.accept(this);
            }
            for (OWLDataPropertyExpression prop : axiom.getDataPropertyExpressions()) {
                prop.accept(this);
            }
        }

        public void visit(OWLClass desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getIRI().accept(this);
            popClassExpression();
        }


        public void visit(OWLDataAllValuesFrom desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popClassExpression();
        }


        public void visit(OWLDataExactCardinality desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popClassExpression();
        }


        public void visit(OWLDataMaxCardinality desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popClassExpression();
        }


        public void visit(OWLDataMinCardinality desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popClassExpression();
        }


        public void visit(OWLDataSomeValuesFrom desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popClassExpression();
        }


        public void visit(OWLDataHasValue desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getValue().accept(this);
            popClassExpression();
        }


        public void visit(OWLObjectAllValuesFrom desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popClassExpression();
        }


        public void visit(OWLObjectComplementOf desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getOperand().accept(this);
            popClassExpression();
        }


        public void visit(OWLObjectExactCardinality desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popClassExpression();
        }


        public void visit(OWLObjectIntersectionOf desc) {
            pushClassExpression(desc);
            process(desc);

            for (OWLClassExpression op : desc.getOperands()) {
                op.accept(this);
            }
            popClassExpression();
        }


        public void visit(OWLObjectMaxCardinality desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popClassExpression();
        }


        public void visit(OWLObjectMinCardinality desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popClassExpression();
        }


        public void visit(OWLObjectOneOf desc) {
            pushClassExpression(desc);
            process(desc);
            for (OWLIndividual ind : desc.getIndividuals()) {
                ind.accept(this);
            }
            popClassExpression();
        }


        public void visit(OWLObjectHasSelf desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            popClassExpression();
        }


        public void visit(OWLObjectSomeValuesFrom desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popClassExpression();
        }


        public void visit(OWLObjectUnionOf desc) {
            pushClassExpression(desc);
            process(desc);
            for (OWLClassExpression op : desc.getOperands()) {
                op.accept(this);
            }
            popClassExpression();
        }


        public void visit(OWLObjectHasValue desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getValue().accept(this);
            popClassExpression();
        }


        public void visit(OWLDataComplementOf node) {
            pushDataRange(node);
            process(node);
            node.getDataRange().accept(this);
            popDataRange();
        }


        public void visit(OWLDataOneOf node) {
            pushDataRange(node);
            process(node);
            for (OWLLiteral con : node.getValues()) {
                con.accept(this);
            }
            popDataRange();
        }

        public void visit(OWLDataIntersectionOf node) {
            pushDataRange(node);
            process(node);
            for (OWLDataRange rng : node.getOperands()) {
                rng.accept(this);
            }
            popDataRange();
        }

        public void visit(OWLDataUnionOf node) {
            pushDataRange(node);
            process(node);
            for (OWLDataRange rng : node.getOperands()) {
                rng.accept(this);
            }
            popDataRange();
        }

        public void visit(OWLFacetRestriction node) {
            process(node);
            node.getFacetValue().accept(this);
        }


        public void visit(OWLDatatypeRestriction node) {
            pushDataRange(node);
            process(node);
            node.getDatatype().accept(this);
            for (OWLFacetRestriction fr : node.getFacetRestrictions()) {
                fr.accept(this);
            }
            popDataRange();
        }


        public void visit(OWLDatatype node) {
            pushDataRange(node);
            process(node);
            popDataRange();
        }

        public void visit(OWLLiteral node) {
            process(node);
            node.getDatatype().accept(this);
            popDataRange();
        }

        public void visit(OWLAnnotationProperty property) {
            process(property);
            property.getIRI().accept(this);
        }

        public void visit(OWLDataProperty property) {
            process(property);
            property.getIRI().accept(this);
        }


        public void visit(OWLObjectProperty property) {
            process(property);
            property.getIRI().accept(this);
        }


        public void visit(OWLObjectInverseOf property) {
            process(property);
            property.getInverse().accept(this);
        }


        public void visit(OWLNamedIndividual individual) {
            process(individual);
            individual.getIRI().accept(this);
        }

        public void visit(OWLAnonymousIndividual individual) {
            process(individual);
        }

        public void visit(SWRLLiteralArgument node) {
            process(node);
            node.getLiteral().accept(this);
        }


        public void visit(SWRLVariable node) {
            process(node);
        }


        public void visit(SWRLIndividualArgument node) {
            process(node);
            node.getIndividual().accept(this);
        }


        public void visit(SWRLBuiltInAtom node) {
            process(node);
            for (SWRLDArgument at : node.getArguments()) {
                at.accept(this);
            }
        }


        public void visit(SWRLClassAtom node) {
            process(node);
            node.getArgument().accept(this);
            node.getPredicate().accept(this);
        }


        public void visit(SWRLDataRangeAtom node) {
            process(node);
            node.getArgument().accept(this);
            node.getPredicate().accept(this);
        }


        public void visit(SWRLDataPropertyAtom node) {
            process(node);
            node.getPredicate().accept(this);
            node.getFirstArgument().accept(this);
            node.getSecondArgument().accept(this);
        }


        public void visit(SWRLDifferentIndividualsAtom node) {
            process(node);
            node.getFirstArgument().accept(this);
            node.getSecondArgument().accept(this);
        }


        public void visit(SWRLObjectPropertyAtom node) {
            process(node);
            node.getPredicate().accept(this);
            node.getFirstArgument().accept(this);
            node.getSecondArgument().accept(this);
        }


        public void visit(SWRLSameIndividualAtom node) {
            process(node);
            node.getFirstArgument().accept(this);
            node.getSecondArgument().accept(this);
        }


        public void visit(OWLDatatypeDefinitionAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.ax = axiom;
            axiom.getDatatype().accept(this);
            axiom.getDataRange().accept(this);
        }
    }
}
