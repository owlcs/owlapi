package org.obolibrary.macro;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.mansyntax.renderer.ParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
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
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.util.OntologyAxiomPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author cjm TODO - allow use of prefixes */
public class MacroExpansionVisitor {

    protected static final Logger log = LoggerFactory
            .getLogger(MacroExpansionVisitor.class);
    @Nonnull
    private OWLOntology inputOntology;
    @Nonnull
    private OWLOntologyManager manager;
    @Nonnull
    private Visitor visitor;
    @Nonnull
    protected ManchesterSyntaxTool manchesterSyntaxTool;

    /**
     * @param inputOntology
     *        inputOntology
     */
    public MacroExpansionVisitor(@Nonnull OWLOntology inputOntology) {
        super();
        this.inputOntology = inputOntology;
        visitor = new Visitor(inputOntology);
        manchesterSyntaxTool = new ManchesterSyntaxTool(inputOntology);
        manager = inputOntology.getOWLOntologyManager();
    }

    /** @return ontology with expanded macros */
    public OWLOntology expandAll() {
        Set<OWLAxiom> newAxioms = new HashSet<OWLAxiom>();
        Set<OWLAxiom> rmAxioms = new HashSet<OWLAxiom>();
        for (OWLAxiom ax : inputOntology.getAxioms()) {
            OWLAxiom exAx = ax;
            if (ax instanceof OWLSubClassOfAxiom) {
                exAx = visitor.visit((OWLSubClassOfAxiom) ax);
            } else if (ax instanceof OWLEquivalentClassesAxiom) {
                exAx = visitor.visit((OWLEquivalentClassesAxiom) ax);
            } else if (ax instanceof OWLClassAssertionAxiom) {
                exAx = visitor.visit((OWLClassAssertionAxiom) ax);
            } else if (ax instanceof OWLAnnotationAssertionAxiom) {
                for (OWLAxiom expandedAx : expand((OWLAnnotationAssertionAxiom) ax)) {
                    // output(expandedAx);
                    if (!ax.equals(expandedAx)) {
                        newAxioms.add(expandedAx);
                        rmAxioms.add(ax);
                    }
                }
            }
            /*
             * else if(ax instanceof OWLDeclarationAxiom) { exAx =
             * vistor.visit((OWLDeclarationAxiom) ax); }
             */
            // output(exAx);
            if (!ax.equals(exAx)) {
                newAxioms.add(exAx);
                rmAxioms.add(ax);
            }
        }
        manager.addAxioms(inputOntology, newAxioms);
        manager.removeAxioms(inputOntology, rmAxioms);
        return inputOntology;
    }

    @Nonnull
    private Set<OWLAxiom> expand(@Nonnull OWLAnnotationAssertionAxiom ax) {
        OWLAnnotationProperty prop = ax.getProperty();
        String expandTo = visitor.expandAssertionToMap.get(prop.getIRI());
        HashSet<OWLAxiom> setAx = new HashSet<OWLAxiom>();
        if (expandTo != null) {
            // when expanding assertions, the axiom is an annotation assertion,
            // and the value may be not be explicitly declared. If it is not,
            // we assume it is a class
            IRI axValIRI = (IRI) ax.getValue();
            OWLClass axValClass = visitor.dataFactory.getOWLClass(axValIRI);
            if (inputOntology.getDeclarationAxioms(axValClass).size() == 0) {
                OWLDeclarationAxiom newAx = visitor.dataFactory
                        .getOWLDeclarationAxiom(axValClass);
                manager.addAxiom(inputOntology, newAx);
                // we need to sync the MST entity checker with the new ontology
                // plus declarations;
                // we do this by creating a new MST - this is not particularly
                // efficient, a better
                // way might be to first scan the ontology for all annotation
                // axioms that will be expanded,
                // then add the declarations at this point
                manchesterSyntaxTool = new ManchesterSyntaxTool(inputOntology);
            }
            log.info("Template to Expand {}", expandTo);
            expandTo = expandTo.replaceAll("\\?X",
                    manchesterSyntaxTool.getId((IRI) ax.getSubject()));
            expandTo = expandTo.replaceAll("\\?Y",
                    manchesterSyntaxTool.getId(axValIRI));
            log.info("Expanding {}", expandTo);
            try {
                Set<OntologyAxiomPair> setAxp = manchesterSyntaxTool
                        .parseManchesterExpressionFrames(expandTo);
                for (OntologyAxiomPair axp : setAxp) {
                    setAx.add(axp.getAxiom());
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
            // TODO:
        }
        return setAx;
    }

    private class Visitor extends AbstractMacroExpansionVisitor {

        Visitor(@Nonnull OWLOntology inputOntology) {
            super(inputOntology);
        }

        @Nullable
        @Override
        protected OWLClassExpression expandOWLObjSomeVal(
                @Nonnull OWLClassExpression filler,
                @Nonnull OWLObjectPropertyExpression p) {
            return expandObject(filler, p);
        }

        @Nonnull
        @Override
        protected OWLClassExpression expandOWLObjHasVal(
                @Nonnull OWLObjectHasValue desc, OWLIndividual filler,
                @Nonnull OWLObjectPropertyExpression p) {
            OWLClassExpression result = expandObject(filler, p);
            if (result != null) {
                result = dataFactory.getOWLObjectSomeValuesFrom(
                        desc.getProperty(), result);
            }
            return result;
        }

        @Nullable
        OWLClassExpression expandObject(Object filler,
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
