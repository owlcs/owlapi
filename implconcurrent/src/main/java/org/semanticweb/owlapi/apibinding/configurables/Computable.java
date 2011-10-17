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

package org.semanticweb.owlapi.apibinding.configurables;

/**
 * This class represents a computable task to be used when filling a
 * MemoizingCache; the results to be stored in the cache, i.e., the return value
 * of compute(), are expected to be non null, although it is not forbidden to
 * have a null value; if that case is expected to be frequent, then the use of a
 * cache is not sensible.
 *
 * The expected code pattern looks like this:<br/>
 * MemoizingCache&lt;A, B&gt; cache = new MemoizingCache&lt;A, B&gt;();<br/>
 * A key = ...;<br/>
 * Computable&lt;B&gt; c = new Computable&lt;B&gt;{...};<br/>
 * B toReturn = cache.get(c, key);<br/>
 * if(c.hasThrownException()){<br/>
 * throw c.thrownException();<br/>
 * }<br/>
 *
 * When refactoring code where the computation of the toReturn value was
 * inlined, this reproduces the behaviour of the refactored code; casting of the
 * Throwable returned may be needed.
 * @param <V> type of computed result
 *
 */
public interface Computable<V> {
	/**
	 * do the actual computation and return the result; any exception should be
	 * caught and stored for retrieval by hasThrownException and
	 * thrownException; failure to capture exceptions will result in it being
	 * caught and rethrown by the MemoizingCache internals, changing the
	 * behaviour of the refactored code.
	 *
	 * @return the result of the computation, or null if exceptions occur
	 */
	public V compute();

	/**
	 * @return true if the execution of compute() has captured an exception;
	 *         false if the execution has not completed or has completed without
	 *         problems
	 */
	public boolean hasThrownException();

	/**
	 * @return the exception captured during the execution of compute(), or null
	 *         if no exception exist
	 */
	public Throwable thrownException();
}