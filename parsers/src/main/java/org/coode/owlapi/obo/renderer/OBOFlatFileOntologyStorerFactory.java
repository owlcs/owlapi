/**
 * 
 */
package org.coode.owlapi.obo.renderer;

import org.kohsuke.MetaInfServices;
import org.semanticweb.owlapi.formats.OBOOntologyFormatFactory;
import org.semanticweb.owlapi.formats.OWLOntologyFormatFactory;
import org.semanticweb.owlapi.model.OWLOntologyStorer;
import org.semanticweb.owlapi.model.OWLOntologyStorerFactory;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 *
 */
// Use new OBO storer
//@MetaInfServices(org.semanticweb.owlapi.model.OWLOntologyStorerFactory.class)
public class OBOFlatFileOntologyStorerFactory implements OWLOntologyStorerFactory
{
    @Override
    public OWLOntologyStorer createStorer() {
        return new OBOFlatFileOntologyStorer();
    }
    
    @Override
    public OWLOntologyFormatFactory getFormatFactory() {
        return new OBOOntologyFormatFactory();
    }
    
}
