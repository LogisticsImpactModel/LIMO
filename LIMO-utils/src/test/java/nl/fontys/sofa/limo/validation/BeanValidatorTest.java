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
        IntegerBean bean = new IntegerBean();
        bean.minValue = 1;
        bean.maxValue = 9;
        try {
            BeanValidator.validate(bean);
        } catch (ValidationException ex) {
            fail("IntegerBean should be valid.");
        }
        bean.minValue = -1;
        bean.maxValue = 5;
        try {
            BeanValidator.validate(bean);
            fail("IntegerBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
        bean.minValue = 1;
        bean.maxValue = 11;
        try {
            BeanValidator.validate(bean);
            fail("IntegerBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
    }
    
    @Test
    public void testNullNotNull() {
        ObjectBean bean = new ObjectBean();
        bean.notNullObject = new Object();
        bean.nullObject = null;
        try {
            BeanValidator.validate(bean);
        } catch (ValidationException ex) {
            fail("ObjectBean should be valid.");
        }
        bean.notNullObject = null;
        bean.nullObject = null;
        try {
            BeanValidator.validate(bean);
            fail("ObjectBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
        bean.notNullObject = new Object();
        bean.nullObject = new Object();
        try {
            BeanValidator.validate(bean);
            fail("ObjectBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
    }
    
    @Test
    public void testPattern() {
        EmailBean bean = new EmailBean();
        bean.email = "test@web.de";
        try {
            BeanValidator.validate(bean);
        } catch (ValidationException ex) {
            fail("EmailBean should be valid.");
        }
        bean.email = "abcdefg";
        try {
            BeanValidator.validate(bean);
            fail("EmailBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
        bean.email = "";
        try {
            BeanValidator.validate(bean);
            fail("EmailBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
        bean.email = null;
        try {
            BeanValidator.validate(bean);
            fail("EmailBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
        bean.email = "hello@world";
        try {
            BeanValidator.validate(bean);
            fail("EmailBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
        
    }
    
    @Test
    public void testSizeString() {
        SizeBean bean = new SizeBean();
        try {
            BeanValidator.validate(bean);
        } catch (ValidationException ex) {
            fail("SizeBean should be valid.");
        }
        bean.text = "h";
         try {
            BeanValidator.validate(bean);
            fail("SizeBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
         bean.text = null;
         try {
            BeanValidator.validate(bean);
            fail("SizeBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
         bean.text = "";
         try {
            BeanValidator.validate(bean);
            fail("SizeBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
         bean.text = "hello world";
         try {
            BeanValidator.validate(bean);
            fail("SizeBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
    }
    
    @Test
    public void testSizeCollection() {
        SizeBean bean = new SizeBean();
        bean.list.add("b");
        try {
            BeanValidator.validate(bean);
        } catch (ValidationException ex) {
            fail("SizeBean should be valid.");
        }
        bean.list.add("b");
        try {
            BeanValidator.validate(bean);
            fail("SizeBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
        bean.list.clear();
        try {
            BeanValidator.validate(bean);
            fail("SizeBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
        bean.list = null;
        try {
            BeanValidator.validate(bean);
            fail("SizeBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
    }
    
    @Test
    public void testSizeArray() {
        SizeBean bean = new SizeBean();
        try {
            BeanValidator.validate(bean);
        } catch (ValidationException ex) {
            fail("SizeBean should be valid.");
        }
        bean.array = new String[]{};
        try {
            BeanValidator.validate(bean);
            fail("SizeBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
        bean.array = new String[]{"a"};
        try {
            BeanValidator.validate(bean);
            fail("SizeBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
        bean.array = new String[]{"a", "b", "c", "d", "e"};
        try {
            BeanValidator.validate(bean);
            fail("SizeBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
        bean.array = null;
        try {
            BeanValidator.validate(bean);
            fail("SizeBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
    }
    
    @Test
    public void testSizeMap() {
        SizeBean bean = new SizeBean();
        try {
            BeanValidator.validate(bean);
        } catch (ValidationException ex) {
            fail("SizeBean should be valid.");
        }
        bean.map.put("r", "b");
        bean.map.put("t", "b");
        bean.map.put("x", "b");
        try {
            BeanValidator.validate(bean);
            fail("SizeBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
        bean.map.clear();
        try {
            BeanValidator.validate(bean);
            fail("SizeBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
        bean.map = null;
        try {
            BeanValidator.validate(bean);
            fail("SizeBean should be invalid.");
        } catch (ValidationException ex) {
            // good
        }
    }
}
