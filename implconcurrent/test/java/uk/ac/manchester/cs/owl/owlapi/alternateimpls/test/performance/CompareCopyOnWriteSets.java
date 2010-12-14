package uk.ac.manchester.cs.owl.owlapi.alternateimpls.test.performance;

import java.util.*;

import org.semanticweb.owlapi.util.CollectionFactory;

public class CompareCopyOnWriteSets {
	public static void main(String[] args) {
		HashSet<String> testSet = new HashSet<String>();
		for (int i = 0; i < 1000; i++) {
			testSet.add("string" + i);
		}
		long l=System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			Set<String> copyOnWrite = CollectionFactory
					.getCopyOnWriteSet(testSet);
		}
		System.out.println("CompareCopyOnWriteSets.main() "+(System.currentTimeMillis()-l));
		l=System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			Set<String> regular = new HashSet<String>(testSet);
		}
		System.out.println("CompareCopyOnWriteSets. reg() "+(System.currentTimeMillis()-l));
		l=System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			List<String> regular = new ArrayList<String>(testSet);
		}
		System.out.println("CompareCopyOnWriteSets. reg() "+(System.currentTimeMillis()-l));
	}
}
