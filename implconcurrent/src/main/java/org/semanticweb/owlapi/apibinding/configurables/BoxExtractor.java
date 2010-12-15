package org.semanticweb.owlapi.apibinding.configurables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.OWLObjectVisitorExAdapter;

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
 * This class allows for the extraction of subsets of axioms based on their types; the purpose is to offer a flexible extraction mechanism to collect axioms belonging to, e.g., the ABox, the TBox or the RBox of an ontology. Sets of axiom types covering these cases are available in AxiomType::TBoxAxiomTypes, AxiomType::ABoxAxiomTypes, AxiomType::RBoxAxiomType, but the class can be configured through the constructor arguments to include different subsets.
 * 
 * The idea for this construct was graciously provided by Thomas Scharrenbach, Swiss Federal Research Institute WSL (http://www.wsl.ch/info/mitarbeitende/scharren/owl-defaults/index_EN?redir=1)
 * 
 * This class does not store any internal state referring to the ontology; it is safe for use in multithread environments, provided that the parameters to the constructor do not change while the constructor executes and the OWLOntology implementation is itself threadsafe.
 * A single instance can be reused for extracting the same Box from different ontologies without side effects.
 * */
public class BoxExtractor extends OWLObjectVisitorExAdapter<Set<OWLAxiom>>
		implements OWLObjectVisitorEx<Set<OWLAxiom>> {
	private Collection<AxiomType> types;
	private boolean closure;

	/**@param types the set of AxiomType objects to use for selection
	 * @param importsClosure if true, the imports closure is included in the search, otherwise only the visited ontology is included 
	 * */
	public BoxExtractor(Collection<AxiomType> types,
			boolean importsClosure) {
		this.types = new ArrayList<AxiomType>(types);
		this.closure = importsClosure;
	}

	@Override
	public Set<OWLAxiom> visit(OWLOntology ontology) {
		Set<OWLAxiom> toReturn = new HashSet<OWLAxiom>();
		for (AxiomType<OWLAxiom> t : types) {
			toReturn.addAll(ontology.getAxioms(t, closure));
		}
		return toReturn;
	}
}
