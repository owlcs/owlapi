package org.obolibrary.macro;

import static org.obolibrary.obo2owl.Obo2OWLConstants.Obo2OWLVocabulary.*;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.search.Searcher.annotations;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.manchestersyntax.renderer.ParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataVisitorEx;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObject;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.search.Filters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Empty abstract visitor for macro expansion. This class allows to minimize the
 * code in the actual visitors, as they only need to overwrite the relevant
 * methods.
 */
public abstract class AbstractMacroExpansionVisitor implements
        OWLAxiomVisitorEx<OWLAxiom> {

    static final Logger LOG = LoggerFactory
            .getLogger(AbstractMacroExpansionVisitor.class);
    final OWLDataFactory df;
    @Nonnull
    final Map<IRI, String> expandAssertionToMap;
    @Nonnull
    protected final Map<IRI, String> expandExpressionMap;
    protected OWLDataVisitorEx<OWLDataRange> rangeVisitor;
    protected OWLClassExpressionVisitorEx<OWLClassExpression> classVisitor;
    protected ManchesterSyntaxTool manchesterSyntaxTool;

    @Override
    public OWLAxiom doDefault(Object o) {
        return (OWLAxiom) o;
    }

    /**
     * class expression visitor
     */
    public abstract class AbstractClassExpressionVisitorEx implements
            OWLClassExpressionVisitorEx<OWLClassExpression> {

        protected AbstractClassExpressionVisitorEx() {}

        @Override
        public OWLClassExpression visit(@Nonnull OWLObjectIntersectionOf ce) {
            return df.getOWLObjectIntersectionOf(asSet(ce.operands().map(
                    o -> o.accept(this))));
        }

        @Override
        public OWLClassExpression visit(@Nonnull OWLObjectUnionOf ce) {
            return df.getOWLObjectUnionOf(asSet(ce.operands().map(
                    o -> o.accept(this))));
        }

        @Override
        public OWLClassExpression visit(@Nonnull OWLObjectComplementOf ce) {
            return df.getOWLObjectComplementOf(ce.getOperand().accept(this));
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
                result = df.getOWLObjectSomeValuesFrom(ce.getProperty(),
                        filler.accept(this));
            }
            return result;
        }

        @Nullable
        protected abstract OWLClassExpression expandOWLObjSomeVal(
                @Nonnull OWLClassExpression filler,
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
                result = df.getOWLObjectHasValue(ce.getProperty(), filler);
            }
            return result;
        }

        @Nullable
        protected abstract OWLClassExpression expandOWLObjHasVal(
                @Nonnull OWLObjectHasValue desc, @Nonnull OWLIndividual filler,
                @Nonnull OWLObjectPropertyExpression p);

        @Override
        public OWLClassExpression visit(@Nonnull OWLObjectAllValuesFrom ce) {
            return ce.getFiller().accept(this);
        }

        @Override
        public OWLClassExpression visit(@Nonnull OWLObjectMinCardinality ce) {
            OWLClassExpression filler = ce.getFiller().accept(this);
            return df.getOWLObjectMinCardinality(ce.getCardinality(),
                    ce.getProperty(), filler);
        }

        @Override
        public OWLClassExpression visit(@Nonnull OWLObjectExactCardinality ce) {
            return ce.asIntersectionOfMinMax().accept(this);
        }

        @Override
        public OWLClassExpression visit(@Nonnull OWLObjectMaxCardinality ce) {
            OWLClassExpression filler = ce.getFiller().accept(this);
            return df.getOWLObjectMaxCardinality(ce.getCardinality(),
                    ce.getProperty(), filler);
        }

        @Override
        public OWLClassExpression visit(@Nonnull OWLDataSomeValuesFrom ce) {
            OWLDataRange filler = ce.getFiller().accept(rangeVisitor);
            return df.getOWLDataSomeValuesFrom(ce.getProperty(), filler);
        }

        @Override
        public OWLClassExpression visit(@Nonnull OWLDataAllValuesFrom ce) {
            OWLDataRange filler = ce.getFiller().accept(rangeVisitor);
            return df.getOWLDataAllValuesFrom(ce.getProperty(), filler);
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
            OWLDataRange filler = ce.getFiller().accept(rangeVisitor);
            return df.getOWLDataMaxCardinality(card, ce.getProperty(), filler);
        }

        @Override
        public OWLClassExpression visit(@Nonnull OWLDataMinCardinality ce) {
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

    protected AbstractMacroExpansionVisitor(@Nonnull OWLOntology o) {
        df = o.getOWLOntologyManager().getOWLDataFactory();
        expandExpressionMap = new HashMap<>();
        expandAssertionToMap = new HashMap<>();
        o.objectPropertiesInSignature().forEach(
                p -> annotations(
                        o.axioms(Filters.annotations, p.getIRI(), INCLUDED),
                        df.getOWLAnnotationProperty(IRI_IAO_0000424.getIRI()))
                        .forEach(a -> {
                            OWLAnnotationValue v = a.getValue();
                            if (v instanceof OWLLiteral) {
                                String str = ((OWLLiteral) v).getLiteral();
                                LOG.info("mapping {} to {}", p.getIRI(), str);
                                expandExpressionMap.put(p.getIRI(), str);
                            }
                        }));
        o.annotationPropertiesInSignature()
                .forEach(p -> expandAssertions(o, p));
    }

    protected void expandAssertions(OWLOntology o, OWLAnnotationProperty p) {
        annotations(o.axioms(Filters.annotations, p.getIRI(), INCLUDED),
                df.getOWLAnnotationProperty(IRI_IAO_0000425.getIRI()))
                .map(a -> a.getValue().asLiteral()).filter(v -> v.isPresent())
                .forEach(v -> {
                    String str = v.get().getLiteral();
                    LOG.info("assertion mapping {} to {}", p, str);
                    expandAssertionToMap.put(p.getIRI(), str);
                });
    }

    @Nullable
    protected OWLClassExpression expandObject(Object filler,
            @Nonnull OWLObjectPropertyExpression p) {
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

    protected OWLClassExpression resultFromVal(IRI iri, IRI templateVal) {
        String tStr = expandExpressionMap.get(iri);
        String exStr = tStr.replaceAll("\\?Y",
                manchesterSyntaxTool.getId(templateVal));
        try {
            return manchesterSyntaxTool.parseManchesterExpression(exStr);
        } catch (ParserException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    protected IRI valFromOneOf(Object filler) {
        Set<OWLIndividual> inds = asSet(
                ((OWLObjectOneOf) filler).individuals(), OWLIndividual.class);
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
    public OWLAxiom visit(@Nonnull OWLSubClassOfAxiom ax) {
        return df.getOWLSubClassOfAxiom(ax.getSubClass().accept(classVisitor),
                ax.getSuperClass().accept(classVisitor));
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLDisjointClassesAxiom ax) {
        Set<OWLClassExpression> ops = new HashSet<>();
        ax.classExpressions().forEach(op -> ops.add(op.accept(classVisitor)));
        return df.getOWLDisjointClassesAxiom(asSet(ax.classExpressions().map(
                o -> o.accept(classVisitor))));
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLDataPropertyDomainAxiom ax) {
        return df.getOWLDataPropertyDomainAxiom(ax.getProperty(), ax
                .getDomain().accept(classVisitor));
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLObjectPropertyDomainAxiom ax) {
        return df.getOWLObjectPropertyDomainAxiom(ax.getProperty(), ax
                .getDomain().accept(classVisitor));
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLObjectPropertyRangeAxiom ax) {
        return df.getOWLObjectPropertyRangeAxiom(ax.getProperty(), ax
                .getRange().accept(classVisitor));
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLDisjointUnionAxiom ax) {
        Set<OWLClassExpression> descs = asSet(ax.classExpressions().map(
                op -> op.accept(classVisitor)));
        return df.getOWLDisjointUnionAxiom(ax.getOWLClass(), descs);
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLDataPropertyRangeAxiom ax) {
        return df.getOWLDataPropertyRangeAxiom(ax.getProperty(), ax.getRange()
                .accept(rangeVisitor));
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLClassAssertionAxiom ax) {
        if (ax.getClassExpression().isAnonymous()) {
            return df.getOWLClassAssertionAxiom(
                    ax.getClassExpression().accept(classVisitor),
                    ax.getIndividual());
        }
        return ax;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLEquivalentClassesAxiom ax) {
        Set<OWLClassExpression> ops = asSet(ax.classExpressions().map(
                op -> op.accept(classVisitor)));
        return df.getOWLEquivalentClassesAxiom(ops);
    }
}
