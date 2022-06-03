package org.semanticweb.owlapi.utility;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

class TarjanStructure<T> {
    private Deque<T> stack = new LinkedList<>();
    private Map<T, Integer> indexMap = new HashMap<>();
    private Map<T, Integer> lowlinkMap = new HashMap<>();
    private Set<Set<T>> result;
    private Set<T> processed;
    private Set<T> stackEntities = new HashSet<>();

    /**
     * @param result result
     * @param processed processed
     */
    public TarjanStructure(Set<Set<T>> result, Set<T> processed) {
        this.result = result;
        this.processed = processed;
    }

    public Map<T, Integer> getIndexMap() {
        return this.indexMap;
    }

    public Map<T, Integer> getLowlinkMap() {
        return this.lowlinkMap;
    }

    public Set<T> getProcessed() {
        return this.processed;
    }

    public Set<Set<T>> getResult() {
        return this.result;
    }

    public Deque<T> getStack() {
        return this.stack;
    }

    public Set<T> getStackEntities() {
        return this.stackEntities;
    }
}
