package nl.fontys.sofa.limo.validation;


import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BeanValidatorTest {
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testAssertFalse() {
    
    }
    
    @Test
    public void testAssertTrue() {
    
    }
    
    @Test
    public void testDecimalMinMax() {
        DecimalBean bean = new DecimalBean();
        bean.minValue = 2;
        bean.maxValue = 5;
        try {
            BeanValidator.validate(bean);
        } catch (ValidationException ex) {
            fail("DecimalBean should be valid.");
        }
        bean.minValue = 0;
        bean.maxValue = 5;
        try {
            BeanValidator.validate(bean);
            fail("DecimalBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
        bean.minValue = 1;
        bean.maxValue = 11;
        try {
            BeanValidator.validate(bean);
            fail("DecimalBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
    }
    
    @Test
    public void testDigits() {
    
    }
    
    @Test
    public void testPast() {
    
    }
    
    @Test
    public void testFuture() {
    
    }
    
    @Test
    public void testMin() {
    
    }
    
    @Test
    public void testMax() {
    
    }
    
    @Test
    public void testNotNull() {
    
    }
    
    @Test
    public void testNull() {
    
    }
    
    @Test
    public void testPattern() {
    
    }
    
    @Test
    public void testSize() {
    
    }
}
