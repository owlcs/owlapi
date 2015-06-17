package org.obolibrary.macro;

import static java.util.stream.Collectors.toSet;
import static org.obolibrary.obo2owl.Obo2OWLConstants.Obo2OWLVocabulary.*;
import static org.semanticweb.owlapi.search.EntitySearcher.getAnnotationObjects;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.obolibrary.obo2owl.Obo2OWLConstants;
import org.semanticweb.owlapi.manchestersyntax.renderer.ParserException;
import org.semanticweb.owlapi.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Empty abstract visitor for macro expansion. This class allows to minimize the
 * code in the actual visitors, as they only need to overwrite the relevant
 * methods.
 */
public abstract class AbstractMacroExpansionVisitor implements OWLAxiomVisitorEx<OWLAxiom> {

    static final Logger LOG = LoggerFactory.getLogger(AbstractMacroExpansionVisitor.class);
    static final Set<OWLAnnotation> EMPTY_ANNOTATIONS = Collections.emptySet();
    final OWLDataFactory df;
    final @Nonnull Map<IRI, String> expandAssertionToMap;
    protected final @Nonnull Map<IRI, String> expandExpressionMap;
    protected @Nonnull OWLDataVisitorEx<OWLDataRange> rangeVisitor;
    protected @Nonnull OWLClassExpressionVisitorEx<OWLClassExpression> classVisitor;
    protected ManchesterSyntaxTool manchesterSyntaxTool;

    /**
     * @return value for OIO isexpansion
     */
    public OWLAnnotationProperty getOIO_ISEXPANSION() {
        return OIO_ISEXPANSION;
    }

    final protected OWLAnnotationProperty OIO_ISEXPANSION;

    /**
     * @return value for expansion annotation
     */
    public OWLAnnotation getExpansionMarkerAnnotation() {
        return expansionMarkerAnnotation;
    }

    final protected OWLAnnotation expansionMarkerAnnotation;
    private boolean shouldAddExpansionMarker = false;

    @Override
    public OWLAxiom doDefault(Object o) {
        return (OWLAxiom) o;
    }

    protected AbstractMacroExpansionVisitor(OWLOntology ontology, boolean shouldAddExpansionMarker) {
        this(ontology);
        this.shouldAddExpansionMarker = shouldAddExpansionMarker;
    }

    /**
     * class expression visitor
     */
    public abstract class AbstractClassExpressionVisitorEx implements OWLClassExpressionVisitorEx<OWLClassExpression> {

        protected AbstractClassExpressionVisitorEx() {}

        @Override
        public OWLClassExpression visit(OWLObjectIntersectionOf ce) {
            return df.getOWLObjectIntersectionOf(asSet(ce.operands().map(o -> o.accept(this))));
        }

        @Override
        public OWLClassExpression visit(OWLObjectUnionOf ce) {
            return df.getOWLObjectUnionOf(asSet(ce.operands().map(o -> o.accept(this))));
        }

        @Override
        public OWLClassExpression visit(OWLObjectComplementOf ce) {
            return df.getOWLObjectComplementOf(ce.getOperand().accept(this));
        }

        @Override
        public OWLClassExpression visit(OWLObjectSomeValuesFrom ce) {
            OWLClassExpression filler = ce.getFiller();
            OWLObjectPropertyExpression p = ce.getProperty();
            OWLClassExpression result = null;
            if (p.isOWLObjectProperty()) {
                result = expandOWLObjSomeVal(filler, p);
            }
            if (result == null) {
                result = df.getOWLObjectSomeValuesFrom(ce.getProperty(), filler.accept(this));
            }
            return result;
        }

        protected abstract @Nullable OWLClassExpression expandOWLObjSomeVal(OWLClassExpression filler,
            OWLObjectPropertyExpression p);

