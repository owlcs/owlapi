package org.semanticweb.owlapi.debugging;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 24-Nov-2006<br><br>
 */
public interface OWLDebugger {

    /**
     * Gets the <code>OWLOntology</code> that is being debugged.
     * @return
     * @throws OWLException
     */
    public OWLOntology getOWLOntology() throws OWLException;


    /**
     * Gets the first set of supporting (SOS) axioms that are responsible for the specified class
     * being inconsistent.
     * @param cls The class which is inconsistent
     * @return
     * @throws OWLException
     */
    public Set<OWLAxiom> getSOSForIncosistentClass(OWLClassExpression cls) throws OWLException;


    /**
     * Gets all sets of supporting axioms that are responsible for the specified class being inconsistent
     * @param cls
     * @return
     * @throws OWLException
     */
    public Set<Set<OWLAxiom>> getAllSOSForIncosistentClass(OWLClassExpression cls) throws OWLException;

    void dispose();
}
