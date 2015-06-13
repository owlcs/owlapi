package org.obolibrary.macro;

import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.search.Searcher.*;

import java.util.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.obolibrary.obo2owl.Obo2OWLConstants;
import org.obolibrary.obo2owl.Obo2OWLConstants.Obo2OWLVocabulary;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLDataVisitorExAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Empty abstract visitor for macro expansion. This class allows to minimize the
 * code in the actual visitors, as they only need to overwrite the relevant
 * methods.
 */
public abstract class AbstractMacroExpansionVisitor extends OWLDataVisitorExAdapter<OWLDataRange>implements
    OWLClassExpressionVisitorEx<OWLClassExpression>, OWLDataVisitorEx<OWLDataRange>, OWLAxiomVisitorEx<OWLAxiom> {

    static final Logger LOG = LoggerFactory.getLogger(AbstractMacroExpansionVisitor.class);
    static final Set<OWLAnnotation> EMPTY_ANNOTATIONS = Collections.emptySet();
    final OWLDataFactory dataFactory;
    @Nonnull
    final Map<IRI, String> expandAssertionToMap;
    @Nonnull
    final Map<IRI, String> expandExpressionMap;

    /**
     * @return is expansion property
     */
    public OWLAnnotationProperty getOIO_ISEXPANSION() {
        return OIO_ISEXPANSION;
    }

    final protected OWLAnnotationProperty OIO_ISEXPANSION;

    /**
     * @return expansion annotation
     */
    public OWLAnnotation getExpansionMarkerAnnotation() {
        return expansionMarkerAnnotation;
    }

    final protected OWLAnnotation expansionMarkerAnnotation;
    private boolean shouldAddExpansionMarker = false;

    protected AbstractMacroExpansionVisitor(OWLOntology ontology, boolean shouldAddExpansionMarker) {
        this(ontology);
        this.shouldAddExpansionMarker = shouldAddExpansionMarker;
    }

    @SuppressWarnings("null")
    protected AbstractMacroExpansionVisitor(OWLOntology inputOntology) {
        super(null);
        dataFactory = inputOntology.getOWLOntologyManager().getOWLDataFactory();
        expandExpressionMap = new HashMap<>();
        expandAssertionToMap = new HashMap<>();
        OWLAnnotationProperty expandExpressionAP = dataFactory.getOWLAnnotationProperty(
            Obo2OWLVocabulary.IRI_IAO_0000424.getIRI());
        OWLAnnotationProperty expandAssertionAP = dataFactory.getOWLAnnotationProperty(Obo2OWLVocabulary.IRI_IAO_0000425
            .getIRI());
        OIO_ISEXPANSION = dataFactory.getOWLAnnotationProperty(IRI.create(Obo2OWLConstants.OIOVOCAB_IRI_PREFIX,
            "is_expansion"));
        expansionMarkerAnnotation = dataFactory.getOWLAnnotation(OIO_ISEXPANSION, dataFactory.getOWLLiteral(true));
        for (OWLObjectProperty p : inputOntology.getObjectPropertiesInSignature()) {
            for (OWLOntology o : inputOntology.getImportsClosure()) {
                for (OWLAnnotation a : annotationObjects(o.getAnnotationAssertionAxioms(p.getIRI()), expandExpressionAP)) {
                    OWLAnnotationValue v = a.getValue();
                    if (v instanceof OWLLiteral) {
                        String str = ((OWLLiteral) v).getLiteral();
                        LOG.info("mapping {} to {}", p, str);
                        expandExpressionMap.put(p.getIRI(), str);
                    }
                }
            }
        }
        for (OWLAnnotationProperty p : inputOntology.getAnnotationPropertiesInSignature(EXCLUDED)) {
            for (OWLOntology o : inputOntology.getImportsClosure()) {
                for (OWLAnnotation a : annotationObjects(o.getAnnotationAssertionAxioms(p.getIRI()), expandAssertionAP)) {
                    OWLAnnotationValue v = a.getValue();
                    if (v instanceof OWLLiteral) {
                        String str = ((OWLLiteral) v).getLiteral();
                        LOG.info("assertion mapping {} to {}", p, str);
                        expandAssertionToMap.put(p.getIRI(), str);
                    }
                }
            }
        }
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLObjectIntersectionOf ce) {
        Set<OWLClassExpression> ops = new HashSet<>();
        for (OWLClassExpression op : ce.getOperands()) {
            ops.add(op.accept(this));
        }
        return dataFactory.getOWLObjectIntersectionOf(ops);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLObjectUnionOf ce) {
        Set<OWLClassExpression> ops = new HashSet<>();
        for (OWLClassExpression op : ce.getOperands()) {
            ops.add(op.accept(this));
        }
        return dataFactory.getOWLObjectUnionOf(ops);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLObjectComplementOf ce) {
        return dataFactory.getOWLObjectComplementOf(ce.getOperand().accept(this));
    }

    @Nonnull
    @Override
    public OWLClassExpression visit(@Nonnull OWLObjectSomeValuesFrom ce) {
        OWLClassExpression filler = ce.getFiller();
        OWLObjectPropertyExpression p = ce.getProperty();
        OWLClassExpression result = null;
        if (p instanceof OWLObjectProperty) {
            result = expandOWLObjSomeVal(filler, p);
        }
        if (result == null) {
            result = dataFactory.getOWLObjectSomeValuesFrom(ce.getProperty(), filler.accept(this));
        }
        return result;
    }

    @Nullable
    protected abstract OWLClassExpression expandOWLObjSomeVal(@Nonnull OWLClassExpression filler,
        @Nonnull OWLObjectPropertyExpression p);

    @Nonnull
    @Override
    public OWLClassExpression visit(@Nonnull OWLObjectHasValue ce) {
        OWLClassExpression result = null;
        OWLIndividual filler = ce.getFiller();
        OWLObjectPropertyExpression p = ce.getProperty();
        if (p instanceof OWLObjectProperty) {
            result = expandOWLObjHasVal(ce, filler, p);
        }
        if (result == null) {
            result = dataFactory.getOWLObjectHasValue(ce.getProperty(), filler);
        }
        return result;
    }

    @Nullable
    protected abstract OWLClassExpression expandOWLObjHasVal(@Nonnull OWLObjectHasValue desc,
        @Nonnull OWLIndividual filler, @Nonnull OWLObjectPropertyExpression p);

    @Override
    public OWLClassExpression visit(@Nonnull OWLObjectAllValuesFrom ce) {
        return ce.getFiller().accept(this);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLObjectMinCardinality ce) {
        OWLClassExpression filler = ce.getFiller().accept(this);
        return dataFactory.getOWLObjectMinCardinality(ce.getCardinality(), ce.getProperty(), filler);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLObjectExactCardinality ce) {
        return ce.asIntersectionOfMinMax().accept(this);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLObjectMaxCardinality ce) {
        OWLClassExpression filler = ce.getFiller().accept(this);
        return dataFactory.getOWLObjectMaxCardinality(ce.getCardinality(), ce.getProperty(), filler);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLDataSomeValuesFrom ce) {
        OWLDataRange filler = ce.getFiller().accept(this);
        return dataFactory.getOWLDataSomeValuesFrom(ce.getProperty(), filler);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLDataAllValuesFrom ce) {
        OWLDataRange filler = ce.getFiller().accept(this);
        return dataFactory.getOWLDataAllValuesFrom(ce.getProperty(), filler);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLDataHasValue ce) {
        return ce.asSomeValuesFrom().accept(this);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLDataExactCardinality ce) {
        return ce.asIntersectionOfMinMax().accept(this);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLDataMaxCardinality ce) {
        int card = ce.getCardinality();
        OWLDataRange filler = ce.getFiller().accept(this);
        return dataFactory.getOWLDataMaxCardinality(card, ce.getProperty(), filler);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLDataMinCardinality ce) {
        int card = ce.getCardinality();
        OWLDataRange filler = ce.getFiller().accept(this);
        return dataFactory.getOWLDataMinCardinality(card, ce.getProperty(), filler);
    }

    @Override
    public OWLDataRange visit(@Nonnull OWLDataOneOf node) {
        // Encode as a data union of and return result
        Set<OWLDataOneOf> oneOfs = new HashSet<>();
        for (OWLLiteral lit : node.getValues()) {
            oneOfs.add(dataFactory.getOWLDataOneOf(lit));
        }
        return dataFactory.getOWLDataUnionOf(oneOfs).accept(this);
    }

    @Override
    public OWLDataRange visit(@Nonnull OWLDataIntersectionOf node) {
        Set<OWLDataRange> ops = new HashSet<>();
        for (OWLDataRange op : node.getOperands()) {
            ops.add(op.accept(this));
        }
        return dataFactory.getOWLDataIntersectionOf(ops);
    }

    @Override
    public OWLDataRange visit(@Nonnull OWLDataUnionOf node) {
        Set<OWLDataRange> ops = new HashSet<>();
        for (OWLDataRange op : node.getOperands()) {
            ops.add(op.accept(this));
        }
        return dataFactory.getOWLDataUnionOf(ops);
    }

    // Conversion of non-class expressions to MacroExpansionVisitor
    @Override
    public OWLAxiom visit(@Nonnull OWLSubClassOfAxiom axiom) {
        OWLClassExpression subClass = axiom.getSubClass();
        OWLClassExpression newSubclass = subClass.accept(this);
        OWLClassExpression superClass = axiom.getSuperClass();
        OWLClassExpression newSuperclass = superClass.accept(this);
        if (subClass.equals(newSubclass) && superClass.equals(newSuperclass)) {
            return axiom;
        } else {
            return dataFactory.getOWLSubClassOfAxiom(newSubclass, newSuperclass,
                getAnnotationsWithOptionalExpansionMarker(axiom));
        }
    }

    /**
     * @param axiom
     *        axiom providing annotations
     * @return annotations
     */
    @Nonnull
    public Set<OWLAnnotation> getAnnotationsWithOptionalExpansionMarker(OWLAxiom axiom) {
        if (shouldAddExpansionMarker) {
            Set<OWLAnnotation> annotations = new LinkedHashSet<>(axiom.getAnnotations());
            annotations.add(expansionMarkerAnnotation);
            return annotations;
        } else {
            return axiom.getAnnotations();
        }
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLDisjointClassesAxiom axiom) {
        Set<OWLClassExpression> ops = new HashSet<>();
        boolean sawChange = false;
        for (OWLClassExpression op : axiom.getClassExpressions()) {
            OWLClassExpression newOp = op.accept(this);
            ops.add(newOp);
            if (!op.equals(newOp)) {
                sawChange = true;
            }
        }
        if (sawChange) {
            return dataFactory.getOWLDisjointClassesAxiom(ops, getAnnotationsWithOptionalExpansionMarker(axiom));
        } else {
            return axiom;
        }
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLDisjointUnionAxiom axiom) {
        Set<OWLClassExpression> newOps = new HashSet<>();
        boolean sawChange = false;
        for (OWLClassExpression op : axiom.getClassExpressions()) {
            OWLClassExpression newOp = op.accept(this);
            newOps.add(newOp);
            if (!op.equals(newOp)) {
                sawChange = true;
            }
        }
        if (!sawChange) {
            return axiom;
        }
        return dataFactory.getOWLDisjointUnionAxiom(axiom.getOWLClass(), newOps,
            getAnnotationsWithOptionalExpansionMarker(axiom));
    }

    @Override
    public OWLAxiom visit(OWLDataPropertyDomainAxiom axiom) {
        OWLClassExpression domain = axiom.getDomain();
        OWLClassExpression newDomain = domain.accept(this);
        if (domain.equals(newDomain)) {
            return axiom;
        } else {
            return dataFactory.getOWLDataPropertyDomainAxiom(axiom.getProperty(), newDomain,
                getAnnotationsWithOptionalExpansionMarker(axiom));
        }
    }

    @Override
    public OWLAxiom visit(OWLObjectPropertyDomainAxiom axiom) {
        OWLClassExpression domain = axiom.getDomain();
        OWLClassExpression newDomain = domain.accept(this);
        if (domain.equals(newDomain)) {
            return axiom;
        } else {
            return dataFactory.getOWLObjectPropertyDomainAxiom(axiom.getProperty(), newDomain,
                getAnnotationsWithOptionalExpansionMarker(axiom));
        }
    }

    @Override
    public OWLAxiom visit(OWLObjectPropertyRangeAxiom axiom) {
        OWLClassExpression range = axiom.getRange();
        OWLClassExpression newRange = range.accept(this);
        if (range.equals(newRange)) {
            return axiom;
        } else {
            return dataFactory.getOWLObjectPropertyRangeAxiom(axiom.getProperty(), newRange,
                getAnnotationsWithOptionalExpansionMarker(axiom));
        }
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLDataPropertyRangeAxiom axiom) {
        OWLDataRange range = axiom.getRange();
        OWLDataRange newRange = range.accept(this);
        if (range.equals(newRange)) {
            return axiom;
        } else {
            return dataFactory.getOWLDataPropertyRangeAxiom(axiom.getProperty(), newRange,
                getAnnotationsWithOptionalExpansionMarker(axiom));
        }
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLClassAssertionAxiom axiom) {
        OWLClassExpression classExpression = axiom.getClassExpression();
        if (classExpression.isAnonymous()) {
            OWLClassExpression newClassExpression = classExpression.accept(this);
            if (!classExpression.equals(newClassExpression)) {
                return dataFactory.getOWLClassAssertionAxiom(newClassExpression, axiom.getIndividual(),
                    getAnnotationsWithOptionalExpansionMarker(axiom));
            }
        }
        return axiom;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLEquivalentClassesAxiom axiom) {
        Set<OWLClassExpression> newExpressions = new HashSet<>();
        boolean sawChange = false;
        for (OWLClassExpression expression : axiom.getClassExpressions()) {
            OWLClassExpression newExpression = expression.accept(this);
            newExpressions.add(newExpression);
            if (!expression.equals(newExpression)) {
                sawChange = true;
            }
        }
        if (sawChange) {
            return dataFactory.getOWLEquivalentClassesAxiom(newExpressions, getAnnotationsWithOptionalExpansionMarker(
                axiom));
        } else {
            return axiom;
        }
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

    @Override
    public OWLDataRange visit(OWLDatatype node) {
        return node;
    }

    @Override
    public OWLDataRange visit(OWLDataComplementOf node) {
        return node;
    }

    @Override
    public OWLAxiom visit(OWLHasKeyAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLDataRange visit(OWLDatatypeRestriction node) {
        return node;
    }

    @Override
    public OWLAxiom visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLReflexiveObjectPropertyAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLDifferentIndividualsAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLDisjointDataPropertiesAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLDisjointObjectPropertiesAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLObjectPropertyAssertionAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLFunctionalObjectPropertyAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLSubObjectPropertyOfAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLDeclarationAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLAnnotationAssertionAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLSymmetricObjectPropertyAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLFunctionalDataPropertyAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLEquivalentDataPropertiesAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLDataPropertyAssertionAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLTransitiveObjectPropertyAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLSubDataPropertyOfAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLSameIndividualAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLSubPropertyChainOfAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLInverseObjectPropertiesAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(SWRLRule rule) {
        return rule;
    }

    @Override
    public OWLAxiom visit(OWLAnnotationPropertyDomainAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLAnnotationPropertyRangeAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLDatatypeDefinitionAxiom axiom) {
        return axiom;
    }
}
