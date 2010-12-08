package org.semanticweb.owlapi.util;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
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
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Feb-2008<br><br>
 * <p/>
 * Provides the object that is the subject of an axiom.
 */
public class AxiomSubjectProvider implements OWLAxiomVisitor {

    private OWLObject subject;

    public OWLObject getSubject(OWLAxiom axiom) {
        axiom.accept(this);
        return subject;
    }


    public void visit(OWLSubClassOfAxiom axiom) {
        subject = axiom.getSubClass();
    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        subject = axiom.getSubject();
    }


    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }

    private OWLClassExpression selectClassExpression(Set<OWLClassExpression> descs) {
        for (OWLClassExpression desc : descs) {
            if (!desc.isAnonymous()) {
                return desc;
            }
        }
        return descs.iterator().next();
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        subject = selectClassExpression(axiom.getClassExpressions());
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        subject = axiom.getProperty();
    }
    
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        subject = axiom.getProperty();
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        subject = axiom.getProperties().iterator().next();
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        subject = axiom.getSubject();
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        subject = axiom.getIndividuals().iterator().next();
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        subject = axiom.getProperties().iterator().next();
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        subject = axiom.getProperties().iterator().next();
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        subject = axiom.getProperty();
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        subject = axiom.getSubject();
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }


    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        subject = axiom.getSubProperty();
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        subject = axiom.getOWLClass();
    }


    public void visit(OWLDeclarationAxiom axiom) {
        subject = axiom.getEntity();
    }


    public void visit(OWLAnnotationAssertionAxiom axiom) {
        subject = axiom.getSubject();
    }

    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
        subject = axiom.getProperty();
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        subject = axiom.getProperties().iterator().next();
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        subject = axiom.getIndividual();
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        subject = selectClassExpression(axiom.getClassExpressions());
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        subject = axiom.getSubject();
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }


    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        subject = axiom.getSubProperty();
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }


    public void visit(OWLSameIndividualAxiom axiom) {
        subject = axiom.getIndividuals().iterator().next();
    }


    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        subject = axiom.getSuperProperty();
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        subject = axiom.getFirstProperty();
    }


    public void visit(SWRLRule rule) {
        subject = rule.getHead().iterator().next();
    }

    public void visit(OWLHasKeyAxiom axiom) {
        subject = axiom.getClassExpression();
    }

    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        subject = axiom.getProperty();
    }

    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        subject = axiom.getProperty();
    }

    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        subject = axiom.getSubProperty();
    }


    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        subject = axiom.getDataRange();
    }
}
