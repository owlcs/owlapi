package org.semanticweb.owlapi.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitorEx;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.util.OWLEntityVisitorExAdapter;

public class Searcher<T> implements Iterable<T> {
    enum Searches {
        CLASSES, PROPERTIES, ANNOTATIONPROPERTIES, AXIOMS, ENTITIES, DOMAINS, RANGES, ANNOTATIONAXIOMS, ANNOTATIONS, VALUES, NEGATIVEVALUES, INSTANCES, TYPES
    }

    enum Tasks {
        SEARCH, DESCRIBE
    }

    enum Direction {
        SUPER, SUB, EQUIVALENT, DISJOINT, DIFFERENT, SAME, INVERSE
    }

    enum Types {
        OBJECT, DATA, ANNOTATION
    }

    public static Searcher find() {
        return new Searcher().task(Tasks.SEARCH);
    }

    public static <T> Searcher<T> find(Class<T> c) {
        return new Searcher<T>().task(Tasks.SEARCH);
    }

    public static Searcher describe(OWLEntity c) {
        return find().task(Tasks.DESCRIBE).entity(c);
    }

    protected OWLOntology o;
    private Searches search;
    private Tasks task;
    private AxiomType axiomType;
    private OWLEntity entity;
    private Direction direction = Direction.SUPER;
    private boolean includeImports = true;
    private OWLClassExpression ce;
    private OWLEntity property;
    private OWLIndividual individual;
    private OWLObjectPropertyExpression objectProperty;
    private Types type;

    Searcher<T> task(Tasks t) {
        task = t;
        return this;
    }

    public Searcher<T> excludeImports() {
        includeImports = false;
        return this;
    }

    public Searcher<T> data() {
        type = Types.DATA;
        return this;
    }

    public Searcher<T> object() {
        type = Types.OBJECT;
        return this;
    }

    public Searcher<T> axiomsOfType(AxiomType type) {
        axiomType = type;
        search = Searches.AXIOMS;
        return this;
    }

    public Searcher<T> entity(OWLEntity e) {
        search = Searches.ENTITIES;
        entity = e;
        return this;
    }

    public Searcher<T> classes() {
        search = Searches.CLASSES;
        return this;
    }

    public Searcher<T> classes(OWLClass c) {
        search = Searches.ENTITIES;
        entity = c;
        return this;
    }

    public Searcher<T> annotationAxioms(OWLEntity c) {
        search = Searches.ANNOTATIONAXIOMS;
        entity = c;
        return this;
    }

    public Searcher<T> annotations(OWLEntity c) {
        search = Searches.ANNOTATIONS;
        entity = c;
        return this;
    }

    public Searcher<T> forProperty(OWLEntity p) {
        property = p;
        return this;
    }

    public Searcher<T> in(OWLOntology o) {
        this.o = o;
        return this;
    }

    public boolean contains(Object o) {
        return asCollection().contains(o);
    }

    public boolean isEmpty() {
        return asCollection().isEmpty();
    }

    public int size() {
        return asCollection().size();
    }

    // public Iterable<T> asIterable() {
    // return asCollection();
    // }
    //
    // public Iterable<T> asIterable(Class<T> clazz) {
    // return asCollection();
    // }
    public Collection<T> asCollection(Class<T> clazz) {
        return asCollection();
    }

