package org.coode.owlapi.manchesterowlsyntax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntologyID;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 24-Mar-2009
 */
public class ManchesterOWLSyntaxOntologyHeader {

    private OWLOntologyID ontologyID;

    private Collection<OWLAnnotation> annotations;

    private Collection<OWLImportsDeclaration> importsDeclarations;


    public ManchesterOWLSyntaxOntologyHeader(IRI ontologyIRI,
                                             IRI versionIRI,
                                             Set<OWLAnnotation> annotations,
                                             Set<OWLImportsDeclaration> importsDeclarations) {
        this.ontologyID = new OWLOntologyID(ontologyIRI, versionIRI);
        this.annotations = new ArrayList<OWLAnnotation>(annotations);
        this.importsDeclarations = new ArrayList<OWLImportsDeclaration>(importsDeclarations);
    }


    public OWLOntologyID getOntologyID() {
        return ontologyID;
    }


    public Collection<OWLAnnotation> getAnnotations() {
        return annotations;
    }


    public Collection<OWLImportsDeclaration> getImportsDeclarations() {
        return importsDeclarations;
    }
}
