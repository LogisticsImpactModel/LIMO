/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.TimeType;
import nl.fontys.sofa.limo.domain.component.procedure.value.SingleValue;
import nl.fontys.sofa.limo.orientdb.OrientDBConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.junit.NbTestCase;

public class OrientDBLegDAOTest extends NbTestCase {

    private OrientDBLegDAO dao;

    public OrientDBLegDAOTest(String testCase) {
        super(testCase);
    }

    @Before
    @Override
    public void setUp() {
        try {
            Field databaseURLField = OrientDBConnector.class.getDeclaredField("databaseURL");
            databaseURLField.setAccessible(true);
            databaseURLField.set(null, "memory:tests");
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(OrientProcedureCategoryDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        dao = new OrientDBLegDAO();
    }

    @After
    @Override
    public void tearDown() {
        for (Leg ht : dao.findAll()) {
            dao.delete(ht);
        }
        dao = null;
        OrientDBConnector.close();
    }

    /**
     * Test of findAll method, of class OrientDBLegDAO.
     */
    @Test
    public void testFindAll() {
        List<Leg> hubs = dao.findAll();
        assertTrue(hubs.isEmpty());
    }

    /**
     * Test of findById method, of class OrientDBLegDAO.
     */
    @Test
    public void testFindById() {
        Leg leg = dao.findById("");
        assertNull(leg);
        leg = dao.findById("38129803980");
        assertNull(leg);
    }

    /**
     * Test of insert method, of class OrientDBLegDAO.
     */
    @Test
    public void testInsert() {
        Leg leg = new Leg();
        leg.setName("11112");
        ArrayList<Procedure> procedures = new ArrayList<>();
        procedures.add(new Procedure("Cost1", "Costs", new SingleValue(1), new SingleValue(2), TimeType.MINUTES));
        procedures.add(new Procedure("Cost2", "Costs", new SingleValue(3), new SingleValue(4), TimeType.MINUTES));
        leg.setProcedures(procedures);
        leg.setIcon(new Icon());
        leg = dao.insert(leg);
        List<Leg> legs = dao.findAll();
        assertEquals(1, legs.size());
        Leg foundLeg = dao.findById(legs.get(0).getId());
        assertEquals(leg.getId(), foundLeg.getId());
        assertEquals(leg.getName(), foundLeg.getName());
    }

    /**
     * Test of update method, of class OrientDBLegDAO.
     */
    @Test
    public void testUpdate() {
        String newLegName = "LegXYZ";
        Leg leg = new Leg();
        leg.setName("1234");
        boolean updateSuccess = dao.update(leg);
        assertFalse(updateSuccess);
        leg = dao.insert(leg);
        leg = dao.findById(leg.getId());
        leg.setName(newLegName);
        updateSuccess = dao.update(leg);
        assertTrue(updateSuccess);
        leg = dao.findById(leg.getId());
        assertEquals(newLegName, leg.getName());
    }

    /**
     * Test of delete method, of class OrientDBLegDAO.
     */
    @Test
    public void testDelete() {
        boolean deleteSuccess = dao.delete(new Leg());
        assertFalse(deleteSuccess);
        Leg leg = new Leg();
        leg.setName("112233");
        leg = dao.insert(leg);
        deleteSuccess = dao.delete(leg);
        assertTrue(deleteSuccess);
    }
}
