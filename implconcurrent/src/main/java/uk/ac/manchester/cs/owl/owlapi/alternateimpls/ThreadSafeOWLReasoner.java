package uk.ac.manchester.cs.owl.owlapi.alternateimpls;

import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.*;
import org.semanticweb.owlapi.util.Version;

public class ThreadSafeOWLReasoner implements OWLReasoner {
	private final OWLReasoner delegate;

	public ThreadSafeOWLReasoner(OWLReasoner reasoner) {
		if (reasoner == null) {
			throw new IllegalArgumentException(
					"The input reasoner cannot be null");
		}
		delegate = reasoner;
	}

	public synchronized String getReasonerName() {
		return this.delegate.getReasonerName();
	}

	public synchronized Version getReasonerVersion() {
		return this.delegate.getReasonerVersion();
	}

	public synchronized BufferingMode getBufferingMode() {
		return this.delegate.getBufferingMode();
	}

	public synchronized void flush() {
		this.delegate.flush();
	}

	public synchronized List<OWLOntologyChange> getPendingChanges() {
		return this.delegate.getPendingChanges();
	}

	public synchronized Set<OWLAxiom> getPendingAxiomAdditions() {
		return this.delegate.getPendingAxiomAdditions();
	}

	public synchronized Set<OWLAxiom> getPendingAxiomRemovals() {
		return this.delegate.getPendingAxiomRemovals();
	}

	public synchronized OWLOntology getRootOntology() {
		return this.delegate.getRootOntology();
	}

	public synchronized void interrupt() {
		this.delegate.interrupt();
	}

	public synchronized void precomputeInferences(InferenceType... inferenceTypes)
			throws ReasonerInterruptedException, TimeOutException,
			InconsistentOntologyException {
		this.delegate.precomputeInferences(inferenceTypes);
	}

	public synchronized boolean isPrecomputed(InferenceType inferenceType) {
		return this.delegate.isPrecomputed(inferenceType);
	}

	public synchronized Set<InferenceType> getPrecomputableInferenceTypes() {
		return this.delegate.getPrecomputableInferenceTypes();
	}

	public synchronized boolean isConsistent() throws ReasonerInterruptedException,
			TimeOutException {
		return this.delegate.isConsistent();
	}

	public synchronized boolean isSatisfiable(OWLClassExpression classExpression)
			throws ReasonerInterruptedException, TimeOutException,
			ClassExpressionNotInProfileException, FreshEntitiesException,
			InconsistentOntologyException {
		return this.delegate.isSatisfiable(classExpression);
	}

	public synchronized Node<OWLClass> getUnsatisfiableClasses()
			throws ReasonerInterruptedException, TimeOutException,
			InconsistentOntologyException {
		return this.delegate.getUnsatisfiableClasses();
	}

	public synchronized boolean isEntailed(OWLAxiom axiom)
			throws ReasonerInterruptedException,
			UnsupportedEntailmentTypeException, TimeOutException,
			AxiomNotInProfileException, FreshEntitiesException,
			InconsistentOntologyException {
		return this.delegate.isEntailed(axiom);
	}

	public synchronized boolean isEntailed(Set<? extends OWLAxiom> axioms)
			throws ReasonerInterruptedException,
			UnsupportedEntailmentTypeException, TimeOutException,
			AxiomNotInProfileException, FreshEntitiesException,
			InconsistentOntologyException {
		return this.delegate.isEntailed(axioms);
	}

	public synchronized boolean isEntailmentCheckingSupported(AxiomType<?> axiomType) {
		return this.delegate.isEntailmentCheckingSupported(axiomType);
	}

	public synchronized Node<OWLClass> getTopClassNode() {
		return this.delegate.getTopClassNode();
	}

	public synchronized Node<OWLClass> getBottomClassNode() {
		return this.delegate.getBottomClassNode();
	}

	public synchronized NodeSet<OWLClass> getSubClasses(OWLClassExpression ce, boolean direct)
			throws ReasonerInterruptedException, TimeOutException,
			FreshEntitiesException, InconsistentOntologyException,
			ClassExpressionNotInProfileException {
		return this.delegate.getSubClasses(ce, direct);
	}

