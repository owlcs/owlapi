package uk.ac.manchester.cs.owl.owlapi.util.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

class SmallSetTest {

    @Test
    void testAddRemoveContains() {
        SmallSet<String> set = new SmallSet<>();
        assertEquals(0, set.size());
        assertFalse(set.contains("a"));
        set.add("a");
        assertTrue(set.contains("a"));
        assertFalse(set.contains("b"));
        set.add("b");
        assertTrue(set.contains("a"));
        assertTrue(set.contains("b"));
        assertEquals(2, set.size());
        set.remove("a");
        assertFalse(set.contains("a"));
        assertTrue(set.contains("b"));
        assertEquals(1, set.size());
        set.add("a");
        set.add("c");
        assertTrue(set.contains("a"));
        assertTrue(set.contains("b"));
        assertTrue(set.contains("c"));
        assertEquals(3, set.size());
        assertThrows(IllegalStateException.class, () -> set.add("d"),
            "should not be able to add fourth elephant");
    }

    @Test
    void testIterator() {
        List<String> stringList = Arrays.asList("a", "c", "b");
        SmallSet<String> set = new SmallSet<>(stringList);
        HashSet<String> validationSet = new HashSet<>(stringList);
        assertEquals(3, set.size());
        String v;
        Iterator<String> it = set.iterator();
        assertTrue(it.hasNext());
        v = it.next();
        assertTrue(validationSet.remove(v));
        assertTrue(it.hasNext());
        v = it.next();
        assertTrue(validationSet.remove(v));
        assertTrue(it.hasNext());
        v = it.next();
        assertTrue(validationSet.remove(v));
        assertFalse(it.hasNext());
        assertTrue(validationSet.isEmpty());
    }

    @Test
    void testIteratorPostRemoval() {
        List<String> stringList = Arrays.asList("a", "c", "b");
        SmallSet<String> set = new SmallSet<>(stringList);
        HashSet<String> validationSet = new HashSet<>(stringList);
        set.remove("c");
        validationSet.remove("c");
        assertEquals(2, set.size());
        String v;
        Iterator<String> it = set.iterator();
        assertTrue(it.hasNext());
        v = it.next();
        assertTrue(validationSet.remove(v));
        assertTrue(it.hasNext());
        v = it.next();
        assertTrue(validationSet.remove(v));
        assertFalse(it.hasNext());
        assertTrue(validationSet.isEmpty());
    }
}