        @Override
        public OWLClassExpression visit(OWLObjectHasValue ce) {
            OWLClassExpression result = null;
            OWLIndividual filler = ce.getFiller();
            OWLObjectPropertyExpression p = ce.getProperty();
            if (p.isOWLObjectProperty()) {
                result = expandOWLObjHasVal(ce, filler, p);
            }
            if (result == null) {
                result = df.getOWLObjectHasValue(ce.getProperty(), filler);
            }
            return result;
        }

        protected abstract @Nullable OWLClassExpression expandOWLObjHasVal(OWLObjectHasValue desc, OWLIndividual filler,
            OWLObjectPropertyExpression p);

        @Override
        public OWLClassExpression visit(OWLObjectAllValuesFrom ce) {
            return ce.getFiller().accept(this);
        }

        @Override
        public OWLClassExpression visit(OWLObjectMinCardinality ce) {
            OWLClassExpression filler = ce.getFiller().accept(this);
            return df.getOWLObjectMinCardinality(ce.getCardinality(), ce.getProperty(), filler);
        }

        @Override
        public OWLClassExpression visit(OWLObjectExactCardinality ce) {
            return ce.asIntersectionOfMinMax().accept(this);
        }

        @Override
        public OWLClassExpression visit(OWLObjectMaxCardinality ce) {
            OWLClassExpression filler = ce.getFiller().accept(this);
            return df.getOWLObjectMaxCardinality(ce.getCardinality(), ce.getProperty(), filler);
        }

        @Override
        public OWLClassExpression visit(OWLDataSomeValuesFrom ce) {
            OWLDataRange filler = ce.getFiller().accept(rangeVisitor);
            return df.getOWLDataSomeValuesFrom(ce.getProperty(), filler);
        }

        @Override
        public OWLClassExpression visit(OWLDataAllValuesFrom ce) {
            OWLDataRange filler = ce.getFiller().accept(rangeVisitor);
            return df.getOWLDataAllValuesFrom(ce.getProperty(), filler);
        }

        @Override
        public OWLClassExpression visit(OWLDataHasValue ce) {
            return ce.asSomeValuesFrom().accept(this);
        }

        @Override
        public OWLClassExpression visit(OWLDataExactCardinality ce) {
            return ce.asIntersectionOfMinMax().accept(this);
        }

        @Override
        public OWLClassExpression visit(OWLDataMaxCardinality ce) {
            int card = ce.getCardinality();
            OWLDataRange filler = ce.getFiller().accept(rangeVisitor);
            return df.getOWLDataMaxCardinality(card, ce.getProperty(), filler);
        }

        @Override
        public OWLClassExpression visit(OWLDataMinCardinality ce) {
            int card = ce.getCardinality();
            OWLDataRange filler = ce.getFiller().accept(rangeVisitor);
            return df.getOWLDataMinCardinality(card, ce.getProperty(), filler);
        }

        @Override
        public OWLClassExpression visit(OWLClass ce) {
            return ce;
        }

        @Override
        public OWLClassExpression visit(OWLObjectHasSelf ce) {
            return ce;
        }

        @Override
        public OWLClassExpression visit(OWLObjectOneOf ce) {
            return ce;
        }
    }

    /**
     * @param input
     *        ontology
     */
    public void rebuild(OWLOntology input) {
        manchesterSyntaxTool = new ManchesterSyntaxTool(input);
    }

    /**
     * @return Manchester syntax tool
     */
    public ManchesterSyntaxTool getTool() {
        return manchesterSyntaxTool;
    }

    protected AbstractMacroExpansionVisitor(OWLOntology o) {
        df = o.getOWLOntologyManager().getOWLDataFactory();
        expandExpressionMap = new HashMap<>();
        expandAssertionToMap = new HashMap<>();
        OWLAnnotationProperty ap424 = df.getOWLAnnotationProperty(IRI_IAO_0000424.getIRI());
        o.objectPropertiesInSignature().forEach(p -> {
            getAnnotationObjects(p, o.importsClosure(), ap424).forEach(a -> {
                OWLAnnotationValue v = a.getValue();
                if (v instanceof OWLLiteral) {
                    String str = ((OWLLiteral) v).getLiteral();
                    LOG.info("mapping {} to {}", p.getIRI(), str);
                    expandExpressionMap.put(p.getIRI(), str);
                }
            });
        });
        o.annotationPropertiesInSignature().forEach(p -> expandAssertions(o, p));
        OIO_ISEXPANSION = df.getOWLAnnotationProperty(IRI.create(Obo2OWLConstants.OIOVOCAB_IRI_PREFIX, "is_expansion"));
        expansionMarkerAnnotation = df.getOWLAnnotation(OIO_ISEXPANSION, df.getOWLLiteral(true));
    }