	public synchronized NodeSet<OWLClass> getSuperClasses(OWLClassExpression ce,
			boolean direct) throws InconsistentOntologyException,
			ClassExpressionNotInProfileException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return this.delegate.getSuperClasses(ce, direct);
	}

	public synchronized Node<OWLClass> getEquivalentClasses(OWLClassExpression ce)
			throws InconsistentOntologyException,
			ClassExpressionNotInProfileException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return this.delegate.getEquivalentClasses(ce);
	}

	public synchronized NodeSet<OWLClass> getDisjointClasses(OWLClassExpression ce)
			throws ReasonerInterruptedException, TimeOutException,
			FreshEntitiesException, InconsistentOntologyException {
		return this.delegate.getDisjointClasses(ce);
	}

	public synchronized Node<OWLObjectPropertyExpression> getTopObjectPropertyNode() {
		return this.delegate.getTopObjectPropertyNode();
	}

	public synchronized Node<OWLObjectPropertyExpression> getBottomObjectPropertyNode() {
		return this.delegate.getBottomObjectPropertyNode();
	}

	public synchronized NodeSet<OWLObjectPropertyExpression> getSubObjectProperties(
			OWLObjectPropertyExpression pe, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return this.delegate.getSubObjectProperties(pe, direct);
	}

	public synchronized NodeSet<OWLObjectPropertyExpression> getSuperObjectProperties(
			OWLObjectPropertyExpression pe, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return this.delegate.getSuperObjectProperties(pe, direct);
	}

	public synchronized Node<OWLObjectPropertyExpression> getEquivalentObjectProperties(
			OWLObjectPropertyExpression pe)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return this.delegate.getEquivalentObjectProperties(pe);
	}

	public synchronized NodeSet<OWLObjectPropertyExpression> getDisjointObjectProperties(
			OWLObjectPropertyExpression pe)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return this.delegate.getDisjointObjectProperties(pe);
	}

	public synchronized Node<OWLObjectPropertyExpression> getInverseObjectProperties(
			OWLObjectPropertyExpression pe)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return this.delegate.getInverseObjectProperties(pe);
	}

	public synchronized NodeSet<OWLClass> getObjectPropertyDomains(
			OWLObjectPropertyExpression pe, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return this.delegate.getObjectPropertyDomains(pe, direct);
	}

	public synchronized NodeSet<OWLClass> getObjectPropertyRanges(
			OWLObjectPropertyExpression pe, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return this.delegate.getObjectPropertyRanges(pe, direct);
	}

	public synchronized Node<OWLDataProperty> getTopDataPropertyNode() {
		return this.delegate.getTopDataPropertyNode();
	}

	public synchronized Node<OWLDataProperty> getBottomDataPropertyNode() {
		return this.delegate.getBottomDataPropertyNode();
	}

	public synchronized NodeSet<OWLDataProperty> getSubDataProperties(OWLDataProperty pe,
			boolean direct) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException,
			TimeOutException {
		return this.delegate.getSubDataProperties(pe, direct);
	}

	public synchronized NodeSet<OWLDataProperty> getSuperDataProperties(OWLDataProperty pe,
			boolean direct) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException,
			TimeOutException {
		return this.delegate.getSuperDataProperties(pe, direct);
	}

	public synchronized Node<OWLDataProperty> getEquivalentDataProperties(OWLDataProperty pe)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return this.delegate.getEquivalentDataProperties(pe);
	}

	public synchronized NodeSet<OWLDataProperty> getDisjointDataProperties(
			OWLDataPropertyExpression pe) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException,
			TimeOutException {
		return this.delegate.getDisjointDataProperties(pe);
	}

	public synchronized NodeSet<OWLClass> getDataPropertyDomains(OWLDataProperty pe,
			boolean direct) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException,
			TimeOutException {
		return this.delegate.getDataPropertyDomains(pe, direct);
	}

	public synchronized NodeSet<OWLClass> getTypes(OWLNamedIndividual ind, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return this.delegate.getTypes(ind, direct);
	}

	public synchronized NodeSet<OWLNamedIndividual> getInstances(OWLClassExpression ce,
			boolean direct) throws InconsistentOntologyException,
			ClassExpressionNotInProfileException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return this.delegate.getInstances(ce, direct);
	}

	public synchronized NodeSet<OWLNamedIndividual> getObjectPropertyValues(
			OWLNamedIndividual ind, OWLObjectPropertyExpression pe)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return this.delegate.getObjectPropertyValues(ind, pe);
	}

	public synchronized Set<OWLLiteral> getDataPropertyValues(OWLNamedIndividual ind,
			OWLDataProperty pe) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException,
			TimeOutException {
		return this.delegate.getDataPropertyValues(ind, pe);
	}

	public synchronized Node<OWLNamedIndividual> getSameIndividuals(OWLNamedIndividual ind)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return this.delegate.getSameIndividuals(ind);
	}

	public synchronized NodeSet<OWLNamedIndividual> getDifferentIndividuals(
			OWLNamedIndividual ind) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException,
			TimeOutException {
		return this.delegate.getDifferentIndividuals(ind);
	}

	public synchronized long getTimeOut() {
		return this.delegate.getTimeOut();
	}

	public synchronized FreshEntityPolicy getFreshEntityPolicy() {
		return this.delegate.getFreshEntityPolicy();
	}

	public synchronized IndividualNodeSetPolicy getIndividualNodeSetPolicy() {
		return this.delegate.getIndividualNodeSetPolicy();
	}

	public synchronized void dispose() {
		this.delegate.dispose();
	}
}
