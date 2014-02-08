package org.semanticweb.owlapitools.profiles;

import org.semanticweb.owlapitools.profiles.violations.CycleInDatatypeDefinition;
import org.semanticweb.owlapitools.profiles.violations.DatatypeIRIAlsoUsedAsClassIRI;
import org.semanticweb.owlapitools.profiles.violations.EmptyOneOfAxiom;
import org.semanticweb.owlapitools.profiles.violations.IllegalPunning;
import org.semanticweb.owlapitools.profiles.violations.InsufficientIndividuals;
import org.semanticweb.owlapitools.profiles.violations.InsufficientOperands;
import org.semanticweb.owlapitools.profiles.violations.InsufficientPropertyExpressions;
import org.semanticweb.owlapitools.profiles.violations.LastPropertyInChainNotInImposedRange;
import org.semanticweb.owlapitools.profiles.violations.LexicalNotInLexicalSpace;
import org.semanticweb.owlapitools.profiles.violations.OntologyIRINotAbsolute;
import org.semanticweb.owlapitools.profiles.violations.OntologyVersionIRINotAbsolute;
import org.semanticweb.owlapitools.profiles.violations.UseOfAnonymousIndividual;
import org.semanticweb.owlapitools.profiles.violations.UseOfBuiltInDatatypeInDatatypeDefinition;
import org.semanticweb.owlapitools.profiles.violations.UseOfDefinedDatatypeInDatatypeRestriction;
import org.semanticweb.owlapitools.profiles.violations.UseOfIllegalAxiom;
import org.semanticweb.owlapitools.profiles.violations.UseOfIllegalClassExpression;
import org.semanticweb.owlapitools.profiles.violations.UseOfIllegalDataRange;
import org.semanticweb.owlapitools.profiles.violations.UseOfIllegalFacetRestriction;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonAbsoluteIRI;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonAtomicClassExpression;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonEquivalentClassExpression;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonSimplePropertyInCardinalityRestriction;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonSimplePropertyInDisjointPropertiesAxiom;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonSimplePropertyInFunctionalPropertyAxiom;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonSimplePropertyInIrreflexivePropertyAxiom;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonSimplePropertyInObjectHasSelf;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonSubClassExpression;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonSuperClassExpression;
import org.semanticweb.owlapitools.profiles.violations.UseOfObjectPropertyInverse;
import org.semanticweb.owlapitools.profiles.violations.UseOfPropertyInChainCausesCycle;
import org.semanticweb.owlapitools.profiles.violations.UseOfReservedVocabularyForAnnotationPropertyIRI;
import org.semanticweb.owlapitools.profiles.violations.UseOfReservedVocabularyForClassIRI;
import org.semanticweb.owlapitools.profiles.violations.UseOfReservedVocabularyForDataPropertyIRI;
import org.semanticweb.owlapitools.profiles.violations.UseOfReservedVocabularyForIndividualIRI;
import org.semanticweb.owlapitools.profiles.violations.UseOfReservedVocabularyForObjectPropertyIRI;
import org.semanticweb.owlapitools.profiles.violations.UseOfReservedVocabularyForOntologyIRI;
import org.semanticweb.owlapitools.profiles.violations.UseOfReservedVocabularyForVersionIRI;
import org.semanticweb.owlapitools.profiles.violations.UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom;
import org.semanticweb.owlapitools.profiles.violations.UseOfUndeclaredAnnotationProperty;
import org.semanticweb.owlapitools.profiles.violations.UseOfUndeclaredClass;
import org.semanticweb.owlapitools.profiles.violations.UseOfUndeclaredDataProperty;
import org.semanticweb.owlapitools.profiles.violations.UseOfUndeclaredDatatype;
import org.semanticweb.owlapitools.profiles.violations.UseOfUndeclaredObjectProperty;
import org.semanticweb.owlapitools.profiles.violations.UseOfUnknownDatatype;

/** @author ignazio */
public class OWLProfileViolationVisitorAdapter implements OWLProfileViolationVisitor {
    /** override this method in subclasses to change default behaviour
     * 
     * @param v
     *            violation */
    protected void doDefault(@SuppressWarnings("unused") OWLProfileViolation<?> v) {}

    @Override
    public void visit(IllegalPunning v) {
        doDefault(v);
    }

    @Override
    public void visit(CycleInDatatypeDefinition v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfBuiltInDatatypeInDatatypeDefinition v) {
        doDefault(v);
    }

    @Override
    public void visit(DatatypeIRIAlsoUsedAsClassIRI v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfNonSimplePropertyInCardinalityRestriction v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfNonSimplePropertyInDisjointPropertiesAxiom v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfNonSimplePropertyInFunctionalPropertyAxiom v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfNonSimplePropertyInIrreflexivePropertyAxiom v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfNonSimplePropertyInObjectHasSelf v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfPropertyInChainCausesCycle v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfReservedVocabularyForAnnotationPropertyIRI v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfReservedVocabularyForClassIRI v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfReservedVocabularyForDataPropertyIRI v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfReservedVocabularyForIndividualIRI v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfReservedVocabularyForObjectPropertyIRI v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfReservedVocabularyForOntologyIRI v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfReservedVocabularyForVersionIRI v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfUndeclaredAnnotationProperty v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfUndeclaredClass v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfUndeclaredDataProperty v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfUndeclaredDatatype v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfUndeclaredObjectProperty v) {
        doDefault(v);
    }

    @Override
    public void visit(InsufficientPropertyExpressions v) {
        doDefault(v);
    }

    @Override
    public void visit(InsufficientIndividuals v) {
        doDefault(v);
    }

    @Override
    public void visit(InsufficientOperands v) {
        doDefault(v);
    }

    @Override
    public void visit(EmptyOneOfAxiom v) {
        doDefault(v);
    }

    @Override
    public void visit(LastPropertyInChainNotInImposedRange v) {
        doDefault(v);
    }

    @Override
    public void visit(OntologyIRINotAbsolute v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfDefinedDatatypeInDatatypeRestriction v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfIllegalClassExpression v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfIllegalDataRange v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfUnknownDatatype v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfObjectPropertyInverse v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfNonSuperClassExpression v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfNonSubClassExpression v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfNonEquivalentClassExpression v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfNonAtomicClassExpression v) {
        doDefault(v);
    }

    @Override
    public void visit(LexicalNotInLexicalSpace v) {
        doDefault(v);
    }

    @Override
    public void visit(OntologyVersionIRINotAbsolute v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfAnonymousIndividual v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfIllegalAxiom v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfIllegalFacetRestriction v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfNonAbsoluteIRI v) {
        doDefault(v);
    }
}
