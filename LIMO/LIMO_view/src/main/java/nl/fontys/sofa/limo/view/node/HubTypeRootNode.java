package nl.fontys.sofa.limo.view.node;

import java.io.IOException;
import javax.swing.Action;
import nl.fontys.sofa.limo.api.dao.DAO;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.api.service.provider.HubTypeService;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.custom.pane.NameDescriptionDialogInputPane;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.actions.NewAction;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.NewType;

/**
 * Root node for HubType.
 *
 * @author Sebastiaan Heijmann
 */
public class HubTypeRootNode extends AbstractRootNode {

	public HubTypeRootNode(Children children) throws ServiceNotFoundException {
		super(children);
	}

	@Override
	Class getBeanClass() {
		return HubType.class;
	}

	@Override
	Class getServiceClass() {
		return HubTypeService.class;
	}

	@Override
	public Action[] getActions(boolean context) {
		return new Action[]{SystemAction.get(NewAction.class)};
	}

	@Override
	public NewType[] getNewTypes() {
		return new NewType[]{new NewType() {

			@Override
			public String getName() {
				return "Hub Type";
			}

			@Override
			public void create() throws IOException {
				NameDescriptionDialogInputPane inputPane = new NameDescriptionDialogInputPane();
				DialogDescriptor dd = new DialogDescriptor(inputPane, "Hub Type");
				Object result = DialogDisplayer.getDefault().notify(dd);

				String name = inputPane.getNameFieldValue();
				String description = inputPane.getDescriptionFieldValue();

				if (!name.isEmpty() && !description.isEmpty()) {
					HubType ht = new HubType();
					ht.setName(name);
					ht.setDescription(description);
					service.insert(ht);
				}
			}
		}};
	}
}
