package org.coode.owlapi.oboformat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.coode.owlapi.obo.parser.OBOOntologyFormat;
import org.obolibrary.obo2owl.OWLAPIObo2Owl;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatParser;
import org.obolibrary.oboformat.parser.OBOFormatParserException;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.UnloadableImportException;

/** oboformat parser */
public class OBOFormatOWLAPIParser implements OWLParser {

    private OWLOntologyManager manager;

    /**
     * @param m
     *        manager
     */
    public OBOFormatOWLAPIParser(OWLOntologyManager m) {
        manager = m;
    }

    @Override
    public void setOWLOntologyManager(OWLOntologyManager owlOntologyManager) {
        manager = owlOntologyManager;
    }

    @Override
    public OWLOntologyFormat parse(IRI documentIRI, OWLOntology ontology)
            throws OWLParserException, IOException, OWLOntologyChangeException,
            UnloadableImportException {
        try {
            parse(documentIRI, null, ontology);
        } catch (OBOFormatParserException e) {
            throw new OWLParserException(e);
        } catch (OWLOntologyCreationException e) {
            throw new OWLParserException(e);
        }
        OBOOntologyFormat format = new OBOOntologyFormat();
        return format;
    }

    @Override
    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource,
            OWLOntology ontology) throws OWLParserException, IOException,
            OWLOntologyChangeException, UnloadableImportException {
        try {
            parse(null, documentSource, ontology);
        } catch (OBOFormatParserException e) {
            throw new OWLParserException(e);
        } catch (OWLOntologyCreationException e) {
            throw new OWLParserException(e);
        }
        OBOOntologyFormat format = new OBOOntologyFormat();
        return format;
    }

    @Override
    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource,
            OWLOntology ontology, OWLOntologyLoaderConfiguration configuration)
            throws OWLParserException, IOException, OWLOntologyChangeException,
            UnloadableImportException {
        try {
            parse(null, documentSource, ontology);
        } catch (OBOFormatParserException e) {
            throw new OWLParserException(e);
        } catch (OWLOntologyCreationException e) {
            throw new OWLParserException(e);
        }
        OBOOntologyFormat format = new OBOOntologyFormat();
        return format;
    }

    private OWLOntology parse(IRI iri, OWLOntologyDocumentSource source,
            OWLOntology in) throws OBOFormatParserException,
            MalformedURLException, IOException, OWLOntologyCreationException {
        OBOFormatParser p = new OBOFormatParser();
        OBODoc obodoc = null;
        if (iri != null) {
            obodoc = p.parse(iri.toURI().toURL());
        } else {
            if (source.isReaderAvailable()) {
                obodoc = p.parse(new BufferedReader(source.getReader()));
            } else if (source.isInputStreamAvailable()) {
                obodoc = p.parse(new BufferedReader(new InputStreamReader(
                        source.getInputStream())));
            } else {
                return parse(source.getDocumentIRI(), null, in);
            }
        }
        // create a translator object and feed it the OBO Document
        OWLAPIObo2Owl bridge = new OWLAPIObo2Owl(manager);
        OWLOntology ontology = bridge.convert(obodoc, in);
        if (ontology == in) {
            return in;
        }
        return ontology;
    }
}
