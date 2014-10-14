/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.node;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Action;
import nl.fontys.sofa.limo.domain.category.CostCategory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openide.util.Exceptions;
import org.openide.util.datatransfer.NewType;

/**
 *
 * @author Ben
 */
public class CostCategoryRootNodeTest {
    
    public CostCategoryRootNodeTest() {
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
     * Test of getIcon method, of class CostCategoryRootNode.
     */
    @Test
    public void testGetIcon() {
        System.out.println("getIcon");
        try {
            ProcedureCategoryNode ccn = new ProcedureCategoryNode(new CostCategory());
            try {
                //load orig. icon what we are looking for for comparison
                BufferedImage sourceImg = ImageIO.read(new File("src/main/resources/icons/CostCategory_32x32.png"));
                //get appl. icon in ProcedureCategoryNode class w/ type 2 (32x32)
                BufferedImage buffImg = (BufferedImage) ccn.getIcon(2);
                //compare orig. icon and icon from CCNode's byte[]s
                Assert.assertArrayEquals("Images should consist out of identical byteArrays",((DataBufferByte) sourceImg.getData().getDataBuffer()).getData(),((DataBufferByte) buffImg.getData().getDataBuffer()).getData());
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }  
    }

    /**
     * Test of getActions method, of class CostCategoryRootNode.
     */
    @Test
    public void testGetActions() {
        System.out.println("getActions");
      
    }

    /**
     * Test of getNewTypes method, of class CostCategoryRootNode.
     */
    @Test
    public void testGetNewTypes() {
        System.out.println("getNewTypes");
        
    }
    
}
