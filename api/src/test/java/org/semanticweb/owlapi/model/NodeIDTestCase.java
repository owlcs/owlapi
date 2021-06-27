package org.semanticweb.owlapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author ignazio
 */
class NodeIDTestCase {

    private static final String SOMESTRING = "somestring";

    @Test
    void shouldCreateNodeString() {
        assertEquals("_:genid30", NodeID.nodeString(30));
    }

    @Test
    void shouldCreateIRIFromNodeString() {
        assertEquals("_:genid-nodeid-somestring_", NodeID.getIRIFromNodeID("somestring_genid"));
    }

    @Test
    void shouldCreateNextIRI() {
        assertTrue(NodeID.nextAnonymousIRI().matches("_:genid[0-9]+"));
    }

    @Test
    void shouldFindAnonymousNode() {
        assertTrue(NodeID.isAnonymousNodeIRI("_:sometest_genid_something"));
        assertTrue(NodeID.isAnonymousNodeIRI("_:genid_something"));
        assertFalse(NodeID.isAnonymousNodeIRI("http://sometest_genid_something"));
        assertFalse(NodeID.isAnonymousNodeIRI((String) null));
    }

    @Test
    void shouldFindAnonymousNodeIRI() {
        assertTrue(NodeID.isAnonymousNodeIRI(IRI.create("_:sometest_genid_something")));
        assertTrue(NodeID.isAnonymousNodeIRI(IRI.create("_:genid_something")));
        assertFalse(NodeID.isAnonymousNodeIRI(IRI.create("http://sometest_genid#", "something")));
        assertFalse(NodeID.isAnonymousNodeIRI((IRI) null));
    }

    @Test
    void shouldFindsharedNodeIRI() {
        assertFalse(NodeID.isAnonymousNodeID("_:sometest_genid-nodeid-_something"));
        assertTrue(NodeID.isAnonymousNodeID("_:genid-nodeid-_something"));
        assertFalse(NodeID.isAnonymousNodeID("http://sometest_genid-nodeid-_something"));
        assertFalse(NodeID.isAnonymousNodeID(null));
    }

    @Test
    void shouldbuildNode() {
        assertTrue(
            NodeID.isAnonymousNodeIRI(NodeID.getNodeID("_:sometest_genid_something").getID()));
        assertTrue(
            NodeID.isAnonymousNodeIRI(NodeID.getNodeID("http://sometest_genid_something").getID()));
        assertTrue(NodeID.isAnonymousNodeIRI(NodeID.getNodeID(null).getID()));
        NodeID id = NodeID.getNodeID(null);
        assertEquals(id.getID(), id.toString());
        assertEquals(NodeID.getNodeID(SOMESTRING), NodeID.getNodeID(SOMESTRING));
        assertEquals(NodeID.getNodeID(SOMESTRING).compareTo(NodeID.getNodeID("someotherstring")),
            SOMESTRING.compareTo("someotherstring"));
        assertEquals(id.hashCode(), id.toString().hashCode());
    }
}
