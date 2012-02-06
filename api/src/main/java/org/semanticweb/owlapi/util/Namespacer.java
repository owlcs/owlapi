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
package org.semanticweb.owlapi.util;

import java.io.Serializable;
import java.util.*;
import java.util.regex.*;

import org.semanticweb.owlapi.model.IRI;

public class Namespacer implements Serializable {
	private Pattern qnamePattern = Pattern.compile("[a-zA-Z_][a-zA-Z_0-9\\.\\-_\\%]*");
	private transient WeakIndexCache<IRI, String> prefixedNamesCache = new WeakIndexCache<IRI, String>();
	private transient WeakCache<IRI> unprefixable = new WeakCache<IRI>();
	private Map<String, String> prefix2NamespaceMap = new HashMap<String, String>();

	public Namespacer(Map<String, String> prefMap) {
		prefix2NamespaceMap.putAll(prefMap);
	}

	public void resetCaches() {
		unprefixable.clear();
		prefixedNamesCache.clear();
		prefix2NamespaceMap.clear();
	}

	public void addPrefixMap(Map<String, String> prefixes) {
		prefix2NamespaceMap.putAll(prefixes);
	}

	public void addPrefix(String namespace, String value) {
		prefix2NamespaceMap.put(namespace, value);
		// drop weak caches, new prefix might make them invalid
		unprefixable.clear();
		prefixedNamesCache.clear();
	}

	public String getQName(IRI iri) {
		String cached = prefixedNamesCache.get(iri);
		if (cached != null) {
			return cached;
		}
		String iriString = iri.toString();
		String toReturn=getQName(iriString);
		if(toReturn==null) {
			unprefixable.cache(iri);
		}else {
			prefixedNamesCache.cache(iri, toReturn);
		}
		return toReturn;
	}

	public String getQName(String s) {
		for (String prefixName : prefix2NamespaceMap.keySet()) {
			String prefix = prefix2NamespaceMap.get(prefixName);
			if (s.startsWith(prefix)) {
				String localName = s.substring(prefix.length());
				final Matcher matcher = qnamePattern.matcher(localName);
				if (matcher.find() && localName.equals(matcher.group())) {
					String toReturn =null;
					if(prefixName.endsWith(":")) {
					 toReturn=prefixName + localName;
					}else {
						toReturn=prefixName+":" + localName;
					}
					return toReturn;
				}
			}
		}
		return null;
	}
}
