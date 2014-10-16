package nl.fontys.sofa.limo.view.node;

import java.awt.Image;
import java.io.IOException;
import javax.swing.Action;
import nl.fontys.sofa.limo.api.service.provider.ProcedureCategoryService;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
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
 * Root node view representation of the CostCategory class.
 *
 * @author Sebastiaan Heijmann
 */
public class ProcedureCategoryRootNode extends AbstractNode{
	private ProcedureCategoryService service;

	public ProcedureCategoryRootNode(Children children) {
		super(children);
	    service = Lookup.getDefault().lookup(ProcedureCategoryService.class);
	}

	@Override
	public Image getIcon(int type) {
		Image icon = IconUtil.getIcon(ProcedureCategory.class, type);
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
				return "Cost Category";
			}

			@Override
			public void create() throws IOException {
				NameDescriptionDialogInputPane inputPane = new NameDescriptionDialogInputPane();
				DialogDescriptor dd = new DialogDescriptor(inputPane, "Cost Category");
				Object result = DialogDisplayer.getDefault().notify(dd);
	
				String name = inputPane.getNameFieldValue();
				String description = inputPane.getDescriptionFieldValue();

				ProcedureCategory pc = new ProcedureCategory();
				pc.setName(name);
				pc.setDescription(description);
				
				service.insert(pc);
			}
		}};
	}
}
