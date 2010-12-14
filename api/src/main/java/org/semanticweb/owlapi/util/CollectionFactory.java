package org.semanticweb.owlapi.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Jan-2007<br>
 * <br>
 */
public class CollectionFactory {
	private static AtomicInteger expectedThreads = new AtomicInteger(16);

	public static void setExpectedThreads(int value) {
		expectedThreads.set(value);
	}

	public static <T> Set<T> createSet() {
		return new HashSet<T>();
	}

	public static <T> Set<T> createSet(Collection<T> c) {
		return new HashSet<T>(c);
	}

	public static <T> Set<T> createSet(int initialCapacity) {
		return new HashSet<T>(initialCapacity);
	}

	public static <K, V> Map<K, V> createMap() {
		return new HashMap<K, V>();
	}

	public static <T> Set<T> createSet(T... elements) {
		Set<T> result = createSet();
		for (T t : elements) {
			result.add(t);
		}
		return result;
	}

	public static <T> Set<T> createSyncSet() {
		return new SyncSet<T>();
	}

	public static <K, V> ConcurrentHashMap<K, V> createSyncMap() {
		return new ConcurrentHashMap<K, V>(16, 0.75F, expectedThreads.get());
	}

	/**
	 * this class implements a Set using a ConcurrentHashMap as backing
	 * structure; compared to the default HashSet implementation, this structure
	 * is threadsafe without being completely synchronized, so it offers better
	 * performances
	 */
	private static final class SyncSet<T> implements Set<T> {
		private final ConcurrentHashMap<T, Set<T>> backingMap;

		public SyncSet(ConcurrentHashMap<T, Set<T>> map) {
			backingMap = map;
		}

		public SyncSet() {
			this(new ConcurrentHashMap<T, Set<T>>());
		}

		public SyncSet(Set<T> delegate) {
			this();
			for (T d : delegate) {
				add(d);
			}
		}

		public boolean add(T e) {
			return backingMap.put(e, this) == null;
		}

		public boolean addAll(Collection<? extends T> c) {
			boolean toReturn = false;
			for (T o : c) {
				toReturn = toReturn || add(o);
			}
			return toReturn;
		}

		public void clear() {
			backingMap.clear();
		}

		public boolean contains(Object o) {
			return backingMap.containsKey(o);
		}

		public boolean containsAll(Collection<?> c) {
			boolean toReturn = true;
			for (Object o : c) {
				toReturn = toReturn && contains(o);
				if (!toReturn) {
					return toReturn;
				}
			}
			return toReturn;
		}

		public boolean isEmpty() {
			return backingMap.isEmpty();
		}

		public Iterator<T> iterator() {
			return backingMap.keySet().iterator();
		}

		public int size() {
			return backingMap.size();
		}

		public boolean remove(Object o) {
			return backingMap.remove(o) != null;
		}

		public boolean removeAll(Collection<?> c) {
			boolean toReturn = false;
			for (Object o : c) {
				toReturn = toReturn || remove(o);
			}
			return toReturn;
		}

		public boolean retainAll(Collection<?> c) {
			boolean toReturn = false;
			for (Map.Entry<T, Set<T>> e : backingMap.entrySet()) {
				if (!c.contains(e.getKey())) {
					toReturn = true;
					backingMap.remove(e.getKey());
				}
			}
			return toReturn;
		}

		public Object[] toArray() {
			return backingMap.keySet().toArray();
		}

		public <T> T[] toArray(T[] a) {
			return backingMap.keySet().toArray(a);
		}
	}

	public static <T> Set<T> getCopyOnRequestSet(Set<T> source) {
		return new ConditionalCopySet<T>(source);
	}

	public static <T> Set<T> getThreadSafeCopyOnRequestSet(Set<T> source) {
		return new ThreadSafeConditionalCopySet<T>(source);
	}

	/**
	 * a set implementation that uses a delegate set for all read-only
	 * operations and makes a copy if changes are attempted. Useful for cheap
	 * defensive copies: no costly rehashing on the original set is made unless
	 * changes are attempted. Changes are not mirrored back to the original set,
	 * although changes to the original set BEFORE changes to the copy are
	 * reflected in the copy. This is not the expected behaviour for regular
	 * defensive copies, so it needs to be documented; it is irrelevant for
	 * copies of sets that never change, i.e., signatures of OWL objects.
	 * 
	 * This implementation is not threadsafe even if the source set is: there is
	 * no lock during the copy, and the new set is not threadsafe
	 */
	public static class ConditionalCopySet<T> implements Set<T> {
		private boolean copyDone = false;
		private Set<T> delegate;

		public ConditionalCopySet(Set<T> source) {
			this.delegate = source;
		}

		public boolean add(T arg0) {
			if (!copyDone) {
				copyDone = true;
				delegate = new HashSet<T>(delegate);
			}
			return delegate.add(arg0);
		}

