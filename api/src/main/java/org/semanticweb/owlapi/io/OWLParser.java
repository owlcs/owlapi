package org.semanticweb.owlapi.io;

import java.io.IOException;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.UnloadableImportException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Nov-2006<br><br>
 * </p>
 * An <code>OWLParser</code> parses an ontology document into an OWL API object representation of an ontology.
 *
 */
public interface OWLParser {

    /**
     * Sets the <code>OWLOntologyManager</code> which should be used to load
     * imports etc.
     * @param owlOntologyManager
     */
    void setOWLOntologyManager(OWLOntologyManager owlOntologyManager);

    /**
     * Parses the ontology that has a concrete representation which is pointed
     * to by the specified document IRI.  Implementors of this method should
     * load any imported ontologies with the loadImports method on OWLOntologyManager.
     * @param documentIRI The document IRI where the ontology should be loaded from
     *@param ontology The ontology that the concrete representation should be
     * parsed into.  @return An <code>OWLOntologyFormat</code> which describes the concrete representation
     * format which was parsed to obtain the ontology. This will never be <code>null</code>.
     * @return The format of the ontology
     * @throws OWLParserException if there was a problem parsing the ontology.  This indicates an error in the syntax
     * with this ontology document that the parser reads.
     * @throws IOException if there was an IOException during parsing
     * @throws OWLOntologyChangeException if there was a problem updating the specified ontology from information
     * that was parsed.
     * @throws UnloadableImportException if loading this ontology prompted the loading of an import and the import
     * could not be loaded.
     */
    OWLOntologyFormat parse(IRI documentIRI, OWLOntology ontology) throws OWLParserException, IOException, OWLOntologyChangeException, UnloadableImportException;


    /**
     * Parses the ontology that has a concrete representation which is pointed to
     * by the specified input source. Implementors of this method should
     * load any imported ontologies with the makeImportsLoadRequest method on OWLOntologyManager.
     * @param documentSource The input source which points the concrete representation.  If
     * the input source can provide a <code>Reader</code> then the ontology is parsed
     * from the <code>Reader</code>.  If the input source cannot provide a reader then
     * it is parsed from the <code>InputStream</code>.  If the input source cannot provide
     * an <code>InputStream</code> then it is parsed from the ontology document IRI.
     * @param ontology The ontology which the representation will be parsed into
     * @return An <code>OWLOntologyFormat</code> which describes the concrete representation
     * format which was parsed to obtain the ontology.
     * @throws OWLParserException if there was a problem parsing the ontology.  This indicates an error in the syntax
     * with this ontology document that the parser reads.
     * @throws IOException if there was an IOException during parsing
     * @throws OWLOntologyChangeException if there was a problem updating the specified ontology from information
     * that was parsed.
     * @throws UnloadableImportException if loading this ontology prompted the loading of an import and the import
     * could not be loaded.
     */
    OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource, OWLOntology ontology) throws OWLParserException, IOException, OWLOntologyChangeException, UnloadableImportException;

    /**
     * Parses the ontology that has a concrete representation which is pointed to
     * by the specified input source. Implementors of this method should
     * load any imported ontologies with the makeImportsLoadRequest method on OWLOntologyManager.
     * @param documentSource The input source which points the concrete representation.  If
     * the input source can provider a <code>Reader</code> then the ontology is parsed
     * from the <code>Reader</code>.  If the input source cannot provide a reader then
     * it is parsed from the <code>InputStream</code>.  If the input source cannot provide
     * an <code>InputStream</code> then it is parsed from the ontology document IRI.
     * @param ontology The ontology which the representation will be parsed into
     * @param configuration A configuration object that provides various generic options to the parser.
     * @return An <code>OWLOntologyFormat</code> which describes the concrete representation
     * format which was parsed to obtain the ontology.
     * @throws OWLParserException if there was a problem parsing the ontology.  This indicates an error in the syntax
     * with this ontology document that the parser reads.
     * @throws IOException if there was an IOException during parsing
     * @throws OWLOntologyChangeException if there was a problem updating the specified ontology from information
     * that was parsed.
     * @throws UnloadableImportException if loading this ontology prompted the loading of an import and the import
     * could not be loaded.
     */
    OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource, OWLOntology ontology, OWLOntologyLoaderConfiguration configuration) throws OWLParserException, IOException, OWLOntologyChangeException, UnloadableImportException;
}
