package uk.ac.manchester.cs.owl.owlapi.alternateimpls.test.performance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class VersionedSet<T> implements Set<T> {
	private final Set<T> delegate;
	private Set<T> additions = new HashSet<T>();
	private Set<T> removals = new HashSet<T>();

	public VersionedSet(Set<T> delegate) {
		this.delegate = Collections.unmodifiableSet(delegate);
	}

	public boolean add(T e) {
		if (delegate.contains(e)) {
			return false;
		}
		if (additions.contains(e)) {
			return false;
		}
		additions.add(e);
		removals.remove(e);
		return true;
	}

	public boolean addAll(Collection<? extends T> c) {
		boolean toReturn = false;
		for (T e : c) {
			toReturn = toReturn || add(e);
		}
		return toReturn;
	}

	public void clear() {
		additions.clear();
		removals.addAll(delegate);
	}

	public boolean contains(Object o) {
		return !removals.contains(o)
				&& (delegate.contains(o) || additions.contains(o));
	}

	public boolean containsAll(Collection<?> c) {
		boolean toReturn = true;
		for (Object e : c) {
			toReturn = toReturn && contains(e);
			if (!toReturn) {
				return toReturn;
			}
		}
		return toReturn;
	}

	public boolean isEmpty() {
		return (delegate.isEmpty() && additions.isEmpty())
				|| (removals.containsAll(additions) && removals
						.containsAll(delegate));
	}

	public Iterator<T> iterator() {

		List<T> toIterate = new ArrayList<T>();
		for (T e : delegate) {
			if (!removals.contains(e)) {
				toIterate.add(e);
			}
		}
		for (T e : additions) {
			if (!removals.contains(e)) {
				toIterate.add(e);
			}
		}
		return toIterate.iterator();
	}

	public boolean remove(Object o) {
		boolean toReturn = (delegate.contains(o) || additions.contains(o))
				&& !removals.contains(o);
		if (toReturn) {
			removals.add((T) o);
		}
		return toReturn;
	}

	public boolean removeAll(Collection<?> c) {
		boolean toReturn = false;
		for (Object e : c) {
			toReturn = toReturn || remove(e);
		}
		return toReturn;
	}

	public boolean retainAll(Collection<?> c) {
		Iterator<T> it = this.iterator();
		boolean toReturn = false;
		while (it.hasNext()) {
			T t = it.next();
			if (!c.contains(t)) {
				toReturn = toReturn || remove(t);
			}
		}
		return toReturn;
	}

	public int size() {
		return delegate.size() + additions.size() - removals.size();
	}

	public Object[] toArray() {
		List<T> l = asList();
		return l.toArray();
	}

	private List<T> asList() {
		List<T> l = new ArrayList<T>();
		Iterator<T> it = this.iterator();
		while (it.hasNext()) {
			l.add(it.next());
		}
		return l;
	}

	public <K> K[] toArray(K[] a) {
		return asList().toArray(a);
	}

	@Override
	public String toString() {
		return Arrays.toString(toArray());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (obj instanceof Set<?>) {
			Set<?> set = (Set<?>) obj;
			boolean toReturn = true;
			for (Object o : this) {
				toReturn = toReturn && set.contains(o);
				if (!toReturn) {
					return false;
				}
			}
			for (Object o : set) {
				toReturn = toReturn && contains(o);
				if (!toReturn) {
					return false;
				}
			}
			return toReturn;
		}
		return false;
	}
}
