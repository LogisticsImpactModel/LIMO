package nl.fontys.sofa.limo.orientdb.dao;

import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.domain.component.Event;
import nl.fontys.sofa.limo.orientdb.database.OrientDBDAOFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.junit.NbTestCase;

public class OrientDBEventDAOTest extends NbTestCase {

    private EventDAO eventDAO;

    public OrientDBEventDAOTest(String testCase) {
        super(testCase);
    }

    @Before
    @Override
    public void setUp() {
        OrientDBDAOFactory orientDBDAOFactory = new OrientDBDAOFactory();
        eventDAO = orientDBDAOFactory.getEventDAO();
    }

    @After
    @Override
    public void tearDown() {
        eventDAO = null;
    }

    /**
     * Test of findAll method, of class OrientDBEventDAO.
     */
    @Test
    public void testFindAll() {
        List<Event> events = eventDAO.findAll();
        assertTrue(events.isEmpty());
    }

    /**
     * Test of findById method, of class OrientDBEventDAO.
     */
    @Test
    public void testFindById() {
        Event event = eventDAO.findById("");
        assertNull(event);
        event = eventDAO.findById("38129803980");
        assertNull(event);
    }

    /**
     * Test of insert method, of class OrientDBEventDAO.
     */
    @Test
    public void testInsert() {
        Event event = new Event("pirates");
        eventDAO.insert(event);
        List<Event> events = eventDAO.findAll();
        assertEquals(1, events.size());
        Event foundEvent = eventDAO.findById("1");
        assertEquals(event, foundEvent);
    }

    /**
     * Test of update method, of class OrientDBEventDAO.
     */
    @Test
    public void testUpdate() {
        String newEventName = "pirate attack";
        Event event = new Event("pirates");
        boolean updateSuccess = eventDAO.update(event);
        assertFalse(updateSuccess);
        eventDAO.insert(event);
        event = eventDAO.findById("1");
        event.setIdentifier(newEventName);
        updateSuccess = eventDAO.update(event);
        assertTrue(updateSuccess);
        event = eventDAO.findById("1");
        assertEquals(newEventName, event.getIdentifier());
    }

    /**
     * Test of delete method, of class OrientDBEventDAO.
     */
    @Test
    public void testDelete() {
        boolean deleteSuccess = eventDAO.delete("");
        assertFalse(deleteSuccess);
        deleteSuccess = eventDAO.delete("798319203");
        assertFalse(deleteSuccess);
        Event event = new Event("pirates");
        eventDAO.insert(event);
        deleteSuccess = eventDAO.delete("1");
        assertTrue(deleteSuccess);
    }

    /**
     * Test of getTableName method, of class OrientDBEventDAO.
     */
    @Test
    public void testGetTableName() {
        assertNotNull(eventDAO.getTableName());
        assertFalse(eventDAO.getTableName().isEmpty());
    }

}
