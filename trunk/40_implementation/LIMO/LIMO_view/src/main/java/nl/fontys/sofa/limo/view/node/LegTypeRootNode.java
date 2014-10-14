package nl.fontys.sofa.limo.view.node;

import java.awt.Image;
import java.io.IOException;
import javax.swing.Action;
import nl.fontys.sofa.limo.api.dao.LegTypeDAO;
import nl.fontys.sofa.limo.api.service.provider.LegService;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.custom.pane.NameDescriptionDialogInputPane;
import nl.fontys.sofa.limo.view.util.IconUtil;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.actions.NewAction;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.NewType;

/**
 * Root node view representation of the LegType class.
 *
 * @author Sebastiaan Heijmann
 */
public class LegTypeRootNode extends AbstractNode{
	private LegService service;

	public LegTypeRootNode(Children children) {
		super(children);
		service = Lookup.getDefault().lookup(LegService.class);
	}

	@Override
	public Image getIcon(int type) {
		Image icon = IconUtil.getIcon(LegType.class, type);
		if(icon == null){
			return super.getIcon(type);
		}
		return icon;
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
				return "LegType";
			}

			@Override
			public void create() throws IOException {
				NameDescriptionDialogInputPane inputPane = new NameDescriptionDialogInputPane();
				DialogDescriptor dd = new DialogDescriptor(inputPane, "Leg Type");
				Object result = DialogDisplayer.getDefault().notify(dd);
	
				String name = inputPane.getNameFieldValue();
				String description = inputPane.getDescriptionFieldValue();

				LegType lt = new LegType();
				lt.setName(name);
				lt.setDescription(description);
				
				service.insertLegType(lt);
			}
		}};
	}
}