    protected void expandAssertions(OWLOntology o, OWLAnnotationProperty p) {
        OWLAnnotationProperty ap425 = df.getOWLAnnotationProperty(IRI_IAO_0000425.getIRI());
        getAnnotationObjects(p, o.importsClosure(), ap425).map(a -> a.getValue().asLiteral()).filter(v -> v.isPresent())
            .forEach(v -> {
                String str = v.get().getLiteral();
                LOG.info("assertion mapping {} to {}", p, str);
                expandAssertionToMap.put(p.getIRI(), str);
            });
    }

    protected @Nullable OWLClassExpression expandObject(Object filler, OWLObjectPropertyExpression p) {
        OWLClassExpression result = null;
        IRI iri = ((OWLObjectProperty) p).getIRI();
        IRI templateVal = null;
        if (expandExpressionMap.containsKey(iri)) {
            if (filler instanceof OWLObjectOneOf) {
                templateVal = valFromOneOf(filler);
            }
            if (filler instanceof OWLNamedObject) {
                templateVal = ((OWLNamedObject) filler).getIRI();
            }
            if (templateVal != null) {
                result = resultFromVal(iri, templateVal);
            }
        }
        return result;
    }

    protected @Nullable OWLClassExpression resultFromVal(IRI iri, IRI templateVal) {
        String tStr = expandExpressionMap.get(iri);
        String exStr = tStr.replace("?Y", manchesterSyntaxTool.getId(templateVal));
        try {
            return manchesterSyntaxTool.parseManchesterExpression(exStr);
        } catch (ParserException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    protected @Nullable IRI valFromOneOf(Object filler) {
        Set<OWLIndividual> inds = asSet(((OWLObjectOneOf) filler).individuals(), OWLIndividual.class);
        if (inds.size() == 1) {
            OWLIndividual ind = inds.iterator().next();
            if (ind instanceof OWLNamedIndividual) {
                return ((OWLNamedObject) ind).getIRI();
            }
        }
        return null;
    }

    // Conversion of non-class expressions to MacroExpansionVisitor
    @Override
    public OWLAxiom visit(OWLSubClassOfAxiom axiom) {
        OWLClassExpression subClass = axiom.getSubClass();
        OWLClassExpression newSubclass = subClass.accept(classVisitor);
        OWLClassExpression superClass = axiom.getSuperClass();
        OWLClassExpression newSuperclass = superClass.accept(classVisitor);
        if (subClass.equals(newSubclass) && superClass.equals(newSuperclass)) {
            return axiom;
        } else {
            return df.getOWLSubClassOfAxiom(newSubclass, newSuperclass, getAnnotationsWithOptionalExpansionMarker(
                axiom));
        }
    }

    /**
     * @param axiom
     *        annotation source
     * @return new set of annotations
     */
    public Set<OWLAnnotation> getAnnotationsWithOptionalExpansionMarker(OWLAxiom axiom) {
        Set<OWLAnnotation> annotations = axiom.annotations().collect(toSet());
        if (shouldAddExpansionMarker) {
            annotations.add(expansionMarkerAnnotation);
        }
        return annotations;
    }

    @Override
    public OWLAxiom visit(OWLDisjointClassesAxiom axiom) {
        Set<OWLClassExpression> ops = new HashSet<>();
        AtomicBoolean sawChange = new AtomicBoolean(false);
        axiom.classExpressions().forEach(op -> {
            OWLClassExpression newOp = op.accept(classVisitor);
            ops.add(newOp);
            if (!op.equals(newOp)) {
                sawChange.set(true);
            }
        });
        if (!sawChange.get()) {
            return axiom;
        }
        return df.getOWLDisjointClassesAxiom(ops, getAnnotationsWithOptionalExpansionMarker(axiom));
    }

    @Override
    public OWLAxiom visit(OWLDisjointUnionAxiom axiom) {
        Set<OWLClassExpression> newOps = new HashSet<>();
        AtomicBoolean sawChange = new AtomicBoolean(false);
        axiom.classExpressions().forEach(op -> {
            OWLClassExpression newOp = op.accept(classVisitor);
            newOps.add(newOp);
            if (!op.equals(newOp)) {
                sawChange.set(true);
            }
        });
        if (!sawChange.get()) {
            return axiom;
        }
        return df.getOWLDisjointUnionAxiom(axiom.getOWLClass(), newOps, getAnnotationsWithOptionalExpansionMarker(
            axiom));
    }

    @Override
    public OWLAxiom visit(OWLDataPropertyDomainAxiom axiom) {
        OWLClassExpression domain = axiom.getDomain();
        OWLClassExpression newDomain = domain.accept(classVisitor);
        if (domain.equals(newDomain)) {
            return axiom;
        }
        return df.getOWLDataPropertyDomainAxiom(axiom.getProperty(), newDomain,
            getAnnotationsWithOptionalExpansionMarker(axiom));
    }

    @Override
    public OWLAxiom visit(OWLObjectPropertyDomainAxiom axiom) {
        OWLClassExpression domain = axiom.getDomain();
        OWLClassExpression newDomain = domain.accept(classVisitor);
        if (domain.equals(newDomain)) {
            return axiom;
        }
        return df.getOWLObjectPropertyDomainAxiom(axiom.getProperty(), newDomain,
            getAnnotationsWithOptionalExpansionMarker(axiom));
    }

    @Override
    public OWLAxiom visit(OWLObjectPropertyRangeAxiom axiom) {
        OWLClassExpression range = axiom.getRange();
        OWLClassExpression newRange = range.accept(classVisitor);
        if (range.equals(newRange)) {
            return axiom;
        }
        return df.getOWLObjectPropertyRangeAxiom(axiom.getProperty(), newRange,
            getAnnotationsWithOptionalExpansionMarker(axiom));
    }

    @Override
    public OWLAxiom visit(OWLDataPropertyRangeAxiom axiom) {
        OWLDataRange range = axiom.getRange();
        OWLDataRange newRange = range.accept(rangeVisitor);
        if (range.equals(newRange)) {
            return axiom;
        }
        return df.getOWLDataPropertyRangeAxiom(axiom.getProperty(), newRange, getAnnotationsWithOptionalExpansionMarker(
            axiom));
    }

    @Override
    public OWLAxiom visit(OWLClassAssertionAxiom axiom) {
        OWLClassExpression classExpression = axiom.getClassExpression();
        if (classExpression.isAnonymous()) {
            OWLClassExpression newClassExpression = classExpression.accept(classVisitor);
            if (!classExpression.equals(newClassExpression)) {
                return df.getOWLClassAssertionAxiom(newClassExpression, axiom.getIndividual(),
                    getAnnotationsWithOptionalExpansionMarker(axiom));
            }
        }
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLEquivalentClassesAxiom axiom) {
        Set<OWLClassExpression> newExpressions = new HashSet<>();
        AtomicBoolean sawChange = new AtomicBoolean(false);
        axiom.classExpressions().forEach(op -> {
            OWLClassExpression newExpression = op.accept(classVisitor);
            newExpressions.add(newExpression);
            if (!op.equals(newExpression)) {
                sawChange.set(true);
            }
        });
        if (!sawChange.get()) {
            return axiom;
        }
        return df.getOWLEquivalentClassesAxiom(newExpressions, getAnnotationsWithOptionalExpansionMarker(axiom));
    }
}
