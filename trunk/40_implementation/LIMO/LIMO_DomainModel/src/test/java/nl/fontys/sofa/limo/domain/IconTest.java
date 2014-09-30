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
        assertNull("There should be no Image object because of an incorrect URL",ic2.getIcon());
        //testing w/ method w/ URLstring correct
        Icon icX = new Icon("C:/icon.jpg");
        assertNotNull("There should be an Image object because of a correct URL",icX.getIcon());
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
            fail("Could not locate image");
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
            fail("Could not locate image");
        }
    }
    /**
     * Test of setIcon method, of class Icon.
     */
    @Test
    public void testSetIcon_byteArr() {
        System.out.println("setIcon");
                File fi = new File("C:/icon.jpg");
        try {
            fileContent = Files.readAllBytes(fi.toPath());
            System.out.println("Image loaded");
            Icon ic3 = new Icon();//create icon w/ no params, so empty
            ic3.setIcon(fileContent);
            Assert.assertArrayEquals("Identical byteArrays",fileContent,ic3.getIconBytes());
        } catch (IOException ex) {
            fail("Could not locate image");
        }
    }
    /**
     * Test of setIcon method, of class Icon.
     */
    @Test
    public void testSetIcon_Image() {
        System.out.println("setIcon");
        File fi = new File("C:/icon.jpg");
        try {
            fileContent = Files.readAllBytes(fi.toPath());
            System.out.println("Image loaded");
            Icon ic3 = new Icon();//new empty icon
            BufferedImage origBuffImg = ImageIO.read(new ByteArrayInputStream(fileContent));
            ic3.setIcon(origBuffImg);//set image into icon using setIcon
            BufferedImage buffImg1 = (BufferedImage) ic3.getIcon();
            assertEquals("Identical img heights",origBuffImg.getHeight(),buffImg1.getHeight(),1);
            
            Icon ic4 = new Icon();
            Image imgImage1  = new javax.swing.ImageIcon("C:/icon.jpg").getImage();
            ic4.setIcon(imgImage1);
            //assertEquals("Identical graphics",ic4.getIcon().getSource(),imgImage1.getSource());
            
            System.out.println("Height img: "+buffImg1.getHeight());
        } catch (IOException ex) {
            fail("Img could not be loaded at testSetIcon_Image");
        }
    }
    /**
     * Test of setImage method, of class Icon.
     */
    @Test
    public void testSetImage() {
        System.out.println("setImage");
        Icon ic1 = new Icon();//create empty icon
        ic1.setImage("C:/icon.jpg");//then use setImage to fill the icon object
        BufferedImage img1 = (BufferedImage) ic1.getIcon();//get the image from icon object back
        File fi = new File("C:/icon.jpg");//make file to compare images
        try {
            fileContent = Files.readAllBytes(fi.toPath());
            BufferedImage origImg = ImageIO.read(new ByteArrayInputStream(fileContent));
            assertEquals("Heights should be equal",img1.getHeight(),origImg.getHeight(),1);//comparing heights of artificially made image (origImg) and img using setter
        } catch(IOException ex) {
            fail("Could not locate test icon image at URL");
        }
    }
}