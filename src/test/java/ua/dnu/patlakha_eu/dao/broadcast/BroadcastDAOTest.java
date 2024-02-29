package test.java.ua.dnu.patlakha_eu.dao.broadcast;

import main.java.ua.dnu.patlakha_eu.dao.broadcast.BroadcastDAO;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BroadcastDAOTest {
    private BroadcastDAO broadcastDAO;

    @Before
    public void setUp() {
        broadcastDAO = new BroadcastDAO();
    }

    @Test
    public void testAddBroadcast() {
        int duration = 60;
        int broadcastId = broadcastDAO.addBroadcast(duration);

        assertTrue(broadcastId > 0);
    }
}
