package nl.fontys.sofa.limo.validation;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import org.junit.Test;
import static org.junit.Assert.*;

public class BeanValidatorTest {
    
    @Test
    public void testAssertTrueFalse() {
        BooleanBean bean = new BooleanBean();
        bean.trueValue = true;
        bean.falseValue = false;
         try {
            BeanValidator.validate(bean);
        } catch (ValidationException ex) {
            fail("BooleanBean should be valid.");
        }
        bean.trueValue = false;
        bean.falseValue = false;
        try {
            BeanValidator.validate(bean);
            fail("BooleanBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
        bean.trueValue = true;
        bean.falseValue = true;
        try {
            BeanValidator.validate(bean);
            fail("BooleanBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
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
    public void testPastFuture() {
        Calendar cal = Calendar.getInstance();
        DateBean bean = new DateBean();
        
        cal.setTime(Date.valueOf(LocalDate.now()));
        cal.add(Calendar.DATE, -30);
        bean.pastDate = cal.getTime();
        cal.setTime(Date.valueOf(LocalDate.now()));
        cal.add(Calendar.DATE, 30);
        bean.futureDate = cal.getTime();
        try {
            BeanValidator.validate(bean);
        } catch (ValidationException ex) {
            fail("DateBean should be valid.");
        }
        cal.setTime(Date.valueOf(LocalDate.now()));
        cal.add(Calendar.DATE, 30);
        bean.pastDate = cal.getTime();
        cal.setTime(Date.valueOf(LocalDate.now()));
        cal.add(Calendar.DATE, 30);
        bean.futureDate = cal.getTime();
         try {
            BeanValidator.validate(bean);
            fail("DateBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
        cal.setTime(Date.valueOf(LocalDate.now()));
        cal.add(Calendar.DATE, -30);
        bean.pastDate = cal.getTime();
        cal.setTime(Date.valueOf(LocalDate.now()));
        cal.add(Calendar.DATE, -30);
        bean.futureDate = cal.getTime();
         try {
            BeanValidator.validate(bean);
            fail("DateBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
    }
    
    @Test
    public void testMinMax() {
    
    }
    
    @Test
    public void testNullNotNull() {
    
    }
    
    @Test
    public void testPattern() {
    
    }
    
    @Test
    public void testSize() {
    
    }
}
