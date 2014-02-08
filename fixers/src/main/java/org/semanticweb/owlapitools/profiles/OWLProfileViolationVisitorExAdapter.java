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

/** adapter class
 * 
 * @param <O>
 *            return type */
public class OWLProfileViolationVisitorExAdapter<O> implements
        OWLProfileViolationVisitorEx<O> {
    /** override this method in subclasses to change default behaviour
     * 
     * @param v
     *            violation
     * @return default return value */
    protected O doDefault(@SuppressWarnings("unused") OWLProfileViolation<?> v) {
        return defaultValue;
    }

    private O defaultValue;

    /** default returned value is null */
    public OWLProfileViolationVisitorExAdapter() {
        this(null);
    }

    /** @param o
     *            default return value */
    public OWLProfileViolationVisitorExAdapter(O o) {
        defaultValue = o;
    }

    @Override
    public O visit(IllegalPunning v) {
        return doDefault(v);
    }

    @Override
    public O visit(CycleInDatatypeDefinition v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfBuiltInDatatypeInDatatypeDefinition v) {
        return doDefault(v);
    }

    @Override
    public O visit(DatatypeIRIAlsoUsedAsClassIRI v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfNonSimplePropertyInCardinalityRestriction v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfNonSimplePropertyInDisjointPropertiesAxiom v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfNonSimplePropertyInFunctionalPropertyAxiom v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfNonSimplePropertyInIrreflexivePropertyAxiom v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfNonSimplePropertyInObjectHasSelf v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfPropertyInChainCausesCycle v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfReservedVocabularyForAnnotationPropertyIRI v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfReservedVocabularyForClassIRI v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfReservedVocabularyForDataPropertyIRI v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfReservedVocabularyForIndividualIRI v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfReservedVocabularyForObjectPropertyIRI v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfReservedVocabularyForOntologyIRI v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfReservedVocabularyForVersionIRI v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfUndeclaredAnnotationProperty v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfUndeclaredClass v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfUndeclaredDataProperty v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfUndeclaredDatatype v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfUndeclaredObjectProperty v) {
        return doDefault(v);
    }

    @Override
    public O visit(InsufficientPropertyExpressions v) {
        return doDefault(v);
    }

    @Override
    public O visit(InsufficientIndividuals v) {
        return doDefault(v);
    }

    @Override
    public O visit(InsufficientOperands v) {
        return doDefault(v);
    }

    @Override
    public O visit(EmptyOneOfAxiom v) {
        return doDefault(v);
    }

    @Override
    public O visit(LastPropertyInChainNotInImposedRange v) {
        return doDefault(v);
    }

    @Override
    public O visit(OntologyIRINotAbsolute v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfDefinedDatatypeInDatatypeRestriction v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfIllegalClassExpression v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfIllegalDataRange v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfUnknownDatatype v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfObjectPropertyInverse v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfNonSuperClassExpression v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfNonSubClassExpression v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfNonEquivalentClassExpression v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfNonAtomicClassExpression v) {
        return doDefault(v);
    }

    @Override
    public O visit(LexicalNotInLexicalSpace v) {
        return doDefault(v);
    }

    @Override
    public O visit(OntologyVersionIRINotAbsolute v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfAnonymousIndividual v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfIllegalAxiom v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfIllegalFacetRestriction v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfNonAbsoluteIRI v) {
        return doDefault(v);
    }
}
