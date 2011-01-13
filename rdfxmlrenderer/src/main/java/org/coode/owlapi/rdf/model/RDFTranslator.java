package org.coode.owlapi.rdf.model;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/*
 * Copyright (C) 2006, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 06-Dec-2006<br>
 * <br>
 */
public class RDFTranslator
		extends
		AbstractTranslator<RDFNode, RDFResourceNode, RDFResourceNode, RDFLiteralNode> {
	private RDFGraph graph;

	public RDFTranslator(OWLOntologyManager manager, OWLOntology ontology,
			boolean useStrongTyping) {
		super(manager, ontology, useStrongTyping);
		graph = new RDFGraph();
	}

	public RDFGraph getGraph() {
		return graph;
	}

	@Override
	protected void addTriple(RDFResourceNode subject, RDFResourceNode pred,
			RDFNode object) {
		graph.addTriple(new RDFTriple(subject, pred, object));
	}

	@Override
	protected RDFResourceNode getAnonymousNode(Object key) {
		if (key instanceof OWLAnonymousIndividual) {
			RDFResourceNode toReturn = new RDFResourceNode(
					((OWLAnonymousIndividual) key).getID().getID().hashCode());
			//System.out.println("RDFTranslator.getAnonymousNodeTricked() "+key.getClass().getSimpleName()+"\t"+key+"\t"+toReturn);
			return toReturn;
		}
		RDFResourceNode toReturn = new RDFResourceNode(
				System.identityHashCode(key));
		//System.out.println("RDFTranslator.getAnonymousNode() "+key.getClass().getSimpleName()+"\t"+key+"\t"+toReturn);
		return toReturn;
	}

	@Override
	protected RDFLiteralNode getLiteralNode(OWLLiteral literal) {
		return translateLiteralNode(literal);
	}

	public static RDFLiteralNode translateLiteralNode(OWLLiteral literal) {
		if (!literal.isRDFPlainLiteral()) {
			return new RDFLiteralNode(literal.getLiteral(), literal
					.getDatatype().getIRI());
		} else {
			return new RDFLiteralNode(literal.getLiteral(),
					literal.hasLang() ? literal.getLang() : null);
		}
	}

	@Override
	protected RDFResourceNode getPredicateNode(IRI uri) {
		return new RDFResourceNode(uri);
	}

	@Override
	protected RDFResourceNode getResourceNode(IRI uri) {
		return new RDFResourceNode(uri);
	}

	public void reset() {
		graph = new RDFGraph();
	}
}
