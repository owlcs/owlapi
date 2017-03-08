package org.obolibrary.macro;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Macro expansion gci visitor.
 */
public class MacroExpansionGCIVisitor {

    protected static final Logger LOG = LoggerFactory.getLogger(MacroExpansionGCIVisitor.class);
    protected final OWLOntology inputOntology;
    protected final OWLOntology outputOntology;
    protected final AbstractDataVisitorEx dataVisitor;
    protected final boolean shouldAddExpansionMarker;
    protected boolean preserveAnnotationsWhenExpanding = false;

    /**
     * @param inputOntology ontology to use
     * @param outputManager manager for ontology creation
     * @param preserveAnnotationsWhenExpanding true if annotations should be preserved when
     * expanding
     */
    public MacroExpansionGCIVisitor(OWLOntology inputOntology, OWLOntologyManager outputManager,
        boolean preserveAnnotationsWhenExpanding) {
        this(outputManager, inputOntology, false);
        this.preserveAnnotationsWhenExpanding = preserveAnnotationsWhenExpanding;
    }

    /**
     * @param outputManager outputManager
     * @param inputOntology inputOntology
     * @param shouldAddExpansionMarker should expansionMarker be added
     */
    public MacroExpansionGCIVisitor(OWLOntologyManager outputManager, OWLOntology inputOntology,
        boolean shouldAddExpansionMarker) {
        this.inputOntology = inputOntology;
        this.shouldAddExpansionMarker = shouldAddExpansionMarker;
        try {
            outputOntology = outputManager.createOntology(inputOntology.getOntologyID());
        } catch (Exception ex) {
            throw new OWLRuntimeException(ex);
        }
        dataVisitor = new AbstractDataVisitorEx(
            inputOntology.getOWLOntologyManager().getOWLDataFactory());
    }

    /**
     * @return ontology for gci
     */
    public OWLOntology createGCIOntology() {
        MacroExpansions expansions = new MacroExpansions();
        outputOntology.add(expansions.getNewAxioms());
        outputOntology.remove(expansions.getRmAxioms());
        return outputOntology;
    }

    /**
     * @return true if annotations should be preserved
     */
    public boolean shouldPreserveAnnotationsWhenExpanding() {
        return preserveAnnotationsWhenExpanding;
    }

    /**
     * @param preserveAnnotationsWhenExpanding new value
     */
    public void setPreserveAnnotationsWhenExpanding(boolean preserveAnnotationsWhenExpanding) {
        this.preserveAnnotationsWhenExpanding = preserveAnnotationsWhenExpanding;
    }

    private class MacroExpansions {

        private final Set<OWLAxiom> newAxioms = new HashSet<>();
        private final Set<OWLAxiom> rmAxioms = new HashSet<>();
        GCIVisitor visitor;

        public MacroExpansions() {
            visitor = new GCIVisitor(inputOntology, newAxioms);
            inputOntology.axioms(AxiomType.SUBCLASS_OF).forEach(axiom -> axiom.accept(visitor));
            inputOntology.axioms(AxiomType.EQUIVALENT_CLASSES)
                .forEach(axiom -> axiom.accept(visitor));
            inputOntology.axioms(AxiomType.CLASS_ASSERTION).forEach(axiom -> axiom.accept(visitor));
            inputOntology.axioms(AxiomType.ANNOTATION_ASSERTION).forEach(this::expand);
        }

        public Set<OWLAxiom> getNewAxioms() {
            return newAxioms;
        }

        public Set<OWLAxiom> getRmAxioms() {
            return rmAxioms;
        }

        private boolean expand(OWLAnnotationAssertionAxiom ax) {
            OWLAnnotationProperty prop = ax.getProperty();
            AtomicBoolean didExpansion = new AtomicBoolean(false);
            String expandTo = visitor.expandAssertionToMap.get(prop.getIRI());
            if (expandTo != null) {
                LOG.info("Template to Expand{}", expandTo);
                expandTo = expandTo.replaceAll("\\?X",
                    visitor.getTool().getId((IRI) ax.getSubject()));
                expandTo = expandTo.replaceAll("\\?Y",
                    visitor.getTool().getId((IRI) ax.getValue()));
                LOG.info("Expanding {}", expandTo);
                try {
                    visitor.getTool().parseManchesterExpressionFrames(expandTo).forEach(axp -> {
                        OWLAxiom axiom = axp.getAxiom();
                        if (shouldPreserveAnnotationsWhenExpanding()) {
                            axiom = axiom.getAnnotatedAxiom(
                                visitor.getAnnotationsWithOptionalExpansionMarker(ax));
                        }
                        newAxioms.add(axiom);
                        didExpansion.set(true);
                    });
                } catch (Exception ex) {
                    LOG.error(ex.getMessage(), ex);
                }
            }
            return didExpansion.get();
        }
    }

    private class GCIVisitor extends AbstractMacroExpansionVisitor {

        final Set<OWLAnnotation> expansionMarkingAnnotations;

        GCIVisitor(OWLOntology inputOntology, Set<OWLAxiom> newAxioms) {
            super(inputOntology, shouldAddExpansionMarker);
            if (shouldAddExpansionMarker) {
                expansionMarkingAnnotations = Collections.singleton(expansionMarkerAnnotation);
            } else {
                expansionMarkingAnnotations = EMPTY_ANNOTATIONS;
            }
            rangeVisitor = dataVisitor;
            classVisitor = new ClassVisitor(newAxioms);
            rebuild(inputOntology);
        }

        class ClassVisitor extends AbstractMacroExpansionVisitor.AbstractClassExpressionVisitorEx {

            private Set<OWLAxiom> newAxioms;

            public ClassVisitor(Set<OWLAxiom> newAxioms) {
                this.newAxioms = newAxioms;
            }

            @Override
            @Nullable
            protected OWLClassExpression expandOWLObjSomeVal(OWLClassExpression filler,
                OWLObjectPropertyExpression p) {
                OWLClassExpression gciRHS = expandObject(filler, p);
                if (gciRHS != null) {
                    OWLClassExpression gciLHS = df.getOWLObjectSomeValuesFrom(p, filler);
                    OWLEquivalentClassesAxiom ax = df.getOWLEquivalentClassesAxiom(gciLHS, gciRHS,
                        expansionMarkingAnnotations);
                    newAxioms.add(ax);
                }
                return gciRHS;
            }

            @Override
            @Nullable
            protected OWLClassExpression expandOWLObjHasVal(OWLObjectHasValue desc,
                OWLIndividual filler, OWLObjectPropertyExpression p) {
                OWLClassExpression gciRHS = expandObject(filler, p);
                if (gciRHS != null) {
                    OWLClassExpression gciLHS = df.getOWLObjectHasValue(p, filler);
                    OWLEquivalentClassesAxiom ax = df.getOWLEquivalentClassesAxiom(gciLHS, gciRHS,
                        expansionMarkingAnnotations);
                    newAxioms.add(ax);
                }
                return gciRHS;
            }
        }
    }
}
