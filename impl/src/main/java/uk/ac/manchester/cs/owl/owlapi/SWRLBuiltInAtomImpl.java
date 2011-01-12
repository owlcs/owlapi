package uk.ac.manchester.cs.owl.owlapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
import org.semanticweb.owlapi.vocab.SWRLBuiltInsVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Jan-2007<br><br>
 */
public class SWRLBuiltInAtomImpl extends SWRLAtomImpl implements SWRLBuiltInAtom {

    private List<SWRLDArgument> args;


    public SWRLBuiltInAtomImpl(OWLDataFactory dataFactory, IRI predicate, List<SWRLDArgument> args) {
        super(dataFactory, predicate);
        this.args = new ArrayList<SWRLDArgument>(args);
    }

    @Override
	public IRI getPredicate() {
        return (IRI) super.getPredicate();
    }

    /**
     * Determines if the predicate of this atom is is a core builtin.
     * @return <code>true</code> if this is a core builtin, otherwise <code>false</code>
     */
    public boolean isCoreBuiltIn() {
        return SWRLBuiltInsVocabulary.getBuiltIn(getPredicate().toURI()) != null;
    }

    public List<SWRLDArgument> getArguments() {
        return new ArrayList<SWRLDArgument>(args);
    }


    public Collection<SWRLArgument> getAllArguments() {
        return new ArrayList<SWRLArgument>(args);
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
        if (!(obj instanceof SWRLBuiltInAtom)) {
            return false;
        }
        SWRLBuiltInAtom other = (SWRLBuiltInAtom) obj;
        return other.getPredicate().equals(getPredicate()) && other.getArguments().equals(getArguments());
    }


    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        SWRLBuiltInAtom other = (SWRLBuiltInAtom) object;
        int diff = getPredicate().compareTo(other.getPredicate());
        if (diff != 0) {
            return diff;
        }
        List<SWRLDArgument> otherArgs = other.getArguments();
        int i = 0;
        while (i < args.size() && i < otherArgs.size()) {
            diff = args.get(i).compareTo(otherArgs.get(i));
            if (diff != 0) {
                return diff;
            }
            i++;
        }
        return args.size() - otherArgs.size();
    }
}
