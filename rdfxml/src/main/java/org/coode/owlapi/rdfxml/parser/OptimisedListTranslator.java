/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.coode.owlapi.rdfxml.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Jan-2007<br>
 * <br>
 * <p/>
 * Translates an rdf:List into a Java <code>List</code>, or Java
 * <code>Set</code>. The type of list (i.e. the type of objects in the list) are
 * determined by a <code>ListItemTranslator</code>. The translator consumes all
 * triples which are used in the translation.
 */
public class OptimisedListTranslator<O extends OWLObject> {
	private static final Logger logger = Logger.getLogger(OWLRDFConsumer.class
			.getName());
	private OWLRDFConsumer consumer;
	private ListItemTranslator<O> translator;

	protected OptimisedListTranslator(OWLRDFConsumer consumer,
			ListItemTranslator<O> translator) {
		this.consumer = consumer;
		this.translator = translator;
	}

	protected OWLRDFConsumer getConsumer() {
		return consumer;
	}

	private void translateList(IRI mainNode, List<O> list) {
		IRI current = mainNode;
		while (current != null) {
			IRI firstResource = consumer.getFirstResource(current, true);
			if (firstResource != null) {
				list.add(translator.translate(firstResource));
			} else {
				OWLLiteral literal = consumer.getFirstLiteral(current);
				if (literal != null) {
					O translate = translator.translate(literal);
					if(translate!=null) {
						list.add(translate);
					}
				} else {
					// Empty list?
					if (logger.isLoggable(Level.FINE)) {
						logger.fine("Possible malformed list: rdf:first triple missing");
					}
				}
			}
			current = consumer.getRest(current, true);
		}
	}

	//    private void translateList(IRI mainNode, List<O> list) {
	//        
	//        IRI firstResource = consumer.getFirstResource(mainNode, true);
	//        if (firstResource != null) {
	//            list.add(translator.translate(firstResource));
	//        }
	//        else {
	//            OWLLiteral literal = consumer.getFirstLiteral(mainNode);
	//            if (literal != null) {
	//                list.add(translator.translate(literal));
	//            }
	//            else {
	//                // Empty list?
	//                if (logger.isLoggable(Level.FINE)) {
	//                    logger.fine("Possible malformed list: rdf:first triple missing");
	//                }
	//            }
	//        }
	//        IRI rest = consumer.getRest(mainNode, true);
	//        if (rest != null) {
	//            translateList(rest, list);
	//        }
	//    }
	public List<O> translateList(IRI mainNode) {
		boolean shared = consumer.isSharedAnonymousNode(mainNode);
		List<O> list;
		if (shared) {
			Object o = consumer.getSharedAnonymousNode(mainNode);
			if (o != null && o instanceof List) {
				list = (List<O>) o;
			} else {
				list = new ArrayList<O>();
				translateList(mainNode, list);
				consumer.addSharedAnonymousNode(mainNode, list);
			}
		} else {
			list = new ArrayList<O>();
			translateList(mainNode, list);
		}
		return list;
	}

	public Set<O> translateToSet(IRI mainNode) {
		return new HashSet<O>(translateList(mainNode));
	}
}
