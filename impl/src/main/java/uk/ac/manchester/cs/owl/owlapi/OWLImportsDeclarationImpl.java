package uk.ac.manchester.cs.owl.owlapi;

import java.net.URI;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLImportsDeclarationImpl implements OWLImportsDeclaration {

    private IRI iri;

    public OWLImportsDeclarationImpl(IRI iri) {
        this.iri = iri;
    }

    public boolean isLogicalAxiom() {
        return false;
    }


    public IRI getIRI() {
        return iri;
    }


    public URI getURI() {
        return iri.toURI();
    }


    @Override
	public int hashCode() {
        return iri.hashCode();
    }


    @Override
	public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLImportsDeclaration)) {
            return false;
        }
        OWLImportsDeclaration other = (OWLImportsDeclaration) obj;
        return this.iri.equals(other.getIRI());
    }


    public int compareTo(OWLImportsDeclaration o) {
        return iri.compareTo(o.getIRI());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Import(");
        sb.append(iri.toQuotedString());
        sb.append(")");
        return sb.toString();
    }
}
