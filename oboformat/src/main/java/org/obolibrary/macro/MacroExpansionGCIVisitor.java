package org.obolibrary.macro;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
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
import org.semanticweb.owlapi.util.OntologyAxiomPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** macro expansion gci visitor */
public class MacroExpansionGCIVisitor {

    protected static final Logger LOG = LoggerFactory
            .getLogger(MacroExpansionGCIVisitor.class);
    private final OWLOntology inputOntology;
    private final OWLOntologyManager outputManager;
    @Nonnull
    private final OWLOntology outputOntology;
    private final GCIVisitor visitor;
    protected final AbstractDataVisitorEx dataVisitor;

    /**
     * @param inputOntology
     *        inputOntology
     * @param outputManager
     *        outputManager
     */
    public MacroExpansionGCIVisitor(@Nonnull OWLOntology inputOntology,
            @Nonnull OWLOntologyManager outputManager) {
        this.inputOntology = inputOntology;
        visitor = new GCIVisitor(inputOntology);
        visitor.rebuild(inputOntology);
        this.outputManager = outputManager;
        try {
            outputOntology = outputManager.createOntology(inputOntology
                    .getOntologyID());
        } catch (Exception ex) {
            throw new OWLRuntimeException(ex);
        }
        dataVisitor = new AbstractDataVisitorEx(inputOntology
                .getOWLOntologyManager().getOWLDataFactory());
    }

    protected void output(@Nullable OWLAxiom axiom) {
        if (axiom == null) {
            LOG.error("no axiom");
            return;
        }
        // System.out.println("adding:"+axiom);
        AddAxiom addAx = new AddAxiom(outputOntology, axiom);
        try {
            outputManager.applyChange(addAx);
        } catch (Exception e) {
            LOG.error("COULD NOT TRANSLATE AXIOM", e);
        }
    }

    /** @return ontology for gci */
    public OWLOntology createGCIOntology() {
        for (OWLAxiom ax : inputOntology.getAxioms()) {
            if (ax instanceof OWLSubClassOfAxiom) {
                visitor.visit((OWLSubClassOfAxiom) ax);
            } else if (ax instanceof OWLEquivalentClassesAxiom) {
                visitor.visit((OWLEquivalentClassesAxiom) ax);
            } else if (ax instanceof OWLClassAssertionAxiom) {
                visitor.visit((OWLClassAssertionAxiom) ax);
            } else if (ax instanceof OWLAnnotationAssertionAxiom) {
                expand((OWLAnnotationAssertionAxiom) ax);
            }
        }
        return outputOntology;
    }

    private void expand(@Nonnull OWLAnnotationAssertionAxiom ax) {
        OWLAnnotationProperty prop = ax.getProperty();
        String expandTo = visitor.expandAssertionToMap.get(prop.getIRI());
        if (expandTo != null) {
            LOG.info("Template to Expand{}", expandTo);
            expandTo = expandTo.replaceAll("\\?X",
                    visitor.getTool().getId((IRI) ax.getSubject()));
            expandTo = expandTo.replaceAll("\\?Y",
                    visitor.getTool().getId((IRI) ax.getValue()));
            LOG.info("Expanding {}", expandTo);
            try {
                Set<OntologyAxiomPair> setAxp = visitor.getTool()
                        .parseManchesterExpressionFrames(expandTo);
                for (OntologyAxiomPair axp : setAxp) {
                    output(axp.getAxiom());
                }
            } catch (Exception ex) {
                LOG.error(ex.getMessage(), ex);
            }
        }
    }

    private class GCIVisitor extends AbstractMacroExpansionVisitor {

        GCIVisitor(@Nonnull OWLOntology inputOntology) {
            super(inputOntology);
            rangeVisitor = dataVisitor;
            classVisitor = new AbstractMacroExpansionVisitor.AbstractClassExpressionVisitorEx() {

                @Nullable
                @Override
                protected OWLClassExpression expandOWLObjSomeVal(
                        OWLClassExpression filler,
                        @Nonnull OWLObjectPropertyExpression p) {
                    OWLClassExpression gciRHS = expandObject(filler, p);
                    if (gciRHS != null) {
                        OWLClassExpression gciLHS = dataFactory
                                .getOWLObjectSomeValuesFrom(p, filler);
                        OWLEquivalentClassesAxiom ax = dataFactory
                                .getOWLEquivalentClassesAxiom(gciLHS, gciRHS);
                        output(ax);
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
                        OWLClassExpression gciLHS = dataFactory
                                .getOWLObjectHasValue(p, filler);
                        OWLEquivalentClassesAxiom ax = dataFactory
                                .getOWLEquivalentClassesAxiom(gciLHS, gciRHS);
                        output(ax);
                    }
                    return gciRHS;
                }
            };
        }
    }

    /** Call this method to clear internal references. */
    public void dispose() {
        visitor.getTool().dispose();
    }
}
