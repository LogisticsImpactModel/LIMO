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
import org.openide.util.Exceptions;

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
        icon = null;

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
        try {
            BufferedImage inputImg = ImageIO.read(new File(this.location));
            assertTrue("Input img must have height>0 but has not",inputImg.getHeight()>0);
            byte[] inputImageBytes = ((DataBufferByte) inputImg.getData().getDataBuffer()).getData();
            
            icon.setData(inputImageBytes);
     
            BufferedImage outputImg = icon.getImage();
            //TODO: matthias: setData and afterwards retrieving img by calling getImage does not work
            
            
            //assertTrue(outputImg.getHeight()>0);
            //byte[] outputImageBytes = ((DataBufferByte) outputImg.getData().getDataBuffer()).getData();//using Icon class method getImage
            //Assert.assertArrayEquals("Input and output image byte arrays should match but do not",inputImageBytes,outputImageBytes);
        } catch (IOException ex) {
            fail("Could not locate image");
        }
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
        try {
            BufferedImage inputImg = ImageIO.read(new File(this.location));
            byte[] inputImageBytes = ((DataBufferByte) inputImg.getData().getDataBuffer()).getData();
                        
            icon.setImage(inputImg);
            
            BufferedImage outputImg = icon.getImage();
            byte[] outputImageBytes = ((DataBufferByte) outputImg.getData().getDataBuffer()).getData();
            Assert.assertArrayEquals("Byte arrays should be equal",inputImageBytes,outputImageBytes);
            assertTrue("Input img height should be > 0",inputImg.getHeight()>0);
            assertEquals("Input and output img should have same heights",inputImg.getHeight(),outputImg.getHeight());
        } catch (IOException e) {
            fail("Could not locate image at "+ this.location);
        }
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
     * Test of setImage method, providing String (loc), of class Icon.
     */
    @Test
    public void testSetImage_String() {
        System.out.println("setImage w/ string ref to existing img");
        icon.setImage(this.location);//set image based on URL

        byte[] imageBytes = ((DataBufferByte) icon.getImage().getData().getDataBuffer()).getData();
        assertNotNull("ByteArray of img should not be null",imageBytes);
       
    }
    /**
     * Test of setImage method, providing string (loc) WHICH DOES NOT EXIST
     * Throws exception
     */
    @Test
    public void testSetImage_String_notExistingPath() {
        System.out.println("setImage w/ string ref to Non-existing img");
        icon.setImage("lkjadfkljasdfkljasdklfj");//non existing path, img can not be found -> exception
        assertNull("Non existing image should not have a valid height but has",icon.getImage());        
    }
    
}
