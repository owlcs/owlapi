package uk.ac.manchester.cs.owl.owlapi.alternateimpls;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import org.semanticweb.owlapi.model.*;

import uk.ac.manchester.cs.owl.owlapi.ChangeAxiomVisitor;
import uk.ac.manchester.cs.owl.owlapi.Internals;

/**locking version of ChangeAxiomVisitor, used to effect add/remove actions; the lock is intended to protect axiomsByType*/
class SyncChangeAxiomVisitor implements OWLAxiomVisitor {
	private final ChangeAxiomVisitor delegate;
	private final ReadWriteLock lock;

        public SyncChangeAxiomVisitor( Internals oi, boolean add, ReadWriteLock lock) {
			this.delegate=new ChangeAxiomVisitor(oi, add);
			this.lock=lock;
		}

        public void visit(OWLSubClassOfAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLDisjointClassesAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLDataPropertyDomainAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }

        public void visit(OWLObjectPropertyDomainAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLDifferentIndividualsAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLObjectPropertyRangeAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLDisjointUnionAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLDeclarationAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLAnnotationAssertionAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }

        public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }

        public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }

        public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }

        public void visit(OWLHasKeyAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }

        public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLDataPropertyRangeAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLClassAssertionAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLEquivalentClassesAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLDataPropertyAssertionAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLSubDataPropertyOfAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLSameIndividualAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLSubPropertyChainOfAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(SWRLRule rule) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(rule);
        	}finally {
        		l.unlock();
        	}
        }


        public void visit(OWLDatatypeDefinitionAxiom axiom) {
        	Lock l=lock.writeLock();
        	l.lock();
        	try {
        		delegate.visit(axiom);
        	}finally {
        		l.unlock();
        	}
        }
    }