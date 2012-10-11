/**
 * 
 */
package de.uulm.ecs.ai.owlapi.krssrenderer;

import org.kohsuke.MetaInfServices;
import org.semanticweb.owlapi.formats.KRSS2OntologyFormatFactory;
import org.semanticweb.owlapi.formats.OWLOntologyFormatFactory;
import org.semanticweb.owlapi.model.OWLOntologyStorer;
import org.semanticweb.owlapi.model.OWLOntologyStorerFactory;


/**
 * @author Peter Ansell p_ansell@yahoo.com
 *
 */
@MetaInfServices(org.semanticweb.owlapi.model.OWLOntologyStorerFactory.class)
public class KRSS2OWLSyntaxOntologyStorerFactory implements OWLOntologyStorerFactory
{
    
    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLOntologyStorerFactory#createStorer()
     */
    @Override
    public OWLOntologyStorer createStorer() {
        return new KRSS2OWLSyntaxOntologyStorer();
    }
    
    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLOntologyStorerFactory#getFormat()
     */
    @Override
    public OWLOntologyFormatFactory getFormatFactory() {
        return new KRSS2OntologyFormatFactory();
    }
    
}
