package org.semanticweb.owlapi.reasoner.structural;

import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

class TarjanStructure<T> {
    private Deque<T> stack = new LinkedList<>();
    private Map<T, Integer> indexMap = new HashMap<>();
    private Map<T, Integer> lowlinkMap = new HashMap<>();
    private Set<T> stackEntities = new HashSet<>();
    private Set<Set<T>> result;
    private Set<T> processed;
    private @Nullable Map<T, Collection<T>> cache;
    private Set<T> childrenOfTop;
    private Set<T> parentsOfBottom;

    /**
     * @param result result
     * @param processed processed
     * @param cache A cache of children to parents - may be {@code null} if no caching is to take
     *        place.
     * @param childrenOfTop A set of entities that have a raw parent that is the top entity
     * @param parentsOfBottom A set of entities that have a raw parent that is the bottom entity
     */
    public TarjanStructure(Set<Set<T>> result, Set<T> processed,
        @Nullable Map<T, Collection<T>> cache, Set<T> childrenOfTop, Set<T> parentsOfBottom) {
        this.result = result;
        this.processed = processed;
        this.cache = cache;
        this.childrenOfTop = childrenOfTop;
        this.parentsOfBottom = parentsOfBottom;
    }

    public Map<T, Collection<T>> getCache() {
        return this.cache;
    }

    public Set<T> getChildrenOfTop() {
        return this.childrenOfTop;
    }

    public Map<T, Integer> getIndexMap() {
        return this.indexMap;
    }

    public Map<T, Integer> getLowlinkMap() {
        return this.lowlinkMap;
    }

    public Set<T> getParentsOfBottom() {
        return this.parentsOfBottom;
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
