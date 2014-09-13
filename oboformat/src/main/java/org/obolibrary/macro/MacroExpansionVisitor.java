package org.obolibrary.macro;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.util.OntologyAxiomPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author cjm TODO - allow use of prefixes */
public class MacroExpansionVisitor {

    protected static final Logger LOG = LoggerFactory
            .getLogger(MacroExpansionVisitor.class);
    @Nonnull
    private final OWLOntology inputOntology;
    @Nonnull
    private final OWLOntologyManager manager;
    @Nonnull
    private final Visitor visitor;
    protected final AbstractDataVisitorEx dataVisitor;

    /**
     * @param inputOntology
     *        inputOntology
     */
    public MacroExpansionVisitor(@Nonnull OWLOntology inputOntology) {
        this.inputOntology = inputOntology;
        visitor = new Visitor(inputOntology);
        visitor.rebuild(inputOntology);
        manager = inputOntology.getOWLOntologyManager();
        dataVisitor = new AbstractDataVisitorEx(manager.getOWLDataFactory());
    }

    /** @return ontology with expanded macros */
    public OWLOntology expandAll() {
        Set<OWLAxiom> newAxioms = new HashSet<>();
        Set<OWLAxiom> rmAxioms = new HashSet<>();
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
        HashSet<OWLAxiom> setAx = new HashSet<>();
        if (expandTo != null) {
            // when expanding assertions, the axiom is an annotation assertion,
            // and the value may be not be explicitly declared. If it is not,
            // we assume it is a class
            IRI axValIRI = (IRI) ax.getValue();
            OWLClass axValClass = visitor.dataFactory.getOWLClass(axValIRI);
            if (inputOntology.getDeclarationAxioms(axValClass).isEmpty()) {
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
                visitor.rebuild(inputOntology);
            }
            LOG.info("Template to Expand {}", expandTo);
            expandTo = expandTo.replaceAll("\\?X",
                    visitor.getTool().getId((IRI) ax.getSubject()));
            expandTo = expandTo.replaceAll("\\?Y",
                    visitor.getTool().getId(axValIRI));
            LOG.info("Expanding {}", expandTo);
            try {
                Set<OntologyAxiomPair> setAxp = visitor.getTool()
                        .parseManchesterExpressionFrames(expandTo);
                for (OntologyAxiomPair axp : setAxp) {
                    setAx.add(axp.getAxiom());
                }
            } catch (Exception ex) {
                LOG.error(ex.getMessage(), ex);
            }
            // TODO:
        }
        return setAx;
    }

    private class Visitor extends AbstractMacroExpansionVisitor {

        Visitor(@Nonnull OWLOntology inputOntology) {
            super(inputOntology);
            rangeVisitor = dataVisitor;
            classVisitor = new AbstractMacroExpansionVisitor.AbstractClassExpressionVisitorEx() {

                @Nullable
                @Override
                protected OWLClassExpression expandOWLObjSomeVal(
                        @Nonnull OWLClassExpression filler,
                        @Nonnull OWLObjectPropertyExpression p) {
                    return expandObject(filler, p);
                }

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
            };
        }
    }

    /** Call this method to clear internal references. */
    public void dispose() {
        visitor.getTool().dispose();
    }
}
