package org.obolibrary.macro;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Macro expansion gci visitor. */
public class MacroExpansionGCIVisitor {

    protected static final Logger LOG = LoggerFactory
        .getLogger(MacroExpansionGCIVisitor.class);
    @Nonnull
    protected final OWLOntology inputOntology;
    private final OWLOntologyManager outputManager;
    @Nonnull
    protected final OWLOntology outputOntology;
    protected final AbstractDataVisitorEx dataVisitor;
    protected boolean preserveAnnotationsWhenExpanding = false;
    protected final boolean shouldAddExpansionMarker;

    /**
     * @param inputOntology
     *        ontology to use
     * @param outputManager
     *        manager for ontology creation
     * @param preserveAnnotationsWhenExpanding
     *        true if annotations should be preserved when expanding
     */
    public MacroExpansionGCIVisitor(OWLOntology inputOntology,
        OWLOntologyManager outputManager,
        boolean preserveAnnotationsWhenExpanding) {
        this(outputManager, inputOntology, false);
        this.preserveAnnotationsWhenExpanding = preserveAnnotationsWhenExpanding;
    }

    /**
     * @param outputManager
     *        outputManager
     * @param inputOntology
     *        inputOntology
     * @param shouldAddExpansionMarker
     *        should expansionMarker be added
     */
    public MacroExpansionGCIVisitor(OWLOntologyManager outputManager,
        OWLOntology inputOntology, boolean shouldAddExpansionMarker) {
        this.inputOntology = inputOntology;
        this.shouldAddExpansionMarker = shouldAddExpansionMarker;
        this.outputManager = outputManager;
        try {
            outputOntology = outputManager
                .createOntology(inputOntology.getOntologyID());
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
        outputManager.addAxioms(outputOntology, expansions.getNewAxioms());
        outputManager.removeAxioms(outputOntology, expansions.getRmAxioms());
        return outputOntology;
    }

    private class MacroExpansions {

        private Set<OWLAxiom> newAxioms = new HashSet<>();
        private Set<OWLAxiom> rmAxioms = new HashSet<>();
        GCIVisitor visitor;

        public MacroExpansions() {
            visitor = new GCIVisitor(inputOntology, newAxioms);
            for (OWLSubClassOfAxiom axiom : inputOntology
                .getAxioms(AxiomType.SUBCLASS_OF)) {
                OWLAxiom newAxiom = visitor.visit(axiom);
                // System.out.println("not adding " + newAxiom);
            }
            for (OWLEquivalentClassesAxiom axiom : inputOntology
                .getAxioms(AxiomType.EQUIVALENT_CLASSES)) {
                OWLAxiom newAxiom = visitor.visit(axiom);
                // System.out.println("not adding " + newAxiom);
            }
            for (OWLClassAssertionAxiom axiom : inputOntology
                .getAxioms(AxiomType.CLASS_ASSERTION)) {
                OWLAxiom newAxiom = visitor.visit(axiom);
                // System.out.println("not adding " + newAxiom);
            }
            for (OWLAnnotationAssertionAxiom axiom : inputOntology
                .getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
                if (expand(axiom)) {
                    // System.out.println("not removing " + axiom);
                }
            }
        }

        private void replaceIfDifferent(OWLAxiom ax, OWLAxiom exAx) {
            if (!ax.equals(exAx)) {
                newAxioms.add(exAx);
                rmAxioms.add(ax);
            }
        }

        public Set<OWLAxiom> getNewAxioms() {
            return newAxioms;
        }

        public Set<OWLAxiom> getRmAxioms() {
            return rmAxioms;
        }

        private boolean expand(@Nonnull OWLAnnotationAssertionAxiom ax) {
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
                    visitor.getTool().parseManchesterExpressionFrames(expandTo)
                        .forEach(axp -> {
                            OWLAxiom axiom = axp.getAxiom();
                            if (shouldPreserveAnnotationsWhenExpanding()) {
                                Set<OWLAnnotation> annotationsWithOptionalExpansionMarker = visitor
                                    .getAnnotationsWithOptionalExpansionMarker(
                                        ax);
                                axiom = axiom.getAnnotatedAxiom(
                                    annotationsWithOptionalExpansionMarker);
                            }
                            newAxioms.add(axiom);
                            didExpansion.set(true);
                        } );
                } catch (Exception ex) {
                    LOG.error(ex.getMessage(), ex);
                }
            }
            return didExpansion.get();
        }
    }

    private class GCIVisitor extends AbstractMacroExpansionVisitor {

        final Set<OWLAnnotation> expansionMarkingAnnotations;
        final Set<OWLAxiom> newAxioms;

        GCIVisitor(@Nonnull OWLOntology inputOntology,
            Set<OWLAxiom> newAxioms) {
            super(inputOntology, shouldAddExpansionMarker);
            this.newAxioms = newAxioms;
            if (shouldAddExpansionMarker) {
                expansionMarkingAnnotations = Collections
                    .singleton(expansionMarkerAnnotation);
            } else {
                expansionMarkingAnnotations = EMPTY_ANNOTATIONS;
            }
            rangeVisitor = dataVisitor;
            classVisitor = new AbstractMacroExpansionVisitor.AbstractClassExpressionVisitorEx() {

                @Nullable
                @Override
                protected OWLClassExpression expandOWLObjSomeVal(
                    OWLClassExpression filler,
                    @Nonnull OWLObjectPropertyExpression p) {
                    OWLClassExpression gciRHS = expandObject(filler, p);
                    if (gciRHS != null) {
                        OWLClassExpression gciLHS = df
                            .getOWLObjectSomeValuesFrom(p, filler);
                        OWLEquivalentClassesAxiom ax = df
                            .getOWLEquivalentClassesAxiom(gciLHS, gciRHS,
                                expansionMarkingAnnotations);
                        newAxioms.add(ax);
                    }
                    return gciRHS;
                }

                @Nullable
                @Override
                protected OWLClassExpression expandOWLObjHasVal(
                    OWLObjectHasValue desc, @Nonnull OWLIndividual filler,
                    @Nonnull OWLObjectPropertyExpression p) {
                    OWLClassExpression gciRHS = expandObject(filler, p);
                    if (gciRHS != null) {
                        OWLClassExpression gciLHS = df.getOWLObjectHasValue(p,
                            filler);
                        OWLEquivalentClassesAxiom ax = df
                            .getOWLEquivalentClassesAxiom(gciLHS, gciRHS,
                                expansionMarkingAnnotations);
                        newAxioms.add(ax);
                    }
                    return gciRHS;
                }
            };
            rebuild(inputOntology);
        }
    }

    /**
     * @return true if annotations should be preserved
     */
    public boolean shouldPreserveAnnotationsWhenExpanding() {
        return preserveAnnotationsWhenExpanding;
    }

    /**
     * @param preserveAnnotationsWhenExpanding
     *        new value
     */
    public void setPreserveAnnotationsWhenExpanding(
        boolean preserveAnnotationsWhenExpanding) {
        this.preserveAnnotationsWhenExpanding = preserveAnnotationsWhenExpanding;
    }
}
