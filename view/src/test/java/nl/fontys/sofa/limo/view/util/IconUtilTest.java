/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.util;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
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

/**
 *
 * @author Ben
 */
public class IconUtilTest {
    
    public IconUtilTest() {
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
     * getFileName 
     */
    @Test
    public void testGetFilename(){
        assertEquals("Filename should be Edit for enum EDIT","edit",IconUtil.UI_ICON.EDIT.getFilename());
    }
//    /**
//     * getIcon based on enum
//     */
//    @Test
//    public void testGetIcon_enum (){
//        TODO: make all icons strictly 16x16 or 32x32 in files, so that height can be verified based on pixels
//        BufferedImage buffImg5 = (BufferedImage) IconUtil.getIcon(IconUtil.UI_ICON.EDIT);
//        assertTrue("Height should be more than 0 pixels",buffImg5.getHeight()>0);
//        BufferedImage buffImg6 = (BufferedImage) IconUtil.getIcon(IconUtil.UI_ICON.ADD);
//        assertTrue("Height should be more than 0 pixels",buffImg6.getHeight()>0);
//    }
//    /**
//     * getIcon based on class and req. size
//     */
//    @Test
//    public void testGetIcon() {
//        try {
//            System.out.println("Loading image for comparison, 16px height and color (case 1)");
//            BufferedImage sourceImg = ImageIO.read(new File("src/main/resources/icons/TimeCategory_16x16.png"));
//            assertEquals("OrigImg must have height of 16",16,sourceImg.getHeight());
//            BufferedImage buffImg = (BufferedImage) IconUtil.getIcon(TimeCategory.class, 1);
//            Assert.assertArrayEquals("Images should consist out of identical byteArrays",((DataBufferByte) sourceImg.getData().getDataBuffer()).getData(),((DataBufferByte) buffImg.getData().getDataBuffer()).getData());
//        } catch (IOException ex) {
//            fail("Could not locate image for comparison in case 1");
//        }
//        
//        try {
//            System.out.println("Loading image for comparison, 32px height and color (case 2)");
//            BufferedImage sourceImg2 = ImageIO.read(new File("src/main/resources/icons/TimeCategory_32x32.png"));
//            assertEquals("OrigImg must have height of 32",32,sourceImg2.getHeight());
//            BufferedImage buffImg2 = (BufferedImage) IconUtil.getIcon(TimeCategory.class, 2);
//            Assert.assertArrayEquals("Images should consist out of identical byteArrays",((DataBufferByte) sourceImg2.getData().getDataBuffer()).getData(),((DataBufferByte) buffImg2.getData().getDataBuffer()).getData());
//        } catch (IOException ex) {
//            fail("Could not locate image for comparison in case 2");
//        }
//        
//        try {
//            System.out.println("Loading image for comparison, 16px height black white (case 3)");
//            BufferedImage sourceImg3 = ImageIO.read(new File("src/main/resources/icons/TimeCategory_SW_16x16.png"));
//            assertEquals("OrigImg must have height of 16",16,sourceImg3.getHeight());
//            BufferedImage buffImg3 = (BufferedImage) IconUtil.getIcon(TimeCategory.class, 3);
//            Assert.assertArrayEquals("Images should consist out of identical byteArrays",((DataBufferByte) sourceImg3.getData().getDataBuffer()).getData(),((DataBufferByte) buffImg3.getData().getDataBuffer()).getData());
//        } catch (IOException ex) {
//            fail("Could not locate image for comparison in case 3");
//        }
//        try {
//            System.out.println("Loading image for comparison, 32px height black white (case 4)");
//            BufferedImage sourceImg4 = ImageIO.read(new File("src/main/resources/icons/TimeCategory_SW_32x32.png"));
//            assertEquals("OrigImg must have height of 32",32,sourceImg4.getHeight());
//            BufferedImage buffImg4 = (BufferedImage) IconUtil.getIcon(TimeCategory.class, 4);
//            Assert.assertArrayEquals("Images should consist out of identical byteArrays",((DataBufferByte) sourceImg4.getData().getDataBuffer()).getData(),((DataBufferByte) buffImg4.getData().getDataBuffer()).getData());
//        } catch (IOException ex) {
//            fail("Could not locate image for comparison in case 4");
//        }
//        //assert null for not existing switch case options
//        assertNull("Null expected for non existing switch case",IconUtil.getIcon(TimeCategory.class, 5));
//    
//    }

    
}