		public boolean addAll(Collection<? extends T> arg0) {
			if (!copyDone) {
				copyDone = true;
				delegate = new HashSet<T>(delegate);
			}
			return delegate.addAll(arg0);
		}

		public void clear() {
			if (!copyDone) {
				copyDone = true;
				delegate = new HashSet<T>(delegate);
			}
			delegate.clear();
		}

		public boolean contains(Object arg0) {
			return delegate.contains(arg0);
		}

		public boolean containsAll(Collection<?> arg0) {
			return delegate.containsAll(arg0);
		}

		public boolean isEmpty() {
			return delegate.isEmpty();
		}

		public Iterator<T> iterator() {
			return delegate.iterator();
		}

		public boolean remove(Object arg0) {
			if (!copyDone) {
				copyDone = true;
				delegate = new HashSet<T>(delegate);
			}
			return delegate.remove(arg0);
		}

		public boolean removeAll(Collection<?> arg0) {
			if (!copyDone) {
				copyDone = true;
				delegate = new HashSet<T>(delegate);
			}
			return delegate.removeAll(arg0);
		}

		public boolean retainAll(Collection<?> arg0) {
			if (!copyDone) {
				copyDone = true;
				delegate = new HashSet<T>(delegate);
			}
			return delegate.retainAll(arg0);
		}

		public int size() {
			return delegate.size();
		}

		public Object[] toArray() {
			return delegate.toArray();
		}

		public <T> T[] toArray(T[] arg0) {
			return delegate.toArray(arg0);
		}
	}

	/**
	 * this class behaves like ConditionalCopySet except it is designed to be
	 * threadsafe; multiple thread access is regulated by a readwritelock;
	 * modifications will create a copy based on SyncSet.
	 * 
	 * */
	public static class ThreadSafeConditionalCopySet<T> implements Set<T> {
		private boolean copyDone = false;
		private Set<T> delegate;
		private final ReadWriteLock lock = new ReentrantReadWriteLock();
		private final Lock readLock = lock.readLock();
		private final Lock writeLock = lock.writeLock();

		public ThreadSafeConditionalCopySet(Set<T> source) {
			this.delegate = source;
		}

		public boolean add(T arg0) {
			try {
				writeLock.lock();
				if (!copyDone) {
					copyDone = true;
					delegate = new SyncSet<T>(delegate);
				}
				return delegate.add(arg0);
			} finally {
				writeLock.unlock();
			}
		}

		public boolean addAll(Collection<? extends T> arg0) {
			try {
				writeLock.lock();
				if (!copyDone) {
					copyDone = true;
					delegate = new SyncSet<T>(delegate);
				}
				return delegate.addAll(arg0);
			} finally {
				writeLock.unlock();
			}
		}

		public void clear() {
			try {
				writeLock.lock();
				if (!copyDone) {
					copyDone = true;
					delegate = new SyncSet<T>(delegate);
				}
				delegate.clear();
			} finally {
				writeLock.unlock();
			}
		}

		public boolean contains(Object arg0) {
			try {
				readLock.lock();
				return delegate.contains(arg0);
			} finally {
				readLock.unlock();
			}
		}

		public boolean containsAll(Collection<?> arg0) {
			try {
				readLock.lock();
				return delegate.containsAll(arg0);
			} finally {
				readLock.unlock();
			}
		}

		public boolean isEmpty() {
			try {
				readLock.lock();
				return delegate.isEmpty();
			} finally {
				readLock.unlock();
			}
		}

		public Iterator<T> iterator() {
			try {
				readLock.lock();
				return delegate.iterator();
			} finally {
				readLock.unlock();
			}
		}

		public boolean remove(Object arg0) {
			try {
				writeLock.lock();
				if (!copyDone) {
					copyDone = true;
					delegate = new SyncSet<T>(delegate);
				}
				return delegate.remove(arg0);
			} finally {
				writeLock.unlock();
			}
		}

		public boolean removeAll(Collection<?> arg0) {
			try {
				writeLock.lock();
				if (!copyDone) {
					copyDone = true;
					delegate = new SyncSet<T>(delegate);
				}
				return delegate.removeAll(arg0);
			} finally {
				writeLock.unlock();
			}
		}

		public boolean retainAll(Collection<?> arg0) {
			try {
				writeLock.lock();
				if (!copyDone) {
					copyDone = true;
					delegate = new SyncSet<T>(delegate);
				}
				return delegate.retainAll(arg0);
			} finally {
				writeLock.unlock();
			}
		}

		public int size() {
			try {
				readLock.lock();
				return delegate.size();
			} finally {
				readLock.unlock();
			}
		}

		public Object[] toArray() {
			try {
				readLock.lock();
				return delegate.toArray();
			} finally {
				readLock.unlock();
			}
		}

		public <Type> Type[] toArray(Type[] arg0) {
			try {
				readLock.lock();
				return delegate.toArray(arg0);
			} finally {
				readLock.unlock();
			}
		}
	}
}
