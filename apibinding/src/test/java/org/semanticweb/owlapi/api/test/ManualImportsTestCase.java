package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 18-Jan-2010
 */
public class ManualImportsTestCase extends AbstractOWLAPITestCase {

    public void testManualImports() throws Exception {
        OWLOntologyManager manager = getManager();
        OWLOntology baseOnt = manager.createOntology(IRI.create("http://semanticweb.org/ontologies/base"));
        IRI importedIRI = IRI.create("http://semanticweb.org/ontologies/imported");
        OWLOntology importedOnt = manager.createOntology(importedIRI);
        Set<OWLOntology> preImportsClosureCache = new HashSet<OWLOntology>(manager.getImportsClosure(baseOnt));
        assertTrue(preImportsClosureCache.contains(baseOnt));
        assertFalse(preImportsClosureCache.contains(importedOnt));
        manager.applyChange(new AddImport(baseOnt, manager.getOWLDataFactory().getOWLImportsDeclaration(importedIRI)));
        Set<OWLOntology> postImportsClosureCache = new HashSet<OWLOntology>(manager.getImportsClosure(baseOnt));
        assertTrue(postImportsClosureCache.contains(baseOnt));
        assertTrue(postImportsClosureCache.contains(importedOnt));

    }
}
