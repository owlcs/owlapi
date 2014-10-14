package uk.ac.manchester.cs.owl.owlapi.util.collections;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class SmallSetTest {

    @Test(expected = IllegalStateException.class)
    public void testAddRemoveContains() {
        SmallSet<String> set = new SmallSet<>();
        assertEquals("size", 0, set.size());
        assertFalse("contains a", set.contains("a"));
        set.add("a");
        assertTrue("contains a", set.contains("a"));
        assertFalse("contains b", set.contains("b"));
        set.add("b");
        assertTrue("contains a", set.contains("a"));
        assertTrue("contains b", set.contains("b"));
        assertEquals("set size", 2, set.size());
        set.remove("a");
        assertFalse("contains a", set.contains("a"));
        assertTrue("contains b", set.contains("b"));
        assertEquals("set size", 1, set.size());
        set.add("a");
        set.add("c");
        assertTrue("contains a", set.contains("a"));
        assertTrue("contains b", set.contains("b"));
        assertTrue("contains c", set.contains("c"));
        assertEquals("set size", 3, set.size());
        set.add("d");
        fail("should not be able to add fourth elephant");
    }

    @Test
    public void testIterator() {
        List<String> stringList = Arrays.asList("a", "c", "b");
        SmallSet<String> set = new SmallSet<>(stringList);
        HashSet<String> validationSet = new HashSet<>(stringList);
        assertEquals("size", 3, set.size());
        String v;
        Iterator<String> it = set.iterator();
        assertTrue("hasNext", it.hasNext());
        v = it.next();
        assertTrue("element was in validationSet", validationSet.remove(v));
        assertTrue("hasNext", it.hasNext());
        v = it.next();
        assertTrue("element was in validationSet", validationSet.remove(v));
        assertTrue("hasNext", it.hasNext());
        v = it.next();
        assertTrue("element was in validationSet", validationSet.remove(v));
        assertFalse("no more", it.hasNext());
        assertTrue("validation set should be empty", validationSet.isEmpty());
    }

    @Test
    public void testIteratorPostRemoval() {
        List<String> stringList = Arrays.asList("a", "c", "b");
        SmallSet<String> set = new SmallSet<>(stringList);
        HashSet<String> validationSet = new HashSet<>(stringList);
        set.remove("c");
        validationSet.remove("c");
        assertEquals("size", 2, set.size());
        String v;
        Iterator<String> it = set.iterator();
        assertTrue("hasNext", it.hasNext());
        v = it.next();
        assertTrue("element was in validationSet", validationSet.remove(v));
        assertTrue("hasNext", it.hasNext());
        v = it.next();
        assertTrue("element was in validationSet", validationSet.remove(v));
        assertFalse("no more", it.hasNext());
        assertTrue("validation set should be empty", validationSet.isEmpty());
    }
}
