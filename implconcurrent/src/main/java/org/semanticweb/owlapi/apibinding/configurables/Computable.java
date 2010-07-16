package org.semanticweb.owlapi.apibinding.configurables;

/*
 * Copyright (C) 2010, University of Manchester
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