package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.io.RDFOntologyFormat;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.RemoveImport;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Dec-2006<br><br>
 */
public class TPImportsHandler extends TriplePredicateHandler {


    public TPImportsHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_IMPORTS.getIRI());
    }


    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return true;
    }

    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {

        consumeTriple(subject, predicate, object);
        getConsumer().addOntology(subject);
        getConsumer().addOntology(object);
        OWLImportsDeclaration importsDeclaration = getDataFactory().getOWLImportsDeclaration(object);
        getConsumer().addImport(importsDeclaration);

        if (!getConsumer().getConfiguration().isIgnoredImport(object)) {
            OWLOntologyManager man = getConsumer().getOWLOntologyManager();
            man.makeLoadImportRequest(importsDeclaration, getConsumer().getConfiguration());


            OWLOntology importedOntology = man.getImportedOntology(importsDeclaration);
            if (importedOntology != null) {
                OWLOntologyFormat importedOntologyFormat = man.getOntologyFormat(importedOntology);
                if (importedOntologyFormat instanceof RDFOntologyFormat) {
                    if (importedOntology.isAnonymous()) {
                        OWLOntologyLoaderConfiguration.MissingOntologyHeaderStrategy missingOntologyHeaderStrategy = getConsumer().getConfiguration().getMissingOntologyHeaderStrategy();
                        boolean includeGraph = missingOntologyHeaderStrategy.equals(OWLOntologyLoaderConfiguration.MissingOntologyHeaderStrategy.INCLUDE_GRAPH);

                        if (includeGraph) {
                            // We should have just included the triples rather than imported them. So,
                            // we remove the imports statement, add the axioms from the imported ontology to
                            // out importing ontology and remove the imported ontology.
                            // WHO EVER THOUGHT THAT THIS WAS A GOOD IDEA?
                            man.applyChange(new RemoveImport(getConsumer().getOntology(), importsDeclaration));

                            for (OWLImportsDeclaration decl : importedOntology.getImportsDeclarations()) {
                                man.applyChange(new AddImport(getConsumer().getOntology(), decl));
                            }
                            for (OWLAnnotation anno : importedOntology.getAnnotations()) {
                                man.applyChange(new AddOntologyAnnotation(getConsumer().getOntology(), anno));
                            }
                            for (OWLAxiom ax : importedOntology.getAxioms()) {
                                getConsumer().addAxiom(ax);
                            }
                            man.removeOntology(importedOntology);
                        }

                    }
                }
            }

            getConsumer().importsClosureChanged();

        }

    }
}
