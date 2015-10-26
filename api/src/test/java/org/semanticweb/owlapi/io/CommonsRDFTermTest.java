package org.semanticweb.owlapi.io;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.rdf.api.AbstractRDFTermFactoryTest;
import org.apache.commons.rdf.api.BlankNode;
import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.Literal;

// NOTE: Always fully qualified IRI  to avoid confusion between
//import org.apache.commons.rdf.api.IRI;
//import org.semanticweb.owlapi.model.IRI;

import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.RDFTermFactory;
import org.apache.commons.rdf.api.Triple;

import org.semanticweb.owlapi.model.NodeID;

public class CommonsRDFTermTest extends AbstractRDFTermFactoryTest {

	private final class OWLAPIRDFTermFactory implements RDFTermFactory {
		
		private AtomicInteger bnodeCounter = new AtomicInteger();
		
		@Override
		public RDFResourceBlankNode createBlankNode()  {			
			return new RDFResourceBlankNode(bnodeCounter.incrementAndGet(), false, false);
		}
		
		@Override
		public RDFResourceBlankNode createBlankNode(String name)  {			
			org.semanticweb.owlapi.model.IRI iri = createIRI(NodeID.getIRIFromNodeID(name));
			return new RDFResourceBlankNode(iri, false, false);
		}
		
		@Override
		public RDFLiteral createLiteral(String lexicalForm)
				throws IllegalArgumentException, UnsupportedOperationException {
			return new RDFLiteral(lexicalForm, null, null);
		}
		
		@Override
		public RDFLiteral createLiteral(String lexicalForm, org.apache.commons.rdf.api.IRI dataType)
				throws IllegalArgumentException, UnsupportedOperationException {
			return new RDFLiteral(lexicalForm, null, createIRI(dataType.getIRIString()));
		}

		@Override
		public RDFLiteral createLiteral(String lexicalForm, String languageTag)
				throws IllegalArgumentException, UnsupportedOperationException {			
			return new RDFLiteral(lexicalForm, languageTag, null);
		}
		
		@Override
		public org.semanticweb.owlapi.model.IRI createIRI(String iriStr) throws IllegalArgumentException, UnsupportedOperationException {
			org.semanticweb.owlapi.model.IRI innerIri = org.semanticweb.owlapi.model.IRI.create(iriStr);			// 
			return innerIri;
			// TODO: What about RDFResourceIRI?
			//return new RDFResourceIRI(innerIri);
		}
		
		@Override
		public Triple createTriple(BlankNodeOrIRI subject, org.apache.commons.rdf.api.IRI predicate, RDFTerm object)
				throws IllegalArgumentException, UnsupportedOperationException {
			RDFResource subject2 = (RDFResource) convert(subject);
			RDFResourceIRI predicate2 = (RDFResourceIRI) convert(predicate);
			RDFNode object2 = convert(object);
			return new RDFTriple(subject2, predicate2, object2);
		}

		private RDFNode convert(RDFTerm term) {
			if (term instanceof RDFNode) {
				// NOTE: Naively assuming it can then be further casted to RDFResourceIRI etc
				return (RDFNode)term;
			}
			if (term instanceof org.apache.commons.rdf.api.IRI) {
				org.apache.commons.rdf.api.IRI iri = (org.apache.commons.rdf.api.IRI)term;
				return new RDFResourceIRI(createIRI(iri.getIRIString()));
			}
			if (term instanceof BlankNode) {				
				BlankNode blankNode = (BlankNode) term;
				String ntriples = blankNode.ntriplesString();
				org.semanticweb.owlapi.model.IRI iriLike;
				if (ntriples.startsWith("<") && ntriples.endsWith(">")) {
					// strange.. a BlankNode with IRI? Well, we can handle those.
					iriLike = createIRI(ntriples.substring(1, ntriples.length()-1));					
				} else if (ntriples.startsWith("_:")) {
					// org.semanticweb.owlapi.model.IRI supports these directly
					// even though they are not relly IRIs
					iriLike = createIRI(ntriples);
				} else {
					// How can this be valid N-Triples?
					throw new IllegalArgumentException("Unsupported ntriplesString on BlankNode: " + ntriples);
				}
				return new RDFResourceBlankNode(iriLike, false, false);
			}
			if (term instanceof Literal) {
				Literal literal = (Literal) term;
				org.semanticweb.owlapi.model.IRI dataType = null;
				if (! literal.getLanguageTag().isPresent()) {
					dataType = createIRI(literal.getDatatype().getIRIString());
				}
				return new RDFLiteral(literal.getLexicalForm(), 
						literal.getLanguageTag().orElse(null), 
						dataType);
			}
			throw new IllegalArgumentException("Unsupported type: " + term.getClass());
		}
		
	}

	@Override
	public RDFTermFactory createFactory() {
		return new OWLAPIRDFTermFactory();
	}

}
