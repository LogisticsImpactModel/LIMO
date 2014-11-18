package nl.fontys.sofa.limo.view.chain;

import nl.fontys.sofa.limo.view.node.AbstractBeanNode;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 * GraphScene to draw a supply chain on. Widgets can be dragged and dropped to 
 * this scene to build the chain from.
 *
 * @author Sebastiaan Heijmann
 */
public class GraphSceneImpl2 extends GraphScene<AbstractBeanNode, String> {
    
    private final LayerWidget mainLayer;

    public GraphSceneImpl2() {
        this.mainLayer = new LayerWidget(this);
        addChild(mainLayer);
    }
    
    @Override
    protected Widget attachNodeWidget(AbstractBeanNode node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Widget attachEdgeWidget(String edge) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void attachEdgeSourceAnchor(String edge, AbstractBeanNode oldSourceNode, AbstractBeanNode sourceNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void attachEdgeTargetAnchor(String edge, AbstractBeanNode oldTargetNode, AbstractBeanNode targetNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
