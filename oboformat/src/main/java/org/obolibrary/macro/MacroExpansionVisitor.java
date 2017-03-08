package org.obolibrary.macro;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.add;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

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
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cjm TODO - allow use of prefixes
 */
public class MacroExpansionVisitor {

    protected static final Logger LOG = LoggerFactory.getLogger(MacroExpansionVisitor.class);
    protected final OWLOntology inputOntology;
    protected final OWLOntologyManager manager;
    protected final Visitor visitor;
    protected final AbstractDataVisitorEx dataVisitor;
    protected final boolean shouldAddExpansionMarker;
    protected boolean shouldTransferAnnotations = false;
    protected Set<OWLAnnotation> extraAnnotations;

    /**
     * @param ontology ontology to use
     */
    public MacroExpansionVisitor(OWLOntology ontology) {
        this(ontology, AbstractMacroExpansionVisitor.EMPTY_ANNOTATIONS, false, false);
    }

    /**
     * @param ontology ontology to use
     * @param shouldAddExpansionMarker true if expansions should be added
     */
    public MacroExpansionVisitor(OWLOntology ontology, boolean shouldAddExpansionMarker) {
        this(ontology, AbstractMacroExpansionVisitor.EMPTY_ANNOTATIONS, false,
            shouldAddExpansionMarker);
    }

    /**
     * @param ontology ontology to use
     * @param shouldTransferAnnotations true if annotations should be transferred
     * @param shouldAddExpansionMarker true if expansions should be added
     */
    public MacroExpansionVisitor(OWLOntology ontology, boolean shouldTransferAnnotations,
        boolean shouldAddExpansionMarker) {
        this(ontology, AbstractMacroExpansionVisitor.EMPTY_ANNOTATIONS, shouldTransferAnnotations,
            shouldAddExpansionMarker);
    }

    /**
     * @param inputOntology inputOntology
     * @param extraAnnotations extra annotations to add
     * @param shouldTransferAnnotations true if annotations should be transferred
     * @param shouldAddExpansionMarker true if expansions should be added
     */
    public MacroExpansionVisitor(OWLOntology inputOntology, Set<OWLAnnotation> extraAnnotations,
        boolean shouldTransferAnnotations, boolean shouldAddExpansionMarker) {
        this.inputOntology = inputOntology;
        this.extraAnnotations = extraAnnotations;
        this.shouldTransferAnnotations = shouldTransferAnnotations;
        this.shouldAddExpansionMarker = shouldAddExpansionMarker;
        visitor = new Visitor(inputOntology, shouldAddExpansionMarker);
        visitor.rebuild(inputOntology);
        manager = inputOntology.getOWLOntologyManager();
        dataVisitor = new AbstractDataVisitorEx(manager.getOWLDataFactory());
    }

    /**
     * @return new MacroExpansions
     */
    public MacroExpansions getMacroExpansions() {
        return new MacroExpansions();
    }

    /**
     * @return ontology with expanded macros
     */
    public OWLOntology expandAll() {
        MacroExpansions macroExpansions = new MacroExpansions();
        Set<OWLAxiom> newAxioms = macroExpansions.getNewAxioms();
        Set<OWLAxiom> rmAxioms = macroExpansions.getRmAxioms();
        inputOntology.add(newAxioms);
        inputOntology.remove(rmAxioms);
        return inputOntology;
    }

    /**
     * @return true if annotations should be transferred
     */
    public boolean shouldTransferAnnotations() {
        return shouldTransferAnnotations;
    }

    /**
     * @param shouldTransferAnnotations new value
     */
    public void setShouldTransferAnnotations(boolean shouldTransferAnnotations) {
        this.shouldTransferAnnotations = shouldTransferAnnotations;
    }

    /**
     * Call this method to clear internal references.
     */
    public void dispose() {
        visitor.getTool().dispose();
    }

    private class MacroExpansions {

        private final Set<OWLAxiom> newAxioms = new HashSet<>();
        private final Set<OWLAxiom> rmAxioms = new HashSet<>();

