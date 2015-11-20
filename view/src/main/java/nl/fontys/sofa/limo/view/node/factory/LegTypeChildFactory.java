package nl.fontys.sofa.limo.view.node.factory;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.util.List;
import nl.fontys.sofa.limo.api.service.provider.LegTypeService;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.node.bean.LegTypeNode;
import nl.fontys.sofa.limo.view.util.IconUtil;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.nodes.NodeEvent;
import org.openide.nodes.NodeListener;
import org.openide.nodes.NodeMemberEvent;
import org.openide.nodes.NodeReorderEvent;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * Factory responsible for creating the LegType children. It listens to changes
 * in the service layer and in the nodes.
 *
 * @author Sebastiaan Heijmann
 */
public class LegTypeChildFactory extends ChildFactory<LegType>
        implements LookupListener, NodeListener {
    
    private final Result<LegType> lookupResult;
    private final LegTypeService service;
    
    public static final LegType MULTIMODE_LEGTYPE = new LegType();
    public static final LegType SCHEDULED_LEGTYPE = new LegType();

    /**
     * Constructor creates a new LegTypeChildFactory and attaches
     * {@link org.openide.util.LookupListener} on the child factories to listen
     * for changes in the data models.
     */
    public LegTypeChildFactory() {
        service = Lookup.getDefault().lookup(LegTypeService.class);
        lookupResult = service.getLookup().lookupResult(LegType.class);
        lookupResult.addLookupListener(this);
    }
    
    @Override
    protected boolean createKeys(List<LegType> list) {
        list.addAll(lookupResult.allInstances());
        MULTIMODE_LEGTYPE.setName("Multi Leg");
        Image image = IconUtil.getIcon(LegType.class, 2);
        Icon newIcon = new Icon((BufferedImage) image, "png");
        MULTIMODE_LEGTYPE.setIcon(newIcon);
        SCHEDULED_LEGTYPE.setName("Scheduled Leg");
        SCHEDULED_LEGTYPE.setIcon(newIcon);
        list.add(MULTIMODE_LEGTYPE);
        list.add(SCHEDULED_LEGTYPE);
        return true;
    }
    
    @Override
    protected Node createNodeForKey(LegType key) {
        BeanNode node = null;
        try {
            node = new LegTypeNode(key);
            node.addNodeListener(this);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }
    
    @Override
    public void resultChanged(LookupEvent ev) {
        refresh(true);
    }
    
    @Override
    public void nodeDestroyed(NodeEvent ne) {
        refresh(true);
    }
    
    @Override
    public void childrenAdded(NodeMemberEvent ev) {
    }
    
    @Override
    public void childrenRemoved(NodeMemberEvent ev) {
    }
    
    @Override
    public void childrenReordered(NodeReorderEvent ev) {
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent pce) {
    }
}
