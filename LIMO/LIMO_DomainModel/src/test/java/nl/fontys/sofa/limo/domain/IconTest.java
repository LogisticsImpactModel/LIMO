/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.domain;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    byte[] fileContent;
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
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getIcon method, of class Icon.
     */
    @Test
    public void testGetIcon() {
        System.out.println("getIcon");
        //testing w/ method w/o parameters
        Icon ic1 = new Icon();//icon w/ empty byteArray
        assertNull("There should be no Image object because of an empty byte array",ic1.getIcon());
        //testing w/ method w/ URLstring incorrect
        Icon ic2 = new Icon("C:\notExisting.crap");
        assertNull("There should be nog Image object because of an incorrect URL",ic2.getIcon());
        //lead to existing file to convert to bytes
        File fi = new File("C:/icon.jpg");
        try {
            fileContent = Files.readAllBytes(fi.toPath());
            System.out.println("Image loaded");
            Icon ic3 = new Icon(fileContent);
            BufferedImage img1 = (BufferedImage) ic3.getIcon();
            BufferedImage origImg = ImageIO.read(new ByteArrayInputStream(fileContent));
            assertEquals("Identical img heights",origImg.getHeight(),img1.getHeight(),1);
            System.out.println("Height img: "+img1.getHeight());
        } catch (IOException ex) {
            System.out.println("Could not locate image");
        }
        
    }

    /**
     * Test of getIconBytes method, of class Icon.
     */
    @Test
    public void testGetIconBytes() {
        System.out.println("getIconBytes");
        //testing w/ File/Image object
        File fi = new File("C:/icon.jpg");
        try {
            fileContent = Files.readAllBytes(fi.toPath());
            Icon ic3 = new Icon(fileContent);
            System.out.println("Image loaded");
            Assert.assertArrayEquals("Identical byteArrays",fileContent,ic3.getIconBytes());
        } catch (IOException ex) {
            System.out.println("Could not locate image");
        }
    }

    /**
     * Test of setIcon method, of class Icon.
     */
    @Test
    public void testSetIcon_byteArr() {
        System.out.println("setIcon");
        byte[] icon = null;
        Icon instance = new Icon();
        instance.setIcon(icon);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setIcon method, of class Icon.
     */
    @Test
    public void testSetIcon_Image() {
        System.out.println("setIcon");
        Image image = null;
        Icon instance = new Icon();
        //instance.setIcon(image);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setImage method, of class Icon.
     */
    @Test
    public void testSetImage() {
        System.out.println("setImage");
        String iconPath = "";
        Icon instance = new Icon();
        instance.setImage(iconPath);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