        public MacroExpansions() {
            inputOntology.axioms(AxiomType.SUBCLASS_OF).forEach(axiom -> {
                OWLAxiom newAxiom = axiom.accept(visitor);
                replaceIfDifferent(axiom, newAxiom);
            });
            inputOntology.axioms(AxiomType.EQUIVALENT_CLASSES).forEach(axiom -> {
                OWLAxiom newAxiom = axiom.accept(visitor);
                replaceIfDifferent(axiom, newAxiom);
            });
            inputOntology.axioms(AxiomType.CLASS_ASSERTION).forEach(axiom -> {
                OWLAxiom newAxiom = axiom.accept(visitor);
                replaceIfDifferent(axiom, newAxiom);
            });
            add(rmAxioms, inputOntology.axioms(AxiomType.ANNOTATION_ASSERTION)
                .filter(this::expand));
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

        private boolean expand(OWLAnnotationAssertionAxiom axiom) {
            OWLAnnotationProperty prop = axiom.getProperty();
            String expandTo = visitor.expandAssertionToMap.get(prop.getIRI());
            HashSet<OWLAxiom> declarations = new HashSet<>();
            AtomicBoolean expandedSomething = new AtomicBoolean(false);
            try {
                if (expandTo != null) {
                    Set<OWLAnnotation> annotations = new HashSet<>(extraAnnotations);
                    if (shouldAddExpansionMarker) {
                        annotations.add(visitor.getExpansionMarkerAnnotation());
                    }
                    if (shouldTransferAnnotations()) {
                        add(annotations, axiom.annotations());
                    }
                    // when expanding assertions, the axiom is an annotation
                    // assertion, and the value may be not be explicitly
                    // declared. If it is
                    // not, we assume it is a class
                    IRI axValIRI = (IRI) axiom.getValue();
                    OWLDataFactory dataFactory = visitor.df;
                    OWLClass axValClass = dataFactory.getOWLClass(axValIRI);
                    if (asList(inputOntology.declarationAxioms(axValClass)).isEmpty()) {
                        OWLDeclarationAxiom declarationAxiom =
                            dataFactory.getOWLDeclarationAxiom(axValClass, annotations);
                        declarations.add(declarationAxiom);
                        newAxioms.add(declarationAxiom);
                        manager.addAxiom(inputOntology, declarationAxiom);
                        // we need to sync the MST entity checker with the new
                        // ontology plus declarations;
                        // we do this by creating a new MST - this is not
                        // particularly efficient, a better
                        // way might be to first scan the ontology for all
                        // annotation axioms that will be expanded,
                        // then add the declarations at this point
                        visitor.rebuild(inputOntology);
                    }
                    LOG.info("Template to Expand {}", expandTo);
                    expandTo = expandTo.replaceAll("\\?X",
                        visitor.getTool().getId((IRI) axiom.getSubject()));
                    expandTo = expandTo.replaceAll("\\?Y", visitor.getTool().getId(axValIRI));
                    LOG.info("Expanding {}", expandTo);
                    try {
                        expandAndAddAnnotations(expandTo, expandedSomething, annotations);
                    } catch (Exception ex) {
                        LOG.error(ex.getMessage(), ex);
                    }
                    // TODO:
                }
            } finally {
                inputOntology.remove(declarations);
            }
            return expandedSomething.get();
        }

        protected void expandAndAddAnnotations(String expandTo, AtomicBoolean expandedSomething,
            Set<OWLAnnotation> annotations) {
            visitor.getTool().parseManchesterExpressionFrames(expandTo).stream()
                .map(axp -> axp.getAxiom())
                .map(ax -> shouldTransferAnnotations()
                    ? ax.getAnnotatedAxiom(annotations)
                    : ax)
                .forEach(expandedAxiom -> {
                    newAxioms.add(expandedAxiom);
                    expandedSomething.set(true);
                });
        }
    }

    private class Visitor extends AbstractMacroExpansionVisitor {

        Visitor(OWLOntology inputOntology, boolean shouldAddExpansionMarker) {
            super(inputOntology, shouldAddExpansionMarker);
            rangeVisitor = dataVisitor;
            classVisitor = new AbstractMacroExpansionVisitor.AbstractClassExpressionVisitorEx() {

                @Override
                @Nullable
                protected OWLClassExpression expandOWLObjSomeVal(OWLClassExpression filler,
                    OWLObjectPropertyExpression p) {
                    return expandObject(filler, p);
                }

                @Override
                @Nullable
                protected OWLClassExpression expandOWLObjHasVal(OWLObjectHasValue desc,
                    OWLIndividual filler, OWLObjectPropertyExpression p) {
                    OWLClassExpression result = expandObject(filler, p);
                    if (result != null) {
                        result = df.getOWLObjectSomeValuesFrom(desc.getProperty(), result);
                    }
                    return result;
                }
            };
        }
    }
}
