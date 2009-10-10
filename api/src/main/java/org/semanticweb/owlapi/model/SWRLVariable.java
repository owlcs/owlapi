package org.semanticweb.owlapi.model;

import java.net.URI;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 15, 2007<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 * Represents a varaible that can appear in an atom. Variable can either be place holders for individuals
 * or literals. Variables are named with IRIs.
 */
public interface SWRLVariable extends SWRLIArgument, SWRLDArgument {

    /**
     * Gets the variable IRI that names the variable
     * @return The IRI that represents the name of the variable
     */
    IRI getIRI();

}
