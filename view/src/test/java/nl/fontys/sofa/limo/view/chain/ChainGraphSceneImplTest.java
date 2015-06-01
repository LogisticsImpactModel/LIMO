/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.chain;

import java.beans.IntrospectionException;
import java.io.IOException;
import javax.swing.undo.UndoManager;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.view.node.bean.HubNode;
import nl.fontys.sofa.limo.view.topcomponent.ChainBuilderTopComponent;
import nl.fontys.sofa.limo.view.widget.HubWidget;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Scene;
import org.openide.util.Exceptions;

/**
 *
 * @author Merlissa Maas
 */
public class ChainGraphSceneImplTest {

    private ChainGraphSceneImpl cgsi;
    private UndoManager um;
    private Scene sc;

    private HubWidget hw1;
    private Hub h1;

    private HubWidget hw2;
    private Hub h2;

    private ConnectionWidget cw3;
    private ConnectionWidget cw4;

    public ChainGraphSceneImplTest() {

    }

    @Before
    public void setUp() {
        try {
            um = new UndoManager();
            cgsi = new ChainGraphSceneImpl(new ChainBuilderTopComponent("test"), new SupplyChain(), um);
            sc = cgsi.getScene();

            h1 = new Hub();

            h2 = new Hub();

            cw3 = new ConnectionWidget(sc);
            cw4 = new ConnectionWidget(sc);

        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @After
    public void tearDown() {
        cgsi = null;
        sc = null;
        um = null;

        h1 = null;
        hw1 = null;

        h2 = null;
        hw2 = null;

        cw3 = null;
        cw4 = null;
    }

    /**
     * Test of the undo/redo implementation.
     */
    @Test
    public void undoTest() {
        try {
            ChainGraphSceneImpl cgsi2 = cgsi;
            cgsi.addHubWidget(new HubWidget(cgsi.getScene(), new HubNode(h1)));

            Assert.assertNotEquals(cgsi2, cgsi);
            Assert.assertTrue(cgsi.getChainBuilder().getNumberOfHubs() == 1);
            Assert.assertEquals(cgsi.getChainBuilder().getStartHub(), h1);
            Assert.assertTrue(um.canUndo());
            um.undo();
            Assert.assertTrue(cgsi.getChainBuilder().getNumberOfHubs() == 0);
            Assert.assertEquals(cgsi, cgsi2);
            Assert.assertFalse(um.canUndo());
            Assert.assertTrue(um.canRedo());

            hw2 = new HubWidget(cgsi.getScene(), new HubNode(h2));
            cgsi.addHubWidget(hw2);
            ChainGraphSceneImpl cgsi3 = cgsi;
            cgsi.removeHubWidget(hw2);
            um.undo();
            Assert.assertTrue(cgsi.getChainBuilder().getNumberOfHubs() == 1);
            Assert.assertEquals(cgsi, cgsi3);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Test
    public void undoWithLegTest() {
        try {
            hw1 = new HubWidget(cgsi.getScene(), new HubNode(h1));
            hw2 = new HubWidget(cgsi.getScene(), new HubNode(h2));
            cgsi.addHubWidget(new HubWidget(cgsi.getScene(), new HubNode(h1)));
            cgsi.addHubWidget(new HubWidget(cgsi.getScene(), new HubNode(h2)));
            cgsi.connectHubWidgets(hw1, cw3, hw2);
            ChainGraphSceneImpl cgsi2 = cgsi;
            um.undo();
            cgsi.connectHubWidgets(hw1, cw4, hw2);
            Assert.assertNotEquals(cgsi, cgsi2);
            um.undo();
            um.undo();
            Assert.assertEquals(cgsi, cgsi2);
            
        } catch (IntrospectionException | IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
    public void redoTest() {
        try {
            ChainGraphSceneImpl cgsi3 = cgsi;
            hw1 = new HubWidget(cgsi.getScene(), new HubNode(h1));
            hw2 = new HubWidget(cgsi.getScene(), new HubNode(h2));
            cgsi.addHubWidget(new HubWidget(cgsi.getScene(), new HubNode(h1)));
            cgsi.addHubWidget(new HubWidget(cgsi.getScene(), new HubNode(h2)));
            cgsi.connectHubWidgets(hw1, cw3, hw2);
            ChainGraphSceneImpl cgsi2 = cgsi;
            cgsi.removeHubWidget(hw1);
            cgsi.removeHubWidget(hw2);
            Assert.assertEquals(cgsi, cgsi3);
            um.redo();
            um.redo();
            Assert.assertEquals(cgsi, cgsi2);
        } catch (IntrospectionException | IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
