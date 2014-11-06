/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.domain.component;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ben
 */
public class IconTest {
    Icon icon;
    String location = "testing_src/ic.png";
    
    public IconTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        icon = new Icon();//icon w/ empty byteArray
    }
    
    @After
    public void tearDown() {

    }

    /**
     * Test of getData method, of class Icon.
     */
    @Test
    public void testGetData() {
        System.out.println("getData");
    }

    /**
     * Test of setData method, of class Icon.
     */
    @Test
    public void testSetData() {
        System.out.println("setData");        
    }

    /**
     * Test of getImage method, of class Icon.
     */
    @Test
    public void testGetImage() {
        System.out.println("getImage");
        testSetImage_String();//set img using other test
        BufferedImage actualImg = icon.getImage();
        byte[] actualImageBytes = ((DataBufferByte) actualImg.getData().getDataBuffer()).getData();//using Icon class method getImage
        
        BufferedImage expectedImg;
        try {
            expectedImg = ImageIO.read(new File(this.location));
            byte[] expectedImageBytes = ((DataBufferByte) expectedImg.getData().getDataBuffer()).getData();
            assertTrue("Actual image should have a dimension higher than 0",actualImg.getHeight()>0);
            System.out.println("Actual img height: "+actualImg);
            Assert.assertArrayEquals("ByteArrays for actual and expected images should be equal but are not",expectedImageBytes,actualImageBytes);
        } catch (IOException e) {
            fail("Could not locate image at "+ this.location);
        }
       
    }

    /**
     * Test of setImage method, of class Icon.
     */
    @Test
    public void testSetImage_BufferedImage() {
        System.out.println("setImage");
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of setImage method, of class Icon.
     */
    @Test
    public void testSetImage_Image() {
        System.out.println("setImage");
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of setImage method, of class Icon.
     */
    @Test
    public void testSetImage_String() {
        System.out.println("setImage");
        icon.setImage(this.location);//set image based on URL

        byte[] imageBytes = ((DataBufferByte) icon.getImage().getData().getDataBuffer()).getData();
        assertNotNull("ByteArray of img should not be null",imageBytes);
       
    }
    
}
