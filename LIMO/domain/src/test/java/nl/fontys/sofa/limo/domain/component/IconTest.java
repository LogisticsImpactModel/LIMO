/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.domain.component;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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
        testSetData();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(icon.getImage(), "png", baos);
            byte[] outputImageBytes = baos.toByteArray();        
            Assert.assertArrayEquals("hi",outputImageBytes,icon.getData());
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /**
     * Test of setData method, of class Icon.
     */
    @Test
    public void testSetData() {
        System.out.println("setData");
        try {
            BufferedImage inputImg = ImageIO.read(new File(this.location));
            assertTrue("Input img must have height>0 but has not", inputImg.getHeight() > 0);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(inputImg, "png", baos);
            byte[] inputImageBytes = baos.toByteArray();

            icon.setData(inputImageBytes, "png");

            BufferedImage outputImg = icon.getImage();
            assertTrue(outputImg.getHeight() == 64);
        } catch (IOException ex) {
            fail("Could not locate image");
        }
    }

    /**
     * Test of getImage method, of class Icon.
     * Using default blank constructur as initialized by setUp
     */
    @Test
    public void testGetImage_blankConstructor() {
        System.out.println("getImage");
        testSetImage_String();//set img using other test
        BufferedImage actualImg = icon.getImage();
        assertTrue("Height of img must be 64 due to fixed size resizing", actualImg.getHeight() == 64);
    }
    /**
     * Test of getImage method, of class Icon
     * Using constructur having byteArray and imageType. Overrides already initialized icon object in setUp
     */
    @Test
    public void testGetImage_byteArrayConstructor(){
        try {
            BufferedImage inputImg = ImageIO.read(new File(this.location));
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(inputImg, "png", baos);
            byte[] inputImageBytes = baos.toByteArray();
            icon = new Icon(inputImageBytes,"png");//override icon object w/ new one w/ this constructor
            BufferedImage actualImg = icon.getImage();
            assertTrue("Height of img must be 64 due to fixed size resizing", actualImg.getHeight() == 64);
        } catch (IOException ex) {
            fail("Could not locate image");
        }
    }
        /**
     * Test of getImage method, of class Icon.
     * Using default blank constructur as initialized by setUp
     */
    @Test
    public void testGetImage_imageConstructor() {
        System.out.println("getImage");
        try {
            BufferedImage inputImg = ImageIO.read(new File(this.location));
            icon = new Icon(inputImg,"png");
            BufferedImage actualImg = icon.getImage();
            assertTrue("Height of img must be 64 due to fixed size resizing", actualImg.getHeight() == 64);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            fail("");
        }
    }

    /**
     * Test of setImage method, of class Icon.
     */
    @Test
    public void testSetImage_BufferedImage() {
        System.out.println("setImage");
        try {
            BufferedImage inputImg = ImageIO.read(new File(this.location));//orig res                        
            icon.setImage(inputImg, "png");//insert orig img into icon class
            BufferedImage outputImg = icon.getImage();//has been resized to 64x64
            assertTrue("Input img height should be > 0", inputImg.getHeight() > 0);
            assertEquals("Input and output img should have same heights", 64, outputImg.getHeight());
        } catch (IOException e) {
            fail("Could not locate image at " + this.location);
        }
    }

    /**
     * Test of setImage method, of class Icon.
     * Testing providing an image that is not instanceof BufferedImage (needs conversion)
     */
    @Test
    public void testSetImage_Image() {
        System.out.println("setImage");
        
    }

    /**
     * Test of setImage method, providing String (loc), of class Icon.
     */
    @Test
    public void testSetImage_String() {
        try {
            System.out.println("setImage w/ string ref to existing img");
            icon.setImage(this.location);//set image based on URL
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(icon.getImage(), "png", baos);
            byte[] imageBytes = baos.toByteArray();
            assertNotNull("ByteArray of img should not be null", imageBytes);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            fail();
        }

    }

    /**
     * Test of setImage method, providing string (loc) WHICH DOES NOT EXIST
     * Throws exception
     */
    @Test
    public void testSetImage_String_notExistingPath() {
        System.out.println("setImage w/ string ref to Non-existing img");
        icon.setImage("lkjadfkljasdfkljasdklfj");//non existing path, img can not be found -> exception
        assertNull("Non existing image should not have a valid height but has", icon.getImage());
    }

}
