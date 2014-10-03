package nl.fontys.limo.view.node;

import java.io.IOException;
import javax.swing.Action;
import nl.fontys.sofa.limo.api.dao.CostCategoryDAO;
import nl.fontys.sofa.limo.api.dao.DAOFactory;
import nl.fontys.sofa.limo.domain.category.CostCategory;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.actions.NewAction;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.NewType;

/**
 * Root node class for cost category. Defines actions which can be invoked.
 *
 * @author Sebastiaan Heijmann
 */
public class CostCategoryRootNode extends AbstractNode{

	public CostCategoryRootNode(Children children) {
		super(children);
	}

	@Override
	public Action[] getActions(boolean context) {
		return new Action[]{SystemAction.get(NewAction.class)};
	}

	@Override
	public NewType[] getNewTypes() {
		return new NewType[]{new NewType(){

			@Override
			public String getName() {
				return "Cost Category";
			}

			@Override
			public void create() throws IOException {
				NotifyDescriptor.InputLine name =
								new NotifyDescriptor.InputLine("Name",
												"New Cost Category");
				NotifyDescriptor.InputLine description =
								new NotifyDescriptor.InputLine("Description",
												"New Cost Category");
				DialogDisplayer.getDefault().notify(name);
				DialogDisplayer.getDefault().notify(description);

				CostCategory cc = new CostCategory();
				cc.setIdentifier(name.getInputText());
				cc.setDescription(description.getInputText());
				
				DAOFactory df = Lookup.getDefault().lookup(DAOFactory.class);
				CostCategoryDAO ccd = df.getCostCategoryDAO();
				ccd.insert(cc);
			}
		}};
	}
}
