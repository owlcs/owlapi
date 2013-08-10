package org.semanticweb.owlapi.api.test.searcher;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;
import static org.semanticweb.owlapi.search.Searcher.*;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

@SuppressWarnings("javadoc")
public class SearcherTestCase {
    @Test
    public void shouldSearch() throws OWLOntologyCreationException {
        // given
        OWLOntology o = Factory.getManager().createOntology();
        OWLClass c = Class(IRI("urn:c"));
        OWLClass d = Class(IRI("urn:d"));
        OWLAxiom ax = SubClassOf(c, d);
        o.getOWLOntologyManager().addAxiom(o, ax);
        assertTrue(find().axiomsOfType(AxiomType.SUBCLASS_OF).in(o).contains(ax));
        assertTrue(find().classes().in(o).contains(c));
        assertTrue(find().classes().in(o).contains(d));
        assertTrue(describe(c).in(o).contains(ax));
    }

    @Test
    public void shouldSearchObjectProperties() throws OWLOntologyCreationException {
        // given
        OWLOntology o = Factory.getManager().createOntology();
        OWLObjectProperty c = ObjectProperty(IRI("urn:c"));
        OWLObjectProperty d = ObjectProperty(IRI("urn:d"));
        OWLObjectProperty e = ObjectProperty(IRI("urn:e"));
        OWLClass x = Class(IRI("urn:x"));
        OWLClass y = Class(IRI("urn:Y"));
        OWLAxiom ax = SubObjectPropertyOf(c, d);
        OWLAxiom ax2 = ObjectPropertyDomain(c, x);
        OWLAxiom ax3 = ObjectPropertyRange(c, y);
        OWLAxiom ax4 = EquivalentObjectProperties(c, e);
        o.getOWLOntologyManager().addAxiom(o, ax);
        o.getOWLOntologyManager().addAxiom(o, ax2);
        o.getOWLOntologyManager().addAxiom(o, ax3);
        o.getOWLOntologyManager().addAxiom(o, ax4);
        assertTrue(find().axiomsOfType(AxiomType.SUB_OBJECT_PROPERTY).in(o).contains(ax));
        assertTrue(find().sub().propertiesOf(d).in(o).contains(c));
        assertTrue(find().sup().propertiesOf(c).in(o).contains(d));
        assertTrue(find().domains(c).in(o).contains(x));
        assertTrue(find().ranges(c).in(o).contains(y));
        assertTrue(find().equivalent().propertiesOf(c).in(o).contains(e));
    }

