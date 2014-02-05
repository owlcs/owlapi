/**
 * 
 */
package org.coode.owlapi.functionalrenderer;

import org.kohsuke.MetaInfServices;
import org.semanticweb.owlapi.formats.OWLFunctionalSyntaxOntologyFormatFactory;
import org.semanticweb.owlapi.formats.OWLOntologyFormatFactory;
import org.semanticweb.owlapi.model.OWLOntologyStorer;
import org.semanticweb.owlapi.model.OWLOntologyStorerFactory;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 *
 */
@MetaInfServices(org.semanticweb.owlapi.model.OWLOntologyStorerFactory.class)
public class OWLFunctionalSyntaxOntologyStorerFactory implements OWLOntologyStorerFactory
{
    
    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLOntologyStorerFactory#createStorer()
     */
    @Override
    public OWLOntologyStorer createStorer() {
        return new OWLFunctionalSyntaxOntologyStorer();
    }
    
    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLOntologyStorerFactory#getFormat()
     */
    @Override
    public OWLOntologyFormatFactory getFormatFactory() {
        return new OWLFunctionalSyntaxOntologyFormatFactory();
    }
    
}
