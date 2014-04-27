package org.obolibrary.macro;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.mansyntax.renderer.ParserException;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObject;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
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

    protected static final Logger log = LoggerFactory
            .getLogger(MacroExpansionGCIVisitor.class);
    private OWLOntology inputOntology;
    private OWLOntologyManager outputManager;
    @Nonnull
    private OWLOntology outputOntology;
    protected ManchesterSyntaxTool manchesterSyntaxTool;
    private GCIVisitor visitor;

    /**
     * @param inputOntology
     *        inputOntology
     * @param outputManager
     *        outputManager
     */
    public MacroExpansionGCIVisitor(@Nonnull OWLOntology inputOntology,
            @Nonnull OWLOntologyManager outputManager) {
        super();
        this.inputOntology = inputOntology;
        visitor = new GCIVisitor(inputOntology);
        manchesterSyntaxTool = new ManchesterSyntaxTool(inputOntology);
        this.outputManager = outputManager;
        try {
            outputOntology = outputManager.createOntology(inputOntology
                    .getOntologyID());
        } catch (Exception ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected void output(@Nullable OWLAxiom axiom) {
        if (axiom == null) {
            log.error("no axiom");
            return;
        }
        // System.out.println("adding:"+axiom);
        AddAxiom addAx = new AddAxiom(outputOntology, axiom);
        try {
            outputManager.applyChange(addAx);
        } catch (Exception e) {
            log.error("COULD NOT TRANSLATE AXIOM", e);
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
            log.info("Template to Expand{}", expandTo);
            expandTo = expandTo.replaceAll("\\?X",
                    manchesterSyntaxTool.getId((IRI) ax.getSubject()));
            expandTo = expandTo.replaceAll("\\?Y",
                    manchesterSyntaxTool.getId((IRI) ax.getValue()));
            log.info("Expanding {}", expandTo);
            try {
                Set<OntologyAxiomPair> setAxp = manchesterSyntaxTool
                        .parseManchesterExpressionFrames(expandTo);
                for (OntologyAxiomPair axp : setAxp) {
                    output(axp.getAxiom());
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }

    private class GCIVisitor extends AbstractMacroExpansionVisitor {

        GCIVisitor(@Nonnull OWLOntology inputOntology) {
            super(inputOntology);
        }

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
                @SuppressWarnings("unused") OWLObjectHasValue desc,
                @Nonnull OWLIndividual filler,
                @Nonnull OWLObjectPropertyExpression p) {
            OWLClassExpression gciRHS = expandObject(filler, p);
            if (gciRHS != null) {
                OWLClassExpression gciLHS = dataFactory.getOWLObjectHasValue(p,
                        filler);
                OWLEquivalentClassesAxiom ax = dataFactory
                        .getOWLEquivalentClassesAxiom(gciLHS, gciRHS);
                output(ax);
            }
            return gciRHS;
        }

        @Nullable
        private OWLClassExpression expandObject(Object filler,
                @Nonnull OWLObjectPropertyExpression p) {
            OWLClassExpression result = null;
            IRI iri = ((OWLObjectProperty) p).getIRI();
            IRI templateVal = null;
            if (expandExpressionMap.containsKey(iri)) {
                if (filler instanceof OWLObjectOneOf) {
                    Set<OWLIndividual> inds = ((OWLObjectOneOf) filler)
                            .getIndividuals();
                    if (inds.size() == 1) {
                        OWLIndividual ind = inds.iterator().next();
                        if (ind instanceof OWLNamedIndividual) {
                            templateVal = ((OWLNamedObject) ind).getIRI();
                        }
                    }
                }
                if (filler instanceof OWLNamedObject) {
                    templateVal = ((OWLNamedObject) filler).getIRI();
                }
                if (templateVal != null) {
                    String tStr = expandExpressionMap.get(iri);
                    String exStr = tStr.replaceAll("\\?Y",
                            manchesterSyntaxTool.getId(templateVal));
                    try {
                        result = manchesterSyntaxTool
                                .parseManchesterExpression(exStr);
                    } catch (ParserException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
            return result;
        }
    }

    /** Call this method to clear internal references. */
    public void dispose() {
        manchesterSyntaxTool.dispose();
    }
}
