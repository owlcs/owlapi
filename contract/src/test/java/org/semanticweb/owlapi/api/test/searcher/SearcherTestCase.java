package org.semanticweb.owlapi.api.test.searcher;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;
import static org.semanticweb.owlapi.search.Searcher.*;

import java.util.Collection;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
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
        Collection<OWLAxiom> axioms = find().axiomsOfType(AxiomType.SUBCLASS_OF)
                .in(o).asCollection();
        assertTrue(axioms.contains(ax));
        Collection<OWLClass> classes = find().classes().in(o).asCollection();
        assertTrue(classes.contains(c));
        assertTrue(classes.contains(d));
        axioms = describe(c).in(o).asCollection();
        assertTrue(axioms.contains(ax));
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
        Collection<OWLAxiom> axioms = find().axiomsOfType(AxiomType.SUB_OBJECT_PROPERTY)
                .in(o)
                .asCollection();
        assertTrue(axioms.contains(ax));
        Collection<OWLObjectPropertyExpression> properties = find().sub().propertiesOf(d)
                .in(o).asCollection();
        assertTrue(properties.contains(c));
        properties = find().sup().propertiesOf(c).in(o).asCollection();
        assertTrue(properties.contains(d));
        assertTrue(find().domains(c).in(o).asCollection().contains(x));
        assertTrue(find().ranges(c).in(o).asCollection().contains(y));
        assertTrue(find().equivalent().propertiesOf(c).in(o).asCollection().contains(e));
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
        Collection<OWLAxiom> axioms = find().axiomsOfType(AxiomType.SUB_DATA_PROPERTY)
                .in(o).asCollection();
        assertTrue(axioms.contains(ax));
        Collection<OWLObjectPropertyExpression> properties = find().sub().propertiesOf(d)
                .in(o).asCollection();
        assertTrue(properties.contains(c));
        properties = find().sup().propertiesOf(c).in(o).asCollection();
        assertTrue(properties.contains(d));
        assertTrue(find().domains(c).in(o).asCollection().contains(x));
        assertTrue(find().ranges(c).in(o).asCollection().contains(Boolean()));
        assertTrue(find().equivalent().propertiesOf(c).in(o).asCollection().contains(e));
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
}
