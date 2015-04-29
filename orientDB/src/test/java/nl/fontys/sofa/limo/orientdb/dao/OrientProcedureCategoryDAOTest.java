package nl.fontys.sofa.limo.orientdb.dao;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.orientdb.OrientDBConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.junit.NbTestCase;

/**
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
public class OrientProcedureCategoryDAOTest extends NbTestCase{
    
    private OrientDBProcedureCategoryDAO dao;

    public OrientProcedureCategoryDAOTest(String name) {
        super(name);
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
        
        dao = new OrientDBProcedureCategoryDAO();
    }
    
    @After
    @Override
    public void tearDown() {
        for (ProcedureCategory ht : dao.findAll())
            dao.delete(ht);
        dao = null;
        OrientDBConnector.close();
    }

    @Test
    public void testFindAll() {
        assertEquals("No entities yet", 0, dao.findAll().size());
        dao.insert(getEntityInstance("Test 1"));
        assertEquals("1 entity", 1, dao.findAll().size());
        dao.insert(getEntityInstance("Test 2"));
        assertEquals("2 entity", 2, dao.findAll().size());
        dao.insert(getEntityInstance("Test 3"));
        assertEquals("3 entity", 3, dao.findAll().size());
    }

    @Test
    public void testFindById() {
        ProcedureCategory entity = dao.insert(getEntityInstance("Test Category"));
        assertNull("Null id", dao.findById(null));
        assertNull("Wrong id", dao.findById("asduihb"));
        assertNull("Unknown id", dao.findById("#100:100"));
        
        ProcedureCategory dbEntity = dao.findById(entity.getId());
        assertNotNull("Entity found", dbEntity);
        assertEquals("Same uuid", entity.getUniqueIdentifier(), dbEntity.getUniqueIdentifier());
        assertEquals("Same name", entity.getName(), dbEntity.getName());
    }

    @Test
    public void testFindByUniqueIdentifier() {
        ProcedureCategory entity = dao.insert(getEntityInstance("Test Category"));
        assertNull("Null id", dao.findByUniqueIdentifier(null));
        assertNull("Wrong id", dao.findByUniqueIdentifier("asduihb"));
        assertNull("Unknown id", dao.findByUniqueIdentifier("#100:100"));
        
        ProcedureCategory dbEntity = dao.findByUniqueIdentifier(entity.getUniqueIdentifier());
        assertNotNull("Entity found", dbEntity);
        assertEquals("Same id", entity.getId(), dbEntity.getId());
        assertEquals("Same uuid", entity.getUniqueIdentifier(), dbEntity.getUniqueIdentifier());
        assertEquals("Same name", entity.getName(), dbEntity.getName());
    }

    @Test
    public void testInsert() {
        ProcedureCategory testEntity = getEntityInstance("Test Category");
        
        // Test insert
        ProcedureCategory dbEntity = dao.insert(testEntity);
        assertNotNull("Not null dbEntity", dbEntity);
        assertNotNull("Not null ID", dbEntity.getId());
        assertEquals("Same unique identifier", testEntity.getUniqueIdentifier(), dbEntity.getUniqueIdentifier());
        assertEquals("Same name", testEntity.getName(), dbEntity.getName());
        
        // Test false insert
        assertNull("False insert", dao.insert(dbEntity));
    }

    @Test
    public void testUpdate() {
        ProcedureCategory testEntity = getEntityInstance("Test Category");
        
        // Test false update
        assertFalse("False update", dao.update(testEntity));
        
        // Test update
        ProcedureCategory dbEntity = dao.insert(testEntity);
        dbEntity.setName("Update");
        assertTrue("Update dbEntity", dao.update(dbEntity));
        assertEquals("dbEntity updated name", dbEntity.getName(), dao.findById(dbEntity.getId()).getName());
    }

    @Test
    public void testDelete() {
        ProcedureCategory testEntity = getEntityInstance("Test Category");
        
        // Test insert
        ProcedureCategory dbEntity = dao.insert(testEntity);
        assertNotNull("Not null dbEntity", dbEntity);
        assertNotNull("Not null ID", dbEntity.getId());
        assertEquals("Same unique identifier", testEntity.getUniqueIdentifier(), dbEntity.getUniqueIdentifier());
        assertEquals("Same name", testEntity.getName(), dbEntity.getName());
        
        // Test delete
        assertFalse("Delete null", dao.delete(null));
        assertTrue("Delete", dao.delete(dbEntity));
        assertNull("No entity left", dao.findById(dbEntity.getId()));
    }

    @Test
    public void testStringIsValidId() {
        System.out.println("=== Testing: stringIsValidId ===");
        
        String[] ids = new String[]{"sjhadb", "112314", "#10:3", "10:4"};
        boolean[] expected = new boolean[]{false, false, true, true};
        
        for (int i = 0; i < ids.length; i++) {
            assertEquals("- Test of ID: " + ids[i], expected[i], this.dao.stringIsValidId(ids[i]));
        }
        
        System.out.println("Test succesful!");
        System.out.println();
    }
    
    private ProcedureCategory getEntityInstance(String name) {
        ProcedureCategory entity = new ProcedureCategory();
        entity.setName(name);
        return entity;
    }
    
}
