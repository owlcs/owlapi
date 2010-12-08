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
import uk.ac.manchester.cs.owl.owlapi.OWLNamedObjectReferenceRemover;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedObjectReferenceRemoverImpl;

public class SyncOWLNamedObjectReferenceRemoverImpl implements
		OWLNamedObjectReferenceRemover {
	private final OWLNamedObjectReferenceRemoverImpl delegate;
	private ReadWriteLock lock;

	public SyncOWLNamedObjectReferenceRemoverImpl(Internals oi,
			ReadWriteLock lock) {
		delegate = new OWLNamedObjectReferenceRemoverImpl(oi);
		this.lock = lock;
	}

	public void visit(OWLClass cls) {
		Lock l = lock.writeLock();
		l.lock();
		try {
			delegate.visit(cls);
		} finally {
			l.unlock();
		}
	}

	public void visit(OWLObjectProperty property) {
		Lock l = lock.writeLock();
		l.lock();
		try {
			delegate.visit(property);
		} finally {
			l.unlock();
		}
	}

	public void visit(OWLDataProperty property) {
		Lock l = lock.writeLock();
		l.lock();
		try {
			delegate.visit(property);
		} finally {
			l.unlock();
		}
	}

	public void visit(OWLNamedIndividual individual) {
		Lock l = lock.writeLock();
		l.lock();
		try {
			delegate.visit(individual);
		} finally {
			l.unlock();
		}
	}

	public void visit(OWLDatatype datatype) {
		Lock l = lock.writeLock();
		l.lock();
		try {
			delegate.visit(datatype);
		} finally {
			l.unlock();
		}
	}

	public void visit(OWLAnnotationProperty property) {
		Lock l = lock.writeLock();
		l.lock();
		try {
			delegate.visit(property);
		} finally {
			l.unlock();
		}
	}

	public void setAxiom(OWLAxiom axiom) {
		this.delegate.setAxiom(axiom);
	}
}
