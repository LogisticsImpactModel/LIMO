package nl.fontys.sofa.limo.view.node;

import java.io.IOException;
import javax.swing.Action;
import javax.swing.JPanel;
import nl.fontys.sofa.limo.api.dao.DAOFactory;
import nl.fontys.sofa.limo.api.dao.TimeCategoryDAO;
import nl.fontys.sofa.limo.domain.category.TimeCategory;
import nl.fontys.sofa.limo.view.custom.components.NameDescriptionDialogInputPane;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.actions.NewAction;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.NewType;

/**
 * Root node view representation of the TimeCategory class.
 *
 * @author Sebastiaan Heijmann
 */
public class TimeCategoryRootNode extends AbstractNode{

	public TimeCategoryRootNode(Children children) {
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
				return "Time Category";
			}

			@Override
			public void create() throws IOException {
				NameDescriptionDialogInputPane inputPane = new NameDescriptionDialogInputPane();
				DialogDescriptor dd = new DialogDescriptor(inputPane, "Time Category");
				Object result = DialogDisplayer.getDefault().notify(dd);
	
				String name = inputPane.getNameFieldValue();
				String description = inputPane.getDescriptionFieldValue();
				TimeCategory tc = new TimeCategory();
				tc.setIdentifier(name);
				tc.setDescription(description);
				
				DAOFactory df = Lookup.getDefault().lookup(DAOFactory.class);
				TimeCategoryDAO tcd = df.getTimeCategoryDAO();
				tcd.insert(tc);
			}
		}};
	}
}