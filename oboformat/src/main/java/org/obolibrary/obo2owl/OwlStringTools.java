package org.obolibrary.obo2owl;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.functional.renderer.OWLFunctionalSyntaxRenderer;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFactory;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.utilities.Injector;

/**
 * Tools to read and write a set of owl axioms to/from a string. Used to preserve untranslatable
 * axioms in an owl2obo conversion.
 */
public class OwlStringTools {

    /**
     * Exception indicating an un-recoverable error during the handling of axiom strings.
     */
    public static class OwlStringException extends Exception {

        // generated
        private static final long serialVersionUID = 40000L;

        /**
         * @param cause cause
         */
        protected OwlStringException(Throwable cause) {
            super(cause);
        }
    }

    private static final Injector injector = injector();

    /**
     * Create a string for the given set of axioms. Return null for empty sets or if the set is
     * null.
     * 
     * @param axioms axioms
     * @param translationManager translation manager
     * @return string or null
     * @throws OwlStringException any exception
     * @see #translate(String, OWLOntologyManager)
     */
    @Nullable
    public static String translate(@Nullable Set<OWLAxiom> axioms,
        @Nonnull OWLOntologyManager translationManager) throws OwlStringException {
        if (axioms == null || axioms.isEmpty()) {
            return null;
        }
        try {

            OWLOntologyManager m =
                injector.inject(injector.getImplementation(OWLOntologyManager.class));
            Set<OWLOntologyFactory> set = new HashSet<>();
            translationManager.getOntologyFactories().forEach(set::add);
            m.setOntologyFactories(set);
            OWLOntology ontology = m.createOntology();
            ontology.getOWLOntologyManager().addAxioms(ontology, axioms);
            OWLFunctionalSyntaxRenderer r = new OWLFunctionalSyntaxRenderer();
            Writer writer = new StringWriter();
            r.render(ontology, writer);
            return writer.toString();
        } catch (OWLRendererException e) {
            throw new OwlStringException(e);
        } catch (OWLOntologyCreationException e) {
            throw new OwlStringException(e);
        }
    }

    private static Injector injector() {
        Injector i = new Injector();
        i.bindToOne(() -> new ReentrantReadWriteLock(), ReadWriteLock.class);
        return i;
    }

    /**
     * Parse the axioms from the given axiom string. Returns null for empty and null strings.
     * 
     * @param axioms axioms
     * @param translationManager translation manager
     * @return set of axioms or null
     * @throws OwlStringException any exception
     * @see #translate(Set,OWLOntologyManager)
     */
    @Nullable
    public static Set<OWLAxiom> translate(@Nullable String axioms,
        @Nonnull OWLOntologyManager translationManager) throws OwlStringException {
        if (axioms == null || axioms.isEmpty()) {
            return null;
        }
        try {
            OWLOntologyDocumentSource documentSource = new StringDocumentSource(axioms,
                IRI.generateDocumentIRI(), new FunctionalSyntaxDocumentFormat(), null);
            OWLOntologyManager m =
                injector.inject(injector.getImplementation(OWLOntologyManager.class));
            Set<OWLOntologyFactory> set = new HashSet<>();
            translationManager.getOntologyFactories().forEach(set::add);
            m.setOntologyFactories(set);
            return m.loadOntologyFromOntologyDocument(documentSource).getAxioms();
        } catch (UnloadableImportException e) {
            throw new OwlStringException(e);
        } catch (OWLOntologyCreationException e) {
            throw new OwlStringException(e);
        } catch (OWLParserException e) {
            throw new OwlStringException(e);
        }
    }
}
