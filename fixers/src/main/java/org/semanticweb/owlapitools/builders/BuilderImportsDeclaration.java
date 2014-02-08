package org.semanticweb.owlapitools.builders;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;

/** Builder class for OWLImportsDeclaration */
public class BuilderImportsDeclaration implements
        Builder<OWLImportsDeclaration> {
    // XXX inject
    protected final OWLDataFactory df;
    private IRI iri;

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderImportsDeclaration(OWLImportsDeclaration expected,
            OWLDataFactory df) {
        this(df);
        withImportedOntology(expected.getIRI());
    }

    /** default constructor
     * 
     * @param df
     *            data factory */
    public BuilderImportsDeclaration(OWLDataFactory df) {
        this.df = checkNotNull(df);
    }

    /** @param arg
     *            IRI of imported ontology
     * @return builder */
    public BuilderImportsDeclaration withImportedOntology(IRI arg) {
        iri = arg;
        return this;
    }

    @Override
    public OWLImportsDeclaration buildObject() {
        return df.getOWLImportsDeclaration(iri);
    }

    @Override
    public List<OWLOntologyChange<?>> buildChanges(OWLOntology o) {
        List<OWLOntologyChange<?>> list = new ArrayList<OWLOntologyChange<?>>();
        list.add(new AddImport(o, buildObject()));
        return list;
    }
}