    public Collection<T> asCollection() {
        if (search == Searches.TYPES) {
            Collection<OWLClassExpression> toReturn = new HashSet<OWLClassExpression>();
            for (OWLClassAssertionAxiom c : o.getClassAssertionAxioms(individual)) {
                toReturn.add(c.getClassExpression());
            }
            return (Collection<T>) toReturn;
        }
        if (search == Searches.INSTANCES) {
            Collection<OWLIndividual> toReturn = new HashSet<OWLIndividual>();
            for (OWLClassAssertionAxiom c : o.getClassAssertionAxioms(ce)) {
                toReturn.add(c.getIndividual());
            }
            return (Collection<T>) toReturn;
        }
        if (direction == Direction.INVERSE) {
            Collection<OWLObjectPropertyExpression> toReturn = new HashSet<OWLObjectPropertyExpression>();
            for (OWLInverseObjectPropertiesAxiom inverse : o
                    .getInverseObjectPropertyAxioms(objectProperty)) {
                if (inverse.getFirstProperty().equals(inverse.getSecondProperty())) {
                    toReturn.add(inverse.getFirstProperty());
                } else {
                    if (inverse.getFirstProperty().equals(objectProperty)) {
                        toReturn.add(inverse.getSecondProperty());
                    } else {
                        toReturn.add(inverse.getFirstProperty());
                    }
                }
            }
            return (Collection<T>) toReturn;
        }
        if (search == Searches.VALUES) {
            if (entity == null) {
                if (type == Types.DATA) {
                    return (Collection<T>) o.getDataPropertyAssertionAxioms(individual);
                }
                if (type == Types.OBJECT) {
                    return (Collection<T>) o.getObjectPropertyAssertionAxioms(individual);
                }
                Set<T> hashSet = new HashSet<T>(
                        (Collection<T>) o.getDataPropertyAssertionAxioms(individual));
                hashSet.addAll((Collection<T>) o
                        .getObjectPropertyAssertionAxioms(individual));
                return hashSet;
            }
            if (entity instanceof OWLDataProperty) {
                Set<OWLLiteral> set = new HashSet<OWLLiteral>();
                for (OWLDataPropertyAssertionAxiom ax : o
                        .getDataPropertyAssertionAxioms(individual)) {
                    if (ax.getProperty().equals(entity)) {
                        set.add(ax.getObject());
                    }
                }
                return (Collection<T>) set;
            }
            if (entity instanceof OWLObjectProperty) {
                Set<OWLIndividual> set = new HashSet<OWLIndividual>();
                for (OWLObjectPropertyAssertionAxiom ax : o
                        .getObjectPropertyAssertionAxioms(individual)) {
                    if (ax.getProperty().equals(entity)) {
                        set.add(ax.getObject());
                    }
                }
                return (Collection<T>) set;
            }
        }
        if (search == Searches.NEGATIVEVALUES) {
            if (entity == null) {
                if (type == Types.DATA) {
                    return (Collection<T>) o
                            .getNegativeDataPropertyAssertionAxioms(individual);
                }
                if (type == Types.OBJECT) {
                    return (Collection<T>) o
                            .getNegativeObjectPropertyAssertionAxioms(individual);
                }
                Set<T> hashSet = new HashSet<T>(
                        (Collection<T>) o
                                .getNegativeDataPropertyAssertionAxioms(individual));
                hashSet.addAll((Collection<T>) o
                        .getNegativeObjectPropertyAssertionAxioms(individual));
                return hashSet;
            }
            if (entity instanceof OWLDataProperty) {
                Set<OWLLiteral> set = new HashSet<OWLLiteral>();
                for (OWLNegativeDataPropertyAssertionAxiom ax : o
                        .getNegativeDataPropertyAssertionAxioms(individual)) {
                    if (ax.getProperty().equals(entity)) {
                        set.add(ax.getObject());
                    }
                }
                return (Collection<T>) set;
            }
            if (entity instanceof OWLObjectProperty) {
                Set<OWLIndividual> set = new HashSet<OWLIndividual>();
                for (OWLNegativeObjectPropertyAssertionAxiom ax : o
                        .getNegativeObjectPropertyAssertionAxioms(individual)) {
                    if (ax.getProperty().equals(entity)) {
                        set.add(ax.getObject());
                    }
                }
                return (Collection<T>) set;
            }
        }
        // TODO add imports closure to all searches
        if (individual != null) {
            if (direction == Direction.SAME) {
                Set<OWLIndividual> toReturn = new HashSet<OWLIndividual>();
                for (OWLSameIndividualAxiom i : o.getSameIndividualAxioms(individual)) {
                    toReturn.addAll(i.getIndividuals());
                }
                toReturn.remove(individual);
                return (Collection<T>) toReturn;
            }
            if (direction == Direction.DIFFERENT) {
                Set<OWLIndividual> toReturn = new HashSet<OWLIndividual>();
                for (OWLDifferentIndividualsAxiom i : o
                        .getDifferentIndividualAxioms(individual)) {
                    toReturn.addAll(i.getIndividuals());
                }
                toReturn.remove(individual);
                return (Collection<T>) toReturn;
            }
        }
        if (task == Tasks.DESCRIBE && search == Searches.ENTITIES) {
            AxiomsRetriever<OWLAxiom> retriever = new AxiomsRetriever<OWLAxiom>();
            return (Collection<T>) entity.accept(retriever);
        }
        if (search == Searches.ANNOTATIONAXIOMS) {
            List<OWLAnnotationAssertionAxiom> annotationAssertionAxioms = new ArrayList<OWLAnnotationAssertionAxiom>(
                    o.getAnnotationAssertionAxioms(entity.getIRI()));
            if (property != null) {
                for (int i = 0; i < annotationAssertionAxioms.size();) {
                    if (!annotationAssertionAxioms.get(i).getProperty().equals(property)) {
                        annotationAssertionAxioms.remove(i);
                    } else {
                        i++;
                    }
                }
            }
            return (Collection<T>) annotationAssertionAxioms;
        }
        if (search == Searches.ANNOTATIONS) {
            if (property != null) {
                return (Collection<T>) getAnnotations(entity,
                        (OWLAnnotationProperty) property, o.getImportsClosure());
            }
            return (Collection<T>) getAnnotations(entity, o.getImportsClosure());
        }
        if (search == Searches.ENTITIES) {
            if (entity != null) {
                if (direction == Direction.SUPER) {
                    return entity.accept(new SupRetriever<T>());
                }
                if (direction == Direction.SUB) {
                    return entity.accept(new SubRetriever<T>());
                }
                if (direction == Direction.EQUIVALENT) {
                    return entity.accept(new EquivalentRetriever<T>());
                }
                if (direction == Direction.DISJOINT) {
                    return entity.accept(new DisjointRetriever<T>());
                }
            }
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
            if (axiomType == null) {
                return (Collection<T>) o.getAxioms();
            }
            return o.getAxioms(axiomType, includeImports);
        }
        if (search == Searches.PROPERTIES) {
            if (direction == Direction.SUPER) {
                return entity.accept(new SupRetriever<T>());
            }
            if (direction == Direction.SUB) {
                return entity.accept(new SubRetriever<T>());
            }
            if (direction == Direction.EQUIVALENT) {
                return entity.accept(new EquivalentRetriever<T>());
            }
            if (direction == Direction.DISJOINT) {
                return entity.accept(new DisjointRetriever<T>());
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

    public Searcher<T> sub() {
        direction = Direction.SUB;
        return this;
    }

    public Searcher<T> different(OWLIndividual i) {
        direction = Direction.DIFFERENT;
        individual = i;
        return this;
    }

    public Searcher<T> same(OWLIndividual i) {
        direction = Direction.SAME;
        individual = i;
        return this;
    }

    public Searcher<T> individual(OWLIndividual i) {
        individual = i;
        return this;
    }

    public Searcher<T> sup() {
        direction = Direction.SUPER;
        return this;
    }

    public Searcher<T> equivalent() {
        direction = Direction.EQUIVALENT;
        return this;
    }

    public Searcher<T> disjoint() {
        direction = Direction.DISJOINT;
        return this;
    }

    public Searcher<T> propertiesOf(OWLEntity d) {
        search = Searches.PROPERTIES;
        entity = d;
        return this;
    }

    public Searcher<T> domains(OWLEntity property) {
        search = Searches.DOMAINS;
        entity = property;
        return this;
    }

    public Searcher<T> ranges(OWLEntity property) {
        search = Searches.RANGES;
        entity = property;
        return this;
    }

    public Searcher<T> values(OWLEntity p) {
        entity = p;
        search = Searches.VALUES;
        return this;
    }

    public Searcher<T> types(OWLIndividual p) {
        individual = p;
        search = Searches.TYPES;
        return this;
    }

    public Searcher<T> individuals(OWLClassExpression p) {
        ce = p;
        search = Searches.INSTANCES;
        return this;
    }

    public Searcher<T> inverse(OWLObjectPropertyExpression p) {
        objectProperty = p;
        direction = Direction.INVERSE;
        return this;
    }

    public Searcher<T> negativeValues(OWLEntity p) {
        entity = p;
        search = Searches.NEGATIVEVALUES;
        return this;
    }

    public boolean isTransitive(OWLObjectProperty e) {
        return !o.getTransitiveObjectPropertyAxioms(e).isEmpty();
    }

    public boolean isSymmetric(OWLObjectProperty e) {
        return !o.getSymmetricObjectPropertyAxioms(e).isEmpty();
    }

    public boolean isAsymmetric(OWLObjectProperty e) {
        return !o.getAsymmetricObjectPropertyAxioms(e).isEmpty();
    }

    public boolean isReflexive(OWLObjectProperty e) {
        return !o.getReflexiveObjectPropertyAxioms(e).isEmpty();
    }

    public boolean isIrreflexive(OWLObjectProperty e) {
        return !o.getIrreflexiveObjectPropertyAxioms(e).isEmpty();
    }

    public boolean isInverseFunctional(OWLObjectProperty e) {
        return !o.getInverseFunctionalObjectPropertyAxioms(e).isEmpty();
    }

    public boolean isFunctional(OWLObjectProperty e) {
        return !o.getFunctionalObjectPropertyAxioms(e).isEmpty();
    }

    public boolean isFunctional(OWLDataProperty e) {
        return !o.getFunctionalDataPropertyAxioms(e).isEmpty();
    }

    public boolean isDefined(OWLClass c) {
        return !o.getEquivalentClassesAxioms(c).isEmpty();
    }

    /** @param entity
     *            entity to search
     * @param ontologies
     *            ontologis to search
     * @return annotations about entity */
    public static Set<OWLAnnotationAssertionAxiom> getAnnotationAxioms(OWLEntity entity,
            Set<OWLOntology> ontologies) {
        Set<OWLAnnotationAssertionAxiom> result = new HashSet<OWLAnnotationAssertionAxiom>();
        for (OWLOntology ont : ontologies) {
            result.addAll(ont.getAnnotationAssertionAxioms(entity.getIRI()));
        }
        return result;
    }

    /** @param entity
     *            entity to search
     * @param ontologies
     *            ontologies to search
     * @return annotations about entity */
    public static Set<OWLAnnotation> getAnnotations(OWLEntity entity,
            Set<OWLOntology> ontologies) {
        Set<OWLAnnotation> result = new HashSet<OWLAnnotation>();
        for (OWLAnnotationAssertionAxiom ax : getAnnotationAxioms(entity, ontologies)) {
            result.add(ax.getAnnotation());
        }
        return result;
    }

    /** @param entity
     *            entity to search
     * @param annotationProperty
     *            annotation property to match
     * @param ontologies
     *            ontologies to search
     * @return annotations about entity whose annotation property is
     *         annotationProperty */
    public static Set<OWLAnnotation> getAnnotations(OWLEntity entity,
            OWLAnnotationProperty annotationProperty, Set<OWLOntology> ontologies) {
        Set<OWLAnnotation> result = new HashSet<OWLAnnotation>();
        for (OWLAnnotationAssertionAxiom ax : getAnnotationAxioms(entity, ontologies)) {
            if (ax.getAnnotation().getProperty().equals(annotationProperty)) {
                result.add(ax.getAnnotation());
            }
        }
        return result;
    }

    // @Override
    // public Set<OWLAnnotationProperty> getSubProperties(OWLOntology ontology)
    // {
    // return getSubProperties(Collections.singleton(ontology));
    // }
    //
    // @Override
    // public Set<OWLAnnotationProperty> getSubProperties(OWLOntology ontology,
    // boolean includeImportsClosure) {
    // if (includeImportsClosure) {
    // return getSubProperties(ontology.getImportsClosure());
    // } else {
    // return getSubProperties(Collections.singleton(ontology));
    // }
    // }
    //
    // @Override
    // public Set<OWLAnnotationProperty> getSubProperties(Set<OWLOntology>
    // ontologies) {
    // Set<OWLAnnotationProperty> result = new HashSet<OWLAnnotationProperty>();
    // for (OWLOntology ont : ontologies) {
    // for (OWLSubAnnotationPropertyOfAxiom ax : ont
    // .getAxioms(AxiomType.SUB_ANNOTATION_PROPERTY_OF)) {
    // if (ax.getSuperProperty().equals(this)) {
    // result.add(ax.getSubProperty());
    // }
    // }
    // }
    // return result;
    // }
    //
    // @Override
    // public Set<OWLAnnotationProperty> getSuperProperties(OWLOntology
    // ontology) {
    // return getSuperProperties(Collections.singleton(ontology));
    // }
    //
    // @Override
    // public Set<OWLAnnotationProperty> getSuperProperties(OWLOntology
    // ontology,
    // boolean includeImportsClosure) {
    // if (includeImportsClosure) {
    // return getSuperProperties(ontology.getImportsClosure());
    // } else {
    // return getSuperProperties(Collections.singleton(ontology));
    // }
    // }
    //
    // @Override
    // public Set<OWLAnnotationProperty> getSuperProperties(Set<OWLOntology>
    // ontologies) {
    // Set<OWLAnnotationProperty> result = new HashSet<OWLAnnotationProperty>();
    // for (OWLOntology ont : ontologies) {
    // for (OWLSubAnnotationPropertyOfAxiom ax : ont
    // .getAxioms(AxiomType.SUB_ANNOTATION_PROPERTY_OF)) {
    // if (ax.getSubProperty().equals(this)) {
    // result.add(ax.getSuperProperty());
    // }
    // }
    // }
    // return result;
    // }
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

    private class SupRetriever<T> extends OWLEntityVisitorExAdapter<Collection<T>> {
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

        @Override
        public Collection<T> visit(OWLClass desc) {
            // TODO same operation should be allowed on class expressions
            Collection<OWLClassExpression> toReturn = new ArrayList<OWLClassExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLSubClassOfAxiom ax : ont.getSubClassAxiomsForSubClass(desc)) {
                    toReturn.add(ax.getSuperClass());
                }
            }
            return (Collection<T>) toReturn;
        }
    }

    private class SubRetriever<T> extends OWLEntityVisitorExAdapter<Collection<T>> {
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

        @Override
        public Collection<T> visit(OWLClass desc) {
            // TODO same operation should be allowed on class expressions
            Collection<OWLClassExpression> toReturn = new ArrayList<OWLClassExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLSubClassOfAxiom ax : ont.getSubClassAxiomsForSuperClass(desc)) {
                    toReturn.add(ax.getSubClass());
                }
            }
            return (Collection<T>) toReturn;
        }
    }

    private class EquivalentRetriever<T> extends OWLEntityVisitorExAdapter<Collection<T>> {
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

        @Override
        public Collection<T> visit(OWLClass desc) {
            // TODO same operation should be allowed on class expressions
            Collection<OWLClassExpression> toReturn = new ArrayList<OWLClassExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLEquivalentClassesAxiom ax : ont.getEquivalentClassesAxioms(desc)) {
                    toReturn.addAll(ax.getClassExpressions());
                }
            }
            return (Collection<T>) toReturn;
        }
    }

    private class DisjointRetriever<T> extends OWLEntityVisitorExAdapter<Collection<T>> {
        @Override
        public Collection<T> visit(OWLObjectProperty e) {
            Collection<OWLObjectPropertyExpression> toReturn = new ArrayList<OWLObjectPropertyExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLDisjointObjectPropertiesAxiom ax : ont
                        .getDisjointObjectPropertiesAxioms(e)) {
                    toReturn.addAll(ax.getProperties());
                }
            }
            return (Collection<T>) toReturn;
        }

        @Override
        public Collection<T> visit(OWLDataProperty e) {
            Collection<OWLDataPropertyExpression> toReturn = new ArrayList<OWLDataPropertyExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLDisjointDataPropertiesAxiom ax : ont
                        .getDisjointDataPropertiesAxioms(e)) {
                    toReturn.addAll(ax.getProperties());
                }
            }
            return (Collection<T>) toReturn;
        }

        @Override
        public Collection<T> visit(OWLClass desc) {
            // TODO same operation should be allowed on class expressions
            Collection<OWLClassExpression> toReturn = new ArrayList<OWLClassExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLDisjointClassesAxiom ax : ont.getDisjointClassesAxioms(desc)) {
                    toReturn.addAll(ax.getClassExpressions());
                }
            }
            toReturn.remove(desc);
            return (Collection<T>) toReturn;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return asCollection().iterator();
    }
}
