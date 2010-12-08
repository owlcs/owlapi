package uk.ac.manchester.cs.owl.owlapi.alternateimpls;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import uk.ac.manchester.cs.owl.owlapi.Internals;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedObjectReferenceAdder;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedObjectReferenceAdderImpl;

public class SyncOWLNamedObjectReferenceAdderImpl implements OWLNamedObjectReferenceAdder{
	private final OWLNamedObjectReferenceAdderImpl delegate;
        
        private final ReadWriteLock lock;
        
        public SyncOWLNamedObjectReferenceAdderImpl(Internals oi, ReadWriteLock lock) {
        	delegate=new OWLNamedObjectReferenceAdderImpl(oi);
			this.lock=lock;
		}


        public void setAxiom(OWLAxiom axiom) {
            this.delegate.setAxiom(axiom);
        }


        public void visit(OWLClass owlClass) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(owlClass);
        	}finally {
        		l.unlock();
        	}

        }


        public void visit(OWLObjectProperty property) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(property);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLDataProperty property) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(property);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLNamedIndividual owlIndividual) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(owlIndividual);
        	}finally {
        		l.unlock();
        	}
        }

        public void visit(OWLAnnotationProperty property) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(property);
        	}finally {
        		l.unlock();
        	}
        }

        public void visit(OWLDatatype datatype) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(datatype);
        	}finally {
        		l.unlock();
        	}
        }
    }