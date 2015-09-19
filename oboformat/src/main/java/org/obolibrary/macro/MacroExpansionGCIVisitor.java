package org.obolibrary.macro;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Macro expansion gci visitor. */
public class MacroExpansionGCIVisitor {

    protected static final Logger LOG = LoggerFactory.getLogger(MacroExpansionGCIVisitor.class);
    protected final @Nonnull OWLOntology inputOntology;
    protected final @Nonnull OWLOntology outputOntology;
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
    public MacroExpansionGCIVisitor(OWLOntology inputOntology, OWLOntologyManager outputManager,
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
    public MacroExpansionGCIVisitor(OWLOntologyManager outputManager, OWLOntology inputOntology,
        boolean shouldAddExpansionMarker) {
        this.inputOntology = inputOntology;
        this.shouldAddExpansionMarker = shouldAddExpansionMarker;
        try {
            outputOntology = outputManager.createOntology(inputOntology.getOntologyID());
        } catch (Exception ex) {
            throw new OWLRuntimeException(ex);
        }
        dataVisitor = new AbstractDataVisitorEx(inputOntology.getOWLOntologyManager().getOWLDataFactory());
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

    private class MacroExpansions {

        private final Set<OWLAxiom> newAxioms = new HashSet<>();
        private final Set<OWLAxiom> rmAxioms = new HashSet<>();
        GCIVisitor visitor;

        public MacroExpansions() {
            visitor = new GCIVisitor(inputOntology, newAxioms);
            inputOntology.axioms(AxiomType.SUBCLASS_OF).forEach(axiom -> visitor.visit(axiom));
            inputOntology.axioms(AxiomType.EQUIVALENT_CLASSES).forEach(axiom -> visitor.visit(axiom));
            inputOntology.axioms(AxiomType.CLASS_ASSERTION).forEach(axiom -> visitor.visit(axiom));
             inputOntology.axioms(AxiomType.ANNOTATION_ASSERTION).forEach(axiom -> expand(axiom)); 
             //.forEach(ax-> System.out.println("not removing" + ax)) ;
        }

        // private void replaceIfDifferent(OWLAxiom ax, OWLAxiom exAx) {
        // if (!ax.equals(exAx)) {
        // newAxioms.add(exAx);
        // rmAxioms.add(ax);
        // }
        // }
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
                expandTo = expandTo.replaceAll("\\?X", visitor.getTool().getId((IRI) ax.getSubject()));
                expandTo = expandTo.replaceAll("\\?Y", visitor.getTool().getId((IRI) ax.getValue()));
                LOG.info("Expanding {}", expandTo);
                try {
                    visitor.getTool().parseManchesterExpressionFrames(expandTo).forEach(axp -> {
                        OWLAxiom axiom = axp.getAxiom();
                        if (shouldPreserveAnnotationsWhenExpanding()) {
                            axiom = axiom.getAnnotatedAxiom(visitor.getAnnotationsWithOptionalExpansionMarker(ax));
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
            classVisitor = new AbstractMacroExpansionVisitor.AbstractClassExpressionVisitorEx() {

                @Override
                protected @Nullable OWLClassExpression expandOWLObjSomeVal(OWLClassExpression filler,
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
                protected @Nullable OWLClassExpression expandOWLObjHasVal(OWLObjectHasValue desc, OWLIndividual filler,
                    OWLObjectPropertyExpression p) {
                    OWLClassExpression gciRHS = expandObject(filler, p);
                    if (gciRHS != null) {
                        OWLClassExpression gciLHS = df.getOWLObjectHasValue(p, filler);
                        OWLEquivalentClassesAxiom ax = df.getOWLEquivalentClassesAxiom(gciLHS, gciRHS,
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
    public void setPreserveAnnotationsWhenExpanding(boolean preserveAnnotationsWhenExpanding) {
        this.preserveAnnotationsWhenExpanding = preserveAnnotationsWhenExpanding;
    }
}
