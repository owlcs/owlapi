package uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntax;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 11-Mar-2010
 */
public interface RenderingDirector {

    boolean renderEmptyFrameSection(ManchesterOWLSyntax frameSectionKeyword, OWLOntology ... ontologies);

}
