package casualtest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.semanticweb.owlapi.util.CollectionFactory;

public class TestCopyOnRequestList extends TestCase {
	public void testList() {
		List<Integer> l = new ArrayList<Integer>();
		//l.add(1);
		List<Integer> l1 = new ArrayList<Integer>();
		//l1.add(1);
		Set<Integer> set = CollectionFactory.getCopyOnRequestSet(l);
		assertTrue(l.containsAll(set));
		assertTrue(set.containsAll(l));
		assertTrue((l1.containsAll(set) && set.containsAll(l1)));
	}
}
