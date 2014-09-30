package nl.fontys.sofa.limo.orientdb.dao;

import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import nl.fontys.sofa.limo.api.dao.IconDAO;
import nl.fontys.sofa.limo.domain.Icon;
import nl.fontys.sofa.limo.domain.category.TimeCategory;
import nl.fontys.sofa.limo.orientdb.mock.MockOrientDBAccess;
import nl.fontys.sofa.limo.orientdb.mock.OrientDBDAOFactoryMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.junit.NbTestCase;

public class OrientDBIconDAOTest extends NbTestCase {

    private IconDAO iconDAO;

    public OrientDBIconDAOTest(String testCase) {
        super(testCase);
    }

    @Before
    @Override
    public void setUp() {
        OrientDBDAOFactoryMock orientDBDAOFactory = new OrientDBDAOFactoryMock();
        iconDAO = orientDBDAOFactory.getIconDAO();
    }

    @After
    @Override
    public void tearDown() {
        iconDAO = null;
        MockOrientDBAccess.getInstance().closeConnection();
    }

    /**
     * Test of findAll method, of class OrientDBIconDAO.
     */
    @Test
    public void testFindAll() {
        List<Icon> icons = iconDAO.findAll();
        assertTrue(icons.isEmpty());
    }

    /**
     * Test of findById method, of class OrientDBIconDAO.
     */
    @Test
    public void testFindById() {
        Icon icon = iconDAO.findById("");
        assertNull(icon);
        icon = iconDAO.findById("38129803980");
        assertNull(icon);
    }

    /**
     * Test of insert method, of class OrientDBIconDAO.
     */
    @Test
    public void testInsert() {
        Icon icon = new Icon();
        iconDAO.insert(icon);
        List<Icon> icons = iconDAO.findAll();
        assertEquals(1, icons.size());
        Icon foundIcon = iconDAO.findById(icons.get(0).getId());
        assertEquals(icon.getId(), foundIcon.getId());
        Assert.assertArrayEquals(icon.getIconByteArray(), foundIcon.getIconByteArray());
    }

    /**
     * Test of update method, of class OrientDBIconDAO.
     */
    @Test
    public void testUpdate() {
        byte[] newIconBytes = new byte[]{1, 2, 3};
        Icon icon = new Icon();
        boolean updateSuccess = iconDAO.update(icon);
        assertFalse(updateSuccess);
        iconDAO.insert(icon);
        icon = iconDAO.findById(icon.getId());
        icon.setIconByteArray(newIconBytes);
        updateSuccess = iconDAO.update(icon);
        assertTrue(updateSuccess);
        icon = iconDAO.findById(icon.getId());
        Assert.assertArrayEquals(newIconBytes, icon.getIconByteArray());
    }

    /**
     * Test of delete method, of class OrientDBIconDAO.
     */
    @Test
    public void testDelete() {
        List<Icon> icons = iconDAO.findAll();
        assertTrue(icons.isEmpty());
        boolean deleteSuccess = iconDAO.delete("");
        assertFalse(deleteSuccess);
        deleteSuccess = iconDAO.delete("123345");
        assertFalse(deleteSuccess);
        Icon icon = new Icon();
        iconDAO.insert(icon);
        icons = iconDAO.findAll();
        assertEquals(1, icons.size());
        deleteSuccess = iconDAO.delete(icon.getId());
        assertTrue(deleteSuccess);
        icons = iconDAO.findAll();
        assertTrue(icons.isEmpty());
    }

}