    @Test
    public void shouldSearchDataProperties() throws OWLOntologyCreationException {
        // given
        OWLOntology o = Factory.getManager().createOntology();
        OWLDataProperty c = DataProperty(IRI("urn:c"));
        OWLDataProperty d = DataProperty(IRI("urn:d"));
        OWLDataProperty e = DataProperty(IRI("urn:e"));
        OWLAxiom ax = SubDataPropertyOf(c, d);
        OWLClass x = Class(IRI("urn:x"));
        OWLAxiom ax2 = DataPropertyDomain(c, x);
        OWLAxiom ax3 = DataPropertyRange(c, Boolean());
        OWLAxiom ax4 = EquivalentDataProperties(c, e);
        o.getOWLOntologyManager().addAxiom(o, ax);
        o.getOWLOntologyManager().addAxiom(o, ax2);
        o.getOWLOntologyManager().addAxiom(o, ax3);
        o.getOWLOntologyManager().addAxiom(o, ax4);
        assertTrue(find().axiomsOfType(AxiomType.SUB_DATA_PROPERTY).in(o).contains(ax));
        assertTrue(find().sub().propertiesOf(d).in(o).contains(c));
        assertTrue(find().sup().propertiesOf(c).in(o).contains(d));
        assertTrue(find().domains(c).in(o).contains(x));
        assertTrue(find().ranges(c).in(o).contains(Boolean()));
        assertTrue(find().equivalent().propertiesOf(c).in(o).contains(e));
    }
    // Set<P> getDisjointProperties(OWLOntology ontology);
    //
    // Set<P> getDisjointProperties(Set<OWLOntology> ontologies);
    // Set<OWLAnnotationProperty> getSubProperties(OWLOntology ontology);
    //
    // Set<OWLAnnotationProperty> getSubProperties(OWLOntology ontology,
    // boolean includeImportsClosure);
    //
    // Set<OWLAnnotationProperty> getSubProperties(Set<OWLOntology> ontologies);
    //
    // Set<OWLAnnotationProperty> getSuperProperties(OWLOntology ontology);
    //
    // Set<OWLAnnotationProperty> getSuperProperties(OWLOntology ontology,
    // boolean includeImportsClosure);
    //
    // Set<OWLAnnotationProperty> getSuperProperties(Set<OWLOntology>
    // ontologies);
    // /** A convenience method that examines the axioms in the specified
    // ontology
    // * and return the class expressions corresponding to super classes of this
    // * class.
    // *
    // * @param ontology
    // * The ontology to be examined
    // * @return A {@code Set} of {@code OWLClassExpression}s that
    // * represent the superclasses of this class, which have been
    // * asserted in the specified ontology. */
    // Set<OWLClassExpression> getSuperClasses(OWLOntology ontology);
    //
    // /** A convenience method that examines the axioms in the specified
    // ontologies
    // * and returns the class expression corresponding to the asserted super
    // * classes of this class.
    // *
    // * @param ontologies
    // * The set of ontologies to be examined.
    // * @return A set of {@code OWLClassExpressions}s that represent the
    // * super classes of this class */
    // Set<OWLClassExpression> getSuperClasses(Set<OWLOntology> ontologies);
    //
    // /** Gets the classes which have been <i>asserted</i> to be subclasses of
    // this
    // * class in the specified ontology.
    // *
    // * @param ontology
    // * The ontology which should be examined for subclass axioms.
    // * @return A {@code Set} of {@code OWLClassExpression}s that
    // * represet the asserted subclasses of this class. */
    // Set<OWLClassExpression> getSubClasses(OWLOntology ontology);
    //
    // /** Gets the classes which have been <i>asserted</i> to be subclasses of
    // this
    // * class in the specified ontologies.
    // *
    // * @param ontologies
    // * The ontologies which should be examined for subclass axioms.
    // * @return A {@code Set} of {@code OWLClassExpression}s that
    // * represet the asserted subclasses of this class. */
    // Set<OWLClassExpression> getSubClasses(Set<OWLOntology> ontologies);
    //
    // /** A convenience method that examines the axioms in the specified
    // ontology
    // * and returns the class expressions corresponding to equivalent classes
    // of
    // * this class.
    // *
    // * @param ontology
    // * The ontology to be examined for axioms
    // * @return A {@code Set} of {@code OWLClassExpression}s that
    // * represent the equivalent classes of this class, that have been
    // * asserted in the specified ontology. */
    // Set<OWLClassExpression> getEquivalentClasses(OWLOntology ontology);
    //
    // /** A convenience method that examines the axioms in the specified
    // ontologies
    // * and returns the class expressions corresponding to equivalent classes
    // of
    // * this class.
    // *
    // * @param ontologies
    // * The ontologies to be examined for axioms
    // * @return A {@code Set} of {@code OWLClassExpression}s that
    // * represent the equivalent classes of this class, that have been
    // * asserted in the specified ontologies. */
    // Set<OWLClassExpression> getEquivalentClasses(Set<OWLOntology>
    // ontologies);
    //
    // /** Gets the classes which have been asserted to be disjoint with this
    // class
    // * by axioms in the specified ontology.
    // *
    // * @param ontology
    // * The ontology to search for disjoint class axioms
    // * @return A {@code Set} of {@code OWLClassExpression}s that
    // * represent the disjoint classes of this class. */
    // Set<OWLClassExpression> getDisjointClasses(OWLOntology ontology);
    //
    // /** Gets the classes which have been asserted to be disjoint with this
    // class
    // * by axioms in the specified ontologies.
    // *
    // * @param ontologies
    // * The ontologies to search for disjoint class axioms
    // * @return A {@code Set} of {@code OWLClassExpression}s that
    // * represent the disjoint classes of this class. */
    // Set<OWLClassExpression> getDisjointClasses(Set<OWLOntology> ontologies);
    //
    // /** Gets the individuals that have been asserted to be an instance of
    // this
    // * class by axioms in the specified ontology.
    // *
    // * @param ontology
    // * The ontology to be examined for class assertion axioms that
    // * assert an individual to be an instance of this class.
    // * @return A {@code Set} of {@code OWLIndividual}s that
    // represent
    // * the individual that have been asserted to be an instance of this
    // * class. */
    // Set<OWLIndividual> getIndividuals(OWLOntology ontology);
    //
    // /** Gets the individuals that have been asserted to be an instance of
    // this
    // * class by axioms in the speficied ontologies.
    // *
    // * @param ontologies
    // * The ontologies to be examined for class assertion axioms that
    // * assert an individual to be an instance of this class.
    // * @return A {@code Set} of {@code OWLIndividual}s that
    // represent
    // * the individual that have been asserted to be an instance of this
    // * class. */
    // Set<OWLIndividual> getIndividuals(Set<OWLOntology> ontologies);
    //
    // /** Determines if this class is a top level class in an
    // * {@link org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom} in the
    // * specified ontology.
    // *
    // * @param ontology
    // * The ontology to examine for axioms.
    // * @return {@code true} if {@code ontology} contains an
    // * {@code EquivalentClassesAxiom} where this class is a top
    // * level class in the axiom, other wise {@code false}. */
    // boolean isDefined(OWLOntology ontology);
    //
    // /** Determines if this class is a top level class in an
    // * {@link org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom} in at
    // * least one of the specified ontologies.
    // *
    // * @param ontologies
    // * The ontologies to examine for axioms.
    // * @return {@code true} if one or more of {@code ontologies}
    // * contains an {@code EquivalentClassesAxiom} where this class
    // * is a top level class in the axiom, other wise {@code false}. */
    // boolean isDefined(Set<OWLOntology> ontologies);
    //
    // /** Gets the annotations for this entity. These are deemed to be
    // annotations
    // * in annotation assertion axioms that have a subject that is an IRI that
    // is
    // * equal to the IRI of this entity.
    // *
    // * @param ontology
    // * The ontology to be examined for annotation assertion axioms
    // * @return The annotations that participate directly in an annotation
    // * assertion whose subject is an IRI corresponding to the IRI of
    // * this entity. */
    // Set<OWLAnnotation> getAnnotations(OWLOntology ontology);
    //
    // /** Obtains the annotations on this entity where the annotation has the
    // * specified annotation property.
    // *
    // * @param ontology
    // * The ontology to examine for annotation axioms
    // * @param annotationProperty
    // * The annotation property
    // * @return A set of {@code OWLAnnotation} objects that have the
    // * specified URI. */
    // Set<OWLAnnotation> getAnnotations(OWLOntology ontology,
    // OWLAnnotationProperty annotationProperty);
    //
    // /** @param ontology
    // * the ontology to use
    // * @return the annotation assertion axioms about this entity in the
    // provided
    // * ontology */
    // Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(OWLOntology
    // ontology);
    //
    // /** Gets the axioms in the specified ontology that contain this entity in
    // * their signature.
    // *
    // * @param ontology
    // * The ontology that will be searched for axioms
    // * @return The axioms in the specified ontology whose signature contains
    // * this entity. */
    // Set<OWLAxiom> getReferencingAxioms(OWLOntology ontology);
    //
    // /** Gets the axioms in the specified ontology and possibly its imports
    // * closure that contain this entity in their signature.
    // *
    // * @param ontology
    // * The ontology that will be searched for axioms
    // * @param includeImports
    // * If {@code true} then axioms in the imports closure will
    // * also be returned, if {@code false} then only the axioms
    // * in the specified ontology will be returned.
    // * @return The axioms in the specified ontology whose signature contains
    // * this entity. */
    // Set<OWLAxiom> getReferencingAxioms(OWLOntology ontology, boolean
    // includeImports);
    //
    // /** A convenience method, which gets the types of this individual, that
    // * correspond to the types asserted with axioms in the specified ontology.
    // *
    // * @param ontology
    // * The ontology that should be examined for class assertion
    // * axioms in order to get the types for this individual.
    // * @return A set of class expressions that correspond the asserted types
    // of
    // * this individual in the specified ontology. */
    // Set<OWLClassExpression> getTypes(OWLOntology ontology);
    //
    // /** A convenience method that gets the types of this individual by
    // examining
    // * the specified ontologies.
    // *
    // * @param ontologies
    // * The ontologies to be examined for class assertions
    // * @return A set of class expressions that represent the types of this
    // * individual as asserted in the specified ontologies. */
    // Set<OWLClassExpression> getTypes(Set<OWLOntology> ontologies);
    //
    // /** Gets the object property values for this individual.
    // *
    // * @param ontology
    // * The ontology to search for the property values.
    // * @return A map, which maps object properties to sets of individuals. */
    // Map<OWLObjectPropertyExpression, Set<OWLIndividual>>
    // getObjectPropertyValues(
    // OWLOntology ontology);
    //
    // /** Gets the asserted object property values for this individual and the
    // * specified property.
    // *
    // * @param ontology
    // * The ontology to be examined for axioms that assert property
    // * values for this individual
    // * @param property
    // * The property for which values will be returned.
    // * @return The set of individuals that are the values of this property.
    // More
    // * precisely, the set of individuals such that for each individual i
    // * in the set, is in a property assertion axiom property(this, i) is
    // * in the specified ontology. */
    // Set<OWLIndividual> getObjectPropertyValues(OWLObjectPropertyExpression
    // property,
    // OWLOntology ontology);
    //
    // /** Test whether a specific value for a specific object property on this
    // * individual has been asserted.
    // *
    // * @param property
    // * The property whose values will be examined
    // * @param individual
    // * The individual value of the property that will be tested for
    // * @param ontology
    // * The ontology to search for the property value
    // * @return {@code true} if the individual has the specified property
    // * value, that is, {@code true} if the specified ontology
    // * contains an object property assertion
    // * ObjectPropertyAssertion(property, this, individual), otherwise
    // * {@code false} */
    // boolean hasObjectPropertyValue(OWLObjectPropertyExpression property,
    // OWLIndividual individual, OWLOntology ontology);
    //
    // /** Test whether a specific value for a specific data property on this
    // * individual has been asserted.
    // *
    // * @param property
    // * The property whose values will be examined
    // * @param value
    // * The value value of the property that will be tested for
    // * @param ontology
    // * The ontology to search for the property value
    // * @return {@code true} if the individual has the specified property
    // * value, that is, {@code true} if the specified ontology
    // * contains a data property assertion
    // * DataPropertyAssertion(property, this, value), otherwise
    // * {@code false} */
    // boolean hasDataPropertyValue(OWLDataPropertyExpression property,
    // OWLLiteral value,
    // OWLOntology ontology);
    //
    // /** Test whether a specific value for a specific object property has been
    // * asserted not to hold for this individual.
    // *
    // * @param property
    // * The property to test for
    // * @param individual
    // * The value to test for
    // * @param ontology
    // * The ontology to search for the assertion
    // * @return {@code true} if the specified property value has
    // explicitly
    // * been asserted not to hold, that is, {@code true} if the
    // * specified ontology contains a negative object property assertion
    // * NegativeObjectPropertyAssertion(property, this, individual),
    // * otherwise {@code false} */
    // boolean hasNegativeObjectPropertyValue(OWLObjectPropertyExpression
    // property,
    // OWLIndividual individual, OWLOntology ontology);
    //
    // /** Gets the object property values that are explicitly asserted NOT to
    // hold
    // * for this individual
    // *
    // * @param ontology
    // * The ontology that should be examined for axioms
    // * @return A map containing the negative object property values */
    // Map<OWLObjectPropertyExpression, Set<OWLIndividual>>
    // getNegativeObjectPropertyValues(
    // OWLOntology ontology);
    //
    // /** Gets the data property values for this individual
    // *
    // * @param ontology
    // * the ontology to check
    // * @return a map property->set of values */
    // Map<OWLDataPropertyExpression, Set<OWLLiteral>> getDataPropertyValues(
    // OWLOntology ontology);
    //
    // /** Gets the values that this individual has for a specific data property
    // *
    // * @param ontology
    // * The ontology to examine for property assertions
    // * @param property
    // * the property
    // * @return The values that this individual has for the specified property
    // in
    // * the specified ontology. This is the set of values such that each
    // * value LV in the set is in an axiom of the form
    // * DataPropertyAssertion(property, thisIndividual, LV) in the
    // * ontology specified by the ontology parameter. */
    // Set<OWLLiteral> getDataPropertyValues(OWLDataPropertyExpression property,
    // OWLOntology ontology);
    //
    // /** Gets the data property values that are explicitly asserted NOT to
    // hold
    // * for this individual
    // *
    // * @param ontology
    // * The ontology that should be examined for axioms
    // * @return A map containing the negative data property values */
    // Map<OWLDataPropertyExpression, Set<OWLLiteral>>
    // getNegativeDataPropertyValues(
    // OWLOntology ontology);
    //
    // /** Test whether a specific value for a specific data property has been
    // * asserted not to hold for this individual.
    // *
    // * @param property
    // * The property to test for
    // * @param literal
    // * The value to test for
    // * @param ontology
    // * The ontology to search for the assertion
    // * @return {@code true} if the specified property value has
    // explicitly
    // * been asserted not to hold, that is, {@code true} if the
    // * specified ontology contains a negative data property assertion
    // * NegativeDataPropertyAssertion(property, this, literal), otherwise
    // * {@code false} */
    // boolean hasNegativeDataPropertyValue(OWLDataPropertyExpression property,
    // OWLLiteral literal, OWLOntology ontology);
    //
    // /** @param ontology
    // * the ontology to use A convenience method that examines axioms
    // * in ontology to determine the individuals that are asserted to
    // * be the same as this individual.
    // * @return Individuals that have been asserted to be the same as this
    // * individual. */
    // Set<OWLIndividual> getSameIndividuals(OWLOntology ontology);
    //
    // /** A convenience method that examines axioms in the specified ontology
    // to
    // * determine the individuals that are asserted to be different to this
    // * individual.
    // *
    // * @param ontology
    // * @return the set of different individuals */
    // Set<OWLIndividual> getDifferentIndividuals(OWLOntology ontology);
    //
    // /** Determines if this property is functional in the specified ontology
    // *
    // * @param ontology
    // * The ontology to be tested for a functional property axiom.
    // * @return {@code true} if the specified ontology contains an axiom
    // * stating that the property is functional, other wise
    // * {@code false}. */
    // boolean isFunctional(OWLOntology ontology);
    //
    // /** Determines if the property is functional because there is an axiom in
    // one
    // * of the specified ontologies that assert this to be the case.
    // *
    // * @param ontologies
    // * The ontologies which will be searched for axioms which specify
    // * that this property is fuctional.
    // * @return {@code true} if the property is functional, or
    // * {@code false} if the property is not functional. */
    // boolean isFunctional(Set<OWLOntology> ontologies);
    //
    // /** Determines if the specified ontology specifies that this property is
    // * inverse functional.
    // *
    // * @param ontology
    // * The ontology to be tested for an inverse functional property
    // * axiom.
    // * @return {@code true} if the property is inverse functional, or
    // * {@code false} if the property is not inverse functional. */
    // boolean isInverseFunctional(OWLOntology ontology);
    //
    // /** @param ontologies
    // * the ontologies to check
    // * @return true if the property is defined as inverse functional */
    // boolean isInverseFunctional(Set<OWLOntology> ontologies);
    //
    // /** @param ontology
    // * the ontology to check
    // * @return true if symmetric */
    // boolean isSymmetric(OWLOntology ontology);
    //
    // /** @param ontologies
    // * the ontologies to check
    // * @return true if symmetric */
    // boolean isSymmetric(Set<OWLOntology> ontologies);
    //
    // /** @param ontology
    // * the ontology to check
    // * @return true if asymmetric */
    // boolean isAsymmetric(OWLOntology ontology);
    //
    // /** @param ontologies
    // * the ontologies to check
    // * @return true if asymmetric */
    // boolean isAsymmetric(Set<OWLOntology> ontologies);
    //
    // /** @param ontology
    // * the ontology to check
    // * @return true if reflexive */
    // boolean isReflexive(OWLOntology ontology);
    //
    // /** @param ontologies
    // * the ontologies to check
    // * @return true if reflexive */
    // boolean isReflexive(Set<OWLOntology> ontologies);
    //
    // /** @param ontology
    // * the ontology to check
    // * @return true if irreflexive */
    // boolean isIrreflexive(OWLOntology ontology);
    //
    // /** @param ontologies
    // * the ontologies to check
    // * @return true if irreflexive */
    // boolean isIrreflexive(Set<OWLOntology> ontologies);
    //
    // /** @param ontology
    // * the ontology to check
    // * @return true if transitive */
    // boolean isTransitive(OWLOntology ontology);
    //
    // /** @param ontologies
    // * the ontologies to check
    // * @return true if transitive */
    // boolean isTransitive(Set<OWLOntology> ontologies);
    //
    // /** @param ontology
    // * the ontology to check
    // * @return the inverse properties */
    // Set<OWLObjectPropertyExpression> getInverses(OWLOntology ontology);
    //
    // /** @param ontologies
    // * the ontologies to check
    // * @return the inverse properties */
    // Set<OWLObjectPropertyExpression> getInverses(Set<OWLOntology>
    // ontologies);
    //
    // @Override
    // public boolean isFunctional(OWLOntology ontology) {
    // return ontology.getFunctionalObjectPropertyAxioms(this).size() > 0;
    // }
    //
    // @Override
    // public boolean isFunctional(Set<OWLOntology> ontologies) {
    // for (OWLOntology ont : ontologies) {
    // if (isFunctional(ont)) {
    // return true;
    // }
    // }
    // return false;
    // }
    //
    // @Override
    // public boolean isInverseFunctional(OWLOntology ontology) {
    // return
    // !ontology.getInverseFunctionalObjectPropertyAxioms(this).isEmpty();
    // }
    //
    // @Override
    // public boolean isInverseFunctional(Set<OWLOntology> ontologies) {
    // for (OWLOntology ont : ontologies) {
    // if (isInverseFunctional(ont)) {
    // return true;
    // }
    // }
    // return false;
    // }
    //
    // @Override
    // public boolean isSymmetric(OWLOntology ontology) {
    // return !ontology.getSymmetricObjectPropertyAxioms(this).isEmpty();
    // }
    //
    // @Override
    // public boolean isSymmetric(Set<OWLOntology> ontologies) {
    // for (OWLOntology ont : ontologies) {
    // if (isSymmetric(ont)) {
    // return true;
    // }
    // }
    // return false;
    // }
    //
    // @Override
    // public boolean isAsymmetric(OWLOntology ontology) {
    // return !ontology.getAsymmetricObjectPropertyAxioms(this).isEmpty();
    // }
    //
    // @Override
    // public boolean isAsymmetric(Set<OWLOntology> ontologies) {
    // for (OWLOntology ont : ontologies) {
    // if (isAsymmetric(ont)) {
    // return true;
    // }
    // }
    // return false;
    // }
    //
    // @Override
    // public boolean isReflexive(OWLOntology ontology) {
    // return !ontology.getReflexiveObjectPropertyAxioms(this).isEmpty();
    // }
    //
    // @Override
    // public boolean isReflexive(Set<OWLOntology> ontologies) {
    // for (OWLOntology ont : ontologies) {
    // if (isReflexive(ont)) {
    // return true;
    // }
    // }
    // return false;
    // }
    //
    // @Override
    // public boolean isIrreflexive(OWLOntology ontology) {
    // return !ontology.getIrreflexiveObjectPropertyAxioms(this).isEmpty();
    // }
    //
    // @Override
    // public boolean isIrreflexive(Set<OWLOntology> ontologies) {
    // for (OWLOntology ont : ontologies) {
    // if (isIrreflexive(ont)) {
    // return true;
    // }
    // }
    // return false;
    // }
    //
    // @Override
    // public boolean isTransitive(OWLOntology ontology) {
    // return !ontology.getTransitiveObjectPropertyAxioms(this).isEmpty();
    // }
    //
    // @Override
    // public boolean isTransitive(Set<OWLOntology> ontologies) {
    // for (OWLOntology ont : ontologies) {
    // if (isTransitive(ont)) {
    // return true;
    // }
    // }
    // return false;
    // }
    //
    //
    // @Override
    // public Set<OWLObjectPropertyExpression> getInverses(OWLOntology ontology)
    // {
    // Set<OWLObjectPropertyExpression> result = new
    // TreeSet<OWLObjectPropertyExpression>();
    // for (OWLInverseObjectPropertiesAxiom ax : ontology
    // .getInverseObjectPropertyAxioms(this)) {
    // if (ax.getFirstProperty().equals(this)) {
    // result.add(ax.getSecondProperty());
    // } else {
    // result.add(ax.getFirstProperty());
    // }
    // }
    // return result;
    // }
    //
    // @Override
    // public Set<OWLObjectPropertyExpression> getInverses(Set<OWLOntology>
    // ontologies) {
    // Set<OWLObjectPropertyExpression> result = new
    // TreeSet<OWLObjectPropertyExpression>();
    // for (OWLOntology ont : ontologies) {
    // result.addAll(getInverses(ont));
    // }
    // return result;
    // }
    // @Override
    // public Set<OWLAnnotation> getAnnotations(OWLOntology ontology) {
    // return ImplUtils.getAnnotations(this, Collections.singleton(ontology));
    // }
    //
    // @Override
    // public Set<OWLAnnotation> getAnnotations(OWLOntology ontology,
    // OWLAnnotationProperty annotationProperty) {
    // return ImplUtils.getAnnotations(this, annotationProperty,
    // Collections.singleton(ontology));
    // }
    //
    // @Override
    // public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(
    // OWLOntology ontology) {
    // return ImplUtils.getAnnotationAxioms(this,
    // Collections.singleton(ontology));
    // } @Override
    // public Set<OWLAxiom> getReferencingAxioms(OWLOntology ontology) {
    // return ontology.getReferencingAxioms(this);
    // }
    //
    // @Override
    // public Set<OWLAxiom>
    // getReferencingAxioms(OWLOntology ontology, boolean includeImports) {
    // return ontology.getReferencingAxioms(this, true);
    // }
    // @Override
    // public Set<OWLClassExpression> getTypes(OWLOntology ontology) {
    // Set<OWLClassExpression> result = new TreeSet<OWLClassExpression>();
    // for (OWLClassAssertionAxiom axiom :
    // ontology.getClassAssertionAxioms(this)) {
    // result.add(axiom.getClassExpression());
    // }
    // return result;
    // }
    //
    // @Override
    // public Set<OWLClassExpression> getTypes(Set<OWLOntology> ontologies) {
    // Set<OWLClassExpression> result = new TreeSet<OWLClassExpression>();
    // for (OWLOntology ont : ontologies) {
    // result.addAll(getTypes(ont));
    // }
    // return result;
    // }
    //
    // @Override
    // public Set<OWLIndividual> getObjectPropertyValues(
    // OWLObjectPropertyExpression property, OWLOntology ontology) {
    // Map<OWLObjectPropertyExpression, Set<OWLIndividual>> map =
    // getObjectPropertyValues(ontology);
    // Set<OWLIndividual> vals = map.get(property);
    // if (vals == null) {
    // return Collections.emptySet();
    // } else {
    // return new HashSet<OWLIndividual>(vals);
    // }
    // }
    //
    // @Override
    // public Set<OWLLiteral> getDataPropertyValues(OWLDataPropertyExpression
    // property,
    // OWLOntology ontology) {
    // Set<OWLLiteral> result = new HashSet<OWLLiteral>();
    // for (OWLDataPropertyAssertionAxiom ax : ontology
    // .getDataPropertyAssertionAxioms(this)) {
    // if (ax.getProperty().equals(property)) {
    // result.add(ax.getObject());
    // }
    // }
    // return result;
    // }
    //
    // @Override
    // public boolean hasObjectPropertyValue(OWLObjectPropertyExpression
    // property,
    // OWLIndividual individual, OWLOntology ontology) {
    // for (OWLObjectPropertyAssertionAxiom ax : ontology
    // .getObjectPropertyAssertionAxioms(this)) {
    // if (ax.getProperty().equals(property) &&
    // ax.getObject().equals(individual)) {
    // return true;
    // }
    // }
    // return false;
    // }
    //
    // @Override
    // public boolean hasDataPropertyValue(OWLDataPropertyExpression property,
    // OWLLiteral value, OWLOntology ontology) {
    // for (OWLDataPropertyAssertionAxiom ax : ontology
    // .getDataPropertyAssertionAxioms(this)) {
    // if (ax.getProperty().equals(property) && ax.getObject().equals(value)) {
    // return true;
    // }
    // }
    // return false;
    // }
    //
    // @Override
    // public Map<OWLObjectPropertyExpression, Set<OWLIndividual>>
    // getObjectPropertyValues(
    // OWLOntology ontology) {
    // Map<OWLObjectPropertyExpression, Set<OWLIndividual>> result = new
    // HashMap<OWLObjectPropertyExpression, Set<OWLIndividual>>();
    // for (OWLObjectPropertyAssertionAxiom ax : ontology
    // .getObjectPropertyAssertionAxioms(this)) {
    // Set<OWLIndividual> inds = result.get(ax.getProperty());
    // if (inds == null) {
    // inds = new TreeSet<OWLIndividual>();
    // result.put(ax.getProperty(), inds);
    // }
    // inds.add(ax.getObject());
    // }
    // return result;
    // }
    //
    // @Override
    // public Map<OWLObjectPropertyExpression, Set<OWLIndividual>>
    // getNegativeObjectPropertyValues(OWLOntology ontology) {
    // Map<OWLObjectPropertyExpression, Set<OWLIndividual>> result = new
    // HashMap<OWLObjectPropertyExpression, Set<OWLIndividual>>();
    // for (OWLNegativeObjectPropertyAssertionAxiom ax : ontology
    // .getNegativeObjectPropertyAssertionAxioms(this)) {
    // Set<OWLIndividual> inds = result.get(ax.getProperty());
    // if (inds == null) {
    // inds = new TreeSet<OWLIndividual>();
    // result.put(ax.getProperty(), inds);
    // }
    // inds.add(ax.getObject());
    // }
    // return result;
    // }
    //
    // @Override
    // public boolean hasNegativeObjectPropertyValue(OWLObjectPropertyExpression
    // property,
    // OWLIndividual individual, OWLOntology ontology) {
    // for (OWLNegativeObjectPropertyAssertionAxiom ax : ontology
    // .getNegativeObjectPropertyAssertionAxioms(this)) {
    // if (ax.getProperty().equals(property) &&
    // ax.getObject().equals(individual)) {
    // return true;
    // }
    // }
    // return false;
    // }
    //
    // @Override
    // public Map<OWLDataPropertyExpression, Set<OWLLiteral>>
    // getDataPropertyValues(
    // OWLOntology ontology) {
    // Map<OWLDataPropertyExpression, Set<OWLLiteral>> result = new
    // HashMap<OWLDataPropertyExpression, Set<OWLLiteral>>();
    // for (OWLDataPropertyAssertionAxiom ax : ontology
    // .getDataPropertyAssertionAxioms(this)) {
    // Set<OWLLiteral> vals = result.get(ax.getProperty());
    // if (vals == null) {
    // vals = new TreeSet<OWLLiteral>();
    // result.put(ax.getProperty(), vals);
    // }
    // vals.add(ax.getObject());
    // }
    // return result;
    // }
    //
    // @Override
    // public Map<OWLDataPropertyExpression, Set<OWLLiteral>>
    // getNegativeDataPropertyValues(
    // OWLOntology ontology) {
    // Map<OWLDataPropertyExpression, Set<OWLLiteral>> result = new
    // HashMap<OWLDataPropertyExpression, Set<OWLLiteral>>();
    // for (OWLNegativeDataPropertyAssertionAxiom ax : ontology
    // .getNegativeDataPropertyAssertionAxioms(this)) {
    // Set<OWLLiteral> inds = result.get(ax.getProperty());
    // if (inds == null) {
    // inds = new TreeSet<OWLLiteral>();
    // result.put(ax.getProperty(), inds);
    // }
    // inds.add(ax.getObject());
    // }
    // return result;
    // }
    //
    // @Override
    // public boolean hasNegativeDataPropertyValue(OWLDataPropertyExpression
    // property,
    // OWLLiteral literal, OWLOntology ontology) {
    // for (OWLNegativeDataPropertyAssertionAxiom ax : ontology
    // .getNegativeDataPropertyAssertionAxioms(this)) {
    // if (ax.getProperty().equals(property) && ax.getObject().equals(literal))
    // {
    // return true;
    // }
    // }
    // return false;
    // }
    //
    // @Override
    // public Set<OWLIndividual> getSameIndividuals(OWLOntology ontology) {
    // Set<OWLIndividual> result = new TreeSet<OWLIndividual>();
    // for (OWLSameIndividualAxiom ax : ontology.getSameIndividualAxioms(this))
    // {
    // result.addAll(ax.getIndividuals());
    // }
    // result.remove(this);
    // return result;
    // }
    //
    // @Override
    // public Set<OWLIndividual> getDifferentIndividuals(OWLOntology ontology) {
    // Set<OWLIndividual> result = new TreeSet<OWLIndividual>();
    // for (OWLDifferentIndividualsAxiom ax : ontology
    // .getDifferentIndividualAxioms(this)) {
    // result.addAll(ax.getIndividuals());
    // }
    // result.remove(this);
    // return result;
    // }
    // @Override
    // public Set<OWLClassExpression> getSuperClasses(OWLOntology ontology) {
    //
    // @Override
    // public Set<OWLClassExpression> getSubClasses(OWLOntology ontology) {
    //
    // @Override
    // public Set<OWLClassExpression> getEquivalentClasses(OWLOntology ontology)
    //
    // @Override
    // public Set<OWLClassExpression> getDisjointClasses(OWLOntology ontology) {
    //
    // @Override
    // public Set<OWLIndividual> getIndividuals(OWLOntology ontology) {
    // Set<OWLIndividual> result = new TreeSet<OWLIndividual>();
    // for (OWLClassAssertionAxiom ax : ontology.getClassAssertionAxioms(this))
    // {
    // result.add(ax.getIndividual());
    // }
    // return result;
    // }
    //
    // @Override
    // public Set<OWLIndividual> getIndividuals(Set<OWLOntology> ontologies) {
    // Set<OWLIndividual> result = new TreeSet<OWLIndividual>();
    // for (OWLOntology ont : ontologies) {
    // result.addAll(getIndividuals(ont));
    // }
    // return result;
    // }
    //
    // @Override
    // public Set<OWLAnnotation> getAnnotations(OWLOntology ontology) {
    // return ImplUtils.getAnnotations(this, Collections.singleton(ontology));
    // }
    //
    // @Override
    // public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(
    // OWLOntology ontology) {
    // return ImplUtils.getAnnotationAxioms(this,
    // Collections.singleton(ontology));
    // }
    //
    // @Override
    // public Set<OWLAnnotation> getAnnotations(OWLOntology ontology,
    // OWLAnnotationProperty annotationProperty) {
    // return ImplUtils.getAnnotations(this, annotationProperty,
    // Collections.singleton(ontology));
    // }
    //
    // @Override
    // public boolean isDefined(OWLOntology ontology) {
    // return !ontology.getEquivalentClassesAxioms(this).isEmpty();
    // }
    //
    // @Override
    // public boolean isDefined(Set<OWLOntology> ontologies) {
    // for (OWLOntology ont : ontologies) {
    // if (isDefined(ont)) {
    // return true;
    // }
    // }
    // return false;
    // }
    // @Override
    // public boolean isFunctional(OWLOntology ontology) {
    // return ontology.getFunctionalDataPropertyAxioms(this).size() > 0;
    // }
    //
    // @Override
    // public boolean isFunctional(Set<OWLOntology> ontologies) {
    // for (OWLOntology ont : ontologies) {
    // if (isFunctional(ont)) {
    // return true;
    // }
    // }
    // return false;
    // }
    // @Override
    // public Set<OWLAxiom> getReferencingAxioms(OWLOntology ontology) {
    // return ontology.getReferencingAxioms(this);
    // }
    //
    // @Override
    // public Set<OWLAxiom>
    // getReferencingAxioms(OWLOntology ontology, boolean includeImports) {
    // return ontology.getReferencingAxioms(this, includeImports);
    // }
    //
    // @Override
    // public Set<OWLAnnotation> getAnnotations(OWLOntology ontology) {
    // return ImplUtils.getAnnotations(this, Collections.singleton(ontology));
    // }
    //
    // @Override
    // public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(
    // OWLOntology ontology) {
    // return ImplUtils.getAnnotationAxioms(this,
    // Collections.singleton(ontology));
    // }
    //
    // @Override
    // public Set<OWLAnnotation> getAnnotations(OWLOntology ontology,
    // OWLAnnotationProperty annotationProperty) {
    // return ImplUtils.getAnnotations(this, annotationProperty,
    // Collections.singleton(ontology));
    // }
    // @Override
    // public Set<OWLAnnotation> getAnnotations(OWLOntology ontology) {
    // return ImplUtils.getAnnotations(this, Collections.singleton(ontology));
    // }
    //
    // @Override
    // public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(
    // OWLOntology ontology) {
    // return ImplUtils.getAnnotationAxioms(this,
    // Collections.singleton(ontology));
    // }
    //
    // @Override
    // public Set<OWLAnnotation> getAnnotations(OWLOntology ontology,
    // OWLAnnotationProperty annotationProperty) {
    // return ImplUtils.getAnnotations(this, annotationProperty,
    // Collections.singleton(ontology));
    // }
    //
    // @Override
    // public Set<OWLAxiom> getReferencingAxioms(OWLOntology ontology) {
    // return ontology.getReferencingAxioms(this);
    // }
    //
    // @Override
    // public Set<OWLAxiom>
    // getReferencingAxioms(OWLOntology ontology, boolean includeImports) {
    // return ontology.getReferencingAxioms(this, includeImports);
    // }
    // @Override
    // public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(
    // OWLOntology ontology) {
    // return ImplUtils.getAnnotationAxioms(this,
    // Collections.singleton(ontology));
    // }
    //
    // @Override
    // public Set<OWLAnnotation> getAnnotations(OWLOntology ontology) {
    // return ImplUtils.getAnnotations(this, Collections.singleton(ontology));
    // }
    //
    // @Override
    // public Set<OWLAnnotation> getAnnotations(OWLOntology ontology,
    // OWLAnnotationProperty annotationProperty) {
    // return ImplUtils.getAnnotations(this, annotationProperty,
    // Collections.singleton(ontology));
    // }
    // @Override
    // public Set<OWLAxiom> getReferencingAxioms(OWLOntology ontology) {
    // return ontology.getReferencingAxioms(this);
    // }
    //
    // @Override
    // public Set<OWLAxiom>
    // getReferencingAxioms(OWLOntology ontology, boolean includeImports) {
    // return ontology.getReferencingAxioms(this, includeImports);
    // }
    // @Override
    // public Set<OWLAnnotation> getAnnotations(OWLOntology ontology) {
    // return ImplUtils.getAnnotations(this, Collections.singleton(ontology));
    // }
    //
    // @Override
    // public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(
    // OWLOntology ontology) {
    // return ImplUtils.getAnnotationAxioms(this,
    // Collections.singleton(ontology));
    // }
    //
    // @Override
    // public Set<OWLAnnotation> getAnnotations(OWLOntology ontology,
    // OWLAnnotationProperty annotationProperty) {
    // return ImplUtils.getAnnotations(this, annotationProperty,
    // Collections.singleton(ontology));
    // }
    //
    // @Override
    // public Set<OWLAxiom> getReferencingAxioms(OWLOntology ontology) {
    // return ontology.getReferencingAxioms(this);
    // }
    //
    // @Override
    // public Set<OWLAxiom>
    // getReferencingAxioms(OWLOntology ontology, boolean includeImports) {
    // return ontology.getReferencingAxioms(this, includeImports);
    // }
}
