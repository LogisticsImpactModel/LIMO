package nl.fontys.limo.view.factory;

import java.beans.IntrospectionException;
import java.util.List;
import nl.fontys.sofa.limo.api.dao.CostCategoryDAO;
import nl.fontys.sofa.limo.api.dao.DAOFactory;
import nl.fontys.sofa.limo.domain.category.CostCategory;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

/**
 * Factory for creating the Cost Category children.
 *
 * @author Sebastiaan Heijmann
 */
public class CostCategoryChildFactory extends ChildFactory<String> {

    @Override
    protected boolean createKeys(List<String> list) {
        DAOFactory df = Lookup.getDefault().lookup(DAOFactory.class);
        CostCategoryDAO ccd = df.getCostCategoryDAO();
        List<CostCategory> ccl = ccd.findAll();
        for (CostCategory cc : ccl) {
            list.add(cc.getIdentifier());
        }
        return true;
    }

    @Override
    protected Node createNodeForKey(String key) {
        BeanNode node = null;
        try {
            node = new BeanNode(key);
            node.setDisplayName(key);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }
}
