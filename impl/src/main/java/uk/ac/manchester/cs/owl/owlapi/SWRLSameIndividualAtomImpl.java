package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 18-Feb-2007<br><br>
 */
public class SWRLSameIndividualAtomImpl extends SWRLBinaryAtomImpl<SWRLIArgument, SWRLIArgument> implements SWRLSameIndividualAtom {

    public SWRLSameIndividualAtomImpl(OWLDataFactory dataFactory, SWRLIArgument arg0, SWRLIArgument arg1) {
        super(dataFactory, dataFactory.getOWLObjectProperty(OWLRDFVocabulary.OWL_SAME_AS.getIRI()), arg0, arg1);
    }


    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }


    public void accept(SWRLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(SWRLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
	public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SWRLSameIndividualAtom)) {
            return false;
        }
        SWRLSameIndividualAtom other = (SWRLSameIndividualAtom) obj;
        return other.getAllArguments().equals(getAllArguments());
    }
}
