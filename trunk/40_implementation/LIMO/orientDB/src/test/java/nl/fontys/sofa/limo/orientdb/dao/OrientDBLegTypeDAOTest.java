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
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.TimeType;
import nl.fontys.sofa.limo.domain.component.procedure.value.SingleValue;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.orientdb.OrientDBConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.junit.NbTestCase;

public class OrientDBLegTypeDAOTest extends NbTestCase {

    private OrientDBLegTypeDAO dao;

    public OrientDBLegTypeDAOTest(String testCase) {
        super(testCase);
    }

    @Before
    @Override
    public void setUp() {
        try {
            Field databaseURLField = OrientDBConnector.class.getDeclaredField("databaseURL");
            databaseURLField.setAccessible(true);
            databaseURLField.set(null, "memory:tests");
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException ex) {
            Logger.getLogger(OrientProcedureCategoryDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        dao = new OrientDBLegTypeDAO();
    }

    @After
    @Override
    public void tearDown() {
        for (LegType ht : dao.findAll()) {
            dao.delete(ht);
        }
        dao = null;
        OrientDBConnector.close();
    }

    /**
     * Test of findAll method, of class OrientDBLegTypeDAO.
     */
    @Test
    public void testFindAll() {
        List<LegType> costCategories = dao.findAll();
        assertTrue(costCategories.isEmpty());
    }

    /**
     * Test of findById method, of class OrientDBLegTypeDAO.
     */
    @Test
    public void testFindById() {
        LegType legType = dao.findById("");
        assertNull(legType);
        LegType legType2 = new LegType();
        legType2.setName("12345678");
        legType2.setIcon(new Icon());
        List<Procedure> procedures = new ArrayList<>();
        procedures.add(new Procedure("Costs1", "Costs", new SingleValue(1), new SingleValue(2), TimeType.MINUTES));
        procedures.add(new Procedure("Costs2", "Costs", new SingleValue(3), new SingleValue(4), TimeType.MINUTES));
        legType2.setProcedures(procedures);
        legType2 = dao.insert(legType2);
        legType = dao.findById(legType2.getId());
        assertNotNull(legType);
    }

    @Test
    public void testForMultipleObjects() {
        LegType t1 = new LegType();
        LegType t2 = new LegType();
        ArrayList<Procedure> procedures = new ArrayList<>();
        Procedure p = new Procedure("T", "C", new SingleValue(1), new SingleValue(2), TimeType.MINUTES);
        procedures.add(p);
        t1.setProcedures(procedures);
        t1 = dao.insert(t1);
        p.setName("asdigzah");
        t2.setProcedures(procedures);
        t2 = dao.insert(t2);

        t1 = dao.findById(t1.getId());
        t2 = dao.findById(t2.getId());
        assertNotSame(t1.getProcedures().get(0).getName(), t2.getProcedures().get(0).getName());
    }

    /**
     * Test of insert method, of class OrientDBLegTypeDAO.
     */
    @Test
    public void testInsert() {
        LegType legType = new LegType();
        legType.setName("112233");
        legType = dao.insert(legType);
        List<LegType> legTypes = dao.findAll();
        assertEquals(1, legTypes.size());
        LegType legType1 = dao.findById(legType.getId());
        assertEquals(legType.getId(), legType1.getId());
        assertEquals(legType.getName(), legType1.getName());
    }

    /**
     * Test of update method, of class OrientDBLegTypeDAO.
     */
    @Test
    public void testUpdate() {
        String newLegTypeName = "LegTypeNew";
        LegType legType = new LegType();
        legType.setName("112233");
        boolean updateSuccess = dao.update(legType);
        assertFalse(updateSuccess);
        legType = dao.insert(legType);
        legType = dao.findById(legType.getId());
        legType.setName(newLegTypeName);
        updateSuccess = dao.update(legType);
        assertTrue(updateSuccess);
        legType = dao.findById(legType.getId());
        assertEquals(newLegTypeName, legType.getName());
    }

    /**
     * Test of delete method, of class OrientDBLegTypeDAO.
     */
    @Test
    public void testDelete() {
        boolean deleteSuccess = dao.delete(new LegType());
        assertFalse(deleteSuccess);
        LegType legType = new LegType();
        legType.setName("112233");
        legType = dao.insert(legType);
        deleteSuccess = dao.delete(legType);
        assertTrue(deleteSuccess);
    }

}
