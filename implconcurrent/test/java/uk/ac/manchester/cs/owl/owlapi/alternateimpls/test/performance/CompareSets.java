package uk.ac.manchester.cs.owl.owlapi.alternateimpls.test.performance;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CompareSets {
	public static void main(String[] args) {
		Set<String> base = new HashSet<String>();
		int k = 5000;
		int j = 2*k;
		for (int index = 0; index < k; index++) {
			base.add("test" + index);
		}
		VersionedSet<String> v = new VersionedSet<String>(base);
		Set<String> c = new HashSet<String>(base);
		compareEmpty(v, c);
		compareSets(v, c);
		compareIterators(v.iterator(), c.iterator());
		for (int i = 0; i < k; i++) {
			String el = "test" + i;
			compareRemove(v, c, el);
			compareEmpty(v, c);
			compareSets(v, c);
			compareIterators(v.iterator(), c.iterator());
		}
		for (int i = 0; i < k; i++) {
			String el = "test" + i;
			compareRemove(v, c, el);
			compareEmpty(v, c);
			compareSets(v, c);
			compareIterators(v.iterator(), c.iterator());
		}

		for (int i = k; i < j; i++) {
			String el = "test" + i;
			compareAdd(v, c, el);
			compareEmpty(v, c);
			compareSets(v, c);
			compareIterators(v.iterator(), c.iterator());
		}
		for (int i = k; i < j; i++) {
			String el = "test" + i;
			compareAdd(v, c, el);
			compareEmpty(v, c);
			compareSets(v, c);
			compareIterators(v.iterator(), c.iterator());
		}
	}

	public static <T> void compareIterators(Iterator<T> it1, Iterator<T> it2) {
		Set<T> s1 = new HashSet<T>();
		Set<T> s2 = new HashSet<T>();
		while (it1.hasNext()) {
			s1.add(it1.next());
		}
		while (it2.hasNext()) {
			s2.add(it2.next());
		}
		if (!s1.equals(s2)) {
			System.out.println("CompareSets.compareIterators() DIFFER");
		}
	}

	public static <T> void compareAdd(Set<T> it1, Set<T> it2, T element) {
		if (it1.add(element) != it2.add(element)) {
			System.out.println("CompareSets.compareAdd() DIFFER");
		}
	}

	public static <T> void compareRemove(Set<T> it1, Set<T> it2, T element) {
		if (it1.remove(element) != it2.remove(element)) {
			System.out.println("CompareSets.compareRemove() DIFFER");
		}
	}

	public static <T> void compareSets(Set<T> it1, Set<T> it2) {
		if (!it1.equals(it2)) {
			System.out.println("CompareSets.compareSets() DIFFER");
			System.out.println(it1.toString());
			System.out.println(it2.toString());
		}
	}

	public static <T> void compareEmpty(Set<T> it1, Set<T> it2) {
		if (it1.isEmpty() != it2.isEmpty()) {
			System.out.println("CompareSets.compareEmpty() DIFFER");
		}
	}
}
