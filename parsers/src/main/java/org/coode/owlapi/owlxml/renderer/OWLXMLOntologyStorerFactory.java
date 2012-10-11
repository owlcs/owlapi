/**
 * 
 */
package org.coode.owlapi.owlxml.renderer;

import org.kohsuke.MetaInfServices;
import org.semanticweb.owlapi.formats.OWLOntologyFormatFactory;
import org.semanticweb.owlapi.formats.OWLXMLOntologyFormatFactory;
import org.semanticweb.owlapi.model.OWLOntologyStorer;
import org.semanticweb.owlapi.model.OWLOntologyStorerFactory;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 *
 */
@MetaInfServices(org.semanticweb.owlapi.model.OWLOntologyStorerFactory.class)
public class OWLXMLOntologyStorerFactory implements OWLOntologyStorerFactory
{
    
    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLOntologyStorerFactory#createStorer()
     */
    @Override
    public OWLOntologyStorer createStorer() {
        return new OWLXMLOntologyStorer();
    }
    
    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLOntologyStorerFactory#getFormat()
     */
    @Override
    public OWLOntologyFormatFactory getFormatFactory() {
        return new OWLXMLOntologyFormatFactory();
    }
    
}
