/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import nl.fontys.sofa.limo.domain.category.TimeCategory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openide.util.Exceptions;

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
     * Test of getIcon method, of class IconUtil.
     */
    @Test
    public void testGetIcon() {
        try {
            System.out.println("Loading image for comparison, 16px height and color (case 1)");
            BufferedImage sourceImg = ImageIO.read(new File("src/main/resources/icons/TimeCategory_16x16.png"));
            assertEquals("OrigImg must have height of 16",16,sourceImg.getHeight());
            BufferedImage buffImg = (BufferedImage) IconUtil.getIcon(TimeCategory.class, 1);
            assertEquals("Images should be the same height",sourceImg.getHeight(),buffImg.getHeight());
        } catch (IOException ex) {
            fail("Could not locate image for comparison in case 1");
        }
        
        try {
            System.out.println("Loading image for comparison, 32px height and color (case 2)");
            BufferedImage sourceImg2 = ImageIO.read(new File("src/main/resources/icons/TimeCategory_32x32.png"));
            assertEquals("OrigImg must have height of 32",32,sourceImg2.getHeight());
            BufferedImage buffImg2 = (BufferedImage) IconUtil.getIcon(TimeCategory.class, 2);
            assertEquals("Images should be the same height",sourceImg2.getHeight(),buffImg2.getHeight());
        } catch (IOException ex) {
            fail("Could not locate image for comparison in case 2");
        }
        
        try {
            System.out.println("Loading image for comparison, 16px height black white (case 3)");
            BufferedImage sourceImg3 = ImageIO.read(new File("src/main/resources/icons/TimeCategory_SW_16x16.png"));
            assertEquals("OrigImg must have height of 16",16,sourceImg3.getHeight());
            BufferedImage buffImg3 = (BufferedImage) IconUtil.getIcon(TimeCategory.class, 3);
            assertEquals("Images should be the same height",sourceImg3.getHeight(),buffImg3.getHeight());
        } catch (IOException ex) {
            fail("Could not locate image for comparison in case 3");
        }
        try {
            System.out.println("Loading image for comparison, 32px height black white (case 4)");
            BufferedImage sourceImg4 = ImageIO.read(new File("src/main/resources/icons/TimeCategory_SW_32x32.png"));
            assertEquals("OrigImg must have height of 32",32,sourceImg4.getHeight());
            BufferedImage buffImg4 = (BufferedImage) IconUtil.getIcon(TimeCategory.class, 4);
            assertEquals("Images should be the same height",sourceImg4.getHeight(),buffImg4.getHeight());
        } catch (IOException ex) {
            fail("Could not locate image for comparison in case 4");
        }
        
    }
    
}
