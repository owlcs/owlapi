package uk.ac.manchester.cs.owl.owlapi.alternateimpls;

import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;

import uk.ac.manchester.cs.owl.owlapi.Internals;
import uk.ac.manchester.cs.owl.owlapi.MapPointer;

class SyncMapPointer<K, V extends OWLAxiom> extends MapPointer<K, V> {
		/**
	 * 
	 */
	private static final long serialVersionUID = -1662346304061602489L;

		public SyncMapPointer(AxiomType<?> t, OWLAxiomVisitorEx<?> v,
				boolean initialized, Internals i) {
			super(t, v, initialized, i);
		}

		@Override
		public synchronized boolean contains(K key, V value) {
			return super.contains(key, value);
		}

		@Override
		public synchronized boolean containsKey(K key) {
			return super.containsKey(key);
		}

		@Override
		public synchronized Set<V> getAllValues() {
			return super.getAllValues();
		}

		@Override
		public synchronized Set<V> getValues(K key) {
			return super.getValues(key);
		}

		@Override
		public synchronized void init() {
			super.init();
		}

		@Override
		public synchronized boolean isInitialized() {
			return super.isInitialized();
		}

		@Override
		public synchronized Set<K> keySet() {
			return super.keySet();
		}

		@Override
		public synchronized boolean put(K key, V value) {
			return super.put(key, value);
		}

		@Override
		public synchronized boolean remove(K key, V value) {
			return super.remove(key, value);
		}

		@Override
		public synchronized int size() {
			return super.size();
		}
	}
