package nl.fontys.sofa.limo.orientdb.serialization;

import nl.fontys.sofa.limo.domain.component.hub.Continent;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Ben
 */
public class ContinentSerializerTest {

    public ContinentSerializerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of serializeFieldValue method, of class ContinentSerializer.
     */
    @Test
    public void testSerializeFieldValue() {
        System.out.println("serializeFieldValue");
        Class iClass = null;
        ContinentSerializer instance = new ContinentSerializer();
        Object expResult = Continent.Africa.name();
        Object result = instance.serializeFieldValue(iClass, Continent.Africa);
        System.out.println("Result: " + expResult.toString());
        assertEquals(expResult, result.toString());
    }

    /**
     * Test of unserializeFieldValue method, of class ContinentSerializer.
     */
    @Test
    public void testUnserializeFieldValue() {
        System.out.println("unserializeFieldValue");
        Class iClass = null;
        Object iFieldValue = Continent.valueOf("Africa");
        ContinentSerializer instance = new ContinentSerializer();
        Object expResult = Continent.Africa;
        Object result = instance.unserializeFieldValue(iClass, "Africa");
        assertEquals(expResult, result);
    }

}
