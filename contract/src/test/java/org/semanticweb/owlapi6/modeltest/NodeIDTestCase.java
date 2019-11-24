package org.semanticweb.owlapi6.modeltest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.NodeID;

/**
 * @author ignazio
 */
public class NodeIDTestCase extends TestBase {

    private static final String SOMESTRING = "somestring";

    @Test
    public void shouldCreateNodeString() {
        assertEquals("_:genid30", NodeID.nodeString(30));
    }

    @Test
    public void shouldCreateIRIFromNodeString() {
        assertEquals("_:genid-nodeid-somestring_", NodeID.getIRIFromNodeID("somestring_genid"));
    }

    @Test
    public void shouldCreateNextIRI() {
        assertTrue(NodeID.nextAnonymousIRI().matches("_:genid[0-9]+"));
    }

    @Test
    public void shouldFindAnonymousNode() {
        assertTrue(NodeID.isAnonymousNodeIRI("_:sometest_genid_something"));
        assertTrue(NodeID.isAnonymousNodeIRI("_:genid_something"));
        assertFalse(NodeID.isAnonymousNodeIRI("http://sometest_genid_something"));
        assertFalse(NodeID.isAnonymousNodeIRI((String) null));
    }

    @Test
    public void shouldFindAnonymousNodeIRI() {
        assertTrue(NodeID.isAnonymousNodeIRI(df.getIRI("_:sometest_genid_something")));
        assertTrue(NodeID.isAnonymousNodeIRI(df.getIRI("_:genid_something")));
        assertFalse(NodeID.isAnonymousNodeIRI(df.getIRI("http://sometest_genid#", "something")));
        assertFalse(NodeID.isAnonymousNodeIRI((IRI) null));
    }

    @Test
    public void shouldFindsharedNodeIRI() {
        assertFalse(NodeID.isAnonymousNodeID("_:sometest_genid-nodeid-_something"));
        assertTrue(NodeID.isAnonymousNodeID("_:genid-nodeid-_something"));
        assertFalse(NodeID.isAnonymousNodeID("http://sometest_genid-nodeid-_something"));
        assertFalse(NodeID.isAnonymousNodeID(null));
    }

    @Test
    public void shouldbuildNode() {
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
