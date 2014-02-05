/**
 * 
 */
package org.coode.owlapi.latex;

import org.kohsuke.MetaInfServices;
import org.semanticweb.owlapi.formats.LatexOntologyFormatFactory;
import org.semanticweb.owlapi.formats.OWLOntologyFormatFactory;
import org.semanticweb.owlapi.model.OWLOntologyStorer;
import org.semanticweb.owlapi.model.OWLOntologyStorerFactory;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 *
 */
@MetaInfServices(org.semanticweb.owlapi.model.OWLOntologyStorerFactory.class)
public class LatexOntologyStorerFactory implements OWLOntologyStorerFactory
{
    
    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLOntologyStorerFactory#createStorer()
     */
    @Override
    public OWLOntologyStorer createStorer() {
        return new LatexOntologyStorer();
    }
    
    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLOntologyStorerFactory#getFormat()
     */
    @Override
    public OWLOntologyFormatFactory getFormatFactory() {
        return new LatexOntologyFormatFactory();
    }
    
}
