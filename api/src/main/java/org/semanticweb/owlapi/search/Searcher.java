package org.semanticweb.owlapi.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitorEx;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.util.OWLEntityVisitorExAdapter;

public class Searcher {
    private class AxiomsRetriever<T extends OWLAxiom> implements
            OWLEntityVisitorEx<Collection<T>> {
        @Override
        public Collection visit(OWLClass e) {
            return o.getAxioms(e);
        }

        @Override
        public Collection visit(OWLObjectProperty e) {
            return o.getAxioms(e);
        }

        @Override
        public Collection visit(OWLDataProperty e) {
            return o.getAxioms(e);
        }

        @Override
        public Collection visit(OWLNamedIndividual e) {
            return o.getAxioms(e);
        }

        @Override
        public Collection visit(OWLDatatype e) {
            return o.getAxioms(e);
        }

        @Override
        public Collection visit(OWLAnnotationProperty e) {
            return o.getAxioms(e);
        }
    }

    private class SuperPropertyRetriever<T> extends
            OWLEntityVisitorExAdapter<Collection<T>> {
        @Override
        public Collection<T> visit(OWLObjectProperty e) {
            Collection<OWLObjectPropertyExpression> toReturn = new ArrayList<OWLObjectPropertyExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLSubObjectPropertyOfAxiom ax : ont
                        .getObjectSubPropertyAxiomsForSubProperty(e)) {
                    toReturn.add(ax.getSuperProperty());
                }
            }
            return (Collection<T>) toReturn;
        }

        @Override
        public Collection<T> visit(OWLDataProperty e) {
            Collection<OWLDataPropertyExpression> toReturn = new ArrayList<OWLDataPropertyExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLSubDataPropertyOfAxiom ax : ont
                        .getDataSubPropertyAxiomsForSubProperty(e)) {
                    toReturn.add(ax.getSuperProperty());
                }
            }
            return (Collection<T>) toReturn;
        }

        @Override
        public Collection<T> visit(OWLAnnotationProperty e) {
            Collection<OWLAnnotationProperty> toReturn = new ArrayList<OWLAnnotationProperty>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLSubAnnotationPropertyOfAxiom ax : ont
                        .getSubAnnotationPropertyOfAxioms(e)) {
                    toReturn.add(ax.getSuperProperty());
                }
            }
            return (Collection<T>) toReturn;
        }
    }

    private class SubPropertyRetriever<T> extends
            OWLEntityVisitorExAdapter<Collection<T>> {
        @Override
        public Collection<T> visit(OWLObjectProperty e) {
            Collection<OWLObjectPropertyExpression> toReturn = new ArrayList<OWLObjectPropertyExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLSubObjectPropertyOfAxiom ax : ont
                        .getObjectSubPropertyAxiomsForSuperProperty(e)) {
                    toReturn.add(ax.getSubProperty());
                }
            }
            return (Collection<T>) toReturn;
        }

        @Override
        public Collection<T> visit(OWLDataProperty e) {
            Collection<OWLDataPropertyExpression> toReturn = new ArrayList<OWLDataPropertyExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLSubDataPropertyOfAxiom ax : ont
                        .getDataSubPropertyAxiomsForSuperProperty(e)) {
                    toReturn.add(ax.getSubProperty());
                }
            }
            return (Collection<T>) toReturn;
        }

        @Override
        public Collection<T> visit(OWLAnnotationProperty e) {
            Collection<OWLAnnotationProperty> toReturn = new ArrayList<OWLAnnotationProperty>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLSubAnnotationPropertyOfAxiom ax : ont
                        .getAxioms(AxiomType.SUB_ANNOTATION_PROPERTY_OF)) {
                    if (ax.getSuperProperty().equals(e)) {
                        toReturn.add(ax.getSubProperty());
                    }
                }
            }
            return (Collection<T>) toReturn;
        }
    }

    private class EquivalentPropertyRetriever<T> extends
            OWLEntityVisitorExAdapter<Collection<T>> {
        @Override
        public Collection<T> visit(OWLObjectProperty e) {
            Collection<OWLObjectPropertyExpression> toReturn = new ArrayList<OWLObjectPropertyExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLEquivalentObjectPropertiesAxiom ax : ont
                        .getEquivalentObjectPropertiesAxioms(e)) {
                    toReturn.addAll(ax.getProperties());
                }
            }
            return (Collection<T>) toReturn;
        }

        @Override
        public Collection<T> visit(OWLDataProperty e) {
            Collection<OWLDataPropertyExpression> toReturn = new ArrayList<OWLDataPropertyExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLEquivalentDataPropertiesAxiom ax : ont
                        .getEquivalentDataPropertiesAxioms(e)) {
                    toReturn.addAll(ax.getProperties());
                }
            }
            return (Collection<T>) toReturn;
        }
    }

    enum Searches {
        CLASSES, PROPERTIES, ANNOTATIONPROPERTIES, AXIOMS, ENTITIES, DOMAINS, RANGES
    }

    enum Tasks {
        SEARCH, DESCRIBE
    }

    enum Direction {
        SUPER, SUB, EQUIVALENT
    }

    public static Searcher find() {
        return new Searcher().task(Tasks.SEARCH);
    }

    public static Searcher describe(OWLEntity c) {
        return find().task(Tasks.DESCRIBE).entity(c);
    }

    private OWLOntology o;
    private Searches search;
    private Tasks task;
    private AxiomType type;
    private OWLEntity entity;
    private Direction direction = Direction.SUPER;
    private boolean includeImports = true;

    Searcher task(Tasks t) {
        task = t;
        return this;
    }

    public Searcher excludeImports() {
        includeImports = false;
        return this;
    }

    public Searcher axiomsOfType(AxiomType type) {
        this.type = type;
        search = Searches.AXIOMS;
        return this;
    }

    public Searcher entity(OWLEntity e) {
        search = Searches.ENTITIES;
        entity = e;
        return this;
    }

    public Searcher classes() {
        search = Searches.CLASSES;
        return this;
    }

    public Searcher in(OWLOntology o) {
        this.o = o;
        return this;
    }

    public <T> Collection<T> asCollection(Class<T> clazz) {
        return asCollection();
    }

    public <T> Collection<T> asCollection() {
        // TODO add imports closure to all searches
        if (task == Tasks.DESCRIBE && search == Searches.ENTITIES) {
            AxiomsRetriever<OWLAxiom> retriever = new AxiomsRetriever<OWLAxiom>();
            return (Collection<T>) entity.accept(retriever);
        }
        if (search == Searches.ENTITIES) {
            if (entity == null) {
                return (Collection<T>) o.getSignature(includeImports);
            }
        }
        if (search == Searches.CLASSES) {
            if (entity == null) {
                return (Collection<T>) o.getClassesInSignature(includeImports);
            }
        }
        if (search == Searches.AXIOMS) {
            if (type == null) {
                return (Collection<T>) o.getAxioms();
            }
            return o.getAxioms(type, includeImports);
        }
        if (search == Searches.PROPERTIES) {
            if (direction == Direction.SUPER) {
                return entity.accept(new SuperPropertyRetriever<T>());
            }
            if (direction == Direction.SUB) {
                return entity.accept(new SubPropertyRetriever<T>());
            }
            if (direction == Direction.EQUIVALENT) {
                return entity.accept(new EquivalentPropertyRetriever<T>());
            }
        }
        if (search == Searches.DOMAINS) {
            Collection<OWLClassExpression> classes = new ArrayList<OWLClassExpression>();
            if (entity instanceof OWLObjectProperty) {
                for (OWLOntology ont : o.getImportsClosure()) {
                    for (OWLObjectPropertyDomainAxiom d : ont
                            .getObjectPropertyDomainAxioms((OWLObjectProperty) entity)) {
                        classes.add(d.getDomain());
                    }
                }
            }
            if (entity instanceof OWLDataProperty) {
                for (OWLOntology ont : o.getImportsClosure()) {
                    for (OWLDataPropertyDomainAxiom d : ont
                            .getDataPropertyDomainAxioms((OWLDataProperty) entity)) {
                        classes.add(d.getDomain());
                    }
                }
            }
            return (Collection) classes;
        }
        if (search == Searches.RANGES) {
            if (entity instanceof OWLObjectProperty) {
                Collection<OWLClassExpression> classes = new ArrayList<OWLClassExpression>();
                for (OWLOntology ont : o.getImportsClosure()) {
                    for (OWLObjectPropertyRangeAxiom d : ont
                            .getObjectPropertyRangeAxioms((OWLObjectProperty) entity)) {
                        classes.add(d.getRange());
                    }
                }
                return (Collection) classes;
            }
            if (entity instanceof OWLDataProperty) {
                Collection<OWLDataRange> classes = new ArrayList<OWLDataRange>();
                for (OWLOntology ont : o.getImportsClosure()) {
                    for (OWLDataPropertyRangeAxiom d : ont
                            .getDataPropertyRangeAxioms((OWLDataProperty) entity)) {
                        classes.add(d.getRange());
                    }
                }
                return (Collection) classes;
            }
        }
        return Collections.emptyList();
    }

    public Searcher sub() {
        direction = Direction.SUB;
        return this;
    }

    public Searcher sup() {
        direction = Direction.SUPER;
        return this;
    }

    public Searcher equivalent() {
        direction = Direction.EQUIVALENT;
        return this;
    }

    public Searcher propertiesOf(OWLProperty d) {
        search = Searches.PROPERTIES;
        entity = d;
        return this;
    }

    public Searcher domains(OWLProperty property) {
        search = Searches.DOMAINS;
        entity = property;
        return this;
    }

    public Searcher ranges(OWLProperty property) {
        search = Searches.RANGES;
        entity = property;
        return this;
    }
}
