package casualtest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.util.CollectionFactory;

public class TestCopyOnRequestList {
	public static void main(String[] args) {
		List<Integer> l=new ArrayList<Integer>();
		//l.add(1);
		List<Integer> l1=new ArrayList<Integer>();
		//l1.add(1);
		Set<Integer> set=CollectionFactory.getCopyOnRequestSet(l);
		System.out.println("TestCopyOnRequestList.main() "+l.containsAll(set) +"\t"+set.containsAll(l));
		System.out.println("TestCopyOnRequestList.main() "+(l1.containsAll(set) &&set.containsAll(l1)));
	}

}
