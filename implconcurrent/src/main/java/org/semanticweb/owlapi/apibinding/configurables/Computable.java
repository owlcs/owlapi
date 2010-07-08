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