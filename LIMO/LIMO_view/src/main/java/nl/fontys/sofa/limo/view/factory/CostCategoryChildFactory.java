package nl.fontys.sofa.limo.view.factory;

import java.beans.IntrospectionException;
import java.util.List;
import nl.fontys.sofa.limo.api.dao.CostCategoryDAO;
import nl.fontys.sofa.limo.api.dao.DAOFactory;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.category.CostCategory;
import nl.fontys.sofa.limo.view.node.CostCategoryNode;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * Factory for creating the cost category children.
 *
 * @author Sebastiaan Heijmann
 */
public class CostCategoryChildFactory extends ChildFactory<CostCategory>
		implements LookupListener{

	private final Result<BaseEntity> lookupResult;
	private CostCategoryDAO ccd; 

	public CostCategoryChildFactory() {
		DAOFactory df = Lookup.getDefault().lookup(DAOFactory.class);
		ccd = df.getCostCategoryDAO();

		lookupResult = ccd.getLookup().lookupResult(BaseEntity.class);
		lookupResult.addLookupListener(this);
	}
	
	@Override
	protected boolean createKeys(List<CostCategory> list) {
		List<CostCategory> ccl = ccd.findAll();
			for(CostCategory cc : ccl){
				list.add(cc);
		}
		return true;
	}

    @Override
    protected Node createNodeForKey(CostCategory key) {
        BeanNode node = null;
        try {
			node = new CostCategoryNode(key);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }

	@Override
	public void resultChanged(LookupEvent le) {
	    refresh(true);
	}
}
