package org.semanticweb.owlapi.apibinding.configurables;

public abstract class ComputableAdapter<V> implements Computable<V> {
	public boolean hasThrownException() {
		return false;
	}

	public Throwable thrownException() {
		return null;
	}
}